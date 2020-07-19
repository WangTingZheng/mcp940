package net.minecraft.client.shader;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static ShaderLinkHelper staticShaderLinkHelper;

    public static void setNewStaticShaderLinkHelper()
    {
        staticShaderLinkHelper = new ShaderLinkHelper();
    }

    public static ShaderLinkHelper getStaticShaderLinkHelper()
    {
        return staticShaderLinkHelper;
    }

    public void deleteShader(ShaderManager manager)
    {
        manager.getFragmentShaderLoader().deleteShader(manager);
        manager.getVertexShaderLoader().deleteShader(manager);
        OpenGlHelper.glDeleteProgram(manager.getProgram());
    }

    public int createProgram() throws JsonException
    {
        int i = OpenGlHelper.glCreateProgram();

        if (i <= 0)
        {
            throw new JsonException("Could not create shader program (returned program ID " + i + ")");
        }
        else
        {
            return i;
        }
    }

    public void linkProgram(ShaderManager manager) throws IOException
    {
        manager.getFragmentShaderLoader().attachShader(manager);
        manager.getVertexShaderLoader().attachShader(manager);
        OpenGlHelper.glLinkProgram(manager.getProgram());
        int i = OpenGlHelper.glGetProgrami(manager.getProgram(), OpenGlHelper.GL_LINK_STATUS);

        if (i == 0)
        {
            LOGGER.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", manager.getVertexShaderLoader().getShaderFilename(), manager.getFragmentShaderLoader().getShaderFilename());
            LOGGER.warn(OpenGlHelper.glGetProgramInfoLog(manager.getProgram(), 32768));
        }
    }
}
