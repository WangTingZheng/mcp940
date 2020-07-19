package net.minecraft.client.particle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ParticleManager
{
    private static final ResourceLocation PARTICLE_TEXTURES = new ResourceLocation("textures/particle/particles.png");

    /** Reference to the World object. */
    protected World world;
    private final ArrayDeque<Particle>[][] fxLayers = new ArrayDeque[4][];
    private final Queue<ParticleEmitter> particleEmitters = Queues.<ParticleEmitter>newArrayDeque();
    private final TextureManager renderer;

    /** RNG. */
    private final Random rand = new Random();
    private final Map<Integer, IParticleFactory> particleTypes = Maps.<Integer, IParticleFactory>newHashMap();
    private final Queue<Particle> queueEntityFX = Queues.<Particle>newArrayDeque();

    public ParticleManager(World worldIn, TextureManager rendererIn)
    {
        this.world = worldIn;
        this.renderer = rendererIn;

        for (int i = 0; i < 4; ++i)
        {
            this.fxLayers[i] = new ArrayDeque[2];

            for (int j = 0; j < 2; ++j)
            {
                this.fxLayers[i][j] = Queues.newArrayDeque();
            }
        }

        this.registerVanillaParticles();
    }

    private void registerVanillaParticles()
    {
        this.registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new ParticleExplosion.Factory());
        this.registerParticle(EnumParticleTypes.SPIT.getParticleID(), new ParticleSpit.Factory());
        this.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new ParticleBubble.Factory());
        this.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new ParticleSplash.Factory());
        this.registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new ParticleWaterWake.Factory());
        this.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new ParticleRain.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new ParticleSuspend.Factory());
        this.registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new ParticleSuspendedTown.Factory());
        this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new ParticleCrit.Factory());
        this.registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new ParticleCrit.MagicFactory());
        this.registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new ParticleSmokeNormal.Factory());
        this.registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new ParticleSmokeLarge.Factory());
        this.registerParticle(EnumParticleTypes.SPELL.getParticleID(), new ParticleSpell.Factory());
        this.registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new ParticleSpell.InstantFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new ParticleSpell.MobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new ParticleSpell.AmbientMobFactory());
        this.registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new ParticleSpell.WitchFactory());
        this.registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new ParticleDrip.WaterFactory());
        this.registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new ParticleDrip.LavaFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new ParticleHeart.AngryVillagerFactory());
        this.registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new ParticleSuspendedTown.HappyVillagerFactory());
        this.registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new ParticleSuspendedTown.Factory());
        this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new ParticleNote.Factory());
        this.registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new ParticlePortal.Factory());
        this.registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new ParticleEnchantmentTable.EnchantmentTable());
        this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new ParticleFlame.Factory());
        this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new ParticleLava.Factory());
        this.registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new ParticleFootStep.Factory());
        this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new ParticleCloud.Factory());
        this.registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new ParticleRedstone.Factory());
        this.registerParticle(EnumParticleTypes.FALLING_DUST.getParticleID(), new ParticleFallingDust.Factory());
        this.registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new ParticleBreaking.SnowballFactory());
        this.registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new ParticleSnowShovel.Factory());
        this.registerParticle(EnumParticleTypes.SLIME.getParticleID(), new ParticleBreaking.SlimeFactory());
        this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new ParticleHeart.Factory());
        this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
        this.registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new ParticleBreaking.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new ParticleDigging.Factory());
        this.registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new ParticleBlockDust.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new ParticleExplosionHuge.Factory());
        this.registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new ParticleExplosionLarge.Factory());
        this.registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new ParticleFirework.Factory());
        this.registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new ParticleMobAppearance.Factory());
        this.registerParticle(EnumParticleTypes.DRAGON_BREATH.getParticleID(), new ParticleDragonBreath.Factory());
        this.registerParticle(EnumParticleTypes.END_ROD.getParticleID(), new ParticleEndRod.Factory());
        this.registerParticle(EnumParticleTypes.DAMAGE_INDICATOR.getParticleID(), new ParticleCrit.DamageIndicatorFactory());
        this.registerParticle(EnumParticleTypes.SWEEP_ATTACK.getParticleID(), new ParticleSweepAttack.Factory());
        this.registerParticle(EnumParticleTypes.TOTEM.getParticleID(), new ParticleTotem.Factory());
    }

    public void registerParticle(int id, IParticleFactory particleFactory)
    {
        this.particleTypes.put(Integer.valueOf(id), particleFactory);
    }

    public void emitParticleAtEntity(Entity entityIn, EnumParticleTypes particleTypes)
    {
        this.particleEmitters.add(new ParticleEmitter(this.world, entityIn, particleTypes));
    }

    public void emitParticleAtEntity(Entity p_191271_1_, EnumParticleTypes p_191271_2_, int p_191271_3_)
    {
        this.particleEmitters.add(new ParticleEmitter(this.world, p_191271_1_, p_191271_2_, p_191271_3_));
    }

    @Nullable

    /**
     * Spawns the relevant particle according to the particle id.
     */
    public Particle spawnEffectParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        IParticleFactory iparticlefactory = this.particleTypes.get(Integer.valueOf(particleId));

        if (iparticlefactory != null)
        {
            Particle particle = iparticlefactory.createParticle(particleId, this.world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);

            if (particle != null)
            {
                this.addEffect(particle);
                return particle;
            }
        }

        return null;
    }

    public void addEffect(Particle effect)
    {
        if (effect != null)
        {
            if (!(effect instanceof ParticleFirework.Spark) || Config.isFireworkParticles())
            {
                this.queueEntityFX.add(effect);
            }
        }
    }

    public void updateEffects()
    {
        for (int i = 0; i < 4; ++i)
        {
            this.updateEffectLayer(i);
        }

        if (!this.particleEmitters.isEmpty())
        {
            List<ParticleEmitter> list = Lists.<ParticleEmitter>newArrayList();

            for (ParticleEmitter particleemitter : this.particleEmitters)
            {
                particleemitter.onUpdate();

                if (!particleemitter.isAlive())
                {
                    list.add(particleemitter);
                }
            }

            this.particleEmitters.removeAll(list);
        }

        if (!this.queueEntityFX.isEmpty())
        {
            for (Particle particle = this.queueEntityFX.poll(); particle != null; particle = this.queueEntityFX.poll())
            {
                int j = particle.getFXLayer();
                int k = particle.shouldDisableDepth() ? 0 : 1;

                if (this.fxLayers[j][k].size() >= 16384)
                {
                    this.fxLayers[j][k].removeFirst();
                }

                if (!(particle instanceof Barrier) || !this.reuseBarrierParticle(particle, this.fxLayers[j][k]))
                {
                    this.fxLayers[j][k].add(particle);
                }
            }
        }
    }

    private void updateEffectLayer(int layer)
    {
        this.world.profiler.startSection(layer + "");

        for (int i = 0; i < 2; ++i)
        {
            this.world.profiler.startSection(i + "");
            this.tickParticleList(this.fxLayers[layer][i]);
            this.world.profiler.endSection();
        }

        this.world.profiler.endSection();
    }

    private void tickParticleList(Queue<Particle> p_187240_1_)
    {
        if (!p_187240_1_.isEmpty())
        {
            Iterator<Particle> iterator = p_187240_1_.iterator();

            while (iterator.hasNext())
            {
                Particle particle = iterator.next();
                this.tickParticle(particle);

                if (!particle.isAlive())
                {
                    iterator.remove();
                }
            }
        }
    }

    private void tickParticle(final Particle particle)
    {
        try
        {
            particle.onUpdate();
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
            final int i = particle.getFXLayer();
            crashreportcategory.addDetail("Particle", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    return particle.toString();
                }
            });
            crashreportcategory.addDetail("Particle Type", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    if (i == 0)
                    {
                        return "MISC_TEXTURE";
                    }
                    else if (i == 1)
                    {
                        return "TERRAIN_TEXTURE";
                    }
                    else
                    {
                        return i == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + i;
                    }
                }
            });
            throw new ReportedException(crashreport);
        }
    }

    /**
     * Renders all current particles. Args player, partialTickTime
     */
    public void renderParticles(Entity entityIn, float partialTicks)
    {
        float f = ActiveRenderInfo.getRotationX();
        float f1 = ActiveRenderInfo.getRotationZ();
        float f2 = ActiveRenderInfo.getRotationYZ();
        float f3 = ActiveRenderInfo.getRotationXY();
        float f4 = ActiveRenderInfo.getRotationXZ();
        Particle.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        Particle.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        Particle.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        Particle.cameraViewDir = entityIn.getLook(partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);

        for (int i = 0; i < 3; ++i)
        {
            final int j = i;

            for (int k = 0; k < 2; ++k)
            {
                if (!this.fxLayers[j][k].isEmpty())
                {
                    switch (k)
                    {
                        case 0:
                            GlStateManager.depthMask(false);
                            break;

                        case 1:
                            GlStateManager.depthMask(true);
                    }

                    switch (j)
                    {
                        case 0:
                        default:
                            this.renderer.bindTexture(PARTICLE_TEXTURES);
                            break;

                        case 1:
                            this.renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    }

                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

                    for (final Particle particle : this.fxLayers[j][k])
                    {
                        try
                        {
                            particle.renderParticle(bufferbuilder, entityIn, partialTicks, f, f4, f1, f2, f3);
                        }
                        catch (Throwable throwable)
                        {
                            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                            CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                            crashreportcategory.addDetail("Particle", new ICrashReportDetail<String>()
                            {
                                public String call() throws Exception
                                {
                                    return particle.toString();
                                }
                            });
                            crashreportcategory.addDetail("Particle Type", new ICrashReportDetail<String>()
                            {
                                public String call() throws Exception
                                {
                                    if (j == 0)
                                    {
                                        return "MISC_TEXTURE";
                                    }
                                    else if (j == 1)
                                    {
                                        return "TERRAIN_TEXTURE";
                                    }
                                    else
                                    {
                                        return j == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + j;
                                    }
                                }
                            });
                            throw new ReportedException(crashreport);
                        }
                    }

                    tessellator.draw();
                }
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
    }

    public void renderLitParticles(Entity entityIn, float partialTick)
    {
        float f = 0.017453292F;
        float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
        float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
        float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
        float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
        float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);

        for (int i = 0; i < 2; ++i)
        {
            Queue<Particle> queue = this.fxLayers[3][i];

            if (!queue.isEmpty())
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();

                for (Particle particle : queue)
                {
                    particle.renderParticle(bufferbuilder, entityIn, partialTick, f1, f5, f2, f3, f4);
                }
            }
        }
    }

    public void clearEffects(@Nullable World worldIn)
    {
        this.world = worldIn;

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 2; ++j)
            {
                this.fxLayers[i][j].clear();
            }
        }

        this.particleEmitters.clear();
    }

    public void addBlockDestroyEffects(BlockPos pos, IBlockState state)
    {
        boolean flag;

        if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists())
        {
            Block block = state.getBlock();
            flag = !Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, state, this.world, pos) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, this.world, pos, this);
        }
        else
        {
            flag = state.getMaterial() != Material.AIR;
        }

        if (flag)
        {
            state = state.getActualState(this.world, pos);
            int l = 4;

            for (int i = 0; i < 4; ++i)
            {
                for (int j = 0; j < 4; ++j)
                {
                    for (int k = 0; k < 4; ++k)
                    {
                        double d0 = ((double)i + 0.5D) / 4.0D;
                        double d1 = ((double)j + 0.5D) / 4.0D;
                        double d2 = ((double)k + 0.5D) / 4.0D;
                        this.addEffect((new ParticleDigging(this.world, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, state)).setBlockPos(pos));
                    }
                }
            }
        }
    }

    /**
     * Adds block hit particles for the specified block
     */
    public void addBlockHitEffects(BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = this.world.getBlockState(pos);

        if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE)
        {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            float f = 0.1F;
            AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(this.world, pos);
            double d0 = (double)i + this.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minX;
            double d1 = (double)j + this.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minY;
            double d2 = (double)k + this.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.20000000298023224D) + 0.10000000149011612D + axisalignedbb.minZ;

            if (side == EnumFacing.DOWN)
            {
                d1 = (double)j + axisalignedbb.minY - 0.10000000149011612D;
            }

            if (side == EnumFacing.UP)
            {
                d1 = (double)j + axisalignedbb.maxY + 0.10000000149011612D;
            }

            if (side == EnumFacing.NORTH)
            {
                d2 = (double)k + axisalignedbb.minZ - 0.10000000149011612D;
            }

            if (side == EnumFacing.SOUTH)
            {
                d2 = (double)k + axisalignedbb.maxZ + 0.10000000149011612D;
            }

            if (side == EnumFacing.WEST)
            {
                d0 = (double)i + axisalignedbb.minX - 0.10000000149011612D;
            }

            if (side == EnumFacing.EAST)
            {
                d0 = (double)i + axisalignedbb.maxX + 0.10000000149011612D;
            }

            this.addEffect((new ParticleDigging(this.world, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        }
    }

    public String getStatistics()
    {
        int i = 0;

        for (int j = 0; j < 4; ++j)
        {
            for (int k = 0; k < 2; ++k)
            {
                i += this.fxLayers[j][k].size();
            }
        }

        return "" + i;
    }

    private boolean reuseBarrierParticle(Particle p_reuseBarrierParticle_1_, ArrayDeque<Particle> p_reuseBarrierParticle_2_)
    {
        for (Particle particle : p_reuseBarrierParticle_2_)
        {
            if (particle instanceof Barrier && p_reuseBarrierParticle_1_.prevPosX == particle.prevPosX && p_reuseBarrierParticle_1_.prevPosY == particle.prevPosY && p_reuseBarrierParticle_1_.prevPosZ == particle.prevPosZ)
            {
                particle.particleAge = 0;
                return true;
            }
        }

        return false;
    }

    public void addBlockHitEffects(BlockPos p_addBlockHitEffects_1_, RayTraceResult p_addBlockHitEffects_2_)
    {
        IBlockState iblockstate = this.world.getBlockState(p_addBlockHitEffects_1_);

        if (iblockstate != null)
        {
            boolean flag = Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_addHitEffects, iblockstate, this.world, p_addBlockHitEffects_2_, this);

            if (iblockstate != null && !flag)
            {
                this.addBlockHitEffects(p_addBlockHitEffects_1_, p_addBlockHitEffects_2_.sideHit);
            }
        }
    }
}
