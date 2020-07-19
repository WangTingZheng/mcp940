package net.minecraft.entity.monster;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityEnderman extends EntityMob {
   private static final UUID field_110192_bp = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier field_110193_bq = (new AttributeModifier(field_110192_bp, "Attacking speed boost", 0.15000000596046448D, 0)).func_111168_a(false);
   private static final Set<Block> field_70827_d = Sets.<Block>newIdentityHashSet();
   private static final DataParameter<Optional<IBlockState>> field_184718_bv = EntityDataManager.<Optional<IBlockState>>func_187226_a(EntityEnderman.class, DataSerializers.field_187197_g);
   private static final DataParameter<Boolean> field_184719_bw = EntityDataManager.<Boolean>func_187226_a(EntityEnderman.class, DataSerializers.field_187198_h);
   private int field_184720_bx;
   private int field_184721_by;

   public EntityEnderman(World p_i1734_1_) {
      super(p_i1734_1_);
      this.func_70105_a(0.6F, 2.9F);
      this.field_70138_W = 1.0F;
      this.func_184644_a(PathNodeType.WATER, -1.0F);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIAttackMelee(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70714_bg.func_75776_a(10, new EntityEnderman.AIPlaceBlock(this));
      this.field_70714_bg.func_75776_a(11, new EntityEnderman.AITakeBlock(this));
      this.field_70715_bh.func_75776_a(1, new EntityEnderman.AIFindPlayer(this));
      this.field_70715_bh.func_75776_a(2, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>() {
         public boolean apply(@Nullable EntityEndermite p_apply_1_) {
            return p_apply_1_.func_175495_n();
         }
      }));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
   }

   public void func_70624_b(@Nullable EntityLivingBase p_70624_1_) {
      super.func_70624_b(p_70624_1_);
      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (p_70624_1_ == null) {
         this.field_184721_by = 0;
         this.field_70180_af.func_187227_b(field_184719_bw, Boolean.valueOf(false));
         iattributeinstance.func_111124_b(field_110193_bq);
      } else {
         this.field_184721_by = this.field_70173_aa;
         this.field_70180_af.func_187227_b(field_184719_bw, Boolean.valueOf(true));
         if (!iattributeinstance.func_180374_a(field_110193_bq)) {
            iattributeinstance.func_111121_a(field_110193_bq);
         }
      }

   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184718_bv, Optional.absent());
      this.field_70180_af.func_187214_a(field_184719_bw, Boolean.valueOf(false));
   }

   public void func_184716_o() {
      if (this.field_70173_aa >= this.field_184720_bx + 400) {
         this.field_184720_bx = this.field_70173_aa;
         if (!this.func_174814_R()) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v, SoundEvents.field_187533_aW, this.func_184176_by(), 2.5F, 1.0F, false);
         }
      }

   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184719_bw.equals(p_184206_1_) && this.func_70823_r() && this.field_70170_p.field_72995_K) {
         this.func_184716_o();
      }

      super.func_184206_a(p_184206_1_);
   }

   public static void func_189763_b(DataFixer p_189763_0_) {
      EntityLiving.func_189752_a(p_189763_0_, EntityEnderman.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      IBlockState iblockstate = this.func_175489_ck();
      if (iblockstate != null) {
         p_70014_1_.func_74777_a("carried", (short)Block.func_149682_b(iblockstate.func_177230_c()));
         p_70014_1_.func_74777_a("carriedData", (short)iblockstate.func_177230_c().func_176201_c(iblockstate));
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      IBlockState iblockstate;
      if (p_70037_1_.func_150297_b("carried", 8)) {
         iblockstate = Block.func_149684_b(p_70037_1_.func_74779_i("carried")).func_176203_a(p_70037_1_.func_74765_d("carriedData") & '\uffff');
      } else {
         iblockstate = Block.func_149729_e(p_70037_1_.func_74765_d("carried")).func_176203_a(p_70037_1_.func_74765_d("carriedData") & '\uffff');
      }

      if (iblockstate == null || iblockstate.func_177230_c() == null || iblockstate.func_185904_a() == Material.field_151579_a) {
         iblockstate = null;
      }

      this.func_175490_a(iblockstate);
   }

   private boolean func_70821_d(EntityPlayer p_70821_1_) {
      ItemStack itemstack = p_70821_1_.field_71071_by.field_70460_b.get(3);
      if (itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150423_aK)) {
         return false;
      } else {
         Vec3d vec3d = p_70821_1_.func_70676_i(1.0F).func_72432_b();
         Vec3d vec3d1 = new Vec3d(this.field_70165_t - p_70821_1_.field_70165_t, this.func_174813_aQ().field_72338_b + (double)this.func_70047_e() - (p_70821_1_.field_70163_u + (double)p_70821_1_.func_70047_e()), this.field_70161_v - p_70821_1_.field_70161_v);
         double d0 = vec3d1.func_72433_c();
         vec3d1 = vec3d1.func_72432_b();
         double d1 = vec3d.func_72430_b(vec3d1);
         return d1 > 1.0D - 0.025D / d0 ? p_70821_1_.func_70685_l(this) : false;
      }
   }

   public float func_70047_e() {
      return 2.55F;
   }

   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         for(int i = 0; i < 2; ++i) {
            this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D);
         }
      }

      this.field_70703_bu = false;
      super.func_70636_d();
   }

   protected void func_70619_bc() {
      if (this.func_70026_G()) {
         this.func_70097_a(DamageSource.field_76369_e, 1.0F);
      }

      if (this.field_70170_p.func_72935_r() && this.field_70173_aa >= this.field_184721_by + 600) {
         float f = this.func_70013_c();
         if (f > 0.5F && this.field_70170_p.func_175678_i(new BlockPos(this)) && this.field_70146_Z.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
            this.func_70624_b((EntityLivingBase)null);
            this.func_70820_n();
         }
      }

      super.func_70619_bc();
   }

   protected boolean func_70820_n() {
      double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 64.0D;
      double d1 = this.field_70163_u + (double)(this.field_70146_Z.nextInt(64) - 32);
      double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 64.0D;
      return this.func_70825_j(d0, d1, d2);
   }

   protected boolean func_70816_c(Entity p_70816_1_) {
      Vec3d vec3d = new Vec3d(this.field_70165_t - p_70816_1_.field_70165_t, this.func_174813_aQ().field_72338_b + (double)(this.field_70131_O / 2.0F) - p_70816_1_.field_70163_u + (double)p_70816_1_.func_70047_e(), this.field_70161_v - p_70816_1_.field_70161_v);
      vec3d = vec3d.func_72432_b();
      double d0 = 16.0D;
      double d1 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72450_a * 16.0D;
      double d2 = this.field_70163_u + (double)(this.field_70146_Z.nextInt(16) - 8) - vec3d.field_72448_b * 16.0D;
      double d3 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72449_c * 16.0D;
      return this.func_70825_j(d1, d2, d3);
   }

   private boolean func_70825_j(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
      boolean flag = this.func_184595_k(p_70825_1_, p_70825_3_, p_70825_5_);
      if (flag) {
         this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, this.func_184176_by(), 1.0F, 1.0F);
         this.func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
      }

      return flag;
   }

   protected SoundEvent func_184639_G() {
      return this.func_70823_r() ? SoundEvents.field_187532_aV : SoundEvents.field_187529_aS;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187531_aU;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187530_aT;
   }

   protected void func_82160_b(boolean p_82160_1_, int p_82160_2_) {
      super.func_82160_b(p_82160_1_, p_82160_2_);
      IBlockState iblockstate = this.func_175489_ck();
      if (iblockstate != null) {
         Item item = Item.func_150898_a(iblockstate.func_177230_c());
         int i = item.func_77614_k() ? iblockstate.func_177230_c().func_176201_c(iblockstate) : 0;
         this.func_70099_a(new ItemStack(item, 1, i), 0.0F);
      }

   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186439_u;
   }

   public void func_175490_a(@Nullable IBlockState p_175490_1_) {
      this.field_70180_af.func_187227_b(field_184718_bv, Optional.fromNullable(p_175490_1_));
   }

   @Nullable
   public IBlockState func_175489_ck() {
      return (IBlockState)((Optional)this.field_70180_af.func_187225_a(field_184718_bv)).orNull();
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else if (p_70097_1_ instanceof EntityDamageSourceIndirect) {
         for(int i = 0; i < 64; ++i) {
            if (this.func_70820_n()) {
               return true;
            }
         }

         return false;
      } else {
         boolean flag = super.func_70097_a(p_70097_1_, p_70097_2_);
         if (p_70097_1_.func_76363_c() && this.field_70146_Z.nextInt(10) != 0) {
            this.func_70820_n();
         }

         return flag;
      }
   }

   public boolean func_70823_r() {
      return ((Boolean)this.field_70180_af.func_187225_a(field_184719_bw)).booleanValue();
   }

   static {
      field_70827_d.add(Blocks.field_150349_c);
      field_70827_d.add(Blocks.field_150346_d);
      field_70827_d.add(Blocks.field_150354_m);
      field_70827_d.add(Blocks.field_150351_n);
      field_70827_d.add(Blocks.field_150327_N);
      field_70827_d.add(Blocks.field_150328_O);
      field_70827_d.add(Blocks.field_150338_P);
      field_70827_d.add(Blocks.field_150337_Q);
      field_70827_d.add(Blocks.field_150335_W);
      field_70827_d.add(Blocks.field_150434_aF);
      field_70827_d.add(Blocks.field_150435_aG);
      field_70827_d.add(Blocks.field_150423_aK);
      field_70827_d.add(Blocks.field_150440_ba);
      field_70827_d.add(Blocks.field_150391_bh);
      field_70827_d.add(Blocks.field_150424_aL);
   }

   static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
      private final EntityEnderman field_179449_j;
      private EntityPlayer field_179448_g;
      private int field_179450_h;
      private int field_179451_i;

      public AIFindPlayer(EntityEnderman p_i45842_1_) {
         super(p_i45842_1_, EntityPlayer.class, false);
         this.field_179449_j = p_i45842_1_;
      }

      public boolean func_75250_a() {
         double d0 = this.func_111175_f();
         this.field_179448_g = this.field_179449_j.field_70170_p.func_184150_a(this.field_179449_j.field_70165_t, this.field_179449_j.field_70163_u, this.field_179449_j.field_70161_v, d0, d0, (Function)null, new Predicate<EntityPlayer>() {
            public boolean apply(@Nullable EntityPlayer p_apply_1_) {
               return p_apply_1_ != null && AIFindPlayer.this.field_179449_j.func_70821_d(p_apply_1_);
            }
         });
         return this.field_179448_g != null;
      }

      public void func_75249_e() {
         this.field_179450_h = 5;
         this.field_179451_i = 0;
      }

      public void func_75251_c() {
         this.field_179448_g = null;
         super.func_75251_c();
      }

      public boolean func_75253_b() {
         if (this.field_179448_g != null) {
            if (!this.field_179449_j.func_70821_d(this.field_179448_g)) {
               return false;
            } else {
               this.field_179449_j.func_70625_a(this.field_179448_g, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.field_75309_a != null && ((EntityPlayer)this.field_75309_a).func_70089_S() ? true : super.func_75253_b();
         }
      }

      public void func_75246_d() {
         if (this.field_179448_g != null) {
            if (--this.field_179450_h <= 0) {
               this.field_75309_a = (T)this.field_179448_g;
               this.field_179448_g = null;
               super.func_75249_e();
            }
         } else {
            if (this.field_75309_a != null) {
               if (this.field_179449_j.func_70821_d((EntityPlayer)this.field_75309_a)) {
                  if (((EntityPlayer)this.field_75309_a).func_70068_e(this.field_179449_j) < 16.0D) {
                     this.field_179449_j.func_70820_n();
                  }

                  this.field_179451_i = 0;
               } else if (((EntityPlayer)this.field_75309_a).func_70068_e(this.field_179449_j) > 256.0D && this.field_179451_i++ >= 30 && this.field_179449_j.func_70816_c(this.field_75309_a)) {
                  this.field_179451_i = 0;
               }
            }

            super.func_75246_d();
         }

      }
   }

   static class AIPlaceBlock extends EntityAIBase {
      private final EntityEnderman field_179475_a;

      public AIPlaceBlock(EntityEnderman p_i45843_1_) {
         this.field_179475_a = p_i45843_1_;
      }

      public boolean func_75250_a() {
         if (this.field_179475_a.func_175489_ck() == null) {
            return false;
         } else if (!this.field_179475_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         } else {
            return this.field_179475_a.func_70681_au().nextInt(2000) == 0;
         }
      }

      public void func_75246_d() {
         Random random = this.field_179475_a.func_70681_au();
         World world = this.field_179475_a.field_70170_p;
         int i = MathHelper.func_76128_c(this.field_179475_a.field_70165_t - 1.0D + random.nextDouble() * 2.0D);
         int j = MathHelper.func_76128_c(this.field_179475_a.field_70163_u + random.nextDouble() * 2.0D);
         int k = MathHelper.func_76128_c(this.field_179475_a.field_70161_v - 1.0D + random.nextDouble() * 2.0D);
         BlockPos blockpos = new BlockPos(i, j, k);
         IBlockState iblockstate = world.func_180495_p(blockpos);
         IBlockState iblockstate1 = world.func_180495_p(blockpos.func_177977_b());
         IBlockState iblockstate2 = this.field_179475_a.func_175489_ck();
         if (iblockstate2 != null && this.func_188518_a(world, blockpos, iblockstate2.func_177230_c(), iblockstate, iblockstate1)) {
            world.func_180501_a(blockpos, iblockstate2, 3);
            this.field_179475_a.func_175490_a((IBlockState)null);
         }

      }

      private boolean func_188518_a(World p_188518_1_, BlockPos p_188518_2_, Block p_188518_3_, IBlockState p_188518_4_, IBlockState p_188518_5_) {
         if (!p_188518_3_.func_176196_c(p_188518_1_, p_188518_2_)) {
            return false;
         } else if (p_188518_4_.func_185904_a() != Material.field_151579_a) {
            return false;
         } else if (p_188518_5_.func_185904_a() == Material.field_151579_a) {
            return false;
         } else {
            return p_188518_5_.func_185917_h();
         }
      }
   }

   static class AITakeBlock extends EntityAIBase {
      private final EntityEnderman field_179473_a;

      public AITakeBlock(EntityEnderman p_i45841_1_) {
         this.field_179473_a = p_i45841_1_;
      }

      public boolean func_75250_a() {
         if (this.field_179473_a.func_175489_ck() != null) {
            return false;
         } else if (!this.field_179473_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         } else {
            return this.field_179473_a.func_70681_au().nextInt(20) == 0;
         }
      }

      public void func_75246_d() {
         Random random = this.field_179473_a.func_70681_au();
         World world = this.field_179473_a.field_70170_p;
         int i = MathHelper.func_76128_c(this.field_179473_a.field_70165_t - 2.0D + random.nextDouble() * 4.0D);
         int j = MathHelper.func_76128_c(this.field_179473_a.field_70163_u + random.nextDouble() * 3.0D);
         int k = MathHelper.func_76128_c(this.field_179473_a.field_70161_v - 2.0D + random.nextDouble() * 4.0D);
         BlockPos blockpos = new BlockPos(i, j, k);
         IBlockState iblockstate = world.func_180495_p(blockpos);
         Block block = iblockstate.func_177230_c();
         RayTraceResult raytraceresult = world.func_147447_a(new Vec3d((double)((float)MathHelper.func_76128_c(this.field_179473_a.field_70165_t) + 0.5F), (double)((float)j + 0.5F), (double)((float)MathHelper.func_76128_c(this.field_179473_a.field_70161_v) + 0.5F)), new Vec3d((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F)), false, true, false);
         boolean flag = raytraceresult != null && raytraceresult.func_178782_a().equals(blockpos);
         if (EntityEnderman.field_70827_d.contains(block) && flag) {
            this.field_179473_a.func_175490_a(iblockstate);
            world.func_175698_g(blockpos);
         }

      }
   }
}
