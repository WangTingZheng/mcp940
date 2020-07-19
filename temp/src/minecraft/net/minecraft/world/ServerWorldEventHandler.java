package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class ServerWorldEventHandler implements IWorldEventListener {
   private final MinecraftServer field_72783_a;
   private final WorldServer field_72782_b;

   public ServerWorldEventHandler(MinecraftServer p_i1517_1_, WorldServer p_i1517_2_) {
      this.field_72783_a = p_i1517_1_;
      this.field_72782_b = p_i1517_2_;
   }

   public void func_180442_a(int p_180442_1_, boolean p_180442_2_, double p_180442_3_, double p_180442_5_, double p_180442_7_, double p_180442_9_, double p_180442_11_, double p_180442_13_, int... p_180442_15_) {
   }

   public void func_190570_a(int p_190570_1_, boolean p_190570_2_, boolean p_190570_3_, double p_190570_4_, double p_190570_6_, double p_190570_8_, double p_190570_10_, double p_190570_12_, double p_190570_14_, int... p_190570_16_) {
   }

   public void func_72703_a(Entity p_72703_1_) {
      this.field_72782_b.func_73039_n().func_72786_a(p_72703_1_);
      if (p_72703_1_ instanceof EntityPlayerMP) {
         this.field_72782_b.field_73011_w.func_186061_a((EntityPlayerMP)p_72703_1_);
      }

   }

   public void func_72709_b(Entity p_72709_1_) {
      this.field_72782_b.func_73039_n().func_72790_b(p_72709_1_);
      this.field_72782_b.func_96441_U().func_181140_a(p_72709_1_);
      if (p_72709_1_ instanceof EntityPlayerMP) {
         this.field_72782_b.field_73011_w.func_186062_b((EntityPlayerMP)p_72709_1_);
      }

   }

   public void func_184375_a(@Nullable EntityPlayer p_184375_1_, SoundEvent p_184375_2_, SoundCategory p_184375_3_, double p_184375_4_, double p_184375_6_, double p_184375_8_, float p_184375_10_, float p_184375_11_) {
      this.field_72783_a.func_184103_al().func_148543_a(p_184375_1_, p_184375_4_, p_184375_6_, p_184375_8_, p_184375_10_ > 1.0F ? (double)(16.0F * p_184375_10_) : 16.0D, this.field_72782_b.field_73011_w.func_186058_p().func_186068_a(), new SPacketSoundEffect(p_184375_2_, p_184375_3_, p_184375_4_, p_184375_6_, p_184375_8_, p_184375_10_, p_184375_11_));
   }

   public void func_147585_a(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {
   }

   public void func_184376_a(World p_184376_1_, BlockPos p_184376_2_, IBlockState p_184376_3_, IBlockState p_184376_4_, int p_184376_5_) {
      this.field_72782_b.func_184164_w().func_180244_a(p_184376_2_);
   }

   public void func_174959_b(BlockPos p_174959_1_) {
   }

   public void func_184377_a(SoundEvent p_184377_1_, BlockPos p_184377_2_) {
   }

   public void func_180439_a(EntityPlayer p_180439_1_, int p_180439_2_, BlockPos p_180439_3_, int p_180439_4_) {
      this.field_72783_a.func_184103_al().func_148543_a(p_180439_1_, (double)p_180439_3_.func_177958_n(), (double)p_180439_3_.func_177956_o(), (double)p_180439_3_.func_177952_p(), 64.0D, this.field_72782_b.field_73011_w.func_186058_p().func_186068_a(), new SPacketEffect(p_180439_2_, p_180439_3_, p_180439_4_, false));
   }

   public void func_180440_a(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
      this.field_72783_a.func_184103_al().func_148540_a(new SPacketEffect(p_180440_1_, p_180440_2_, p_180440_3_, true));
   }

   public void func_180441_b(int p_180441_1_, BlockPos p_180441_2_, int p_180441_3_) {
      for(EntityPlayerMP entityplayermp : this.field_72783_a.func_184103_al().func_181057_v()) {
         if (entityplayermp != null && entityplayermp.field_70170_p == this.field_72782_b && entityplayermp.func_145782_y() != p_180441_1_) {
            double d0 = (double)p_180441_2_.func_177958_n() - entityplayermp.field_70165_t;
            double d1 = (double)p_180441_2_.func_177956_o() - entityplayermp.field_70163_u;
            double d2 = (double)p_180441_2_.func_177952_p() - entityplayermp.field_70161_v;
            if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D) {
               entityplayermp.field_71135_a.func_147359_a(new SPacketBlockBreakAnim(p_180441_1_, p_180441_2_, p_180441_3_));
            }
         }
      }

   }
}
