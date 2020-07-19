package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class CooldownTracker {
   private final Map<Item, CooldownTracker.Cooldown> field_185147_a = Maps.<Item, CooldownTracker.Cooldown>newHashMap();
   private int field_185148_b;

   public boolean func_185141_a(Item p_185141_1_) {
      return this.func_185143_a(p_185141_1_, 0.0F) > 0.0F;
   }

   public float func_185143_a(Item p_185143_1_, float p_185143_2_) {
      CooldownTracker.Cooldown cooldowntracker$cooldown = this.field_185147_a.get(p_185143_1_);
      if (cooldowntracker$cooldown != null) {
         float f = (float)(cooldowntracker$cooldown.field_185138_b - cooldowntracker$cooldown.field_185137_a);
         float f1 = (float)cooldowntracker$cooldown.field_185138_b - ((float)this.field_185148_b + p_185143_2_);
         return MathHelper.func_76131_a(f1 / f, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void func_185144_a() {
      ++this.field_185148_b;
      if (!this.field_185147_a.isEmpty()) {
         Iterator<Entry<Item, CooldownTracker.Cooldown>> iterator = this.field_185147_a.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry<Item, CooldownTracker.Cooldown> entry = (Entry)iterator.next();
            if ((entry.getValue()).field_185138_b <= this.field_185148_b) {
               iterator.remove();
               this.func_185146_c(entry.getKey());
            }
         }
      }

   }

   public void func_185145_a(Item p_185145_1_, int p_185145_2_) {
      this.field_185147_a.put(p_185145_1_, new CooldownTracker.Cooldown(this.field_185148_b, this.field_185148_b + p_185145_2_));
      this.func_185140_b(p_185145_1_, p_185145_2_);
   }

   public void func_185142_b(Item p_185142_1_) {
      this.field_185147_a.remove(p_185142_1_);
      this.func_185146_c(p_185142_1_);
   }

   protected void func_185140_b(Item p_185140_1_, int p_185140_2_) {
   }

   protected void func_185146_c(Item p_185146_1_) {
   }

   class Cooldown {
      final int field_185137_a;
      final int field_185138_b;

      private Cooldown(int p_i47037_2_, int p_i47037_3_) {
         this.field_185137_a = p_i47037_2_;
         this.field_185138_b = p_i47037_3_;
      }
   }
}
