package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

public class CompiledChunk
{
    public static final CompiledChunk DUMMY = new CompiledChunk()
    {
        protected void setLayerUsed(BlockRenderLayer layer)
        {
            throw new UnsupportedOperationException();
        }
        public void setLayerStarted(BlockRenderLayer layer)
        {
            throw new UnsupportedOperationException();
        }
        public boolean isVisible(EnumFacing facing, EnumFacing facing2)
        {
            return false;
        }
    };
    private final boolean[] layersUsed = new boolean[BlockRenderLayer.values().length];
    private final boolean[] layersStarted = new boolean[BlockRenderLayer.values().length];
    private boolean empty = true;
    private final List<TileEntity> tileEntities = Lists.<TileEntity>newArrayList();
    private SetVisibility setVisibility = new SetVisibility();
    private BufferBuilder.State state;

    public boolean isEmpty()
    {
        return this.empty;
    }

    protected void setLayerUsed(BlockRenderLayer layer)
    {
        this.empty = false;
        this.layersUsed[layer.ordinal()] = true;
    }

    public boolean isLayerEmpty(BlockRenderLayer layer)
    {
        return !this.layersUsed[layer.ordinal()];
    }

    public void setLayerStarted(BlockRenderLayer layer)
    {
        this.layersStarted[layer.ordinal()] = true;
    }

    public boolean isLayerStarted(BlockRenderLayer layer)
    {
        return this.layersStarted[layer.ordinal()];
    }

    public List<TileEntity> getTileEntities()
    {
        return this.tileEntities;
    }

    public void addTileEntity(TileEntity tileEntityIn)
    {
        this.tileEntities.add(tileEntityIn);
    }

    public boolean isVisible(EnumFacing facing, EnumFacing facing2)
    {
        return this.setVisibility.isVisible(facing, facing2);
    }

    public void setVisibility(SetVisibility visibility)
    {
        this.setVisibility = visibility;
    }

    public BufferBuilder.State getState()
    {
        return this.state;
    }

    public void setState(BufferBuilder.State stateIn)
    {
        this.state = stateIn;
    }
}
