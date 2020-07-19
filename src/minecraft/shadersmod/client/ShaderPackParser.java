package shadersmod.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.minecraft.src.StrUtils;

public class ShaderPackParser
{
    private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
    private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
    private static final Set<String> setConstNames = makeSetConstNames();

    public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions)
    {
        if (shaderPack == null)
        {
            return new ShaderOption[0];
        }
        else
        {
            Map<String, ShaderOption> map = new HashMap<String, ShaderOption>();
            collectShaderOptions(shaderPack, "/shaders", programNames, map);
            Iterator<Integer> iterator = listDimensions.iterator();

            while (iterator.hasNext())
            {
                int i = ((Integer)iterator.next()).intValue();
                String s = "/shaders/world" + i;
                collectShaderOptions(shaderPack, s, programNames, map);
            }

            Collection<ShaderOption> collection = map.values();
            ShaderOption[] ashaderoption = (ShaderOption[])collection.toArray(new ShaderOption[collection.size()]);
            Comparator<ShaderOption> comparator = new Comparator<ShaderOption>()
            {
                public int compare(ShaderOption o1, ShaderOption o2)
                {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            };
            Arrays.sort(ashaderoption, comparator);
            return ashaderoption;
        }
    }

    private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions)
    {
        for (int i = 0; i < programNames.length; ++i)
        {
            String s = programNames[i];

            if (!s.equals(""))
            {
                String s1 = dir + "/" + s + ".vsh";
                String s2 = dir + "/" + s + ".fsh";
                collectShaderOptions(shaderPack, s1, mapOptions);
                collectShaderOptions(shaderPack, s2, mapOptions);
            }
        }
    }

    private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions)
    {
        String[] astring = getLines(sp, path);

        for (int i = 0; i < astring.length; ++i)
        {
            String s = astring[i];
            ShaderOption shaderoption = getShaderOption(s, path);

            if (shaderoption != null && !shaderoption.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!shaderoption.checkUsed() || isOptionUsed(shaderoption, astring)))
            {
                String s1 = shaderoption.getName();
                ShaderOption shaderoption1 = mapOptions.get(s1);

                if (shaderoption1 != null)
                {
                    if (!Config.equals(shaderoption1.getValueDefault(), shaderoption.getValueDefault()))
                    {
                        Config.warn("Ambiguous shader option: " + shaderoption.getName());
                        Config.warn(" - in " + Config.arrayToString((Object[])shaderoption1.getPaths()) + ": " + shaderoption1.getValueDefault());
                        Config.warn(" - in " + Config.arrayToString((Object[])shaderoption.getPaths()) + ": " + shaderoption.getValueDefault());
                        shaderoption1.setEnabled(false);
                    }

                    if (shaderoption1.getDescription() == null || shaderoption1.getDescription().length() <= 0)
                    {
                        shaderoption1.setDescription(shaderoption.getDescription());
                    }

                    shaderoption1.addPaths(shaderoption.getPaths());
                }
                else
                {
                    mapOptions.put(s1, shaderoption);
                }
            }
        }
    }

    private static boolean isOptionUsed(ShaderOption so, String[] lines)
    {
        for (int i = 0; i < lines.length; ++i)
        {
            String s = lines[i];

            if (so.isUsedInLine(s))
            {
                return true;
            }
        }

        return false;
    }

    private static String[] getLines(IShaderPack sp, String path)
    {
        try
        {
            List<String> list = new ArrayList<String>();
            String s = loadFile(path, sp, 0, list, 0);

            if (s == null)
            {
                return new String[0];
            }
            else
            {
                ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
                String[] astring = Config.readLines(bytearrayinputstream);
                return astring;
            }
        }
        catch (IOException ioexception)
        {
            Config.dbg(ioexception.getClass().getName() + ": " + ioexception.getMessage());
            return new String[0];
        }
    }

    private static ShaderOption getShaderOption(String line, String path)
    {
        ShaderOption shaderoption = null;

        if (shaderoption == null)
        {
            shaderoption = ShaderOptionSwitch.parseOption(line, path);
        }

        if (shaderoption == null)
        {
            shaderoption = ShaderOptionVariable.parseOption(line, path);
        }

        if (shaderoption != null)
        {
            return shaderoption;
        }
        else
        {
            if (shaderoption == null)
            {
                shaderoption = ShaderOptionSwitchConst.parseOption(line, path);
            }

            if (shaderoption == null)
            {
                shaderoption = ShaderOptionVariableConst.parseOption(line, path);
            }

            return shaderoption != null && setConstNames.contains(shaderoption.getName()) ? shaderoption : null;
        }
    }

    private static Set<String> makeSetConstNames()
    {
        Set<String> set = new HashSet<String>();
        set.add("shadowMapResolution");
        set.add("shadowDistance");
        set.add("shadowDistanceRenderMul");
        set.add("shadowIntervalSize");
        set.add("generateShadowMipmap");
        set.add("generateShadowColorMipmap");
        set.add("shadowHardwareFiltering");
        set.add("shadowHardwareFiltering0");
        set.add("shadowHardwareFiltering1");
        set.add("shadowtex0Mipmap");
        set.add("shadowtexMipmap");
        set.add("shadowtex1Mipmap");
        set.add("shadowcolor0Mipmap");
        set.add("shadowColor0Mipmap");
        set.add("shadowcolor1Mipmap");
        set.add("shadowColor1Mipmap");
        set.add("shadowtex0Nearest");
        set.add("shadowtexNearest");
        set.add("shadow0MinMagNearest");
        set.add("shadowtex1Nearest");
        set.add("shadow1MinMagNearest");
        set.add("shadowcolor0Nearest");
        set.add("shadowColor0Nearest");
        set.add("shadowColor0MinMagNearest");
        set.add("shadowcolor1Nearest");
        set.add("shadowColor1Nearest");
        set.add("shadowColor1MinMagNearest");
        set.add("wetnessHalflife");
        set.add("drynessHalflife");
        set.add("eyeBrightnessHalflife");
        set.add("centerDepthHalflife");
        set.add("sunPathRotation");
        set.add("ambientOcclusionLevel");
        set.add("superSamplingLevel");
        set.add("noiseTextureResolution");
        return set;
    }

    public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions)
    {
        String s = "profile.";
        List<ShaderProfile> list = new ArrayList<ShaderProfile>();

        for (Object s10 : props.keySet())
        {
        	String s1 = (String) s10;
            if (s1.startsWith(s))
            {
                String s2 = s1.substring(s.length());
                props.getProperty(s1);
                Set<String> set = new HashSet<String>();
                ShaderProfile shaderprofile = parseProfile(s2, props, set, shaderOptions);

                if (shaderprofile != null)
                {
                    list.add(shaderprofile);
                }
            }
        }

        if (list.size() <= 0)
        {
            return null;
        }
        else
        {
            ShaderProfile[] ashaderprofile = (ShaderProfile[])list.toArray(new ShaderProfile[list.size()]);
            return ashaderprofile;
        }
    }

    public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions)
    {
        Set<String> set = new HashSet<String>();
        String s = props.getProperty("sliders");

        if (s == null)
        {
            return set;
        }
        else
        {
            String[] astring = Config.tokenize(s, " ");

            for (int i = 0; i < astring.length; ++i)
            {
                String s1 = astring[i];
                ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);

                if (shaderoption == null)
                {
                    Config.warn("Invalid shader option: " + s1);
                }
                else
                {
                    set.add(s1);
                }
            }

            return set;
        }
    }

    private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions)
    {
        String s = "profile.";
        String s1 = s + name;

        if (parsedProfiles.contains(s1))
        {
            Config.warn("[Shaders] Profile already parsed: " + name);
            return null;
        }
        else
        {
            parsedProfiles.add(name);
            ShaderProfile shaderprofile = new ShaderProfile(name);
            String s2 = props.getProperty(s1);
            String[] astring = Config.tokenize(s2, " ");

            for (int i = 0; i < astring.length; ++i)
            {
                String s3 = astring[i];

                if (s3.startsWith(s))
                {
                    String s6 = s3.substring(s.length());
                    ShaderProfile shaderprofile1 = parseProfile(s6, props, parsedProfiles, shaderOptions);

                    if (shaderprofile != null)
                    {
                        shaderprofile.addOptionValues(shaderprofile1);
                        shaderprofile.addDisabledPrograms(shaderprofile1.getDisabledPrograms());
                    }
                }
                else
                {
                    String[] astring1 = Config.tokenize(s3, ":=");

                    if (astring1.length == 1)
                    {
                        String s7 = astring1[0];
                        boolean flag = true;

                        if (s7.startsWith("!"))
                        {
                            flag = false;
                            s7 = s7.substring(1);
                        }

                        String s8 = "program.";

                        if (!flag && s7.startsWith("program."))
                        {
                            String s9 = s7.substring(s8.length());

                            if (!Shaders.isProgramPath(s9))
                            {
                                Config.warn("Invalid program: " + s9 + " in profile: " + shaderprofile.getName());
                            }
                            else
                            {
                                shaderprofile.addDisabledProgram(s9);
                            }
                        }
                        else
                        {
                            ShaderOption shaderoption1 = ShaderUtils.getShaderOption(s7, shaderOptions);

                            if (!(shaderoption1 instanceof ShaderOptionSwitch))
                            {
                                Config.warn("[Shaders] Invalid option: " + s7);
                            }
                            else
                            {
                                shaderprofile.addOptionValue(s7, String.valueOf(flag));
                                shaderoption1.setVisible(true);
                            }
                        }
                    }
                    else if (astring1.length != 2)
                    {
                        Config.warn("[Shaders] Invalid option value: " + s3);
                    }
                    else
                    {
                        String s4 = astring1[0];
                        String s5 = astring1[1];
                        ShaderOption shaderoption = ShaderUtils.getShaderOption(s4, shaderOptions);

                        if (shaderoption == null)
                        {
                            Config.warn("[Shaders] Invalid option: " + s3);
                        }
                        else if (!shaderoption.isValidValue(s5))
                        {
                            Config.warn("[Shaders] Invalid value: " + s3);
                        }
                        else
                        {
                            shaderoption.setVisible(true);
                            shaderprofile.addOptionValue(s4, s5);
                        }
                    }
                }
            }

            return shaderprofile;
        }
    }

    public static Map<String, ShaderOption[]> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions)
    {
        Map<String, ShaderOption[]> map = new HashMap<String, ShaderOption[]>();
        parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
        return map.isEmpty() ? null : map;
    }

    private static boolean parseGuiScreen(String key, Properties props, Map<String, ShaderOption[]> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions)
    {
        String s = props.getProperty(key);

        if (s == null)
        {
            return false;
        }
        else
        {
            List<ShaderOption> list = new ArrayList<ShaderOption>();
            Set<String> set = new HashSet<String>();
            String[] astring = Config.tokenize(s, " ");

            for (int i = 0; i < astring.length; ++i)
            {
                String s1 = astring[i];

                if (s1.equals("<empty>"))
                {
                    list.add((ShaderOption)null);
                }
                else if (set.contains(s1))
                {
                    Config.warn("[Shaders] Duplicate option: " + s1 + ", key: " + key);
                }
                else
                {
                    set.add(s1);

                    if (s1.equals("<profile>"))
                    {
                        if (shaderProfiles == null)
                        {
                            Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + s1 + ", key: " + key);
                        }
                        else
                        {
                            ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
                            list.add(shaderoptionprofile);
                        }
                    }
                    else if (s1.equals("*"))
                    {
                        ShaderOption shaderoption1 = new ShaderOptionRest("<rest>");
                        list.add(shaderoption1);
                    }
                    else if (s1.startsWith("[") && s1.endsWith("]"))
                    {
                        String s2 = StrUtils.removePrefixSuffix(s1, "[", "]");

                        if (!s2.matches("^[a-zA-Z0-9_]+$"))
                        {
                            Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
                        }
                        else if (!parseGuiScreen("screen." + s2, props, map, shaderProfiles, shaderOptions))
                        {
                            Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
                        }
                        else
                        {
                            ShaderOptionScreen shaderoptionscreen = new ShaderOptionScreen(s2);
                            list.add(shaderoptionscreen);
                        }
                    }
                    else
                    {
                        ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);

                        if (shaderoption == null)
                        {
                            Config.warn("[Shaders] Invalid option: " + s1 + ", key: " + key);
                            list.add((ShaderOption)null);
                        }
                        else
                        {
                            shaderoption.setVisible(true);
                            list.add(shaderoption);
                        }
                    }
                }
            }

            ShaderOption[] ashaderoption = (ShaderOption[])list.toArray(new ShaderOption[list.size()]);
            map.put(key, ashaderoption);
            return true;
        }
    }

    public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException
    {
        String s = "/";
        int i = filePath.lastIndexOf("/");

        if (i >= 0)
        {
            s = filePath.substring(0, i);
        }

        CharArrayWriter chararraywriter = new CharArrayWriter();
        int j = -1;
        Set<String> set = new LinkedHashSet<String>();
        int k = 1;

        while (true)
        {
            String s1 = reader.readLine();

            if (s1 == null)
            {
                char[] achar = chararraywriter.toCharArray();

                if (j >= 0 && set.size() > 0)
                {
                    StringBuilder stringbuilder = new StringBuilder();

                    for (String s7 : set)
                    {
                        stringbuilder.append("#define ");
                        stringbuilder.append(s7);
                        stringbuilder.append("\n");
                    }

                    String s6 = stringbuilder.toString();
                    StringBuilder stringbuilder1 = new StringBuilder(new String(achar));
                    stringbuilder1.insert(j, s6);
                    String s10 = stringbuilder1.toString();
                    achar = s10.toCharArray();
                }

                CharArrayReader chararrayreader = new CharArrayReader(achar);
                return new BufferedReader(chararrayreader);
            }

            if (j < 0)
            {
                Matcher matcher = PATTERN_VERSION.matcher(s1);

                if (matcher.matches())
                {
                    String s2 = "#define MC_VERSION " + Config.getMinecraftVersionInt() + "\n#define " + "MC_GL_VERSION" + " " + Config.getGlVersion().toInt() + "\n#define " + "MC_GLSL_VERSION" + " " + Config.getGlslVersion().toInt() + "\n#define " + ShaderMacros.getOs() + "\n#define " + ShaderMacros.getVendor() + "\n#define " + ShaderMacros.getRenderer() + "\n";
                    String s3 = s1 + "\n" + s2;
                    String s4 = "#line " + (k + 1) + " " + fileIndex;
                    s1 = s3 + s4;
                    j = chararraywriter.size() + s3.length();
                }
            }

            Matcher matcher1 = PATTERN_INCLUDE.matcher(s1);

            if (matcher1.matches())
            {
                String s5 = matcher1.group(1);
                boolean flag = s5.startsWith("/");
                String s8 = flag ? "/shaders" + s5 : s + "/" + s5;

                if (!listFiles.contains(s8))
                {
                    listFiles.add(s8);
                }

                int l = listFiles.indexOf(s8) + 1;
                s1 = loadFile(s8, shaderPack, l, listFiles, includeLevel);

                if (s1 == null)
                {
                    throw new IOException("Included file not found: " + filePath);
                }

                if (s1.endsWith("\n"))
                {
                    s1 = s1.substring(0, s1.length() - 1);
                }

                s1 = "#line 1 " + l + "\n" + s1 + "\n#line " + (k + 1) + " " + fileIndex;
            }

            if (j >= 0 && s1.contains(ShaderMacros.getPrefixMacro()))
            {
                String[] astring = findExtensions(s1, ShaderMacros.getExtensions());

                for (int i1 = 0; i1 < astring.length; ++i1)
                {
                    String s9 = astring[i1];
                    set.add(s9);
                }
            }

            chararraywriter.write(s1);
            chararraywriter.write("\n");
            ++k;
        }
    }

    private static String[] findExtensions(String line, String[] extensions)
    {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < extensions.length; ++i)
        {
            String s = extensions[i];

            if (line.contains(s))
            {
                list.add(s);
            }
        }

        String[] astring = (String[])list.toArray(new String[list.size()]);
        return astring;
    }

    private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException
    {
        if (includeLevel >= 10)
        {
            throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
        }
        else
        {
            ++includeLevel;
            InputStream inputstream = shaderPack.getResourceAsStream(filePath);

            if (inputstream == null)
            {
                return null;
            }
            else
            {
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
                bufferedreader = resolveIncludes(bufferedreader, filePath, shaderPack, fileIndex, listFiles, includeLevel);
                CharArrayWriter chararraywriter = new CharArrayWriter();

                while (true)
                {
                    String s = bufferedreader.readLine();

                    if (s == null)
                    {
                        return chararraywriter.toString();
                    }

                    chararraywriter.write(s);
                    chararraywriter.write("\n");
                }
            }
        }
    }
}
