# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 13:44:42 2011

@author: ProfMobius
@version: 0.1
"""

import csv

import srgshandler


CLIENT = 0
SERVER = 1


def writesrgsfromcsvs(csvclass, csvmethods, csvfields, outsrgs, side, ignore_classes=None):
    """Reads 3 CSVs and output a srgs"""

    if ignore_classes is None:
        ignore_classes = []

    packages = []
    classes = []
    methods = []
    fields = []

    # HINT: We are adding the package conversions
    packages.append(['.', 'net/minecraft/src'])
    packages.append(['net', 'net'])
    packages.append(['net/minecraft', 'net/minecraft'])
    if side == CLIENT:
        packages.append(['net/minecraft/client', 'net/minecraft/client'])
        packages.append(['net/minecraft/isom', 'net/minecraft/isom'])
    elif side == SERVER:
        packages.append(['net/minecraft/server', 'net/minecraft/server'])
    else:
        raise Exception("Side not recognized : %d" % side)

    # HINT: We append the class elements. We also handle the special case of Minecraft, MinecraftApplet, MinecraftServer
    with open(csvclass, 'rb') as fh:
        csvreader = csv.DictReader(fh)
        for row in csvreader:
            if int(row['side']) == side:
                # HINT : Those checks are here to append the proper packages to notch version of the Minecraft, etc.
                # They are needed since we don't have notch package information (lost during recompilation)
                # The skip on start is there because of a quirk of the bot updating process
                # We use recompiled sources, so the bot catches the Start.class which have been added by Searge.
                if row['notch'] in ['Minecraft', 'MinecraftApplet']:
                    row['notch'] = 'net/minecraft/client/%s' % row['notch']
                if row['notch'] in ['IsomPreviewApplet']:
                    row['notch'] = 'net/minecraft/isom/%s' % row['notch']
                if row['notch'] in ['MinecraftServer']:
                    row['notch'] = 'net/minecraft/server/%s' % row['notch']
                if row['name'] in ignore_classes:
                    continue
                classes.append([row['notch'], '%s/%s' % (row['package'], row['name'])])

    # HINT: We append the method elements
    with open(csvmethods, 'rb') as fh:
        csvreader = csv.DictReader(fh)
        for row in csvreader:
            if int(row['side']) == side:
                if row['classnotch'] in ['Minecraft', 'MinecraftApplet']:
                    row['classnotch'] = 'net/minecraft/client/%s' % row['classnotch']
                if row['classnotch'] in ['IsomPreviewApplet']:
                    row['classnotch'] = 'net/minecraft/isom/%s' % row['classnotch']
                if row['classnotch'] in ['MinecraftServer']:
                    row['classnotch'] = 'net/minecraft/server/%s' % row['classnotch']
                if row['classname'] in ignore_classes:
                    continue
                methods.append(['%s/%s %s' % (row['classnotch'], row['notch'], row['notchsig']),
                                '%s/%s/%s %s' % (row['package'], row['classname'], row['searge'], row['sig'])])

    # HINT: We append the field elements
    with open(csvfields, 'rb') as fh:
        csvreader = csv.DictReader(fh)
        for row in csvreader:
            if int(row['side']) == side:
                if row['classnotch'] in ['Minecraft', 'MinecraftApplet']:
                    row['classnotch'] = 'net/minecraft/client/%s' % row['classnotch']
                if row['classnotch'] in ['IsomPreviewApplet']:
                    row['classnotch'] = 'net/minecraft/isom/%s' % row['classnotch']
                if row['classnotch'] in ['MinecraftServer']:
                    row['classnotch'] = 'net/minecraft/server/%s' % row['classnotch']
                if row['classname'] in ignore_classes:
                    continue
                fields.append(['%s/%s' % (row['classnotch'], row['notch']),
                               '%s/%s/%s' % (row['package'], row['classname'], row['searge'])])

    srgshandler.writesrgs(outsrgs, {'PK': packages, 'CL': classes, 'FD': fields, 'MD': methods})
