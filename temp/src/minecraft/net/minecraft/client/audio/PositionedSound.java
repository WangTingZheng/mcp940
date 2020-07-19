package net.minecraft.client.audio;

import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class PositionedSound implements ISound {
   protected Sound field_184367_a;
   @Nullable
   private SoundEventAccessor field_184369_l;
   protected SoundCategory field_184368_b;
   protected ResourceLocation field_147664_a;
   protected float field_147662_b;
   protected float field_147663_c;
   protected float field_147660_d;
   protected float field_147661_e;
   protected float field_147658_f;
   protected boolean field_147659_g;
   protected int field_147665_h;
   protected ISound.AttenuationType field_147666_i;

   protected PositionedSound(SoundEvent p_i46533_1_, SoundCategory p_i46533_2_) {
      this(p_i46533_1_.func_187503_a(), p_i46533_2_);
   }

   protected PositionedSound(ResourceLocation p_i46534_1_, SoundCategory p_i46534_2_) {
      this.field_147662_b = 1.0F;
      this.field_147663_c = 1.0F;
      this.field_147666_i = ISound.AttenuationType.LINEAR;
      this.field_147664_a = p_i46534_1_;
      this.field_184368_b = p_i46534_2_;
   }

   public ResourceLocation func_147650_b() {
      return this.field_147664_a;
   }

   public SoundEventAccessor func_184366_a(SoundHandler p_184366_1_) {
      this.field_184369_l = p_184366_1_.func_184398_a(this.field_147664_a);
      if (this.field_184369_l == null) {
         this.field_184367_a = SoundHandler.field_147700_a;
      } else {
         this.field_184367_a = this.field_184369_l.func_148720_g();
      }

      return this.field_184369_l;
   }

   public Sound func_184364_b() {
      return this.field_184367_a;
   }

   public SoundCategory func_184365_d() {
      return this.field_184368_b;
   }

   public boolean func_147657_c() {
      return this.field_147659_g;
   }

   public int func_147652_d() {
      return this.field_147665_h;
   }

   public float func_147653_e() {
      return this.field_147662_b * this.field_184367_a.func_188724_c();
   }

   public float func_147655_f() {
      return this.field_147663_c * this.field_184367_a.func_188725_d();
   }

   public float func_147649_g() {
      return this.field_147660_d;
   }

   public float func_147654_h() {
      return this.field_147661_e;
   }

   public float func_147651_i() {
      return this.field_147658_f;
   }

   public ISound.AttenuationType func_147656_j() {
      return this.field_147666_i;
   }
}
