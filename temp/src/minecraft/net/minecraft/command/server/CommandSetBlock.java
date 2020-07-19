package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandSetBlock extends CommandBase {
   public String func_71517_b() {
      return "setblock";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.setblock.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 4) {
         throw new WrongUsageException("commands.setblock.usage", new Object[0]);
      } else {
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, false);
         Block block = CommandBase.func_147180_g(p_184881_2_, p_184881_3_[3]);
         IBlockState iblockstate;
         if (p_184881_3_.length >= 5) {
            iblockstate = func_190794_a(block, p_184881_3_[4]);
         } else {
            iblockstate = block.func_176223_P();
         }

         World world = p_184881_2_.func_130014_f_();
         if (!world.func_175667_e(blockpos)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
         } else {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;
            if (p_184881_3_.length >= 7 && block.func_149716_u()) {
               String s = func_180529_a(p_184881_3_, 6);

               try {
                  nbttagcompound = JsonToNBT.func_180713_a(s);
                  flag = true;
               } catch (NBTException nbtexception) {
                  throw new CommandException("commands.setblock.tagError", new Object[]{nbtexception.getMessage()});
               }
            }

            if (p_184881_3_.length >= 6) {
               if ("destroy".equals(p_184881_3_[5])) {
                  world.func_175655_b(blockpos, true);
                  if (block == Blocks.field_150350_a) {
                     func_152373_a(p_184881_2_, this, "commands.setblock.success", new Object[0]);
                     return;
                  }
               } else if ("keep".equals(p_184881_3_[5]) && !world.func_175623_d(blockpos)) {
                  throw new CommandException("commands.setblock.noChange", new Object[0]);
               }
            }

            TileEntity tileentity1 = world.func_175625_s(blockpos);
            if (tileentity1 != null && tileentity1 instanceof IInventory) {
               ((IInventory)tileentity1).func_174888_l();
            }

            if (!world.func_180501_a(blockpos, iblockstate, 2)) {
               throw new CommandException("commands.setblock.noChange", new Object[0]);
            } else {
               if (flag) {
                  TileEntity tileentity = world.func_175625_s(blockpos);
                  if (tileentity != null) {
                     nbttagcompound.func_74768_a("x", blockpos.func_177958_n());
                     nbttagcompound.func_74768_a("y", blockpos.func_177956_o());
                     nbttagcompound.func_74768_a("z", blockpos.func_177952_p());
                     tileentity.func_145839_a(nbttagcompound);
                  }
               }

               world.func_175722_b(blockpos, iblockstate.func_177230_c(), false);
               p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
               func_152373_a(p_184881_2_, this, "commands.setblock.success", new Object[0]);
            }
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length > 0 && p_184883_3_.length <= 3) {
         return func_175771_a(p_184883_3_, 0, p_184883_4_);
      } else if (p_184883_3_.length == 4) {
         return func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b());
      } else {
         return p_184883_3_.length == 6 ? func_71530_a(p_184883_3_, new String[]{"replace", "destroy", "keep"}) : Collections.emptyList();
      }
   }
}
