package net.minecraft.potion;

import com.google.common.collect.ComparisonChain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect implements Comparable<PotionEffect> {
   private static final Logger field_180155_a = LogManager.getLogger();
   private final Potion field_188420_b;
   private int field_76460_b;
   private int field_76461_c;
   private boolean field_82723_d;
   private boolean field_82724_e;
   private boolean field_100013_f;
   private boolean field_188421_h;

   public PotionEffect(Potion p_i46811_1_) {
      this(p_i46811_1_, 0, 0);
   }

   public PotionEffect(Potion p_i46812_1_, int p_i46812_2_) {
      this(p_i46812_1_, p_i46812_2_, 0);
   }

   public PotionEffect(Potion p_i46813_1_, int p_i46813_2_, int p_i46813_3_) {
      this(p_i46813_1_, p_i46813_2_, p_i46813_3_, false, true);
   }

   public PotionEffect(Potion p_i46814_1_, int p_i46814_2_, int p_i46814_3_, boolean p_i46814_4_, boolean p_i46814_5_) {
      this.field_188420_b = p_i46814_1_;
      this.field_76460_b = p_i46814_2_;
      this.field_76461_c = p_i46814_3_;
      this.field_82724_e = p_i46814_4_;
      this.field_188421_h = p_i46814_5_;
   }

   public PotionEffect(PotionEffect p_i1577_1_) {
      this.field_188420_b = p_i1577_1_.field_188420_b;
      this.field_76460_b = p_i1577_1_.field_76460_b;
      this.field_76461_c = p_i1577_1_.field_76461_c;
      this.field_82724_e = p_i1577_1_.field_82724_e;
      this.field_188421_h = p_i1577_1_.field_188421_h;
   }

   public void func_76452_a(PotionEffect p_76452_1_) {
      if (this.field_188420_b != p_76452_1_.field_188420_b) {
         field_180155_a.warn("This method should only be called for matching effects!");
      }

      if (p_76452_1_.field_76461_c > this.field_76461_c) {
         this.field_76461_c = p_76452_1_.field_76461_c;
         this.field_76460_b = p_76452_1_.field_76460_b;
      } else if (p_76452_1_.field_76461_c == this.field_76461_c && this.field_76460_b < p_76452_1_.field_76460_b) {
         this.field_76460_b = p_76452_1_.field_76460_b;
      } else if (!p_76452_1_.field_82724_e && this.field_82724_e) {
         this.field_82724_e = p_76452_1_.field_82724_e;
      }

      this.field_188421_h = p_76452_1_.field_188421_h;
   }

   public Potion func_188419_a() {
      return this.field_188420_b;
   }

   public int func_76459_b() {
      return this.field_76460_b;
   }

   public int func_76458_c() {
      return this.field_76461_c;
   }

   public boolean func_82720_e() {
      return this.field_82724_e;
   }

   public boolean func_188418_e() {
      return this.field_188421_h;
   }

   public boolean func_76455_a(EntityLivingBase p_76455_1_) {
      if (this.field_76460_b > 0) {
         if (this.field_188420_b.func_76397_a(this.field_76460_b, this.field_76461_c)) {
            this.func_76457_b(p_76455_1_);
         }

         this.func_76454_e();
      }

      return this.field_76460_b > 0;
   }

   private int func_76454_e() {
      return --this.field_76460_b;
   }

   public void func_76457_b(EntityLivingBase p_76457_1_) {
      if (this.field_76460_b > 0) {
         this.field_188420_b.func_76394_a(p_76457_1_, this.field_76461_c);
      }

   }

   public String func_76453_d() {
      return this.field_188420_b.func_76393_a();
   }

   public String toString() {
      String s;
      if (this.field_76461_c > 0) {
         s = this.func_76453_d() + " x " + (this.field_76461_c + 1) + ", Duration: " + this.field_76460_b;
      } else {
         s = this.func_76453_d() + ", Duration: " + this.field_76460_b;
      }

      if (this.field_82723_d) {
         s = s + ", Splash: true";
      }

      if (!this.field_188421_h) {
         s = s + ", Particles: false";
      }

      return s;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof PotionEffect)) {
         return false;
      } else {
         PotionEffect potioneffect = (PotionEffect)p_equals_1_;
         return this.field_76460_b == potioneffect.field_76460_b && this.field_76461_c == potioneffect.field_76461_c && this.field_82723_d == potioneffect.field_82723_d && this.field_82724_e == potioneffect.field_82724_e && this.field_188420_b.equals(potioneffect.field_188420_b);
      }
   }

   public int hashCode() {
      int i = this.field_188420_b.hashCode();
      i = 31 * i + this.field_76460_b;
      i = 31 * i + this.field_76461_c;
      i = 31 * i + (this.field_82723_d ? 1 : 0);
      i = 31 * i + (this.field_82724_e ? 1 : 0);
      return i;
   }

   public NBTTagCompound func_82719_a(NBTTagCompound p_82719_1_) {
      p_82719_1_.func_74774_a("Id", (byte)Potion.func_188409_a(this.func_188419_a()));
      p_82719_1_.func_74774_a("Amplifier", (byte)this.func_76458_c());
      p_82719_1_.func_74768_a("Duration", this.func_76459_b());
      p_82719_1_.func_74757_a("Ambient", this.func_82720_e());
      p_82719_1_.func_74757_a("ShowParticles", this.func_188418_e());
      return p_82719_1_;
   }

   public static PotionEffect func_82722_b(NBTTagCompound p_82722_0_) {
      int i = p_82722_0_.func_74771_c("Id");
      Potion potion = Potion.func_188412_a(i);
      if (potion == null) {
         return null;
      } else {
         int j = p_82722_0_.func_74771_c("Amplifier");
         int k = p_82722_0_.func_74762_e("Duration");
         boolean flag = p_82722_0_.func_74767_n("Ambient");
         boolean flag1 = true;
         if (p_82722_0_.func_150297_b("ShowParticles", 1)) {
            flag1 = p_82722_0_.func_74767_n("ShowParticles");
         }

         return new PotionEffect(potion, k, j < 0 ? 0 : j, flag, flag1);
      }
   }

   public void func_100012_b(boolean p_100012_1_) {
      this.field_100013_f = p_100012_1_;
   }

   public boolean func_100011_g() {
      return this.field_100013_f;
   }

   public int compareTo(PotionEffect p_compareTo_1_) {
      int i = 32147;
      return (this.func_76459_b() <= 32147 || p_compareTo_1_.func_76459_b() <= 32147) && (!this.func_82720_e() || !p_compareTo_1_.func_82720_e()) ? ComparisonChain.start().compare(Boolean.valueOf(this.func_82720_e()), Boolean.valueOf(p_compareTo_1_.func_82720_e())).compare(this.func_76459_b(), p_compareTo_1_.func_76459_b()).compare(this.func_188419_a().func_76401_j(), p_compareTo_1_.func_188419_a().func_76401_j()).result() : ComparisonChain.start().compare(Boolean.valueOf(this.func_82720_e()), Boolean.valueOf(p_compareTo_1_.func_82720_e())).compare(this.func_188419_a().func_76401_j(), p_compareTo_1_.func_188419_a().func_76401_j()).result();
   }
}
