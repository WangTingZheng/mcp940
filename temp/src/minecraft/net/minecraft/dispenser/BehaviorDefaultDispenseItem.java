package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
   public final ItemStack func_82482_a(IBlockSource p_82482_1_, ItemStack p_82482_2_) {
      ItemStack itemstack = this.func_82487_b(p_82482_1_, p_82482_2_);
      this.func_82485_a(p_82482_1_);
      this.func_82489_a(p_82482_1_, (EnumFacing)p_82482_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
      return itemstack;
   }

   protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
      EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
      IPosition iposition = BlockDispenser.func_149939_a(p_82487_1_);
      ItemStack itemstack = p_82487_2_.func_77979_a(1);
      func_82486_a(p_82487_1_.func_82618_k(), itemstack, 6, enumfacing, iposition);
      return p_82487_2_;
   }

   public static void func_82486_a(World p_82486_0_, ItemStack p_82486_1_, int p_82486_2_, EnumFacing p_82486_3_, IPosition p_82486_4_) {
      double d0 = p_82486_4_.func_82615_a();
      double d1 = p_82486_4_.func_82617_b();
      double d2 = p_82486_4_.func_82616_c();
      if (p_82486_3_.func_176740_k() == EnumFacing.Axis.Y) {
         d1 = d1 - 0.125D;
      } else {
         d1 = d1 - 0.15625D;
      }

      EntityItem entityitem = new EntityItem(p_82486_0_, d0, d1, d2, p_82486_1_);
      double d3 = p_82486_0_.field_73012_v.nextDouble() * 0.1D + 0.2D;
      entityitem.field_70159_w = (double)p_82486_3_.func_82601_c() * d3;
      entityitem.field_70181_x = 0.20000000298023224D;
      entityitem.field_70179_y = (double)p_82486_3_.func_82599_e() * d3;
      entityitem.field_70159_w += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
      entityitem.field_70181_x += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
      entityitem.field_70179_y += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
      p_82486_0_.func_72838_d(entityitem);
   }

   protected void func_82485_a(IBlockSource p_82485_1_) {
      p_82485_1_.func_82618_k().func_175718_b(1000, p_82485_1_.func_180699_d(), 0);
   }

   protected void func_82489_a(IBlockSource p_82489_1_, EnumFacing p_82489_2_) {
      p_82489_1_.func_82618_k().func_175718_b(2000, p_82489_1_.func_180699_d(), this.func_82488_a(p_82489_2_));
   }

   private int func_82488_a(EnumFacing p_82488_1_) {
      return p_82488_1_.func_82601_c() + 1 + (p_82488_1_.func_82599_e() + 1) * 3;
   }
}
