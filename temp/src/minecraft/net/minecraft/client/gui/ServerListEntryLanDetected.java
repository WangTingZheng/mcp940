package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry {
   private final GuiMultiplayer field_148292_c;
   protected final Minecraft field_148293_a;
   protected final LanServerInfo field_148291_b;
   private long field_148290_d;

   protected ServerListEntryLanDetected(GuiMultiplayer p_i47141_1_, LanServerInfo p_i47141_2_) {
      this.field_148292_c = p_i47141_1_;
      this.field_148291_b = p_i47141_2_;
      this.field_148293_a = Minecraft.func_71410_x();
   }

   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
      this.field_148293_a.field_71466_p.func_78276_b(I18n.func_135052_a("lanServer.title"), p_192634_2_ + 32 + 3, p_192634_3_ + 1, 16777215);
      this.field_148293_a.field_71466_p.func_78276_b(this.field_148291_b.func_77487_a(), p_192634_2_ + 32 + 3, p_192634_3_ + 12, 8421504);
      if (this.field_148293_a.field_71474_y.field_80005_w) {
         this.field_148293_a.field_71466_p.func_78276_b(I18n.func_135052_a("selectServer.hiddenAddress"), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 3158064);
      } else {
         this.field_148293_a.field_71466_p.func_78276_b(this.field_148291_b.func_77488_b(), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 3158064);
      }

   }

   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
      this.field_148292_c.func_146790_a(p_148278_1_);
      if (Minecraft.func_71386_F() - this.field_148290_d < 250L) {
         this.field_148292_c.func_146796_h();
      }

      this.field_148290_d = Minecraft.func_71386_F();
      return false;
   }

   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
   }

   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
   }

   public LanServerInfo func_189995_a() {
      return this.field_148291_b;
   }
}
