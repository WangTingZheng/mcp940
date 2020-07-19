package net.minecraft.entity.projectile;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityTippedArrow extends EntityArrow {
   private static final DataParameter<Integer> field_184559_f = EntityDataManager.<Integer>func_187226_a(EntityTippedArrow.class, DataSerializers.field_187192_b);
   private PotionType field_184560_g = PotionTypes.field_185229_a;
   private final Set<PotionEffect> field_184561_h = Sets.<PotionEffect>newHashSet();
   private boolean field_191509_at;

   public EntityTippedArrow(World p_i46756_1_) {
      super(p_i46756_1_);
   }

   public EntityTippedArrow(World p_i46757_1_, double p_i46757_2_, double p_i46757_4_, double p_i46757_6_) {
      super(p_i46757_1_, p_i46757_2_, p_i46757_4_, p_i46757_6_);
   }

   public EntityTippedArrow(World p_i46758_1_, EntityLivingBase p_i46758_2_) {
      super(p_i46758_1_, p_i46758_2_);
   }

   public void func_184555_a(ItemStack p_184555_1_) {
      if (p_184555_1_.func_77973_b() == Items.field_185167_i) {
         this.field_184560_g = PotionUtils.func_185191_c(p_184555_1_);
         Collection<PotionEffect> collection = PotionUtils.func_185190_b(p_184555_1_);
         if (!collection.isEmpty()) {
            for(PotionEffect potioneffect : collection) {
               this.field_184561_h.add(new PotionEffect(potioneffect));
            }
         }

         int i = func_191508_b(p_184555_1_);
         if (i == -1) {
            this.func_190548_o();
         } else {
            this.func_191507_d(i);
         }
      } else if (p_184555_1_.func_77973_b() == Items.field_151032_g) {
         this.field_184560_g = PotionTypes.field_185229_a;
         this.field_184561_h.clear();
         this.field_70180_af.func_187227_b(field_184559_f, Integer.valueOf(-1));
      }

   }

   public static int func_191508_b(ItemStack p_191508_0_) {
      NBTTagCompound nbttagcompound = p_191508_0_.func_77978_p();
      return nbttagcompound != null && nbttagcompound.func_150297_b("CustomPotionColor", 99) ? nbttagcompound.func_74762_e("CustomPotionColor") : -1;
   }

   private void func_190548_o() {
      this.field_191509_at = false;
      this.field_70180_af.func_187227_b(field_184559_f, Integer.valueOf(PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184560_g, this.field_184561_h))));
   }

   public void func_184558_a(PotionEffect p_184558_1_) {
      this.field_184561_h.add(p_184558_1_);
      this.func_184212_Q().func_187227_b(field_184559_f, Integer.valueOf(PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184560_g, this.field_184561_h))));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184559_f, Integer.valueOf(-1));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_70254_i) {
            if (this.field_184552_b % 5 == 0) {
               this.func_184556_b(1);
            }
         } else {
            this.func_184556_b(2);
         }
      } else if (this.field_70254_i && this.field_184552_b != 0 && !this.field_184561_h.isEmpty() && this.field_184552_b >= 600) {
         this.field_70170_p.func_72960_a(this, (byte)0);
         this.field_184560_g = PotionTypes.field_185229_a;
         this.field_184561_h.clear();
         this.field_70180_af.func_187227_b(field_184559_f, Integer.valueOf(-1));
      }

   }

   private void func_184556_b(int p_184556_1_) {
      int i = this.func_184557_n();
      if (i != -1 && p_184556_1_ > 0) {
         double d0 = (double)(i >> 16 & 255) / 255.0D;
         double d1 = (double)(i >> 8 & 255) / 255.0D;
         double d2 = (double)(i >> 0 & 255) / 255.0D;

         for(int j = 0; j < p_184556_1_; ++j) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, d0, d1, d2);
         }

      }
   }

   public int func_184557_n() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184559_f)).intValue();
   }

   private void func_191507_d(int p_191507_1_) {
      this.field_191509_at = true;
      this.field_70180_af.func_187227_b(field_184559_f, Integer.valueOf(p_191507_1_));
   }

   public static void func_189660_b(DataFixer p_189660_0_) {
      EntityArrow.func_189657_a(p_189660_0_, "TippedArrow");
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      if (this.field_184560_g != PotionTypes.field_185229_a && this.field_184560_g != null) {
         p_70014_1_.func_74778_a("Potion", ((ResourceLocation)PotionType.field_185176_a.func_177774_c(this.field_184560_g)).toString());
      }

      if (this.field_191509_at) {
         p_70014_1_.func_74768_a("Color", this.func_184557_n());
      }

      if (!this.field_184561_h.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(PotionEffect potioneffect : this.field_184561_h) {
            nbttaglist.func_74742_a(potioneffect.func_82719_a(new NBTTagCompound()));
         }

         p_70014_1_.func_74782_a("CustomPotionEffects", nbttaglist);
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("Potion", 8)) {
         this.field_184560_g = PotionUtils.func_185187_c(p_70037_1_);
      }

      for(PotionEffect potioneffect : PotionUtils.func_185192_b(p_70037_1_)) {
         this.func_184558_a(potioneffect);
      }

      if (p_70037_1_.func_150297_b("Color", 99)) {
         this.func_191507_d(p_70037_1_.func_74762_e("Color"));
      } else {
         this.func_190548_o();
      }

   }

   protected void func_184548_a(EntityLivingBase p_184548_1_) {
      super.func_184548_a(p_184548_1_);

      for(PotionEffect potioneffect : this.field_184560_g.func_185170_a()) {
         p_184548_1_.func_70690_d(new PotionEffect(potioneffect.func_188419_a(), Math.max(potioneffect.func_76459_b() / 8, 1), potioneffect.func_76458_c(), potioneffect.func_82720_e(), potioneffect.func_188418_e()));
      }

      if (!this.field_184561_h.isEmpty()) {
         for(PotionEffect potioneffect1 : this.field_184561_h) {
            p_184548_1_.func_70690_d(potioneffect1);
         }
      }

   }

   protected ItemStack func_184550_j() {
      if (this.field_184561_h.isEmpty() && this.field_184560_g == PotionTypes.field_185229_a) {
         return new ItemStack(Items.field_151032_g);
      } else {
         ItemStack itemstack = new ItemStack(Items.field_185167_i);
         PotionUtils.func_185188_a(itemstack, this.field_184560_g);
         PotionUtils.func_185184_a(itemstack, this.field_184561_h);
         if (this.field_191509_at) {
            NBTTagCompound nbttagcompound = itemstack.func_77978_p();
            if (nbttagcompound == null) {
               nbttagcompound = new NBTTagCompound();
               itemstack.func_77982_d(nbttagcompound);
            }

            nbttagcompound.func_74768_a("CustomPotionColor", this.func_184557_n());
         }

         return itemstack;
      }
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 0) {
         int i = this.func_184557_n();
         if (i != -1) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < 20; ++j) {
               this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, d0, d1, d2);
            }
         }
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }
}
