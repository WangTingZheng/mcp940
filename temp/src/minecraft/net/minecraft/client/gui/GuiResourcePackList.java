package net.minecraft.client.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
   protected final Minecraft field_148205_k;
   protected final List<ResourcePackListEntry> field_148204_l;

   public GuiResourcePackList(Minecraft p_i45055_1_, int p_i45055_2_, int p_i45055_3_, List<ResourcePackListEntry> p_i45055_4_) {
      super(p_i45055_1_, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
      this.field_148205_k = p_i45055_1_;
      this.field_148204_l = p_i45055_4_;
      this.field_148163_i = false;
      this.func_148133_a(true, (int)((float)p_i45055_1_.field_71466_p.field_78288_b * 1.5F));
   }

   protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
      String s = TextFormatting.UNDERLINE + "" + TextFormatting.BOLD + this.func_148202_k();
      this.field_148205_k.field_71466_p.func_78276_b(s, p_148129_1_ + this.field_148155_a / 2 - this.field_148205_k.field_71466_p.func_78256_a(s) / 2, Math.min(this.field_148153_b + 3, p_148129_2_), 16777215);
   }

   protected abstract String func_148202_k();

   public List<ResourcePackListEntry> func_148201_l() {
      return this.field_148204_l;
   }

   protected int func_148127_b() {
      return this.func_148201_l().size();
   }

   public ResourcePackListEntry func_148180_b(int p_148180_1_) {
      return (ResourcePackListEntry)this.func_148201_l().get(p_148180_1_);
   }

   public int func_148139_c() {
      return this.field_148155_a;
   }

   protected int func_148137_d() {
      return this.field_148151_d - 6;
   }
}
