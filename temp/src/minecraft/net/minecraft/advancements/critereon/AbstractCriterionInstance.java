package net.minecraft.advancements.critereon;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.util.ResourceLocation;

public class AbstractCriterionInstance implements ICriterionInstance {
   private final ResourceLocation field_192245_a;

   public AbstractCriterionInstance(ResourceLocation p_i47465_1_) {
      this.field_192245_a = p_i47465_1_;
   }

   public ResourceLocation func_192244_a() {
      return this.field_192245_a;
   }

   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.field_192245_a + '}';
   }
}
