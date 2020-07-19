package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import org.lwjgl.input.Keyboard;

public class GuiScreenCustomizePresets extends GuiScreen {
   private static final List<GuiScreenCustomizePresets.Info> field_175310_f = Lists.<GuiScreenCustomizePresets.Info>newArrayList();
   private GuiScreenCustomizePresets.ListPreset field_175311_g;
   private GuiButton field_175316_h;
   private GuiTextField field_175317_i;
   private final GuiCustomizeWorldScreen field_175314_r;
   protected String field_175315_a = "Customize World Presets";
   private String field_175313_s;
   private String field_175312_t;

   public GuiScreenCustomizePresets(GuiCustomizeWorldScreen p_i45524_1_) {
      this.field_175314_r = p_i45524_1_;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_175315_a = I18n.func_135052_a("createWorld.customize.custom.presets.title");
      this.field_175313_s = I18n.func_135052_a("createWorld.customize.presets.share");
      this.field_175312_t = I18n.func_135052_a("createWorld.customize.presets.list");
      this.field_175317_i = new GuiTextField(2, this.field_146289_q, 50, 40, this.field_146294_l - 100, 20);
      this.field_175311_g = new GuiScreenCustomizePresets.ListPreset();
      this.field_175317_i.func_146203_f(2000);
      this.field_175317_i.func_146180_a(this.field_175314_r.func_175323_a());
      this.field_175316_h = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 102, this.field_146295_m - 27, 100, 20, I18n.func_135052_a("createWorld.customize.presets.select")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 3, this.field_146295_m - 27, 100, 20, I18n.func_135052_a("gui.cancel")));
      this.func_175304_a();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_175311_g.func_178039_p();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      this.field_175317_i.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (!this.field_175317_i.func_146201_a(p_73869_1_, p_73869_2_)) {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      switch(p_146284_1_.field_146127_k) {
      case 0:
         this.field_175314_r.func_175324_a(this.field_175317_i.func_146179_b());
         this.field_146297_k.func_147108_a(this.field_175314_r);
         break;
      case 1:
         this.field_146297_k.func_147108_a(this.field_175314_r);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_175311_g.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_175315_a, this.field_146294_l / 2, 8, 16777215);
      this.func_73731_b(this.field_146289_q, this.field_175313_s, 50, 30, 10526880);
      this.func_73731_b(this.field_146289_q, this.field_175312_t, 50, 70, 10526880);
      this.field_175317_i.func_146194_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_73876_c() {
      this.field_175317_i.func_146178_a();
      super.func_73876_c();
   }

   public void func_175304_a() {
      this.field_175316_h.field_146124_l = this.func_175305_g();
   }

   private boolean func_175305_g() {
      return this.field_175311_g.field_178053_u > -1 && this.field_175311_g.field_178053_u < field_175310_f.size() || this.field_175317_i.func_146179_b().length() > 1;
   }

   static {
      ChunkGeneratorSettings.Factory chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }");
      ResourceLocation resourcelocation = new ResourceLocation("textures/gui/presets/water.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.waterWorld"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/isles.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.isleLand"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/delight.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.caveDelight"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/madness.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.mountains"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/drought.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.drought"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/chaos.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.caveChaos"), resourcelocation, chunkgeneratorsettings$factory));
      chunkgeneratorsettings$factory = ChunkGeneratorSettings.Factory.func_177865_a("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }");
      resourcelocation = new ResourceLocation("textures/gui/presets/luck.png");
      field_175310_f.add(new GuiScreenCustomizePresets.Info(I18n.func_135052_a("createWorld.customize.custom.preset.goodLuck"), resourcelocation, chunkgeneratorsettings$factory));
   }

   static class Info {
      public String field_178955_a;
      public ResourceLocation field_178953_b;
      public ChunkGeneratorSettings.Factory field_178954_c;

      public Info(String p_i45523_1_, ResourceLocation p_i45523_2_, ChunkGeneratorSettings.Factory p_i45523_3_) {
         this.field_178955_a = p_i45523_1_;
         this.field_178953_b = p_i45523_2_;
         this.field_178954_c = p_i45523_3_;
      }
   }

   class ListPreset extends GuiSlot {
      public int field_178053_u = -1;

      public ListPreset() {
         super(GuiScreenCustomizePresets.this.field_146297_k, GuiScreenCustomizePresets.this.field_146294_l, GuiScreenCustomizePresets.this.field_146295_m, 80, GuiScreenCustomizePresets.this.field_146295_m - 32, 38);
      }

      protected int func_148127_b() {
         return GuiScreenCustomizePresets.field_175310_f.size();
      }

      protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
         this.field_178053_u = p_148144_1_;
         GuiScreenCustomizePresets.this.func_175304_a();
         GuiScreenCustomizePresets.this.field_175317_i.func_146180_a((GuiScreenCustomizePresets.field_175310_f.get(GuiScreenCustomizePresets.this.field_175311_g.field_178053_u)).field_178954_c.toString());
      }

      protected boolean func_148131_a(int p_148131_1_) {
         return p_148131_1_ == this.field_178053_u;
      }

      protected void func_148123_a() {
      }

      private void func_178051_a(int p_178051_1_, int p_178051_2_, ResourceLocation p_178051_3_) {
         int i = p_178051_1_ + 5;
         GuiScreenCustomizePresets.this.func_73730_a(i - 1, i + 32, p_178051_2_ - 1, -2039584);
         GuiScreenCustomizePresets.this.func_73730_a(i - 1, i + 32, p_178051_2_ + 32, -6250336);
         GuiScreenCustomizePresets.this.func_73728_b(i - 1, p_178051_2_ - 1, p_178051_2_ + 32, -2039584);
         GuiScreenCustomizePresets.this.func_73728_b(i + 32, p_178051_2_ - 1, p_178051_2_ + 32, -6250336);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_148161_k.func_110434_K().func_110577_a(p_178051_3_);
         int j = 32;
         int k = 32;
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         bufferbuilder.func_181662_b((double)(i + 0), (double)(p_178051_2_ + 32), 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
         bufferbuilder.func_181662_b((double)(i + 32), (double)(p_178051_2_ + 32), 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
         bufferbuilder.func_181662_b((double)(i + 32), (double)(p_178051_2_ + 0), 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
         bufferbuilder.func_181662_b((double)(i + 0), (double)(p_178051_2_ + 0), 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
         tessellator.func_78381_a();
      }

      protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
         GuiScreenCustomizePresets.Info guiscreencustomizepresets$info = GuiScreenCustomizePresets.field_175310_f.get(p_192637_1_);
         this.func_178051_a(p_192637_2_, p_192637_3_, guiscreencustomizepresets$info.field_178953_b);
         GuiScreenCustomizePresets.this.field_146289_q.func_78276_b(guiscreencustomizepresets$info.field_178955_a, p_192637_2_ + 32 + 10, p_192637_3_ + 14, 16777215);
      }
   }
}
