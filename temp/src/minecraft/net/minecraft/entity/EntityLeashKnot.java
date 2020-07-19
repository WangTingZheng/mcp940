package net.minecraft.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World p_i1592_1_) {
      super(p_i1592_1_);
   }

   public EntityLeashKnot(World p_i45851_1_, BlockPos p_i45851_2_) {
      super(p_i45851_1_, p_i45851_2_);
      this.func_70107_b((double)p_i45851_2_.func_177958_n() + 0.5D, (double)p_i45851_2_.func_177956_o() + 0.5D, (double)p_i45851_2_.func_177952_p() + 0.5D);
      float f = 0.125F;
      float f1 = 0.1875F;
      float f2 = 0.25F;
      this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.1875D, this.field_70163_u - 0.25D + 0.125D, this.field_70161_v - 0.1875D, this.field_70165_t + 0.1875D, this.field_70163_u + 0.25D + 0.125D, this.field_70161_v + 0.1875D));
      this.field_98038_p = true;
   }

   public void func_70107_b(double p_70107_1_, double p_70107_3_, double p_70107_5_) {
      super.func_70107_b((double)MathHelper.func_76128_c(p_70107_1_) + 0.5D, (double)MathHelper.func_76128_c(p_70107_3_) + 0.5D, (double)MathHelper.func_76128_c(p_70107_5_) + 0.5D);
   }

   protected void func_174856_o() {
      this.field_70165_t = (double)this.field_174861_a.func_177958_n() + 0.5D;
      this.field_70163_u = (double)this.field_174861_a.func_177956_o() + 0.5D;
      this.field_70161_v = (double)this.field_174861_a.func_177952_p() + 0.5D;
   }

   public void func_174859_a(EnumFacing p_174859_1_) {
   }

   public int func_82329_d() {
      return 9;
   }

   public int func_82330_g() {
      return 9;
   }

   public float func_70047_e() {
      return -0.0625F;
   }

   public boolean func_70112_a(double p_70112_1_) {
      return p_70112_1_ < 1024.0D;
   }

   public void func_110128_b(@Nullable Entity p_110128_1_) {
      this.func_184185_a(SoundEvents.field_187746_da, 1.0F, 1.0F);
   }

   public boolean func_70039_c(NBTTagCompound p_70039_1_) {
      return false;
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      if (this.field_70170_p.field_72995_K) {
         return true;
      } else {
         boolean flag = false;
         double d0 = 7.0D;
         List<EntityLiving> list = this.field_70170_p.<EntityLiving>func_72872_a(EntityLiving.class, new AxisAlignedBB(this.field_70165_t - 7.0D, this.field_70163_u - 7.0D, this.field_70161_v - 7.0D, this.field_70165_t + 7.0D, this.field_70163_u + 7.0D, this.field_70161_v + 7.0D));

         for(EntityLiving entityliving : list) {
            if (entityliving.func_110167_bD() && entityliving.func_110166_bE() == p_184230_1_) {
               entityliving.func_110162_b(this, true);
               flag = true;
            }
         }

         if (!flag) {
            this.func_70106_y();
            if (p_184230_1_.field_71075_bZ.field_75098_d) {
               for(EntityLiving entityliving1 : list) {
                  if (entityliving1.func_110167_bD() && entityliving1.func_110166_bE() == this) {
                     entityliving1.func_110160_i(true, false);
                  }
               }
            }
         }

         return true;
      }
   }

   public boolean func_70518_d() {
      return this.field_70170_p.func_180495_p(this.field_174861_a).func_177230_c() instanceof BlockFence;
   }

   public static EntityLeashKnot func_174862_a(World p_174862_0_, BlockPos p_174862_1_) {
      EntityLeashKnot entityleashknot = new EntityLeashKnot(p_174862_0_, p_174862_1_);
      p_174862_0_.func_72838_d(entityleashknot);
      entityleashknot.func_184523_o();
      return entityleashknot;
   }

   @Nullable
   public static EntityLeashKnot func_174863_b(World p_174863_0_, BlockPos p_174863_1_) {
      int i = p_174863_1_.func_177958_n();
      int j = p_174863_1_.func_177956_o();
      int k = p_174863_1_.func_177952_p();

      for(EntityLeashKnot entityleashknot : p_174863_0_.func_72872_a(EntityLeashKnot.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
         if (entityleashknot.func_174857_n().equals(p_174863_1_)) {
            return entityleashknot;
         }
      }

      return null;
   }

   public void func_184523_o() {
      this.func_184185_a(SoundEvents.field_187748_db, 1.0F, 1.0F);
   }
}
