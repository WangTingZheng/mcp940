package shadersmod.client;

import net.minecraft.src.Config;

public class ShaderUtils
{
    public static ShaderOption getShaderOption(String name, ShaderOption[] opts)
    {
        if (opts == null)
        {
            return null;
        }
        else
        {
            for (int i = 0; i < opts.length; ++i)
            {
                ShaderOption shaderoption = opts[i];

                if (shaderoption.getName().equals(name))
                {
                    return shaderoption;
                }
            }

            return null;
        }
    }

    public static ShaderProfile detectProfile(ShaderProfile[] profs, ShaderOption[] opts, boolean def)
    {
        if (profs == null)
        {
            return null;
        }
        else
        {
            for (int i = 0; i < profs.length; ++i)
            {
                ShaderProfile shaderprofile = profs[i];

                if (matchProfile(shaderprofile, opts, def))
                {
                    return shaderprofile;
                }
            }

            return null;
        }
    }

    public static boolean matchProfile(ShaderProfile prof, ShaderOption[] opts, boolean def)
    {
        if (prof == null)
        {
            return false;
        }
        else if (opts == null)
        {
            return false;
        }
        else
        {
            String[] astring = prof.getOptions();

            for (int i = 0; i < astring.length; ++i)
            {
                String s = astring[i];
                ShaderOption shaderoption = getShaderOption(s, opts);

                if (shaderoption != null)
                {
                    String s1 = def ? shaderoption.getValueDefault() : shaderoption.getValue();
                    String s2 = prof.getValue(s);

                    if (!Config.equals(s1, s2))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
