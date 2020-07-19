package net.minecraft.entity.ai;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks {
   private static final Logger field_151506_a = LogManager.getLogger();
   private final Set<EntityAITasks.EntityAITaskEntry> field_75782_a = Sets.<EntityAITasks.EntityAITaskEntry>newLinkedHashSet();
   private final Set<EntityAITasks.EntityAITaskEntry> field_75780_b = Sets.<EntityAITasks.EntityAITaskEntry>newLinkedHashSet();
   private final Profiler field_75781_c;
   private int field_75778_d;
   private int field_75779_e = 3;
   private int field_188529_g;

   public EntityAITasks(Profiler p_i1628_1_) {
      this.field_75781_c = p_i1628_1_;
   }

   public void func_75776_a(int p_75776_1_, EntityAIBase p_75776_2_) {
      this.field_75782_a.add(new EntityAITasks.EntityAITaskEntry(p_75776_1_, p_75776_2_));
   }

   public void func_85156_a(EntityAIBase p_85156_1_) {
      Iterator<EntityAITasks.EntityAITaskEntry> iterator = this.field_75782_a.iterator();

      while(iterator.hasNext()) {
         EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
         EntityAIBase entityaibase = entityaitasks$entityaitaskentry.field_75733_a;
         if (entityaibase == p_85156_1_) {
            if (entityaitasks$entityaitaskentry.field_188524_c) {
               entityaitasks$entityaitaskentry.field_188524_c = false;
               entityaitasks$entityaitaskentry.field_75733_a.func_75251_c();
               this.field_75780_b.remove(entityaitasks$entityaitaskentry);
            }

            iterator.remove();
            return;
         }
      }

   }

   public void func_75774_a() {
      this.field_75781_c.func_76320_a("goalSetup");
      if (this.field_75778_d++ % this.field_75779_e == 0) {
         for(EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry : this.field_75782_a) {
            if (entityaitasks$entityaitaskentry.field_188524_c) {
               if (!this.func_75775_b(entityaitasks$entityaitaskentry) || !this.func_75773_a(entityaitasks$entityaitaskentry)) {
                  entityaitasks$entityaitaskentry.field_188524_c = false;
                  entityaitasks$entityaitaskentry.field_75733_a.func_75251_c();
                  this.field_75780_b.remove(entityaitasks$entityaitaskentry);
               }
            } else if (this.func_75775_b(entityaitasks$entityaitaskentry) && entityaitasks$entityaitaskentry.field_75733_a.func_75250_a()) {
               entityaitasks$entityaitaskentry.field_188524_c = true;
               entityaitasks$entityaitaskentry.field_75733_a.func_75249_e();
               this.field_75780_b.add(entityaitasks$entityaitaskentry);
            }
         }
      } else {
         Iterator<EntityAITasks.EntityAITaskEntry> iterator = this.field_75780_b.iterator();

         while(iterator.hasNext()) {
            EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry1 = iterator.next();
            if (!this.func_75773_a(entityaitasks$entityaitaskentry1)) {
               entityaitasks$entityaitaskentry1.field_188524_c = false;
               entityaitasks$entityaitaskentry1.field_75733_a.func_75251_c();
               iterator.remove();
            }
         }
      }

      this.field_75781_c.func_76319_b();
      if (!this.field_75780_b.isEmpty()) {
         this.field_75781_c.func_76320_a("goalTick");

         for(EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry2 : this.field_75780_b) {
            entityaitasks$entityaitaskentry2.field_75733_a.func_75246_d();
         }

         this.field_75781_c.func_76319_b();
      }

   }

   private boolean func_75773_a(EntityAITasks.EntityAITaskEntry p_75773_1_) {
      return p_75773_1_.field_75733_a.func_75253_b();
   }

   private boolean func_75775_b(EntityAITasks.EntityAITaskEntry p_75775_1_) {
      if (this.field_75780_b.isEmpty()) {
         return true;
      } else if (this.func_188528_b(p_75775_1_.field_75733_a.func_75247_h())) {
         return false;
      } else {
         for(EntityAITasks.EntityAITaskEntry entityaitasks$entityaitaskentry : this.field_75780_b) {
            if (entityaitasks$entityaitaskentry != p_75775_1_) {
               if (p_75775_1_.field_75731_b >= entityaitasks$entityaitaskentry.field_75731_b) {
                  if (!this.func_75777_a(p_75775_1_, entityaitasks$entityaitaskentry)) {
                     return false;
                  }
               } else if (!entityaitasks$entityaitaskentry.field_75733_a.func_75252_g()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean func_75777_a(EntityAITasks.EntityAITaskEntry p_75777_1_, EntityAITasks.EntityAITaskEntry p_75777_2_) {
      return (p_75777_1_.field_75733_a.func_75247_h() & p_75777_2_.field_75733_a.func_75247_h()) == 0;
   }

   public boolean func_188528_b(int p_188528_1_) {
      return (this.field_188529_g & p_188528_1_) > 0;
   }

   public void func_188526_c(int p_188526_1_) {
      this.field_188529_g |= p_188526_1_;
   }

   public void func_188525_d(int p_188525_1_) {
      this.field_188529_g &= ~p_188525_1_;
   }

   public void func_188527_a(int p_188527_1_, boolean p_188527_2_) {
      if (p_188527_2_) {
         this.func_188525_d(p_188527_1_);
      } else {
         this.func_188526_c(p_188527_1_);
      }

   }

   class EntityAITaskEntry {
      public final EntityAIBase field_75733_a;
      public final int field_75731_b;
      public boolean field_188524_c;

      public EntityAITaskEntry(int p_i1627_2_, EntityAIBase p_i1627_3_) {
         this.field_75731_b = p_i1627_2_;
         this.field_75733_a = p_i1627_3_;
      }

      public boolean equals(@Nullable Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else {
            return p_equals_1_ != null && this.getClass() == p_equals_1_.getClass() ? this.field_75733_a.equals(((EntityAITasks.EntityAITaskEntry)p_equals_1_).field_75733_a) : false;
         }
      }

      public int hashCode() {
         return this.field_75733_a.hashCode();
      }
   }
}
