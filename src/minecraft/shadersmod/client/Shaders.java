package shadersmod.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.EntityUtils;
import net.minecraft.src.Lang;
import net.minecraft.src.PropertiesOrdered;
import net.minecraft.src.Reflector;
import net.minecraft.src.StrUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import shadersmod.common.SMCLog;

public class Shaders {
	static Minecraft mc;
	static EntityRenderer entityRenderer;
	public static boolean isInitializedOnce = false;
	public static boolean isShaderPackInitialized = false;
	public static ContextCapabilities capabilities;
	public static String glVersionString;
	public static String glVendorString;
	public static String glRendererString;
	public static boolean hasGlGenMipmap = false;
	public static boolean hasForge = false;
	public static int numberResetDisplayList = 0;
	static boolean needResetModels = false;
	private static int renderDisplayWidth = 0;
	private static int renderDisplayHeight = 0;
	public static int renderWidth = 0;
	public static int renderHeight = 0;
	public static boolean isRenderingWorld = false;
	public static boolean isRenderingSky = false;
	public static boolean isCompositeRendered = false;
	public static boolean isRenderingDfb = false;
	public static boolean isShadowPass = false;
	public static boolean isSleeping;
	private static boolean isRenderingFirstPersonHand;
	private static boolean isHandRenderedMain;
	private static boolean isHandRenderedOff;
	private static boolean skipRenderHandMain;
	private static boolean skipRenderHandOff;
	public static boolean renderItemKeepDepthMask = false;
	public static boolean itemToRenderMainTranslucent = false;
	public static boolean itemToRenderOffTranslucent = false;
	static float[] sunPosition = new float[4];
	static float[] moonPosition = new float[4];
	static float[] shadowLightPosition = new float[4];
	static float[] upPosition = new float[4];
	static float[] shadowLightPositionVector = new float[4];
	static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
	static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
	static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
	private static float[] tempMat = new float[16];
	static float clearColorR;
	static float clearColorG;
	static float clearColorB;
	static float skyColorR;
	static float skyColorG;
	static float skyColorB;
	static long worldTime = 0L;
	static long lastWorldTime = 0L;
	static long diffWorldTime = 0L;
	static float celestialAngle = 0.0F;
	static float sunAngle = 0.0F;
	static float shadowAngle = 0.0F;
	static int moonPhase = 0;
	static long systemTime = 0L;
	static long lastSystemTime = 0L;
	static long diffSystemTime = 0L;
	static int frameCounter = 0;
	static float frameTime = 0.0F;
	static float frameTimeCounter = 0.0F;
	static int systemTimeInt32 = 0;
	static float rainStrength = 0.0F;
	static float wetness = 0.0F;
	public static float wetnessHalfLife = 600.0F;
	public static float drynessHalfLife = 200.0F;
	public static float eyeBrightnessHalflife = 10.0F;
	static boolean usewetness = false;
	static int isEyeInWater = 0;
	static int eyeBrightness = 0;
	static float eyeBrightnessFadeX = 0.0F;
	static float eyeBrightnessFadeY = 0.0F;
	static float eyePosY = 0.0F;
	static float centerDepth = 0.0F;
	static float centerDepthSmooth = 0.0F;
	static float centerDepthSmoothHalflife = 1.0F;
	static boolean centerDepthSmoothEnabled = false;
	static int superSamplingLevel = 1;
	static float nightVision = 0.0F;
	static float blindness = 0.0F;
	static boolean updateChunksErrorRecorded = false;
	static boolean lightmapEnabled = false;
	static boolean fogEnabled = true;
	public static int entityAttrib = 10;
	public static int midTexCoordAttrib = 11;
	public static int tangentAttrib = 12;
	public static boolean useEntityAttrib = false;
	public static boolean useMidTexCoordAttrib = false;
	public static boolean useMultiTexCoord3Attrib = false;
	public static boolean useTangentAttrib = false;
	public static boolean progUseEntityAttrib = false;
	public static boolean progUseMidTexCoordAttrib = false;
	public static boolean progUseTangentAttrib = false;
	public static int atlasSizeX = 0;
	public static int atlasSizeY = 0;
	public static ShaderUniformFloat4 uniformEntityColor = new ShaderUniformFloat4("entityColor");
	public static ShaderUniformInt uniformEntityId = new ShaderUniformInt("entityId");
	public static ShaderUniformInt uniformBlockEntityId = new ShaderUniformInt("blockEntityId");
	static double previousCameraPositionX;
	static double previousCameraPositionY;
	static double previousCameraPositionZ;
	static double cameraPositionX;
	static double cameraPositionY;
	static double cameraPositionZ;
	static int shadowPassInterval = 0;
	public static boolean needResizeShadow = false;
	static int shadowMapWidth = 1024;
	static int shadowMapHeight = 1024;
	static int spShadowMapWidth = 1024;
	static int spShadowMapHeight = 1024;
	static float shadowMapFOV = 90.0F;
	static float shadowMapHalfPlane = 160.0F;
	static boolean shadowMapIsOrtho = true;
	static float shadowDistanceRenderMul = -1.0F;
	static int shadowPassCounter = 0;
	static int preShadowPassThirdPersonView;
	public static boolean shouldSkipDefaultShadow = false;
	static boolean waterShadowEnabled = false;
	static final int MaxDrawBuffers = 8;
	static final int MaxColorBuffers = 8;
	static final int MaxDepthBuffers = 3;
	static final int MaxShadowColorBuffers = 8;
	static final int MaxShadowDepthBuffers = 2;
	static int usedColorBuffers = 0;
	static int usedDepthBuffers = 0;
	static int usedShadowColorBuffers = 0;
	static int usedShadowDepthBuffers = 0;
	static int usedColorAttachs = 0;
	static int usedDrawBuffers = 0;
	static int dfb = 0;
	static int sfb = 0;
	private static int[] gbuffersFormat = new int[8];
	private static boolean[] gbuffersClear = new boolean[8];
	public static int activeProgram = 0;
	public static final int ProgramNone = 0;
	public static final int ProgramBasic = 1;
	public static final int ProgramTextured = 2;
	public static final int ProgramTexturedLit = 3;
	public static final int ProgramSkyBasic = 4;
	public static final int ProgramSkyTextured = 5;
	public static final int ProgramClouds = 6;
	public static final int ProgramTerrain = 7;
	public static final int ProgramTerrainSolid = 8;
	public static final int ProgramTerrainCutoutMip = 9;
	public static final int ProgramTerrainCutout = 10;
	public static final int ProgramDamagedBlock = 11;
	public static final int ProgramWater = 12;
	public static final int ProgramBlock = 13;
	public static final int ProgramBeaconBeam = 14;
	public static final int ProgramItem = 15;
	public static final int ProgramEntities = 16;
	public static final int ProgramArmorGlint = 17;
	public static final int ProgramSpiderEyes = 18;
	public static final int ProgramHand = 19;
	public static final int ProgramWeather = 20;
	public static final int ProgramComposite = 21;
	public static final int ProgramComposite1 = 22;
	public static final int ProgramComposite2 = 23;
	public static final int ProgramComposite3 = 24;
	public static final int ProgramComposite4 = 25;
	public static final int ProgramComposite5 = 26;
	public static final int ProgramComposite6 = 27;
	public static final int ProgramComposite7 = 28;
	public static final int ProgramFinal = 29;
	public static final int ProgramShadow = 30;
	public static final int ProgramShadowSolid = 31;
	public static final int ProgramShadowCutout = 32;
	public static final int ProgramCount = 33;
	public static final int MaxCompositePasses = 8;
	private static final String[] programNames = new String[] { "", "gbuffers_basic", "gbuffers_textured",
			"gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_clouds", "gbuffers_terrain",
			"gbuffers_terrain_solid", "gbuffers_terrain_cutout_mip", "gbuffers_terrain_cutout", "gbuffers_damagedblock",
			"gbuffers_water", "gbuffers_block", "gbuffers_beaconbeam", "gbuffers_item", "gbuffers_entities",
			"gbuffers_armor_glint", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite",
			"composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final",
			"shadow", "shadow_solid", "shadow_cutout" };
	private static final int[] programBackups = new int[] { 0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3,
			3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30 };
	static int[] programsID = new int[33];
	private static int[] programsRef = new int[33];
	private static int programIDCopyDepth = 0;
	private static String[] programsDrawBufSettings = new String[33];
	private static String newDrawBufSetting = null;
	static IntBuffer[] programsDrawBuffers = new IntBuffer[33];
	static IntBuffer activeDrawBuffers = null;
	private static String[] programsColorAtmSettings = new String[33];
	private static String newColorAtmSetting = null;
	private static String activeColorAtmSettings = null;
	private static int[] programsCompositeMipmapSetting = new int[33];
	private static int newCompositeMipmapSetting = 0;
	private static int activeCompositeMipmapSetting = 0;
	public static Properties loadedShaders = null;
	public static Properties shadersConfig = null;
	public static ITextureObject defaultTexture = null;
	public static boolean normalMapEnabled = false;
	public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
	public static boolean[] shadowMipmapEnabled = new boolean[2];
	public static boolean[] shadowFilterNearest = new boolean[2];
	public static boolean[] shadowColorMipmapEnabled = new boolean[8];
	public static boolean[] shadowColorFilterNearest = new boolean[8];
	public static boolean configTweakBlockDamage = true;
	public static boolean configCloudShadow = true;
	public static float configHandDepthMul = 0.125F;
	public static float configRenderResMul = 1.0F;
	public static float configShadowResMul = 1.0F;
	public static int configTexMinFilB = 0;
	public static int configTexMinFilN = 0;
	public static int configTexMinFilS = 0;
	public static int configTexMagFilB = 0;
	public static int configTexMagFilN = 0;
	public static int configTexMagFilS = 0;
	public static boolean configShadowClipFrustrum = true;
	public static boolean configNormalMap = true;
	public static boolean configSpecularMap = true;
	public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting",
			"Classic Lighting", 0);
	public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight",
			"Old Hand Light", 0);
	public static int configAntialiasingLevel = 0;
	public static final int texMinFilRange = 3;
	public static final int texMagFilRange = 2;
	public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
	public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
	public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
	public static final int[] texMagFilValue = new int[] { 9728, 9729 };
	static IShaderPack shaderPack = null;
	public static boolean shaderPackLoaded = false;
	static File currentshader;
	static String currentshadername;
	public static String packNameNone = "OFF";
	static String packNameDefault = "(internal)";
	static String shaderpacksdirname = "shaderpacks";
	static String optionsfilename = "optionsshaders.txt";
	static File shadersdir;
	static File shaderpacksdir;
	static File configFile;
	static ShaderOption[] shaderPackOptions = null;
	static Set<String> shaderPackOptionSliders = null;
	static ShaderProfile[] shaderPackProfiles = null;
	static Map<String, ShaderOption[]> shaderPackGuiScreens = null;
	public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
	public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting",
			"Classic Lighting", 0);
	public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight",
			"Old Hand Light", 0);
	public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight",
			"Dynamic Hand Light", 0);
	public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse(
			"shadowTranslucent", "Shadow Translucent", 0);
	public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse(
			"underwaterOverlay", "Underwater Overlay", 0);
	public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
	public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
	public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
	public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid",
			"Back-face Solid", 0);
	public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout",
			"Back-face Cutout", 0);
	public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse(
			"backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
	public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse(
			"backFace.translucent", "Back-face Translucent", 0);
	private static Map<String, String> shaderPackResources = new HashMap<String, String>();
	private static World currentWorld = null;
	private static List<Integer> shaderPackDimensions = new ArrayList<Integer>();
	private static CustomTexture[] customTexturesGbuffers = null;
	private static CustomTexture[] customTexturesComposite = null;
	private static final int STAGE_GBUFFERS = 0;
	private static final int STAGE_COMPOSITE = 1;
	private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite" };
	public static final boolean enableShadersOption = true;
	private static final boolean enableShadersDebug = true;
	private static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
	public static float blockLightLevel05 = 0.5F;
	public static float blockLightLevel06 = 0.6F;
	public static float blockLightLevel08 = 0.8F;
	public static float aoLevel = -1.0F;
	public static float sunPathRotation = 0.0F;
	public static float shadowAngleInterval = 0.0F;
	public static int fogMode = 0;
	public static float fogColorR;
	public static float fogColorG;
	public static float fogColorB;
	public static float shadowIntervalSize = 2.0F;
	public static int terrainIconSize = 16;
	public static int[] terrainTextureSize = new int[2];
	private static HFNoiseTexture noiseTexture;
	private static boolean noiseTextureEnabled = false;
	private static int noiseTextureResolution = 256;
	static final int[] dfbColorTexturesA = new int[16];
	static final int[] colorTexturesToggle = new int[8];
	static final int[] colorTextureTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
	static final boolean[][] programsToggleColorTextures = new boolean[33][8];
	private static final int bigBufferSize = 2196;
	private static final ByteBuffer bigBuffer = (ByteBuffer) BufferUtils.createByteBuffer(2196).limit(0);
	static final float[] faProjection = new float[16];
	static final float[] faProjectionInverse = new float[16];
	static final float[] faModelView = new float[16];
	static final float[] faModelViewInverse = new float[16];
	static final float[] faShadowProjection = new float[16];
	static final float[] faShadowProjectionInverse = new float[16];
	static final float[] faShadowModelView = new float[16];
	static final float[] faShadowModelViewInverse = new float[16];
	static final FloatBuffer projection = nextFloatBuffer(16);
	static final FloatBuffer projectionInverse = nextFloatBuffer(16);
	static final FloatBuffer modelView = nextFloatBuffer(16);
	static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
	static final FloatBuffer shadowProjection = nextFloatBuffer(16);
	static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
	static final FloatBuffer shadowModelView = nextFloatBuffer(16);
	static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
	static final FloatBuffer previousProjection = nextFloatBuffer(16);
	static final FloatBuffer previousModelView = nextFloatBuffer(16);
	static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
	static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
	static final IntBuffer dfbColorTextures = nextIntBuffer(16);
	static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
	static final IntBuffer sfbColorTextures = nextIntBuffer(8);
	static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
	static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
	static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
	static final IntBuffer drawBuffersNone = nextIntBuffer(8);
	static final IntBuffer drawBuffersAll = nextIntBuffer(8);
	static final IntBuffer drawBuffersClear0 = nextIntBuffer(8);
	static final IntBuffer drawBuffersClear1 = nextIntBuffer(8);
	static final IntBuffer drawBuffersClearColor = nextIntBuffer(8);
	static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8);
	static final IntBuffer[] drawBuffersBuffer = nextIntBufferArray(33, 8);
	static Map<Block, Integer> mapBlockToEntityData;
	private static final Pattern gbufferFormatPattern = Pattern
			.compile("[ \t]*const[ \t]*int[ \t]*(\\w+)Format[ \t]*=[ \t]*([RGBA0123456789FUI_SNORM]*)[ \t]*;.*");
	private static final Pattern gbufferClearPattern = Pattern
			.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)Clear[ \t]*=[ \t]*false[ \t]*;.*");
	private static final Pattern gbufferMipmapEnabledPattern = Pattern
			.compile("[ \t]*const[ \t]*bool[ \t]*(\\w+)MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*");
	private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM",
			"RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM",
			"RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I",
			"RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2",
			"R11F_G11F_B10F", "RGB9_E5" };
	private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322,
			33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836,
			33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
	private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
	public static int[] entityData = new int[32];
	public static int entityDataIndex = 0;

	private static ByteBuffer nextByteBuffer(int size) {
		ByteBuffer bytebuffer = bigBuffer;
		int i = bytebuffer.limit();
		bytebuffer.position(i).limit(i + size);
		return bytebuffer.slice();
	}

	private static IntBuffer nextIntBuffer(int size) {
		ByteBuffer bytebuffer = bigBuffer;
		int i = bytebuffer.limit();
		bytebuffer.position(i).limit(i + size * 4);
		return bytebuffer.asIntBuffer();
	}

	private static FloatBuffer nextFloatBuffer(int size) {
		ByteBuffer bytebuffer = bigBuffer;
		int i = bytebuffer.limit();
		bytebuffer.position(i).limit(i + size * 4);
		return bytebuffer.asFloatBuffer();
	}

	private static IntBuffer[] nextIntBufferArray(int count, int size) {
		IntBuffer[] aintbuffer = new IntBuffer[count];

		for (int i = 0; i < count; ++i) {
			aintbuffer[i] = nextIntBuffer(size);
		}

		return aintbuffer;
	}

	public static void loadConfig() {
		SMCLog.info("Load ShadersMod configuration.");

		try {
			if (!shaderpacksdir.exists()) {
				shaderpacksdir.mkdir();
			}
		} catch (Exception var8) {
			SMCLog.severe("Failed to open the shaderpacks directory: " + shaderpacksdir);
		}

		shadersConfig = new PropertiesOrdered();
		shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");

		if (configFile.exists()) {
			try {
				FileReader filereader = new FileReader(configFile);
				shadersConfig.load(filereader);
				filereader.close();
			} catch (Exception var7) {
				;
			}
		}

		if (!configFile.exists()) {
			try {
				storeConfig();
			} catch (Exception var6) {
				;
			}
		}

		EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();

		for (int i = 0; i < aenumshaderoption.length; ++i) {
			EnumShaderOption enumshaderoption = aenumshaderoption[i];
			String s = enumshaderoption.getPropertyKey();
			String s1 = enumshaderoption.getValueDefault();
			String s2 = shadersConfig.getProperty(s, s1);
			setEnumShaderOption(enumshaderoption, s2);
		}

		loadShaderPack();
	}

	private static void setEnumShaderOption(EnumShaderOption eso, String str) {
		if (str == null) {
			str = eso.getValueDefault();
		}

		switch (eso) {
		case ANTIALIASING:
			configAntialiasingLevel = Config.parseInt(str, 0);
			break;

		case NORMAL_MAP:
			configNormalMap = Config.parseBoolean(str, true);
			break;

		case SPECULAR_MAP:
			configSpecularMap = Config.parseBoolean(str, true);
			break;

		case RENDER_RES_MUL:
			configRenderResMul = Config.parseFloat(str, 1.0F);
			break;

		case SHADOW_RES_MUL:
			configShadowResMul = Config.parseFloat(str, 1.0F);
			break;

		case HAND_DEPTH_MUL:
			configHandDepthMul = Config.parseFloat(str, 0.125F);
			break;

		case CLOUD_SHADOW:
			configCloudShadow = Config.parseBoolean(str, true);
			break;

		case OLD_HAND_LIGHT:
			configOldHandLight.setPropertyValue(str);
			break;

		case OLD_LIGHTING:
			configOldLighting.setPropertyValue(str);
			break;

		case SHADER_PACK:
			currentshadername = str;
			break;

		case TWEAK_BLOCK_DAMAGE:
			configTweakBlockDamage = Config.parseBoolean(str, true);
			break;

		case SHADOW_CLIP_FRUSTRUM:
			configShadowClipFrustrum = Config.parseBoolean(str, true);
			break;

		case TEX_MIN_FIL_B:
			configTexMinFilB = Config.parseInt(str, 0);
			break;

		case TEX_MIN_FIL_N:
			configTexMinFilN = Config.parseInt(str, 0);
			break;

		case TEX_MIN_FIL_S:
			configTexMinFilS = Config.parseInt(str, 0);
			break;

		case TEX_MAG_FIL_B:
			configTexMagFilB = Config.parseInt(str, 0);
			break;

		case TEX_MAG_FIL_N:
			configTexMagFilB = Config.parseInt(str, 0);
			break;

		case TEX_MAG_FIL_S:
			configTexMagFilB = Config.parseInt(str, 0);
			break;

		default:
			throw new IllegalArgumentException("Unknown option: " + eso);
		}
	}

	public static void storeConfig() {
		SMCLog.info("Save ShadersMod configuration.");

		if (shadersConfig == null) {
			shadersConfig = new PropertiesOrdered();
		}

		EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();

		for (int i = 0; i < aenumshaderoption.length; ++i) {
			EnumShaderOption enumshaderoption = aenumshaderoption[i];
			String s = enumshaderoption.getPropertyKey();
			String s1 = getEnumShaderOption(enumshaderoption);
			shadersConfig.setProperty(s, s1);
		}

		try {
			FileWriter filewriter = new FileWriter(configFile);
			shadersConfig.store(filewriter, (String) null);
			filewriter.close();
		} catch (Exception exception) {
			SMCLog.severe(
					"Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
		}
	}

	public static String getEnumShaderOption(EnumShaderOption eso) {
		switch (eso) {
		case ANTIALIASING:
			return Integer.toString(configAntialiasingLevel);

		case NORMAL_MAP:
			return Boolean.toString(configNormalMap);

		case SPECULAR_MAP:
			return Boolean.toString(configSpecularMap);

		case RENDER_RES_MUL:
			return Float.toString(configRenderResMul);

		case SHADOW_RES_MUL:
			return Float.toString(configShadowResMul);

		case HAND_DEPTH_MUL:
			return Float.toString(configHandDepthMul);

		case CLOUD_SHADOW:
			return Boolean.toString(configCloudShadow);

		case OLD_HAND_LIGHT:
			return configOldHandLight.getPropertyValue();

		case OLD_LIGHTING:
			return configOldLighting.getPropertyValue();

		case SHADER_PACK:
			return currentshadername;

		case TWEAK_BLOCK_DAMAGE:
			return Boolean.toString(configTweakBlockDamage);

		case SHADOW_CLIP_FRUSTRUM:
			return Boolean.toString(configShadowClipFrustrum);

		case TEX_MIN_FIL_B:
			return Integer.toString(configTexMinFilB);

		case TEX_MIN_FIL_N:
			return Integer.toString(configTexMinFilN);

		case TEX_MIN_FIL_S:
			return Integer.toString(configTexMinFilS);

		case TEX_MAG_FIL_B:
			return Integer.toString(configTexMagFilB);

		case TEX_MAG_FIL_N:
			return Integer.toString(configTexMagFilB);

		case TEX_MAG_FIL_S:
			return Integer.toString(configTexMagFilB);

		default:
			throw new IllegalArgumentException("Unknown option: " + eso);
		}
	}

	public static void setShaderPack(String par1name) {
		currentshadername = par1name;
		shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
		loadShaderPack();
	}

	public static void loadShaderPack() {
		boolean flag = shaderPackLoaded;
		boolean flag1 = isOldLighting();
		shaderPackLoaded = false;

		if (shaderPack != null) {
			shaderPack.close();
			shaderPack = null;
			shaderPackResources.clear();
			shaderPackDimensions.clear();
			shaderPackOptions = null;
			shaderPackOptionSliders = null;
			shaderPackProfiles = null;
			shaderPackGuiScreens = null;
			shaderPackClouds.resetValue();
			shaderPackOldHandLight.resetValue();
			shaderPackDynamicHandLight.resetValue();
			shaderPackOldLighting.resetValue();
			resetCustomTextures();
		}

		boolean flag2 = false;

		if (Config.isAntialiasing()) {
			SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
			flag2 = true;
		}

		if (Config.isAnisotropicFiltering()) {
			SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: "
					+ Config.getAnisotropicFilterLevel() + "x");
			flag2 = true;
		}

		if (Config.isFastRender()) {
			SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
			flag2 = true;
		}

		String s = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), packNameDefault);

		if (!s.isEmpty() && !s.equals(packNameNone) && !flag2) {
			if (s.equals(packNameDefault)) {
				shaderPack = new ShaderPackDefault();
				shaderPackLoaded = true;
			} else {
				try {
					File file1 = new File(shaderpacksdir, s);

					if (file1.isDirectory()) {
						shaderPack = new ShaderPackFolder(s, file1);
						shaderPackLoaded = true;
					} else if (file1.isFile() && s.toLowerCase().endsWith(".zip")) {
						shaderPack = new ShaderPackZip(s, file1);
						shaderPackLoaded = true;
					}
				} catch (Exception var6) {
					;
				}
			}
		}

		if (shaderPack != null) {
			SMCLog.info("Loaded shaderpack: " + getShaderPackName());
		} else {
			SMCLog.info("No shaderpack loaded.");
			shaderPack = new ShaderPackNone();
		}

		loadShaderPackResources();
		loadShaderPackDimensions();
		shaderPackOptions = loadShaderPackOptions();
		loadShaderPackProperties();
		boolean flag4 = shaderPackLoaded != flag;
		boolean flag3 = isOldLighting() != flag1;

		if (flag4 || flag3) {
			DefaultVertexFormats.updateVertexFormats();

			if (Reflector.LightUtil.exists()) {
				Reflector.LightUtil_itemConsumer.setValue((Object) null);
				Reflector.LightUtil_tessellator.setValue((Object) null);
			}

			updateBlockLightLevel();
			mc.scheduleResourcesRefresh();
		}
	}

	private static void loadShaderPackDimensions() {
		shaderPackDimensions.clear();

		for (int i = -128; i <= 128; ++i) {
			String s = "/shaders/world" + i;

			if (shaderPack.hasDirectory(s)) {
				shaderPackDimensions.add(Integer.valueOf(i));
			}
		}

		if (shaderPackDimensions.size() > 0) {
			Integer[] ainteger = (Integer[]) shaderPackDimensions.toArray(new Integer[shaderPackDimensions.size()]);
			Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[]) ainteger));
		}
	}

	private static void loadShaderPackProperties() {
		shaderPackClouds.resetValue();
		shaderPackOldHandLight.resetValue();
		shaderPackDynamicHandLight.resetValue();
		shaderPackOldLighting.resetValue();
		shaderPackShadowTranslucent.resetValue();
		shaderPackUnderwaterOverlay.resetValue();
		shaderPackSun.resetValue();
		shaderPackMoon.resetValue();
		shaderPackVignette.resetValue();
		shaderPackBackFaceSolid.resetValue();
		shaderPackBackFaceCutout.resetValue();
		shaderPackBackFaceCutoutMipped.resetValue();
		shaderPackBackFaceTranslucent.resetValue();
		BlockAliases.reset();

		if (shaderPack != null) {
			BlockAliases.update(shaderPack);
			String s = "/shaders/shaders.properties";

			try {
				InputStream inputstream = shaderPack.getResourceAsStream(s);

				if (inputstream == null) {
					return;
				}

				Properties properties = new PropertiesOrdered();
				properties.load(inputstream);
				inputstream.close();
				shaderPackClouds.loadFrom(properties);
				shaderPackOldHandLight.loadFrom(properties);
				shaderPackDynamicHandLight.loadFrom(properties);
				shaderPackOldLighting.loadFrom(properties);
				shaderPackShadowTranslucent.loadFrom(properties);
				shaderPackUnderwaterOverlay.loadFrom(properties);
				shaderPackSun.loadFrom(properties);
				shaderPackVignette.loadFrom(properties);
				shaderPackMoon.loadFrom(properties);
				shaderPackBackFaceSolid.loadFrom(properties);
				shaderPackBackFaceCutout.loadFrom(properties);
				shaderPackBackFaceCutoutMipped.loadFrom(properties);
				shaderPackBackFaceTranslucent.loadFrom(properties);
				shaderPackOptionSliders = ShaderPackParser.parseOptionSliders(properties, shaderPackOptions);
				shaderPackProfiles = ShaderPackParser.parseProfiles(properties, shaderPackOptions);
				shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(properties, shaderPackProfiles,
						shaderPackOptions);
				customTexturesGbuffers = loadCustomTextures(properties, 0);
				customTexturesComposite = loadCustomTextures(properties, 1);
			} catch (IOException var3) {
				Config.warn("[Shaders] Error reading: " + s);
			}
		}
	}

	private static CustomTexture[] loadCustomTextures(Properties props, int stage) {
		String s = "texture." + STAGE_NAMES[stage] + ".";
		Set set = props.keySet();
		List<CustomTexture> list = new ArrayList<CustomTexture>();

		for (Object s10 : set) {
			String s1 = (String) s10;
			if (s1.startsWith(s)) {
				String s2 = s1.substring(s.length());
				String s3 = props.getProperty(s1).trim();
				int i = getTextureIndex(stage, s2);

				if (i < 0) {
					SMCLog.warning("Invalid texture name: " + s1);
				} else {
					try {
						String s4 = "shaders/" + StrUtils.removePrefix(s3, "/");
						InputStream inputstream = shaderPack.getResourceAsStream(s4);

						if (inputstream == null) {
							SMCLog.warning("Texture not found: " + s3);
						} else {
							IOUtils.closeQuietly(inputstream);
							SimpleShaderTexture simpleshadertexture = new SimpleShaderTexture(s4);
							simpleshadertexture.loadTexture(mc.getResourceManager());
							CustomTexture customtexture = new CustomTexture(i, s4, simpleshadertexture);
							list.add(customtexture);
						}
					} catch (IOException ioexception) {
						SMCLog.warning("Error loading texture: " + s3);
						SMCLog.warning("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
					}
				}
			}
		}

		if (list.size() <= 0) {
			return null;
		} else {
			CustomTexture[] acustomtexture = (CustomTexture[]) list.toArray(new CustomTexture[list.size()]);
			return acustomtexture;
		}
	}

	private static int getTextureIndex(int stage, String name) {
		if (stage == 0) {
			if (name.equals("texture")) {
				return 0;
			}

			if (name.equals("lightmap")) {
				return 1;
			}

			if (name.equals("normals")) {
				return 2;
			}

			if (name.equals("specular")) {
				return 3;
			}

			if (name.equals("shadowtex0") || name.equals("watershadow")) {
				return 4;
			}

			if (name.equals("shadow")) {
				return waterShadowEnabled ? 5 : 4;
			}

			if (name.equals("shadowtex1")) {
				return 5;
			}

			if (name.equals("depthtex0")) {
				return 6;
			}

			if (name.equals("gaux1")) {
				return 7;
			}

			if (name.equals("gaux2")) {
				return 8;
			}

			if (name.equals("gaux3")) {
				return 9;
			}

			if (name.equals("gaux4")) {
				return 10;
			}

			if (name.equals("depthtex1")) {
				return 12;
			}

			if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
				return 13;
			}

			if (name.equals("shadowcolor1")) {
				return 14;
			}

			if (name.equals("noisetex")) {
				return 15;
			}
		}

		if (stage == 1) {
			if (name.equals("colortex0") || name.equals("colortex0")) {
				return 0;
			}

			if (name.equals("colortex1") || name.equals("gdepth")) {
				return 1;
			}

			if (name.equals("colortex2") || name.equals("gnormal")) {
				return 2;
			}

			if (name.equals("colortex3") || name.equals("composite")) {
				return 3;
			}

			if (name.equals("shadowtex0") || name.equals("watershadow")) {
				return 4;
			}

			if (name.equals("shadow")) {
				return waterShadowEnabled ? 5 : 4;
			}

			if (name.equals("shadowtex1")) {
				return 5;
			}

			if (name.equals("depthtex0") || name.equals("gdepthtex")) {
				return 6;
			}

			if (name.equals("colortex4") || name.equals("gaux1")) {
				return 7;
			}

			if (name.equals("colortex5") || name.equals("gaux2")) {
				return 8;
			}

			if (name.equals("colortex6") || name.equals("gaux3")) {
				return 9;
			}

			if (name.equals("colortex7") || name.equals("gaux4")) {
				return 10;
			}

			if (name.equals("depthtex1")) {
				return 11;
			}

			if (name.equals("depthtex2")) {
				return 12;
			}

			if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
				return 13;
			}

			if (name.equals("shadowcolor1")) {
				return 14;
			}

			if (name.equals("noisetex")) {
				return 15;
			}
		}

		return -1;
	}

	private static void bindCustomTextures(CustomTexture[] cts) {
		if (cts != null) {
			for (int i = 0; i < cts.length; ++i) {
				CustomTexture customtexture = cts[i];
				GlStateManager.setActiveTexture(33984 + customtexture.getTextureUnit());
				ITextureObject itextureobject = customtexture.getTexture();
				GlStateManager.bindTexture(itextureobject.getGlTextureId());
			}
		}
	}

	private static void resetCustomTextures() {
		deleteCustomTextures(customTexturesGbuffers);
		deleteCustomTextures(customTexturesComposite);
		customTexturesGbuffers = null;
		customTexturesComposite = null;
	}

	private static void deleteCustomTextures(CustomTexture[] cts) {
		if (cts != null) {
			for (int i = 0; i < cts.length; ++i) {
				CustomTexture customtexture = cts[i];
				ITextureObject itextureobject = customtexture.getTexture();
				TextureUtil.deleteTexture(itextureobject.getGlTextureId());
			}
		}
	}

	public static ShaderOption[] getShaderPackOptions(String screenName) {
		ShaderOption[] ashaderoption = (ShaderOption[]) shaderPackOptions.clone();

		if (shaderPackGuiScreens == null) {
			if (shaderPackProfiles != null) {
				ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderPackProfiles, ashaderoption);
				ashaderoption = (ShaderOption[]) Config.addObjectToArray(ashaderoption, shaderoptionprofile, 0);
			}

			ashaderoption = getVisibleOptions(ashaderoption);
			return ashaderoption;
		} else {
			String s = screenName != null ? "screen." + screenName : "screen";
			ShaderOption[] ashaderoption1 = shaderPackGuiScreens.get(s);

			if (ashaderoption1 == null) {
				return new ShaderOption[0];
			} else {
				List<ShaderOption> list = new ArrayList<ShaderOption>();

				for (int i = 0; i < ashaderoption1.length; ++i) {
					ShaderOption shaderoption = ashaderoption1[i];

					if (shaderoption == null) {
						list.add((ShaderOption) null);
					} else if (shaderoption instanceof ShaderOptionRest) {
						ShaderOption[] ashaderoption2 = getShaderOptionsRest(shaderPackGuiScreens, ashaderoption);
						list.addAll(Arrays.asList(ashaderoption2));
					} else {
						list.add(shaderoption);
					}
				}

				ShaderOption[] ashaderoption3 = (ShaderOption[]) list.toArray(new ShaderOption[list.size()]);
				return ashaderoption3;
			}
		}
	}

	private static ShaderOption[] getShaderOptionsRest(Map<String, ShaderOption[]> mapScreens, ShaderOption[] ops) {
		Set<String> set = new HashSet<String>();

		for (String s : mapScreens.keySet()) {
			ShaderOption[] ashaderoption = mapScreens.get(s);

			for (int i = 0; i < ashaderoption.length; ++i) {
				ShaderOption shaderoption = ashaderoption[i];

				if (shaderoption != null) {
					set.add(shaderoption.getName());
				}
			}
		}

		List<ShaderOption> list = new ArrayList<ShaderOption>();

		for (int j = 0; j < ops.length; ++j) {
			ShaderOption shaderoption1 = ops[j];

			if (shaderoption1.isVisible()) {
				String s1 = shaderoption1.getName();

				if (!set.contains(s1)) {
					list.add(shaderoption1);
				}
			}
		}

		ShaderOption[] ashaderoption1 = (ShaderOption[]) list.toArray(new ShaderOption[list.size()]);
		return ashaderoption1;
	}

	public static ShaderOption getShaderOption(String name) {
		return ShaderUtils.getShaderOption(name, shaderPackOptions);
	}

	public static ShaderOption[] getShaderPackOptions() {
		return shaderPackOptions;
	}

	public static boolean isShaderPackOptionSlider(String name) {
		return shaderPackOptionSliders == null ? false : shaderPackOptionSliders.contains(name);
	}

	private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
		List<ShaderOption> list = new ArrayList<ShaderOption>();

		for (int i = 0; i < ops.length; ++i) {
			ShaderOption shaderoption = ops[i];

			if (shaderoption.isVisible()) {
				list.add(shaderoption);
			}
		}

		ShaderOption[] ashaderoption = (ShaderOption[]) list.toArray(new ShaderOption[list.size()]);
		return ashaderoption;
	}

	public static void saveShaderPackOptions() {
		saveShaderPackOptions(shaderPackOptions, shaderPack);
	}

	private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
		Properties properties = new Properties();

		if (shaderPackOptions != null) {
			for (int i = 0; i < sos.length; ++i) {
				ShaderOption shaderoption = sos[i];

				if (shaderoption.isChanged() && shaderoption.isEnabled()) {
					properties.setProperty(shaderoption.getName(), shaderoption.getValue());
				}
			}
		}

		try {
			saveOptionProperties(sp, properties);
		} catch (IOException ioexception) {
			Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
			ioexception.printStackTrace();
		}
	}

	private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
		String s = shaderpacksdirname + "/" + sp.getName() + ".txt";
		File file1 = new File(Minecraft.getMinecraft().mcDataDir, s);

		if (props.isEmpty()) {
			file1.delete();
		} else {
			FileOutputStream fileoutputstream = new FileOutputStream(file1);
			props.store(fileoutputstream, (String) null);
			fileoutputstream.flush();
			fileoutputstream.close();
		}
	}

	private static ShaderOption[] loadShaderPackOptions() {
		try {
			ShaderOption[] ashaderoption = ShaderPackParser.parseShaderPackOptions(shaderPack, programNames,
					shaderPackDimensions);
			Properties properties = loadOptionProperties(shaderPack);

			for (int i = 0; i < ashaderoption.length; ++i) {
				ShaderOption shaderoption = ashaderoption[i];
				String s = properties.getProperty(shaderoption.getName());

				if (s != null) {
					shaderoption.resetValue();

					if (!shaderoption.setValue(s)) {
						Config.warn("[Shaders] Invalid value, option: " + shaderoption.getName() + ", value: " + s);
					}
				}
			}

			return ashaderoption;
		} catch (IOException ioexception) {
			Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
			ioexception.printStackTrace();
			return null;
		}
	}

	private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
		Properties properties = new Properties();
		String s = shaderpacksdirname + "/" + sp.getName() + ".txt";
		File file1 = new File(Minecraft.getMinecraft().mcDataDir, s);

		if (file1.exists() && file1.isFile() && file1.canRead()) {
			FileInputStream fileinputstream = new FileInputStream(file1);
			properties.load(fileinputstream);
			fileinputstream.close();
			return properties;
		} else {
			return properties;
		}
	}

	public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
		List<ShaderOption> list = new ArrayList<ShaderOption>();

		for (int i = 0; i < ops.length; ++i) {
			ShaderOption shaderoption = ops[i];

			if (shaderoption.isEnabled() && shaderoption.isChanged()) {
				list.add(shaderoption);
			}
		}

		ShaderOption[] ashaderoption = (ShaderOption[]) list.toArray(new ShaderOption[list.size()]);
		return ashaderoption;
	}

	private static String applyOptions(String line, ShaderOption[] ops) {
		if (ops != null && ops.length > 0) {
			for (int i = 0; i < ops.length; ++i) {
				ShaderOption shaderoption = ops[i];
				String s = shaderoption.getName();

				if (shaderoption.matchesLine(line)) {
					line = shaderoption.getSourceLine();
					break;
				}
			}

			return line;
		} else {
			return line;
		}
	}

	static ArrayList listOfShaders() {
		ArrayList<String> arraylist = new ArrayList<String>();
		arraylist.add(packNameNone);
		arraylist.add(packNameDefault);

		try {
			if (!shaderpacksdir.exists()) {
				shaderpacksdir.mkdir();
			}

			File[] afile = shaderpacksdir.listFiles();

			for (int i = 0; i < afile.length; ++i) {
				File file1 = afile[i];
				String s = file1.getName();

				if (file1.isDirectory()) {
					File file2 = new File(file1, "shaders");

					if (file2.exists() && file2.isDirectory()) {
						arraylist.add(s);
					}
				} else if (file1.isFile() && s.toLowerCase().endsWith(".zip")) {
					arraylist.add(s);
				}
			}
		} catch (Exception var6) {
			;
		}

		return arraylist;
	}

	static String versiontostring(int vv) {
		String s = Integer.toString(vv);
		return Integer.toString(Integer.parseInt(s.substring(1, 3))) + "."
				+ Integer.toString(Integer.parseInt(s.substring(3, 5))) + "."
				+ Integer.toString(Integer.parseInt(s.substring(5)));
	}

	static void checkOptifine() {
	}

	public static int checkFramebufferStatus(String location) {
		int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);

		if (i != 36053) {
			System.err.format("FramebufferStatus 0x%04X at %s\n", i, location);
		}

		return i;
	}

	public static int checkGLError(String location) {
		int i = GL11.glGetError();

		if (i != 0) {
			boolean flag = false;

			if (!flag) {
				if (i == 1286) {
					int j = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
					System.err.format("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", i, GLU.gluErrorString(i), j,
							location);
				} else {
					System.err.format("GL error 0x%04X: %s at %s\n", i, GLU.gluErrorString(i), location);
				}
			}
		}

		return i;
	}

	public static int checkGLError(String location, String info) {
		int i = GL11.glGetError();

		if (i != 0) {
			System.err.format("GL error 0x%04x: %s at %s %s\n", i, GLU.gluErrorString(i), location, info);
		}

		return i;
	}

	public static int checkGLError(String location, String info1, String info2) {
		int i = GL11.glGetError();

		if (i != 0) {
			System.err.format("GL error 0x%04x: %s at %s %s %s\n", i, GLU.gluErrorString(i), location, info1, info2);
		}

		return i;
	}

	private static void printChat(String str) {
		mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(str));
	}

	private static void printChatAndLogError(String str) {
		SMCLog.severe(str);
		mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(str));
	}

	public static void printIntBuffer(String title, IntBuffer buf) {
		StringBuilder stringbuilder = new StringBuilder(128);
		stringbuilder.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit())
				.append(" cap ").append(buf.capacity()).append(" :");
		int i = buf.limit();

		for (int j = 0; j < i; ++j) {
			stringbuilder.append(" ").append(buf.get(j));
		}

		stringbuilder.append("]");
		SMCLog.info(stringbuilder.toString());
	}

	public static void startup(Minecraft mc) {
		checkShadersModInstalled();
		Shaders.mc = mc;
		mc = Minecraft.getMinecraft();
		capabilities = GLContext.getCapabilities();
		glVersionString = GL11.glGetString(GL11.GL_VERSION);
		glVendorString = GL11.glGetString(GL11.GL_VENDOR);
		glRendererString = GL11.glGetString(GL11.GL_RENDERER);
		SMCLog.info("ShadersMod version: 2.4.12");
		SMCLog.info("OpenGL Version: " + glVersionString);
		SMCLog.info("Vendor:  " + glVendorString);
		SMCLog.info("Renderer: " + glRendererString);
		SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ")
				+ (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ")
				+ (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
		SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(GL20.GL_MAX_DRAW_BUFFERS));
		SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
		SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));
		hasGlGenMipmap = capabilities.OpenGL30;
		loadConfig();
	}

	private static String toStringYN(boolean b) {
		return b ? "Y" : "N";
	}

	public static void updateBlockLightLevel() {
		if (isOldLighting()) {
			blockLightLevel05 = 0.5F;
			blockLightLevel06 = 0.6F;
			blockLightLevel08 = 0.8F;
		} else {
			blockLightLevel05 = 1.0F;
			blockLightLevel06 = 1.0F;
			blockLightLevel08 = 1.0F;
		}
	}

	public static boolean isOldHandLight() {
		if (!configOldHandLight.isDefault()) {
			return configOldHandLight.isTrue();
		} else {
			return !shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true;
		}
	}

	public static boolean isDynamicHandLight() {
		return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
	}

	public static boolean isOldLighting() {
		if (!configOldLighting.isDefault()) {
			return configOldLighting.isTrue();
		} else {
			return !shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true;
		}
	}

	public static boolean isRenderShadowTranslucent() {
		return !shaderPackShadowTranslucent.isFalse();
	}

	public static boolean isUnderwaterOverlay() {
		return !shaderPackUnderwaterOverlay.isFalse();
	}

	public static boolean isSun() {
		return !shaderPackSun.isFalse();
	}

	public static boolean isMoon() {
		return !shaderPackMoon.isFalse();
	}

	public static boolean isVignette() {
		return !shaderPackVignette.isFalse();
	}

	public static boolean isRenderBackFace(BlockRenderLayer blockLayerIn) {
		switch (blockLayerIn) {
		case SOLID:
			return shaderPackBackFaceSolid.isTrue();

		case CUTOUT:
			return shaderPackBackFaceCutout.isTrue();

		case CUTOUT_MIPPED:
			return shaderPackBackFaceCutoutMipped.isTrue();

		case TRANSLUCENT:
			return shaderPackBackFaceTranslucent.isTrue();

		default:
			return false;
		}
	}

	public static void init() {
		boolean flag;

		if (!isInitializedOnce) {
			isInitializedOnce = true;
			flag = true;
		} else {
			flag = false;
		}

		if (!isShaderPackInitialized) {
			checkGLError("Shaders.init pre");

			if (getShaderPackName() != null) {
				;
			}

			if (!capabilities.OpenGL20) {
				printChatAndLogError("No OpenGL 2.0");
			}

			if (!capabilities.GL_EXT_framebuffer_object) {
				printChatAndLogError("No EXT_framebuffer_object");
			}

			dfbDrawBuffers.position(0).limit(8);
			dfbColorTextures.position(0).limit(16);
			dfbDepthTextures.position(0).limit(3);
			sfbDrawBuffers.position(0).limit(8);
			sfbDepthTextures.position(0).limit(2);
			sfbColorTextures.position(0).limit(8);
			usedColorBuffers = 4;
			usedDepthBuffers = 1;
			usedShadowColorBuffers = 0;
			usedShadowDepthBuffers = 0;
			usedColorAttachs = 1;
			usedDrawBuffers = 1;
			Arrays.fill(gbuffersFormat, 6408);
			Arrays.fill(gbuffersClear, true);
			Arrays.fill(shadowHardwareFilteringEnabled, false);
			Arrays.fill(shadowMipmapEnabled, false);
			Arrays.fill(shadowFilterNearest, false);
			Arrays.fill(shadowColorMipmapEnabled, false);
			Arrays.fill(shadowColorFilterNearest, false);
			centerDepthSmoothEnabled = false;
			noiseTextureEnabled = false;
			sunPathRotation = 0.0F;
			shadowIntervalSize = 2.0F;
			shadowDistanceRenderMul = -1.0F;
			aoLevel = -1.0F;
			useEntityAttrib = false;
			useMidTexCoordAttrib = false;
			useMultiTexCoord3Attrib = false;
			useTangentAttrib = false;
			waterShadowEnabled = false;
			updateChunksErrorRecorded = false;
			updateBlockLightLevel();
			ShaderProfile shaderprofile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
			String s = "";

			if (currentWorld != null) {
				int i = currentWorld.provider.getDimensionType().getId();

				if (shaderPackDimensions.contains(Integer.valueOf(i))) {
					s = "world" + i + "/";
				}
			}

			if (saveFinalShaders) {
				clearDirectory(new File(shaderpacksdir, "debug"));
			}

			for (int k1 = 0; k1 < 33; ++k1) {
				String s1 = programNames[k1];

				if (s1.equals("")) {
					programsID[k1] = programsRef[k1] = 0;
					programsDrawBufSettings[k1] = null;
					programsColorAtmSettings[k1] = null;
					programsCompositeMipmapSetting[k1] = 0;
				} else {
					newDrawBufSetting = null;
					newColorAtmSetting = null;
					newCompositeMipmapSetting = 0;
					String s2 = s + s1;

					if (shaderprofile != null && shaderprofile.isProgramDisabled(s2)) {
						SMCLog.info("Program disabled: " + s2);
						s1 = "<disabled>";
						s2 = s + s1;
					}

					String s3 = "/shaders/" + s2;
					int j = setupProgram(k1, s3 + ".vsh", s3 + ".fsh");

					if (j > 0) {
						SMCLog.info("Program loaded: " + s2);
					}

					programsID[k1] = programsRef[k1] = j;
					programsDrawBufSettings[k1] = j != 0 ? newDrawBufSetting : null;
					programsColorAtmSettings[k1] = j != 0 ? newColorAtmSetting : null;
					programsCompositeMipmapSetting[k1] = j != 0 ? newCompositeMipmapSetting : 0;
				}
			}

			int l1 = GL11.glGetInteger(GL20.GL_MAX_DRAW_BUFFERS);
			new HashMap();

			for (int i2 = 0; i2 < 33; ++i2) {
				Arrays.fill(programsToggleColorTextures[i2], false);

				if (i2 == 29) {
					programsDrawBuffers[i2] = null;
				} else if (programsID[i2] == 0) {
					if (i2 == 30) {
						programsDrawBuffers[i2] = drawBuffersNone;
					} else {
						programsDrawBuffers[i2] = drawBuffersColorAtt0;
					}
				} else {
					String s4 = programsDrawBufSettings[i2];

					if (s4 != null) {
						IntBuffer intbuffer = drawBuffersBuffer[i2];
						int k = s4.length();

						if (k > usedDrawBuffers) {
							usedDrawBuffers = k;
						}

						if (k > l1) {
							k = l1;
						}

						programsDrawBuffers[i2] = intbuffer;
						intbuffer.limit(k);

						for (int l = 0; l < k; ++l) {
							int i1 = 0;

							if (s4.length() > l) {
								int j1 = s4.charAt(l) - 48;

								if (i2 != 30) {
									if (j1 >= 0 && j1 <= 7) {
										programsToggleColorTextures[i2][j1] = true;
										i1 = j1 + 36064;

										if (j1 > usedColorAttachs) {
											usedColorAttachs = j1;
										}

										if (j1 > usedColorBuffers) {
											usedColorBuffers = j1;
										}
									}
								} else if (j1 >= 0 && j1 <= 1) {
									i1 = j1 + 36064;

									if (j1 > usedShadowColorBuffers) {
										usedShadowColorBuffers = j1;
									}
								}
							}

							intbuffer.put(l, i1);
						}
					} else if (i2 != 30 && i2 != 31 && i2 != 32) {
						programsDrawBuffers[i2] = dfbDrawBuffers;
						usedDrawBuffers = usedColorBuffers;
						Arrays.fill(programsToggleColorTextures[i2], 0, usedColorBuffers, true);
					} else {
						programsDrawBuffers[i2] = sfbDrawBuffers;
					}
				}
			}

			usedColorAttachs = usedColorBuffers;
			shadowPassInterval = usedShadowDepthBuffers > 0 ? 1 : 0;
			shouldSkipDefaultShadow = usedShadowDepthBuffers > 0;
			SMCLog.info("usedColorBuffers: " + usedColorBuffers);
			SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
			SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
			SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
			SMCLog.info("usedColorAttachs: " + usedColorAttachs);
			SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
			dfbDrawBuffers.position(0).limit(usedDrawBuffers);
			dfbColorTextures.position(0).limit(usedColorBuffers * 2);

			for (int j2 = 0; j2 < usedDrawBuffers; ++j2) {
				dfbDrawBuffers.put(j2, 36064 + j2);
			}

			if (usedDrawBuffers > l1) {
				printChatAndLogError(
						"[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + l1);
			}

			sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);

			for (int k2 = 0; k2 < usedShadowColorBuffers; ++k2) {
				sfbDrawBuffers.put(k2, 36064 + k2);
			}

			for (int l2 = 0; l2 < 33; ++l2) {
				int i3;

				for (i3 = l2; programsID[i3] == 0 && programBackups[i3] != i3; i3 = programBackups[i3]) {
					;
				}

				if (i3 != l2 && l2 != 30) {
					programsID[l2] = programsID[i3];
					programsDrawBufSettings[l2] = programsDrawBufSettings[i3];
					programsDrawBuffers[l2] = programsDrawBuffers[i3];
				}
			}

			resize();
			resizeShadow();

			if (noiseTextureEnabled) {
				setupNoiseTexture();
			}

			if (defaultTexture == null) {
				defaultTexture = ShadersTex.createDefaultTexture();
			}

			GlStateManager.pushMatrix();
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			preCelestialRotate();
			postCelestialRotate();
			GlStateManager.popMatrix();
			isShaderPackInitialized = true;
			loadEntityDataMap();
			resetDisplayList();

			if (!flag) {
				;
			}

			checkGLError("Shaders.init");
		}
	}

	public static void resetDisplayList() {
		++numberResetDisplayList;
		needResetModels = true;
		SMCLog.info("Reset world renderers");
		mc.renderGlobal.loadRenderers();
	}

	public static void resetDisplayListModels() {
		if (needResetModels) {
			needResetModels = false;
			SMCLog.info("Reset model renderers");

			for (Render render : mc.getRenderManager().getEntityRenderMap().values()) {
				if (render instanceof RenderLiving) {
					RenderLiving renderliving = (RenderLiving) render;
					resetDisplayListModel(renderliving.getMainModel());
				}
			}
		}
	}

	public static void resetDisplayListModel(ModelBase model) {
		if (model != null) {
			for (Object object : model.boxList) {
				if (object instanceof ModelRenderer) {
					resetDisplayListModelRenderer((ModelRenderer) object);
				}
			}
		}
	}

	public static void resetDisplayListModelRenderer(ModelRenderer mrr) {
		mrr.resetDisplayList();

		if (mrr.childModels != null) {
			int i = 0;

			for (int j = mrr.childModels.size(); i < j; ++i) {
				resetDisplayListModelRenderer(mrr.childModels.get(i));
			}
		}
	}

	private static int setupProgram(int program, String vShaderPath, String fShaderPath) {
		checkGLError("pre setupProgram");
		int i = ARBShaderObjects.glCreateProgramObjectARB();
		checkGLError("create");

		if (i != 0) {
			progUseEntityAttrib = false;
			progUseMidTexCoordAttrib = false;
			progUseTangentAttrib = false;
			int j = createVertShader(vShaderPath);
			int k = createFragShader(fShaderPath);
			checkGLError("create");

			if (j == 0 && k == 0) {
				ARBShaderObjects.glDeleteObjectARB(i);
				i = 0;
			} else {
				if (j != 0) {
					ARBShaderObjects.glAttachObjectARB(i, j);
					checkGLError("attach");
				}

				if (k != 0) {
					ARBShaderObjects.glAttachObjectARB(i, k);
					checkGLError("attach");
				}

				if (progUseEntityAttrib) {
					ARBVertexShader.glBindAttribLocationARB(i, entityAttrib, "mc_Entity");
					checkGLError("mc_Entity");
				}

				if (progUseMidTexCoordAttrib) {
					ARBVertexShader.glBindAttribLocationARB(i, midTexCoordAttrib, "mc_midTexCoord");
					checkGLError("mc_midTexCoord");
				}

				if (progUseTangentAttrib) {
					ARBVertexShader.glBindAttribLocationARB(i, tangentAttrib, "at_tangent");
					checkGLError("at_tangent");
				}

				ARBShaderObjects.glLinkProgramARB(i);

				if (GL20.glGetProgrami(i, 35714) != 1) {
					SMCLog.severe("Error linking program: " + i);
				}

				printLogInfo(i, vShaderPath + ", " + fShaderPath);

				if (j != 0) {
					ARBShaderObjects.glDetachObjectARB(i, j);
					ARBShaderObjects.glDeleteObjectARB(j);
				}

				if (k != 0) {
					ARBShaderObjects.glDetachObjectARB(i, k);
					ARBShaderObjects.glDeleteObjectARB(k);
				}

				programsID[program] = i;
				useProgram(program);
				ARBShaderObjects.glValidateProgramARB(i);
				useProgram(0);
				printLogInfo(i, vShaderPath + ", " + fShaderPath);
				int l = GL20.glGetProgrami(i, 35715);

				if (l != 1) {
					String s = "\"";
					printChatAndLogError("[Shaders] Error: Invalid program " + s + programNames[program] + s);
					ARBShaderObjects.glDeleteObjectARB(i);
					i = 0;
				}
			}
		}

		return i;
	}

	private static int createVertShader(String filename) {
		int i = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);

		if (i == 0) {
			return 0;
		} else {
			StringBuilder stringbuilder = new StringBuilder(131072);
			BufferedReader bufferedreader = null;

			try {
				bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
			} catch (Exception var8) {
				try {
					bufferedreader = new BufferedReader(new FileReader(new File(filename)));
				} catch (Exception var7) {
					ARBShaderObjects.glDeleteObjectARB(i);
					return 0;
				}
			}

			ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
			List<String> list = new ArrayList<String>();

			if (bufferedreader != null) {
				try {
					bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);

					while (true) {
						String s = bufferedreader.readLine();

						if (s == null) {
							bufferedreader.close();
							break;
						}

						s = applyOptions(s, ashaderoption);
						stringbuilder.append(s).append('\n');

						if (s.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
							useEntityAttrib = true;
							progUseEntityAttrib = true;
						} else if (s.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
							useMidTexCoordAttrib = true;
							progUseMidTexCoordAttrib = true;
						} else if (s.matches(".*gl_MultiTexCoord3.*")) {
							useMultiTexCoord3Attrib = true;
						} else if (s.matches("attribute [_a-zA-Z0-9]+ at_tangent.*")) {
							useTangentAttrib = true;
							progUseTangentAttrib = true;
						}
					}
				} catch (Exception exception) {
					SMCLog.severe("Couldn't read " + filename + "!");
					exception.printStackTrace();
					ARBShaderObjects.glDeleteObjectARB(i);
					return 0;
				}
			}

			if (saveFinalShaders) {
				saveShader(filename, stringbuilder.toString());
			}

			ARBShaderObjects.glShaderSourceARB(i, (CharSequence) stringbuilder);
			ARBShaderObjects.glCompileShaderARB(i);

			if (GL20.glGetShaderi(i, 35713) != 1) {
				SMCLog.severe("Error compiling vertex shader: " + filename);
			}

			printShaderLogInfo(i, filename, list);
			return i;
		}
	}

	private static int createFragShader(String filename) {
		int i = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);

		if (i == 0) {
			return 0;
		} else {
			StringBuilder stringbuilder = new StringBuilder(131072);
			BufferedReader bufferedreader = null;

			try {
				bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
			} catch (Exception var13) {
				try {
					bufferedreader = new BufferedReader(new FileReader(new File(filename)));
				} catch (Exception var12) {
					ARBShaderObjects.glDeleteObjectARB(i);
					return 0;
				}
			}

			ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
			List<String> list = new ArrayList<String>();

			if (bufferedreader != null) {
				try {
					bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);

					while (true) {
						String s = bufferedreader.readLine();

						if (s == null) {
							bufferedreader.close();
							break;
						}

						s = applyOptions(s, ashaderoption);
						stringbuilder.append(s).append('\n');

						if (!s.matches("#version .*")) {
							if (s.matches("uniform [ _a-zA-Z0-9]+ shadow;.*")) {
								if (usedShadowDepthBuffers < 1) {
									usedShadowDepthBuffers = 1;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ watershadow;.*")) {
								waterShadowEnabled = true;

								if (usedShadowDepthBuffers < 2) {
									usedShadowDepthBuffers = 2;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ shadowtex0;.*")) {
								if (usedShadowDepthBuffers < 1) {
									usedShadowDepthBuffers = 1;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ shadowtex1;.*")) {
								if (usedShadowDepthBuffers < 2) {
									usedShadowDepthBuffers = 2;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ shadowcolor;.*")) {
								if (usedShadowColorBuffers < 1) {
									usedShadowColorBuffers = 1;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ shadowcolor0;.*")) {
								if (usedShadowColorBuffers < 1) {
									usedShadowColorBuffers = 1;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ shadowcolor1;.*")) {
								if (usedShadowColorBuffers < 2) {
									usedShadowColorBuffers = 2;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ depthtex0;.*")) {
								if (usedDepthBuffers < 1) {
									usedDepthBuffers = 1;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ depthtex1;.*")) {
								if (usedDepthBuffers < 2) {
									usedDepthBuffers = 2;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ depthtex2;.*")) {
								if (usedDepthBuffers < 3) {
									usedDepthBuffers = 3;
								}
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ gdepth;.*")) {
								if (gbuffersFormat[1] == 6408) {
									gbuffersFormat[1] = 34836;
								}
							} else if (usedColorBuffers < 5 && s.matches("uniform [ _a-zA-Z0-9]+ gaux1;.*")) {
								usedColorBuffers = 5;
							} else if (usedColorBuffers < 6 && s.matches("uniform [ _a-zA-Z0-9]+ gaux2;.*")) {
								usedColorBuffers = 6;
							} else if (usedColorBuffers < 7 && s.matches("uniform [ _a-zA-Z0-9]+ gaux3;.*")) {
								usedColorBuffers = 7;
							} else if (usedColorBuffers < 8 && s.matches("uniform [ _a-zA-Z0-9]+ gaux4;.*")) {
								usedColorBuffers = 8;
							} else if (usedColorBuffers < 5 && s.matches("uniform [ _a-zA-Z0-9]+ colortex4;.*")) {
								usedColorBuffers = 5;
							} else if (usedColorBuffers < 6 && s.matches("uniform [ _a-zA-Z0-9]+ colortex5;.*")) {
								usedColorBuffers = 6;
							} else if (usedColorBuffers < 7 && s.matches("uniform [ _a-zA-Z0-9]+ colortex6;.*")) {
								usedColorBuffers = 7;
							} else if (usedColorBuffers < 8 && s.matches("uniform [ _a-zA-Z0-9]+ colortex7;.*")) {
								usedColorBuffers = 8;
							} else if (s.matches("uniform [ _a-zA-Z0-9]+ centerDepthSmooth;.*")) {
								centerDepthSmoothEnabled = true;
							} else if (s.matches("/\\* SHADOWRES:[0-9]+ \\*/.*")) {
								String[] astring17 = s.split("(:| )", 4);
								SMCLog.info("Shadow map resolution: " + astring17[2]);
								spShadowMapWidth = spShadowMapHeight = Integer.parseInt(astring17[2]);
								shadowMapWidth = shadowMapHeight = Math
										.round((float) spShadowMapWidth * configShadowResMul);
							} else if (s.matches(
									"[ \t]*const[ \t]*int[ \t]*shadowMapResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring16 = s.split("(=[ \t]*|;)");
								SMCLog.info("Shadow map resolution: " + astring16[1]);
								spShadowMapWidth = spShadowMapHeight = Integer.parseInt(astring16[1]);
								shadowMapWidth = shadowMapHeight = Math
										.round((float) spShadowMapWidth * configShadowResMul);
							} else if (s.matches("/\\* SHADOWFOV:[0-9\\.]+ \\*/.*")) {
								String[] astring15 = s.split("(:| )", 4);
								SMCLog.info("Shadow map field of view: " + astring15[2]);
								shadowMapFOV = Float.parseFloat(astring15[2]);
								shadowMapIsOrtho = false;
							} else if (s.matches("/\\* SHADOWHPL:[0-9\\.]+ \\*/.*")) {
								String[] astring14 = s.split("(:| )", 4);
								SMCLog.info("Shadow map half-plane: " + astring14[2]);
								shadowMapHalfPlane = Float.parseFloat(astring14[2]);
								shadowMapIsOrtho = true;
							} else if (s
									.matches("[ \t]*const[ \t]*float[ \t]*shadowDistance[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring13 = s.split("(=[ \t]*|;)");
								SMCLog.info("Shadow map distance: " + astring13[1]);
								shadowMapHalfPlane = Float.parseFloat(astring13[1]);
								shadowMapIsOrtho = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*shadowDistanceRenderMul[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring12 = s.split("(=[ \t]*|;)");
								SMCLog.info("Shadow distance render mul: " + astring12[1]);
								shadowDistanceRenderMul = Float.parseFloat(astring12[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*shadowIntervalSize[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring11 = s.split("(=[ \t]*|;)");
								SMCLog.info("Shadow map interval size: " + astring11[1]);
								shadowIntervalSize = Float.parseFloat(astring11[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*generateShadowMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("Generate shadow mipmap");
								Arrays.fill(shadowMipmapEnabled, true);
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*generateShadowColorMipmap[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("Generate shadow color mipmap");
								Arrays.fill(shadowColorMipmapEnabled, true);
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("Hardware shadow filtering enabled.");
								Arrays.fill(shadowHardwareFilteringEnabled, true);
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering0[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowHardwareFiltering0");
								shadowHardwareFilteringEnabled[0] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*shadowHardwareFiltering1[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowHardwareFiltering1");
								shadowHardwareFilteringEnabled[1] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowtex0Mipmap|shadowtexMipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowtex0Mipmap");
								shadowMipmapEnabled[0] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowtex1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowtex1Mipmap");
								shadowMipmapEnabled[1] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Mipmap|shadowColor0Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowcolor0Mipmap");
								shadowColorMipmapEnabled[0] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Mipmap|shadowColor1Mipmap)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowcolor1Mipmap");
								shadowColorMipmapEnabled[1] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowtex0Nearest|shadowtexNearest|shadow0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowtex0Nearest");
								shadowFilterNearest[0] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowtex1Nearest|shadow1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowtex1Nearest");
								shadowFilterNearest[1] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowcolor0Nearest|shadowColor0Nearest|shadowColor0MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowcolor0Nearest");
								shadowColorFilterNearest[0] = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*(shadowcolor1Nearest|shadowColor1Nearest|shadowColor1MinMagNearest)[ \t]*=[ \t]*true[ \t]*;.*")) {
								SMCLog.info("shadowcolor1Nearest");
								shadowColorFilterNearest[1] = true;
							} else if (s.matches("/\\* WETNESSHL:[0-9\\.]+ \\*/.*")) {
								String[] astring10 = s.split("(:| )", 4);
								SMCLog.info("Wetness halflife: " + astring10[2]);
								wetnessHalfLife = Float.parseFloat(astring10[2]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*wetnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring9 = s.split("(=[ \t]*|;)");
								SMCLog.info("Wetness halflife: " + astring9[1]);
								wetnessHalfLife = Float.parseFloat(astring9[1]);
							} else if (s.matches("/\\* DRYNESSHL:[0-9\\.]+ \\*/.*")) {
								String[] astring8 = s.split("(:| )", 4);
								SMCLog.info("Dryness halflife: " + astring8[2]);
								drynessHalfLife = Float.parseFloat(astring8[2]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*drynessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring7 = s.split("(=[ \t]*|;)");
								SMCLog.info("Dryness halflife: " + astring7[1]);
								drynessHalfLife = Float.parseFloat(astring7[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*eyeBrightnessHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring6 = s.split("(=[ \t]*|;)");
								SMCLog.info("Eye brightness halflife: " + astring6[1]);
								eyeBrightnessHalflife = Float.parseFloat(astring6[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*centerDepthHalflife[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring5 = s.split("(=[ \t]*|;)");
								SMCLog.info("Center depth halflife: " + astring5[1]);
								centerDepthSmoothHalflife = Float.parseFloat(astring5[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*sunPathRotation[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring4 = s.split("(=[ \t]*|;)");
								SMCLog.info("Sun path rotation: " + astring4[1]);
								sunPathRotation = Float.parseFloat(astring4[1]);
							} else if (s.matches(
									"[ \t]*const[ \t]*float[ \t]*ambientOcclusionLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring3 = s.split("(=[ \t]*|;)");
								SMCLog.info("AO Level: " + astring3[1]);
								aoLevel = Config.limit(Float.parseFloat(astring3[1]), 0.0F, 1.0F);
							} else if (s.matches(
									"[ \t]*const[ \t]*int[ \t]*superSamplingLevel[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring2 = s.split("(=[ \t]*|;)");
								int i1 = Integer.parseInt(astring2[1]);

								if (i1 > 1) {
									SMCLog.info("Super sampling level: " + i1 + "x");
									superSamplingLevel = i1;
								} else {
									superSamplingLevel = 1;
								}
							} else if (s.matches(
									"[ \t]*const[ \t]*int[ \t]*noiseTextureResolution[ \t]*=[ \t]*-?[0-9.]+f?;.*")) {
								String[] astring1 = s.split("(=[ \t]*|;)");
								SMCLog.info("Noise texture enabled");
								SMCLog.info("Noise texture resolution: " + astring1[1]);
								noiseTextureResolution = Integer.parseInt(astring1[1]);
								noiseTextureEnabled = true;
							} else if (s.matches(
									"[ \t]*const[ \t]*int[ \t]*\\w+Format[ \t]*=[ \t]*[RGBA0123456789FUI_SNORM]*[ \t]*;.*")) {
								Matcher matcher2 = gbufferFormatPattern.matcher(s);
								matcher2.matches();
								String s3 = matcher2.group(1);
								String s4 = matcher2.group(2);
								int k = getBufferIndexFromString(s3);
								int l = getTextureFormatFromString(s4);

								if (k >= 0 && l != 0) {
									gbuffersFormat[k] = l;
									SMCLog.info("%s format: %s", s3, s4);
								}
							} else if (s.matches("[ \t]*const[ \t]*bool[ \t]*\\w+Clear[ \t]*=[ \t]*false[ \t]*;.*")) {
								if (filename.matches(".*composite[0-9]?.fsh")) {
									Matcher matcher1 = gbufferClearPattern.matcher(s);
									matcher1.matches();
									String s2 = matcher1.group(1);
									int j1 = getBufferIndexFromString(s2);

									if (j1 >= 0) {
										gbuffersClear[j1] = false;
										SMCLog.info("%s clear disabled", s2);
									}
								}
							} else if (s.matches("/\\* GAUX4FORMAT:RGBA32F \\*/.*")) {
								SMCLog.info("gaux4 format : RGB32AF");
								gbuffersFormat[7] = 34836;
							} else if (s.matches("/\\* GAUX4FORMAT:RGB32F \\*/.*")) {
								SMCLog.info("gaux4 format : RGB32F");
								gbuffersFormat[7] = 34837;
							} else if (s.matches("/\\* GAUX4FORMAT:RGB16 \\*/.*")) {
								SMCLog.info("gaux4 format : RGB16");
								gbuffersFormat[7] = 32852;
							} else if (s.matches(
									"[ \t]*const[ \t]*bool[ \t]*\\w+MipmapEnabled[ \t]*=[ \t]*true[ \t]*;.*")) {
								if (filename.matches(".*composite[0-9]?.fsh") || filename.matches(".*final.fsh")) {
									Matcher matcher = gbufferMipmapEnabledPattern.matcher(s);
									matcher.matches();
									String s1 = matcher.group(1);
									int j = getBufferIndexFromString(s1);

									if (j >= 0) {
										newCompositeMipmapSetting |= 1 << j;
										SMCLog.info("%s mipmap enabled", s1);
									}
								}
							} else if (s.matches("/\\* DRAWBUFFERS:[0-7N]* \\*/.*")) {
								String[] astring = s.split("(:| )", 4);
								newDrawBufSetting = astring[2];
							}
						}
					}
				} catch (Exception exception) {
					SMCLog.severe("Couldn't read " + filename + "!");
					exception.printStackTrace();
					ARBShaderObjects.glDeleteObjectARB(i);
					return 0;
				}
			}

			if (saveFinalShaders) {
				saveShader(filename, stringbuilder.toString());
			}

			ARBShaderObjects.glShaderSourceARB(i, (CharSequence) stringbuilder);
			ARBShaderObjects.glCompileShaderARB(i);

			if (GL20.glGetShaderi(i, 35713) != 1) {
				SMCLog.severe("Error compiling fragment shader: " + filename);
			}

			printShaderLogInfo(i, filename, list);
			return i;
		}
	}

	private static void saveShader(String filename, String code) {
		try {
			File file1 = new File(shaderpacksdir, "debug/" + filename);
			file1.getParentFile().mkdirs();
			Config.writeFile(file1, code);
		} catch (IOException ioexception) {
			Config.warn("Error saving: " + filename);
			ioexception.printStackTrace();
		}
	}

	private static void clearDirectory(File dir) {
		if (dir.exists()) {
			if (dir.isDirectory()) {
				File[] afile = dir.listFiles();

				if (afile != null) {
					for (int i = 0; i < afile.length; ++i) {
						File file1 = afile[i];

						if (file1.isDirectory()) {
							clearDirectory(file1);
						}

						file1.delete();
					}
				}
			}
		}
	}

	private static boolean printLogInfo(int obj, String name) {
		IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
		ARBShaderObjects.glGetObjectParameterARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, intbuffer);
		int i = intbuffer.get();

		if (i > 1) {
			ByteBuffer bytebuffer = BufferUtils.createByteBuffer(i);
			intbuffer.flip();
			ARBShaderObjects.glGetInfoLogARB(obj, intbuffer, bytebuffer);
			byte[] abyte = new byte[i];
			bytebuffer.get(abyte);

			if (abyte[i - 1] == 0) {
				abyte[i - 1] = 10;
			}

			String s = new String(abyte);
			SMCLog.info("Info log: " + name + "\n" + s);
			return false;
		} else {
			return true;
		}
	}

	private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
		IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
		int i = GL20.glGetShaderi(shader, 35716);

		if (i <= 1) {
			return true;
		} else {
			for (int j = 0; j < listFiles.size(); ++j) {
				String s = listFiles.get(j);
				SMCLog.info("File: " + (j + 1) + " = " + s);
			}

			String s1 = GL20.glGetShaderInfoLog(shader, i);
			SMCLog.info("Shader info log: " + name + "\n" + s1);
			return false;
		}
	}

	public static void setDrawBuffers(IntBuffer drawBuffers) {
		if (drawBuffers == null) {
			drawBuffers = drawBuffersNone;
		}

		if (activeDrawBuffers != drawBuffers) {
			activeDrawBuffers = drawBuffers;
			GL20.glDrawBuffers(drawBuffers);
		}
	}

	public static void useProgram(int program) {
		checkGLError("pre-useProgram");

		if (isShadowPass) {
			program = 30;

			if (programsID[30] == 0) {
				normalMapEnabled = false;
				return;
			}
		}

		if (activeProgram != program) {
			activeProgram = program;
			ARBShaderObjects.glUseProgramObjectARB(programsID[program]);

			if (programsID[program] == 0) {
				normalMapEnabled = false;
			} else {
				if (checkGLError("useProgram ", programNames[program]) != 0) {
					programsID[program] = 0;
				}

				IntBuffer intbuffer = programsDrawBuffers[program];

				if (isRenderingDfb) {
					setDrawBuffers(intbuffer);
					checkGLError(programNames[program], " draw buffers = ", programsDrawBufSettings[program]);
				}

				activeCompositeMipmapSetting = programsCompositeMipmapSetting[program];
				uniformEntityColor.setProgram(programsID[activeProgram]);
				uniformEntityId.setProgram(programsID[activeProgram]);
				uniformBlockEntityId.setProgram(programsID[activeProgram]);

				switch (program) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 16:
				case 18:
				case 19:
				case 20:
					normalMapEnabled = true;
					setProgramUniform1i("texture", 0);
					setProgramUniform1i("lightmap", 1);
					setProgramUniform1i("normals", 2);
					setProgramUniform1i("specular", 3);
					setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
					setProgramUniform1i("watershadow", 4);
					setProgramUniform1i("shadowtex0", 4);
					setProgramUniform1i("shadowtex1", 5);
					setProgramUniform1i("depthtex0", 6);

					if (customTexturesGbuffers != null) {
						setProgramUniform1i("gaux1", 7);
						setProgramUniform1i("gaux2", 8);
						setProgramUniform1i("gaux3", 9);
						setProgramUniform1i("gaux4", 10);
					}

					setProgramUniform1i("depthtex1", 12);
					setProgramUniform1i("shadowcolor", 13);
					setProgramUniform1i("shadowcolor0", 13);
					setProgramUniform1i("shadowcolor1", 14);
					setProgramUniform1i("noisetex", 15);
					break;

				case 14:
				case 15:
				case 17:
				default:
					normalMapEnabled = false;
					break;

				case 21:
				case 22:
				case 23:
				case 24:
				case 25:
				case 26:
				case 27:
				case 28:
				case 29:
					normalMapEnabled = false;
					setProgramUniform1i("gcolor", 0);
					setProgramUniform1i("gdepth", 1);
					setProgramUniform1i("gnormal", 2);
					setProgramUniform1i("composite", 3);
					setProgramUniform1i("gaux1", 7);
					setProgramUniform1i("gaux2", 8);
					setProgramUniform1i("gaux3", 9);
					setProgramUniform1i("gaux4", 10);
					setProgramUniform1i("colortex0", 0);
					setProgramUniform1i("colortex1", 1);
					setProgramUniform1i("colortex2", 2);
					setProgramUniform1i("colortex3", 3);
					setProgramUniform1i("colortex4", 7);
					setProgramUniform1i("colortex5", 8);
					setProgramUniform1i("colortex6", 9);
					setProgramUniform1i("colortex7", 10);
					setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
					setProgramUniform1i("watershadow", 4);
					setProgramUniform1i("shadowtex0", 4);
					setProgramUniform1i("shadowtex1", 5);
					setProgramUniform1i("gdepthtex", 6);
					setProgramUniform1i("depthtex0", 6);
					setProgramUniform1i("depthtex1", 11);
					setProgramUniform1i("depthtex2", 12);
					setProgramUniform1i("shadowcolor", 13);
					setProgramUniform1i("shadowcolor0", 13);
					setProgramUniform1i("shadowcolor1", 14);
					setProgramUniform1i("noisetex", 15);
					break;

				case 30:
				case 31:
				case 32:
					setProgramUniform1i("tex", 0);
					setProgramUniform1i("texture", 0);
					setProgramUniform1i("lightmap", 1);
					setProgramUniform1i("normals", 2);
					setProgramUniform1i("specular", 3);
					setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
					setProgramUniform1i("watershadow", 4);
					setProgramUniform1i("shadowtex0", 4);
					setProgramUniform1i("shadowtex1", 5);

					if (customTexturesGbuffers != null) {
						setProgramUniform1i("gaux1", 7);
						setProgramUniform1i("gaux2", 8);
						setProgramUniform1i("gaux3", 9);
						setProgramUniform1i("gaux4", 10);
					}

					setProgramUniform1i("shadowcolor", 13);
					setProgramUniform1i("shadowcolor0", 13);
					setProgramUniform1i("shadowcolor1", 14);
					setProgramUniform1i("noisetex", 15);
				}

				ItemStack itemstack = mc.player != null ? mc.player.getHeldItemMainhand() : null;
				Item item = itemstack != null ? itemstack.getItem() : null;
				int i = -1;
				Block block = null;

				if (item != null) {
					i = Item.REGISTRY.getIDForObject(item);
					block = Block.REGISTRY.getObjectById(i);
				}

				int j = block != null ? block.getLightValue(block.getDefaultState()) : 0;
				ItemStack itemstack1 = mc.player != null ? mc.player.getHeldItemOffhand() : null;
				Item item1 = itemstack1 != null ? itemstack1.getItem() : null;
				int k = -1;
				Block block1 = null;

				if (item1 != null) {
					k = Item.REGISTRY.getIDForObject(item1);
					block1 = Block.REGISTRY.getObjectById(k);
				}

				int l = block1 != null ? block1.getLightValue(block1.getDefaultState()) : 0;

				if (isOldHandLight() && l > j) {
					i = k;
					j = l;
				}

				setProgramUniform1i("heldItemId", i);
				setProgramUniform1i("heldBlockLightValue", j);
				setProgramUniform1i("heldItemId2", k);
				setProgramUniform1i("heldBlockLightValue2", l);
				setProgramUniform1i("fogMode", fogEnabled ? fogMode : 0);
				setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
				setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
				setProgramUniform1i("worldTime", (int) (worldTime % 24000L));
				setProgramUniform1i("worldDay", (int) (worldTime / 24000L));
				setProgramUniform1i("moonPhase", moonPhase);
				setProgramUniform1i("frameCounter", frameCounter);
				setProgramUniform1f("frameTime", frameTime);
				setProgramUniform1f("frameTimeCounter", frameTimeCounter);
				setProgramUniform1f("sunAngle", sunAngle);
				setProgramUniform1f("shadowAngle", shadowAngle);
				setProgramUniform1f("rainStrength", rainStrength);
				setProgramUniform1f("aspectRatio", (float) renderWidth / (float) renderHeight);
				setProgramUniform1f("viewWidth", (float) renderWidth);
				setProgramUniform1f("viewHeight", (float) renderHeight);
				setProgramUniform1f("near", 0.05F);
				setProgramUniform1f("far", (float) (mc.gameSettings.renderDistanceChunks * 16));
				setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
				setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
				setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1],
						shadowLightPosition[2]);
				setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
				setProgramUniform3f("previousCameraPosition", (float) previousCameraPositionX,
						(float) previousCameraPositionY, (float) previousCameraPositionZ);
				setProgramUniform3f("cameraPosition", (float) cameraPositionX, (float) cameraPositionY,
						(float) cameraPositionZ);
				setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
				setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
				setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
				setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
				setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
				setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);

				if (usedShadowDepthBuffers > 0) {
					setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
					setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
					setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
					setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
				}

				setProgramUniform1f("wetness", wetness);
				setProgramUniform1f("eyeAltitude", eyePosY);
				setProgramUniform2i("eyeBrightness", eyeBrightness & 65535, eyeBrightness >> 16);
				setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX),
						Math.round(eyeBrightnessFadeY));
				setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
				setProgramUniform1i("terrainIconSize", terrainIconSize);
				setProgramUniform1i("isEyeInWater", isEyeInWater);
				setProgramUniform1f("nightVision", nightVision);
				setProgramUniform1f("blindness", blindness);
				setProgramUniform1f("screenBrightness", mc.gameSettings.gammaSetting);
				setProgramUniform1i("hideGUI", mc.gameSettings.hideGUI ? 1 : 0);
				setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
				setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
				checkGLError("useProgram ", programNames[program]);
			}
		}
	}

	public static void setProgramUniform1i(String name, int x) {
		int i = programsID[activeProgram];

		if (i != 0) {
			int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
			ARBShaderObjects.glUniform1iARB(j, x);
			checkGLError(programNames[activeProgram], name);
		}
	}

	public static void setProgramUniform2i(String name, int x, int y) {
		int i = programsID[activeProgram];

		if (i != 0) {
			int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
			ARBShaderObjects.glUniform2iARB(j, x, y);
			checkGLError(programNames[activeProgram], name);
		}
	}

	public static void setProgramUniform1f(String name, float x) {
		int i = programsID[activeProgram];

		if (i != 0) {
			int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
			ARBShaderObjects.glUniform1fARB(j, x);
			checkGLError(programNames[activeProgram], name);
		}
	}

	public static void setProgramUniform3f(String name, float x, float y, float z) {
		int i = programsID[activeProgram];

		if (i != 0) {
			int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
			ARBShaderObjects.glUniform3fARB(j, x, y, z);
			checkGLError(programNames[activeProgram], name);
		}
	}

	public static void setProgramUniformMatrix4ARB(String name, boolean transpose, FloatBuffer matrix) {
		int i = programsID[activeProgram];

		if (i != 0 && matrix != null) {
			int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
			ARBShaderObjects.glUniformMatrix4ARB(j, transpose, matrix);
			checkGLError(programNames[activeProgram], name);
		}
	}

	private static int getBufferIndexFromString(String name) {
		if (!name.equals("colortex0") && !name.equals("gcolor")) {
			if (!name.equals("colortex1") && !name.equals("gdepth")) {
				if (!name.equals("colortex2") && !name.equals("gnormal")) {
					if (!name.equals("colortex3") && !name.equals("composite")) {
						if (!name.equals("colortex4") && !name.equals("gaux1")) {
							if (!name.equals("colortex5") && !name.equals("gaux2")) {
								if (!name.equals("colortex6") && !name.equals("gaux3")) {
									return !name.equals("colortex7") && !name.equals("gaux4") ? -1 : 7;
								} else {
									return 6;
								}
							} else {
								return 5;
							}
						} else {
							return 4;
						}
					} else {
						return 3;
					}
				} else {
					return 2;
				}
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	private static int getTextureFormatFromString(String par) {
		par = par.trim();

		for (int i = 0; i < formatNames.length; ++i) {
			String s = formatNames[i];

			if (par.equals(s)) {
				return formatIds[i];
			}
		}

		return 0;
	}

	private static void setupNoiseTexture() {
		if (noiseTexture == null) {
			noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
		}
	}

	private static void loadEntityDataMap() {
		mapBlockToEntityData = new IdentityHashMap<Block, Integer>(300);

		if (mapBlockToEntityData.isEmpty()) {
			for (ResourceLocation resourcelocation : Block.REGISTRY.getKeys()) {
				Block block = Block.REGISTRY.getObject(resourcelocation);
				int i = Block.REGISTRY.getIDForObject(block);
				mapBlockToEntityData.put(block, Integer.valueOf(i));
			}
		}

		BufferedReader bufferedreader = null;

		try {
			bufferedreader = new BufferedReader(
					new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
		} catch (Exception var8) {
			;
		}

		if (bufferedreader != null) {
			String s1;

			try {
				while ((s1 = bufferedreader.readLine()) != null) {
					Matcher matcher = patternLoadEntityDataMap.matcher(s1);

					if (matcher.matches()) {
						String s2 = matcher.group(1);
						String s = matcher.group(2);
						int j = Integer.parseInt(s);
						Block block1 = Block.getBlockFromName(s2);

						if (block1 != null) {
							mapBlockToEntityData.put(block1, Integer.valueOf(j));
						} else {
							SMCLog.warning("Unknown block name %s", s2);
						}
					} else {
						SMCLog.warning("unmatched %s\n", s1);
					}
				}
			} catch (Exception var9) {
				SMCLog.warning("Error parsing mc_Entity_x.txt");
			}
		}

		if (bufferedreader != null) {
			try {
				bufferedreader.close();
			} catch (Exception var7) {
				;
			}
		}
	}

	private static IntBuffer fillIntBufferZero(IntBuffer buf) {
		int i = buf.limit();

		for (int j = buf.position(); j < i; ++j) {
			buf.put(j, 0);
		}

		return buf;
	}

	public static void uninit() {
		if (isShaderPackInitialized) {
			checkGLError("Shaders.uninit pre");

			for (int i = 0; i < 33; ++i) {
				if (programsRef[i] != 0) {
					ARBShaderObjects.glDeleteObjectARB(programsRef[i]);
					checkGLError("del programRef");
				}

				programsRef[i] = 0;
				programsID[i] = 0;
				programsDrawBufSettings[i] = null;
				programsDrawBuffers[i] = null;
				programsCompositeMipmapSetting[i] = 0;
			}

			if (dfb != 0) {
				EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
				dfb = 0;
				checkGLError("del dfb");
			}

			if (sfb != 0) {
				EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
				sfb = 0;
				checkGLError("del sfb");
			}

			if (dfbDepthTextures != null) {
				GlStateManager.deleteTextures(dfbDepthTextures);
				fillIntBufferZero(dfbDepthTextures);
				checkGLError("del dfbDepthTextures");
			}

			if (dfbColorTextures != null) {
				GlStateManager.deleteTextures(dfbColorTextures);
				fillIntBufferZero(dfbColorTextures);
				checkGLError("del dfbTextures");
			}

			if (sfbDepthTextures != null) {
				GlStateManager.deleteTextures(sfbDepthTextures);
				fillIntBufferZero(sfbDepthTextures);
				checkGLError("del shadow depth");
			}

			if (sfbColorTextures != null) {
				GlStateManager.deleteTextures(sfbColorTextures);
				fillIntBufferZero(sfbColorTextures);
				checkGLError("del shadow color");
			}

			if (dfbDrawBuffers != null) {
				fillIntBufferZero(dfbDrawBuffers);
			}

			if (noiseTexture != null) {
				noiseTexture.destroy();
				noiseTexture = null;
			}

			SMCLog.info("Uninit");
			shadowPassInterval = 0;
			shouldSkipDefaultShadow = false;
			isShaderPackInitialized = false;
			checkGLError("Shaders.uninit");
		}
	}

	public static void scheduleResize() {
		renderDisplayHeight = 0;
	}

	public static void scheduleResizeShadow() {
		needResizeShadow = true;
	}

	private static void resize() {
		renderDisplayWidth = mc.displayWidth;
		renderDisplayHeight = mc.displayHeight;
		renderWidth = Math.round((float) renderDisplayWidth * configRenderResMul);
		renderHeight = Math.round((float) renderDisplayHeight * configRenderResMul);
		setupFrameBuffer();
	}

	private static void resizeShadow() {
		needResizeShadow = false;
		shadowMapWidth = Math.round((float) spShadowMapWidth * configShadowResMul);
		shadowMapHeight = Math.round((float) spShadowMapHeight * configShadowResMul);
		setupShadowFrameBuffer();
	}

	private static void setupFrameBuffer() {
		if (dfb != 0) {
			EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
			GlStateManager.deleteTextures(dfbDepthTextures);
			GlStateManager.deleteTextures(dfbColorTextures);
		}

		dfb = EXTFramebufferObject.glGenFramebuffersEXT();
		GL11.glGenTextures((IntBuffer) dfbDepthTextures.clear().limit(usedDepthBuffers));
		GL11.glGenTextures((IntBuffer) dfbColorTextures.clear().limit(16));
		dfbDepthTextures.position(0);
		dfbColorTextures.position(0);
		dfbColorTextures.get(dfbColorTexturesA).position(0);
		EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
		GL20.glDrawBuffers(0);
		GL11.glReadBuffer(0);

		for (int i = 0; i < usedDepthBuffers; ++i) {
			GlStateManager.bindTexture(dfbDepthTextures.get(i));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_DEPTH_TEXTURE_MODE, GL11.GL_LUMINANCE);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, renderWidth, renderHeight, 0,
					GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (FloatBuffer) null);
		}

		EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
		GL20.glDrawBuffers(dfbDrawBuffers);
		GL11.glReadBuffer(0);
		checkGLError("FT d");

		for (int k = 0; k < usedColorBuffers; ++k) {
			GlStateManager.bindTexture(dfbColorTexturesA[k]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, gbuffersFormat[k], renderWidth, renderHeight, 0, GL12.GL_BGRA,
					GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer) null);
			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[k], 0);
			checkGLError("FT c");
		}

		for (int l = 0; l < usedColorBuffers; ++l) {
			GlStateManager.bindTexture(dfbColorTexturesA[8 + l]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, gbuffersFormat[l], renderWidth, renderHeight, 0, GL12.GL_BGRA,
					GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer) null);
			checkGLError("FT ca");
		}

		int i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);

		if (i1 == 36058) {
			printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");

			for (int j = 0; j < usedColorBuffers; ++j) {
				GlStateManager.bindTexture(dfbColorTextures.get(j));
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, renderWidth, renderHeight, 0, GL12.GL_BGRA,
						GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer) null);
				EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTextures.get(j), 0);
				checkGLError("FT c");
			}

			i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);

			if (i1 == 36053) {
				SMCLog.info("complete");
			}
		}

		GlStateManager.bindTexture(0);

		if (i1 != 36053) {
			printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + i1 + ")");
		} else {
			SMCLog.info("Framebuffer created.");
		}
	}

	private static void setupShadowFrameBuffer() {
		if (usedShadowDepthBuffers != 0) {
			if (sfb != 0) {
				EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
				GlStateManager.deleteTextures(sfbDepthTextures);
				GlStateManager.deleteTextures(sfbColorTextures);
			}

			sfb = EXTFramebufferObject.glGenFramebuffersEXT();
			EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
			GL11.glDrawBuffer(0);
			GL11.glReadBuffer(0);
			GL11.glGenTextures((IntBuffer) sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
			GL11.glGenTextures((IntBuffer) sfbColorTextures.clear().limit(usedShadowColorBuffers));
			sfbDepthTextures.position(0);
			sfbColorTextures.position(0);

			for (int i = 0; i < usedShadowDepthBuffers; ++i) {
				GlStateManager.bindTexture(sfbDepthTextures.get(i));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10496.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10496.0F);
				int j = shadowFilterNearest[i] ? 9728 : 9729;
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, j);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, j);

				if (shadowHardwareFilteringEnabled[i]) {
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_MODE,
							GL14.GL_COMPARE_R_TO_TEXTURE);
				}

				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, shadowMapWidth, shadowMapHeight, 0,
						GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (FloatBuffer) null);
			}

			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
			checkGLError("FT sd");

			for (int k = 0; k < usedShadowColorBuffers; ++k) {
				GlStateManager.bindTexture(sfbColorTextures.get(k));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10496.0F);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10496.0F);
				int i1 = shadowColorFilterNearest[k] ? 9728 : 9729;
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, i1);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, i1);
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, shadowMapWidth, shadowMapHeight, 0, GL12.GL_BGRA,
						GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer) null);
				EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, sfbColorTextures.get(k), 0);
				checkGLError("FT sc");
			}

			GlStateManager.bindTexture(0);

			if (usedShadowColorBuffers > 0) {
				GL20.glDrawBuffers(sfbDrawBuffers);
			}

			int l = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);

			if (l != 36053) {
				printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + l + ")");
			} else {
				SMCLog.info("Shadow framebuffer created.");
			}
		}
	}

	public static void beginRender(Minecraft minecraft, float partialTicks, long finishTimeNano) {
		checkGLError("pre beginRender");
		checkWorldChanged(mc.world);
		mc = minecraft;
		mc.mcProfiler.startSection("init");
		entityRenderer = mc.entityRenderer;

		if (!isShaderPackInitialized) {
			try {
				init();
			} catch (IllegalStateException illegalstateexception) {
				if (Config.normalize(illegalstateexception.getMessage()).equals("Function is not supported")) {
					printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
					illegalstateexception.printStackTrace();
					setShaderPack(packNameNone);
					return;
				}
			}
		}

		if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight) {
			resize();
		}

		if (needResizeShadow) {
			resizeShadow();
		}

		worldTime = mc.world.getWorldTime();
		diffWorldTime = (worldTime - lastWorldTime) % 24000L;

		if (diffWorldTime < 0L) {
			diffWorldTime += 24000L;
		}

		lastWorldTime = worldTime;
		moonPhase = mc.world.getMoonPhase();
		++frameCounter;

		if (frameCounter >= 720720) {
			frameCounter = 0;
		}

		systemTime = System.currentTimeMillis();

		if (lastSystemTime == 0L) {
			lastSystemTime = systemTime;
		}

		diffSystemTime = systemTime - lastSystemTime;
		lastSystemTime = systemTime;
		frameTime = (float) diffSystemTime / 1000.0F;
		frameTimeCounter += frameTime;
		frameTimeCounter %= 3600.0F;
		rainStrength = minecraft.world.getRainStrength(partialTicks);
		float f = (float) diffSystemTime * 0.01F;
		float f1 = (float) Math.exp(
				Math.log(0.5D) * (double) f / (double) (wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
		wetness = wetness * f1 + rainStrength * (1.0F - f1);
		Entity entity = mc.getRenderViewEntity();

		if (entity != null) {
			isSleeping = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping();
			eyePosY = (float) entity.posY * partialTicks + (float) entity.lastTickPosY * (1.0F - partialTicks);
			eyeBrightness = entity.getBrightnessForRender();
			f1 = (float) diffSystemTime * 0.01F;
			float f2 = (float) Math.exp(Math.log(0.5D) * (double) f1 / (double) eyeBrightnessHalflife);
			eyeBrightnessFadeX = eyeBrightnessFadeX * f2 + (float) (eyeBrightness & 65535) * (1.0F - f2);
			eyeBrightnessFadeY = eyeBrightnessFadeY * f2 + (float) (eyeBrightness >> 16) * (1.0F - f2);
			isEyeInWater = 0;

			if (mc.gameSettings.thirdPersonView == 0 && !isSleeping) {
				if (entity.isInsideOfMaterial(Material.WATER)) {
					isEyeInWater = 1;
				} else if (entity.isInsideOfMaterial(Material.LAVA)) {
					isEyeInWater = 2;
				}
			}

			if (mc.player != null) {
				nightVision = 0.0F;

				if (mc.player.isPotionActive(MobEffects.NIGHT_VISION)) {
					nightVision = Config.getMinecraft().entityRenderer.getNightVisionBrightness(mc.player,
							partialTicks);
				}

				blindness = 0.0F;

				if (mc.player.isPotionActive(MobEffects.BLINDNESS)) {
					int i = mc.player.getActivePotionEffect(MobEffects.BLINDNESS).getDuration();
					blindness = Config.limit((float) i / 20.0F, 0.0F, 1.0F);
				}
			}

			Vec3d vec3d = mc.world.getSkyColor(entity, partialTicks);
			vec3d = CustomColors.getWorldSkyColor(vec3d, currentWorld, entity, partialTicks);
			skyColorR = (float) vec3d.x;
			skyColorG = (float) vec3d.y;
			skyColorB = (float) vec3d.z;
		}

		isRenderingWorld = true;
		isCompositeRendered = false;
		isHandRenderedMain = false;
		isHandRenderedOff = false;
		skipRenderHandMain = false;
		skipRenderHandOff = false;

		if (usedShadowDepthBuffers >= 1) {
			GlStateManager.setActiveTexture(33988);
			GlStateManager.bindTexture(sfbDepthTextures.get(0));

			if (usedShadowDepthBuffers >= 2) {
				GlStateManager.setActiveTexture(33989);
				GlStateManager.bindTexture(sfbDepthTextures.get(1));
			}
		}

		GlStateManager.setActiveTexture(33984);

		for (int j = 0; j < usedColorBuffers; ++j) {
			GlStateManager.bindTexture(dfbColorTexturesA[j]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GlStateManager.bindTexture(dfbColorTexturesA[8 + j]);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		}

		GlStateManager.bindTexture(0);

		for (int k = 0; k < 4 && 4 + k < usedColorBuffers; ++k) {
			GlStateManager.setActiveTexture(33991 + k);
			GlStateManager.bindTexture(dfbColorTextures.get(4 + k));
		}

		GlStateManager.setActiveTexture(33990);
		GlStateManager.bindTexture(dfbDepthTextures.get(0));

		if (usedDepthBuffers >= 2) {
			GlStateManager.setActiveTexture(33995);
			GlStateManager.bindTexture(dfbDepthTextures.get(1));

			if (usedDepthBuffers >= 3) {
				GlStateManager.setActiveTexture(33996);
				GlStateManager.bindTexture(dfbDepthTextures.get(2));
			}
		}

		for (int l = 0; l < usedShadowColorBuffers; ++l) {
			GlStateManager.setActiveTexture(33997 + l);
			GlStateManager.bindTexture(sfbColorTextures.get(l));
		}

		if (noiseTextureEnabled) {
			GlStateManager.setActiveTexture(33984 + noiseTexture.textureUnit);
			GlStateManager.bindTexture(noiseTexture.getID());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		}

		bindCustomTextures(customTexturesGbuffers);
		GlStateManager.setActiveTexture(33984);
		previousCameraPositionX = cameraPositionX;
		previousCameraPositionY = cameraPositionY;
		previousCameraPositionZ = cameraPositionZ;
		previousProjection.position(0);
		projection.position(0);
		previousProjection.put(projection);
		previousProjection.position(0);
		projection.position(0);
		previousModelView.position(0);
		modelView.position(0);
		previousModelView.put(modelView);
		previousModelView.position(0);
		modelView.position(0);
		checkGLError("beginRender");
		ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
		mc.mcProfiler.endSection();
		EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);

		for (int i1 = 0; i1 < usedColorBuffers; ++i1) {
			colorTexturesToggle[i1] = 0;
			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i1, 3553, dfbColorTexturesA[i1], 0);
		}

		checkGLError("end beginRender");
	}

	private static void checkWorldChanged(World worldd) {
		if (currentWorld != worldd) {
			World world = currentWorld;
			currentWorld = worldd;

			if (world != null && worldd != null) {
				int i = world.provider.getDimensionType().getId();
				int j = worldd.provider.getDimensionType().getId();
				boolean flag = shaderPackDimensions.contains(Integer.valueOf(i));
				boolean flag1 = shaderPackDimensions.contains(Integer.valueOf(j));

				if (flag || flag1) {
					uninit();
				}
			}
		}
	}

	public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
		if (!isShadowPass) {
			EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
			GL11.glViewport(0, 0, renderWidth, renderHeight);
			activeDrawBuffers = null;
			ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
			useProgram(2);
			checkGLError("end beginRenderPass");
		}
	}

	public static void setViewport(int vx, int vy, int vw, int vh) {
		GlStateManager.colorMask(true, true, true, true);

		if (isShadowPass) {
			GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
		} else {
			GL11.glViewport(0, 0, renderWidth, renderHeight);
			EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
			isRenderingDfb = true;
			GlStateManager.enableCull();
			GlStateManager.enableDepth();
			setDrawBuffers(drawBuffersNone);
			useProgram(2);
			checkGLError("beginRenderPass");
		}
	}

	public static int setFogMode(int val) {
		fogMode = val;
		return val;
	}

	public static void setFogColor(float r, float g, float b) {
		fogColorR = r;
		fogColorG = g;
		fogColorB = b;
	}

	public static void setClearColor(float red, float green, float blue, float alpha) {
		GlStateManager.clearColor(red, green, blue, alpha);
		clearColorR = red;
		clearColorG = green;
		clearColorB = blue;
	}

	public static void clearRenderBuffer() {
		if (isShadowPass) {
			checkGLError("shadow clear pre");
			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
			GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
			GL20.glDrawBuffers(programsDrawBuffers[30]);
			checkFramebufferStatus("shadow clear");
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			checkGLError("shadow clear");
		} else {
			checkGLError("clear pre");

			if (gbuffersClear[0]) {
				GL20.glDrawBuffers(36064);
				GL11.glClear(16384);
			}

			if (gbuffersClear[1]) {
				GL20.glDrawBuffers(36065);
				GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glClear(16384);
			}

			for (int i = 2; i < usedColorBuffers; ++i) {
				if (gbuffersClear[i]) {
					GL20.glDrawBuffers(36064 + i);
					GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
					GL11.glClear(16384);
				}
			}

			setDrawBuffers(dfbDrawBuffers);
			checkFramebufferStatus("clear");
			checkGLError("clear");
		}
	}

	public static void setCamera(float partialTicks) {
		Entity entity = mc.getRenderViewEntity();
		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		cameraPositionX = d0;
		cameraPositionY = d1;
		cameraPositionZ = d2;
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, (FloatBuffer) projection.position(0));
		SMath.invertMat4FBFA((FloatBuffer) projectionInverse.position(0), (FloatBuffer) projection.position(0),
				faProjectionInverse, faProjection);
		projection.position(0);
		projectionInverse.position(0);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, (FloatBuffer) modelView.position(0));
		SMath.invertMat4FBFA((FloatBuffer) modelViewInverse.position(0), (FloatBuffer) modelView.position(0),
				faModelViewInverse, faModelView);
		modelView.position(0);
		modelViewInverse.position(0);
		checkGLError("setCamera");
	}

	public static void setCameraShadow(float partialTicks) {
		Entity entity = mc.getRenderViewEntity();
		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		cameraPositionX = d0;
		cameraPositionY = d1;
		cameraPositionZ = d2;
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, (FloatBuffer) projection.position(0));
		SMath.invertMat4FBFA((FloatBuffer) projectionInverse.position(0), (FloatBuffer) projection.position(0),
				faProjectionInverse, faProjection);
		projection.position(0);
		projectionInverse.position(0);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, (FloatBuffer) modelView.position(0));
		SMath.invertMat4FBFA((FloatBuffer) modelViewInverse.position(0), (FloatBuffer) modelView.position(0),
				faModelViewInverse, faModelView);
		modelView.position(0);
		modelViewInverse.position(0);
		GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		if (shadowMapIsOrtho) {
			GL11.glOrtho((double) (-shadowMapHalfPlane), (double) shadowMapHalfPlane, (double) (-shadowMapHalfPlane),
					(double) shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
		} else {
			GLU.gluPerspective(shadowMapFOV, (float) shadowMapWidth / (float) shadowMapHeight, 0.05F, 256.0F);
		}

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -100.0F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		celestialAngle = mc.world.getCelestialAngle(partialTicks);
		sunAngle = celestialAngle < 0.75F ? celestialAngle + 0.25F : celestialAngle - 0.75F;
		float f = celestialAngle * -360.0F;
		float f1 = shadowAngleInterval > 0.0F ? f % shadowAngleInterval - shadowAngleInterval * 0.5F : 0.0F;

		if ((double) sunAngle <= 0.5D) {
			GL11.glRotatef(f - f1, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
			shadowAngle = sunAngle;
		} else {
			GL11.glRotatef(f + 180.0F - f1, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
			shadowAngle = sunAngle - 0.5F;
		}

		if (shadowMapIsOrtho) {
			float f2 = shadowIntervalSize;
			float f3 = f2 / 2.0F;
			GL11.glTranslatef((float) d0 % f2 - f3, (float) d1 % f2 - f3, (float) d2 % f2 - f3);
		}

		float f9 = sunAngle * ((float) Math.PI * 2F);
		float f10 = (float) Math.cos((double) f9);
		float f4 = (float) Math.sin((double) f9);
		float f5 = sunPathRotation * ((float) Math.PI * 2F);
		float f6 = f10;
		float f7 = f4 * (float) Math.cos((double) f5);
		float f8 = f4 * (float) Math.sin((double) f5);

		if ((double) sunAngle > 0.5D) {
			f6 = -f10;
			f7 = -f7;
			f8 = -f8;
		}

		shadowLightPositionVector[0] = f6;
		shadowLightPositionVector[1] = f7;
		shadowLightPositionVector[2] = f8;
		shadowLightPositionVector[3] = 0.0F;
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, (FloatBuffer) shadowProjection.position(0));
		SMath.invertMat4FBFA((FloatBuffer) shadowProjectionInverse.position(0),
				(FloatBuffer) shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
		shadowProjection.position(0);
		shadowProjectionInverse.position(0);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, (FloatBuffer) shadowModelView.position(0));
		SMath.invertMat4FBFA((FloatBuffer) shadowModelViewInverse.position(0),
				(FloatBuffer) shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
		shadowModelView.position(0);
		shadowModelViewInverse.position(0);
		setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
		setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
		setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
		setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
		setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
		setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
		setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
		setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
		setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
		setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
		mc.gameSettings.thirdPersonView = 1;
		checkGLError("setCamera");
	}

	public static void preCelestialRotate() {
		GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
		checkGLError("preCelestialRotate");
	}

	public static void postCelestialRotate() {
		FloatBuffer floatbuffer = tempMatrixDirectBuffer;
		floatbuffer.clear();
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, floatbuffer);
		floatbuffer.get(tempMat, 0, 16);
		SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
		SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
		System.arraycopy(shadowAngle == sunAngle ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
		setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
		setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
		setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1],
				shadowLightPosition[2]);
		checkGLError("postCelestialRotate");
	}

	public static void setUpPosition() {
		FloatBuffer floatbuffer = tempMatrixDirectBuffer;
		floatbuffer.clear();
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, floatbuffer);
		floatbuffer.get(tempMat, 0, 16);
		SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
		setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
	}

	public static void genCompositeMipmap() {
		if (hasGlGenMipmap) {
			for (int i = 0; i < usedColorBuffers; ++i) {
				if ((activeCompositeMipmapSetting & 1 << i) != 0) {
					GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
					GL30.glGenerateMipmap(3553);
				}
			}

			GlStateManager.setActiveTexture(33984);
		}
	}

	public static void drawComposite() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0F, 0.0F);
		GL11.glVertex3f(0.0F, 0.0F, 0.0F);
		GL11.glTexCoord2f(1.0F, 0.0F);
		GL11.glVertex3f(1.0F, 0.0F, 0.0F);
		GL11.glTexCoord2f(1.0F, 1.0F);
		GL11.glVertex3f(1.0F, 1.0F, 0.0F);
		GL11.glTexCoord2f(0.0F, 1.0F);
		GL11.glVertex3f(0.0F, 1.0F, 0.0F);
		GL11.glEnd();
	}

	public static void renderCompositeFinal() {
		if (!isShadowPass) {
			checkGLError("pre-renderCompositeFinal");
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture2D();
			GlStateManager.disableAlpha();
			GlStateManager.disableBlend();
			GlStateManager.enableDepth();
			GlStateManager.depthFunc(519);
			GlStateManager.depthMask(false);
			GlStateManager.disableLighting();

			if (usedShadowDepthBuffers >= 1) {
				GlStateManager.setActiveTexture(33988);
				GlStateManager.bindTexture(sfbDepthTextures.get(0));

				if (usedShadowDepthBuffers >= 2) {
					GlStateManager.setActiveTexture(33989);
					GlStateManager.bindTexture(sfbDepthTextures.get(1));
				}
			}

			for (int i = 0; i < usedColorBuffers; ++i) {
				GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
				GlStateManager.bindTexture(dfbColorTexturesA[i]);
			}

			GlStateManager.setActiveTexture(33990);
			GlStateManager.bindTexture(dfbDepthTextures.get(0));

			if (usedDepthBuffers >= 2) {
				GlStateManager.setActiveTexture(33995);
				GlStateManager.bindTexture(dfbDepthTextures.get(1));

				if (usedDepthBuffers >= 3) {
					GlStateManager.setActiveTexture(33996);
					GlStateManager.bindTexture(dfbDepthTextures.get(2));
				}
			}

			for (int j1 = 0; j1 < usedShadowColorBuffers; ++j1) {
				GlStateManager.setActiveTexture(33997 + j1);
				GlStateManager.bindTexture(sfbColorTextures.get(j1));
			}

			if (noiseTextureEnabled) {
				GlStateManager.setActiveTexture(33984 + noiseTexture.textureUnit);
				GlStateManager.bindTexture(noiseTexture.getID());
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			}

			bindCustomTextures(customTexturesComposite);
			GlStateManager.setActiveTexture(33984);
			boolean flag = true;

			for (int j = 0; j < usedColorBuffers; ++j) {
				EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesA[8 + j], 0);
			}

			EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
			GL20.glDrawBuffers(dfbDrawBuffers);
			checkGLError("pre-composite");

			for (int k1 = 0; k1 < 8; ++k1) {
				if (programsID[21 + k1] != 0) {
					useProgram(21 + k1);
					checkGLError(programNames[21 + k1]);

					if (activeCompositeMipmapSetting != 0) {
						genCompositeMipmap();
					}

					drawComposite();

					for (int k = 0; k < usedColorBuffers; ++k) {
						if (programsToggleColorTextures[21 + k1][k]) {
							int l = colorTexturesToggle[k];
							int i1 = colorTexturesToggle[k] = 8 - l;
							GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[k]);
							GlStateManager.bindTexture(dfbColorTexturesA[i1 + k]);
							EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553,
									dfbColorTexturesA[l + k], 0);
						}
					}

					GlStateManager.setActiveTexture(33984);
				}
			}

			checkGLError("composite");
			isRenderingDfb = false;
			mc.getFramebuffer().bindFramebuffer(true);
			OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553,
					mc.getFramebuffer().framebufferTexture, 0);
			GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);

			if (EntityRenderer.anaglyphEnable) {
				boolean flag1 = EntityRenderer.anaglyphField != 0;
				GlStateManager.colorMask(flag1, !flag1, !flag1, true);
			}

			GlStateManager.depthMask(true);
			GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture2D();
			GlStateManager.disableAlpha();
			GlStateManager.disableBlend();
			GlStateManager.enableDepth();
			GlStateManager.depthFunc(519);
			GlStateManager.depthMask(false);
			checkGLError("pre-final");
			useProgram(29);
			checkGLError("final");

			if (activeCompositeMipmapSetting != 0) {
				genCompositeMipmap();
			}

			drawComposite();
			checkGLError("renderCompositeFinal");
			isCompositeRendered = true;
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.depthFunc(515);
			GlStateManager.depthMask(true);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPopMatrix();
			useProgram(0);
		}
	}

	public static void endRender() {
		if (isShadowPass) {
			checkGLError("shadow endRender");
		} else {
			if (!isCompositeRendered) {
				renderCompositeFinal();
			}

			isRenderingWorld = false;
			GlStateManager.colorMask(true, true, true, true);
			useProgram(0);
			RenderHelper.disableStandardItemLighting();
			checkGLError("endRender end");
		}
	}

	public static void beginSky() {
		isRenderingSky = true;
		fogEnabled = true;
		setDrawBuffers(dfbDrawBuffers);
		useProgram(5);
		pushEntity(-2, 0);
	}

	public static void setSkyColor(Vec3d v3color) {
		skyColorR = (float) v3color.x;
		skyColorG = (float) v3color.y;
		skyColorB = (float) v3color.z;
		setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
	}

	public static void drawHorizon() {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		float f = (float) (mc.gameSettings.renderDistanceChunks * 16);
		double d0 = (double) f * 0.9238D;
		double d1 = (double) f * 0.3826D;
		double d2 = -d1;
		double d3 = -d0;
		double d4 = 16.0D;
		double d5 = -cameraPositionY;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(d2, d5, d3).endVertex();
		bufferbuilder.pos(d2, d4, d3).endVertex();
		bufferbuilder.pos(d3, d4, d2).endVertex();
		bufferbuilder.pos(d3, d5, d2).endVertex();
		bufferbuilder.pos(d3, d5, d2).endVertex();
		bufferbuilder.pos(d3, d4, d2).endVertex();
		bufferbuilder.pos(d3, d4, d1).endVertex();
		bufferbuilder.pos(d3, d5, d1).endVertex();
		bufferbuilder.pos(d3, d5, d1).endVertex();
		bufferbuilder.pos(d3, d4, d1).endVertex();
		bufferbuilder.pos(d2, d4, d1).endVertex();
		bufferbuilder.pos(d2, d5, d1).endVertex();
		bufferbuilder.pos(d2, d5, d1).endVertex();
		bufferbuilder.pos(d2, d4, d1).endVertex();
		bufferbuilder.pos(d1, d4, d0).endVertex();
		bufferbuilder.pos(d1, d5, d0).endVertex();
		bufferbuilder.pos(d1, d5, d0).endVertex();
		bufferbuilder.pos(d1, d4, d0).endVertex();
		bufferbuilder.pos(d0, d4, d1).endVertex();
		bufferbuilder.pos(d0, d5, d1).endVertex();
		bufferbuilder.pos(d0, d5, d1).endVertex();
		bufferbuilder.pos(d0, d4, d1).endVertex();
		bufferbuilder.pos(d0, d4, d2).endVertex();
		bufferbuilder.pos(d0, d5, d2).endVertex();
		bufferbuilder.pos(d0, d5, d2).endVertex();
		bufferbuilder.pos(d0, d4, d2).endVertex();
		bufferbuilder.pos(d1, d4, d3).endVertex();
		bufferbuilder.pos(d1, d5, d3).endVertex();
		bufferbuilder.pos(d1, d5, d3).endVertex();
		bufferbuilder.pos(d1, d4, d3).endVertex();
		bufferbuilder.pos(d2, d4, d3).endVertex();
		bufferbuilder.pos(d2, d5, d3).endVertex();
		Tessellator.getInstance().draw();
	}

	public static void preSkyList() {
		setUpPosition();
		GL11.glColor3f(fogColorR, fogColorG, fogColorB);
		drawHorizon();
		GL11.glColor3f(skyColorR, skyColorG, skyColorB);
	}

	public static void endSky() {
		isRenderingSky = false;
		setDrawBuffers(dfbDrawBuffers);
		useProgram(lightmapEnabled ? 3 : 2);
		popEntity();
	}

	public static void beginUpdateChunks() {
		checkGLError("beginUpdateChunks1");
		checkFramebufferStatus("beginUpdateChunks1");

		if (!isShadowPass) {
			useProgram(7);
		}

		checkGLError("beginUpdateChunks2");
		checkFramebufferStatus("beginUpdateChunks2");
	}

	public static void endUpdateChunks() {
		checkGLError("endUpdateChunks1");
		checkFramebufferStatus("endUpdateChunks1");

		if (!isShadowPass) {
			useProgram(7);
		}

		checkGLError("endUpdateChunks2");
		checkFramebufferStatus("endUpdateChunks2");
	}

	public static boolean shouldRenderClouds(GameSettings gs) {
		if (!shaderPackLoaded) {
			return true;
		} else {
			checkGLError("shouldRenderClouds");
			return isShadowPass ? configCloudShadow : gs.clouds > 0;
		}
	}

	public static void beginClouds() {
		fogEnabled = true;
		pushEntity(-3, 0);
		useProgram(6);
	}

	public static void endClouds() {
		disableFog();
		popEntity();
		useProgram(lightmapEnabled ? 3 : 2);
	}

	public static void beginEntities() {
		if (isRenderingWorld) {
			useProgram(16);
			resetDisplayListModels();
		}
	}

	public static void nextEntity(Entity entity) {
		if (isRenderingWorld) {
			useProgram(16);
			setEntityId(entity);
		}
	}

	public static void setEntityId(Entity entity) {
		if (isRenderingWorld && !isShadowPass && uniformEntityId.isDefined()) {
			int i = EntityUtils.getEntityIdByClass(entity);
			uniformEntityId.setValue(i);
		}
	}

	public static void beginSpiderEyes() {
		if (isRenderingWorld && programsID[18] != programsID[0]) {
			useProgram(18);
			GlStateManager.enableAlpha();
			GlStateManager.alphaFunc(516, 0.0F);
			GlStateManager.blendFunc(770, 771);
		}
	}

	public static void endSpiderEyes() {
		if (isRenderingWorld && programsID[18] != programsID[0]) {
			useProgram(16);
			GlStateManager.disableAlpha();
		}
	}

	public static void endEntities() {
		if (isRenderingWorld) {
			useProgram(lightmapEnabled ? 3 : 2);
		}
	}

	public static void setEntityColor(float r, float g, float b, float a) {
		if (isRenderingWorld && !isShadowPass) {
			uniformEntityColor.setValue(r, g, b, a);
		}
	}

	public static void beginLivingDamage() {
		if (isRenderingWorld) {
			ShadersTex.bindTexture(defaultTexture);

			if (!isShadowPass) {
				setDrawBuffers(drawBuffersColorAtt0);
			}
		}
	}

	public static void endLivingDamage() {
		if (isRenderingWorld && !isShadowPass) {
			setDrawBuffers(programsDrawBuffers[16]);
		}
	}

	public static void beginBlockEntities() {
		if (isRenderingWorld) {
			checkGLError("beginBlockEntities");
			useProgram(13);
		}
	}

	public static void nextBlockEntity(TileEntity tileEntity) {
		if (isRenderingWorld) {
			checkGLError("nextBlockEntity");
			useProgram(13);
			setBlockEntityId(tileEntity);
		}
	}

	public static void setBlockEntityId(TileEntity tileEntity) {
		if (isRenderingWorld && !isShadowPass && uniformBlockEntityId.isDefined()) {
			Block block = tileEntity.getBlockType();
			int i = Block.getIdFromBlock(block);
			uniformBlockEntityId.setValue(i);
		}
	}

	public static void endBlockEntities() {
		if (isRenderingWorld) {
			checkGLError("endBlockEntities");
			useProgram(lightmapEnabled ? 3 : 2);
			ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
		}
	}

	public static void beginLitParticles() {
		useProgram(3);
	}

	public static void beginParticles() {
		useProgram(2);
	}

	public static void endParticles() {
		useProgram(3);
	}

	public static void readCenterDepth() {
		if (!isShadowPass && centerDepthSmoothEnabled) {
			tempDirectFloatBuffer.clear();
			GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT,
					tempDirectFloatBuffer);
			centerDepth = tempDirectFloatBuffer.get(0);
			float f = (float) diffSystemTime * 0.01F;
			float f1 = (float) Math.exp(Math.log(0.5D) * (double) f / (double) centerDepthSmoothHalflife);
			centerDepthSmooth = centerDepthSmooth * f1 + centerDepth * (1.0F - f1);
		}
	}

	public static void beginWeather() {
		if (!isShadowPass) {
			if (usedDepthBuffers >= 3) {
				GlStateManager.setActiveTexture(33996);
				GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, renderWidth, renderHeight);
				GlStateManager.setActiveTexture(33984);
			}

			GlStateManager.enableDepth();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 771);
			GlStateManager.enableAlpha();
			useProgram(20);
		}
	}

	public static void endWeather() {
		GlStateManager.disableBlend();
		useProgram(3);
	}

	public static void preWater() {
		if (usedDepthBuffers >= 2) {
			GlStateManager.setActiveTexture(33995);
			checkGLError("pre copy depth");
			GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, renderWidth, renderHeight);
			checkGLError("copy depth");
			GlStateManager.setActiveTexture(33984);
		}

		ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
	}

	public static void beginWater() {
		if (isRenderingWorld) {
			if (!isShadowPass) {
				useProgram(12);
				GlStateManager.enableBlend();
				GlStateManager.depthMask(true);
			} else {
				GlStateManager.depthMask(true);
			}
		}
	}

	public static void endWater() {
		if (isRenderingWorld) {
			if (isShadowPass) {
				;
			}

			useProgram(lightmapEnabled ? 3 : 2);
		}
	}

	public static void beginProjectRedHalo() {
		if (isRenderingWorld) {
			useProgram(1);
		}
	}

	public static void endProjectRedHalo() {
		if (isRenderingWorld) {
			useProgram(3);
		}
	}

	public static void applyHandDepth() {
		if ((double) configHandDepthMul != 1.0D) {
			GL11.glScaled(1.0D, 1.0D, (double) configHandDepthMul);
		}
	}

	public static void beginHand() {
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		useProgram(19);
		checkGLError("beginHand");
		checkFramebufferStatus("beginHand");
	}

	public static void endHand() {
		checkGLError("pre endHand");
		checkFramebufferStatus("pre endHand");
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GlStateManager.blendFunc(770, 771);
		checkGLError("endHand");
	}

	public static void beginFPOverlay() {
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
	}

	public static void endFPOverlay() {
	}

	public static void glEnableWrapper(int cap) {
		GL11.glEnable(cap);

		if (cap == 3553) {
			enableTexture2D();
		} else if (cap == 2912) {
			enableFog();
		}
	}

	public static void glDisableWrapper(int cap) {
		GL11.glDisable(cap);

		if (cap == 3553) {
			disableTexture2D();
		} else if (cap == 2912) {
			disableFog();
		}
	}

	public static void sglEnableT2D(int cap) {
		GL11.glEnable(cap);
		enableTexture2D();
	}

	public static void sglDisableT2D(int cap) {
		GL11.glDisable(cap);
		disableTexture2D();
	}

	public static void sglEnableFog(int cap) {
		GL11.glEnable(cap);
		enableFog();
	}

	public static void sglDisableFog(int cap) {
		GL11.glDisable(cap);
		disableFog();
	}

	public static void enableTexture2D() {
		if (isRenderingSky) {
			useProgram(5);
		} else if (activeProgram == 1) {
			useProgram(lightmapEnabled ? 3 : 2);
		}
	}

	public static void disableTexture2D() {
		if (isRenderingSky) {
			useProgram(4);
		} else if (activeProgram == 2 || activeProgram == 3) {
			useProgram(1);
		}
	}

	public static void beginLeash() {
		useProgram(1);
	}

	public static void endLeash() {
		useProgram(16);
	}

	public static void enableFog() {
		fogEnabled = true;
		setProgramUniform1i("fogMode", fogMode);
	}

	public static void disableFog() {
		fogEnabled = false;
		setProgramUniform1i("fogMode", 0);
	}

	public static void setFog(GlStateManager.FogMode fogModef) {
		GlStateManager.setFog(fogModef);
		fogMode = fogModef.capabilityId;

		if (fogEnabled) {
			setProgramUniform1i("fogMode", fogModef.capabilityId);
		}
	}

	public static void sglFogi(int pname, int param) {
		GL11.glFogi(pname, param);

		if (pname == 2917) {
			fogMode = param;

			if (fogEnabled) {
				setProgramUniform1i("fogMode", fogMode);
			}
		}
	}

	public static void enableLightmap() {
		lightmapEnabled = true;

		if (activeProgram == 2) {
			useProgram(3);
		}
	}

	public static void disableLightmap() {
		lightmapEnabled = false;

		if (activeProgram == 3) {
			useProgram(2);
		}
	}

	public static int getEntityData() {
		return entityData[entityDataIndex * 2];
	}

	public static int getEntityData2() {
		return entityData[entityDataIndex * 2 + 1];
	}

	public static int setEntityData1(int data1) {
		entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 65535 | data1 << 16;
		return data1;
	}

	public static int setEntityData2(int data2) {
		entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & -65536 | data2 & 65535;
		return data2;
	}

	public static void pushEntity(int data0, int data1) {
		++entityDataIndex;
		entityData[entityDataIndex * 2] = data0 & 65535 | data1 << 16;
		entityData[entityDataIndex * 2 + 1] = 0;
	}

	public static void pushEntity(int data0) {
		++entityDataIndex;
		entityData[entityDataIndex * 2] = data0 & 65535;
		entityData[entityDataIndex * 2 + 1] = 0;
	}

	public static void pushEntity(Block block) {
		++entityDataIndex;
		int i = block.getRenderType(block.getDefaultState()).ordinal();
		entityData[entityDataIndex * 2] = Block.REGISTRY.getIDForObject(block) & 65535 | i << 16;
		entityData[entityDataIndex * 2 + 1] = 0;
	}

	public static void popEntity() {
		entityData[entityDataIndex * 2] = 0;
		entityData[entityDataIndex * 2 + 1] = 0;
		--entityDataIndex;
	}

	public static void mcProfilerEndSection() {
		mc.mcProfiler.endSection();
	}

	public static String getShaderPackName() {
		if (shaderPack == null) {
			return null;
		} else {
			return shaderPack instanceof ShaderPackNone ? null : shaderPack.getName();
		}
	}

	public static InputStream getShaderPackResourceStream(String path) {
		return shaderPack == null ? null : shaderPack.getResourceAsStream(path);
	}

	public static void nextAntialiasingLevel() {
		configAntialiasingLevel += 2;
		configAntialiasingLevel = configAntialiasingLevel / 2 * 2;

		if (configAntialiasingLevel > 4) {
			configAntialiasingLevel = 0;
		}

		configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
	}

	public static void checkShadersModInstalled() {
		try {
			Class e = Class.forName("shadersmod.transform.SMCClassTransformer");
		} catch (Throwable var1) {
			return;
		}

		throw new RuntimeException(
				"Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
	}

	public static void resourcesReloaded() {
		loadShaderPackResources();
	}

	private static void loadShaderPackResources() {
		shaderPackResources = new HashMap<String, String>();

		if (shaderPackLoaded) {
			List<String> list = new ArrayList<String>();
			String s = "/shaders/lang/";
			String s1 = "en_US";
			String s2 = ".lang";
			list.add(s + s1 + s2);

			if (!Config.getGameSettings().language.equals(s1)) {
				list.add(s + Config.getGameSettings().language + s2);
			}

			try {
				for (String s3 : list) {
					InputStream inputstream = shaderPack.getResourceAsStream(s3);

					if (inputstream != null) {
						Properties properties = new Properties();
						Lang.loadLocaleData(inputstream, properties);
						inputstream.close();

						for (Object s4 : properties.keySet()) {
							String s5 = properties.getProperty((String) s4);
							shaderPackResources.put((String) s4, s5);
						}
					}
				}
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
	}

	public static String translate(String key, String def) {
		String s = shaderPackResources.get(key);
		return s == null ? def : s;
	}

	public static boolean isProgramPath(String program) {
		if (program == null) {
			return false;
		} else if (program.length() <= 0) {
			return false;
		} else {
			int i = program.lastIndexOf("/");

			if (i >= 0) {
				program = program.substring(i + 1);
			}

			return Arrays.asList(programNames).contains(program);
		}
	}

	public static void setItemToRenderMain(ItemStack itemToRenderMain) {
		itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
	}

	public static void setItemToRenderOff(ItemStack itemToRenderOff) {
		itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
	}

	public static boolean isItemToRenderMainTranslucent() {
		return itemToRenderMainTranslucent;
	}

	public static boolean isItemToRenderOffTranslucent() {
		return itemToRenderOffTranslucent;
	}

	public static boolean isBothHandsRendered() {
		return isHandRenderedMain && isHandRenderedOff;
	}

	private static boolean isTranslucentBlock(ItemStack stack) {
		if (stack == null) {
			return false;
		} else {
			Item item = stack.getItem();

			if (item == null) {
				return false;
			} else if (!(item instanceof ItemBlock)) {
				return false;
			} else {
				ItemBlock itemblock = (ItemBlock) item;
				Block block = itemblock.getBlock();

				if (block == null) {
					return false;
				} else {
					BlockRenderLayer blockrenderlayer = block.getBlockLayer();
					return blockrenderlayer == BlockRenderLayer.TRANSLUCENT;
				}
			}
		}
	}

	public static boolean isSkipRenderHand(EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND && skipRenderHandMain) {
			return true;
		} else {
			return hand == EnumHand.OFF_HAND && skipRenderHandOff;
		}
	}

	public static boolean isRenderBothHands() {
		return !skipRenderHandMain && !skipRenderHandOff;
	}

	public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
		skipRenderHandMain = skipMain;
		skipRenderHandOff = skipOff;
	}

	public static void setHandsRendered(boolean handMain, boolean handOff) {
		isHandRenderedMain = handMain;
		isHandRenderedOff = handOff;
	}

	public static boolean isHandRenderedMain() {
		return isHandRenderedMain;
	}

	public static boolean isHandRenderedOff() {
		return isHandRenderedOff;
	}

	public static float getShadowRenderDistance() {
		return shadowDistanceRenderMul < 0.0F ? -1.0F : shadowMapHalfPlane * shadowDistanceRenderMul;
	}

	public static void setRenderingFirstPersonHand(boolean flag) {
		isRenderingFirstPersonHand = flag;
	}

	public static boolean isRenderingFirstPersonHand() {
		return isRenderingFirstPersonHand;
	}

	static {
		shadersdir = new File(Minecraft.getMinecraft().mcDataDir, "shaders");
		shaderpacksdir = new File(Minecraft.getMinecraft().mcDataDir, shaderpacksdirname);
		configFile = new File(Minecraft.getMinecraft().mcDataDir, optionsfilename);
		drawBuffersNone.limit(0);
		drawBuffersColorAtt0.put(36064).position(0).limit(1);
	}
}
