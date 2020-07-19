#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.2
"""

import sys
import logging
from optparse import OptionParser

from commands import Commands, CLIENT, SERVER, CalledProcessError
from mcp import decompile_side, updatemd5_side
import clientCopy


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('--client', dest='only_client', action='store_true', help='only process client', default=False)
    parser.add_option('--server', dest='only_server', action='store_true', help='only process server', default=False)
    parser.add_option('--joined', dest='only_joined', action='store_true', help='only process joined [MCP dev only]', default=False)
    parser.add_option('-j', '--jad', dest='force_jad', action='store_true',
                      help='force use of JAD even if Fernflower available', default=False)
    parser.add_option('--rg', dest='force_rg', action='store_true',
                      help='force use of RetroGuard even if SpecialSource available', default=False)
    parser.add_option('-s', '--csv', dest='force_csv', action='store_true',
                      help='force use of CSVs even if SRGs available', default=False)
    parser.add_option('-r', '--norecompile', dest='no_recompile', action='store_true',
                      help='disable recompile after decompile', default=False)
    parser.add_option('-d', '--nocomments', dest='no_comments', action='store_true', help='disable javadoc',
                      default=False)
    parser.add_option('-a', '--noreformat', dest='no_reformat', action='store_true',
                      help='disable source reformatting', default=False)
    parser.add_option('-n', '--norenamer', dest='no_renamer', action='store_true',
                      help='disable field and method renaming', default=False)
    parser.add_option('-l', '--kill_lvt', dest='keep_lvt', action='store_false', help='erase local variable table',
                      default=True)
    parser.add_option('-g', '--kill_generics', dest='keep_generics', action='store_false',
                      help='Destroy generics', default=True)
    parser.add_option('-o', '--onlypatch', dest='only_patch', action='store_true', help='only patch source',
                      default=False)
    parser.add_option('-p', dest='no_patch', action='store_true', help='Undocumented magic', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    parser.add_option('-w', '--workDir', dest='workdir', help='Change client lookup place (by default, ~/.minecraft)')
    parser.add_option('--json', dest='json', help='Location of the json file for none standard installs')   
    parser.add_option('--nocopy', dest='nocopy', action='store_true', help='Do not try to copy assets from minecraft install', default=False)       


    options, _ = parser.parse_args()
    decompile(options.config, options.force_jad, options.force_csv, options.no_recompile, options.no_comments,
              options.no_reformat, options.no_renamer, options.no_patch, options.only_patch, options.keep_lvt,
              options.keep_generics, options.only_client, options.only_server, options.force_rg, options.workdir, options.json, options.nocopy,
              only_joined=options.only_joined)


def decompile(conffile, force_jad, force_csv, no_recompile, no_comments, no_reformat, no_renamer, no_patch, only_patch,
              keep_lvt, keep_generics, only_client, only_server, force_rg, workdir, json, nocopy, only_joined=False):
    try:
        commands = Commands(conffile, verify=True, no_patch=no_patch, workdir=workdir, json=json)

        commands.checkupdates()
        
        if not commands.has_ss:
            force_rg = True

        use_ff = commands.has_ff and not force_jad
        use_srg = commands.has_srg and not force_csv

        if force_jad and not commands.has_jad:
            commands.logger.error('!! forcing jad when not available !!')
            sys.exit(1)

        if force_rg and not commands.has_rg:
            commands.logger.error('!! forcing retroguard when not available !!')
            sys.exit(1)

        if force_csv and not commands.has_map_csv:
            commands.logger.error('!! forcing csvs when not available !!')
            sys.exit(1)

        # client or server
        process_client = True
        process_server = True
        if only_client and not only_server:
            process_server = False
        if only_server and not only_client:
            process_client = False
        if only_joined and not only_server and not only_client:
            process_server = False

        # always strip comments by default, turn off in update mode if required
        strip_comments = True

        # update only options
        rg_update = False
        exc_update = False

        if no_patch:
            # no_patch is basically update mode, disables everything
            # and reuses a few different options to do update stuff
            if only_patch:
                # with only_patch then we actually do the patches, but not the comment stripping, for use when updating
                # the fernflower patches
                no_patch = False
                strip_comments = False
            if no_reformat:
                # reuse -a no_reformat to switch rg to fullmap=1 startindex=RGIndex
                rg_update = True
            if no_renamer:
                # reuse -n to switch mcinjector to outputing exc file, and adding new parameters
                exc_update = True
            no_comments = True
            no_reformat = True
            no_renamer = True
            no_recompile = True
        elif only_patch:
            # if only_patch then disable everything but patching and comment stripping
            no_comments = True
            no_reformat = True
            no_renamer = True
            no_recompile = True

        # if we have generics enabled we need the lvt as well
        if keep_generics:
            keep_lvt = True

        if force_rg:
            commands.logger.info('> Creating Retroguard config files')
            commands.creatergcfg(reobf=False, keep_lvt=keep_lvt, keep_generics=keep_generics, rg_update=rg_update, joined_jar=only_joined)

        if not nocopy:
            clientCopy.copyClientAssets(commands, workdir)
            
        commands.setupjsr305()

        try:
            if process_client:
                cltdecomp = decompile_side(commands, CLIENT, use_ff=use_ff, use_srg=use_srg, no_comments=no_comments,
                                           no_reformat=no_reformat, no_renamer=no_renamer, no_patch=no_patch,
                                           strip_comments=strip_comments, exc_update=exc_update,
                                           keep_lvt=keep_lvt, keep_generics=keep_generics, force_rg=force_rg, rg_update=rg_update,
                                           joined=only_joined)
            else:
                cltdecomp = False
            if process_server:
                srvdecomp = decompile_side(commands, SERVER, use_ff=use_ff, use_srg=use_srg, no_comments=no_comments,
                                           no_reformat=no_reformat, no_renamer=no_renamer, no_patch=no_patch,
                                           strip_comments=strip_comments, exc_update=exc_update,
                                           keep_lvt=keep_lvt, keep_generics=keep_generics, force_rg=force_rg, rg_update=rg_update)
            else:
                srvdecomp = False
        except CalledProcessError:
            # retroguard or other called process error so bail
            commands.logger.error('Decompile failed')
            sys.exit(1)
        if not no_recompile:
            if cltdecomp:
                try:
                    updatemd5_side(commands, CLIENT)
                except CalledProcessError:
                    commands.logger.error('Initial client recompile failed, correct source then run updatemd5')
            if srvdecomp:
                try:
                    updatemd5_side(commands, SERVER)
                except CalledProcessError:
                    commands.logger.error('Initial server recompile failed, correct source then run updatemd5')
        else:
            commands.logger.info('!! recompile disabled !!')
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
