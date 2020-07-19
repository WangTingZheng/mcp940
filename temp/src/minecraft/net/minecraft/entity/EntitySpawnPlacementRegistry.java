package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;

public class EntitySpawnPlacementRegistry {
   private static final Map<Class<?>, EntityLiving.SpawnPlacementType> field_180110_a = Maps.<Class<?>, EntityLiving.SpawnPlacementType>newHashMap();

   public static EntityLiving.SpawnPlacementType func_180109_a(Class<?> p_180109_0_) {
      return field_180110_a.get(p_180109_0_);
   }

   static {
      field_180110_a.put(EntityBat.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityChicken.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityCow.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityHorse.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySkeletonHorse.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityZombieHorse.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityDonkey.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityMule.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityMooshroom.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityOcelot.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityPig.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityRabbit.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityParrot.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySheep.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySnowman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
      field_180110_a.put(EntityIronGolem.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityWolf.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityVillager.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityDragon.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityWither.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityBlaze.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityCaveSpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityEnderman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityEndermite.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityGhast.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityGiantZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityGuardian.class, EntityLiving.SpawnPlacementType.IN_WATER);
      field_180110_a.put(EntityMagmaCube.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityPigZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySilverfish.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySkeleton.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityWitherSkeleton.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityStray.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySlime.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntitySpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityWitch.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityZombieVillager.class, EntityLiving.SpawnPlacementType.ON_GROUND);
      field_180110_a.put(EntityHusk.class, EntityLiving.SpawnPlacementType.ON_GROUND);
   }
}
