package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeFireworks implements IRecipe
{
    private ItemStack resultItem = ItemStack.EMPTY;

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        this.resultItem = ItemStack.EMPTY;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = 0; k1 < inv.getSizeInventory(); ++k1)
        {
            ItemStack itemstack = inv.getStackInSlot(k1);

            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() == Items.GUNPOWDER)
                {
                    ++j;
                }
                else if (itemstack.getItem() == Items.FIREWORK_CHARGE)
                {
                    ++l;
                }
                else if (itemstack.getItem() == Items.DYE)
                {
                    ++k;
                }
                else if (itemstack.getItem() == Items.PAPER)
                {
                    ++i;
                }
                else if (itemstack.getItem() == Items.GLOWSTONE_DUST)
                {
                    ++i1;
                }
                else if (itemstack.getItem() == Items.DIAMOND)
                {
                    ++i1;
                }
                else if (itemstack.getItem() == Items.FIRE_CHARGE)
                {
                    ++j1;
                }
                else if (itemstack.getItem() == Items.FEATHER)
                {
                    ++j1;
                }
                else if (itemstack.getItem() == Items.GOLD_NUGGET)
                {
                    ++j1;
                }
                else
                {
                    if (itemstack.getItem() != Items.SKULL)
                    {
                        return false;
                    }

                    ++j1;
                }
            }
        }

        i1 = i1 + k + j1;

        if (j <= 3 && i <= 1)
        {
            if (j >= 1 && i == 1 && i1 == 0)
            {
                this.resultItem = new ItemStack(Items.FIREWORKS, 3);
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                if (l > 0)
                {
                    NBTTagList nbttaglist = new NBTTagList();

                    for (int k2 = 0; k2 < inv.getSizeInventory(); ++k2)
                    {
                        ItemStack itemstack3 = inv.getStackInSlot(k2);

                        if (itemstack3.getItem() == Items.FIREWORK_CHARGE && itemstack3.hasTagCompound() && itemstack3.getTagCompound().hasKey("Explosion", 10))
                        {
                            nbttaglist.appendTag(itemstack3.getTagCompound().getCompoundTag("Explosion"));
                        }
                    }

                    nbttagcompound1.setTag("Explosions", nbttaglist);
                }

                nbttagcompound1.setByte("Flight", (byte)j);
                NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                nbttagcompound3.setTag("Fireworks", nbttagcompound1);
                this.resultItem.setTagCompound(nbttagcompound3);
                return true;
            }
            else if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1)
            {
                this.resultItem = new ItemStack(Items.FIREWORK_CHARGE);
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                byte b0 = 0;
                List<Integer> list = Lists.<Integer>newArrayList();

                for (int l1 = 0; l1 < inv.getSizeInventory(); ++l1)
                {
                    ItemStack itemstack2 = inv.getStackInSlot(l1);

                    if (!itemstack2.isEmpty())
                    {
                        if (itemstack2.getItem() == Items.DYE)
                        {
                            list.add(Integer.valueOf(ItemDye.DYE_COLORS[itemstack2.getMetadata() & 15]));
                        }
                        else if (itemstack2.getItem() == Items.GLOWSTONE_DUST)
                        {
                            nbttagcompound2.setBoolean("Flicker", true);
                        }
                        else if (itemstack2.getItem() == Items.DIAMOND)
                        {
                            nbttagcompound2.setBoolean("Trail", true);
                        }
                        else if (itemstack2.getItem() == Items.FIRE_CHARGE)
                        {
                            b0 = 1;
                        }
                        else if (itemstack2.getItem() == Items.FEATHER)
                        {
                            b0 = 4;
                        }
                        else if (itemstack2.getItem() == Items.GOLD_NUGGET)
                        {
                            b0 = 2;
                        }
                        else if (itemstack2.getItem() == Items.SKULL)
                        {
                            b0 = 3;
                        }
                    }
                }

                int[] aint1 = new int[list.size()];

                for (int l2 = 0; l2 < aint1.length; ++l2)
                {
                    aint1[l2] = ((Integer)list.get(l2)).intValue();
                }

                nbttagcompound2.setIntArray("Colors", aint1);
                nbttagcompound2.setByte("Type", b0);
                nbttagcompound.setTag("Explosion", nbttagcompound2);
                this.resultItem.setTagCompound(nbttagcompound);
                return true;
            }
            else if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1)
            {
                List<Integer> list1 = Lists.<Integer>newArrayList();

                for (int i2 = 0; i2 < inv.getSizeInventory(); ++i2)
                {
                    ItemStack itemstack1 = inv.getStackInSlot(i2);

                    if (!itemstack1.isEmpty())
                    {
                        if (itemstack1.getItem() == Items.DYE)
                        {
                            list1.add(Integer.valueOf(ItemDye.DYE_COLORS[itemstack1.getMetadata() & 15]));
                        }
                        else if (itemstack1.getItem() == Items.FIREWORK_CHARGE)
                        {
                            this.resultItem = itemstack1.copy();
                            this.resultItem.setCount(1);
                        }
                    }
                }

                int[] aint = new int[list1.size()];

                for (int j2 = 0; j2 < aint.length; ++j2)
                {
                    aint[j2] = ((Integer)list1.get(j2)).intValue();
                }

                if (!this.resultItem.isEmpty() && this.resultItem.hasTagCompound())
                {
                    NBTTagCompound nbttagcompound4 = this.resultItem.getTagCompound().getCompoundTag("Explosion");

                    if (nbttagcompound4 == null)
                    {
                        return false;
                    }
                    else
                    {
                        nbttagcompound4.setIntArray("FadeColors", aint);
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        return this.resultItem.copy();
    }

    public ItemStack getRecipeOutput()
    {
        return this.resultItem;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);

            if (itemstack.getItem().hasContainerItem())
            {
                nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
            }
        }

        return nonnulllist;
    }

    public boolean isHidden()
    {
        return true;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height)
    {
        return width * height >= 1;
    }
}
