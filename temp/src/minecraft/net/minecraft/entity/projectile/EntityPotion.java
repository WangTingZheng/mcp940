package net.minecraft.entity.projectile;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPotion extends EntityThrowable {
   private static final DataParameter<ItemStack> field_184545_d = EntityDataManager.<ItemStack>func_187226_a(EntityPotion.class, DataSerializers.field_187196_f);
   private static final Logger field_184546_e = LogManager.getLogger();
   public static final Predicate<EntityLivingBase> field_190546_d = new Predicate<EntityLivingBase>() {
      public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
         return EntityPotion.func_190544_c(p_apply_1_);
      }
   };

   public EntityPotion(World p_i1788_1_) {
      super(p_i1788_1_);
   }

   public EntityPotion(World p_i1790_1_, EntityLivingBase p_i1790_2_, ItemStack p_i1790_3_) {
      super(p_i1790_1_, p_i1790_2_);
      this.func_184541_a(p_i1790_3_);
   }

   public EntityPotion(World p_i1792_1_, double p_i1792_2_, double p_i1792_4_, double p_i1792_6_, ItemStack p_i1792_8_) {
      super(p_i1792_1_, p_i1792_2_, p_i1792_4_, p_i1792_6_);
      if (!p_i1792_8_.func_190926_b()) {
         this.func_184541_a(p_i1792_8_);
      }

   }

   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184545_d, ItemStack.field_190927_a);
   }

   public ItemStack func_184543_l() {
      ItemStack itemstack = (ItemStack)this.func_184212_Q().func_187225_a(field_184545_d);
      if (itemstack.func_77973_b() != Items.field_185155_bH && itemstack.func_77973_b() != Items.field_185156_bI) {
         if (this.field_70170_p != null) {
            field_184546_e.error("ThrownPotion entity {} has no item?!", (int)this.func_145782_y());
         }

         return new ItemStack(Items.field_185155_bH);
      } else {
         return itemstack;
      }
   }

   public void func_184541_a(ItemStack p_184541_1_) {
      this.func_184212_Q().func_187227_b(field_184545_d, p_184541_1_);
      this.func_184212_Q().func_187217_b(field_184545_d);
   }

   protected float func_70185_h() {
      return 0.05F;
   }

   protected void func_70184_a(RayTraceResult p_70184_1_) {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack itemstack = this.func_184543_l();
         PotionType potiontype = PotionUtils.func_185191_c(itemstack);
         List<PotionEffect> list = PotionUtils.func_185189_a(itemstack);
         boolean flag = potiontype == PotionTypes.field_185230_b && list.isEmpty();
         if (p_70184_1_.field_72313_a == RayTraceResult.Type.BLOCK && flag) {
            BlockPos blockpos = p_70184_1_.func_178782_a().func_177972_a(p_70184_1_.field_178784_b);
            this.func_184542_a(blockpos, p_70184_1_.field_178784_b);

            for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
               this.func_184542_a(blockpos.func_177972_a(enumfacing), enumfacing);
            }
         }

         if (flag) {
            this.func_190545_n();
         } else if (!list.isEmpty()) {
            if (this.func_184544_n()) {
               this.func_190542_a(itemstack, potiontype);
            } else {
               this.func_190543_a(p_70184_1_, list);
            }
         }

         int i = potiontype.func_185172_c() ? 2007 : 2002;
         this.field_70170_p.func_175718_b(i, new BlockPos(this), PotionUtils.func_190932_c(itemstack));
         this.func_70106_y();
      }
   }

   private void func_190545_n() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D);
      List<EntityLivingBase> list = this.field_70170_p.<EntityLivingBase>func_175647_a(EntityLivingBase.class, axisalignedbb, field_190546_d);
      if (!list.isEmpty()) {
         for(EntityLivingBase entitylivingbase : list) {
            double d0 = this.func_70068_e(entitylivingbase);
            if (d0 < 16.0D && func_190544_c(entitylivingbase)) {
               entitylivingbase.func_70097_a(DamageSource.field_76369_e, 1.0F);
            }
         }
      }

   }

   private void func_190543_a(RayTraceResult p_190543_1_, List<PotionEffect> p_190543_2_) {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D);
      List<EntityLivingBase> list = this.field_70170_p.<EntityLivingBase>func_72872_a(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for(EntityLivingBase entitylivingbase : list) {
            if (entitylivingbase.func_184603_cC()) {
               double d0 = this.func_70068_e(entitylivingbase);
               if (d0 < 16.0D) {
                  double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                  if (entitylivingbase == p_190543_1_.field_72308_g) {
                     d1 = 1.0D;
                  }

                  for(PotionEffect potioneffect : p_190543_2_) {
                     Potion potion = potioneffect.func_188419_a();
                     if (potion.func_76403_b()) {
                        potion.func_180793_a(this, this.func_85052_h(), entitylivingbase, potioneffect.func_76458_c(), d1);
                     } else {
                        int i = (int)(d1 * (double)potioneffect.func_76459_b() + 0.5D);
                        if (i > 20) {
                           entitylivingbase.func_70690_d(new PotionEffect(potion, i, potioneffect.func_76458_c(), potioneffect.func_82720_e(), potioneffect.func_188418_e()));
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void func_190542_a(ItemStack p_190542_1_, PotionType p_190542_2_) {
      EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      entityareaeffectcloud.func_184481_a(this.func_85052_h());
      entityareaeffectcloud.func_184483_a(3.0F);
      entityareaeffectcloud.func_184495_b(-0.5F);
      entityareaeffectcloud.func_184485_d(10);
      entityareaeffectcloud.func_184487_c(-entityareaeffectcloud.func_184490_j() / (float)entityareaeffectcloud.func_184489_o());
      entityareaeffectcloud.func_184484_a(p_190542_2_);

      for(PotionEffect potioneffect : PotionUtils.func_185190_b(p_190542_1_)) {
         entityareaeffectcloud.func_184496_a(new PotionEffect(potioneffect));
      }

      NBTTagCompound nbttagcompound = p_190542_1_.func_77978_p();
      if (nbttagcompound != null && nbttagcompound.func_150297_b("CustomPotionColor", 99)) {
         entityareaeffectcloud.func_184482_a(nbttagcompound.func_74762_e("CustomPotionColor"));
      }

      this.field_70170_p.func_72838_d(entityareaeffectcloud);
   }

   private boolean func_184544_n() {
      return this.func_184543_l().func_77973_b() == Items.field_185156_bI;
   }

   private void func_184542_a(BlockPos p_184542_1_, EnumFacing p_184542_2_) {
      if (this.field_70170_p.func_180495_p(p_184542_1_).func_177230_c() == Blocks.field_150480_ab) {
         this.field_70170_p.func_175719_a((EntityPlayer)null, p_184542_1_.func_177972_a(p_184542_2_), p_184542_2_.func_176734_d());
      }

   }

   public static void func_189665_a(DataFixer p_189665_0_) {
      EntityThrowable.func_189661_a(p_189665_0_, "ThrownPotion");
      p_189665_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData(EntityPotion.class, new String[]{"Potion"}));
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      ItemStack itemstack = new ItemStack(p_70037_1_.func_74775_l("Potion"));
      if (itemstack.func_190926_b()) {
         this.func_70106_y();
      } else {
         this.func_184541_a(itemstack);
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      ItemStack itemstack = this.func_184543_l();
      if (!itemstack.func_190926_b()) {
         p_70014_1_.func_74782_a("Potion", itemstack.func_77955_b(new NBTTagCompound()));
      }

   }

   private static boolean func_190544_c(EntityLivingBase p_190544_0_) {
      return p_190544_0_ instanceof EntityEnderman || p_190544_0_ instanceof EntityBlaze;
   }
}
