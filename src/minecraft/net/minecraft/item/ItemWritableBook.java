package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemWritableBook extends Item
{
    public ItemWritableBook()
    {
        this.setMaxStackSize(1);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.openBook(itemstack, handIn);
        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    /**
     * this method returns true if the book's NBT Tag List "pages" is valid
     */
    public static boolean isNBTValid(NBTTagCompound nbt)
    {
        if (nbt == null)
        {
            return false;
        }
        else if (!nbt.hasKey("pages", 9))
        {
            return false;
        }
        else
        {
            NBTTagList nbttaglist = nbt.getTagList("pages", 8);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                String s = nbttaglist.getStringTagAt(i);

                if (s.length() > 32767)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
