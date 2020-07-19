package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

public class GuiFlatPresets extends GuiScreen {
   private static final List<GuiFlatPresets.LayerItem> field_146431_f = Lists.<GuiFlatPresets.LayerItem>newArrayList();
   private final GuiCreateFlatWorld field_146432_g;
   private String field_146438_h;
   private String field_146439_i;
   private String field_146436_r;
   private GuiFlatPresets.ListSlot field_146435_s;
   private GuiButton field_146434_t;
   private GuiTextField field_146433_u;

   public GuiFlatPresets(GuiCreateFlatWorld p_i46318_1_) {
      this.field_146432_g = p_i46318_1_;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_146438_h = I18n.func_135052_a("createWorld.customize.presets.title");
      this.field_146439_i = I18n.func_135052_a("createWorld.customize.presets.share");
      this.field_146436_r = I18n.func_135052_a("createWorld.customize.presets.list");
      this.field_146433_u = new GuiTextField(2, this.field_146289_q, 50, 40, this.field_146294_l - 100, 20);
      this.field_146435_s = new GuiFlatPresets.ListSlot();
      this.field_146433_u.func_146203_f(1230);
      this.field_146433_u.func_146180_a(this.field_146432_g.func_146384_e());
      this.field_146434_t = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("createWorld.customize.presets.select")));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")));
      this.func_146426_g();
   }

   public void func_146274_d() throws IOException {
      super.func_146274_d();
      this.field_146435_s.func_178039_p();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      this.field_146433_u.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (!this.field_146433_u.func_146201_a(p_73869_1_, p_73869_2_)) {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146127_k == 0 && this.func_146430_p()) {
         this.field_146432_g.func_146383_a(this.field_146433_u.func_146179_b());
         this.field_146297_k.func_147108_a(this.field_146432_g);
      } else if (p_146284_1_.field_146127_k == 1) {
         this.field_146297_k.func_147108_a(this.field_146432_g);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.field_146435_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_146289_q, this.field_146438_h, this.field_146294_l / 2, 8, 16777215);
      this.func_73731_b(this.field_146289_q, this.field_146439_i, 50, 30, 10526880);
      this.func_73731_b(this.field_146289_q, this.field_146436_r, 50, 70, 10526880);
      this.field_146433_u.func_146194_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_73876_c() {
      this.field_146433_u.func_146178_a();
      super.func_73876_c();
   }

   public void func_146426_g() {
      this.field_146434_t.field_146124_l = this.func_146430_p();
   }

   private boolean func_146430_p() {
      return this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < field_146431_f.size() || this.field_146433_u.func_146179_b().length() > 1;
   }

   private static void func_146421_a(String p_146421_0_, Item p_146421_1_, Biome p_146421_2_, List<String> p_146421_3_, FlatLayerInfo... p_146421_4_) {
      func_175354_a(p_146421_0_, p_146421_1_, 0, p_146421_2_, p_146421_3_, p_146421_4_);
   }

   private static void func_175354_a(String p_175354_0_, Item p_175354_1_, int p_175354_2_, Biome p_175354_3_, List<String> p_175354_4_, FlatLayerInfo... p_175354_5_) {
      FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();

      for(int i = p_175354_5_.length - 1; i >= 0; --i) {
         flatgeneratorinfo.func_82650_c().add(p_175354_5_[i]);
      }

      flatgeneratorinfo.func_82647_a(Biome.func_185362_a(p_175354_3_));
      flatgeneratorinfo.func_82645_d();

      for(String s : p_175354_4_) {
         flatgeneratorinfo.func_82644_b().put(s, Maps.newHashMap());
      }

      field_146431_f.add(new GuiFlatPresets.LayerItem(p_175354_1_, p_175354_2_, p_175354_0_, flatgeneratorinfo.toString()));
   }

   static {
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.classic_flat"), Item.func_150898_a(Blocks.field_150349_c), Biomes.field_76772_c, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.field_150349_c), new FlatLayerInfo(2, Blocks.field_150346_d), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.tunnelers_dream"), Item.func_150898_a(Blocks.field_150348_b), Biomes.field_76770_e, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.field_150349_c), new FlatLayerInfo(5, Blocks.field_150346_d), new FlatLayerInfo(230, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.water_world"), Items.field_151131_as, Biomes.field_150575_M, Arrays.asList("biome_1", "oceanmonument"), new FlatLayerInfo(90, Blocks.field_150355_j), new FlatLayerInfo(5, Blocks.field_150354_m), new FlatLayerInfo(5, Blocks.field_150346_d), new FlatLayerInfo(5, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_175354_a(I18n.func_135052_a("createWorld.customize.preset.overworld"), Item.func_150898_a(Blocks.field_150329_H), BlockTallGrass.EnumType.GRASS.func_177044_a(), Biomes.field_76772_c, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.field_150349_c), new FlatLayerInfo(3, Blocks.field_150346_d), new FlatLayerInfo(59, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.snowy_kingdom"), Item.func_150898_a(Blocks.field_150431_aC), Biomes.field_76774_n, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.field_150431_aC), new FlatLayerInfo(1, Blocks.field_150349_c), new FlatLayerInfo(3, Blocks.field_150346_d), new FlatLayerInfo(59, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.bottomless_pit"), Items.field_151008_G, Biomes.field_76772_c, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.field_150349_c), new FlatLayerInfo(3, Blocks.field_150346_d), new FlatLayerInfo(2, Blocks.field_150347_e));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.desert"), Item.func_150898_a(Blocks.field_150354_m), Biomes.field_76769_d, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.field_150354_m), new FlatLayerInfo(52, Blocks.field_150322_A), new FlatLayerInfo(3, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.redstone_ready"), Items.field_151137_ax, Biomes.field_76769_d, Collections.emptyList(), new FlatLayerInfo(52, Blocks.field_150322_A), new FlatLayerInfo(3, Blocks.field_150348_b), new FlatLayerInfo(1, Blocks.field_150357_h));
      func_146421_a(I18n.func_135052_a("createWorld.customize.preset.the_void"), Item.func_150898_a(Blocks.field_180401_cv), Biomes.field_185440_P, Arrays.asList("decoration"), new FlatLayerInfo(1, Blocks.field_150350_a));
   }

   static class LayerItem {
      public Item field_148234_a;
      public int field_179037_b;
      public String field_148232_b;
      public String field_148233_c;

      public LayerItem(Item p_i45518_1_, int p_i45518_2_, String p_i45518_3_, String p_i45518_4_) {
         this.field_148234_a = p_i45518_1_;
         this.field_179037_b = p_i45518_2_;
         this.field_148232_b = p_i45518_3_;
         this.field_148233_c = p_i45518_4_;
      }
   }

   class ListSlot extends GuiSlot {
      public int field_148175_k = -1;

      public ListSlot() {
         super(GuiFlatPresets.this.field_146297_k, GuiFlatPresets.this.field_146294_l, GuiFlatPresets.this.field_146295_m, 80, GuiFlatPresets.this.field_146295_m - 37, 24);
      }

      private void func_178054_a(int p_178054_1_, int p_178054_2_, Item p_178054_3_, int p_178054_4_) {
         this.func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
         GlStateManager.func_179091_B();
         RenderHelper.func_74520_c();
         GuiFlatPresets.this.field_146296_j.func_175042_a(new ItemStack(p_178054_3_, 1, p_178054_3_.func_77614_k() ? p_178054_4_ : 0), p_178054_1_ + 2, p_178054_2_ + 2);
         RenderHelper.func_74518_a();
         GlStateManager.func_179101_C();
      }

      private void func_148173_e(int p_148173_1_, int p_148173_2_) {
         this.func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
      }

      private void func_148171_c(int p_148171_1_, int p_148171_2_, int p_148171_3_, int p_148171_4_) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110323_l);
         float f = 0.0078125F;
         float f1 = 0.0078125F;
         int i = 18;
         int j = 18;
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder bufferbuilder = tessellator.func_178180_c();
         bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         bufferbuilder.func_181662_b((double)(p_148171_1_ + 0), (double)(p_148171_2_ + 18), (double)GuiFlatPresets.this.field_73735_i).func_187315_a((double)((float)(p_148171_3_ + 0) * 0.0078125F), (double)((float)(p_148171_4_ + 18) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148171_1_ + 18), (double)(p_148171_2_ + 18), (double)GuiFlatPresets.this.field_73735_i).func_187315_a((double)((float)(p_148171_3_ + 18) * 0.0078125F), (double)((float)(p_148171_4_ + 18) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148171_1_ + 18), (double)(p_148171_2_ + 0), (double)GuiFlatPresets.this.field_73735_i).func_187315_a((double)((float)(p_148171_3_ + 18) * 0.0078125F), (double)((float)(p_148171_4_ + 0) * 0.0078125F)).func_181675_d();
         bufferbuilder.func_181662_b((double)(p_148171_1_ + 0), (double)(p_148171_2_ + 0), (double)GuiFlatPresets.this.field_73735_i).func_187315_a((double)((float)(p_148171_3_ + 0) * 0.0078125F), (double)((float)(p_148171_4_ + 0) * 0.0078125F)).func_181675_d();
         tessellator.func_78381_a();
      }

      protected int func_148127_b() {
         return GuiFlatPresets.field_146431_f.size();
      }

      protected void func_148144_a(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
         this.field_148175_k = p_148144_1_;
         GuiFlatPresets.this.func_146426_g();
         GuiFlatPresets.this.field_146433_u.func_146180_a((GuiFlatPresets.field_146431_f.get(GuiFlatPresets.this.field_146435_s.field_148175_k)).field_148233_c);
      }

      protected boolean func_148131_a(int p_148131_1_) {
         return p_148131_1_ == this.field_148175_k;
      }

      protected void func_148123_a() {
      }

      protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
         GuiFlatPresets.LayerItem guiflatpresets$layeritem = GuiFlatPresets.field_146431_f.get(p_192637_1_);
         this.func_178054_a(p_192637_2_, p_192637_3_, guiflatpresets$layeritem.field_148234_a, guiflatpresets$layeritem.field_179037_b);
         GuiFlatPresets.this.field_146289_q.func_78276_b(guiflatpresets$layeritem.field_148232_b, p_192637_2_ + 18 + 5, p_192637_3_ + 6, 16777215);
      }
   }
}
