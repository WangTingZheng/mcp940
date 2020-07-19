package net.minecraft.entity.player;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

@SuppressWarnings("incomplete-switch")
public abstract class EntityPlayer extends EntityLivingBase
{
    private static final DataParameter<Float> ABSORPTION = EntityDataManager.<Float>createKey(EntityPlayer.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> PLAYER_SCORE = EntityDataManager.<Integer>createKey(EntityPlayer.class, DataSerializers.VARINT);
    protected static final DataParameter<Byte> PLAYER_MODEL_FLAG = EntityDataManager.<Byte>createKey(EntityPlayer.class, DataSerializers.BYTE);
    protected static final DataParameter<Byte> MAIN_HAND = EntityDataManager.<Byte>createKey(EntityPlayer.class, DataSerializers.BYTE);
    protected static final DataParameter<NBTTagCompound> LEFT_SHOULDER_ENTITY = EntityDataManager.<NBTTagCompound>createKey(EntityPlayer.class, DataSerializers.COMPOUND_TAG);
    protected static final DataParameter<NBTTagCompound> RIGHT_SHOULDER_ENTITY = EntityDataManager.<NBTTagCompound>createKey(EntityPlayer.class, DataSerializers.COMPOUND_TAG);

    /** Inventory of the player */
    public InventoryPlayer inventory = new InventoryPlayer(this);
    protected InventoryEnderChest enderChest = new InventoryEnderChest();

    /**
     * The Container for the player's inventory (which opens when they press E)
     */
    public Container inventoryContainer;

    /** The Container the player has open. */
    public Container openContainer;

    /** The food object of the player, the general hunger logic. */
    protected FoodStats foodStats = new FoodStats();

    /**
     * Used to tell if the player pressed jump twice. If this is at 0 and it's pressed (And they are allowed to fly, as
     * defined in the player's movementInput) it sets this to 7. If it's pressed and it's greater than 0 enable fly.
     */
    protected int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;

    /**
     * Used by EntityPlayer to prevent too many xp orbs from getting absorbed at once.
     */
    public int xpCooldown;
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;

    /** Boolean value indicating weather a player is sleeping or not */
    protected boolean sleeping;

    /**
     * The location of the bed the player is sleeping in, or {@code null} if they are not sleeping
     */
    public BlockPos bedLocation;
    private int sleepTimer;
    public float renderOffsetX;
    public float renderOffsetY;
    public float renderOffsetZ;

    /** holds the spawn chunk of the player */
    private BlockPos spawnChunk;

    /**
     * Whether this player's spawn point is forced, preventing execution of bed checks.
     */
    private boolean spawnForced;

    /** The player's capabilities. (See class PlayerCapabilities) */
    public PlayerCapabilities capabilities = new PlayerCapabilities();

    /** The current experience level the player is on. */
    public int experienceLevel;

    /**
     * The total amount of experience the player has. This also includes the amount of experience within their
     * Experience Bar.
     */
    public int experienceTotal;

    /**
     * The current amount of experience the player has within their Experience Bar.
     */
    public float experience;
    protected int xpSeed;
    protected float speedInAir = 0.02F;
    private int lastXPSound;

    /** The player's unique game profile */
    private final GameProfile gameProfile;
    private boolean hasReducedDebug;
    private ItemStack itemStackMainHand = ItemStack.EMPTY;
    private final CooldownTracker cooldownTracker = this.createCooldownTracker();
    @Nullable

    /**
     * An instance of a fishing rod's hook. If this isn't null, the icon image of the fishing rod is slightly different
     */
    public EntityFishHook fishEntity;

    protected CooldownTracker createCooldownTracker()
    {
        return new CooldownTracker();
    }

    public EntityPlayer(World worldIn, GameProfile gameProfileIn)
    {
        super(worldIn);
        this.setUniqueId(getUUID(gameProfileIn));
        this.gameProfile = gameProfileIn;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
        this.openContainer = this.inventoryContainer;
        BlockPos blockpos = worldIn.getSpawnPoint();
        this.setLocationAndAngles((double)blockpos.getX() + 0.5D, (double)(blockpos.getY() + 1), (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        this.unused180 = 180.0F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ABSORPTION, Float.valueOf(0.0F));
        this.dataManager.register(PLAYER_SCORE, Integer.valueOf(0));
        this.dataManager.register(PLAYER_MODEL_FLAG, Byte.valueOf((byte)0));
        this.dataManager.register(MAIN_HAND, Byte.valueOf((byte)1));
        this.dataManager.register(LEFT_SHOULDER_ENTITY, new NBTTagCompound());
        this.dataManager.register(RIGHT_SHOULDER_ENTITY, new NBTTagCompound());
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.noClip = this.isSpectator();

        if (this.isSpectator())
        {
            this.onGround = false;
        }

        if (this.xpCooldown > 0)
        {
            --this.xpCooldown;
        }

        if (this.isPlayerSleeping())
        {
            ++this.sleepTimer;

            if (this.sleepTimer > 100)
            {
                this.sleepTimer = 100;
            }

            if (!this.world.isRemote)
            {
                if (!this.isInBed())
                {
                    this.wakeUpPlayer(true, true, false);
                }
                else if (this.world.isDaytime())
                {
                    this.wakeUpPlayer(false, true, true);
                }
            }
        }
        else if (this.sleepTimer > 0)
        {
            ++this.sleepTimer;

            if (this.sleepTimer >= 110)
            {
                this.sleepTimer = 0;
            }
        }

        super.onUpdate();

        if (!this.world.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this))
        {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }

        if (this.isBurning() && this.capabilities.disableDamage)
        {
            this.extinguish();
        }

        this.updateCape();

        if (!this.world.isRemote)
        {
            this.foodStats.onUpdate(this);
            this.addStat(StatList.PLAY_ONE_MINUTE);

            if (this.isEntityAlive())
            {
                this.addStat(StatList.TIME_SINCE_DEATH);
            }

            if (this.isSneaking())
            {
                this.addStat(StatList.SNEAK_TIME);
            }
        }

        int i = 29999999;
        double d0 = MathHelper.clamp(this.posX, -2.9999999E7D, 2.9999999E7D);
        double d1 = MathHelper.clamp(this.posZ, -2.9999999E7D, 2.9999999E7D);

        if (d0 != this.posX || d1 != this.posZ)
        {
            this.setPosition(d0, this.posY, d1);
        }

        ++this.ticksSinceLastSwing;
        ItemStack itemstack = this.getHeldItemMainhand();

        if (!ItemStack.areItemStacksEqual(this.itemStackMainHand, itemstack))
        {
            if (!ItemStack.areItemsEqualIgnoreDurability(this.itemStackMainHand, itemstack))
            {
                this.resetCooldown();
            }

            this.itemStackMainHand = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
        }

        this.cooldownTracker.tick();
        this.updateSize();
    }

    private void updateCape()
    {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double d0 = this.posX - this.chasingPosX;
        double d1 = this.posY - this.chasingPosY;
        double d2 = this.posZ - this.chasingPosZ;
        double d3 = 10.0D;

        if (d0 > 10.0D)
        {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }

        if (d2 > 10.0D)
        {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if (d1 > 10.0D)
        {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }

        if (d0 < -10.0D)
        {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }

        if (d2 < -10.0D)
        {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }

        if (d1 < -10.0D)
        {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }

        this.chasingPosX += d0 * 0.25D;
        this.chasingPosZ += d2 * 0.25D;
        this.chasingPosY += d1 * 0.25D;
    }

    protected void updateSize()
    {
        float f;
        float f1;

        if (this.isElytraFlying())
        {
            f = 0.6F;
            f1 = 0.6F;
        }
        else if (this.isPlayerSleeping())
        {
            f = 0.2F;
            f1 = 0.2F;
        }
        else if (this.isSneaking())
        {
            f = 0.6F;
            f1 = 1.65F;
        }
        else
        {
            f = 0.6F;
            f1 = 1.8F;
        }

        if (f != this.width || f1 != this.height)
        {
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
            axisalignedbb = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)f, axisalignedbb.minY + (double)f1, axisalignedbb.minZ + (double)f);

            if (!this.world.collidesWithAnyBlock(axisalignedbb))
            {
                this.setSize(f, f1);
            }
        }
    }

