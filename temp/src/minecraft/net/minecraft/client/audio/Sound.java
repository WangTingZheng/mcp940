package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class Sound implements ISoundEventAccessor<Sound> {
   private final ResourceLocation field_188726_a;
   private final float field_188727_b;
   private final float field_188728_c;
   private final int field_188729_d;
   private final Sound.Type field_188730_e;
   private final boolean field_188731_f;

   public Sound(String p_i46526_1_, float p_i46526_2_, float p_i46526_3_, int p_i46526_4_, Sound.Type p_i46526_5_, boolean p_i46526_6_) {
      this.field_188726_a = new ResourceLocation(p_i46526_1_);
      this.field_188727_b = p_i46526_2_;
      this.field_188728_c = p_i46526_3_;
      this.field_188729_d = p_i46526_4_;
      this.field_188730_e = p_i46526_5_;
      this.field_188731_f = p_i46526_6_;
   }

   public ResourceLocation func_188719_a() {
      return this.field_188726_a;
   }

   public ResourceLocation func_188721_b() {
      return new ResourceLocation(this.field_188726_a.func_110624_b(), "sounds/" + this.field_188726_a.func_110623_a() + ".ogg");
   }

   public float func_188724_c() {
      return this.field_188727_b;
   }

   public float func_188725_d() {
      return this.field_188728_c;
   }

   public int func_148721_a() {
      return this.field_188729_d;
   }

   public Sound func_148720_g() {
      return this;
   }

   public Sound.Type func_188722_g() {
      return this.field_188730_e;
   }

   public boolean func_188723_h() {
      return this.field_188731_f;
   }

   public static enum Type {
      FILE("file"),
      SOUND_EVENT("event");

      private final String field_188708_c;

      private Type(String p_i46631_3_) {
         this.field_188708_c = p_i46631_3_;
      }

      public static Sound.Type func_188704_a(String p_188704_0_) {
         for(Sound.Type sound$type : values()) {
            if (sound$type.field_188708_c.equals(p_188704_0_)) {
               return sound$type;
            }
         }

         return null;
      }
   }
}
