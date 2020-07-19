package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
   public static final PropertyBool field_176464_a = PropertyBool.func_177716_a("powered");
   public static final PropertyEnum<BlockRedstoneComparator.Mode> field_176463_b = PropertyEnum.<BlockRedstoneComparator.Mode>func_177709_a("mode", BlockRedstoneComparator.Mode.class);

   public BlockRedstoneComparator(boolean p_i45399_1_) {
      super(p_i45399_1_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_185512_D, EnumFacing.NORTH).func_177226_a(field_176464_a, Boolean.valueOf(false)).func_177226_a(field_176463_b, BlockRedstoneComparator.Mode.COMPARE));
      this.field_149758_A = true;
   }

   public String func_149732_F() {
      return I18n.func_74838_a("item.comparator.name");
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151132_bS;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151132_bS);
   }

   protected int func_176403_d(IBlockState p_176403_1_) {
      return 2;
   }

   protected IBlockState func_180674_e(IBlockState p_180674_1_) {
      Boolean obool = (Boolean)p_180674_1_.func_177229_b(field_176464_a);
      BlockRedstoneComparator.Mode blockredstonecomparator$mode = (BlockRedstoneComparator.Mode)p_180674_1_.func_177229_b(field_176463_b);
      EnumFacing enumfacing = (EnumFacing)p_180674_1_.func_177229_b(field_185512_D);
      return Blocks.field_150455_bV.func_176223_P().func_177226_a(field_185512_D, enumfacing).func_177226_a(field_176464_a, obool).func_177226_a(field_176463_b, blockredstonecomparator$mode);
   }

   protected IBlockState func_180675_k(IBlockState p_180675_1_) {
      Boolean obool = (Boolean)p_180675_1_.func_177229_b(field_176464_a);
      BlockRedstoneComparator.Mode blockredstonecomparator$mode = (BlockRedstoneComparator.Mode)p_180675_1_.func_177229_b(field_176463_b);
      EnumFacing enumfacing = (EnumFacing)p_180675_1_.func_177229_b(field_185512_D);
      return Blocks.field_150441_bU.func_176223_P().func_177226_a(field_185512_D, enumfacing).func_177226_a(field_176464_a, obool).func_177226_a(field_176463_b, blockredstonecomparator$mode);
   }

   protected boolean func_176406_l(IBlockState p_176406_1_) {
      return this.field_149914_a || ((Boolean)p_176406_1_.func_177229_b(field_176464_a)).booleanValue();
   }

   protected int func_176408_a(IBlockAccess p_176408_1_, BlockPos p_176408_2_, IBlockState p_176408_3_) {
      TileEntity tileentity = p_176408_1_.func_175625_s(p_176408_2_);
      return tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).func_145996_a() : 0;
   }

   private int func_176460_j(World p_176460_1_, BlockPos p_176460_2_, IBlockState p_176460_3_) {
      return p_176460_3_.func_177229_b(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT ? Math.max(this.func_176397_f(p_176460_1_, p_176460_2_, p_176460_3_) - this.func_176407_c(p_176460_1_, p_176460_2_, p_176460_3_), 0) : this.func_176397_f(p_176460_1_, p_176460_2_, p_176460_3_);
   }

   protected boolean func_176404_e(World p_176404_1_, BlockPos p_176404_2_, IBlockState p_176404_3_) {
      int i = this.func_176397_f(p_176404_1_, p_176404_2_, p_176404_3_);
      if (i >= 15) {
         return true;
      } else if (i == 0) {
         return false;
      } else {
         int j = this.func_176407_c(p_176404_1_, p_176404_2_, p_176404_3_);
         if (j == 0) {
            return true;
         } else {
            return i >= j;
         }
      }
   }

   protected int func_176397_f(World p_176397_1_, BlockPos p_176397_2_, IBlockState p_176397_3_) {
      int i = super.func_176397_f(p_176397_1_, p_176397_2_, p_176397_3_);
      EnumFacing enumfacing = (EnumFacing)p_176397_3_.func_177229_b(field_185512_D);
      BlockPos blockpos = p_176397_2_.func_177972_a(enumfacing);
      IBlockState iblockstate = p_176397_1_.func_180495_p(blockpos);
      if (iblockstate.func_185912_n()) {
         i = iblockstate.func_185888_a(p_176397_1_, blockpos);
      } else if (i < 15 && iblockstate.func_185915_l()) {
         blockpos = blockpos.func_177972_a(enumfacing);
         iblockstate = p_176397_1_.func_180495_p(blockpos);
         if (iblockstate.func_185912_n()) {
            i = iblockstate.func_185888_a(p_176397_1_, blockpos);
         } else if (iblockstate.func_185904_a() == Material.field_151579_a) {
            EntityItemFrame entityitemframe = this.func_176461_a(p_176397_1_, enumfacing, blockpos);
            if (entityitemframe != null) {
               i = entityitemframe.func_174866_q();
            }
         }
      }

      return i;
   }

   @Nullable
   private EntityItemFrame func_176461_a(World p_176461_1_, final EnumFacing p_176461_2_, BlockPos p_176461_3_) {
      List<EntityItemFrame> list = p_176461_1_.<EntityItemFrame>func_175647_a(EntityItemFrame.class, new AxisAlignedBB((double)p_176461_3_.func_177958_n(), (double)p_176461_3_.func_177956_o(), (double)p_176461_3_.func_177952_p(), (double)(p_176461_3_.func_177958_n() + 1), (double)(p_176461_3_.func_177956_o() + 1), (double)(p_176461_3_.func_177952_p() + 1)), new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            return p_apply_1_ != null && p_apply_1_.func_174811_aO() == p_176461_2_;
         }
      });
      return list.size() == 1 ? (EntityItemFrame)list.get(0) : null;
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (!p_180639_4_.field_71075_bZ.field_75099_e) {
         return false;
      } else {
         p_180639_3_ = p_180639_3_.func_177231_a(field_176463_b);
         float f = p_180639_3_.func_177229_b(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT ? 0.55F : 0.5F;
         p_180639_1_.func_184133_a(p_180639_4_, p_180639_2_, SoundEvents.field_187556_aj, SoundCategory.BLOCKS, 0.3F, f);
         p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 2);
         this.func_176462_k(p_180639_1_, p_180639_2_, p_180639_3_);
         return true;
      }
   }

   protected void func_176398_g(World p_176398_1_, BlockPos p_176398_2_, IBlockState p_176398_3_) {
      if (!p_176398_1_.func_175691_a(p_176398_2_, this)) {
         int i = this.func_176460_j(p_176398_1_, p_176398_2_, p_176398_3_);
         TileEntity tileentity = p_176398_1_.func_175625_s(p_176398_2_);
         int j = tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).func_145996_a() : 0;
         if (i != j || this.func_176406_l(p_176398_3_) != this.func_176404_e(p_176398_1_, p_176398_2_, p_176398_3_)) {
            if (this.func_176402_i(p_176398_1_, p_176398_2_, p_176398_3_)) {
               p_176398_1_.func_175654_a(p_176398_2_, this, 2, -1);
            } else {
               p_176398_1_.func_175654_a(p_176398_2_, this, 2, 0);
            }
         }

      }
   }

   private void func_176462_k(World p_176462_1_, BlockPos p_176462_2_, IBlockState p_176462_3_) {
      int i = this.func_176460_j(p_176462_1_, p_176462_2_, p_176462_3_);
      TileEntity tileentity = p_176462_1_.func_175625_s(p_176462_2_);
      int j = 0;
      if (tileentity instanceof TileEntityComparator) {
         TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;
         j = tileentitycomparator.func_145996_a();
         tileentitycomparator.func_145995_a(i);
      }

      if (j != i || p_176462_3_.func_177229_b(field_176463_b) == BlockRedstoneComparator.Mode.COMPARE) {
         boolean flag1 = this.func_176404_e(p_176462_1_, p_176462_2_, p_176462_3_);
         boolean flag = this.func_176406_l(p_176462_3_);
         if (flag && !flag1) {
            p_176462_1_.func_180501_a(p_176462_2_, p_176462_3_.func_177226_a(field_176464_a, Boolean.valueOf(false)), 2);
         } else if (!flag && flag1) {
            p_176462_1_.func_180501_a(p_176462_2_, p_176462_3_.func_177226_a(field_176464_a, Boolean.valueOf(true)), 2);
         }

         this.func_176400_h(p_176462_1_, p_176462_2_, p_176462_3_);
      }

   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      if (this.field_149914_a) {
         p_180650_1_.func_180501_a(p_180650_2_, this.func_180675_k(p_180650_3_).func_177226_a(field_176464_a, Boolean.valueOf(true)), 4);
      }

      this.func_176462_k(p_180650_1_, p_180650_2_, p_180650_3_);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      super.func_176213_c(p_176213_1_, p_176213_2_, p_176213_3_);
      p_176213_1_.func_175690_a(p_176213_2_, this.func_149915_a(p_176213_1_, 0));
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
      p_180663_1_.func_175713_t(p_180663_2_);
      this.func_176400_h(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_) {
      super.func_189539_a(p_189539_1_, p_189539_2_, p_189539_3_, p_189539_4_, p_189539_5_);
      TileEntity tileentity = p_189539_2_.func_175625_s(p_189539_3_);
      return tileentity == null ? false : tileentity.func_145842_c(p_189539_4_, p_189539_5_);
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityComparator();
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_185512_D, EnumFacing.func_176731_b(p_176203_1_)).func_177226_a(field_176464_a, Boolean.valueOf((p_176203_1_ & 8) > 0)).func_177226_a(field_176463_b, (p_176203_1_ & 4) > 0 ? BlockRedstoneComparator.Mode.SUBTRACT : BlockRedstoneComparator.Mode.COMPARE);
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_185512_D)).func_176736_b();
      if (((Boolean)p_176201_1_.func_177229_b(field_176464_a)).booleanValue()) {
         i |= 8;
      }

      if (p_176201_1_.func_177229_b(field_176463_b) == BlockRedstoneComparator.Mode.SUBTRACT) {
         i |= 4;
      }

      return i;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_185512_D, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_185512_D)));
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185512_D, field_176463_b, field_176464_a});
   }

   public IBlockState func_180642_a(World p_180642_1_, BlockPos p_180642_2_, EnumFacing p_180642_3_, float p_180642_4_, float p_180642_5_, float p_180642_6_, int p_180642_7_, EntityLivingBase p_180642_8_) {
      return this.func_176223_P().func_177226_a(field_185512_D, p_180642_8_.func_174811_aO().func_176734_d()).func_177226_a(field_176464_a, Boolean.valueOf(false)).func_177226_a(field_176463_b, BlockRedstoneComparator.Mode.COMPARE);
   }

   public static enum Mode implements IStringSerializable {
      COMPARE("compare"),
      SUBTRACT("subtract");

      private final String field_177041_c;

      private Mode(String p_i45731_3_) {
         this.field_177041_c = p_i45731_3_;
      }

      public String toString() {
         return this.field_177041_c;
      }

      public String func_176610_l() {
         return this.field_177041_c;
      }
   }
}
