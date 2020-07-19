package net.minecraft.block;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOldLog extends BlockLog {
   public static final PropertyEnum<BlockPlanks.EnumType> field_176301_b = PropertyEnum.<BlockPlanks.EnumType>func_177708_a("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>() {
      public boolean apply(@Nullable BlockPlanks.EnumType p_apply_1_) {
         return p_apply_1_.func_176839_a() < 4;
      }
   });

   public BlockOldLog() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176301_b, BlockPlanks.EnumType.OAK).func_177226_a(field_176299_a, BlockLog.EnumAxis.Y));
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)p_180659_1_.func_177229_b(field_176301_b);
      switch((BlockLog.EnumAxis)p_180659_1_.func_177229_b(field_176299_a)) {
      case X:
      case Z:
      case NONE:
      default:
         switch(blockplanks$enumtype) {
         case OAK:
         default:
            return BlockPlanks.EnumType.SPRUCE.func_181070_c();
         case SPRUCE:
            return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
         case BIRCH:
            return MapColor.field_151677_p;
         case JUNGLE:
            return BlockPlanks.EnumType.SPRUCE.func_181070_c();
         }
      case Y:
         return blockplanks$enumtype.func_181070_c();
      }
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      p_149666_2_.add(new ItemStack(this, 1, BlockPlanks.EnumType.OAK.func_176839_a()));
      p_149666_2_.add(new ItemStack(this, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()));
      p_149666_2_.add(new ItemStack(this, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()));
      p_149666_2_.add(new ItemStack(this, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      IBlockState iblockstate = this.func_176223_P().func_177226_a(field_176301_b, BlockPlanks.EnumType.func_176837_a((p_176203_1_ & 3) % 4));
      switch(p_176203_1_ & 12) {
      case 0:
         iblockstate = iblockstate.func_177226_a(field_176299_a, BlockLog.EnumAxis.Y);
         break;
      case 4:
         iblockstate = iblockstate.func_177226_a(field_176299_a, BlockLog.EnumAxis.X);
         break;
      case 8:
         iblockstate = iblockstate.func_177226_a(field_176299_a, BlockLog.EnumAxis.Z);
         break;
      default:
         iblockstate = iblockstate.func_177226_a(field_176299_a, BlockLog.EnumAxis.NONE);
      }

      return iblockstate;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((BlockPlanks.EnumType)p_176201_1_.func_177229_b(field_176301_b)).func_176839_a();
      switch((BlockLog.EnumAxis)p_176201_1_.func_177229_b(field_176299_a)) {
      case X:
         i |= 4;
         break;
      case Z:
         i |= 8;
         break;
      case NONE:
         i |= 12;
      }

      return i;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176301_b, field_176299_a});
   }

   protected ItemStack func_180643_i(IBlockState p_180643_1_) {
      return new ItemStack(Item.func_150898_a(this), 1, ((BlockPlanks.EnumType)p_180643_1_.func_177229_b(field_176301_b)).func_176839_a());
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockPlanks.EnumType)p_180651_1_.func_177229_b(field_176301_b)).func_176839_a();
   }
}
