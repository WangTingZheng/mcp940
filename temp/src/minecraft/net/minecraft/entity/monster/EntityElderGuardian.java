package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityElderGuardian extends EntityGuardian {
   public EntityElderGuardian(World p_i47288_1_) {
      super(p_i47288_1_);
      this.func_70105_a(this.field_70130_N * 2.35F, this.field_70131_O * 2.35F);
      this.func_110163_bv();
      if (this.field_175481_bq != null) {
         this.field_175481_bq.func_179479_b(400);
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(80.0D);
   }

   public static void func_190768_b(DataFixer p_190768_0_) {
      EntityLiving.func_189752_a(p_190768_0_, EntityElderGuardian.class);
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186441_w;
   }

   public int func_175464_ck() {
      return 60;
   }

   public void func_190767_di() {
      this.field_175485_bl = 1.0F;
      this.field_175486_bm = this.field_175485_bl;
   }

   protected SoundEvent func_184639_G() {
      return this.func_70090_H() ? SoundEvents.field_187512_aB : SoundEvents.field_187513_aC;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return this.func_70090_H() ? SoundEvents.field_187517_aG : SoundEvents.field_187518_aH;
   }

   protected SoundEvent func_184615_bR() {
      return this.func_70090_H() ? SoundEvents.field_187515_aE : SoundEvents.field_187516_aF;
   }

   protected SoundEvent func_190765_dj() {
      return SoundEvents.field_191240_aK;
   }

   protected void func_70619_bc() {
      super.func_70619_bc();
      int i = 1200;
      if ((this.field_70173_aa + this.func_145782_y()) % 1200 == 0) {
         Potion potion = MobEffects.field_76419_f;
         List<EntityPlayerMP> list = this.field_70170_p.<EntityPlayerMP>func_175661_b(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
            public boolean apply(@Nullable EntityPlayerMP p_apply_1_) {
               return EntityElderGuardian.this.func_70068_e(p_apply_1_) < 2500.0D && p_apply_1_.field_71134_c.func_180239_c();
            }
         });
         int j = 2;
         int k = 6000;
         int l = 1200;

         for(EntityPlayerMP entityplayermp : list) {
            if (!entityplayermp.func_70644_a(potion) || entityplayermp.func_70660_b(potion).func_76458_c() < 2 || entityplayermp.func_70660_b(potion).func_76459_b() < 1200) {
               entityplayermp.field_71135_a.func_147359_a(new SPacketChangeGameState(10, 0.0F));
               entityplayermp.func_70690_d(new PotionEffect(potion, 6000, 2));
            }
         }
      }

      if (!this.func_110175_bO()) {
         this.func_175449_a(new BlockPos(this), 16);
      }

   }
}
