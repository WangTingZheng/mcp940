package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.util.ResourceLocation;

public class RenderZombieVillager extends RenderBiped<EntityZombieVillager> {
   private static final ResourceLocation field_110864_q = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");
   private static final ResourceLocation field_188330_l = new ResourceLocation("textures/entity/zombie_villager/zombie_farmer.png");
   private static final ResourceLocation field_188331_m = new ResourceLocation("textures/entity/zombie_villager/zombie_librarian.png");
   private static final ResourceLocation field_188332_n = new ResourceLocation("textures/entity/zombie_villager/zombie_priest.png");
   private static final ResourceLocation field_188333_o = new ResourceLocation("textures/entity/zombie_villager/zombie_smith.png");
   private static final ResourceLocation field_188329_p = new ResourceLocation("textures/entity/zombie_villager/zombie_butcher.png");

   public RenderZombieVillager(RenderManager p_i47186_1_) {
      super(p_i47186_1_, new ModelZombieVillager(), 0.5F);
      this.func_177094_a(new LayerVillagerArmor(this));
   }

   protected ResourceLocation func_110775_a(EntityZombieVillager p_110775_1_) {
      switch(p_110775_1_.func_190736_dl()) {
      case 0:
         return field_188330_l;
      case 1:
         return field_188331_m;
      case 2:
         return field_188332_n;
      case 3:
         return field_188333_o;
      case 4:
         return field_188329_p;
      case 5:
      default:
         return field_110864_q;
      }
   }

   protected void func_77043_a(EntityZombieVillager p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
      if (p_77043_1_.func_82230_o()) {
         p_77043_3_ += (float)(Math.cos((double)p_77043_1_.field_70173_aa * 3.25D) * 3.141592653589793D * 0.25D);
      }

      super.func_77043_a(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
   }
}
