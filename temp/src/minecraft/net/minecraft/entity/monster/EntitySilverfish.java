package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySilverfish extends EntityMob {
   private EntitySilverfish.AISummonSilverfish field_175460_b;

   public EntitySilverfish(World p_i1740_1_) {
      super(p_i1740_1_);
      this.func_70105_a(0.4F, 0.3F);
   }

   public static void func_189767_b(DataFixer p_189767_0_) {
      EntityLiving.func_189752_a(p_189767_0_, EntitySilverfish.class);
   }

   protected void func_184651_r() {
      this.field_175460_b = new EntitySilverfish.AISummonSilverfish(this);
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(3, this.field_175460_b);
      this.field_70714_bg.func_75776_a(4, new EntityAIAttackMelee(this, 1.0D, false));
      this.field_70714_bg.func_75776_a(5, new EntitySilverfish.AIHideInStone(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   public double func_70033_W() {
      return 0.1D;
   }

   public float func_70047_e() {
      return 0.1F;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187793_eY;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187850_fa;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187795_eZ;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_187852_fb, 0.15F, 1.0F);
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         if ((p_70097_1_ instanceof EntityDamageSource || p_70097_1_ == DamageSource.field_76376_m) && this.field_175460_b != null) {
            this.field_175460_b.func_179462_f();
         }

         return super.func_70097_a(p_70097_1_, p_70097_2_);
      }
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186438_t;
   }

   public void func_70071_h_() {
      this.field_70761_aq = this.field_70177_z;
      super.func_70071_h_();
   }

   public void func_181013_g(float p_181013_1_) {
      this.field_70177_z = p_181013_1_;
      super.func_181013_g(p_181013_1_);
   }

   public float func_180484_a(BlockPos p_180484_1_) {
      return this.field_70170_p.func_180495_p(p_180484_1_.func_177977_b()).func_177230_c() == Blocks.field_150348_b ? 10.0F : super.func_180484_a(p_180484_1_);
   }

   protected boolean func_70814_o() {
      return true;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         EntityPlayer entityplayer = this.field_70170_p.func_184136_b(this, 5.0D);
         return entityplayer == null;
      } else {
         return false;
      }
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   static class AIHideInStone extends EntityAIWander {
      private EnumFacing field_179483_b;
      private boolean field_179484_c;

      public AIHideInStone(EntitySilverfish p_i45827_1_) {
         super(p_i45827_1_, 1.0D, 10);
         this.func_75248_a(1);
      }

      public boolean func_75250_a() {
         if (this.field_75457_a.func_70638_az() != null) {
            return false;
         } else if (!this.field_75457_a.func_70661_as().func_75500_f()) {
            return false;
         } else {
            Random random = this.field_75457_a.func_70681_au();
            if (this.field_75457_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing") && random.nextInt(10) == 0) {
               this.field_179483_b = EnumFacing.func_176741_a(random);
               BlockPos blockpos = (new BlockPos(this.field_75457_a.field_70165_t, this.field_75457_a.field_70163_u + 0.5D, this.field_75457_a.field_70161_v)).func_177972_a(this.field_179483_b);
               IBlockState iblockstate = this.field_75457_a.field_70170_p.func_180495_p(blockpos);
               if (BlockSilverfish.func_176377_d(iblockstate)) {
                  this.field_179484_c = true;
                  return true;
               }
            }

            this.field_179484_c = false;
            return super.func_75250_a();
         }
      }

      public boolean func_75253_b() {
         return this.field_179484_c ? false : super.func_75253_b();
      }

      public void func_75249_e() {
         if (!this.field_179484_c) {
            super.func_75249_e();
         } else {
            World world = this.field_75457_a.field_70170_p;
            BlockPos blockpos = (new BlockPos(this.field_75457_a.field_70165_t, this.field_75457_a.field_70163_u + 0.5D, this.field_75457_a.field_70161_v)).func_177972_a(this.field_179483_b);
            IBlockState iblockstate = world.func_180495_p(blockpos);
            if (BlockSilverfish.func_176377_d(iblockstate)) {
               world.func_180501_a(blockpos, Blocks.field_150418_aU.func_176223_P().func_177226_a(BlockSilverfish.field_176378_a, BlockSilverfish.EnumType.func_176878_a(iblockstate)), 3);
               this.field_75457_a.func_70656_aK();
               this.field_75457_a.func_70106_y();
            }

         }
      }
   }

   static class AISummonSilverfish extends EntityAIBase {
      private final EntitySilverfish field_179464_a;
      private int field_179463_b;

      public AISummonSilverfish(EntitySilverfish p_i45826_1_) {
         this.field_179464_a = p_i45826_1_;
      }

      public void func_179462_f() {
         if (this.field_179463_b == 0) {
            this.field_179463_b = 20;
         }

      }

      public boolean func_75250_a() {
         return this.field_179463_b > 0;
      }

      public void func_75246_d() {
         --this.field_179463_b;
         if (this.field_179463_b <= 0) {
            World world = this.field_179464_a.field_70170_p;
            Random random = this.field_179464_a.func_70681_au();
            BlockPos blockpos = new BlockPos(this.field_179464_a);

            for(int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
               for(int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                  for(int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                     BlockPos blockpos1 = blockpos.func_177982_a(j, i, k);
                     IBlockState iblockstate = world.func_180495_p(blockpos1);
                     if (iblockstate.func_177230_c() == Blocks.field_150418_aU) {
                        if (world.func_82736_K().func_82766_b("mobGriefing")) {
                           world.func_175655_b(blockpos1, true);
                        } else {
                           world.func_180501_a(blockpos1, ((BlockSilverfish.EnumType)iblockstate.func_177229_b(BlockSilverfish.field_176378_a)).func_176883_d(), 3);
                        }

                        if (random.nextBoolean()) {
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
