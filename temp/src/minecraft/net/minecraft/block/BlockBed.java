package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBed extends BlockHorizontal implements ITileEntityProvider {
   public static final PropertyEnum<BlockBed.EnumPartType> field_176472_a = PropertyEnum.<BlockBed.EnumPartType>func_177709_a("part", BlockBed.EnumPartType.class);
   public static final PropertyBool field_176471_b = PropertyBool.func_177716_a("occupied");
   protected static final AxisAlignedBB field_185513_c = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);

   public BlockBed() {
      super(Material.field_151580_n);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176472_a, BlockBed.EnumPartType.FOOT).func_177226_a(field_176471_b, Boolean.valueOf(false)));
      this.field_149758_A = true;
   }

   public MapColor func_180659_g(IBlockState p_180659_1_, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
      if (p_180659_1_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT) {
         TileEntity tileentity = p_180659_2_.func_175625_s(p_180659_3_);
         if (tileentity instanceof TileEntityBed) {
            EnumDyeColor enumdyecolor = ((TileEntityBed)tileentity).func_193048_a();
            return MapColor.func_193558_a(enumdyecolor);
         }
      }

      return MapColor.field_151659_e;
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         if (p_180639_3_.func_177229_b(field_176472_a) != BlockBed.EnumPartType.HEAD) {
            p_180639_2_ = p_180639_2_.func_177972_a((EnumFacing)p_180639_3_.func_177229_b(field_185512_D));
            p_180639_3_ = p_180639_1_.func_180495_p(p_180639_2_);
            if (p_180639_3_.func_177230_c() != this) {
               return true;
            }
         }

         if (p_180639_1_.field_73011_w.func_76567_e() && p_180639_1_.func_180494_b(p_180639_2_) != Biomes.field_76778_j) {
            if (((Boolean)p_180639_3_.func_177229_b(field_176471_b)).booleanValue()) {
               EntityPlayer entityplayer = this.func_176470_e(p_180639_1_, p_180639_2_);
               if (entityplayer != null) {
                  p_180639_4_.func_146105_b(new TextComponentTranslation("tile.bed.occupied", new Object[0]), true);
                  return true;
               }

               p_180639_3_ = p_180639_3_.func_177226_a(field_176471_b, Boolean.valueOf(false));
               p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 4);
            }

            EntityPlayer.SleepResult entityplayer$sleepresult = p_180639_4_.func_180469_a(p_180639_2_);
            if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
               p_180639_3_ = p_180639_3_.func_177226_a(field_176471_b, Boolean.valueOf(true));
               p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 4);
               return true;
            } else {
               if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
                  p_180639_4_.func_146105_b(new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
               } else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE) {
                  p_180639_4_.func_146105_b(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
               } else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY) {
                  p_180639_4_.func_146105_b(new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
               }

               return true;
            }
         } else {
            p_180639_1_.func_175698_g(p_180639_2_);
            BlockPos blockpos = p_180639_2_.func_177972_a(((EnumFacing)p_180639_3_.func_177229_b(field_185512_D)).func_176734_d());
            if (p_180639_1_.func_180495_p(blockpos).func_177230_c() == this) {
               p_180639_1_.func_175698_g(blockpos);
            }

            p_180639_1_.func_72885_a((Entity)null, (double)p_180639_2_.func_177958_n() + 0.5D, (double)p_180639_2_.func_177956_o() + 0.5D, (double)p_180639_2_.func_177952_p() + 0.5D, 5.0F, true, true);
            return true;
         }
      }
   }

   @Nullable
   private EntityPlayer func_176470_e(World p_176470_1_, BlockPos p_176470_2_) {
      for(EntityPlayer entityplayer : p_176470_1_.field_73010_i) {
         if (entityplayer.func_70608_bn() && entityplayer.field_71081_bT.equals(p_176470_2_)) {
            return entityplayer;
         }
      }

      return null;
   }

   public boolean func_149686_d(IBlockState p_149686_1_) {
      return false;
   }

   public boolean func_149662_c(IBlockState p_149662_1_) {
      return false;
   }

   public void func_180658_a(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
      super.func_180658_a(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_ * 0.5F);
   }

   public void func_176216_a(World p_176216_1_, Entity p_176216_2_) {
      if (p_176216_2_.func_70093_af()) {
         super.func_176216_a(p_176216_1_, p_176216_2_);
      } else if (p_176216_2_.field_70181_x < 0.0D) {
         p_176216_2_.field_70181_x = -p_176216_2_.field_70181_x * 0.6600000262260437D;
         if (!(p_176216_2_ instanceof EntityLivingBase)) {
            p_176216_2_.field_70181_x *= 0.8D;
         }
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      EnumFacing enumfacing = (EnumFacing)p_189540_1_.func_177229_b(field_185512_D);
      if (p_189540_1_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT) {
         if (p_189540_2_.func_180495_p(p_189540_3_.func_177972_a(enumfacing)).func_177230_c() != this) {
            p_189540_2_.func_175698_g(p_189540_3_);
         }
      } else if (p_189540_2_.func_180495_p(p_189540_3_.func_177972_a(enumfacing.func_176734_d())).func_177230_c() != this) {
         if (!p_189540_2_.field_72995_K) {
            this.func_176226_b(p_189540_2_, p_189540_3_, p_189540_1_, 0);
         }

         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return p_180660_1_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT ? Items.field_190931_a : Items.field_151104_aV;
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return field_185513_c;
   }

   public boolean func_190946_v(IBlockState p_190946_1_) {
      return true;
   }

   @Nullable
   public static BlockPos func_176468_a(World p_176468_0_, BlockPos p_176468_1_, int p_176468_2_) {
      EnumFacing enumfacing = (EnumFacing)p_176468_0_.func_180495_p(p_176468_1_).func_177229_b(field_185512_D);
      int i = p_176468_1_.func_177958_n();
      int j = p_176468_1_.func_177956_o();
      int k = p_176468_1_.func_177952_p();

      for(int l = 0; l <= 1; ++l) {
         int i1 = i - enumfacing.func_82601_c() * l - 1;
         int j1 = k - enumfacing.func_82599_e() * l - 1;
         int k1 = i1 + 2;
         int l1 = j1 + 2;

         for(int i2 = i1; i2 <= k1; ++i2) {
            for(int j2 = j1; j2 <= l1; ++j2) {
               BlockPos blockpos = new BlockPos(i2, j, j2);
               if (func_176469_d(p_176468_0_, blockpos)) {
                  if (p_176468_2_ <= 0) {
                     return blockpos;
                  }

                  --p_176468_2_;
               }
            }
         }
      }

      return null;
   }

   protected static boolean func_176469_d(World p_176469_0_, BlockPos p_176469_1_) {
      return p_176469_0_.func_180495_p(p_176469_1_.func_177977_b()).func_185896_q() && !p_176469_0_.func_180495_p(p_176469_1_).func_185904_a().func_76220_a() && !p_176469_0_.func_180495_p(p_176469_1_.func_177984_a()).func_185904_a().func_76220_a();
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      if (p_180653_3_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.HEAD) {
         TileEntity tileentity = p_180653_1_.func_175625_s(p_180653_2_);
         EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed)tileentity).func_193048_a() : EnumDyeColor.RED;
         func_180635_a(p_180653_1_, p_180653_2_, new ItemStack(Items.field_151104_aV, 1, enumdyecolor.func_176765_a()));
      }

   }

   public EnumPushReaction func_149656_h(IBlockState p_149656_1_) {
      return EnumPushReaction.DESTROY;
   }

   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public ItemStack func_185473_a(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
      BlockPos blockpos = p_185473_2_;
      if (p_185473_3_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT) {
         blockpos = p_185473_2_.func_177972_a((EnumFacing)p_185473_3_.func_177229_b(field_185512_D));
      }

      TileEntity tileentity = p_185473_1_.func_175625_s(blockpos);
      EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed)tileentity).func_193048_a() : EnumDyeColor.RED;
      return new ItemStack(Items.field_151104_aV, 1, enumdyecolor.func_176765_a());
   }

   public void func_176208_a(World p_176208_1_, BlockPos p_176208_2_, IBlockState p_176208_3_, EntityPlayer p_176208_4_) {
      if (p_176208_4_.field_71075_bZ.field_75098_d && p_176208_3_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT) {
         BlockPos blockpos = p_176208_2_.func_177972_a((EnumFacing)p_176208_3_.func_177229_b(field_185512_D));
         if (p_176208_1_.func_180495_p(blockpos).func_177230_c() == this) {
            p_176208_1_.func_175698_g(blockpos);
         }
      }

   }

   public void func_180657_a(World p_180657_1_, EntityPlayer p_180657_2_, BlockPos p_180657_3_, IBlockState p_180657_4_, TileEntity p_180657_5_, ItemStack p_180657_6_) {
      if (p_180657_4_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.HEAD && p_180657_5_ instanceof TileEntityBed) {
         TileEntityBed tileentitybed = (TileEntityBed)p_180657_5_;
         ItemStack itemstack = tileentitybed.func_193049_f();
         func_180635_a(p_180657_1_, p_180657_3_, itemstack);
      } else {
         super.func_180657_a(p_180657_1_, p_180657_2_, p_180657_3_, p_180657_4_, (TileEntity)null, p_180657_6_);
      }

   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
      p_180663_1_.func_175713_t(p_180663_2_);
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      EnumFacing enumfacing = EnumFacing.func_176731_b(p_176203_1_);
      return (p_176203_1_ & 8) > 0 ? this.func_176223_P().func_177226_a(field_176472_a, BlockBed.EnumPartType.HEAD).func_177226_a(field_185512_D, enumfacing).func_177226_a(field_176471_b, Boolean.valueOf((p_176203_1_ & 4) > 0)) : this.func_176223_P().func_177226_a(field_176472_a, BlockBed.EnumPartType.FOOT).func_177226_a(field_185512_D, enumfacing);
   }

   public IBlockState func_176221_a(IBlockState p_176221_1_, IBlockAccess p_176221_2_, BlockPos p_176221_3_) {
      if (p_176221_1_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.FOOT) {
         IBlockState iblockstate = p_176221_2_.func_180495_p(p_176221_3_.func_177972_a((EnumFacing)p_176221_1_.func_177229_b(field_185512_D)));
         if (iblockstate.func_177230_c() == this) {
            p_176221_1_ = p_176221_1_.func_177226_a(field_176471_b, iblockstate.func_177229_b(field_176471_b));
         }
      }

      return p_176221_1_;
   }

   public IBlockState func_185499_a(IBlockState p_185499_1_, Rotation p_185499_2_) {
      return p_185499_1_.func_177226_a(field_185512_D, p_185499_2_.func_185831_a((EnumFacing)p_185499_1_.func_177229_b(field_185512_D)));
   }

   public IBlockState func_185471_a(IBlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.func_185907_a(p_185471_2_.func_185800_a((EnumFacing)p_185471_1_.func_177229_b(field_185512_D)));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      int i = 0;
      i = i | ((EnumFacing)p_176201_1_.func_177229_b(field_185512_D)).func_176736_b();
      if (p_176201_1_.func_177229_b(field_176472_a) == BlockBed.EnumPartType.HEAD) {
         i |= 8;
         if (((Boolean)p_176201_1_.func_177229_b(field_176471_b)).booleanValue()) {
            i |= 4;
         }
      }

      return i;
   }

   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
      return BlockFaceShape.UNDEFINED;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_185512_D, field_176472_a, field_176471_b});
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new TileEntityBed();
   }

   public static boolean func_193385_b(int p_193385_0_) {
      return (p_193385_0_ & 8) != 0;
   }

   public static enum EnumPartType implements IStringSerializable {
      HEAD("head"),
      FOOT("foot");

      private final String field_177036_c;

      private EnumPartType(String p_i45735_3_) {
         this.field_177036_c = p_i45735_3_;
      }

      public String toString() {
         return this.field_177036_c;
      }

      public String func_176610_l() {
         return this.field_177036_c;
      }
   }
}
