package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
   public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
      World world = p_82487_1_.func_82618_k();
      IPosition iposition = BlockDispenser.func_149939_a(p_82487_1_);
      EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
      IProjectile iprojectile = this.func_82499_a(world, iposition, p_82487_2_);
      iprojectile.func_70186_c((double)enumfacing.func_82601_c(), (double)((float)enumfacing.func_96559_d() + 0.1F), (double)enumfacing.func_82599_e(), this.func_82500_b(), this.func_82498_a());
      world.func_72838_d((Entity)iprojectile);
      p_82487_2_.func_190918_g(1);
      return p_82487_2_;
   }

   protected void func_82485_a(IBlockSource p_82485_1_) {
      p_82485_1_.func_82618_k().func_175718_b(1002, p_82485_1_.func_180699_d(), 0);
   }

   protected abstract IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3);

   protected float func_82498_a() {
      return 6.0F;
   }

   protected float func_82500_b() {
      return 1.1F;
   }
}
