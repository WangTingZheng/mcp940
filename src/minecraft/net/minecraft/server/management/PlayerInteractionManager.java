package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PlayerInteractionManager
{
    /** The world object that this object is connected to. */
    public World world;

    /** The EntityPlayerMP object that this object is connected to. */
    public EntityPlayerMP player;
    private GameType gameType = GameType.NOT_SET;

    /** True if the player is destroying a block */
    private boolean isDestroyingBlock;
    private int initialDamage;
    private BlockPos destroyPos = BlockPos.ORIGIN;
    private int curblockDamage;

    /**
     * Set to true when the "finished destroying block" packet is received but the block wasn't fully damaged yet. The
     * block will not be destroyed while this is false.
     */
    private boolean receivedFinishDiggingPacket;
    private BlockPos delayedDestroyPos = BlockPos.ORIGIN;
    private int initialBlockDamage;
    private int durabilityRemainingOnBlock = -1;

    public PlayerInteractionManager(World worldIn)
    {
        this.world = worldIn;
    }

    public void setGameType(GameType type)
    {
        this.gameType = type;
        type.configurePlayerCapabilities(this.player.capabilities);
        this.player.sendPlayerAbilities();
        this.player.mcServer.getPlayerList().sendPacketToAllPlayers(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] {this.player}));
        this.world.updateAllPlayersSleepingFlag();
    }

    public GameType getGameType()
    {
        return this.gameType;
    }

    public boolean survivalOrAdventure()
    {
        return this.gameType.isSurvivalOrAdventure();
    }

    /**
     * Get if we are in creative game mode.
     */
    public boolean isCreative()
    {
        return this.gameType.isCreative();
    }

    /**
     * if the gameType is currently NOT_SET then change it to par1
     */
    public void initializeGameType(GameType type)
    {
        if (this.gameType == GameType.NOT_SET)
        {
            this.gameType = type;
        }

        this.setGameType(this.gameType);
    }

    public void updateBlockRemoving()
    {
        ++this.curblockDamage;

        if (this.receivedFinishDiggingPacket)
        {
            int i = this.curblockDamage - this.initialBlockDamage;
            IBlockState iblockstate = this.world.getBlockState(this.delayedDestroyPos);

            if (iblockstate.getMaterial() == Material.AIR)
            {
                this.receivedFinishDiggingPacket = false;
            }
            else
            {
                float f = iblockstate.getPlayerRelativeBlockHardness(this.player, this.player.world, this.delayedDestroyPos) * (float)(i + 1);
                int j = (int)(f * 10.0F);

                if (j != this.durabilityRemainingOnBlock)
                {
                    this.world.sendBlockBreakProgress(this.player.getEntityId(), this.delayedDestroyPos, j);
                    this.durabilityRemainingOnBlock = j;
                }

                if (f >= 1.0F)
                {
                    this.receivedFinishDiggingPacket = false;
                    this.tryHarvestBlock(this.delayedDestroyPos);
                }
            }
        }
        else if (this.isDestroyingBlock)
        {
            IBlockState iblockstate1 = this.world.getBlockState(this.destroyPos);

            if (iblockstate1.getMaterial() == Material.AIR)
            {
                this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;
            }
            else
            {
                int k = this.curblockDamage - this.initialDamage;
                float f1 = iblockstate1.getPlayerRelativeBlockHardness(this.player, this.player.world, this.delayedDestroyPos) * (float)(k + 1);
                int l = (int)(f1 * 10.0F);

                if (l != this.durabilityRemainingOnBlock)
                {
                    this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, l);
                    this.durabilityRemainingOnBlock = l;
                }
            }
        }
    }

    /**
     * If not creative, it calls sendBlockBreakProgress until the block is broken first. tryHarvestBlock can also be the
     * result of this call.
     */
    public void onBlockClicked(BlockPos pos, EnumFacing side)
    {
        if (this.isCreative())
        {
            if (!this.world.extinguishFire((EntityPlayer)null, pos, side))
            {
                this.tryHarvestBlock(pos);
            }
        }
        else
        {
            IBlockState iblockstate = this.world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (this.gameType.isAdventure())
            {
                if (this.gameType == GameType.SPECTATOR)
                {
                    return;
                }

                if (!this.player.isAllowEdit())
                {
                    ItemStack itemstack = this.player.getHeldItemMainhand();

                    if (itemstack.isEmpty())
                    {
                        return;
                    }

                    if (!itemstack.canDestroy(block))
                    {
                        return;
                    }
                }
            }

            this.world.extinguishFire((EntityPlayer)null, pos, side);
            this.initialDamage = this.curblockDamage;
            float f = 1.0F;

            if (iblockstate.getMaterial() != Material.AIR)
            {
                block.onBlockClicked(this.world, pos, this.player);
                f = iblockstate.getPlayerRelativeBlockHardness(this.player, this.player.world, pos);
            }

            if (iblockstate.getMaterial() != Material.AIR && f >= 1.0F)
            {
                this.tryHarvestBlock(pos);
            }
            else
            {
                this.isDestroyingBlock = true;
                this.destroyPos = pos;
                int i = (int)(f * 10.0F);
                this.world.sendBlockBreakProgress(this.player.getEntityId(), pos, i);
                this.durabilityRemainingOnBlock = i;
            }
        }
    }

    public void blockRemoving(BlockPos pos)
    {
        if (pos.equals(this.destroyPos))
        {
            int i = this.curblockDamage - this.initialDamage;
            IBlockState iblockstate = this.world.getBlockState(pos);

            if (iblockstate.getMaterial() != Material.AIR)
            {
                float f = iblockstate.getPlayerRelativeBlockHardness(this.player, this.player.world, pos) * (float)(i + 1);

                if (f >= 0.7F)
                {
                    this.isDestroyingBlock = false;
                    this.world.sendBlockBreakProgress(this.player.getEntityId(), pos, -1);
                    this.tryHarvestBlock(pos);
                }
                else if (!this.receivedFinishDiggingPacket)
                {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.delayedDestroyPos = pos;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }

    /**
     * Stops the block breaking process
     */
    public void cancelDestroyingBlock()
    {
        this.isDestroyingBlock = false;
        this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
    }

    /**
     * Removes a block and triggers the appropriate events
     */
    private boolean removeBlock(BlockPos pos)
    {
        IBlockState iblockstate = this.world.getBlockState(pos);
        iblockstate.getBlock().onBlockHarvested(this.world, pos, iblockstate, this.player);
        boolean flag = this.world.setBlockToAir(pos);

        if (flag)
        {
            iblockstate.getBlock().onBlockDestroyedByPlayer(this.world, pos, iblockstate);
        }

        return flag;
    }

    /**
     * Attempts to harvest a block
     */
    public boolean tryHarvestBlock(BlockPos pos)
    {
        if (this.gameType.isCreative() && !this.player.getHeldItemMainhand().isEmpty() && this.player.getHeldItemMainhand().getItem() instanceof ItemSword)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = this.world.getBlockState(pos);
            TileEntity tileentity = this.world.getTileEntity(pos);
            Block block = iblockstate.getBlock();

            if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.player.canUseCommandBlock())
            {
                this.world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
                return false;
            }
            else
            {
                if (this.gameType.isAdventure())
                {
                    if (this.gameType == GameType.SPECTATOR)
                    {
                        return false;
                    }

                    if (!this.player.isAllowEdit())
                    {
                        ItemStack itemstack = this.player.getHeldItemMainhand();

                        if (itemstack.isEmpty())
                        {
                            return false;
                        }

                        if (!itemstack.canDestroy(block))
                        {
                            return false;
                        }
                    }
                }

                this.world.playEvent(this.player, 2001, pos, Block.getStateId(iblockstate));
                boolean flag1 = this.removeBlock(pos);

                if (this.isCreative())
                {
                    this.player.connection.sendPacket(new SPacketBlockChange(this.world, pos));
                }
                else
                {
                    ItemStack itemstack1 = this.player.getHeldItemMainhand();
                    ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy();
                    boolean flag = this.player.canHarvestBlock(iblockstate);

                    if (!itemstack1.isEmpty())
                    {
                        itemstack1.onBlockDestroyed(this.world, iblockstate, pos, this.player);
                    }

                    if (flag1 && flag)
                    {
                        iblockstate.getBlock().harvestBlock(this.world, this.player, pos, iblockstate, tileentity, itemstack2);
                    }
                }

                return flag1;
            }
        }
    }

    public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand)
    {
        if (this.gameType == GameType.SPECTATOR)
        {
            return EnumActionResult.PASS;
        }
        else if (player.getCooldownTracker().hasCooldown(stack.getItem()))
        {
            return EnumActionResult.PASS;
        }
        else
        {
            int i = stack.getCount();
            int j = stack.getMetadata();
            ActionResult<ItemStack> actionresult = stack.useItemRightClick(worldIn, player, hand);
            ItemStack itemstack = actionresult.getResult();

            if (itemstack == stack && itemstack.getCount() == i && itemstack.getMaxItemUseDuration() <= 0 && itemstack.getMetadata() == j)
            {
                return actionresult.getType();
            }
            else if (actionresult.getType() == EnumActionResult.FAIL && itemstack.getMaxItemUseDuration() > 0 && !player.isHandActive())
            {
                return actionresult.getType();
            }
            else
            {
                player.setHeldItem(hand, itemstack);

                if (this.isCreative())
                {
                    itemstack.setCount(i);

                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(j);
                    }
                }

                if (itemstack.isEmpty())
                {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }

                if (!player.isHandActive())
                {
                    ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
                }

                return actionresult.getType();
            }
        }
    }

    public EnumActionResult processRightClickBlock(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (this.gameType == GameType.SPECTATOR)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof ILockableContainer)
            {
                Block block1 = worldIn.getBlockState(pos).getBlock();
                ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;

                if (ilockablecontainer instanceof TileEntityChest && block1 instanceof BlockChest)
                {
                    ilockablecontainer = ((BlockChest)block1).getLockableContainer(worldIn, pos);
                }

                if (ilockablecontainer != null)
                {
                    player.displayGUIChest(ilockablecontainer);
                    return EnumActionResult.SUCCESS;
                }
            }
            else if (tileentity instanceof IInventory)
            {
                player.displayGUIChest((IInventory)tileentity);
                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.PASS;
        }
        else
        {
            if (!player.isSneaking() || player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty())
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);

                if (iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, hand, facing, hitX, hitY, hitZ))
                {
                    return EnumActionResult.SUCCESS;
                }
            }

            if (stack.isEmpty())
            {
                return EnumActionResult.PASS;
            }
            else if (player.getCooldownTracker().hasCooldown(stack.getItem()))
            {
                return EnumActionResult.PASS;
            }
            else
            {
                if (stack.getItem() instanceof ItemBlock && !player.canUseCommandBlock())
                {
                    Block block = ((ItemBlock)stack.getItem()).getBlock();

                    if (block instanceof BlockCommandBlock || block instanceof BlockStructure)
                    {
                        return EnumActionResult.FAIL;
                    }
                }

                if (this.isCreative())
                {
                    int j = stack.getMetadata();
                    int i = stack.getCount();
                    EnumActionResult enumactionresult = stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
                    stack.setItemDamage(j);
                    stack.setCount(i);
                    return enumactionresult;
                }
                else
                {
                    return stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
                }
            }
        }
    }

    /**
     * Sets the world instance.
     */
    public void setWorld(WorldServer serverWorld)
    {
        this.world = serverWorld;
    }
}
