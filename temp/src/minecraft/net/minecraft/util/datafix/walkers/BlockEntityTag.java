package net.minecraft.util.datafix.walkers;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockEntityTag implements IDataWalker {
   private static final Logger field_188268_a = LogManager.getLogger();
   private static final Map<String, String> field_190892_b = Maps.<String, String>newHashMap();
   private static final Map<String, String> field_188269_b = Maps.<String, String>newHashMap();

   @Nullable
   private static String func_188267_a(int p_188267_0_, String p_188267_1_) {
      return p_188267_0_ < 515 ? (String)field_190892_b.get((new ResourceLocation(p_188267_1_)).toString()) : (String)field_188269_b.get((new ResourceLocation(p_188267_1_)).toString());
   }

   public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
      if (!p_188266_2_.func_150297_b("tag", 10)) {
         return p_188266_2_;
      } else {
         NBTTagCompound nbttagcompound = p_188266_2_.func_74775_l("tag");
         if (nbttagcompound.func_150297_b("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("BlockEntityTag");
            String s = p_188266_2_.func_74779_i("id");
            String s1 = func_188267_a(p_188266_3_, s);
            boolean flag;
            if (s1 == null) {
               field_188268_a.warn("Unable to resolve BlockEntity for ItemInstance: {}", (Object)s);
               flag = false;
            } else {
               flag = !nbttagcompound1.func_74764_b("id");
               nbttagcompound1.func_74778_a("id", s1);
            }

            p_188266_1_.func_188251_a(FixTypes.BLOCK_ENTITY, nbttagcompound1, p_188266_3_);
            if (flag) {
               nbttagcompound1.func_82580_o("id");
            }
         }

         return p_188266_2_;
      }
   }

   static {
      Map<String, String> map = field_190892_b;
      map.put("minecraft:furnace", "Furnace");
      map.put("minecraft:lit_furnace", "Furnace");
      map.put("minecraft:chest", "Chest");
      map.put("minecraft:trapped_chest", "Chest");
      map.put("minecraft:ender_chest", "EnderChest");
      map.put("minecraft:jukebox", "RecordPlayer");
      map.put("minecraft:dispenser", "Trap");
      map.put("minecraft:dropper", "Dropper");
      map.put("minecraft:sign", "Sign");
      map.put("minecraft:mob_spawner", "MobSpawner");
      map.put("minecraft:noteblock", "Music");
      map.put("minecraft:brewing_stand", "Cauldron");
      map.put("minecraft:enhanting_table", "EnchantTable");
      map.put("minecraft:command_block", "CommandBlock");
      map.put("minecraft:beacon", "Beacon");
      map.put("minecraft:skull", "Skull");
      map.put("minecraft:daylight_detector", "DLDetector");
      map.put("minecraft:hopper", "Hopper");
      map.put("minecraft:banner", "Banner");
      map.put("minecraft:flower_pot", "FlowerPot");
      map.put("minecraft:repeating_command_block", "CommandBlock");
      map.put("minecraft:chain_command_block", "CommandBlock");
      map.put("minecraft:standing_sign", "Sign");
      map.put("minecraft:wall_sign", "Sign");
      map.put("minecraft:piston_head", "Piston");
      map.put("minecraft:daylight_detector_inverted", "DLDetector");
      map.put("minecraft:unpowered_comparator", "Comparator");
      map.put("minecraft:powered_comparator", "Comparator");
      map.put("minecraft:wall_banner", "Banner");
      map.put("minecraft:standing_banner", "Banner");
      map.put("minecraft:structure_block", "Structure");
      map.put("minecraft:end_portal", "Airportal");
      map.put("minecraft:end_gateway", "EndGateway");
      map.put("minecraft:shield", "Shield");
      map = field_188269_b;
      map.put("minecraft:furnace", "minecraft:furnace");
      map.put("minecraft:lit_furnace", "minecraft:furnace");
      map.put("minecraft:chest", "minecraft:chest");
      map.put("minecraft:trapped_chest", "minecraft:chest");
      map.put("minecraft:ender_chest", "minecraft:enderchest");
      map.put("minecraft:jukebox", "minecraft:jukebox");
      map.put("minecraft:dispenser", "minecraft:dispenser");
      map.put("minecraft:dropper", "minecraft:dropper");
      map.put("minecraft:sign", "minecraft:sign");
      map.put("minecraft:mob_spawner", "minecraft:mob_spawner");
      map.put("minecraft:noteblock", "minecraft:noteblock");
      map.put("minecraft:brewing_stand", "minecraft:brewing_stand");
      map.put("minecraft:enhanting_table", "minecraft:enchanting_table");
      map.put("minecraft:command_block", "minecraft:command_block");
      map.put("minecraft:beacon", "minecraft:beacon");
      map.put("minecraft:skull", "minecraft:skull");
      map.put("minecraft:daylight_detector", "minecraft:daylight_detector");
      map.put("minecraft:hopper", "minecraft:hopper");
      map.put("minecraft:banner", "minecraft:banner");
      map.put("minecraft:flower_pot", "minecraft:flower_pot");
      map.put("minecraft:repeating_command_block", "minecraft:command_block");
      map.put("minecraft:chain_command_block", "minecraft:command_block");
      map.put("minecraft:shulker_box", "minecraft:shulker_box");
      map.put("minecraft:white_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:green_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:red_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:black_shulker_box", "minecraft:shulker_box");
      map.put("minecraft:bed", "minecraft:bed");
      map.put("minecraft:standing_sign", "minecraft:sign");
      map.put("minecraft:wall_sign", "minecraft:sign");
      map.put("minecraft:piston_head", "minecraft:piston");
      map.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
      map.put("minecraft:unpowered_comparator", "minecraft:comparator");
      map.put("minecraft:powered_comparator", "minecraft:comparator");
      map.put("minecraft:wall_banner", "minecraft:banner");
      map.put("minecraft:standing_banner", "minecraft:banner");
      map.put("minecraft:structure_block", "minecraft:structure_block");
      map.put("minecraft:end_portal", "minecraft:end_portal");
      map.put("minecraft:end_gateway", "minecraft:end_gateway");
      map.put("minecraft:shield", "minecraft:shield");
   }
}
