package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoor extends EntityAIDoorInteract {
   private int field_75359_i;
   private int field_75358_j = -1;

   public EntityAIBreakDoor(EntityLiving p_i1618_1_) {
      super(p_i1618_1_);
   }

   public boolean func_75250_a() {
      if (!super.func_75250_a()) {
         return false;
      } else if (!this.field_75356_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
         return false;
      } else {
         BlockDoor blockdoor = this.field_151504_e;
         return !BlockDoor.func_176514_f(this.field_75356_a.field_70170_p, this.field_179507_b);
      }
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.field_75359_i = 0;
   }

   public boolean func_75253_b() {
      double d0 = this.field_75356_a.func_174818_b(this.field_179507_b);
      boolean flag;
      if (this.field_75359_i <= 240) {
         BlockDoor blockdoor = this.field_151504_e;
         if (!BlockDoor.func_176514_f(this.field_75356_a.field_70170_p, this.field_179507_b) && d0 < 4.0D) {
            flag = true;
            return flag;
         }
      }

      flag = false;
      return flag;
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.field_75356_a.field_70170_p.func_175715_c(this.field_75356_a.func_145782_y(), this.field_179507_b, -1);
   }

   public void func_75246_d() {
      super.func_75246_d();
      if (this.field_75356_a.func_70681_au().nextInt(20) == 0) {
         this.field_75356_a.field_70170_p.func_175718_b(1019, this.field_179507_b, 0);
      }

      ++this.field_75359_i;
      int i = (int)((float)this.field_75359_i / 240.0F * 10.0F);
      if (i != this.field_75358_j) {
         this.field_75356_a.field_70170_p.func_175715_c(this.field_75356_a.func_145782_y(), this.field_179507_b, i);
         this.field_75358_j = i;
      }

      if (this.field_75359_i == 240 && this.field_75356_a.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
         this.field_75356_a.field_70170_p.func_175698_g(this.field_179507_b);
         this.field_75356_a.field_70170_p.func_175718_b(1021, this.field_179507_b, 0);
         this.field_75356_a.field_70170_p.func_175718_b(2001, this.field_179507_b, Block.func_149682_b(this.field_151504_e));
      }

   }
}
