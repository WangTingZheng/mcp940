package net.minecraft.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.world.World;

public abstract class AbstractChestHorse extends AbstractHorse
{
    private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.<Boolean>createKey(AbstractChestHorse.class, DataSerializers.BOOLEAN);

    public AbstractChestHorse(World worldIn)
    {
        super(worldIn);
        this.canGallop = false;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DATA_ID_CHEST, Boolean.valueOf(false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.getModifiedMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.17499999701976776D);
        this.getEntityAttribute(JUMP_STRENGTH).setBaseValue(0.5D);
    }

    public boolean hasChest()
    {
        return ((Boolean)this.dataManager.get(DATA_ID_CHEST)).booleanValue();
    }

    public void setChested(boolean chested)
    {
        this.dataManager.set(DATA_ID_CHEST, Boolean.valueOf(chested));
    }

    protected int getInventorySize()
    {
        return this.hasChest() ? 17 : super.getInventorySize();
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return super.getMountedYOffset() - 0.25D;
    }

    protected SoundEvent getAngrySound()
    {
        super.getAngrySound();
        return SoundEvents.ENTITY_DONKEY_ANGRY;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (this.hasChest())
        {
            if (!this.world.isRemote)
            {
                this.dropItem(Item.getItemFromBlock(Blocks.CHEST), 1);
            }

            this.setChested(false);
        }
    }

    public static void registerFixesAbstractChestHorse(DataFixer fixer, Class<?> entityClass)
    {
        AbstractHorse.registerFixesAbstractHorse(fixer, entityClass);
        fixer.registerWalker(FixTypes.ENTITY, new ItemStackDataLists(entityClass, new String[] {"Items"}));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("ChestedHorse", this.hasChest());

        if (this.hasChest())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 2; i < this.horseChest.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.horseChest.getStackInSlot(i);

                if (!itemstack.isEmpty())
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("Slot", (byte)i);
                    itemstack.writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("Items", nbttaglist);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setChested(compound.getBoolean("ChestedHorse"));

        if (this.hasChest())
        {
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            this.initHorseChest();

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 2 && j < this.horseChest.getSizeInventory())
                {
                    this.horseChest.setInventorySlotContents(j, new ItemStack(nbttagcompound));
                }
            }
        }

        this.updateHorseSlots();
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn)
    {
        if (inventorySlot == 499)
        {
            if (this.hasChest() && itemStackIn.isEmpty())
            {
                this.setChested(false);
                this.initHorseChest();
                return true;
            }

            if (!this.hasChest() && itemStackIn.getItem() == Item.getItemFromBlock(Blocks.CHEST))
            {
                this.setChested(true);
                this.initHorseChest();
                return true;
            }
        }

        return super.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.SPAWN_EGG)
        {
            return super.processInteract(player, hand);
        }
        else
        {
            if (!this.isChild())
            {
                if (this.isTame() && player.isSneaking())
                {
                    this.openGUI(player);
                    return true;
                }

                if (this.isBeingRidden())
                {
                    return super.processInteract(player, hand);
                }
            }

            if (!itemstack.isEmpty())
            {
                boolean flag = this.handleEating(player, itemstack);

                if (!flag && !this.isTame())
                {
                    if (itemstack.interactWithEntity(player, this, hand))
                    {
                        return true;
                    }

                    this.makeMad();
                    return true;
                }

                if (!flag && !this.hasChest() && itemstack.getItem() == Item.getItemFromBlock(Blocks.CHEST))
                {
                    this.setChested(true);
                    this.playChestEquipSound();
                    flag = true;
                    this.initHorseChest();
                }

                if (!flag && !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE)
                {
                    this.openGUI(player);
                    return true;
                }

                if (flag)
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }

                    return true;
                }
            }

            if (this.isChild())
            {
                return super.processInteract(player, hand);
            }
            else if (itemstack.interactWithEntity(player, this, hand))
            {
                return true;
            }
            else
            {
                this.mountTo(player);
                return true;
            }
        }
    }

    protected void playChestEquipSound()
    {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    public int getInventoryColumns()
    {
        return 5;
    }
}
