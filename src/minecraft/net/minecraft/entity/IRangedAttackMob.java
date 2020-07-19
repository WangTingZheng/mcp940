package net.minecraft.entity;

public interface IRangedAttackMob
{
    /**
     * Attack the specified entity using a ranged attack.
     */
    void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor);

    void setSwingingArms(boolean swingingArms);
}
