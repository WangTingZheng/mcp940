package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessor> {
   private Map<ResourceLocation, SoundEventAccessor> field_148764_a;

   protected Map<ResourceLocation, SoundEventAccessor> func_148740_a() {
      this.field_148764_a = Maps.<ResourceLocation, SoundEventAccessor>newHashMap();
      return this.field_148764_a;
   }

   public void func_186803_a(SoundEventAccessor p_186803_1_) {
      this.func_82595_a(p_186803_1_.func_188714_b(), p_186803_1_);
   }

   public void func_148763_c() {
      this.field_148764_a.clear();
   }
}
