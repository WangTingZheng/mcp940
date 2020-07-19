package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public interface IItemPropertyGetter
{
    float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn);
}
