package net.minecraft.entity.player;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityPlayer extends EntityLivingBase {
   private static final DataParameter<Float> field_184829_a = EntityDataManager.<Float>func_187226_a(EntityPlayer.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184830_b = EntityDataManager.<Integer>func_187226_a(EntityPlayer.class, DataSerializers.field_187192_b);
   protected static final DataParameter<Byte> field_184827_bp = EntityDataManager.<Byte>func_187226_a(EntityPlayer.class, DataSerializers.field_187191_a);
   protected static final DataParameter<Byte> field_184828_bq = EntityDataManager.<Byte>func_187226_a(EntityPlayer.class, DataSerializers.field_187191_a);
   protected static final DataParameter<NBTTagCompound> field_192032_bt = EntityDataManager.<NBTTagCompound>func_187226_a(EntityPlayer.class, DataSerializers.field_192734_n);
   protected static final DataParameter<NBTTagCompound> field_192033_bu = EntityDataManager.<NBTTagCompound>func_187226_a(EntityPlayer.class, DataSerializers.field_192734_n);
   public InventoryPlayer field_71071_by = new InventoryPlayer(this);
   protected InventoryEnderChest field_71078_a = new InventoryEnderChest();
   public Container field_71069_bz;
   public Container field_71070_bA;
   protected FoodStats field_71100_bB = new FoodStats();
   protected int field_71101_bC;
   public float field_71107_bF;
   public float field_71109_bG;
   public int field_71090_bL;
   public double field_71091_bM;
   public double field_71096_bN;
   public double field_71097_bO;
   public double field_71094_bP;
   public double field_71095_bQ;
   public double field_71085_bR;
   protected boolean field_71083_bS;
   public BlockPos field_71081_bT;
   private int field_71076_b;
   public float field_71079_bU;
   public float field_71082_cx;
   public float field_71089_bV;
   private BlockPos field_71077_c;
   private boolean field_82248_d;
   public PlayerCapabilities field_71075_bZ = new PlayerCapabilities();
   public int field_71068_ca;
   public int field_71067_cb;
   public float field_71106_cc;
   protected int field_175152_f;
   protected float field_71102_ce = 0.02F;
   private int field_82249_h;
   private final GameProfile field_146106_i;
   private boolean field_175153_bG;
   private ItemStack field_184831_bT = ItemStack.field_190927_a;
   private final CooldownTracker field_184832_bU = this.func_184815_l();
   @Nullable
   public EntityFishHook field_71104_cf;

   protected CooldownTracker func_184815_l() {
      return new CooldownTracker();
   }

   public EntityPlayer(World p_i45324_1_, GameProfile p_i45324_2_) {
      super(p_i45324_1_);
      this.func_184221_a(func_146094_a(p_i45324_2_));
      this.field_146106_i = p_i45324_2_;
      this.field_71069_bz = new ContainerPlayer(this.field_71071_by, !p_i45324_1_.field_72995_K, this);
      this.field_71070_bA = this.field_71069_bz;
      BlockPos blockpos = p_i45324_1_.func_175694_M();
      this.func_70012_b((double)blockpos.func_177958_n() + 0.5D, (double)(blockpos.func_177956_o() + 1), (double)blockpos.func_177952_p() + 0.5D, 0.0F, 0.0F);
      this.field_70741_aB = 180.0F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.10000000149011612D);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188790_f);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188792_h);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184829_a, Float.valueOf(0.0F));
      this.field_70180_af.func_187214_a(field_184830_b, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184827_bp, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_184828_bq, Byte.valueOf((byte)1));
      this.field_70180_af.func_187214_a(field_192032_bt, new NBTTagCompound());
      this.field_70180_af.func_187214_a(field_192033_bu, new NBTTagCompound());
   }

   public void func_70071_h_() {
      this.field_70145_X = this.func_175149_v();
      if (this.func_175149_v()) {
         this.field_70122_E = false;
      }

      if (this.field_71090_bL > 0) {
         --this.field_71090_bL;
      }

      if (this.func_70608_bn()) {
         ++this.field_71076_b;
         if (this.field_71076_b > 100) {
            this.field_71076_b = 100;
         }

         if (!this.field_70170_p.field_72995_K) {
            if (!this.func_175143_p()) {
               this.func_70999_a(true, true, false);
            } else if (this.field_70170_p.func_72935_r()) {
               this.func_70999_a(false, true, true);
            }
         }
      } else if (this.field_71076_b > 0) {
         ++this.field_71076_b;
         if (this.field_71076_b >= 110) {
            this.field_71076_b = 0;
         }
      }

      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.field_71070_bA != null && !this.field_71070_bA.func_75145_c(this)) {
         this.func_71053_j();
         this.field_71070_bA = this.field_71069_bz;
      }

      if (this.func_70027_ad() && this.field_71075_bZ.field_75102_a) {
         this.func_70066_B();
      }

      this.func_184820_o();
      if (!this.field_70170_p.field_72995_K) {
         this.field_71100_bB.func_75118_a(this);
         this.func_71029_a(StatList.field_188097_g);
         if (this.func_70089_S()) {
            this.func_71029_a(StatList.field_188098_h);
         }

         if (this.func_70093_af()) {
            this.func_71029_a(StatList.field_188099_i);
         }
      }

      int i = 29999999;
      double d0 = MathHelper.func_151237_a(this.field_70165_t, -2.9999999E7D, 2.9999999E7D);
      double d1 = MathHelper.func_151237_a(this.field_70161_v, -2.9999999E7D, 2.9999999E7D);
      if (d0 != this.field_70165_t || d1 != this.field_70161_v) {
         this.func_70107_b(d0, this.field_70163_u, d1);
      }

      ++this.field_184617_aD;
      ItemStack itemstack = this.func_184614_ca();
      if (!ItemStack.func_77989_b(this.field_184831_bT, itemstack)) {
         if (!ItemStack.func_185132_d(this.field_184831_bT, itemstack)) {
            this.func_184821_cY();
         }

         this.field_184831_bT = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.func_77946_l();
      }

      this.field_184832_bU.func_185144_a();
      this.func_184808_cD();
   }

   private void func_184820_o() {
      this.field_71091_bM = this.field_71094_bP;
      this.field_71096_bN = this.field_71095_bQ;
      this.field_71097_bO = this.field_71085_bR;
      double d0 = this.field_70165_t - this.field_71094_bP;
      double d1 = this.field_70163_u - this.field_71095_bQ;
      double d2 = this.field_70161_v - this.field_71085_bR;
      double d3 = 10.0D;
      if (d0 > 10.0D) {
         this.field_71094_bP = this.field_70165_t;
         this.field_71091_bM = this.field_71094_bP;
      }

      if (d2 > 10.0D) {
         this.field_71085_bR = this.field_70161_v;
         this.field_71097_bO = this.field_71085_bR;
      }

      if (d1 > 10.0D) {
         this.field_71095_bQ = this.field_70163_u;
         this.field_71096_bN = this.field_71095_bQ;
      }

      if (d0 < -10.0D) {
         this.field_71094_bP = this.field_70165_t;
         this.field_71091_bM = this.field_71094_bP;
      }

      if (d2 < -10.0D) {
         this.field_71085_bR = this.field_70161_v;
         this.field_71097_bO = this.field_71085_bR;
      }

      if (d1 < -10.0D) {
         this.field_71095_bQ = this.field_70163_u;
         this.field_71096_bN = this.field_71095_bQ;
      }

      this.field_71094_bP += d0 * 0.25D;
      this.field_71085_bR += d2 * 0.25D;
      this.field_71095_bQ += d1 * 0.25D;
   }

   protected void func_184808_cD() {
      float f;
      float f1;
      if (this.func_184613_cA()) {
         f = 0.6F;
         f1 = 0.6F;
      } else if (this.func_70608_bn()) {
         f = 0.2F;
         f1 = 0.2F;
      } else if (this.func_70093_af()) {
         f = 0.6F;
         f1 = 1.65F;
      } else {
         f = 0.6F;
         f1 = 1.8F;
      }

      if (f != this.field_70130_N || f1 != this.field_70131_O) {
         AxisAlignedBB axisalignedbb = this.func_174813_aQ();
         axisalignedbb = new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + (double)f, axisalignedbb.field_72338_b + (double)f1, axisalignedbb.field_72339_c + (double)f);
         if (!this.field_70170_p.func_184143_b(axisalignedbb)) {
            this.func_70105_a(f, f1);
         }
      }

   }

   public int func_82145_z() {
      return this.field_71075_bZ.field_75102_a ? 1 : 80;
   }

   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_187808_ef;
   }

   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_187806_ee;
   }

   public int func_82147_ab() {
      return 10;
   }

   public void func_184185_a(SoundEvent p_184185_1_, float p_184185_2_, float p_184185_3_) {
      this.field_70170_p.func_184148_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, p_184185_1_, this.func_184176_by(), p_184185_2_, p_184185_3_);
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.PLAYERS;
   }

   protected int func_190531_bD() {
      return 20;
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 9) {
         this.func_71036_o();
      } else if (p_70103_1_ == 23) {
         this.field_175153_bG = false;
      } else if (p_70103_1_ == 22) {
         this.field_175153_bG = true;
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F || this.func_70608_bn();
   }

   protected void func_71053_j() {
      this.field_71070_bA = this.field_71069_bz;
   }

   public void func_70098_U() {
      if (!this.field_70170_p.field_72995_K && this.func_70093_af() && this.func_184218_aH()) {
         this.func_184210_p();
         this.func_70095_a(false);
      } else {
         double d0 = this.field_70165_t;
         double d1 = this.field_70163_u;
         double d2 = this.field_70161_v;
         float f = this.field_70177_z;
         float f1 = this.field_70125_A;
         super.func_70098_U();
         this.field_71107_bF = this.field_71109_bG;
         this.field_71109_bG = 0.0F;
         this.func_71015_k(this.field_70165_t - d0, this.field_70163_u - d1, this.field_70161_v - d2);
         if (this.func_184187_bx() instanceof EntityPig) {
            this.field_70125_A = f1;
            this.field_70177_z = f;
            this.field_70761_aq = ((EntityPig)this.func_184187_bx()).field_70761_aq;
         }

      }
   }

   public void func_70065_x() {
      this.func_70105_a(0.6F, 1.8F);
      super.func_70065_x();
      this.func_70606_j(this.func_110138_aP());
      this.field_70725_aQ = 0;
   }

   protected void func_70626_be() {
      super.func_70626_be();
      this.func_82168_bl();
      this.field_70759_as = this.field_70177_z;
   }

   public void func_70636_d() {
      if (this.field_71101_bC > 0) {
         --this.field_71101_bC;
      }

      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL && this.field_70170_p.func_82736_K().func_82766_b("naturalRegeneration")) {
         if (this.func_110143_aJ() < this.func_110138_aP() && this.field_70173_aa % 20 == 0) {
            this.func_70691_i(1.0F);
         }

         if (this.field_71100_bB.func_75121_c() && this.field_70173_aa % 10 == 0) {
            this.field_71100_bB.func_75114_a(this.field_71100_bB.func_75116_a() + 1);
         }
      }

      this.field_71071_by.func_70429_k();
      this.field_71107_bF = this.field_71109_bG;
      super.func_70636_d();
      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (!this.field_70170_p.field_72995_K) {
         iattributeinstance.func_111128_a((double)this.field_71075_bZ.func_75094_b());
      }

      this.field_70747_aH = this.field_71102_ce;
      if (this.func_70051_ag()) {
         this.field_70747_aH = (float)((double)this.field_70747_aH + (double)this.field_71102_ce * 0.3D);
      }

      this.func_70659_e((float)iattributeinstance.func_111126_e());
      float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      float f1 = (float)(Math.atan(-this.field_70181_x * 0.20000000298023224D) * 15.0D);
      if (f > 0.1F) {
         f = 0.1F;
      }

      if (!this.field_70122_E || this.func_110143_aJ() <= 0.0F) {
         f = 0.0F;
      }

      if (this.field_70122_E || this.func_110143_aJ() <= 0.0F) {
         f1 = 0.0F;
      }

      this.field_71109_bG += (f - this.field_71109_bG) * 0.4F;
      this.field_70726_aT += (f1 - this.field_70726_aT) * 0.8F;
      if (this.func_110143_aJ() > 0.0F && !this.func_175149_v()) {
         AxisAlignedBB axisalignedbb;
         if (this.func_184218_aH() && !this.func_184187_bx().field_70128_L) {
            axisalignedbb = this.func_174813_aQ().func_111270_a(this.func_184187_bx().func_174813_aQ()).func_72314_b(1.0D, 0.0D, 1.0D);
         } else {
            axisalignedbb = this.func_174813_aQ().func_72314_b(1.0D, 0.5D, 1.0D);
         }

         List<Entity> list = this.field_70170_p.func_72839_b(this, axisalignedbb);

         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (!entity.field_70128_L) {
               this.func_71044_o(entity);
            }
         }
      }

      this.func_192028_j(this.func_192023_dk());
      this.func_192028_j(this.func_192025_dl());
      if (!this.field_70170_p.field_72995_K && (this.field_70143_R > 0.5F || this.func_70090_H() || this.func_184218_aH()) || this.field_71075_bZ.field_75100_b) {
         this.func_192030_dh();
      }

   }

   private void func_192028_j(@Nullable NBTTagCompound p_192028_1_) {
      if (p_192028_1_ != null && !p_192028_1_.func_74764_b("Silent") || !p_192028_1_.func_74767_n("Silent")) {
         String s = p_192028_1_.func_74779_i("id");
         if (s.equals(EntityList.func_191306_a(EntityParrot.class).toString())) {
            EntityParrot.func_192005_a(this.field_70170_p, this);
         }
      }

   }

   private void func_71044_o(Entity p_71044_1_) {
      p_71044_1_.func_70100_b_(this);
   }

   public int func_71037_bA() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184830_b)).intValue();
   }

   public void func_85040_s(int p_85040_1_) {
      this.field_70180_af.func_187227_b(field_184830_b, Integer.valueOf(p_85040_1_));
   }

   public void func_85039_t(int p_85039_1_) {
      int i = this.func_71037_bA();
      this.field_70180_af.func_187227_b(field_184830_b, Integer.valueOf(i + p_85039_1_));
   }

   public void func_70645_a(DamageSource p_70645_1_) {
      super.func_70645_a(p_70645_1_);
      this.func_70105_a(0.2F, 0.2F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70181_x = 0.10000000149011612D;
      if ("Notch".equals(this.func_70005_c_())) {
         this.func_146097_a(new ItemStack(Items.field_151034_e, 1), true, false);
      }

      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         this.func_190776_cN();
         this.field_71071_by.func_70436_m();
      }

      if (p_70645_1_ != null) {
         this.field_70159_w = (double)(-MathHelper.func_76134_b((this.field_70739_aP + this.field_70177_z) * 0.017453292F) * 0.1F);
         this.field_70179_y = (double)(-MathHelper.func_76126_a((this.field_70739_aP + this.field_70177_z) * 0.017453292F) * 0.1F);
      } else {
         this.field_70159_w = 0.0D;
         this.field_70179_y = 0.0D;
      }

      this.func_71029_a(StatList.field_188069_A);
      this.func_175145_a(StatList.field_188098_h);
      this.func_70066_B();
      this.func_70052_a(0, false);
   }

   protected void func_190776_cN() {
      for(int i = 0; i < this.field_71071_by.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_71071_by.func_70301_a(i);
         if (!itemstack.func_190926_b() && EnchantmentHelper.func_190939_c(itemstack)) {
            this.field_71071_by.func_70304_b(i);
         }
      }

   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      if (p_184601_1_ == DamageSource.field_76370_b) {
         return SoundEvents.field_193806_fH;
      } else {
         return p_184601_1_ == DamageSource.field_76369_e ? SoundEvents.field_193805_fG : SoundEvents.field_187800_eb;
      }
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187798_ea;
   }

   @Nullable
   public EntityItem func_71040_bB(boolean p_71040_1_) {
      return this.func_146097_a(this.field_71071_by.func_70298_a(this.field_71071_by.field_70461_c, p_71040_1_ && !this.field_71071_by.func_70448_g().func_190926_b() ? this.field_71071_by.func_70448_g().func_190916_E() : 1), false, true);
   }

   @Nullable
   public EntityItem func_71019_a(ItemStack p_71019_1_, boolean p_71019_2_) {
      return this.func_146097_a(p_71019_1_, false, p_71019_2_);
   }

   @Nullable
   public EntityItem func_146097_a(ItemStack p_146097_1_, boolean p_146097_2_, boolean p_146097_3_) {
      if (p_146097_1_.func_190926_b()) {
         return null;
      } else {
         double d0 = this.field_70163_u - 0.30000001192092896D + (double)this.func_70047_e();
         EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, d0, this.field_70161_v, p_146097_1_);
         entityitem.func_174867_a(40);
         if (p_146097_3_) {
            entityitem.func_145799_b(this.func_70005_c_());
         }

         if (p_146097_2_) {
            float f = this.field_70146_Z.nextFloat() * 0.5F;
            float f1 = this.field_70146_Z.nextFloat() * 6.2831855F;
            entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(f1) * f);
            entityitem.field_70179_y = (double)(MathHelper.func_76134_b(f1) * f);
            entityitem.field_70181_x = 0.20000000298023224D;
         } else {
            float f2 = 0.3F;
            entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * MathHelper.func_76134_b(this.field_70125_A * 0.017453292F) * f2);
            entityitem.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * MathHelper.func_76134_b(this.field_70125_A * 0.017453292F) * f2);
            entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A * 0.017453292F) * f2 + 0.1F);
            float f3 = this.field_70146_Z.nextFloat() * 6.2831855F;
            f2 = 0.02F * this.field_70146_Z.nextFloat();
            entityitem.field_70159_w += Math.cos((double)f3) * (double)f2;
            entityitem.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
            entityitem.field_70179_y += Math.sin((double)f3) * (double)f2;
         }

         ItemStack itemstack = this.func_184816_a(entityitem);
         if (p_146097_3_) {
            if (!itemstack.func_190926_b()) {
               this.func_71064_a(StatList.func_188058_e(itemstack.func_77973_b()), p_146097_1_.func_190916_E());
            }

            this.func_71029_a(StatList.field_75952_v);
         }

         return entityitem;
      }
   }

   protected ItemStack func_184816_a(EntityItem p_184816_1_) {
      this.field_70170_p.func_72838_d(p_184816_1_);
      return p_184816_1_.func_92059_d();
   }

   public float func_184813_a(IBlockState p_184813_1_) {
      float f = this.field_71071_by.func_184438_a(p_184813_1_);
      if (f > 1.0F) {
         int i = EnchantmentHelper.func_185293_e(this);
         ItemStack itemstack = this.func_184614_ca();
         if (i > 0 && !itemstack.func_190926_b()) {
            f += (float)(i * i + 1);
         }
      }

      if (this.func_70644_a(MobEffects.field_76422_e)) {
         f *= 1.0F + (float)(this.func_70660_b(MobEffects.field_76422_e).func_76458_c() + 1) * 0.2F;
      }

      if (this.func_70644_a(MobEffects.field_76419_f)) {
         float f1;
         switch(this.func_70660_b(MobEffects.field_76419_f).func_76458_c()) {
         case 0:
            f1 = 0.3F;
            break;
         case 1:
            f1 = 0.09F;
            break;
         case 2:
            f1 = 0.0027F;
            break;
         case 3:
         default:
            f1 = 8.1E-4F;
         }

         f *= f1;
      }

      if (this.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i(this)) {
         f /= 5.0F;
      }

      if (!this.field_70122_E) {
         f /= 5.0F;
      }

      return f;
   }

   public boolean func_184823_b(IBlockState p_184823_1_) {
      return this.field_71071_by.func_184432_b(p_184823_1_);
   }

   public static void func_189806_a(DataFixer p_189806_0_) {
      p_189806_0_.func_188258_a(FixTypes.PLAYER, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            DataFixesManager.func_188278_b(p_188266_1_, p_188266_2_, p_188266_3_, "Inventory");
            DataFixesManager.func_188278_b(p_188266_1_, p_188266_2_, p_188266_3_, "EnderItems");
            if (p_188266_2_.func_150297_b("ShoulderEntityLeft", 10)) {
               p_188266_2_.func_74782_a("ShoulderEntityLeft", p_188266_1_.func_188251_a(FixTypes.ENTITY, p_188266_2_.func_74775_l("ShoulderEntityLeft"), p_188266_3_));
            }

            if (p_188266_2_.func_150297_b("ShoulderEntityRight", 10)) {
               p_188266_2_.func_74782_a("ShoulderEntityRight", p_188266_1_.func_188251_a(FixTypes.ENTITY, p_188266_2_.func_74775_l("ShoulderEntityRight"), p_188266_3_));
            }

            return p_188266_2_;
         }
      });
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_184221_a(func_146094_a(this.field_146106_i));
      NBTTagList nbttaglist = p_70037_1_.func_150295_c("Inventory", 10);
      this.field_71071_by.func_70443_b(nbttaglist);
      this.field_71071_by.field_70461_c = p_70037_1_.func_74762_e("SelectedItemSlot");
      this.field_71083_bS = p_70037_1_.func_74767_n("Sleeping");
      this.field_71076_b = p_70037_1_.func_74765_d("SleepTimer");
      this.field_71106_cc = p_70037_1_.func_74760_g("XpP");
      this.field_71068_ca = p_70037_1_.func_74762_e("XpLevel");
      this.field_71067_cb = p_70037_1_.func_74762_e("XpTotal");
      this.field_175152_f = p_70037_1_.func_74762_e("XpSeed");
      if (this.field_175152_f == 0) {
         this.field_175152_f = this.field_70146_Z.nextInt();
      }

      this.func_85040_s(p_70037_1_.func_74762_e("Score"));
      if (this.field_71083_bS) {
         this.field_71081_bT = new BlockPos(this);
         this.func_70999_a(true, true, false);
      }

      if (p_70037_1_.func_150297_b("SpawnX", 99) && p_70037_1_.func_150297_b("SpawnY", 99) && p_70037_1_.func_150297_b("SpawnZ", 99)) {
         this.field_71077_c = new BlockPos(p_70037_1_.func_74762_e("SpawnX"), p_70037_1_.func_74762_e("SpawnY"), p_70037_1_.func_74762_e("SpawnZ"));
         this.field_82248_d = p_70037_1_.func_74767_n("SpawnForced");
      }

      this.field_71100_bB.func_75112_a(p_70037_1_);
      this.field_71075_bZ.func_75095_b(p_70037_1_);
      if (p_70037_1_.func_150297_b("EnderItems", 9)) {
         NBTTagList nbttaglist1 = p_70037_1_.func_150295_c("EnderItems", 10);
         this.field_71078_a.func_70486_a(nbttaglist1);
      }

      if (p_70037_1_.func_150297_b("ShoulderEntityLeft", 10)) {
         this.func_192029_h(p_70037_1_.func_74775_l("ShoulderEntityLeft"));
      }

      if (p_70037_1_.func_150297_b("ShoulderEntityRight", 10)) {
         this.func_192031_i(p_70037_1_.func_74775_l("ShoulderEntityRight"));
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("DataVersion", 1139);
      p_70014_1_.func_74782_a("Inventory", this.field_71071_by.func_70442_a(new NBTTagList()));
      p_70014_1_.func_74768_a("SelectedItemSlot", this.field_71071_by.field_70461_c);
      p_70014_1_.func_74757_a("Sleeping", this.field_71083_bS);
      p_70014_1_.func_74777_a("SleepTimer", (short)this.field_71076_b);
      p_70014_1_.func_74776_a("XpP", this.field_71106_cc);
      p_70014_1_.func_74768_a("XpLevel", this.field_71068_ca);
      p_70014_1_.func_74768_a("XpTotal", this.field_71067_cb);
      p_70014_1_.func_74768_a("XpSeed", this.field_175152_f);
      p_70014_1_.func_74768_a("Score", this.func_71037_bA());
      if (this.field_71077_c != null) {
         p_70014_1_.func_74768_a("SpawnX", this.field_71077_c.func_177958_n());
         p_70014_1_.func_74768_a("SpawnY", this.field_71077_c.func_177956_o());
         p_70014_1_.func_74768_a("SpawnZ", this.field_71077_c.func_177952_p());
         p_70014_1_.func_74757_a("SpawnForced", this.field_82248_d);
      }

      this.field_71100_bB.func_75117_b(p_70014_1_);
      this.field_71075_bZ.func_75091_a(p_70014_1_);
      p_70014_1_.func_74782_a("EnderItems", this.field_71078_a.func_70487_g());
      if (!this.func_192023_dk().func_82582_d()) {
         p_70014_1_.func_74782_a("ShoulderEntityLeft", this.func_192023_dk());
      }

      if (!this.func_192025_dl().func_82582_d()) {
         p_70014_1_.func_74782_a("ShoulderEntityRight", this.func_192025_dl());
      }

   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (this.field_71075_bZ.field_75102_a && !p_70097_1_.func_76357_e()) {
         return false;
      } else {
         this.field_70708_bq = 0;
         if (this.func_110143_aJ() <= 0.0F) {
            return false;
         } else {
            if (this.func_70608_bn() && !this.field_70170_p.field_72995_K) {
               this.func_70999_a(true, true, false);
            }

            this.func_192030_dh();
            if (p_70097_1_.func_76350_n()) {
               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
                  p_70097_2_ = 0.0F;
               }

               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY) {
                  p_70097_2_ = Math.min(p_70097_2_ / 2.0F + 1.0F, p_70097_2_);
               }

               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                  p_70097_2_ = p_70097_2_ * 3.0F / 2.0F;
               }
            }

            return p_70097_2_ == 0.0F ? false : super.func_70097_a(p_70097_1_, p_70097_2_);
         }
      }
   }

   protected void func_190629_c(EntityLivingBase p_190629_1_) {
      super.func_190629_c(p_190629_1_);
      if (p_190629_1_.func_184614_ca().func_77973_b() instanceof ItemAxe) {
         this.func_190777_m(true);
      }

   }

   public boolean func_96122_a(EntityPlayer p_96122_1_) {
      Team team = this.func_96124_cp();
      Team team1 = p_96122_1_.func_96124_cp();
      if (team == null) {
         return true;
      } else {
         return !team.func_142054_a(team1) ? true : team.func_96665_g();
      }
   }

   protected void func_70675_k(float p_70675_1_) {
      this.field_71071_by.func_70449_g(p_70675_1_);
   }

   protected void func_184590_k(float p_184590_1_) {
      if (p_184590_1_ >= 3.0F && this.field_184627_bm.func_77973_b() == Items.field_185159_cQ) {
         int i = 1 + MathHelper.func_76141_d(p_184590_1_);
         this.field_184627_bm.func_77972_a(i, this);
         if (this.field_184627_bm.func_190926_b()) {
            EnumHand enumhand = this.func_184600_cs();
            if (enumhand == EnumHand.MAIN_HAND) {
               this.func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
            } else {
               this.func_184201_a(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a);
            }

            this.field_184627_bm = ItemStack.field_190927_a;
            this.func_184185_a(SoundEvents.field_187769_eM, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
         }
      }

   }

   public float func_82243_bO() {
      int i = 0;

      for(ItemStack itemstack : this.field_71071_by.field_70460_b) {
         if (!itemstack.func_190926_b()) {
            ++i;
         }
      }

      return (float)i / (float)this.field_71071_by.field_70460_b.size();
   }

   protected void func_70665_d(DamageSource p_70665_1_, float p_70665_2_) {
      if (!this.func_180431_b(p_70665_1_)) {
         p_70665_2_ = this.func_70655_b(p_70665_1_, p_70665_2_);
         p_70665_2_ = this.func_70672_c(p_70665_1_, p_70665_2_);
         float f = p_70665_2_;
         p_70665_2_ = Math.max(p_70665_2_ - this.func_110139_bj(), 0.0F);
         this.func_110149_m(this.func_110139_bj() - (f - p_70665_2_));
         if (p_70665_2_ != 0.0F) {
            this.func_71020_j(p_70665_1_.func_76345_d());
            float f1 = this.func_110143_aJ();
            this.func_70606_j(this.func_110143_aJ() - p_70665_2_);
            this.func_110142_aN().func_94547_a(p_70665_1_, f1, p_70665_2_);
            if (p_70665_2_ < 3.4028235E37F) {
               this.func_71064_a(StatList.field_188112_z, Math.round(p_70665_2_ * 10.0F));
            }

         }
      }
   }

   public void func_175141_a(TileEntitySign p_175141_1_) {
   }

   public void func_184809_a(CommandBlockBaseLogic p_184809_1_) {
   }

   public void func_184824_a(TileEntityCommandBlock p_184824_1_) {
   }

   public void func_189807_a(TileEntityStructure p_189807_1_) {
   }

   public void func_180472_a(IMerchant p_180472_1_) {
   }

   public void func_71007_a(IInventory p_71007_1_) {
   }

   public void func_184826_a(AbstractHorse p_184826_1_, IInventory p_184826_2_) {
   }

   public void func_180468_a(IInteractionObject p_180468_1_) {
   }

   public void func_184814_a(ItemStack p_184814_1_, EnumHand p_184814_2_) {
   }

   public EnumActionResult func_190775_a(Entity p_190775_1_, EnumHand p_190775_2_) {
      if (this.func_175149_v()) {
         if (p_190775_1_ instanceof IInventory) {
            this.func_71007_a((IInventory)p_190775_1_);
         }

         return EnumActionResult.PASS;
      } else {
         ItemStack itemstack = this.func_184586_b(p_190775_2_);
         ItemStack itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.func_77946_l();
         if (p_190775_1_.func_184230_a(this, p_190775_2_)) {
            if (this.field_71075_bZ.field_75098_d && itemstack == this.func_184586_b(p_190775_2_) && itemstack.func_190916_E() < itemstack1.func_190916_E()) {
               itemstack.func_190920_e(itemstack1.func_190916_E());
            }

            return EnumActionResult.SUCCESS;
         } else {
            if (!itemstack.func_190926_b() && p_190775_1_ instanceof EntityLivingBase) {
               if (this.field_71075_bZ.field_75098_d) {
                  itemstack = itemstack1;
               }

               if (itemstack.func_111282_a(this, (EntityLivingBase)p_190775_1_, p_190775_2_)) {
                  if (itemstack.func_190926_b() && !this.field_71075_bZ.field_75098_d) {
                     this.func_184611_a(p_190775_2_, ItemStack.field_190927_a);
                  }

                  return EnumActionResult.SUCCESS;
               }
            }

            return EnumActionResult.PASS;
         }
      }
   }

   public double func_70033_W() {
      return -0.35D;
   }

   public void func_184210_p() {
      super.func_184210_p();
      this.field_184245_j = 0;
   }

   public void func_71059_n(Entity p_71059_1_) {
      if (p_71059_1_.func_70075_an()) {
         if (!p_71059_1_.func_85031_j(this)) {
            float f = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            float f1;
            if (p_71059_1_ instanceof EntityLivingBase) {
               f1 = EnchantmentHelper.func_152377_a(this.func_184614_ca(), ((EntityLivingBase)p_71059_1_).func_70668_bt());
            } else {
               f1 = EnchantmentHelper.func_152377_a(this.func_184614_ca(), EnumCreatureAttribute.UNDEFINED);
            }

            float f2 = this.func_184825_o(0.5F);
            f = f * (0.2F + f2 * f2 * 0.8F);
            f1 = f1 * f2;
            this.func_184821_cY();
            if (f > 0.0F || f1 > 0.0F) {
               boolean flag = f2 > 0.9F;
               boolean flag1 = false;
               int i = 0;
               i = i + EnchantmentHelper.func_77501_a(this);
               if (this.func_70051_ag() && flag) {
                  this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187721_dT, this.func_184176_by(), 1.0F, 1.0F);
                  ++i;
                  flag1 = true;
               }

               boolean flag2 = flag && this.field_70143_R > 0.0F && !this.field_70122_E && !this.func_70617_f_() && !this.func_70090_H() && !this.func_70644_a(MobEffects.field_76440_q) && !this.func_184218_aH() && p_71059_1_ instanceof EntityLivingBase;
               flag2 = flag2 && !this.func_70051_ag();
               if (flag2) {
                  f *= 1.5F;
               }

               f = f + f1;
               boolean flag3 = false;
               double d0 = (double)(this.field_70140_Q - this.field_70141_P);
               if (flag && !flag2 && !flag1 && this.field_70122_E && d0 < (double)this.func_70689_ay()) {
                  ItemStack itemstack = this.func_184586_b(EnumHand.MAIN_HAND);
                  if (itemstack.func_77973_b() instanceof ItemSword) {
                     flag3 = true;
                  }
               }

               float f4 = 0.0F;
               boolean flag4 = false;
               int j = EnchantmentHelper.func_90036_a(this);
               if (p_71059_1_ instanceof EntityLivingBase) {
                  f4 = ((EntityLivingBase)p_71059_1_).func_110143_aJ();
                  if (j > 0 && !p_71059_1_.func_70027_ad()) {
                     flag4 = true;
                     p_71059_1_.func_70015_d(1);
                  }
               }

               double d1 = p_71059_1_.field_70159_w;
               double d2 = p_71059_1_.field_70181_x;
               double d3 = p_71059_1_.field_70179_y;
               boolean flag5 = p_71059_1_.func_70097_a(DamageSource.func_76365_a(this), f);
               if (flag5) {
                  if (i > 0) {
                     if (p_71059_1_ instanceof EntityLivingBase) {
                        ((EntityLivingBase)p_71059_1_).func_70653_a(this, (float)i * 0.5F, (double)MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), (double)(-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F)));
                     } else {
                        p_71059_1_.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * (float)i * 0.5F));
                     }

                     this.field_70159_w *= 0.6D;
                     this.field_70179_y *= 0.6D;
                     this.func_70031_b(false);
                  }

                  if (flag3) {
                     float f3 = 1.0F + EnchantmentHelper.func_191527_a(this) * f;

                     for(EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, p_71059_1_.func_174813_aQ().func_72314_b(1.0D, 0.25D, 1.0D))) {
                        if (entitylivingbase != this && entitylivingbase != p_71059_1_ && !this.func_184191_r(entitylivingbase) && this.func_70068_e(entitylivingbase) < 9.0D) {
                           entitylivingbase.func_70653_a(this, 0.4F, (double)MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), (double)(-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F)));
                           entitylivingbase.func_70097_a(DamageSource.func_76365_a(this), f3);
                        }
                     }

                     this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187730_dW, this.func_184176_by(), 1.0F, 1.0F);
                     this.func_184810_cG();
                  }

                  if (p_71059_1_ instanceof EntityPlayerMP && p_71059_1_.field_70133_I) {
                     ((EntityPlayerMP)p_71059_1_).field_71135_a.func_147359_a(new SPacketEntityVelocity(p_71059_1_));
                     p_71059_1_.field_70133_I = false;
                     p_71059_1_.field_70159_w = d1;
                     p_71059_1_.field_70181_x = d2;
                     p_71059_1_.field_70179_y = d3;
                  }

                  if (flag2) {
                     this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187718_dS, this.func_184176_by(), 1.0F, 1.0F);
                     this.func_71009_b(p_71059_1_);
                  }

                  if (!flag2 && !flag3) {
                     if (flag) {
                        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187727_dV, this.func_184176_by(), 1.0F, 1.0F);
                     } else {
                        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187733_dX, this.func_184176_by(), 1.0F, 1.0F);
                     }
                  }

                  if (f1 > 0.0F) {
                     this.func_71047_c(p_71059_1_);
                  }

                  this.func_130011_c(p_71059_1_);
                  if (p_71059_1_ instanceof EntityLivingBase) {
                     EnchantmentHelper.func_151384_a((EntityLivingBase)p_71059_1_, this);
                  }

                  EnchantmentHelper.func_151385_b(this, p_71059_1_);
                  ItemStack itemstack1 = this.func_184614_ca();
                  Entity entity = p_71059_1_;
                  if (p_71059_1_ instanceof MultiPartEntityPart) {
                     IEntityMultiPart ientitymultipart = ((MultiPartEntityPart)p_71059_1_).field_70259_a;
                     if (ientitymultipart instanceof EntityLivingBase) {
                        entity = (EntityLivingBase)ientitymultipart;
                     }
                  }

                  if (!itemstack1.func_190926_b() && entity instanceof EntityLivingBase) {
                     itemstack1.func_77961_a((EntityLivingBase)entity, this);
                     if (itemstack1.func_190926_b()) {
                        this.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
                     }
                  }

                  if (p_71059_1_ instanceof EntityLivingBase) {
                     float f5 = f4 - ((EntityLivingBase)p_71059_1_).func_110143_aJ();
                     this.func_71064_a(StatList.field_188111_y, Math.round(f5 * 10.0F));
                     if (j > 0) {
                        p_71059_1_.func_70015_d(j * 4);
                     }

                     if (this.field_70170_p instanceof WorldServer && f5 > 2.0F) {
                        int k = (int)((double)f5 * 0.5D);
                        ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.DAMAGE_INDICATOR, p_71059_1_.field_70165_t, p_71059_1_.field_70163_u + (double)(p_71059_1_.field_70131_O * 0.5F), p_71059_1_.field_70161_v, k, 0.1D, 0.0D, 0.1D, 0.2D);
                     }
                  }

                  this.func_71020_j(0.1F);
               } else {
                  this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187724_dU, this.func_184176_by(), 1.0F, 1.0F);
                  if (flag4) {
                     p_71059_1_.func_70066_B();
                  }
               }
            }

         }
      }
   }

   public void func_190777_m(boolean p_190777_1_) {
      float f = 0.25F + (float)EnchantmentHelper.func_185293_e(this) * 0.05F;
      if (p_190777_1_) {
         f += 0.75F;
      }

      if (this.field_70146_Z.nextFloat() < f) {
         this.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
         this.func_184602_cy();
         this.field_70170_p.func_72960_a(this, (byte)30);
      }

   }

   public void func_71009_b(Entity p_71009_1_) {
   }

   public void func_71047_c(Entity p_71047_1_) {
   }

   public void func_184810_cG() {
      double d0 = (double)(-MathHelper.func_76126_a(this.field_70177_z * 0.017453292F));
      double d1 = (double)MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
      if (this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.SWEEP_ATTACK, this.field_70165_t + d0, this.field_70163_u + (double)this.field_70131_O * 0.5D, this.field_70161_v + d1, 0, d0, 0.0D, d1, 0.0D);
      }

   }

   public void func_71004_bE() {
   }

   public void func_70106_y() {
      super.func_70106_y();
      this.field_71069_bz.func_75134_a(this);
      if (this.field_71070_bA != null) {
         this.field_71070_bA.func_75134_a(this);
      }

   }

   public boolean func_70094_T() {
      return !this.field_71083_bS && super.func_70094_T();
   }

   public boolean func_175144_cb() {
      return false;
   }

   public GameProfile func_146103_bH() {
      return this.field_146106_i;
   }

   public EntityPlayer.SleepResult func_180469_a(BlockPos p_180469_1_) {
      EnumFacing enumfacing = (EnumFacing)this.field_70170_p.func_180495_p(p_180469_1_).func_177229_b(BlockHorizontal.field_185512_D);
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70608_bn() || !this.func_70089_S()) {
            return EntityPlayer.SleepResult.OTHER_PROBLEM;
         }

         if (!this.field_70170_p.field_73011_w.func_76569_d()) {
            return EntityPlayer.SleepResult.NOT_POSSIBLE_HERE;
         }

         if (this.field_70170_p.func_72935_r()) {
            return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
         }

         if (!this.func_190774_a(p_180469_1_, enumfacing)) {
            return EntityPlayer.SleepResult.TOO_FAR_AWAY;
         }

         double d0 = 8.0D;
         double d1 = 5.0D;
         List<EntityMob> list = this.field_70170_p.<EntityMob>func_175647_a(EntityMob.class, new AxisAlignedBB((double)p_180469_1_.func_177958_n() - 8.0D, (double)p_180469_1_.func_177956_o() - 5.0D, (double)p_180469_1_.func_177952_p() - 8.0D, (double)p_180469_1_.func_177958_n() + 8.0D, (double)p_180469_1_.func_177956_o() + 5.0D, (double)p_180469_1_.func_177952_p() + 8.0D), new EntityPlayer.SleepEnemyPredicate(this));
         if (!list.isEmpty()) {
            return EntityPlayer.SleepResult.NOT_SAFE;
         }
      }

      if (this.func_184218_aH()) {
         this.func_184210_p();
      }

      this.func_192030_dh();
      this.func_70105_a(0.2F, 0.2F);
      if (this.field_70170_p.func_175667_e(p_180469_1_)) {
         float f1 = 0.5F + (float)enumfacing.func_82601_c() * 0.4F;
         float f = 0.5F + (float)enumfacing.func_82599_e() * 0.4F;
         this.func_175139_a(enumfacing);
         this.func_70107_b((double)((float)p_180469_1_.func_177958_n() + f1), (double)((float)p_180469_1_.func_177956_o() + 0.6875F), (double)((float)p_180469_1_.func_177952_p() + f));
      } else {
         this.func_70107_b((double)((float)p_180469_1_.func_177958_n() + 0.5F), (double)((float)p_180469_1_.func_177956_o() + 0.6875F), (double)((float)p_180469_1_.func_177952_p() + 0.5F));
      }

      this.field_71083_bS = true;
      this.field_71076_b = 0;
      this.field_71081_bT = p_180469_1_;
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72854_c();
      }

      return EntityPlayer.SleepResult.OK;
   }

   private boolean func_190774_a(BlockPos p_190774_1_, EnumFacing p_190774_2_) {
      if (Math.abs(this.field_70165_t - (double)p_190774_1_.func_177958_n()) <= 3.0D && Math.abs(this.field_70163_u - (double)p_190774_1_.func_177956_o()) <= 2.0D && Math.abs(this.field_70161_v - (double)p_190774_1_.func_177952_p()) <= 3.0D) {
         return true;
      } else {
         BlockPos blockpos = p_190774_1_.func_177972_a(p_190774_2_.func_176734_d());
         return Math.abs(this.field_70165_t - (double)blockpos.func_177958_n()) <= 3.0D && Math.abs(this.field_70163_u - (double)blockpos.func_177956_o()) <= 2.0D && Math.abs(this.field_70161_v - (double)blockpos.func_177952_p()) <= 3.0D;
      }
   }

   private void func_175139_a(EnumFacing p_175139_1_) {
      this.field_71079_bU = -1.8F * (float)p_175139_1_.func_82601_c();
      this.field_71089_bV = -1.8F * (float)p_175139_1_.func_82599_e();
   }

   public void func_70999_a(boolean p_70999_1_, boolean p_70999_2_, boolean p_70999_3_) {
      this.func_70105_a(0.6F, 1.8F);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(this.field_71081_bT);
      if (this.field_71081_bT != null && iblockstate.func_177230_c() == Blocks.field_150324_C) {
         this.field_70170_p.func_180501_a(this.field_71081_bT, iblockstate.func_177226_a(BlockBed.field_176471_b, Boolean.valueOf(false)), 4);
         BlockPos blockpos = BlockBed.func_176468_a(this.field_70170_p, this.field_71081_bT, 0);
         if (blockpos == null) {
            blockpos = this.field_71081_bT.func_177984_a();
         }

         this.func_70107_b((double)((float)blockpos.func_177958_n() + 0.5F), (double)((float)blockpos.func_177956_o() + 0.1F), (double)((float)blockpos.func_177952_p() + 0.5F));
      }

      this.field_71083_bS = false;
      if (!this.field_70170_p.field_72995_K && p_70999_2_) {
         this.field_70170_p.func_72854_c();
      }

      this.field_71076_b = p_70999_1_ ? 0 : 100;
      if (p_70999_3_) {
         this.func_180473_a(this.field_71081_bT, false);
      }

   }

   private boolean func_175143_p() {
      return this.field_70170_p.func_180495_p(this.field_71081_bT).func_177230_c() == Blocks.field_150324_C;
   }

   @Nullable
   public static BlockPos func_180467_a(World p_180467_0_, BlockPos p_180467_1_, boolean p_180467_2_) {
      Block block = p_180467_0_.func_180495_p(p_180467_1_).func_177230_c();
      if (block != Blocks.field_150324_C) {
         if (!p_180467_2_) {
            return null;
         } else {
            boolean flag = block.func_181623_g();
            boolean flag1 = p_180467_0_.func_180495_p(p_180467_1_.func_177984_a()).func_177230_c().func_181623_g();
            return flag && flag1 ? p_180467_1_ : null;
         }
      } else {
         return BlockBed.func_176468_a(p_180467_0_, p_180467_1_, 0);
      }
   }

   public float func_71051_bG() {
      if (this.field_71081_bT != null) {
         EnumFacing enumfacing = (EnumFacing)this.field_70170_p.func_180495_p(this.field_71081_bT).func_177229_b(BlockHorizontal.field_185512_D);
         switch(enumfacing) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         }
      }

      return 0.0F;
   }

   public boolean func_70608_bn() {
      return this.field_71083_bS;
   }

   public boolean func_71026_bH() {
      return this.field_71083_bS && this.field_71076_b >= 100;
   }

   public int func_71060_bI() {
      return this.field_71076_b;
   }

   public void func_146105_b(ITextComponent p_146105_1_, boolean p_146105_2_) {
   }

   public BlockPos func_180470_cg() {
      return this.field_71077_c;
   }

   public boolean func_82245_bX() {
      return this.field_82248_d;
   }

   public void func_180473_a(BlockPos p_180473_1_, boolean p_180473_2_) {
      if (p_180473_1_ != null) {
         this.field_71077_c = p_180473_1_;
         this.field_82248_d = p_180473_2_;
      } else {
         this.field_71077_c = null;
         this.field_82248_d = false;
      }

   }

   public void func_71029_a(StatBase p_71029_1_) {
      this.func_71064_a(p_71029_1_, 1);
   }

   public void func_71064_a(StatBase p_71064_1_, int p_71064_2_) {
   }

   public void func_175145_a(StatBase p_175145_1_) {
   }

   public void func_192021_a(List<IRecipe> p_192021_1_) {
   }

   public void func_193102_a(ResourceLocation[] p_193102_1_) {
   }

   public void func_192022_b(List<IRecipe> p_192022_1_) {
   }

   public void func_70664_aZ() {
      super.func_70664_aZ();
      this.func_71029_a(StatList.field_75953_u);
      if (this.func_70051_ag()) {
         this.func_71020_j(0.2F);
      } else {
         this.func_71020_j(0.05F);
      }

   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      double d0 = this.field_70165_t;
      double d1 = this.field_70163_u;
      double d2 = this.field_70161_v;
      if (this.field_71075_bZ.field_75100_b && !this.func_184218_aH()) {
         double d3 = this.field_70181_x;
         float f = this.field_70747_aH;
         this.field_70747_aH = this.field_71075_bZ.func_75093_a() * (float)(this.func_70051_ag() ? 2 : 1);
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
         this.field_70181_x = d3 * 0.6D;
         this.field_70747_aH = f;
         this.field_70143_R = 0.0F;
         this.func_70052_a(7, false);
      } else {
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
      }

      this.func_71000_j(this.field_70165_t - d0, this.field_70163_u - d1, this.field_70161_v - d2);
   }

   public float func_70689_ay() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
   }

   public void func_71000_j(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
      if (!this.func_184218_aH()) {
         if (this.func_70055_a(Material.field_151586_h)) {
            int i = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
            if (i > 0) {
               this.func_71064_a(StatList.field_188105_q, i);
               this.func_71020_j(0.01F * (float)i * 0.01F);
            }
         } else if (this.func_70090_H()) {
            int j = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
            if (j > 0) {
               this.func_71064_a(StatList.field_75946_m, j);
               this.func_71020_j(0.01F * (float)j * 0.01F);
            }
         } else if (this.func_70617_f_()) {
            if (p_71000_3_ > 0.0D) {
               this.func_71064_a(StatList.field_188103_o, (int)Math.round(p_71000_3_ * 100.0D));
            }
         } else if (this.field_70122_E) {
            int k = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
            if (k > 0) {
               if (this.func_70051_ag()) {
                  this.func_71064_a(StatList.field_188102_l, k);
                  this.func_71020_j(0.1F * (float)k * 0.01F);
               } else if (this.func_70093_af()) {
                  this.func_71064_a(StatList.field_188101_k, k);
                  this.func_71020_j(0.0F * (float)k * 0.01F);
               } else {
                  this.func_71064_a(StatList.field_188100_j, k);
                  this.func_71020_j(0.0F * (float)k * 0.01F);
               }
            }
         } else if (this.func_184613_cA()) {
            int l = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
            this.func_71064_a(StatList.field_188110_v, l);
         } else {
            int i1 = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
            if (i1 > 25) {
               this.func_71064_a(StatList.field_188104_p, i1);
            }
         }

      }
   }

   private void func_71015_k(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
      if (this.func_184218_aH()) {
         int i = Math.round(MathHelper.func_76133_a(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);
         if (i > 0) {
            if (this.func_184187_bx() instanceof EntityMinecart) {
               this.func_71064_a(StatList.field_188106_r, i);
            } else if (this.func_184187_bx() instanceof EntityBoat) {
               this.func_71064_a(StatList.field_188107_s, i);
            } else if (this.func_184187_bx() instanceof EntityPig) {
               this.func_71064_a(StatList.field_188108_t, i);
            } else if (this.func_184187_bx() instanceof AbstractHorse) {
               this.func_71064_a(StatList.field_188109_u, i);
            }
         }
      }

   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      if (!this.field_71075_bZ.field_75101_c) {
         if (p_180430_1_ >= 2.0F) {
            this.func_71064_a(StatList.field_75943_n, (int)Math.round((double)p_180430_1_ * 100.0D));
         }

         super.func_180430_e(p_180430_1_, p_180430_2_);
      }
   }

   protected void func_71061_d_() {
      if (!this.func_175149_v()) {
         super.func_71061_d_();
      }

   }

   protected SoundEvent func_184588_d(int p_184588_1_) {
      return p_184588_1_ > 4 ? SoundEvents.field_187736_dY : SoundEvents.field_187804_ed;
   }

   public void func_70074_a(EntityLivingBase p_70074_1_) {
      EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.field_75627_a.get(EntityList.func_191301_a(p_70074_1_));
      if (entitylist$entityegginfo != null) {
         this.func_71029_a(entitylist$entityegginfo.field_151512_d);
      }

   }

   public void func_70110_aj() {
      if (!this.field_71075_bZ.field_75100_b) {
         super.func_70110_aj();
      }

   }

   public void func_71023_q(int p_71023_1_) {
      this.func_85039_t(p_71023_1_);
      int i = Integer.MAX_VALUE - this.field_71067_cb;
      if (p_71023_1_ > i) {
         p_71023_1_ = i;
      }

      this.field_71106_cc += (float)p_71023_1_ / (float)this.func_71050_bK();

      for(this.field_71067_cb += p_71023_1_; this.field_71106_cc >= 1.0F; this.field_71106_cc /= (float)this.func_71050_bK()) {
         this.field_71106_cc = (this.field_71106_cc - 1.0F) * (float)this.func_71050_bK();
         this.func_82242_a(1);
      }

   }

   public int func_175138_ci() {
      return this.field_175152_f;
   }

   public void func_192024_a(ItemStack p_192024_1_, int p_192024_2_) {
      this.field_71068_ca -= p_192024_2_;
      if (this.field_71068_ca < 0) {
         this.field_71068_ca = 0;
         this.field_71106_cc = 0.0F;
         this.field_71067_cb = 0;
      }

      this.field_175152_f = this.field_70146_Z.nextInt();
   }

   public void func_82242_a(int p_82242_1_) {
      this.field_71068_ca += p_82242_1_;
      if (this.field_71068_ca < 0) {
         this.field_71068_ca = 0;
         this.field_71106_cc = 0.0F;
         this.field_71067_cb = 0;
      }

      if (p_82242_1_ > 0 && this.field_71068_ca % 5 == 0 && (float)this.field_82249_h < (float)this.field_70173_aa - 100.0F) {
         float f = this.field_71068_ca > 30 ? 1.0F : (float)this.field_71068_ca / 30.0F;
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187802_ec, this.func_184176_by(), f * 0.75F, 1.0F);
         this.field_82249_h = this.field_70173_aa;
      }

   }

   public int func_71050_bK() {
      if (this.field_71068_ca >= 30) {
         return 112 + (this.field_71068_ca - 30) * 9;
      } else {
         return this.field_71068_ca >= 15 ? 37 + (this.field_71068_ca - 15) * 5 : 7 + this.field_71068_ca * 2;
      }
   }

   public void func_71020_j(float p_71020_1_) {
      if (!this.field_71075_bZ.field_75102_a) {
         if (!this.field_70170_p.field_72995_K) {
            this.field_71100_bB.func_75113_a(p_71020_1_);
         }

      }
   }

   public FoodStats func_71024_bL() {
      return this.field_71100_bB;
   }

   public boolean func_71043_e(boolean p_71043_1_) {
      return (p_71043_1_ || this.field_71100_bB.func_75121_c()) && !this.field_71075_bZ.field_75102_a;
   }

   public boolean func_70996_bM() {
      return this.func_110143_aJ() > 0.0F && this.func_110143_aJ() < this.func_110138_aP();
   }

   public boolean func_175142_cm() {
      return this.field_71075_bZ.field_75099_e;
   }

   public boolean func_175151_a(BlockPos p_175151_1_, EnumFacing p_175151_2_, ItemStack p_175151_3_) {
      if (this.field_71075_bZ.field_75099_e) {
         return true;
      } else if (p_175151_3_.func_190926_b()) {
         return false;
      } else {
         BlockPos blockpos = p_175151_1_.func_177972_a(p_175151_2_.func_176734_d());
         Block block = this.field_70170_p.func_180495_p(blockpos).func_177230_c();
         return p_175151_3_.func_179547_d(block) || p_175151_3_.func_82835_x();
      }
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         int i = this.field_71068_ca * 7;
         return i > 100 ? 100 : i;
      } else {
         return 0;
      }
   }

   protected boolean func_70684_aJ() {
      return true;
   }

   public boolean func_94059_bO() {
      return true;
   }

   protected boolean func_70041_e_() {
      return !this.field_71075_bZ.field_75100_b;
   }

   public void func_71016_p() {
   }

   public void func_71033_a(GameType p_71033_1_) {
   }

   public String func_70005_c_() {
      return this.field_146106_i.getName();
   }

   public InventoryEnderChest func_71005_bN() {
      return this.field_71078_a;
   }

   public ItemStack func_184582_a(EntityEquipmentSlot p_184582_1_) {
      if (p_184582_1_ == EntityEquipmentSlot.MAINHAND) {
         return this.field_71071_by.func_70448_g();
      } else if (p_184582_1_ == EntityEquipmentSlot.OFFHAND) {
         return this.field_71071_by.field_184439_c.get(0);
      } else {
         return p_184582_1_.func_188453_a() == EntityEquipmentSlot.Type.ARMOR ? (ItemStack)this.field_71071_by.field_70460_b.get(p_184582_1_.func_188454_b()) : ItemStack.field_190927_a;
      }
   }

   public void func_184201_a(EntityEquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
      if (p_184201_1_ == EntityEquipmentSlot.MAINHAND) {
         this.func_184606_a_(p_184201_2_);
         this.field_71071_by.field_70462_a.set(this.field_71071_by.field_70461_c, p_184201_2_);
      } else if (p_184201_1_ == EntityEquipmentSlot.OFFHAND) {
         this.func_184606_a_(p_184201_2_);
         this.field_71071_by.field_184439_c.set(0, p_184201_2_);
      } else if (p_184201_1_.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
         this.func_184606_a_(p_184201_2_);
         this.field_71071_by.field_70460_b.set(p_184201_1_.func_188454_b(), p_184201_2_);
      }

   }

   public boolean func_191521_c(ItemStack p_191521_1_) {
      this.func_184606_a_(p_191521_1_);
      return this.field_71071_by.func_70441_a(p_191521_1_);
   }

   public Iterable<ItemStack> func_184214_aD() {
      return Lists.newArrayList(this.func_184614_ca(), this.func_184592_cb());
   }

   public Iterable<ItemStack> func_184193_aE() {
      return this.field_71071_by.field_70460_b;
   }

   public boolean func_192027_g(NBTTagCompound p_192027_1_) {
      if (!this.func_184218_aH() && this.field_70122_E && !this.func_70090_H()) {
         if (this.func_192023_dk().func_82582_d()) {
            this.func_192029_h(p_192027_1_);
            return true;
         } else if (this.func_192025_dl().func_82582_d()) {
            this.func_192031_i(p_192027_1_);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected void func_192030_dh() {
      this.func_192026_k(this.func_192023_dk());
      this.func_192029_h(new NBTTagCompound());
      this.func_192026_k(this.func_192025_dl());
      this.func_192031_i(new NBTTagCompound());
   }

   private void func_192026_k(@Nullable NBTTagCompound p_192026_1_) {
      if (!this.field_70170_p.field_72995_K && !p_192026_1_.func_82582_d()) {
         Entity entity = EntityList.func_75615_a(p_192026_1_, this.field_70170_p);
         if (entity instanceof EntityTameable) {
            ((EntityTameable)entity).func_184754_b(this.field_96093_i);
         }

         entity.func_70107_b(this.field_70165_t, this.field_70163_u + 0.699999988079071D, this.field_70161_v);
         this.field_70170_p.func_72838_d(entity);
      }

   }

   public boolean func_98034_c(EntityPlayer p_98034_1_) {
      if (!this.func_82150_aj()) {
         return false;
      } else if (p_98034_1_.func_175149_v()) {
         return false;
      } else {
         Team team = this.func_96124_cp();
         return team == null || p_98034_1_ == null || p_98034_1_.func_96124_cp() != team || !team.func_98297_h();
      }
   }

   public abstract boolean func_175149_v();

   public abstract boolean func_184812_l_();

   public boolean func_96092_aw() {
      return !this.field_71075_bZ.field_75100_b;
   }

   public Scoreboard func_96123_co() {
      return this.field_70170_p.func_96441_U();
   }

   public Team func_96124_cp() {
      return this.func_96123_co().func_96509_i(this.func_70005_c_());
   }

   public ITextComponent func_145748_c_() {
      ITextComponent itextcomponent = new TextComponentString(ScorePlayerTeam.func_96667_a(this.func_96124_cp(), this.func_70005_c_()));
      itextcomponent.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.func_70005_c_() + " "));
      itextcomponent.func_150256_b().func_150209_a(this.func_174823_aP());
      itextcomponent.func_150256_b().func_179989_a(this.func_70005_c_());
      return itextcomponent;
   }

   public float func_70047_e() {
      float f = 1.62F;
      if (this.func_70608_bn()) {
         f = 0.2F;
      } else if (!this.func_70093_af() && this.field_70131_O != 1.65F) {
         if (this.func_184613_cA() || this.field_70131_O == 0.6F) {
            f = 0.4F;
         }
      } else {
         f -= 0.08F;
      }

      return f;
   }

   public void func_110149_m(float p_110149_1_) {
      if (p_110149_1_ < 0.0F) {
         p_110149_1_ = 0.0F;
      }

      this.func_184212_Q().func_187227_b(field_184829_a, Float.valueOf(p_110149_1_));
   }

   public float func_110139_bj() {
      return ((Float)this.func_184212_Q().func_187225_a(field_184829_a)).floatValue();
   }

   public static UUID func_146094_a(GameProfile p_146094_0_) {
      UUID uuid = p_146094_0_.getId();
      if (uuid == null) {
         uuid = func_175147_b(p_146094_0_.getName());
      }

      return uuid;
   }

   public static UUID func_175147_b(String p_175147_0_) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_175147_0_).getBytes(StandardCharsets.UTF_8));
   }

   public boolean func_175146_a(LockCode p_175146_1_) {
      if (p_175146_1_.func_180160_a()) {
         return true;
      } else {
         ItemStack itemstack = this.func_184614_ca();
         return !itemstack.func_190926_b() && itemstack.func_82837_s() ? itemstack.func_82833_r().equals(p_175146_1_.func_180159_b()) : false;
      }
   }

   public boolean func_175148_a(EnumPlayerModelParts p_175148_1_) {
      return (((Byte)this.func_184212_Q().func_187225_a(field_184827_bp)).byteValue() & p_175148_1_.func_179327_a()) == p_175148_1_.func_179327_a();
   }

   public boolean func_174792_t_() {
      return this.func_184102_h().field_71305_c[0].func_82736_K().func_82766_b("sendCommandFeedback");
   }

   public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
      if (p_174820_1_ >= 0 && p_174820_1_ < this.field_71071_by.field_70462_a.size()) {
         this.field_71071_by.func_70299_a(p_174820_1_, p_174820_2_);
         return true;
      } else {
         EntityEquipmentSlot entityequipmentslot;
         if (p_174820_1_ == 100 + EntityEquipmentSlot.HEAD.func_188454_b()) {
            entityequipmentslot = EntityEquipmentSlot.HEAD;
         } else if (p_174820_1_ == 100 + EntityEquipmentSlot.CHEST.func_188454_b()) {
            entityequipmentslot = EntityEquipmentSlot.CHEST;
         } else if (p_174820_1_ == 100 + EntityEquipmentSlot.LEGS.func_188454_b()) {
            entityequipmentslot = EntityEquipmentSlot.LEGS;
         } else if (p_174820_1_ == 100 + EntityEquipmentSlot.FEET.func_188454_b()) {
            entityequipmentslot = EntityEquipmentSlot.FEET;
         } else {
            entityequipmentslot = null;
         }

         if (p_174820_1_ == 98) {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, p_174820_2_);
            return true;
         } else if (p_174820_1_ == 99) {
            this.func_184201_a(EntityEquipmentSlot.OFFHAND, p_174820_2_);
            return true;
         } else if (entityequipmentslot == null) {
            int i = p_174820_1_ - 200;
            if (i >= 0 && i < this.field_71078_a.func_70302_i_()) {
               this.field_71078_a.func_70299_a(i, p_174820_2_);
               return true;
            } else {
               return false;
            }
         } else {
            if (!p_174820_2_.func_190926_b()) {
               if (!(p_174820_2_.func_77973_b() instanceof ItemArmor) && !(p_174820_2_.func_77973_b() instanceof ItemElytra)) {
                  if (entityequipmentslot != EntityEquipmentSlot.HEAD) {
                     return false;
                  }
               } else if (EntityLiving.func_184640_d(p_174820_2_) != entityequipmentslot) {
                  return false;
               }
            }

            this.field_71071_by.func_70299_a(entityequipmentslot.func_188454_b() + this.field_71071_by.field_70462_a.size(), p_174820_2_);
            return true;
         }
      }
   }

   public boolean func_175140_cp() {
      return this.field_175153_bG;
   }

   public void func_175150_k(boolean p_175150_1_) {
      this.field_175153_bG = p_175150_1_;
   }

   public EnumHandSide func_184591_cq() {
      return ((Byte)this.field_70180_af.func_187225_a(field_184828_bq)).byteValue() == 0 ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
   }

   public void func_184819_a(EnumHandSide p_184819_1_) {
      this.field_70180_af.func_187227_b(field_184828_bq, Byte.valueOf((byte)(p_184819_1_ == EnumHandSide.LEFT ? 0 : 1)));
   }

   public NBTTagCompound func_192023_dk() {
      return (NBTTagCompound)this.field_70180_af.func_187225_a(field_192032_bt);
   }

   protected void func_192029_h(NBTTagCompound p_192029_1_) {
      this.field_70180_af.func_187227_b(field_192032_bt, p_192029_1_);
   }

   public NBTTagCompound func_192025_dl() {
      return (NBTTagCompound)this.field_70180_af.func_187225_a(field_192033_bu);
   }

   protected void func_192031_i(NBTTagCompound p_192031_1_) {
      this.field_70180_af.func_187227_b(field_192033_bu, p_192031_1_);
   }

   public float func_184818_cX() {
      return (float)(1.0D / this.func_110148_a(SharedMonsterAttributes.field_188790_f).func_111126_e() * 20.0D);
   }

   public float func_184825_o(float p_184825_1_) {
      return MathHelper.func_76131_a(((float)this.field_184617_aD + p_184825_1_) / this.func_184818_cX(), 0.0F, 1.0F);
   }

   public void func_184821_cY() {
      this.field_184617_aD = 0;
   }

   public CooldownTracker func_184811_cZ() {
      return this.field_184832_bU;
   }

   public void func_70108_f(Entity p_70108_1_) {
      if (!this.func_70608_bn()) {
         super.func_70108_f(p_70108_1_);
      }

   }

   public float func_184817_da() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_188792_h).func_111126_e();
   }

   public boolean func_189808_dh() {
      return this.field_71075_bZ.field_75098_d && this.func_70003_b(2, "");
   }

   public static enum EnumChatVisibility {
      FULL(0, "options.chat.visibility.full"),
      SYSTEM(1, "options.chat.visibility.system"),
      HIDDEN(2, "options.chat.visibility.hidden");

      private static final EntityPlayer.EnumChatVisibility[] field_151432_d = new EntityPlayer.EnumChatVisibility[values().length];
      private final int field_151433_e;
      private final String field_151430_f;

      private EnumChatVisibility(int p_i45323_3_, String p_i45323_4_) {
         this.field_151433_e = p_i45323_3_;
         this.field_151430_f = p_i45323_4_;
      }

      public int func_151428_a() {
         return this.field_151433_e;
      }

      public static EntityPlayer.EnumChatVisibility func_151426_a(int p_151426_0_) {
         return field_151432_d[p_151426_0_ % field_151432_d.length];
      }

      public String func_151429_b() {
         return this.field_151430_f;
      }

      static {
         for(EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility : values()) {
            field_151432_d[entityplayer$enumchatvisibility.field_151433_e] = entityplayer$enumchatvisibility;
         }

      }
   }

   static class SleepEnemyPredicate implements Predicate<EntityMob> {
      private final EntityPlayer field_192387_a;

      private SleepEnemyPredicate(EntityPlayer p_i47461_1_) {
         this.field_192387_a = p_i47461_1_;
      }

      public boolean apply(@Nullable EntityMob p_apply_1_) {
         return p_apply_1_.func_191990_c(this.field_192387_a);
      }
   }

   public static enum SleepResult {
      OK,
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW,
      TOO_FAR_AWAY,
      OTHER_PROBLEM,
      NOT_SAFE;
   }
}
