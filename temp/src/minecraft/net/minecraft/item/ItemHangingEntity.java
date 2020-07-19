package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {
   private final Class<? extends EntityHanging> field_82811_a;

   public ItemHangingEntity(Class<? extends EntityHanging> p_i45342_1_) {
      this.field_82811_a = p_i45342_1_;
      this.func_77637_a(CreativeTabs.field_78031_c);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      BlockPos blockpos = p_180614_3_.func_177972_a(p_180614_5_);
      if (p_180614_5_ != EnumFacing.DOWN && p_180614_5_ != EnumFacing.UP && p_180614_1_.func_175151_a(blockpos, p_180614_5_, itemstack)) {
         EntityHanging entityhanging = this.func_179233_a(p_180614_2_, blockpos, p_180614_5_);
         if (entityhanging != null && entityhanging.func_70518_d()) {
            if (!p_180614_2_.field_72995_K) {
               entityhanging.func_184523_o();
               p_180614_2_.func_72838_d(entityhanging);
            }

            itemstack.func_190918_g(1);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }

   @Nullable
   private EntityHanging func_179233_a(World p_179233_1_, BlockPos p_179233_2_, EnumFacing p_179233_3_) {
      if (this.field_82811_a == EntityPainting.class) {
         return new EntityPainting(p_179233_1_, p_179233_2_, p_179233_3_);
      } else {
         return this.field_82811_a == EntityItemFrame.class ? new EntityItemFrame(p_179233_1_, p_179233_2_, p_179233_3_) : null;
      }
   }
}
