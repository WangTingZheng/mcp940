package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityList {
   public static final ResourceLocation field_191307_a = new ResourceLocation("lightning_bolt");
   private static final ResourceLocation field_191310_e = new ResourceLocation("player");
   private static final Logger field_151516_b = LogManager.getLogger();
   public static final RegistryNamespaced<ResourceLocation, Class<? extends Entity>> field_191308_b = new RegistryNamespaced<ResourceLocation, Class<? extends Entity>>();
   public static final Map<ResourceLocation, EntityList.EntityEggInfo> field_75627_a = Maps.<ResourceLocation, EntityList.EntityEggInfo>newLinkedHashMap();
   public static final Set<ResourceLocation> field_191309_d = Sets.<ResourceLocation>newHashSet();
   private static final List<String> field_191311_g = Lists.<String>newArrayList();

   @Nullable
   public static ResourceLocation func_191301_a(Entity p_191301_0_) {
      return func_191306_a(p_191301_0_.getClass());
   }

   @Nullable
   public static ResourceLocation func_191306_a(Class<? extends Entity> p_191306_0_) {
      return field_191308_b.func_177774_c(p_191306_0_);
   }

   @Nullable
   public static String func_75621_b(Entity p_75621_0_) {
      int i = field_191308_b.func_148757_b(p_75621_0_.getClass());
      return i == -1 ? null : (String)field_191311_g.get(i);
   }

   @Nullable
   public static String func_191302_a(@Nullable ResourceLocation p_191302_0_) {
      int i = field_191308_b.func_148757_b(field_191308_b.func_82594_a(p_191302_0_));
      return i == -1 ? null : (String)field_191311_g.get(i);
   }

   @Nullable
   public static Class<? extends Entity> func_90035_a(int p_90035_0_) {
      return (Class)field_191308_b.func_148754_a(p_90035_0_);
   }

   @Nullable
   public static Class<? extends Entity> func_192839_a(String p_192839_0_) {
      return (Class)field_191308_b.func_82594_a(new ResourceLocation(p_192839_0_));
   }

   @Nullable
   public static Entity func_191304_a(@Nullable Class<? extends Entity> p_191304_0_, World p_191304_1_) {
      if (p_191304_0_ == null) {
         return null;
      } else {
         try {
            return p_191304_0_.getConstructor(World.class).newInstance(p_191304_1_);
         } catch (Exception exception) {
            exception.printStackTrace();
            return null;
         }
      }
   }

   @Nullable
   public static Entity func_75616_a(int p_75616_0_, World p_75616_1_) {
      return func_191304_a(func_90035_a(p_75616_0_), p_75616_1_);
   }

   @Nullable
   public static Entity func_188429_b(ResourceLocation p_188429_0_, World p_188429_1_) {
      return func_191304_a(field_191308_b.func_82594_a(p_188429_0_), p_188429_1_);
   }

   @Nullable
   public static Entity func_75615_a(NBTTagCompound p_75615_0_, World p_75615_1_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_75615_0_.func_74779_i("id"));
      Entity entity = func_188429_b(resourcelocation, p_75615_1_);
      if (entity == null) {
         field_151516_b.warn("Skipping Entity with id {}", (Object)resourcelocation);
      } else {
         entity.func_70020_e(p_75615_0_);
      }

      return entity;
   }

   public static Set<ResourceLocation> func_180124_b() {
      return field_191309_d;
   }

   public static boolean func_180123_a(Entity p_180123_0_, ResourceLocation p_180123_1_) {
      ResourceLocation resourcelocation = func_191306_a(p_180123_0_.getClass());
      if (resourcelocation != null) {
         return resourcelocation.equals(p_180123_1_);
      } else if (p_180123_0_ instanceof EntityPlayer) {
         return field_191310_e.equals(p_180123_1_);
      } else {
         return p_180123_0_ instanceof EntityLightningBolt ? field_191307_a.equals(p_180123_1_) : false;
      }
   }

   public static boolean func_180125_b(ResourceLocation p_180125_0_) {
      return field_191310_e.equals(p_180125_0_) || func_180124_b().contains(p_180125_0_);
   }

   public static String func_192840_b() {
      StringBuilder stringbuilder = new StringBuilder();

      for(ResourceLocation resourcelocation : func_180124_b()) {
         stringbuilder.append((Object)resourcelocation).append(", ");
      }

      stringbuilder.append((Object)field_191310_e);
      return stringbuilder.toString();
   }

   public static void func_151514_a() {
      func_191303_a(1, "item", EntityItem.class, "Item");
      func_191303_a(2, "xp_orb", EntityXPOrb.class, "XPOrb");
      func_191303_a(3, "area_effect_cloud", EntityAreaEffectCloud.class, "AreaEffectCloud");
      func_191303_a(4, "elder_guardian", EntityElderGuardian.class, "ElderGuardian");
      func_191303_a(5, "wither_skeleton", EntityWitherSkeleton.class, "WitherSkeleton");
      func_191303_a(6, "stray", EntityStray.class, "Stray");
      func_191303_a(7, "egg", EntityEgg.class, "ThrownEgg");
      func_191303_a(8, "leash_knot", EntityLeashKnot.class, "LeashKnot");
      func_191303_a(9, "painting", EntityPainting.class, "Painting");
      func_191303_a(10, "arrow", EntityTippedArrow.class, "Arrow");
      func_191303_a(11, "snowball", EntitySnowball.class, "Snowball");
      func_191303_a(12, "fireball", EntityLargeFireball.class, "Fireball");
      func_191303_a(13, "small_fireball", EntitySmallFireball.class, "SmallFireball");
      func_191303_a(14, "ender_pearl", EntityEnderPearl.class, "ThrownEnderpearl");
      func_191303_a(15, "eye_of_ender_signal", EntityEnderEye.class, "EyeOfEnderSignal");
      func_191303_a(16, "potion", EntityPotion.class, "ThrownPotion");
      func_191303_a(17, "xp_bottle", EntityExpBottle.class, "ThrownExpBottle");
      func_191303_a(18, "item_frame", EntityItemFrame.class, "ItemFrame");
      func_191303_a(19, "wither_skull", EntityWitherSkull.class, "WitherSkull");
      func_191303_a(20, "tnt", EntityTNTPrimed.class, "PrimedTnt");
      func_191303_a(21, "falling_block", EntityFallingBlock.class, "FallingSand");
      func_191303_a(22, "fireworks_rocket", EntityFireworkRocket.class, "FireworksRocketEntity");
      func_191303_a(23, "husk", EntityHusk.class, "Husk");
      func_191303_a(24, "spectral_arrow", EntitySpectralArrow.class, "SpectralArrow");
      func_191303_a(25, "shulker_bullet", EntityShulkerBullet.class, "ShulkerBullet");
      func_191303_a(26, "dragon_fireball", EntityDragonFireball.class, "DragonFireball");
      func_191303_a(27, "zombie_villager", EntityZombieVillager.class, "ZombieVillager");
      func_191303_a(28, "skeleton_horse", EntitySkeletonHorse.class, "SkeletonHorse");
      func_191303_a(29, "zombie_horse", EntityZombieHorse.class, "ZombieHorse");
      func_191303_a(30, "armor_stand", EntityArmorStand.class, "ArmorStand");
      func_191303_a(31, "donkey", EntityDonkey.class, "Donkey");
      func_191303_a(32, "mule", EntityMule.class, "Mule");
      func_191303_a(33, "evocation_fangs", EntityEvokerFangs.class, "EvocationFangs");
      func_191303_a(34, "evocation_illager", EntityEvoker.class, "EvocationIllager");
      func_191303_a(35, "vex", EntityVex.class, "Vex");
      func_191303_a(36, "vindication_illager", EntityVindicator.class, "VindicationIllager");
      func_191303_a(37, "illusion_illager", EntityIllusionIllager.class, "IllusionIllager");
      func_191303_a(40, "commandblock_minecart", EntityMinecartCommandBlock.class, EntityMinecart.Type.COMMAND_BLOCK.func_184954_b());
      func_191303_a(41, "boat", EntityBoat.class, "Boat");
      func_191303_a(42, "minecart", EntityMinecartEmpty.class, EntityMinecart.Type.RIDEABLE.func_184954_b());
      func_191303_a(43, "chest_minecart", EntityMinecartChest.class, EntityMinecart.Type.CHEST.func_184954_b());
      func_191303_a(44, "furnace_minecart", EntityMinecartFurnace.class, EntityMinecart.Type.FURNACE.func_184954_b());
      func_191303_a(45, "tnt_minecart", EntityMinecartTNT.class, EntityMinecart.Type.TNT.func_184954_b());
      func_191303_a(46, "hopper_minecart", EntityMinecartHopper.class, EntityMinecart.Type.HOPPER.func_184954_b());
      func_191303_a(47, "spawner_minecart", EntityMinecartMobSpawner.class, EntityMinecart.Type.SPAWNER.func_184954_b());
      func_191303_a(50, "creeper", EntityCreeper.class, "Creeper");
      func_191303_a(51, "skeleton", EntitySkeleton.class, "Skeleton");
      func_191303_a(52, "spider", EntitySpider.class, "Spider");
      func_191303_a(53, "giant", EntityGiantZombie.class, "Giant");
      func_191303_a(54, "zombie", EntityZombie.class, "Zombie");
      func_191303_a(55, "slime", EntitySlime.class, "Slime");
      func_191303_a(56, "ghast", EntityGhast.class, "Ghast");
      func_191303_a(57, "zombie_pigman", EntityPigZombie.class, "PigZombie");
      func_191303_a(58, "enderman", EntityEnderman.class, "Enderman");
      func_191303_a(59, "cave_spider", EntityCaveSpider.class, "CaveSpider");
      func_191303_a(60, "silverfish", EntitySilverfish.class, "Silverfish");
      func_191303_a(61, "blaze", EntityBlaze.class, "Blaze");
      func_191303_a(62, "magma_cube", EntityMagmaCube.class, "LavaSlime");
      func_191303_a(63, "ender_dragon", EntityDragon.class, "EnderDragon");
      func_191303_a(64, "wither", EntityWither.class, "WitherBoss");
      func_191303_a(65, "bat", EntityBat.class, "Bat");
      func_191303_a(66, "witch", EntityWitch.class, "Witch");
      func_191303_a(67, "endermite", EntityEndermite.class, "Endermite");
      func_191303_a(68, "guardian", EntityGuardian.class, "Guardian");
      func_191303_a(69, "shulker", EntityShulker.class, "Shulker");
      func_191303_a(90, "pig", EntityPig.class, "Pig");
      func_191303_a(91, "sheep", EntitySheep.class, "Sheep");
      func_191303_a(92, "cow", EntityCow.class, "Cow");
      func_191303_a(93, "chicken", EntityChicken.class, "Chicken");
      func_191303_a(94, "squid", EntitySquid.class, "Squid");
      func_191303_a(95, "wolf", EntityWolf.class, "Wolf");
      func_191303_a(96, "mooshroom", EntityMooshroom.class, "MushroomCow");
      func_191303_a(97, "snowman", EntitySnowman.class, "SnowMan");
      func_191303_a(98, "ocelot", EntityOcelot.class, "Ozelot");
      func_191303_a(99, "villager_golem", EntityIronGolem.class, "VillagerGolem");
      func_191303_a(100, "horse", EntityHorse.class, "Horse");
      func_191303_a(101, "rabbit", EntityRabbit.class, "Rabbit");
      func_191303_a(102, "polar_bear", EntityPolarBear.class, "PolarBear");
      func_191303_a(103, "llama", EntityLlama.class, "Llama");
      func_191303_a(104, "llama_spit", EntityLlamaSpit.class, "LlamaSpit");
      func_191303_a(105, "parrot", EntityParrot.class, "Parrot");
      func_191303_a(120, "villager", EntityVillager.class, "Villager");
      func_191303_a(200, "ender_crystal", EntityEnderCrystal.class, "EnderCrystal");
      func_191305_a("bat", 4996656, 986895);
      func_191305_a("blaze", 16167425, 16775294);
      func_191305_a("cave_spider", 803406, 11013646);
      func_191305_a("chicken", 10592673, 16711680);
      func_191305_a("cow", 4470310, 10592673);
      func_191305_a("creeper", 894731, 0);
      func_191305_a("donkey", 5457209, 8811878);
      func_191305_a("elder_guardian", 13552826, 7632531);
      func_191305_a("enderman", 1447446, 0);
      func_191305_a("endermite", 1447446, 7237230);
      func_191305_a("evocation_illager", 9804699, 1973274);
      func_191305_a("ghast", 16382457, 12369084);
      func_191305_a("guardian", 5931634, 15826224);
      func_191305_a("horse", 12623485, 15656192);
      func_191305_a("husk", 7958625, 15125652);
      func_191305_a("llama", 12623485, 10051392);
      func_191305_a("magma_cube", 3407872, 16579584);
      func_191305_a("mooshroom", 10489616, 12040119);
      func_191305_a("mule", 1769984, 5321501);
      func_191305_a("ocelot", 15720061, 5653556);
      func_191305_a("parrot", 894731, 16711680);
      func_191305_a("pig", 15771042, 14377823);
      func_191305_a("polar_bear", 15921906, 9803152);
      func_191305_a("rabbit", 10051392, 7555121);
      func_191305_a("sheep", 15198183, 16758197);
      func_191305_a("shulker", 9725844, 5060690);
      func_191305_a("silverfish", 7237230, 3158064);
      func_191305_a("skeleton", 12698049, 4802889);
      func_191305_a("skeleton_horse", 6842447, 15066584);
      func_191305_a("slime", 5349438, 8306542);
      func_191305_a("spider", 3419431, 11013646);
      func_191305_a("squid", 2243405, 7375001);
      func_191305_a("stray", 6387319, 14543594);
      func_191305_a("vex", 8032420, 15265265);
      func_191305_a("villager", 5651507, 12422002);
      func_191305_a("vindication_illager", 9804699, 2580065);
      func_191305_a("witch", 3407872, 5349438);
      func_191305_a("wither_skeleton", 1315860, 4672845);
      func_191305_a("wolf", 14144467, 13545366);
      func_191305_a("zombie", 44975, 7969893);
      func_191305_a("zombie_horse", 3232308, 9945732);
      func_191305_a("zombie_pigman", 15373203, 5009705);
      func_191305_a("zombie_villager", 5651507, 7969893);
      field_191309_d.add(field_191307_a);
   }

   private static void func_191303_a(int p_191303_0_, String p_191303_1_, Class<? extends Entity> p_191303_2_, String p_191303_3_) {
      try {
         p_191303_2_.getConstructor(World.class);
      } catch (NoSuchMethodException var5) {
         throw new RuntimeException("Invalid class " + p_191303_2_ + " no constructor taking " + World.class.getName());
      }

      if ((p_191303_2_.getModifiers() & 1024) == 1024) {
         throw new RuntimeException("Invalid abstract class " + p_191303_2_);
      } else {
         ResourceLocation resourcelocation = new ResourceLocation(p_191303_1_);
         field_191308_b.func_177775_a(p_191303_0_, resourcelocation, p_191303_2_);
         field_191309_d.add(resourcelocation);

         while(field_191311_g.size() <= p_191303_0_) {
            field_191311_g.add((Object)null);
         }

         field_191311_g.set(p_191303_0_, p_191303_3_);
      }
   }

   protected static EntityList.EntityEggInfo func_191305_a(String p_191305_0_, int p_191305_1_, int p_191305_2_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_191305_0_);
      return field_75627_a.put(resourcelocation, new EntityList.EntityEggInfo(resourcelocation, p_191305_1_, p_191305_2_));
   }

   public static class EntityEggInfo {
      public final ResourceLocation field_75613_a;
      public final int field_75611_b;
      public final int field_75612_c;
      public final StatBase field_151512_d;
      public final StatBase field_151513_e;

      public EntityEggInfo(ResourceLocation p_i47341_1_, int p_i47341_2_, int p_i47341_3_) {
         this.field_75613_a = p_i47341_1_;
         this.field_75611_b = p_i47341_2_;
         this.field_75612_c = p_i47341_3_;
         this.field_151512_d = StatList.func_151182_a(this);
         this.field_151513_e = StatList.func_151176_b(this);
      }
   }
}
