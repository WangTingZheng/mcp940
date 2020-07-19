# -*- coding: utf-8 -*-
"""
Created on Thu Jan  19 16:29:03 2012

@author: Fesh0r
@version: v0.1
"""

import sys
import os
import fnmatch
import shutil
import re
from optparse import OptionParser


_MODIFIERS = r'public|protected|private|static|abstract|final|native|synchronized|transient|volatile|strictfp'

_REGEXP = {
    # convert OSX double 1.23456789E-12D to Windows double 1.23456789E-012D
    'fltexp': re.compile(r'(?<=[Ee][+-])([0-9]{2})(?=[DdFf])'),

    # Remove trailing whitespace
    'trailing': re.compile(r'[ \t]+$', re.MULTILINE),

    # close up extends, and implements that JAD puts on a seperate line
    'extends': re.compile(r'$\n {4}(?=extends|implements)', re.MULTILINE),

    # close up throws that JAD puts on a seperate line
    'throws': re.compile(r'$\n {8}(?=throws)', re.MULTILINE),

    # close up method parameters that have wrapped
    'params': re.compile(r'^ {4}(?P<main>\S.*?)(?P<wrapped>(?:\n {12}\S.*?)+)(?P<suffix>(?:\n {4}\{$)|;)', re.MULTILINE),
    'params_sub': re.compile(r'\n {12}', re.MULTILINE),

    # Remove repeated blank lines
    'newlines': re.compile(r'^\n{2,}', re.MULTILINE),

    # Rename variables called enum
    'rename_enum': re.compile(r'(?<=\W)(enum)(?=\W)'),

    'modifiers': re.compile(r'(' + _MODIFIERS + ') '),
    'list': re.compile(r', '),
    'deindent': re.compile(r'^ {4}(.*$)', re.MULTILINE),

    # Class
    'class': re.compile(r'^(?P<modifiers>(?:(?:' + _MODIFIERS + r') )*)(?P<type>class|interface|enum) (?P<name>[\w$]+)(?: extends (?P<extends>[\w$.]+(?:, [\w$.]+)*))?(?: implements (?P<implements>[\w$.]+(?:, [\w$.]+)*))?\n\{\n(?P<body>(?:.*?\n)*?)(?P<end>\}\n+)', re.MULTILINE),

    # method regex
    'method': re.compile(r'^ {4}(?P<modifiers>(?:(?:' + _MODIFIERS + r') )*)(?P<type>(?!' + _MODIFIERS + r')[\w$.[\]]+) (?P<name>[\w$]+)\((?P<parameters>.*?)\)(?: throws (?P<throws>[\w$.]+(?:, [\w$.]+)*))?\n {4}\{\n(?P<body>(?:.*?\n)*?)(?P<end> {4}\}\n+)', re.MULTILINE),

    # abstract method regex
    'method_abstract': re.compile(r'^ {4}(?P<modifiers>(?:(?:' + _MODIFIERS + r') )*)(?P<type>(?!' + _MODIFIERS + r')[\w$.[\]]+) (?P<name>[\w$]+)\((?P<parameters>.*?)\)(?: throws (?P<throws>[\w$.]+(?:, [\w$.]+)*))?(?P<end>;\n+)', re.MULTILINE),

    # move super call to start of method
    'fix_super': re.compile(r'(?P<before>(?:.*\n)*)(?P<super> {8}super\((?P<parameters>.*?)\);\n)(?P<after>(?:.*\n)*)', re.MULTILINE),

    # remove super call in enum
    'enum_super': re.compile(r' {8}super\(\w+, \w+\);\n', re.MULTILINE),

    # static block
    'static': re.compile(r'^ {4}static\n {4}\{\n(?P<body>(?:.*?\n)*?)(?P<end> {4}\}\n+)', re.MULTILINE),

    # field_1234.field_5678 += abc + def;
    'str1': re.compile(r'(?P<indent>^ +)new StringBuilder\(\);\n +(?P<dest1>.*?);\n +JVM INSTR dup_x1 ;\n +(?P<dest2>.*?);\n +append\(\);\n +(?P<src1>.*?);\n(:? +append\(\);\n +(?P<src2>.*?);\n)? +append\(\);\n +toString\(\);\n +(?P=dest2);$', re.MULTILINE),

    # field_1234[field_5678] += abc + def;
    'str2': re.compile(r'(?P<indent>^ +)new StringBuilder\(\);\n +(?P<dest1>.*?);\n +(?P<dest2>.*?);\n +JVM INSTR dup2_x1 ;\n +JVM INSTR aaload ;\n +append\(\);\n +(?P<src1>.*?);\n +append\(\);\n(:? +(?P<src2>.*?);\n +append\(\);\n)? +toString\(\);\n +JVM INSTR aastore ;$', re.MULTILINE),

    # if(test) goto _L1; else goto _L2
    'if_goto': re.compile(r'(?P<indent>^ +)if(?P<test>\(.*\)) goto (?P<label1>_L[0-9]+); else goto (?P<label2>_L[0-9]+)\n(?P<label3>_L[0-9]+):$', re.MULTILINE),
}

