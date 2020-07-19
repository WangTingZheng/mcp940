package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemFirework extends Item {
   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      if (!p_180614_2_.field_72995_K) {
         ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
         EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(p_180614_2_, (double)((float)p_180614_3_.func_177958_n() + p_180614_6_), (double)((float)p_180614_3_.func_177956_o() + p_180614_7_), (double)((float)p_180614_3_.func_177952_p() + p_180614_8_), itemstack);
         p_180614_2_.func_72838_d(entityfireworkrocket);
         if (!p_180614_1_.field_71075_bZ.field_75098_d) {
            itemstack.func_190918_g(1);
         }
      }

      return EnumActionResult.SUCCESS;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      if (p_77659_2_.func_184613_cA()) {
         ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
         if (!p_77659_1_.field_72995_K) {
            EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(p_77659_1_, itemstack, p_77659_2_);
            p_77659_1_.func_72838_d(entityfireworkrocket);
            if (!p_77659_2_.field_71075_bZ.field_75098_d) {
               itemstack.func_190918_g(1);
            }
         }

         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, p_77659_2_.func_184586_b(p_77659_3_));
      } else {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, p_77659_2_.func_184586_b(p_77659_3_));
      }
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      NBTTagCompound nbttagcompound = p_77624_1_.func_179543_a("Fireworks");
      if (nbttagcompound != null) {
         if (nbttagcompound.func_150297_b("Flight", 99)) {
            p_77624_3_.add(I18n.func_74838_a("item.fireworks.flight") + " " + nbttagcompound.func_74771_c("Flight"));
         }

         NBTTagList nbttaglist = nbttagcompound.func_150295_c("Explosions", 10);
         if (!nbttaglist.func_82582_d()) {
            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
               List<String> list = Lists.<String>newArrayList();
               ItemFireworkCharge.func_150902_a(nbttagcompound1, list);
               if (!list.isEmpty()) {
                  for(int j = 1; j < list.size(); ++j) {
                     list.set(j, "  " + (String)list.get(j));
                  }

                  p_77624_3_.addAll(list);
               }
            }
         }

      }
   }
}
