# -*- coding: utf-8 -*-
"""
Created on Thu Jan  19 16:29:03 2012
Rewritten on Tue July 28 13:09:00 2015

@author: Fesh0r, LexManos
@version: v7.0
"""

import sys
import os
import fnmatch
import shutil
import re
import zipfile
import time
from contextlib import closing
from optparse import OptionParser
from pprint import pprint

"""
This processes a FernFlower output file and fixes some of the common decompiler mistakes.
Making the output code cleaner and less errornious.
This takes advantage of the reconstituted local variables and inner class attributes that are present
in MC release 1.8.2 and above.

Things that are cleaned:

Consecutive empty lines are consensed:
    Line 1


    Line 2
    ------------------------------------
    Line 1

    Line 2
    ------------------------------------    

Trailing whitespace is removed:
  '  HELLO  '
  '  HELLO'

#Decompile differences between machines related to double and floats, by removing trailing zeros:
#  0.0010D => 0.001D

Unnessasary calls to super with zero arguments, this is implied by the compiler.
  'super();' => ''
  
Parameter names in abstract methods, seince abstract methods have no LVT attribute, FF does not name them correctly.
  '    <T extends Object & Comparable<T>, V extends T> IBlockState func_177226_a(IProperty<T> var1, V var2);'
  '    <T extends Object & Comparable<T>, V extends T> IBlockState func_177226_a(IProperty<T> p_177226_1_, V p_177226_1_);'
  
  
Enum Members, Enums are majorly syntax sugar, FernFlower does a good job at decompiling most of it.
However it still leaves the first two paramters in code. So we fix that:
  'LOGIN("LOGIN", 0, 1)' => 'LOGIN(1)'

#If a Enum's value is an anonymous inner class, the compiler adds a 'null' parameter to the initalizer. Unsure why but we need to strip this out.
#  'STONEBRICK("STONEBRICK", 2, 2, "stone_brick", "brick", (BlockSilverfish.NamelessClass1508106186)null) {'
#  'STONEBRICK(2, "stone_brick", "brick") {'

It also leaves those two parameters in the constructor arguments:
  'EnumSomething(String p_i123_1_, int p_i123_2_, int p_i123_3_)'
  'EnumSomething(int p_i123_3_)'

Synthetic methods, To support generics Java creates synthetic methods that bounce to concrete methods.
We scan for these methods that do nothing more then bounce with potential typcasting. And remove them
if the target method has the same name. This heavily relies on the mapping data having the correct mappings
  '// \$FF: synthetic method'
  'public Object call() {
  '   return (Object)this.call();'
  '}'

Fernflower does not properly add generic parameters to anonymous inner class declarations.
I can't think of a good way to fix this generically, so we fix it for the classes
used in Minecraft, Function, Predicate, and Comparator

'new Predicate() {' => 'new Predicate<String, ItemStack>() {'

"""

_JAVA_IDENTIFIER = r'[a-zA-Z_$][\w_$\.]*'
#Good regex but doesn't work on our windows portable install
#_JAVA_ANNOTATION = r'(?:\@(?:' + _JAVA_IDENTIFIER + ')(?:\((?:.*)*\))? ?)'
#Broken Regex cuz we use python 2.7.3, we need to update to 2.7.6+
_JAVA_ANNOTATION = r'(?:\@(?:' + _JAVA_IDENTIFIER + ')(?:\((?:.+)*\))? ?)'
_MODIFIERS = r'public|protected|private|static|abstract|final|native|synchronized|transient|volatile|strictfp'
_MODIFIERS_INIT = r'public|protected|private'
_PARAMETERS_VAR = r'(?:(?P<annotation>' + _JAVA_ANNOTATION + ')?(?P<type>(?:[^ ,])+(?:<.*>)?(?: \.\.\.)?) var(?P<id>\d+)(?P<end>,? )?)'
_PARAMETERS = r'(?:(?P<annotation>' + _JAVA_ANNOTATION + ')?(?P<type>(?:[^ ,])+(?:<.*>)?(?: \.\.\.)?) (?P<name>' + _JAVA_IDENTIFIER + r')(?P<end>,? )?)'

