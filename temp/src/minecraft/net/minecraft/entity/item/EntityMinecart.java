package net.minecraft.entity.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
   private static final DataParameter<Integer> field_184265_a = EntityDataManager.<Integer>func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184266_b = EntityDataManager.<Integer>func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Float> field_184267_c = EntityDataManager.<Float>func_187226_a(EntityMinecart.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184268_d = EntityDataManager.<Integer>func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184269_e = EntityDataManager.<Integer>func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184270_f = EntityDataManager.<Boolean>func_187226_a(EntityMinecart.class, DataSerializers.field_187198_h);
   private boolean field_70499_f;
   private static final int[][][] field_70500_g = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
   private int field_70510_h;
   private double field_70511_i;
   private double field_70509_j;
   private double field_70514_an;
   private double field_70512_ao;
   private double field_70513_ap;
   private double field_70508_aq;
   private double field_70507_ar;
   private double field_70506_as;

   public EntityMinecart(World p_i1712_1_) {
      super(p_i1712_1_);
      this.field_70156_m = true;
      this.func_70105_a(0.98F, 0.7F);
   }

   public static EntityMinecart func_184263_a(World p_184263_0_, double p_184263_1_, double p_184263_3_, double p_184263_5_, EntityMinecart.Type p_184263_7_) {
      switch(p_184263_7_) {
      case CHEST:
         return new EntityMinecartChest(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      case FURNACE:
         return new EntityMinecartFurnace(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      case TNT:
         return new EntityMinecartTNT(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      case SPAWNER:
         return new EntityMinecartMobSpawner(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      case HOPPER:
         return new EntityMinecartHopper(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      case COMMAND_BLOCK:
         return new EntityMinecartCommandBlock(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      default:
         return new EntityMinecartEmpty(p_184263_0_, p_184263_1_, p_184263_3_, p_184263_5_);
      }
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184265_a, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184266_b, Integer.valueOf(1));
      this.field_70180_af.func_187214_a(field_184267_c, Float.valueOf(0.0F));
      this.field_70180_af.func_187214_a(field_184268_d, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184269_e, Integer.valueOf(6));
      this.field_70180_af.func_187214_a(field_184270_f, Boolean.valueOf(false));
   }

   @Nullable
   public AxisAlignedBB func_70114_g(Entity p_70114_1_) {
      return p_70114_1_.func_70104_M() ? p_70114_1_.func_174813_aQ() : null;
   }

   @Nullable
   public AxisAlignedBB func_70046_E() {
      return null;
   }

   public boolean func_70104_M() {
      return true;
   }

   public EntityMinecart(World p_i1713_1_, double p_i1713_2_, double p_i1713_4_, double p_i1713_6_) {
      this(p_i1713_1_);
      this.func_70107_b(p_i1713_2_, p_i1713_4_, p_i1713_6_);
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70169_q = p_i1713_2_;
      this.field_70167_r = p_i1713_4_;
      this.field_70166_s = p_i1713_6_;
   }

   public double func_70042_X() {
      return 0.0D;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         if (this.func_180431_b(p_70097_1_)) {
            return false;
         } else {
            this.func_70494_i(-this.func_70493_k());
            this.func_70497_h(10);
            this.func_70018_K();
            this.func_70492_c(this.func_70491_i() + p_70097_2_ * 10.0F);
            boolean flag = p_70097_1_.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75098_d;
            if (flag || this.func_70491_i() > 40.0F) {
               this.func_184226_ay();
               if (flag && !this.func_145818_k_()) {
                  this.func_70106_y();
               } else {
                  this.func_94095_a(p_70097_1_);
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public void func_94095_a(DamageSource p_94095_1_) {
      this.func_70106_y();
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         ItemStack itemstack = new ItemStack(Items.field_151143_au, 1);
         if (this.func_145818_k_()) {
            itemstack.func_151001_c(this.func_95999_t());
         }

         this.func_70099_a(itemstack, 0.0F);
      }

   }

   public void func_70057_ab() {
      this.func_70494_i(-this.func_70493_k());
      this.func_70497_h(10);
      this.func_70492_c(this.func_70491_i() + this.func_70491_i() * 10.0F);
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   public EnumFacing func_184172_bi() {
      return this.field_70499_f ? this.func_174811_aO().func_176734_d().func_176746_e() : this.func_174811_aO().func_176746_e();
   }

   public void func_70071_h_() {
      if (this.func_70496_j() > 0) {
         this.func_70497_h(this.func_70496_j() - 1);
      }

      if (this.func_70491_i() > 0.0F) {
         this.func_70492_c(this.func_70491_i() - 1.0F);
      }

      if (this.field_70163_u < -64.0D) {
         this.func_70076_C();
      }

      if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer) {
         this.field_70170_p.field_72984_F.func_76320_a("portal");
         MinecraftServer minecraftserver = this.field_70170_p.func_73046_m();
         int i = this.func_82145_z();
         if (this.field_71087_bX) {
            if (minecraftserver.func_71255_r()) {
               if (!this.func_184218_aH() && this.field_82153_h++ >= i) {
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

         if (this.field_71088_bW > 0) {
            --this.field_71088_bW;
         }

         this.field_70170_p.field_72984_F.func_76319_b();
      }

      if (this.field_70170_p.field_72995_K) {
         if (this.field_70510_h > 0) {
            double d4 = this.field_70165_t + (this.field_70511_i - this.field_70165_t) / (double)this.field_70510_h;
            double d5 = this.field_70163_u + (this.field_70509_j - this.field_70163_u) / (double)this.field_70510_h;
            double d6 = this.field_70161_v + (this.field_70514_an - this.field_70161_v) / (double)this.field_70510_h;
            double d1 = MathHelper.func_76138_g(this.field_70512_ao - (double)this.field_70177_z);
            this.field_70177_z = (float)((double)this.field_70177_z + d1 / (double)this.field_70510_h);
            this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70513_ap - (double)this.field_70125_A) / (double)this.field_70510_h);
            --this.field_70510_h;
            this.func_70107_b(d4, d5, d6);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
         } else {
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
         }

      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.03999999910593033D;
         }

         int k = MathHelper.func_76128_c(this.field_70165_t);
         int l = MathHelper.func_76128_c(this.field_70163_u);
         int i1 = MathHelper.func_76128_c(this.field_70161_v);
         if (BlockRailBase.func_176562_d(this.field_70170_p, new BlockPos(k, l - 1, i1))) {
            --l;
         }

         BlockPos blockpos = new BlockPos(k, l, i1);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         if (BlockRailBase.func_176563_d(iblockstate)) {
            this.func_180460_a(blockpos, iblockstate);
            if (iblockstate.func_177230_c() == Blocks.field_150408_cc) {
               this.func_96095_a(k, l, i1, ((Boolean)iblockstate.func_177229_b(BlockRailPowered.field_176569_M)).booleanValue());
            }
         } else {
            this.func_180459_n();
         }

         this.func_145775_I();
         this.field_70125_A = 0.0F;
         double d0 = this.field_70169_q - this.field_70165_t;
         double d2 = this.field_70166_s - this.field_70161_v;
         if (d0 * d0 + d2 * d2 > 0.001D) {
            this.field_70177_z = (float)(MathHelper.func_181159_b(d2, d0) * 180.0D / 3.141592653589793D);
            if (this.field_70499_f) {
               this.field_70177_z += 180.0F;
            }
         }

         double d3 = (double)MathHelper.func_76142_g(this.field_70177_z - this.field_70126_B);
         if (d3 < -170.0D || d3 >= 170.0D) {
            this.field_70177_z += 180.0F;
            this.field_70499_f = !this.field_70499_f;
         }

         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         if (this.func_184264_v() == EntityMinecart.Type.RIDEABLE && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.01D) {
            List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D), EntitySelectors.func_188442_a(this));
            if (!list.isEmpty()) {
               for(int j1 = 0; j1 < list.size(); ++j1) {
                  Entity entity1 = list.get(j1);
                  if (!(entity1 instanceof EntityPlayer) && !(entity1 instanceof EntityIronGolem) && !(entity1 instanceof EntityMinecart) && !this.func_184207_aI() && !entity1.func_184218_aH()) {
                     entity1.func_184220_m(this);
                  } else {
                     entity1.func_70108_f(this);
                  }
               }
            }
         } else {
            for(Entity entity : this.field_70170_p.func_72839_b(this, this.func_174813_aQ().func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
               if (!this.func_184196_w(entity) && entity.func_70104_M() && entity instanceof EntityMinecart) {
                  entity.func_70108_f(this);
               }
            }
         }

         this.func_70072_I();
      }
   }

   protected double func_174898_m() {
      return 0.4D;
   }

   public void func_96095_a(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_) {
   }

   protected void func_180459_n() {
      double d0 = this.func_174898_m();
      this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -d0, d0);
      this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -d0, d0);
      if (this.field_70122_E) {
         this.field_70159_w *= 0.5D;
         this.field_70181_x *= 0.5D;
         this.field_70179_y *= 0.5D;
      }

      this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if (!this.field_70122_E) {
         this.field_70159_w *= 0.949999988079071D;
         this.field_70181_x *= 0.949999988079071D;
         this.field_70179_y *= 0.949999988079071D;
      }

   }

   protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
      this.field_70143_R = 0.0F;
      Vec3d vec3d = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70163_u = (double)p_180460_1_.func_177956_o();
      boolean flag = false;
      boolean flag1 = false;
      BlockRailBase blockrailbase = (BlockRailBase)p_180460_2_.func_177230_c();
      if (blockrailbase == Blocks.field_150318_D) {
         flag = ((Boolean)p_180460_2_.func_177229_b(BlockRailPowered.field_176569_M)).booleanValue();
         flag1 = !flag;
      }

      double d0 = 0.0078125D;
      BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_180460_2_.func_177229_b(blockrailbase.func_176560_l());
      switch(blockrailbase$enumraildirection) {
      case ASCENDING_EAST:
         this.field_70159_w -= 0.0078125D;
         ++this.field_70163_u;
         break;
      case ASCENDING_WEST:
         this.field_70159_w += 0.0078125D;
         ++this.field_70163_u;
         break;
      case ASCENDING_NORTH:
         this.field_70179_y += 0.0078125D;
         ++this.field_70163_u;
         break;
      case ASCENDING_SOUTH:
         this.field_70179_y -= 0.0078125D;
         ++this.field_70163_u;
      }

      int[][] aint = field_70500_g[blockrailbase$enumraildirection.func_177015_a()];
      double d1 = (double)(aint[1][0] - aint[0][0]);
      double d2 = (double)(aint[1][2] - aint[0][2]);
      double d3 = Math.sqrt(d1 * d1 + d2 * d2);
      double d4 = this.field_70159_w * d1 + this.field_70179_y * d2;
      if (d4 < 0.0D) {
         d1 = -d1;
         d2 = -d2;
      }

      double d5 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (d5 > 2.0D) {
         d5 = 2.0D;
      }

      this.field_70159_w = d5 * d1 / d3;
      this.field_70179_y = d5 * d2 / d3;
      Entity entity = this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
      if (entity instanceof EntityLivingBase) {
         double d6 = (double)((EntityLivingBase)entity).field_191988_bg;
         if (d6 > 0.0D) {
            double d7 = -Math.sin((double)(entity.field_70177_z * 0.017453292F));
            double d8 = Math.cos((double)(entity.field_70177_z * 0.017453292F));
            double d9 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
            if (d9 < 0.01D) {
               this.field_70159_w += d7 * 0.1D;
               this.field_70179_y += d8 * 0.1D;
               flag1 = false;
            }
         }
      }

      if (flag1) {
         double d17 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (d17 < 0.03D) {
            this.field_70159_w *= 0.0D;
            this.field_70181_x *= 0.0D;
            this.field_70179_y *= 0.0D;
         } else {
            this.field_70159_w *= 0.5D;
            this.field_70181_x *= 0.0D;
            this.field_70179_y *= 0.5D;
         }
      }

      double d18 = (double)p_180460_1_.func_177958_n() + 0.5D + (double)aint[0][0] * 0.5D;
      double d19 = (double)p_180460_1_.func_177952_p() + 0.5D + (double)aint[0][2] * 0.5D;
      double d20 = (double)p_180460_1_.func_177958_n() + 0.5D + (double)aint[1][0] * 0.5D;
      double d21 = (double)p_180460_1_.func_177952_p() + 0.5D + (double)aint[1][2] * 0.5D;
      d1 = d20 - d18;
      d2 = d21 - d19;
      double d10;
      if (d1 == 0.0D) {
         this.field_70165_t = (double)p_180460_1_.func_177958_n() + 0.5D;
         d10 = this.field_70161_v - (double)p_180460_1_.func_177952_p();
      } else if (d2 == 0.0D) {
         this.field_70161_v = (double)p_180460_1_.func_177952_p() + 0.5D;
         d10 = this.field_70165_t - (double)p_180460_1_.func_177958_n();
      } else {
         double d11 = this.field_70165_t - d18;
         double d12 = this.field_70161_v - d19;
         d10 = (d11 * d1 + d12 * d2) * 2.0D;
      }

      this.field_70165_t = d18 + d1 * d10;
      this.field_70161_v = d19 + d2 * d10;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      double d22 = this.field_70159_w;
      double d23 = this.field_70179_y;
      if (this.func_184207_aI()) {
         d22 *= 0.75D;
         d23 *= 0.75D;
      }

      double d13 = this.func_174898_m();
      d22 = MathHelper.func_151237_a(d22, -d13, d13);
      d23 = MathHelper.func_151237_a(d23, -d13, d13);
      this.func_70091_d(MoverType.SELF, d22, 0.0D, d23);
      if (aint[0][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - p_180460_1_.func_177958_n() == aint[0][0] && MathHelper.func_76128_c(this.field_70161_v) - p_180460_1_.func_177952_p() == aint[0][2]) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[0][1], this.field_70161_v);
      } else if (aint[1][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - p_180460_1_.func_177958_n() == aint[1][0] && MathHelper.func_76128_c(this.field_70161_v) - p_180460_1_.func_177952_p() == aint[1][2]) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[1][1], this.field_70161_v);
      }

      this.func_94101_h();
      Vec3d vec3d1 = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if (vec3d1 != null && vec3d != null) {
         double d14 = (vec3d.field_72448_b - vec3d1.field_72448_b) * 0.05D;
         d5 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (d5 > 0.0D) {
            this.field_70159_w = this.field_70159_w / d5 * (d5 + d14);
            this.field_70179_y = this.field_70179_y / d5 * (d5 + d14);
         }

         this.func_70107_b(this.field_70165_t, vec3d1.field_72448_b, this.field_70161_v);
      }

      int j = MathHelper.func_76128_c(this.field_70165_t);
      int i = MathHelper.func_76128_c(this.field_70161_v);
      if (j != p_180460_1_.func_177958_n() || i != p_180460_1_.func_177952_p()) {
         d5 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70159_w = d5 * (double)(j - p_180460_1_.func_177958_n());
         this.field_70179_y = d5 * (double)(i - p_180460_1_.func_177952_p());
      }

      if (flag) {
         double d15 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (d15 > 0.01D) {
            double d16 = 0.06D;
            this.field_70159_w += this.field_70159_w / d15 * 0.06D;
            this.field_70179_y += this.field_70179_y / d15 * 0.06D;
         } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (this.field_70170_p.func_180495_p(p_180460_1_.func_177976_e()).func_185915_l()) {
               this.field_70159_w = 0.02D;
            } else if (this.field_70170_p.func_180495_p(p_180460_1_.func_177974_f()).func_185915_l()) {
               this.field_70159_w = -0.02D;
            }
         } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (this.field_70170_p.func_180495_p(p_180460_1_.func_177978_c()).func_185915_l()) {
               this.field_70179_y = 0.02D;
            } else if (this.field_70170_p.func_180495_p(p_180460_1_.func_177968_d()).func_185915_l()) {
               this.field_70179_y = -0.02D;
            }
         }
      }

   }

   protected void func_94101_h() {
      if (this.func_184207_aI()) {
         this.field_70159_w *= 0.996999979019165D;
         this.field_70181_x *= 0.0D;
         this.field_70179_y *= 0.996999979019165D;
      } else {
         this.field_70159_w *= 0.9599999785423279D;
         this.field_70181_x *= 0.0D;
         this.field_70179_y *= 0.9599999785423279D;
      }

   }

   public void func_70107_b(double p_70107_1_, double p_70107_3_, double p_70107_5_) {
      this.field_70165_t = p_70107_1_;
      this.field_70163_u = p_70107_3_;
      this.field_70161_v = p_70107_5_;
      float f = this.field_70130_N / 2.0F;
      float f1 = this.field_70131_O;
      this.func_174826_a(new AxisAlignedBB(p_70107_1_ - (double)f, p_70107_3_, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ + (double)f1, p_70107_5_ + (double)f));
   }

   @Nullable
   public Vec3d func_70495_a(double p_70495_1_, double p_70495_3_, double p_70495_5_, double p_70495_7_) {
      int i = MathHelper.func_76128_c(p_70495_1_);
      int j = MathHelper.func_76128_c(p_70495_3_);
      int k = MathHelper.func_76128_c(p_70495_5_);
      if (BlockRailBase.func_176562_d(this.field_70170_p, new BlockPos(i, j - 1, k))) {
         --j;
      }

      IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
      if (BlockRailBase.func_176563_d(iblockstate)) {
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.func_177229_b(((BlockRailBase)iblockstate.func_177230_c()).func_176560_l());
         p_70495_3_ = (double)j;
         if (blockrailbase$enumraildirection.func_177018_c()) {
            p_70495_3_ = (double)(j + 1);
         }

         int[][] aint = field_70500_g[blockrailbase$enumraildirection.func_177015_a()];
         double d0 = (double)(aint[1][0] - aint[0][0]);
         double d1 = (double)(aint[1][2] - aint[0][2]);
         double d2 = Math.sqrt(d0 * d0 + d1 * d1);
         d0 = d0 / d2;
         d1 = d1 / d2;
         p_70495_1_ = p_70495_1_ + d0 * p_70495_7_;
         p_70495_5_ = p_70495_5_ + d1 * p_70495_7_;
         if (aint[0][1] != 0 && MathHelper.func_76128_c(p_70495_1_) - i == aint[0][0] && MathHelper.func_76128_c(p_70495_5_) - k == aint[0][2]) {
            p_70495_3_ += (double)aint[0][1];
         } else if (aint[1][1] != 0 && MathHelper.func_76128_c(p_70495_1_) - i == aint[1][0] && MathHelper.func_76128_c(p_70495_5_) - k == aint[1][2]) {
            p_70495_3_ += (double)aint[1][1];
         }

         return this.func_70489_a(p_70495_1_, p_70495_3_, p_70495_5_);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3d func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
      int i = MathHelper.func_76128_c(p_70489_1_);
      int j = MathHelper.func_76128_c(p_70489_3_);
      int k = MathHelper.func_76128_c(p_70489_5_);
      if (BlockRailBase.func_176562_d(this.field_70170_p, new BlockPos(i, j - 1, k))) {
         --j;
      }

      IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
      if (BlockRailBase.func_176563_d(iblockstate)) {
         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.func_177229_b(((BlockRailBase)iblockstate.func_177230_c()).func_176560_l());
         int[][] aint = field_70500_g[blockrailbase$enumraildirection.func_177015_a()];
         double d0 = (double)i + 0.5D + (double)aint[0][0] * 0.5D;
         double d1 = (double)j + 0.0625D + (double)aint[0][1] * 0.5D;
         double d2 = (double)k + 0.5D + (double)aint[0][2] * 0.5D;
         double d3 = (double)i + 0.5D + (double)aint[1][0] * 0.5D;
         double d4 = (double)j + 0.0625D + (double)aint[1][1] * 0.5D;
         double d5 = (double)k + 0.5D + (double)aint[1][2] * 0.5D;
         double d6 = d3 - d0;
         double d7 = (d4 - d1) * 2.0D;
         double d8 = d5 - d2;
         double d9;
         if (d6 == 0.0D) {
            d9 = p_70489_5_ - (double)k;
         } else if (d8 == 0.0D) {
            d9 = p_70489_1_ - (double)i;
         } else {
            double d10 = p_70489_1_ - d0;
            double d11 = p_70489_5_ - d2;
            d9 = (d10 * d6 + d11 * d8) * 2.0D;
         }

         p_70489_1_ = d0 + d6 * d9;
         p_70489_3_ = d1 + d7 * d9;
         p_70489_5_ = d2 + d8 * d9;
         if (d7 < 0.0D) {
            ++p_70489_3_;
         }

         if (d7 > 0.0D) {
            p_70489_3_ += 0.5D;
         }

         return new Vec3d(p_70489_1_, p_70489_3_, p_70489_5_);
      } else {
         return null;
      }
   }

   public AxisAlignedBB func_184177_bl() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      return this.func_94100_s() ? axisalignedbb.func_186662_g((double)Math.abs(this.func_94099_q()) / 16.0D) : axisalignedbb;
   }

   public static void func_189669_a(DataFixer p_189669_0_, Class<?> p_189669_1_) {
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      if (p_70037_1_.func_74767_n("CustomDisplayTile")) {
         Block block;
         if (p_70037_1_.func_150297_b("DisplayTile", 8)) {
            block = Block.func_149684_b(p_70037_1_.func_74779_i("DisplayTile"));
         } else {
            block = Block.func_149729_e(p_70037_1_.func_74762_e("DisplayTile"));
         }

         int i = p_70037_1_.func_74762_e("DisplayData");
         this.func_174899_a(block == null ? Blocks.field_150350_a.func_176223_P() : block.func_176203_a(i));
         this.func_94086_l(p_70037_1_.func_74762_e("DisplayOffset"));
      }

   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      if (this.func_94100_s()) {
         p_70014_1_.func_74757_a("CustomDisplayTile", true);
         IBlockState iblockstate = this.func_174897_t();
         ResourceLocation resourcelocation = Block.field_149771_c.func_177774_c(iblockstate.func_177230_c());
         p_70014_1_.func_74778_a("DisplayTile", resourcelocation == null ? "" : resourcelocation.toString());
         p_70014_1_.func_74768_a("DisplayData", iblockstate.func_177230_c().func_176201_c(iblockstate));
         p_70014_1_.func_74768_a("DisplayOffset", this.func_94099_q());
      }

   }

   public void func_70108_f(Entity p_70108_1_) {
      if (!this.field_70170_p.field_72995_K) {
         if (!p_70108_1_.field_70145_X && !this.field_70145_X) {
            if (!this.func_184196_w(p_70108_1_)) {
               double d0 = p_70108_1_.field_70165_t - this.field_70165_t;
               double d1 = p_70108_1_.field_70161_v - this.field_70161_v;
               double d2 = d0 * d0 + d1 * d1;
               if (d2 >= 9.999999747378752E-5D) {
                  d2 = (double)MathHelper.func_76133_a(d2);
                  d0 = d0 / d2;
                  d1 = d1 / d2;
                  double d3 = 1.0D / d2;
                  if (d3 > 1.0D) {
                     d3 = 1.0D;
                  }

                  d0 = d0 * d3;
                  d1 = d1 * d3;
                  d0 = d0 * 0.10000000149011612D;
                  d1 = d1 * 0.10000000149011612D;
                  d0 = d0 * (double)(1.0F - this.field_70144_Y);
                  d1 = d1 * (double)(1.0F - this.field_70144_Y);
                  d0 = d0 * 0.5D;
                  d1 = d1 * 0.5D;
                  if (p_70108_1_ instanceof EntityMinecart) {
                     double d4 = p_70108_1_.field_70165_t - this.field_70165_t;
                     double d5 = p_70108_1_.field_70161_v - this.field_70161_v;
                     Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).func_72432_b();
                     Vec3d vec3d1 = (new Vec3d((double)MathHelper.func_76134_b(this.field_70177_z * 0.017453292F), 0.0D, (double)MathHelper.func_76126_a(this.field_70177_z * 0.017453292F))).func_72432_b();
                     double d6 = Math.abs(vec3d.func_72430_b(vec3d1));
                     if (d6 < 0.800000011920929D) {
                        return;
                     }

                     double d7 = p_70108_1_.field_70159_w + this.field_70159_w;
                     double d8 = p_70108_1_.field_70179_y + this.field_70179_y;
                     if (((EntityMinecart)p_70108_1_).func_184264_v() == EntityMinecart.Type.FURNACE && this.func_184264_v() != EntityMinecart.Type.FURNACE) {
                        this.field_70159_w *= 0.20000000298023224D;
                        this.field_70179_y *= 0.20000000298023224D;
                        this.func_70024_g(p_70108_1_.field_70159_w - d0, 0.0D, p_70108_1_.field_70179_y - d1);
                        p_70108_1_.field_70159_w *= 0.949999988079071D;
                        p_70108_1_.field_70179_y *= 0.949999988079071D;
                     } else if (((EntityMinecart)p_70108_1_).func_184264_v() != EntityMinecart.Type.FURNACE && this.func_184264_v() == EntityMinecart.Type.FURNACE) {
                        p_70108_1_.field_70159_w *= 0.20000000298023224D;
                        p_70108_1_.field_70179_y *= 0.20000000298023224D;
                        p_70108_1_.func_70024_g(this.field_70159_w + d0, 0.0D, this.field_70179_y + d1);
                        this.field_70159_w *= 0.949999988079071D;
                        this.field_70179_y *= 0.949999988079071D;
                     } else {
                        d7 = d7 / 2.0D;
                        d8 = d8 / 2.0D;
                        this.field_70159_w *= 0.20000000298023224D;
                        this.field_70179_y *= 0.20000000298023224D;
                        this.func_70024_g(d7 - d0, 0.0D, d8 - d1);
                        p_70108_1_.field_70159_w *= 0.20000000298023224D;
                        p_70108_1_.field_70179_y *= 0.20000000298023224D;
                        p_70108_1_.func_70024_g(d7 + d0, 0.0D, d8 + d1);
                     }
                  } else {
                     this.func_70024_g(-d0, 0.0D, -d1);
                     p_70108_1_.func_70024_g(d0 / 4.0D, 0.0D, d1 / 4.0D);
                  }
               }

            }
         }
      }
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.field_70511_i = p_180426_1_;
      this.field_70509_j = p_180426_3_;
      this.field_70514_an = p_180426_5_;
      this.field_70512_ao = (double)p_180426_7_;
      this.field_70513_ap = (double)p_180426_8_;
      this.field_70510_h = p_180426_9_ + 2;
      this.field_70159_w = this.field_70508_aq;
      this.field_70181_x = this.field_70507_ar;
      this.field_70179_y = this.field_70506_as;
   }

   public void func_70016_h(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.field_70159_w = p_70016_1_;
      this.field_70181_x = p_70016_3_;
      this.field_70179_y = p_70016_5_;
      this.field_70508_aq = this.field_70159_w;
      this.field_70507_ar = this.field_70181_x;
      this.field_70506_as = this.field_70179_y;
   }

   public void func_70492_c(float p_70492_1_) {
      this.field_70180_af.func_187227_b(field_184267_c, Float.valueOf(p_70492_1_));
   }

   public float func_70491_i() {
      return ((Float)this.field_70180_af.func_187225_a(field_184267_c)).floatValue();
   }

   public void func_70497_h(int p_70497_1_) {
      this.field_70180_af.func_187227_b(field_184265_a, Integer.valueOf(p_70497_1_));
   }

   public int func_70496_j() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184265_a)).intValue();
   }

   public void func_70494_i(int p_70494_1_) {
      this.field_70180_af.func_187227_b(field_184266_b, Integer.valueOf(p_70494_1_));
   }

   public int func_70493_k() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184266_b)).intValue();
   }

   public abstract EntityMinecart.Type func_184264_v();

   public IBlockState func_174897_t() {
      return !this.func_94100_s() ? this.func_180457_u() : Block.func_176220_d(((Integer)this.func_184212_Q().func_187225_a(field_184268_d)).intValue());
   }

   public IBlockState func_180457_u() {
      return Blocks.field_150350_a.func_176223_P();
   }

   public int func_94099_q() {
      return !this.func_94100_s() ? this.func_94085_r() : ((Integer)this.func_184212_Q().func_187225_a(field_184269_e)).intValue();
   }

   public int func_94085_r() {
      return 6;
   }

   public void func_174899_a(IBlockState p_174899_1_) {
      this.func_184212_Q().func_187227_b(field_184268_d, Integer.valueOf(Block.func_176210_f(p_174899_1_)));
      this.func_94096_e(true);
   }

   public void func_94086_l(int p_94086_1_) {
      this.func_184212_Q().func_187227_b(field_184269_e, Integer.valueOf(p_94086_1_));
      this.func_94096_e(true);
   }

   public boolean func_94100_s() {
      return ((Boolean)this.func_184212_Q().func_187225_a(field_184270_f)).booleanValue();
   }

   public void func_94096_e(boolean p_94096_1_) {
      this.func_184212_Q().func_187227_b(field_184270_f, Boolean.valueOf(p_94096_1_));
   }

   public static enum Type {
      RIDEABLE(0, "MinecartRideable"),
      CHEST(1, "MinecartChest"),
      FURNACE(2, "MinecartFurnace"),
      TNT(3, "MinecartTNT"),
      SPAWNER(4, "MinecartSpawner"),
      HOPPER(5, "MinecartHopper"),
      COMMAND_BLOCK(6, "MinecartCommandBlock");

      private static final Map<Integer, EntityMinecart.Type> field_184965_h = Maps.<Integer, EntityMinecart.Type>newHashMap();
      private final int field_184966_i;
      private final String field_184967_j;

      private Type(int p_i47005_3_, String p_i47005_4_) {
         this.field_184966_i = p_i47005_3_;
         this.field_184967_j = p_i47005_4_;
      }

      public int func_184956_a() {
         return this.field_184966_i;
      }

      public String func_184954_b() {
         return this.field_184967_j;
      }

      public static EntityMinecart.Type func_184955_a(int p_184955_0_) {
         EntityMinecart.Type entityminecart$type = field_184965_h.get(Integer.valueOf(p_184955_0_));
         return entityminecart$type == null ? RIDEABLE : entityminecart$type;
      }

      static {
         for(EntityMinecart.Type entityminecart$type : values()) {
            field_184965_h.put(Integer.valueOf(entityminecart$type.func_184956_a()), entityminecart$type);
         }

      }
   }
}
