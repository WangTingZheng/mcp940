#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""

import sys
import logging
from optparse import OptionParser

from commands import Commands


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('-f', '--force', action='store_true', dest='force', help='force update', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    options, _ = parser.parse_args()
    updatemcp(options.config, options.force)


def updatemcp(conffile, force):
    try:
        commands = Commands(conffile)

        commands.logger.info('== Updating MCP ==')
        commands.downloadupdates(force)
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
