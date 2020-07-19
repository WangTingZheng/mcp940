import sys
import os
import re
import fnmatch
import shutil
from optparse import OptionParser


def strip_comments(src_dir):
    """Removes all C/C++/Java-style comments from files"""

    regexps = {
        # Remove C and C++ style comments
        'comments': re.compile(r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
                               re.MULTILINE | re.DOTALL),

        # Remove trailing whitespace
        'trailing': re.compile(r'[ \t]+$', re.MULTILINE),

        # Remove repeated blank lines
        'newlines': re.compile(r'^\n{2,}', re.MULTILINE),
    }

    def comment_replacer(match):
        part = match.group(0)
        if part.startswith('/'):
            return ''
        else:
            return part

    for path, _, filelist in os.walk(src_dir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            tmp_file = src_file + '.tmp'
            with open(src_file, 'r') as fh:
                buf = fh.read()

            buf = regexps['comments'].sub(comment_replacer, buf)
            buf = regexps['trailing'].sub(r'', buf)
            buf = regexps['newlines'].sub(r'\n', buf)

            with open(tmp_file, 'w') as fh:
                fh.write(buf)
            shutil.move(tmp_file, src_file)


def src_cleanup(src_dir, fix_imports=True, fix_unicode=False, fix_charval=False, fix_pi=False, fix_round=False):
    """Do lots of random cleanups including stripping comments, trailing whitespace and extra blank lines"""

    regexps = {
        # Remove extra whitespace at start of file
        'header': re.compile(r'^\s+'),

        # Remove extra whitespace at end of file
        'footer': re.compile(r'\s+$'),

        # Remove trailing whitespace
        'trailing': re.compile(r'[ \t]+$', re.MULTILINE),

        # find package
        'package': re.compile(r'^package (?P<package>[\w.]+);$', re.MULTILINE),

        # find imports
        'import': re.compile(r'^import (?:(?P<package>[\w.]*?)\.)?(?P<class>[\w]+);\n', re.MULTILINE),

        # Remove repeated blank lines
        'newlines': re.compile(r'^\n{2,}', re.MULTILINE),

        # close up blanks in code like:
        # {
        #
        #     private
        'blockstarts': re.compile(r'(?<={)\s+(?=\n[ \t]*\S)', re.MULTILINE),

        # close up blanks in code like:
        #     }
        #
        # }
        'blockends': re.compile(r'(?<=[;}])\s+(?=\n\s*})', re.MULTILINE),

        # Remove GL comments and surrounding whitespace
        'gl': re.compile(r'\s*/\*\s*GL_[^*]+\*/\s*'),

        # convert unicode character constants back to integers
        'unicode': re.compile(r"'\\u([0-9a-fA-F]{4})'"),

        # strip out Character.valueof
        'charval': re.compile(r"Character\.valueOf\(('.')\)"),

        # 1.7976...E+308D to Double.MAX_VALUE
        'maxD': re.compile(r'1\.7976[0-9]*[Ee]\+308[Dd]'),

        # 3.1415...D to Math.PI
        'piD': re.compile(r'3\.1415[0-9]*[Dd]'),

        # 3.1415...F to (float)Math.PI
        'piF': re.compile(r'3\.1415[0-9]*[Ff]'),

        # 6.2831...D to (Math.PI * 2D)
        '2piD': re.compile(r'6\.2831[0-9]*[Dd]'),

        # 6.2831...F to ((float)Math.PI * 2F)
        '2piF': re.compile(r'6\.2831[0-9]*[Ff]'),

        # 1.5707...D to (Math.PI / 2D)
        'pi2D': re.compile(r'1\.5707[0-9]*[Dd]'),

        # 1.5707...F to ((float)Math.PI / 2F)
        'pi2F': re.compile(r'1\.5707[0-9]*[Ff]'),

        # 4.7123...D to (Math.PI * 3D / 2D)
        '3pi2D': re.compile(r'4\.7123[0-9]*[Dd]'),

        # 4.7123...F to ((float)Math.PI * 3F / 2F)
        '3pi2F': re.compile(r'4\.7123[0-9]*[Ff]'),

        # 0.7853...D to (Math.PI / 4D)
        'pi4D': re.compile(r'0\.7853[0-9]*[Dd]'),

        # 0.7853...F to ((float)Math.PI / 4F)
        'pi4F': re.compile(r'0\.7853[0-9]*[Ff]'),

        # 0.6283...D to (Math.PI / 5D)
        'pi5D': re.compile(r'0\.6283[0-9]*[Dd]'),

        # 0.6283...F to ((float)Math.PI / 5F)
        'pi5F': re.compile(r'0\.6283[0-9]*[Ff]'),

        # 57.295...D to (180D / Math.PI)
        '180piD': re.compile(r'57\.295[0-9]*[Dd]'),

        # 57.295...F to (180F / (float)Math.PI)
        '180piF': re.compile(r'57\.295[0-9]*[Ff]'),

        # 0.6981...D to (Math.PI * 2D / 9D)
        '2pi9D': re.compile(r'0\.6981[0-9]*[Dd]'),

        # 0.6981...F to ((float)Math.PI * 2F / 9F)
        '2pi9F': re.compile(r'0\.6981[0-9]*[Ff]'),

        # 0.3141...D to (Math.PI / 10D)
        'pi10D': re.compile(r'0\.3141[0-9]*[Dd]'),

        # 0.3141...F to ((float)Math.PI / 10F)
        'pi10F': re.compile(r'0\.3141[0-9]*[Ff]'),

        # 1.2566...D to (Math.PI * 2D / 5D)
        '2pi5D': re.compile(r'1\.2566[0-9]*[Dd]'),

        # 1.2566...F to ((float)Math.PI 2F / 5F)
        '2pi5F': re.compile(r'1\.2566[0-9]*[Ff]'),

        # 0.21991...D to (Math.PI * 7D / 100D)
        '7pi100D': re.compile(r'0\.21991[0-9]*[Dd]'),

        # 0.21991...F to ((float)Math.PI * 7F / 100F)
        '7pi100F': re.compile(r'0\.21991[0-9]*[Ff]'),

        # 5.8119...D to (Math.PI * 185D / 100D)
        '185pi100D': re.compile(r'5\.8119[0-9]*[Dd]'),

        # 5.8119...F to ((float)Math.PI * 185F / 100F)
        '185pi100F': re.compile(r'0\.8119[0-9]*[Ff]'),

        # 1.230000... to 1.23
        'rounddown': re.compile(r'(?P<full>[0-9]+\.(?P<decimal>[0-9]+?)00000000[0-9]*)(?P<type>[Dd])'),

        # 1.239999... to 1.24
        'roundup': re.compile(r'(?P<full>[0-9]+\.(?P<decimal>[0-9]+?9)9999999[0-9]*)(?P<type>[Dd])'),
    }

    def unicode_replacer(match):
        value = int(match.group(1), 16)
        # work around the replace('\u00a7', '$') call in MinecraftServer and a couple of '\u0000'
        if value > 255:
            return str(value)
        return match.group(0)

    def rounddown_match(match):
        # hackaround for GL11.glScalef(1.000001F, 1.000001F, 1.000001F) in WorldRenderer
        if match.group(0) == '1.000001F':
            return match.group(0)
        val = float(match.group('full'))
        return '%.*f%s' % (len(match.group('decimal')), val, match.group('type'))

    def roundup_match(match):
        val = float(match.group('full'))
        return '%.*f%s' % (len(match.group('decimal')) - 1, val, match.group('type'))

    # HINT: We pathwalk the sources
    for path, _, filelist in os.walk(src_dir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            tmp_file = src_file + '.tmp'
            with open(src_file, 'r') as fh:
                buf = fh.read()

            if fix_imports:
                # find the package for the current class
                match = regexps['package'].search(buf)
                if match:
                    package = match.group('package')

                    # if the import is for the same package as current class then delete it
                    def import_match(match):
                        if match.group('package') != package:
                            return match.group(0)
                        return ''
                    buf = regexps['import'].sub(import_match, buf)

            buf = regexps['header'].sub(r'', buf)
            buf = regexps['footer'].sub(r'\n', buf)
            buf = regexps['trailing'].sub(r'', buf)
            buf = regexps['newlines'].sub(r'\n', buf)
            buf = regexps['blockstarts'].sub(r'', buf)
            buf = regexps['blockends'].sub(r'', buf)
            buf = regexps['gl'].sub(r'', buf)
            buf = regexps['maxD'].sub(r'Double.MAX_VALUE', buf)
            if fix_unicode:
                buf = regexps['unicode'].sub(unicode_replacer, buf)
            if fix_charval:
                buf = regexps['charval'].sub(r'\1', buf)

            if fix_pi:
                buf = regexps['piD'].sub(r'Math.PI', buf)
                buf = regexps['piF'].sub(r'(float)Math.PI', buf)
                buf = regexps['2piD'].sub(r'(Math.PI * 2D)', buf)
                buf = regexps['2piF'].sub(r'((float)Math.PI * 2F)', buf)
                buf = regexps['pi2D'].sub(r'(Math.PI / 2D)', buf)
                buf = regexps['pi2F'].sub(r'((float)Math.PI / 2F)', buf)
                buf = regexps['3pi2D'].sub(r'(Math.PI * 3D / 2D)', buf)
                buf = regexps['3pi2F'].sub(r'((float)Math.PI * 3F / 2F)', buf)
                buf = regexps['pi4D'].sub(r'(Math.PI / 4D)', buf)
                buf = regexps['pi4F'].sub(r'((float)Math.PI / 4F)', buf)
                buf = regexps['pi5D'].sub(r'(Math.PI / 5D)', buf)
                buf = regexps['pi5F'].sub(r'((float)Math.PI / 5F)', buf)
                buf = regexps['180piD'].sub(r'(180D / Math.PI)', buf)
                buf = regexps['180piF'].sub(r'(180F / (float)Math.PI)', buf)
                buf = regexps['2pi9D'].sub(r'(Math.PI * 2D / 9D)', buf)
                buf = regexps['2pi9F'].sub(r'((float)Math.PI * 2F / 9F)', buf)
                buf = regexps['pi10D'].sub(r'(Math.PI / 10D)', buf)
                buf = regexps['pi10F'].sub(r'((float)Math.PI / 10F)', buf)
                buf = regexps['2pi5D'].sub(r'(Math.PI * 2D / 5D)', buf)
                buf = regexps['2pi5F'].sub(r'((float)Math.PI * 2F / 5F)', buf)
                buf = regexps['7pi100D'].sub(r'(Math.PI * 7D / 100D)', buf)
                buf = regexps['7pi100F'].sub(r'((float)Math.PI * 7F / 100F)', buf)
                buf = regexps['185pi100D'].sub(r'(Math.PI * 185D / 100D)', buf)
                buf = regexps['185pi100F'].sub(r'((float)Math.PI * 185F / 100F)', buf)

            if fix_round:
                buf = regexps['rounddown'].sub(rounddown_match, buf)
                buf = regexps['roundup'].sub(roundup_match, buf)

            with open(tmp_file, 'w') as fh:
                fh.write(buf)
            shutil.move(tmp_file, src_file)


def cleanup_src(src_dir, clean_comments=True, clean_src=True, fix_imports=True, fix_unicode=False, fix_charval=False,
                fix_pi=False, fix_round=False):
    if clean_comments:
        strip_comments(src_dir)
    if clean_src:
        src_cleanup(src_dir, fix_imports=fix_imports, fix_unicode=fix_unicode, fix_charval=fix_charval, fix_pi=fix_pi,
                    fix_round=fix_round)


def main():
    usage = 'usage: %prog [options] src_dir'
    version = '%prog 6.0'
    parser = OptionParser(version=version, usage=usage)
    parser.add_option('-c', '--nocomment', action='store_false', dest='clean_comments', help="don't strip comments",
                      default=True)
    parser.add_option('-s', '--nocleanup', action='store_false', dest='clean_src', help="don't cleanup source",
                      default=True)
    parser.add_option('-i', '--imports', action='store_true', dest='fix_imports', help='cleanup unneeded imports',
                      default=False)
    parser.add_option('-u', '--unicode', action='store_true', dest='fix_unicode',
                      help='convert unicode character constant to integer', default=False)
    parser.add_option('-v', '--charval', action='store_true', dest='fix_charval',
                      help='convert Character.valueof to constant', default=False)
    parser.add_option('-p', '--pi', action='store_true', dest='fix_pi',
                      help='convert pi constants', default=False)
    parser.add_option('-r', '--round', action='store_true', dest='fix_round',
                      help='round long float and double constants', default=False)
    options, args = parser.parse_args()
    if len(args) != 1:
        print >> sys.stderr, 'src_dir required'
        sys.exit(1)
    cleanup_src(args[0], clean_comments=options.clean_comments, clean_src=options.clean_src,
                fix_imports=options.fix_imports, fix_unicode=options.fix_unicode, fix_charval=options.fix_charval,
                fix_pi=options.fix_pi, fix_round=options.fix_round)


if __name__ == '__main__':
    main()
