# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:36:26 2011

@author: ProfMobius
@version: v0.1
"""

import os
import logging


def whereis(filename, rootdir):
    if not os.path.exists(rootdir):
        return []
    logging.info('> Searching for %s in %s', filename, rootdir)
    results = []
    for path, _, filelist in os.walk(rootdir):
        if filename in filelist:
            results.append(path)
    return results
