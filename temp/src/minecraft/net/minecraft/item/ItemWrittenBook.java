package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemWrittenBook extends Item {
   public ItemWrittenBook() {
      this.func_77625_d(1);
   }

   public static boolean func_77828_a(NBTTagCompound p_77828_0_) {
      if (!ItemWritableBook.func_150930_a(p_77828_0_)) {
         return false;
      } else if (!p_77828_0_.func_150297_b("title", 8)) {
         return false;
      } else {
         String s = p_77828_0_.func_74779_i("title");
         return s != null && s.length() <= 32 ? p_77828_0_.func_150297_b("author", 8) : false;
      }
   }

   public static int func_179230_h(ItemStack p_179230_0_) {
      return p_179230_0_.func_77978_p().func_74762_e("generation");
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      if (p_77653_1_.func_77942_o()) {
         NBTTagCompound nbttagcompound = p_77653_1_.func_77978_p();
         String s = nbttagcompound.func_74779_i("title");
         if (!StringUtils.func_151246_b(s)) {
            return s;
         }
      }

      return super.func_77653_i(p_77653_1_);
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      if (p_77624_1_.func_77942_o()) {
         NBTTagCompound nbttagcompound = p_77624_1_.func_77978_p();
         String s = nbttagcompound.func_74779_i("author");
         if (!StringUtils.func_151246_b(s)) {
            p_77624_3_.add(TextFormatting.GRAY + I18n.func_74837_a("book.byAuthor", s));
         }

         p_77624_3_.add(TextFormatting.GRAY + I18n.func_74838_a("book.generation." + nbttagcompound.func_74762_e("generation")));
      }

   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (!p_77659_1_.field_72995_K) {
         this.func_179229_a(itemstack, p_77659_2_);
      }

      p_77659_2_.func_184814_a(itemstack, p_77659_3_);
      p_77659_2_.func_71029_a(StatList.func_188057_b(this));
      return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
   }

   private void func_179229_a(ItemStack p_179229_1_, EntityPlayer p_179229_2_) {
      if (p_179229_1_.func_77978_p() != null) {
         NBTTagCompound nbttagcompound = p_179229_1_.func_77978_p();
         if (!nbttagcompound.func_74767_n("resolved")) {
            nbttagcompound.func_74757_a("resolved", true);
            if (func_77828_a(nbttagcompound)) {
               NBTTagList nbttaglist = nbttagcompound.func_150295_c("pages", 8);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  String s = nbttaglist.func_150307_f(i);

                  ITextComponent itextcomponent;
                  try {
                     itextcomponent = ITextComponent.Serializer.func_186877_b(s);
                     itextcomponent = TextComponentUtils.func_179985_a(p_179229_2_, itextcomponent, p_179229_2_);
                  } catch (Exception var9) {
                     itextcomponent = new TextComponentString(s);
                  }

                  nbttaglist.func_150304_a(i, new NBTTagString(ITextComponent.Serializer.func_150696_a(itextcomponent)));
               }

               nbttagcompound.func_74782_a("pages", nbttaglist);
               if (p_179229_2_ instanceof EntityPlayerMP && p_179229_2_.func_184614_ca() == p_179229_1_) {
                  Slot slot = p_179229_2_.field_71070_bA.func_75147_a(p_179229_2_.field_71071_by, p_179229_2_.field_71071_by.field_70461_c);
                  ((EntityPlayerMP)p_179229_2_).field_71135_a.func_147359_a(new SPacketSetSlot(0, slot.field_75222_d, p_179229_1_));
               }

            }
         }
      }
   }

   public boolean func_77636_d(ItemStack p_77636_1_) {
      return true;
   }
}
