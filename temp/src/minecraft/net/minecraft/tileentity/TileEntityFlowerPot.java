package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;

public class TileEntityFlowerPot extends TileEntity {
   private Item field_145967_a;
   private int field_145968_i;

   public TileEntityFlowerPot() {
   }

   public TileEntityFlowerPot(Item p_i45442_1_, int p_i45442_2_) {
      this.field_145967_a = p_i45442_1_;
      this.field_145968_i = p_i45442_2_;
   }

   public static void func_189699_a(DataFixer p_189699_0_) {
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      ResourceLocation resourcelocation = Item.field_150901_e.func_177774_c(this.field_145967_a);
      p_189515_1_.func_74778_a("Item", resourcelocation == null ? "" : resourcelocation.toString());
      p_189515_1_.func_74768_a("Data", this.field_145968_i);
      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      if (p_145839_1_.func_150297_b("Item", 8)) {
         this.field_145967_a = Item.func_111206_d(p_145839_1_.func_74779_i("Item"));
      } else {
         this.field_145967_a = Item.func_150899_d(p_145839_1_.func_74762_e("Item"));
      }

      this.field_145968_i = p_145839_1_.func_74762_e("Data");
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 5, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public void func_190614_a(ItemStack p_190614_1_) {
      this.field_145967_a = p_190614_1_.func_77973_b();
      this.field_145968_i = p_190614_1_.func_77960_j();
   }

   public ItemStack func_184403_b() {
      return this.field_145967_a == null ? ItemStack.field_190927_a : new ItemStack(this.field_145967_a, 1, this.field_145968_i);
   }

   @Nullable
   public Item func_145965_a() {
      return this.field_145967_a;
   }

   public int func_145966_b() {
      return this.field_145968_i;
   }
}
