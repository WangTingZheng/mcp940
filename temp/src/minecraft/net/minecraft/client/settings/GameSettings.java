package net.minecraft.client.settings;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GameSettings {
   private static final Logger field_151454_ax = LogManager.getLogger();
   private static final Gson field_151450_ay = new Gson();
   private static final Type field_151449_az = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   public static final Splitter field_189990_a = Splitter.on(':');
   private static final String[] field_74367_ae = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
   private static final String[] field_74364_ag = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] field_98303_au = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   private static final String[] field_181149_aW = new String[]{"options.off", "options.clouds.fast", "options.clouds.fancy"};
   private static final String[] field_186713_aK = new String[]{"options.off", "options.attack.crosshair", "options.attack.hotbar"};
   public static final String[] field_193632_b = new String[]{"options.narrator.off", "options.narrator.all", "options.narrator.chat", "options.narrator.system"};
   public float field_74341_c = 0.5F;
   public boolean field_74338_d;
   public int field_151451_c = -1;
   public boolean field_74336_f = true;
   public boolean field_74337_g;
   public boolean field_151448_g = true;
   public int field_74350_i = 120;
   public int field_74345_l = 2;
   public boolean field_74347_j = true;
   public int field_74348_k = 2;
   public List<String> field_151453_l = Lists.<String>newArrayList();
   public List<String> field_183018_l = Lists.<String>newArrayList();
   public EntityPlayer.EnumChatVisibility field_74343_n = EntityPlayer.EnumChatVisibility.FULL;
   public boolean field_74344_o = true;
   public boolean field_74359_p = true;
   public boolean field_74358_q = true;
   public float field_74357_r = 1.0F;
   public boolean field_74355_t = true;
   public boolean field_74353_u;
   public boolean field_74352_v = true;
   public boolean field_178881_t = true;
   public boolean field_178879_v;
   public boolean field_80005_w;
   public boolean field_82882_x;
   public boolean field_82881_y = true;
   private final Set<EnumPlayerModelParts> field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
   public boolean field_85185_A;
   public EnumHandSide field_186715_A = EnumHandSide.RIGHT;
   public int field_92118_B;
   public int field_92119_C;
   public boolean field_92117_D = true;
   public float field_96691_E = 1.0F;
   public float field_96692_F = 1.0F;
   public float field_96693_G = 0.44366196F;
   public float field_96694_H = 1.0F;
   public int field_151442_I = 4;
   private final Map<SoundCategory, Float> field_186714_aM = Maps.newEnumMap(SoundCategory.class);
   public boolean field_181150_U = true;
   public boolean field_181151_V = true;
   public int field_186716_M = 1;
   public boolean field_189422_N;
   public boolean field_186717_N;
   public boolean field_183509_X = true;
   public boolean field_189989_R = true;
   public TutorialSteps field_193631_S = TutorialSteps.MOVEMENT;
   public KeyBinding field_74351_w = new KeyBinding("key.forward", 17, "key.categories.movement");
   public KeyBinding field_74370_x = new KeyBinding("key.left", 30, "key.categories.movement");
   public KeyBinding field_74368_y = new KeyBinding("key.back", 31, "key.categories.movement");
   public KeyBinding field_74366_z = new KeyBinding("key.right", 32, "key.categories.movement");
   public KeyBinding field_74314_A = new KeyBinding("key.jump", 57, "key.categories.movement");
   public KeyBinding field_74311_E = new KeyBinding("key.sneak", 42, "key.categories.movement");
   public KeyBinding field_151444_V = new KeyBinding("key.sprint", 29, "key.categories.movement");
   public KeyBinding field_151445_Q = new KeyBinding("key.inventory", 18, "key.categories.inventory");
   public KeyBinding field_186718_X = new KeyBinding("key.swapHands", 33, "key.categories.inventory");
   public KeyBinding field_74316_C = new KeyBinding("key.drop", 16, "key.categories.inventory");
   public KeyBinding field_74313_G = new KeyBinding("key.use", -99, "key.categories.gameplay");
   public KeyBinding field_74312_F = new KeyBinding("key.attack", -100, "key.categories.gameplay");
   public KeyBinding field_74322_I = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
   public KeyBinding field_74310_D = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
   public KeyBinding field_74321_H = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
   public KeyBinding field_74323_J = new KeyBinding("key.command", 53, "key.categories.multiplayer");
   public KeyBinding field_151447_Z = new KeyBinding("key.screenshot", 60, "key.categories.misc");
   public KeyBinding field_151457_aa = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
   public KeyBinding field_151458_ab = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
   public KeyBinding field_152395_am = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
   public KeyBinding field_178883_an = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
   public KeyBinding field_194146_ao = new KeyBinding("key.advancements", 38, "key.categories.misc");
   public KeyBinding[] field_151456_ac = new KeyBinding[]{new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
   public KeyBinding field_193629_ap = new KeyBinding("key.saveToolbarActivator", 46, "key.categories.creative");
   public KeyBinding field_193630_aq = new KeyBinding("key.loadToolbarActivator", 45, "key.categories.creative");
   public KeyBinding[] field_74324_K;
   protected Minecraft field_74317_L;
   private File field_74354_ai;
   public EnumDifficulty field_74318_M;
   public boolean field_74319_N;
   public int field_74320_O;
   public boolean field_74330_P;
   public boolean field_74329_Q;
   public boolean field_181657_aC;
   public String field_74332_R;
   public boolean field_74326_T;
   public boolean field_74325_U;
   public float field_74334_X;
   public float field_74333_Y;
   public float field_151452_as;
   public int field_74335_Z;
   public int field_74362_aa;
   public int field_192571_R;
   public String field_74363_ab;
   public boolean field_151455_aw;

   public GameSettings(Minecraft p_i46326_1_, File p_i46326_2_) {
      this.field_74324_K = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.field_74312_F, this.field_74313_G, this.field_74351_w, this.field_74370_x, this.field_74368_y, this.field_74366_z, this.field_74314_A, this.field_74311_E, this.field_151444_V, this.field_74316_C, this.field_151445_Q, this.field_74310_D, this.field_74321_H, this.field_74322_I, this.field_74323_J, this.field_151447_Z, this.field_151457_aa, this.field_151458_ab, this.field_152395_am, this.field_178883_an, this.field_186718_X, this.field_193629_ap, this.field_193630_aq, this.field_194146_ao}, this.field_151456_ac);
      this.field_74318_M = EnumDifficulty.NORMAL;
      this.field_74332_R = "";
      this.field_74334_X = 70.0F;
      this.field_74363_ab = "en_us";
      this.field_74317_L = p_i46326_1_;
      this.field_74354_ai = new File(p_i46326_2_, "options.txt");
      if (p_i46326_1_.func_147111_S() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         GameSettings.Options.RENDER_DISTANCE.func_148263_a(32.0F);
      } else {
         GameSettings.Options.RENDER_DISTANCE.func_148263_a(16.0F);
      }

      this.field_151451_c = p_i46326_1_.func_147111_S() ? 12 : 8;
      this.func_74300_a();
   }

   public GameSettings() {
      this.field_74324_K = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[]{this.field_74312_F, this.field_74313_G, this.field_74351_w, this.field_74370_x, this.field_74368_y, this.field_74366_z, this.field_74314_A, this.field_74311_E, this.field_151444_V, this.field_74316_C, this.field_151445_Q, this.field_74310_D, this.field_74321_H, this.field_74322_I, this.field_74323_J, this.field_151447_Z, this.field_151457_aa, this.field_151458_ab, this.field_152395_am, this.field_178883_an, this.field_186718_X, this.field_193629_ap, this.field_193630_aq, this.field_194146_ao}, this.field_151456_ac);
      this.field_74318_M = EnumDifficulty.NORMAL;
      this.field_74332_R = "";
      this.field_74334_X = 70.0F;
      this.field_74363_ab = "en_us";
   }

   public static String func_74298_c(int p_74298_0_) {
      if (p_74298_0_ < 0) {
         switch(p_74298_0_) {
         case -100:
            return I18n.func_135052_a("key.mouse.left");
         case -99:
            return I18n.func_135052_a("key.mouse.right");
         case -98:
            return I18n.func_135052_a("key.mouse.middle");
         default:
            return I18n.func_135052_a("key.mouseButton", p_74298_0_ + 101);
         }
      } else {
         return p_74298_0_ < 256 ? Keyboard.getKeyName(p_74298_0_) : String.format("%c", (char)(p_74298_0_ - 256)).toUpperCase();
      }
   }

   public static boolean func_100015_a(KeyBinding p_100015_0_) {
      int i = p_100015_0_.func_151463_i();
      if (i != 0 && i < 256) {
         return i < 0 ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
      } else {
         return false;
      }
   }

   public void func_151440_a(KeyBinding p_151440_1_, int p_151440_2_) {
      p_151440_1_.func_151462_b(p_151440_2_);
      this.func_74303_b();
   }

   public void func_74304_a(GameSettings.Options p_74304_1_, float p_74304_2_) {
      if (p_74304_1_ == GameSettings.Options.SENSITIVITY) {
         this.field_74341_c = p_74304_2_;
      }

      if (p_74304_1_ == GameSettings.Options.FOV) {
         this.field_74334_X = p_74304_2_;
      }

      if (p_74304_1_ == GameSettings.Options.GAMMA) {
         this.field_74333_Y = p_74304_2_;
      }

      if (p_74304_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
         this.field_74350_i = (int)p_74304_2_;
      }

      if (p_74304_1_ == GameSettings.Options.CHAT_OPACITY) {
         this.field_74357_r = p_74304_2_;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         this.field_96694_H = p_74304_2_;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         this.field_96693_G = p_74304_2_;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (p_74304_1_ == GameSettings.Options.CHAT_WIDTH) {
         this.field_96692_F = p_74304_2_;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (p_74304_1_ == GameSettings.Options.CHAT_SCALE) {
         this.field_96691_E = p_74304_2_;
         this.field_74317_L.field_71456_v.func_146158_b().func_146245_b();
      }

      if (p_74304_1_ == GameSettings.Options.MIPMAP_LEVELS) {
         int i = this.field_151442_I;
         this.field_151442_I = (int)p_74304_2_;
         if ((float)i != p_74304_2_) {
            this.field_74317_L.func_147117_R().func_147633_a(this.field_151442_I);
            this.field_74317_L.func_110434_K().func_110577_a(TextureMap.field_110575_b);
            this.field_74317_L.func_147117_R().func_174937_a(false, this.field_151442_I > 0);
            this.field_74317_L.func_175603_A();
         }
      }

      if (p_74304_1_ == GameSettings.Options.RENDER_DISTANCE) {
         this.field_151451_c = (int)p_74304_2_;
         this.field_74317_L.field_71438_f.func_174979_m();
      }

   }

   public void func_74306_a(GameSettings.Options p_74306_1_, int p_74306_2_) {
      if (p_74306_1_ == GameSettings.Options.RENDER_DISTANCE) {
         this.func_74304_a(p_74306_1_, MathHelper.func_76131_a((float)(this.field_151451_c + p_74306_2_), p_74306_1_.func_186707_e(), p_74306_1_.func_148267_f()));
      }

      if (p_74306_1_ == GameSettings.Options.MAIN_HAND) {
         this.field_186715_A = this.field_186715_A.func_188468_a();
      }

      if (p_74306_1_ == GameSettings.Options.INVERT_MOUSE) {
         this.field_74338_d = !this.field_74338_d;
      }

      if (p_74306_1_ == GameSettings.Options.GUI_SCALE) {
         this.field_74335_Z = this.field_74335_Z + p_74306_2_ & 3;
      }

      if (p_74306_1_ == GameSettings.Options.PARTICLES) {
         this.field_74362_aa = (this.field_74362_aa + p_74306_2_) % 3;
      }

      if (p_74306_1_ == GameSettings.Options.VIEW_BOBBING) {
         this.field_74336_f = !this.field_74336_f;
      }

      if (p_74306_1_ == GameSettings.Options.RENDER_CLOUDS) {
         this.field_74345_l = (this.field_74345_l + p_74306_2_) % 3;
      }

      if (p_74306_1_ == GameSettings.Options.FORCE_UNICODE_FONT) {
         this.field_151455_aw = !this.field_151455_aw;
         this.field_74317_L.field_71466_p.func_78264_a(this.field_74317_L.func_135016_M().func_135042_a() || this.field_151455_aw);
      }

      if (p_74306_1_ == GameSettings.Options.FBO_ENABLE) {
         this.field_151448_g = !this.field_151448_g;
      }

      if (p_74306_1_ == GameSettings.Options.ANAGLYPH) {
         this.field_74337_g = !this.field_74337_g;
         this.field_74317_L.func_110436_a();
      }

      if (p_74306_1_ == GameSettings.Options.GRAPHICS) {
         this.field_74347_j = !this.field_74347_j;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (p_74306_1_ == GameSettings.Options.AMBIENT_OCCLUSION) {
         this.field_74348_k = (this.field_74348_k + p_74306_2_) % 3;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (p_74306_1_ == GameSettings.Options.CHAT_VISIBILITY) {
         this.field_74343_n = EntityPlayer.EnumChatVisibility.func_151426_a((this.field_74343_n.func_151428_a() + p_74306_2_) % 3);
      }

      if (p_74306_1_ == GameSettings.Options.CHAT_COLOR) {
         this.field_74344_o = !this.field_74344_o;
      }

      if (p_74306_1_ == GameSettings.Options.CHAT_LINKS) {
         this.field_74359_p = !this.field_74359_p;
      }

      if (p_74306_1_ == GameSettings.Options.CHAT_LINKS_PROMPT) {
         this.field_74358_q = !this.field_74358_q;
      }

      if (p_74306_1_ == GameSettings.Options.SNOOPER_ENABLED) {
         this.field_74355_t = !this.field_74355_t;
      }

      if (p_74306_1_ == GameSettings.Options.TOUCHSCREEN) {
         this.field_85185_A = !this.field_85185_A;
      }

      if (p_74306_1_ == GameSettings.Options.USE_FULLSCREEN) {
         this.field_74353_u = !this.field_74353_u;
         if (this.field_74317_L.func_71372_G() != this.field_74353_u) {
            this.field_74317_L.func_71352_k();
         }
      }

      if (p_74306_1_ == GameSettings.Options.ENABLE_VSYNC) {
         this.field_74352_v = !this.field_74352_v;
         Display.setVSyncEnabled(this.field_74352_v);
      }

      if (p_74306_1_ == GameSettings.Options.USE_VBO) {
         this.field_178881_t = !this.field_178881_t;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if (p_74306_1_ == GameSettings.Options.REDUCED_DEBUG_INFO) {
         this.field_178879_v = !this.field_178879_v;
      }

      if (p_74306_1_ == GameSettings.Options.ENTITY_SHADOWS) {
         this.field_181151_V = !this.field_181151_V;
      }

      if (p_74306_1_ == GameSettings.Options.ATTACK_INDICATOR) {
         this.field_186716_M = (this.field_186716_M + p_74306_2_) % 3;
      }

      if (p_74306_1_ == GameSettings.Options.SHOW_SUBTITLES) {
         this.field_186717_N = !this.field_186717_N;
      }

      if (p_74306_1_ == GameSettings.Options.REALMS_NOTIFICATIONS) {
         this.field_183509_X = !this.field_183509_X;
      }

      if (p_74306_1_ == GameSettings.Options.AUTO_JUMP) {
         this.field_189989_R = !this.field_189989_R;
      }

      if (p_74306_1_ == GameSettings.Options.NARRATOR) {
         if (NarratorChatListener.field_193643_a.func_193640_a()) {
            this.field_192571_R = (this.field_192571_R + p_74306_2_) % field_193632_b.length;
         } else {
            this.field_192571_R = 0;
         }

         NarratorChatListener.field_193643_a.func_193641_a(this.field_192571_R);
      }

      this.func_74303_b();
   }

   public float func_74296_a(GameSettings.Options p_74296_1_) {
      if (p_74296_1_ == GameSettings.Options.FOV) {
         return this.field_74334_X;
      } else if (p_74296_1_ == GameSettings.Options.GAMMA) {
         return this.field_74333_Y;
      } else if (p_74296_1_ == GameSettings.Options.SATURATION) {
         return this.field_151452_as;
      } else if (p_74296_1_ == GameSettings.Options.SENSITIVITY) {
         return this.field_74341_c;
      } else if (p_74296_1_ == GameSettings.Options.CHAT_OPACITY) {
         return this.field_74357_r;
      } else if (p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         return this.field_96694_H;
      } else if (p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         return this.field_96693_G;
      } else if (p_74296_1_ == GameSettings.Options.CHAT_SCALE) {
         return this.field_96691_E;
      } else if (p_74296_1_ == GameSettings.Options.CHAT_WIDTH) {
         return this.field_96692_F;
      } else if (p_74296_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
         return (float)this.field_74350_i;
      } else if (p_74296_1_ == GameSettings.Options.MIPMAP_LEVELS) {
         return (float)this.field_151442_I;
      } else {
         return p_74296_1_ == GameSettings.Options.RENDER_DISTANCE ? (float)this.field_151451_c : 0.0F;
      }
   }

   public boolean func_74308_b(GameSettings.Options p_74308_1_) {
      switch(p_74308_1_) {
      case INVERT_MOUSE:
         return this.field_74338_d;
      case VIEW_BOBBING:
         return this.field_74336_f;
      case ANAGLYPH:
         return this.field_74337_g;
      case FBO_ENABLE:
         return this.field_151448_g;
      case CHAT_COLOR:
         return this.field_74344_o;
      case CHAT_LINKS:
         return this.field_74359_p;
      case CHAT_LINKS_PROMPT:
         return this.field_74358_q;
      case SNOOPER_ENABLED:
         return this.field_74355_t;
      case USE_FULLSCREEN:
         return this.field_74353_u;
      case ENABLE_VSYNC:
         return this.field_74352_v;
      case USE_VBO:
         return this.field_178881_t;
      case TOUCHSCREEN:
         return this.field_85185_A;
      case FORCE_UNICODE_FONT:
         return this.field_151455_aw;
      case REDUCED_DEBUG_INFO:
         return this.field_178879_v;
      case ENTITY_SHADOWS:
         return this.field_181151_V;
      case SHOW_SUBTITLES:
         return this.field_186717_N;
      case REALMS_NOTIFICATIONS:
         return this.field_183509_X;
      case ENABLE_WEAK_ATTACKS:
         return this.field_189422_N;
      case AUTO_JUMP:
         return this.field_189989_R;
      default:
         return false;
      }
   }

   private static String func_74299_a(String[] p_74299_0_, int p_74299_1_) {
      if (p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length) {
         p_74299_1_ = 0;
      }

      return I18n.func_135052_a(p_74299_0_[p_74299_1_]);
   }

   public String func_74297_c(GameSettings.Options p_74297_1_) {
      String s = I18n.func_135052_a(p_74297_1_.func_74378_d()) + ": ";
      if (p_74297_1_.func_74380_a()) {
         float f1 = this.func_74296_a(p_74297_1_);
         float f = p_74297_1_.func_148266_c(f1);
         if (p_74297_1_ == GameSettings.Options.SENSITIVITY) {
            if (f == 0.0F) {
               return s + I18n.func_135052_a("options.sensitivity.min");
            } else {
               return f == 1.0F ? s + I18n.func_135052_a("options.sensitivity.max") : s + (int)(f * 200.0F) + "%";
            }
         } else if (p_74297_1_ == GameSettings.Options.FOV) {
            if (f1 == 70.0F) {
               return s + I18n.func_135052_a("options.fov.min");
            } else {
               return f1 == 110.0F ? s + I18n.func_135052_a("options.fov.max") : s + (int)f1;
            }
         } else if (p_74297_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
            return f1 == p_74297_1_.field_148272_O ? s + I18n.func_135052_a("options.framerateLimit.max") : s + I18n.func_135052_a("options.framerate", (int)f1);
         } else if (p_74297_1_ == GameSettings.Options.RENDER_CLOUDS) {
            return f1 == p_74297_1_.field_148271_N ? s + I18n.func_135052_a("options.cloudHeight.min") : s + ((int)f1 + 128);
         } else if (p_74297_1_ == GameSettings.Options.GAMMA) {
            if (f == 0.0F) {
               return s + I18n.func_135052_a("options.gamma.min");
            } else {
               return f == 1.0F ? s + I18n.func_135052_a("options.gamma.max") : s + "+" + (int)(f * 100.0F) + "%";
            }
         } else if (p_74297_1_ == GameSettings.Options.SATURATION) {
            return s + (int)(f * 400.0F) + "%";
         } else if (p_74297_1_ == GameSettings.Options.CHAT_OPACITY) {
            return s + (int)(f * 90.0F + 10.0F) + "%";
         } else if (p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
            return s + GuiNewChat.func_146243_b(f) + "px";
         } else if (p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
            return s + GuiNewChat.func_146243_b(f) + "px";
         } else if (p_74297_1_ == GameSettings.Options.CHAT_WIDTH) {
            return s + GuiNewChat.func_146233_a(f) + "px";
         } else if (p_74297_1_ == GameSettings.Options.RENDER_DISTANCE) {
            return s + I18n.func_135052_a("options.chunks", (int)f1);
         } else if (p_74297_1_ == GameSettings.Options.MIPMAP_LEVELS) {
            return f1 == 0.0F ? s + I18n.func_135052_a("options.off") : s + (int)f1;
         } else {
            return f == 0.0F ? s + I18n.func_135052_a("options.off") : s + (int)(f * 100.0F) + "%";
         }
      } else if (p_74297_1_.func_74382_b()) {
         boolean flag = this.func_74308_b(p_74297_1_);
         return flag ? s + I18n.func_135052_a("options.on") : s + I18n.func_135052_a("options.off");
      } else if (p_74297_1_ == GameSettings.Options.MAIN_HAND) {
         return s + this.field_186715_A;
      } else if (p_74297_1_ == GameSettings.Options.GUI_SCALE) {
         return s + func_74299_a(field_74367_ae, this.field_74335_Z);
      } else if (p_74297_1_ == GameSettings.Options.CHAT_VISIBILITY) {
         return s + I18n.func_135052_a(this.field_74343_n.func_151429_b());
      } else if (p_74297_1_ == GameSettings.Options.PARTICLES) {
         return s + func_74299_a(field_74364_ag, this.field_74362_aa);
      } else if (p_74297_1_ == GameSettings.Options.AMBIENT_OCCLUSION) {
         return s + func_74299_a(field_98303_au, this.field_74348_k);
      } else if (p_74297_1_ == GameSettings.Options.RENDER_CLOUDS) {
         return s + func_74299_a(field_181149_aW, this.field_74345_l);
      } else if (p_74297_1_ == GameSettings.Options.GRAPHICS) {
         if (this.field_74347_j) {
            return s + I18n.func_135052_a("options.graphics.fancy");
         } else {
            String s1 = "options.graphics.fast";
            return s + I18n.func_135052_a("options.graphics.fast");
         }
      } else if (p_74297_1_ == GameSettings.Options.ATTACK_INDICATOR) {
         return s + func_74299_a(field_186713_aK, this.field_186716_M);
      } else if (p_74297_1_ == GameSettings.Options.NARRATOR) {
         return NarratorChatListener.field_193643_a.func_193640_a() ? s + func_74299_a(field_193632_b, this.field_192571_R) : s + I18n.func_135052_a("options.narrator.notavailable");
      } else {
         return s;
      }
   }

   public void func_74300_a() {
      try {
         if (!this.field_74354_ai.exists()) {
            return;
         }

         this.field_186714_aM.clear();
         List<String> list = IOUtils.readLines(new FileInputStream(this.field_74354_ai));
         NBTTagCompound nbttagcompound = new NBTTagCompound();

         for(String s : list) {
            try {
               Iterator<String> iterator = field_189990_a.omitEmptyStrings().limit(2).split(s).iterator();
               nbttagcompound.func_74778_a(iterator.next(), iterator.next());
            } catch (Exception var10) {
               field_151454_ax.warn("Skipping bad option: {}", (Object)s);
            }
         }

         nbttagcompound = this.func_189988_a(nbttagcompound);

         for(String s1 : nbttagcompound.func_150296_c()) {
            String s2 = nbttagcompound.func_74779_i(s1);

            try {
               if ("mouseSensitivity".equals(s1)) {
                  this.field_74341_c = this.func_74305_a(s2);
               }

               if ("fov".equals(s1)) {
                  this.field_74334_X = this.func_74305_a(s2) * 40.0F + 70.0F;
               }

               if ("gamma".equals(s1)) {
                  this.field_74333_Y = this.func_74305_a(s2);
               }

               if ("saturation".equals(s1)) {
                  this.field_151452_as = this.func_74305_a(s2);
               }

               if ("invertYMouse".equals(s1)) {
                  this.field_74338_d = "true".equals(s2);
               }

               if ("renderDistance".equals(s1)) {
                  this.field_151451_c = Integer.parseInt(s2);
               }

               if ("guiScale".equals(s1)) {
                  this.field_74335_Z = Integer.parseInt(s2);
               }

               if ("particles".equals(s1)) {
                  this.field_74362_aa = Integer.parseInt(s2);
               }

               if ("bobView".equals(s1)) {
                  this.field_74336_f = "true".equals(s2);
               }

               if ("anaglyph3d".equals(s1)) {
                  this.field_74337_g = "true".equals(s2);
               }

               if ("maxFps".equals(s1)) {
                  this.field_74350_i = Integer.parseInt(s2);
               }

               if ("fboEnable".equals(s1)) {
                  this.field_151448_g = "true".equals(s2);
               }

               if ("difficulty".equals(s1)) {
                  this.field_74318_M = EnumDifficulty.func_151523_a(Integer.parseInt(s2));
               }

               if ("fancyGraphics".equals(s1)) {
                  this.field_74347_j = "true".equals(s2);
               }

               if ("tutorialStep".equals(s1)) {
                  this.field_193631_S = TutorialSteps.func_193307_a(s2);
               }

               if ("ao".equals(s1)) {
                  if ("true".equals(s2)) {
                     this.field_74348_k = 2;
                  } else if ("false".equals(s2)) {
                     this.field_74348_k = 0;
                  } else {
                     this.field_74348_k = Integer.parseInt(s2);
                  }
               }

               if ("renderClouds".equals(s1)) {
                  if ("true".equals(s2)) {
                     this.field_74345_l = 2;
                  } else if ("false".equals(s2)) {
                     this.field_74345_l = 0;
                  } else if ("fast".equals(s2)) {
                     this.field_74345_l = 1;
                  }
               }

               if ("attackIndicator".equals(s1)) {
                  if ("0".equals(s2)) {
                     this.field_186716_M = 0;
                  } else if ("1".equals(s2)) {
                     this.field_186716_M = 1;
                  } else if ("2".equals(s2)) {
                     this.field_186716_M = 2;
                  }
               }

               if ("resourcePacks".equals(s1)) {
                  this.field_151453_l = (List)JsonUtils.func_193840_a(field_151450_ay, s2, field_151449_az);
                  if (this.field_151453_l == null) {
                     this.field_151453_l = Lists.<String>newArrayList();
                  }
               }

               if ("incompatibleResourcePacks".equals(s1)) {
                  this.field_183018_l = (List)JsonUtils.func_193840_a(field_151450_ay, s2, field_151449_az);
                  if (this.field_183018_l == null) {
                     this.field_183018_l = Lists.<String>newArrayList();
                  }
               }

               if ("lastServer".equals(s1)) {
                  this.field_74332_R = s2;
               }

               if ("lang".equals(s1)) {
                  this.field_74363_ab = s2;
               }

               if ("chatVisibility".equals(s1)) {
                  this.field_74343_n = EntityPlayer.EnumChatVisibility.func_151426_a(Integer.parseInt(s2));
               }

               if ("chatColors".equals(s1)) {
                  this.field_74344_o = "true".equals(s2);
               }

               if ("chatLinks".equals(s1)) {
                  this.field_74359_p = "true".equals(s2);
               }

               if ("chatLinksPrompt".equals(s1)) {
                  this.field_74358_q = "true".equals(s2);
               }

               if ("chatOpacity".equals(s1)) {
                  this.field_74357_r = this.func_74305_a(s2);
               }

               if ("snooperEnabled".equals(s1)) {
                  this.field_74355_t = "true".equals(s2);
               }

               if ("fullscreen".equals(s1)) {
                  this.field_74353_u = "true".equals(s2);
               }

               if ("enableVsync".equals(s1)) {
                  this.field_74352_v = "true".equals(s2);
               }

               if ("useVbo".equals(s1)) {
                  this.field_178881_t = "true".equals(s2);
               }

               if ("hideServerAddress".equals(s1)) {
                  this.field_80005_w = "true".equals(s2);
               }

               if ("advancedItemTooltips".equals(s1)) {
                  this.field_82882_x = "true".equals(s2);
               }

               if ("pauseOnLostFocus".equals(s1)) {
                  this.field_82881_y = "true".equals(s2);
               }

               if ("touchscreen".equals(s1)) {
                  this.field_85185_A = "true".equals(s2);
               }

               if ("overrideHeight".equals(s1)) {
                  this.field_92119_C = Integer.parseInt(s2);
               }

               if ("overrideWidth".equals(s1)) {
                  this.field_92118_B = Integer.parseInt(s2);
               }

               if ("heldItemTooltips".equals(s1)) {
                  this.field_92117_D = "true".equals(s2);
               }

               if ("chatHeightFocused".equals(s1)) {
                  this.field_96694_H = this.func_74305_a(s2);
               }

               if ("chatHeightUnfocused".equals(s1)) {
                  this.field_96693_G = this.func_74305_a(s2);
               }

               if ("chatScale".equals(s1)) {
                  this.field_96691_E = this.func_74305_a(s2);
               }

               if ("chatWidth".equals(s1)) {
                  this.field_96692_F = this.func_74305_a(s2);
               }

               if ("mipmapLevels".equals(s1)) {
                  this.field_151442_I = Integer.parseInt(s2);
               }

               if ("forceUnicodeFont".equals(s1)) {
                  this.field_151455_aw = "true".equals(s2);
               }

               if ("reducedDebugInfo".equals(s1)) {
                  this.field_178879_v = "true".equals(s2);
               }

               if ("useNativeTransport".equals(s1)) {
                  this.field_181150_U = "true".equals(s2);
               }

               if ("entityShadows".equals(s1)) {
                  this.field_181151_V = "true".equals(s2);
               }

               if ("mainHand".equals(s1)) {
                  this.field_186715_A = "left".equals(s2) ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
               }

               if ("showSubtitles".equals(s1)) {
                  this.field_186717_N = "true".equals(s2);
               }

               if ("realmsNotifications".equals(s1)) {
                  this.field_183509_X = "true".equals(s2);
               }

               if ("enableWeakAttacks".equals(s1)) {
                  this.field_189422_N = "true".equals(s2);
               }

               if ("autoJump".equals(s1)) {
                  this.field_189989_R = "true".equals(s2);
               }

               if ("narrator".equals(s1)) {
                  this.field_192571_R = Integer.parseInt(s2);
               }

               for(KeyBinding keybinding : this.field_74324_K) {
                  if (s1.equals("key_" + keybinding.func_151464_g())) {
                     keybinding.func_151462_b(Integer.parseInt(s2));
                  }
               }

               for(SoundCategory soundcategory : SoundCategory.values()) {
                  if (s1.equals("soundCategory_" + soundcategory.func_187948_a())) {
                     this.field_186714_aM.put(soundcategory, Float.valueOf(this.func_74305_a(s2)));
                  }
               }

               for(EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
                  if (s1.equals("modelPart_" + enumplayermodelparts.func_179329_c())) {
                     this.func_178878_a(enumplayermodelparts, "true".equals(s2));
                  }
               }
            } catch (Exception var11) {
               field_151454_ax.warn("Skipping bad option: {}:{}", s1, s2);
            }
         }

         KeyBinding.func_74508_b();
      } catch (Exception exception) {
         field_151454_ax.error("Failed to load options", (Throwable)exception);
      }

   }

   private NBTTagCompound func_189988_a(NBTTagCompound p_189988_1_) {
      int i = 0;

      try {
         i = Integer.parseInt(p_189988_1_.func_74779_i("version"));
      } catch (RuntimeException var4) {
         ;
      }

      return this.field_74317_L.func_184126_aj().func_188251_a(FixTypes.OPTIONS, p_189988_1_, i);
   }

   private float func_74305_a(String p_74305_1_) {
      if ("true".equals(p_74305_1_)) {
         return 1.0F;
      } else {
         return "false".equals(p_74305_1_) ? 0.0F : Float.parseFloat(p_74305_1_);
      }
   }

   public void func_74303_b() {
      PrintWriter printwriter = null;

      try {
         printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.field_74354_ai), StandardCharsets.UTF_8));
         printwriter.println("version:1139");
         printwriter.println("invertYMouse:" + this.field_74338_d);
         printwriter.println("mouseSensitivity:" + this.field_74341_c);
         printwriter.println("fov:" + (this.field_74334_X - 70.0F) / 40.0F);
         printwriter.println("gamma:" + this.field_74333_Y);
         printwriter.println("saturation:" + this.field_151452_as);
         printwriter.println("renderDistance:" + this.field_151451_c);
         printwriter.println("guiScale:" + this.field_74335_Z);
         printwriter.println("particles:" + this.field_74362_aa);
         printwriter.println("bobView:" + this.field_74336_f);
         printwriter.println("anaglyph3d:" + this.field_74337_g);
         printwriter.println("maxFps:" + this.field_74350_i);
         printwriter.println("fboEnable:" + this.field_151448_g);
         printwriter.println("difficulty:" + this.field_74318_M.func_151525_a());
         printwriter.println("fancyGraphics:" + this.field_74347_j);
         printwriter.println("ao:" + this.field_74348_k);
         switch(this.field_74345_l) {
         case 0:
            printwriter.println("renderClouds:false");
            break;
         case 1:
            printwriter.println("renderClouds:fast");
            break;
         case 2:
            printwriter.println("renderClouds:true");
         }

         printwriter.println("resourcePacks:" + field_151450_ay.toJson(this.field_151453_l));
         printwriter.println("incompatibleResourcePacks:" + field_151450_ay.toJson(this.field_183018_l));
         printwriter.println("lastServer:" + this.field_74332_R);
         printwriter.println("lang:" + this.field_74363_ab);
         printwriter.println("chatVisibility:" + this.field_74343_n.func_151428_a());
         printwriter.println("chatColors:" + this.field_74344_o);
         printwriter.println("chatLinks:" + this.field_74359_p);
         printwriter.println("chatLinksPrompt:" + this.field_74358_q);
         printwriter.println("chatOpacity:" + this.field_74357_r);
         printwriter.println("snooperEnabled:" + this.field_74355_t);
         printwriter.println("fullscreen:" + this.field_74353_u);
         printwriter.println("enableVsync:" + this.field_74352_v);
         printwriter.println("useVbo:" + this.field_178881_t);
         printwriter.println("hideServerAddress:" + this.field_80005_w);
         printwriter.println("advancedItemTooltips:" + this.field_82882_x);
         printwriter.println("pauseOnLostFocus:" + this.field_82881_y);
         printwriter.println("touchscreen:" + this.field_85185_A);
         printwriter.println("overrideWidth:" + this.field_92118_B);
         printwriter.println("overrideHeight:" + this.field_92119_C);
         printwriter.println("heldItemTooltips:" + this.field_92117_D);
         printwriter.println("chatHeightFocused:" + this.field_96694_H);
         printwriter.println("chatHeightUnfocused:" + this.field_96693_G);
         printwriter.println("chatScale:" + this.field_96691_E);
         printwriter.println("chatWidth:" + this.field_96692_F);
         printwriter.println("mipmapLevels:" + this.field_151442_I);
         printwriter.println("forceUnicodeFont:" + this.field_151455_aw);
         printwriter.println("reducedDebugInfo:" + this.field_178879_v);
         printwriter.println("useNativeTransport:" + this.field_181150_U);
         printwriter.println("entityShadows:" + this.field_181151_V);
         printwriter.println("mainHand:" + (this.field_186715_A == EnumHandSide.LEFT ? "left" : "right"));
         printwriter.println("attackIndicator:" + this.field_186716_M);
         printwriter.println("showSubtitles:" + this.field_186717_N);
         printwriter.println("realmsNotifications:" + this.field_183509_X);
         printwriter.println("enableWeakAttacks:" + this.field_189422_N);
         printwriter.println("autoJump:" + this.field_189989_R);
         printwriter.println("narrator:" + this.field_192571_R);
         printwriter.println("tutorialStep:" + this.field_193631_S.func_193308_a());

         for(KeyBinding keybinding : this.field_74324_K) {
            printwriter.println("key_" + keybinding.func_151464_g() + ":" + keybinding.func_151463_i());
         }

         for(SoundCategory soundcategory : SoundCategory.values()) {
            printwriter.println("soundCategory_" + soundcategory.func_187948_a() + ":" + this.func_186711_a(soundcategory));
         }

         for(EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
            printwriter.println("modelPart_" + enumplayermodelparts.func_179329_c() + ":" + this.field_178882_aU.contains(enumplayermodelparts));
         }
      } catch (Exception exception) {
         field_151454_ax.error("Failed to save options", (Throwable)exception);
      } finally {
         IOUtils.closeQuietly((Writer)printwriter);
      }

      this.func_82879_c();
   }

   public float func_186711_a(SoundCategory p_186711_1_) {
      return this.field_186714_aM.containsKey(p_186711_1_) ? ((Float)this.field_186714_aM.get(p_186711_1_)).floatValue() : 1.0F;
   }

   public void func_186712_a(SoundCategory p_186712_1_, float p_186712_2_) {
      this.field_74317_L.func_147118_V().func_184399_a(p_186712_1_, p_186712_2_);
      this.field_186714_aM.put(p_186712_1_, Float.valueOf(p_186712_2_));
   }

   public void func_82879_c() {
      if (this.field_74317_L.field_71439_g != null) {
         int i = 0;

         for(EnumPlayerModelParts enumplayermodelparts : this.field_178882_aU) {
            i |= enumplayermodelparts.func_179327_a();
         }

         this.field_74317_L.field_71439_g.field_71174_a.func_147297_a(new CPacketClientSettings(this.field_74363_ab, this.field_151451_c, this.field_74343_n, this.field_74344_o, i, this.field_186715_A));
      }

   }

   public Set<EnumPlayerModelParts> func_178876_d() {
      return ImmutableSet.copyOf(this.field_178882_aU);
   }

   public void func_178878_a(EnumPlayerModelParts p_178878_1_, boolean p_178878_2_) {
      if (p_178878_2_) {
         this.field_178882_aU.add(p_178878_1_);
      } else {
         this.field_178882_aU.remove(p_178878_1_);
      }

      this.func_82879_c();
   }

   public void func_178877_a(EnumPlayerModelParts p_178877_1_) {
      if (this.func_178876_d().contains(p_178877_1_)) {
         this.field_178882_aU.remove(p_178877_1_);
      } else {
         this.field_178882_aU.add(p_178877_1_);
      }

      this.func_82879_c();
   }

   public int func_181147_e() {
      return this.field_151451_c >= 4 ? this.field_74345_l : 0;
   }

   public boolean func_181148_f() {
      return this.field_181150_U;
   }

   public static enum Options {
      INVERT_MOUSE("options.invertMouse", false, true),
      SENSITIVITY("options.sensitivity", true, false),
      FOV("options.fov", true, false, 30.0F, 110.0F, 1.0F),
      GAMMA("options.gamma", true, false),
      SATURATION("options.saturation", true, false),
      RENDER_DISTANCE("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
      VIEW_BOBBING("options.viewBobbing", false, true),
      ANAGLYPH("options.anaglyph", false, true),
      FRAMERATE_LIMIT("options.framerateLimit", true, false, 10.0F, 260.0F, 10.0F),
      FBO_ENABLE("options.fboEnable", false, true),
      RENDER_CLOUDS("options.renderClouds", false, false),
      GRAPHICS("options.graphics", false, false),
      AMBIENT_OCCLUSION("options.ao", false, false),
      GUI_SCALE("options.guiScale", false, false),
      PARTICLES("options.particles", false, false),
      CHAT_VISIBILITY("options.chat.visibility", false, false),
      CHAT_COLOR("options.chat.color", false, true),
      CHAT_LINKS("options.chat.links", false, true),
      CHAT_OPACITY("options.chat.opacity", true, false),
      CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
      SNOOPER_ENABLED("options.snooper", false, true),
      USE_FULLSCREEN("options.fullscreen", false, true),
      ENABLE_VSYNC("options.vsync", false, true),
      USE_VBO("options.vbo", false, true),
      TOUCHSCREEN("options.touchscreen", false, true),
      CHAT_SCALE("options.chat.scale", true, false),
      CHAT_WIDTH("options.chat.width", true, false),
      CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
      CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
      MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
      FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
      REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
      ENTITY_SHADOWS("options.entityShadows", false, true),
      MAIN_HAND("options.mainHand", false, false),
      ATTACK_INDICATOR("options.attackIndicator", false, false),
      ENABLE_WEAK_ATTACKS("options.enableWeakAttacks", false, true),
      SHOW_SUBTITLES("options.showSubtitles", false, true),
      REALMS_NOTIFICATIONS("options.realmsNotifications", false, true),
      AUTO_JUMP("options.autoJump", false, true),
      NARRATOR("options.narrator", false, false);

      private final boolean field_74385_A;
      private final boolean field_74386_B;
      private final String field_74387_C;
      private final float field_148270_M;
      private float field_148271_N;
      private float field_148272_O;

      public static GameSettings.Options func_74379_a(int p_74379_0_) {
         for(GameSettings.Options gamesettings$options : values()) {
            if (gamesettings$options.func_74381_c() == p_74379_0_) {
               return gamesettings$options;
            }
         }

         return null;
      }

      private Options(String p_i1015_3_, boolean p_i1015_4_, boolean p_i1015_5_) {
         this(p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0F, 1.0F, 0.0F);
      }

      private Options(String p_i45004_3_, boolean p_i45004_4_, boolean p_i45004_5_, float p_i45004_6_, float p_i45004_7_, float p_i45004_8_) {
         this.field_74387_C = p_i45004_3_;
         this.field_74385_A = p_i45004_4_;
         this.field_74386_B = p_i45004_5_;
         this.field_148271_N = p_i45004_6_;
         this.field_148272_O = p_i45004_7_;
         this.field_148270_M = p_i45004_8_;
      }

      public boolean func_74380_a() {
         return this.field_74385_A;
      }

      public boolean func_74382_b() {
         return this.field_74386_B;
      }

      public int func_74381_c() {
         return this.ordinal();
      }

      public String func_74378_d() {
         return this.field_74387_C;
      }

      public float func_186707_e() {
         return this.field_148271_N;
      }

      public float func_148267_f() {
         return this.field_148272_O;
      }

      public void func_148263_a(float p_148263_1_) {
         this.field_148272_O = p_148263_1_;
      }

      public float func_148266_c(float p_148266_1_) {
         return MathHelper.func_76131_a((this.func_148268_e(p_148266_1_) - this.field_148271_N) / (this.field_148272_O - this.field_148271_N), 0.0F, 1.0F);
      }

      public float func_148262_d(float p_148262_1_) {
         return this.func_148268_e(this.field_148271_N + (this.field_148272_O - this.field_148271_N) * MathHelper.func_76131_a(p_148262_1_, 0.0F, 1.0F));
      }

      public float func_148268_e(float p_148268_1_) {
         p_148268_1_ = this.func_148264_f(p_148268_1_);
         return MathHelper.func_76131_a(p_148268_1_, this.field_148271_N, this.field_148272_O);
      }

      private float func_148264_f(float p_148264_1_) {
         if (this.field_148270_M > 0.0F) {
            p_148264_1_ = this.field_148270_M * (float)Math.round(p_148264_1_ / this.field_148270_M);
         }

         return p_148264_1_;
      }
   }
}
