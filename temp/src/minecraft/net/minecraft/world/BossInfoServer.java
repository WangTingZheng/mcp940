package net.minecraft.world;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BossInfoServer extends BossInfo {
   private final Set<EntityPlayerMP> field_186762_h = Sets.<EntityPlayerMP>newHashSet();
   private final Set<EntityPlayerMP> field_186763_i;
   private boolean field_186764_j;

   public BossInfoServer(ITextComponent p_i46839_1_, BossInfo.Color p_i46839_2_, BossInfo.Overlay p_i46839_3_) {
      super(MathHelper.func_188210_a(), p_i46839_1_, p_i46839_2_, p_i46839_3_);
      this.field_186763_i = Collections.<EntityPlayerMP>unmodifiableSet(this.field_186762_h);
      this.field_186764_j = true;
   }

   public void func_186735_a(float p_186735_1_) {
      if (p_186735_1_ != this.field_186750_b) {
         super.func_186735_a(p_186735_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PCT);
      }

   }

   public void func_186745_a(BossInfo.Color p_186745_1_) {
      if (p_186745_1_ != this.field_186751_c) {
         super.func_186745_a(p_186745_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
      }

   }

   public void func_186746_a(BossInfo.Overlay p_186746_1_) {
      if (p_186746_1_ != this.field_186752_d) {
         super.func_186746_a(p_186746_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
      }

   }

   public BossInfo func_186741_a(boolean p_186741_1_) {
      if (p_186741_1_ != this.field_186753_e) {
         super.func_186741_a(p_186741_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   public BossInfo func_186742_b(boolean p_186742_1_) {
      if (p_186742_1_ != this.field_186754_f) {
         super.func_186742_b(p_186742_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   public BossInfo func_186743_c(boolean p_186743_1_) {
      if (p_186743_1_ != this.field_186755_g) {
         super.func_186743_c(p_186743_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   public void func_186739_a(ITextComponent p_186739_1_) {
      if (!Objects.equal(p_186739_1_, this.field_186749_a)) {
         super.func_186739_a(p_186739_1_);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_NAME);
      }

   }

   private void func_186759_a(SPacketUpdateBossInfo.Operation p_186759_1_) {
      if (this.field_186764_j) {
         SPacketUpdateBossInfo spacketupdatebossinfo = new SPacketUpdateBossInfo(p_186759_1_, this);

         for(EntityPlayerMP entityplayermp : this.field_186762_h) {
            entityplayermp.field_71135_a.func_147359_a(spacketupdatebossinfo);
         }
      }

   }

   public void func_186760_a(EntityPlayerMP p_186760_1_) {
      if (this.field_186762_h.add(p_186760_1_) && this.field_186764_j) {
         p_186760_1_.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, this));
      }

   }

   public void func_186761_b(EntityPlayerMP p_186761_1_) {
      if (this.field_186762_h.remove(p_186761_1_) && this.field_186764_j) {
         p_186761_1_.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, this));
      }

   }

   public void func_186758_d(boolean p_186758_1_) {
      if (p_186758_1_ != this.field_186764_j) {
         this.field_186764_j = p_186758_1_;

         for(EntityPlayerMP entityplayermp : this.field_186762_h) {
            entityplayermp.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(p_186758_1_ ? SPacketUpdateBossInfo.Operation.ADD : SPacketUpdateBossInfo.Operation.REMOVE, this));
         }
      }

   }

   public Collection<EntityPlayerMP> func_186757_c() {
      return this.field_186763_i;
   }
}
