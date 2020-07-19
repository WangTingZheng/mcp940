package net.minecraft.block;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDropper extends BlockDispenser {
   private final IBehaviorDispenseItem field_149947_P = new BehaviorDefaultDispenseItem();

   protected IBehaviorDispenseItem func_149940_a(ItemStack p_149940_1_) {
      return this.field_149947_P;
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityDropper();
   }

   protected void func_176439_d(World p_176439_1_, BlockPos p_176439_2_) {
      BlockSourceImpl blocksourceimpl = new BlockSourceImpl(p_176439_1_, p_176439_2_);
      TileEntityDispenser tileentitydispenser = (TileEntityDispenser)blocksourceimpl.func_150835_j();
      if (tileentitydispenser != null) {
         int i = tileentitydispenser.func_146017_i();
         if (i < 0) {
            p_176439_1_.func_175718_b(1001, p_176439_2_, 0);
         } else {
            ItemStack itemstack = tileentitydispenser.func_70301_a(i);
            if (!itemstack.func_190926_b()) {
               EnumFacing enumfacing = (EnumFacing)p_176439_1_.func_180495_p(p_176439_2_).func_177229_b(field_176441_a);
               BlockPos blockpos = p_176439_2_.func_177972_a(enumfacing);
               IInventory iinventory = TileEntityHopper.func_145893_b(p_176439_1_, (double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
               ItemStack itemstack1;
               if (iinventory == null) {
                  itemstack1 = this.field_149947_P.func_82482_a(blocksourceimpl, itemstack);
               } else {
                  itemstack1 = TileEntityHopper.func_174918_a(tileentitydispenser, iinventory, itemstack.func_77946_l().func_77979_a(1), enumfacing.func_176734_d());
                  if (itemstack1.func_190926_b()) {
                     itemstack1 = itemstack.func_77946_l();
                     itemstack1.func_190918_g(1);
                  } else {
                     itemstack1 = itemstack.func_77946_l();
                  }
               }

               tileentitydispenser.func_70299_a(i, itemstack1);
            }
         }
      }
   }
}
