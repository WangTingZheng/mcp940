# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 12:50:02 2011

@author: ProfMobius
@version : v0.1
"""


def parse_srg(srg_filename):
    """Reads a SeargeRG file and returns a dictionary of lists for packages, classes, methods and fields"""
    srg_types = {'PK:': ['obf_name', 'deobf_name'],
                 'CL:': ['obf_name', 'deobf_name'],
                 'FD:': ['obf_name', 'deobf_name'],
                 'MD:': ['obf_name', 'obf_desc', 'deobf_name', 'deobf_desc']}
    parsed_dict = {'PK': [],
                   'CL': [],
                   'FD': [],
                   'MD': []}

    def get_parsed_line(keyword, buf):
        return dict(zip(srg_types[keyword], [i.strip() for i in buf]))

    with open(srg_filename, 'r') as srg_file:
        for buf in srg_file:
            buf = buf.strip()
            if buf == '' or buf[0] == '#':
                continue
            buf = buf.split()
            parsed_dict[buf[0][:2]].append(get_parsed_line(buf[0], buf[1:]))
    return parsed_dict


def writesrgs(filename, data):
    """Writes a srgs file based on data. Data is formatted similar to the output of readsrgs (dict of lists)"""
    if not 'PK' in data or not 'CL' in data or not 'FD' in data or not 'MD' in data:
        raise Exception("Malformed data for writesrgs. Keys should be in ['PK', 'CL', 'FD', 'MD']")

    with open(filename, 'w') as srgsout:
        # HINT: We write all the entries for a given key in order
        for key in ['PK', 'CL', 'FD', 'MD']:
            for entry in data[key]:
                srgsout.write('%s: %s %s\n' % (key, entry[0], entry[1]))
