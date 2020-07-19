package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemKnowledgeBook extends Item {
   private static final Logger field_194126_a = LogManager.getLogger();

   public ItemKnowledgeBook() {
      this.func_77625_d(1);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      NBTTagCompound nbttagcompound = itemstack.func_77978_p();
      if (!p_77659_2_.field_71075_bZ.field_75098_d) {
         p_77659_2_.func_184611_a(p_77659_3_, ItemStack.field_190927_a);
      }

      if (nbttagcompound != null && nbttagcompound.func_150297_b("Recipes", 9)) {
         if (!p_77659_1_.field_72995_K) {
            NBTTagList nbttaglist = nbttagcompound.func_150295_c("Recipes", 8);
            List<IRecipe> list = Lists.<IRecipe>newArrayList();

            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               String s = nbttaglist.func_150307_f(i);
               IRecipe irecipe = CraftingManager.func_193373_a(new ResourceLocation(s));
               if (irecipe == null) {
                  field_194126_a.error("Invalid recipe: " + s);
                  return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
               }

               list.add(irecipe);
            }

            p_77659_2_.func_192021_a(list);
            p_77659_2_.func_71029_a(StatList.func_188057_b(this));
         }

         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      } else {
         field_194126_a.error("Tag not valid: " + nbttagcompound);
         return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
      }
   }
}
