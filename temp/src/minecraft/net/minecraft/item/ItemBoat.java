package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemBoat extends Item {
   private final EntityBoat.Type field_185057_a;

   public ItemBoat(EntityBoat.Type p_i46748_1_) {
      this.field_185057_a = p_i46748_1_;
      this.field_77777_bU = 1;
      this.func_77637_a(CreativeTabs.field_78029_e);
      this.func_77655_b("boat." + p_i46748_1_.func_184980_a());
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      float f = 1.0F;
      float f1 = p_77659_2_.field_70127_C + (p_77659_2_.field_70125_A - p_77659_2_.field_70127_C) * 1.0F;
      float f2 = p_77659_2_.field_70126_B + (p_77659_2_.field_70177_z - p_77659_2_.field_70126_B) * 1.0F;
      double d0 = p_77659_2_.field_70169_q + (p_77659_2_.field_70165_t - p_77659_2_.field_70169_q) * 1.0D;
      double d1 = p_77659_2_.field_70167_r + (p_77659_2_.field_70163_u - p_77659_2_.field_70167_r) * 1.0D + (double)p_77659_2_.func_70047_e();
      double d2 = p_77659_2_.field_70166_s + (p_77659_2_.field_70161_v - p_77659_2_.field_70166_s) * 1.0D;
      Vec3d vec3d = new Vec3d(d0, d1, d2);
      float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
      float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
      float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
      float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
      float f7 = f4 * f5;
      float f8 = f3 * f5;
      double d3 = 5.0D;
      Vec3d vec3d1 = vec3d.func_72441_c((double)f7 * 5.0D, (double)f6 * 5.0D, (double)f8 * 5.0D);
      RayTraceResult raytraceresult = p_77659_1_.func_72901_a(vec3d, vec3d1, true);
      if (raytraceresult == null) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         Vec3d vec3d2 = p_77659_2_.func_70676_i(1.0F);
         boolean flag = false;
         List<Entity> list = p_77659_1_.func_72839_b(p_77659_2_, p_77659_2_.func_174813_aQ().func_72321_a(vec3d2.field_72450_a * 5.0D, vec3d2.field_72448_b * 5.0D, vec3d2.field_72449_c * 5.0D).func_186662_g(1.0D));

         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity.func_70067_L()) {
               AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_186662_g((double)entity.func_70111_Y());
               if (axisalignedbb.func_72318_a(vec3d)) {
                  flag = true;
               }
            }
         }

         if (flag) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
         } else if (raytraceresult.field_72313_a != RayTraceResult.Type.BLOCK) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
         } else {
            Block block = p_77659_1_.func_180495_p(raytraceresult.func_178782_a()).func_177230_c();
            boolean flag1 = block == Blocks.field_150355_j || block == Blocks.field_150358_i;
            EntityBoat entityboat = new EntityBoat(p_77659_1_, raytraceresult.field_72307_f.field_72450_a, flag1 ? raytraceresult.field_72307_f.field_72448_b - 0.12D : raytraceresult.field_72307_f.field_72448_b, raytraceresult.field_72307_f.field_72449_c);
            entityboat.func_184458_a(this.field_185057_a);
            entityboat.field_70177_z = p_77659_2_.field_70177_z;
            if (!p_77659_1_.func_184144_a(entityboat, entityboat.func_174813_aQ().func_186662_g(-0.1D)).isEmpty()) {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            } else {
               if (!p_77659_1_.field_72995_K) {
                  p_77659_1_.func_72838_d(entityboat);
               }

               if (!p_77659_2_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }

               p_77659_2_.func_71029_a(StatList.func_188057_b(this));
               return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
         }
      }
   }
}
