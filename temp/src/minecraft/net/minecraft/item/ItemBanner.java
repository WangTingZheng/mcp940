package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemBanner extends ItemBlock {
   public ItemBanner() {
      super(Blocks.field_180393_cK);
      this.field_77777_bU = 16;
      this.func_77637_a(CreativeTabs.field_78031_c);
      this.func_77627_a(true);
      this.func_77656_e(0);
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
      boolean flag = iblockstate.func_177230_c().func_176200_f(p_180614_2_, p_180614_3_);
      if (p_180614_5_ != EnumFacing.DOWN && (iblockstate.func_185904_a().func_76220_a() || flag) && (!flag || p_180614_5_ == EnumFacing.UP)) {
         p_180614_3_ = p_180614_3_.func_177972_a(p_180614_5_);
         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         if (p_180614_1_.func_175151_a(p_180614_3_, p_180614_5_, itemstack) && Blocks.field_180393_cK.func_176196_c(p_180614_2_, p_180614_3_)) {
            if (p_180614_2_.field_72995_K) {
               return EnumActionResult.SUCCESS;
            } else {
               p_180614_3_ = flag ? p_180614_3_.func_177977_b() : p_180614_3_;
               if (p_180614_5_ == EnumFacing.UP) {
                  int i = MathHelper.func_76128_c((double)((p_180614_1_.field_70177_z + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                  p_180614_2_.func_180501_a(p_180614_3_, Blocks.field_180393_cK.func_176223_P().func_177226_a(BlockStandingSign.field_176413_a, Integer.valueOf(i)), 3);
               } else {
                  p_180614_2_.func_180501_a(p_180614_3_, Blocks.field_180394_cL.func_176223_P().func_177226_a(BlockWallSign.field_176412_a, p_180614_5_), 3);
               }

               TileEntity tileentity = p_180614_2_.func_175625_s(p_180614_3_);
               if (tileentity instanceof TileEntityBanner) {
                  ((TileEntityBanner)tileentity).func_175112_a(itemstack, false);
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
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      String s = "item.banner.";
      EnumDyeColor enumdyecolor = func_179225_h(p_77653_1_);
      s = s + enumdyecolor.func_176762_d() + ".name";
      return I18n.func_74838_a(s);
   }

   public static void func_185054_a(ItemStack p_185054_0_, List<String> p_185054_1_) {
      NBTTagCompound nbttagcompound = p_185054_0_.func_179543_a("BlockEntityTag");
      if (nbttagcompound != null && nbttagcompound.func_74764_b("Patterns")) {
         NBTTagList nbttaglist = nbttagcompound.func_150295_c("Patterns", 10);

         for(int i = 0; i < nbttaglist.func_74745_c() && i < 6; ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
            EnumDyeColor enumdyecolor = EnumDyeColor.func_176766_a(nbttagcompound1.func_74762_e("Color"));
            BannerPattern bannerpattern = BannerPattern.func_190994_a(nbttagcompound1.func_74779_i("Pattern"));
            if (bannerpattern != null) {
               p_185054_1_.add(I18n.func_74838_a("item.banner." + bannerpattern.func_190997_a() + "." + enumdyecolor.func_176762_d()));
            }
         }

      }
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      func_185054_a(p_77624_1_, p_77624_3_);
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
            p_150895_2_.add(func_190910_a(enumdyecolor, (NBTTagList)null));
         }
      }

   }

   public static ItemStack func_190910_a(EnumDyeColor p_190910_0_, @Nullable NBTTagList p_190910_1_) {
      ItemStack itemstack = new ItemStack(Items.field_179564_cE, 1, p_190910_0_.func_176767_b());
      if (p_190910_1_ != null && !p_190910_1_.func_82582_d()) {
         itemstack.func_190925_c("BlockEntityTag").func_74782_a("Patterns", p_190910_1_.func_74737_b());
      }

      return itemstack;
   }

   public CreativeTabs func_77640_w() {
      return CreativeTabs.field_78031_c;
   }

   public static EnumDyeColor func_179225_h(ItemStack p_179225_0_) {
      return EnumDyeColor.func_176766_a(p_179225_0_.func_77960_j() & 15);
   }
}
