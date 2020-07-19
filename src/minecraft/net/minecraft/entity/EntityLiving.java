package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.scoreboard.Team;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public abstract class EntityLiving extends EntityLivingBase
{
    private static final DataParameter<Byte> AI_FLAGS = EntityDataManager.<Byte>createKey(EntityLiving.class, DataSerializers.BYTE);

    /** Number of ticks since this EntityLiving last produced its sound */
    public int livingSoundTime;

    /** The experience points the Entity gives. */
    protected int experienceValue;
    private final EntityLookHelper lookHelper;
    protected EntityMoveHelper moveHelper;

    /** Entity jumping helper */
    protected EntityJumpHelper jumpHelper;
    private final EntityBodyHelper bodyHelper;
    protected PathNavigate navigator;

    /**
     * Active AI tasks (moving, looking, attack the target selected by {@link #targetTasks}, etc.)
     */
    protected final EntityAITasks tasks;

    /** (Usually one-shot) tasks used to select an attack target */
    protected final EntityAITasks targetTasks;

    /** The active target the Task system uses for tracking */
    private EntityLivingBase attackTarget;
    private final EntitySenses senses;
    private final NonNullList<ItemStack> inventoryHands = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

    /** Chances for equipment in hands dropping when this entity dies. */
    protected float[] inventoryHandsDropChances = new float[2];
    private final NonNullList<ItemStack> inventoryArmor = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);

    /** Chances for armor dropping when this entity dies. */
    protected float[] inventoryArmorDropChances = new float[4];

    /** Whether this entity can pick up items from the ground. */
    private boolean canPickUpLoot;

    /** Whether this entity should NOT despawn. */
    private boolean persistenceRequired;
    private final Map<PathNodeType, Float> mapPathPriority = Maps.newEnumMap(PathNodeType.class);
    private ResourceLocation deathLootTable;
    private long deathLootTableSeed;
    private boolean isLeashed;
    private Entity leashedToEntity;
    private NBTTagCompound leashNBTTag;
    public int randomMobsId = 0;
    public Biome spawnBiome = null;
    public BlockPos spawnPosition = null;
    private UUID teamUuid = null;
    private String teamUuidString = null;

    public EntityLiving(World worldIn)
    {
        super(worldIn);
        this.tasks = new EntityAITasks(worldIn != null && worldIn.profiler != null ? worldIn.profiler : null);
        this.targetTasks = new EntityAITasks(worldIn != null && worldIn.profiler != null ? worldIn.profiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = this.createBodyHelper();
        this.navigator = this.createNavigator(worldIn);
        this.senses = new EntitySenses(this);
        Arrays.fill(this.inventoryArmorDropChances, 0.085F);
        Arrays.fill(this.inventoryHandsDropChances, 0.085F);

        if (worldIn != null && !worldIn.isRemote)
        {
            this.initEntityAI();
        }

        UUID uuid = this.getUniqueID();
        long i = uuid.getLeastSignificantBits();
        this.randomMobsId = (int)(i & 2147483647L);
    }

    protected void initEntityAI()
    {
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }

    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateGround(this, worldIn);
    }

    public float getPathPriority(PathNodeType nodeType)
    {
        Float f = this.mapPathPriority.get(nodeType);
        return f == null ? nodeType.getPriority() : f.floatValue();
    }

    public void setPathPriority(PathNodeType nodeType, float priority)
    {
        this.mapPathPriority.put(nodeType, Float.valueOf(priority));
    }

    protected EntityBodyHelper createBodyHelper()
    {
        return new EntityBodyHelper(this);
    }

    public EntityLookHelper getLookHelper()
    {
        return this.lookHelper;
    }

    public EntityMoveHelper getMoveHelper()
    {
        return this.moveHelper;
    }

    public EntityJumpHelper getJumpHelper()
    {
        return this.jumpHelper;
    }

    public PathNavigate getNavigator()
    {
        return this.navigator;
    }

    /**
     * returns the EntitySenses Object for the EntityLiving
     */
    public EntitySenses getEntitySenses()
    {
        return this.senses;
    }

    @Nullable

    /**
     * Gets the active target the Task system uses for tracking
     */
    public EntityLivingBase getAttackTarget()
    {
        return this.attackTarget;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
    {
        this.attackTarget = entitylivingbaseIn;
        Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, this, entitylivingbaseIn);
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class <? extends EntityLivingBase > cls)
    {
        return cls != EntityGhast.class;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    public void eatGrassBonus()
    {
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(AI_FLAGS, Byte.valueOf((byte)0));
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 80;
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        SoundEvent soundevent = this.getAmbientSound();

        if (soundevent != null)
        {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    /**
     * Gets called every tick from main Entity class
     */
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        this.world.profiler.startSection("mobBaseTick");

        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++)
        {
            this.applyEntityAI();
            this.playLivingSound();
        }

        this.world.profiler.endSection();
    }

    protected void playHurtSound(DamageSource source)
    {
        this.applyEntityAI();
        super.playHurtSound(source);
    }

    private void applyEntityAI()
    {
        this.livingSoundTime = -this.getTalkInterval();
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer player)
    {
        if (this.experienceValue > 0)
        {
            int i = this.experienceValue;

            for (int j = 0; j < this.inventoryArmor.size(); ++j)
            {
                if (!((ItemStack)this.inventoryArmor.get(j)).isEmpty() && this.inventoryArmorDropChances[j] <= 1.0F)
                {
                    i += 1 + this.rand.nextInt(3);
                }
            }

            for (int k = 0; k < this.inventoryHands.size(); ++k)
            {
                if (!((ItemStack)this.inventoryHands.get(k)).isEmpty() && this.inventoryHandsDropChances[k] <= 1.0F)
                {
                    i += 1 + this.rand.nextInt(3);
                }
            }

            return i;
        }
        else
        {
            return this.experienceValue;
        }
    }

    /**
     * Spawns an explosion particle around the Entity's location
     */
    public void spawnExplosionParticle()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 20; ++i)
            {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d0 * 10.0D, this.posY + (double)(this.rand.nextFloat() * this.height) - d1 * 10.0D, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * 10.0D, d0, d1, d2);
            }
        }
        else
        {
            this.world.setEntityState(this, (byte)20);
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    public void handleStatusUpdate(byte id)
    {
        if (id == 20)
        {
            this.spawnExplosionParticle();
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (Config.isSmoothWorld() && this.canSkipUpdate())
        {
            this.onUpdateMinimal();
        }
        else
        {
            super.onUpdate();

            if (!this.world.isRemote)
            {
                this.updateLeashedState();

                if (this.ticksExisted % 5 == 0)
                {
                    boolean flag = !(this.getControllingPassenger() instanceof EntityLiving);
                    boolean flag1 = !(this.getRidingEntity() instanceof EntityBoat);
                    this.tasks.setControlFlag(1, flag);
                    this.tasks.setControlFlag(4, flag && flag1);
                    this.tasks.setControlFlag(2, flag);
                }
            }
        }
    }

    protected float updateDistance(float p_110146_1_, float p_110146_2_)
    {
        this.bodyHelper.updateRenderAngles();
        return p_110146_2_;
    }

    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Nullable
    protected Item getDropItem()
    {
        return null;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int i = this.rand.nextInt(3);

            if (lootingModifier > 0)
            {
                i += this.rand.nextInt(lootingModifier + 1);
            }

            for (int j = 0; j < i; ++j)
            {
                this.dropItem(item, 1);
            }
        }
    }

    public static void registerFixesMob(DataFixer fixer, Class<?> name)
    {
        fixer.registerWalker(FixTypes.ENTITY, new ItemStackDataLists(name, new String[] {"ArmorItems", "HandItems"}));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        compound.setBoolean("PersistenceRequired", this.persistenceRequired);
        NBTTagList nbttaglist = new NBTTagList();

        for (ItemStack itemstack : this.inventoryArmor)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            if (!itemstack.isEmpty())
            {
                itemstack.writeToNBT(nbttagcompound);
            }

            nbttaglist.appendTag(nbttagcompound);
        }

        compound.setTag("ArmorItems", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (ItemStack itemstack1 : this.inventoryHands)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            if (!itemstack1.isEmpty())
            {
                itemstack1.writeToNBT(nbttagcompound1);
            }

            nbttaglist1.appendTag(nbttagcompound1);
        }

        compound.setTag("HandItems", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();

        for (float f : this.inventoryArmorDropChances)
        {
            nbttaglist2.appendTag(new NBTTagFloat(f));
        }

        compound.setTag("ArmorDropChances", nbttaglist2);
        NBTTagList nbttaglist3 = new NBTTagList();

        for (float f1 : this.inventoryHandsDropChances)
        {
            nbttaglist3.appendTag(new NBTTagFloat(f1));
        }

        compound.setTag("HandDropChances", nbttaglist3);
        compound.setBoolean("Leashed", this.isLeashed);

        if (this.leashedToEntity != null)
        {
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();

            if (this.leashedToEntity instanceof EntityLivingBase)
            {
                UUID uuid = this.leashedToEntity.getUniqueID();
                nbttagcompound2.setUniqueId("UUID", uuid);
            }
            else if (this.leashedToEntity instanceof EntityHanging)
            {
                BlockPos blockpos = ((EntityHanging)this.leashedToEntity).getHangingPosition();
                nbttagcompound2.setInteger("X", blockpos.getX());
                nbttagcompound2.setInteger("Y", blockpos.getY());
                nbttagcompound2.setInteger("Z", blockpos.getZ());
            }

            compound.setTag("Leash", nbttagcompound2);
        }

        compound.setBoolean("LeftHanded", this.isLeftHanded());

        if (this.deathLootTable != null)
        {
            compound.setString("DeathLootTable", this.deathLootTable.toString());

            if (this.deathLootTableSeed != 0L)
            {
                compound.setLong("DeathLootTableSeed", this.deathLootTableSeed);
            }
        }

        if (this.isAIDisabled())
        {
            compound.setBoolean("NoAI", this.isAIDisabled());
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("CanPickUpLoot", 1))
        {
            this.setCanPickUpLoot(compound.getBoolean("CanPickUpLoot"));
        }

        this.persistenceRequired = compound.getBoolean("PersistenceRequired");

        if (compound.hasKey("ArmorItems", 9))
        {
            NBTTagList nbttaglist = compound.getTagList("ArmorItems", 10);

            for (int i = 0; i < this.inventoryArmor.size(); ++i)
            {
                this.inventoryArmor.set(i, new ItemStack(nbttaglist.getCompoundTagAt(i)));
            }
        }

        if (compound.hasKey("HandItems", 9))
        {
            NBTTagList nbttaglist1 = compound.getTagList("HandItems", 10);

            for (int j = 0; j < this.inventoryHands.size(); ++j)
            {
                this.inventoryHands.set(j, new ItemStack(nbttaglist1.getCompoundTagAt(j)));
            }
        }

        if (compound.hasKey("ArmorDropChances", 9))
        {
            NBTTagList nbttaglist2 = compound.getTagList("ArmorDropChances", 5);

            for (int k = 0; k < nbttaglist2.tagCount(); ++k)
            {
                this.inventoryArmorDropChances[k] = nbttaglist2.getFloatAt(k);
            }
        }

        if (compound.hasKey("HandDropChances", 9))
        {
            NBTTagList nbttaglist3 = compound.getTagList("HandDropChances", 5);

            for (int l = 0; l < nbttaglist3.tagCount(); ++l)
            {
                this.inventoryHandsDropChances[l] = nbttaglist3.getFloatAt(l);
            }
        }

        this.isLeashed = compound.getBoolean("Leashed");

        if (this.isLeashed && compound.hasKey("Leash", 10))
        {
            this.leashNBTTag = compound.getCompoundTag("Leash");
        }

        this.setLeftHanded(compound.getBoolean("LeftHanded"));

        if (compound.hasKey("DeathLootTable", 8))
        {
            this.deathLootTable = new ResourceLocation(compound.getString("DeathLootTable"));
            this.deathLootTableSeed = compound.getLong("DeathLootTableSeed");
        }

        this.setNoAI(compound.getBoolean("NoAI"));
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    /**
     * drops the loot of this entity upon death
     */
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)
    {
        ResourceLocation resourcelocation = this.deathLootTable;

        if (resourcelocation == null)
        {
            resourcelocation = this.getLootTable();
        }

        if (resourcelocation != null)
        {
            LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
            this.deathLootTable = null;
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)this.world)).withLootedEntity(this).withDamageSource(source);

            if (wasRecentlyHit && this.attackingPlayer != null)
            {
                lootcontext$builder = lootcontext$builder.withPlayer(this.attackingPlayer).withLuck(this.attackingPlayer.getLuck());
            }

            for (ItemStack itemstack : loottable.generateLootForPools(this.deathLootTableSeed == 0L ? this.rand : new Random(this.deathLootTableSeed), lootcontext$builder.build()))
            {
                this.entityDropItem(itemstack, 0.0F);
            }

            this.dropEquipment(wasRecentlyHit, lootingModifier);
        }
        else
        {
            super.dropLoot(wasRecentlyHit, lootingModifier, source);
        }
    }

    public void setMoveForward(float amount)
    {
        this.moveForward = amount;
    }

    public void setMoveVertical(float amount)
    {
        this.moveVertical = amount;
    }

    public void setMoveStrafing(float amount)
    {
        this.moveStrafing = amount;
    }

    /**
     * set the movespeed used for the new AI system
     */
    public void setAIMoveSpeed(float speedIn)
    {
        super.setAIMoveSpeed(speedIn);
        this.setMoveForward(speedIn);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.world.profiler.startSection("looting");

        if (!this.world.isRemote && this.canPickUpLoot() && !this.dead && this.world.getGameRules().getBoolean("mobGriefing"))
        {
            for (EntityItem entityitem : this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(1.0D, 0.0D, 1.0D)))
            {
                if (!entityitem.isDead && !entityitem.getItem().isEmpty() && !entityitem.cannotPickup())
                {
                    this.updateEquipmentIfNeeded(entityitem);
                }
            }
        }

        this.world.profiler.endSection();
    }

    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
     * better.
     */
    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        ItemStack itemstack = itemEntity.getItem();
        EntityEquipmentSlot entityequipmentslot = getSlotForItemStack(itemstack);
        boolean flag = true;
        ItemStack itemstack1 = this.getItemStackFromSlot(entityequipmentslot);

        if (!itemstack1.isEmpty())
        {
            if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.HAND)
            {
                if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword))
                {
                    flag = true;
                }
                else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword)
                {
                    ItemSword itemsword = (ItemSword)itemstack.getItem();
                    ItemSword itemsword1 = (ItemSword)itemstack1.getItem();

                    if (itemsword.getDamageVsEntity() == itemsword1.getDamageVsEntity())
                    {
                        flag = itemstack.getMetadata() > itemstack1.getMetadata() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                    }
                    else
                    {
                        flag = itemsword.getDamageVsEntity() > itemsword1.getDamageVsEntity();
                    }
                }
                else if (itemstack.getItem() instanceof ItemBow && itemstack1.getItem() instanceof ItemBow)
                {
                    flag = itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                }
                else
                {
                    flag = false;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor))
            {
                flag = true;
            }
            else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor && !EnchantmentHelper.hasBindingCurse(itemstack1))
            {
                ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();

                if (itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount)
                {
                    flag = itemstack.getMetadata() > itemstack1.getMetadata() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                }
                else
                {
                    flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
                }
            }
            else
            {
                flag = false;
            }
        }

        if (flag && this.canEquipItem(itemstack))
        {
            double d0;

            switch (entityequipmentslot.getSlotType())
            {
                case HAND:
                    d0 = (double)this.inventoryHandsDropChances[entityequipmentslot.getIndex()];
                    break;

                case ARMOR:
                    d0 = (double)this.inventoryArmorDropChances[entityequipmentslot.getIndex()];
                    break;

                default:
                    d0 = 0.0D;
            }

            if (!itemstack1.isEmpty() && (double)(this.rand.nextFloat() - 0.1F) < d0)
            {
                this.entityDropItem(itemstack1, 0.0F);
            }

            this.setItemStackToSlot(entityequipmentslot, itemstack);

            switch (entityequipmentslot.getSlotType())
            {
                case HAND:
                    this.inventoryHandsDropChances[entityequipmentslot.getIndex()] = 2.0F;
                    break;

                case ARMOR:
                    this.inventoryArmorDropChances[entityequipmentslot.getIndex()] = 2.0F;
            }

            this.persistenceRequired = true;
            this.onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.setDead();
        }
    }

    protected boolean canEquipItem(ItemStack stack)
    {
        return true;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return true;
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void despawnEntity()
    {
        Object object = null;
        Object object1 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
        Object object2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);

        if (this.persistenceRequired)
        {
            this.idleTime = 0;
        }
        else if ((this.idleTime & 31) == 31 && (object = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, this)) != object1)
        {
            if (object == object2)
            {
                this.idleTime = 0;
            }
            else
            {
                this.setDead();
            }
        }
        else
        {
            Entity entity = this.world.getClosestPlayerToEntity(this, -1.0D);

            if (entity != null)
            {
                double d0 = entity.posX - this.posX;
                double d1 = entity.posY - this.posY;
                double d2 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.canDespawn() && d3 > 16384.0D)
                {
                    this.setDead();
                }

                if (this.idleTime > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && this.canDespawn())
                {
                    this.setDead();
                }
                else if (d3 < 1024.0D)
                {
                    this.idleTime = 0;
                }
            }
        }
    }

    protected final void updateEntityActionState()
    {
        ++this.idleTime;
        this.world.profiler.startSection("checkDespawn");
        this.despawnEntity();
        this.world.profiler.endSection();
        this.world.profiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.world.profiler.endSection();
        this.world.profiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.world.profiler.endSection();
        this.world.profiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.world.profiler.endSection();
        this.world.profiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.world.profiler.endSection();
        this.world.profiler.startSection("mob tick");
        this.updateAITasks();
        this.world.profiler.endSection();

        if (this.isRiding() && this.getRidingEntity() instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)this.getRidingEntity();
            entityliving.getNavigator().setPath(this.getNavigator().getPath(), 1.5D);
            entityliving.getMoveHelper().read(this.getMoveHelper());
        }

        this.world.profiler.startSection("controls");
        this.world.profiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.world.profiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.world.profiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.world.profiler.endSection();
        this.world.profiler.endSection();
    }

    protected void updateAITasks()
    {
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return 40;
    }

    public int getHorizontalFaceSpeed()
    {
        return 10;
    }

    /**
     * Changes pitch and yaw so that the entity calling the function is facing the entity provided as an argument.
     */
    public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease)
    {
        double d0 = entityIn.posX - this.posX;
        double d1 = entityIn.posZ - this.posZ;
        double d2;

        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            d2 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        }
        else
        {
            d2 = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d2, d3) * (180D / Math.PI)));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f1, maxPitchIncrease);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f, maxYawIncrease);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return iblockstate.canEntitySpawn(this);
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    public boolean isNotColliding()
    {
        return !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    /**
     * Returns render size modifier
     */
    public float getRenderSizeModifier()
    {
        return 1.0F;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    /**
     * The maximum height from where the entity is alowed to jump (used in pathfinder)
     */
    public int getMaxFallHeight()
    {
        if (this.getAttackTarget() == null)
        {
            return 3;
        }
        else
        {
            int i = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
            i = i - (3 - this.world.getDifficulty().getDifficultyId()) * 4;

            if (i < 0)
            {
                i = 0;
            }

            return i + 3;
        }
    }

    public Iterable<ItemStack> getHeldEquipment()
    {
        return this.inventoryHands;
    }

    public Iterable<ItemStack> getArmorInventoryList()
    {
        return this.inventoryArmor;
    }

    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
    {
        switch (slotIn.getSlotType())
        {
            case HAND:
                return this.inventoryHands.get(slotIn.getIndex());

            case ARMOR:
                return this.inventoryArmor.get(slotIn.getIndex());

            default:
                return ItemStack.EMPTY;
        }
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        switch (slotIn.getSlotType())
        {
            case HAND:
                this.inventoryHands.set(slotIn.getIndex(), stack);
                break;

            case ARMOR:
                this.inventoryArmor.set(slotIn.getIndex(), stack);
        }
    }

    /**
     * Drop the equipment for this entity.
     */
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier)
    {
        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
        {
            ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);
            double d0;

            switch (entityequipmentslot.getSlotType())
            {
                case HAND:
                    d0 = (double)this.inventoryHandsDropChances[entityequipmentslot.getIndex()];
                    break;

                case ARMOR:
                    d0 = (double)this.inventoryArmorDropChances[entityequipmentslot.getIndex()];
                    break;

                default:
                    d0 = 0.0D;
            }

            boolean flag = d0 > 1.0D;

            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack) && (wasRecentlyHit || flag) && (double)(this.rand.nextFloat() - (float)lootingModifier * 0.01F) < d0)
            {
                if (!flag && itemstack.isItemStackDamageable())
                {
                    itemstack.setItemDamage(itemstack.getMaxDamage() - this.rand.nextInt(1 + this.rand.nextInt(Math.max(itemstack.getMaxDamage() - 3, 1))));
                }

                this.entityDropItem(itemstack, 0.0F);
            }
        }
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty())
        {
            int i = this.rand.nextInt(2);
            float f = this.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F)
            {
                ++i;
            }

            boolean flag = true;

            for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
            {
                if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
                {
                    ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);

                    if (!flag && this.rand.nextFloat() < f)
                    {
                        break;
                    }

                    flag = false;

                    if (itemstack.isEmpty())
                    {
                        Item item = getArmorByChance(entityequipmentslot, i);

                        if (item != null)
                        {
                            this.setItemStackToSlot(entityequipmentslot, new ItemStack(item));
                        }
                    }
                }
            }
        }
    }

    public static EntityEquipmentSlot getSlotForItemStack(ItemStack stack)
    {
        if (stack.getItem() != Item.getItemFromBlock(Blocks.PUMPKIN) && stack.getItem() != Items.SKULL)
        {
            if (stack.getItem() instanceof ItemArmor)
            {
                return ((ItemArmor)stack.getItem()).armorType;
            }
            else if (stack.getItem() == Items.ELYTRA)
            {
                return EntityEquipmentSlot.CHEST;
            }
            else
            {
                return stack.getItem() == Items.SHIELD ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
            }
        }
        else
        {
            return EntityEquipmentSlot.HEAD;
        }
    }

    @Nullable
    public static Item getArmorByChance(EntityEquipmentSlot slotIn, int chance)
    {
        switch (slotIn)
        {
            case HEAD:
                if (chance == 0)
                {
                    return Items.LEATHER_HELMET;
                }
                else if (chance == 1)
                {
                    return Items.GOLDEN_HELMET;
                }
                else if (chance == 2)
                {
                    return Items.CHAINMAIL_HELMET;
                }
                else if (chance == 3)
                {
                    return Items.IRON_HELMET;
                }
                else if (chance == 4)
                {
                    return Items.DIAMOND_HELMET;
                }

            case CHEST:
                if (chance == 0)
                {
                    return Items.LEATHER_CHESTPLATE;
                }
                else if (chance == 1)
                {
                    return Items.GOLDEN_CHESTPLATE;
                }
                else if (chance == 2)
                {
                    return Items.CHAINMAIL_CHESTPLATE;
                }
                else if (chance == 3)
                {
                    return Items.IRON_CHESTPLATE;
                }
                else if (chance == 4)
                {
                    return Items.DIAMOND_CHESTPLATE;
                }

            case LEGS:
                if (chance == 0)
                {
                    return Items.LEATHER_LEGGINGS;
                }
                else if (chance == 1)
                {
                    return Items.GOLDEN_LEGGINGS;
                }
                else if (chance == 2)
                {
                    return Items.CHAINMAIL_LEGGINGS;
                }
                else if (chance == 3)
                {
                    return Items.IRON_LEGGINGS;
                }
                else if (chance == 4)
                {
                    return Items.DIAMOND_LEGGINGS;
                }

            case FEET:
                if (chance == 0)
                {
                    return Items.LEATHER_BOOTS;
                }
                else if (chance == 1)
                {
                    return Items.GOLDEN_BOOTS;
                }
                else if (chance == 2)
                {
                    return Items.CHAINMAIL_BOOTS;
                }
                else if (chance == 3)
                {
                    return Items.IRON_BOOTS;
                }
                else if (chance == 4)
                {
                    return Items.DIAMOND_BOOTS;
                }

            default:
                return null;
        }
    }

    /**
     * Enchants Entity's current equipments based on given DifficultyInstance
     */
    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        float f = difficulty.getClampedAdditionalDifficulty();

        if (!this.getHeldItemMainhand().isEmpty() && this.rand.nextFloat() < 0.25F * f)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0F + f * (float)this.rand.nextInt(18)), false));
        }

        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
        {
            if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
            {
                ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);

                if (!itemstack.isEmpty() && this.rand.nextFloat() < 0.5F * f)
                {
                    this.setItemStackToSlot(entityequipmentslot, EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)), false));
                }
            }
        }
    }

    @Nullable

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));

        if (this.rand.nextFloat() < 0.05F)
        {
            this.setLeftHanded(true);
        }
        else
        {
            this.setLeftHanded(false);
        }

        return livingdata;
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered()
    {
        return false;
    }

    /**
     * Enable the Entity persistence
     */
    public void enablePersistence()
    {
        this.persistenceRequired = true;
    }

    public void setDropChance(EntityEquipmentSlot slotIn, float chance)
    {
        switch (slotIn.getSlotType())
        {
            case HAND:
                this.inventoryHandsDropChances[slotIn.getIndex()] = chance;
                break;

            case ARMOR:
                this.inventoryArmorDropChances[slotIn.getIndex()] = chance;
        }
    }

    public boolean canPickUpLoot()
    {
        return this.canPickUpLoot;
    }

    public void setCanPickUpLoot(boolean canPickup)
    {
        this.canPickUpLoot = canPickup;
    }

    /**
     * Return the persistenceRequired field (whether this entity is allowed to naturally despawn)
     */
    public boolean isNoDespawnRequired()
    {
        return this.persistenceRequired;
    }

    public final boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.getLeashed() && this.getLeashedToEntity() == player)
        {
            this.clearLeashed(true, !player.capabilities.isCreativeMode);
            return true;
        }
        else
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.LEAD && this.canBeLeashedTo(player))
            {
                this.setLeashedToEntity(player, true);
                itemstack.shrink(1);
                return true;
            }
            else
            {
                return this.processInteract(player, hand) ? true : super.processInitialInteract(player, hand);
            }
        }
    }

    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        return false;
    }

    /**
     * Applies logic related to leashes, for example dragging the entity or breaking the leash.
     */
    protected void updateLeashedState()
    {
        if (this.leashNBTTag != null)
        {
            this.recreateLeash();
        }

        if (this.isLeashed)
        {
            if (!this.isEntityAlive())
            {
                this.clearLeashed(true, true);
            }

            if (this.leashedToEntity == null || this.leashedToEntity.isDead)
            {
                this.clearLeashed(true, true);
            }
        }
    }

    /**
     * Removes the leash from this entity
     */
    public void clearLeashed(boolean sendPacket, boolean dropLead)
    {
        if (this.isLeashed)
        {
            this.isLeashed = false;
            this.leashedToEntity = null;

            if (!this.world.isRemote && dropLead)
            {
                this.dropItem(Items.LEAD, 1);
            }

            if (!this.world.isRemote && sendPacket && this.world instanceof WorldServer)
            {
                ((WorldServer)this.world).getEntityTracker().sendToTracking(this, new SPacketEntityAttach(this, (Entity)null));
            }
        }
    }

    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return !this.getLeashed() && !(this instanceof IMob);
    }

    public boolean getLeashed()
    {
        return this.isLeashed;
    }

    public Entity getLeashedToEntity()
    {
        return this.leashedToEntity;
    }

    /**
     * Sets the entity to be leashed to.
     */
    public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification)
    {
        this.isLeashed = true;
        this.leashedToEntity = entityIn;

        if (!this.world.isRemote && sendAttachNotification && this.world instanceof WorldServer)
        {
            ((WorldServer)this.world).getEntityTracker().sendToTracking(this, new SPacketEntityAttach(this, this.leashedToEntity));
        }

        if (this.isRiding())
        {
            this.dismountRidingEntity();
        }
    }

    public boolean startRiding(Entity entityIn, boolean force)
    {
        boolean flag = super.startRiding(entityIn, force);

        if (flag && this.getLeashed())
        {
            this.clearLeashed(true, true);
        }

        return flag;
    }

    private void recreateLeash()
    {
        if (this.isLeashed && this.leashNBTTag != null)
        {
            if (this.leashNBTTag.hasUniqueId("UUID"))
            {
                UUID uuid = this.leashNBTTag.getUniqueId("UUID");

                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0D)))
                {
                    if (entitylivingbase.getUniqueID().equals(uuid))
                    {
                        this.setLeashedToEntity(entitylivingbase, true);
                        break;
                    }
                }
            }
            else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99))
            {
                BlockPos blockpos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
                EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(this.world, blockpos);

                if (entityleashknot == null)
                {
                    entityleashknot = EntityLeashKnot.createKnot(this.world, blockpos);
                }

                this.setLeashedToEntity(entityleashknot, true);
            }
            else
            {
                this.clearLeashed(false, true);
            }
        }

        this.leashNBTTag = null;
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn)
    {
        EntityEquipmentSlot entityequipmentslot;

        if (inventorySlot == 98)
        {
            entityequipmentslot = EntityEquipmentSlot.MAINHAND;
        }
        else if (inventorySlot == 99)
        {
            entityequipmentslot = EntityEquipmentSlot.OFFHAND;
        }
        else if (inventorySlot == 100 + EntityEquipmentSlot.HEAD.getIndex())
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
        else
        {
            if (inventorySlot != 100 + EntityEquipmentSlot.FEET.getIndex())
            {
                return false;
            }

            entityequipmentslot = EntityEquipmentSlot.FEET;
        }

        if (!itemStackIn.isEmpty() && !isItemStackInSlot(entityequipmentslot, itemStackIn) && entityequipmentslot != EntityEquipmentSlot.HEAD)
        {
            return false;
        }
        else
        {
            this.setItemStackToSlot(entityequipmentslot, itemStackIn);
            return true;
        }
    }

    public boolean canPassengerSteer()
    {
        return this.canBeSteered() && super.canPassengerSteer();
    }

    public static boolean isItemStackInSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        EntityEquipmentSlot entityequipmentslot = getSlotForItemStack(stack);
        return entityequipmentslot == slotIn || entityequipmentslot == EntityEquipmentSlot.MAINHAND && slotIn == EntityEquipmentSlot.OFFHAND || entityequipmentslot == EntityEquipmentSlot.OFFHAND && slotIn == EntityEquipmentSlot.MAINHAND;
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isServerWorld()
    {
        return super.isServerWorld() && !this.isAIDisabled();
    }

    /**
     * Set whether this Entity's AI is disabled
     */
    public void setNoAI(boolean disable)
    {
        byte b0 = ((Byte)this.dataManager.get(AI_FLAGS)).byteValue();
        this.dataManager.set(AI_FLAGS, Byte.valueOf(disable ? (byte)(b0 | 1) : (byte)(b0 & -2)));
    }

    public void setLeftHanded(boolean leftHanded)
    {
        byte b0 = ((Byte)this.dataManager.get(AI_FLAGS)).byteValue();
        this.dataManager.set(AI_FLAGS, Byte.valueOf(leftHanded ? (byte)(b0 | 2) : (byte)(b0 & -3)));
    }

    /**
     * Get whether this Entity's AI is disabled
     */
    public boolean isAIDisabled()
    {
        return (((Byte)this.dataManager.get(AI_FLAGS)).byteValue() & 1) != 0;
    }

    public boolean isLeftHanded()
    {
        return (((Byte)this.dataManager.get(AI_FLAGS)).byteValue() & 2) != 0;
    }

    public EnumHandSide getPrimaryHand()
    {
        return this.isLeftHanded() ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
    }

    private boolean canSkipUpdate()
    {
        if (this.isChild())
        {
            return false;
        }
        else if (this.hurtTime > 0)
        {
            return false;
        }
        else if (this.ticksExisted < 20)
        {
            return false;
        }
        else
        {
            World world = this.getEntityWorld();

            if (world == null)
            {
                return false;
            }
            else if (world.playerEntities.size() != 1)
            {
                return false;
            }
            else
            {
                Entity entity = world.playerEntities.get(0);
                double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0D, 0.0D);
                double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0D, 0.0D);
                double d2 = d0 * d0 + d1 * d1;
                return !this.isInRangeToRenderDist(d2);
            }
        }
    }

    private void onUpdateMinimal()
    {
        ++this.idleTime;

        if (this instanceof EntityMob)
        {
            float f = this.getBrightness();

            if (f > 0.5F)
            {
                this.idleTime += 2;
            }
        }

        this.despawnEntity();
    }

    public Team getTeam()
    {
        UUID uuid = this.getUniqueID();

        if (this.teamUuid != uuid)
        {
            this.teamUuid = uuid;
            this.teamUuidString = uuid.toString();
        }

        return this.world.getScoreboard().getPlayersTeam(this.teamUuidString);
    }

    public static enum SpawnPlacementType
    {
        ON_GROUND,
        IN_AIR,
        IN_WATER;
    }
}
