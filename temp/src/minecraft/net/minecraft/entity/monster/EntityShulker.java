package net.minecraft.entity.monster;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityShulker extends EntityGolem implements IMob {
   private static final UUID field_184703_bv = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
   private static final AttributeModifier field_184704_bw = (new AttributeModifier(field_184703_bv, "Covered armor bonus", 20.0D, 0)).func_111168_a(false);
   protected static final DataParameter<EnumFacing> field_184700_a = EntityDataManager.<EnumFacing>func_187226_a(EntityShulker.class, DataSerializers.field_187202_l);
   protected static final DataParameter<Optional<BlockPos>> field_184701_b = EntityDataManager.<Optional<BlockPos>>func_187226_a(EntityShulker.class, DataSerializers.field_187201_k);
   protected static final DataParameter<Byte> field_184702_c = EntityDataManager.<Byte>func_187226_a(EntityShulker.class, DataSerializers.field_187191_a);
   protected static final DataParameter<Byte> field_190770_bw = EntityDataManager.<Byte>func_187226_a(EntityShulker.class, DataSerializers.field_187191_a);
   public static final EnumDyeColor field_190771_bx = EnumDyeColor.PURPLE;
   private float field_184705_bx;
   private float field_184706_by;
   private BlockPos field_184707_bz;
   private int field_184708_bA;

   public EntityShulker(World p_i46779_1_) {
      super(p_i46779_1_);
      this.func_70105_a(1.0F, 1.0F);
      this.field_70760_ar = 180.0F;
      this.field_70761_aq = 180.0F;
      this.field_70178_ae = true;
      this.field_184707_bz = null;
      this.field_70728_aV = 5;
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      this.field_70761_aq = 180.0F;
      this.field_70760_ar = 180.0F;
      this.field_70177_z = 180.0F;
      this.field_70126_B = 180.0F;
      this.field_70759_as = 180.0F;
      this.field_70758_at = 180.0F;
      return super.func_180482_a(p_180482_1_, p_180482_2_);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(4, new EntityShulker.AIAttack());
      this.field_70714_bg.func_75776_a(7, new EntityShulker.AIPeek());
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntityShulker.AIAttackNearest(this));
      this.field_70715_bh.func_75776_a(3, new EntityShulker.AIDefenseAttack(this));
   }

   protected boolean func_70041_e_() {
      return false;
   }

   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187773_eO;
   }

   public void func_70642_aH() {
      if (!this.func_184686_df()) {
         super.func_70642_aH();
      }

   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187781_eS;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return this.func_184686_df() ? SoundEvents.field_187785_eU : SoundEvents.field_187783_eT;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184700_a, EnumFacing.DOWN);
      this.field_70180_af.func_187214_a(field_184701_b, Optional.absent());
      this.field_70180_af.func_187214_a(field_184702_c, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_190770_bw, Byte.valueOf((byte)field_190771_bx.func_176765_a()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
   }

   protected EntityBodyHelper func_184650_s() {
      return new EntityShulker.BodyHelper(this);
   }

   public static void func_189757_b(DataFixer p_189757_0_) {
      EntityLiving.func_189752_a(p_189757_0_, EntityShulker.class);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_70180_af.func_187227_b(field_184700_a, EnumFacing.func_82600_a(p_70037_1_.func_74771_c("AttachFace")));
      this.field_70180_af.func_187227_b(field_184702_c, Byte.valueOf(p_70037_1_.func_74771_c("Peek")));
      this.field_70180_af.func_187227_b(field_190770_bw, Byte.valueOf(p_70037_1_.func_74771_c("Color")));
      if (p_70037_1_.func_74764_b("APX")) {
         int i = p_70037_1_.func_74762_e("APX");
         int j = p_70037_1_.func_74762_e("APY");
         int k = p_70037_1_.func_74762_e("APZ");
         this.field_70180_af.func_187227_b(field_184701_b, Optional.of(new BlockPos(i, j, k)));
      } else {
         this.field_70180_af.func_187227_b(field_184701_b, Optional.absent());
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74774_a("AttachFace", (byte)((EnumFacing)this.field_70180_af.func_187225_a(field_184700_a)).func_176745_a());
      p_70014_1_.func_74774_a("Peek", ((Byte)this.field_70180_af.func_187225_a(field_184702_c)).byteValue());
      p_70014_1_.func_74774_a("Color", ((Byte)this.field_70180_af.func_187225_a(field_190770_bw)).byteValue());
      BlockPos blockpos = this.func_184699_da();
      if (blockpos != null) {
         p_70014_1_.func_74768_a("APX", blockpos.func_177958_n());
         p_70014_1_.func_74768_a("APY", blockpos.func_177956_o());
         p_70014_1_.func_74768_a("APZ", blockpos.func_177952_p());
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      BlockPos blockpos = (BlockPos)((Optional)this.field_70180_af.func_187225_a(field_184701_b)).orNull();
      if (blockpos == null && !this.field_70170_p.field_72995_K) {
         blockpos = new BlockPos(this);
         this.field_70180_af.func_187227_b(field_184701_b, Optional.of(blockpos));
      }

      if (this.func_184218_aH()) {
         blockpos = null;
         float f = this.func_184187_bx().field_70177_z;
         this.field_70177_z = f;
         this.field_70761_aq = f;
         this.field_70760_ar = f;
         this.field_184708_bA = 0;
      } else if (!this.field_70170_p.field_72995_K) {
         IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
         if (iblockstate.func_185904_a() != Material.field_151579_a) {
            if (iblockstate.func_177230_c() == Blocks.field_180384_M) {
               EnumFacing enumfacing = (EnumFacing)iblockstate.func_177229_b(BlockPistonBase.field_176387_N);
               if (this.field_70170_p.func_175623_d(blockpos.func_177972_a(enumfacing))) {
                  blockpos = blockpos.func_177972_a(enumfacing);
                  this.field_70180_af.func_187227_b(field_184701_b, Optional.of(blockpos));
               } else {
                  this.func_184689_o();
               }
            } else if (iblockstate.func_177230_c() == Blocks.field_150332_K) {
               EnumFacing enumfacing3 = (EnumFacing)iblockstate.func_177229_b(BlockPistonExtension.field_176387_N);
               if (this.field_70170_p.func_175623_d(blockpos.func_177972_a(enumfacing3))) {
                  blockpos = blockpos.func_177972_a(enumfacing3);
                  this.field_70180_af.func_187227_b(field_184701_b, Optional.of(blockpos));
               } else {
                  this.func_184689_o();
               }
            } else {
               this.func_184689_o();
            }
         }

         BlockPos blockpos1 = blockpos.func_177972_a(this.func_184696_cZ());
         if (!this.field_70170_p.func_175677_d(blockpos1, false)) {
            boolean flag = false;

            for(EnumFacing enumfacing1 : EnumFacing.values()) {
               blockpos1 = blockpos.func_177972_a(enumfacing1);
               if (this.field_70170_p.func_175677_d(blockpos1, false)) {
                  this.field_70180_af.func_187227_b(field_184700_a, enumfacing1);
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               this.func_184689_o();
            }
         }

         BlockPos blockpos2 = blockpos.func_177972_a(this.func_184696_cZ().func_176734_d());
         if (this.field_70170_p.func_175677_d(blockpos2, false)) {
            this.func_184689_o();
         }
      }

      float f1 = (float)this.func_184684_db() * 0.01F;
      this.field_184705_bx = this.field_184706_by;
      if (this.field_184706_by > f1) {
         this.field_184706_by = MathHelper.func_76131_a(this.field_184706_by - 0.05F, f1, 1.0F);
      } else if (this.field_184706_by < f1) {
         this.field_184706_by = MathHelper.func_76131_a(this.field_184706_by + 0.05F, 0.0F, f1);
      }

      if (blockpos != null) {
         if (this.field_70170_p.field_72995_K) {
            if (this.field_184708_bA > 0 && this.field_184707_bz != null) {
               --this.field_184708_bA;
            } else {
               this.field_184707_bz = blockpos;
            }
         }

         this.field_70165_t = (double)blockpos.func_177958_n() + 0.5D;
         this.field_70163_u = (double)blockpos.func_177956_o();
         this.field_70161_v = (double)blockpos.func_177952_p() + 0.5D;
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         this.field_70142_S = this.field_70165_t;
         this.field_70137_T = this.field_70163_u;
         this.field_70136_U = this.field_70161_v;
         double d3 = 0.5D - (double)MathHelper.func_76126_a((0.5F + this.field_184706_by) * 3.1415927F) * 0.5D;
         double d4 = 0.5D - (double)MathHelper.func_76126_a((0.5F + this.field_184705_bx) * 3.1415927F) * 0.5D;
         double d5 = d3 - d4;
         double d0 = 0.0D;
         double d1 = 0.0D;
         double d2 = 0.0D;
         EnumFacing enumfacing2 = this.func_184696_cZ();
         switch(enumfacing2) {
         case DOWN:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D + d3, this.field_70161_v + 0.5D));
            d1 = d5;
            break;
         case UP:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u - d3, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d1 = -d5;
            break;
         case NORTH:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D + d3));
            d2 = d5;
            break;
         case SOUTH:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D - d3, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d2 = -d5;
            break;
         case WEST:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D + d3, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d0 = d5;
            break;
         case EAST:
            this.func_174826_a(new AxisAlignedBB(this.field_70165_t - 0.5D - d3, this.field_70163_u, this.field_70161_v - 0.5D, this.field_70165_t + 0.5D, this.field_70163_u + 1.0D, this.field_70161_v + 0.5D));
            d0 = -d5;
         }

         if (d5 > 0.0D) {
            List<Entity> list = this.field_70170_p.func_72839_b(this, this.func_174813_aQ());
            if (!list.isEmpty()) {
               for(Entity entity : list) {
                  if (!(entity instanceof EntityShulker) && !entity.field_70145_X) {
                     entity.func_70091_d(MoverType.SHULKER, d0, d1, d2);
                  }
               }
            }
         }
      }

   }

   public void func_70091_d(MoverType p_70091_1_, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
      if (p_70091_1_ == MoverType.SHULKER_BOX) {
         this.func_184689_o();
      } else {
         super.func_70091_d(p_70091_1_, p_70091_2_, p_70091_4_, p_70091_6_);
      }

   }

   public void func_70107_b(double p_70107_1_, double p_70107_3_, double p_70107_5_) {
      super.func_70107_b(p_70107_1_, p_70107_3_, p_70107_5_);
      if (this.field_70180_af != null && this.field_70173_aa != 0) {
         Optional<BlockPos> optional = (Optional)this.field_70180_af.func_187225_a(field_184701_b);
         Optional<BlockPos> optional1 = Optional.<BlockPos>of(new BlockPos(p_70107_1_, p_70107_3_, p_70107_5_));
         if (!optional1.equals(optional)) {
            this.field_70180_af.func_187227_b(field_184701_b, optional1);
            this.field_70180_af.func_187227_b(field_184702_c, Byte.valueOf((byte)0));
            this.field_70160_al = true;
         }

      }
   }

   protected boolean func_184689_o() {
      if (!this.func_175446_cd() && this.func_70089_S()) {
         BlockPos blockpos = new BlockPos(this);

         for(int i = 0; i < 5; ++i) {
            BlockPos blockpos1 = blockpos.func_177982_a(8 - this.field_70146_Z.nextInt(17), 8 - this.field_70146_Z.nextInt(17), 8 - this.field_70146_Z.nextInt(17));
            if (blockpos1.func_177956_o() > 0 && this.field_70170_p.func_175623_d(blockpos1) && this.field_70170_p.func_191503_g(this) && this.field_70170_p.func_184144_a(this, new AxisAlignedBB(blockpos1)).isEmpty()) {
               boolean flag = false;

               for(EnumFacing enumfacing : EnumFacing.values()) {
                  if (this.field_70170_p.func_175677_d(blockpos1.func_177972_a(enumfacing), false)) {
                     this.field_70180_af.func_187227_b(field_184700_a, enumfacing);
                     flag = true;
                     break;
                  }
               }

               if (flag) {
                  this.func_184185_a(SoundEvents.field_187791_eX, 1.0F, 1.0F);
                  this.field_70180_af.func_187227_b(field_184701_b, Optional.of(blockpos1));
                  this.field_70180_af.func_187227_b(field_184702_c, Byte.valueOf((byte)0));
                  this.func_70624_b((EntityLivingBase)null);
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70760_ar = 180.0F;
      this.field_70761_aq = 180.0F;
      this.field_70177_z = 180.0F;
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184701_b.equals(p_184206_1_) && this.field_70170_p.field_72995_K && !this.func_184218_aH()) {
         BlockPos blockpos = this.func_184699_da();
         if (blockpos != null) {
            if (this.field_184707_bz == null) {
               this.field_184707_bz = blockpos;
            } else {
               this.field_184708_bA = 6;
            }

            this.field_70165_t = (double)blockpos.func_177958_n() + 0.5D;
            this.field_70163_u = (double)blockpos.func_177956_o();
            this.field_70161_v = (double)blockpos.func_177952_p() + 0.5D;
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            this.field_70142_S = this.field_70165_t;
            this.field_70137_T = this.field_70163_u;
            this.field_70136_U = this.field_70161_v;
         }
      }

      super.func_184206_a(p_184206_1_);
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      this.field_70716_bi = 0;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_184686_df()) {
         Entity entity = p_70097_1_.func_76364_f();
         if (entity instanceof EntityArrow) {
            return false;
         }
      }

      if (super.func_70097_a(p_70097_1_, p_70097_2_)) {
         if ((double)this.func_110143_aJ() < (double)this.func_110138_aP() * 0.5D && this.field_70146_Z.nextInt(4) == 0) {
            this.func_184689_o();
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean func_184686_df() {
      return this.func_184684_db() == 0;
   }

   @Nullable
   public AxisAlignedBB func_70046_E() {
      return this.func_70089_S() ? this.func_174813_aQ() : null;
   }

   public EnumFacing func_184696_cZ() {
      return (EnumFacing)this.field_70180_af.func_187225_a(field_184700_a);
   }

   @Nullable
   public BlockPos func_184699_da() {
      return (BlockPos)((Optional)this.field_70180_af.func_187225_a(field_184701_b)).orNull();
   }

   public void func_184694_g(@Nullable BlockPos p_184694_1_) {
      this.field_70180_af.func_187227_b(field_184701_b, Optional.fromNullable(p_184694_1_));
   }

   public int func_184684_db() {
      return ((Byte)this.field_70180_af.func_187225_a(field_184702_c)).byteValue();
   }

   public void func_184691_a(int p_184691_1_) {
      if (!this.field_70170_p.field_72995_K) {
         this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111124_b(field_184704_bw);
         if (p_184691_1_ == 0) {
            this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111121_a(field_184704_bw);
            this.func_184185_a(SoundEvents.field_187779_eR, 1.0F, 1.0F);
         } else {
            this.func_184185_a(SoundEvents.field_187787_eV, 1.0F, 1.0F);
         }
      }

      this.field_70180_af.func_187227_b(field_184702_c, Byte.valueOf((byte)p_184691_1_));
   }

   public float func_184688_a(float p_184688_1_) {
      return this.field_184705_bx + (this.field_184706_by - this.field_184705_bx) * p_184688_1_;
   }

   public int func_184693_dc() {
      return this.field_184708_bA;
   }

   public BlockPos func_184692_dd() {
      return this.field_184707_bz;
   }

   public float func_70047_e() {
      return 0.5F;
   }

   public int func_70646_bf() {
      return 180;
   }

   public int func_184649_cE() {
      return 180;
   }

   public void func_70108_f(Entity p_70108_1_) {
   }

   public float func_70111_Y() {
      return 0.0F;
   }

   public boolean func_184697_de() {
      return this.field_184707_bz != null && this.func_184699_da() != null;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186442_x;
   }

   public EnumDyeColor func_190769_dn() {
      return EnumDyeColor.func_176764_b(((Byte)this.field_70180_af.func_187225_a(field_190770_bw)).byteValue());
   }

   class AIAttack extends EntityAIBase {
      private int field_188520_b;

      public AIAttack() {
         this.func_75248_a(3);
      }

      public boolean func_75250_a() {
         EntityLivingBase entitylivingbase = EntityShulker.this.func_70638_az();
         if (entitylivingbase != null && entitylivingbase.func_70089_S()) {
            return EntityShulker.this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL;
         } else {
            return false;
         }
      }

      public void func_75249_e() {
         this.field_188520_b = 20;
         EntityShulker.this.func_184691_a(100);
      }

      public void func_75251_c() {
         EntityShulker.this.func_184691_a(0);
      }

      public void func_75246_d() {
         if (EntityShulker.this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL) {
            --this.field_188520_b;
            EntityLivingBase entitylivingbase = EntityShulker.this.func_70638_az();
            EntityShulker.this.func_70671_ap().func_75651_a(entitylivingbase, 180.0F, 180.0F);
            double d0 = EntityShulker.this.func_70068_e(entitylivingbase);
            if (d0 < 400.0D) {
               if (this.field_188520_b <= 0) {
                  this.field_188520_b = 20 + EntityShulker.this.field_70146_Z.nextInt(10) * 20 / 2;
                  EntityShulkerBullet entityshulkerbullet = new EntityShulkerBullet(EntityShulker.this.field_70170_p, EntityShulker.this, entitylivingbase, EntityShulker.this.func_184696_cZ().func_176740_k());
                  EntityShulker.this.field_70170_p.func_72838_d(entityshulkerbullet);
                  EntityShulker.this.func_184185_a(SoundEvents.field_187789_eW, 2.0F, (EntityShulker.this.field_70146_Z.nextFloat() - EntityShulker.this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
               }
            } else {
               EntityShulker.this.func_70624_b((EntityLivingBase)null);
            }

            super.func_75246_d();
         }
      }
   }

   class AIAttackNearest extends EntityAINearestAttackableTarget<EntityPlayer> {
      public AIAttackNearest(EntityShulker p_i47060_2_) {
         super(p_i47060_2_, EntityPlayer.class, true);
      }

      public boolean func_75250_a() {
         return EntityShulker.this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL ? false : super.func_75250_a();
      }

      protected AxisAlignedBB func_188511_a(double p_188511_1_) {
         EnumFacing enumfacing = ((EntityShulker)this.field_75299_d).func_184696_cZ();
         if (enumfacing.func_176740_k() == EnumFacing.Axis.X) {
            return this.field_75299_d.func_174813_aQ().func_72314_b(4.0D, p_188511_1_, p_188511_1_);
         } else {
            return enumfacing.func_176740_k() == EnumFacing.Axis.Z ? this.field_75299_d.func_174813_aQ().func_72314_b(p_188511_1_, p_188511_1_, 4.0D) : this.field_75299_d.func_174813_aQ().func_72314_b(p_188511_1_, 4.0D, p_188511_1_);
         }
      }
   }

   static class AIDefenseAttack extends EntityAINearestAttackableTarget<EntityLivingBase> {
      public AIDefenseAttack(EntityShulker p_i47061_1_) {
         super(p_i47061_1_, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
            public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
               return p_apply_1_ instanceof IMob;
            }
         });
      }

      public boolean func_75250_a() {
         return this.field_75299_d.func_96124_cp() == null ? false : super.func_75250_a();
      }

      protected AxisAlignedBB func_188511_a(double p_188511_1_) {
         EnumFacing enumfacing = ((EntityShulker)this.field_75299_d).func_184696_cZ();
         if (enumfacing.func_176740_k() == EnumFacing.Axis.X) {
            return this.field_75299_d.func_174813_aQ().func_72314_b(4.0D, p_188511_1_, p_188511_1_);
         } else {
            return enumfacing.func_176740_k() == EnumFacing.Axis.Z ? this.field_75299_d.func_174813_aQ().func_72314_b(p_188511_1_, p_188511_1_, 4.0D) : this.field_75299_d.func_174813_aQ().func_72314_b(p_188511_1_, 4.0D, p_188511_1_);
         }
      }
   }

   class AIPeek extends EntityAIBase {
      private int field_188522_b;

      private AIPeek() {
      }

      public boolean func_75250_a() {
         return EntityShulker.this.func_70638_az() == null && EntityShulker.this.field_70146_Z.nextInt(40) == 0;
      }

      public boolean func_75253_b() {
         return EntityShulker.this.func_70638_az() == null && this.field_188522_b > 0;
      }

      public void func_75249_e() {
         this.field_188522_b = 20 * (1 + EntityShulker.this.field_70146_Z.nextInt(3));
         EntityShulker.this.func_184691_a(30);
      }

      public void func_75251_c() {
         if (EntityShulker.this.func_70638_az() == null) {
            EntityShulker.this.func_184691_a(0);
         }

      }

      public void func_75246_d() {
         --this.field_188522_b;
      }
   }

   class BodyHelper extends EntityBodyHelper {
      public BodyHelper(EntityLivingBase p_i47062_2_) {
         super(p_i47062_2_);
      }

      public void func_75664_a() {
      }
   }
}
