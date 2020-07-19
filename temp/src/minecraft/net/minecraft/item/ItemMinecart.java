package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem field_96602_b = new BehaviorDefaultDispenseItem() {
      private final BehaviorDefaultDispenseItem field_96465_b = new BehaviorDefaultDispenseItem();

      public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
         EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
         World world = p_82487_1_.func_82618_k();
         double d0 = p_82487_1_.func_82615_a() + (double)enumfacing.func_82601_c() * 1.125D;
         double d1 = Math.floor(p_82487_1_.func_82617_b()) + (double)enumfacing.func_96559_d();
         double d2 = p_82487_1_.func_82616_c() + (double)enumfacing.func_82599_e() * 1.125D;
         BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a(enumfacing);
         IBlockState iblockstate = world.func_180495_p(blockpos);
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.func_177230_c() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection)iblockstate.func_177229_b(((BlockRailBase)iblockstate.func_177230_c()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         double d3;
         if (BlockRailBase.func_176563_d(iblockstate)) {
            if (blockrailbase$enumraildirection.func_177018_c()) {
               d3 = 0.6D;
            } else {
               d3 = 0.1D;
            }
         } else {
            if (iblockstate.func_185904_a() != Material.field_151579_a || !BlockRailBase.func_176563_d(world.func_180495_p(blockpos.func_177977_b()))) {
               return this.field_96465_b.func_82482_a(p_82487_1_, p_82487_2_);
            }

            IBlockState iblockstate1 = world.func_180495_p(blockpos.func_177977_b());
            BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = iblockstate1.func_177230_c() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection)iblockstate1.func_177229_b(((BlockRailBase)iblockstate1.func_177230_c()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.func_177018_c()) {
               d3 = -0.4D;
            } else {
               d3 = -0.9D;
            }
         }

         EntityMinecart entityminecart = EntityMinecart.func_184263_a(world, d0, d1 + d3, d2, ((ItemMinecart)p_82487_2_.func_77973_b()).field_77841_a);
         if (p_82487_2_.func_82837_s()) {
            entityminecart.func_96094_a(p_82487_2_.func_82833_r());
         }

         world.func_72838_d(entityminecart);
         p_82487_2_.func_190918_g(1);
         return p_82487_2_;
      }

      protected void func_82485_a(IBlockSource p_82485_1_) {
         p_82485_1_.func_82618_k().func_175718_b(1000, p_82485_1_.func_180699_d(), 0);
      }
   };
   private final EntityMinecart.Type field_77841_a;

   public ItemMinecart(EntityMinecart.Type p_i46743_1_) {
      this.field_77777_bU = 1;
      this.field_77841_a = p_i46743_1_;
      this.func_77637_a(CreativeTabs.field_78029_e);
      BlockDispenser.field_149943_a.func_82595_a(this, field_96602_b);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      if (!BlockRailBase.func_176563_d(iblockstate)) {
         return EnumActionResult.FAIL;
      } else {
         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         if (!p_180614_2_.field_72995_K) {
            BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.func_177230_c() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection)iblockstate.func_177229_b(((BlockRailBase)iblockstate.func_177230_c()).func_176560_l()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            double d0 = 0.0D;
            if (blockrailbase$enumraildirection.func_177018_c()) {
               d0 = 0.5D;
            }

            EntityMinecart entityminecart = EntityMinecart.func_184263_a(p_180614_2_, (double)p_180614_3_.func_177958_n() + 0.5D, (double)p_180614_3_.func_177956_o() + 0.0625D + d0, (double)p_180614_3_.func_177952_p() + 0.5D, this.field_77841_a);
            if (itemstack.func_82837_s()) {
               entityminecart.func_96094_a(itemstack.func_82833_r());
            }

            p_180614_2_.func_72838_d(entityminecart);
         }

         itemstack.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      }
   }
}
