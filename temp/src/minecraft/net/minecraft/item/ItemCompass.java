package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemCompass extends Item {
   public ItemCompass() {
      this.func_185043_a(new ResourceLocation("angle"), new IItemPropertyGetter() {
         double field_185095_a;
         double field_185096_b;
         long field_185097_c;

         public float func_185085_a(ItemStack p_185085_1_, @Nullable World p_185085_2_, @Nullable EntityLivingBase p_185085_3_) {
            if (p_185085_3_ == null && !p_185085_1_.func_82839_y()) {
               return 0.0F;
            } else {
               boolean flag = p_185085_3_ != null;
               Entity entity = (Entity)(flag ? p_185085_3_ : p_185085_1_.func_82836_z());
               if (p_185085_2_ == null) {
                  p_185085_2_ = entity.field_70170_p;
               }

               double d0;
               if (p_185085_2_.field_73011_w.func_76569_d()) {
                  double d1 = flag ? (double)entity.field_70177_z : this.func_185094_a((EntityItemFrame)entity);
                  d1 = MathHelper.func_191273_b(d1 / 360.0D, 1.0D);
                  double d2 = this.func_185092_a(p_185085_2_, entity) / 6.2831854820251465D;
                  d0 = 0.5D - (d1 - 0.25D - d2);
               } else {
                  d0 = Math.random();
               }

               if (flag) {
                  d0 = this.func_185093_a(p_185085_2_, d0);
               }

               return MathHelper.func_188207_b((float)d0, 1.0F);
            }
         }

         private double func_185093_a(World p_185093_1_, double p_185093_2_) {
            if (p_185093_1_.func_82737_E() != this.field_185097_c) {
               this.field_185097_c = p_185093_1_.func_82737_E();
               double d0 = p_185093_2_ - this.field_185095_a;
               d0 = MathHelper.func_191273_b(d0 + 0.5D, 1.0D) - 0.5D;
               this.field_185096_b += d0 * 0.1D;
               this.field_185096_b *= 0.8D;
               this.field_185095_a = MathHelper.func_191273_b(this.field_185095_a + this.field_185096_b, 1.0D);
            }

            return this.field_185095_a;
         }

         private double func_185094_a(EntityItemFrame p_185094_1_) {
            return (double)MathHelper.func_188209_b(180 + p_185094_1_.field_174860_b.func_176736_b() * 90);
         }

         private double func_185092_a(World p_185092_1_, Entity p_185092_2_) {
            BlockPos blockpos = p_185092_1_.func_175694_M();
            return Math.atan2((double)blockpos.func_177952_p() - p_185092_2_.field_70161_v, (double)blockpos.func_177958_n() - p_185092_2_.field_70165_t);
         }
      });
   }
}
