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

from commands import Commands, CLIENT, SERVER, CalledProcessError
from mcp import recompile_side


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('--client', dest='only_client', action='store_true', help='only process client', default=False)
    parser.add_option('--server', dest='only_server', action='store_true', help='only process server', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    options, _ = parser.parse_args()
    recompile(options.config, options.only_client, options.only_server)


def recompile(conffile, only_client, only_server):
    errorcode = 0
    try:
        commands = Commands(conffile, verify=True)

        # client or server
        process_client = True
        process_server = True
        if only_client and not only_server:
            process_server = False
        if only_server and not only_client:
            process_client = False

        if process_client:
            try:
                recompile_side(commands, CLIENT)
            except CalledProcessError:
                errorcode = 2
                pass
        if process_server:
            try:
                recompile_side(commands, SERVER)
            except CalledProcessError:
                errorcode = 3
                pass
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)
    sys.exit(errorcode)

if __name__ == '__main__':
    main()
