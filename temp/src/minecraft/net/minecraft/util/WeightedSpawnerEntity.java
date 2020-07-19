package net.minecraft.util;

import net.minecraft.nbt.NBTTagCompound;

public class WeightedSpawnerEntity extends WeightedRandom.Item {
   private final NBTTagCompound field_185279_b;

   public WeightedSpawnerEntity() {
      super(1);
      this.field_185279_b = new NBTTagCompound();
      this.field_185279_b.func_74778_a("id", "minecraft:pig");
   }

   public WeightedSpawnerEntity(NBTTagCompound p_i46715_1_) {
      this(p_i46715_1_.func_150297_b("Weight", 99) ? p_i46715_1_.func_74762_e("Weight") : 1, p_i46715_1_.func_74775_l("Entity"));
   }

   public WeightedSpawnerEntity(int p_i46716_1_, NBTTagCompound p_i46716_2_) {
      super(p_i46716_1_);
      this.field_185279_b = p_i46716_2_;
   }

   public NBTTagCompound func_185278_a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (!this.field_185279_b.func_150297_b("id", 8)) {
         this.field_185279_b.func_74778_a("id", "minecraft:pig");
      } else if (!this.field_185279_b.func_74779_i("id").contains(":")) {
         this.field_185279_b.func_74778_a("id", (new ResourceLocation(this.field_185279_b.func_74779_i("id"))).toString());
      }

      nbttagcompound.func_74782_a("Entity", this.field_185279_b);
      nbttagcompound.func_74768_a("Weight", this.field_76292_a);
      return nbttagcompound;
   }

   public NBTTagCompound func_185277_b() {
      return this.field_185279_b;
   }
}
