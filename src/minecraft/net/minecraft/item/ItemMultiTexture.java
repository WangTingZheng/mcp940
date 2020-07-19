package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block unused;
    protected final ItemMultiTexture.Mapper nameFunction;

    public ItemMultiTexture(Block p_i47262_1_, Block p_i47262_2_, ItemMultiTexture.Mapper p_i47262_3_)
    {
        super(p_i47262_1_);
        this.unused = p_i47262_2_;
        this.nameFunction = p_i47262_3_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public ItemMultiTexture(Block block, Block block2, final String[] namesByMeta)
    {
        this(block, block2, new ItemMultiTexture.Mapper()
        {
            public String apply(ItemStack p_apply_1_)
            {
                int i = p_apply_1_.getMetadata();

                if (i < 0 || i >= namesByMeta.length)
                {
                    i = 0;
                }

                return namesByMeta[i];
            }
        });
    }

    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    public int getMetadata(int damage)
    {
        return damage;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + this.nameFunction.apply(stack);
    }

    public interface Mapper
    {
        String apply(ItemStack var1);
    }
}
