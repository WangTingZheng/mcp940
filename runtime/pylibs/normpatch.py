import sys
import os
import shutil
from optparse import OptionParser


def normalisepatch(in_filename, out_filename=None):
    in_filename = os.path.normpath(in_filename)
    if out_filename is None:
        tmp_filename = in_filename + '.tmp'
    else:
        out_filename = os.path.normpath(out_filename)
        tmp_filename = out_filename
        dir_name = os.path.dirname(out_filename)
        if dir_name:
            if not os.path.exists(dir_name):
                os.makedirs(dir_name)
    with open(in_filename, 'rb') as inpatch:
        with open(tmp_filename, 'wb') as outpatch:
            for line in inpatch:
                line = line.rstrip('\r\n')
                if line[:3] in ['+++', '---', 'Onl', 'dif']:
                    outpatch.write(line.replace('\\', os.sep).replace('/', os.sep) + os.linesep)
                else:
                    outpatch.write(line + os.linesep)
    if out_filename is None:
        shutil.move(tmp_filename, in_filename)


def main():
    usage = 'usage: %prog [options] src_file [dest_file]'
    version = '%prog 6.0'
    parser = OptionParser(version=version, usage=usage)
    options, args = parser.parse_args()
    if len(args) == 1:
        in_file = args[0]
        out_file = None
    elif len(args) == 2:
        in_file = args[0]
        out_file = args[1]
    else:
        print >> sys.stderr, 'src_file required'
        sys.exit(1)
    normalisepatch(in_file, out_file)


if __name__ == '__main__':
    main()
