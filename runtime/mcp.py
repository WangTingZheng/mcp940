#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Thu Jan  19 15:04:52 2012

@author: Fesh0r
@version: v1.0
"""

import time

from commands import CLIENT, SIDE_NAME


def decompile_side(commands, side, use_ff=False, use_srg=False, no_comments=False, no_reformat=False, no_renamer=False,
                   no_patch=False, strip_comments=True, exc_update=False, keep_lvt=False, keep_generics=False, force_rg=False, rg_update=False,
                   joined=False):
    if not commands.checkjars(side):
        commands.logger.warning('!! Missing %s jar file. Aborting !!', SIDE_NAME[side])
        return False
    if commands.checksourcedir(side):
        commands.logger.warning('!! %s already decompiled. Run cleanup before decompiling again !!', SIDE_NAME[side])
        return False
    starttime = time.time()
    decompiler = 'JAD'
    if use_ff:
        decompiler = 'fernflower'
    commands.logger.info('== Decompiling %s using %s ==', SIDE_NAME[side], decompiler)
    if joined:
        commands.logger.info('> Using Joined variants')
    commands.logger.info('> Creating SRGs')
    commands.createsrgs(side, use_srg=use_srg, rg_update=rg_update)
    if force_rg:
        commands.logger.info('> Applying Retroguard')
        commands.applyrg(side)
    else:
        commands.logger.info('> Applying SpecialSource')
        commands.applyss(side, keep_lvt=keep_lvt, keep_generics=keep_generics,joined=joined)
    commands.logger.info('> Applying MCInjector')
    commands.applyexceptor(side, exc_update=exc_update)
    commands.logger.info('> Creating renamed srg')
    commands.createclssrg(side)
    if use_ff:
        commands.logger.info('> Filtering classes')
        commands.filterffjar(side)
        commands.logger.info('> Decompiling')
        commands.applyff(side)
    else:
        commands.logger.info('> Unpacking jar')
        commands.extractjar(side)
        commands.logger.info('> Copying classes')
        commands.copycls(side)
        commands.logger.info('> Applying jadretro')
        commands.applyjadretro(side)
        commands.logger.info('> Decompiling')
        commands.applyjad(side)
    commands.logger.info('> Copying sources')
    commands.copysrc(side)
    if use_ff:
        commands.logger.info('> Applying fernflower fixes')
        commands.process_fffixes(side)
    else:
        commands.logger.info('> Applying JAD fixes')
        commands.process_jadfixes(side)
        if commands.osname == 'osx' and not no_patch:
            commands.logger.info('> Applying OSX JAD fixes')
            commands.applypatches(side, use_ff=False, use_osx=True)
    if not no_patch:
        if joined:
            commands.logger.info('> Applying Joined patches')
        else:
            commands.logger.info('> Applying patches')
        commands.applypatches(side, use_ff=use_ff, use_joined=joined)
        if strip_comments:
            commands.logger.info('> Cleaning comments')
            commands.process_comments(side)
        else:
            commands.logger.warning('!! comment cleaning disabled !!')
    else:
        commands.logger.warning('!! patches disabled !!')
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    if not no_reformat:
        reformat_side(commands, side)
    else:
        commands.logger.warning('!! reformating disabled !!')
    if not no_renamer:
        updatenames_side(commands, side, no_comments=no_comments)
    else:
        commands.logger.warning('!! renaming disabled !!')
    return True


def reformat_side(commands, side):
    if not commands.checksourcedir(side):
        return False
    starttime = time.time()
    commands.logger.info('== Reformating %s ==', SIDE_NAME[side])
    commands.logger.info('> Cleaning sources')
    commands.process_cleanup(side)
    if side == CLIENT:
        commands.logger.info('> Replacing OpenGL constants')
        commands.process_annotate(side)
    if commands.has_astyle:
        commands.logger.info('> Reformating sources')
        commands.applyastyle(side)
    else:
        commands.logger.warning('!! reformating disabled due to no astyle or config !!')
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    return True


def updatenames_side(commands, side, no_comments=False):
    if not commands.checksourcedir(side):
        return False
    starttime = time.time()
    commands.logger.info('== Updating %s ==', SIDE_NAME[side])
    if not no_comments:
        if commands.has_doc_csv:
            commands.logger.info('> Adding javadoc')
            commands.process_javadoc(side)
        else:
            commands.logger.warning('!! javadoc disabled due to no csvs !!')
    else:
        commands.logger.warning('!! javadoc disabled !!')
    if commands.has_name_csv:
        commands.logger.info('> Renaming sources')
        commands.process_rename(side)
    else:
        commands.logger.warning('!! renaming disabled due to no csvs !!')
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    return True


def recompile_side(commands, side):
    if not commands.checksources(side):
        commands.logger.warning('!! Can not find %s sources, try decompiling !!', SIDE_NAME[side])
        return False
    starttime = time.time()
    commands.logger.info('== Recompiling %s ==', SIDE_NAME[side])
    commands.logger.info('> Cleaning bin')
    commands.cleanbindirs(side)
    commands.logger.info('> Recompiling')
    commands.recompile(side)
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    return True


def updatemd5_side(commands, side):
    recomp = recompile_side(commands, side)
    if recomp:
        commands.logger.info('> Generating %s md5s', SIDE_NAME[side])
        commands.gathermd5s(side)
        return True
    return False


def reobfuscate_side(commands, side, reobf_all=False, srg_names=False, force_rg=False, keep_lvt=True, keep_generics=False):
    if not commands.checkmd5s(side):
        commands.logger.warning('!! Can not find %s md5s !!', SIDE_NAME[side])
        return False
    if not commands.checkbins(side):
        commands.logger.warning('!! Can not find %s bins, try recompiling !!', SIDE_NAME[side])
        return False
    starttime = time.time()
    commands.logger.info('== Reobfuscating %s ==', SIDE_NAME[side])
    commands.logger.info('> Cleaning reobf')
    commands.cleanreobfdir(side)
    commands.logger.info('> Generating md5s')
    commands.gathermd5s(side, True)
    commands.logger.info('> Packing jar')
    commands.packbin(side)
    commands.logger.info('> Generating class markers')
    commands.applyexceptor(side, dryrun=True)

    #commands.cmpclassmarkers(side)
    # List gen by MCInjector should be done here
    commands.logger.info('> Creating reobf mapping')
    commands.creatergreobfsrg(side)
    if srg_names:
        commands.logger.info('> Creating srg maping')
        commands.createreobfsrg()

    commands.logger.info('> Reobfuscating jar')
    if force_rg:
        commands.applyrg(side, True)
    else:
        commands.applyss(side, True, srg_names=srg_names, keep_lvt=keep_lvt, keep_generics=keep_generics)
    commands.logger.info('> Extracting modified classes')
    commands.unpackreobfclasses(side, reobf_all, srg_names=srg_names)
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    return True


def getchangedsrc_side(commands, side):
    if not commands.checkmd5s(side):
        commands.logger.warning('!! Can not find %s md5s !!', SIDE_NAME[side])
        return False
    if not commands.checksources(side):
        commands.logger.warning('!! Can not find %s sources !!', SIDE_NAME[side])
        return False
    commands.logger.info('> Getting changed %s source', SIDE_NAME[side])
    commands.gathermd5s(side, True)
    commands.unpackmodifiedclasses(side)
    return True


def updateids_side(commands, side, no_comments=False):
    if not commands.checksourcedir(side):
        return False
    starttime = time.time()
    commands.logger.info('== Updating %s ==', SIDE_NAME[side])
    if commands.has_renumber_csv:
        commands.logger.info('> Renumbering sources')
        commands.process_renumber(side)
    else:
        commands.logger.warning('!! renumbering disabled due to no csvs !!')
    commands.logger.info('- Done in %.2f seconds', time.time() - starttime)
    return True
