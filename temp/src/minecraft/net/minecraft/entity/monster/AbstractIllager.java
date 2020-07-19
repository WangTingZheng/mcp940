package net.minecraft.entity.monster;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class AbstractIllager extends EntityMob {
   protected static final DataParameter<Byte> field_193080_a = EntityDataManager.<Byte>func_187226_a(AbstractIllager.class, DataSerializers.field_187191_a);

   public AbstractIllager(World p_i47509_1_) {
      super(p_i47509_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_193080_a, Byte.valueOf((byte)0));
   }

   protected boolean func_193078_a(int p_193078_1_) {
      int i = ((Byte)this.field_70180_af.func_187225_a(field_193080_a)).byteValue();
      return (i & p_193078_1_) != 0;
   }

   protected void func_193079_a(int p_193079_1_, boolean p_193079_2_) {
      int i = ((Byte)this.field_70180_af.func_187225_a(field_193080_a)).byteValue();
      if (p_193079_2_) {
         i = i | p_193079_1_;
      } else {
         i = i & ~p_193079_1_;
      }

      this.field_70180_af.func_187227_b(field_193080_a, Byte.valueOf((byte)(i & 255)));
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ILLAGER;
   }

   public AbstractIllager.IllagerArmPose func_193077_p() {
      return AbstractIllager.IllagerArmPose.CROSSED;
   }

   public static enum IllagerArmPose {
      CROSSED,
      ATTACKING,
      SPELLCASTING,
      BOW_AND_ARROW;
   }
}
