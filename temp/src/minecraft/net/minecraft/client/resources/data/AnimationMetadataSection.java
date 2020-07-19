package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
   private final List<AnimationFrame> field_110478_a;
   private final int field_110476_b;
   private final int field_110477_c;
   private final int field_110475_d;
   private final boolean field_177220_e;

   public AnimationMetadataSection(List<AnimationFrame> p_i46088_1_, int p_i46088_2_, int p_i46088_3_, int p_i46088_4_, boolean p_i46088_5_) {
      this.field_110478_a = p_i46088_1_;
      this.field_110476_b = p_i46088_2_;
      this.field_110477_c = p_i46088_3_;
      this.field_110475_d = p_i46088_4_;
      this.field_177220_e = p_i46088_5_;
   }

   public int func_110471_a() {
      return this.field_110477_c;
   }

   public int func_110474_b() {
      return this.field_110476_b;
   }

   public int func_110473_c() {
      return this.field_110478_a.size();
   }

   public int func_110469_d() {
      return this.field_110475_d;
   }

   public boolean func_177219_e() {
      return this.field_177220_e;
   }

   private AnimationFrame func_130072_d(int p_130072_1_) {
      return this.field_110478_a.get(p_130072_1_);
   }

   public int func_110472_a(int p_110472_1_) {
      AnimationFrame animationframe = this.func_130072_d(p_110472_1_);
      return animationframe.func_110495_a() ? this.field_110475_d : animationframe.func_110497_b();
   }

   public boolean func_110470_b(int p_110470_1_) {
      return !((AnimationFrame)this.field_110478_a.get(p_110470_1_)).func_110495_a();
   }

   public int func_110468_c(int p_110468_1_) {
      return ((AnimationFrame)this.field_110478_a.get(p_110468_1_)).func_110496_c();
   }

   public Set<Integer> func_130073_e() {
      Set<Integer> set = Sets.<Integer>newHashSet();

      for(AnimationFrame animationframe : this.field_110478_a) {
         set.add(Integer.valueOf(animationframe.func_110496_c()));
      }

      return set;
   }
}
