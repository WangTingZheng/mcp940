package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAreaEffectCloud extends Entity {
   private static final DataParameter<Float> field_184498_a = EntityDataManager.<Float>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184499_b = EntityDataManager.<Integer>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184500_c = EntityDataManager.<Boolean>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184501_d = EntityDataManager.<Integer>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_189736_e = EntityDataManager.<Integer>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_189737_f = EntityDataManager.<Integer>func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187192_b);
   private PotionType field_184502_e;
   private final List<PotionEffect> field_184503_f;
   private final Map<Entity, Integer> field_184504_g;
   private int field_184505_h;
   private int field_184506_as;
   private int field_184507_at;
   private boolean field_184508_au;
   private int field_184509_av;
   private float field_184510_aw;
   private float field_184511_ax;
   private EntityLivingBase field_184512_ay;
   private UUID field_184513_az;

   public EntityAreaEffectCloud(World p_i46809_1_) {
      super(p_i46809_1_);
      this.field_184502_e = PotionTypes.field_185229_a;
      this.field_184503_f = Lists.<PotionEffect>newArrayList();
      this.field_184504_g = Maps.<Entity, Integer>newHashMap();
      this.field_184505_h = 600;
      this.field_184506_as = 20;
      this.field_184507_at = 20;
      this.field_70145_X = true;
      this.field_70178_ae = true;
      this.func_184483_a(3.0F);
   }

   public EntityAreaEffectCloud(World p_i46810_1_, double p_i46810_2_, double p_i46810_4_, double p_i46810_6_) {
      this(p_i46810_1_);
      this.func_70107_b(p_i46810_2_, p_i46810_4_, p_i46810_6_);
   }

   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184499_b, Integer.valueOf(0));
      this.func_184212_Q().func_187214_a(field_184498_a, Float.valueOf(0.5F));
      this.func_184212_Q().func_187214_a(field_184500_c, Boolean.valueOf(false));
      this.func_184212_Q().func_187214_a(field_184501_d, Integer.valueOf(EnumParticleTypes.SPELL_MOB.func_179348_c()));
      this.func_184212_Q().func_187214_a(field_189736_e, Integer.valueOf(0));
      this.func_184212_Q().func_187214_a(field_189737_f, Integer.valueOf(0));
   }

   public void func_184483_a(float p_184483_1_) {
      double d0 = this.field_70165_t;
      double d1 = this.field_70163_u;
      double d2 = this.field_70161_v;
      this.func_70105_a(p_184483_1_ * 2.0F, 0.5F);
      this.func_70107_b(d0, d1, d2);
      if (!this.field_70170_p.field_72995_K) {
         this.func_184212_Q().func_187227_b(field_184498_a, Float.valueOf(p_184483_1_));
      }

   }

   public float func_184490_j() {
      return ((Float)this.func_184212_Q().func_187225_a(field_184498_a)).floatValue();
   }

   public void func_184484_a(PotionType p_184484_1_) {
      this.field_184502_e = p_184484_1_;
      if (!this.field_184508_au) {
         this.func_190618_C();
      }

   }

   private void func_190618_C() {
      if (this.field_184502_e == PotionTypes.field_185229_a && this.field_184503_f.isEmpty()) {
         this.func_184212_Q().func_187227_b(field_184499_b, Integer.valueOf(0));
      } else {
         this.func_184212_Q().func_187227_b(field_184499_b, Integer.valueOf(PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184502_e, this.field_184503_f))));
      }

   }

   public void func_184496_a(PotionEffect p_184496_1_) {
      this.field_184503_f.add(p_184496_1_);
      if (!this.field_184508_au) {
         this.func_190618_C();
      }

   }

   public int func_184492_k() {
      return ((Integer)this.func_184212_Q().func_187225_a(field_184499_b)).intValue();
   }

   public void func_184482_a(int p_184482_1_) {
      this.field_184508_au = true;
      this.func_184212_Q().func_187227_b(field_184499_b, Integer.valueOf(p_184482_1_));
   }

   public EnumParticleTypes func_184493_l() {
      return EnumParticleTypes.func_179342_a(((Integer)this.func_184212_Q().func_187225_a(field_184501_d)).intValue());
   }

   public void func_184491_a(EnumParticleTypes p_184491_1_) {
      this.func_184212_Q().func_187227_b(field_184501_d, Integer.valueOf(p_184491_1_.func_179348_c()));
   }

   public int func_189733_n() {
      return ((Integer)this.func_184212_Q().func_187225_a(field_189736_e)).intValue();
   }

   public void func_189734_b(int p_189734_1_) {
      this.func_184212_Q().func_187227_b(field_189736_e, Integer.valueOf(p_189734_1_));
   }

   public int func_189735_o() {
      return ((Integer)this.func_184212_Q().func_187225_a(field_189737_f)).intValue();
   }

   public void func_189732_d(int p_189732_1_) {
      this.func_184212_Q().func_187227_b(field_189737_f, Integer.valueOf(p_189732_1_));
   }

   protected void func_184488_a(boolean p_184488_1_) {
      this.func_184212_Q().func_187227_b(field_184500_c, Boolean.valueOf(p_184488_1_));
   }

   public boolean func_184497_n() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184500_c)).booleanValue();
   }

   public int func_184489_o() {
      return this.field_184505_h;
   }

   public void func_184486_b(int p_184486_1_) {
      this.field_184505_h = p_184486_1_;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      boolean flag = this.func_184497_n();
      float f = this.func_184490_j();
      if (this.field_70170_p.field_72995_K) {
         EnumParticleTypes enumparticletypes = this.func_184493_l();
         int[] aint = new int[enumparticletypes.func_179345_d()];
         if (aint.length > 0) {
            aint[0] = this.func_189733_n();
         }

         if (aint.length > 1) {
            aint[1] = this.func_189735_o();
         }

         if (flag) {
            if (this.field_70146_Z.nextBoolean()) {
               for(int i = 0; i < 2; ++i) {
                  float f1 = this.field_70146_Z.nextFloat() * 6.2831855F;
                  float f2 = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * 0.2F;
                  float f3 = MathHelper.func_76134_b(f1) * f2;
                  float f4 = MathHelper.func_76126_a(f1) * f2;
                  if (enumparticletypes == EnumParticleTypes.SPELL_MOB) {
                     int j = this.field_70146_Z.nextBoolean() ? 16777215 : this.func_184492_k();
                     int k = j >> 16 & 255;
                     int l = j >> 8 & 255;
                     int i1 = j & 255;
                     this.field_70170_p.func_190523_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), this.field_70165_t + (double)f3, this.field_70163_u, this.field_70161_v + (double)f4, (double)((float)k / 255.0F), (double)((float)l / 255.0F), (double)((float)i1 / 255.0F));
                  } else {
                     this.field_70170_p.func_190523_a(enumparticletypes.func_179348_c(), this.field_70165_t + (double)f3, this.field_70163_u, this.field_70161_v + (double)f4, 0.0D, 0.0D, 0.0D, aint);
                  }
               }
            }
         } else {
            float f5 = 3.1415927F * f * f;

            for(int k1 = 0; (float)k1 < f5; ++k1) {
               float f6 = this.field_70146_Z.nextFloat() * 6.2831855F;
               float f7 = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * f;
               float f8 = MathHelper.func_76134_b(f6) * f7;
               float f9 = MathHelper.func_76126_a(f6) * f7;
               if (enumparticletypes == EnumParticleTypes.SPELL_MOB) {
                  int l1 = this.func_184492_k();
                  int i2 = l1 >> 16 & 255;
                  int j2 = l1 >> 8 & 255;
                  int j1 = l1 & 255;
                  this.field_70170_p.func_190523_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), this.field_70165_t + (double)f8, this.field_70163_u, this.field_70161_v + (double)f9, (double)((float)i2 / 255.0F), (double)((float)j2 / 255.0F), (double)((float)j1 / 255.0F));
               } else {
                  this.field_70170_p.func_190523_a(enumparticletypes.func_179348_c(), this.field_70165_t + (double)f8, this.field_70163_u, this.field_70161_v + (double)f9, (0.5D - this.field_70146_Z.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.field_70146_Z.nextDouble()) * 0.15D, aint);
               }
            }
         }
      } else {
         if (this.field_70173_aa >= this.field_184506_as + this.field_184505_h) {
            this.func_70106_y();
            return;
         }

         boolean flag1 = this.field_70173_aa < this.field_184506_as;
         if (flag != flag1) {
            this.func_184488_a(flag1);
         }

         if (flag1) {
            return;
         }

         if (this.field_184511_ax != 0.0F) {
            f += this.field_184511_ax;
            if (f < 0.5F) {
               this.func_70106_y();
               return;
            }

            this.func_184483_a(f);
         }

         if (this.field_70173_aa % 5 == 0) {
            Iterator<Entry<Entity, Integer>> iterator = this.field_184504_g.entrySet().iterator();

            while(iterator.hasNext()) {
               Entry<Entity, Integer> entry = (Entry)iterator.next();
               if (this.field_70173_aa >= ((Integer)entry.getValue()).intValue()) {
                  iterator.remove();
               }
            }

            iterator = Lists.<Entry<Entity, Integer>>newArrayList();

            for(PotionEffect potioneffect1 : this.field_184502_e.func_185170_a()) {
               iterator.add(new PotionEffect(potioneffect1.func_188419_a(), potioneffect1.func_76459_b() / 4, potioneffect1.func_76458_c(), potioneffect1.func_82720_e(), potioneffect1.func_188418_e()));
            }

            iterator.addAll(this.field_184503_f);
            if (iterator.isEmpty()) {
               this.field_184504_g.clear();
            } else {
               List<EntityLivingBase> list = this.field_70170_p.<EntityLivingBase>func_72872_a(EntityLivingBase.class, this.func_174813_aQ());
               if (!list.isEmpty()) {
                  for(EntityLivingBase entitylivingbase : list) {
                     if (!this.field_184504_g.containsKey(entitylivingbase) && entitylivingbase.func_184603_cC()) {
                        double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
                        double d1 = entitylivingbase.field_70161_v - this.field_70161_v;
                        double d2 = d0 * d0 + d1 * d1;
                        if (d2 <= (double)(f * f)) {
                           this.field_184504_g.put(entitylivingbase, Integer.valueOf(this.field_70173_aa + this.field_184507_at));

                           for(PotionEffect potioneffect : iterator) {
                              if (potioneffect.func_188419_a().func_76403_b()) {
                                 potioneffect.func_188419_a().func_180793_a(this, this.func_184494_w(), entitylivingbase, potioneffect.func_76458_c(), 0.5D);
                              } else {
                                 entitylivingbase.func_70690_d(new PotionEffect(potioneffect));
                              }
                           }

                           if (this.field_184510_aw != 0.0F) {
                              f += this.field_184510_aw;
                              if (f < 0.5F) {
                                 this.func_70106_y();
                                 return;
                              }

                              this.func_184483_a(f);
                           }

                           if (this.field_184509_av != 0) {
                              this.field_184505_h += this.field_184509_av;
                              if (this.field_184505_h <= 0) {
                                 this.func_70106_y();
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

   }

   public void func_184495_b(float p_184495_1_) {
      this.field_184510_aw = p_184495_1_;
   }

   public void func_184487_c(float p_184487_1_) {
      this.field_184511_ax = p_184487_1_;
   }

   public void func_184485_d(int p_184485_1_) {
      this.field_184506_as = p_184485_1_;
   }

   public void func_184481_a(@Nullable EntityLivingBase p_184481_1_) {
      this.field_184512_ay = p_184481_1_;
      this.field_184513_az = p_184481_1_ == null ? null : p_184481_1_.func_110124_au();
   }

   @Nullable
   public EntityLivingBase func_184494_w() {
      if (this.field_184512_ay == null && this.field_184513_az != null && this.field_70170_p instanceof WorldServer) {
         Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.field_184513_az);
         if (entity instanceof EntityLivingBase) {
            this.field_184512_ay = (EntityLivingBase)entity;
         }
      }

      return this.field_184512_ay;
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      this.field_70173_aa = p_70037_1_.func_74762_e("Age");
      this.field_184505_h = p_70037_1_.func_74762_e("Duration");
      this.field_184506_as = p_70037_1_.func_74762_e("WaitTime");
      this.field_184507_at = p_70037_1_.func_74762_e("ReapplicationDelay");
      this.field_184509_av = p_70037_1_.func_74762_e("DurationOnUse");
      this.field_184510_aw = p_70037_1_.func_74760_g("RadiusOnUse");
      this.field_184511_ax = p_70037_1_.func_74760_g("RadiusPerTick");
      this.func_184483_a(p_70037_1_.func_74760_g("Radius"));
      this.field_184513_az = p_70037_1_.func_186857_a("OwnerUUID");
      if (p_70037_1_.func_150297_b("Particle", 8)) {
         EnumParticleTypes enumparticletypes = EnumParticleTypes.func_186831_a(p_70037_1_.func_74779_i("Particle"));
         if (enumparticletypes != null) {
            this.func_184491_a(enumparticletypes);
            this.func_189734_b(p_70037_1_.func_74762_e("ParticleParam1"));
            this.func_189732_d(p_70037_1_.func_74762_e("ParticleParam2"));
         }
      }

      if (p_70037_1_.func_150297_b("Color", 99)) {
         this.func_184482_a(p_70037_1_.func_74762_e("Color"));
      }

      if (p_70037_1_.func_150297_b("Potion", 8)) {
         this.func_184484_a(PotionUtils.func_185187_c(p_70037_1_));
      }

      if (p_70037_1_.func_150297_b("Effects", 9)) {
         NBTTagList nbttaglist = p_70037_1_.func_150295_c("Effects", 10);
         this.field_184503_f.clear();

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            PotionEffect potioneffect = PotionEffect.func_82722_b(nbttaglist.func_150305_b(i));
            if (potioneffect != null) {
               this.func_184496_a(potioneffect);
            }
         }
      }

   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74768_a("Age", this.field_70173_aa);
      p_70014_1_.func_74768_a("Duration", this.field_184505_h);
      p_70014_1_.func_74768_a("WaitTime", this.field_184506_as);
      p_70014_1_.func_74768_a("ReapplicationDelay", this.field_184507_at);
      p_70014_1_.func_74768_a("DurationOnUse", this.field_184509_av);
      p_70014_1_.func_74776_a("RadiusOnUse", this.field_184510_aw);
      p_70014_1_.func_74776_a("RadiusPerTick", this.field_184511_ax);
      p_70014_1_.func_74776_a("Radius", this.func_184490_j());
      p_70014_1_.func_74778_a("Particle", this.func_184493_l().func_179346_b());
      p_70014_1_.func_74768_a("ParticleParam1", this.func_189733_n());
      p_70014_1_.func_74768_a("ParticleParam2", this.func_189735_o());
      if (this.field_184513_az != null) {
         p_70014_1_.func_186854_a("OwnerUUID", this.field_184513_az);
      }

      if (this.field_184508_au) {
         p_70014_1_.func_74768_a("Color", this.func_184492_k());
      }

      if (this.field_184502_e != PotionTypes.field_185229_a && this.field_184502_e != null) {
         p_70014_1_.func_74778_a("Potion", ((ResourceLocation)PotionType.field_185176_a.func_177774_c(this.field_184502_e)).toString());
      }

      if (!this.field_184503_f.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(PotionEffect potioneffect : this.field_184503_f) {
            nbttaglist.func_74742_a(potioneffect.func_82719_a(new NBTTagCompound()));
         }

         p_70014_1_.func_74782_a("Effects", nbttaglist);
      }

   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184498_a.equals(p_184206_1_)) {
         this.func_184483_a(this.func_184490_j());
      }

      super.func_184206_a(p_184206_1_);
   }

   public EnumPushReaction func_184192_z() {
      return EnumPushReaction.IGNORE;
   }
}
