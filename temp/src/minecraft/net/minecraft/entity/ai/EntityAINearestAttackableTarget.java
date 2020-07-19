package net.minecraft.entity.ai;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget {
   protected final Class<T> field_75307_b;
   private final int field_75308_c;
   protected final EntityAINearestAttackableTarget.Sorter field_75306_g;
   protected final Predicate<? super T> field_82643_g;
   protected T field_75309_a;

   public EntityAINearestAttackableTarget(EntityCreature p_i45878_1_, Class<T> p_i45878_2_, boolean p_i45878_3_) {
      this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature p_i45879_1_, Class<T> p_i45879_2_, boolean p_i45879_3_, boolean p_i45879_4_) {
      this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate)null);
   }

   public EntityAINearestAttackableTarget(EntityCreature p_i45880_1_, Class<T> p_i45880_2_, int p_i45880_3_, boolean p_i45880_4_, boolean p_i45880_5_, @Nullable final Predicate<? super T> p_i45880_6_) {
      super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
      this.field_75307_b = p_i45880_2_;
      this.field_75308_c = p_i45880_3_;
      this.field_75306_g = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
      this.func_75248_a(1);
      this.field_82643_g = new Predicate<T>() {
         public boolean apply(@Nullable T p_apply_1_) {
            if (p_apply_1_ == null) {
               return false;
            } else if (p_i45880_6_ != null && !p_i45880_6_.apply(p_apply_1_)) {
               return false;
            } else {
               return !EntitySelectors.field_180132_d.apply(p_apply_1_) ? false : EntityAINearestAttackableTarget.this.func_75296_a(p_apply_1_, false);
            }
         }
      };
   }

   public boolean func_75250_a() {
      if (this.field_75308_c > 0 && this.field_75299_d.func_70681_au().nextInt(this.field_75308_c) != 0) {
         return false;
      } else if (this.field_75307_b != EntityPlayer.class && this.field_75307_b != EntityPlayerMP.class) {
         List<T> list = this.field_75299_d.field_70170_p.<T>func_175647_a(this.field_75307_b, this.func_188511_a(this.func_111175_f()), this.field_82643_g);
         if (list.isEmpty()) {
            return false;
         } else {
            Collections.sort(list, this.field_75306_g);
            this.field_75309_a = list.get(0);
            return true;
         }
      } else {
         this.field_75309_a = this.field_75299_d.field_70170_p.func_184150_a(this.field_75299_d.field_70165_t, this.field_75299_d.field_70163_u + (double)this.field_75299_d.func_70047_e(), this.field_75299_d.field_70161_v, this.func_111175_f(), this.func_111175_f(), new Function<EntityPlayer, Double>() {
            @Nullable
            public Double apply(@Nullable EntityPlayer p_apply_1_) {
               ItemStack itemstack = p_apply_1_.func_184582_a(EntityEquipmentSlot.HEAD);
               if (itemstack.func_77973_b() == Items.field_151144_bL) {
                  int i = itemstack.func_77952_i();
                  boolean flag = EntityAINearestAttackableTarget.this.field_75299_d instanceof EntitySkeleton && i == 0;
                  boolean flag1 = EntityAINearestAttackableTarget.this.field_75299_d instanceof EntityZombie && i == 2;
                  boolean flag2 = EntityAINearestAttackableTarget.this.field_75299_d instanceof EntityCreeper && i == 4;
                  if (flag || flag1 || flag2) {
                     return 0.5D;
                  }
               }

               return 1.0D;
            }
         }, this.field_82643_g);
         return this.field_75309_a != null;
      }
   }

   protected AxisAlignedBB func_188511_a(double p_188511_1_) {
      return this.field_75299_d.func_174813_aQ().func_72314_b(p_188511_1_, 4.0D, p_188511_1_);
   }

   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.field_75309_a);
      super.func_75249_e();
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity field_75459_b;

      public Sorter(Entity p_i1662_1_) {
         this.field_75459_b = p_i1662_1_;
      }

      public int compare(Entity p_compare_1_, Entity p_compare_2_) {
         double d0 = this.field_75459_b.func_70068_e(p_compare_1_);
         double d1 = this.field_75459_b.func_70068_e(p_compare_2_);
         if (d0 < d1) {
            return -1;
         } else {
            return d0 > d1 ? 1 : 0;
         }
      }
   }
}
