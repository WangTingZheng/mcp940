#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: Searge
@version: v1.0
"""

import sys
import logging
from optparse import OptionParser

from commands import Commands, CLIENT, SERVER
from mcp import updatenames_side


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('--client', dest='only_client', action='store_true', help='only process client', default=False)
    parser.add_option('--server', dest='only_server', action='store_true', help='only process server', default=False)
    parser.add_option('-f', '--force', action='store_true', dest='force', help='force update', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    options, _ = parser.parse_args()
    updatenames(options.config, options.force, options.only_client, options.only_server)


def updatenames(conffile, force, only_client, only_server):
    try:
        commands = Commands(conffile)

        # client or server
        process_client = True
        process_server = True
        if only_client and not only_server:
            process_server = False
        if only_server and not only_client:
            process_client = False

        if not force:
            print 'WARNING:'
            print 'The updatenames script is unsupported, not recommended, and can break your'
            print 'code in hard to detect ways.'
            print 'Only use this script if you absolutely know what you are doing, or when a'
            print 'MCP team member asks you to do so.'
            answer = raw_input('If you really want to update all classes, enter "Yes" ')
            if answer.lower() not in ['yes']:
                print 'You have not entered "Yes", aborting the update process'
                sys.exit(1)

        if process_client:
            updatenames_side(commands, CLIENT)
        if process_server:
            updatenames_side(commands, SERVER)
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
