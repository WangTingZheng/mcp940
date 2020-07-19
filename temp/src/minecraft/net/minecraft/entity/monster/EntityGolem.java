package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
   public EntityGolem(World p_i1686_1_) {
      super(p_i1686_1_);
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
   }

   @Nullable
   protected SoundEvent func_184639_G() {
      return null;
   }

   @Nullable
   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return null;
   }

   @Nullable
   protected SoundEvent func_184615_bR() {
      return null;
   }

   public int func_70627_aG() {
      return 120;
   }

   protected boolean func_70692_ba() {
      return false;
   }
}
