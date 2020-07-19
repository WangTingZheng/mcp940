#!/usr/bin/env python2
'''
Created on Jun 30, 2013

@author: kickban
'''
import os, sys
import shutil
import zipfile
from commands import Commands
import MinecraftDiscovery

def copyAssets(src, dst):
    
    if os.path.exists(dst):
        shutil.rmtree(dst)
    
    try:
        shutil.copytree(src, dst)
    except:
        print ("Error copying assets.")
        sys.exit()


def copyLibrary(src, dst, name, library):
    try:
        dstFile = os.path.join(dst, library['filename'])
        dstPath = os.path.split(dstFile)[0]
        if os.path.exists(dstFile):
            os.remove(dstFile)
        if not os.path.exists(dstPath):
            os.makedirs(dstPath)
        
        shutil.copy2(os.path.join(src, library['filename']), dstPath)
    except:
        print ("Error copying library %s"%name)
        sys.exit()

#def extractLibrary(src, dst, library, version):
#    try:
#        srcPath = os.path.join(src, library['filename'])
#        dstPath = os.path.join(dst, "versions", version, "%s-natives"%version)
#    
#        if not os.path.exists(dstPath):
#            os.makedirs(dstPath)
#        
#        jarFile = zipfile.ZipFile(srcPath)
#        fileList = jarFile.namelist()
#    
#        for _file in fileList:
#            if not os.path.exists(os.path.join(dstPath, _file)):
#                exclude = False;
#                for entry in library['exclude']:
#                    if entry in _file:
#                        exclude = True
#                if not exclude:
#                    print("Extracting file %s from library %s"%(_file, library['name'].split(":")[1]))
#                    jarFile.extract(_file, dstPath)
#
#    except:
#        print ("Error extracting library %s"%library['name'])
#        sys.exit()

def extractNative(root, name, jarname, version):
    try:
        srcPath = os.path.join(root, jarname)
        dstPath = os.path.join(root, "versions", version, "%s-natives"%version)

        if not os.path.exists(dstPath):
            os.makedirs(dstPath)

        jarFile = zipfile.ZipFile(srcPath)
        jarFile.extract(name, dstPath)
    except:
        print ("Error extracting native %s from %s"%(name, jarname))
        sys.exit()

def copyMinecraft(src, dst, version):
    try:
        jarSrcPath = os.path.join(src, "versions", version, "%s.jar"%version)
        jsonSrcPath = os.path.join(src, "versions", version, "%s.json"%version)
        dstPath = os.path.join(dst, "versions", version)
    
        if os.path.exists(dstPath):
            shutil.rmtree(dstPath)
        os.makedirs(dstPath)    

        shutil.copy2(jarSrcPath, dstPath)
        shutil.copy2(jsonSrcPath, dstPath)
    except Exception as e:
        print ("\nError while copying Minecraft : %s"%e)
        sys.exit()    

#########################################################################################################

def copyClientAssets(commands, workDir = None):
    currentVersion = commands.versionClient

    osKeyword    = MinecraftDiscovery.getNativesKeyword()
    if workDir == None:
        if MinecraftDiscovery.checkCacheIntegrity(commands.dirjars, commands.jsonFile, osKeyword, currentVersion):
            return
        else:
            mcDir = MinecraftDiscovery.getMinecraftPath()
    else:
        mcDir = workDir

    #if not workDir:
    #    mcDir = MinecraftDiscovery.getMinecraftPath()
    #else:
    #    mcDir = workDir

    dstDir       = commands.dirjars

    #versionDir   = os.path.join(mcDir, "versions")
    #librariesDir = os.path.join(mcDir, "libraries")

    print("Looking in %s for mc installs..."%os.path.join(mcDir, "versions")),
    MinecraftDiscovery.checkMCDir(mcDir, currentVersion)
    print("OK")

    print("Copying assets..."),
    copyAssets(os.path.join(mcDir, "assets"), os.path.join(dstDir, "assets"))
    print("OK")

    print("Parsing JSON file..."),
    mcLibraries = MinecraftDiscovery.getLibraries(mcDir, MinecraftDiscovery.getJSONFilename(mcDir, currentVersion), osKeyword)
    print("OK")

    print ("Looking for minecraft main jar..."),
    if (MinecraftDiscovery.checkMinecraftExists(dstDir, currentVersion)):
        print ("OK")
    else:
        print ("Not found")
        print ("Copying minecraft main jar..."),
        copyMinecraft(mcDir, dstDir, currentVersion)
        print("OK")

    print ("> Checking libraries...")
    for name, library in mcLibraries.iteritems():
        if not MinecraftDiscovery.checkLibraryExists(dstDir, library):
            print ("\tCopying library %s..."%name),
            copyLibrary(mcDir, dstDir, name, library)
            print ("OK")

    print ("> Checking Natives...")
    for native, jarname in MinecraftDiscovery.getNatives(dstDir, mcLibraries).items():
        if not MinecraftDiscovery.checkNativeExists(dstDir, native, currentVersion):
            print ("\tExtracting native %s..."%native),
            extractNative(dstDir, native, jarname, currentVersion)
            print ("OK")

if __name__ == '__main__':
    commands = Commands()
    copyClientAssets(commands)
