package net.minecraft.pathfinding;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;

public class PathWorldListener implements IWorldEventListener {
   private final List<PathNavigate> field_189519_a = Lists.<PathNavigate>newArrayList();

   public void func_184376_a(World p_184376_1_, BlockPos p_184376_2_, IBlockState p_184376_3_, IBlockState p_184376_4_, int p_184376_5_) {
      if (this.func_184378_a(p_184376_1_, p_184376_2_, p_184376_3_, p_184376_4_)) {
         int i = 0;

         for(int j = this.field_189519_a.size(); i < j; ++i) {
            PathNavigate pathnavigate = this.field_189519_a.get(i);
            if (pathnavigate != null && !pathnavigate.func_188553_i()) {
               Path path = pathnavigate.func_75505_d();
               if (path != null && !path.func_75879_b() && path.func_75874_d() != 0) {
                  PathPoint pathpoint = pathnavigate.field_75514_c.func_75870_c();
                  double d0 = p_184376_2_.func_177954_c(((double)pathpoint.field_75839_a + pathnavigate.field_75515_a.field_70165_t) / 2.0D, ((double)pathpoint.field_75837_b + pathnavigate.field_75515_a.field_70163_u) / 2.0D, ((double)pathpoint.field_75838_c + pathnavigate.field_75515_a.field_70161_v) / 2.0D);
                  int k = (path.func_75874_d() - path.func_75873_e()) * (path.func_75874_d() - path.func_75873_e());
                  if (d0 < (double)k) {
                     pathnavigate.func_188554_j();
                  }
               }
            }
         }

      }
   }

   protected boolean func_184378_a(World p_184378_1_, BlockPos p_184378_2_, IBlockState p_184378_3_, IBlockState p_184378_4_) {
      AxisAlignedBB axisalignedbb = p_184378_3_.func_185890_d(p_184378_1_, p_184378_2_);
      AxisAlignedBB axisalignedbb1 = p_184378_4_.func_185890_d(p_184378_1_, p_184378_2_);
      return axisalignedbb != axisalignedbb1 && (axisalignedbb == null || !axisalignedbb.equals(axisalignedbb1));
   }

   public void func_174959_b(BlockPos p_174959_1_) {
   }

   public void func_147585_a(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {
   }

   public void func_184375_a(@Nullable EntityPlayer p_184375_1_, SoundEvent p_184375_2_, SoundCategory p_184375_3_, double p_184375_4_, double p_184375_6_, double p_184375_8_, float p_184375_10_, float p_184375_11_) {
   }

   public void func_180442_a(int p_180442_1_, boolean p_180442_2_, double p_180442_3_, double p_180442_5_, double p_180442_7_, double p_180442_9_, double p_180442_11_, double p_180442_13_, int... p_180442_15_) {
   }

   public void func_190570_a(int p_190570_1_, boolean p_190570_2_, boolean p_190570_3_, double p_190570_4_, double p_190570_6_, double p_190570_8_, double p_190570_10_, double p_190570_12_, double p_190570_14_, int... p_190570_16_) {
   }

   public void func_72703_a(Entity p_72703_1_) {
      if (p_72703_1_ instanceof EntityLiving) {
         this.field_189519_a.add(((EntityLiving)p_72703_1_).func_70661_as());
      }

   }

   public void func_72709_b(Entity p_72709_1_) {
      if (p_72709_1_ instanceof EntityLiving) {
         this.field_189519_a.remove(((EntityLiving)p_72709_1_).func_70661_as());
      }

   }

   public void func_184377_a(SoundEvent p_184377_1_, BlockPos p_184377_2_) {
   }

   public void func_180440_a(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
   }

   public void func_180439_a(EntityPlayer p_180439_1_, int p_180439_2_, BlockPos p_180439_3_, int p_180439_4_) {
   }

   public void func_180441_b(int p_180441_1_, BlockPos p_180441_2_, int p_180441_3_) {
   }
}
