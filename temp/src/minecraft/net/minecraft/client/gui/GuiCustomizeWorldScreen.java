package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.primitives.Floats;
import java.io.IOException;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;

public class GuiCustomizeWorldScreen extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
   private final GuiCreateWorld field_175343_i;
   protected String field_175341_a = "Customize World Settings";
   protected String field_175333_f = "Page 1 of 3";
   protected String field_175335_g = "Basic Settings";
   protected String[] field_175342_h = new String[4];
   private GuiPageButtonList field_175349_r;
   private GuiButton field_175348_s;
   private GuiButton field_175347_t;
   private GuiButton field_175346_u;
   private GuiButton field_175345_v;
   private GuiButton field_175344_w;
   private GuiButton field_175352_x;
   private GuiButton field_175351_y;
   private GuiButton field_175350_z;
   private boolean field_175338_A;
   private int field_175339_B;
   private boolean field_175340_C;
   private final Predicate<String> field_175332_D = new Predicate<String>() {
      public boolean apply(@Nullable String p_apply_1_) {
         Float f = Floats.tryParse(p_apply_1_);
         return p_apply_1_.isEmpty() || f != null && Floats.isFinite(f.floatValue()) && f.floatValue() >= 0.0F;
      }
   };
   private final ChunkGeneratorSettings.Factory field_175334_E = new ChunkGeneratorSettings.Factory();
   private ChunkGeneratorSettings.Factory field_175336_F;
   private final Random field_175337_G = new Random();

   public GuiCustomizeWorldScreen(GuiScreen p_i45521_1_, String p_i45521_2_) {
      this.field_175343_i = (GuiCreateWorld)p_i45521_1_;
      this.func_175324_a(p_i45521_2_);
   }

   public void func_73866_w_() {
      int i = 0;
      int j = 0;
      if (this.field_175349_r != null) {
         i = this.field_175349_r.func_178059_e();
         j = this.field_175349_r.func_148148_g();
      }

      this.field_175341_a = I18n.func_135052_a("options.customizeTitle");
      this.field_146292_n.clear();
      this.field_175345_v = this.func_189646_b(new GuiButton(302, 20, 5, 80, 20, I18n.func_135052_a("createWorld.customize.custom.prev")));
      this.field_175344_w = this.func_189646_b(new GuiButton(303, this.field_146294_l - 100, 5, 80, 20, I18n.func_135052_a("createWorld.customize.custom.next")));
      this.field_175346_u = this.func_189646_b(new GuiButton(304, this.field_146294_l / 2 - 187, this.field_146295_m - 27, 90, 20, I18n.func_135052_a("createWorld.customize.custom.defaults")));
      this.field_175347_t = this.func_189646_b(new GuiButton(301, this.field_146294_l / 2 - 92, this.field_146295_m - 27, 90, 20, I18n.func_135052_a("createWorld.customize.custom.randomize")));
      this.field_175350_z = this.func_189646_b(new GuiButton(305, this.field_146294_l / 2 + 3, this.field_146295_m - 27, 90, 20, I18n.func_135052_a("createWorld.customize.custom.presets")));
      this.field_175348_s = this.func_189646_b(new GuiButton(300, this.field_146294_l / 2 + 98, this.field_146295_m - 27, 90, 20, I18n.func_135052_a("gui.done")));
      this.field_175346_u.field_146124_l = this.field_175338_A;
      this.field_175352_x = new GuiButton(306, this.field_146294_l / 2 - 55, 160, 50, 20, I18n.func_135052_a("gui.yes"));
      this.field_175352_x.field_146125_m = false;
      this.field_146292_n.add(this.field_175352_x);
      this.field_175351_y = new GuiButton(307, this.field_146294_l / 2 + 5, 160, 50, 20, I18n.func_135052_a("gui.no"));
      this.field_175351_y.field_146125_m = false;
      this.field_146292_n.add(this.field_175351_y);
      if (this.field_175339_B != 0) {
         this.field_175352_x.field_146125_m = true;
         this.field_175351_y.field_146125_m = true;
      }

      this.func_175325_f();
      if (i != 0) {
         this.field_175349_r.func_181156_c(i);
         this.field_175349_r.func_148145_f(j);
         this.func_175328_i();
      }

   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_175349_r.func_178039_p();
   }

   private void func_175325_f() {
      GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry = new GuiPageButtonList.GuiListEntry[]{new GuiPageButtonList.GuiSlideEntry(160, I18n.func_135052_a("createWorld.customize.custom.seaLevel"), true, this, 1.0F, 255.0F, (float)this.field_175336_F.field_177929_r), new GuiPageButtonList.GuiButtonEntry(148, I18n.func_135052_a("createWorld.customize.custom.useCaves"), true, this.field_175336_F.field_177927_s), new GuiPageButtonList.GuiButtonEntry(150, I18n.func_135052_a("createWorld.customize.custom.useStrongholds"), true, this.field_175336_F.field_177921_v), new GuiPageButtonList.GuiButtonEntry(151, I18n.func_135052_a("createWorld.customize.custom.useVillages"), true, this.field_175336_F.field_177919_w), new GuiPageButtonList.GuiButtonEntry(152, I18n.func_135052_a("createWorld.customize.custom.useMineShafts"), true, this.field_175336_F.field_177944_x), new GuiPageButtonList.GuiButtonEntry(153, I18n.func_135052_a("createWorld.customize.custom.useTemples"), true, this.field_175336_F.field_177942_y), new GuiPageButtonList.GuiButtonEntry(210, I18n.func_135052_a("createWorld.customize.custom.useMonuments"), true, this.field_175336_F.field_177940_z), new GuiPageButtonList.GuiButtonEntry(211, I18n.func_135052_a("createWorld.customize.custom.useMansions"), true, this.field_175336_F.field_191076_A), new GuiPageButtonList.GuiButtonEntry(154, I18n.func_135052_a("createWorld.customize.custom.useRavines"), true, this.field_175336_F.field_177870_A), new GuiPageButtonList.GuiButtonEntry(149, I18n.func_135052_a("createWorld.customize.custom.useDungeons"), true, this.field_175336_F.field_177925_t), new GuiPageButtonList.GuiSlideEntry(157, I18n.func_135052_a("createWorld.customize.custom.dungeonChance"), true, this, 1.0F, 100.0F, (float)this.field_175336_F.field_177923_u), new GuiPageButtonList.GuiButtonEntry(155, I18n.func_135052_a("createWorld.customize.custom.useWaterLakes"), true, this.field_175336_F.field_177871_B), new GuiPageButtonList.GuiSlideEntry(158, I18n.func_135052_a("createWorld.customize.custom.waterLakeChance"), true, this, 1.0F, 100.0F, (float)this.field_175336_F.field_177872_C), new GuiPageButtonList.GuiButtonEntry(156, I18n.func_135052_a("createWorld.customize.custom.useLavaLakes"), true, this.field_175336_F.field_177866_D), new GuiPageButtonList.GuiSlideEntry(159, I18n.func_135052_a("createWorld.customize.custom.lavaLakeChance"), true, this, 10.0F, 100.0F, (float)this.field_175336_F.field_177867_E), new GuiPageButtonList.GuiButtonEntry(161, I18n.func_135052_a("createWorld.customize.custom.useLavaOceans"), true, this.field_175336_F.field_177868_F), new GuiPageButtonList.GuiSlideEntry(162, I18n.func_135052_a("createWorld.customize.custom.fixedBiome"), true, this, -1.0F, 37.0F, (float)this.field_175336_F.field_177869_G), new GuiPageButtonList.GuiSlideEntry(163, I18n.func_135052_a("createWorld.customize.custom.biomeSize"), true, this, 1.0F, 8.0F, (float)this.field_175336_F.field_177877_H), new GuiPageButtonList.GuiSlideEntry(164, I18n.func_135052_a("createWorld.customize.custom.riverSize"), true, this, 1.0F, 5.0F, (float)this.field_175336_F.field_177878_I)};
      GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry1 = new GuiPageButtonList.GuiListEntry[]{new GuiPageButtonList.GuiLabelEntry(416, I18n.func_135052_a("tile.dirt.name"), false), null, new GuiPageButtonList.GuiSlideEntry(165, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177879_J), new GuiPageButtonList.GuiSlideEntry(166, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177880_K), new GuiPageButtonList.GuiSlideEntry(167, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177873_L), new GuiPageButtonList.GuiSlideEntry(168, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177874_M), new GuiPageButtonList.GuiLabelEntry(417, I18n.func_135052_a("tile.gravel.name"), false), null, new GuiPageButtonList.GuiSlideEntry(169, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177875_N), new GuiPageButtonList.GuiSlideEntry(170, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177876_O), new GuiPageButtonList.GuiSlideEntry(171, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177886_P), new GuiPageButtonList.GuiSlideEntry(172, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177885_Q), new GuiPageButtonList.GuiLabelEntry(418, I18n.func_135052_a("tile.stone.granite.name"), false), null, new GuiPageButtonList.GuiSlideEntry(173, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177888_R), new GuiPageButtonList.GuiSlideEntry(174, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177887_S), new GuiPageButtonList.GuiSlideEntry(175, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177882_T), new GuiPageButtonList.GuiSlideEntry(176, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177881_U), new GuiPageButtonList.GuiLabelEntry(419, I18n.func_135052_a("tile.stone.diorite.name"), false), null, new GuiPageButtonList.GuiSlideEntry(177, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177884_V), new GuiPageButtonList.GuiSlideEntry(178, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177883_W), new GuiPageButtonList.GuiSlideEntry(179, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177891_X), new GuiPageButtonList.GuiSlideEntry(180, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177890_Y), new GuiPageButtonList.GuiLabelEntry(420, I18n.func_135052_a("tile.stone.andesite.name"), false), null, new GuiPageButtonList.GuiSlideEntry(181, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177892_Z), new GuiPageButtonList.GuiSlideEntry(182, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177936_aa), new GuiPageButtonList.GuiSlideEntry(183, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177937_ab), new GuiPageButtonList.GuiSlideEntry(184, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177934_ac), new GuiPageButtonList.GuiLabelEntry(421, I18n.func_135052_a("tile.oreCoal.name"), false), null, new GuiPageButtonList.GuiSlideEntry(185, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177935_ad), new GuiPageButtonList.GuiSlideEntry(186, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177941_ae), new GuiPageButtonList.GuiSlideEntry(187, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177943_af), new GuiPageButtonList.GuiSlideEntry(189, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177938_ag), new GuiPageButtonList.GuiLabelEntry(422, I18n.func_135052_a("tile.oreIron.name"), false), null, new GuiPageButtonList.GuiSlideEntry(190, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177939_ah), new GuiPageButtonList.GuiSlideEntry(191, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177922_ai), new GuiPageButtonList.GuiSlideEntry(192, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177924_aj), new GuiPageButtonList.GuiSlideEntry(193, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177918_ak), new GuiPageButtonList.GuiLabelEntry(423, I18n.func_135052_a("tile.oreGold.name"), false), null, new GuiPageButtonList.GuiSlideEntry(194, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177920_al), new GuiPageButtonList.GuiSlideEntry(195, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177930_am), new GuiPageButtonList.GuiSlideEntry(196, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177932_an), new GuiPageButtonList.GuiSlideEntry(197, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177926_ao), new GuiPageButtonList.GuiLabelEntry(424, I18n.func_135052_a("tile.oreRedstone.name"), false), null, new GuiPageButtonList.GuiSlideEntry(198, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177928_ap), new GuiPageButtonList.GuiSlideEntry(199, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177908_aq), new GuiPageButtonList.GuiSlideEntry(200, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177906_ar), new GuiPageButtonList.GuiSlideEntry(201, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177904_as), new GuiPageButtonList.GuiLabelEntry(425, I18n.func_135052_a("tile.oreDiamond.name"), false), null, new GuiPageButtonList.GuiSlideEntry(202, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177902_at), new GuiPageButtonList.GuiSlideEntry(203, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177916_au), new GuiPageButtonList.GuiSlideEntry(204, I18n.func_135052_a("createWorld.customize.custom.minHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177914_av), new GuiPageButtonList.GuiSlideEntry(205, I18n.func_135052_a("createWorld.customize.custom.maxHeight"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177912_aw), new GuiPageButtonList.GuiLabelEntry(426, I18n.func_135052_a("tile.oreLapis.name"), false), null, new GuiPageButtonList.GuiSlideEntry(206, I18n.func_135052_a("createWorld.customize.custom.size"), false, this, 1.0F, 50.0F, (float)this.field_175336_F.field_177910_ax), new GuiPageButtonList.GuiSlideEntry(207, I18n.func_135052_a("createWorld.customize.custom.count"), false, this, 0.0F, 40.0F, (float)this.field_175336_F.field_177897_ay), new GuiPageButtonList.GuiSlideEntry(208, I18n.func_135052_a("createWorld.customize.custom.center"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177895_az), new GuiPageButtonList.GuiSlideEntry(209, I18n.func_135052_a("createWorld.customize.custom.spread"), false, this, 0.0F, 255.0F, (float)this.field_175336_F.field_177889_aA)};
      GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry2 = new GuiPageButtonList.GuiListEntry[]{new GuiPageButtonList.GuiSlideEntry(100, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleX"), false, this, 1.0F, 5000.0F, this.field_175336_F.field_177917_i), new GuiPageButtonList.GuiSlideEntry(101, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleY"), false, this, 1.0F, 5000.0F, this.field_175336_F.field_177911_j), new GuiPageButtonList.GuiSlideEntry(102, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleZ"), false, this, 1.0F, 5000.0F, this.field_175336_F.field_177913_k), new GuiPageButtonList.GuiSlideEntry(103, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleX"), false, this, 1.0F, 2000.0F, this.field_175336_F.field_177893_f), new GuiPageButtonList.GuiSlideEntry(104, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleZ"), false, this, 1.0F, 2000.0F, this.field_175336_F.field_177894_g), new GuiPageButtonList.GuiSlideEntry(105, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleExponent"), false, this, 0.01F, 20.0F, this.field_175336_F.field_177915_h), new GuiPageButtonList.GuiSlideEntry(106, I18n.func_135052_a("createWorld.customize.custom.baseSize"), false, this, 1.0F, 25.0F, this.field_175336_F.field_177907_l), new GuiPageButtonList.GuiSlideEntry(107, I18n.func_135052_a("createWorld.customize.custom.coordinateScale"), false, this, 1.0F, 6000.0F, this.field_175336_F.field_177899_b), new GuiPageButtonList.GuiSlideEntry(108, I18n.func_135052_a("createWorld.customize.custom.heightScale"), false, this, 1.0F, 6000.0F, this.field_175336_F.field_177900_c), new GuiPageButtonList.GuiSlideEntry(109, I18n.func_135052_a("createWorld.customize.custom.stretchY"), false, this, 0.01F, 50.0F, this.field_175336_F.field_177909_m), new GuiPageButtonList.GuiSlideEntry(110, I18n.func_135052_a("createWorld.customize.custom.upperLimitScale"), false, this, 1.0F, 5000.0F, this.field_175336_F.field_177896_d), new GuiPageButtonList.GuiSlideEntry(111, I18n.func_135052_a("createWorld.customize.custom.lowerLimitScale"), false, this, 1.0F, 5000.0F, this.field_175336_F.field_177898_e), new GuiPageButtonList.GuiSlideEntry(112, I18n.func_135052_a("createWorld.customize.custom.biomeDepthWeight"), false, this, 1.0F, 20.0F, this.field_175336_F.field_177903_n), new GuiPageButtonList.GuiSlideEntry(113, I18n.func_135052_a("createWorld.customize.custom.biomeDepthOffset"), false, this, 0.0F, 20.0F, this.field_175336_F.field_177905_o), new GuiPageButtonList.GuiSlideEntry(114, I18n.func_135052_a("createWorld.customize.custom.biomeScaleWeight"), false, this, 1.0F, 20.0F, this.field_175336_F.field_177933_p), new GuiPageButtonList.GuiSlideEntry(115, I18n.func_135052_a("createWorld.customize.custom.biomeScaleOffset"), false, this, 0.0F, 20.0F, this.field_175336_F.field_177931_q)};
      GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry3 = new GuiPageButtonList.GuiListEntry[]{new GuiPageButtonList.GuiLabelEntry(400, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleX") + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", this.field_175336_F.field_177917_i), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(401, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleY") + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", this.field_175336_F.field_177911_j), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(402, I18n.func_135052_a("createWorld.customize.custom.mainNoiseScaleZ") + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", this.field_175336_F.field_177913_k), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(403, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleX") + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", this.field_175336_F.field_177893_f), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(404, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleZ") + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", this.field_175336_F.field_177894_g), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(405, I18n.func_135052_a("createWorld.customize.custom.depthNoiseScaleExponent") + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", this.field_175336_F.field_177915_h), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(406, I18n.func_135052_a("createWorld.customize.custom.baseSize") + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", this.field_175336_F.field_177907_l), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(407, I18n.func_135052_a("createWorld.customize.custom.coordinateScale") + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", this.field_175336_F.field_177899_b), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(408, I18n.func_135052_a("createWorld.customize.custom.heightScale") + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", this.field_175336_F.field_177900_c), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(409, I18n.func_135052_a("createWorld.customize.custom.stretchY") + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", this.field_175336_F.field_177909_m), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(410, I18n.func_135052_a("createWorld.customize.custom.upperLimitScale") + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", this.field_175336_F.field_177896_d), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(411, I18n.func_135052_a("createWorld.customize.custom.lowerLimitScale") + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", this.field_175336_F.field_177898_e), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(412, I18n.func_135052_a("createWorld.customize.custom.biomeDepthWeight") + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", this.field_175336_F.field_177903_n), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(413, I18n.func_135052_a("createWorld.customize.custom.biomeDepthOffset") + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", this.field_175336_F.field_177905_o), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(414, I18n.func_135052_a("createWorld.customize.custom.biomeScaleWeight") + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", this.field_175336_F.field_177933_p), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(415, I18n.func_135052_a("createWorld.customize.custom.biomeScaleOffset") + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", this.field_175336_F.field_177931_q), false, this.field_175332_D)};
      this.field_175349_r = new GuiPageButtonList(this.field_146297_k, this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 32, 25, this, new GuiPageButtonList.GuiListEntry[][]{aguipagebuttonlist$guilistentry, aguipagebuttonlist$guilistentry1, aguipagebuttonlist$guilistentry2, aguipagebuttonlist$guilistentry3});

      for(int i = 0; i < 4; ++i) {
         this.field_175342_h[i] = I18n.func_135052_a("createWorld.customize.custom.page" + i);
      }

      this.func_175328_i();
   }

   public String func_175323_a() {
      return this.field_175336_F.toString().replace("\n", "");
   }

   public void func_175324_a(String p_175324_1_) {
      if (p_175324_1_ != null && !p_175324_1_.isEmpty()) {
         this.field_175336_F = ChunkGeneratorSettings.Factory.func_177865_a(p_175324_1_);
      } else {
         this.field_175336_F = new ChunkGeneratorSettings.Factory();
      }

   }

   public void func_175319_a(int p_175319_1_, String p_175319_2_) {
      float f = 0.0F;

      try {
         f = Float.parseFloat(p_175319_2_);
      } catch (NumberFormatException var5) {
         ;
      }

      float f1 = 0.0F;
      switch(p_175319_1_) {
      case 132:
         this.field_175336_F.field_177917_i = MathHelper.func_76131_a(f, 1.0F, 5000.0F);
         f1 = this.field_175336_F.field_177917_i;
         break;
      case 133:
         this.field_175336_F.field_177911_j = MathHelper.func_76131_a(f, 1.0F, 5000.0F);
         f1 = this.field_175336_F.field_177911_j;
         break;
      case 134:
         this.field_175336_F.field_177913_k = MathHelper.func_76131_a(f, 1.0F, 5000.0F);
         f1 = this.field_175336_F.field_177913_k;
         break;
      case 135:
         this.field_175336_F.field_177893_f = MathHelper.func_76131_a(f, 1.0F, 2000.0F);
         f1 = this.field_175336_F.field_177893_f;
         break;
      case 136:
         this.field_175336_F.field_177894_g = MathHelper.func_76131_a(f, 1.0F, 2000.0F);
         f1 = this.field_175336_F.field_177894_g;
         break;
      case 137:
         this.field_175336_F.field_177915_h = MathHelper.func_76131_a(f, 0.01F, 20.0F);
         f1 = this.field_175336_F.field_177915_h;
         break;
      case 138:
         this.field_175336_F.field_177907_l = MathHelper.func_76131_a(f, 1.0F, 25.0F);
         f1 = this.field_175336_F.field_177907_l;
         break;
      case 139:
         this.field_175336_F.field_177899_b = MathHelper.func_76131_a(f, 1.0F, 6000.0F);
         f1 = this.field_175336_F.field_177899_b;
         break;
      case 140:
         this.field_175336_F.field_177900_c = MathHelper.func_76131_a(f, 1.0F, 6000.0F);
         f1 = this.field_175336_F.field_177900_c;
         break;
      case 141:
         this.field_175336_F.field_177909_m = MathHelper.func_76131_a(f, 0.01F, 50.0F);
         f1 = this.field_175336_F.field_177909_m;
         break;
      case 142:
         this.field_175336_F.field_177896_d = MathHelper.func_76131_a(f, 1.0F, 5000.0F);
         f1 = this.field_175336_F.field_177896_d;
         break;
      case 143:
         this.field_175336_F.field_177898_e = MathHelper.func_76131_a(f, 1.0F, 5000.0F);
         f1 = this.field_175336_F.field_177898_e;
         break;
      case 144:
         this.field_175336_F.field_177903_n = MathHelper.func_76131_a(f, 1.0F, 20.0F);
         f1 = this.field_175336_F.field_177903_n;
         break;
      case 145:
         this.field_175336_F.field_177905_o = MathHelper.func_76131_a(f, 0.0F, 20.0F);
         f1 = this.field_175336_F.field_177905_o;
         break;
      case 146:
         this.field_175336_F.field_177933_p = MathHelper.func_76131_a(f, 1.0F, 20.0F);
         f1 = this.field_175336_F.field_177933_p;
         break;
      case 147:
         this.field_175336_F.field_177931_q = MathHelper.func_76131_a(f, 0.0F, 20.0F);
         f1 = this.field_175336_F.field_177931_q;
      }

      if (f1 != f && f != 0.0F) {
         ((GuiTextField)this.field_175349_r.func_178061_c(p_175319_1_)).func_146180_a(this.func_175330_b(p_175319_1_, f1));
      }

      ((GuiSlider)this.field_175349_r.func_178061_c(p_175319_1_ - 132 + 100)).func_175218_a(f1, false);
      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }

   }

   private void func_181031_a(boolean p_181031_1_) {
      this.field_175338_A = p_181031_1_;
      this.field_175346_u.field_146124_l = p_181031_1_;
   }

   public String func_175318_a(int p_175318_1_, String p_175318_2_, float p_175318_3_) {
      return p_175318_2_ + ": " + this.func_175330_b(p_175318_1_, p_175318_3_);
   }

   private String func_175330_b(int p_175330_1_, float p_175330_2_) {
      switch(p_175330_1_) {
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 107:
      case 108:
      case 110:
      case 111:
      case 132:
      case 133:
      case 134:
      case 135:
      case 136:
      case 139:
      case 140:
      case 142:
      case 143:
         return String.format("%5.3f", p_175330_2_);
      case 105:
      case 106:
      case 109:
      case 112:
      case 113:
      case 114:
      case 115:
      case 137:
      case 138:
      case 141:
      case 144:
      case 145:
      case 146:
      case 147:
         return String.format("%2.3f", p_175330_2_);
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 159:
      case 160:
      case 161:
      default:
         return String.format("%d", (int)p_175330_2_);
      case 162:
         if (p_175330_2_ < 0.0F) {
            return I18n.func_135052_a("gui.all");
         } else if ((int)p_175330_2_ >= Biome.func_185362_a(Biomes.field_76778_j)) {
            Biome biome1 = Biome.func_185357_a((int)p_175330_2_ + 2);
            return biome1 != null ? biome1.func_185359_l() : "?";
         } else {
            Biome biome = Biome.func_185357_a((int)p_175330_2_);
            return biome != null ? biome.func_185359_l() : "?";
         }
      }
   }

   public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
      switch(p_175321_1_) {
      case 148:
         this.field_175336_F.field_177927_s = p_175321_2_;
         break;
      case 149:
         this.field_175336_F.field_177925_t = p_175321_2_;
         break;
      case 150:
         this.field_175336_F.field_177921_v = p_175321_2_;
         break;
      case 151:
         this.field_175336_F.field_177919_w = p_175321_2_;
         break;
      case 152:
         this.field_175336_F.field_177944_x = p_175321_2_;
         break;
      case 153:
         this.field_175336_F.field_177942_y = p_175321_2_;
         break;
      case 154:
         this.field_175336_F.field_177870_A = p_175321_2_;
         break;
      case 155:
         this.field_175336_F.field_177871_B = p_175321_2_;
         break;
      case 156:
         this.field_175336_F.field_177866_D = p_175321_2_;
         break;
      case 161:
         this.field_175336_F.field_177868_F = p_175321_2_;
         break;
      case 210:
         this.field_175336_F.field_177940_z = p_175321_2_;
         break;
      case 211:
         this.field_175336_F.field_191076_A = p_175321_2_;
      }

      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }

   }

   public void func_175320_a(int p_175320_1_, float p_175320_2_) {
      switch(p_175320_1_) {
      case 100:
         this.field_175336_F.field_177917_i = p_175320_2_;
         break;
      case 101:
         this.field_175336_F.field_177911_j = p_175320_2_;
         break;
      case 102:
         this.field_175336_F.field_177913_k = p_175320_2_;
         break;
      case 103:
         this.field_175336_F.field_177893_f = p_175320_2_;
         break;
      case 104:
         this.field_175336_F.field_177894_g = p_175320_2_;
         break;
      case 105:
         this.field_175336_F.field_177915_h = p_175320_2_;
         break;
      case 106:
         this.field_175336_F.field_177907_l = p_175320_2_;
         break;
      case 107:
         this.field_175336_F.field_177899_b = p_175320_2_;
         break;
      case 108:
         this.field_175336_F.field_177900_c = p_175320_2_;
         break;
      case 109:
         this.field_175336_F.field_177909_m = p_175320_2_;
         break;
      case 110:
         this.field_175336_F.field_177896_d = p_175320_2_;
         break;
      case 111:
         this.field_175336_F.field_177898_e = p_175320_2_;
         break;
      case 112:
         this.field_175336_F.field_177903_n = p_175320_2_;
         break;
      case 113:
         this.field_175336_F.field_177905_o = p_175320_2_;
         break;
      case 114:
         this.field_175336_F.field_177933_p = p_175320_2_;
         break;
      case 115:
         this.field_175336_F.field_177931_q = p_175320_2_;
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 132:
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 153:
      case 154:
      case 155:
      case 156:
      case 161:
      case 188:
      default:
         break;
      case 157:
         this.field_175336_F.field_177923_u = (int)p_175320_2_;
         break;
      case 158:
         this.field_175336_F.field_177872_C = (int)p_175320_2_;
         break;
      case 159:
         this.field_175336_F.field_177867_E = (int)p_175320_2_;
         break;
      case 160:
         this.field_175336_F.field_177929_r = (int)p_175320_2_;
         break;
      case 162:
         this.field_175336_F.field_177869_G = (int)p_175320_2_;
         break;
      case 163:
         this.field_175336_F.field_177877_H = (int)p_175320_2_;
         break;
      case 164:
         this.field_175336_F.field_177878_I = (int)p_175320_2_;
         break;
      case 165:
         this.field_175336_F.field_177879_J = (int)p_175320_2_;
         break;
      case 166:
         this.field_175336_F.field_177880_K = (int)p_175320_2_;
         break;
      case 167:
         this.field_175336_F.field_177873_L = (int)p_175320_2_;
         break;
      case 168:
         this.field_175336_F.field_177874_M = (int)p_175320_2_;
         break;
      case 169:
         this.field_175336_F.field_177875_N = (int)p_175320_2_;
         break;
      case 170:
         this.field_175336_F.field_177876_O = (int)p_175320_2_;
         break;
      case 171:
         this.field_175336_F.field_177886_P = (int)p_175320_2_;
         break;
      case 172:
         this.field_175336_F.field_177885_Q = (int)p_175320_2_;
         break;
      case 173:
         this.field_175336_F.field_177888_R = (int)p_175320_2_;
         break;
      case 174:
         this.field_175336_F.field_177887_S = (int)p_175320_2_;
         break;
      case 175:
         this.field_175336_F.field_177882_T = (int)p_175320_2_;
         break;
      case 176:
         this.field_175336_F.field_177881_U = (int)p_175320_2_;
         break;
      case 177:
         this.field_175336_F.field_177884_V = (int)p_175320_2_;
         break;
      case 178:
         this.field_175336_F.field_177883_W = (int)p_175320_2_;
         break;
      case 179:
         this.field_175336_F.field_177891_X = (int)p_175320_2_;
         break;
      case 180:
         this.field_175336_F.field_177890_Y = (int)p_175320_2_;
         break;
      case 181:
         this.field_175336_F.field_177892_Z = (int)p_175320_2_;
         break;
      case 182:
         this.field_175336_F.field_177936_aa = (int)p_175320_2_;
         break;
      case 183:
         this.field_175336_F.field_177937_ab = (int)p_175320_2_;
         break;
      case 184:
         this.field_175336_F.field_177934_ac = (int)p_175320_2_;
         break;
      case 185:
         this.field_175336_F.field_177935_ad = (int)p_175320_2_;
         break;
      case 186:
         this.field_175336_F.field_177941_ae = (int)p_175320_2_;
         break;
      case 187:
         this.field_175336_F.field_177943_af = (int)p_175320_2_;
         break;
      case 189:
         this.field_175336_F.field_177938_ag = (int)p_175320_2_;
         break;
      case 190:
         this.field_175336_F.field_177939_ah = (int)p_175320_2_;
         break;
      case 191:
         this.field_175336_F.field_177922_ai = (int)p_175320_2_;
         break;
      case 192:
         this.field_175336_F.field_177924_aj = (int)p_175320_2_;
         break;
      case 193:
         this.field_175336_F.field_177918_ak = (int)p_175320_2_;
         break;
      case 194:
         this.field_175336_F.field_177920_al = (int)p_175320_2_;
         break;
      case 195:
         this.field_175336_F.field_177930_am = (int)p_175320_2_;
         break;
      case 196:
         this.field_175336_F.field_177932_an = (int)p_175320_2_;
         break;
      case 197:
         this.field_175336_F.field_177926_ao = (int)p_175320_2_;
         break;
      case 198:
         this.field_175336_F.field_177928_ap = (int)p_175320_2_;
         break;
      case 199:
         this.field_175336_F.field_177908_aq = (int)p_175320_2_;
         break;
      case 200:
         this.field_175336_F.field_177906_ar = (int)p_175320_2_;
         break;
      case 201:
         this.field_175336_F.field_177904_as = (int)p_175320_2_;
         break;
      case 202:
         this.field_175336_F.field_177902_at = (int)p_175320_2_;
         break;
      case 203:
         this.field_175336_F.field_177916_au = (int)p_175320_2_;
         break;
      case 204:
         this.field_175336_F.field_177914_av = (int)p_175320_2_;
         break;
      case 205:
         this.field_175336_F.field_177912_aw = (int)p_175320_2_;
         break;
      case 206:
         this.field_175336_F.field_177910_ax = (int)p_175320_2_;
         break;
      case 207:
         this.field_175336_F.field_177897_ay = (int)p_175320_2_;
         break;
      case 208:
         this.field_175336_F.field_177895_az = (int)p_175320_2_;
         break;
      case 209:
         this.field_175336_F.field_177889_aA = (int)p_175320_2_;
      }

      if (p_175320_1_ >= 100 && p_175320_1_ < 116) {
         Gui gui = this.field_175349_r.func_178061_c(p_175320_1_ - 100 + 132);
         if (gui != null) {
            ((GuiTextField)gui).func_146180_a(this.func_175330_b(p_175320_1_, p_175320_2_));
         }
      }

      if (!this.field_175336_F.equals(this.field_175334_E)) {
         this.func_181031_a(true);
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         switch(p_146284_1_.field_146127_k) {
         case 300:
            this.field_175343_i.field_146334_a = this.field_175336_F.toString();
            this.field_146297_k.func_147108_a(this.field_175343_i);
            break;
         case 301:
            for(int i = 0; i < this.field_175349_r.func_148127_b(); ++i) {
               GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.field_175349_r.func_148180_b(i);
               Gui gui = guipagebuttonlist$guientry.func_178022_a();
               if (gui instanceof GuiButton) {
                  GuiButton guibutton = (GuiButton)gui;
                  if (guibutton instanceof GuiSlider) {
                     float f = ((GuiSlider)guibutton).func_175217_d() * (0.75F + this.field_175337_G.nextFloat() * 0.5F) + (this.field_175337_G.nextFloat() * 0.1F - 0.05F);
                     ((GuiSlider)guibutton).func_175219_a(MathHelper.func_76131_a(f, 0.0F, 1.0F));
                  } else if (guibutton instanceof GuiListButton) {
                     ((GuiListButton)guibutton).func_175212_b(this.field_175337_G.nextBoolean());
                  }
               }

               Gui gui1 = guipagebuttonlist$guientry.func_178021_b();
               if (gui1 instanceof GuiButton) {
                  GuiButton guibutton1 = (GuiButton)gui1;
                  if (guibutton1 instanceof GuiSlider) {
                     float f1 = ((GuiSlider)guibutton1).func_175217_d() * (0.75F + this.field_175337_G.nextFloat() * 0.5F) + (this.field_175337_G.nextFloat() * 0.1F - 0.05F);
                     ((GuiSlider)guibutton1).func_175219_a(MathHelper.func_76131_a(f1, 0.0F, 1.0F));
                  } else if (guibutton1 instanceof GuiListButton) {
                     ((GuiListButton)guibutton1).func_175212_b(this.field_175337_G.nextBoolean());
                  }
               }
            }

            return;
         case 302:
            this.field_175349_r.func_178071_h();
            this.func_175328_i();
            break;
         case 303:
            this.field_175349_r.func_178064_i();
            this.func_175328_i();
            break;
         case 304:
            if (this.field_175338_A) {
               this.func_175322_b(304);
            }
            break;
         case 305:
            this.field_146297_k.func_147108_a(new GuiScreenCustomizePresets(this));
            break;
         case 306:
            this.func_175331_h();
            break;
         case 307:
            this.field_175339_B = 0;
            this.func_175331_h();
         }

      }
   }

   private void func_175326_g() {
      this.field_175336_F.func_177863_a();
      this.func_175325_f();
      this.func_181031_a(false);
   }

   private void func_175322_b(int p_175322_1_) {
      this.field_175339_B = p_175322_1_;
      this.func_175329_a(true);
   }

   private void func_175331_h() throws IOException {
      switch(this.field_175339_B) {
      case 300:
         this.func_146284_a((GuiListButton)this.field_175349_r.func_178061_c(300));
         break;
      case 304:
         this.func_175326_g();
      }

      this.field_175339_B = 0;
      this.field_175340_C = true;
      this.func_175329_a(false);
   }

   private void func_175329_a(boolean p_175329_1_) {
      this.field_175352_x.field_146125_m = p_175329_1_;
      this.field_175351_y.field_146125_m = p_175329_1_;
      this.field_175347_t.field_146124_l = !p_175329_1_;
      this.field_175348_s.field_146124_l = !p_175329_1_;
      this.field_175345_v.field_146124_l = !p_175329_1_;
      this.field_175344_w.field_146124_l = !p_175329_1_;
      this.field_175346_u.field_146124_l = this.field_175338_A && !p_175329_1_;
      this.field_175350_z.field_146124_l = !p_175329_1_;
      this.field_175349_r.func_181155_a(!p_175329_1_);
   }

   private void func_175328_i() {
      this.field_175345_v.field_146124_l = this.field_175349_r.func_178059_e() != 0;
      this.field_175344_w.field_146124_l = this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1;
      this.field_175333_f = I18n.func_135052_a("book.pageIndicator", this.field_175349_r.func_178059_e() + 1, this.field_175349_r.func_178057_f());
      this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
      this.field_175347_t.field_146124_l = this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      super.func_73869_a(p_73869_1_, p_73869_2_);
      if (this.field_175339_B == 0) {
         switch(p_73869_2_) {
         case 200:
            this.func_175327_a(1.0F);
            break;
         case 208:
            this.func_175327_a(-1.0F);
            break;
         default:
            this.field_175349_r.func_178062_a(p_73869_1_, p_73869_2_);
         }

      }
   }

   private void func_175327_a(float p_175327_1_) {
      Gui gui = this.field_175349_r.func_178056_g();
      if (gui instanceof GuiTextField) {
         float f = p_175327_1_;
         if (GuiScreen.func_146272_n()) {
            f = p_175327_1_ * 0.1F;
            if (GuiScreen.func_146271_m()) {
               f *= 0.1F;
            }
         } else if (GuiScreen.func_146271_m()) {
            f = p_175327_1_ * 10.0F;
            if (GuiScreen.func_175283_s()) {
               f *= 10.0F;
            }
         }

         GuiTextField guitextfield = (GuiTextField)gui;
         Float f1 = Floats.tryParse(guitextfield.func_146179_b());
         if (f1 != null) {
            f1 = f1.floatValue() + f;
            int i = guitextfield.func_175206_d();
            String s = this.func_175330_b(guitextfield.func_175206_d(), f1.floatValue());
            guitextfield.func_146180_a(s);
            this.func_175319_a(i, s);
         }
      }
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if (this.field_175339_B == 0 && !this.field_175340_C) {
         this.field_175349_r.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
      }
   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      super.func_146286_b(p_146286_1_, p_146286_2_, p_146286_3_);
      if (this.field_175340_C) {
         this.field_175340_C = false;
      } else if (this.field_175339_B == 0) {
         this.field_175349_r.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_);
      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_175349_r.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_175341_a, this.field_146294_l / 2, 2, 16777215);
      this.func_73732_a(this.field_146289_q, this.field_175333_f, this.field_146294_l / 2, 12, 16777215);
      this.func_73732_a(this.field_146289_q, this.field_175335_g, this.field_146294_l / 2, 22, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if (this.field_175339_B != 0) {
         func_73734_a(0, 0, this.field_146294_l, this.field_146295_m, Integer.MIN_VALUE);
         this.func_73730_a(this.field_146294_l / 2 - 91, this.field_146294_l / 2 + 90, 99, -2039584);
         this.func_73730_a(this.field_146294_l / 2 - 91, this.field_146294_l / 2 + 90, 185, -6250336);
         this.func_73728_b(this.field_146294_l / 2 - 91, 99, 185, -2039584);
         this.func_73728_b(this.field_146294_l / 2 + 90, 99, 185, -6250336);
         float f = 85.0F;
         float f1 = 180.0F;
         GlStateManager.func_179140_f();
         GlStateManager.func_179106_n();
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         this.field_146297_k.func_110434_K().func_110577_a(field_110325_k);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         float f2 = 32.0F;
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         bufferbuilder.func_181662_b((double)(this.field_146294_l / 2 - 90), 185.0D, 0.0D).func_187315_a(0.0D, 2.65625D).func_181669_b(64, 64, 64, 64).func_181675_d();
         bufferbuilder.func_181662_b((double)(this.field_146294_l / 2 + 90), 185.0D, 0.0D).func_187315_a(5.625D, 2.65625D).func_181669_b(64, 64, 64, 64).func_181675_d();
         bufferbuilder.func_181662_b((double)(this.field_146294_l / 2 + 90), 100.0D, 0.0D).func_187315_a(5.625D, 0.0D).func_181669_b(64, 64, 64, 64).func_181675_d();
         bufferbuilder.func_181662_b((double)(this.field_146294_l / 2 - 90), 100.0D, 0.0D).func_187315_a(0.0D, 0.0D).func_181669_b(64, 64, 64, 64).func_181675_d();
         tessellator.func_78381_a();
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("createWorld.customize.custom.confirmTitle"), this.field_146294_l / 2, 105, 16777215);
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("createWorld.customize.custom.confirm1"), this.field_146294_l / 2, 125, 16777215);
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("createWorld.customize.custom.confirm2"), this.field_146294_l / 2, 135, 16777215);
         this.field_175352_x.func_191745_a(this.field_146297_k, p_73863_1_, p_73863_2_, p_73863_3_);
         this.field_175351_y.func_191745_a(this.field_146297_k, p_73863_1_, p_73863_2_, p_73863_3_);
      }

   }
}
