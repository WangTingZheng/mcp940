package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class ItemSkull extends Item {
   private static final String[] field_82807_a = new String[]{"skeleton", "wither", "zombie", "char", "creeper", "dragon"};

   public ItemSkull() {
      this.func_77637_a(CreativeTabs.field_78031_c);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      if (p_180614_5_ == EnumFacing.DOWN) {
         return EnumActionResult.FAIL;
      } else {
         IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
         Block block = iblockstate.func_177230_c();
         boolean flag = block.func_176200_f(p_180614_2_, p_180614_3_);
         if (!flag) {
            if (!p_180614_2_.func_180495_p(p_180614_3_).func_185904_a().func_76220_a()) {
               return EnumActionResult.FAIL;
            }

            p_180614_3_ = p_180614_3_.func_177972_a(p_180614_5_);
         }

         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         if (p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack) && Blocks.field_150465_bP.func_176196_c(p_180614_2_, p_180614_3_)) {
            if (p_180614_2_.field_72995_K) {
               return EnumActionResult.SUCCESS;
            } else {
               p_180614_2_.func_180501_a(p_180614_3_, Blocks.field_150465_bP.func_176223_P().func_177226_a(BlockSkull.field_176418_a, p_180614_5_), 11);
               int i = 0;
               if (p_180614_5_ == EnumFacing.UP) {
                  i = MathHelper.func_76128_c((double)(p_180614_1_.field_70177_z * 16.0F / 360.0F) + 0.5D) & 15;
               }

               TileEntity tileentity = p_180614_2_.func_175625_s(p_180614_3_);
               if (tileentity instanceof TileEntitySkull) {
                  TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
                  if (itemstack.func_77960_j() == 3) {
                     GameProfile gameprofile = null;
                     if (itemstack.func_77942_o()) {
                        NBTTagCompound nbttagcompound = itemstack.func_77978_p();
                        if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
                           gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
                        } else if (nbttagcompound.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.func_74779_i("SkullOwner"))) {
                           gameprofile = new GameProfile((UUID)null, nbttagcompound.func_74779_i("SkullOwner"));
                        }
                     }

                     tileentityskull.func_152106_a(gameprofile);
                  } else {
                     tileentityskull.func_152107_a(itemstack.func_77960_j());
                  }

                  tileentityskull.func_145903_a(i);
                  Blocks.field_150465_bP.func_180679_a(p_180614_2_, p_180614_3_, tileentityskull);
               }

               if (p_180614_1_ instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)p_180614_1_, p_180614_3_, itemstack);
               }

               itemstack.func_190918_g(1);
               return EnumActionResult.SUCCESS;
            }
         } else {
            return EnumActionResult.FAIL;
         }
      }
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         for(int i = 0; i < field_82807_a.length; ++i) {
            p_150895_2_.add(new ItemStack(this, 1, i));
         }
      }

   }

   public int func_77647_b(int p_77647_1_) {
      return p_77647_1_;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      int i = p_77667_1_.func_77960_j();
      if (i < 0 || i >= field_82807_a.length) {
         i = 0;
      }

      return super.func_77658_a() + "." + field_82807_a[i];
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      if (p_77653_1_.func_77960_j() == 3 && p_77653_1_.func_77942_o()) {
         if (p_77653_1_.func_77978_p().func_150297_b("SkullOwner", 8)) {
            return I18n.func_74837_a("item.skull.player.name", p_77653_1_.func_77978_p().func_74779_i("SkullOwner"));
         }

         if (p_77653_1_.func_77978_p().func_150297_b("SkullOwner", 10)) {
            NBTTagCompound nbttagcompound = p_77653_1_.func_77978_p().func_74775_l("SkullOwner");
            if (nbttagcompound.func_150297_b("Name", 8)) {
               return I18n.func_74837_a("item.skull.player.name", nbttagcompound.func_74779_i("Name"));
            }
         }
      }

      return super.func_77653_i(p_77653_1_);
   }

   public boolean func_179215_a(NBTTagCompound p_179215_1_) {
      super.func_179215_a(p_179215_1_);
      if (p_179215_1_.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(p_179215_1_.func_74779_i("SkullOwner"))) {
         GameProfile gameprofile = new GameProfile((UUID)null, p_179215_1_.func_74779_i("SkullOwner"));
         gameprofile = TileEntitySkull.func_174884_b(gameprofile);
         p_179215_1_.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), gameprofile));
         return true;
      } else {
         return false;
      }
   }
}
