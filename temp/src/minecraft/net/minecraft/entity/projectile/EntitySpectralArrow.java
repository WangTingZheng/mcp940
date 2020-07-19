package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntitySpectralArrow extends EntityArrow {
   private int field_184562_f = 200;

   public EntitySpectralArrow(World p_i46767_1_) {
      super(p_i46767_1_);
   }

   public EntitySpectralArrow(World p_i46768_1_, EntityLivingBase p_i46768_2_) {
      super(p_i46768_1_, p_i46768_2_);
   }

   public EntitySpectralArrow(World p_i46769_1_, double p_i46769_2_, double p_i46769_4_, double p_i46769_6_) {
      super(p_i46769_1_, p_i46769_2_, p_i46769_4_, p_i46769_6_);
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K && !this.field_70254_i) {
         this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_INSTANT, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D);
      }

   }

   protected ItemStack func_184550_j() {
      return new ItemStack(Items.field_185166_h);
   }

   protected void func_184548_a(EntityLivingBase p_184548_1_) {
      super.func_184548_a(p_184548_1_);
      PotionEffect potioneffect = new PotionEffect(MobEffects.field_188423_x, this.field_184562_f, 0);
      p_184548_1_.func_70690_d(potioneffect);
   }

   public static void func_189659_b(DataFixer p_189659_0_) {
      EntityArrow.func_189657_a(p_189659_0_, "SpectralArrow");
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_74764_b("Duration")) {
         this.field_184562_f = p_70037_1_.func_74762_e("Duration");
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Duration", this.field_184562_f);
   }
}
