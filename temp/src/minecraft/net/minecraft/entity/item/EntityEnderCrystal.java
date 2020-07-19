package net.minecraft.entity.item;

import com.google.common.base.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;

public class EntityEnderCrystal extends Entity {
   private static final DataParameter<Optional<BlockPos>> field_184521_b = EntityDataManager.<Optional<BlockPos>>func_187226_a(EntityEnderCrystal.class, DataSerializers.field_187201_k);
   private static final DataParameter<Boolean> field_184522_c = EntityDataManager.<Boolean>func_187226_a(EntityEnderCrystal.class, DataSerializers.field_187198_h);
   public int field_70261_a;

   public EntityEnderCrystal(World p_i1698_1_) {
      super(p_i1698_1_);
      this.field_70156_m = true;
      this.func_70105_a(2.0F, 2.0F);
      this.field_70261_a = this.field_70146_Z.nextInt(100000);
   }

   public EntityEnderCrystal(World p_i1699_1_, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_) {
      this(p_i1699_1_);
      this.func_70107_b(p_i1699_2_, p_i1699_4_, p_i1699_6_);
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184521_b, Optional.absent());
      this.func_184212_Q().func_187214_a(field_184522_c, Boolean.valueOf(true));
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      ++this.field_70261_a;
      if (!this.field_70170_p.field_72995_K) {
         BlockPos blockpos = new BlockPos(this);
         if (this.field_70170_p.field_73011_w instanceof WorldProviderEnd && this.field_70170_p.func_180495_p(blockpos).func_177230_c() != Blocks.field_150480_ab) {
            this.field_70170_p.func_175656_a(blockpos, Blocks.field_150480_ab.func_176223_P());
         }
      }

   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      if (this.func_184518_j() != null) {
         p_70014_1_.func_74782_a("BeamTarget", NBTUtil.func_186859_a(this.func_184518_j()));
      }

      p_70014_1_.func_74757_a("ShowBottom", this.func_184520_k());
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      if (p_70037_1_.func_150297_b("BeamTarget", 10)) {
         this.func_184516_a(NBTUtil.func_186861_c(p_70037_1_.func_74775_l("BeamTarget")));
      }

      if (p_70037_1_.func_150297_b("ShowBottom", 1)) {
         this.func_184517_a(p_70037_1_.func_74767_n("ShowBottom"));
      }

   }

   public boolean func_70067_L() {
      return true;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (p_70097_1_.func_76346_g() instanceof EntityDragon) {
         return false;
      } else {
         if (!this.field_70128_L && !this.field_70170_p.field_72995_K) {
            this.func_70106_y();
            if (!this.field_70170_p.field_72995_K) {
               if (!p_70097_1_.func_94541_c()) {
                  this.field_70170_p.func_72876_a((Entity)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 6.0F, true);
               }

               this.func_184519_a(p_70097_1_);
            }
         }

         return true;
      }
   }

   public void func_174812_G() {
      this.func_184519_a(DamageSource.field_76377_j);
      super.func_174812_G();
   }

   private void func_184519_a(DamageSource p_184519_1_) {
      if (this.field_70170_p.field_73011_w instanceof WorldProviderEnd) {
         WorldProviderEnd worldproviderend = (WorldProviderEnd)this.field_70170_p.field_73011_w;
         DragonFightManager dragonfightmanager = worldproviderend.func_186063_s();
         if (dragonfightmanager != null) {
            dragonfightmanager.func_186090_a(this, p_184519_1_);
         }
      }

   }

   public void func_184516_a(@Nullable BlockPos p_184516_1_) {
      this.func_184212_Q().func_187227_b(field_184521_b, Optional.fromNullable(p_184516_1_));
   }

   @Nullable
   public BlockPos func_184518_j() {
      return (BlockPos)((Optional)this.func_184212_Q().func_187225_a(field_184521_b)).orNull();
   }

   public void func_184517_a(boolean p_184517_1_) {
      this.func_184212_Q().func_187227_b(field_184522_c, Boolean.valueOf(p_184517_1_));
   }

   public boolean func_184520_k() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184522_c)).booleanValue();
   }

   public boolean func_70112_a(double p_70112_1_) {
      return super.func_70112_a(p_70112_1_) || this.func_184518_j() != null;
   }
}
