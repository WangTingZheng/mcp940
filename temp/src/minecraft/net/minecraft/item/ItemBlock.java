package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlock extends Item {
   protected final Block field_150939_a;

   public ItemBlock(Block p_i45328_1_) {
      this.field_150939_a = p_i45328_1_;
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      Block block = iblockstate.func_177230_c();
      if (!block.func_176200_f(p_180614_2_, p_180614_3_)) {
         p_180614_3_ = p_180614_3_.func_177972_a(p_180614_5_);
      }

      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!itemstack.func_190926_b() && p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack) && p_180614_2_.func_190527_a(this.field_150939_a, p_180614_3_, false, p_180614_5_, (Entity)null)) {
         int i = this.func_77647_b(itemstack.func_77960_j());
         IBlockState iblockstate1 = this.field_150939_a.func_180642_a(p_180614_2_, p_180614_3_, p_180614_5_, p_180614_6_, p_180614_7_, p_180614_8_, i, p_180614_1_);
         if (p_180614_2_.func_180501_a(p_180614_3_, iblockstate1, 11)) {
            iblockstate1 = p_180614_2_.func_180495_p(p_180614_3_);
            if (iblockstate1.func_177230_c() == this.field_150939_a) {
               func_179224_a(p_180614_2_, p_180614_1_, p_180614_3_, itemstack);
               this.field_150939_a.func_180633_a(p_180614_2_, p_180614_3_, iblockstate1, p_180614_1_, itemstack);
               if (p_180614_1_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_, itemstack);
               }
            }

            SoundType soundtype = this.field_150939_a.func_185467_w();
            p_180614_2_.func_184133_a(p_180614_1_, p_180614_3_, soundtype.func_185841_e(), SoundCategory.BLOCKS, (soundtype.func_185843_a() + 1.0F) / 2.0F, soundtype.func_185847_b() * 0.8F);
            itemstack.func_190918_g(1);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public static boolean func_179224_a(World p_179224_0_, @Nullable EntityPlayer p_179224_1_, BlockPos p_179224_2_, ItemStack p_179224_3_) {
      MinecraftServer minecraftserver = p_179224_0_.func_73046_m();
      if (minecraftserver == null) {
         return false;
      } else {
         NBTTagCompound nbttagcompound = p_179224_3_.func_179543_a("BlockEntityTag");
         if (nbttagcompound != null) {
            TileEntity tileentity = p_179224_0_.func_175625_s(p_179224_2_);
            if (tileentity != null) {
               if (!p_179224_0_.field_72995_K && tileentity.func_183000_F() && (p_179224_1_ == null || !p_179224_1_.func_189808_dh())) {
                  return false;
               }

               NBTTagCompound nbttagcompound1 = tileentity.func_189515_b(new NBTTagCompound());
               NBTTagCompound nbttagcompound2 = nbttagcompound1.func_74737_b();
               nbttagcompound1.func_179237_a(nbttagcompound);
               nbttagcompound1.func_74768_a("x", p_179224_2_.func_177958_n());
               nbttagcompound1.func_74768_a("y", p_179224_2_.func_177956_o());
               nbttagcompound1.func_74768_a("z", p_179224_2_.func_177952_p());
               if (!nbttagcompound1.equals(nbttagcompound2)) {
                  tileentity.func_145839_a(nbttagcompound1);
                  tileentity.func_70296_d();
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean func_179222_a(World p_179222_1_, BlockPos p_179222_2_, EnumFacing p_179222_3_, EntityPlayer p_179222_4_, ItemStack p_179222_5_) {
      Block block = p_179222_1_.func_180495_p(p_179222_2_).func_177230_c();
      if (block == Blocks.field_150431_aC) {
         p_179222_3_ = EnumFacing.UP;
      } else if (!block.func_176200_f(p_179222_1_, p_179222_2_)) {
         p_179222_2_ = p_179222_2_.func_177972_a(p_179222_3_);
      }

      return p_179222_1_.func_190527_a(this.field_150939_a, p_179222_2_, false, p_179222_3_, (Entity)null);
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return this.field_150939_a.func_149739_a();
   }

   public String func_77658_a() {
      return this.field_150939_a.func_149739_a();
   }

   public CreativeTabs func_77640_w() {
      return this.field_150939_a.func_149708_J();
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         this.field_150939_a.func_149666_a(p_150895_1_, p_150895_2_);
      }

   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      super.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
      this.field_150939_a.func_190948_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
   }

   public Block func_179223_d() {
      return this.field_150939_a;
   }
}
