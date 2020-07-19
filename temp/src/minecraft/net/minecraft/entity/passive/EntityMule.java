package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMule extends AbstractChestHorse {
   public EntityMule(World p_i47296_1_) {
      super(p_i47296_1_);
   }

   public static void func_190700_b(DataFixer p_190700_0_) {
      AbstractChestHorse.func_190694_b(p_190700_0_, EntityMule.class);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191191_I;
   }

   protected SoundEvent func_184639_G() {
      super.func_184639_G();
      return SoundEvents.field_187786_du;
   }

   protected SoundEvent func_184615_bR() {
      super.func_184615_bR();
      return SoundEvents.field_187788_dv;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      super.func_184601_bQ(p_184601_1_);
      return SoundEvents.field_187790_dw;
   }

   protected void func_190697_dk() {
      this.func_184185_a(SoundEvents.field_191259_dX, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
   }
}
