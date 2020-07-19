package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.Nullable;

public interface IAttributeInstance {
   IAttribute func_111123_a();

   double func_111125_b();

   void func_111128_a(double var1);

   Collection<AttributeModifier> func_111130_a(int var1);

   Collection<AttributeModifier> func_111122_c();

   boolean func_180374_a(AttributeModifier var1);

   @Nullable
   AttributeModifier func_111127_a(UUID var1);

   void func_111121_a(AttributeModifier var1);

   void func_111124_b(AttributeModifier var1);

   void func_188479_b(UUID var1);

   void func_142049_d();

   double func_111126_e();
}
