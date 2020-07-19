package net.minecraft.item.crafting;

import javax.annotation.Nullable;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesBanners
{
    public static class RecipeAddPattern implements IRecipe
    {
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            boolean flag = false;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (itemstack.getItem() == Items.BANNER)
                {
                    if (flag)
                    {
                        return false;
                    }

                    if (TileEntityBanner.getPatterns(itemstack) >= 6)
                    {
                        return false;
                    }

                    flag = true;
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                return this.matchPatterns(inv) != null;
            }
        }

        public ItemStack getCraftingResult(InventoryCrafting inv)
        {
            ItemStack itemstack = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack1 = inv.getStackInSlot(i);

                if (!itemstack1.isEmpty() && itemstack1.getItem() == Items.BANNER)
                {
                    itemstack = itemstack1.copy();
                    itemstack.setCount(1);
                    break;
                }
            }

            BannerPattern bannerpattern = this.matchPatterns(inv);

            if (bannerpattern != null)
            {
                int k = 0;

                for (int j = 0; j < inv.getSizeInventory(); ++j)
                {
                    ItemStack itemstack2 = inv.getStackInSlot(j);

                    if (itemstack2.getItem() == Items.DYE)
                    {
                        k = itemstack2.getMetadata();
                        break;
                    }
                }

                NBTTagCompound nbttagcompound1 = itemstack.getOrCreateSubCompound("BlockEntityTag");
                NBTTagList nbttaglist;

                if (nbttagcompound1.hasKey("Patterns", 9))
                {
                    nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
                }
                else
                {
                    nbttaglist = new NBTTagList();
                    nbttagcompound1.setTag("Patterns", nbttaglist);
                }

                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setString("Pattern", bannerpattern.getHashname());
                nbttagcompound.setInteger("Color", k);
                nbttaglist.appendTag(nbttagcompound);
            }

            return itemstack;
        }

        public ItemStack getRecipeOutput()
        {
            return ItemStack.EMPTY;
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

        @Nullable
        private BannerPattern matchPatterns(InventoryCrafting p_190933_1_)
        {
            for (BannerPattern bannerpattern : BannerPattern.values())
            {
                if (bannerpattern.hasPattern())
                {
                    boolean flag = true;

                    if (bannerpattern.hasPatternItem())
                    {
                        boolean flag1 = false;
                        boolean flag2 = false;

                        for (int i = 0; i < p_190933_1_.getSizeInventory() && flag; ++i)
                        {
                            ItemStack itemstack = p_190933_1_.getStackInSlot(i);

                            if (!itemstack.isEmpty() && itemstack.getItem() != Items.BANNER)
                            {
                                if (itemstack.getItem() == Items.DYE)
                                {
                                    if (flag2)
                                    {
                                        flag = false;
                                        break;
                                    }

                                    flag2 = true;
                                }
                                else
                                {
                                    if (flag1 || !itemstack.isItemEqual(bannerpattern.getPatternItem()))
                                    {
                                        flag = false;
                                        break;
                                    }

                                    flag1 = true;
                                }
                            }
                        }

                        if (!flag1 || !flag2)
                        {
                            flag = false;
                        }
                    }
                    else if (p_190933_1_.getSizeInventory() == bannerpattern.getPatterns().length * bannerpattern.getPatterns()[0].length())
                    {
                        int j = -1;

                        for (int k = 0; k < p_190933_1_.getSizeInventory() && flag; ++k)
                        {
                            int l = k / 3;
                            int i1 = k % 3;
                            ItemStack itemstack1 = p_190933_1_.getStackInSlot(k);

                            if (!itemstack1.isEmpty() && itemstack1.getItem() != Items.BANNER)
                            {
                                if (itemstack1.getItem() != Items.DYE)
                                {
                                    flag = false;
                                    break;
                                }

                                if (j != -1 && j != itemstack1.getMetadata())
                                {
                                    flag = false;
                                    break;
                                }

                                if (bannerpattern.getPatterns()[l].charAt(i1) == ' ')
                                {
                                    flag = false;
                                    break;
                                }

                                j = itemstack1.getMetadata();
                            }
                            else if (bannerpattern.getPatterns()[l].charAt(i1) != ' ')
                            {
                                flag = false;
                                break;
                            }
                        }
                    }
                    else
                    {
                        flag = false;
                    }

                    if (flag)
                    {
                        return bannerpattern;
                    }
                }
            }

            return null;
        }

        public boolean isHidden()
        {
            return true;
        }

        public boolean canFit(int width, int height)
        {
            return width >= 3 && height >= 3;
        }
    }

    public static class RecipeDuplicatePattern implements IRecipe
    {
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            ItemStack itemstack = ItemStack.EMPTY;
            ItemStack itemstack1 = ItemStack.EMPTY;

            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack2 = inv.getStackInSlot(i);

                if (!itemstack2.isEmpty())
                {
                    if (itemstack2.getItem() != Items.BANNER)
                    {
                        return false;
                    }

                    if (!itemstack.isEmpty() && !itemstack1.isEmpty())
                    {
                        return false;
                    }

                    EnumDyeColor enumdyecolor = ItemBanner.getBaseColor(itemstack2);
                    boolean flag = TileEntityBanner.getPatterns(itemstack2) > 0;

                    if (!itemstack.isEmpty())
                    {
                        if (flag)
                        {
                            return false;
                        }

                        if (enumdyecolor != ItemBanner.getBaseColor(itemstack))
                        {
                            return false;
                        }

                        itemstack1 = itemstack2;
                    }
                    else if (!itemstack1.isEmpty())
                    {
                        if (!flag)
                        {
                            return false;
                        }

                        if (enumdyecolor != ItemBanner.getBaseColor(itemstack1))
                        {
                            return false;
                        }

                        itemstack = itemstack2;
                    }
                    else if (flag)
                    {
                        itemstack = itemstack2;
                    }
                    else
                    {
                        itemstack1 = itemstack2;
                    }
                }
            }

            return !itemstack.isEmpty() && !itemstack1.isEmpty();
        }

        public ItemStack getCraftingResult(InventoryCrafting inv)
        {
            for (int i = 0; i < inv.getSizeInventory(); ++i)
            {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (!itemstack.isEmpty() && TileEntityBanner.getPatterns(itemstack) > 0)
                {
                    ItemStack itemstack1 = itemstack.copy();
                    itemstack1.setCount(1);
                    return itemstack1;
                }
            }

            return ItemStack.EMPTY;
        }

        public ItemStack getRecipeOutput()
        {
            return ItemStack.EMPTY;
        }

        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
        {
            NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

            for (int i = 0; i < nonnulllist.size(); ++i)
            {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.getItem().hasContainerItem())
                    {
                        nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
                    }
                    else if (itemstack.hasTagCompound() && TileEntityBanner.getPatterns(itemstack) > 0)
                    {
                        ItemStack itemstack1 = itemstack.copy();
                        itemstack1.setCount(1);
                        nonnulllist.set(i, itemstack1);
                    }
                }
            }

            return nonnulllist;
        }

        public boolean isHidden()
        {
            return true;
        }

        public boolean canFit(int width, int height)
        {
            return width * height >= 2;
        }
    }
}
