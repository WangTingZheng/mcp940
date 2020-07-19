package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDragonFireball extends EntityFireball
{
    public EntityDragonFireball(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    public EntityDragonFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(1.0F, 1.0F);
    }

    public EntityDragonFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(1.0F, 1.0F);
    }

    public static void registerFixesDragonFireball(DataFixer fixer)
    {
        EntityFireball.registerFixesFireball(fixer, "DragonFireball");
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit == null || !result.entityHit.isEntityEqual(this.shootingEntity))
        {
            if (!this.world.isRemote)
            {
                List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D));
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setOwner(this.shootingEntity);
                entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
                entityareaeffectcloud.setRadius(3.0F);
                entityareaeffectcloud.setDuration(600);
                entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
                entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));

                if (!list.isEmpty())
                {
                    for (EntityLivingBase entitylivingbase : list)
                    {
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
                            break;
                        }
                    }
                }

                this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
                this.world.spawnEntity(entityareaeffectcloud);
                this.setDead();
            }
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    protected EnumParticleTypes getParticleType()
    {
        return EnumParticleTypes.DRAGON_BREATH;
    }

    protected boolean isFireballFiery()
    {
        return false;
    }
}
