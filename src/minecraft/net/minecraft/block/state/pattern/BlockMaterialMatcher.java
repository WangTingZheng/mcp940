package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockMaterialMatcher implements Predicate<IBlockState>
{
    private final Material material;

    private BlockMaterialMatcher(Material materialIn)
    {
        this.material = materialIn;
    }

    public static BlockMaterialMatcher forMaterial(Material materialIn)
    {
        return new BlockMaterialMatcher(materialIn);
    }

    public boolean apply(@Nullable IBlockState p_apply_1_)
    {
        return p_apply_1_ != null && p_apply_1_.getMaterial() == this.material;
    }
}
