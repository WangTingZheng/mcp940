package net.minecraft.client.renderer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper
{
    /** The logger used by {@link OpenGlHelper} in the event of an error */
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean nvidia;
    public static boolean ati;
    public static int GL_FRAMEBUFFER;
    public static int GL_RENDERBUFFER;
    public static int GL_COLOR_ATTACHMENT0;
    public static int GL_DEPTH_ATTACHMENT;
    public static int GL_FRAMEBUFFER_COMPLETE;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    private static OpenGlHelper.FboMode framebufferType;
    public static boolean framebufferSupported;
    private static boolean shadersAvailable;
    private static boolean arbShaders;
    public static int GL_LINK_STATUS;
    public static int GL_COMPILE_STATUS;
    public static int GL_VERTEX_SHADER;
    public static int GL_FRAGMENT_SHADER;
    private static boolean arbMultitexture;

    /**
     * An OpenGL constant corresponding to GL_TEXTURE0, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int defaultTexUnit;

    /**
     * An OpenGL constant corresponding to GL_TEXTURE1, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int lightmapTexUnit;
    public static int GL_TEXTURE2;
    private static boolean arbTextureEnvCombine;
    public static int GL_COMBINE;
    public static int GL_INTERPOLATE;
    public static int GL_PRIMARY_COLOR;
    public static int GL_CONSTANT;
    public static int GL_PREVIOUS;
    public static int GL_COMBINE_RGB;
    public static int GL_SOURCE0_RGB;
    public static int GL_SOURCE1_RGB;
    public static int GL_SOURCE2_RGB;
    public static int GL_OPERAND0_RGB;
    public static int GL_OPERAND1_RGB;
    public static int GL_OPERAND2_RGB;
    public static int GL_COMBINE_ALPHA;
    public static int GL_SOURCE0_ALPHA;
    public static int GL_SOURCE1_ALPHA;
    public static int GL_SOURCE2_ALPHA;
    public static int GL_OPERAND0_ALPHA;
    public static int GL_OPERAND1_ALPHA;
    public static int GL_OPERAND2_ALPHA;
    private static boolean openGL14;
    public static boolean extBlendFuncSeparate;
    public static boolean openGL21;
    public static boolean shadersSupported;
    private static String logText = "";
    private static String cpu;
    public static boolean vboSupported;
    public static boolean vboSupportedAti;
    private static boolean arbVbo;
    public static int GL_ARRAY_BUFFER;
    public static int GL_STATIC_DRAW;
    public static float lastBrightnessX = 0.0F;
    public static float lastBrightnessY = 0.0F;

    /**
     * Initializes the texture constants to be used when rendering lightmap values
     */
    public static void initializeTextures()
    {
        Config.initDisplay();
        ContextCapabilities contextcapabilities = GLContext.getCapabilities();
        arbMultitexture = contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13;
        arbTextureEnvCombine = contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13;

        if (arbMultitexture)
        {
            logText = logText + "Using ARB_multitexture.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        }
        else
        {
            logText = logText + "Using GL 1.3 multitexturing.\n";
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
            GL_TEXTURE2 = 33986;
        }

        if (arbTextureEnvCombine)
        {
            logText = logText + "Using ARB_texture_env_combine.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        }
        else
        {
            logText = logText + "Using GL 1.3 texture combiners.\n";
            GL_COMBINE = 34160;
            GL_INTERPOLATE = 34165;
            GL_PRIMARY_COLOR = 34167;
            GL_CONSTANT = 34166;
            GL_PREVIOUS = 34168;
            GL_COMBINE_RGB = 34161;
            GL_SOURCE0_RGB = 34176;
            GL_SOURCE1_RGB = 34177;
            GL_SOURCE2_RGB = 34178;
            GL_OPERAND0_RGB = 34192;
            GL_OPERAND1_RGB = 34193;
            GL_OPERAND2_RGB = 34194;
            GL_COMBINE_ALPHA = 34162;
            GL_SOURCE0_ALPHA = 34184;
            GL_SOURCE1_ALPHA = 34185;
            GL_SOURCE2_ALPHA = 34186;
            GL_OPERAND0_ALPHA = 34200;
            GL_OPERAND1_ALPHA = 34201;
            GL_OPERAND2_ALPHA = 34202;
        }

        extBlendFuncSeparate = contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14;
        openGL14 = contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate;
        framebufferSupported = openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30);

        if (framebufferSupported)
        {
            logText = logText + "Using framebuffer objects because ";

            if (contextcapabilities.OpenGL30)
            {
                logText = logText + "OpenGL 3.0 is supported and separate blending is supported.\n";
                framebufferType = OpenGlHelper.FboMode.BASE;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
            else if (contextcapabilities.GL_ARB_framebuffer_object)
            {
                logText = logText + "ARB_framebuffer_object is supported and separate blending is supported.\n";
                framebufferType = OpenGlHelper.FboMode.ARB;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
            else if (contextcapabilities.GL_EXT_framebuffer_object)
            {
                logText = logText + "EXT_framebuffer_object is supported.\n";
                framebufferType = OpenGlHelper.FboMode.EXT;
                GL_FRAMEBUFFER = 36160;
                GL_RENDERBUFFER = 36161;
                GL_COLOR_ATTACHMENT0 = 36064;
                GL_DEPTH_ATTACHMENT = 36096;
                GL_FRAMEBUFFER_COMPLETE = 36053;
                GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
        }
        else
        {
            logText = logText + "Not using framebuffer objects because ";
            logText = logText + "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
            logText = logText + "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            logText = logText + "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
            logText = logText + "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            logText = logText + "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }

        openGL21 = contextcapabilities.OpenGL21;
        shadersAvailable = openGL21 || contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects;
        logText = logText + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";

        if (shadersAvailable)
        {
            if (contextcapabilities.OpenGL21)
            {
                logText = logText + "OpenGL 2.1 is supported.\n";
                arbShaders = false;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            }
            else
            {
                logText = logText + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                arbShaders = true;
                GL_LINK_STATUS = 35714;
                GL_COMPILE_STATUS = 35713;
                GL_VERTEX_SHADER = 35633;
                GL_FRAGMENT_SHADER = 35632;
            }
        }
        else
        {
            logText = logText + "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
            logText = logText + "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            logText = logText + "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            logText = logText + "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }

        shadersSupported = framebufferSupported && shadersAvailable;
        String s = GL11.glGetString(GL11.GL_VENDOR).toLowerCase(Locale.ROOT);
        nvidia = s.contains("nvidia");
        arbVbo = !contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object;
        vboSupported = contextcapabilities.OpenGL15 || arbVbo;
        logText = logText + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";

        if (vboSupported)
        {
            if (arbVbo)
            {
                logText = logText + "ARB_vertex_buffer_object is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            }
            else
            {
                logText = logText + "OpenGL 1.5 is supported.\n";
                GL_STATIC_DRAW = 35044;
                GL_ARRAY_BUFFER = 34962;
            }
        }

        ati = s.contains("ati");

        if (ati)
        {
            if (vboSupported)
            {
                vboSupportedAti = true;
            }
            else
            {
                GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
            }
        }

        try
        {
            Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
            cpu = String.format("%dx %s", aprocessor.length, aprocessor[0]).replaceAll("\\s+", " ");
        }
        catch (Throwable var3)
        {
            ;
        }
    }

    public static boolean areShadersSupported()
    {
        return shadersSupported;
    }

    public static String getLogText()
    {
        return logText;
    }

    public static int glGetProgrami(int program, int pname)
    {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(program, pname) : GL20.glGetProgrami(program, pname);
    }

    public static void glAttachShader(int program, int shaderIn)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glAttachObjectARB(program, shaderIn);
        }
        else
        {
            GL20.glAttachShader(program, shaderIn);
        }
    }

    public static void glDeleteShader(int shaderIn)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glDeleteObjectARB(shaderIn);
        }
        else
        {
            GL20.glDeleteShader(shaderIn);
        }
    }

    /**
     * creates a shader with the given mode and returns the GL id. params: mode
     */
    public static int glCreateShader(int type)
    {
        return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(type) : GL20.glCreateShader(type);
    }

    public static void glShaderSource(int shaderIn, ByteBuffer string)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glShaderSourceARB(shaderIn, string);
        }
        else
        {
            GL20.glShaderSource(shaderIn, string);
        }
    }

    public static void glCompileShader(int shaderIn)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glCompileShaderARB(shaderIn);
        }
        else
        {
            GL20.glCompileShader(shaderIn);
        }
    }

    public static int glGetShaderi(int shaderIn, int pname)
    {
        return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(shaderIn, pname) : GL20.glGetShaderi(shaderIn, pname);
    }

    public static String glGetShaderInfoLog(int shaderIn, int maxLength)
    {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB(shaderIn, maxLength) : GL20.glGetShaderInfoLog(shaderIn, maxLength);
    }

    public static String glGetProgramInfoLog(int program, int maxLength)
    {
        return arbShaders ? ARBShaderObjects.glGetInfoLogARB(program, maxLength) : GL20.glGetProgramInfoLog(program, maxLength);
    }

    public static void glUseProgram(int program)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUseProgramObjectARB(program);
        }
        else
        {
            GL20.glUseProgram(program);
        }
    }

    public static int glCreateProgram()
    {
        return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }

    public static void glDeleteProgram(int program)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glDeleteObjectARB(program);
        }
        else
        {
            GL20.glDeleteProgram(program);
        }
    }

    public static void glLinkProgram(int program)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glLinkProgramARB(program);
        }
        else
        {
            GL20.glLinkProgram(program);
        }
    }

    public static int glGetUniformLocation(int programObj, CharSequence name)
    {
        return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
    }

    public static void glUniform1(int location, IntBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform1ARB(location, values);
        }
        else
        {
            GL20.glUniform1(location, values);
        }
    }

    public static void glUniform1i(int location, int v0)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform1iARB(location, v0);
        }
        else
        {
            GL20.glUniform1i(location, v0);
        }
    }

    public static void glUniform1(int location, FloatBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform1ARB(location, values);
        }
        else
        {
            GL20.glUniform1(location, values);
        }
    }

    public static void glUniform2(int location, IntBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform2ARB(location, values);
        }
        else
        {
            GL20.glUniform2(location, values);
        }
    }

    public static void glUniform2(int location, FloatBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform2ARB(location, values);
        }
        else
        {
            GL20.glUniform2(location, values);
        }
    }

    public static void glUniform3(int location, IntBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform3ARB(location, values);
        }
        else
        {
            GL20.glUniform3(location, values);
        }
    }

    public static void glUniform3(int location, FloatBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform3ARB(location, values);
        }
        else
        {
            GL20.glUniform3(location, values);
        }
    }

    public static void glUniform4(int location, IntBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform4ARB(location, values);
        }
        else
        {
            GL20.glUniform4(location, values);
        }
    }

    public static void glUniform4(int location, FloatBuffer values)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniform4ARB(location, values);
        }
        else
        {
            GL20.glUniform4(location, values);
        }
    }

    public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniformMatrix2ARB(location, transpose, matrices);
        }
        else
        {
            GL20.glUniformMatrix2(location, transpose, matrices);
        }
    }

    public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniformMatrix3ARB(location, transpose, matrices);
        }
        else
        {
            GL20.glUniformMatrix3(location, transpose, matrices);
        }
    }

    public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices)
    {
        if (arbShaders)
        {
            ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
        }
        else
        {
            GL20.glUniformMatrix4(location, transpose, matrices);
        }
    }

    public static int glGetAttribLocation(int program, CharSequence name)
    {
        return arbShaders ? ARBVertexShader.glGetAttribLocationARB(program, name) : GL20.glGetAttribLocation(program, name);
    }

    public static int glGenBuffers()
    {
        return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }

    public static void glBindBuffer(int target, int buffer)
    {
        if (arbVbo)
        {
            ARBVertexBufferObject.glBindBufferARB(target, buffer);
        }
        else
        {
            GL15.glBindBuffer(target, buffer);
        }
    }

    public static void glBufferData(int target, ByteBuffer data, int usage)
    {
        if (arbVbo)
        {
            ARBVertexBufferObject.glBufferDataARB(target, data, usage);
        }
        else
        {
            GL15.glBufferData(target, data, usage);
        }
    }

    public static void glDeleteBuffers(int buffer)
    {
        if (arbVbo)
        {
            ARBVertexBufferObject.glDeleteBuffersARB(buffer);
        }
        else
        {
            GL15.glDeleteBuffers(buffer);
        }
    }

    public static boolean useVbo()
    {
        if (Config.isMultiTexture())
        {
            return false;
        }
        else
        {
            return vboSupported && Minecraft.getMinecraft().gameSettings.useVbo;
        }
    }

    public static void glBindFramebuffer(int target, int framebufferIn)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glBindFramebuffer(target, framebufferIn);
                    break;

                case ARB:
                    ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
                    break;

                case EXT:
                    EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
            }
        }
    }

    public static void glBindRenderbuffer(int target, int renderbuffer)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glBindRenderbuffer(target, renderbuffer);
                    break;

                case ARB:
                    ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
                    break;

                case EXT:
                    EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
            }
        }
    }

    public static void glDeleteRenderbuffers(int renderbuffer)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glDeleteRenderbuffers(renderbuffer);
                    break;

                case ARB:
                    ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
                    break;

                case EXT:
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
            }
        }
    }

    public static void glDeleteFramebuffers(int framebufferIn)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glDeleteFramebuffers(framebufferIn);
                    break;

                case ARB:
                    ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
                    break;

                case EXT:
                    EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
            }
        }
    }

    /**
     * Calls the appropriate glGenFramebuffers method and returns the newly created fbo, or returns -1 if not supported.
     */
    public static int glGenFramebuffers()
    {
        if (!framebufferSupported)
        {
            return -1;
        }
        else
        {
            switch (framebufferType)
            {
                case BASE:
                    return GL30.glGenFramebuffers();

                case ARB:
                    return ARBFramebufferObject.glGenFramebuffers();

                case EXT:
                    return EXTFramebufferObject.glGenFramebuffersEXT();

                default:
                    return -1;
            }
        }
    }

    public static int glGenRenderbuffers()
    {
        if (!framebufferSupported)
        {
            return -1;
        }
        else
        {
            switch (framebufferType)
            {
                case BASE:
                    return GL30.glGenRenderbuffers();

                case ARB:
                    return ARBFramebufferObject.glGenRenderbuffers();

                case EXT:
                    return EXTFramebufferObject.glGenRenderbuffersEXT();

                default:
                    return -1;
            }
        }
    }

    public static void glRenderbufferStorage(int target, int internalFormat, int width, int height)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glRenderbufferStorage(target, internalFormat, width, height);
                    break;

                case ARB:
                    ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
                    break;

                case EXT:
                    EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
            }
        }
    }

    public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
                    break;

                case ARB:
                    ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
                    break;

                case EXT:
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
            }
        }
    }

    public static int glCheckFramebufferStatus(int target)
    {
        if (!framebufferSupported)
        {
            return -1;
        }
        else
        {
            switch (framebufferType)
            {
                case BASE:
                    return GL30.glCheckFramebufferStatus(target);

                case ARB:
                    return ARBFramebufferObject.glCheckFramebufferStatus(target);

                case EXT:
                    return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);

                default:
                    return -1;
            }
        }
    }

    public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level)
    {
        if (framebufferSupported)
        {
            switch (framebufferType)
            {
                case BASE:
                    GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
                    break;

                case ARB:
                    ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
                    break;

                case EXT:
                    EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
            }
        }
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setActiveTexture(int texture)
    {
        if (arbMultitexture)
        {
            ARBMultitexture.glActiveTextureARB(texture);
        }
        else
        {
            GL13.glActiveTexture(texture);
        }
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setClientActiveTexture(int texture)
    {
        if (arbMultitexture)
        {
            ARBMultitexture.glClientActiveTextureARB(texture);
        }
        else
        {
            GL13.glClientActiveTexture(texture);
        }
    }

    /**
     * Sets the current coordinates of the given lightmap texture
     */
    public static void setLightmapTextureCoords(int target, float p_77475_1_, float t)
    {
        if (arbMultitexture)
        {
            ARBMultitexture.glMultiTexCoord2fARB(target, p_77475_1_, t);
        }
        else
        {
            GL13.glMultiTexCoord2f(target, p_77475_1_, t);
        }

        if (target == lightmapTexUnit)
        {
            lastBrightnessX = p_77475_1_;
            lastBrightnessY = t;
        }
    }

    public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha)
    {
        if (openGL14)
        {
            if (extBlendFuncSeparate)
            {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
            }
            else
            {
                GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
            }
        }
        else
        {
            GL11.glBlendFunc(sFactorRGB, dFactorRGB);
        }
    }

    public static boolean isFramebufferEnabled()
    {
        if (Config.isFastRender())
        {
            return false;
        }
        else if (Config.isAntialiasing())
        {
            return false;
        }
        else
        {
            return framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable;
        }
    }

    public static String getCpu()
    {
        return cpu == null ? "<unknown>" : cpu;
    }

    public static void renderDirections(int p_188785_0_)
    {
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GL11.glLineWidth(4.0F);
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos((double)p_188785_0_, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, (double)p_188785_0_, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)p_188785_0_).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
        GL11.glLineWidth(2.0F);
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
        bufferbuilder.pos((double)p_188785_0_, 0.0D, 0.0D).color(255, 0, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(0, 255, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, (double)p_188785_0_, 0.0D).color(0, 255, 0, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(127, 127, 255, 255).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, (double)p_188785_0_).color(127, 127, 255, 255).endVertex();
        tessellator.draw();
        GL11.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
    }

    public static void openFile(File fileIn)
    {
        String s = fileIn.getAbsolutePath();

        if (Util.getOSType() == Util.EnumOS.OSX)
        {
            try
            {
                LOGGER.info(s);
                Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                return;
            }
            catch (IOException ioexception1)
            {
                LOGGER.error("Couldn't open file", (Throwable)ioexception1);
            }
        }
        else if (Util.getOSType() == Util.EnumOS.WINDOWS)
        {
            String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", s);

            try
            {
                Runtime.getRuntime().exec(s1);
                return;
            }
            catch (IOException ioexception)
            {
                LOGGER.error("Couldn't open file", (Throwable)ioexception);
            }
        }

        boolean flag = false;

        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke((Object)null);
            oclass.getMethod("browse", URI.class).invoke(object, fileIn.toURI());
        }
        catch (Throwable throwable1)
        {
            LOGGER.error("Couldn't open link", throwable1);
            flag = true;
        }

        if (flag)
        {
            LOGGER.info("Opening via system class!");
            Sys.openURL("file://" + s);
        }
    }

    static enum FboMode
    {
        BASE,
        ARB,
        EXT;
    }
}
