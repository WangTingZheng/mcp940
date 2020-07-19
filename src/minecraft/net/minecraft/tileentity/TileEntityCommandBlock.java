package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity
{
    private boolean powered;
    private boolean auto;
    private boolean conditionMet;
    private boolean sendToClient;
    private final CommandBlockBaseLogic commandBlockLogic = new CommandBlockBaseLogic()
    {
        public BlockPos getPosition()
        {
            return TileEntityCommandBlock.this.pos;
        }
        public Vec3d getPositionVector()
        {
            return new Vec3d((double)TileEntityCommandBlock.this.pos.getX() + 0.5D, (double)TileEntityCommandBlock.this.pos.getY() + 0.5D, (double)TileEntityCommandBlock.this.pos.getZ() + 0.5D);
        }
        public World getEntityWorld()
        {
            return TileEntityCommandBlock.this.getWorld();
        }
        public void setCommand(String command)
        {
            super.setCommand(command);
            TileEntityCommandBlock.this.markDirty();
        }
        public void updateCommand()
        {
            IBlockState iblockstate = TileEntityCommandBlock.this.world.getBlockState(TileEntityCommandBlock.this.pos);
            TileEntityCommandBlock.this.getWorld().notifyBlockUpdate(TileEntityCommandBlock.this.pos, iblockstate, iblockstate, 3);
        }
        public int getCommandBlockType()
        {
            return 0;
        }
        public void fillInInfo(ByteBuf buf)
        {
            buf.writeInt(TileEntityCommandBlock.this.pos.getX());
            buf.writeInt(TileEntityCommandBlock.this.pos.getY());
            buf.writeInt(TileEntityCommandBlock.this.pos.getZ());
        }
        public MinecraftServer getServer()
        {
            return TileEntityCommandBlock.this.world.getMinecraftServer();
        }
    };

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.commandBlockLogic.writeToNBT(compound);
        compound.setBoolean("powered", this.isPowered());
        compound.setBoolean("conditionMet", this.isConditionMet());
        compound.setBoolean("auto", this.isAuto());
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.commandBlockLogic.readDataFromNBT(compound);
        this.powered = compound.getBoolean("powered");
        this.conditionMet = compound.getBoolean("conditionMet");
        this.setAuto(compound.getBoolean("auto"));
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        if (this.isSendToClient())
        {
            this.setSendToClient(false);
            NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
            return new SPacketUpdateTileEntity(this.pos, 2, nbttagcompound);
        }
        else
        {
            return null;
        }
    }

    public boolean onlyOpsCanSetNbt()
    {
        return true;
    }

    public CommandBlockBaseLogic getCommandBlockLogic()
    {
        return this.commandBlockLogic;
    }

    public CommandResultStats getCommandResultStats()
    {
        return this.commandBlockLogic.getCommandResultStats();
    }

    public void setPowered(boolean poweredIn)
    {
        this.powered = poweredIn;
    }

    public boolean isPowered()
    {
        return this.powered;
    }

    public boolean isAuto()
    {
        return this.auto;
    }

    public void setAuto(boolean autoIn)
    {
        boolean flag = this.auto;
        this.auto = autoIn;

        if (!flag && autoIn && !this.powered && this.world != null && this.getMode() != TileEntityCommandBlock.Mode.SEQUENCE)
        {
            Block block = this.getBlockType();

            if (block instanceof BlockCommandBlock)
            {
                this.setConditionMet();
                this.world.scheduleUpdate(this.pos, block, block.tickRate(this.world));
            }
        }
    }

    public boolean isConditionMet()
    {
        return this.conditionMet;
    }

    public boolean setConditionMet()
    {
        this.conditionMet = true;

        if (this.isConditional())
        {
            BlockPos blockpos = this.pos.offset(((EnumFacing)this.world.getBlockState(this.pos).getValue(BlockCommandBlock.FACING)).getOpposite());

            if (this.world.getBlockState(blockpos).getBlock() instanceof BlockCommandBlock)
            {
                TileEntity tileentity = this.world.getTileEntity(blockpos);
                this.conditionMet = tileentity instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)tileentity).getCommandBlockLogic().getSuccessCount() > 0;
            }
            else
            {
                this.conditionMet = false;
            }
        }

        return this.conditionMet;
    }

    public boolean isSendToClient()
    {
        return this.sendToClient;
    }

    public void setSendToClient(boolean p_184252_1_)
    {
        this.sendToClient = p_184252_1_;
    }

    public TileEntityCommandBlock.Mode getMode()
    {
        Block block = this.getBlockType();

        if (block == Blocks.COMMAND_BLOCK)
        {
            return TileEntityCommandBlock.Mode.REDSTONE;
        }
        else if (block == Blocks.REPEATING_COMMAND_BLOCK)
        {
            return TileEntityCommandBlock.Mode.AUTO;
        }
        else
        {
            return block == Blocks.CHAIN_COMMAND_BLOCK ? TileEntityCommandBlock.Mode.SEQUENCE : TileEntityCommandBlock.Mode.REDSTONE;
        }
    }

    public boolean isConditional()
    {
        IBlockState iblockstate = this.world.getBlockState(this.getPos());
        return iblockstate.getBlock() instanceof BlockCommandBlock ? ((Boolean)iblockstate.getValue(BlockCommandBlock.CONDITIONAL)).booleanValue() : false;
    }

    /**
     * validates a tile entity
     */
    public void validate()
    {
        this.blockType = null;
        super.validate();
    }

    public static enum Mode
    {
        SEQUENCE,
        AUTO,
        REDSTONE;
    }
}
