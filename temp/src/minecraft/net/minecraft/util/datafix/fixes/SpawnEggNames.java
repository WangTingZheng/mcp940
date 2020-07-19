package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class SpawnEggNames implements IFixableData {
   private static final String[] field_188226_a = new String[256];

   public int func_188216_a() {
      return 105;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("minecraft:spawn_egg".equals(p_188217_1_.func_74779_i("id"))) {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("tag");
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("EntityTag");
         short short1 = p_188217_1_.func_74765_d("Damage");
         if (!nbttagcompound1.func_150297_b("id", 8)) {
            String s = field_188226_a[short1 & 255];
            if (s != null) {
               nbttagcompound1.func_74778_a("id", s);
               nbttagcompound.func_74782_a("EntityTag", nbttagcompound1);
               p_188217_1_.func_74782_a("tag", nbttagcompound);
            }
         }

         if (short1 != 0) {
            p_188217_1_.func_74777_a("Damage", (short)0);
         }
      }

      return p_188217_1_;
   }

   static {
      String[] astring = field_188226_a;
      astring[1] = "Item";
      astring[2] = "XPOrb";
      astring[7] = "ThrownEgg";
      astring[8] = "LeashKnot";
      astring[9] = "Painting";
      astring[10] = "Arrow";
      astring[11] = "Snowball";
      astring[12] = "Fireball";
      astring[13] = "SmallFireball";
      astring[14] = "ThrownEnderpearl";
      astring[15] = "EyeOfEnderSignal";
      astring[16] = "ThrownPotion";
      astring[17] = "ThrownExpBottle";
      astring[18] = "ItemFrame";
      astring[19] = "WitherSkull";
      astring[20] = "PrimedTnt";
      astring[21] = "FallingSand";
      astring[22] = "FireworksRocketEntity";
      astring[23] = "TippedArrow";
      astring[24] = "SpectralArrow";
      astring[25] = "ShulkerBullet";
      astring[26] = "DragonFireball";
      astring[30] = "ArmorStand";
      astring[41] = "Boat";
      astring[42] = "MinecartRideable";
      astring[43] = "MinecartChest";
      astring[44] = "MinecartFurnace";
      astring[45] = "MinecartTNT";
      astring[46] = "MinecartHopper";
      astring[47] = "MinecartSpawner";
      astring[40] = "MinecartCommandBlock";
      astring[48] = "Mob";
      astring[49] = "Monster";
      astring[50] = "Creeper";
      astring[51] = "Skeleton";
      astring[52] = "Spider";
      astring[53] = "Giant";
      astring[54] = "Zombie";
      astring[55] = "Slime";
      astring[56] = "Ghast";
      astring[57] = "PigZombie";
      astring[58] = "Enderman";
      astring[59] = "CaveSpider";
      astring[60] = "Silverfish";
      astring[61] = "Blaze";
      astring[62] = "LavaSlime";
      astring[63] = "EnderDragon";
      astring[64] = "WitherBoss";
      astring[65] = "Bat";
      astring[66] = "Witch";
      astring[67] = "Endermite";
      astring[68] = "Guardian";
      astring[69] = "Shulker";
      astring[90] = "Pig";
      astring[91] = "Sheep";
      astring[92] = "Cow";
      astring[93] = "Chicken";
      astring[94] = "Squid";
      astring[95] = "Wolf";
      astring[96] = "MushroomCow";
      astring[97] = "SnowMan";
      astring[98] = "Ozelot";
      astring[99] = "VillagerGolem";
      astring[100] = "EntityHorse";
      astring[101] = "Rabbit";
      astring[120] = "Villager";
      astring[200] = "EnderCrystal";
   }
}
