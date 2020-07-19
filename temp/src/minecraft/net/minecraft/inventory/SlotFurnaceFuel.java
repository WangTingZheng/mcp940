package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory p_i45795_1_, int p_i45795_2_, int p_i45795_3_, int p_i45795_4_) {
      super(p_i45795_1_, p_i45795_2_, p_i45795_3_, p_i45795_4_);
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return TileEntityFurnace.func_145954_b(p_75214_1_) || func_178173_c_(p_75214_1_);
   }

   public int func_178170_b(ItemStack p_178170_1_) {
      return func_178173_c_(p_178170_1_) ? 1 : super.func_178170_b(p_178170_1_);
   }

   public static boolean func_178173_c_(ItemStack p_178173_0_) {
      return p_178173_0_.func_77973_b() == Items.field_151133_ar;
   }
}
