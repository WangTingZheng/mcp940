import sys
import os
import shutil
import fnmatch
import re
from optparse import OptionParser


def normaliselines(in_filename, out_filename=None):
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
    regex_ending = re.compile(r'\r?\n')
    with open(in_filename, 'rb') as in_file:
        with open(tmp_filename, 'wb') as out_file:
            buf = in_file.read()
            if os.linesep == '\r\n':
                buf = regex_ending.sub(r'\r\n', buf)
            else:
                buf = buf.replace('\r\n', '\n')
            out_file.write(buf)
    if out_filename is None:
        shutil.move(tmp_filename, in_filename)


def normaliselines_dir(in_dirname, out_dirname=None):
    in_dirname = os.path.normpath(in_dirname)
    if out_dirname is not None:
        out_dirname = os.path.normpath(out_dirname)
    for path, _, filelist in os.walk(in_dirname, followlinks=True):
        sub_dir = os.path.relpath(path, in_dirname)
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            if out_dirname is not None:
                dest_file = os.path.join(out_dirname, sub_dir, cur_file)
            else:
                dest_file = None
            normaliselines(src_file, dest_file)


def main():
    usage = 'usage: %prog [options] src_file|src_dir [dest_file|dest_dir]'
    version = '%prog 6.0'
    parser = OptionParser(version=version, usage=usage)
    options, args = parser.parse_args()
    if len(args) == 1:
        in_name = args[0]
        out_name = None
    elif len(args) == 2:
        in_name = args[0]
        out_name = args[1]
    else:
        print >> sys.stderr, 'src_file or src_dir required'
        sys.exit(1)
    if not os.path.exists(in_name):
        print >> sys.stderr, 'src_file or src_dir not found'
        sys.exit(1)
    if os.path.isfile(in_name):
        normaliselines(in_name, out_name)
    elif os.path.isdir(in_name):
        normaliselines_dir(in_name, out_name)


if __name__ == '__main__':
    main()
