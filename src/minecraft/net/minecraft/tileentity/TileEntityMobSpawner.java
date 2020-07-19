package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements ITickable
{
    private final MobSpawnerBaseLogic spawnerLogic = new MobSpawnerBaseLogic()
    {
        public void broadcastEvent(int id)
        {
            TileEntityMobSpawner.this.world.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.MOB_SPAWNER, id, 0);
        }
        public World getSpawnerWorld()
        {
            return TileEntityMobSpawner.this.world;
        }
        public BlockPos getSpawnerPosition()
        {
            return TileEntityMobSpawner.this.pos;
        }
        public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_)
        {
            super.setNextSpawnData(p_184993_1_);

            if (this.getSpawnerWorld() != null)
            {
                IBlockState iblockstate = this.getSpawnerWorld().getBlockState(this.getSpawnerPosition());
                this.getSpawnerWorld().notifyBlockUpdate(TileEntityMobSpawner.this.pos, iblockstate, iblockstate, 4);
            }
        }
    };

    public static void registerFixesMobSpawner(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new IDataWalker()
        {
            public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
            {
                if (TileEntity.getKey(TileEntityMobSpawner.class).equals(new ResourceLocation(compound.getString("id"))))
                {
                    if (compound.hasKey("SpawnPotentials", 9))
                    {
                        NBTTagList nbttaglist = compound.getTagList("SpawnPotentials", 10);

                        for (int i = 0; i < nbttaglist.tagCount(); ++i)
                        {
                            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                            nbttagcompound.setTag("Entity", fixer.process(FixTypes.ENTITY, nbttagcompound.getCompoundTag("Entity"), versionIn));
                        }
                    }

                    compound.setTag("SpawnData", fixer.process(FixTypes.ENTITY, compound.getCompoundTag("SpawnData"), versionIn));
                }

                return compound;
            }
        });
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.spawnerLogic.readFromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        this.spawnerLogic.writeToNBT(compound);
        return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        this.spawnerLogic.updateSpawner();
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        nbttagcompound.removeTag("SpawnPotentials");
        return nbttagcompound;
    }

    public boolean receiveClientEvent(int id, int type)
    {
        return this.spawnerLogic.setDelayToMin(id) ? true : super.receiveClientEvent(id, type);
    }

    public boolean onlyOpsCanSetNbt()
    {
        return true;
    }

    public MobSpawnerBaseLogic getSpawnerBaseLogic()
    {
        return this.spawnerLogic;
    }
}
