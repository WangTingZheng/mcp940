#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sat Apr  9 13:51:48 2011

@author: ProfMobius & Searge
@version: v1.0
"""

import sys
import os
import glob
import logging
from optparse import OptionParser

from commands import Commands, reallyrmtree


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('-f', '--force', action='store_true',  dest='force', help='force cleanup', default=False)
    parser.add_option('-c', '--config',    dest='config',    help='additional configuration file')
    parser.add_option('-d', '--distclean', action='store_true', dest='distclean', help='Remove worlds, libraries and jars', default=False)    
    options, _ = parser.parse_args()
    cleanup(options.config, options.force, options.distclean)


def cleanup(conffile, force, distclean):
    try:
        commands = Commands(conffile, shortstart=True)

        if not force:
            print 'WARNING:'
            print 'The cleanup script will delete all folders created by MCP, including the'
            print 'src folder which may contain changes you made to the code, along with any'
            print 'saved worlds from the client or server.'
            answer = raw_input('If you really want to clean up, enter "Yes" ')
            if answer.lower() not in ['yes']:
                print 'You have not entered "Yes", aborting the clean up process'
                sys.exit(1)

        commands.checkupdates()

        try:
            commands.logger.info('> Cleaning temp')
            reallyrmtree(commands.dirtemp)

            commands.logger.info('> Cleaning src')
            reallyrmtree(commands.dirsrc)

            commands.logger.info('> Cleaning bin')
            reallyrmtree(commands.dirbin)

            commands.logger.info('> Cleaning reobf')
            reallyrmtree(commands.dirreobf)

            if distclean:
                commands.logger.info('> Cleaning lib')
                reallyrmtree(commands.dirlib)

            commands.logger.info('> Cleaning jars')
            reallyrmtree(os.path.join(commands.dirjars, 'stats'))
            reallyrmtree(os.path.join(commands.dirjars, 'texturepacks'))
            reallyrmtree(os.path.join(commands.dirjars, 'texturepacks-mp-cache'))            
            if distclean:
                reallyrmtree(os.path.join(commands.dirjars, 'saves'))
                reallyrmtree(os.path.join(commands.dirjars, 'mcpworld'))
                reallyrmtree(os.path.join(commands.dirjars, 'versions'))                
                reallyrmtree(os.path.join(commands.dirjars, 'assets'))                                
                reallyrmtree(os.path.join(commands.dirjars, 'libraries'))                
            if os.path.exists(os.path.join(commands.dirjars, 'server.log')):
                os.remove(os.path.join(commands.dirjars, 'server.log'))
            for txt_file in glob.glob(os.path.join(commands.dirjars, '*.txt')):
                os.remove(txt_file)

            commands.logger.info('> Cleaning logs')
            logging.shutdown()
            reallyrmtree(commands.dirlogs)
        except OSError as ex:
            print >> sys.stderr, 'Cleanup FAILED'
            if hasattr(ex, 'filename'):
                print >> sys.stderr, 'Failed to remove ' + ex.filename
            sys.exit(1)
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
