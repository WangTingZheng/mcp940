package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase
{
    protected ItemMap()
    {
        this.setHasSubtypes(true);
    }

    public static ItemStack setupNewMap(World worldIn, double worldX, double worldZ, byte scale, boolean trackingPosition, boolean unlimitedTracking)
    {
        ItemStack itemstack = new ItemStack(Items.FILLED_MAP, 1, worldIn.getUniqueDataId("map"));
        String s = "map_" + itemstack.getMetadata();
        MapData mapdata = new MapData(s);
        worldIn.setData(s, mapdata);
        mapdata.scale = scale;
        mapdata.calculateMapCenter(worldX, worldZ, mapdata.scale);
        mapdata.dimension = (byte)worldIn.provider.getDimensionType().getId();
        mapdata.trackingPosition = trackingPosition;
        mapdata.unlimitedTracking = unlimitedTracking;
        mapdata.markDirty();
        return itemstack;
    }

    @Nullable
    public static MapData loadMapData(int mapId, World worldIn)
    {
        String s = "map_" + mapId;
        return (MapData)worldIn.loadData(MapData.class, s);
    }

    @Nullable
    public MapData getMapData(ItemStack stack, World worldIn)
    {
        String s = "map_" + stack.getMetadata();
        MapData mapdata = (MapData)worldIn.loadData(MapData.class, s);

        if (mapdata == null && !worldIn.isRemote)
        {
            stack.setItemDamage(worldIn.getUniqueDataId("map"));
            s = "map_" + stack.getMetadata();
            mapdata = new MapData(s);
            mapdata.scale = 3;
            mapdata.calculateMapCenter((double)worldIn.getWorldInfo().getSpawnX(), (double)worldIn.getWorldInfo().getSpawnZ(), mapdata.scale);
            mapdata.dimension = (byte)worldIn.provider.getDimensionType().getId();
            mapdata.markDirty();
            worldIn.setData(s, mapdata);
        }

        return mapdata;
    }

    public void updateMapData(World worldIn, Entity viewer, MapData data)
    {
        if (worldIn.provider.getDimensionType().getId() == data.dimension && viewer instanceof EntityPlayer)
        {
            int i = 1 << data.scale;
            int j = data.xCenter;
            int k = data.zCenter;
            int l = MathHelper.floor(viewer.posX - (double)j) / i + 64;
            int i1 = MathHelper.floor(viewer.posZ - (double)k) / i + 64;
            int j1 = 128 / i;

            if (worldIn.provider.isNether())
            {
                j1 /= 2;
            }

            MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer)viewer);
            ++mapdata$mapinfo.step;
            boolean flag = false;

            for (int k1 = l - j1 + 1; k1 < l + j1; ++k1)
            {
                if ((k1 & 15) == (mapdata$mapinfo.step & 15) || flag)
                {
                    flag = false;
                    double d0 = 0.0D;

                    for (int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1)
                    {
                        if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128)
                        {
                            int i2 = k1 - l;
                            int j2 = l1 - i1;
                            boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                            int k2 = (j / i + k1 - 64) * i;
                            int l2 = (k / i + l1 - 64) * i;
                            Multiset<MapColor> multiset = HashMultiset.<MapColor>create();
                            Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));

                            if (!chunk.isEmpty())
                            {
                                int i3 = k2 & 15;
                                int j3 = l2 & 15;
                                int k3 = 0;
                                double d1 = 0.0D;

                                if (worldIn.provider.isNether())
                                {
                                    int l3 = k2 + l2 * 231871;
                                    l3 = l3 * l3 * 31287121 + l3 * 11;

                                    if ((l3 >> 20 & 1) == 0)
                                    {
                                        multiset.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT).getMapColor(worldIn, BlockPos.ORIGIN), 10);
                                    }
                                    else
                                    {
                                        multiset.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE).getMapColor(worldIn, BlockPos.ORIGIN), 100);
                                    }

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                                    for (int i4 = 0; i4 < i; ++i4)
                                    {
                                        for (int j4 = 0; j4 < i; ++j4)
                                        {
                                            int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
                                            IBlockState iblockstate = Blocks.AIR.getDefaultState();

                                            if (k4 <= 1)
                                            {
                                                iblockstate = Blocks.BEDROCK.getDefaultState();
                                            }
                                            else
                                            {
                                                label175:
                                                {
                                                    while (true)
                                                    {
                                                        --k4;
                                                        iblockstate = chunk.getBlockState(i4 + i3, k4, j4 + j3);
                                                        blockpos$mutableblockpos.setPos((chunk.x << 4) + i4 + i3, k4, (chunk.z << 4) + j4 + j3);

                                                        if (iblockstate.getMapColor(worldIn, blockpos$mutableblockpos) != MapColor.AIR || k4 <= 0)
                                                        {
                                                            break;
                                                        }
                                                    }

                                                    if (k4 > 0 && iblockstate.getMaterial().isLiquid())
                                                    {
                                                        int l4 = k4 - 1;

                                                        while (true)
                                                        {
                                                            IBlockState iblockstate1 = chunk.getBlockState(i4 + i3, l4--, j4 + j3);
                                                            ++k3;

                                                            if (l4 <= 0 || !iblockstate1.getMaterial().isLiquid())
                                                            {
                                                                break label175;
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            d1 += (double)k4 / (double)(i * i);
                                            multiset.add(iblockstate.getMapColor(worldIn, blockpos$mutableblockpos));
                                        }
                                    }
                                }

                                k3 = k3 / (i * i);
                                double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5D) * 0.4D;
                                int i5 = 1;

                                if (d2 > 0.6D)
                                {
                                    i5 = 2;
                                }

                                if (d2 < -0.6D)
                                {
                                    i5 = 0;
                                }

                                MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.AIR);

                                if (mapcolor == MapColor.WATER)
                                {
                                    d2 = (double)k3 * 0.1D + (double)(k1 + l1 & 1) * 0.2D;
                                    i5 = 1;

                                    if (d2 < 0.5D)
                                    {
                                        i5 = 2;
                                    }

                                    if (d2 > 0.9D)
                                    {
                                        i5 = 0;
                                    }
                                }

                                d0 = d1;

                                if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0))
                                {
                                    byte b0 = data.colors[k1 + l1 * 128];
                                    byte b1 = (byte)(mapcolor.colorIndex * 4 + i5);

                                    if (b0 != b1)
                                    {
                                        data.colors[k1 + l1 * 128] = b1;
                                        data.updateMapData(k1, l1);
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Draws ambiguous landmasses representing unexplored terrain onto a treasure map
     */
    public static void renderBiomePreviewMap(World worldIn, ItemStack map)
    {
        if (map.getItem() == Items.FILLED_MAP)
        {
            MapData mapdata = Items.FILLED_MAP.getMapData(map, worldIn);

            if (mapdata != null)
            {
                if (worldIn.provider.getDimensionType().getId() == mapdata.dimension)
                {
                    int i = 1 << mapdata.scale;
                    int j = mapdata.xCenter;
                    int k = mapdata.zCenter;
                    Biome[] abiome = worldIn.getBiomeProvider().getBiomes((Biome[])null, (j / i - 64) * i, (k / i - 64) * i, 128 * i, 128 * i, false);

                    for (int l = 0; l < 128; ++l)
                    {
                        for (int i1 = 0; i1 < 128; ++i1)
                        {
                            int j1 = l * i;
                            int k1 = i1 * i;
                            Biome biome = abiome[j1 + k1 * 128 * i];
                            MapColor mapcolor = MapColor.AIR;
                            int l1 = 3;
                            int i2 = 8;

                            if (l > 0 && i1 > 0 && l < 127 && i1 < 127)
                            {
                                if (abiome[(l - 1) * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[(l - 1) * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[(l - 1) * i + i1 * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[(l + 1) * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[(l + 1) * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[(l + 1) * i + i1 * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[l * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (abiome[l * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
                                {
                                    --i2;
                                }

                                if (biome.getBaseHeight() < 0.0F)
                                {
                                    mapcolor = MapColor.ADOBE;

                                    if (i2 > 7 && i1 % 2 == 0)
                                    {
                                        l1 = (l + (int)(MathHelper.sin((float)i1 + 0.0F) * 7.0F)) / 8 % 5;

                                        if (l1 == 3)
                                        {
                                            l1 = 1;
                                        }
                                        else if (l1 == 4)
                                        {
                                            l1 = 0;
                                        }
                                    }
                                    else if (i2 > 7)
                                    {
                                        mapcolor = MapColor.AIR;
                                    }
                                    else if (i2 > 5)
                                    {
                                        l1 = 1;
                                    }
                                    else if (i2 > 3)
                                    {
                                        l1 = 0;
                                    }
                                    else if (i2 > 1)
                                    {
                                        l1 = 0;
                                    }
                                }
                                else if (i2 > 0)
                                {
                                    mapcolor = MapColor.BROWN;

                                    if (i2 > 3)
                                    {
                                        l1 = 1;
                                    }
                                    else
                                    {
                                        l1 = 3;
                                    }
                                }
                            }

                            if (mapcolor != MapColor.AIR)
                            {
                                mapdata.colors[l + i1 * 128] = (byte)(mapcolor.colorIndex * 4 + l1);
                                mapdata.updateMapData(l, i1);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (!worldIn.isRemote)
        {
            MapData mapdata = this.getMapData(stack, worldIn);

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                mapdata.updateVisiblePlayers(entityplayer, stack);
            }

            if (isSelected || entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).getHeldItemOffhand() == stack)
            {
                this.updateMapData(worldIn, entityIn, mapdata);
            }
        }
    }

    @Nullable
    public Packet<?> createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player)
    {
        return this.getMapData(stack, worldIn).getMapPacket(stack, worldIn, player);
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            if (nbttagcompound.hasKey("map_scale_direction", 99))
            {
                scaleMap(stack, worldIn, nbttagcompound.getInteger("map_scale_direction"));
                nbttagcompound.removeTag("map_scale_direction");
            }
            else if (nbttagcompound.getBoolean("map_tracking_position"))
            {
                enableMapTracking(stack, worldIn);
                nbttagcompound.removeTag("map_tracking_position");
            }
        }
    }

    protected static void scaleMap(ItemStack p_185063_0_, World p_185063_1_, int p_185063_2_)
    {
        MapData mapdata = Items.FILLED_MAP.getMapData(p_185063_0_, p_185063_1_);
        p_185063_0_.setItemDamage(p_185063_1_.getUniqueDataId("map"));
        MapData mapdata1 = new MapData("map_" + p_185063_0_.getMetadata());

        if (mapdata != null)
        {
            mapdata1.scale = (byte)MathHelper.clamp(mapdata.scale + p_185063_2_, 0, 4);
            mapdata1.trackingPosition = mapdata.trackingPosition;
            mapdata1.calculateMapCenter((double)mapdata.xCenter, (double)mapdata.zCenter, mapdata1.scale);
            mapdata1.dimension = mapdata.dimension;
            mapdata1.markDirty();
            p_185063_1_.setData("map_" + p_185063_0_.getMetadata(), mapdata1);
        }
    }

    protected static void enableMapTracking(ItemStack p_185064_0_, World p_185064_1_)
    {
        MapData mapdata = Items.FILLED_MAP.getMapData(p_185064_0_, p_185064_1_);
        p_185064_0_.setItemDamage(p_185064_1_.getUniqueDataId("map"));
        MapData mapdata1 = new MapData("map_" + p_185064_0_.getMetadata());
        mapdata1.trackingPosition = true;

        if (mapdata != null)
        {
            mapdata1.xCenter = mapdata.xCenter;
            mapdata1.zCenter = mapdata.zCenter;
            mapdata1.scale = mapdata.scale;
            mapdata1.dimension = mapdata.dimension;
            mapdata1.markDirty();
            p_185064_1_.setData("map_" + p_185064_0_.getMetadata(), mapdata1);
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (flagIn.isAdvanced())
        {
            MapData mapdata = worldIn == null ? null : this.getMapData(stack, worldIn);

            if (mapdata != null)
            {
                tooltip.add(I18n.translateToLocalFormatted("filled_map.scale", 1 << mapdata.scale));
                tooltip.add(I18n.translateToLocalFormatted("filled_map.level", mapdata.scale, Integer.valueOf(4)));
            }
            else
            {
                tooltip.add(I18n.translateToLocal("filled_map.unknown"));
            }
        }
    }

    public static int getColor(ItemStack p_190907_0_)
    {
        NBTTagCompound nbttagcompound = p_190907_0_.getSubCompound("display");

        if (nbttagcompound != null && nbttagcompound.hasKey("MapColor", 99))
        {
            int i = nbttagcompound.getInteger("MapColor");
            return -16777216 | i & 16777215;
        }
        else
        {
            return -12173266;
        }
    }
}
