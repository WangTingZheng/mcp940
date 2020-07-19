package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity
{
    private static final DataParameter<ItemStack> FIREWORK_ITEM = EntityDataManager.<ItemStack>createKey(EntityFireworkRocket.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> BOOSTED_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityFireworkRocket.class, DataSerializers.VARINT);

    /** The age of the firework in ticks. */
    private int fireworkAge;

    /**
     * The lifetime of the firework in ticks. When the age reaches the lifetime the firework explodes.
     */
    private int lifetime;
    private EntityLivingBase boostedEntity;

    public EntityFireworkRocket(World worldIn)
    {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit()
    {
        this.dataManager.register(FIREWORK_ITEM, ItemStack.EMPTY);
        this.dataManager.register(BOOSTED_ENTITY_ID, Integer.valueOf(0));
    }

    /**
     * Checks if the entity is in range to render.
     */
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 4096.0D && !this.isAttachedToEntity();
    }

    public boolean isInRangeToRender3d(double x, double y, double z)
    {
        return super.isInRangeToRender3d(x, y, z) && !this.isAttachedToEntity();
    }

    public EntityFireworkRocket(World worldIn, double x, double y, double z, ItemStack givenItem)
    {
        super(worldIn);
        this.fireworkAge = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(x, y, z);
        int i = 1;

        if (!givenItem.isEmpty() && givenItem.hasTagCompound())
        {
            this.dataManager.set(FIREWORK_ITEM, givenItem.copy());
            NBTTagCompound nbttagcompound = givenItem.getTagCompound();
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Fireworks");
            i += nbttagcompound1.getByte("Flight");
        }

        this.motionX = this.rand.nextGaussian() * 0.001D;
        this.motionZ = this.rand.nextGaussian() * 0.001D;
        this.motionY = 0.05D;
        this.lifetime = 10 * i + this.rand.nextInt(6) + this.rand.nextInt(7);
    }

    public EntityFireworkRocket(World p_i47367_1_, ItemStack p_i47367_2_, EntityLivingBase p_i47367_3_)
    {
        this(p_i47367_1_, p_i47367_3_.posX, p_i47367_3_.posY, p_i47367_3_.posZ, p_i47367_2_);
        this.dataManager.set(BOOSTED_ENTITY_ID, Integer.valueOf(p_i47367_3_.getEntityId()));
        this.boostedEntity = p_i47367_3_;
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    public void setVelocity(double x, double y, double z)
    {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.isAttachedToEntity())
        {
            if (this.boostedEntity == null)
            {
                Entity entity = this.world.getEntityByID(((Integer)this.dataManager.get(BOOSTED_ENTITY_ID)).intValue());

                if (entity instanceof EntityLivingBase)
                {
                    this.boostedEntity = (EntityLivingBase)entity;
                }
            }

            if (this.boostedEntity != null)
            {
                if (this.boostedEntity.isElytraFlying())
                {
                    Vec3d vec3d = this.boostedEntity.getLookVec();
                    double d0 = 1.5D;
                    double d1 = 0.1D;
                    this.boostedEntity.motionX += vec3d.x * 0.1D + (vec3d.x * 1.5D - this.boostedEntity.motionX) * 0.5D;
                    this.boostedEntity.motionY += vec3d.y * 0.1D + (vec3d.y * 1.5D - this.boostedEntity.motionY) * 0.5D;
                    this.boostedEntity.motionZ += vec3d.z * 0.1D + (vec3d.z * 1.5D - this.boostedEntity.motionZ) * 0.5D;
                }

                this.setPosition(this.boostedEntity.posX, this.boostedEntity.posY, this.boostedEntity.posZ);
                this.motionX = this.boostedEntity.motionX;
                this.motionY = this.boostedEntity.motionY;
                this.motionZ = this.boostedEntity.motionZ;
            }
        }
        else
        {
            this.motionX *= 1.15D;
            this.motionZ *= 1.15D;
            this.motionY += 0.04D;
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

        for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

        if (this.fireworkAge == 0 && !this.isSilent())
        {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.AMBIENT, 3.0F, 1.0F);
        }

        ++this.fireworkAge;

        if (this.world.isRemote && this.fireworkAge % 2 < 2)
        {
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
        }

        if (!this.world.isRemote && this.fireworkAge > this.lifetime)
        {
            this.world.setEntityState(this, (byte)17);
            this.dealExplosionDamage();
            this.setDead();
        }
    }

    private void dealExplosionDamage()
    {
        float f = 0.0F;
        ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);
        NBTTagCompound nbttagcompound = itemstack.isEmpty() ? null : itemstack.getSubCompound("Fireworks");
        NBTTagList nbttaglist = nbttagcompound != null ? nbttagcompound.getTagList("Explosions", 10) : null;

        if (nbttaglist != null && !nbttaglist.hasNoTags())
        {
            f = (float)(5 + nbttaglist.tagCount() * 2);
        }

        if (f > 0.0F)
        {
            if (this.boostedEntity != null)
            {
                this.boostedEntity.attackEntityFrom(DamageSource.FIREWORKS, (float)(5 + nbttaglist.tagCount() * 2));
            }

            double d0 = 5.0D;
            Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);

            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(5.0D)))
            {
                if (entitylivingbase != this.boostedEntity && this.getDistanceSqToEntity(entitylivingbase) <= 25.0D)
                {
                    boolean flag = false;

                    for (int i = 0; i < 2; ++i)
                    {
                        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, new Vec3d(entitylivingbase.posX, entitylivingbase.posY + (double)entitylivingbase.height * 0.5D * (double)i, entitylivingbase.posZ), false, true, false);

                        if (raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS)
                        {
                            flag = true;
                            break;
                        }
                    }

                    if (flag)
                    {
                        float f1 = f * (float)Math.sqrt((5.0D - (double)this.getDistanceToEntity(entitylivingbase)) / 5.0D);
                        entitylivingbase.attackEntityFrom(DamageSource.FIREWORKS, f1);
                    }
                }
            }
        }
    }

    public boolean isAttachedToEntity()
    {
        return ((Integer)this.dataManager.get(BOOSTED_ENTITY_ID)).intValue() > 0;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    public void handleStatusUpdate(byte id)
    {
        if (id == 17 && this.world.isRemote)
        {
            ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);
            NBTTagCompound nbttagcompound = itemstack.isEmpty() ? null : itemstack.getSubCompound("Fireworks");
            this.world.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, nbttagcompound);
        }

        super.handleStatusUpdate(id);
    }

    public static void registerFixesFireworkRocket(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.ENTITY, new ItemStackData(EntityFireworkRocket.class, new String[] {"FireworksItem"}));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Life", this.fireworkAge);
        compound.setInteger("LifeTime", this.lifetime);
        ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);

        if (!itemstack.isEmpty())
        {
            compound.setTag("FireworksItem", itemstack.writeToNBT(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.fireworkAge = compound.getInteger("Life");
        this.lifetime = compound.getInteger("LifeTime");
        NBTTagCompound nbttagcompound = compound.getCompoundTag("FireworksItem");

        if (nbttagcompound != null)
        {
            ItemStack itemstack = new ItemStack(nbttagcompound);

            if (!itemstack.isEmpty())
            {
                this.dataManager.set(FIREWORK_ITEM, itemstack);
            }
        }
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem()
    {
        return false;
    }
}
