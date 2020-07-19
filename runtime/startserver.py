#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""

import sys
import logging
import json
from optparse import OptionParser

from commands import Commands, SERVER


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    parser.add_option('-m', '--main', dest='mainclass', help='Main class to start', default='net.minecraft.server.MinecraftServer')
    parser.add_option('-j', '--json', dest='json',action='store_true', help='Use the json file to setup parameters', default=False)
    options, _ = parser.parse_args()
    startserver(options.config, options.mainclass, options.json)


def startserver(conffile, mainclass, jsonoverride):
    try:
        commands = Commands(conffile)

        #if not mainclass:
        #    mainclass = "net.minecraft.server.MinecraftServer"

        extraargs = ""
        if jsonoverride:
            jsonData  = json.load(open(commands.jsonFile))
            mainclass = jsonData['mainClass']
            extraargs = jsonData['minecraftArguments']

        if not commands.checkbins(SERVER):
            commands.logger.warning('!! Can not find server bins !!')
            sys.exit(1)
        commands.startserver(mainclass, extraargs)
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
