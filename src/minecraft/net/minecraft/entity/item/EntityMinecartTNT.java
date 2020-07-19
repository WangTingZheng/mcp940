package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse = -1;

    public EntityMinecartTNT(World worldIn)
    {
        super(worldIn);
    }

    public EntityMinecartTNT(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void registerFixesMinecartTNT(DataFixer fixer)
    {
        EntityMinecart.registerFixesMinecart(fixer, EntityMinecartTNT.class);
    }

    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.TNT;
    }

    public IBlockState getDefaultDisplayTile()
    {
        return Blocks.TNT.getDefaultState();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.minecartTNTFuse > 0)
        {
            --this.minecartTNTFuse;
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
        else if (this.minecartTNTFuse == 0)
        {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }

        if (this.isCollidedHorizontally)
        {
            double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;

            if (d0 >= 0.009999999776482582D)
            {
                this.explodeCart(d0);
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        Entity entity = source.getImmediateSource();

        if (entity instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)entity;

            if (entityarrow.isBurning())
            {
                this.explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    public void killMinecart(DamageSource source)
    {
        double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;

        if (!source.isFireDamage() && !source.isExplosion() && d0 < 0.009999999776482582D)
        {
            super.killMinecart(source);

            if (!source.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops"))
            {
                this.entityDropItem(new ItemStack(Blocks.TNT, 1), 0.0F);
            }
        }
        else
        {
            if (this.minecartTNTFuse < 0)
            {
                this.ignite();
                this.minecartTNTFuse = this.rand.nextInt(20) + this.rand.nextInt(20);
            }
        }
    }

    /**
     * Makes the minecart explode.
     */
    protected void explodeCart(double p_94103_1_)
    {
        if (!this.world.isRemote)
        {
            double d0 = Math.sqrt(p_94103_1_);

            if (d0 > 5.0D)
            {
                d0 = 5.0D;
            }

            this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0D + this.rand.nextDouble() * 1.5D * d0), true);
            this.setDead();
        }
    }

    public void fall(float distance, float damageMultiplier)
    {
        if (distance >= 3.0F)
        {
            float f = distance / 10.0F;
            this.explodeCart((double)(f * f));
        }

        super.fall(distance, damageMultiplier);
    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    public void onActivatorRailPass(int x, int y, int z, boolean receivingPower)
    {
        if (receivingPower && this.minecartTNTFuse < 0)
        {
            this.ignite();
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.ignite();
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Ignites this TNT cart.
     */
    public void ignite()
    {
        this.minecartTNTFuse = 80;

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)10);

            if (!this.isSilent())
            {
                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Gets the remaining fuse time in ticks.
     */
    public int getFuseTicks()
    {
        return this.minecartTNTFuse;
    }

    /**
     * Returns true if the TNT minecart is ignited.
     */
    public boolean isIgnited()
    {
        return this.minecartTNTFuse > -1;
    }

    /**
     * Explosion resistance of a block relative to this entity
     */
    public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn)
    {
        return !this.isIgnited() || !BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()) ? super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn) : 0.0F;
    }

    public boolean canExplosionDestroyBlock(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_)
    {
        return !this.isIgnited() || !BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()) ? super.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_) : false;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("TNTFuse", 99))
        {
            this.minecartTNTFuse = compound.getInteger("TNTFuse");
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("TNTFuse", this.minecartTNTFuse);
    }
}
