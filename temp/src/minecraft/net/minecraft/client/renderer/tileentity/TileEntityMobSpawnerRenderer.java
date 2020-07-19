package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
   public void func_192841_a(TileEntityMobSpawner p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)p_192841_2_ + 0.5F, (float)p_192841_4_, (float)p_192841_6_ + 0.5F);
      func_147517_a(p_192841_1_.func_145881_a(), p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_);
      GlStateManager.func_179121_F();
   }

   public static void func_147517_a(MobSpawnerBaseLogic p_147517_0_, double p_147517_1_, double p_147517_3_, double p_147517_5_, float p_147517_7_) {
      Entity entity = p_147517_0_.func_184994_d();
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.field_70130_N, entity.field_70131_O);
         if ((double)f1 > 1.0D) {
            f /= f1;
         }

         GlStateManager.func_179109_b(0.0F, 0.4F, 0.0F);
         GlStateManager.func_179114_b((float)(p_147517_0_.func_177223_e() + (p_147517_0_.func_177222_d() - p_147517_0_.func_177223_e()) * (double)p_147517_7_) * 10.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.0F, -0.2F, 0.0F);
         GlStateManager.func_179114_b(-30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179152_a(f, f, f);
         entity.func_70012_b(p_147517_1_, p_147517_3_, p_147517_5_, 0.0F, 0.0F);
         Minecraft.func_71410_x().func_175598_ae().func_188391_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, p_147517_7_, false);
      }

   }
}
