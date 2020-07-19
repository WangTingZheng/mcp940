package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.src.PlayerItemsLayer;
import net.minecraft.src.Reflector;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.optifine.entity.model.CustomEntityModels;

public class RenderManager
{
    private final Map<Class, Render> entityRenderMap = Maps.newHashMap();
    private final Map<String, RenderPlayer> skinMap = Maps.<String, RenderPlayer>newHashMap();
    private final RenderPlayer playerRenderer;

    /** Renders fonts */
    private FontRenderer textRenderer;
    private double renderPosX;
    private double renderPosY;
    private double renderPosZ;
    public TextureManager renderEngine;

    /** Reference to the World object. */
    public World world;

    /** RenderManager's field for the renderViewEntity */
    public Entity renderViewEntity;
    public Entity pointedEntity;
    public float playerViewY;
    public float playerViewX;

    /** Reference to the GameSettings object. */
    public GameSettings options;
    public double viewerPosX;
    public double viewerPosY;
    public double viewerPosZ;
    private boolean renderOutlines;
    private boolean renderShadow = true;

    /** whether bounding box should be rendered or not */
    private boolean debugBoundingBox;
    public Entity renderEntity = null;
    public Render renderRender = null;

    public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn)
    {
        this.renderEngine = renderEngineIn;
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
        this.entityRenderMap.put(EntityPig.class, new RenderPig(this));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(this));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this));
        this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this));
        this.entityRenderMap.put(EntityParrot.class, new RenderParrot(this));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
        this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
        this.entityRenderMap.put(EntityWitherSkeleton.class, new RenderWitherSkeleton(this));
        this.entityRenderMap.put(EntityStray.class, new RenderStray(this));
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
        this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
        this.entityRenderMap.put(EntityZombieVillager.class, new RenderZombieVillager(this));
        this.entityRenderMap.put(EntityHusk.class, new RenderHusk(this));
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, 6.0F));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
        this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
        this.entityRenderMap.put(EntityElderGuardian.class, new RenderElderGuardian(this));
        this.entityRenderMap.put(EntityShulker.class, new RenderShulker(this));
        this.entityRenderMap.put(EntityPolarBear.class, new RenderPolarBear(this));
        this.entityRenderMap.put(EntityEvoker.class, new RenderEvoker(this));
        this.entityRenderMap.put(EntityVindicator.class, new RenderVindicator(this));
        this.entityRenderMap.put(EntityVex.class, new RenderVex(this));
        this.entityRenderMap.put(EntityIllusionIllager.class, new RenderIllusionIllager(this));
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
        this.entityRenderMap.put(Entity.class, new RenderEntity(this));
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.entityRenderMap.put(EntityTippedArrow.class, new RenderTippedArrow(this));
        this.entityRenderMap.put(EntitySpectralArrow.class, new RenderSpectralArrow(this));
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.SNOWBALL, itemRendererIn));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ENDER_PEARL, itemRendererIn));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ENDER_EYE, itemRendererIn));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.EGG, itemRendererIn));
        this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.EXPERIENCE_BOTTLE, itemRendererIn));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.FIREWORKS, itemRendererIn));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
        this.entityRenderMap.put(EntityDragonFireball.class, new RenderDragonFireball(this));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.entityRenderMap.put(EntityShulkerBullet.class, new RenderShulkerBullet(this));
        this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.entityRenderMap.put(EntityArmorStand.class, new RenderArmorStand(this));
        this.entityRenderMap.put(EntityEvokerFangs.class, new RenderEvokerFangs(this));
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
        this.entityRenderMap.put(EntityAreaEffectCloud.class, new RenderAreaEffectCloud(this));
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this));
        this.entityRenderMap.put(EntitySkeletonHorse.class, new RenderAbstractHorse(this));
        this.entityRenderMap.put(EntityZombieHorse.class, new RenderAbstractHorse(this));
        this.entityRenderMap.put(EntityMule.class, new RenderAbstractHorse(this, 0.92F));
        this.entityRenderMap.put(EntityDonkey.class, new RenderAbstractHorse(this, 0.87F));
        this.entityRenderMap.put(EntityLlama.class, new RenderLlama(this));
        this.entityRenderMap.put(EntityLlamaSpit.class, new RenderLlamaSpit(this));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.playerRenderer = new RenderPlayer(this);
        this.skinMap.put("default", this.playerRenderer);
        this.skinMap.put("slim", new RenderPlayer(this, true));
        PlayerItemsLayer.register(this.skinMap);

        if (Reflector.RenderingRegistry_loadEntityRenderers.exists())
        {
            Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, this, this.entityRenderMap);
        }
    }

    public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn)
    {
        this.renderPosX = renderPosXIn;
        this.renderPosY = renderPosYIn;
        this.renderPosZ = renderPosZIn;
    }

    public <T extends Entity> Render<T> getEntityClassRenderObject(Class <? extends Entity > entityClass)
    {
        Render<T> render = (Render)this.entityRenderMap.get(entityClass);

        if (render == null && entityClass != Entity.class)
        {
            render = this.getEntityClassRenderObject((Class<? extends Entity>) entityClass.getSuperclass());
            this.entityRenderMap.put(entityClass, render);
        }

        return render;
    }

    @Nullable
    public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn)
    {
        if (entityIn instanceof AbstractClientPlayer)
        {
            String s = ((AbstractClientPlayer)entityIn).getSkinType();
            RenderPlayer renderplayer = this.skinMap.get(s);
            return (Render<T>)(renderplayer != null ? renderplayer : this.playerRenderer);
        }
        else
        {
            return this.<T>getEntityClassRenderObject(entityIn.getClass());
        }
    }

    public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks)
    {
        this.world = worldIn;
        this.options = optionsIn;
        this.renderViewEntity = livingPlayerIn;
        this.pointedEntity = pointedEntityIn;
        this.textRenderer = textRendererIn;

        if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping())
        {
            IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
            Block block = iblockstate.getBlock();

            if (Reflector.callBoolean(block, Reflector.ForgeBlock_isBed, iblockstate, worldIn, new BlockPos(livingPlayerIn), (EntityLivingBase)livingPlayerIn))
            {
                EnumFacing enumfacing = (EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, iblockstate, worldIn, new BlockPos(livingPlayerIn));
                int i = enumfacing.getHorizontalIndex();
                this.playerViewY = (float)(i * 90 + 180);
                this.playerViewX = 0.0F;
            }
            else if (block == Blocks.BED)
            {
                int j = ((EnumFacing)iblockstate.getValue(BlockBed.FACING)).getHorizontalIndex();
                this.playerViewY = (float)(j * 90 + 180);
                this.playerViewX = 0.0F;
            }
        }
        else
        {
            this.playerViewY = livingPlayerIn.prevRotationYaw + (livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
            this.playerViewX = livingPlayerIn.prevRotationPitch + (livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
        }

        if (optionsIn.thirdPersonView == 2)
        {
            this.playerViewY += 180.0F;
        }

        this.viewerPosX = livingPlayerIn.lastTickPosX + (livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * (double)partialTicks;
        this.viewerPosY = livingPlayerIn.lastTickPosY + (livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * (double)partialTicks;
        this.viewerPosZ = livingPlayerIn.lastTickPosZ + (livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * (double)partialTicks;
    }

    public void setPlayerViewY(float playerViewYIn)
    {
        this.playerViewY = playerViewYIn;
    }

    public boolean isRenderShadow()
    {
        return this.renderShadow;
    }

    public void setRenderShadow(boolean renderShadowIn)
    {
        this.renderShadow = renderShadowIn;
    }

    public void setDebugBoundingBox(boolean debugBoundingBoxIn)
    {
        this.debugBoundingBox = debugBoundingBoxIn;
    }

    public boolean isDebugBoundingBox()
    {
        return this.debugBoundingBox;
    }

    public boolean isRenderMultipass(Entity p_188390_1_)
    {
        return this.getEntityRenderObject(p_188390_1_).isMultipass();
    }

    public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ)
    {
        Render<Entity> render = this.<Entity>getEntityRenderObject(entityIn);
        return render != null && render.shouldRender(entityIn, camera, camX, camY, camZ);
    }

    public void renderEntityStatic(Entity entityIn, float partialTicks, boolean p_188388_3_)
    {
        if (entityIn.ticksExisted == 0)
        {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }

        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
        int i = entityIn.getBrightnessForRender();

        if (entityIn.isBurning())
        {
            i = 15728880;
        }

        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.doRenderEntity(entityIn, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, partialTicks, p_188388_3_);
    }

    public void doRenderEntity(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_)
    {
        Render<Entity> render = null;

        try
        {
            render = this.<Entity>getEntityRenderObject(entityIn);

            if (render != null && this.renderEngine != null)
            {
                try
                {
                    render.setRenderOutlines(this.renderOutlines);

                    if (CustomEntityModels.isActive())
                    {
                        this.renderEntity = entityIn;
                        this.renderRender = render;
                    }

                    render.doRender(entityIn, x, y, z, yaw, partialTicks);
                }
                catch (Throwable throwable2)
                {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
                }

                try
                {
                    if (!this.renderOutlines)
                    {
                        render.doRenderShadowAndFire(entityIn, x, y, z, yaw, partialTicks);
                    }
                }
                catch (Throwable throwable1)
                {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
                }

                if (this.debugBoundingBox && !entityIn.isInvisible() && !p_188391_10_ && !Minecraft.getMinecraft().isReducedDebug())
                {
                    try
                    {
                        this.renderDebugBoundingBox(entityIn, x, y, z, yaw, partialTicks);
                    }
                    catch (Throwable throwable)
                    {
                        throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
                    }
                }
            }
        }
        catch (Throwable throwable3)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
            entityIn.addEntityCrashInfo(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
            crashreportcategory1.addCrashSection("Assigned renderer", render);
            crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
            crashreportcategory1.addCrashSection("Rotation", Float.valueOf(yaw));
            crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
            throw new ReportedException(crashreport);
        }
    }

    public void renderMultipass(Entity p_188389_1_, float p_188389_2_)
    {
        if (p_188389_1_.ticksExisted == 0)
        {
            p_188389_1_.lastTickPosX = p_188389_1_.posX;
            p_188389_1_.lastTickPosY = p_188389_1_.posY;
            p_188389_1_.lastTickPosZ = p_188389_1_.posZ;
        }

        double d0 = p_188389_1_.lastTickPosX + (p_188389_1_.posX - p_188389_1_.lastTickPosX) * (double)p_188389_2_;
        double d1 = p_188389_1_.lastTickPosY + (p_188389_1_.posY - p_188389_1_.lastTickPosY) * (double)p_188389_2_;
        double d2 = p_188389_1_.lastTickPosZ + (p_188389_1_.posZ - p_188389_1_.lastTickPosZ) * (double)p_188389_2_;
        float f = p_188389_1_.prevRotationYaw + (p_188389_1_.rotationYaw - p_188389_1_.prevRotationYaw) * p_188389_2_;
        int i = p_188389_1_.getBrightnessForRender();

        if (p_188389_1_.isBurning())
        {
            i = 15728880;
        }

        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Render<Entity> render = this.<Entity>getEntityRenderObject(p_188389_1_);

        if (render != null && this.renderEngine != null)
        {
            render.renderMultipass(p_188389_1_, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, p_188389_2_);
        }
    }

    /**
     * Renders the bounding box around an entity when F3+B is pressed
     */
    private void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float f = entityIn.width / 2.0F;
        AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
        RenderGlobal.drawBoundingBox(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z, 1.0F, 1.0F, 1.0F, 1.0F);
        Entity[] aentity = entityIn.getParts();

        if (aentity != null)
        {
            for (Entity entity : aentity)
            {
                double d0 = (entity.posX - entity.prevPosX) * (double)partialTicks;
                double d1 = (entity.posY - entity.prevPosY) * (double)partialTicks;
                double d2 = (entity.posZ - entity.prevPosZ) * (double)partialTicks;
                AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
                RenderGlobal.drawBoundingBox(axisalignedbb1.minX - this.renderPosX + d0, axisalignedbb1.minY - this.renderPosY + d1, axisalignedbb1.minZ - this.renderPosZ + d2, axisalignedbb1.maxX - this.renderPosX + d0, axisalignedbb1.maxY - this.renderPosY + d1, axisalignedbb1.maxZ - this.renderPosZ + d2, 0.25F, 1.0F, 0.0F, 1.0F);
            }
        }

        if (entityIn instanceof EntityLivingBase)
        {
            float f1 = 0.01F;
            RenderGlobal.drawBoundingBox(x - (double)f, y + (double)entityIn.getEyeHeight() - 0.009999999776482582D, z - (double)f, x + (double)f, y + (double)entityIn.getEyeHeight() + 0.009999999776482582D, z + (double)f, 1.0F, 0.0F, 0.0F, 1.0F);
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        Vec3d vec3d = entityIn.getLook(partialTicks);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y + (double)entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
        bufferbuilder.pos(x + vec3d.x * 2.0D, y + (double)entityIn.getEyeHeight() + vec3d.y * 2.0D, z + vec3d.z * 2.0D).color(0, 0, 255, 255).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }

    /**
     * World sets this RenderManager's worldObj to the world provided
     */
    public void setWorld(@Nullable World worldIn)
    {
        this.world = worldIn;

        if (worldIn == null)
        {
            this.renderViewEntity = null;
        }
    }

    public double getDistanceToCamera(double x, double y, double z)
    {
        double d0 = x - this.viewerPosX;
        double d1 = y - this.viewerPosY;
        double d2 = z - this.viewerPosZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * Returns the font renderer
     */
    public FontRenderer getFontRenderer()
    {
        return this.textRenderer;
    }

    public void setRenderOutlines(boolean renderOutlinesIn)
    {
        this.renderOutlines = renderOutlinesIn;
    }

    public Map<Class, Render> getEntityRenderMap()
    {
        return this.entityRenderMap;
    }

    public Map<String, RenderPlayer> getSkinMap()
    {
        return Collections.<String, RenderPlayer>unmodifiableMap(this.skinMap);
    }
}
