package net.minecraft.tileentity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public abstract class TileEntityLockableLoot extends TileEntityLockable implements ILootContainer {
   protected ResourceLocation field_184284_m;
   protected long field_184285_n;
   protected String field_190577_o;

   protected boolean func_184283_b(NBTTagCompound p_184283_1_) {
      if (p_184283_1_.func_150297_b("LootTable", 8)) {
         this.field_184284_m = new ResourceLocation(p_184283_1_.func_74779_i("LootTable"));
         this.field_184285_n = p_184283_1_.func_74763_f("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_184282_c(NBTTagCompound p_184282_1_) {
      if (this.field_184284_m != null) {
         p_184282_1_.func_74778_a("LootTable", this.field_184284_m.toString());
         if (this.field_184285_n != 0L) {
            p_184282_1_.func_74772_a("LootTableSeed", this.field_184285_n);
         }

         return true;
      } else {
         return false;
      }
   }

   public void func_184281_d(@Nullable EntityPlayer p_184281_1_) {
      if (this.field_184284_m != null) {
         LootTable loottable = this.field_145850_b.func_184146_ak().func_186521_a(this.field_184284_m);
         this.field_184284_m = null;
         Random random;
         if (this.field_184285_n == 0L) {
            random = new Random();
         } else {
            random = new Random(this.field_184285_n);
         }

         LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.field_145850_b);
         if (p_184281_1_ != null) {
            lootcontext$builder.func_186469_a(p_184281_1_.func_184817_da());
         }

         loottable.func_186460_a(this, random, lootcontext$builder.func_186471_a());
      }

   }

   public ResourceLocation func_184276_b() {
      return this.field_184284_m;
   }

   public void func_189404_a(ResourceLocation p_189404_1_, long p_189404_2_) {
      this.field_184284_m = p_189404_1_;
      this.field_184285_n = p_189404_2_;
   }

   public boolean func_145818_k_() {
      return this.field_190577_o != null && !this.field_190577_o.isEmpty();
   }

   public void func_190575_a(String p_190575_1_) {
      this.field_190577_o = p_190575_1_;
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      this.func_184281_d((EntityPlayer)null);
      return (ItemStack)this.func_190576_q().get(p_70301_1_);
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      this.func_184281_d((EntityPlayer)null);
      ItemStack itemstack = ItemStackHelper.func_188382_a(this.func_190576_q(), p_70298_1_, p_70298_2_);
      if (!itemstack.func_190926_b()) {
         this.func_70296_d();
      }

      return itemstack;
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      this.func_184281_d((EntityPlayer)null);
      return ItemStackHelper.func_188383_a(this.func_190576_q(), p_70304_1_);
   }

   public void func_70299_a(int p_70299_1_, @Nullable ItemStack p_70299_2_) {
      this.func_184281_d((EntityPlayer)null);
      this.func_190576_q().set(p_70299_1_, p_70299_2_);
      if (p_70299_2_.func_190916_E() > this.func_70297_j_()) {
         p_70299_2_.func_190920_e(this.func_70297_j_());
      }

      this.func_70296_d();
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return p_70300_1_.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }

   public int func_174887_a_(int p_174887_1_) {
      return 0;
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
   }

   public int func_174890_g() {
      return 0;
   }

   public void func_174888_l() {
      this.func_184281_d((EntityPlayer)null);
      this.func_190576_q().clear();
   }

   protected abstract NonNullList<ItemStack> func_190576_q();
}
