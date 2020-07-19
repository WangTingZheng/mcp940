package net.minecraft.client.resources;

import java.util.Map;

public class I18n
{
    private static Locale i18nLocale;

    static void setLocale(Locale i18nLocaleIn)
    {
        i18nLocale = i18nLocaleIn;
    }

    /**
     * Translates the given string and then formats it. Equivilant to String.format(translate(key), parameters).
     */
    public static String format(String translateKey, Object... parameters)
    {
        return i18nLocale.formatMessage(translateKey, parameters);
    }

    public static boolean hasKey(String key)
    {
        return i18nLocale.hasKey(key);
    }

    public static Map getLocaleProperties()
    {
        return i18nLocale.properties;
    }
}
