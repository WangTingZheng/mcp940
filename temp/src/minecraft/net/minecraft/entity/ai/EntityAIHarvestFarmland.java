package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {
   private final EntityVillager field_179504_c;
   private boolean field_179502_d;
   private boolean field_179503_e;
   private int field_179501_f;

   public EntityAIHarvestFarmland(EntityVillager p_i45889_1_, double p_i45889_2_) {
      super(p_i45889_1_, p_i45889_2_, 16);
      this.field_179504_c = p_i45889_1_;
   }

   public boolean func_75250_a() {
      if (this.field_179496_a <= 0) {
         if (!this.field_179504_c.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         }

         this.field_179501_f = -1;
         this.field_179502_d = this.field_179504_c.func_175556_cs();
         this.field_179503_e = this.field_179504_c.func_175557_cr();
      }

      return super.func_75250_a();
   }

   public boolean func_75253_b() {
      return this.field_179501_f >= 0 && super.func_75253_b();
   }

   public void func_75246_d() {
      super.func_75246_d();
      this.field_179504_c.func_70671_ap().func_75650_a((double)this.field_179494_b.func_177958_n() + 0.5D, (double)(this.field_179494_b.func_177956_o() + 1), (double)this.field_179494_b.func_177952_p() + 0.5D, 10.0F, (float)this.field_179504_c.func_70646_bf());
      if (this.func_179487_f()) {
         World world = this.field_179504_c.field_70170_p;
         BlockPos blockpos = this.field_179494_b.func_177984_a();
         IBlockState iblockstate = world.func_180495_p(blockpos);
         Block block = iblockstate.func_177230_c();
         if (this.field_179501_f == 0 && block instanceof BlockCrops && ((BlockCrops)block).func_185525_y(iblockstate)) {
            world.func_175655_b(blockpos, true);
         } else if (this.field_179501_f == 1 && iblockstate.func_185904_a() == Material.field_151579_a) {
            InventoryBasic inventorybasic = this.field_179504_c.func_175551_co();

            for(int i = 0; i < inventorybasic.func_70302_i_(); ++i) {
               ItemStack itemstack = inventorybasic.func_70301_a(i);
               boolean flag = false;
               if (!itemstack.func_190926_b()) {
                  if (itemstack.func_77973_b() == Items.field_151014_N) {
                     world.func_180501_a(blockpos, Blocks.field_150464_aj.func_176223_P(), 3);
                     flag = true;
                  } else if (itemstack.func_77973_b() == Items.field_151174_bG) {
                     world.func_180501_a(blockpos, Blocks.field_150469_bN.func_176223_P(), 3);
                     flag = true;
                  } else if (itemstack.func_77973_b() == Items.field_151172_bF) {
                     world.func_180501_a(blockpos, Blocks.field_150459_bM.func_176223_P(), 3);
                     flag = true;
                  } else if (itemstack.func_77973_b() == Items.field_185163_cU) {
                     world.func_180501_a(blockpos, Blocks.field_185773_cZ.func_176223_P(), 3);
                     flag = true;
                  }
               }

               if (flag) {
                  itemstack.func_190918_g(1);
                  if (itemstack.func_190926_b()) {
                     inventorybasic.func_70299_a(i, ItemStack.field_190927_a);
                  }
                  break;
               }
            }
         }

         this.field_179501_f = -1;
         this.field_179496_a = 10;
      }

   }

   protected boolean func_179488_a(World p_179488_1_, BlockPos p_179488_2_) {
      Block block = p_179488_1_.func_180495_p(p_179488_2_).func_177230_c();
      if (block == Blocks.field_150458_ak) {
         p_179488_2_ = p_179488_2_.func_177984_a();
         IBlockState iblockstate = p_179488_1_.func_180495_p(p_179488_2_);
         block = iblockstate.func_177230_c();
         if (block instanceof BlockCrops && ((BlockCrops)block).func_185525_y(iblockstate) && this.field_179503_e && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
            this.field_179501_f = 0;
            return true;
         }

         if (iblockstate.func_185904_a() == Material.field_151579_a && this.field_179502_d && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
            this.field_179501_f = 1;
            return true;
         }
      }

      return false;
   }
}
