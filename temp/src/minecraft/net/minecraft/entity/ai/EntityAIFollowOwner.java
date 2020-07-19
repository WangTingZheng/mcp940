package net.minecraft.entity.ai;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
   private final EntityTameable field_75338_d;
   private EntityLivingBase field_75339_e;
   World field_75342_a;
   private final double field_75336_f;
   private final PathNavigate field_75337_g;
   private int field_75343_h;
   float field_75340_b;
   float field_75341_c;
   private float field_75344_i;

   public EntityAIFollowOwner(EntityTameable p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_) {
      this.field_75338_d = p_i1625_1_;
      this.field_75342_a = p_i1625_1_.field_70170_p;
      this.field_75336_f = p_i1625_2_;
      this.field_75337_g = p_i1625_1_.func_70661_as();
      this.field_75341_c = p_i1625_4_;
      this.field_75340_b = p_i1625_5_;
      this.func_75248_a(3);
      if (!(p_i1625_1_.func_70661_as() instanceof PathNavigateGround) && !(p_i1625_1_.func_70661_as() instanceof PathNavigateFlying)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.field_75338_d.func_70902_q();
      if (entitylivingbase == null) {
         return false;
      } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).func_175149_v()) {
         return false;
      } else if (this.field_75338_d.func_70906_o()) {
         return false;
      } else if (this.field_75338_d.func_70068_e(entitylivingbase) < (double)(this.field_75341_c * this.field_75341_c)) {
         return false;
      } else {
         this.field_75339_e = entitylivingbase;
         return true;
      }
   }

   public boolean func_75253_b() {
      return !this.field_75337_g.func_75500_f() && this.field_75338_d.func_70068_e(this.field_75339_e) > (double)(this.field_75340_b * this.field_75340_b) && !this.field_75338_d.func_70906_o();
   }

   public void func_75249_e() {
      this.field_75343_h = 0;
      this.field_75344_i = this.field_75338_d.func_184643_a(PathNodeType.WATER);
      this.field_75338_d.func_184644_a(PathNodeType.WATER, 0.0F);
   }

   public void func_75251_c() {
      this.field_75339_e = null;
      this.field_75337_g.func_75499_g();
      this.field_75338_d.func_184644_a(PathNodeType.WATER, this.field_75344_i);
   }

   public void func_75246_d() {
      this.field_75338_d.func_70671_ap().func_75651_a(this.field_75339_e, 10.0F, (float)this.field_75338_d.func_70646_bf());
      if (!this.field_75338_d.func_70906_o()) {
         if (--this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.field_75337_g.func_75497_a(this.field_75339_e, this.field_75336_f)) {
               if (!this.field_75338_d.func_110167_bD() && !this.field_75338_d.func_184218_aH()) {
                  if (this.field_75338_d.func_70068_e(this.field_75339_e) >= 144.0D) {
                     int i = MathHelper.func_76128_c(this.field_75339_e.field_70165_t) - 2;
                     int j = MathHelper.func_76128_c(this.field_75339_e.field_70161_v) - 2;
                     int k = MathHelper.func_76128_c(this.field_75339_e.func_174813_aQ().field_72338_b);

                     for(int l = 0; l <= 4; ++l) {
                        for(int i1 = 0; i1 <= 4; ++i1) {
                           if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.func_192381_a(i, j, k, l, i1)) {
                              this.field_75338_d.func_70012_b((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.field_75338_d.field_70177_z, this.field_75338_d.field_70125_A);
                              this.field_75337_g.func_75499_g();
                              return;
                           }
                        }
                     }

                  }
               }
            }
         }
      }
   }

   protected boolean func_192381_a(int p_192381_1_, int p_192381_2_, int p_192381_3_, int p_192381_4_, int p_192381_5_) {
      BlockPos blockpos = new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ - 1, p_192381_2_ + p_192381_5_);
      IBlockState iblockstate = this.field_75342_a.func_180495_p(blockpos);
      return iblockstate.func_193401_d(this.field_75342_a, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.func_189884_a(this.field_75338_d) && this.field_75342_a.func_175623_d(blockpos.func_177984_a()) && this.field_75342_a.func_175623_d(blockpos.func_177981_b(2));
   }
}
