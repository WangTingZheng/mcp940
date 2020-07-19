package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerControllerMP
{
    private boolean acting = false;

    public PlayerControllerOF(Minecraft p_i69_1_, NetHandlerPlayClient p_i69_2_)
    {
        super(p_i69_1_, p_i69_2_);
    }

    /**
     * Called when the player is hitting a block with an item.
     */
    public boolean clickBlock(BlockPos loc, EnumFacing face)
    {
        this.acting = true;
        boolean flag = super.clickBlock(loc, face);
        this.acting = false;
        return flag;
    }

    public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing)
    {
        this.acting = true;
        boolean flag = super.onPlayerDamageBlock(posBlock, directionFacing);
        this.acting = false;
        return flag;
    }

    public EnumActionResult processRightClick(EntityPlayer player, World worldIn, EnumHand hand)
    {
        this.acting = true;
        EnumActionResult enumactionresult = super.processRightClick(player, worldIn, hand);
        this.acting = false;
        return enumactionresult;
    }

    public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand)
    {
        this.acting = true;
        EnumActionResult enumactionresult = super.processRightClickBlock(player, worldIn, pos, direction, vec, hand);
        this.acting = false;
        return enumactionresult;
    }

    public boolean isActing()
    {
        return this.acting;
    }
}
