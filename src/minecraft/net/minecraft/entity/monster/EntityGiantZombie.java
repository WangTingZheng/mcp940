package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGiantZombie extends EntityMob
{
    public EntityGiantZombie(World worldIn)
    {
        super(worldIn);
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }

    public static void registerFixesGiantZombie(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityGiantZombie.class);
    }

    public float getEyeHeight()
    {
        return 10.440001F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
    }

    public float getBlockPathWeight(BlockPos pos)
    {
        return this.world.getLightBrightness(pos) - 0.5F;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_GIANT;
    }
}
