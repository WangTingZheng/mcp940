package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

public class EntityAIAvoidEntity<T extends Entity> extends EntityAIBase {
   private final Predicate<Entity> field_179509_a;
   protected EntityCreature field_75380_a;
   private final double field_75378_b;
   private final double field_75379_c;
   protected T field_75376_d;
   private final float field_179508_f;
   private Path field_75374_f;
   private final PathNavigate field_75375_g;
   private final Class<T> field_181064_i;
   private final Predicate<? super T> field_179510_i;

   public EntityAIAvoidEntity(EntityCreature p_i46404_1_, Class<T> p_i46404_2_, float p_i46404_3_, double p_i46404_4_, double p_i46404_6_) {
      this(p_i46404_1_, p_i46404_2_, Predicates.alwaysTrue(), p_i46404_3_, p_i46404_4_, p_i46404_6_);
   }

   public EntityAIAvoidEntity(EntityCreature p_i46405_1_, Class<T> p_i46405_2_, Predicate<? super T> p_i46405_3_, float p_i46405_4_, double p_i46405_5_, double p_i46405_7_) {
      this.field_179509_a = new Predicate<Entity>() {
         public boolean apply(@Nullable Entity p_apply_1_) {
            return p_apply_1_.func_70089_S() && EntityAIAvoidEntity.this.field_75380_a.func_70635_at().func_75522_a(p_apply_1_) && !EntityAIAvoidEntity.this.field_75380_a.func_184191_r(p_apply_1_);
         }
      };
      this.field_75380_a = p_i46405_1_;
      this.field_181064_i = p_i46405_2_;
      this.field_179510_i = p_i46405_3_;
      this.field_179508_f = p_i46405_4_;
      this.field_75378_b = p_i46405_5_;
      this.field_75379_c = p_i46405_7_;
      this.field_75375_g = p_i46405_1_.func_70661_as();
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      List<T> list = this.field_75380_a.field_70170_p.<T>func_175647_a(this.field_181064_i, this.field_75380_a.func_174813_aQ().func_72314_b((double)this.field_179508_f, 3.0D, (double)this.field_179508_f), Predicates.and(EntitySelectors.field_188444_d, this.field_179509_a, this.field_179510_i));
      if (list.isEmpty()) {
         return false;
      } else {
         this.field_75376_d = list.get(0);
         Vec3d vec3d = RandomPositionGenerator.func_75461_b(this.field_75380_a, 16, 7, new Vec3d(this.field_75376_d.field_70165_t, this.field_75376_d.field_70163_u, this.field_75376_d.field_70161_v));
         if (vec3d == null) {
            return false;
         } else if (this.field_75376_d.func_70092_e(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c) < this.field_75376_d.func_70068_e(this.field_75380_a)) {
            return false;
         } else {
            this.field_75374_f = this.field_75375_g.func_75488_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
            return this.field_75374_f != null;
         }
      }
   }

   public boolean func_75253_b() {
      return !this.field_75375_g.func_75500_f();
   }

   public void func_75249_e() {
      this.field_75375_g.func_75484_a(this.field_75374_f, this.field_75378_b);
   }

   public void func_75251_c() {
      this.field_75376_d = null;
   }

   public void func_75246_d() {
      if (this.field_75380_a.func_70068_e(this.field_75376_d) < 49.0D) {
         this.field_75380_a.func_70661_as().func_75489_a(this.field_75379_c);
      } else {
         this.field_75380_a.func_70661_as().func_75489_a(this.field_75378_b);
      }

   }
}
