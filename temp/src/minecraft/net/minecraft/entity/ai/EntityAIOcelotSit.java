package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
   private final EntityOcelot field_151493_a;

   public EntityAIOcelotSit(EntityOcelot p_i45315_1_, double p_i45315_2_) {
      super(p_i45315_1_, p_i45315_2_, 8);
      this.field_151493_a = p_i45315_1_;
   }

   public boolean func_75250_a() {
      return this.field_151493_a.func_70909_n() && !this.field_151493_a.func_70906_o() && super.func_75250_a();
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.field_151493_a.func_70907_r().func_75270_a(false);
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.field_151493_a.func_70904_g(false);
   }

   public void func_75246_d() {
      super.func_75246_d();
      this.field_151493_a.func_70907_r().func_75270_a(false);
      if (!this.func_179487_f()) {
         this.field_151493_a.func_70904_g(false);
      } else if (!this.field_151493_a.func_70906_o()) {
         this.field_151493_a.func_70904_g(true);
      }

   }

   protected boolean func_179488_a(World p_179488_1_, BlockPos p_179488_2_) {
      if (!p_179488_1_.func_175623_d(p_179488_2_.func_177984_a())) {
         return false;
      } else {
         IBlockState iblockstate = p_179488_1_.func_180495_p(p_179488_2_);
         Block block = iblockstate.func_177230_c();
         if (block == Blocks.field_150486_ae) {
            TileEntity tileentity = p_179488_1_.func_175625_s(p_179488_2_);
            if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).field_145987_o < 1) {
               return true;
            }
         } else {
            if (block == Blocks.field_150470_am) {
               return true;
            }

            if (block == Blocks.field_150324_C && iblockstate.func_177229_b(BlockBed.field_176472_a) != BlockBed.EnumPartType.HEAD) {
               return true;
            }
         }

         return false;
      }
   }
}
