package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class ModifiableAttributeInstance implements IAttributeInstance {
   private final AbstractAttributeMap field_111138_a;
   private final IAttribute field_111136_b;
   private final Map<Integer, Set<AttributeModifier>> field_111137_c = Maps.<Integer, Set<AttributeModifier>>newHashMap();
   private final Map<String, Set<AttributeModifier>> field_111134_d = Maps.<String, Set<AttributeModifier>>newHashMap();
   private final Map<UUID, AttributeModifier> field_111135_e = Maps.<UUID, AttributeModifier>newHashMap();
   private double field_111132_f;
   private boolean field_111133_g = true;
   private double field_111139_h;

   public ModifiableAttributeInstance(AbstractAttributeMap p_i1608_1_, IAttribute p_i1608_2_) {
      this.field_111138_a = p_i1608_1_;
      this.field_111136_b = p_i1608_2_;
      this.field_111132_f = p_i1608_2_.func_111110_b();

      for(int i = 0; i < 3; ++i) {
         this.field_111137_c.put(Integer.valueOf(i), Sets.newHashSet());
      }

   }

   public IAttribute func_111123_a() {
      return this.field_111136_b;
   }

   public double func_111125_b() {
      return this.field_111132_f;
   }

   public void func_111128_a(double p_111128_1_) {
      if (p_111128_1_ != this.func_111125_b()) {
         this.field_111132_f = p_111128_1_;
         this.func_111131_f();
      }
   }

   public Collection<AttributeModifier> func_111130_a(int p_111130_1_) {
      return (Collection)this.field_111137_c.get(Integer.valueOf(p_111130_1_));
   }

   public Collection<AttributeModifier> func_111122_c() {
      Set<AttributeModifier> set = Sets.<AttributeModifier>newHashSet();

      for(int i = 0; i < 3; ++i) {
         set.addAll(this.func_111130_a(i));
      }

      return set;
   }

   @Nullable
   public AttributeModifier func_111127_a(UUID p_111127_1_) {
      return this.field_111135_e.get(p_111127_1_);
   }

   public boolean func_180374_a(AttributeModifier p_180374_1_) {
      return this.field_111135_e.get(p_180374_1_.func_111167_a()) != null;
   }

   public void func_111121_a(AttributeModifier p_111121_1_) {
      if (this.func_111127_a(p_111121_1_.func_111167_a()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         Set<AttributeModifier> set = (Set)this.field_111134_d.get(p_111121_1_.func_111166_b());
         if (set == null) {
            set = Sets.<AttributeModifier>newHashSet();
            this.field_111134_d.put(p_111121_1_.func_111166_b(), set);
         }

         (this.field_111137_c.get(Integer.valueOf(p_111121_1_.func_111169_c()))).add(p_111121_1_);
         set.add(p_111121_1_);
         this.field_111135_e.put(p_111121_1_.func_111167_a(), p_111121_1_);
         this.func_111131_f();
      }
   }

   protected void func_111131_f() {
      this.field_111133_g = true;
      this.field_111138_a.func_180794_a(this);
   }

   public void func_111124_b(AttributeModifier p_111124_1_) {
      for(int i = 0; i < 3; ++i) {
         Set<AttributeModifier> set = (Set)this.field_111137_c.get(Integer.valueOf(i));
         set.remove(p_111124_1_);
      }

      Set<AttributeModifier> set1 = (Set)this.field_111134_d.get(p_111124_1_.func_111166_b());
      if (set1 != null) {
         set1.remove(p_111124_1_);
         if (set1.isEmpty()) {
            this.field_111134_d.remove(p_111124_1_.func_111166_b());
         }
      }

      this.field_111135_e.remove(p_111124_1_.func_111167_a());
      this.func_111131_f();
   }

   public void func_188479_b(UUID p_188479_1_) {
      AttributeModifier attributemodifier = this.func_111127_a(p_188479_1_);
      if (attributemodifier != null) {
         this.func_111124_b(attributemodifier);
      }

   }

   public void func_142049_d() {
      Collection<AttributeModifier> collection = this.func_111122_c();
      if (collection != null) {
         for(AttributeModifier attributemodifier : Lists.newArrayList(collection)) {
            this.func_111124_b(attributemodifier);
         }

      }
   }

   public double func_111126_e() {
      if (this.field_111133_g) {
         this.field_111139_h = this.func_111129_g();
         this.field_111133_g = false;
      }

      return this.field_111139_h;
   }

   private double func_111129_g() {
      double d0 = this.func_111125_b();

      for(AttributeModifier attributemodifier : this.func_180375_b(0)) {
         d0 += attributemodifier.func_111164_d();
      }

      double d1 = d0;

      for(AttributeModifier attributemodifier1 : this.func_180375_b(1)) {
         d1 += d0 * attributemodifier1.func_111164_d();
      }

      for(AttributeModifier attributemodifier2 : this.func_180375_b(2)) {
         d1 *= 1.0D + attributemodifier2.func_111164_d();
      }

      return this.field_111136_b.func_111109_a(d1);
   }

   private Collection<AttributeModifier> func_180375_b(int p_180375_1_) {
      Set<AttributeModifier> set = Sets.newHashSet(this.func_111130_a(p_180375_1_));

      for(IAttribute iattribute = this.field_111136_b.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d()) {
         IAttributeInstance iattributeinstance = this.field_111138_a.func_111151_a(iattribute);
         if (iattributeinstance != null) {
            set.addAll(iattributeinstance.func_111130_a(p_180375_1_));
         }
      }

      return set;
   }
}
