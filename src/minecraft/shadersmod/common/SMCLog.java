package shadersmod.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PREFIX = "[Shaders] ";

    public static void severe(String message)
    {
        LOGGER.error("[Shaders] " + message);
    }

    public static void warning(String message)
    {
        LOGGER.warn("[Shaders] " + message);
    }

    public static void info(String message)
    {
        LOGGER.info("[Shaders] " + message);
    }

    public static void fine(String message)
    {
        LOGGER.debug("[Shaders] " + message);
    }

    public static void severe(String format, Object... args)
    {
        String s = String.format(format, args);
        LOGGER.error("[Shaders] " + s);
    }

    public static void warning(String format, Object... args)
    {
        String s = String.format(format, args);
        LOGGER.warn("[Shaders] " + s);
    }

    public static void info(String format, Object... args)
    {
        String s = String.format(format, args);
        LOGGER.info("[Shaders] " + s);
    }

    public static void fine(String format, Object... args)
    {
        String s = String.format(format, args);
        LOGGER.debug("[Shaders] " + s);
    }
}
