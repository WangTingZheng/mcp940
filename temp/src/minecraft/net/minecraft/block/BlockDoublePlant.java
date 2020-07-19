package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoublePlant extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockDoublePlant.EnumPlantType> field_176493_a = PropertyEnum.<BlockDoublePlant.EnumPlantType>func_177709_a("variant", BlockDoublePlant.EnumPlantType.class);
   public static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> field_176492_b = PropertyEnum.<BlockDoublePlant.EnumBlockHalf>func_177709_a("half", BlockDoublePlant.EnumBlockHalf.class);
   public static final PropertyEnum<EnumFacing> field_181084_N = BlockHorizontal.field_185512_D;

   public BlockDoublePlant() {
      super(Material.field_151582_l);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176493_a, BlockDoublePlant.EnumPlantType.SUNFLOWER).func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.LOWER).func_177226_a(field_181084_N, EnumFacing.NORTH));
      this.func_149711_c(0.0F);
      this.func_149672_a(SoundType.field_185850_c);
      this.func_149663_c("doublePlant");
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185505_j;
   }

   private BlockDoublePlant.EnumPlantType func_185517_a(IBlockAccess p_185517_1_, BlockPos p_185517_2_, IBlockState p_185517_3_) {
      if (p_185517_3_.func_177230_c() == this) {
         p_185517_3_ = p_185517_3_.func_185899_b(p_185517_1_, p_185517_2_);
         return (BlockDoublePlant.EnumPlantType)p_185517_3_.func_177229_b(field_176493_a);
      } else {
         return BlockDoublePlant.EnumPlantType.FERN;
      }
   }

   public boolean func_176196_c(World p_176196_1_, BlockPos p_176196_2_) {
      return super.func_176196_c(p_176196_1_, p_176196_2_) && p_176196_1_.func_175623_d(p_176196_2_.func_177984_a());
   }

   public boolean func_176200_f(IBlockAccess p_176200_1_, BlockPos p_176200_2_) {
      IBlockState iblockstate = p_176200_1_.func_180495_p(p_176200_2_);
      if (iblockstate.func_177230_c() != this) {
         return true;
      } else {
         BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)iblockstate.func_185899_b(p_176200_1_, p_176200_2_).func_177229_b(field_176493_a);
         return blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.FERN || blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.GRASS;
      }
   }

   protected void func_176475_e(World p_176475_1_, BlockPos p_176475_2_, IBlockState p_176475_3_) {
      if (!this.func_180671_f(p_176475_1_, p_176475_2_, p_176475_3_)) {
         boolean flag = p_176475_3_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER;
         BlockPos blockpos = flag ? p_176475_2_ : p_176475_2_.func_177984_a();
         BlockPos blockpos1 = flag ? p_176475_2_.func_177977_b() : p_176475_2_;
         Block block = (Block)(flag ? this : p_176475_1_.func_180495_p(blockpos).func_177230_c());
         Block block1 = (Block)(flag ? p_176475_1_.func_180495_p(blockpos1).func_177230_c() : this);
         if (block == this) {
            p_176475_1_.func_180501_a(blockpos, Blocks.field_150350_a.func_176223_P(), 2);
         }

         if (block1 == this) {
            p_176475_1_.func_180501_a(blockpos1, Blocks.field_150350_a.func_176223_P(), 3);
            if (!flag) {
               this.func_176226_b(p_176475_1_, blockpos1, p_176475_3_, 0);
            }
         }

      }
   }

   public boolean func_180671_f(World p_180671_1_, BlockPos p_180671_2_, IBlockState p_180671_3_) {
      if (p_180671_3_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return p_180671_1_.func_180495_p(p_180671_2_.func_177977_b()).func_177230_c() == this;
      } else {
         IBlockState iblockstate = p_180671_1_.func_180495_p(p_180671_2_.func_177984_a());
         return iblockstate.func_177230_c() == this && super.func_180671_f(p_180671_1_, p_180671_2_, iblockstate);
      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      if (p_180660_1_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return Items.field_190931_a;
      } else {
         BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)p_180660_1_.func_177229_b(field_176493_a);
         if (blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.FERN) {
            return Items.field_190931_a;
         } else if (blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.GRASS) {
            return p_180660_2_.nextInt(8) == 0 ? Items.field_151014_N : Items.field_190931_a;
         } else {
            return super.func_180660_a(p_180660_1_, p_180660_2_, p_180660_3_);
         }
      }
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return p_180651_1_.func_177229_b(field_176492_b) != BlockDoublePlant.EnumBlockHalf.UPPER && p_180651_1_.func_177229_b(field_176493_a) != BlockDoublePlant.EnumPlantType.GRASS ? ((BlockDoublePlant.EnumPlantType)p_180651_1_.func_177229_b(field_176493_a)).func_176936_a() : 0;
   }

   public void func_176491_a(World p_176491_1_, BlockPos p_176491_2_, BlockDoublePlant.EnumPlantType p_176491_3_, int p_176491_4_) {
      p_176491_1_.func_180501_a(p_176491_2_, this.func_176223_P().func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.LOWER).func_177226_a(field_176493_a, p_176491_3_), p_176491_4_);
      p_176491_1_.func_180501_a(p_176491_2_.func_177984_a(), this.func_176223_P().func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.UPPER), p_176491_4_);
   }

   public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, IBlockState p_180633_3_, EntityLivingBase p_180633_4_, ItemStack p_180633_5_) {
      p_180633_1_.func_180501_a(p_180633_2_.func_177984_a(), this.func_176223_P().func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, @Nullable TileEntity p_180657_5_, ItemStack p_180657_6_) {
      if (p_180657_1_.field_72995_K || p_180657_6_.func_77973_b() != Items.field_151097_aZ || p_180657_4_.func_177229_b(field_176492_b) != BlockDoublePlant.EnumBlockHalf.LOWER || !this.func_176489_b(p_180657_1_, p_180657_3_, p_180657_4_, p_180657_2_)) {
         super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, p_180657_5_, p_180657_6_);
      }
   }

   public void func_176208_a(World p_176208_1_, BlockPos p_176208_2_, IBlockState p_176208_3_, EntityPlayer p_176208_4_) {
      if (p_176208_3_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         if (p_176208_1_.func_180495_p(p_176208_2_.func_177977_b()).func_177230_c() == this) {
            if (p_176208_4_.field_71075_bZ.field_75098_d) {
               p_176208_1_.func_175698_g(p_176208_2_.func_177977_b());
            } else {
               IBlockState iblockstate = p_176208_1_.func_180495_p(p_176208_2_.func_177977_b());
               BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)iblockstate.func_177229_b(field_176493_a);
               if (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS) {
                  p_176208_1_.func_175655_b(p_176208_2_.func_177977_b(), true);
               } else if (p_176208_1_.field_72995_K) {
                  p_176208_1_.func_175698_g(p_176208_2_.func_177977_b());
               } else if (!p_176208_4_.func_184614_ca().func_190926_b() && p_176208_4_.func_184614_ca().func_77973_b() == Items.field_151097_aZ) {
                  this.func_176489_b(p_176208_1_, p_176208_2_, iblockstate, p_176208_4_);
                  p_176208_1_.func_175698_g(p_176208_2_.func_177977_b());
               } else {
                  p_176208_1_.func_175655_b(p_176208_2_.func_177977_b(), true);
               }
            }
         }
      } else if (p_176208_1_.func_180495_p(p_176208_2_.func_177984_a()).func_177230_c() == this) {
         p_176208_1_.func_180501_a(p_176208_2_.func_177984_a(), Blocks.field_150350_a.func_176223_P(), 2);
      }

      super.func_176208_a(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
   }

   private boolean func_176489_b(World p_176489_1_, BlockPos p_176489_2_, IBlockState p_176489_3_, EntityPlayer p_176489_4_) {
      BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)p_176489_3_.func_177229_b(field_176493_a);
      if (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS) {
         return false;
      } else {
         p_176489_4_.func_71029_a(StatList.func_188055_a(this));
         int i = (blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.GRASS ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).func_177044_a();
         func_180635_a(p_176489_1_, p_176489_2_, new ItemStack(Blocks.field_150329_H, 2, i));
         return true;
      }
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype : BlockDoublePlant.EnumPlantType.values()) {
         p_149666_2_.add(new ItemStack(this, 1, blockdoubleplant$enumplanttype.func_176936_a()));
      }

   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(this, 1, this.func_185517_a(p_185473_1_, p_185473_2_, p_185473_3_).func_176936_a());
   }

   public boolean func_176473_a(World p_176473_1_, BlockPos p_176473_2_, IBlockState p_176473_3_, boolean p_176473_4_) {
      BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = this.func_185517_a(p_176473_1_, p_176473_2_, p_176473_3_);
      return blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN;
   }

   public boolean func_180670_a(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, IBlockState p_180670_4_) {
      return true;
   }

   public void func_176474_b(World p_176474_1_, Random p_176474_2_, BlockPos p_176474_3_, IBlockState p_176474_4_) {
      func_180635_a(p_176474_1_, p_176474_3_, new ItemStack(this, 1, this.func_185517_a(p_176474_1_, p_176474_3_, p_176474_4_).func_176936_a()));
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return (p_176203_1_ & 8) > 0 ? this.func_176223_P().func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.UPPER) : this.func_176223_P().func_177226_a(field_176492_b, BlockDoublePlant.EnumBlockHalf.LOWER).func_177226_a(field_176493_a, BlockDoublePlant.EnumPlantType.func_176938_a(p_176203_1_ & 7));
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      if (p_176221_1_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         IBlockState iblockstate = p_176221_2_.func_180495_p(p_176221_3_.func_177977_b());
         if (iblockstate.func_177230_c() == this) {
            p_176221_1_ = p_176221_1_.func_177226_a(field_176493_a, iblockstate.func_177229_b(field_176493_a));
         }
      }

      return p_176221_1_;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return p_176201_1_.func_177229_b(field_176492_b) == BlockDoublePlant.EnumBlockHalf.UPPER ? 8 | ((EnumFacing)p_176201_1_.func_177229_b(field_181084_N)).func_176736_b() : ((BlockDoublePlant.EnumPlantType)p_176201_1_.func_177229_b(field_176493_a)).func_176936_a();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176492_b, field_176493_a, field_181084_N});
   }

   public Block.EnumOffsetType func_176218_Q() {
      return Block.EnumOffsetType.XZ;
   }

   public static enum EnumBlockHalf implements IStringSerializable {
      UPPER,
      LOWER;

      public String toString() {
         return this.func_176610_l();
      }

      public String func_176610_l() {
         return this == UPPER ? "upper" : "lower";
      }
   }

   public static enum EnumPlantType implements IStringSerializable {
      SUNFLOWER(0, "sunflower"),
      SYRINGA(1, "syringa"),
      GRASS(2, "double_grass", "grass"),
      FERN(3, "double_fern", "fern"),
      ROSE(4, "double_rose", "rose"),
      PAEONIA(5, "paeonia");

      private static final BlockDoublePlant.EnumPlantType[] field_176941_g = new BlockDoublePlant.EnumPlantType[values().length];
      private final int field_176949_h;
      private final String field_176950_i;
      private final String field_176947_j;

      private EnumPlantType(int p_i45722_3_, String p_i45722_4_) {
         this(p_i45722_3_, p_i45722_4_, p_i45722_4_);
      }

      private EnumPlantType(int p_i45723_3_, String p_i45723_4_, String p_i45723_5_) {
         this.field_176949_h = p_i45723_3_;
         this.field_176950_i = p_i45723_4_;
         this.field_176947_j = p_i45723_5_;
      }

      public int func_176936_a() {
         return this.field_176949_h;
      }

      public String toString() {
         return this.field_176950_i;
      }

      public static BlockDoublePlant.EnumPlantType func_176938_a(int p_176938_0_) {
         if (p_176938_0_ < 0 || p_176938_0_ >= field_176941_g.length) {
            p_176938_0_ = 0;
         }

         return field_176941_g[p_176938_0_];
      }

      public String func_176610_l() {
         return this.field_176950_i;
      }

      public String func_176939_c() {
         return this.field_176947_j;
      }

      static {
         for(BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype : values()) {
            field_176941_g[blockdoubleplant$enumplanttype.func_176936_a()] = blockdoubleplant$enumplanttype;
         }

      }
   }
}
