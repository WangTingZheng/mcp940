package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySenses {
   EntityLiving field_75526_a;
   List<Entity> field_75524_b = Lists.<Entity>newArrayList();
   List<Entity> field_75525_c = Lists.<Entity>newArrayList();

   public EntitySenses(EntityLiving p_i1672_1_) {
      this.field_75526_a = p_i1672_1_;
   }

   public void func_75523_a() {
      this.field_75524_b.clear();
      this.field_75525_c.clear();
   }

   public boolean func_75522_a(Entity p_75522_1_) {
      if (this.field_75524_b.contains(p_75522_1_)) {
         return true;
      } else if (this.field_75525_c.contains(p_75522_1_)) {
         return false;
      } else {
         this.field_75526_a.field_70170_p.field_72984_F.func_76320_a("canSee");
         boolean flag = this.field_75526_a.func_70685_l(p_75522_1_);
         this.field_75526_a.field_70170_p.field_72984_F.func_76319_b();
         if (flag) {
            this.field_75524_b.add(p_75522_1_);
         } else {
            this.field_75525_c.add(p_75522_1_);
         }

         return flag;
      }
   }
}
