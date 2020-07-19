package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;

public enum EnumCreatureType {
   MONSTER(IMob.class, 70, Material.field_151579_a, false, false),
   CREATURE(EntityAnimal.class, 10, Material.field_151579_a, true, true),
   AMBIENT(EntityAmbientCreature.class, 15, Material.field_151579_a, true, false),
   WATER_CREATURE(EntityWaterMob.class, 5, Material.field_151586_h, true, false);

   private final Class<? extends IAnimals> field_75605_d;
   private final int field_75606_e;
   private final Material field_75603_f;
   private final boolean field_75604_g;
   private final boolean field_82707_i;

   private EnumCreatureType(Class<? extends IAnimals> p_i1596_3_, int p_i1596_4_, Material p_i1596_5_, boolean p_i1596_6_, boolean p_i1596_7_) {
      this.field_75605_d = p_i1596_3_;
      this.field_75606_e = p_i1596_4_;
      this.field_75603_f = p_i1596_5_;
      this.field_75604_g = p_i1596_6_;
      this.field_82707_i = p_i1596_7_;
   }

   public Class<? extends IAnimals> func_75598_a() {
      return this.field_75605_d;
   }

   public int func_75601_b() {
      return this.field_75606_e;
   }

   public boolean func_75599_d() {
      return this.field_75604_g;
   }

   public boolean func_82705_e() {
      return this.field_82707_i;
   }
}