_REGEXP_STR = {
    'constructor': r'^ {4}(?P<modifiers>(?:(?:' + _MODIFIERS + r') )*)%s\((?P<parameters>.*?)\)(?: throws (?P<throws>[\w$.]+(?:, [\w$.]+)*))?\n {4}\{\n(?P<body>(?:.*?\n)*?)(?P<end> {4}\}\n+)',

    'enum_methods': r'^ {4}public static %s(?:\[\])? value(?:s|Of)\(.*?\)\n {4}\{\n(?:.*?\n)*? {4}\}\n+',

    'enum_fields': r'^ {4}(?:public|private) static final %s [\w$.[\]]+;.*\n',

    'enum_entries': r'^ {8}(?P<name>[\w$]+) = new %s\("(?P=name)", [0-9]+(?:, (?P<body>.*?))?\);\n+',

    'enum_values': r'^ {8}(?P<name>[\w$]+)(?P<body> = \(new %s\[\] \{\n(?:.*\n)*? {8}\}\));\n+',
}


class Error(Exception):
    pass


class ParseError(Error):
    pass


def jadfix(srcdir):
    for path, _, filelist in os.walk(srcdir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            _process_file(src_file)


def _process_class(class_name, class_type, modifiers, extends, implements, body, end):
    if class_type == 'class':
        # if we have an enum class then fix the class declaration
        if 'final' in modifiers and 'Enum' in extends:
            modifiers.remove('final')
            extends.remove('Enum')
            class_type = 'enum'
    if class_type == 'interface':
        # is this an annotation type? still missing too much info for it to actually work
        if 'Annotation' in extends:
            extends.remove('Annotation')
            class_type = '@interface'
    if class_type == 'enum':
        body = _process_enum(class_name, body)

    # process normal methods
    def method_match(match):
        modifiers = _REGEXP['modifiers'].findall(match.group('modifiers'))
        if match.group('modifiers') and not modifiers:
            raise ParseError('no modifiers match in %s \'%s\'' % (match.group('name'), match.group('modifiers')))
        parameters = []
        if match.group('parameters'):
            parameters = _REGEXP['list'].split(match.group('parameters'))
        throws = []
        if match.group('throws'):
            throws = _REGEXP['list'].split(match.group('throws'))
        return _process_method(class_name, match.group('name'), modifiers, match.group('type'), parameters, throws,
                               match.group('body'), match.group('end'))
    body = _REGEXP['method'].sub(method_match, body)

    # process abstract methods
    def method_abstract_match(match):
        modifiers = _REGEXP['modifiers'].findall(match.group('modifiers'))
        if match.group('modifiers') and not modifiers:
            raise ParseError('no modifiers match in %s \'%s\'' % (match.group('name'), match.group('modifiers')))
        parameters = []
        if match.group('parameters'):
            parameters = _REGEXP['list'].split(match.group('parameters'))
        throws = []
        if match.group('throws'):
            throws = _REGEXP['list'].split(match.group('throws'))
        return _process_method_abstract(class_name, match.group('name'), modifiers, match.group('type'), parameters,
                                        throws, match.group('end'))
    body = _REGEXP['method_abstract'].sub(method_abstract_match, body)

    # process constructors
    def constructor_match(match):
        modifiers = _REGEXP['modifiers'].findall(match.group('modifiers'))
        if match.group('modifiers') and not modifiers:
            raise ParseError('no modifiers match in %s \'%s\'' % (match.group('name'), match.group('modifiers')))
        parameters = []
        if match.group('parameters'):
            parameters = _REGEXP['list'].split(match.group('parameters'))
        throws = []
        if match.group('throws'):
            throws = _REGEXP['list'].split(match.group('throws'))
        return _process_constructor(class_type, class_name, modifiers, parameters, throws, match.group('body'),
                                    match.group('end'))
    constructor_regex = re.compile(_REGEXP_STR['constructor'] % re.escape(class_name), re.MULTILINE)
    body = constructor_regex.sub(constructor_match, body)

    # rebuild class
    out = ''
    if modifiers:
        out += ' '.join(modifiers) + ' '
    out += class_type + ' ' + class_name
    if extends:
        out += ' extends ' + ', '.join(extends)
    if implements:
        out += ' implements ' + ', '.join(implements)
    out += '\n{\n' + body + end
    return out


def _process_enum(class_name, body):
    # remove super call in constructor
    body = _REGEXP['enum_super'].sub(r'', body)

    # remove values and valueOf methods
    methods_regex = re.compile(_REGEXP_STR['enum_methods'] % re.escape(class_name), re.MULTILINE)
    body = methods_regex.sub(r'', body)

    # remove enum fields and $VALUES
    fields_regex = re.compile(_REGEXP_STR['enum_fields'] % re.escape(class_name), re.MULTILINE)
    body = fields_regex.sub(r'', body)

    # rebuild enum entries from static block
    body = _process_enum_static(class_name, body)

    return body


def _process_enum_static(class_name, enum_body):
    # do we have a static block?
    static_match = _REGEXP['static'].search(enum_body)
    if not static_match:
        return enum_body

    body = static_match.group('body')

    # for each enum field in the static build up a enum entry
    enum_entries = ''
    entries = []

    def _enum_entries_match(match):
        entry_body = ''
        if match.group('body'):
            entry_body = '(' + match.group('body') + ')'
        new_entry = '        ' + match.group('name') + entry_body
        new_entry = _REGEXP['deindent'].sub(r'\1', new_entry)
        entries.append(new_entry)
        return ''
    entries_regex = re.compile(_REGEXP_STR['enum_entries'] % re.escape(class_name), re.MULTILINE | re.DOTALL)
    body = entries_regex.sub(_enum_entries_match, body)
    if entries:
        enum_entries = '\n' + ',\n'.join(entries) + ';\n\n'

    # remove the $VALUES array from the static block
    values_regex = re.compile(_REGEXP_STR['enum_values'] % re.escape(class_name), re.MULTILINE)
    body = values_regex.sub('', body)

    # add the entries and $VALUES to start of body
    enum_body = enum_entries + enum_body

    # remove the entries and values from the static block
    # and remove the block entirely if now empty
    full_static = ''
    if body:
        full_static = '    static\n    {\n' + body + static_match.group('end')
    enum_body = _REGEXP['static'].sub(full_static, enum_body)
    return enum_body


def _process_method(_class_name, method_name, modifiers, method_type, parameters, throws, body, end):
    # kill off the wierd _mthclass$ methods that JAD sticks in for some reason
    if method_name == '_mthclass$' and 'static' in modifiers:
        return ''

    body = _process_string(body)
    body = _process_if_goto(body)

    # rebuild method
    out = '    '
    if modifiers:
        out += ' '.join(modifiers) + ' '
    out += method_type + ' ' + method_name + '(' + ', '.join(parameters) + ')'
    if throws:
        out += ' throws ' + ', '.join(throws)
    out += '\n    {\n' + body + end
    return out


def _process_method_abstract(_class_name, method_name, modifiers, method_type, parameters, throws, end):
    # rebuild method
    out = '    '
    if modifiers:
        out += ' '.join(modifiers) + ' '
    out += method_type + ' ' + method_name + '(' + ', '.join(parameters) + ')'
    if throws:
        out += ' throws ' + ', '.join(throws)
    out += end
    return out


def _process_constructor(class_type, class_name, modifiers, parameters, throws, body, end):
    if class_type == 'enum':
        if len(parameters) >= 2:
            if parameters[0].startswith('String ') and parameters[1].startswith('int '):
                parameters = parameters[2:]
                # empty constructor
                if body == '' and len(parameters) == 0:
                    return ''
            else:
                raise ParseError('invalid initial parameters in enum %s: %s' % (class_name, str(parameters)))
        else:
            raise ParseError('not enough parameters in enum %s: %s' % (class_name, str(parameters)))

    body = _process_string(body)
    body = _process_if_goto(body)

    # move super calls to start of constructor and remove empty super calls
    def super_match(match):
        if match.group('parameters'):
            return match.group('super') + match.group('before') + match.group('after')
        else:
            return match.group('before') + match.group('after')
    body = _REGEXP['fix_super'].sub(super_match, body)

    # rebuild constructor
    out = '    '
    if modifiers:
        out += ' '.join(modifiers) + ' '
    out += class_name + '(' + ', '.join(parameters) + ')'
    if throws:
        out += ' throws ' + ', '.join(throws)
    out += '\n    {\n' + body + end
    return out


def _process_string(body):
    # fix up plain string appends
    def string1_match(match):
        indent = match.group('indent')
        src = match.group('src1')
        if match.group('src2'):
            src = '%s + %s' % (src, match.group('src2'))
        dest = match.group('dest2')
        if match.group('dest1') != 'this':
            dest = '%s.%s' % (match.group('dest1'), dest)
        return '%s%s += %s;' % (indent, dest, src)
    body = _REGEXP['str1'].sub(string1_match, body)

    # fix up string appends to an array
    def string2_match(match):
        indent = match.group('indent')
        src = match.group('src1')
        if match.group('src2'):
            src = '%s + %s' % (src, match.group('src2'))
        dest = '%s[%s]' % (match.group('dest1'), match.group('dest2'))
        return '%s%s += %s;' % (indent, dest, src)
    body = _REGEXP['str2'].sub(string2_match, body)

    return body


def _process_if_goto(body):
    def if_goto_match(match):
        indent = match.group('indent')
        # depending on the following label negate the if test
        if match.group('label3') == match.group('label2'):
            test = 'if(!%s)' % match.group('test')
            comment = '## JADFIX %s %s' % (match.group('label2'), match.group('label1'))
        else:
            test = 'if%s' % match.group('test')
            comment = '## JADFIX %s %s' % (match.group('label1'), match.group('label2'))
        label = '%s:' % match.group('label3')
        return '%s%s\n%s\n%s' % (indent, test, comment, label)
    body = _REGEXP['if_goto'].sub(if_goto_match, body)
    return body


def _process_file(src_file):
    class_name = os.path.splitext(os.path.basename(src_file))[0]
    tmp_file = src_file + '.tmp'
    with open(src_file, 'r') as fh:
        buf = fh.read()

    buf = _REGEXP['fltexp'].sub(r'0\1', buf)
    buf = _REGEXP['trailing'].sub(r'', buf)

    buf = _REGEXP['extends'].sub(r' ', buf)
    buf = _REGEXP['throws'].sub(r' ', buf)

    def params_match(match):
        body = re.sub(_REGEXP['params_sub'], r' ', match.group('wrapped'))
        return '    %s%s%s' % (match.group('main'), body, match.group('suffix'))
    buf = _REGEXP['params'].sub(params_match, buf)

    buf = _REGEXP['rename_enum'].sub(r'\1_', buf)

    def class_match(match):
        if class_name != match.group('name'):
            raise ParseError("file name and class name differ: '%s' '%s" % (class_name, match.group('name')))
        modifiers = _REGEXP['modifiers'].findall(match.group('modifiers'))
        if match.group('modifiers') and not modifiers:
            raise ParseError("no modifiers match in %s '%s'" % (match.group('name'), match.group('modifiers')))
        extends = []
        if match.group('extends'):
            extends = _REGEXP['list'].split(match.group('extends'))
        implements = []
        if match.group('implements'):
            implements = _REGEXP['list'].split(match.group('implements'))
        return _process_class(match.group('name'), match.group('type'), modifiers, extends, implements,
                              match.group('body'), match.group('end'))
    (buf, match_count) = _REGEXP['class'].subn(class_match, buf)
    if not match_count:
        raise ParseError('no class in %s' % class_name)

    buf = _REGEXP['newlines'].sub(r'\n', buf)

    with open(tmp_file, 'w') as fh:
        fh.write(buf)
    shutil.move(tmp_file, src_file)


def main():
    usage = 'usage: %prog [options] src_dir'
    version = '%prog 6.0'
    parser = OptionParser(version=version, usage=usage)
    options, args = parser.parse_args()
    if len(args) != 1:
        print >> sys.stderr, 'src_dir required'
        sys.exit(1)
    jadfix(args[0])


if __name__ == '__main__':
    main()
