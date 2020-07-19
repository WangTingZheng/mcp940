package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILlamaFollowCaravan;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityLlama extends AbstractChestHorse implements IRangedAttackMob
{
    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.<Integer>createKey(EntityLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.<Integer>createKey(EntityLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.<Integer>createKey(EntityLlama.class, DataSerializers.VARINT);
    private boolean didSpit;
    @Nullable
    private EntityLlama caravanHead;
    @Nullable
    private EntityLlama caravanTail;

    public EntityLlama(World worldIn)
    {
        super(worldIn);
        this.setSize(0.9F, 1.87F);
    }

    private void setStrength(int strengthIn)
    {
        this.dataManager.set(DATA_STRENGTH_ID, Integer.valueOf(Math.max(1, Math.min(5, strengthIn))));
    }

    private void setRandomStrength()
    {
        int i = this.rand.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(1 + this.rand.nextInt(i));
    }

    public int getStrength()
    {
        return ((Integer)this.dataManager.get(DATA_STRENGTH_ID)).intValue();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
        compound.setInteger("Strength", this.getStrength());

        if (!this.horseChest.getStackInSlot(1).isEmpty())
        {
            compound.setTag("DecorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setStrength(compound.getInteger("Strength"));
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));

        if (compound.hasKey("DecorItem", 10))
        {
            this.horseChest.setInventorySlotContents(1, new ItemStack(compound.getCompoundTag("DecorItem")));
        }

        this.updateHorseSlots();
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.tasks.addTask(2, new EntityAILlamaFollowCaravan(this, 2.0999999046325684D));
        this.tasks.addTask(3, new EntityAIAttackRanged(this, 1.25D, 40, 20.0F));
        this.tasks.addTask(3, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityLlama.AIHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityLlama.AIDefendTarget(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DATA_STRENGTH_ID, Integer.valueOf(0));
        this.dataManager.register(DATA_COLOR_ID, Integer.valueOf(-1));
        this.dataManager.register(DATA_VARIANT_ID, Integer.valueOf(0));
    }

    public int getVariant()
    {
        return MathHelper.clamp(((Integer)this.dataManager.get(DATA_VARIANT_ID)).intValue(), 0, 3);
    }

    public void setVariant(int variantIn)
    {
        this.dataManager.set(DATA_VARIANT_ID, Integer.valueOf(variantIn));
    }

    protected int getInventorySize()
    {
        return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
    }

    public void updatePassenger(Entity passenger)
    {
        if (this.isPassenger(passenger))
        {
            float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            float f1 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
            float f2 = 0.3F;
            passenger.setPosition(this.posX + (double)(0.3F * f1), this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ - (double)(0.3F * f));
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.67D;
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered()
    {
        return false;
    }

    protected boolean handleEating(EntityPlayer player, ItemStack stack)
    {
        int i = 0;
        int j = 0;
        float f = 0.0F;
        boolean flag = false;
        Item item = stack.getItem();

        if (item == Items.WHEAT)
        {
            i = 10;
            j = 3;
            f = 2.0F;
        }
        else if (item == Item.getItemFromBlock(Blocks.HAY_BLOCK))
        {
            i = 90;
            j = 6;
            f = 10.0F;

            if (this.isTame() && this.getGrowingAge() == 0)
            {
                flag = true;
                this.setInLove(player);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F)
        {
            this.heal(f);
            flag = true;
        }

        if (this.isChild() && i > 0)
        {
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);

            if (!this.world.isRemote)
            {
                this.addGrowth(i);
            }

            flag = true;
        }

        if (j > 0 && (flag || !this.isTame()) && this.getTemper() < this.getMaxTemper())
        {
            flag = true;

            if (!this.world.isRemote)
            {
                this.increaseTemper(j);
            }
        }

        if (flag && !this.isSilent())
        {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }

        return flag;
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
    }

    @Nullable

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setRandomStrength();
        int i;

        if (livingdata instanceof EntityLlama.GroupData)
        {
            i = ((EntityLlama.GroupData)livingdata).variant;
        }
        else
        {
            i = this.rand.nextInt(4);
            livingdata = new EntityLlama.GroupData(i);
        }

        this.setVariant(i);
        return livingdata;
    }

    public boolean hasColor()
    {
        return this.getColor() != null;
    }

    protected SoundEvent getAngrySound()
    {
        return SoundEvents.ENTITY_LLAMA_ANGRY;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }

    protected void playChestEquipSound()
    {
        this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    public void makeMad()
    {
        SoundEvent soundevent = this.getAngrySound();

        if (soundevent != null)
        {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_LLAMA;
    }

    public int getInventoryColumns()
    {
        return this.getStrength();
    }

    public boolean wearsArmor()
    {
        return true;
    }

    public boolean isArmor(ItemStack stack)
    {
        return stack.getItem() == Item.getItemFromBlock(Blocks.CARPET);
    }

    public boolean canBeSaddled()
    {
        return false;
    }

    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    public void onInventoryChanged(IInventory invBasic)
    {
        EnumDyeColor enumdyecolor = this.getColor();
        super.onInventoryChanged(invBasic);
        EnumDyeColor enumdyecolor1 = this.getColor();

        if (this.ticksExisted > 20 && enumdyecolor1 != null && enumdyecolor1 != enumdyecolor)
        {
            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
        }
    }

    /**
     * Updates the items in the saddle and armor slots of the horse's inventory.
     */
    protected void updateHorseSlots()
    {
        if (!this.world.isRemote)
        {
            super.updateHorseSlots();
            this.setColorByItem(this.horseChest.getStackInSlot(1));
        }
    }

    private void setColor(@Nullable EnumDyeColor color)
    {
        this.dataManager.set(DATA_COLOR_ID, Integer.valueOf(color == null ? -1 : color.getMetadata()));
    }

    private void setColorByItem(ItemStack stack)
    {
        if (this.isArmor(stack))
        {
            this.setColor(EnumDyeColor.byMetadata(stack.getMetadata()));
        }
        else
        {
            this.setColor((EnumDyeColor)null);
        }
    }

    @Nullable
    public EnumDyeColor getColor()
    {
        int i = ((Integer)this.dataManager.get(DATA_COLOR_ID)).intValue();
        return i == -1 ? null : EnumDyeColor.byMetadata(i);
    }

    public int getMaxTemper()
    {
        return 30;
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        return otherAnimal != this && otherAnimal instanceof EntityLlama && this.canMate() && ((EntityLlama)otherAnimal).canMate();
    }

    public EntityLlama createChild(EntityAgeable ageable)
    {
        EntityLlama entityllama = new EntityLlama(this.world);
        this.setOffspringAttributes(ageable, entityllama);
        EntityLlama entityllama1 = (EntityLlama)ageable;
        int i = this.rand.nextInt(Math.max(this.getStrength(), entityllama1.getStrength())) + 1;

        if (this.rand.nextFloat() < 0.03F)
        {
            ++i;
        }

        entityllama.setStrength(i);
        entityllama.setVariant(this.rand.nextBoolean() ? this.getVariant() : entityllama1.getVariant());
        return entityllama;
    }

    private void spit(EntityLivingBase target)
    {
        EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityllamaspit.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        entityllamaspit.setThrowableHeading(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        this.world.spawnEntity(entityllamaspit);
        this.didSpit = true;
    }

    private void setDidSpit(boolean didSpitIn)
    {
        this.didSpit = didSpitIn;
    }

    public void fall(float distance, float damageMultiplier)
    {
        int i = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);

        if (i > 0)
        {
            if (distance >= 6.0F)
            {
                this.attackEntityFrom(DamageSource.FALL, (float)i);

                if (this.isBeingRidden())
                {
                    for (Entity entity : this.getRecursivePassengers())
                    {
                        entity.attackEntityFrom(DamageSource.FALL, (float)i);
                    }
                }
            }

            IBlockState iblockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent())
            {
                SoundType soundtype = block.getSoundType();
                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
        }
    }

    public void leaveCaravan()
    {
        if (this.caravanHead != null)
        {
            this.caravanHead.caravanTail = null;
        }

        this.caravanHead = null;
    }

    public void joinCaravan(EntityLlama caravanHeadIn)
    {
        this.caravanHead = caravanHeadIn;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTrail()
    {
        return this.caravanTail != null;
    }

    public boolean inCaravan()
    {
        return this.caravanHead != null;
    }

    @Nullable
    public EntityLlama getCaravanHead()
    {
        return this.caravanHead;
    }

    protected double followLeashSpeed()
    {
        return 2.0D;
    }

    protected void followMother()
    {
        if (!this.inCaravan() && this.isChild())
        {
            super.followMother();
        }
    }

    public boolean canEatGrass()
    {
        return false;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        this.spit(target);
    }

    public void setSwingingArms(boolean swingingArms)
    {
    }

    static class AIDefendTarget extends EntityAINearestAttackableTarget<EntityWolf>
    {
        public AIDefendTarget(EntityLlama llama)
        {
            super(llama, EntityWolf.class, 16, false, true, (Predicate)null);
        }

        public boolean shouldExecute()
        {
            if (super.shouldExecute() && this.targetEntity != null && !((EntityWolf)this.targetEntity).isTamed())
            {
                return true;
            }
            else
            {
                this.taskOwner.setAttackTarget((EntityLivingBase)null);
                return false;
            }
        }

        protected double getTargetDistance()
        {
            return super.getTargetDistance() * 0.25D;
        }
    }

    static class AIHurtByTarget extends EntityAIHurtByTarget
    {
        public AIHurtByTarget(EntityLlama llama)
        {
            super(llama, false);
        }

        public boolean shouldContinueExecuting()
        {
            if (this.taskOwner instanceof EntityLlama)
            {
                EntityLlama entityllama = (EntityLlama)this.taskOwner;

                if (entityllama.didSpit)
                {
                    entityllama.setDidSpit(false);
                    return false;
                }
            }

            return super.shouldContinueExecuting();
        }
    }

    static class GroupData implements IEntityLivingData
    {
        public int variant;

        private GroupData(int variantIn)
        {
            this.variant = variantIn;
        }
    }
}
