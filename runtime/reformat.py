#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Thu Jan  19 12:34:12 2012

@author: Fesh0r
@version: v1.0
"""

import sys
import logging
from optparse import OptionParser

from commands import Commands, CLIENT, SERVER, CalledProcessError
from mcp import reformat_side


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('--client', dest='only_client', action='store_true', help='only process client', default=False)
    parser.add_option('--server', dest='only_server', action='store_true', help='only process server', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    options, _ = parser.parse_args()
    reformat(options.config, options.only_client, options.only_server)


def reformat(conffile, only_client, only_server):
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
                reformat_side(commands, CLIENT)
            except CalledProcessError:
                # astyle failed
                commands.logger.error('Reformat of client failed')

        if process_server:
            try:
                reformat_side(commands, SERVER)
            except CalledProcessError:
                # astyle failed
                commands.logger.error('Reformat of server failed')
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
