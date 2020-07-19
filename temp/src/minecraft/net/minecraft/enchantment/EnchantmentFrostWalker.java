package net.minecraft.enchantment;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment {
   public EnchantmentFrostWalker(Enchantment.Rarity p_i46728_1_, EntityEquipmentSlot... p_i46728_2_) {
      super(p_i46728_1_, EnumEnchantmentType.ARMOR_FEET, p_i46728_2_);
      this.func_77322_b("frostWalker");
   }

   public int func_77321_a(int p_77321_1_) {
      return p_77321_1_ * 10;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 15;
   }

   public boolean func_185261_e() {
      return true;
   }

   public int func_77325_b() {
      return 2;
   }

   public static void func_185266_a(EntityLivingBase p_185266_0_, World p_185266_1_, BlockPos p_185266_2_, int p_185266_3_) {
      if (p_185266_0_.field_70122_E) {
         float f = (float)Math.min(16, 2 + p_185266_3_);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

         for(BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.func_177975_b(p_185266_2_.func_177963_a((double)(-f), -1.0D, (double)(-f)), p_185266_2_.func_177963_a((double)f, -1.0D, (double)f))) {
            if (blockpos$mutableblockpos1.func_177957_d(p_185266_0_.field_70165_t, p_185266_0_.field_70163_u, p_185266_0_.field_70161_v) <= (double)(f * f)) {
               blockpos$mutableblockpos.func_181079_c(blockpos$mutableblockpos1.func_177958_n(), blockpos$mutableblockpos1.func_177956_o() + 1, blockpos$mutableblockpos1.func_177952_p());
               IBlockState iblockstate = p_185266_1_.func_180495_p(blockpos$mutableblockpos);
               if (iblockstate.func_185904_a() == Material.field_151579_a) {
                  IBlockState iblockstate1 = p_185266_1_.func_180495_p(blockpos$mutableblockpos1);
                  if (iblockstate1.func_185904_a() == Material.field_151586_h && ((Integer)iblockstate1.func_177229_b(BlockLiquid.field_176367_b)).intValue() == 0 && p_185266_1_.func_190527_a(Blocks.field_185778_de, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null)) {
                     p_185266_1_.func_175656_a(blockpos$mutableblockpos1, Blocks.field_185778_de.func_176223_P());
                     p_185266_1_.func_175684_a(blockpos$mutableblockpos1.func_185334_h(), Blocks.field_185778_de, MathHelper.func_76136_a(p_185266_0_.func_70681_au(), 60, 120));
                  }
               }
            }
         }

      }
   }

   public boolean func_77326_a(Enchantment p_77326_1_) {
      return super.func_77326_a(p_77326_1_) && p_77326_1_ != Enchantments.field_185300_i;
   }
}
