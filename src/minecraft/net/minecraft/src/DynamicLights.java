package net.minecraft.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamicLights
{
    private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap<Integer, DynamicLight>();
    private static long timeUpdateMs = 0L;
    private static final double MAX_DIST = 7.5D;
    private static final double MAX_DIST_SQ = 56.25D;
    private static final int LIGHT_LEVEL_MAX = 15;
    private static final int LIGHT_LEVEL_FIRE = 15;
    private static final int LIGHT_LEVEL_BLAZE = 10;
    private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
    private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
    private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
    private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
    private static final DataParameter<ItemStack> PARAMETER_ITEM_STACK = new DataParameter<ItemStack>(6, DataSerializers.ITEM_STACK);

    public static void entityAdded(Entity p_entityAdded_0_, RenderGlobal p_entityAdded_1_)
    {
    }

    public static void entityRemoved(Entity p_entityRemoved_0_, RenderGlobal p_entityRemoved_1_)
    {
        synchronized (mapDynamicLights)
        {
            DynamicLight dynamiclight = mapDynamicLights.remove(net.minecraft.util.IntegerCache.getInteger(p_entityRemoved_0_.getEntityId()));

            if (dynamiclight != null)
            {
                dynamiclight.updateLitChunks(p_entityRemoved_1_);
            }
        }
    }

    public static void update(RenderGlobal p_update_0_)
    {
        long i = System.currentTimeMillis();

        if (i >= timeUpdateMs + 50L)
        {
            timeUpdateMs = i;

            synchronized (mapDynamicLights)
            {
                updateMapDynamicLights(p_update_0_);

                if (mapDynamicLights.size() > 0)
                {
                    for (DynamicLight dynamiclight : mapDynamicLights.values())
                    {
                        dynamiclight.update(p_update_0_);
                    }
                }
            }
        }
    }

    private static void updateMapDynamicLights(RenderGlobal p_updateMapDynamicLights_0_)
    {
        World world = p_updateMapDynamicLights_0_.getWorld();

        if (world != null)
        {
            for (Entity entity : world.getLoadedEntityList())
            {
                int i = getLightLevel(entity);

                if (i > 0)
                {
                    Integer integer = net.minecraft.util.IntegerCache.getInteger(entity.getEntityId());
                    DynamicLight dynamiclight = mapDynamicLights.get(integer);

                    if (dynamiclight == null)
                    {
                        dynamiclight = new DynamicLight(entity);
                        mapDynamicLights.put(integer, dynamiclight);
                    }
                }
                else
                {
                    Integer integer1 = net.minecraft.util.IntegerCache.getInteger(entity.getEntityId());
                    DynamicLight dynamiclight1 = mapDynamicLights.remove(integer1);

                    if (dynamiclight1 != null)
                    {
                        dynamiclight1.updateLitChunks(p_updateMapDynamicLights_0_);
                    }
                }
            }
        }
    }

    public static int getCombinedLight(BlockPos p_getCombinedLight_0_, int p_getCombinedLight_1_)
    {
        double d0 = getLightLevel(p_getCombinedLight_0_);
        p_getCombinedLight_1_ = getCombinedLight(d0, p_getCombinedLight_1_);
        return p_getCombinedLight_1_;
    }

    public static int getCombinedLight(Entity p_getCombinedLight_0_, int p_getCombinedLight_1_)
    {
        double d0 = (double)getLightLevel(p_getCombinedLight_0_);
        p_getCombinedLight_1_ = getCombinedLight(d0, p_getCombinedLight_1_);
        return p_getCombinedLight_1_;
    }

    public static int getCombinedLight(double p_getCombinedLight_0_, int p_getCombinedLight_2_)
    {
        if (p_getCombinedLight_0_ > 0.0D)
        {
            int i = (int)(p_getCombinedLight_0_ * 16.0D);
            int j = p_getCombinedLight_2_ & 255;

            if (i > j)
            {
                p_getCombinedLight_2_ = p_getCombinedLight_2_ & -256;
                p_getCombinedLight_2_ = p_getCombinedLight_2_ | i;
            }
        }

        return p_getCombinedLight_2_;
    }

    public static double getLightLevel(BlockPos p_getLightLevel_0_)
    {
        double d0 = 0.0D;

        synchronized (mapDynamicLights)
        {
            for (DynamicLight dynamiclight : mapDynamicLights.values())
            {
                int i = dynamiclight.getLastLightLevel();

                if (i > 0)
                {
                    double d1 = dynamiclight.getLastPosX();
                    double d2 = dynamiclight.getLastPosY();
                    double d3 = dynamiclight.getLastPosZ();
                    double d4 = (double)p_getLightLevel_0_.getX() - d1;
                    double d5 = (double)p_getLightLevel_0_.getY() - d2;
                    double d6 = (double)p_getLightLevel_0_.getZ() - d3;
                    double d7 = d4 * d4 + d5 * d5 + d6 * d6;

                    if (dynamiclight.isUnderwater() && !Config.isClearWater())
                    {
                        i = Config.limit(i - 2, 0, 15);
                        d7 *= 2.0D;
                    }

                    if (d7 <= 56.25D)
                    {
                        double d8 = Math.sqrt(d7);
                        double d9 = 1.0D - d8 / 7.5D;
                        double d10 = d9 * (double)i;

                        if (d10 > d0)
                        {
                            d0 = d10;
                        }
                    }
                }
            }
        }

        double d11 = Config.limit(d0, 0.0D, 15.0D);
        return d11;
    }

    public static int getLightLevel(ItemStack p_getLightLevel_0_)
    {
        if (p_getLightLevel_0_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_getLightLevel_0_.getItem();

            if (item instanceof ItemBlock)
            {
                ItemBlock itemblock = (ItemBlock)item;
                Block block = itemblock.getBlock();

                if (block != null)
                {
                    return block.getLightValue(block.getDefaultState());
                }
            }

            if (item == Items.LAVA_BUCKET)
            {
                return Blocks.LAVA.getLightValue(Blocks.LAVA.getDefaultState());
            }
            else if (item != Items.BLAZE_ROD && item != Items.BLAZE_POWDER)
            {
                if (item == Items.GLOWSTONE_DUST)
                {
                    return 8;
                }
                else if (item == Items.PRISMARINE_CRYSTALS)
                {
                    return 8;
                }
                else if (item == Items.MAGMA_CREAM)
                {
                    return 8;
                }
                else
                {
                    return item == Items.NETHER_STAR ? Blocks.BEACON.getLightValue(Blocks.BEACON.getDefaultState()) / 2 : 0;
                }
            }
            else
            {
                return 10;
            }
        }
    }

    public static int getLightLevel(Entity p_getLightLevel_0_)
    {
        if (p_getLightLevel_0_ == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight())
        {
            return 0;
        }
        else
        {
            if (p_getLightLevel_0_ instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)p_getLightLevel_0_;

                if (entityplayer.isSpectator())
                {
                    return 0;
                }
            }

            if (p_getLightLevel_0_.isBurning())
            {
                return 15;
            }
            else if (p_getLightLevel_0_ instanceof EntityFireball)
            {
                return 15;
            }
            else if (p_getLightLevel_0_ instanceof EntityTNTPrimed)
            {
                return 15;
            }
            else if (p_getLightLevel_0_ instanceof EntityBlaze)
            {
                EntityBlaze entityblaze = (EntityBlaze)p_getLightLevel_0_;
                return entityblaze.isCharged() ? 15 : 10;
            }
            else if (p_getLightLevel_0_ instanceof EntityMagmaCube)
            {
                EntityMagmaCube entitymagmacube = (EntityMagmaCube)p_getLightLevel_0_;
                return (double)entitymagmacube.squishFactor > 0.6D ? 13 : 8;
            }
            else
            {
                if (p_getLightLevel_0_ instanceof EntityCreeper)
                {
                    EntityCreeper entitycreeper = (EntityCreeper)p_getLightLevel_0_;

                    if ((double)entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D)
                    {
                        return 15;
                    }
                }

                if (p_getLightLevel_0_ instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)p_getLightLevel_0_;
                    ItemStack itemstack3 = entitylivingbase.getHeldItemMainhand();
                    int i = getLightLevel(itemstack3);
                    ItemStack itemstack1 = entitylivingbase.getHeldItemOffhand();
                    int j = getLightLevel(itemstack1);
                    ItemStack itemstack2 = entitylivingbase.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    int k = getLightLevel(itemstack2);
                    int l = Math.max(i, j);
                    return Math.max(l, k);
                }
                else if (p_getLightLevel_0_ instanceof EntityItem)
                {
                    EntityItem entityitem = (EntityItem)p_getLightLevel_0_;
                    ItemStack itemstack = getItemStack(entityitem);
                    return getLightLevel(itemstack);
                }
                else
                {
                    return 0;
                }
            }
        }
    }

    public static void removeLights(RenderGlobal p_removeLights_0_)
    {
        synchronized (mapDynamicLights)
        {
            Collection<DynamicLight> collection = mapDynamicLights.values();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext())
            {
                DynamicLight dynamiclight = (DynamicLight)iterator.next();
                iterator.remove();
                dynamiclight.updateLitChunks(p_removeLights_0_);
            }
        }
    }

    public static void clear()
    {
        synchronized (mapDynamicLights)
        {
            mapDynamicLights.clear();
        }
    }

    public static int getCount()
    {
        synchronized (mapDynamicLights)
        {
            return mapDynamicLights.size();
        }
    }

    public static ItemStack getItemStack(EntityItem p_getItemStack_0_)
    {
        ItemStack itemstack = (ItemStack)p_getItemStack_0_.getDataManager().get(PARAMETER_ITEM_STACK);
        return itemstack;
    }
}
