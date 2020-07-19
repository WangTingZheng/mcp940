package net.minecraft.entity.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAIMate extends EntityAIBase {
   private final EntityAnimal field_75390_d;
   private final Class<? extends EntityAnimal> field_190857_e;
   World field_75394_a;
   private EntityAnimal field_75391_e;
   int field_75392_b;
   double field_75393_c;

   public EntityAIMate(EntityAnimal p_i1619_1_, double p_i1619_2_) {
      this(p_i1619_1_, p_i1619_2_, p_i1619_1_.getClass());
   }

   public EntityAIMate(EntityAnimal p_i47306_1_, double p_i47306_2_, Class<? extends EntityAnimal> p_i47306_4_) {
      this.field_75390_d = p_i47306_1_;
      this.field_75394_a = p_i47306_1_.field_70170_p;
      this.field_190857_e = p_i47306_4_;
      this.field_75393_c = p_i47306_2_;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.field_75390_d.func_70880_s()) {
         return false;
      } else {
         this.field_75391_e = this.func_75389_f();
         return this.field_75391_e != null;
      }
   }

   public boolean func_75253_b() {
      return this.field_75391_e.func_70089_S() && this.field_75391_e.func_70880_s() && this.field_75392_b < 60;
   }

   public void func_75251_c() {
      this.field_75391_e = null;
      this.field_75392_b = 0;
   }

   public void func_75246_d() {
      this.field_75390_d.func_70671_ap().func_75651_a(this.field_75391_e, 10.0F, (float)this.field_75390_d.func_70646_bf());
      this.field_75390_d.func_70661_as().func_75497_a(this.field_75391_e, this.field_75393_c);
      ++this.field_75392_b;
      if (this.field_75392_b >= 60 && this.field_75390_d.func_70068_e(this.field_75391_e) < 9.0D) {
         this.func_75388_i();
      }

   }

   private EntityAnimal func_75389_f() {
      List<EntityAnimal> list = this.field_75394_a.<EntityAnimal>func_72872_a(this.field_190857_e, this.field_75390_d.func_174813_aQ().func_186662_g(8.0D));
      double d0 = Double.MAX_VALUE;
      EntityAnimal entityanimal = null;

      for(EntityAnimal entityanimal1 : list) {
         if (this.field_75390_d.func_70878_b(entityanimal1) && this.field_75390_d.func_70068_e(entityanimal1) < d0) {
            entityanimal = entityanimal1;
            d0 = this.field_75390_d.func_70068_e(entityanimal1);
         }
      }

      return entityanimal;
   }

   private void func_75388_i() {
      EntityAgeable entityageable = this.field_75390_d.func_90011_a(this.field_75391_e);
      if (entityageable != null) {
         EntityPlayerMP entityplayermp = this.field_75390_d.func_191993_do();
         if (entityplayermp == null && this.field_75391_e.func_191993_do() != null) {
            entityplayermp = this.field_75391_e.func_191993_do();
         }

         if (entityplayermp != null) {
            entityplayermp.func_71029_a(StatList.field_151186_x);
            CriteriaTriggers.field_192134_n.func_192168_a(entityplayermp, this.field_75390_d, this.field_75391_e, entityageable);
         }

         this.field_75390_d.func_70873_a(6000);
         this.field_75391_e.func_70873_a(6000);
         this.field_75390_d.func_70875_t();
         this.field_75391_e.func_70875_t();
         entityageable.func_70873_a(-24000);
         entityageable.func_70012_b(this.field_75390_d.field_70165_t, this.field_75390_d.field_70163_u, this.field_75390_d.field_70161_v, 0.0F, 0.0F);
         this.field_75394_a.func_72838_d(entityageable);
         Random random = this.field_75390_d.func_70681_au();

         for(int i = 0; i < 7; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = random.nextDouble() * (double)this.field_75390_d.field_70130_N * 2.0D - (double)this.field_75390_d.field_70130_N;
            double d4 = 0.5D + random.nextDouble() * (double)this.field_75390_d.field_70131_O;
            double d5 = random.nextDouble() * (double)this.field_75390_d.field_70130_N * 2.0D - (double)this.field_75390_d.field_70130_N;
            this.field_75394_a.func_175688_a(EnumParticleTypes.HEART, this.field_75390_d.field_70165_t + d3, this.field_75390_d.field_70163_u + d4, this.field_75390_d.field_70161_v + d5, d0, d1, d2);
         }

         if (this.field_75394_a.func_82736_K().func_82766_b("doMobLoot")) {
            this.field_75394_a.func_72838_d(new EntityXPOrb(this.field_75394_a, this.field_75390_d.field_70165_t, this.field_75390_d.field_70163_u, this.field_75390_d.field_70161_v, random.nextInt(7) + 1));
         }

      }
   }
}
