package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemShield extends Item {
   public ItemShield() {
      this.field_77777_bU = 1;
      this.func_77637_a(CreativeTabs.field_78037_j);
      this.func_77656_e(336);
      this.func_185043_a(new ResourceLocation("blocking"), new IItemPropertyGetter() {
         public float func_185085_a(ItemStack p_185085_1_, @Nullable World p_185085_2_, @Nullable EntityLivingBase p_185085_3_) {
            return p_185085_3_ != null && p_185085_3_.func_184587_cr() && p_185085_3_.func_184607_cu() == p_185085_1_ ? 1.0F : 0.0F;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(this, ItemArmor.field_96605_cw);
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      if (p_77653_1_.func_179543_a("BlockEntityTag") != null) {
         EnumDyeColor enumdyecolor = TileEntityBanner.func_190616_d(p_77653_1_);
         return I18n.func_74838_a("item.shield." + enumdyecolor.func_176762_d() + ".name");
      } else {
         return I18n.func_74838_a("item.shield.name");
      }
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      ItemBanner.func_185054_a(p_77624_1_, p_77624_3_);
   }

   public EnumAction func_77661_b(ItemStack p_77661_1_) {
      return EnumAction.BLOCK;
   }

   public int func_77626_a(ItemStack p_77626_1_) {
      return 72000;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      p_77659_2_.func_184598_c(p_77659_3_);
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
   }

   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return p_82789_2_.func_77973_b() == Item.func_150898_a(Blocks.field_150344_f) ? true : super.func_82789_a(p_82789_1_, p_82789_2_);
   }
}
