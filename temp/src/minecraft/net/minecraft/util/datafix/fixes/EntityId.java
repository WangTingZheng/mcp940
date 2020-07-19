package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class EntityId implements IFixableData {
   private static final Map<String, String> field_191276_a = Maps.<String, String>newHashMap();

   public int func_188216_a() {
      return 704;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      String s = field_191276_a.get(p_188217_1_.func_74779_i("id"));
      if (s != null) {
         p_188217_1_.func_74778_a("id", s);
      }

      return p_188217_1_;
   }

   static {
      field_191276_a.put("AreaEffectCloud", "minecraft:area_effect_cloud");
      field_191276_a.put("ArmorStand", "minecraft:armor_stand");
      field_191276_a.put("Arrow", "minecraft:arrow");
      field_191276_a.put("Bat", "minecraft:bat");
      field_191276_a.put("Blaze", "minecraft:blaze");
      field_191276_a.put("Boat", "minecraft:boat");
      field_191276_a.put("CaveSpider", "minecraft:cave_spider");
      field_191276_a.put("Chicken", "minecraft:chicken");
      field_191276_a.put("Cow", "minecraft:cow");
      field_191276_a.put("Creeper", "minecraft:creeper");
      field_191276_a.put("Donkey", "minecraft:donkey");
      field_191276_a.put("DragonFireball", "minecraft:dragon_fireball");
      field_191276_a.put("ElderGuardian", "minecraft:elder_guardian");
      field_191276_a.put("EnderCrystal", "minecraft:ender_crystal");
      field_191276_a.put("EnderDragon", "minecraft:ender_dragon");
      field_191276_a.put("Enderman", "minecraft:enderman");
      field_191276_a.put("Endermite", "minecraft:endermite");
      field_191276_a.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
      field_191276_a.put("FallingSand", "minecraft:falling_block");
      field_191276_a.put("Fireball", "minecraft:fireball");
      field_191276_a.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
      field_191276_a.put("Ghast", "minecraft:ghast");
      field_191276_a.put("Giant", "minecraft:giant");
      field_191276_a.put("Guardian", "minecraft:guardian");
      field_191276_a.put("Horse", "minecraft:horse");
      field_191276_a.put("Husk", "minecraft:husk");
      field_191276_a.put("Item", "minecraft:item");
      field_191276_a.put("ItemFrame", "minecraft:item_frame");
      field_191276_a.put("LavaSlime", "minecraft:magma_cube");
      field_191276_a.put("LeashKnot", "minecraft:leash_knot");
      field_191276_a.put("MinecartChest", "minecraft:chest_minecart");
      field_191276_a.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
      field_191276_a.put("MinecartFurnace", "minecraft:furnace_minecart");
      field_191276_a.put("MinecartHopper", "minecraft:hopper_minecart");
      field_191276_a.put("MinecartRideable", "minecraft:minecart");
      field_191276_a.put("MinecartSpawner", "minecraft:spawner_minecart");
      field_191276_a.put("MinecartTNT", "minecraft:tnt_minecart");
      field_191276_a.put("Mule", "minecraft:mule");
      field_191276_a.put("MushroomCow", "minecraft:mooshroom");
      field_191276_a.put("Ozelot", "minecraft:ocelot");
      field_191276_a.put("Painting", "minecraft:painting");
      field_191276_a.put("Pig", "minecraft:pig");
      field_191276_a.put("PigZombie", "minecraft:zombie_pigman");
      field_191276_a.put("PolarBear", "minecraft:polar_bear");
      field_191276_a.put("PrimedTnt", "minecraft:tnt");
      field_191276_a.put("Rabbit", "minecraft:rabbit");
      field_191276_a.put("Sheep", "minecraft:sheep");
      field_191276_a.put("Shulker", "minecraft:shulker");
      field_191276_a.put("ShulkerBullet", "minecraft:shulker_bullet");
      field_191276_a.put("Silverfish", "minecraft:silverfish");
      field_191276_a.put("Skeleton", "minecraft:skeleton");
      field_191276_a.put("SkeletonHorse", "minecraft:skeleton_horse");
      field_191276_a.put("Slime", "minecraft:slime");
      field_191276_a.put("SmallFireball", "minecraft:small_fireball");
      field_191276_a.put("SnowMan", "minecraft:snowman");
      field_191276_a.put("Snowball", "minecraft:snowball");
      field_191276_a.put("SpectralArrow", "minecraft:spectral_arrow");
      field_191276_a.put("Spider", "minecraft:spider");
      field_191276_a.put("Squid", "minecraft:squid");
      field_191276_a.put("Stray", "minecraft:stray");
      field_191276_a.put("ThrownEgg", "minecraft:egg");
      field_191276_a.put("ThrownEnderpearl", "minecraft:ender_pearl");
      field_191276_a.put("ThrownExpBottle", "minecraft:xp_bottle");
      field_191276_a.put("ThrownPotion", "minecraft:potion");
      field_191276_a.put("Villager", "minecraft:villager");
      field_191276_a.put("VillagerGolem", "minecraft:villager_golem");
      field_191276_a.put("Witch", "minecraft:witch");
      field_191276_a.put("WitherBoss", "minecraft:wither");
      field_191276_a.put("WitherSkeleton", "minecraft:wither_skeleton");
      field_191276_a.put("WitherSkull", "minecraft:wither_skull");
      field_191276_a.put("Wolf", "minecraft:wolf");
      field_191276_a.put("XPOrb", "minecraft:xp_orb");
      field_191276_a.put("Zombie", "minecraft:zombie");
      field_191276_a.put("ZombieHorse", "minecraft:zombie_horse");
      field_191276_a.put("ZombieVillager", "minecraft:zombie_villager");
   }
}
