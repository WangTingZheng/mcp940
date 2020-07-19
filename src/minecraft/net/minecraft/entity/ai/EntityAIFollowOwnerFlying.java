package net.minecraft.entity.ai;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.BlockPos;

public class EntityAIFollowOwnerFlying extends EntityAIFollowOwner
{
    public EntityAIFollowOwnerFlying(EntityTameable p_i47416_1_, double p_i47416_2_, float p_i47416_4_, float p_i47416_5_)
    {
        super(p_i47416_1_, p_i47416_2_, p_i47416_4_, p_i47416_5_);
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        IBlockState iblockstate = this.world.getBlockState(new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_));
        return (iblockstate.isTopSolid() || iblockstate.getMaterial() == Material.LEAVES) && this.world.isAirBlock(new BlockPos(x + p_192381_4_, y, p_192381_2_ + p_192381_5_)) && this.world.isAirBlock(new BlockPos(x + p_192381_4_, y + 1, p_192381_2_ + p_192381_5_));
    }
}
