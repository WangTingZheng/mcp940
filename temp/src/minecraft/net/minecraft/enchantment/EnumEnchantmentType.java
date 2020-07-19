package net.minecraft.enchantment;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
   ALL {
      public boolean func_77557_a(Item p_77557_1_) {
         for(EnumEnchantmentType enumenchantmenttype : EnumEnchantmentType.values()) {
            if (enumenchantmenttype != EnumEnchantmentType.ALL && enumenchantmenttype.func_77557_a(p_77557_1_)) {
               return true;
            }
         }

         return false;
      }
   },
   ARMOR {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemArmor;
      }
   },
   ARMOR_FEET {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemArmor && ((ItemArmor)p_77557_1_).field_77881_a == EntityEquipmentSlot.FEET;
      }
   },
   ARMOR_LEGS {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemArmor && ((ItemArmor)p_77557_1_).field_77881_a == EntityEquipmentSlot.LEGS;
      }
   },
   ARMOR_CHEST {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemArmor && ((ItemArmor)p_77557_1_).field_77881_a == EntityEquipmentSlot.CHEST;
      }
   },
   ARMOR_HEAD {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemArmor && ((ItemArmor)p_77557_1_).field_77881_a == EntityEquipmentSlot.HEAD;
      }
   },
   WEAPON {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemSword;
      }
   },
   DIGGER {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemTool;
      }
   },
   FISHING_ROD {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemFishingRod;
      }
   },
   BREAKABLE {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_.func_77645_m();
      }
   },
   BOW {
      public boolean func_77557_a(Item p_77557_1_) {
         return p_77557_1_ instanceof ItemBow;
      }
   },
   WEARABLE {
      public boolean func_77557_a(Item p_77557_1_) {
         boolean flag = p_77557_1_ instanceof ItemBlock && ((ItemBlock)p_77557_1_).func_179223_d() instanceof BlockPumpkin;
         return p_77557_1_ instanceof ItemArmor || p_77557_1_ instanceof ItemElytra || p_77557_1_ instanceof ItemSkull || flag;
      }
   };

   private EnumEnchantmentType() {
   }

   public abstract boolean func_77557_a(Item var1);
}