_REGEXP = {
    # Typecast marker
    'typecast': re.compile(r'\([\w\.\[\]]+\)'),
    
    # Remove repeated blank lines
    'newlines': re.compile(r'^\n{2,}', re.MULTILINE),
    
    # Normalize line ending to unix style
    'normlines': re.compile(r'\r?\n', re.MULTILINE),
    
    # Remove trailing whitespace
    'trailing': re.compile(r'[ \t]+$'),
    
    # strip trailing 0 from doubles and floats to fix decompile differences on OSX
    # 0.0010D => 0.001D
    #'trailingzero': re.compile(r'(?P<value>[0-9]+\.[0-9]*[1-9])0+(?P<type>[DdFfEe])'),
    
    # Remove unnessasary calls to super()
    #'empty_super': re.compile(r'^ +super\(\);\n'),
    
    # Cleanup the argument names on abstract methods
    'abstract': re.compile(r' (?P<method>func_(?P<number>\d+)_[a-zA-Z_]+)\((?P<arguments>' + _PARAMETERS_VAR + r'+)\)(?: throws (?:[\w$.]+,? ?)+)?;$'),
    
    # Single parts of parameter lists
    'params_var': re.compile(_PARAMETERS_VAR),
    
    # Empty Enum Switch Helper class detection
    #'class_header': re.compile(r'static class ' + _JAVA_IDENTIFIER + ' {'),
    #'obfid_field': re.compile(r'private static final String __OBFID = \"CL_\d+\";'),
    
    # Cleanup enum syntax sugar not being removed properly
    #'enum_member': re.compile(r'^(?P<indent> +)(?P<name>' + _JAVA_IDENTIFIER + r')\("(?P=name)", \d+(?P<sep>[,\)] *)(?P<end>.+)'),
    #
    # Enum declarations, used to find constructors
    #'enum_class': re.compile(r' enum (?P<name>' + _JAVA_IDENTIFIER + r') '),
    #
    # Enum constructor with sugar arguments
    #'enum_init': re.compile(r'^(?P<indent> +)(?P<modifiers>(?:(?:public|protected|private) )*)(?P<name>' + _JAVA_IDENTIFIER + r')\(String p_(?P<id>i\d+)_1_, int p_i\d+_2_(?:, )*(?P<end>.+)'),
    #
    # Empty enum ending
    #'enum_empty': re.compile(r'\)\s*(?:throws (?:[\w$.]+,? ?)+)?\s*\{\s*\}\s*$'),
    #
    # Enum anon classes add a random 'null' argument at the end.. No clue where this comes from
    #'enum_anon': re.compile(r'(?:, )*(?:\([\w\.]+\))*null\) \{'),
    #
    # Enum $VALUES field
    #'enum_values': re.compile(r'^\s*private static final (?P<name>' + _JAVA_IDENTIFIER + r')\[\] \$VALUES = new (?P=name)\[\]\{.*?\};'),
    #
    # Fernflower namecless classes scattered all over the place no clue why....
    #'nameless': re.compile(r'(?:, )*\([\w\.]+(NamelessClass\d+|SwitchHelper)\)null\)'),
    
    # Synthetic markers
    #'syn_marker': re.compile(r'^\s*// \$FF: (synthetic|bridge) method$'),
    
    # Method definition
    #'method_def': re.compile(r'^\s*(?P<modifiers>(?:(?:' + _MODIFIERS + r') )*)(?P<return>.+?) (?P<method>.+?)\((?P<arguments>' + _PARAMETERS + r'*)\)\s*(?:throws (?:[\w$.]+,? ?)+)?\s*\{'),
    
    # Method call
    #'syn_call': re.compile(r'^\s*(?P<return>return )?(this|super)\.(?P<target>.+)\((?P<arguments>(?:(?:(?:\([\w\.\[\]]+\))?[a-zA-Z_$][\w_$]*)(?:, )*)*)\);'),
    
    # Function generic method
    #'apply_def': re.compile(r'^\s*public (?P<return>.+?) apply\((?P<type>[^ ,]+(?:<.*>)?) p_apply_1_\)'),
    #
    # Predicate generic method`
    #'predicate_def': re.compile(r'^\s*public boolean apply\((?P<type>[^ ,]+(?:<.*>)?) p_apply_1_\)'),
    #
    # Comparator generic method
    #'compare_def': re.compile(r'^\s*public int compare\((?P<type>[^ ,]+(?:<.*>)?) p_compare_1_, '),
    #
    # TypeAdapter generic method
    #'write_def': re.compile(r'^\s*public void write\(JsonWriter p_write_1_, (?P<type>[^ ,]+(?:<.*>)?) p_write_2_\)'),
    #
    # SimpleChannelInboundHandler generic method
    #'channelRead0_def': re.compile(r'^\s*(public|protected) void channelRead0\(ChannelHandlerContext p_channelRead0_1_, (?P<type>[^ ,]+(?:<.*>)?) p_channelRead0_2_\)'),
    #
    # GenericFutureListener generic method
    #'operationComplete_def': re.compile(r'^\s*public void operationComplete\((?P<type>[^ ,]+(?:<.*>)?) p_operationComplete_1_\)'),
    #
    # FutureCallback generic method
    #'onSuccess_def': re.compile(r'^\s*public void onSuccess\((?P<type>[^ ,]+(?:<.*>)?) p_onSuccess_1_\)'),
    #
    # CacheLoader generic method
    #'load_def': re.compile(r'^\s*public (?P<return>.+?) load\((?P<type>[^ ,]+(?:<.*>)?) p_load_1_\)'),
    
}