    /**
     * Return the amount of time this entity should stay in a portal before being transported.
     */
    public int getMaxInPortalTime()
    {
        return this.capabilities.disableDamage ? 1 : 80;
    }

    protected SoundEvent getSwimSound()
    {
        return SoundEvents.ENTITY_PLAYER_SWIM;
    }

    protected SoundEvent getSplashSound()
    {
        return SoundEvents.ENTITY_PLAYER_SPLASH;
    }

    /**
     * Return the amount of cooldown before this entity can use a portal again.
     */
    public int getPortalCooldown()
    {
        return 10;
    }

    public void playSound(SoundEvent soundIn, float volume, float pitch)
    {
        this.world.playSound(this, this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch);
    }

    public SoundCategory getSoundCategory()
    {
        return SoundCategory.PLAYERS;
    }

    protected int getFireImmuneTicks()
    {
        return 20;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    public void handleStatusUpdate(byte id)
    {
        if (id == 9)
        {
            this.onItemUseFinish();
        }
        else if (id == 23)
        {
            this.hasReducedDebug = false;
        }
        else if (id == 22)
        {
            this.hasReducedDebug = true;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return this.getHealth() <= 0.0F || this.isPlayerSleeping();
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    protected void closeScreen()
    {
        this.openContainer = this.inventoryContainer;
    }

    /**
     * Handles updating while riding another entity
     */
    public void updateRidden()
    {
        if (!this.world.isRemote && this.isSneaking() && this.isRiding())
        {
            this.dismountRidingEntity();
            this.setSneaking(false);
        }
        else
        {
            double d0 = this.posX;
            double d1 = this.posY;
            double d2 = this.posZ;
            float f = this.rotationYaw;
            float f1 = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0F;
            this.addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);

            if (this.getRidingEntity() instanceof EntityPig)
            {
                this.rotationPitch = f1;
                this.rotationYaw = f;
                this.renderYawOffset = ((EntityPig)this.getRidingEntity()).renderYawOffset;
            }
        }
    }

    /**
     * Keeps moving the entity up so it isn't colliding with blocks and other requirements for this entity to be spawned
     * (only actually used on players though its also on Entity)
     */
    public void preparePlayerToSpawn()
    {
        this.setSize(0.6F, 1.8F);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }

    protected void updateEntityActionState()
    {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
        this.rotationYawHead = this.rotationYaw;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.flyToggleTimer > 0)
        {
            --this.flyToggleTimer;
        }

        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.world.getGameRules().getBoolean("naturalRegeneration"))
        {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0)
            {
                this.heal(1.0F);
            }

            if (this.foodStats.needFood() && this.ticksExisted % 10 == 0)
            {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
            }
        }

        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (!this.world.isRemote)
        {
            iattributeinstance.setBaseValue((double)this.capabilities.getWalkSpeed());
        }

        this.jumpMovementFactor = this.speedInAir;

