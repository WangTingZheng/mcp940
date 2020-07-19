package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RandomMobs
{
    private static Map locationProperties = new HashMap();
    private static RenderGlobal renderGlobal = null;
    private static boolean initialized = false;
    private static Random random = new Random();
    private static boolean working = false;
    public static final String SUFFIX_PNG = ".png";
    public static final String SUFFIX_PROPERTIES = ".properties";
    public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
    public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
    private static final String[] DEPENDANT_SUFFIXES = new String[] {"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};

    public static void entityLoaded(Entity p_entityLoaded_0_, World p_entityLoaded_1_)
    {
        if (p_entityLoaded_0_ instanceof EntityLiving)
        {
            if (p_entityLoaded_1_ != null)
            {
                EntityLiving entityliving = (EntityLiving)p_entityLoaded_0_;
                entityliving.spawnPosition = entityliving.getPosition();
                entityliving.spawnBiome = p_entityLoaded_1_.getBiome(entityliving.spawnPosition);
                WorldServer worldserver = Config.getWorldServer();

                if (worldserver != null)
                {
                    Entity entity = worldserver.getEntityByID(p_entityLoaded_0_.getEntityId());

                    if (entity instanceof EntityLiving)
                    {
                        EntityLiving entityliving1 = (EntityLiving)entity;
                        UUID uuid = entityliving1.getUniqueID();
                        long i = uuid.getLeastSignificantBits();
                        int j = (int)(i & 2147483647L);
                        entityliving.randomMobsId = j;
                    }
                }
            }
        }
    }

    public static void worldChanged(World p_worldChanged_0_, World p_worldChanged_1_)
    {
        if (p_worldChanged_1_ != null)
        {
            List list = p_worldChanged_1_.getLoadedEntityList();

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);
                entityLoaded(entity, p_worldChanged_1_);
            }
        }
    }

    public static ResourceLocation getTextureLocation(ResourceLocation p_getTextureLocation_0_)
    {
        if (working)
        {
            return p_getTextureLocation_0_;
        }
        else
        {
            ResourceLocation entity;

            try
            {
                working = true;

                if (!initialized)
                {
                    initialize();
                }

                if (renderGlobal != null)
                {
                    Entity entity1 = renderGlobal.renderedEntity;

                    if (!(entity1 instanceof EntityLiving))
                    {
                        ResourceLocation resourcelocation2 = p_getTextureLocation_0_;
                        return resourcelocation2;
                    }

                    EntityLiving entityliving = (EntityLiving)entity1;
                    String s = p_getTextureLocation_0_.getResourcePath();

                    if (!s.startsWith("textures/entity/"))
                    {
                        ResourceLocation resourcelocation3 = p_getTextureLocation_0_;
                        return resourcelocation3;
                    }

                    RandomMobsProperties randommobsproperties = getProperties(p_getTextureLocation_0_);

                    if (randommobsproperties == null)
                    {
                        ResourceLocation resourcelocation4 = p_getTextureLocation_0_;
                        return resourcelocation4;
                    }

                    ResourceLocation resourcelocation1 = randommobsproperties.getTextureLocation(p_getTextureLocation_0_, entityliving);
                    return resourcelocation1;
                }

                entity = p_getTextureLocation_0_;
            }
            finally
            {
                working = false;
            }

            return entity;
        }
    }

    private static RandomMobsProperties getProperties(ResourceLocation p_getProperties_0_)
    {
        String s = p_getProperties_0_.getResourcePath();
        RandomMobsProperties randommobsproperties = (RandomMobsProperties)locationProperties.get(s);

        if (randommobsproperties == null)
        {
            randommobsproperties = makeProperties(p_getProperties_0_);
            locationProperties.put(s, randommobsproperties);
        }

        return randommobsproperties;
    }

    private static RandomMobsProperties makeProperties(ResourceLocation p_makeProperties_0_)
    {
        String s = p_makeProperties_0_.getResourcePath();
        ResourceLocation resourcelocation = getPropertyLocation(p_makeProperties_0_);

        if (resourcelocation != null)
        {
            RandomMobsProperties randommobsproperties = parseProperties(resourcelocation, p_makeProperties_0_);

            if (randommobsproperties != null)
            {
                return randommobsproperties;
            }
        }

        ResourceLocation[] aresourcelocation = getTextureVariants(p_makeProperties_0_);
        return new RandomMobsProperties(s, aresourcelocation);
    }

    private static RandomMobsProperties parseProperties(ResourceLocation p_parseProperties_0_, ResourceLocation p_parseProperties_1_)
    {
        try
        {
            String s = p_parseProperties_0_.getResourcePath();
            Config.dbg("RandomMobs: " + p_parseProperties_1_.getResourcePath() + ", variants: " + s);
            InputStream inputstream = Config.getResourceStream(p_parseProperties_0_);

            if (inputstream == null)
            {
                Config.warn("RandomMobs properties not found: " + s);
                return null;
            }
            else
            {
                Properties properties = new Properties();
                properties.load(inputstream);
                inputstream.close();
                RandomMobsProperties randommobsproperties = new RandomMobsProperties(properties, s, p_parseProperties_1_);
                return !randommobsproperties.isValid(s) ? null : randommobsproperties;
            }
        }
        catch (FileNotFoundException var6)
        {
            Config.warn("RandomMobs file not found: " + p_parseProperties_1_.getResourcePath());
            return null;
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            return null;
        }
    }

    private static ResourceLocation getPropertyLocation(ResourceLocation p_getPropertyLocation_0_)
    {
        ResourceLocation resourcelocation = getMcpatcherLocation(p_getPropertyLocation_0_);

        if (resourcelocation == null)
        {
            return null;
        }
        else
        {
            String s = resourcelocation.getResourceDomain();
            String s1 = resourcelocation.getResourcePath();
            String s2 = s1;

            if (s1.endsWith(".png"))
            {
                s2 = s1.substring(0, s1.length() - ".png".length());
            }

            String s3 = s2 + ".properties";
            ResourceLocation resourcelocation1 = new ResourceLocation(s, s3);

            if (Config.hasResource(resourcelocation1))
            {
                return resourcelocation1;
            }
            else
            {
                String s4 = getParentPath(s2);

                if (s4 == null)
                {
                    return null;
                }
                else
                {
                    ResourceLocation resourcelocation2 = new ResourceLocation(s, s4 + ".properties");
                    return Config.hasResource(resourcelocation2) ? resourcelocation2 : null;
                }
            }
        }
    }

    public static ResourceLocation getMcpatcherLocation(ResourceLocation p_getMcpatcherLocation_0_)
    {
        String s = p_getMcpatcherLocation_0_.getResourcePath();

        if (!s.startsWith("textures/entity/"))
        {
            return null;
        }
        else
        {
            String s1 = "mcpatcher/mob/" + s.substring("textures/entity/".length());
            return new ResourceLocation(p_getMcpatcherLocation_0_.getResourceDomain(), s1);
        }
    }

    public static ResourceLocation getLocationIndexed(ResourceLocation p_getLocationIndexed_0_, int p_getLocationIndexed_1_)
    {
        if (p_getLocationIndexed_0_ == null)
        {
            return null;
        }
        else
        {
            String s = p_getLocationIndexed_0_.getResourcePath();
            int i = s.lastIndexOf(46);

            if (i < 0)
            {
                return null;
            }
            else
            {
                String s1 = s.substring(0, i);
                String s2 = s.substring(i);
                String s3 = s1 + p_getLocationIndexed_1_ + s2;
                ResourceLocation resourcelocation = new ResourceLocation(p_getLocationIndexed_0_.getResourceDomain(), s3);
                return resourcelocation;
            }
        }
    }

    private static String getParentPath(String p_getParentPath_0_)
    {
        for (int i = 0; i < DEPENDANT_SUFFIXES.length; ++i)
        {
            String s = DEPENDANT_SUFFIXES[i];

            if (p_getParentPath_0_.endsWith(s))
            {
                String s1 = p_getParentPath_0_.substring(0, p_getParentPath_0_.length() - s.length());
                return s1;
            }
        }

        return null;
    }

    private static ResourceLocation[] getTextureVariants(ResourceLocation p_getTextureVariants_0_)
    {
        List list = new ArrayList();
        list.add(p_getTextureVariants_0_);
        ResourceLocation resourcelocation = getMcpatcherLocation(p_getTextureVariants_0_);

        if (resourcelocation == null)
        {
            return null;
        }
        else
        {
            for (int i = 1; i < list.size() + 10; ++i)
            {
                int j = i + 1;
                ResourceLocation resourcelocation1 = getLocationIndexed(resourcelocation, j);

                if (Config.hasResource(resourcelocation1))
                {
                    list.add(resourcelocation1);
                }
            }

            if (list.size() <= 1)
            {
                return null;
            }
            else
            {
                ResourceLocation[] aresourcelocation = (ResourceLocation[])list.toArray(new ResourceLocation[list.size()]);
                Config.dbg("RandomMobs: " + p_getTextureVariants_0_.getResourcePath() + ", variants: " + aresourcelocation.length);
                return aresourcelocation;
            }
        }
    }

    public static void resetTextures()
    {
        locationProperties.clear();

        if (Config.isRandomMobs())
        {
            initialize();
        }
    }

    private static void initialize()
    {
        renderGlobal = Config.getRenderGlobal();

        if (renderGlobal != null)
        {
            initialized = true;
            List list = new ArrayList();
            list.add("bat");
            list.add("blaze");
            list.add("cat/black");
            list.add("cat/ocelot");
            list.add("cat/red");
            list.add("cat/siamese");
            list.add("chicken");
            list.add("cow/cow");
            list.add("cow/mooshroom");
            list.add("creeper/creeper");
            list.add("enderman/enderman");
            list.add("enderman/enderman_eyes");
            list.add("ghast/ghast");
            list.add("ghast/ghast_shooting");
            list.add("iron_golem");
            list.add("pig/pig");
            list.add("sheep/sheep");
            list.add("sheep/sheep_fur");
            list.add("silverfish");
            list.add("skeleton/skeleton");
            list.add("skeleton/wither_skeleton");
            list.add("slime/slime");
            list.add("slime/magmacube");
            list.add("snowman");
            list.add("spider/cave_spider");
            list.add("spider/spider");
            list.add("spider_eyes");
            list.add("squid");
            list.add("villager/villager");
            list.add("villager/butcher");
            list.add("villager/farmer");
            list.add("villager/librarian");
            list.add("villager/priest");
            list.add("villager/smith");
            list.add("wither/wither");
            list.add("wither/wither_armor");
            list.add("wither/wither_invulnerable");
            list.add("wolf/wolf");
            list.add("wolf/wolf_angry");
            list.add("wolf/wolf_collar");
            list.add("wolf/wolf_tame");
            list.add("zombie_pigman");
            list.add("zombie/zombie");
            list.add("zombie/zombie_villager");

            for (int i = 0; i < list.size(); ++i)
            {
                String s = (String)list.get(i);
                String s1 = "textures/entity/" + s + ".png";
                ResourceLocation resourcelocation = new ResourceLocation(s1);

                if (!Config.hasResource(resourcelocation))
                {
                    Config.warn("Not found: " + resourcelocation);
                }

                getProperties(resourcelocation);
            }
        }
    }
}
