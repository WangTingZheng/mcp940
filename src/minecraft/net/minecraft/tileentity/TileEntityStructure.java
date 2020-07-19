package net.minecraft.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class TileEntityStructure extends TileEntity
{
    private String name = "";
    private String author = "";
    private String metadata = "";
    private BlockPos position = new BlockPos(0, 1, 0);
    private BlockPos size = BlockPos.ORIGIN;
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private TileEntityStructure.Mode mode = TileEntityStructure.Mode.DATA;
    private boolean ignoreEntities = true;
    private boolean powered;
    private boolean showAir;
    private boolean showBoundingBox = true;
    private float integrity = 1.0F;
    private long seed;

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setString("name", this.name);
        compound.setString("author", this.author);
        compound.setString("metadata", this.metadata);
        compound.setInteger("posX", this.position.getX());
        compound.setInteger("posY", this.position.getY());
        compound.setInteger("posZ", this.position.getZ());
        compound.setInteger("sizeX", this.size.getX());
        compound.setInteger("sizeY", this.size.getY());
        compound.setInteger("sizeZ", this.size.getZ());
        compound.setString("rotation", this.rotation.toString());
        compound.setString("mirror", this.mirror.toString());
        compound.setString("mode", this.mode.toString());
        compound.setBoolean("ignoreEntities", this.ignoreEntities);
        compound.setBoolean("powered", this.powered);
        compound.setBoolean("showair", this.showAir);
        compound.setBoolean("showboundingbox", this.showBoundingBox);
        compound.setFloat("integrity", this.integrity);
        compound.setLong("seed", this.seed);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.setName(compound.getString("name"));
        this.author = compound.getString("author");
        this.metadata = compound.getString("metadata");
        int i = MathHelper.clamp(compound.getInteger("posX"), -32, 32);
        int j = MathHelper.clamp(compound.getInteger("posY"), -32, 32);
        int k = MathHelper.clamp(compound.getInteger("posZ"), -32, 32);
        this.position = new BlockPos(i, j, k);
        int l = MathHelper.clamp(compound.getInteger("sizeX"), 0, 32);
        int i1 = MathHelper.clamp(compound.getInteger("sizeY"), 0, 32);
        int j1 = MathHelper.clamp(compound.getInteger("sizeZ"), 0, 32);
        this.size = new BlockPos(l, i1, j1);

        try
        {
            this.rotation = Rotation.valueOf(compound.getString("rotation"));
        }
        catch (IllegalArgumentException var11)
        {
            this.rotation = Rotation.NONE;
        }

        try
        {
            this.mirror = Mirror.valueOf(compound.getString("mirror"));
        }
        catch (IllegalArgumentException var10)
        {
            this.mirror = Mirror.NONE;
        }

        try
        {
            this.mode = TileEntityStructure.Mode.valueOf(compound.getString("mode"));
        }
        catch (IllegalArgumentException var9)
        {
            this.mode = TileEntityStructure.Mode.DATA;
        }

        this.ignoreEntities = compound.getBoolean("ignoreEntities");
        this.powered = compound.getBoolean("powered");
        this.showAir = compound.getBoolean("showair");
        this.showBoundingBox = compound.getBoolean("showboundingbox");

        if (compound.hasKey("integrity"))
        {
            this.integrity = compound.getFloat("integrity");
        }
        else
        {
            this.integrity = 1.0F;
        }

        this.seed = compound.getLong("seed");
        this.updateBlockState();
    }

    private void updateBlockState()
    {
        if (this.world != null)
        {
            BlockPos blockpos = this.getPos();
            IBlockState iblockstate = this.world.getBlockState(blockpos);

            if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
            {
                this.world.setBlockState(blockpos, iblockstate.withProperty(BlockStructure.MODE, this.mode), 2);
            }
        }
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 7, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    public boolean usedBy(EntityPlayer player)
    {
        if (!player.canUseCommandBlock())
        {
            return false;
        }
        else
        {
            if (player.getEntityWorld().isRemote)
            {
                player.openEditStructure(this);
            }

            return true;
        }
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String nameIn)
    {
        String s = nameIn;

        for (char c0 : ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS)
        {
            s = s.replace(c0, '_');
        }

        this.name = s;
    }

    public void createdBy(EntityLivingBase p_189720_1_)
    {
        if (!StringUtils.isNullOrEmpty(p_189720_1_.getName()))
        {
            this.author = p_189720_1_.getName();
        }
    }

    public BlockPos getPosition()
    {
        return this.position;
    }

    public void setPosition(BlockPos posIn)
    {
        this.position = posIn;
    }

    public BlockPos getStructureSize()
    {
        return this.size;
    }

    public void setSize(BlockPos sizeIn)
    {
        this.size = sizeIn;
    }

    public Mirror getMirror()
    {
        return this.mirror;
    }

    public void setMirror(Mirror mirrorIn)
    {
        this.mirror = mirrorIn;
    }

    public Rotation getRotation()
    {
        return this.rotation;
    }

    public void setRotation(Rotation rotationIn)
    {
        this.rotation = rotationIn;
    }

    public String getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(String metadataIn)
    {
        this.metadata = metadataIn;
    }

    public TileEntityStructure.Mode getMode()
    {
        return this.mode;
    }

    public void setMode(TileEntityStructure.Mode modeIn)
    {
        this.mode = modeIn;
        IBlockState iblockstate = this.world.getBlockState(this.getPos());

        if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
        {
            this.world.setBlockState(this.getPos(), iblockstate.withProperty(BlockStructure.MODE, modeIn), 2);
        }
    }

    public void nextMode()
    {
        switch (this.getMode())
        {
            case SAVE:
                this.setMode(TileEntityStructure.Mode.LOAD);
                break;

            case LOAD:
                this.setMode(TileEntityStructure.Mode.CORNER);
                break;

            case CORNER:
                this.setMode(TileEntityStructure.Mode.DATA);
                break;

            case DATA:
                this.setMode(TileEntityStructure.Mode.SAVE);
        }
    }

    public boolean ignoresEntities()
    {
        return this.ignoreEntities;
    }

    public void setIgnoresEntities(boolean ignoreEntitiesIn)
    {
        this.ignoreEntities = ignoreEntitiesIn;
    }

    public float getIntegrity()
    {
        return this.integrity;
    }

    public void setIntegrity(float integrityIn)
    {
        this.integrity = integrityIn;
    }

    public long getSeed()
    {
        return this.seed;
    }

    public void setSeed(long seedIn)
    {
        this.seed = seedIn;
    }

    public boolean detectSize()
    {
        if (this.mode != TileEntityStructure.Mode.SAVE)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = this.getPos();
            int i = 80;
            BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
            BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);
            List<TileEntityStructure> list = this.getNearbyCornerBlocks(blockpos1, blockpos2);
            List<TileEntityStructure> list1 = this.filterRelatedCornerBlocks(list);

            if (list1.size() < 1)
            {
                return false;
            }
            else
            {
                StructureBoundingBox structureboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);

                if (structureboundingbox.maxX - structureboundingbox.minX > 1 && structureboundingbox.maxY - structureboundingbox.minY > 1 && structureboundingbox.maxZ - structureboundingbox.minZ > 1)
                {
                    this.position = new BlockPos(structureboundingbox.minX - blockpos.getX() + 1, structureboundingbox.minY - blockpos.getY() + 1, structureboundingbox.minZ - blockpos.getZ() + 1);
                    this.size = new BlockPos(structureboundingbox.maxX - structureboundingbox.minX - 1, structureboundingbox.maxY - structureboundingbox.minY - 1, structureboundingbox.maxZ - structureboundingbox.minZ - 1);
                    this.markDirty();
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    this.world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    private List<TileEntityStructure> filterRelatedCornerBlocks(List<TileEntityStructure> p_184415_1_)
    {
        Iterable<TileEntityStructure> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntityStructure>()
        {
            public boolean apply(@Nullable TileEntityStructure p_apply_1_)
            {
                return p_apply_1_.mode == TileEntityStructure.Mode.CORNER && TileEntityStructure.this.name.equals(p_apply_1_.name);
            }
        });
        return Lists.newArrayList(iterable);
    }

    private List<TileEntityStructure> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_)
    {
        List<TileEntityStructure> list = Lists.<TileEntityStructure>newArrayList();

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(p_184418_1_, p_184418_2_))
        {
            IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos);

            if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
            {
                TileEntity tileentity = this.world.getTileEntity(blockpos$mutableblockpos);

                if (tileentity != null && tileentity instanceof TileEntityStructure)
                {
                    list.add((TileEntityStructure)tileentity);
                }
            }
        }

        return list;
    }

    private StructureBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<TileEntityStructure> p_184416_2_)
    {
        StructureBoundingBox structureboundingbox;

        if (p_184416_2_.size() > 1)
        {
            BlockPos blockpos = ((TileEntityStructure)p_184416_2_.get(0)).getPos();
            structureboundingbox = new StructureBoundingBox(blockpos, blockpos);
        }
        else
        {
            structureboundingbox = new StructureBoundingBox(p_184416_1_, p_184416_1_);
        }

        for (TileEntityStructure tileentitystructure : p_184416_2_)
        {
            BlockPos blockpos1 = tileentitystructure.getPos();

            if (blockpos1.getX() < structureboundingbox.minX)
            {
                structureboundingbox.minX = blockpos1.getX();
            }
            else if (blockpos1.getX() > structureboundingbox.maxX)
            {
                structureboundingbox.maxX = blockpos1.getX();
            }

            if (blockpos1.getY() < structureboundingbox.minY)
            {
                structureboundingbox.minY = blockpos1.getY();
            }
            else if (blockpos1.getY() > structureboundingbox.maxY)
            {
                structureboundingbox.maxY = blockpos1.getY();
            }

            if (blockpos1.getZ() < structureboundingbox.minZ)
            {
                structureboundingbox.minZ = blockpos1.getZ();
            }
            else if (blockpos1.getZ() > structureboundingbox.maxZ)
            {
                structureboundingbox.maxZ = blockpos1.getZ();
            }
        }

        return structureboundingbox;
    }

    public void writeCoordinates(ByteBuf buf)
    {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
    }

    public boolean save()
    {
        return this.save(true);
    }

    public boolean save(boolean p_189712_1_)
    {
        if (this.mode == TileEntityStructure.Mode.SAVE && !this.world.isRemote && !StringUtils.isNullOrEmpty(this.name))
        {
            BlockPos blockpos = this.getPos().add(this.position);
            WorldServer worldserver = (WorldServer)this.world;
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(this.name));
            template.takeBlocksFromWorld(this.world, blockpos, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
            template.setAuthor(this.author);
            return !p_189712_1_ || templatemanager.writeTemplate(minecraftserver, new ResourceLocation(this.name));
        }
        else
        {
            return false;
        }
    }

    public boolean load()
    {
        return this.load(true);
    }

    public boolean load(boolean p_189714_1_)
    {
        if (this.mode == TileEntityStructure.Mode.LOAD && !this.world.isRemote && !StringUtils.isNullOrEmpty(this.name))
        {
            BlockPos blockpos = this.getPos();
            BlockPos blockpos1 = blockpos.add(this.position);
            WorldServer worldserver = (WorldServer)this.world;
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            Template template = templatemanager.get(minecraftserver, new ResourceLocation(this.name));

            if (template == null)
            {
                return false;
            }
            else
            {
                if (!StringUtils.isNullOrEmpty(template.getAuthor()))
                {
                    this.author = template.getAuthor();
                }

                BlockPos blockpos2 = template.getSize();
                boolean flag = this.size.equals(blockpos2);

                if (!flag)
                {
                    this.size = blockpos2;
                    this.markDirty();
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    this.world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                }

                if (p_189714_1_ && !flag)
                {
                    return false;
                }
                else
                {
                    PlacementSettings placementsettings = (new PlacementSettings()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunk((ChunkPos)null).setReplacedBlock((Block)null).setIgnoreStructureBlock(false);

                    if (this.integrity < 1.0F)
                    {
                        placementsettings.setIntegrity(MathHelper.clamp(this.integrity, 0.0F, 1.0F)).setSeed(Long.valueOf(this.seed));
                    }

                    template.addBlocksToWorldChunk(this.world, blockpos1, placementsettings);
                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    public void unloadStructure()
    {
        WorldServer worldserver = (WorldServer)this.world;
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        templatemanager.remove(new ResourceLocation(this.name));
    }

    public boolean isStructureLoadable()
    {
        if (this.mode == TileEntityStructure.Mode.LOAD && !this.world.isRemote)
        {
            WorldServer worldserver = (WorldServer)this.world;
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            return templatemanager.get(minecraftserver, new ResourceLocation(this.name)) != null;
        }
        else
        {
            return false;
        }
    }

    public boolean isPowered()
    {
        return this.powered;
    }

    public void setPowered(boolean poweredIn)
    {
        this.powered = poweredIn;
    }

    public boolean showsAir()
    {
        return this.showAir;
    }

    public void setShowAir(boolean showAirIn)
    {
        this.showAir = showAirIn;
    }

    public boolean showsBoundingBox()
    {
        return this.showBoundingBox;
    }

    public void setShowBoundingBox(boolean showBoundingBoxIn)
    {
        this.showBoundingBox = showBoundingBoxIn;
    }

    @Nullable

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return new TextComponentTranslation("structure_block.hover." + this.mode.modeName, new Object[] {this.mode == TileEntityStructure.Mode.DATA ? this.metadata : this.name});
    }

    public static enum Mode implements IStringSerializable
    {
        SAVE("save", 0),
        LOAD("load", 1),
        CORNER("corner", 2),
        DATA("data", 3);

        private static final TileEntityStructure.Mode[] MODES = new TileEntityStructure.Mode[values().length];
        private final String modeName;
        private final int modeId;

        private Mode(String modeNameIn, int modeIdIn)
        {
            this.modeName = modeNameIn;
            this.modeId = modeIdIn;
        }

        public String getName()
        {
            return this.modeName;
        }

        public int getModeId()
        {
            return this.modeId;
        }

        public static TileEntityStructure.Mode getById(int id)
        {
            return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
        }

        static {
            for (TileEntityStructure.Mode tileentitystructure$mode : values())
            {
                MODES[tileentitystructure$mode.getModeId()] = tileentitystructure$mode;
            }
        }
    }
}
