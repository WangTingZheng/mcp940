package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGiantZombie extends EntityMob {
   public EntityGiantZombie(World p_i1736_1_) {
      super(p_i1736_1_);
      this.func_70105_a(this.field_70130_N * 6.0F, this.field_70131_O * 6.0F);
   }

   public static void func_189765_b(DataFixer p_189765_0_) {
      EntityLiving.func_189752_a(p_189765_0_, EntityGiantZombie.class);
   }

   public float func_70047_e() {
      return 10.440001F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
   }

   public float func_180484_a(BlockPos p_180484_1_) {
      return this.field_70170_p.func_175724_o(p_180484_1_) - 0.5F;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186437_s;
   }
}
