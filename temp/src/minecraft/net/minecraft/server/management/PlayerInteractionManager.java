package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PlayerInteractionManager {
   public World field_73092_a;
   public EntityPlayerMP field_73090_b;
   private GameType field_73091_c = GameType.NOT_SET;
   private boolean field_73088_d;
   private int field_73089_e;
   private BlockPos field_180240_f = BlockPos.field_177992_a;
   private int field_73100_i;
   private boolean field_73097_j;
   private BlockPos field_180241_i = BlockPos.field_177992_a;
   private int field_73093_n;
   private int field_73094_o = -1;

   public PlayerInteractionManager(World p_i1524_1_) {
      this.field_73092_a = p_i1524_1_;
   }

   public void func_73076_a(GameType p_73076_1_) {
      this.field_73091_c = p_73076_1_;
      p_73076_1_.func_77147_a(this.field_73090_b.field_71075_bZ);
      this.field_73090_b.func_71016_p();
      this.field_73090_b.field_71133_b.func_184103_al().func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[]{this.field_73090_b}));
      this.field_73092_a.func_72854_c();
   }

   public GameType func_73081_b() {
      return this.field_73091_c;
   }

   public boolean func_180239_c() {
      return this.field_73091_c.func_77144_e();
   }

   public boolean func_73083_d() {
      return this.field_73091_c.func_77145_d();
   }

   public void func_73077_b(GameType p_73077_1_) {
      if (this.field_73091_c == GameType.NOT_SET) {
         this.field_73091_c = p_73077_1_;
      }

      this.func_73076_a(this.field_73091_c);
   }

   public void func_73075_a() {
      ++this.field_73100_i;
      if (this.field_73097_j) {
         int i = this.field_73100_i - this.field_73093_n;
         IBlockState iblockstate = this.field_73092_a.func_180495_p(this.field_180241_i);
         if (iblockstate.func_185904_a() == Material.field_151579_a) {
            this.field_73097_j = false;
         } else {
            float f = iblockstate.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, this.field_180241_i) * (float)(i + 1);
            int j = (int)(f * 10.0F);
            if (j != this.field_73094_o) {
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180241_i, j);
               this.field_73094_o = j;
            }

            if (f >= 1.0F) {
               this.field_73097_j = false;
               this.func_180237_b(this.field_180241_i);
            }
         }
      } else if (this.field_73088_d) {
         IBlockState iblockstate1 = this.field_73092_a.func_180495_p(this.field_180240_f);
         if (iblockstate1.func_185904_a() == Material.field_151579_a) {
            this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, -1);
            this.field_73094_o = -1;
            this.field_73088_d = false;
         } else {
            int k = this.field_73100_i - this.field_73089_e;
            float f1 = iblockstate1.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, this.field_180241_i) * (float)(k + 1);
            int l = (int)(f1 * 10.0F);
            if (l != this.field_73094_o) {
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, l);
               this.field_73094_o = l;
            }
         }
      }

   }

   public void func_180784_a(BlockPos p_180784_1_, EnumFacing p_180784_2_) {
      if (this.func_73083_d()) {
         if (!this.field_73092_a.func_175719_a((EntityPlayer)null, p_180784_1_, p_180784_2_)) {
            this.func_180237_b(p_180784_1_);
         }

      } else {
         IBlockState iblockstate = this.field_73092_a.func_180495_p(p_180784_1_);
         Block block = iblockstate.func_177230_c();
         if (this.field_73091_c.func_82752_c()) {
            if (this.field_73091_c == GameType.SPECTATOR) {
               return;
            }

            if (!this.field_73090_b.func_175142_cm()) {
               ItemStack itemstack = this.field_73090_b.func_184614_ca();
               if (itemstack.func_190926_b()) {
                  return;
               }

               if (!itemstack.func_179544_c(block)) {
                  return;
               }
            }
         }

         this.field_73092_a.func_175719_a((EntityPlayer)null, p_180784_1_, p_180784_2_);
         this.field_73089_e = this.field_73100_i;
         float f = 1.0F;
         if (iblockstate.func_185904_a() != Material.field_151579_a) {
            block.func_180649_a(this.field_73092_a, p_180784_1_, this.field_73090_b);
            f = iblockstate.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, p_180784_1_);
         }

         if (iblockstate.func_185904_a() != Material.field_151579_a && f >= 1.0F) {
            this.func_180237_b(p_180784_1_);
         } else {
            this.field_73088_d = true;
            this.field_180240_f = p_180784_1_;
            int i = (int)(f * 10.0F);
            this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), p_180784_1_, i);
            this.field_73094_o = i;
         }

      }
   }

   public void func_180785_a(BlockPos p_180785_1_) {
      if (p_180785_1_.equals(this.field_180240_f)) {
         int i = this.field_73100_i - this.field_73089_e;
         IBlockState iblockstate = this.field_73092_a.func_180495_p(p_180785_1_);
         if (iblockstate.func_185904_a() != Material.field_151579_a) {
            float f = iblockstate.func_185903_a(this.field_73090_b, this.field_73090_b.field_70170_p, p_180785_1_) * (float)(i + 1);
            if (f >= 0.7F) {
               this.field_73088_d = false;
               this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), p_180785_1_, -1);
               this.func_180237_b(p_180785_1_);
            } else if (!this.field_73097_j) {
               this.field_73088_d = false;
               this.field_73097_j = true;
               this.field_180241_i = p_180785_1_;
               this.field_73093_n = this.field_73089_e;
            }
         }
      }

   }

   public void func_180238_e() {
      this.field_73088_d = false;
      this.field_73092_a.func_175715_c(this.field_73090_b.func_145782_y(), this.field_180240_f, -1);
   }

   private boolean func_180235_c(BlockPos p_180235_1_) {
      IBlockState iblockstate = this.field_73092_a.func_180495_p(p_180235_1_);
      iblockstate.func_177230_c().func_176208_a(this.field_73092_a, p_180235_1_, iblockstate, this.field_73090_b);
      boolean flag = this.field_73092_a.func_175698_g(p_180235_1_);
      if (flag) {
         iblockstate.func_177230_c().func_176206_d(this.field_73092_a, p_180235_1_, iblockstate);
      }

      return flag;
   }

   public boolean func_180237_b(BlockPos p_180237_1_) {
      if (this.field_73091_c.func_77145_d() && !this.field_73090_b.func_184614_ca().func_190926_b() && this.field_73090_b.func_184614_ca().func_77973_b() instanceof ItemSword) {
         return false;
      } else {
         IBlockState iblockstate = this.field_73092_a.func_180495_p(p_180237_1_);
         TileEntity tileentity = this.field_73092_a.func_175625_s(p_180237_1_);
         Block block = iblockstate.func_177230_c();
         if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.field_73090_b.func_189808_dh()) {
            this.field_73092_a.func_184138_a(p_180237_1_, iblockstate, iblockstate, 3);
            return false;
         } else {
            if (this.field_73091_c.func_82752_c()) {
               if (this.field_73091_c == GameType.SPECTATOR) {
                  return false;
               }

               if (!this.field_73090_b.func_175142_cm()) {
                  ItemStack itemstack = this.field_73090_b.func_184614_ca();
                  if (itemstack.func_190926_b()) {
                     return false;
                  }

                  if (!itemstack.func_179544_c(block)) {
                     return false;
                  }
               }
            }

            this.field_73092_a.func_180498_a(this.field_73090_b, 2001, p_180237_1_, Block.func_176210_f(iblockstate));
            boolean flag1 = this.func_180235_c(p_180237_1_);
            if (this.func_73083_d()) {
               this.field_73090_b.field_71135_a.func_147359_a(new SPacketBlockChange(this.field_73092_a, p_180237_1_));
            } else {
               ItemStack itemstack1 = this.field_73090_b.func_184614_ca();
               ItemStack itemstack2 = itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.func_77946_l();
               boolean flag = this.field_73090_b.func_184823_b(iblockstate);
               if (!itemstack1.func_190926_b()) {
                  itemstack1.func_179548_a(this.field_73092_a, iblockstate, p_180237_1_, this.field_73090_b);
               }

               if (flag1 && flag) {
                  iblockstate.func_177230_c().func_180657_a(this.field_73092_a, this.field_73090_b, p_180237_1_, iblockstate, tileentity, itemstack2);
               }
            }

            return flag1;
         }
      }
   }

   public EnumActionResult func_187250_a(EntityPlayer p_187250_1_, World p_187250_2_, ItemStack p_187250_3_, EnumHand p_187250_4_) {
      if (this.field_73091_c == GameType.SPECTATOR) {
         return EnumActionResult.PASS;
      } else if (p_187250_1_.func_184811_cZ().func_185141_a(p_187250_3_.func_77973_b())) {
         return EnumActionResult.PASS;
      } else {
         int i = p_187250_3_.func_190916_E();
         int j = p_187250_3_.func_77960_j();
         ActionResult<ItemStack> actionresult = p_187250_3_.func_77957_a(p_187250_2_, p_187250_1_, p_187250_4_);
         ItemStack itemstack = actionresult.func_188398_b();
         if (itemstack == p_187250_3_ && itemstack.func_190916_E() == i && itemstack.func_77988_m() <= 0 && itemstack.func_77960_j() == j) {
            return actionresult.func_188397_a();
         } else if (actionresult.func_188397_a() == EnumActionResult.FAIL && itemstack.func_77988_m() > 0 && !p_187250_1_.func_184587_cr()) {
            return actionresult.func_188397_a();
         } else {
            p_187250_1_.func_184611_a(p_187250_4_, itemstack);
            if (this.func_73083_d()) {
               itemstack.func_190920_e(i);
               if (itemstack.func_77984_f()) {
                  itemstack.func_77964_b(j);
               }
            }

            if (itemstack.func_190926_b()) {
               p_187250_1_.func_184611_a(p_187250_4_, ItemStack.field_190927_a);
            }

            if (!p_187250_1_.func_184587_cr()) {
               ((EntityPlayerMP)p_187250_1_).func_71120_a(p_187250_1_.field_71069_bz);
            }

            return actionresult.func_188397_a();
         }
      }
   }

   public EnumActionResult func_187251_a(EntityPlayer p_187251_1_, World p_187251_2_, ItemStack p_187251_3_, EnumHand p_187251_4_, BlockPos p_187251_5_, EnumFacing p_187251_6_, float p_187251_7_, float p_187251_8_, float p_187251_9_) {
      if (this.field_73091_c == GameType.SPECTATOR) {
         TileEntity tileentity = p_187251_2_.func_175625_s(p_187251_5_);
         if (tileentity instanceof ILockableContainer) {
            Block block1 = p_187251_2_.func_180495_p(p_187251_5_).func_177230_c();
            ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
            if (ilockablecontainer instanceof TileEntityChest && block1 instanceof BlockChest) {
               ilockablecontainer = ((BlockChest)block1).func_180676_d(p_187251_2_, p_187251_5_);
            }

            if (ilockablecontainer != null) {
               p_187251_1_.func_71007_a(ilockablecontainer);
               return EnumActionResult.SUCCESS;
            }
         } else if (tileentity instanceof IInventory) {
            p_187251_1_.func_71007_a((IInventory)tileentity);
            return EnumActionResult.SUCCESS;
         }

         return EnumActionResult.PASS;
      } else {
         if (!p_187251_1_.func_70093_af() || p_187251_1_.func_184614_ca().func_190926_b() && p_187251_1_.func_184592_cb().func_190926_b()) {
            IBlockState iblockstate = p_187251_2_.func_180495_p(p_187251_5_);
            if (iblockstate.func_177230_c().func_180639_a(p_187251_2_, p_187251_5_, iblockstate, p_187251_1_, p_187251_4_, p_187251_6_, p_187251_7_, p_187251_8_, p_187251_9_)) {
               return EnumActionResult.SUCCESS;
            }
         }

         if (p_187251_3_.func_190926_b()) {
            return EnumActionResult.PASS;
         } else if (p_187251_1_.func_184811_cZ().func_185141_a(p_187251_3_.func_77973_b())) {
            return EnumActionResult.PASS;
         } else {
            if (p_187251_3_.func_77973_b() instanceof ItemBlock && !p_187251_1_.func_189808_dh()) {
               Block block = ((ItemBlock)p_187251_3_.func_77973_b()).func_179223_d();
               if (block instanceof BlockCommandBlock || block instanceof BlockStructure) {
                  return EnumActionResult.FAIL;
               }
            }

            if (this.func_73083_d()) {
               int j = p_187251_3_.func_77960_j();
               int i = p_187251_3_.func_190916_E();
               EnumActionResult enumactionresult = p_187251_3_.func_179546_a(p_187251_1_, p_187251_2_, p_187251_5_, p_187251_4_, p_187251_6_, p_187251_7_, p_187251_8_, p_187251_9_);
               p_187251_3_.func_77964_b(j);
               p_187251_3_.func_190920_e(i);
               return enumactionresult;
            } else {
               return p_187251_3_.func_179546_a(p_187251_1_, p_187251_2_, p_187251_5_, p_187251_4_, p_187251_6_, p_187251_7_, p_187251_8_, p_187251_9_);
            }
         }
      }
   }

   public void func_73080_a(WorldServer p_73080_1_) {
      this.field_73092_a = p_73080_1_;
   }
}
