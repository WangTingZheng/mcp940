package net.minecraft.client.audio;

import java.util.List;
import javax.annotation.Nullable;

public class SoundList {
   private final List<Sound> field_188702_a;
   private final boolean field_148575_b;
   private final String field_188703_c;

   public SoundList(List<Sound> p_i46525_1_, boolean p_i46525_2_, String p_i46525_3_) {
      this.field_188702_a = p_i46525_1_;
      this.field_148575_b = p_i46525_2_;
      this.field_188703_c = p_i46525_3_;
   }

   public List<Sound> func_188700_a() {
      return this.field_188702_a;
   }

   public boolean func_148574_b() {
      return this.field_148575_b;
   }

   @Nullable
   public String func_188701_c() {
      return this.field_188703_c;
   }
}
