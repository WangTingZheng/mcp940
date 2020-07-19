package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemClock extends Item {
   public ItemClock() {
      this.func_185043_a(new ResourceLocation("time"), new IItemPropertyGetter() {
         double field_185088_a;
         double field_185089_b;
         long field_185090_c;

         public float func_185085_a(ItemStack p_185085_1_, @Nullable World p_185085_2_, @Nullable EntityLivingBase p_185085_3_) {
            boolean flag = p_185085_3_ != null;
            Entity entity = (Entity)(flag ? p_185085_3_ : p_185085_1_.func_82836_z());
            if (p_185085_2_ == null && entity != null) {
               p_185085_2_ = entity.field_70170_p;
            }

            if (p_185085_2_ == null) {
               return 0.0F;
            } else {
               double d0;
               if (p_185085_2_.field_73011_w.func_76569_d()) {
                  d0 = (double)p_185085_2_.func_72826_c(1.0F);
               } else {
                  d0 = Math.random();
               }

               d0 = this.func_185087_a(p_185085_2_, d0);
               return (float)d0;
            }
         }

         private double func_185087_a(World p_185087_1_, double p_185087_2_) {
            if (p_185087_1_.func_82737_E() != this.field_185090_c) {
               this.field_185090_c = p_185087_1_.func_82737_E();
               double d0 = p_185087_2_ - this.field_185088_a;
               d0 = MathHelper.func_191273_b(d0 + 0.5D, 1.0D) - 0.5D;
               this.field_185089_b += d0 * 0.1D;
               this.field_185089_b *= 0.9D;
               this.field_185088_a = MathHelper.func_191273_b(this.field_185088_a + this.field_185089_b, 1.0D);
            }

            return this.field_185088_a;
         }
      });
   }
}
