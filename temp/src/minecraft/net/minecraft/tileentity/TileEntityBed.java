package net.minecraft.tileentity;

import net.minecraft.block.BlockBed;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileEntityBed extends TileEntity {
   private EnumDyeColor field_193053_a = EnumDyeColor.RED;

   public void func_193051_a(ItemStack p_193051_1_) {
      this.func_193052_a(EnumDyeColor.func_176764_b(p_193051_1_.func_77960_j()));
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      if (p_145839_1_.func_74764_b("color")) {
         this.field_193053_a = EnumDyeColor.func_176764_b(p_145839_1_.func_74762_e("color"));
      }

   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74768_a("color", this.field_193053_a.func_176765_a());
      return p_189515_1_;
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 11, this.func_189517_E_());
   }

   public EnumDyeColor func_193048_a() {
      return this.field_193053_a;
   }

   public void func_193052_a(EnumDyeColor p_193052_1_) {
      this.field_193053_a = p_193052_1_;
      this.func_70296_d();
   }

   public boolean func_193050_e() {
      return BlockBed.func_193385_b(this.func_145832_p());
   }

   public ItemStack func_193049_f() {
      return new ItemStack(Items.field_151104_aV, 1, this.field_193053_a.func_176765_a());
   }
}
