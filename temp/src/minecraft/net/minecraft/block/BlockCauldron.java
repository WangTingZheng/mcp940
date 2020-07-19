package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   public static final PropertyInteger field_176591_a = PropertyInteger.func_177719_a("level", 0, 3);
   protected static final AxisAlignedBB field_185596_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
   protected static final AxisAlignedBB field_185597_c = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
   protected static final AxisAlignedBB field_185598_d = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185599_e = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
   protected static final AxisAlignedBB field_185600_f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

   public BlockCauldron() {
      super(Material.field_151573_f, MapColor.field_151665_m);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176591_a, Integer.valueOf(0)));
   }

   public void func_185477_a(IBlockState p_185477_1_, World p_185477_2_, BlockPos p_185477_3_, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_, boolean p_185477_7_) {
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185596_b);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185600_f);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185597_c);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185599_e);
      func_185492_a(p_185477_3_, p_185477_4_, p_185477_5_, field_185598_d);
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185505_j;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      int i = ((Integer)p_180634_3_.func_177229_b(field_176591_a)).intValue();
      float f = (float)p_180634_2_.func_177956_o() + (6.0F + (float)(3 * i)) / 16.0F;
      if (!p_180634_1_.field_72995_K && p_180634_4_.func_70027_ad() && i > 0 && p_180634_4_.func_174813_aQ().field_72338_b <= (double)f) {
         p_180634_4_.func_70066_B();
         this.func_176590_a(p_180634_1_, p_180634_2_, p_180634_3_, i - 1);
      }

   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      ItemStack itemstack = p_180639_4_.func_184586_b(p_180639_5_);
      if (itemstack.func_190926_b()) {
         return true;
      } else {
         int i = ((Integer)p_180639_3_.func_177229_b(field_176591_a)).intValue();
         Item item = itemstack.func_77973_b();
         if (item == Items.field_151131_as) {
            if (i < 3 && !p_180639_1_.field_72995_K) {
               if (!p_180639_4_.field_71075_bZ.field_75098_d) {
                  p_180639_4_.func_184611_a(p_180639_5_, new ItemStack(Items.field_151133_ar));
               }

               p_180639_4_.func_71029_a(StatList.field_188077_K);
               this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, 3);
               p_180639_1_.func_184133_a((EntityPlayer)null, p_180639_2_, SoundEvents.field_187624_K, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (item == Items.field_151133_ar) {
            if (i == 3 && !p_180639_1_.field_72995_K) {
               if (!p_180639_4_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
                  if (itemstack.func_190926_b()) {
                     p_180639_4_.func_184611_a(p_180639_5_, new ItemStack(Items.field_151131_as));
                  } else if (!p_180639_4_.field_71071_by.func_70441_a(new ItemStack(Items.field_151131_as))) {
                     p_180639_4_.func_71019_a(new ItemStack(Items.field_151131_as), false);
                  }
               }

               p_180639_4_.func_71029_a(StatList.field_188078_L);
               this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, 0);
               p_180639_1_.func_184133_a((EntityPlayer)null, p_180639_2_, SoundEvents.field_187630_M, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (item == Items.field_151069_bo) {
            if (i > 0 && !p_180639_1_.field_72995_K) {
               if (!p_180639_4_.field_71075_bZ.field_75098_d) {
                  ItemStack itemstack3 = PotionUtils.func_185188_a(new ItemStack(Items.field_151068_bn), PotionTypes.field_185230_b);
                  p_180639_4_.func_71029_a(StatList.field_188078_L);
                  itemstack.func_190918_g(1);
                  if (itemstack.func_190926_b()) {
                     p_180639_4_.func_184611_a(p_180639_5_, itemstack3);
                  } else if (!p_180639_4_.field_71071_by.func_70441_a(itemstack3)) {
                     p_180639_4_.func_71019_a(itemstack3, false);
                  } else if (p_180639_4_ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)p_180639_4_).func_71120_a(p_180639_4_.field_71069_bz);
                  }
               }

               p_180639_1_.func_184133_a((EntityPlayer)null, p_180639_2_, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, i - 1);
            }

            return true;
         } else if (item == Items.field_151068_bn && PotionUtils.func_185191_c(itemstack) == PotionTypes.field_185230_b) {
            if (i < 3 && !p_180639_1_.field_72995_K) {
               if (!p_180639_4_.field_71075_bZ.field_75098_d) {
                  ItemStack itemstack2 = new ItemStack(Items.field_151069_bo);
                  p_180639_4_.func_71029_a(StatList.field_188078_L);
                  p_180639_4_.func_184611_a(p_180639_5_, itemstack2);
                  if (p_180639_4_ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)p_180639_4_).func_71120_a(p_180639_4_.field_71069_bz);
                  }
               }

               p_180639_1_.func_184133_a((EntityPlayer)null, p_180639_2_, SoundEvents.field_191241_J, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, i + 1);
            }

            return true;
         } else {
            if (i > 0 && item instanceof ItemArmor) {
               ItemArmor itemarmor = (ItemArmor)item;
               if (itemarmor.func_82812_d() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.func_82816_b_(itemstack) && !p_180639_1_.field_72995_K) {
                  itemarmor.func_82815_c(itemstack);
                  this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, i - 1);
                  p_180639_4_.func_71029_a(StatList.field_188079_M);
                  return true;
               }
            }

            if (i > 0 && item instanceof ItemBanner) {
               if (TileEntityBanner.func_175113_c(itemstack) > 0 && !p_180639_1_.field_72995_K) {
                  ItemStack itemstack1 = itemstack.func_77946_l();
                  itemstack1.func_190920_e(1);
                  TileEntityBanner.func_175117_e(itemstack1);
                  p_180639_4_.func_71029_a(StatList.field_188080_N);
                  if (!p_180639_4_.field_71075_bZ.field_75098_d) {
                     itemstack.func_190918_g(1);
                     this.func_176590_a(p_180639_1_, p_180639_2_, p_180639_3_, i - 1);
                  }

                  if (itemstack.func_190926_b()) {
                     p_180639_4_.func_184611_a(p_180639_5_, itemstack1);
                  } else if (!p_180639_4_.field_71071_by.func_70441_a(itemstack1)) {
                     p_180639_4_.func_71019_a(itemstack1, false);
                  } else if (p_180639_4_ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)p_180639_4_).func_71120_a(p_180639_4_.field_71069_bz);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void func_176590_a(World p_176590_1_, BlockPos p_176590_2_, IBlockState p_176590_3_, int p_176590_4_) {
      p_176590_1_.func_180501_a(p_176590_2_, p_176590_3_.func_177226_a(field_176591_a, Integer.valueOf(MathHelper.func_76125_a(p_176590_4_, 0, 3))), 2);
      p_176590_1_.func_175666_e(p_176590_2_, this);
   }

   public void func_176224_k(World p_176224_1_, BlockPos p_176224_2_) {
      if (p_176224_1_.field_73012_v.nextInt(20) == 1) {
         float f = p_176224_1_.func_180494_b(p_176224_2_).func_180626_a(p_176224_2_);
         if (p_176224_1_.func_72959_q().func_76939_a(f, p_176224_2_.func_177956_o()) >= 0.15F) {
            IBlockState iblockstate = p_176224_1_.func_180495_p(p_176224_2_);
            if (((Integer)iblockstate.func_177229_b(field_176591_a)).intValue() < 3) {
               p_176224_1_.func_180501_a(p_176224_2_, iblockstate.func_177231_a(field_176591_a), 2);
            }

         }
      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return Items.field_151066_bu;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      return new ItemStack(Items.field_151066_bu);
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      return ((Integer)p_180641_1_.func_177229_b(field_176591_a)).intValue();
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176591_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176591_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176591_a});
   }

   public boolean func_176205_b(IBlockAccess p_176205_1_, BlockPos p_176205_2_) {
      return true;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      if (p_193383_4_ == EnumFacing.UP) {
         return BlockFaceShape.BOWL;
      } else {
         return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
      }
   }
}
