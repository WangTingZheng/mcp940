package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.LowerStringMap;

public class AttributeMap extends AbstractAttributeMap {
   private final Set<IAttributeInstance> field_111162_d = Sets.<IAttributeInstance>newHashSet();
   protected final Map<String, IAttributeInstance> field_111163_c = new LowerStringMap();

   public ModifiableAttributeInstance func_111151_a(IAttribute p_111151_1_) {
      return (ModifiableAttributeInstance)super.func_111151_a(p_111151_1_);
   }

   public ModifiableAttributeInstance func_111152_a(String p_111152_1_) {
      IAttributeInstance iattributeinstance = super.func_111152_a(p_111152_1_);
      if (iattributeinstance == null) {
         iattributeinstance = this.field_111163_c.get(p_111152_1_);
      }

      return (ModifiableAttributeInstance)iattributeinstance;
   }

   public IAttributeInstance func_111150_b(IAttribute p_111150_1_) {
      IAttributeInstance iattributeinstance = super.func_111150_b(p_111150_1_);
      if (p_111150_1_ instanceof RangedAttribute && ((RangedAttribute)p_111150_1_).func_111116_f() != null) {
         this.field_111163_c.put(((RangedAttribute)p_111150_1_).func_111116_f(), iattributeinstance);
      }

      return iattributeinstance;
   }

   protected IAttributeInstance func_180376_c(IAttribute p_180376_1_) {
      return new ModifiableAttributeInstance(this, p_180376_1_);
   }

   public void func_180794_a(IAttributeInstance p_180794_1_) {
      if (p_180794_1_.func_111123_a().func_111111_c()) {
         this.field_111162_d.add(p_180794_1_);
      }

      for(IAttribute iattribute : this.field_180377_c.get(p_180794_1_.func_111123_a())) {
         ModifiableAttributeInstance modifiableattributeinstance = this.func_111151_a(iattribute);
         if (modifiableattributeinstance != null) {
            modifiableattributeinstance.func_111131_f();
         }
      }

   }

   public Set<IAttributeInstance> func_111161_b() {
      return this.field_111162_d;
   }

   public Collection<IAttributeInstance> func_111160_c() {
      Set<IAttributeInstance> set = Sets.<IAttributeInstance>newHashSet();

      for(IAttributeInstance iattributeinstance : this.func_111146_a()) {
         if (iattributeinstance.func_111123_a().func_111111_c()) {
            set.add(iattributeinstance);
         }
      }

      return set;
   }
}
