# -*- coding: utf-8 -*-
"""
Created on Wed Apr  18 11:19:07 2012

@author: Fesh0r
@version: v0.1
"""

import sys
import os
import fnmatch
import shutil
import re
from optparse import OptionParser


_REGEXP = {
    # Remove trailing whitespace
    'trailing': re.compile(r'[ \t]+$', re.MULTILINE),

    # close up extends, and implements that JD-GUI puts on a seperate line
    'extends': re.compile(r'$\n {2}(?=extends|implements)', re.MULTILINE),

    # close up throws that JD-GUI puts on a seperate line
    'throws': re.compile(r'$\n {4}(?=throws)', re.MULTILINE),

    # Remove repeated blank lines
    'newlines': re.compile(r'^\n{2,}', re.MULTILINE),
}


def jdfix(srcdir):
    for path, _, filelist in os.walk(srcdir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.java'):
            src_file = os.path.normpath(os.path.join(path, cur_file))
            _process_file(src_file)


def _process_file(src_file):
    class_name = os.path.splitext(os.path.basename(src_file))[0]
    tmp_file = src_file + '.tmp'
    with open(src_file, 'r') as fh:
        buf = fh.read()

    buf = _REGEXP['trailing'].sub(r'', buf)

    buf = _REGEXP['extends'].sub(r' ', buf)
    buf = _REGEXP['throws'].sub(r' ', buf)

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
    jdfix(args[0])


if __name__ == '__main__':
    main()
