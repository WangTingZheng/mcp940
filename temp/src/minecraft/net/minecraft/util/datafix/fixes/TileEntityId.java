package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class TileEntityId implements IFixableData {
   private static final Map<String, String> field_191275_a = Maps.<String, String>newHashMap();

   public int func_188216_a() {
      return 704;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      String s = field_191275_a.get(p_188217_1_.func_74779_i("id"));
      if (s != null) {
         p_188217_1_.func_74778_a("id", s);
      }

      return p_188217_1_;
   }

   static {
      field_191275_a.put("Airportal", "minecraft:end_portal");
      field_191275_a.put("Banner", "minecraft:banner");
      field_191275_a.put("Beacon", "minecraft:beacon");
      field_191275_a.put("Cauldron", "minecraft:brewing_stand");
      field_191275_a.put("Chest", "minecraft:chest");
      field_191275_a.put("Comparator", "minecraft:comparator");
      field_191275_a.put("Control", "minecraft:command_block");
      field_191275_a.put("DLDetector", "minecraft:daylight_detector");
      field_191275_a.put("Dropper", "minecraft:dropper");
      field_191275_a.put("EnchantTable", "minecraft:enchanting_table");
      field_191275_a.put("EndGateway", "minecraft:end_gateway");
      field_191275_a.put("EnderChest", "minecraft:ender_chest");
      field_191275_a.put("FlowerPot", "minecraft:flower_pot");
      field_191275_a.put("Furnace", "minecraft:furnace");
      field_191275_a.put("Hopper", "minecraft:hopper");
      field_191275_a.put("MobSpawner", "minecraft:mob_spawner");
      field_191275_a.put("Music", "minecraft:noteblock");
      field_191275_a.put("Piston", "minecraft:piston");
      field_191275_a.put("RecordPlayer", "minecraft:jukebox");
      field_191275_a.put("Sign", "minecraft:sign");
      field_191275_a.put("Skull", "minecraft:skull");
      field_191275_a.put("Structure", "minecraft:structure_block");
      field_191275_a.put("Trap", "minecraft:dispenser");
   }
}
