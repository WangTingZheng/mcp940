#!/usr/bin/env python2
import os, sys
import json
import zipfile
import platform
import re

def getMinecraftPath():
    if   sys.platform.startswith('linux'):
        return os.path.expanduser("~/.minecraft")
    elif sys.platform.startswith('win'):
        return os.path.join(os.getenv("APPDATA"), ".minecraft")
    elif sys.platform.startswith('darwin'):
        return os.path.expanduser("~/Library/Application Support/minecraft")
    else:
        print "Cannot detect of version : %s. Please report to your closest sysadmin"%sys.platform
        sys.exit()

def getNativesKeyword():
    if   sys.platform.startswith('linux'):
        return "linux"
    elif sys.platform.startswith('win'):
        return "windows"
    elif sys.platform.startswith('darwin'):
        return "osx"
    else:
        print "Cannot detect of version : %s. Please report to your closest sysadmin"%sys.platform
        sys.exit()        

def checkMCDir(src, version):
    #We check that our version of MC is available for analysis
    if not os.path.exists(src) \
      or not os.path.exists(os.path.join(src, "versions")) \
      or not os.path.exists(os.path.join(src, "libraries")) \
      or not os.path.exists(os.path.join(os.path.join(src, "versions"), version)):
        print ("ERROR : You should run the launcher at least once before starting MCP")
        sys.exit()

def getJSONFilename(src, version):
    return os.path.join(os.path.join(src, "versions"), version, "%s.json"%version)

def checkCacheIntegrity(root, jsonfile, osKeyword, version):
    libraries = getLibraries(root, jsonfile, osKeyword)

    if libraries == None:
        return False

    for library in libraries.values():
        if not checkLibraryExists(root, library):
            return False

    if not checkMinecraftExists(root, version):
        return False

    natives = getNatives(root, libraries)

    for native in natives.keys():
        if not checkNativeExists(root, native, version):
            return False

    return True

def checkLibraryExists(dst, library):
    if os.path.exists(os.path.join(dst, library['filename'])):
        return True
    else:
        return False

def checkMinecraftExists(root, version):
    if os.path.exists(os.path.join(root, "versions", version, '%s.jar'%version)) and \
       os.path.exists(os.path.join(root, "versions", version, '%s.json'%version)):
        return True
    else:
        return False

def checkNativeExists(root, native, version):
    nativePath = getNativePath(root, version)
    if (os.path.exists(os.path.join(nativePath, native))):
        return True
    else:
        return False

def getNatives(root, libraries):
    nativeList = {}
    for library in libraries.values():
        if library['extract']:

            srcPath = os.path.join(root, library['filename'])
            jarFile = zipfile.ZipFile(srcPath)
            fileList = jarFile.namelist()

            for _file in fileList:
                exclude = False;
                for entry in library['exclude']:
                    if entry in _file:
                        exclude = True
                if not exclude:
                    nativeList[_file] = library['filename']
    return nativeList

def getNativePath(root, version):
    return os.path.join(root, "versions", version, "%s-natives"%version)

def getLibraries(root, jsonfile, osKeyword):
    #We check the json exits
    if not os.path.exists(jsonfile):
        return None
        #print ("ERROR : json file %s not found."%jsonfile)
        #print ("You should run the launcher at least once before starting MCP")
        #sys.exit()
    
    #We parse the json file
    jsonFile = None    
    try:
        jsonFile = json.load(open(jsonfile))
    except Exception as e:
        print "Error while parsing the library JSON file : %s"%e
        sys.exit()
    
    mcLibraries  = jsonFile['libraries']
    outLibraries = {}
    
    for library in mcLibraries:
        libCononical = library['name'].split(':')[0]
        libSubdir    = library['name'].split(':')[1]
        libVersion   = library['name'].split(':')[2]
        libPath      = libCononical.replace('.', '/')
        extract      = False
        exclude     = []

        #Rule patch from Adam Greenfield 
        if 'rules' in library:
            passRules = False
            for rule in library['rules']:
                ruleApplies = True
                if 'os' in rule:
                    if rule['os']['name'] != osKeyword:
                        ruleApplies = False
                    else:
                        if osKeyword == "osx":
                            os_ver = platform.mac_ver()[0]
                        else:
                            os_ver = platform.release()

                        if 'version' in rule['os'] and not re.match(rule['os']['version'], os_ver):
                            ruleApplies = False

                if ruleApplies:
                    if rule['action'] == "allow":
                        passRules = True
                    else:
                        passRules = False

            if not passRules:
                continue

        if 'natives' in library:
            libFilename = "%s-%s-%s.jar"%(libSubdir, libVersion, substitueString(library['natives'][osKeyword]))
        else:
            libFilename = "%s-%s.jar"%(libSubdir, libVersion)

        if 'extract' in library:
            extract = True
            if 'exclude' in library['extract']:
                exclude.extend(library['extract']['exclude'])
    
        #libFullPath  = os.path.join(os.path.join(root, "libraries"), libPath, libSubdir, libVersion, libFilename)
        libRelativePath = os.path.join("libraries", libPath, libSubdir, libVersion, libFilename)
    
        #if not os.path.exists(libFullPath):
        #    print ("Error while trying to access libraries. Couldn't find %s"%libFullPath)
        #    sys.exit()
        if 'natives' in library:
            outLibraries['%s-%s' % (libSubdir, substitueString(library['natives'][osKeyword]))] = {'name':library['name'], 'filename':libRelativePath, 'extract':extract, 'exclude':exclude}
        else:
            outLibraries[libSubdir] = {'name':library['name'], 'filename':libRelativePath, 'extract':extract, 'exclude':exclude}

    return outLibraries

def getArch():
    machine = platform.machine()
    if os.name == 'nt' and sys.version_info[:2] < (2,7):
        machine = os.environ.get("PROCESSOR_ARCHITEW6432", os.environ.get('PROCESSOR_ARCHITECTURE', ''))
    machine2bits = {'AMD64': '64', 'x86_64': '64', 'i386': '32', 'x86': '32'}
    return machine2bits.get(machine, None)

def substitueString(str):
    str = str.replace("${arch}", getArch())
    return str


if __name__ == '__main__':
    osKeyword = getNativesKeyword()
    mcDir     = getMinecraftPath()
    mcLibraries = getLibraries(mcDir, getJSONFilename(mcDir, "1.6.1"), osKeyword)
    mcNatives = getNatives(mcDir, mcLibraries)

    for native in mcNatives.keys():
        if checkNativeExists("./jars", native, "1.6.1"):
            print 'Found %s %s'%(native, mcNatives[native])
        else:
            print 'Not found %s %s'%(native, mcNatives[native])
            