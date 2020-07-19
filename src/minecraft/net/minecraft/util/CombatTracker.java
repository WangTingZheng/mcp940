package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CombatTracker
{
    private final List<CombatEntry> combatEntries = Lists.<CombatEntry>newArrayList();

    /** The entity tracked. */
    private final EntityLivingBase fighter;
    private int lastDamageTime;
    private int combatStartTime;
    private int combatEndTime;
    private boolean inCombat;
    private boolean takingDamage;
    private String fallSuffix;

    public CombatTracker(EntityLivingBase fighterIn)
    {
        this.fighter = fighterIn;
    }

    public void calculateFallSuffix()
    {
        this.resetFallSuffix();

        if (this.fighter.isOnLadder())
        {
            Block block = this.fighter.world.getBlockState(new BlockPos(this.fighter.posX, this.fighter.getEntityBoundingBox().minY, this.fighter.posZ)).getBlock();

            if (block == Blocks.LADDER)
            {
                this.fallSuffix = "ladder";
            }
            else if (block == Blocks.VINE)
            {
                this.fallSuffix = "vines";
            }
        }
        else if (this.fighter.isInWater())
        {
            this.fallSuffix = "water";
        }
    }

    /**
     * Adds an entry for the combat tracker
     */
    public void trackDamage(DamageSource damageSrc, float healthIn, float damageAmount)
    {
        this.reset();
        this.calculateFallSuffix();
        CombatEntry combatentry = new CombatEntry(damageSrc, this.fighter.ticksExisted, healthIn, damageAmount, this.fallSuffix, this.fighter.fallDistance);
        this.combatEntries.add(combatentry);
        this.lastDamageTime = this.fighter.ticksExisted;
        this.takingDamage = true;

        if (combatentry.isLivingDamageSrc() && !this.inCombat && this.fighter.isEntityAlive())
        {
            this.inCombat = true;
            this.combatStartTime = this.fighter.ticksExisted;
            this.combatEndTime = this.combatStartTime;
            this.fighter.sendEnterCombat();
        }
    }

    public ITextComponent getDeathMessage()
    {
        if (this.combatEntries.isEmpty())
        {
            return new TextComponentTranslation("death.attack.generic", new Object[] {this.fighter.getDisplayName()});
        }
        else
        {
            CombatEntry combatentry = this.getBestCombatEntry();
            CombatEntry combatentry1 = this.combatEntries.get(this.combatEntries.size() - 1);
            ITextComponent itextcomponent1 = combatentry1.getDamageSrcDisplayName();
            Entity entity = combatentry1.getDamageSrc().getTrueSource();
            ITextComponent itextcomponent;

            if (combatentry != null && combatentry1.getDamageSrc() == DamageSource.FALL)
            {
                ITextComponent itextcomponent2 = combatentry.getDamageSrcDisplayName();

                if (combatentry.getDamageSrc() != DamageSource.FALL && combatentry.getDamageSrc() != DamageSource.OUT_OF_WORLD)
                {
                    if (itextcomponent2 != null && (itextcomponent1 == null || !itextcomponent2.equals(itextcomponent1)))
                    {
                        Entity entity1 = combatentry.getDamageSrc().getTrueSource();
                        ItemStack itemstack1 = entity1 instanceof EntityLivingBase ? ((EntityLivingBase)entity1).getHeldItemMainhand() : ItemStack.EMPTY;

                        if (!itemstack1.isEmpty() && itemstack1.hasDisplayName())
                        {
                            itextcomponent = new TextComponentTranslation("death.fell.assist.item", new Object[] {this.fighter.getDisplayName(), itextcomponent2, itemstack1.getTextComponent()});
                        }
                        else
                        {
                            itextcomponent = new TextComponentTranslation("death.fell.assist", new Object[] {this.fighter.getDisplayName(), itextcomponent2});
                        }
                    }
                    else if (itextcomponent1 != null)
                    {
                        ItemStack itemstack = entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getHeldItemMainhand() : ItemStack.EMPTY;

                        if (!itemstack.isEmpty() && itemstack.hasDisplayName())
                        {
                            itextcomponent = new TextComponentTranslation("death.fell.finish.item", new Object[] {this.fighter.getDisplayName(), itextcomponent1, itemstack.getTextComponent()});
                        }
                        else
                        {
                            itextcomponent = new TextComponentTranslation("death.fell.finish", new Object[] {this.fighter.getDisplayName(), itextcomponent1});
                        }
                    }
                    else
                    {
                        itextcomponent = new TextComponentTranslation("death.fell.killer", new Object[] {this.fighter.getDisplayName()});
                    }
                }
                else
                {
                    itextcomponent = new TextComponentTranslation("death.fell.accident." + this.getFallSuffix(combatentry), new Object[] {this.fighter.getDisplayName()});
                }
            }
            else
            {
                itextcomponent = combatentry1.getDamageSrc().getDeathMessage(this.fighter);
            }

            return itextcomponent;
        }
    }

    @Nullable
    public EntityLivingBase getBestAttacker()
    {
        EntityLivingBase entitylivingbase = null;
        EntityPlayer entityplayer = null;
        float f = 0.0F;
        float f1 = 0.0F;

        for (CombatEntry combatentry : this.combatEntries)
        {
            if (combatentry.getDamageSrc().getTrueSource() instanceof EntityPlayer && (entityplayer == null || combatentry.getDamage() > f1))
            {
                f1 = combatentry.getDamage();
                entityplayer = (EntityPlayer)combatentry.getDamageSrc().getTrueSource();
            }

            if (combatentry.getDamageSrc().getTrueSource() instanceof EntityLivingBase && (entitylivingbase == null || combatentry.getDamage() > f))
            {
                f = combatentry.getDamage();
                entitylivingbase = (EntityLivingBase)combatentry.getDamageSrc().getTrueSource();
            }
        }

        if (entityplayer != null && f1 >= f / 3.0F)
        {
            return entityplayer;
        }
        else
        {
            return entitylivingbase;
        }
    }

    @Nullable
    private CombatEntry getBestCombatEntry()
    {
        CombatEntry combatentry = null;
        CombatEntry combatentry1 = null;
        float f = 0.0F;
        float f1 = 0.0F;

        for (int i = 0; i < this.combatEntries.size(); ++i)
        {
            CombatEntry combatentry2 = this.combatEntries.get(i);
            CombatEntry combatentry3 = i > 0 ? (CombatEntry)this.combatEntries.get(i - 1) : null;

            if ((combatentry2.getDamageSrc() == DamageSource.FALL || combatentry2.getDamageSrc() == DamageSource.OUT_OF_WORLD) && combatentry2.getDamageAmount() > 0.0F && (combatentry == null || combatentry2.getDamageAmount() > f1))
            {
                if (i > 0)
                {
                    combatentry = combatentry3;
                }
                else
                {
                    combatentry = combatentry2;
                }

                f1 = combatentry2.getDamageAmount();
            }

            if (combatentry2.getFallSuffix() != null && (combatentry1 == null || combatentry2.getDamage() > f))
            {
                combatentry1 = combatentry2;
                f = combatentry2.getDamage();
            }
        }

        if (f1 > 5.0F && combatentry != null)
        {
            return combatentry;
        }
        else if (f > 5.0F && combatentry1 != null)
        {
            return combatentry1;
        }
        else
        {
            return null;
        }
    }

    private String getFallSuffix(CombatEntry entry)
    {
        return entry.getFallSuffix() == null ? "generic" : entry.getFallSuffix();
    }

    public int getCombatDuration()
    {
        return this.inCombat ? this.fighter.ticksExisted - this.combatStartTime : this.combatEndTime - this.combatStartTime;
    }

    private void resetFallSuffix()
    {
        this.fallSuffix = null;
    }

    /**
     * Resets this trackers list of combat entries
     */
    public void reset()
    {
        int i = this.inCombat ? 300 : 100;

        if (this.takingDamage && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.lastDamageTime > i))
        {
            boolean flag = this.inCombat;
            this.takingDamage = false;
            this.inCombat = false;
            this.combatEndTime = this.fighter.ticksExisted;

            if (flag)
            {
                this.fighter.sendEndCombat();
            }

            this.combatEntries.clear();
        }
    }

    /**
     * Returns EntityLivingBase assigned for this CombatTracker
     */
    public EntityLivingBase getFighter()
    {
        return this.fighter;
    }
}
