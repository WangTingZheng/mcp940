package net.minecraft.entity;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.CombatRules;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EntityLivingBase extends Entity {
   private static final Logger field_190632_a = LogManager.getLogger();
   private static final UUID field_110156_b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier field_110157_c = (new AttributeModifier(field_110156_b, "Sprinting speed boost", 0.30000001192092896D, 2)).func_111168_a(false);
   protected static final DataParameter<Byte> field_184621_as = EntityDataManager.<Byte>func_187226_a(EntityLivingBase.class, DataSerializers.field_187191_a);
   private static final DataParameter<Float> field_184632_c = EntityDataManager.<Float>func_187226_a(EntityLivingBase.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184633_f = EntityDataManager.<Integer>func_187226_a(EntityLivingBase.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184634_g = EntityDataManager.<Boolean>func_187226_a(EntityLivingBase.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184635_h = EntityDataManager.<Integer>func_187226_a(EntityLivingBase.class, DataSerializers.field_187192_b);
   private AbstractAttributeMap field_110155_d;
   private final CombatTracker field_94063_bt = new CombatTracker(this);
   private final Map<Potion, PotionEffect> field_70713_bf = Maps.<Potion, PotionEffect>newHashMap();
   private final NonNullList<ItemStack> field_184630_bs = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
   private final NonNullList<ItemStack> field_184631_bt = NonNullList.<ItemStack>func_191197_a(4, ItemStack.field_190927_a);
   public boolean field_82175_bq;
   public EnumHand field_184622_au;
   public int field_110158_av;
   public int field_70720_be;
   public int field_70737_aN;
   public int field_70738_aO;
   public float field_70739_aP;
   public int field_70725_aQ;
   public float field_70732_aI;
   public float field_70733_aJ;
   protected int field_184617_aD;
   public float field_184618_aE;
   public float field_70721_aZ;
   public float field_184619_aG;
   public int field_70771_an = 20;
   public float field_70727_aS;
   public float field_70726_aT;
   public float field_70769_ao;
   public float field_70770_ap;
   public float field_70761_aq;
   public float field_70760_ar;
   public float field_70759_as;
   public float field_70758_at;
   public float field_70747_aH = 0.02F;
   protected EntityPlayer field_70717_bb;
   protected int field_70718_bc;
   protected boolean field_70729_aU;
   protected int field_70708_bq;
   protected float field_70768_au;
   protected float field_110154_aX;
   protected float field_70764_aw;
   protected float field_70763_ax;
   protected float field_70741_aB;
   protected int field_70744_aE;
   protected float field_110153_bc;
   protected boolean field_70703_bu;
   public float field_70702_br;
   public float field_70701_bs;
   public float field_191988_bg;
   public float field_70704_bt;
   protected int field_70716_bi;
   protected double field_184623_bh;
   protected double field_184624_bi;
   protected double field_184625_bj;
   protected double field_184626_bk;
   protected double field_70709_bj;
   private boolean field_70752_e = true;
   private EntityLivingBase field_70755_b;
   private int field_70756_c;
   private EntityLivingBase field_110150_bn;
   private int field_142016_bo;
   private float field_70746_aG;
   private int field_70773_bE;
   private float field_110151_bq;
   protected ItemStack field_184627_bm = ItemStack.field_190927_a;
   protected int field_184628_bn;
   protected int field_184629_bo;
   private BlockPos field_184620_bC;
   private DamageSource field_189750_bF;
   private long field_189751_bG;

   public void func_174812_G() {
      this.func_70097_a(DamageSource.field_76380_i, Float.MAX_VALUE);
   }

   public EntityLivingBase(World p_i1594_1_) {
      super(p_i1594_1_);
      this.func_110147_ax();
      this.func_70606_j(this.func_110138_aP());
      this.field_70156_m = true;
      this.field_70770_ap = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70769_ao = (float)Math.random() * 12398.0F;
      this.field_70177_z = (float)(Math.random() * 6.2831854820251465D);
      this.field_70759_as = this.field_70177_z;
      this.field_70138_W = 0.6F;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184621_as, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_184633_f, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184634_g, Boolean.valueOf(false));
      this.field_70180_af.func_187214_a(field_184635_h, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184632_c, Float.valueOf(1.0F));
   }

   protected void func_110147_ax() {
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111267_a);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111266_c);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111263_d);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188791_g);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_189429_h);
   }

   protected void func_184231_a(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
      if (!this.func_70090_H()) {
         this.func_70072_I();
      }

      if (!this.field_70170_p.field_72995_K && this.field_70143_R > 3.0F && p_184231_3_) {
         float f = (float)MathHelper.func_76123_f(this.field_70143_R - 3.0F);
         if (p_184231_4_.func_185904_a() != Material.field_151579_a) {
            double d0 = Math.min((double)(0.2F + f / 15.0F), 2.5D);
            int i = (int)(150.0D * d0);
            ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.BLOCK_DUST, this.field_70165_t, this.field_70163_u, this.field_70161_v, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, Block.func_176210_f(p_184231_4_));
         }
      }

      super.func_184231_a(p_184231_1_, p_184231_3_, p_184231_4_, p_184231_5_);
   }

   public boolean func_70648_aU() {
      return false;
   }

   public void func_70030_z() {
      this.field_70732_aI = this.field_70733_aJ;
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("livingEntityBaseTick");
      boolean flag = this instanceof EntityPlayer;
      if (this.func_70089_S()) {
         if (this.func_70094_T()) {
            this.func_70097_a(DamageSource.field_76368_d, 1.0F);
         } else if (flag && !this.field_70170_p.func_175723_af().func_177743_a(this.func_174813_aQ())) {
            double d0 = this.field_70170_p.func_175723_af().func_177745_a(this) + this.field_70170_p.func_175723_af().func_177742_m();
            if (d0 < 0.0D) {
               double d1 = this.field_70170_p.func_175723_af().func_177727_n();
               if (d1 > 0.0D) {
                  this.func_70097_a(DamageSource.field_76368_d, (float)Math.max(1, MathHelper.func_76128_c(-d0 * d1)));
               }
            }
         }
      }

      if (this.func_70045_F() || this.field_70170_p.field_72995_K) {
         this.func_70066_B();
      }

      boolean flag1 = flag && ((EntityPlayer)this).field_71075_bZ.field_75102_a;
      if (this.func_70089_S()) {
         if (!this.func_70055_a(Material.field_151586_h)) {
            this.func_70050_g(300);
         } else {
            if (!this.func_70648_aU() && !this.func_70644_a(MobEffects.field_76427_o) && !flag1) {
               this.func_70050_g(this.func_70682_h(this.func_70086_ai()));
               if (this.func_70086_ai() == -20) {
                  this.func_70050_g(0);

                  for(int i = 0; i < 8; ++i) {
                     float f2 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     float f = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     float f1 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + (double)f2, this.field_70163_u + (double)f, this.field_70161_v + (double)f1, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  }

                  this.func_70097_a(DamageSource.field_76369_e, 2.0F);
               }
            }

            if (!this.field_70170_p.field_72995_K && this.func_184218_aH() && this.func_184187_bx() instanceof EntityLivingBase) {
               this.func_184210_p();
            }
         }

         if (!this.field_70170_p.field_72995_K) {
            BlockPos blockpos = new BlockPos(this);
            if (!Objects.equal(this.field_184620_bC, blockpos)) {
               this.field_184620_bC = blockpos;
               this.func_184594_b(blockpos);
            }
         }
      }

      if (this.func_70089_S() && this.func_70026_G()) {
         this.func_70066_B();
      }

      this.field_70727_aS = this.field_70726_aT;
      if (this.field_70737_aN > 0) {
         --this.field_70737_aN;
      }

      if (this.field_70172_ad > 0 && !(this instanceof EntityPlayerMP)) {
         --this.field_70172_ad;
      }

      if (this.func_110143_aJ() <= 0.0F) {
         this.func_70609_aI();
      }

      if (this.field_70718_bc > 0) {
         --this.field_70718_bc;
      } else {
         this.field_70717_bb = null;
      }

      if (this.field_110150_bn != null && !this.field_110150_bn.func_70089_S()) {
         this.field_110150_bn = null;
      }

      if (this.field_70755_b != null) {
         if (!this.field_70755_b.func_70089_S()) {
            this.func_70604_c((EntityLivingBase)null);
         } else if (this.field_70173_aa - this.field_70756_c > 100) {
            this.func_70604_c((EntityLivingBase)null);
         }
      }

      this.func_70679_bo();
      this.field_70763_ax = this.field_70764_aw;
      this.field_70760_ar = this.field_70761_aq;
      this.field_70758_at = this.field_70759_as;
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_184594_b(BlockPos p_184594_1_) {
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_185301_j, this);
      if (i > 0) {
         EnchantmentFrostWalker.func_185266_a(this, this.field_70170_p, p_184594_1_, i);
      }

   }

   public boolean func_70631_g_() {
      return false;
   }

   protected void func_70609_aI() {
      ++this.field_70725_aQ;
      if (this.field_70725_aQ == 20) {
         if (!this.field_70170_p.field_72995_K && (this.func_70684_aJ() || this.field_70718_bc > 0 && this.func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))) {
            int i = this.func_70693_a(this.field_70717_bb);

            while(i > 0) {
               int j = EntityXPOrb.func_70527_a(i);
               i -= j;
               this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            }
         }

         this.func_70106_y();

         for(int k = 0; k < 20; ++k) {
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d2, d0, d1);
         }
      }

   }

   protected boolean func_146066_aG() {
      return !this.func_70631_g_();
   }

   protected int func_70682_h(int p_70682_1_) {
      int i = EnchantmentHelper.func_185292_c(this);
      return i > 0 && this.field_70146_Z.nextInt(i + 1) > 0 ? p_70682_1_ : p_70682_1_ - 1;
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      return 0;
   }

   protected boolean func_70684_aJ() {
      return false;
   }

   public Random func_70681_au() {
      return this.field_70146_Z;
   }

   @Nullable
   public EntityLivingBase func_70643_av() {
      return this.field_70755_b;
   }

   public int func_142015_aE() {
      return this.field_70756_c;
   }

   public void func_70604_c(@Nullable EntityLivingBase p_70604_1_) {
      this.field_70755_b = p_70604_1_;
      this.field_70756_c = this.field_70173_aa;
   }

   public EntityLivingBase func_110144_aD() {
      return this.field_110150_bn;
   }

   public int func_142013_aG() {
      return this.field_142016_bo;
   }

   public void func_130011_c(Entity p_130011_1_) {
      if (p_130011_1_ instanceof EntityLivingBase) {
         this.field_110150_bn = (EntityLivingBase)p_130011_1_;
      } else {
         this.field_110150_bn = null;
      }

      this.field_142016_bo = this.field_70173_aa;
   }

   public int func_70654_ax() {
      return this.field_70708_bq;
   }

   protected void func_184606_a_(ItemStack p_184606_1_) {
      if (!p_184606_1_.func_190926_b()) {
         SoundEvent soundevent = SoundEvents.field_187719_p;
         Item item = p_184606_1_.func_77973_b();
         if (item instanceof ItemArmor) {
            soundevent = ((ItemArmor)item).func_82812_d().func_185017_b();
         } else if (item == Items.field_185160_cR) {
            soundevent = SoundEvents.field_191258_p;
         }

         this.func_184185_a(soundevent, 1.0F, 1.0F);
      }
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74776_a("Health", this.func_110143_aJ());
      p_70014_1_.func_74777_a("HurtTime", (short)this.field_70737_aN);
      p_70014_1_.func_74768_a("HurtByTimestamp", this.field_70756_c);
      p_70014_1_.func_74777_a("DeathTime", (short)this.field_70725_aQ);
      p_70014_1_.func_74776_a("AbsorptionAmount", this.func_110139_bj());

      for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
         ItemStack itemstack = this.func_184582_a(entityequipmentslot);
         if (!itemstack.func_190926_b()) {
            this.func_110140_aT().func_111148_a(itemstack.func_111283_C(entityequipmentslot));
         }
      }

      p_70014_1_.func_74782_a("Attributes", SharedMonsterAttributes.func_111257_a(this.func_110140_aT()));

      for(EntityEquipmentSlot entityequipmentslot1 : EntityEquipmentSlot.values()) {
         ItemStack itemstack1 = this.func_184582_a(entityequipmentslot1);
         if (!itemstack1.func_190926_b()) {
            this.func_110140_aT().func_111147_b(itemstack1.func_111283_C(entityequipmentslot1));
         }
      }

      if (!this.field_70713_bf.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(PotionEffect potioneffect : this.field_70713_bf.values()) {
            nbttaglist.func_74742_a(potioneffect.func_82719_a(new NBTTagCompound()));
         }

         p_70014_1_.func_74782_a("ActiveEffects", nbttaglist);
      }

      p_70014_1_.func_74757_a("FallFlying", this.func_184613_cA());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.func_110149_m(p_70037_1_.func_74760_g("AbsorptionAmount"));
      if (p_70037_1_.func_150297_b("Attributes", 9) && this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         SharedMonsterAttributes.func_151475_a(this.func_110140_aT(), p_70037_1_.func_150295_c("Attributes", 10));
      }

      if (p_70037_1_.func_150297_b("ActiveEffects", 9)) {
         NBTTagList nbttaglist = p_70037_1_.func_150295_c("ActiveEffects", 10);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
            PotionEffect potioneffect = PotionEffect.func_82722_b(nbttagcompound);
            if (potioneffect != null) {
               this.field_70713_bf.put(potioneffect.func_188419_a(), potioneffect);
            }
         }
      }

      if (p_70037_1_.func_150297_b("Health", 99)) {
         this.func_70606_j(p_70037_1_.func_74760_g("Health"));
      }

      this.field_70737_aN = p_70037_1_.func_74765_d("HurtTime");
      this.field_70725_aQ = p_70037_1_.func_74765_d("DeathTime");
      this.field_70756_c = p_70037_1_.func_74762_e("HurtByTimestamp");
      if (p_70037_1_.func_150297_b("Team", 8)) {
         String s = p_70037_1_.func_74779_i("Team");
         boolean flag = this.field_70170_p.func_96441_U().func_151392_a(this.func_189512_bd(), s);
         if (!flag) {
            field_190632_a.warn("Unable to add mob to team \"" + s + "\" (that team probably doesn't exist)");
         }
      }

      if (p_70037_1_.func_74767_n("FallFlying")) {
         this.func_70052_a(7, true);
      }

   }

   protected void func_70679_bo() {
      Iterator<Potion> iterator = this.field_70713_bf.keySet().iterator();

      try {
         while(iterator.hasNext()) {
            Potion potion = iterator.next();
            PotionEffect potioneffect = this.field_70713_bf.get(potion);
            if (!potioneffect.func_76455_a(this)) {
               if (!this.field_70170_p.field_72995_K) {
                  iterator.remove();
                  this.func_70688_c(potioneffect);
               }
            } else if (potioneffect.func_76459_b() % 600 == 0) {
               this.func_70695_b(potioneffect, false);
            }
         }
      } catch (ConcurrentModificationException var11) {
         ;
      }

      if (this.field_70752_e) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_175135_B();
         }

         this.field_70752_e = false;
      }

      int i = ((Integer)this.field_70180_af.func_187225_a(field_184633_f)).intValue();
      boolean flag1 = ((Boolean)this.field_70180_af.func_187225_a(field_184634_g)).booleanValue();
      if (i > 0) {
         boolean flag;
         if (this.func_82150_aj()) {
            flag = this.field_70146_Z.nextInt(15) == 0;
         } else {
            flag = this.field_70146_Z.nextBoolean();
         }

         if (flag1) {
            flag &= this.field_70146_Z.nextInt(5) == 0;
         }

         if (flag && i > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;
            this.field_70170_p.func_175688_a(flag1 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, d0, d1, d2);
         }
      }

   }

   protected void func_175135_B() {
      if (this.field_70713_bf.isEmpty()) {
         this.func_175133_bi();
         this.func_82142_c(false);
      } else {
         Collection<PotionEffect> collection = this.field_70713_bf.values();
         this.field_70180_af.func_187227_b(field_184634_g, Boolean.valueOf(func_184593_a(collection)));
         this.field_70180_af.func_187227_b(field_184633_f, Integer.valueOf(PotionUtils.func_185181_a(collection)));
         this.func_82142_c(this.func_70644_a(MobEffects.field_76441_p));
      }

   }

   public static boolean func_184593_a(Collection<PotionEffect> p_184593_0_) {
      for(PotionEffect potioneffect : p_184593_0_) {
         if (!potioneffect.func_82720_e()) {
            return false;
         }
      }

      return true;
   }

   protected void func_175133_bi() {
      this.field_70180_af.func_187227_b(field_184634_g, Boolean.valueOf(false));
      this.field_70180_af.func_187227_b(field_184633_f, Integer.valueOf(0));
   }

   public void func_70674_bp() {
      if (!this.field_70170_p.field_72995_K) {
         Iterator<PotionEffect> iterator = this.field_70713_bf.values().iterator();

         while(iterator.hasNext()) {
            this.func_70688_c(iterator.next());
            iterator.remove();
         }

      }
   }

   public Collection<PotionEffect> func_70651_bq() {
      return this.field_70713_bf.values();
   }

   public Map<Potion, PotionEffect> func_193076_bZ() {
      return this.field_70713_bf;
   }

   public boolean func_70644_a(Potion p_70644_1_) {
      return this.field_70713_bf.containsKey(p_70644_1_);
   }

   @Nullable
   public PotionEffect func_70660_b(Potion p_70660_1_) {
      return this.field_70713_bf.get(p_70660_1_);
   }

   public void func_70690_d(PotionEffect p_70690_1_) {
      if (this.func_70687_e(p_70690_1_)) {
         PotionEffect potioneffect = this.field_70713_bf.get(p_70690_1_.func_188419_a());
         if (potioneffect == null) {
            this.field_70713_bf.put(p_70690_1_.func_188419_a(), p_70690_1_);
            this.func_70670_a(p_70690_1_);
         } else {
            potioneffect.func_76452_a(p_70690_1_);
            this.func_70695_b(potioneffect, true);
         }

      }
   }

   public boolean func_70687_e(PotionEffect p_70687_1_) {
      if (this.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
         Potion potion = p_70687_1_.func_188419_a();
         if (potion == MobEffects.field_76428_l || potion == MobEffects.field_76436_u) {
            return false;
         }
      }

      return true;
   }

   public boolean func_70662_br() {
      return this.func_70668_bt() == EnumCreatureAttribute.UNDEAD;
   }

   @Nullable
   public PotionEffect func_184596_c(@Nullable Potion p_184596_1_) {
      return this.field_70713_bf.remove(p_184596_1_);
   }

   public void func_184589_d(Potion p_184589_1_) {
      PotionEffect potioneffect = this.func_184596_c(p_184589_1_);
      if (potioneffect != null) {
         this.func_70688_c(potioneffect);
      }

   }

   protected void func_70670_a(PotionEffect p_70670_1_) {
      this.field_70752_e = true;
      if (!this.field_70170_p.field_72995_K) {
         p_70670_1_.func_188419_a().func_111185_a(this, this.func_110140_aT(), p_70670_1_.func_76458_c());
      }

   }

   protected void func_70695_b(PotionEffect p_70695_1_, boolean p_70695_2_) {
      this.field_70752_e = true;
      if (p_70695_2_ && !this.field_70170_p.field_72995_K) {
         Potion potion = p_70695_1_.func_188419_a();
         potion.func_111187_a(this, this.func_110140_aT(), p_70695_1_.func_76458_c());
         potion.func_111185_a(this, this.func_110140_aT(), p_70695_1_.func_76458_c());
      }

   }

   protected void func_70688_c(PotionEffect p_70688_1_) {
      this.field_70752_e = true;
      if (!this.field_70170_p.field_72995_K) {
         p_70688_1_.func_188419_a().func_111187_a(this, this.func_110140_aT(), p_70688_1_.func_76458_c());
      }

   }

   public void func_70691_i(float p_70691_1_) {
      float f = this.func_110143_aJ();
      if (f > 0.0F) {
         this.func_70606_j(f + p_70691_1_);
      }

   }

   public final float func_110143_aJ() {
      return ((Float)this.field_70180_af.func_187225_a(field_184632_c)).floatValue();
   }

   public void func_70606_j(float p_70606_1_) {
      this.field_70180_af.func_187227_b(field_184632_c, Float.valueOf(MathHelper.func_76131_a(p_70606_1_, 0.0F, this.func_110138_aP())));
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (this.field_70170_p.field_72995_K) {
         return false;
      } else {
         this.field_70708_bq = 0;
         if (this.func_110143_aJ() <= 0.0F) {
            return false;
         } else if (p_70097_1_.func_76347_k() && this.func_70644_a(MobEffects.field_76426_n)) {
            return false;
         } else {
            float f = p_70097_2_;
            if ((p_70097_1_ == DamageSource.field_82728_o || p_70097_1_ == DamageSource.field_82729_p) && !this.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
               this.func_184582_a(EntityEquipmentSlot.HEAD).func_77972_a((int)(p_70097_2_ * 4.0F + this.field_70146_Z.nextFloat() * p_70097_2_ * 2.0F), this);
               p_70097_2_ *= 0.75F;
            }

            boolean flag = false;
            if (p_70097_2_ > 0.0F && this.func_184583_d(p_70097_1_)) {
               this.func_184590_k(p_70097_2_);
               p_70097_2_ = 0.0F;
               if (!p_70097_1_.func_76352_a()) {
                  Entity entity = p_70097_1_.func_76364_f();
                  if (entity instanceof EntityLivingBase) {
                     this.func_190629_c((EntityLivingBase)entity);
                  }
               }

               flag = true;
            }

            this.field_70721_aZ = 1.5F;
            boolean flag1 = true;
            if ((float)this.field_70172_ad > (float)this.field_70771_an / 2.0F) {
               if (p_70097_2_ <= this.field_110153_bc) {
                  return false;
               }

               this.func_70665_d(p_70097_1_, p_70097_2_ - this.field_110153_bc);
               this.field_110153_bc = p_70097_2_;
               flag1 = false;
            } else {
               this.field_110153_bc = p_70097_2_;
               this.field_70172_ad = this.field_70771_an;
               this.func_70665_d(p_70097_1_, p_70097_2_);
               this.field_70738_aO = 10;
               this.field_70737_aN = this.field_70738_aO;
            }

            this.field_70739_aP = 0.0F;
            Entity entity1 = p_70097_1_.func_76346_g();
            if (entity1 != null) {
               if (entity1 instanceof EntityLivingBase) {
                  this.func_70604_c((EntityLivingBase)entity1);
               }

               if (entity1 instanceof EntityPlayer) {
                  this.field_70718_bc = 100;
                  this.field_70717_bb = (EntityPlayer)entity1;
               } else if (entity1 instanceof EntityWolf) {
                  EntityWolf entitywolf = (EntityWolf)entity1;
                  if (entitywolf.func_70909_n()) {
                     this.field_70718_bc = 100;
                     this.field_70717_bb = null;
                  }
               }
            }

            if (flag1) {
               if (flag) {
                  this.field_70170_p.func_72960_a(this, (byte)29);
               } else if (p_70097_1_ instanceof EntityDamageSource && ((EntityDamageSource)p_70097_1_).func_180139_w()) {
                  this.field_70170_p.func_72960_a(this, (byte)33);
               } else {
                  byte b0;
                  if (p_70097_1_ == DamageSource.field_76369_e) {
                     b0 = 36;
                  } else if (p_70097_1_.func_76347_k()) {
                     b0 = 37;
                  } else {
                     b0 = 2;
                  }

                  this.field_70170_p.func_72960_a(this, b0);
               }

               if (p_70097_1_ != DamageSource.field_76369_e && (!flag || p_70097_2_ > 0.0F)) {
                  this.func_70018_K();
               }

               if (entity1 != null) {
                  double d1 = entity1.field_70165_t - this.field_70165_t;

                  double d0;
                  for(d0 = entity1.field_70161_v - this.field_70161_v; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                     d1 = (Math.random() - Math.random()) * 0.01D;
                  }

                  this.field_70739_aP = (float)(MathHelper.func_181159_b(d0, d1) * 57.2957763671875D - (double)this.field_70177_z);
                  this.func_70653_a(entity1, 0.4F, d1, d0);
               } else {
                  this.field_70739_aP = (float)((int)(Math.random() * 2.0D) * 180);
               }
            }

            if (this.func_110143_aJ() <= 0.0F) {
               if (!this.func_190628_d(p_70097_1_)) {
                  SoundEvent soundevent = this.func_184615_bR();
                  if (flag1 && soundevent != null) {
                     this.func_184185_a(soundevent, this.func_70599_aP(), this.func_70647_i());
                  }

                  this.func_70645_a(p_70097_1_);
               }
            } else if (flag1) {
               this.func_184581_c(p_70097_1_);
            }

            boolean flag2 = !flag || p_70097_2_ > 0.0F;
            if (flag2) {
               this.field_189750_bF = p_70097_1_;
               this.field_189751_bG = this.field_70170_p.func_82737_E();
            }

            if (this instanceof EntityPlayerMP) {
               CriteriaTriggers.field_192128_h.func_192200_a((EntityPlayerMP)this, p_70097_1_, f, p_70097_2_, flag);
            }

            if (entity1 instanceof EntityPlayerMP) {
               CriteriaTriggers.field_192127_g.func_192220_a((EntityPlayerMP)entity1, this, p_70097_1_, f, p_70097_2_, flag);
            }

            return flag2;
         }
      }
   }

   protected void func_190629_c(EntityLivingBase p_190629_1_) {
      p_190629_1_.func_70653_a(this, 0.5F, this.field_70165_t - p_190629_1_.field_70165_t, this.field_70161_v - p_190629_1_.field_70161_v);
   }

   private boolean func_190628_d(DamageSource p_190628_1_) {
      if (p_190628_1_.func_76357_e()) {
         return false;
      } else {
         ItemStack itemstack = null;

         for(EnumHand enumhand : EnumHand.values()) {
            ItemStack itemstack1 = this.func_184586_b(enumhand);
            if (itemstack1.func_77973_b() == Items.field_190929_cY) {
               itemstack = itemstack1.func_77946_l();
               itemstack1.func_190918_g(1);
               break;
            }
         }

         if (itemstack != null) {
            if (this instanceof EntityPlayerMP) {
               EntityPlayerMP entityplayermp = (EntityPlayerMP)this;
               entityplayermp.func_71029_a(StatList.func_188057_b(Items.field_190929_cY));
               CriteriaTriggers.field_193130_A.func_193187_a(entityplayermp, itemstack);
            }

            this.func_70606_j(1.0F);
            this.func_70674_bp();
            this.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 900, 1));
            this.func_70690_d(new PotionEffect(MobEffects.field_76444_x, 100, 1));
            this.field_70170_p.func_72960_a(this, (byte)35);
         }

         return itemstack != null;
      }
   }

   @Nullable
   public DamageSource func_189748_bU() {
      if (this.field_70170_p.func_82737_E() - this.field_189751_bG > 40L) {
         this.field_189750_bF = null;
      }

      return this.field_189750_bF;
   }

   protected void func_184581_c(DamageSource p_184581_1_) {
      SoundEvent soundevent = this.func_184601_bQ(p_184581_1_);
      if (soundevent != null) {
         this.func_184185_a(soundevent, this.func_70599_aP(), this.func_70647_i());
      }

   }

   private boolean func_184583_d(DamageSource p_184583_1_) {
      if (!p_184583_1_.func_76363_c() && this.func_184585_cz()) {
         Vec3d vec3d = p_184583_1_.func_188404_v();
         if (vec3d != null) {
            Vec3d vec3d1 = this.func_70676_i(1.0F);
            Vec3d vec3d2 = vec3d.func_72444_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v)).func_72432_b();
            vec3d2 = new Vec3d(vec3d2.field_72450_a, 0.0D, vec3d2.field_72449_c);
            if (vec3d2.func_72430_b(vec3d1) < 0.0D) {
               return true;
            }
         }
      }

      return false;
   }

   public void func_70669_a(ItemStack p_70669_1_) {
      this.func_184185_a(SoundEvents.field_187635_cQ, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);

      for(int i = 0; i < 5; ++i) {
         Vec3d vec3d = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         vec3d = vec3d.func_178789_a(-this.field_70125_A * 0.017453292F);
         vec3d = vec3d.func_178785_b(-this.field_70177_z * 0.017453292F);
         double d0 = (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D;
         Vec3d vec3d1 = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
         vec3d1 = vec3d1.func_178789_a(-this.field_70125_A * 0.017453292F);
         vec3d1 = vec3d1.func_178785_b(-this.field_70177_z * 0.017453292F);
         vec3d1 = vec3d1.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
         this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b + 0.05D, vec3d.field_72449_c, Item.func_150891_b(p_70669_1_.func_77973_b()));
      }

   }

   public void func_70645_a(DamageSource p_70645_1_) {
      if (!this.field_70729_aU) {
         Entity entity = p_70645_1_.func_76346_g();
         EntityLivingBase entitylivingbase = this.func_94060_bK();
         if (this.field_70744_aE >= 0 && entitylivingbase != null) {
            entitylivingbase.func_191956_a(this, this.field_70744_aE, p_70645_1_);
         }

         if (entity != null) {
            entity.func_70074_a(this);
         }

         this.field_70729_aU = true;
         this.func_110142_aN().func_94549_h();
         if (!this.field_70170_p.field_72995_K) {
            int i = 0;
            if (entity instanceof EntityPlayer) {
               i = EnchantmentHelper.func_185283_h((EntityLivingBase)entity);
            }

            if (this.func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
               boolean flag = this.field_70718_bc > 0;
               this.func_184610_a(flag, i, p_70645_1_);
            }
         }

         this.field_70170_p.func_72960_a(this, (byte)3);
      }
   }

   protected void func_184610_a(boolean p_184610_1_, int p_184610_2_, DamageSource p_184610_3_) {
      this.func_70628_a(p_184610_1_, p_184610_2_);
      this.func_82160_b(p_184610_1_, p_184610_2_);
   }

   protected void func_82160_b(boolean p_82160_1_, int p_82160_2_) {
   }

   public void func_70653_a(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
      if (this.field_70146_Z.nextDouble() >= this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e()) {
         this.field_70160_al = true;
         float f = MathHelper.func_76133_a(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
         this.field_70159_w /= 2.0D;
         this.field_70179_y /= 2.0D;
         this.field_70159_w -= p_70653_3_ / (double)f * (double)p_70653_2_;
         this.field_70179_y -= p_70653_5_ / (double)f * (double)p_70653_2_;
         if (this.field_70122_E) {
            this.field_70181_x /= 2.0D;
            this.field_70181_x += (double)p_70653_2_;
            if (this.field_70181_x > 0.4000000059604645D) {
               this.field_70181_x = 0.4000000059604645D;
            }
         }

      }
   }

   @Nullable
   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187543_bD;
   }

   @Nullable
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187661_by;
   }

   protected SoundEvent func_184588_d(int p_184588_1_) {
      return p_184588_1_ > 4 ? SoundEvents.field_187655_bw : SoundEvents.field_187545_bE;
   }

   protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
   }

   public boolean func_70617_f_() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (this instanceof EntityPlayer && ((EntityPlayer)this).func_175149_v()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos(i, j, k);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         Block block = iblockstate.func_177230_c();
         if (block != Blocks.field_150468_ap && block != Blocks.field_150395_bd) {
            return block instanceof BlockTrapDoor && this.func_184604_a(blockpos, iblockstate);
         } else {
            return true;
         }
      }
   }

   private boolean func_184604_a(BlockPos p_184604_1_, IBlockState p_184604_2_) {
      if (((Boolean)p_184604_2_.func_177229_b(BlockTrapDoor.field_176283_b)).booleanValue()) {
         IBlockState iblockstate = this.field_70170_p.func_180495_p(p_184604_1_.func_177977_b());
         if (iblockstate.func_177230_c() == Blocks.field_150468_ap && iblockstate.func_177229_b(BlockLadder.field_176382_a) == p_184604_2_.func_177229_b(BlockTrapDoor.field_176284_a)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_70089_S() {
      return !this.field_70128_L && this.func_110143_aJ() > 0.0F;
   }

   public void func_180430_e(float p_180430_1_, float p_180430_2_) {
      super.func_180430_e(p_180430_1_, p_180430_2_);
      PotionEffect potioneffect = this.func_70660_b(MobEffects.field_76430_j);
      float f = potioneffect == null ? 0.0F : (float)(potioneffect.func_76458_c() + 1);
      int i = MathHelper.func_76123_f((p_180430_1_ - 3.0F - f) * p_180430_2_);
      if (i > 0) {
         this.func_184185_a(this.func_184588_d(i), 1.0F, 1.0F);
         this.func_70097_a(DamageSource.field_76379_h, (float)i);
         int j = MathHelper.func_76128_c(this.field_70165_t);
         int k = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
         int l = MathHelper.func_76128_c(this.field_70161_v);
         IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(j, k, l));
         if (iblockstate.func_185904_a() != Material.field_151579_a) {
            SoundType soundtype = iblockstate.func_177230_c().func_185467_w();
            this.func_184185_a(soundtype.func_185842_g(), soundtype.func_185843_a() * 0.5F, soundtype.func_185847_b() * 0.75F);
         }
      }

   }

   public void func_70057_ab() {
      this.field_70738_aO = 10;
      this.field_70737_aN = this.field_70738_aO;
      this.field_70739_aP = 0.0F;
   }

   public int func_70658_aO() {
      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_188791_g);
      return MathHelper.func_76128_c(iattributeinstance.func_111126_e());
   }

   protected void func_70675_k(float p_70675_1_) {
   }

   protected void func_184590_k(float p_184590_1_) {
   }

   protected float func_70655_b(DamageSource p_70655_1_, float p_70655_2_) {
      if (!p_70655_1_.func_76363_c()) {
         this.func_70675_k(p_70655_2_);
         p_70655_2_ = CombatRules.func_189427_a(p_70655_2_, (float)this.func_70658_aO(), (float)this.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
      }

      return p_70655_2_;
   }

   protected float func_70672_c(DamageSource p_70672_1_, float p_70672_2_) {
      if (p_70672_1_.func_151517_h()) {
         return p_70672_2_;
      } else {
         if (this.func_70644_a(MobEffects.field_76429_m) && p_70672_1_ != DamageSource.field_76380_i) {
            int i = (this.func_70660_b(MobEffects.field_76429_m).func_76458_c() + 1) * 5;
            int j = 25 - i;
            float f = p_70672_2_ * (float)j;
            p_70672_2_ = f / 25.0F;
         }

         if (p_70672_2_ <= 0.0F) {
            return 0.0F;
         } else {
            int k = EnchantmentHelper.func_77508_a(this.func_184193_aE(), p_70672_1_);
            if (k > 0) {
               p_70672_2_ = CombatRules.func_188401_b(p_70672_2_, (float)k);
            }

            return p_70672_2_;
         }
      }
   }

   protected void func_70665_d(DamageSource p_70665_1_, float p_70665_2_) {
      if (!this.func_180431_b(p_70665_1_)) {
         p_70665_2_ = this.func_70655_b(p_70665_1_, p_70665_2_);
         p_70665_2_ = this.func_70672_c(p_70665_1_, p_70665_2_);
         float f = p_70665_2_;
         p_70665_2_ = Math.max(p_70665_2_ - this.func_110139_bj(), 0.0F);
         this.func_110149_m(this.func_110139_bj() - (f - p_70665_2_));
         if (p_70665_2_ != 0.0F) {
            float f1 = this.func_110143_aJ();
            this.func_70606_j(f1 - p_70665_2_);
            this.func_110142_aN().func_94547_a(p_70665_1_, f1, p_70665_2_);
            this.func_110149_m(this.func_110139_bj() - p_70665_2_);
         }
      }
   }

   public CombatTracker func_110142_aN() {
      return this.field_94063_bt;
   }

   @Nullable
   public EntityLivingBase func_94060_bK() {
      if (this.field_94063_bt.func_94550_c() != null) {
         return this.field_94063_bt.func_94550_c();
      } else if (this.field_70717_bb != null) {
         return this.field_70717_bb;
      } else {
         return this.field_70755_b != null ? this.field_70755_b : null;
      }
   }

   public final float func_110138_aP() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
   }

   public final int func_85035_bI() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184635_h)).intValue();
   }

   public final void func_85034_r(int p_85034_1_) {
      this.field_70180_af.func_187227_b(field_184635_h, Integer.valueOf(p_85034_1_));
   }

   private int func_82166_i() {
      if (this.func_70644_a(MobEffects.field_76422_e)) {
         return 6 - (1 + this.func_70660_b(MobEffects.field_76422_e).func_76458_c());
      } else {
         return this.func_70644_a(MobEffects.field_76419_f) ? 6 + (1 + this.func_70660_b(MobEffects.field_76419_f).func_76458_c()) * 2 : 6;
      }
   }

   public void func_184609_a(EnumHand p_184609_1_) {
      if (!this.field_82175_bq || this.field_110158_av >= this.func_82166_i() / 2 || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.field_82175_bq = true;
         this.field_184622_au = p_184609_1_;
         if (this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketAnimation(this, p_184609_1_ == EnumHand.MAIN_HAND ? 0 : 3));
         }
      }

   }

   public void func_70103_a(byte p_70103_1_) {
      boolean flag = p_70103_1_ == 33;
      boolean flag1 = p_70103_1_ == 36;
      boolean flag2 = p_70103_1_ == 37;
      if (p_70103_1_ != 2 && !flag && !flag1 && !flag2) {
         if (p_70103_1_ == 3) {
            SoundEvent soundevent1 = this.func_184615_bR();
            if (soundevent1 != null) {
               this.func_184185_a(soundevent1, this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
            }

            this.func_70606_j(0.0F);
            this.func_70645_a(DamageSource.field_76377_j);
         } else if (p_70103_1_ == 30) {
            this.func_184185_a(SoundEvents.field_187769_eM, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
         } else if (p_70103_1_ == 29) {
            this.func_184185_a(SoundEvents.field_187767_eL, 1.0F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
         } else {
            super.func_70103_a(p_70103_1_);
         }
      } else {
         this.field_70721_aZ = 1.5F;
         this.field_70172_ad = this.field_70771_an;
         this.field_70738_aO = 10;
         this.field_70737_aN = this.field_70738_aO;
         this.field_70739_aP = 0.0F;
         if (flag) {
            this.func_184185_a(SoundEvents.field_187903_gc, this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         }

         DamageSource damagesource;
         if (flag2) {
            damagesource = DamageSource.field_76370_b;
         } else if (flag1) {
            damagesource = DamageSource.field_76369_e;
         } else {
            damagesource = DamageSource.field_76377_j;
         }

         SoundEvent soundevent = this.func_184601_bQ(damagesource);
         if (soundevent != null) {
            this.func_184185_a(soundevent, this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         }

         this.func_70097_a(DamageSource.field_76377_j, 0.0F);
      }

   }

   protected void func_70076_C() {
      this.func_70097_a(DamageSource.field_76380_i, 4.0F);
   }

   protected void func_82168_bl() {
      int i = this.func_82166_i();
      if (this.field_82175_bq) {
         ++this.field_110158_av;
         if (this.field_110158_av >= i) {
            this.field_110158_av = 0;
            this.field_82175_bq = false;
         }
      } else {
         this.field_110158_av = 0;
      }

      this.field_70733_aJ = (float)this.field_110158_av / (float)i;
   }

   public IAttributeInstance func_110148_a(IAttribute p_110148_1_) {
      return this.func_110140_aT().func_111151_a(p_110148_1_);
   }

   public AbstractAttributeMap func_110140_aT() {
      if (this.field_110155_d == null) {
         this.field_110155_d = new AttributeMap();
      }

      return this.field_110155_d;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public ItemStack func_184614_ca() {
      return this.func_184582_a(EntityEquipmentSlot.MAINHAND);
   }

   public ItemStack func_184592_cb() {
      return this.func_184582_a(EntityEquipmentSlot.OFFHAND);
   }

   public ItemStack func_184586_b(EnumHand p_184586_1_) {
      if (p_184586_1_ == EnumHand.MAIN_HAND) {
         return this.func_184582_a(EntityEquipmentSlot.MAINHAND);
      } else if (p_184586_1_ == EnumHand.OFF_HAND) {
         return this.func_184582_a(EntityEquipmentSlot.OFFHAND);
      } else {
         throw new IllegalArgumentException("Invalid hand " + p_184586_1_);
      }
   }

   public void func_184611_a(EnumHand p_184611_1_, ItemStack p_184611_2_) {
      if (p_184611_1_ == EnumHand.MAIN_HAND) {
         this.func_184201_a(EntityEquipmentSlot.MAINHAND, p_184611_2_);
      } else {
         if (p_184611_1_ != EnumHand.OFF_HAND) {
            throw new IllegalArgumentException("Invalid hand " + p_184611_1_);
         }

         this.func_184201_a(EntityEquipmentSlot.OFFHAND, p_184611_2_);
      }

   }

   public boolean func_190630_a(EntityEquipmentSlot p_190630_1_) {
      return !this.func_184582_a(p_190630_1_).func_190926_b();
   }

   public abstract Iterable<ItemStack> func_184193_aE();

   public abstract ItemStack func_184582_a(EntityEquipmentSlot var1);

   public abstract void func_184201_a(EntityEquipmentSlot var1, ItemStack var2);

   public void func_70031_b(boolean p_70031_1_) {
      super.func_70031_b(p_70031_1_);
      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (iattributeinstance.func_111127_a(field_110156_b) != null) {
         iattributeinstance.func_111124_b(field_110157_c);
      }

      if (p_70031_1_) {
         iattributeinstance.func_111121_a(field_110157_c);
      }

   }

   protected float func_70599_aP() {
      return 1.0F;
   }

   protected float func_70647_i() {
      return this.func_70631_g_() ? (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.5F : (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F;
   }

   public void func_110145_l(Entity p_110145_1_) {
      if (!(p_110145_1_ instanceof EntityBoat) && !(p_110145_1_ instanceof AbstractHorse)) {
         double d1 = p_110145_1_.field_70165_t;
         double d13 = p_110145_1_.func_174813_aQ().field_72338_b + (double)p_110145_1_.field_70131_O;
         double d14 = p_110145_1_.field_70161_v;
         EnumFacing enumfacing1 = p_110145_1_.func_184172_bi();
         if (enumfacing1 != null) {
            EnumFacing enumfacing = enumfacing1.func_176746_e();
            int[][] aint1 = new int[][]{{0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 0}, {1, 0}, {0, 1}};
            double d5 = Math.floor(this.field_70165_t) + 0.5D;
            double d6 = Math.floor(this.field_70161_v) + 0.5D;
            double d7 = this.func_174813_aQ().field_72336_d - this.func_174813_aQ().field_72340_a;
            double d8 = this.func_174813_aQ().field_72334_f - this.func_174813_aQ().field_72339_c;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(d5 - d7 / 2.0D, p_110145_1_.func_174813_aQ().field_72338_b, d6 - d8 / 2.0D, d5 + d7 / 2.0D, Math.floor(p_110145_1_.func_174813_aQ().field_72338_b) + (double)this.field_70131_O, d6 + d8 / 2.0D);

            for(int[] aint : aint1) {
               double d9 = (double)(enumfacing1.func_82601_c() * aint[0] + enumfacing.func_82601_c() * aint[1]);
               double d10 = (double)(enumfacing1.func_82599_e() * aint[0] + enumfacing.func_82599_e() * aint[1]);
               double d11 = d5 + d9;
               double d12 = d6 + d10;
               AxisAlignedBB axisalignedbb1 = axisalignedbb.func_72317_d(d9, 0.0D, d10);
               if (!this.field_70170_p.func_184143_b(axisalignedbb1)) {
                  if (this.field_70170_p.func_180495_p(new BlockPos(d11, this.field_70163_u, d12)).func_185896_q()) {
                     this.func_70634_a(d11, this.field_70163_u + 1.0D, d12);
                     return;
                  }

                  BlockPos blockpos = new BlockPos(d11, this.field_70163_u - 1.0D, d12);
                  if (this.field_70170_p.func_180495_p(blockpos).func_185896_q() || this.field_70170_p.func_180495_p(blockpos).func_185904_a() == Material.field_151586_h) {
                     d1 = d11;
                     d13 = this.field_70163_u + 1.0D;
                     d14 = d12;
                  }
               } else if (!this.field_70170_p.func_184143_b(axisalignedbb1.func_72317_d(0.0D, 1.0D, 0.0D)) && this.field_70170_p.func_180495_p(new BlockPos(d11, this.field_70163_u + 1.0D, d12)).func_185896_q()) {
                  d1 = d11;
                  d13 = this.field_70163_u + 2.0D;
                  d14 = d12;
               }
            }
         }

         this.func_70634_a(d1, d13, d14);
      } else {
         double d0 = (double)(this.field_70130_N / 2.0F + p_110145_1_.field_70130_N / 2.0F) + 0.4D;
         float f;
         if (p_110145_1_ instanceof EntityBoat) {
            f = 0.0F;
         } else {
            f = 1.5707964F * (float)(this.func_184591_cq() == EnumHandSide.RIGHT ? -1 : 1);
         }

         float f1 = -MathHelper.func_76126_a(-this.field_70177_z * 0.017453292F - 3.1415927F + f);
         float f2 = -MathHelper.func_76134_b(-this.field_70177_z * 0.017453292F - 3.1415927F + f);
         double d2 = Math.abs(f1) > Math.abs(f2) ? d0 / (double)Math.abs(f1) : d0 / (double)Math.abs(f2);
         double d3 = this.field_70165_t + (double)f1 * d2;
         double d4 = this.field_70161_v + (double)f2 * d2;
         this.func_70107_b(d3, p_110145_1_.field_70163_u + (double)p_110145_1_.field_70131_O + 0.001D, d4);
         if (this.field_70170_p.func_184143_b(this.func_174813_aQ())) {
            this.func_70107_b(d3, p_110145_1_.field_70163_u + (double)p_110145_1_.field_70131_O + 1.001D, d4);
            if (this.field_70170_p.func_184143_b(this.func_174813_aQ())) {
               this.func_70107_b(p_110145_1_.field_70165_t, p_110145_1_.field_70163_u + (double)this.field_70131_O + 0.001D, p_110145_1_.field_70161_v);
            }
         }
      }
   }

   public boolean func_94059_bO() {
      return this.func_174833_aM();
   }

   protected float func_175134_bD() {
      return 0.42F;
   }

   protected void func_70664_aZ() {
      this.field_70181_x = (double)this.func_175134_bD();
      if (this.func_70644_a(MobEffects.field_76430_j)) {
         this.field_70181_x += (double)((float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
      }

      if (this.func_70051_ag()) {
         float f = this.field_70177_z * 0.017453292F;
         this.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F);
         this.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F);
      }

      this.field_70160_al = true;
   }

   protected void func_70629_bd() {
      this.field_70181_x += 0.03999999910593033D;
   }

   protected void func_180466_bG() {
      this.field_70181_x += 0.03999999910593033D;
   }

   protected float func_189749_co() {
      return 0.8F;
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      if (this.func_70613_aW() || this.func_184186_bw()) {
         if (!this.func_70090_H() || this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75100_b) {
            if (!this.func_180799_ab() || this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75100_b) {
               if (this.func_184613_cA()) {
                  if (this.field_70181_x > -0.5D) {
                     this.field_70143_R = 1.0F;
                  }

                  Vec3d vec3d = this.func_70040_Z();
                  float f = this.field_70125_A * 0.017453292F;
                  double d6 = Math.sqrt(vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c);
                  double d8 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                  double d1 = vec3d.func_72433_c();
                  float f4 = MathHelper.func_76134_b(f);
                  f4 = (float)((double)f4 * (double)f4 * Math.min(1.0D, d1 / 0.4D));
                  this.field_70181_x += -0.08D + (double)f4 * 0.06D;
                  if (this.field_70181_x < 0.0D && d6 > 0.0D) {
                     double d2 = this.field_70181_x * -0.1D * (double)f4;
                     this.field_70181_x += d2;
                     this.field_70159_w += vec3d.field_72450_a * d2 / d6;
                     this.field_70179_y += vec3d.field_72449_c * d2 / d6;
                  }

                  if (f < 0.0F) {
                     double d10 = d8 * (double)(-MathHelper.func_76126_a(f)) * 0.04D;
                     this.field_70181_x += d10 * 3.2D;
                     this.field_70159_w -= vec3d.field_72450_a * d10 / d6;
                     this.field_70179_y -= vec3d.field_72449_c * d10 / d6;
                  }

                  if (d6 > 0.0D) {
                     this.field_70159_w += (vec3d.field_72450_a / d6 * d8 - this.field_70159_w) * 0.1D;
                     this.field_70179_y += (vec3d.field_72449_c / d6 * d8 - this.field_70179_y) * 0.1D;
                  }

                  this.field_70159_w *= 0.9900000095367432D;
                  this.field_70181_x *= 0.9800000190734863D;
                  this.field_70179_y *= 0.9900000095367432D;
                  this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  if (this.field_70123_F && !this.field_70170_p.field_72995_K) {
                     double d11 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                     double d3 = d8 - d11;
                     float f5 = (float)(d3 * 10.0D - 3.0D);
                     if (f5 > 0.0F) {
                        this.func_184185_a(this.func_184588_d((int)f5), 1.0F, 1.0F);
                        this.func_70097_a(DamageSource.field_188406_j, f5);
                     }
                  }

                  if (this.field_70122_E && !this.field_70170_p.field_72995_K) {
                     this.func_70052_a(7, false);
                  }
               } else {
                  float f6 = 0.91F;
                  BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(this.field_70165_t, this.func_174813_aQ().field_72338_b - 1.0D, this.field_70161_v);
                  if (this.field_70122_E) {
                     f6 = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos).func_177230_c().field_149765_K * 0.91F;
                  }

                  float f7 = 0.16277136F / (f6 * f6 * f6);
                  float f8;
                  if (this.field_70122_E) {
                     f8 = this.func_70689_ay() * f7;
                  } else {
                     f8 = this.field_70747_aH;
                  }

                  this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, f8);
                  f6 = 0.91F;
                  if (this.field_70122_E) {
                     f6 = this.field_70170_p.func_180495_p(blockpos$pooledmutableblockpos.func_189532_c(this.field_70165_t, this.func_174813_aQ().field_72338_b - 1.0D, this.field_70161_v)).func_177230_c().field_149765_K * 0.91F;
                  }

                  if (this.func_70617_f_()) {
                     float f9 = 0.15F;
                     this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -0.15000000596046448D, 0.15000000596046448D);
                     this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -0.15000000596046448D, 0.15000000596046448D);
                     this.field_70143_R = 0.0F;
                     if (this.field_70181_x < -0.15D) {
                        this.field_70181_x = -0.15D;
                     }

                     boolean flag = this.func_70093_af() && this instanceof EntityPlayer;
                     if (flag && this.field_70181_x < 0.0D) {
                        this.field_70181_x = 0.0D;
                     }
                  }

                  this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  if (this.field_70123_F && this.func_70617_f_()) {
                     this.field_70181_x = 0.2D;
                  }

                  if (this.func_70644_a(MobEffects.field_188424_y)) {
                     this.field_70181_x += (0.05D * (double)(this.func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x) * 0.2D;
                  } else {
                     blockpos$pooledmutableblockpos.func_189532_c(this.field_70165_t, 0.0D, this.field_70161_v);
                     if (!this.field_70170_p.field_72995_K || this.field_70170_p.func_175667_e(blockpos$pooledmutableblockpos) && this.field_70170_p.func_175726_f(blockpos$pooledmutableblockpos).func_177410_o()) {
                        if (!this.func_189652_ae()) {
                           this.field_70181_x -= 0.08D;
                        }
                     } else if (this.field_70163_u > 0.0D) {
                        this.field_70181_x = -0.1D;
                     } else {
                        this.field_70181_x = 0.0D;
                     }
                  }

                  this.field_70181_x *= 0.9800000190734863D;
                  this.field_70159_w *= (double)f6;
                  this.field_70179_y *= (double)f6;
                  blockpos$pooledmutableblockpos.func_185344_t();
               }
            } else {
               double d4 = this.field_70163_u;
               this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
               this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
               this.field_70159_w *= 0.5D;
               this.field_70181_x *= 0.5D;
               this.field_70179_y *= 0.5D;
               if (!this.func_189652_ae()) {
                  this.field_70181_x -= 0.02D;
               }

               if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d4, this.field_70179_y)) {
                  this.field_70181_x = 0.30000001192092896D;
               }
            }
         } else {
            double d0 = this.field_70163_u;
            float f1 = this.func_189749_co();
            float f2 = 0.02F;
            float f3 = (float)EnchantmentHelper.func_185294_d(this);
            if (f3 > 3.0F) {
               f3 = 3.0F;
            }

            if (!this.field_70122_E) {
               f3 *= 0.5F;
            }

            if (f3 > 0.0F) {
               f1 += (0.54600006F - f1) * f3 / 3.0F;
               f2 += (this.func_70689_ay() - f2) * f3 / 3.0F;
            }

            this.func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, f2);
            this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= (double)f1;
            this.field_70181_x *= 0.800000011920929D;
            this.field_70179_y *= (double)f1;
            if (!this.func_189652_ae()) {
               this.field_70181_x -= 0.02D;
            }

            if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d0, this.field_70179_y)) {
               this.field_70181_x = 0.30000001192092896D;
            }
         }
      }

      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      double d9 = this instanceof net.minecraft.entity.passive.EntityFlying ? this.field_70163_u - this.field_70167_r : 0.0D;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d9 * d9 + d7 * d7) * 4.0F;
      if (f10 > 1.0F) {
         f10 = 1.0F;
      }

      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
   }

   public float func_70689_ay() {
      return this.field_70746_aG;
   }

   public void func_70659_e(float p_70659_1_) {
      this.field_70746_aG = p_70659_1_;
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      this.func_130011_c(p_70652_1_);
      return false;
   }

   public boolean func_70608_bn() {
      return false;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.func_184608_ct();
      if (!this.field_70170_p.field_72995_K) {
         int i = this.func_85035_bI();
         if (i > 0) {
            if (this.field_70720_be <= 0) {
               this.field_70720_be = 20 * (30 - i);
            }

            --this.field_70720_be;
            if (this.field_70720_be <= 0) {
               this.func_85034_r(i - 1);
            }
         }

         for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
            ItemStack itemstack;
            switch(entityequipmentslot.func_188453_a()) {
            case HAND:
               itemstack = this.field_184630_bs.get(entityequipmentslot.func_188454_b());
               break;
            case ARMOR:
               itemstack = this.field_184631_bt.get(entityequipmentslot.func_188454_b());
               break;
            default:
               continue;
            }

            ItemStack itemstack1 = this.func_184582_a(entityequipmentslot);
            if (!ItemStack.func_77989_b(itemstack1, itemstack)) {
               ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityEquipment(this.func_145782_y(), entityequipmentslot, itemstack1));
               if (!itemstack.func_190926_b()) {
                  this.func_110140_aT().func_111148_a(itemstack.func_111283_C(entityequipmentslot));
               }

               if (!itemstack1.func_190926_b()) {
                  this.func_110140_aT().func_111147_b(itemstack1.func_111283_C(entityequipmentslot));
               }

               switch(entityequipmentslot.func_188453_a()) {
               case HAND:
                  this.field_184630_bs.set(entityequipmentslot.func_188454_b(), itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.func_77946_l());
                  break;
               case ARMOR:
                  this.field_184631_bt.set(entityequipmentslot.func_188454_b(), itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.func_77946_l());
               }
            }
         }

         if (this.field_70173_aa % 20 == 0) {
            this.func_110142_aN().func_94549_h();
         }

         if (!this.field_184238_ar) {
            boolean flag = this.func_70644_a(MobEffects.field_188423_x);
            if (this.func_70083_f(6) != flag) {
               this.func_70052_a(6, flag);
            }
         }
      }

      this.func_70636_d();
      double d0 = this.field_70165_t - this.field_70169_q;
      double d1 = this.field_70161_v - this.field_70166_s;
      float f3 = (float)(d0 * d0 + d1 * d1);
      float f4 = this.field_70761_aq;
      float f5 = 0.0F;
      this.field_70768_au = this.field_110154_aX;
      float f = 0.0F;
      if (f3 > 0.0025000002F) {
         f = 1.0F;
         f5 = (float)Math.sqrt((double)f3) * 3.0F;
         float f1 = (float)MathHelper.func_181159_b(d1, d0) * 57.295776F - 90.0F;
         float f2 = MathHelper.func_76135_e(MathHelper.func_76142_g(this.field_70177_z) - f1);
         if (95.0F < f2 && f2 < 265.0F) {
            f4 = f1 - 180.0F;
         } else {
            f4 = f1;
         }
      }

      if (this.field_70733_aJ > 0.0F) {
         f4 = this.field_70177_z;
      }

      if (!this.field_70122_E) {
         f = 0.0F;
      }

      this.field_110154_aX += (f - this.field_110154_aX) * 0.3F;
      this.field_70170_p.field_72984_F.func_76320_a("headTurn");
      f5 = this.func_110146_f(f4, f5);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("rangeChecks");

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar < -180.0F) {
         this.field_70760_ar -= 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar >= 180.0F) {
         this.field_70760_ar += 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C < -180.0F) {
         this.field_70127_C -= 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at < -180.0F) {
         this.field_70758_at -= 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at >= 180.0F) {
         this.field_70758_at += 360.0F;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70764_aw += f5;
      if (this.func_184613_cA()) {
         ++this.field_184629_bo;
      } else {
         this.field_184629_bo = 0;
      }

   }

   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
      float f = MathHelper.func_76142_g(p_110146_1_ - this.field_70761_aq);
      this.field_70761_aq += f * 0.3F;
      float f1 = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
      boolean flag = f1 < -90.0F || f1 >= 90.0F;
      if (f1 < -75.0F) {
         f1 = -75.0F;
      }

      if (f1 >= 75.0F) {
         f1 = 75.0F;
      }

      this.field_70761_aq = this.field_70177_z - f1;
      if (f1 * f1 > 2500.0F) {
         this.field_70761_aq += f1 * 0.2F;
      }

      if (flag) {
         p_110146_2_ *= -1.0F;
      }

      return p_110146_2_;
   }

   public void func_70636_d() {
      if (this.field_70773_bE > 0) {
         --this.field_70773_bE;
      }

      if (this.field_70716_bi > 0 && !this.func_184186_bw()) {
         double d0 = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / (double)this.field_70716_bi;
         double d1 = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / (double)this.field_70716_bi;
         double d2 = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / (double)this.field_70716_bi;
         double d3 = MathHelper.func_76138_g(this.field_184626_bk - (double)this.field_70177_z);
         this.field_70177_z = (float)((double)this.field_70177_z + d3 / (double)this.field_70716_bi);
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70709_bj - (double)this.field_70125_A) / (double)this.field_70716_bi);
         --this.field_70716_bi;
         this.func_70107_b(d0, d1, d2);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      } else if (!this.func_70613_aW()) {
         this.field_70159_w *= 0.98D;
         this.field_70181_x *= 0.98D;
         this.field_70179_y *= 0.98D;
      }

      if (Math.abs(this.field_70159_w) < 0.003D) {
         this.field_70159_w = 0.0D;
      }

      if (Math.abs(this.field_70181_x) < 0.003D) {
         this.field_70181_x = 0.0D;
      }

      if (Math.abs(this.field_70179_y) < 0.003D) {
         this.field_70179_y = 0.0D;
      }

      this.field_70170_p.field_72984_F.func_76320_a("ai");
      if (this.func_70610_aX()) {
         this.field_70703_bu = false;
         this.field_70702_br = 0.0F;
         this.field_191988_bg = 0.0F;
         this.field_70704_bt = 0.0F;
      } else if (this.func_70613_aW()) {
         this.field_70170_p.field_72984_F.func_76320_a("newAi");
         this.func_70626_be();
         this.field_70170_p.field_72984_F.func_76319_b();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("jump");
      if (this.field_70703_bu) {
         if (this.func_70090_H()) {
            this.func_70629_bd();
         } else if (this.func_180799_ab()) {
            this.func_180466_bG();
         } else if (this.field_70122_E && this.field_70773_bE == 0) {
            this.func_70664_aZ();
            this.field_70773_bE = 10;
         }
      } else {
         this.field_70773_bE = 0;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("travel");
      this.field_70702_br *= 0.98F;
      this.field_191988_bg *= 0.98F;
      this.field_70704_bt *= 0.9F;
      this.func_184616_r();
      this.func_191986_a(this.field_70702_br, this.field_70701_bs, this.field_191988_bg);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("push");
      this.func_85033_bc();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   private void func_184616_r() {
      boolean flag = this.func_70083_f(7);
      if (flag && !this.field_70122_E && !this.func_184218_aH()) {
         ItemStack itemstack = this.func_184582_a(EntityEquipmentSlot.CHEST);
         if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack)) {
            flag = true;
            if (!this.field_70170_p.field_72995_K && (this.field_184629_bo + 1) % 20 == 0) {
               itemstack.func_77972_a(1, this);
            }
         } else {
            flag = false;
         }
      } else {
         flag = false;
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(7, flag);
      }

   }

   protected void func_70626_be() {
   }

   protected void func_85033_bc() {
      List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ(), EntitySelectors.func_188442_a(this));
      if (!list.isEmpty()) {
         int i = this.field_70170_p.func_82736_K().func_180263_c("maxEntityCramming");
         if (i > 0 && list.size() > i - 1 && this.field_70146_Z.nextInt(4) == 0) {
            int j = 0;

            for(int k = 0; k < list.size(); ++k) {
               if (!((Entity)list.get(k)).func_184218_aH()) {
                  ++j;
               }
            }

            if (j > i - 1) {
               this.func_70097_a(DamageSource.field_191291_g, 6.0F);
            }
         }

         for(int l = 0; l < list.size(); ++l) {
            Entity entity = list.get(l);
            this.func_82167_n(entity);
         }
      }

   }

   protected void func_82167_n(Entity p_82167_1_) {
      p_82167_1_.func_70108_f(this);
   }

   public void func_184210_p() {
      Entity entity = this.func_184187_bx();
      super.func_184210_p();
      if (entity != null && entity != this.func_184187_bx() && !this.field_70170_p.field_72995_K) {
         this.func_110145_l(entity);
      }

   }

   public void func_70098_U() {
      super.func_70098_U();
      this.field_70768_au = this.field_110154_aX;
      this.field_110154_aX = 0.0F;
      this.field_70143_R = 0.0F;
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.field_184623_bh = p_180426_1_;
      this.field_184624_bi = p_180426_3_;
      this.field_184625_bj = p_180426_5_;
      this.field_184626_bk = (double)p_180426_7_;
      this.field_70709_bj = (double)p_180426_8_;
      this.field_70716_bi = p_180426_9_;
   }

   public void func_70637_d(boolean p_70637_1_) {
      this.field_70703_bu = p_70637_1_;
   }

   public void func_71001_a(Entity p_71001_1_, int p_71001_2_) {
      if (!p_71001_1_.field_70128_L && !this.field_70170_p.field_72995_K) {
         EntityTracker entitytracker = ((WorldServer)this.field_70170_p).func_73039_n();
         if (p_71001_1_ instanceof EntityItem || p_71001_1_ instanceof EntityArrow || p_71001_1_ instanceof EntityXPOrb) {
            entitytracker.func_151247_a(p_71001_1_, new SPacketCollectItem(p_71001_1_.func_145782_y(), this.func_145782_y(), p_71001_2_));
         }
      }

   }

   public boolean func_70685_l(Entity p_70685_1_) {
      return this.field_70170_p.func_147447_a(new Vec3d(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v), new Vec3d(p_70685_1_.field_70165_t, p_70685_1_.field_70163_u + (double)p_70685_1_.func_70047_e(), p_70685_1_.field_70161_v), false, true, false) == null;
   }

   public Vec3d func_70676_i(float p_70676_1_) {
      if (p_70676_1_ == 1.0F) {
         return this.func_174806_f(this.field_70125_A, this.field_70759_as);
      } else {
         float f = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * p_70676_1_;
         float f1 = this.field_70758_at + (this.field_70759_as - this.field_70758_at) * p_70676_1_;
         return this.func_174806_f(f, f1);
      }
   }

   public float func_70678_g(float p_70678_1_) {
      float f = this.field_70733_aJ - this.field_70732_aI;
      if (f < 0.0F) {
         ++f;
      }

      return this.field_70732_aI + f * p_70678_1_;
   }

   public boolean func_70613_aW() {
      return !this.field_70170_p.field_72995_K;
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   public boolean func_70104_M() {
      return this.func_70089_S() && !this.func_70617_f_();
   }

   protected void func_70018_K() {
      this.field_70133_I = this.field_70146_Z.nextDouble() >= this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e();
   }

   public float func_70079_am() {
      return this.field_70759_as;
   }

   public void func_70034_d(float p_70034_1_) {
      this.field_70759_as = p_70034_1_;
   }

   public void func_181013_g(float p_181013_1_) {
      this.field_70761_aq = p_181013_1_;
   }

   public float func_110139_bj() {
      return this.field_110151_bq;
   }

   public void func_110149_m(float p_110149_1_) {
      if (p_110149_1_ < 0.0F) {
         p_110149_1_ = 0.0F;
      }

      this.field_110151_bq = p_110149_1_;
   }

   public void func_152111_bt() {
   }

   public void func_152112_bu() {
   }

   protected void func_175136_bO() {
      this.field_70752_e = true;
   }

   public abstract EnumHandSide func_184591_cq();

   public boolean func_184587_cr() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184621_as)).byteValue() & 1) > 0;
   }

   public EnumHand func_184600_cs() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184621_as)).byteValue() & 2) > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
   }

   protected void func_184608_ct() {
      if (this.func_184587_cr()) {
         ItemStack itemstack = this.func_184586_b(this.func_184600_cs());
         if (itemstack == this.field_184627_bm) {
            if (this.func_184605_cv() <= 25 && this.func_184605_cv() % 4 == 0) {
               this.func_184584_a(this.field_184627_bm, 5);
            }

            if (--this.field_184628_bn == 0 && !this.field_70170_p.field_72995_K) {
               this.func_71036_o();
            }
         } else {
            this.func_184602_cy();
         }
      }

   }

   public void func_184598_c(EnumHand p_184598_1_) {
      ItemStack itemstack = this.func_184586_b(p_184598_1_);
      if (!itemstack.func_190926_b() && !this.func_184587_cr()) {
         this.field_184627_bm = itemstack;
         this.field_184628_bn = itemstack.func_77988_m();
         if (!this.field_70170_p.field_72995_K) {
            int i = 1;
            if (p_184598_1_ == EnumHand.OFF_HAND) {
               i |= 2;
            }

            this.field_70180_af.func_187227_b(field_184621_as, Byte.valueOf((byte)i));
         }

      }
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      super.func_184206_a(p_184206_1_);
      if (field_184621_as.equals(p_184206_1_) && this.field_70170_p.field_72995_K) {
         if (this.func_184587_cr() && this.field_184627_bm.func_190926_b()) {
            this.field_184627_bm = this.func_184586_b(this.func_184600_cs());
            if (!this.field_184627_bm.func_190926_b()) {
               this.field_184628_bn = this.field_184627_bm.func_77988_m();
            }
         } else if (!this.func_184587_cr() && !this.field_184627_bm.func_190926_b()) {
            this.field_184627_bm = ItemStack.field_190927_a;
            this.field_184628_bn = 0;
         }
      }

   }

   protected void func_184584_a(ItemStack p_184584_1_, int p_184584_2_) {
      if (!p_184584_1_.func_190926_b() && this.func_184587_cr()) {
         if (p_184584_1_.func_77975_n() == EnumAction.DRINK) {
            this.func_184185_a(SoundEvents.field_187664_bz, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
         }

         if (p_184584_1_.func_77975_n() == EnumAction.EAT) {
            for(int i = 0; i < p_184584_2_; ++i) {
               Vec3d vec3d = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
               vec3d = vec3d.func_178789_a(-this.field_70125_A * 0.017453292F);
               vec3d = vec3d.func_178785_b(-this.field_70177_z * 0.017453292F);
               double d0 = (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D;
               Vec3d vec3d1 = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
               vec3d1 = vec3d1.func_178789_a(-this.field_70125_A * 0.017453292F);
               vec3d1 = vec3d1.func_178785_b(-this.field_70177_z * 0.017453292F);
               vec3d1 = vec3d1.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
               if (p_184584_1_.func_77981_g()) {
                  this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b + 0.05D, vec3d.field_72449_c, Item.func_150891_b(p_184584_1_.func_77973_b()), p_184584_1_.func_77960_j());
               } else {
                  this.field_70170_p.func_175688_a(EnumParticleTypes.ITEM_CRACK, vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, vec3d.field_72450_a, vec3d.field_72448_b + 0.05D, vec3d.field_72449_c, Item.func_150891_b(p_184584_1_.func_77973_b()));
               }
            }

            this.func_184185_a(SoundEvents.field_187537_bA, 0.5F + 0.5F * (float)this.field_70146_Z.nextInt(2), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         }

      }
   }

   protected void func_71036_o() {
      if (!this.field_184627_bm.func_190926_b() && this.func_184587_cr()) {
         this.func_184584_a(this.field_184627_bm, 16);
         this.func_184611_a(this.func_184600_cs(), this.field_184627_bm.func_77950_b(this.field_70170_p, this));
         this.func_184602_cy();
      }

   }

   public ItemStack func_184607_cu() {
      return this.field_184627_bm;
   }

   public int func_184605_cv() {
      return this.field_184628_bn;
   }

   public int func_184612_cw() {
      return this.func_184587_cr() ? this.field_184627_bm.func_77988_m() - this.func_184605_cv() : 0;
   }

   public void func_184597_cx() {
      if (!this.field_184627_bm.func_190926_b()) {
         this.field_184627_bm.func_77974_b(this.field_70170_p, this, this.func_184605_cv());
      }

      this.func_184602_cy();
   }

   public void func_184602_cy() {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70180_af.func_187227_b(field_184621_as, Byte.valueOf((byte)0));
      }

      this.field_184627_bm = ItemStack.field_190927_a;
      this.field_184628_bn = 0;
   }

   public boolean func_184585_cz() {
      if (this.func_184587_cr() && !this.field_184627_bm.func_190926_b()) {
         Item item = this.field_184627_bm.func_77973_b();
         if (item.func_77661_b(this.field_184627_bm) != EnumAction.BLOCK) {
            return false;
         } else {
            return item.func_77626_a(this.field_184627_bm) - this.field_184628_bn >= 5;
         }
      } else {
         return false;
      }
   }

   public boolean func_184613_cA() {
      return this.func_70083_f(7);
   }

   public int func_184599_cB() {
      return this.field_184629_bo;
   }

   public boolean func_184595_k(double p_184595_1_, double p_184595_3_, double p_184595_5_) {
      double d0 = this.field_70165_t;
      double d1 = this.field_70163_u;
      double d2 = this.field_70161_v;
      this.field_70165_t = p_184595_1_;
      this.field_70163_u = p_184595_3_;
      this.field_70161_v = p_184595_5_;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(this);
      World world = this.field_70170_p;
      Random random = this.func_70681_au();
      if (world.func_175667_e(blockpos)) {
         boolean flag1 = false;

         while(!flag1 && blockpos.func_177956_o() > 0) {
            BlockPos blockpos1 = blockpos.func_177977_b();
            IBlockState iblockstate = world.func_180495_p(blockpos1);
            if (iblockstate.func_185904_a().func_76230_c()) {
               flag1 = true;
            } else {
               --this.field_70163_u;
               blockpos = blockpos1;
            }
         }

         if (flag1) {
            this.func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            if (world.func_184144_a(this, this.func_174813_aQ()).isEmpty() && !world.func_72953_d(this.func_174813_aQ())) {
               flag = true;
            }
         }
      }

      if (!flag) {
         this.func_70634_a(d0, d1, d2);
         return false;
      } else {
         int i = 128;

         for(int j = 0; j < 128; ++j) {
            double d6 = (double)j / 127.0D;
            float f = (random.nextFloat() - 0.5F) * 0.2F;
            float f1 = (random.nextFloat() - 0.5F) * 0.2F;
            float f2 = (random.nextFloat() - 0.5F) * 0.2F;
            double d3 = d0 + (this.field_70165_t - d0) * d6 + (random.nextDouble() - 0.5D) * (double)this.field_70130_N * 2.0D;
            double d4 = d1 + (this.field_70163_u - d1) * d6 + random.nextDouble() * (double)this.field_70131_O;
            double d5 = d2 + (this.field_70161_v - d2) * d6 + (random.nextDouble() - 0.5D) * (double)this.field_70130_N * 2.0D;
            world.func_175688_a(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2);
         }

         if (this instanceof EntityCreature) {
            ((EntityCreature)this).func_70661_as().func_75499_g();
         }

         return true;
      }
   }

   public boolean func_184603_cC() {
      return true;
   }

   public boolean func_190631_cK() {
      return true;
   }

   public void func_191987_a(BlockPos p_191987_1_, boolean p_191987_2_) {
   }
}
