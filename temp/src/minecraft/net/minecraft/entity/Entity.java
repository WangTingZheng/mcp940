package net.minecraft.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity implements ICommandSender {
   private static final Logger field_184243_a = LogManager.getLogger();
   private static final List<ItemStack> field_190535_b = Collections.<ItemStack>emptyList();
   private static final AxisAlignedBB field_174836_a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
   private static double field_70155_l = 1.0D;
   private static int field_70152_a;
   private int field_145783_c;
   public boolean field_70156_m;
   private final List<Entity> field_184244_h;
   protected int field_184245_j;
   private Entity field_184239_as;
   public boolean field_98038_p;
   public World field_70170_p;
   public double field_70169_q;
   public double field_70167_r;
   public double field_70166_s;
   public double field_70165_t;
   public double field_70163_u;
   public double field_70161_v;
   public double field_70159_w;
   public double field_70181_x;
   public double field_70179_y;
   public float field_70177_z;
   public float field_70125_A;
   public float field_70126_B;
   public float field_70127_C;
   private AxisAlignedBB field_70121_D;
   public boolean field_70122_E;
   public boolean field_70123_F;
   public boolean field_70124_G;
   public boolean field_70132_H;
   public boolean field_70133_I;
   protected boolean field_70134_J;
   private boolean field_174835_g;
   public boolean field_70128_L;
   public float field_70130_N;
   public float field_70131_O;
   public float field_70141_P;
   public float field_70140_Q;
   public float field_82151_R;
   public float field_70143_R;
   private int field_70150_b;
   private float field_191959_ay;
   public double field_70142_S;
   public double field_70137_T;
   public double field_70136_U;
   public float field_70138_W;
   public boolean field_70145_X;
   public float field_70144_Y;
   protected Random field_70146_Z;
   public int field_70173_aa;
   private int field_190534_ay;
   protected boolean field_70171_ac;
   public int field_70172_ad;
   protected boolean field_70148_d;
   protected boolean field_70178_ae;
   protected EntityDataManager field_70180_af;
   protected static final DataParameter<Byte> field_184240_ax = EntityDataManager.<Byte>func_187226_a(Entity.class, DataSerializers.field_187191_a);
   private static final DataParameter<Integer> field_184241_ay = EntityDataManager.<Integer>func_187226_a(Entity.class, DataSerializers.field_187192_b);
   private static final DataParameter<String> field_184242_az = EntityDataManager.<String>func_187226_a(Entity.class, DataSerializers.field_187194_d);
   private static final DataParameter<Boolean> field_184233_aA = EntityDataManager.<Boolean>func_187226_a(Entity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_184234_aB = EntityDataManager.<Boolean>func_187226_a(Entity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_189655_aD = EntityDataManager.<Boolean>func_187226_a(Entity.class, DataSerializers.field_187198_h);
   public boolean field_70175_ag;
   public int field_70176_ah;
   public int field_70162_ai;
   public int field_70164_aj;
   public long field_70118_ct;
   public long field_70117_cu;
   public long field_70116_cv;
   public boolean field_70158_ak;
   public boolean field_70160_al;
   public int field_71088_bW;
   protected boolean field_71087_bX;
   protected int field_82153_h;
   public int field_71093_bK;
   protected BlockPos field_181016_an;
   protected Vec3d field_181017_ao;
   protected EnumFacing field_181018_ap;
   private boolean field_83001_bt;
   protected UUID field_96093_i;
   protected String field_189513_ar;
   private final CommandResultStats field_174837_as;
   protected boolean field_184238_ar;
   private final Set<String> field_184236_aF;
   private boolean field_184237_aG;
   private final double[] field_191505_aI;
   private long field_191506_aJ;

   public Entity(World p_i1582_1_) {
      this.field_145783_c = field_70152_a++;
      this.field_184244_h = Lists.<Entity>newArrayList();
      this.field_70121_D = field_174836_a;
      this.field_70130_N = 0.6F;
      this.field_70131_O = 1.8F;
      this.field_70150_b = 1;
      this.field_191959_ay = 1.0F;
      this.field_70146_Z = new Random();
      this.field_190534_ay = -this.func_190531_bD();
      this.field_70148_d = true;
      this.field_96093_i = MathHelper.func_180182_a(this.field_70146_Z);
      this.field_189513_ar = this.field_96093_i.toString();
      this.field_174837_as = new CommandResultStats();
      this.field_184236_aF = Sets.<String>newHashSet();
      this.field_191505_aI = new double[]{0.0D, 0.0D, 0.0D};
      this.field_70170_p = p_i1582_1_;
      this.func_70107_b(0.0D, 0.0D, 0.0D);
      if (p_i1582_1_ != null) {
         this.field_71093_bK = p_i1582_1_.field_73011_w.func_186058_p().func_186068_a();
      }

      this.field_70180_af = new EntityDataManager(this);
      this.field_70180_af.func_187214_a(field_184240_ax, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_184241_ay, Integer.valueOf(300));
      this.field_70180_af.func_187214_a(field_184233_aA, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_184242_az, "");
      this.field_70180_af.func_187214_a(field_184234_aB, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_189655_aD, Boolean.valueOf(false));
      this.func_70088_a();
   }

   public int func_145782_y() {
      return this.field_145783_c;
   }

   public void func_145769_d(int p_145769_1_) {
      this.field_145783_c = p_145769_1_;
   }

   public Set<String> func_184216_O() {
      return this.field_184236_aF;
   }

   public boolean func_184211_a(String p_184211_1_) {
      if (this.field_184236_aF.size() >= 1024) {
         return false;
      } else {
         this.field_184236_aF.add(p_184211_1_);
         return true;
      }
   }

   public boolean func_184197_b(String p_184197_1_) {
      return this.field_184236_aF.remove(p_184197_1_);
   }

   public void func_174812_G() {
      this.func_70106_y();
   }

   protected abstract void func_70088_a();

   public EntityDataManager func_184212_Q() {
      return this.field_70180_af;
   }

   public boolean equals(Object p_equals_1_) {
      if (p_equals_1_ instanceof Entity) {
         return ((Entity)p_equals_1_).field_145783_c == this.field_145783_c;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_145783_c;
   }

   protected void func_70065_x() {
      if (this.field_70170_p != null) {
         while(this.field_70163_u > 0.0D && this.field_70163_u < 256.0D) {
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            if (this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty()) {
               break;
            }

            ++this.field_70163_u;
         }

         this.field_70159_w = 0.0D;
         this.field_70181_x = 0.0D;
         this.field_70179_y = 0.0D;
         this.field_70125_A = 0.0F;
      }
   }

   public void func_70106_y() {
      this.field_70128_L = true;
   }

   public void func_184174_b(boolean p_184174_1_) {
   }

   protected void func_70105_a(float p_70105_1_, float p_70105_2_) {
      if (p_70105_1_ != this.field_70130_N || p_70105_2_ != this.field_70131_O) {
         float f = this.field_70130_N;
         this.field_70130_N = p_70105_1_;
         this.field_70131_O = p_70105_2_;
         if (this.field_70130_N < f) {
            double d0 = (double)p_70105_1_ / 2.0D;
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - d0, this.field_70163_u, this.field_70161_v - d0, this.field_70165_t + d0, this.field_70163_u + (double)this.field_70131_O, this.field_70161_v + d0));
            return;
         }

         AxisAlignedBB axisalignedbb = this.func_174813_aQ();
         this.func_174826_a(new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + (double)this.field_70130_N, axisalignedbb.field_72338_b + (double)this.field_70131_O, axisalignedbb.field_72339_c + (double)this.field_70130_N));
         if (this.field_70130_N > f && !this.field_70148_d && !this.field_70170_p.field_72995_K) {
            this.func_70091_d(MoverType.SELF, (double)(f - this.field_70130_N), 0.0D, (double)(f - this.field_70130_N));
         }
      }

   }

   protected void func_70101_b(float p_70101_1_, float p_70101_2_) {
      this.field_70177_z = p_70101_1_ % 360.0F;
      this.field_70125_A = p_70101_2_ % 360.0F;
   }

   public void func_70107_b(double p_70107_1_, double p_70107_3_, double p_70107_5_) {
      this.field_70165_t = p_70107_1_;
      this.field_70163_u = p_70107_3_;
      this.field_70161_v = p_70107_5_;
      float f = this.field_70130_N / 2.0F;
      float f1 = this.field_70131_O;
      this.func_174826_a(new AxisAlignedBB(p_70107_1_ - (double)f, p_70107_3_, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ + (double)f1, p_70107_5_ + (double)f));
   }

   public void func_70082_c(float p_70082_1_, float p_70082_2_) {
      float f = this.field_70125_A;
      float f1 = this.field_70177_z;
      this.field_70177_z = (float)((double)this.field_70177_z + (double)p_70082_1_ * 0.15D);
      this.field_70125_A = (float)((double)this.field_70125_A - (double)p_70082_2_ * 0.15D);
      this.field_70125_A = MathHelper.func_76131_a(this.field_70125_A, -90.0F, 90.0F);
      this.field_70127_C += this.field_70125_A - f;
      this.field_70126_B += this.field_70177_z - f1;
      if (this.field_184239_as != null) {
         this.field_184239_as.func_184190_l(this);
      }

   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(6, this.func_184202_aL());
      }

      this.func_70030_z();
   }

   public void func_70030_z() {
      this.field_70170_p.field_72984_F.func_76320_a("entityBaseTick");
      if (this.func_184218_aH() && this.func_184187_bx().field_70128_L) {
         this.func_184210_p();
      }

      if (this.field_184245_j > 0) {
         --this.field_184245_j;
      }

      this.field_70141_P = this.field_70140_Q;
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70127_C = this.field_70125_A;
      this.field_70126_B = this.field_70177_z;
      if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer) {
         this.field_70170_p.field_72984_F.func_76320_a("portal");
         if (this.field_71087_bX) {
            MinecraftServer minecraftserver = this.field_70170_p.func_73046_m();
            if (minecraftserver.func_71255_r()) {
               if (!this.func_184218_aH()) {
                  int i = this.func_82145_z();
                  if (this.field_82153_h++ >= i) {
                     this.field_82153_h = i;
                     this.field_71088_bW = this.func_82147_ab();
                     int j;
                     if (this.field_70170_p.field_73011_w.func_186058_p().func_186068_a() == -1) {
                        j = 0;
                     } else {
                        j = -1;
                     }

                     this.func_184204_a(j);
                  }
               }

               this.field_71087_bX = false;
            }
         } else {
            if (this.field_82153_h > 0) {
               this.field_82153_h -= 4;
            }

            if (this.field_82153_h < 0) {
               this.field_82153_h = 0;
            }
         }

         this.func_184173_H();
         this.field_70170_p.field_72984_F.func_76319_b();
      }

      this.func_174830_Y();
      this.func_70072_I();
      if (this.field_70170_p.field_72995_K) {
         this.func_70066_B();
      } else if (this.field_190534_ay > 0) {
         if (this.field_70178_ae) {
            this.field_190534_ay -= 4;
            if (this.field_190534_ay < 0) {
               this.func_70066_B();
            }
         } else {
            if (this.field_190534_ay % 20 == 0) {
               this.func_70097_a(DamageSource.field_76370_b, 1.0F);
            }

            --this.field_190534_ay;
         }
      }

      if (this.func_180799_ab()) {
         this.func_70044_A();
         this.field_70143_R *= 0.5F;
      }

      if (this.field_70163_u < -64.0D) {
         this.func_70076_C();
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(0, this.field_190534_ay > 0);
      }

      this.field_70148_d = false;
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_184173_H() {
      if (this.field_71088_bW > 0) {
         --this.field_71088_bW;
      }

   }

   public int func_82145_z() {
      return 1;
   }

   protected void func_70044_A() {
      if (!this.field_70178_ae) {
         this.func_70097_a(DamageSource.field_76371_c, 4.0F);
         this.func_70015_d(15);
      }
   }

   public void func_70015_d(int p_70015_1_) {
      int i = p_70015_1_ * 20;
      if (this instanceof EntityLivingBase) {
         i = EnchantmentProtection.func_92093_a((EntityLivingBase)this, i);
      }

      if (this.field_190534_ay < i) {
         this.field_190534_ay = i;
      }

   }

   public void func_70066_B() {
      this.field_190534_ay = 0;
   }

   protected void func_70076_C() {
      this.func_70106_y();
   }

   public boolean func_70038_c(double p_70038_1_, double p_70038_3_, double p_70038_5_) {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ().func_72317_d(p_70038_1_, p_70038_3_, p_70038_5_);
      return this.func_174809_b(axisalignedbb);
   }

   private boolean func_174809_b(AxisAlignedBB p_174809_1_) {
      return this.field_70170_p.func_184144_a(this, p_174809_1_).isEmpty() && !this.field_70170_p.func_72953_d(p_174809_1_);
   }

   public void func_70091_d(MoverType p_70091_1_, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
      if (this.field_70145_X) {
         this.func_174826_a(this.func_174813_aQ().func_72317_d(p_70091_2_, p_70091_4_, p_70091_6_));
         this.func_174829_m();
      } else {
         if (p_70091_1_ == MoverType.PISTON) {
            long i = this.field_70170_p.func_82737_E();
            if (i != this.field_191506_aJ) {
               Arrays.fill(this.field_191505_aI, 0.0D);
               this.field_191506_aJ = i;
            }

            if (p_70091_2_ != 0.0D) {
               int j = EnumFacing.Axis.X.ordinal();
               double d0 = MathHelper.func_151237_a(p_70091_2_ + this.field_191505_aI[j], -0.51D, 0.51D);
               p_70091_2_ = d0 - this.field_191505_aI[j];
               this.field_191505_aI[j] = d0;
               if (Math.abs(p_70091_2_) <= 9.999999747378752E-6D) {
                  return;
               }
            } else if (p_70091_4_ != 0.0D) {
               int l4 = EnumFacing.Axis.Y.ordinal();
               double d12 = MathHelper.func_151237_a(p_70091_4_ + this.field_191505_aI[l4], -0.51D, 0.51D);
               p_70091_4_ = d12 - this.field_191505_aI[l4];
               this.field_191505_aI[l4] = d12;
               if (Math.abs(p_70091_4_) <= 9.999999747378752E-6D) {
                  return;
               }
            } else {
               if (p_70091_6_ == 0.0D) {
                  return;
               }

               int i5 = EnumFacing.Axis.Z.ordinal();
               double d13 = MathHelper.func_151237_a(p_70091_6_ + this.field_191505_aI[i5], -0.51D, 0.51D);
               p_70091_6_ = d13 - this.field_191505_aI[i5];
               this.field_191505_aI[i5] = d13;
               if (Math.abs(p_70091_6_) <= 9.999999747378752E-6D) {
                  return;
               }
            }
         }

         this.field_70170_p.field_72984_F.func_76320_a("move");
         double d10 = this.field_70165_t;
         double d11 = this.field_70163_u;
         double d1 = this.field_70161_v;
         if (this.field_70134_J) {
            this.field_70134_J = false;
            p_70091_2_ *= 0.25D;
            p_70091_4_ *= 0.05000000074505806D;
            p_70091_6_ *= 0.25D;
            this.field_70159_w = 0.0D;
            this.field_70181_x = 0.0D;
            this.field_70179_y = 0.0D;
         }

         double d2 = p_70091_2_;
         double d3 = p_70091_4_;
         double d4 = p_70091_6_;
         if ((p_70091_1_ == MoverType.SELF || p_70091_1_ == MoverType.PLAYER) && this.field_70122_E && this.func_70093_af() && this instanceof EntityPlayer) {
            for(double d5 = 0.05D; p_70091_2_ != 0.0D && this.field_70170_p.func_184144_a(this, this.func_174813_aQ().func_72317_d(p_70091_2_, (double)(-this.field_70138_W), 0.0D)).isEmpty(); d2 = p_70091_2_) {
               if (p_70091_2_ < 0.05D && p_70091_2_ >= -0.05D) {
                  p_70091_2_ = 0.0D;
               } else if (p_70091_2_ > 0.0D) {
                  p_70091_2_ -= 0.05D;
               } else {
                  p_70091_2_ += 0.05D;
               }
            }

            for(; p_70091_6_ != 0.0D && this.field_70170_p.func_184144_a(this, this.func_174813_aQ().func_72317_d(0.0D, (double)(-this.field_70138_W), p_70091_6_)).isEmpty(); d4 = p_70091_6_) {
               if (p_70091_6_ < 0.05D && p_70091_6_ >= -0.05D) {
                  p_70091_6_ = 0.0D;
               } else if (p_70091_6_ > 0.0D) {
                  p_70091_6_ -= 0.05D;
               } else {
                  p_70091_6_ += 0.05D;
               }
            }

            for(; p_70091_2_ != 0.0D && p_70091_6_ != 0.0D && this.field_70170_p.func_184144_a(this, this.func_174813_aQ().func_72317_d(p_70091_2_, (double)(-this.field_70138_W), p_70091_6_)).isEmpty(); d4 = p_70091_6_) {
               if (p_70091_2_ < 0.05D && p_70091_2_ >= -0.05D) {
                  p_70091_2_ = 0.0D;
               } else if (p_70091_2_ > 0.0D) {
                  p_70091_2_ -= 0.05D;
               } else {
                  p_70091_2_ += 0.05D;
               }

               d2 = p_70091_2_;
               if (p_70091_6_ < 0.05D && p_70091_6_ >= -0.05D) {
                  p_70091_6_ = 0.0D;
               } else if (p_70091_6_ > 0.0D) {
                  p_70091_6_ -= 0.05D;
               } else {
                  p_70091_6_ += 0.05D;
               }
            }
         }

         List<AxisAlignedBB> list1 = this.field_70170_p.func_184144_a(this, this.func_174813_aQ().func_72321_a(p_70091_2_, p_70091_4_, p_70091_6_));
         AxisAlignedBB axisalignedbb = this.func_174813_aQ();
         if (p_70091_4_ != 0.0D) {
            int k = 0;

            for(int l = list1.size(); k < l; ++k) {
               p_70091_4_ = ((AxisAlignedBB)list1.get(k)).func_72323_b(this.func_174813_aQ(), p_70091_4_);
            }

            this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0D, p_70091_4_, 0.0D));
         }

         if (p_70091_2_ != 0.0D) {
            int j5 = 0;

            for(int l5 = list1.size(); j5 < l5; ++j5) {
               p_70091_2_ = ((AxisAlignedBB)list1.get(j5)).func_72316_a(this.func_174813_aQ(), p_70091_2_);
            }

            if (p_70091_2_ != 0.0D) {
               this.func_174826_a(this.func_174813_aQ().func_72317_d(p_70091_2_, 0.0D, 0.0D));
            }
         }

         if (p_70091_6_ != 0.0D) {
            int k5 = 0;

            for(int i6 = list1.size(); k5 < i6; ++k5) {
               p_70091_6_ = ((AxisAlignedBB)list1.get(k5)).func_72322_c(this.func_174813_aQ(), p_70091_6_);
            }

            if (p_70091_6_ != 0.0D) {
               this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0D, 0.0D, p_70091_6_));
            }
         }

         boolean flag = this.field_70122_E || p_70091_4_ != p_70091_4_ && p_70091_4_ < 0.0D;
         if (this.field_70138_W > 0.0F && flag && (d2 != p_70091_2_ || d4 != p_70091_6_)) {
            double d14 = p_70091_2_;
            double d6 = p_70091_4_;
            double d7 = p_70091_6_;
            AxisAlignedBB axisalignedbb1 = this.func_174813_aQ();
            this.func_174826_a(axisalignedbb);
            p_70091_4_ = (double)this.field_70138_W;
            List<AxisAlignedBB> list = this.field_70170_p.func_184144_a(this, this.func_174813_aQ().func_72321_a(d2, p_70091_4_, d4));
            AxisAlignedBB axisalignedbb2 = this.func_174813_aQ();
            AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
            double d8 = p_70091_4_;
            int j1 = 0;

            for(int k1 = list.size(); j1 < k1; ++j1) {
               d8 = ((AxisAlignedBB)list.get(j1)).func_72323_b(axisalignedbb3, d8);
            }

            axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
            double d18 = d2;
            int l1 = 0;

            for(int i2 = list.size(); l1 < i2; ++l1) {
               d18 = ((AxisAlignedBB)list.get(l1)).func_72316_a(axisalignedbb2, d18);
            }

            axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
            double d19 = d4;
            int j2 = 0;

            for(int k2 = list.size(); j2 < k2; ++j2) {
               d19 = ((AxisAlignedBB)list.get(j2)).func_72322_c(axisalignedbb2, d19);
            }

            axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
            AxisAlignedBB axisalignedbb4 = this.func_174813_aQ();
            double d20 = p_70091_4_;
            int l2 = 0;

            for(int i3 = list.size(); l2 < i3; ++l2) {
               d20 = ((AxisAlignedBB)list.get(l2)).func_72323_b(axisalignedbb4, d20);
            }

            axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
            double d21 = d2;
            int j3 = 0;

            for(int k3 = list.size(); j3 < k3; ++j3) {
               d21 = ((AxisAlignedBB)list.get(j3)).func_72316_a(axisalignedbb4, d21);
            }

            axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
            double d22 = d4;
            int l3 = 0;

            for(int i4 = list.size(); l3 < i4; ++l3) {
               d22 = ((AxisAlignedBB)list.get(l3)).func_72322_c(axisalignedbb4, d22);
            }

            axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
            double d23 = d18 * d18 + d19 * d19;
            double d9 = d21 * d21 + d22 * d22;
            if (d23 > d9) {
               p_70091_2_ = d18;
               p_70091_6_ = d19;
               p_70091_4_ = -d8;
               this.func_174826_a(axisalignedbb2);
            } else {
               p_70091_2_ = d21;
               p_70091_6_ = d22;
               p_70091_4_ = -d20;
               this.func_174826_a(axisalignedbb4);
            }

            int j4 = 0;

            for(int k4 = list.size(); j4 < k4; ++j4) {
               p_70091_4_ = ((AxisAlignedBB)list.get(j4)).func_72323_b(this.func_174813_aQ(), p_70091_4_);
            }

            this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0D, p_70091_4_, 0.0D));
            if (d14 * d14 + d7 * d7 >= p_70091_2_ * p_70091_2_ + p_70091_6_ * p_70091_6_) {
               p_70091_2_ = d14;
               p_70091_4_ = d6;
               p_70091_6_ = d7;
               this.func_174826_a(axisalignedbb1);
            }
         }

         this.field_70170_p.field_72984_F.func_76319_b();
         this.field_70170_p.field_72984_F.func_76320_a("rest");
         this.func_174829_m();
         this.field_70123_F = d2 != p_70091_2_ || d4 != p_70091_6_;
         this.field_70124_G = p_70091_4_ != p_70091_4_;
         this.field_70122_E = this.field_70124_G && d3 < 0.0D;
         this.field_70132_H = this.field_70123_F || this.field_70124_G;
         int j6 = MathHelper.func_76128_c(this.field_70165_t);
         int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
         int k6 = MathHelper.func_76128_c(this.field_70161_v);
         BlockPos blockpos = new BlockPos(j6, i1, k6);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         if (iblockstate.func_185904_a() == Material.field_151579_a) {
            BlockPos blockpos1 = blockpos.func_177977_b();
            IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
            Block block1 = iblockstate1.func_177230_c();
            if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
               iblockstate = iblockstate1;
               blockpos = blockpos1;
            }
         }

         this.func_184231_a(p_70091_4_, this.field_70122_E, iblockstate, blockpos);
         if (d2 != p_70091_2_) {
            this.field_70159_w = 0.0D;
         }

         if (d4 != p_70091_6_) {
            this.field_70179_y = 0.0D;
         }

         Block block = iblockstate.func_177230_c();
         if (d3 != p_70091_4_) {
            block.func_176216_a(this.field_70170_p, this);
         }

         if (this.func_70041_e_() && (!this.field_70122_E || !this.func_70093_af() || !(this instanceof EntityPlayer)) && !this.func_184218_aH()) {
            double d15 = this.field_70165_t - d10;
            double d16 = this.field_70163_u - d11;
            double d17 = this.field_70161_v - d1;
            if (block != Blocks.field_150468_ap) {
               d16 = 0.0D;
            }

            if (block != null && this.field_70122_E) {
               block.func_176199_a(this.field_70170_p, blockpos, this);
            }

            this.field_70140_Q = (float)((double)this.field_70140_Q + (double)MathHelper.func_76133_a(d15 * d15 + d17 * d17) * 0.6D);
            this.field_82151_R = (float)((double)this.field_82151_R + (double)MathHelper.func_76133_a(d15 * d15 + d16 * d16 + d17 * d17) * 0.6D);
            if (this.field_82151_R > (float)this.field_70150_b && iblockstate.func_185904_a() != Material.field_151579_a) {
               this.field_70150_b = (int)this.field_82151_R + 1;
               if (this.func_70090_H()) {
                  Entity entity = this.func_184207_aI() && this.func_184179_bs() != null ? this.func_184179_bs() : this;
                  float f = entity == this ? 0.35F : 0.4F;
                  float f1 = MathHelper.func_76133_a(entity.field_70159_w * entity.field_70159_w * 0.20000000298023224D + entity.field_70181_x * entity.field_70181_x + entity.field_70179_y * entity.field_70179_y * 0.20000000298023224D) * f;
                  if (f1 > 1.0F) {
                     f1 = 1.0F;
                  }

                  this.func_184185_a(this.func_184184_Z(), f1, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
               } else {
                  this.func_180429_a(blockpos, block);
               }
            } else if (this.field_82151_R > this.field_191959_ay && this.func_191957_ae() && iblockstate.func_185904_a() == Material.field_151579_a) {
               this.field_191959_ay = this.func_191954_d(this.field_82151_R);
            }
         }

         try {
            this.func_145775_I();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
            this.func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
         }

         boolean flag1 = this.func_70026_G();
         if (this.field_70170_p.func_147470_e(this.func_174813_aQ().func_186664_h(0.001D))) {
            this.func_70081_e(1);
            if (!flag1) {
               ++this.field_190534_ay;
               if (this.field_190534_ay == 0) {
                  this.func_70015_d(8);
               }
            }
         } else if (this.field_190534_ay <= 0) {
            this.field_190534_ay = -this.func_190531_bD();
         }

         if (flag1 && this.func_70027_ad()) {
            this.func_184185_a(SoundEvents.field_187541_bC, 0.7F, 1.6F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
            this.field_190534_ay = -this.func_190531_bD();
         }

         this.field_70170_p.field_72984_F.func_76319_b();
      }
   }

   public void func_174829_m() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      this.field_70165_t = (axisalignedbb.field_72340_a + axisalignedbb.field_72336_d) / 2.0D;
      this.field_70163_u = axisalignedbb.field_72338_b;
      this.field_70161_v = (axisalignedbb.field_72339_c + axisalignedbb.field_72334_f) / 2.0D;
   }

   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_187549_bG;
   }

   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_187547_bF;
   }

   protected void func_145775_I() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(axisalignedbb.field_72340_a + 0.001D, axisalignedbb.field_72338_b + 0.001D, axisalignedbb.field_72339_c + 0.001D);
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.func_185345_c(axisalignedbb.field_72336_d - 0.001D, axisalignedbb.field_72337_e - 0.001D, axisalignedbb.field_72334_f - 0.001D);
      BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.func_185346_s();
      if (this.field_70170_p.func_175707_a(blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1)) {
         for(int i = blockpos$pooledmutableblockpos.func_177958_n(); i <= blockpos$pooledmutableblockpos1.func_177958_n(); ++i) {
            for(int j = blockpos$pooledmutableblockpos.func_177956_o(); j <= blockpos$pooledmutableblockpos1.func_177956_o(); ++j) {
               for(int k = blockpos$pooledmutableblockpos.func_177952_p(); k <= blockpos$pooledmutableblockpos1.func_177952_p(); ++k) {
                  blockpos$pooledmutableblockpos2.func_181079_c(i, j, k);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos2);

                  try {
                     iblockstate.func_177230_c().func_180634_a(this.field_70170_p, blockpos$pooledmutableblockpos2, iblockstate, this);
                     this.func_191955_a(iblockstate);
                  } catch (Throwable throwable) {
                     CrashReport crashreport = CrashReport.func_85055_a(throwable, "Colliding entity with block");
                     CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block being collided with");
                     CrashReportCategory.func_175750_a(crashreportcategory, blockpos$pooledmutableblockpos2, iblockstate);
                     throw new ReportedException(crashreport);
                  }
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.func_185344_t();
      blockpos$pooledmutableblockpos1.func_185344_t();
      blockpos$pooledmutableblockpos2.func_185344_t();
   }

   protected void func_191955_a(IBlockState p_191955_1_) {
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      SoundType soundtype = p_180429_2_.func_185467_w();
      if (this.field_70170_p.func_180495_p(p_180429_1_.func_177984_a()).func_177230_c() == Blocks.field_150431_aC) {
         soundtype = Blocks.field_150431_aC.func_185467_w();
         this.func_184185_a(soundtype.func_185844_d(), soundtype.func_185843_a() * 0.15F, soundtype.func_185847_b());
      } else if (!p_180429_2_.func_176223_P().func_185904_a().func_76224_d()) {
         this.func_184185_a(soundtype.func_185844_d(), soundtype.func_185843_a() * 0.15F, soundtype.func_185847_b());
      }

   }

   protected float func_191954_d(float p_191954_1_) {
      return 0.0F;
   }

   protected boolean func_191957_ae() {
      return false;
   }

   public void func_184185_a(SoundEvent p_184185_1_, float p_184185_2_, float p_184185_3_) {
      if (!this.func_174814_R()) {
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, p_184185_1_, this.func_184176_by(), p_184185_2_, p_184185_3_);
      }

   }

   public boolean func_174814_R() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184234_aB)).booleanValue();
   }

   public void func_174810_b(boolean p_174810_1_) {
      this.field_70180_af.func_187227_b(field_184234_aB, Boolean.valueOf(p_174810_1_));
   }

   public boolean func_189652_ae() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_189655_aD)).booleanValue();
   }

   public void func_189654_d(boolean p_189654_1_) {
      this.field_70180_af.func_187227_b(field_189655_aD, Boolean.valueOf(p_189654_1_));
   }

   protected boolean func_70041_e_() {
      return true;
   }

   protected void func_184231_a(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
      if (p_184231_3_) {
         if (this.field_70143_R > 0.0F) {
            p_184231_4_.func_177230_c().func_180658_a(this.field_70170_p, p_184231_5_, this, this.field_70143_R);
         }

         this.field_70143_R = 0.0F;
      } else if (p_184231_1_ < 0.0D) {
         this.field_70143_R = (float)((double)this.field_70143_R - p_184231_1_);
      }

   }

   @Nullable
   public AxisAlignedBB func_70046_E() {
      return null;
   }

   protected void func_70081_e(int p_70081_1_) {
      if (!this.field_70178_ae) {
         this.func_70097_a(DamageSource.field_76372_a, (float)p_70081_1_);
      }

   }

   public final boolean func_70045_F() {
      return this.field_70178_ae;
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      if (this.func_184207_aI()) {
         for(Entity entity : this.func_184188_bt()) {
            entity.func_180430_e(p_180430_1_, p_180430_2_);
         }
      }

   }

   public boolean func_70026_G() {
      if (this.field_70171_ac) {
         return true;
      } else {
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         if (!this.field_70170_p.func_175727_C(blockpos$pooledmutableblockpos) && !this.field_70170_p.func_175727_C(blockpos$pooledmutableblockpos.func_189532_c(this.field_70165_t, this.field_70163_u + (double)this.field_70131_O, this.field_70161_v))) {
            blockpos$pooledmutableblockpos.func_185344_t();
            return false;
         } else {
            blockpos$pooledmutableblockpos.func_185344_t();
            return true;
         }
      }
   }

   public boolean func_70090_H() {
      return this.field_70171_ac;
   }

   public boolean func_191953_am() {
      return this.field_70170_p.func_72918_a(this.func_174813_aQ().func_72314_b(0.0D, -20.0D, 0.0D).func_186664_h(0.001D), Material.field_151586_h, this);
   }

   public boolean func_70072_I() {
      if (this.func_184187_bx() instanceof EntityBoat) {
         this.field_70171_ac = false;
      } else if (this.field_70170_p.func_72918_a(this.func_174813_aQ().func_72314_b(0.0D, -0.4000000059604645D, 0.0D).func_186664_h(0.001D), Material.field_151586_h, this)) {
         if (!this.field_70171_ac && !this.field_70148_d) {
            this.func_71061_d_();
         }

         this.field_70143_R = 0.0F;
         this.field_70171_ac = true;
         this.func_70066_B();
      } else {
         this.field_70171_ac = false;
      }

      return this.field_70171_ac;
   }

   protected void func_71061_d_() {
      Entity entity = this.func_184207_aI() && this.func_184179_bs() != null ? this.func_184179_bs() : this;
      float f = entity == this ? 0.2F : 0.9F;
      float f1 = MathHelper.func_76133_a(entity.field_70159_w * entity.field_70159_w * 0.20000000298023224D + entity.field_70181_x * entity.field_70181_x + entity.field_70179_y * entity.field_70179_y * 0.20000000298023224D) * f;
      if (f1 > 1.0F) {
         f1 = 1.0F;
      }

      this.func_184185_a(this.func_184181_aa(), f1, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
      float f2 = (float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);

      for(int i = 0; (float)i < 1.0F + this.field_70130_N * 20.0F; ++i) {
         float f3 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         float f4 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + (double)f3, (double)(f2 + 1.0F), this.field_70161_v + (double)f4, this.field_70159_w, this.field_70181_x - (double)(this.field_70146_Z.nextFloat() * 0.2F), this.field_70179_y);
      }

      for(int j = 0; (float)j < 1.0F + this.field_70130_N * 20.0F; ++j) {
         float f5 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         float f6 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + (double)f5, (double)(f2 + 1.0F), this.field_70161_v + (double)f6, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      }

   }

   public void func_174830_Y() {
      if (this.func_70051_ag() && !this.func_70090_H()) {
         this.func_174808_Z();
      }

   }

   protected void func_174808_Z() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
      if (iblockstate.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
         this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + ((double)this.field_70146_Z.nextFloat() - 0.5D) * (double)this.field_70130_N, this.func_174813_aQ().field_72338_b + 0.1D, this.field_70161_v + ((double)this.field_70146_Z.nextFloat() - 0.5D) * (double)this.field_70130_N, -this.field_70159_w * 4.0D, 1.5D, -this.field_70179_y * 4.0D, Block.func_176210_f(iblockstate));
      }

   }

   public boolean func_70055_a(Material p_70055_1_) {
      if (this.func_184187_bx() instanceof EntityBoat) {
         return false;
      } else {
         double d0 = this.field_70163_u + (double)this.func_70047_e();
         BlockPos blockpos = new BlockPos(this.field_70165_t, d0, this.field_70161_v);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         if (iblockstate.func_185904_a() == p_70055_1_) {
            float f = BlockLiquid.func_149801_b(iblockstate.func_177230_c().func_176201_c(iblockstate)) - 0.11111111F;
            float f1 = (float)(blockpos.func_177956_o() + 1) - f;
            boolean flag = d0 < (double)f1;
            return !flag && this instanceof EntityPlayer ? false : flag;
         } else {
            return false;
         }
      }
   }

   public boolean func_180799_ab() {
      return this.field_70170_p.func_72875_a(this.func_174813_aQ().func_72314_b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.field_151587_i);
   }

   public void func_191958_b(float p_191958_1_, float p_191958_2_, float p_191958_3_, float p_191958_4_) {
      float f = p_191958_1_ * p_191958_1_ + p_191958_2_ * p_191958_2_ + p_191958_3_ * p_191958_3_;
      if (f >= 1.0E-4F) {
         f = MathHelper.func_76129_c(f);
         if (f < 1.0F) {
            f = 1.0F;
         }

         f = p_191958_4_ / f;
         p_191958_1_ = p_191958_1_ * f;
         p_191958_2_ = p_191958_2_ * f;
         p_191958_3_ = p_191958_3_ * f;
         float f1 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
         float f2 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
         this.field_70159_w += (double)(p_191958_1_ * f2 - p_191958_3_ * f1);
         this.field_70181_x += (double)p_191958_2_;
         this.field_70179_y += (double)(p_191958_3_ * f2 + p_191958_1_ * f1);
      }
   }

   public int func_70070_b() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(this.field_70165_t), 0, MathHelper.func_76128_c(this.field_70161_v));
      if (this.field_70170_p.func_175667_e(blockpos$mutableblockpos)) {
         blockpos$mutableblockpos.func_185336_p(MathHelper.func_76128_c(this.field_70163_u + (double)this.func_70047_e()));
         return this.field_70170_p.func_175626_b(blockpos$mutableblockpos, 0);
      } else {
         return 0;
      }
   }

   public float func_70013_c() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(this.field_70165_t), 0, MathHelper.func_76128_c(this.field_70161_v));
      if (this.field_70170_p.func_175667_e(blockpos$mutableblockpos)) {
         blockpos$mutableblockpos.func_185336_p(MathHelper.func_76128_c(this.field_70163_u + (double)this.func_70047_e()));
         return this.field_70170_p.func_175724_o(blockpos$mutableblockpos);
      } else {
         return 0.0F;
      }
   }

   public void func_70029_a(World p_70029_1_) {
      this.field_70170_p = p_70029_1_;
   }

   public void func_70080_a(double p_70080_1_, double p_70080_3_, double p_70080_5_, float p_70080_7_, float p_70080_8_) {
      this.field_70165_t = MathHelper.func_151237_a(p_70080_1_, -3.0E7D, 3.0E7D);
      this.field_70163_u = p_70080_3_;
      this.field_70161_v = MathHelper.func_151237_a(p_70080_5_, -3.0E7D, 3.0E7D);
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      p_70080_8_ = MathHelper.func_76131_a(p_70080_8_, -90.0F, 90.0F);
      this.field_70177_z = p_70080_7_;
      this.field_70125_A = p_70080_8_;
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      double d0 = (double)(this.field_70126_B - p_70080_7_);
      if (d0 < -180.0D) {
         this.field_70126_B += 360.0F;
      }

      if (d0 >= 180.0D) {
         this.field_70126_B -= 360.0F;
      }

      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.func_70101_b(p_70080_7_, p_70080_8_);
   }

   public void func_174828_a(BlockPos p_174828_1_, float p_174828_2_, float p_174828_3_) {
      this.func_70012_b((double)p_174828_1_.func_177958_n() + 0.5D, (double)p_174828_1_.func_177956_o(), (double)p_174828_1_.func_177952_p() + 0.5D, p_174828_2_, p_174828_3_);
   }

   public void func_70012_b(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_) {
      this.field_70165_t = p_70012_1_;
      this.field_70163_u = p_70012_3_;
      this.field_70161_v = p_70012_5_;
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      this.field_70177_z = p_70012_7_;
      this.field_70125_A = p_70012_8_;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public float func_70032_d(Entity p_70032_1_) {
      float f = (float)(this.field_70165_t - p_70032_1_.field_70165_t);
      float f1 = (float)(this.field_70163_u - p_70032_1_.field_70163_u);
      float f2 = (float)(this.field_70161_v - p_70032_1_.field_70161_v);
      return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
   }

   public double func_70092_e(double p_70092_1_, double p_70092_3_, double p_70092_5_) {
      double d0 = this.field_70165_t - p_70092_1_;
      double d1 = this.field_70163_u - p_70092_3_;
      double d2 = this.field_70161_v - p_70092_5_;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double func_174818_b(BlockPos p_174818_1_) {
      return p_174818_1_.func_177954_c(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public double func_174831_c(BlockPos p_174831_1_) {
      return p_174831_1_.func_177957_d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public double func_70011_f(double p_70011_1_, double p_70011_3_, double p_70011_5_) {
      double d0 = this.field_70165_t - p_70011_1_;
      double d1 = this.field_70163_u - p_70011_3_;
      double d2 = this.field_70161_v - p_70011_5_;
      return (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double func_70068_e(Entity p_70068_1_) {
      double d0 = this.field_70165_t - p_70068_1_.field_70165_t;
      double d1 = this.field_70163_u - p_70068_1_.field_70163_u;
      double d2 = this.field_70161_v - p_70068_1_.field_70161_v;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void func_70100_b_(EntityPlayer p_70100_1_) {
   }

   public void func_70108_f(Entity p_70108_1_) {
      if (!this.func_184223_x(p_70108_1_)) {
         if (!p_70108_1_.field_70145_X && !this.field_70145_X) {
            double d0 = p_70108_1_.field_70165_t - this.field_70165_t;
            double d1 = p_70108_1_.field_70161_v - this.field_70161_v;
            double d2 = MathHelper.func_76132_a(d0, d1);
            if (d2 >= 0.009999999776482582D) {
               d2 = (double)MathHelper.func_76133_a(d2);
               d0 = d0 / d2;
               d1 = d1 / d2;
               double d3 = 1.0D / d2;
               if (d3 > 1.0D) {
                  d3 = 1.0D;
               }

               d0 = d0 * d3;
               d1 = d1 * d3;
               d0 = d0 * 0.05000000074505806D;
               d1 = d1 * 0.05000000074505806D;
               d0 = d0 * (double)(1.0F - this.field_70144_Y);
               d1 = d1 * (double)(1.0F - this.field_70144_Y);
               if (!this.func_184207_aI()) {
                  this.func_70024_g(-d0, 0.0D, -d1);
               }

               if (!p_70108_1_.func_184207_aI()) {
                  p_70108_1_.func_70024_g(d0, 0.0D, d1);
               }
            }

         }
      }
   }

   public void func_70024_g(double p_70024_1_, double p_70024_3_, double p_70024_5_) {
      this.field_70159_w += p_70024_1_;
      this.field_70181_x += p_70024_3_;
      this.field_70179_y += p_70024_5_;
      this.field_70160_al = true;
   }

   protected void func_70018_K() {
      this.field_70133_I = true;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         this.func_70018_K();
         return false;
      }
   }

   public Vec3d func_70676_i(float p_70676_1_) {
      if (p_70676_1_ == 1.0F) {
         return this.func_174806_f(this.field_70125_A, this.field_70177_z);
      } else {
         float f = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * p_70676_1_;
         float f1 = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * p_70676_1_;
         return this.func_174806_f(f, f1);
      }
   }

   protected final Vec3d func_174806_f(float p_174806_1_, float p_174806_2_) {
      float f = MathHelper.func_76134_b(-p_174806_2_ * 0.017453292F - 3.1415927F);
      float f1 = MathHelper.func_76126_a(-p_174806_2_ * 0.017453292F - 3.1415927F);
      float f2 = -MathHelper.func_76134_b(-p_174806_1_ * 0.017453292F);
      float f3 = MathHelper.func_76126_a(-p_174806_1_ * 0.017453292F);
      return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public Vec3d func_174824_e(float p_174824_1_) {
      if (p_174824_1_ == 1.0F) {
         return new Vec3d(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
      } else {
         double d0 = this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)p_174824_1_;
         double d1 = this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)p_174824_1_ + (double)this.func_70047_e();
         double d2 = this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)p_174824_1_;
         return new Vec3d(d0, d1, d2);
      }
   }

   @Nullable
   public RayTraceResult func_174822_a(double p_174822_1_, float p_174822_3_) {
      Vec3d vec3d = this.func_174824_e(p_174822_3_);
      Vec3d vec3d1 = this.func_70676_i(p_174822_3_);
      Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * p_174822_1_, vec3d1.field_72448_b * p_174822_1_, vec3d1.field_72449_c * p_174822_1_);
      return this.field_70170_p.func_147447_a(vec3d, vec3d2, false, false, true);
   }

   public boolean func_70067_L() {
      return false;
   }

   public boolean func_70104_M() {
      return false;
   }

   public void func_191956_a(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
      if (p_191956_1_ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_192123_c.func_192211_a((EntityPlayerMP)p_191956_1_, this, p_191956_3_);
      }

   }

   public boolean func_145770_h(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
      double d0 = this.field_70165_t - p_145770_1_;
      double d1 = this.field_70163_u - p_145770_3_;
      double d2 = this.field_70161_v - p_145770_5_;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return this.func_70112_a(d3);
   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = this.func_174813_aQ().func_72320_b();
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * field_70155_l;
      return p_70112_1_ < d0 * d0;
   }

   public boolean func_184198_c(NBTTagCompound p_184198_1_) {
      String s = this.func_70022_Q();
      if (!this.field_70128_L && s != null) {
         p_184198_1_.func_74778_a("id", s);
         this.func_189511_e(p_184198_1_);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_70039_c(NBTTagCompound p_70039_1_) {
      String s = this.func_70022_Q();
      if (!this.field_70128_L && s != null && !this.func_184218_aH()) {
         p_70039_1_.func_74778_a("id", s);
         this.func_189511_e(p_70039_1_);
         return true;
      } else {
         return false;
      }
   }

   public static void func_190533_a(DataFixer p_190533_0_) {
      p_190533_0_.func_188258_a(FixTypes.ENTITY, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (p_188266_2_.func_150297_b("Passengers", 9)) {
               NBTTagList nbttaglist = p_188266_2_.func_150295_c("Passengers", 10);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  nbttaglist.func_150304_a(i, p_188266_1_.func_188251_a(FixTypes.ENTITY, nbttaglist.func_150305_b(i), p_188266_3_));
               }
            }

            return p_188266_2_;
         }
      });
   }

   public NBTTagCompound func_189511_e(NBTTagCompound p_189511_1_) {
      try {
         p_189511_1_.func_74782_a("Pos", this.func_70087_a(this.field_70165_t, this.field_70163_u, this.field_70161_v));
         p_189511_1_.func_74782_a("Motion", this.func_70087_a(this.field_70159_w, this.field_70181_x, this.field_70179_y));
         p_189511_1_.func_74782_a("Rotation", this.func_70049_a(this.field_70177_z, this.field_70125_A));
         p_189511_1_.func_74776_a("FallDistance", this.field_70143_R);
         p_189511_1_.func_74777_a("Fire", (short)this.field_190534_ay);
         p_189511_1_.func_74777_a("Air", (short)this.func_70086_ai());
         p_189511_1_.func_74757_a("OnGround", this.field_70122_E);
         p_189511_1_.func_74768_a("Dimension", this.field_71093_bK);
         p_189511_1_.func_74757_a("Invulnerable", this.field_83001_bt);
         p_189511_1_.func_74768_a("PortalCooldown", this.field_71088_bW);
         p_189511_1_.func_186854_a("UUID", this.func_110124_au());
         if (this.func_145818_k_()) {
            p_189511_1_.func_74778_a("CustomName", this.func_95999_t());
         }

         if (this.func_174833_aM()) {
            p_189511_1_.func_74757_a("CustomNameVisible", this.func_174833_aM());
         }

         this.field_174837_as.func_179670_b(p_189511_1_);
         if (this.func_174814_R()) {
            p_189511_1_.func_74757_a("Silent", this.func_174814_R());
         }

         if (this.func_189652_ae()) {
            p_189511_1_.func_74757_a("NoGravity", this.func_189652_ae());
         }

         if (this.field_184238_ar) {
            p_189511_1_.func_74757_a("Glowing", this.field_184238_ar);
         }

         if (!this.field_184236_aF.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(String s : this.field_184236_aF) {
               nbttaglist.func_74742_a(new NBTTagString(s));
            }

            p_189511_1_.func_74782_a("Tags", nbttaglist);
         }

         this.func_70014_b(p_189511_1_);
         if (this.func_184207_aI()) {
            NBTTagList nbttaglist1 = new NBTTagList();

            for(Entity entity : this.func_184188_bt()) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               if (entity.func_184198_c(nbttagcompound)) {
                  nbttaglist1.func_74742_a(nbttagcompound);
               }
            }

            if (!nbttaglist1.func_82582_d()) {
               p_189511_1_.func_74782_a("Passengers", nbttaglist1);
            }
         }

         return p_189511_1_;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Saving entity NBT");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being saved");
         this.func_85029_a(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   public void func_70020_e(NBTTagCompound p_70020_1_) {
      try {
         NBTTagList nbttaglist = p_70020_1_.func_150295_c("Pos", 6);
         NBTTagList nbttaglist2 = p_70020_1_.func_150295_c("Motion", 6);
         NBTTagList nbttaglist3 = p_70020_1_.func_150295_c("Rotation", 5);
         this.field_70159_w = nbttaglist2.func_150309_d(0);
         this.field_70181_x = nbttaglist2.func_150309_d(1);
         this.field_70179_y = nbttaglist2.func_150309_d(2);
         if (Math.abs(this.field_70159_w) > 10.0D) {
            this.field_70159_w = 0.0D;
         }

         if (Math.abs(this.field_70181_x) > 10.0D) {
            this.field_70181_x = 0.0D;
         }

         if (Math.abs(this.field_70179_y) > 10.0D) {
            this.field_70179_y = 0.0D;
         }

         this.field_70165_t = nbttaglist.func_150309_d(0);
         this.field_70163_u = nbttaglist.func_150309_d(1);
         this.field_70161_v = nbttaglist.func_150309_d(2);
         this.field_70142_S = this.field_70165_t;
         this.field_70137_T = this.field_70163_u;
         this.field_70136_U = this.field_70161_v;
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         this.field_70177_z = nbttaglist3.func_150308_e(0);
         this.field_70125_A = nbttaglist3.func_150308_e(1);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
         this.func_70034_d(this.field_70177_z);
         this.func_181013_g(this.field_70177_z);
         this.field_70143_R = p_70020_1_.func_74760_g("FallDistance");
         this.field_190534_ay = p_70020_1_.func_74765_d("Fire");
         this.func_70050_g(p_70020_1_.func_74765_d("Air"));
         this.field_70122_E = p_70020_1_.func_74767_n("OnGround");
         if (p_70020_1_.func_74764_b("Dimension")) {
            this.field_71093_bK = p_70020_1_.func_74762_e("Dimension");
         }

         this.field_83001_bt = p_70020_1_.func_74767_n("Invulnerable");
         this.field_71088_bW = p_70020_1_.func_74762_e("PortalCooldown");
         if (p_70020_1_.func_186855_b("UUID")) {
            this.field_96093_i = p_70020_1_.func_186857_a("UUID");
            this.field_189513_ar = this.field_96093_i.toString();
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         if (p_70020_1_.func_150297_b("CustomName", 8)) {
            this.func_96094_a(p_70020_1_.func_74779_i("CustomName"));
         }

         this.func_174805_g(p_70020_1_.func_74767_n("CustomNameVisible"));
         this.field_174837_as.func_179668_a(p_70020_1_);
         this.func_174810_b(p_70020_1_.func_74767_n("Silent"));
         this.func_189654_d(p_70020_1_.func_74767_n("NoGravity"));
         this.func_184195_f(p_70020_1_.func_74767_n("Glowing"));
         if (p_70020_1_.func_150297_b("Tags", 9)) {
            this.field_184236_aF.clear();
            NBTTagList nbttaglist1 = p_70020_1_.func_150295_c("Tags", 8);
            int i = Math.min(nbttaglist1.func_74745_c(), 1024);

            for(int j = 0; j < i; ++j) {
               this.field_184236_aF.add(nbttaglist1.func_150307_f(j));
            }
         }

         this.func_70037_a(p_70020_1_);
         if (this.func_142008_O()) {
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Loading entity NBT");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being loaded");
         this.func_85029_a(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   protected boolean func_142008_O() {
      return true;
   }

   @Nullable
   protected final String func_70022_Q() {
      ResourceLocation resourcelocation = EntityList.func_191301_a(this);
      return resourcelocation == null ? null : resourcelocation.toString();
   }

   protected abstract void func_70037_a(NBTTagCompound var1);

   protected abstract void func_70014_b(NBTTagCompound var1);

   protected NBTTagList func_70087_a(double... p_70087_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d0 : p_70087_1_) {
         nbttaglist.func_74742_a(new NBTTagDouble(d0));
      }

      return nbttaglist;
   }

   protected NBTTagList func_70049_a(float... p_70049_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(float f : p_70049_1_) {
         nbttaglist.func_74742_a(new NBTTagFloat(f));
      }

      return nbttaglist;
   }

   @Nullable
   public EntityItem func_145779_a(Item p_145779_1_, int p_145779_2_) {
      return this.func_145778_a(p_145779_1_, p_145779_2_, 0.0F);
   }

   @Nullable
   public EntityItem func_145778_a(Item p_145778_1_, int p_145778_2_, float p_145778_3_) {
      return this.func_70099_a(new ItemStack(p_145778_1_, p_145778_2_, 0), p_145778_3_);
   }

   @Nullable
   public EntityItem func_70099_a(ItemStack p_70099_1_, float p_70099_2_) {
      if (p_70099_1_.func_190926_b()) {
         return null;
      } else {
         EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)p_70099_2_, this.field_70161_v, p_70099_1_);
         entityitem.func_174869_p();
         this.field_70170_p.func_72838_d(entityitem);
         return entityitem;
      }
   }

   public boolean func_70089_S() {
      return !this.field_70128_L;
   }

   public boolean func_70094_T() {
      if (this.field_70145_X) {
         return false;
      } else {
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();

         for(int i = 0; i < 8; ++i) {
            int j = MathHelper.func_76128_c(this.field_70163_u + (double)(((float)((i >> 0) % 2) - 0.5F) * 0.1F) + (double)this.func_70047_e());
            int k = MathHelper.func_76128_c(this.field_70165_t + (double)(((float)((i >> 1) % 2) - 0.5F) * this.field_70130_N * 0.8F));
            int l = MathHelper.func_76128_c(this.field_70161_v + (double)(((float)((i >> 2) % 2) - 0.5F) * this.field_70130_N * 0.8F));
            if (blockpos$pooledmutableblockpos.func_177958_n() != k || blockpos$pooledmutableblockpos.func_177956_o() != j || blockpos$pooledmutableblockpos.func_177952_p() != l) {
               blockpos$pooledmutableblockpos.func_181079_c(k, j, l);
               if (this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos).func_191058_s()) {
                  blockpos$pooledmutableblockpos.func_185344_t();
                  return true;
               }
            }
         }

         blockpos$pooledmutableblockpos.func_185344_t();
         return false;
      }
   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      return false;
   }

   @Nullable
   public AxisAlignedBB func_70114_g(Entity p_70114_1_) {
      return null;
   }

   public void func_70098_U() {
      Entity entity = this.func_184187_bx();
      if (this.func_184218_aH() && entity.field_70128_L) {
         this.func_184210_p();
      } else {
         this.field_70159_w = 0.0D;
         this.field_70181_x = 0.0D;
         this.field_70179_y = 0.0D;
         this.func_70071_h_();
         if (this.func_184218_aH()) {
            entity.func_184232_k(this);
         }
      }
   }

   public void func_184232_k(Entity p_184232_1_) {
      if (this.func_184196_w(p_184232_1_)) {
         p_184232_1_.func_70107_b(this.field_70165_t, this.field_70163_u + this.func_70042_X() + p_184232_1_.func_70033_W(), this.field_70161_v);
      }
   }

   public void func_184190_l(Entity p_184190_1_) {
   }

   public double func_70033_W() {
      return 0.0D;
   }

   public double func_70042_X() {
      return (double)this.field_70131_O * 0.75D;
   }

   public boolean func_184220_m(Entity p_184220_1_) {
      return this.func_184205_a(p_184220_1_, false);
   }

   public boolean func_184205_a(Entity p_184205_1_, boolean p_184205_2_) {
      for(Entity entity = p_184205_1_; entity.field_184239_as != null; entity = entity.field_184239_as) {
         if (entity.field_184239_as == this) {
            return false;
         }
      }

      if (p_184205_2_ || this.func_184228_n(p_184205_1_) && p_184205_1_.func_184219_q(this)) {
         if (this.func_184218_aH()) {
            this.func_184210_p();
         }

         this.field_184239_as = p_184205_1_;
         this.field_184239_as.func_184200_o(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_184228_n(Entity p_184228_1_) {
      return this.field_184245_j <= 0;
   }

   public void func_184226_ay() {
      for(int i = this.field_184244_h.size() - 1; i >= 0; --i) {
         ((Entity)this.field_184244_h.get(i)).func_184210_p();
      }

   }

   public void func_184210_p() {
      if (this.field_184239_as != null) {
         Entity entity = this.field_184239_as;
         this.field_184239_as = null;
         entity.func_184225_p(this);
      }

   }

   protected void func_184200_o(Entity p_184200_1_) {
      if (p_184200_1_.func_184187_bx() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (!this.field_70170_p.field_72995_K && p_184200_1_ instanceof EntityPlayer && !(this.func_184179_bs() instanceof EntityPlayer)) {
            this.field_184244_h.add(0, p_184200_1_);
         } else {
            this.field_184244_h.add(p_184200_1_);
         }

      }
   }

   protected void func_184225_p(Entity p_184225_1_) {
      if (p_184225_1_.func_184187_bx() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         this.field_184244_h.remove(p_184225_1_);
         p_184225_1_.field_184245_j = 60;
      }
   }

   protected boolean func_184219_q(Entity p_184219_1_) {
      return this.func_184188_bt().size() < 1;
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.func_70107_b(p_180426_1_, p_180426_3_, p_180426_5_);
      this.func_70101_b(p_180426_7_, p_180426_8_);
   }

   public float func_70111_Y() {
      return 0.0F;
   }

   public Vec3d func_70040_Z() {
      return this.func_174806_f(this.field_70125_A, this.field_70177_z);
   }

   public Vec2f func_189653_aC() {
      return new Vec2f(this.field_70125_A, this.field_70177_z);
   }

   public Vec3d func_189651_aD() {
      return Vec3d.func_189984_a(this.func_189653_aC());
   }

   public void func_181015_d(BlockPos p_181015_1_) {
      if (this.field_71088_bW > 0) {
         this.field_71088_bW = this.func_82147_ab();
      } else {
         if (!this.field_70170_p.field_72995_K && !p_181015_1_.equals(this.field_181016_an)) {
            this.field_181016_an = new BlockPos(p_181015_1_);
            BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.field_150427_aO.func_181089_f(this.field_70170_p, this.field_181016_an);
            double d0 = blockpattern$patternhelper.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? (double)blockpattern$patternhelper.func_181117_a().func_177952_p() : (double)blockpattern$patternhelper.func_181117_a().func_177958_n();
            double d1 = blockpattern$patternhelper.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? this.field_70161_v : this.field_70165_t;
            d1 = Math.abs(MathHelper.func_181160_c(d1 - (double)(blockpattern$patternhelper.func_177669_b().func_176746_e().func_176743_c() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), d0, d0 - (double)blockpattern$patternhelper.func_181118_d()));
            double d2 = MathHelper.func_181160_c(this.field_70163_u - 1.0D, (double)blockpattern$patternhelper.func_181117_a().func_177956_o(), (double)(blockpattern$patternhelper.func_181117_a().func_177956_o() - blockpattern$patternhelper.func_181119_e()));
            this.field_181017_ao = new Vec3d(d1, d2, 0.0D);
            this.field_181018_ap = blockpattern$patternhelper.func_177669_b();
         }

         this.field_71087_bX = true;
      }
   }

   public int func_82147_ab() {
      return 300;
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
   }

   public void func_70103_a(byte p_70103_1_) {
   }

   public void func_70057_ab() {
   }

   public Iterable<ItemStack> func_184214_aD() {
      return field_190535_b;
   }

   public Iterable<ItemStack> func_184193_aE() {
      return field_190535_b;
   }

   public Iterable<ItemStack> func_184209_aF() {
      return Iterables.<ItemStack>concat(this.func_184214_aD(), this.func_184193_aE());
   }

   public void func_184201_a(EntityEquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
   }

   public boolean func_70027_ad() {
      boolean flag = this.field_70170_p != null && this.field_70170_p.field_72995_K;
      return !this.field_70178_ae && (this.field_190534_ay > 0 || flag && this.func_70083_f(0));
   }

   public boolean func_184218_aH() {
      return this.func_184187_bx() != null;
   }

   public boolean func_184207_aI() {
      return !this.func_184188_bt().isEmpty();
   }

   public boolean func_70093_af() {
      return this.func_70083_f(1);
   }

   public void func_70095_a(boolean p_70095_1_) {
      this.func_70052_a(1, p_70095_1_);
   }

   public boolean func_70051_ag() {
      return this.func_70083_f(3);
   }

   public void func_70031_b(boolean p_70031_1_) {
      this.func_70052_a(3, p_70031_1_);
   }

   public boolean func_184202_aL() {
      return this.field_184238_ar || this.field_70170_p.field_72995_K && this.func_70083_f(6);
   }

   public void func_184195_f(boolean p_184195_1_) {
      this.field_184238_ar = p_184195_1_;
      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(6, this.field_184238_ar);
      }

   }

   public boolean func_82150_aj() {
      return this.func_70083_f(5);
   }

   public boolean func_98034_c(EntityPlayer p_98034_1_) {
      if (p_98034_1_.func_175149_v()) {
         return false;
      } else {
         Team team = this.func_96124_cp();
         return team != null && p_98034_1_ != null && p_98034_1_.func_96124_cp() == team && team.func_98297_h() ? false : this.func_82150_aj();
      }
   }

   @Nullable
   public Team func_96124_cp() {
      return this.field_70170_p.func_96441_U().func_96509_i(this.func_189512_bd());
   }

   public boolean func_184191_r(Entity p_184191_1_) {
      return this.func_184194_a(p_184191_1_.func_96124_cp());
   }

   public boolean func_184194_a(Team p_184194_1_) {
      return this.func_96124_cp() != null ? this.func_96124_cp().func_142054_a(p_184194_1_) : false;
   }

   public void func_82142_c(boolean p_82142_1_) {
      this.func_70052_a(5, p_82142_1_);
   }

   protected boolean func_70083_f(int p_70083_1_) {
      return (((Byte)this.field_70180_af.func_187225_a(field_184240_ax)).byteValue() & 1 << p_70083_1_) != 0;
   }

   protected void func_70052_a(int p_70052_1_, boolean p_70052_2_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184240_ax)).byteValue();
      if (p_70052_2_) {
         this.field_70180_af.func_187227_b(field_184240_ax, Byte.valueOf((byte)(b0 | 1 << p_70052_1_)));
      } else {
         this.field_70180_af.func_187227_b(field_184240_ax, Byte.valueOf((byte)(b0 & ~(1 << p_70052_1_))));
      }

   }

   public int func_70086_ai() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184241_ay)).intValue();
   }

   public void func_70050_g(int p_70050_1_) {
      this.field_70180_af.func_187227_b(field_184241_ay, Integer.valueOf(p_70050_1_));
   }

   public void func_70077_a(EntityLightningBolt p_70077_1_) {
      this.func_70097_a(DamageSource.field_180137_b, 5.0F);
      ++this.field_190534_ay;
      if (this.field_190534_ay == 0) {
         this.func_70015_d(8);
      }

   }

   public void func_70074_a(EntityLivingBase p_70074_1_) {
   }

   protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_) {
      BlockPos blockpos = new BlockPos(p_145771_1_, p_145771_3_, p_145771_5_);
      double d0 = p_145771_1_ - (double)blockpos.func_177958_n();
      double d1 = p_145771_3_ - (double)blockpos.func_177956_o();
      double d2 = p_145771_5_ - (double)blockpos.func_177952_p();
      if (!this.field_70170_p.func_184143_b(this.func_174813_aQ())) {
         return false;
      } else {
         EnumFacing enumfacing = EnumFacing.UP;
         double d3 = Double.MAX_VALUE;
         if (!this.field_70170_p.func_175665_u(blockpos.func_177976_e()) && d0 < d3) {
            d3 = d0;
            enumfacing = EnumFacing.WEST;
         }

         if (!this.field_70170_p.func_175665_u(blockpos.func_177974_f()) && 1.0D - d0 < d3) {
            d3 = 1.0D - d0;
            enumfacing = EnumFacing.EAST;
         }

         if (!this.field_70170_p.func_175665_u(blockpos.func_177978_c()) && d2 < d3) {
            d3 = d2;
            enumfacing = EnumFacing.NORTH;
         }

         if (!this.field_70170_p.func_175665_u(blockpos.func_177968_d()) && 1.0D - d2 < d3) {
            d3 = 1.0D - d2;
            enumfacing = EnumFacing.SOUTH;
         }

         if (!this.field_70170_p.func_175665_u(blockpos.func_177984_a()) && 1.0D - d1 < d3) {
            d3 = 1.0D - d1;
            enumfacing = EnumFacing.UP;
         }

         float f = this.field_70146_Z.nextFloat() * 0.2F + 0.1F;
         float f1 = (float)enumfacing.func_176743_c().func_179524_a();
         if (enumfacing.func_176740_k() == EnumFacing.Axis.X) {
            this.field_70159_w = (double)(f1 * f);
            this.field_70181_x *= 0.75D;
            this.field_70179_y *= 0.75D;
         } else if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
            this.field_70159_w *= 0.75D;
            this.field_70181_x = (double)(f1 * f);
            this.field_70179_y *= 0.75D;
         } else if (enumfacing.func_176740_k() == EnumFacing.Axis.Z) {
            this.field_70159_w *= 0.75D;
            this.field_70181_x *= 0.75D;
            this.field_70179_y = (double)(f1 * f);
         }

         return true;
      }
   }

   public void func_70110_aj() {
      this.field_70134_J = true;
      this.field_70143_R = 0.0F;
   }

   public String func_70005_c_() {
      if (this.func_145818_k_()) {
         return this.func_95999_t();
      } else {
         String s = EntityList.func_75621_b(this);
         if (s == null) {
            s = "generic";
         }

         return I18n.func_74838_a("entity." + s + ".name");
      }
   }

   @Nullable
   public Entity[] func_70021_al() {
      return null;
   }

   public boolean func_70028_i(Entity p_70028_1_) {
      return this == p_70028_1_;
   }

   public float func_70079_am() {
      return 0.0F;
   }

   public void func_70034_d(float p_70034_1_) {
   }

   public void func_181013_g(float p_181013_1_) {
   }

   public boolean func_70075_an() {
      return true;
   }

   public boolean func_85031_j(Entity p_85031_1_) {
      return false;
   }

   public String toString() {
      return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.func_70005_c_(), this.field_145783_c, this.field_70170_p == null ? "~NULL~" : this.field_70170_p.func_72912_H().func_76065_j(), this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public boolean func_180431_b(DamageSource p_180431_1_) {
      return this.field_83001_bt && p_180431_1_ != DamageSource.field_76380_i && !p_180431_1_.func_180136_u();
   }

   public boolean func_190530_aW() {
      return this.field_83001_bt;
   }

   public void func_184224_h(boolean p_184224_1_) {
      this.field_83001_bt = p_184224_1_;
   }

   public void func_82149_j(Entity p_82149_1_) {
      this.func_70012_b(p_82149_1_.field_70165_t, p_82149_1_.field_70163_u, p_82149_1_.field_70161_v, p_82149_1_.field_70177_z, p_82149_1_.field_70125_A);
   }

   private void func_180432_n(Entity p_180432_1_) {
      NBTTagCompound nbttagcompound = p_180432_1_.func_189511_e(new NBTTagCompound());
      nbttagcompound.func_82580_o("Dimension");
      this.func_70020_e(nbttagcompound);
      this.field_71088_bW = p_180432_1_.field_71088_bW;
      this.field_181016_an = p_180432_1_.field_181016_an;
      this.field_181017_ao = p_180432_1_.field_181017_ao;
      this.field_181018_ap = p_180432_1_.field_181018_ap;
   }

   @Nullable
   public Entity func_184204_a(int p_184204_1_) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         this.field_70170_p.field_72984_F.func_76320_a("changeDimension");
         MinecraftServer minecraftserver = this.func_184102_h();
         int i = this.field_71093_bK;
         WorldServer worldserver = minecraftserver.func_71218_a(i);
         WorldServer worldserver1 = minecraftserver.func_71218_a(p_184204_1_);
         this.field_71093_bK = p_184204_1_;
         if (i == 1 && p_184204_1_ == 1) {
            worldserver1 = minecraftserver.func_71218_a(0);
            this.field_71093_bK = 0;
         }

         this.field_70170_p.func_72900_e(this);
         this.field_70128_L = false;
         this.field_70170_p.field_72984_F.func_76320_a("reposition");
         BlockPos blockpos;
         if (p_184204_1_ == 1) {
            blockpos = worldserver1.func_180504_m();
         } else {
            double d0 = this.field_70165_t;
            double d1 = this.field_70161_v;
            double d2 = 8.0D;
            if (p_184204_1_ == -1) {
               d0 = MathHelper.func_151237_a(d0 / 8.0D, worldserver1.func_175723_af().func_177726_b() + 16.0D, worldserver1.func_175723_af().func_177728_d() - 16.0D);
               d1 = MathHelper.func_151237_a(d1 / 8.0D, worldserver1.func_175723_af().func_177736_c() + 16.0D, worldserver1.func_175723_af().func_177733_e() - 16.0D);
            } else if (p_184204_1_ == 0) {
               d0 = MathHelper.func_151237_a(d0 * 8.0D, worldserver1.func_175723_af().func_177726_b() + 16.0D, worldserver1.func_175723_af().func_177728_d() - 16.0D);
               d1 = MathHelper.func_151237_a(d1 * 8.0D, worldserver1.func_175723_af().func_177736_c() + 16.0D, worldserver1.func_175723_af().func_177733_e() - 16.0D);
            }

            d0 = (double)MathHelper.func_76125_a((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.func_76125_a((int)d1, -29999872, 29999872);
            float f = this.field_70177_z;
            this.func_70012_b(d0, this.field_70163_u, d1, 90.0F, 0.0F);
            Teleporter teleporter = worldserver1.func_85176_s();
            teleporter.func_180620_b(this, f);
            blockpos = new BlockPos(this);
         }

         worldserver.func_72866_a(this, false);
         this.field_70170_p.field_72984_F.func_76318_c("reloading");
         Entity entity = EntityList.func_191304_a(this.getClass(), worldserver1);
         if (entity != null) {
            entity.func_180432_n(this);
            if (i == 1 && p_184204_1_ == 1) {
               BlockPos blockpos1 = worldserver1.func_175672_r(worldserver1.func_175694_M());
               entity.func_174828_a(blockpos1, entity.field_70177_z, entity.field_70125_A);
            } else {
               entity.func_174828_a(blockpos, entity.field_70177_z, entity.field_70125_A);
            }

            boolean flag = entity.field_98038_p;
            entity.field_98038_p = true;
            worldserver1.func_72838_d(entity);
            entity.field_98038_p = flag;
            worldserver1.func_72866_a(entity, false);
         }

         this.field_70128_L = true;
         this.field_70170_p.field_72984_F.func_76319_b();
         worldserver.func_82742_i();
         worldserver1.func_82742_i();
         this.field_70170_p.field_72984_F.func_76319_b();
         return entity;
      } else {
         return null;
      }
   }

   public boolean func_184222_aU() {
      return true;
   }

   public float func_180428_a(Explosion p_180428_1_, World p_180428_2_, BlockPos p_180428_3_, IBlockState p_180428_4_) {
      return p_180428_4_.func_177230_c().func_149638_a(this);
   }

   public boolean func_174816_a(Explosion p_174816_1_, World p_174816_2_, BlockPos p_174816_3_, IBlockState p_174816_4_, float p_174816_5_) {
      return true;
   }

   public int func_82143_as() {
      return 3;
   }

   public Vec3d func_181014_aG() {
      return this.field_181017_ao;
   }

   public EnumFacing func_181012_aH() {
      return this.field_181018_ap;
   }

   public boolean func_145773_az() {
      return false;
   }

   public void func_85029_a(CrashReportCategory p_85029_1_) {
      p_85029_1_.func_189529_a("Entity Type", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return EntityList.func_191301_a(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
         }
      });
      p_85029_1_.func_71507_a("Entity ID", Integer.valueOf(this.field_145783_c));
      p_85029_1_.func_189529_a("Entity Name", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return Entity.this.func_70005_c_();
         }
      });
      p_85029_1_.func_71507_a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.field_70165_t, this.field_70163_u, this.field_70161_v));
      p_85029_1_.func_71507_a("Entity's Block location", CrashReportCategory.func_184876_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)));
      p_85029_1_.func_71507_a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.field_70159_w, this.field_70181_x, this.field_70179_y));
      p_85029_1_.func_189529_a("Entity's Passengers", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return Entity.this.func_184188_bt().toString();
         }
      });
      p_85029_1_.func_189529_a("Entity's Vehicle", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return Entity.this.func_184187_bx().toString();
         }
      });
   }

   public boolean func_90999_ad() {
      return this.func_70027_ad();
   }

   public void func_184221_a(UUID p_184221_1_) {
      this.field_96093_i = p_184221_1_;
      this.field_189513_ar = this.field_96093_i.toString();
   }

   public UUID func_110124_au() {
      return this.field_96093_i;
   }

   public String func_189512_bd() {
      return this.field_189513_ar;
   }

   public boolean func_96092_aw() {
      return true;
   }

   public static double func_184183_bd() {
      return field_70155_l;
   }

   public static void func_184227_b(double p_184227_0_) {
      field_70155_l = p_184227_0_;
   }

   public ITextComponent func_145748_c_() {
      TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.func_96667_a(this.func_96124_cp(), this.func_70005_c_()));
      textcomponentstring.func_150256_b().func_150209_a(this.func_174823_aP());
      textcomponentstring.func_150256_b().func_179989_a(this.func_189512_bd());
      return textcomponentstring;
   }

   public void func_96094_a(String p_96094_1_) {
      this.field_70180_af.func_187227_b(field_184242_az, p_96094_1_);
   }

   public String func_95999_t() {
      return (String)this.field_70180_af.func_187225_a(field_184242_az);
   }

   public boolean func_145818_k_() {
      return !((String)this.field_70180_af.func_187225_a(field_184242_az)).isEmpty();
   }

   public void func_174805_g(boolean p_174805_1_) {
      this.field_70180_af.func_187227_b(field_184233_aA, Boolean.valueOf(p_174805_1_));
   }

   public boolean func_174833_aM() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184233_aA)).booleanValue();
   }

   public void func_70634_a(double p_70634_1_, double p_70634_3_, double p_70634_5_) {
      this.field_184237_aG = true;
      this.func_70012_b(p_70634_1_, p_70634_3_, p_70634_5_, this.field_70177_z, this.field_70125_A);
      this.field_70170_p.func_72866_a(this, false);
   }

   public boolean func_94059_bO() {
      return this.func_174833_aM();
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
   }

   public EnumFacing func_174811_aO() {
      return EnumFacing.func_176731_b(MathHelper.func_76128_c((double)(this.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3);
   }

   public EnumFacing func_184172_bi() {
      return this.func_174811_aO();
   }

   protected HoverEvent func_174823_aP() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      ResourceLocation resourcelocation = EntityList.func_191301_a(this);
      nbttagcompound.func_74778_a("id", this.func_189512_bd());
      if (resourcelocation != null) {
         nbttagcompound.func_74778_a("type", resourcelocation.toString());
      }

      nbttagcompound.func_74778_a("name", this.func_70005_c_());
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new TextComponentString(nbttagcompound.toString()));
   }

   public boolean func_174827_a(EntityPlayerMP p_174827_1_) {
      return true;
   }

   public AxisAlignedBB func_174813_aQ() {
      return this.field_70121_D;
   }

   public AxisAlignedBB func_184177_bl() {
      return this.func_174813_aQ();
   }

   public void func_174826_a(AxisAlignedBB p_174826_1_) {
      this.field_70121_D = p_174826_1_;
   }

   public float func_70047_e() {
      return this.field_70131_O * 0.85F;
   }

   public boolean func_174832_aS() {
      return this.field_174835_g;
   }

   public void func_174821_h(boolean p_174821_1_) {
      this.field_174835_g = p_174821_1_;
   }

   public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
      return false;
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return true;
   }

   public BlockPos func_180425_c() {
      return new BlockPos(this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v);
   }

   public Vec3d func_174791_d() {
      return new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public World func_130014_f_() {
      return this.field_70170_p;
   }

   public Entity func_174793_f() {
      return this;
   }

   public boolean func_174792_t_() {
      return false;
   }

   public void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_) {
      if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         this.field_174837_as.func_184932_a(this.field_70170_p.func_73046_m(), this, p_174794_1_, p_174794_2_);
      }

   }

   @Nullable
   public MinecraftServer func_184102_h() {
      return this.field_70170_p.func_73046_m();
   }

   public CommandResultStats func_174807_aT() {
      return this.field_174837_as;
   }

   public void func_174817_o(Entity p_174817_1_) {
      this.field_174837_as.func_179671_a(p_174817_1_.func_174807_aT());
   }

   public EnumActionResult func_184199_a(EntityPlayer p_184199_1_, Vec3d p_184199_2_, EnumHand p_184199_3_) {
      return EnumActionResult.PASS;
   }

   public boolean func_180427_aV() {
      return false;
   }

   protected void func_174815_a(EntityLivingBase p_174815_1_, Entity p_174815_2_) {
      if (p_174815_2_ instanceof EntityLivingBase) {
         EnchantmentHelper.func_151384_a((EntityLivingBase)p_174815_2_, p_174815_1_);
      }

      EnchantmentHelper.func_151385_b(p_174815_1_, p_174815_2_);
   }

   public void func_184178_b(EntityPlayerMP p_184178_1_) {
   }

   public void func_184203_c(EntityPlayerMP p_184203_1_) {
   }

   public float func_184229_a(Rotation p_184229_1_) {
      float f = MathHelper.func_76142_g(this.field_70177_z);
      switch(p_184229_1_) {
      case CLOCKWISE_180:
         return f + 180.0F;
      case COUNTERCLOCKWISE_90:
         return f + 270.0F;
      case CLOCKWISE_90:
         return f + 90.0F;
      default:
         return f;
      }
   }

   public float func_184217_a(Mirror p_184217_1_) {
      float f = MathHelper.func_76142_g(this.field_70177_z);
      switch(p_184217_1_) {
      case LEFT_RIGHT:
         return -f;
      case FRONT_BACK:
         return 180.0F - f;
      default:
         return f;
      }
   }

   public boolean func_184213_bq() {
      return false;
   }

   public boolean func_184189_br() {
      boolean flag = this.field_184237_aG;
      this.field_184237_aG = false;
      return flag;
   }

   @Nullable
   public Entity func_184179_bs() {
      return null;
   }

   public List<Entity> func_184188_bt() {
      return (List<Entity>)(this.field_184244_h.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.field_184244_h));
   }

   public boolean func_184196_w(Entity p_184196_1_) {
      for(Entity entity : this.func_184188_bt()) {
         if (entity.equals(p_184196_1_)) {
            return true;
         }
      }

      return false;
   }

   public Collection<Entity> func_184182_bu() {
      Set<Entity> set = Sets.<Entity>newHashSet();
      this.func_184175_a(Entity.class, set);
      return set;
   }

   public <T extends Entity> Collection<T> func_184180_b(Class<T> p_184180_1_) {
      Set<T> set = Sets.<T>newHashSet();
      this.func_184175_a(p_184180_1_, set);
      return set;
   }

   private <T extends Entity> void func_184175_a(Class<T> p_184175_1_, Set<T> p_184175_2_) {
      for(Entity entity : this.func_184188_bt()) {
         if (p_184175_1_.isAssignableFrom(entity.getClass())) {
            p_184175_2_.add(entity);
         }

         entity.func_184175_a(p_184175_1_, p_184175_2_);
      }

   }

   public Entity func_184208_bv() {
      Entity entity;
      for(entity = this; entity.func_184218_aH(); entity = entity.func_184187_bx()) {
         ;
      }

      return entity;
   }

   public boolean func_184223_x(Entity p_184223_1_) {
      return this.func_184208_bv() == p_184223_1_.func_184208_bv();
   }

   public boolean func_184215_y(Entity p_184215_1_) {
      for(Entity entity : this.func_184188_bt()) {
         if (entity.equals(p_184215_1_)) {
            return true;
         }

         if (entity.func_184215_y(p_184215_1_)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_184186_bw() {
      Entity entity = this.func_184179_bs();
      if (entity instanceof EntityPlayer) {
         return ((EntityPlayer)entity).func_175144_cb();
      } else {
         return !this.field_70170_p.field_72995_K;
      }
   }

   @Nullable
   public Entity func_184187_bx() {
      return this.field_184239_as;
   }

   public EnumPushReaction func_184192_z() {
      return EnumPushReaction.NORMAL;
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.NEUTRAL;
   }

   protected int func_190531_bD() {
      return 1;
   }
}