class Error(Exception):
    pass
class ParseError(Error):
    pass

def fffix(srcdir):
    for path, _, filelist in os.walk(srcdir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            _process_file(src_file)

def fffix_zip_dir(src_file, dest_dir):
    #reallyrmtree(dest_dir)
    if not os.path.exists(dest_dir):
        os.makedirs(dest_dir)
    
    files = []
    with closing(zipfile.ZipFile(open(src_file, 'rb'))) as zip:
        for info in zip.filelist:
            data = zip.read(info.filename)
            if info.filename.endswith('.java'):
                print(info.filename)
                data = _process_data(data, os.path.splitext(os.path.basename(info.filename))[0])
            else:
              continue
                
            dest_file = os.path.join(dest_dir, info.filename)
            if not os.path.exists(os.path.dirname(dest_file)):
                os.makedirs(os.path.dirname(dest_file))
                
            with open(dest_file, 'wb') as f:
                f.write(data)
            
            files.append(dest_file.replace('\\', '/'))
    
    rmtree_filter(dest_dir, files);

def _process_file(src_file):
    if not os.path.splitext(src_file)[1] == '.java':
        return
    class_name = os.path.splitext(os.path.basename(src_file))[0]
    tmp_file = src_file + '.tmp'
    with open(src_file, 'r') as fh:
        orig = fh.read()
    
    buf = _process_data(orig, class_name)
    
    if not buf == orig:
        with open(tmp_file, 'w') as fh:
            fh.write(buf)
        shutil.move(tmp_file, src_file)
        
def _process_data(data, class_name):
    buf = data
    buf = _REGEXP['normlines'].sub(r'\n', buf)
    buf = buf.split('\n')
    
    #enums = []
    
    for idx, line in enumerate(buf):
        line_s = line.strip();
        # Gather Enum names for use in constructors
        #for match in _REGEXP['enum_class'].finditer(line):
        #    enums.append(match.group('name'))
        match = None
    
        # Fix Compile differences related to doubles and floats
        #line = _REGEXP['trailingzero'].sub(r'\g<value>\g<type>', line)
        
        # Remove unnessasary super calls
        if line_s == 'super();':
            line = ''
        
        # Remove casts to nameless classes, TODO: Research why these exist in the first place...
        #line = _REGEXP['nameless'].sub(r')', line)
        
        #if line_s == '// $FF: synthetic class':
        #  if not _REGEXP['class_header'].match(buf[idx+1].strip()) is None and not _REGEXP['obfid_field'].match(buf[idx+2].strip()) is None and buf[idx+3].strip() == '}':
        #    line = buf[idx] = buf[idx+1] = buf[idx+2] = buf[idx+3] = ''
        
        #if line_s == '// $FF: synthetic method' or line_s == '// $FF: bridge method':
        #    i = idx + 1
        #    if buf[i].strip() == '// $FF: synthetic method' or buf[i].strip() == '// $FF: bridge method':
        #        i += 1
        #    method = _REGEXP['method_def'].match(buf[i])
        #    body   = _REGEXP['syn_call'].match(buf[i+1])
        #    end    = buf[i+2].strip() == '}'
        #    if method and body and end:
        #        if method.group('method') == body.group('target'):
        #            args1 = '' if method.group('arguments') == '' else ', '.join([v.split(' ')[1] for v in method.group('arguments').split(', ')])
        #            args2 = '' if body.group('arguments') == None else _REGEXP['typecast'].sub('', body.group('arguments'))
        #            if args1 == args2:
        #                line = buf[i-1] = buf[i] = buf[i+1] = buf[i+2] = ''
        #            else:
        #                print 'MISMATCH ARGS %s' % buf[i]
        #                print '              %s' % args1
        #                print 'MISMATCH ARGS %s' % buf[i+1]
        #                print '              %s' % args2
        #        else:
        #            print 'MISMATCH TARGET %s %s' % (method.group('method'), body.group('target'))
        #        #print '  MATCH ' + buf[i]
        #        #print '    ' + buf[i+1]
        #        #pprint(body.groupdict())
        #    else:
        #        if buf[i].endswith(') {') and buf[i+1].lstrip().startswith('this(') and end:
        #            line = buf[i-1] = buf[i] = buf[i+1] = buf[i+2] = ''
        #        #else:
        #        #    print 'MISMATCH ' + buf[i]
        #        #    print 'MISMATCH ' + buf[i+1]
        #        #    print 'MISMATCH ' + buf[i+2]
        
        #match = _REGEXP['enum_member'].search(line)
        #if not match is None:
        #    end = _REGEXP['enum_anon'].sub(r') {', match.group('end'))
        #    line = match.group('indent') + match.group('name')
        #    if not match.group('sep') == ')':
        #        line = line + '(' + end
        #    else:
        #        line = line + end
        
        #match = _REGEXP['enum_init'].search(line)
        #if not match is None and match.group('name') in enums:
        #    if _REGEXP['enum_empty'].search(match.group('end')):
        #        line = ''
        #    else:
        #        line = match.group('indent') + match.group('modifiers') + match.group('name') + '(' + match.group('end')
        #        buf[idx+1] = buf[idx+1].replace('this(p_%s_1_, p_%s_2_, ' % (match.group('id'), match.group('id')), 'this(')
        
        # Strip out synthetic enum $VALUES array
        #if line_s == '// $FF: synthetic field':
        #    if _REGEXP['enum_values'].match(buf[idx+1]):
        #        line = buf[idx+1] = ''
        
        def abstract_match(match):
            args = match.group('arguments')
            args = _REGEXP['params_var'].sub(lambda m: '%s p_%s_%s_%s' % (m.group('type'), match.group('number'), m.group('id'), m.group('end') if not m.group('end') is None else ''), args)
            return match.group(0).replace(match.group('arguments'), args)
        
        if (line.endswith(";")):
            # Cleanup the argument names on abstract methods
            line = _REGEXP['abstract'].sub(abstract_match, line)
            
        #def find_params(buf, index, indent, REG):
        #    for x in range(index, len(buf)):
        #        if not buf[x].endswith('{'):
        #            continue
        #        match = REG.match(buf[x])
        #        if match:
        #            return [match.group('return'), match.group('type')]
        #        if buf[x].startswith(indent):
        #            return None
        #    return None
        #    
        #def find_param(buf, index, indent, REG):
        #    for x in range(index, len(buf)):
        #        if not buf[x].endswith('{'):
        #            continue
        #        match = REG.match(buf[x])
        #        if match:
        #            return match.group('type')
        #        if buf[x].startswith(indent):
        #            return None
        #    return None
        #    
        #def fix_anon_one(buf, idx, line, cls, REG):
        #    if line.endswith(cls + '() {'):
        #        param = find_param(buf, idx + 1, ''.ljust(len(line) - len(line_s)) + '}', REG)
        #        if not param is None:
        #            return '%s%s<%s>() {' % (line[:-4 - len(cls)], cls, param)
        #    return line
        #    
        #def fix_anon_two(buf, idx, line, cls, REG):
        #    if line.endswith(cls + '() {'):
        #        params = find_params(buf, idx + 1, ''.ljust(len(line) - len(line_s)) + '}', REG)
        #        if not params is None:
        #            return '%s%s<%s, %s>() {' % (line[:-4 - len(cls)], cls, params[1], params[0])
        #    return line
        #
        # Fixup anonymous Function, Predicate, and Comparator classes
        #if line.endswith('() {'):
        #    line = fix_anon_two(buf, idx, line, 'new Function', _REGEXP['apply_def'])
        #    line = fix_anon_two(buf, idx, line, 'new CacheLoader', _REGEXP['load_def'])
        #    line = fix_anon_one(buf, idx, line, 'new Predicate', _REGEXP['predicate_def'])
        #    line = fix_anon_one(buf, idx, line, 'new Comparator', _REGEXP['compare_def'])
        #    line = fix_anon_one(buf, idx, line, 'new TypeAdapter', _REGEXP['write_def'])
        #    line = fix_anon_one(buf, idx, line, 'new SimpleChannelInboundHandler', _REGEXP['channelRead0_def'])
        #    line = fix_anon_one(buf, idx, line, 'new GenericFutureListener', _REGEXP['operationComplete_def'])
        #    line = fix_anon_one(buf, idx, line, 'new FutureCallback', _REGEXP['onSuccess_def'])
        #    line = fix_anon_one(buf, idx, line, 'new CacheLoader', _REGEXP['load_def'])
        
        # Trim trailing whitespace
        buf[idx] = line.rstrip()
    
    buf = '\n'.join(buf)
    
    # Condense any consecutive empty lines
    buf = _REGEXP['newlines'].sub(r'\n', buf)
    
    return buf

def main():
    usage = 'usage: %prog [options] src [dest]'
    version = '%prog 7.0'
    parser = OptionParser(version=version, usage=usage)
    options, args = parser.parse_args()
    if len(args) == 1:
        fffix(args[0])
    elif len(args) == 2:
        fffix_zip_dir(args[0], args[1])
    else:
        print >> sys.stderr, 'src_dir required'
        sys.exit(1)

def rmtree_filter(path, whitelist):
  for root, dirs, files in os.walk(path):
      for file in files:
          tmp = os.path.join(root, file).replace('\\', '/')
          if not tmp in whitelist:
              print 'Removing: ' + tmp
              os.remove(tmp)
      
      for dir in dirs:
          rmtree_filter(os.path.join(root, dir), whitelist)
  
  for root, dirs, files in os.walk(path):
      if len(dirs) == 0 and len(files) == 0:
          os.rmdir(root)
        
def reallyrmtree(path):
    if not sys.platform.startswith('win'):
        if os.path.exists(path):
            shutil.rmtree(path)
    else:
        i = 0
        try:
            while os.stat(path) and i < 20:
                shutil.rmtree(path, onerror=rmtree_onerror)
                i += 1
        except OSError:
            pass

        # raise OSError if the path still exists even after trying really hard
        try:
            os.stat(path)
        except OSError:
            pass
        else:
            raise OSError(errno.EPERM, "Failed to remove: '" + path + "'", path)
            
def rmtree_onerror(func, path, _):
    if not os.access(path, os.W_OK):
        os.chmod(path, stat.S_IWUSR)
    time.sleep(0.5)
    try:
        func(path)
    except OSError:
        pass

if __name__ == '__main__':
    main()
