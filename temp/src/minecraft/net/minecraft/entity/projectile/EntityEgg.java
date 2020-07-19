package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {
   public EntityEgg(World p_i1779_1_) {
      super(p_i1779_1_);
   }

   public EntityEgg(World p_i1780_1_, EntityLivingBase p_i1780_2_) {
      super(p_i1780_1_, p_i1780_2_);
   }

   public EntityEgg(World p_i1781_1_, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_) {
      super(p_i1781_1_, p_i1781_2_, p_i1781_4_, p_i1781_6_);
   }

   public static void func_189664_a(DataFixer p_189664_0_) {
      EntityThrowable.func_189661_a(p_189664_0_, "ThrownEgg");
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 3) {
         double d0 = 0.08D;

         for(int i = 0; i < 8; ++i) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, this.field_70165_t, this.field_70163_u, this.field_70161_v, ((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.08D, ((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.08D, ((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.08D, Item.func_150891_b(Items.field_151110_aK));
         }
      }

   }

   protected void func_70184_a(RayTraceResult p_70184_1_) {
      if (p_70184_1_.field_72308_g != null) {
         p_70184_1_.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 0.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70146_Z.nextInt(8) == 0) {
            int i = 1;
            if (this.field_70146_Z.nextInt(32) == 0) {
               i = 4;
            }

            for(int j = 0; j < i; ++j) {
               EntityChicken entitychicken = new EntityChicken(this.field_70170_p);
               entitychicken.func_70873_a(-24000);
               entitychicken.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
               this.field_70170_p.func_72838_d(entitychicken);
            }
         }

         this.field_70170_p.func_72960_a(this, (byte)3);
         this.func_70106_y();
      }

   }
}
