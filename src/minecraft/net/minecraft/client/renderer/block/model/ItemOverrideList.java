package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverrideList
{
    public static final ItemOverrideList NONE = new ItemOverrideList();
    private final List<ItemOverride> overrides = Lists.<ItemOverride>newArrayList();

    private ItemOverrideList()
    {
    }

    public ItemOverrideList(List<ItemOverride> overridesIn)
    {
        for (int i = overridesIn.size() - 1; i >= 0; --i)
        {
            this.overrides.add(overridesIn.get(i));
        }
    }

    @Nullable
    public ResourceLocation applyOverride(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
    {
        if (!this.overrides.isEmpty())
        {
            for (ItemOverride itemoverride : this.overrides)
            {
                if (itemoverride.matchesItemStack(stack, worldIn, entityIn))
                {
                    return itemoverride.getLocation();
                }
            }
        }

        return null;
    }
}
