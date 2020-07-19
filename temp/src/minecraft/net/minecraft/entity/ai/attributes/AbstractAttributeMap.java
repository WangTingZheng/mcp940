package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.util.LowerStringMap;

public abstract class AbstractAttributeMap {
   protected final Map<IAttribute, IAttributeInstance> field_111154_a = Maps.<IAttribute, IAttributeInstance>newHashMap();
   protected final Map<String, IAttributeInstance> field_111153_b = new LowerStringMap();
   protected final Multimap<IAttribute, IAttribute> field_180377_c = HashMultimap.<IAttribute, IAttribute>create();

   public IAttributeInstance func_111151_a(IAttribute p_111151_1_) {
      return this.field_111154_a.get(p_111151_1_);
   }

   @Nullable
   public IAttributeInstance func_111152_a(String p_111152_1_) {
      return this.field_111153_b.get(p_111152_1_);
   }

   public IAttributeInstance func_111150_b(IAttribute p_111150_1_) {
      if (this.field_111153_b.containsKey(p_111150_1_.func_111108_a())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         IAttributeInstance iattributeinstance = this.func_180376_c(p_111150_1_);
         this.field_111153_b.put(p_111150_1_.func_111108_a(), iattributeinstance);
         this.field_111154_a.put(p_111150_1_, iattributeinstance);

         for(IAttribute iattribute = p_111150_1_.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d()) {
            this.field_180377_c.put(iattribute, p_111150_1_);
         }

         return iattributeinstance;
      }
   }

   protected abstract IAttributeInstance func_180376_c(IAttribute var1);

   public Collection<IAttributeInstance> func_111146_a() {
      return this.field_111153_b.values();
   }

   public void func_180794_a(IAttributeInstance p_180794_1_) {
   }

   public void func_111148_a(Multimap<String, AttributeModifier> p_111148_1_) {
      for(Entry<String, AttributeModifier> entry : p_111148_1_.entries()) {
         IAttributeInstance iattributeinstance = this.func_111152_a(entry.getKey());
         if (iattributeinstance != null) {
            iattributeinstance.func_111124_b(entry.getValue());
         }
      }

   }

   public void func_111147_b(Multimap<String, AttributeModifier> p_111147_1_) {
      for(Entry<String, AttributeModifier> entry : p_111147_1_.entries()) {
         IAttributeInstance iattributeinstance = this.func_111152_a(entry.getKey());
         if (iattributeinstance != null) {
            iattributeinstance.func_111124_b(entry.getValue());
            iattributeinstance.func_111121_a(entry.getValue());
         }
      }

   }
}
