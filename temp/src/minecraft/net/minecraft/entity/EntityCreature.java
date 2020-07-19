package net.minecraft.entity;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityCreature extends EntityLiving {
   public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
   public static final AttributeModifier field_110181_i = (new AttributeModifier(field_110179_h, "Fleeing speed bonus", 2.0D, 2)).func_111168_a(false);
   private BlockPos field_70775_bC = BlockPos.field_177992_a;
   private float field_70772_bD = -1.0F;
   private final float field_184661_bw = PathNodeType.WATER.func_186289_a();

   public EntityCreature(World p_i1602_1_) {
      super(p_i1602_1_);
   }

   public float func_180484_a(BlockPos p_180484_1_) {
      return 0.0F;
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() && this.func_180484_a(new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v)) >= 0.0F;
   }

   public boolean func_70781_l() {
      return !this.field_70699_by.func_75500_f();
   }

   public boolean func_110173_bK() {
      return this.func_180485_d(new BlockPos(this));
   }

   public boolean func_180485_d(BlockPos p_180485_1_) {
      if (this.field_70772_bD == -1.0F) {
         return true;
      } else {
         return this.field_70775_bC.func_177951_i(p_180485_1_) < (double)(this.field_70772_bD * this.field_70772_bD);
      }
   }

   public void func_175449_a(BlockPos p_175449_1_, int p_175449_2_) {
      this.field_70775_bC = p_175449_1_;
      this.field_70772_bD = (float)p_175449_2_;
   }

   public BlockPos func_180486_cf() {
      return this.field_70775_bC;
   }

   public float func_110174_bM() {
      return this.field_70772_bD;
   }

   public void func_110177_bN() {
      this.field_70772_bD = -1.0F;
   }

   public boolean func_110175_bO() {
      return this.field_70772_bD != -1.0F;
   }

   protected void func_110159_bB() {
      super.func_110159_bB();
      if (this.func_110167_bD() && this.func_110166_bE() != null && this.func_110166_bE().field_70170_p == this.field_70170_p) {
         Entity entity = this.func_110166_bE();
         this.func_175449_a(new BlockPos((int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v), 5);
         float f = this.func_70032_d(entity);
         if (this instanceof EntityTameable && ((EntityTameable)this).func_70906_o()) {
            if (f > 10.0F) {
               this.func_110160_i(true, true);
            }

            return;
         }

         this.func_142017_o(f);
         if (f > 10.0F) {
            this.func_110160_i(true, true);
            this.field_70714_bg.func_188526_c(1);
         } else if (f > 6.0F) {
            double d0 = (entity.field_70165_t - this.field_70165_t) / (double)f;
            double d1 = (entity.field_70163_u - this.field_70163_u) / (double)f;
            double d2 = (entity.field_70161_v - this.field_70161_v) / (double)f;
            this.field_70159_w += d0 * Math.abs(d0) * 0.4D;
            this.field_70181_x += d1 * Math.abs(d1) * 0.4D;
            this.field_70179_y += d2 * Math.abs(d2) * 0.4D;
         } else {
            this.field_70714_bg.func_188525_d(1);
            float f1 = 2.0F;
            Vec3d vec3d = (new Vec3d(entity.field_70165_t - this.field_70165_t, entity.field_70163_u - this.field_70163_u, entity.field_70161_v - this.field_70161_v)).func_72432_b().func_186678_a((double)Math.max(f - 2.0F, 0.0F));
            this.func_70661_as().func_75492_a(this.field_70165_t + vec3d.field_72450_a, this.field_70163_u + vec3d.field_72448_b, this.field_70161_v + vec3d.field_72449_c, this.func_190634_dg());
         }
      }

   }

   protected double func_190634_dg() {
      return 1.0D;
   }

   protected void func_142017_o(float p_142017_1_) {
   }
}