        if (this.isSprinting())
        {
            this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + (double)this.speedInAir * 0.3D);
        }

        this.setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);

        if (f > 0.1F)
        {
            f = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0.0F)
        {
            f = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F)
        {
            f1 = 0.0F;
        }

        this.cameraYaw += (f - this.cameraYaw) * 0.4F;
        this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;

        if (this.getHealth() > 0.0F && !this.isSpectator())
        {
            AxisAlignedBB axisalignedbb;

            if (this.isRiding() && !this.getRidingEntity().isDead)
            {
                axisalignedbb = this.getEntityBoundingBox().union(this.getRidingEntity().getEntityBoundingBox()).grow(1.0D, 0.0D, 1.0D);
            }
            else
            {
                axisalignedbb = this.getEntityBoundingBox().grow(1.0D, 0.5D, 1.0D);
            }

            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = list.get(i);

                if (!entity.isDead)
                {
                    this.collideWithPlayer(entity);
                }
            }
        }

        this.playShoulderEntityAmbientSound(this.getLeftShoulderEntity());
        this.playShoulderEntityAmbientSound(this.getRightShoulderEntity());

        if (!this.world.isRemote && (this.fallDistance > 0.5F || this.isInWater() || this.isRiding()) || this.capabilities.isFlying)
        {
            this.spawnShoulderEntities();
        }
    }

    private void playShoulderEntityAmbientSound(@Nullable NBTTagCompound p_192028_1_)
    {
        if (p_192028_1_ != null && !p_192028_1_.hasKey("Silent") || !p_192028_1_.getBoolean("Silent"))
        {
            String s = p_192028_1_.getString("id");

            if (s.equals(EntityList.getKey(EntityParrot.class).toString()))
            {
                EntityParrot.playAmbientSound(this.world, this);
            }
        }
    }

    private void collideWithPlayer(Entity entityIn)
    {
        entityIn.onCollideWithPlayer(this);
    }

    public int getScore()
    {
        return ((Integer)this.dataManager.get(PLAYER_SCORE)).intValue();
    }

    /**
     * Set player's score
     */
    public void setScore(int scoreIn)
    {
        this.dataManager.set(PLAYER_SCORE, Integer.valueOf(scoreIn));
    }

    /**
     * Add to player's score
     */
    public void addScore(int scoreIn)
    {
        int i = this.getScore();
        this.dataManager.set(PLAYER_SCORE, Integer.valueOf(i + scoreIn));
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        this.setSize(0.2F, 0.2F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612D;

        if ("Notch".equals(this.getName()))
        {
            this.dropItem(new ItemStack(Items.APPLE, 1), true, false);
        }

        if (!this.world.getGameRules().getBoolean("keepInventory") && !this.isSpectator())
        {
            this.destroyVanishingCursedItems();
            this.inventory.dropAllItems();
        }

        if (cause != null)
        {
            this.motionX = (double)(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F);
            this.motionZ = (double)(-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F);
        }
        else
        {
            this.motionX = 0.0D;
            this.motionZ = 0.0D;
        }

        this.addStat(StatList.DEATHS);
        this.takeStat(StatList.TIME_SINCE_DEATH);
        this.extinguish();
        this.setFlag(0, false);
    }

    protected void destroyVanishingCursedItems()
    {
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.inventory.getStackInSlot(i);

            if (!itemstack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemstack))
            {
                this.inventory.removeStackFromSlot(i);
            }
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        if (damageSourceIn == DamageSource.ON_FIRE)
        {
            return SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE;
        }
        else
        {
            return damageSourceIn == DamageSource.DROWN ? SoundEvents.ENTITY_PLAYER_HURT_DROWN : SoundEvents.ENTITY_PLAYER_HURT;
        }
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    @Nullable

    /**
     * Drop one item out of the currently selected stack if {@code dropAll} is false. If {@code dropItem} is true the
     * entire stack is dropped.
     */
    public EntityItem dropItem(boolean dropAll)
    {
        return this.dropItem(this.inventory.decrStackSize(this.inventory.currentItem, dropAll && !this.inventory.getCurrentItem().isEmpty() ? this.inventory.getCurrentItem().getCount() : 1), false, true);
    }

    @Nullable

    /**
     * Drops an item into the world.
     */
    public EntityItem dropItem(ItemStack itemStackIn, boolean unused)
    {
        return this.dropItem(itemStackIn, false, unused);
    }

    @Nullable
    public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem)
    {
        if (droppedItem.isEmpty())
        {
            return null;
        }
        else
        {
            double d0 = this.posY - 0.30000001192092896D + (double)this.getEyeHeight();
            EntityItem entityitem = new EntityItem(this.world, this.posX, d0, this.posZ, droppedItem);
            entityitem.setPickupDelay(40);

            if (traceItem)
            {
                entityitem.setThrower(this.getName());
            }

            if (dropAround)
            {
                float f = this.rand.nextFloat() * 0.5F;
                float f1 = this.rand.nextFloat() * ((float)Math.PI * 2F);
                entityitem.motionX = (double)(-MathHelper.sin(f1) * f);
                entityitem.motionZ = (double)(MathHelper.cos(f1) * f);
                entityitem.motionY = 0.20000000298023224D;
            }
            else
            {
                float f2 = 0.3F;
                entityitem.motionX = (double)(-MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2);
                entityitem.motionZ = (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2);
                entityitem.motionY = (double)(-MathHelper.sin(this.rotationPitch * 0.017453292F) * f2 + 0.1F);
                float f3 = this.rand.nextFloat() * ((float)Math.PI * 2F);
                f2 = 0.02F * this.rand.nextFloat();
                entityitem.motionX += Math.cos((double)f3) * (double)f2;
                entityitem.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                entityitem.motionZ += Math.sin((double)f3) * (double)f2;
            }

            ItemStack itemstack = this.dropItemAndGetStack(entityitem);

            if (traceItem)
            {
                if (!itemstack.isEmpty())
                {
                    this.addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.getCount());
                }

                this.addStat(StatList.DROP);
            }

            return entityitem;
        }
    }

    protected ItemStack dropItemAndGetStack(EntityItem p_184816_1_)
    {
        this.world.spawnEntity(p_184816_1_);
        return p_184816_1_.getItem();
    }

    public float getDigSpeed(IBlockState state)
    {
        float f = this.inventory.getStrVsBlock(state);

        if (f > 1.0F)
        {
            int i = EnchantmentHelper.getEfficiencyModifier(this);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (i > 0 && !itemstack.isEmpty())
            {
                f += (float)(i * i + 1);
            }
        }

        if (this.isPotionActive(MobEffects.HASTE))
        {
            f *= 1.0F + (float)(this.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2F;
        }

        if (this.isPotionActive(MobEffects.MINING_FATIGUE))
        {
            float f1;

            switch (this.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier())
            {
                case 0:
                    f1 = 0.3F;
                    break;

                case 1:
                    f1 = 0.09F;
                    break;

                case 2:
                    f1 = 0.0027F;
                    break;

                case 3:
                default:
                    f1 = 8.1E-4F;
            }

            f *= f1;
        }

        if (this.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(this))
        {
            f /= 5.0F;
        }

        if (!this.onGround)
        {
            f /= 5.0F;
        }

        return f;
    }

    public boolean canHarvestBlock(IBlockState state)
    {
        return this.inventory.canHarvestBlock(state);
    }

    public static void registerFixesPlayer(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.PLAYER, new IDataWalker()
        {
            public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
            {
                DataFixesManager.processInventory(fixer, compound, versionIn, "Inventory");
                DataFixesManager.processInventory(fixer, compound, versionIn, "EnderItems");

                if (compound.hasKey("ShoulderEntityLeft", 10))
                {
                    compound.setTag("ShoulderEntityLeft", fixer.process(FixTypes.ENTITY, compound.getCompoundTag("ShoulderEntityLeft"), versionIn));
                }

                if (compound.hasKey("ShoulderEntityRight", 10))
                {
                    compound.setTag("ShoulderEntityRight", fixer.process(FixTypes.ENTITY, compound.getCompoundTag("ShoulderEntityRight"), versionIn));
                }

                return compound;
            }
        });
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setUniqueId(getUUID(this.gameProfile));
        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
        this.inventory.readFromNBT(nbttaglist);
        this.inventory.currentItem = compound.getInteger("SelectedItemSlot");
        this.sleeping = compound.getBoolean("Sleeping");
        this.sleepTimer = compound.getShort("SleepTimer");
        this.experience = compound.getFloat("XpP");
        this.experienceLevel = compound.getInteger("XpLevel");
        this.experienceTotal = compound.getInteger("XpTotal");
        this.xpSeed = compound.getInteger("XpSeed");

        if (this.xpSeed == 0)
        {
            this.xpSeed = this.rand.nextInt();
        }

        this.setScore(compound.getInteger("Score"));

        if (this.sleeping)
        {
            this.bedLocation = new BlockPos(this);
            this.wakeUpPlayer(true, true, false);
        }

        if (compound.hasKey("SpawnX", 99) && compound.hasKey("SpawnY", 99) && compound.hasKey("SpawnZ", 99))
        {
            this.spawnChunk = new BlockPos(compound.getInteger("SpawnX"), compound.getInteger("SpawnY"), compound.getInteger("SpawnZ"));
            this.spawnForced = compound.getBoolean("SpawnForced");
        }

        this.foodStats.readNBT(compound);
        this.capabilities.readCapabilitiesFromNBT(compound);

        if (compound.hasKey("EnderItems", 9))
        {
            NBTTagList nbttaglist1 = compound.getTagList("EnderItems", 10);
            this.enderChest.loadInventoryFromNBT(nbttaglist1);
        }

        if (compound.hasKey("ShoulderEntityLeft", 10))
        {
            this.setLeftShoulderEntity(compound.getCompoundTag("ShoulderEntityLeft"));
        }

        if (compound.hasKey("ShoulderEntityRight", 10))
        {
            this.setRightShoulderEntity(compound.getCompoundTag("ShoulderEntityRight"));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("DataVersion", 1139);
        compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        compound.setBoolean("Sleeping", this.sleeping);
        compound.setShort("SleepTimer", (short)this.sleepTimer);
        compound.setFloat("XpP", this.experience);
        compound.setInteger("XpLevel", this.experienceLevel);
        compound.setInteger("XpTotal", this.experienceTotal);
        compound.setInteger("XpSeed", this.xpSeed);
        compound.setInteger("Score", this.getScore());

        if (this.spawnChunk != null)
        {
            compound.setInteger("SpawnX", this.spawnChunk.getX());
            compound.setInteger("SpawnY", this.spawnChunk.getY());
            compound.setInteger("SpawnZ", this.spawnChunk.getZ());
            compound.setBoolean("SpawnForced", this.spawnForced);
        }

        this.foodStats.writeNBT(compound);
        this.capabilities.writeCapabilitiesToNBT(compound);
        compound.setTag("EnderItems", this.enderChest.saveInventoryToNBT());

        if (!this.getLeftShoulderEntity().hasNoTags())
        {
            compound.setTag("ShoulderEntityLeft", this.getLeftShoulderEntity());
        }

        if (!this.getRightShoulderEntity().hasNoTags())
        {
            compound.setTag("ShoulderEntityRight", this.getRightShoulderEntity());
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (this.capabilities.disableDamage && !source.canHarmInCreative())
        {
            return false;
        }
        else
        {
            this.idleTime = 0;

            if (this.getHealth() <= 0.0F)
            {
                return false;
            }
            else
            {
                if (this.isPlayerSleeping() && !this.world.isRemote)
                {
                    this.wakeUpPlayer(true, true, false);
                }

                this.spawnShoulderEntities();

                if (source.isDifficultyScaled())
                {
                    if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
                    {
                        amount = 0.0F;
                    }

                    if (this.world.getDifficulty() == EnumDifficulty.EASY)
                    {
                        amount = Math.min(amount / 2.0F + 1.0F, amount);
                    }

                    if (this.world.getDifficulty() == EnumDifficulty.HARD)
                    {
                        amount = amount * 3.0F / 2.0F;
                    }
                }

                return amount == 0.0F ? false : super.attackEntityFrom(source, amount);
            }
        }
    }

    protected void blockUsingShield(EntityLivingBase p_190629_1_)
    {
        super.blockUsingShield(p_190629_1_);

        if (p_190629_1_.getHeldItemMainhand().getItem() instanceof ItemAxe)
        {
            this.disableShield(true);
        }
    }

    public boolean canAttackPlayer(EntityPlayer other)
    {
        Team team = this.getTeam();
        Team team1 = other.getTeam();

        if (team == null)
        {
            return true;
        }
        else
        {
            return !team.isSameTeam(team1) ? true : team.getAllowFriendlyFire();
        }
    }

    protected void damageArmor(float damage)
    {
        this.inventory.damageArmor(damage);
    }

    protected void damageShield(float damage)
    {
        if (damage >= 3.0F && this.activeItemStack.getItem() == Items.SHIELD)
        {
            int i = 1 + MathHelper.floor(damage);
            this.activeItemStack.damageItem(i, this);

            if (this.activeItemStack.isEmpty())
            {
                EnumHand enumhand = this.getActiveHand();

                if (enumhand == EnumHand.MAIN_HAND)
                {
                    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
                else
                {
                    this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }

                this.activeItemStack = ItemStack.EMPTY;
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
            }
        }
    }

    /**
     * When searching for vulnerable players, if a player is invisible, the return value of this is the chance of seeing
     * them anyway.
     */
    public float getArmorVisibility()
    {
        int i = 0;

        for (ItemStack itemstack : this.inventory.armorInventory)
        {
            if (!itemstack.isEmpty())
            {
                ++i;
            }
        }

        return (float)i / (float)this.inventory.armorInventory.size();
    }

    /**
     * Deals damage to the entity. This will take the armor of the entity into consideration before damaging the health
     * bar.
     */
    protected void damageEntity(DamageSource damageSrc, float damageAmount)
    {
        if (!this.isEntityInvulnerable(damageSrc))
        {
            damageAmount = this.applyArmorCalculations(damageSrc, damageAmount);
            damageAmount = this.applyPotionDamageCalculations(damageSrc, damageAmount);
            float f = damageAmount;
            damageAmount = Math.max(damageAmount - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - damageAmount));

            if (damageAmount != 0.0F)
            {
                this.addExhaustion(damageSrc.getHungerDamage());
                float f1 = this.getHealth();
                this.setHealth(this.getHealth() - damageAmount);
                this.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);

                if (damageAmount < 3.4028235E37F)
                {
                    this.addStat(StatList.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
                }
            }
        }
    }

    public void openEditSign(TileEntitySign signTile)
    {
    }

    public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock)
    {
    }

    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock)
    {
    }

    public void openEditStructure(TileEntityStructure structure)
    {
    }

    public void displayVillagerTradeGui(IMerchant villager)
    {
    }

    /**
     * Displays the GUI for interacting with a chest inventory.
     */
    public void displayGUIChest(IInventory chestInventory)
    {
    }

    public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn)
    {
    }

    public void displayGui(IInteractionObject guiOwner)
    {
    }

    public void openBook(ItemStack stack, EnumHand hand)
    {
    }

    public EnumActionResult interactOn(Entity p_190775_1_, EnumHand p_190775_2_)
    {
        if (this.isSpectator())
        {
            if (p_190775_1_ instanceof IInventory)
            {
                this.displayGUIChest((IInventory)p_190775_1_);
            }

            return EnumActionResult.PASS;
        }
        else
        {
            ItemStack itemstack = this.getHeldItem(p_190775_2_);
            ItemStack itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();

            if (p_190775_1_.processInitialInteract(this, p_190775_2_))
            {
                if (this.capabilities.isCreativeMode && itemstack == this.getHeldItem(p_190775_2_) && itemstack.getCount() < itemstack1.getCount())
                {
                    itemstack.setCount(itemstack1.getCount());
                }

                return EnumActionResult.SUCCESS;
            }
            else
            {
                if (!itemstack.isEmpty() && p_190775_1_ instanceof EntityLivingBase)
                {
                    if (this.capabilities.isCreativeMode)
                    {
                        itemstack = itemstack1;
                    }

                    if (itemstack.interactWithEntity(this, (EntityLivingBase)p_190775_1_, p_190775_2_))
                    {
                        if (itemstack.isEmpty() && !this.capabilities.isCreativeMode)
                        {
                            this.setHeldItem(p_190775_2_, ItemStack.EMPTY);
                        }

                        return EnumActionResult.SUCCESS;
                    }
                }

                return EnumActionResult.PASS;
            }
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return -0.35D;
    }

    /**
     * Dismounts this entity from the entity it is riding.
     */
    public void dismountRidingEntity()
    {
        super.dismountRidingEntity();
        this.rideCooldown = 0;
    }

    /**
     * Attacks for the player the targeted entity with the currently equipped item.  The equipped item has hitEntity
     * called on it. Args: targetEntity
     */
    public void attackTargetEntityWithCurrentItem(Entity targetEntity)
    {
        if (targetEntity.canBeAttackedWithItem())
        {
            if (!targetEntity.hitByEntity(this))
            {
                float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                float f1;

                if (targetEntity instanceof EntityLivingBase)
                {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
                }
                else
                {
                    f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
                }

                float f2 = this.getCooledAttackStrength(0.5F);
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                this.resetCooldown();

                if (f > 0.0F || f1 > 0.0F)
                {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(this);

                    if (this.isSprinting() && flag)
                    {
                        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(MobEffects.BLINDNESS) && !this.isRiding() && targetEntity instanceof EntityLivingBase;
                    flag2 = flag2 && !this.isSprinting();

                    if (flag2)
                    {
                        f *= 1.5F;
                    }

                    f = f + f1;
                    boolean flag3 = false;
                    double d0 = (double)(this.distanceWalkedModified - this.prevDistanceWalkedModified);

                    if (flag && !flag2 && !flag1 && this.onGround && d0 < (double)this.getAIMoveSpeed())
                    {
                        ItemStack itemstack = this.getHeldItem(EnumHand.MAIN_HAND);

                        if (itemstack.getItem() instanceof ItemSword)
                        {
                            flag3 = true;
                        }
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(this);

                    if (targetEntity instanceof EntityLivingBase)
                    {
                        f4 = ((EntityLivingBase)targetEntity).getHealth();

                        if (j > 0 && !targetEntity.isBurning())
                        {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    double d1 = targetEntity.motionX;
                    double d2 = targetEntity.motionY;
                    double d3 = targetEntity.motionZ;
                    boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);

                    if (flag5)
                    {
                        if (i > 0)
                        {
                            if (targetEntity instanceof EntityLivingBase)
                            {
                                ((EntityLivingBase)targetEntity).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                            }
                            else
                            {
                                targetEntity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 0.017453292F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * (float)i * 0.5F));
                            }

                            this.motionX *= 0.6D;
                            this.motionZ *= 0.6D;
                            this.setSprinting(false);
                        }

                        if (flag3)
                        {
                            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(this) * f;

                            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D)))
                            {
                                if (entitylivingbase != this && entitylivingbase != targetEntity && !this.isOnSameTeam(entitylivingbase) && this.getDistanceSqToEntity(entitylivingbase) < 9.0D)
                                {
                                    entitylivingbase.knockBack(this, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                                    entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(this), f3);
                                }
                            }

                            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                            this.spawnSweepParticles();
                        }

                        if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged)
                        {
                            ((EntityPlayerMP)targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.motionX = d1;
                            targetEntity.motionY = d2;
                            targetEntity.motionZ = d3;
                        }

                        if (flag2)
                        {
                            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                            this.onCriticalHit(targetEntity);
                        }

                        if (!flag2 && !flag3)
                        {
                            if (flag)
                            {
                                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
                            }
                            else
                            {
                                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (f1 > 0.0F)
                        {
                            this.onEnchantmentCritical(targetEntity);
                        }

                        this.setLastAttackedEntity(targetEntity);

                        if (targetEntity instanceof EntityLivingBase)
                        {
                            EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, this);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
                        ItemStack itemstack1 = this.getHeldItemMainhand();
                        Entity entity = targetEntity;

                        if (targetEntity instanceof MultiPartEntityPart)
                        {
                            IEntityMultiPart ientitymultipart = ((MultiPartEntityPart)targetEntity).parent;

                            if (ientitymultipart instanceof EntityLivingBase)
                            {
                                entity = (EntityLivingBase)ientitymultipart;
                            }
                        }

                        if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase)
                        {
                            itemstack1.hitEntity((EntityLivingBase)entity, this);

                            if (itemstack1.isEmpty())
                            {
                                this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof EntityLivingBase)
                        {
                            float f5 = f4 - ((EntityLivingBase)targetEntity).getHealth();
                            this.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

                            if (j > 0)
                            {
                                targetEntity.setFire(j * 4);
                            }

                            if (this.world instanceof WorldServer && f5 > 2.0F)
                            {
                                int k = (int)((double)f5 * 0.5D);
                                ((WorldServer)this.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + (double)(targetEntity.height * 0.5F), targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        this.addExhaustion(0.1F);
                    }
                    else
                    {
                        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);

                        if (flag4)
                        {
                            targetEntity.extinguish();
                        }
                    }
                }
            }
        }
    }

    public void disableShield(boolean p_190777_1_)
    {
        float f = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

        if (p_190777_1_)
        {
            f += 0.75F;
        }

        if (this.rand.nextFloat() < f)
        {
            this.getCooldownTracker().setCooldown(Items.SHIELD, 100);
            this.resetActiveHand();
            this.world.setEntityState(this, (byte)30);
        }
    }

    /**
     * Called when the entity is dealt a critical hit.
     */
    public void onCriticalHit(Entity entityHit)
    {
    }

    public void onEnchantmentCritical(Entity entityHit)
    {
    }

    public void spawnSweepParticles()
    {
        double d0 = (double)(-MathHelper.sin(this.rotationYaw * 0.017453292F));
        double d1 = (double)MathHelper.cos(this.rotationYaw * 0.017453292F);

        if (this.world instanceof WorldServer)
        {
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX + d0, this.posY + (double)this.height * 0.5D, this.posZ + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public void respawnPlayer()
    {
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);

        if (this.openContainer != null)
        {
            this.openContainer.onContainerClosed(this);
        }
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }

    /**
     * returns true if this is an EntityPlayerSP, or the logged in player.
     */
    public boolean isUser()
    {
        return false;
    }

    /**
     * Returns the GameProfile for this player
     */
    public GameProfile getGameProfile()
    {
        return this.gameProfile;
    }

    public EntityPlayer.SleepResult trySleep(BlockPos bedLocation)
    {
        EnumFacing enumfacing = (EnumFacing)this.world.getBlockState(bedLocation).getValue(BlockHorizontal.FACING);

        if (!this.world.isRemote)
        {
            if (this.isPlayerSleeping() || !this.isEntityAlive())
            {
                return EntityPlayer.SleepResult.OTHER_PROBLEM;
            }

            if (!this.world.provider.isSurfaceWorld())
            {
                return EntityPlayer.SleepResult.NOT_POSSIBLE_HERE;
            }

            if (this.world.isDaytime())
            {
                return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
            }

            if (!this.bedInRange(bedLocation, enumfacing))
            {
                return EntityPlayer.SleepResult.TOO_FAR_AWAY;
            }

            double d0 = 8.0D;
            double d1 = 5.0D;
            List<EntityMob> list = this.world.<EntityMob>getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB((double)bedLocation.getX() - 8.0D, (double)bedLocation.getY() - 5.0D, (double)bedLocation.getZ() - 8.0D, (double)bedLocation.getX() + 8.0D, (double)bedLocation.getY() + 5.0D, (double)bedLocation.getZ() + 8.0D), new EntityPlayer.SleepEnemyPredicate(this));

            if (!list.isEmpty())
            {
                return EntityPlayer.SleepResult.NOT_SAFE;
            }
        }

        if (this.isRiding())
        {
            this.dismountRidingEntity();
        }

        this.spawnShoulderEntities();
        this.setSize(0.2F, 0.2F);

        if (this.world.isBlockLoaded(bedLocation))
        {
            float f1 = 0.5F + (float)enumfacing.getFrontOffsetX() * 0.4F;
            float f = 0.5F + (float)enumfacing.getFrontOffsetZ() * 0.4F;
            this.setRenderOffsetForSleep(enumfacing);
            this.setPosition((double)((float)bedLocation.getX() + f1), (double)((float)bedLocation.getY() + 0.6875F), (double)((float)bedLocation.getZ() + f));
        }
        else
        {
            this.setPosition((double)((float)bedLocation.getX() + 0.5F), (double)((float)bedLocation.getY() + 0.6875F), (double)((float)bedLocation.getZ() + 0.5F));
        }

        this.sleeping = true;
        this.sleepTimer = 0;
        this.bedLocation = bedLocation;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;

        if (!this.world.isRemote)
        {
            this.world.updateAllPlayersSleepingFlag();
        }

        return EntityPlayer.SleepResult.OK;
    }

    private boolean bedInRange(BlockPos p_190774_1_, EnumFacing p_190774_2_)
    {
        if (Math.abs(this.posX - (double)p_190774_1_.getX()) <= 3.0D && Math.abs(this.posY - (double)p_190774_1_.getY()) <= 2.0D && Math.abs(this.posZ - (double)p_190774_1_.getZ()) <= 3.0D)
        {
            return true;
        }
        else
        {
            BlockPos blockpos = p_190774_1_.offset(p_190774_2_.getOpposite());
            return Math.abs(this.posX - (double)blockpos.getX()) <= 3.0D && Math.abs(this.posY - (double)blockpos.getY()) <= 2.0D && Math.abs(this.posZ - (double)blockpos.getZ()) <= 3.0D;
        }
    }

    private void setRenderOffsetForSleep(EnumFacing p_175139_1_)
    {
        this.renderOffsetX = -1.8F * (float)p_175139_1_.getFrontOffsetX();
        this.renderOffsetZ = -1.8F * (float)p_175139_1_.getFrontOffsetZ();
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn)
    {
        this.setSize(0.6F, 1.8F);
        IBlockState iblockstate = this.world.getBlockState(this.bedLocation);

        if (this.bedLocation != null && iblockstate.getBlock() == Blocks.BED)
        {
            this.world.setBlockState(this.bedLocation, iblockstate.withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
            BlockPos blockpos = BlockBed.getSafeExitLocation(this.world, this.bedLocation, 0);

            if (blockpos == null)
            {
                blockpos = this.bedLocation.up();
            }

            this.setPosition((double)((float)blockpos.getX() + 0.5F), (double)((float)blockpos.getY() + 0.1F), (double)((float)blockpos.getZ() + 0.5F));
        }

        this.sleeping = false;

        if (!this.world.isRemote && updateWorldFlag)
        {
            this.world.updateAllPlayersSleepingFlag();
        }

        this.sleepTimer = immediately ? 0 : 100;

        if (setSpawn)
        {
            this.setSpawnPoint(this.bedLocation, false);
        }
    }

    private boolean isInBed()
    {
        return this.world.getBlockState(this.bedLocation).getBlock() == Blocks.BED;
    }

    @Nullable

    /**
     * Return null if bed is invalid
     */
    public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn)
    {
        Block block = worldIn.getBlockState(bedLocation).getBlock();

        if (block != Blocks.BED)
        {
            if (!forceSpawn)
            {
                return null;
            }
            else
            {
                boolean flag = block.canSpawnInBlock();
                boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().canSpawnInBlock();
                return flag && flag1 ? bedLocation : null;
            }
        }
        else
        {
            return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
        }
    }

    /**
     * Returns the orientation of the bed in degrees.
     */
    public float getBedOrientationInDegrees()
    {
        if (this.bedLocation != null)
        {
            EnumFacing enumfacing = (EnumFacing)this.world.getBlockState(this.bedLocation).getValue(BlockHorizontal.FACING);

            switch (enumfacing)
            {
                case SOUTH:
                    return 90.0F;

                case WEST:
                    return 0.0F;

                case NORTH:
                    return 270.0F;

                case EAST:
                    return 180.0F;
            }
        }

        return 0.0F;
    }

    /**
     * Returns whether player is sleeping or not
     */
    public boolean isPlayerSleeping()
    {
        return this.sleeping;
    }

    /**
     * Returns whether or not the player is asleep and the screen has fully faded.
     */
    public boolean isPlayerFullyAsleep()
    {
        return this.sleeping && this.sleepTimer >= 100;
    }

    public int getSleepTimer()
    {
        return this.sleepTimer;
    }

    public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar)
    {
    }

    public BlockPos getBedLocation()
    {
        return this.spawnChunk;
    }

    public boolean isSpawnForced()
    {
        return this.spawnForced;
    }

    public void setSpawnPoint(BlockPos pos, boolean forced)
    {
        if (pos != null)
        {
            this.spawnChunk = pos;
            this.spawnForced = forced;
        }
        else
        {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }

    /**
     * Add a stat once
     */
    public void addStat(StatBase stat)
    {
        this.addStat(stat, 1);
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase stat, int amount)
    {
    }

    public void takeStat(StatBase stat)
    {
    }

    public void unlockRecipes(List<IRecipe> p_192021_1_)
    {
    }

    public void unlockRecipes(ResourceLocation[] p_193102_1_)
    {
    }

    public void resetRecipes(List<IRecipe> p_192022_1_)
    {
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    public void jump()
    {
        super.jump();
        this.addStat(StatList.JUMP);

        if (this.isSprinting())
        {
            this.addExhaustion(0.2F);
        }
        else
        {
            this.addExhaustion(0.05F);
        }
    }

    public void travel(float p_191986_1_, float p_191986_2_, float p_191986_3_)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;

        if (this.capabilities.isFlying && !this.isRiding())
        {
            double d3 = this.motionY;
            float f = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed() * (float)(this.isSprinting() ? 2 : 1);
            super.travel(p_191986_1_, p_191986_2_, p_191986_3_);
            this.motionY = d3 * 0.6D;
            this.jumpMovementFactor = f;
            this.fallDistance = 0.0F;
            this.setFlag(7, false);
        }
        else
        {
            super.travel(p_191986_1_, p_191986_2_, p_191986_3_);
        }

        this.addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
    }

    /**
     * the movespeed used for the new AI system
     */
    public float getAIMoveSpeed()
    {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
    }

    /**
     * Adds a value to a movement statistic field - like run, walk, swin or climb.
     */
    public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_)
    {
        if (!this.isRiding())
        {
            if (this.isInsideOfMaterial(Material.WATER))
            {
                int i = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i > 0)
                {
                    this.addStat(StatList.DIVE_ONE_CM, i);
                    this.addExhaustion(0.01F * (float)i * 0.01F);
                }
            }
            else if (this.isInWater())
            {
                int j = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (j > 0)
                {
                    this.addStat(StatList.SWIM_ONE_CM, j);
                    this.addExhaustion(0.01F * (float)j * 0.01F);
                }
            }
            else if (this.isOnLadder())
            {
                if (p_71000_3_ > 0.0D)
                {
                    this.addStat(StatList.CLIMB_ONE_CM, (int)Math.round(p_71000_3_ * 100.0D));
                }
            }
            else if (this.onGround)
            {
                int k = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (k > 0)
                {
                    if (this.isSprinting())
                    {
                        this.addStat(StatList.SPRINT_ONE_CM, k);
                        this.addExhaustion(0.1F * (float)k * 0.01F);
                    }
                    else if (this.isSneaking())
                    {
                        this.addStat(StatList.CROUCH_ONE_CM, k);
                        this.addExhaustion(0.0F * (float)k * 0.01F);
                    }
                    else
                    {
                        this.addStat(StatList.WALK_ONE_CM, k);
                        this.addExhaustion(0.0F * (float)k * 0.01F);
                    }
                }
            }
            else if (this.isElytraFlying())
            {
                int l = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
                this.addStat(StatList.AVIATE_ONE_CM, l);
            }
            else
            {
                int i1 = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i1 > 25)
                {
                    this.addStat(StatList.FLY_ONE_CM, i1);
                }
            }
        }
    }

    /**
     * Adds a value to a mounted movement statistic field - by minecart, boat, or pig.
     */
    private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_)
    {
        if (this.isRiding())
        {
            int i = Math.round(MathHelper.sqrt(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);

            if (i > 0)
            {
                if (this.getRidingEntity() instanceof EntityMinecart)
                {
                    this.addStat(StatList.MINECART_ONE_CM, i);
                }
                else if (this.getRidingEntity() instanceof EntityBoat)
                {
                    this.addStat(StatList.BOAT_ONE_CM, i);
                }
                else if (this.getRidingEntity() instanceof EntityPig)
                {
                    this.addStat(StatList.PIG_ONE_CM, i);
                }
                else if (this.getRidingEntity() instanceof AbstractHorse)
                {
                    this.addStat(StatList.HORSE_ONE_CM, i);
                }
            }
        }
    }

    public void fall(float distance, float damageMultiplier)
    {
        if (!this.capabilities.allowFlying)
        {
            if (distance >= 2.0F)
            {
                this.addStat(StatList.FALL_ONE_CM, (int)Math.round((double)distance * 100.0D));
            }

            super.fall(distance, damageMultiplier);
        }
    }

    /**
     * Plays the {@link #getSplashSound() splash sound}, and the {@link ParticleType#WATER_BUBBLE} and {@link
     * ParticleType#WATER_SPLASH} particles.
     */
    protected void doWaterSplashEffect()
    {
        if (!this.isSpectator())
        {
            super.doWaterSplashEffect();
        }
    }

    protected SoundEvent getFallSound(int heightIn)
    {
        return heightIn > 4 ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLivingBase entityLivingIn)
    {
        EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.ENTITY_EGGS.get(EntityList.getKey(entityLivingIn));

        if (entitylist$entityegginfo != null)
        {
            this.addStat(entitylist$entityegginfo.killEntityStat);
        }
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb()
    {
        if (!this.capabilities.isFlying)
        {
            super.setInWeb();
        }
    }

    /**
     * Add experience points to player.
     */
    public void addExperience(int amount)
    {
        this.addScore(amount);
        int i = Integer.MAX_VALUE - this.experienceTotal;

        if (amount > i)
        {
            amount = i;
        }

        this.experience += (float)amount / (float)this.xpBarCap();

        for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= (float)this.xpBarCap())
        {
            this.experience = (this.experience - 1.0F) * (float)this.xpBarCap();
            this.addExperienceLevel(1);
        }
    }

    public int getXPSeed()
    {
        return this.xpSeed;
    }

    public void onEnchant(ItemStack enchantedItem, int cost)
    {
        this.experienceLevel -= cost;

        if (this.experienceLevel < 0)
        {
            this.experienceLevel = 0;
            this.experience = 0.0F;
            this.experienceTotal = 0;
        }

        this.xpSeed = this.rand.nextInt();
    }

    /**
     * Add experience levels to this player.
     */
    public void addExperienceLevel(int levels)
    {
        this.experienceLevel += levels;

        if (this.experienceLevel < 0)
        {
            this.experienceLevel = 0;
            this.experience = 0.0F;
            this.experienceTotal = 0;
        }

        if (levels > 0 && this.experienceLevel % 5 == 0 && (float)this.lastXPSound < (float)this.ticksExisted - 100.0F)
        {
            float f = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75F, 1.0F);
            this.lastXPSound = this.ticksExisted;
        }
    }

    /**
     * This method returns the cap amount of experience that the experience bar can hold. With each level, the
     * experience cap on the player's experience bar is raised by 10.
     */
    public int xpBarCap()
    {
        if (this.experienceLevel >= 30)
        {
            return 112 + (this.experienceLevel - 30) * 9;
        }
        else
        {
            return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
        }
    }

    /**
     * increases exhaustion level by supplied amount
     */
    public void addExhaustion(float exhaustion)
    {
        if (!this.capabilities.disableDamage)
        {
            if (!this.world.isRemote)
            {
                this.foodStats.addExhaustion(exhaustion);
            }
        }
    }

    /**
     * Returns the player's FoodStats object.
     */
    public FoodStats getFoodStats()
    {
        return this.foodStats;
    }

    public boolean canEat(boolean ignoreHunger)
    {
        return (ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }

    /**
     * Checks if the player's health is not full and not zero.
     */
    public boolean shouldHeal()
    {
        return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
    }

    public boolean isAllowEdit()
    {
        return this.capabilities.allowEdit;
    }

    public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack)
    {
        if (this.capabilities.allowEdit)
        {
            return true;
        }
        else if (stack.isEmpty())
        {
            return false;
        }
        else
        {
            BlockPos blockpos = pos.offset(facing.getOpposite());
            Block block = this.world.getBlockState(blockpos).getBlock();
            return stack.canPlaceOn(block) || stack.canEditBlocks();
        }
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer player)
    {
        if (!this.world.getGameRules().getBoolean("keepInventory") && !this.isSpectator())
        {
            int i = this.experienceLevel * 7;
            return i > 100 ? 100 : i;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Only use is to identify if class is an instance of player for experience dropping
     */
    protected boolean isPlayer()
    {
        return true;
    }

    public boolean getAlwaysRenderNameTagForRender()
    {
        return true;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return !this.capabilities.isFlying;
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
    }

    /**
     * Sets the player's game mode and sends it to them.
     */
    public void setGameType(GameType gameType)
    {
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.gameProfile.getName();
    }

    /**
     * Returns the InventoryEnderChest of this player.
     */
    public InventoryEnderChest getInventoryEnderChest()
    {
        return this.enderChest;
    }

    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
    {
        if (slotIn == EntityEquipmentSlot.MAINHAND)
        {
            return this.inventory.getCurrentItem();
        }
        else if (slotIn == EntityEquipmentSlot.OFFHAND)
        {
            return this.inventory.offHandInventory.get(0);
        }
        else
        {
            return slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? (ItemStack)this.inventory.armorInventory.get(slotIn.getIndex()) : ItemStack.EMPTY;
        }
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        if (slotIn == EntityEquipmentSlot.MAINHAND)
        {
            this.playEquipSound(stack);
            this.inventory.mainInventory.set(this.inventory.currentItem, stack);
        }
        else if (slotIn == EntityEquipmentSlot.OFFHAND)
        {
            this.playEquipSound(stack);
            this.inventory.offHandInventory.set(0, stack);
        }
        else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
        {
            this.playEquipSound(stack);
            this.inventory.armorInventory.set(slotIn.getIndex(), stack);
        }
    }

    public boolean addItemStackToInventory(ItemStack p_191521_1_)
    {
        this.playEquipSound(p_191521_1_);
        return this.inventory.addItemStackToInventory(p_191521_1_);
    }

    public Iterable<ItemStack> getHeldEquipment()
    {
        return Lists.newArrayList(this.getHeldItemMainhand(), this.getHeldItemOffhand());
    }

    public Iterable<ItemStack> getArmorInventoryList()
    {
        return this.inventory.armorInventory;
    }

    public boolean addShoulderEntity(NBTTagCompound p_192027_1_)
    {
        if (!this.isRiding() && this.onGround && !this.isInWater())
        {
            if (this.getLeftShoulderEntity().hasNoTags())
            {
                this.setLeftShoulderEntity(p_192027_1_);
                return true;
            }
            else if (this.getRightShoulderEntity().hasNoTags())
            {
                this.setRightShoulderEntity(p_192027_1_);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    protected void spawnShoulderEntities()
    {
        this.spawnShoulderEntity(this.getLeftShoulderEntity());
        this.setLeftShoulderEntity(new NBTTagCompound());
        this.spawnShoulderEntity(this.getRightShoulderEntity());
        this.setRightShoulderEntity(new NBTTagCompound());
    }

    private void spawnShoulderEntity(@Nullable NBTTagCompound p_192026_1_)
    {
        if (!this.world.isRemote && !p_192026_1_.hasNoTags())
        {
            Entity entity = EntityList.createEntityFromNBT(p_192026_1_, this.world);

            if (entity instanceof EntityTameable)
            {
                ((EntityTameable)entity).setOwnerId(this.entityUniqueID);
            }

            entity.setPosition(this.posX, this.posY + 0.699999988079071D, this.posZ);
            this.world.spawnEntity(entity);
        }
    }

    /**
     * Only used by renderer in EntityLivingBase subclasses.
     * Determines if an entity is visible or not to a specfic player, if the entity is normally invisible.
     * For EntityLivingBase subclasses, returning false when invisible will render the entity semitransparent.
     */
    public boolean isInvisibleToPlayer(EntityPlayer player)
    {
        if (!this.isInvisible())
        {
            return false;
        }
        else if (player.isSpectator())
        {
            return false;
        }
        else
        {
            Team team = this.getTeam();
            return team == null || player == null || player.getTeam() != team || !team.getSeeFriendlyInvisiblesEnabled();
        }
    }

    /**
     * Returns true if the player is in spectator mode.
     */
    public abstract boolean isSpectator();

    public abstract boolean isCreative();

    public boolean isPushedByWater()
    {
        return !this.capabilities.isFlying;
    }

    public Scoreboard getWorldScoreboard()
    {
        return this.world.getScoreboard();
    }

    public Team getTeam()
    {
        return this.getWorldScoreboard().getPlayersTeam(this.getName());
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        ITextComponent itextcomponent = new TextComponentString(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
        itextcomponent.getStyle().setInsertion(this.getName());
        return itextcomponent;
    }

    public float getEyeHeight()
    {
        float f = 1.62F;

        if (this.isPlayerSleeping())
        {
            f = 0.2F;
        }
        else if (!this.isSneaking() && this.height != 1.65F)
        {
            if (this.isElytraFlying() || this.height == 0.6F)
            {
                f = 0.4F;
            }
        }
        else
        {
            f -= 0.08F;
        }

        return f;
    }

    public void setAbsorptionAmount(float amount)
    {
        if (amount < 0.0F)
        {
            amount = 0.0F;
        }

        this.getDataManager().set(ABSORPTION, Float.valueOf(amount));
    }

    /**
     * Returns the amount of health added by the Absorption effect.
     */
    public float getAbsorptionAmount()
    {
        return ((Float)this.getDataManager().get(ABSORPTION)).floatValue();
    }

    /**
     * Gets a players UUID given their GameProfie
     */
    public static UUID getUUID(GameProfile profile)
    {
        UUID uuid = profile.getId();

        if (uuid == null)
        {
            uuid = getOfflineUUID(profile.getName());
        }

        return uuid;
    }

    public static UUID getOfflineUUID(String username)
    {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Check whether this player can open an inventory locked with the given LockCode.
     */
    public boolean canOpen(LockCode code)
    {
        if (code.isEmpty())
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.getHeldItemMainhand();
            return !itemstack.isEmpty() && itemstack.hasDisplayName() ? itemstack.getDisplayName().equals(code.getLock()) : false;
        }
    }

    public boolean isWearing(EnumPlayerModelParts part)
    {
        return (((Byte)this.getDataManager().get(PLAYER_MODEL_FLAG)).byteValue() & part.getPartMask()) == part.getPartMask();
    }

    /**
     * Returns true if the command sender should be sent feedback about executed commands
     */
    public boolean sendCommandFeedback()
    {
        return this.getServer().worlds[0].getGameRules().getBoolean("sendCommandFeedback");
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn)
    {
        if (inventorySlot >= 0 && inventorySlot < this.inventory.mainInventory.size())
        {
            this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
            return true;
        }
        else
        {
            EntityEquipmentSlot entityequipmentslot;

            if (inventorySlot == 100 + EntityEquipmentSlot.HEAD.getIndex())
            {
                entityequipmentslot = EntityEquipmentSlot.HEAD;
            }
            else if (inventorySlot == 100 + EntityEquipmentSlot.CHEST.getIndex())
            {
                entityequipmentslot = EntityEquipmentSlot.CHEST;
            }
            else if (inventorySlot == 100 + EntityEquipmentSlot.LEGS.getIndex())
            {
                entityequipmentslot = EntityEquipmentSlot.LEGS;
            }
            else if (inventorySlot == 100 + EntityEquipmentSlot.FEET.getIndex())
            {
                entityequipmentslot = EntityEquipmentSlot.FEET;
            }
            else
            {
                entityequipmentslot = null;
            }

            if (inventorySlot == 98)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStackIn);
                return true;
            }
            else if (inventorySlot == 99)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemStackIn);
                return true;
            }
            else if (entityequipmentslot == null)
            {
                int i = inventorySlot - 200;

                if (i >= 0 && i < this.enderChest.getSizeInventory())
                {
                    this.enderChest.setInventorySlotContents(i, itemStackIn);
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if (!itemStackIn.isEmpty())
                {
                    if (!(itemStackIn.getItem() instanceof ItemArmor) && !(itemStackIn.getItem() instanceof ItemElytra))
                    {
                        if (entityequipmentslot != EntityEquipmentSlot.HEAD)
                        {
                            return false;
                        }
                    }
                    else if (EntityLiving.getSlotForItemStack(itemStackIn) != entityequipmentslot)
                    {
                        return false;
                    }
                }

                this.inventory.setInventorySlotContents(entityequipmentslot.getIndex() + this.inventory.mainInventory.size(), itemStackIn);
                return true;
            }
        }
    }

    /**
     * Whether the "reducedDebugInfo" option is active for this player.
     */
    public boolean hasReducedDebug()
    {
        return this.hasReducedDebug;
    }

    public void setReducedDebug(boolean reducedDebug)
    {
        this.hasReducedDebug = reducedDebug;
    }

    public EnumHandSide getPrimaryHand()
    {
        return ((Byte)this.dataManager.get(MAIN_HAND)).byteValue() == 0 ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
    }

    public void setPrimaryHand(EnumHandSide hand)
    {
        this.dataManager.set(MAIN_HAND, Byte.valueOf((byte)(hand == EnumHandSide.LEFT ? 0 : 1)));
    }

    public NBTTagCompound getLeftShoulderEntity()
    {
        return (NBTTagCompound)this.dataManager.get(LEFT_SHOULDER_ENTITY);
    }

    protected void setLeftShoulderEntity(NBTTagCompound tag)
    {
        this.dataManager.set(LEFT_SHOULDER_ENTITY, tag);
    }

    public NBTTagCompound getRightShoulderEntity()
    {
        return (NBTTagCompound)this.dataManager.get(RIGHT_SHOULDER_ENTITY);
    }

    protected void setRightShoulderEntity(NBTTagCompound tag)
    {
        this.dataManager.set(RIGHT_SHOULDER_ENTITY, tag);
    }

    public float getCooldownPeriod()
    {
        return (float)(1.0D / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0D);
    }

    /**
     * Returns the percentage of attack power available based on the cooldown (zero to one).
     */
    public float getCooledAttackStrength(float adjustTicks)
    {
        return MathHelper.clamp(((float)this.ticksSinceLastSwing + adjustTicks) / this.getCooldownPeriod(), 0.0F, 1.0F);
    }

    public void resetCooldown()
    {
        this.ticksSinceLastSwing = 0;
    }

    public CooldownTracker getCooldownTracker()
    {
        return this.cooldownTracker;
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn)
    {
        if (!this.isPlayerSleeping())
        {
            super.applyEntityCollision(entityIn);
        }
    }

    public float getLuck()
    {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue();
    }

    /**
     * Can the player use command blocks. It checks if the player is on Creative mode and has permissions (is he OP)
     */
    public boolean canUseCommandBlock()
    {
        return this.capabilities.isCreativeMode && this.canUseCommand(2, "");
    }

    public static enum EnumChatVisibility
    {
        FULL(0, "options.chat.visibility.full"),
        SYSTEM(1, "options.chat.visibility.system"),
        HIDDEN(2, "options.chat.visibility.hidden");

        private static final EntityPlayer.EnumChatVisibility[] ID_LOOKUP = new EntityPlayer.EnumChatVisibility[values().length];
        private final int chatVisibility;
        private final String resourceKey;

        private EnumChatVisibility(int id, String resourceKey)
        {
            this.chatVisibility = id;
            this.resourceKey = resourceKey;
        }

        public int getChatVisibility()
        {
            return this.chatVisibility;
        }

        public static EntityPlayer.EnumChatVisibility getEnumChatVisibility(int id)
        {
            return ID_LOOKUP[id % ID_LOOKUP.length];
        }

        public String getResourceKey()
        {
            return this.resourceKey;
        }

        static {
            for (EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility : values())
            {
                ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
            }
        }
    }

    static class SleepEnemyPredicate implements Predicate<EntityMob>
    {
        private final EntityPlayer player;

        private SleepEnemyPredicate(EntityPlayer playerIn)
        {
            this.player = playerIn;
        }

        public boolean apply(@Nullable EntityMob p_apply_1_)
        {
            return p_apply_1_.isPreventingPlayerRest(this.player);
        }
    }

    public static enum SleepResult
    {
        OK,
        NOT_POSSIBLE_HERE,
        NOT_POSSIBLE_NOW,
        TOO_FAR_AWAY,
        OTHER_PROBLEM,
        NOT_SAFE;
    }
}
