package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {
   public static final PropertyBool field_176246_a = PropertyBool.func_177716_a("explode");

   public BlockTNT() {
      super(Material.field_151590_u);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176246_a, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78028_d);
   }

   public void func_176213_c(World p_176213_1_, BlockPos p_176213_2_, IBlockState p_176213_3_) {
      super.func_176213_c(p_176213_1_, p_176213_2_, p_176213_3_);
      if (p_176213_1_.func_175640_z(p_176213_2_)) {
         this.func_176206_d(p_176213_1_, p_176213_2_, p_176213_3_.func_177226_a(field_176246_a, Boolean.valueOf(true)));
         p_176213_1_.func_175698_g(p_176213_2_);
      }

   }

   public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_, BlockPos p_189540_5_) {
      if (p_189540_2_.func_175640_z(p_189540_3_)) {
         this.func_176206_d(p_189540_2_, p_189540_3_, p_189540_1_.func_177226_a(field_176246_a, Boolean.valueOf(true)));
         p_189540_2_.func_175698_g(p_189540_3_);
      }

   }

   public void func_180652_a(World p_180652_1_, BlockPos p_180652_2_, Explosion p_180652_3_) {
      if (!p_180652_1_.field_72995_K) {
         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(p_180652_1_, (double)((float)p_180652_2_.func_177958_n() + 0.5F), (double)p_180652_2_.func_177956_o(), (double)((float)p_180652_2_.func_177952_p() + 0.5F), p_180652_3_.func_94613_c());
         entitytntprimed.func_184534_a((short)(p_180652_1_.field_73012_v.nextInt(entitytntprimed.func_184536_l() / 4) + entitytntprimed.func_184536_l() / 8));
         p_180652_1_.func_72838_d(entitytntprimed);
      }
   }

   public void func_176206_d(World p_176206_1_, BlockPos p_176206_2_, IBlockState p_176206_3_) {
      this.func_180692_a(p_176206_1_, p_176206_2_, p_176206_3_, (EntityLivingBase)null);
   }

   public void func_180692_a(World p_180692_1_, BlockPos p_180692_2_, IBlockState p_180692_3_, EntityLivingBase p_180692_4_) {
      if (!p_180692_1_.field_72995_K) {
         if (((Boolean)p_180692_3_.func_177229_b(field_176246_a)).booleanValue()) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(p_180692_1_, (double)((float)p_180692_2_.func_177958_n() + 0.5F), (double)p_180692_2_.func_177956_o(), (double)((float)p_180692_2_.func_177952_p() + 0.5F), p_180692_4_);
            p_180692_1_.func_72838_d(entitytntprimed);
            p_180692_1_.func_184148_a((EntityPlayer)null, entitytntprimed.field_70165_t, entitytntprimed.field_70163_u, entitytntprimed.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

      }
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      ItemStack itemstack = p_180639_4_.func_184586_b(p_180639_5_);
      if (!itemstack.func_190926_b() && (itemstack.func_77973_b() == Items.field_151033_d || itemstack.func_77973_b() == Items.field_151059_bz)) {
         this.func_180692_a(p_180639_1_, p_180639_2_, p_180639_3_.func_177226_a(field_176246_a, Boolean.valueOf(true)), p_180639_4_);
         p_180639_1_.func_180501_a(p_180639_2_, Blocks.field_150350_a.func_176223_P(), 11);
         if (itemstack.func_77973_b() == Items.field_151033_d) {
            itemstack.func_77972_a(1, p_180639_4_);
         } else if (!p_180639_4_.field_71075_bZ.field_75098_d) {
            itemstack.func_190918_g(1);
         }

         return true;
      } else {
         return super.func_180639_a(p_180639_1_, p_180639_2_, p_180639_3_, p_180639_4_, p_180639_5_, p_180639_6_, p_180639_7_, p_180639_8_, p_180639_9_);
      }
   }

   public void func_180634_a(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_) {
      if (!p_180634_1_.field_72995_K && p_180634_4_ instanceof EntityArrow) {
         EntityArrow entityarrow = (EntityArrow)p_180634_4_;
         if (entityarrow.func_70027_ad()) {
            this.func_180692_a(p_180634_1_, p_180634_2_, p_180634_1_.func_180495_p(p_180634_2_).func_177226_a(field_176246_a, Boolean.valueOf(true)), entityarrow.field_70250_c instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.field_70250_c : null);
            p_180634_1_.func_175698_g(p_180634_2_);
         }
      }

   }

   public boolean func_149659_a(Explosion p_149659_1_) {
      return false;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176246_a, Boolean.valueOf((p_176203_1_ & 1) > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Boolean)p_176201_1_.func_177229_b(field_176246_a)).booleanValue() ? 1 : 0;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176246_a});
   }
}
