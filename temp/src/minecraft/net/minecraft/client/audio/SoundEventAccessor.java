package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SoundEventAccessor implements ISoundEventAccessor<Sound> {
   private final List<ISoundEventAccessor<Sound>> field_188716_a = Lists.<ISoundEventAccessor<Sound>>newArrayList();
   private final Random field_148734_b = new Random();
   private final ResourceLocation field_188717_c;
   private final ITextComponent field_188718_d;

   public SoundEventAccessor(ResourceLocation p_i46521_1_, @Nullable String p_i46521_2_) {
      this.field_188717_c = p_i46521_1_;
      this.field_188718_d = p_i46521_2_ == null ? null : new TextComponentTranslation(p_i46521_2_, new Object[0]);
   }

   public int func_148721_a() {
      int i = 0;

      for(ISoundEventAccessor<Sound> isoundeventaccessor : this.field_188716_a) {
         i += isoundeventaccessor.func_148721_a();
      }

      return i;
   }

   public Sound func_148720_g() {
      int i = this.func_148721_a();
      if (!this.field_188716_a.isEmpty() && i != 0) {
         int j = this.field_148734_b.nextInt(i);

         for(ISoundEventAccessor<Sound> isoundeventaccessor : this.field_188716_a) {
            j -= isoundeventaccessor.func_148721_a();
            if (j < 0) {
               return isoundeventaccessor.func_148720_g();
            }
         }

         return SoundHandler.field_147700_a;
      } else {
         return SoundHandler.field_147700_a;
      }
   }

   public void func_188715_a(ISoundEventAccessor<Sound> p_188715_1_) {
      this.field_188716_a.add(p_188715_1_);
   }

   public ResourceLocation func_188714_b() {
      return this.field_188717_c;
   }

   @Nullable
   public ITextComponent func_188712_c() {
      return this.field_188718_d;
   }
}
