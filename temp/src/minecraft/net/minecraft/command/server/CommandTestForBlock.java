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
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandTestForBlock extends CommandBase {
   public String func_71517_b() {
      return "testforblock";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.testforblock.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 4) {
         throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
      } else {
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, false);
         Block block = func_147180_g(p_184881_2_, p_184881_3_[3]);
         if (block == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[]{p_184881_3_[3]});
         } else {
            World world = p_184881_2_.func_130014_f_();
            if (!world.func_175667_e(blockpos)) {
               throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
            } else {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               boolean flag = false;
               if (p_184881_3_.length >= 6 && block.func_149716_u()) {
                  String s = func_180529_a(p_184881_3_, 5);

                  try {
                     nbttagcompound = JsonToNBT.func_180713_a(s);
                     flag = true;
                  } catch (NBTException nbtexception) {
                     throw new CommandException("commands.setblock.tagError", new Object[]{nbtexception.getMessage()});
                  }
               }

               IBlockState iblockstate = world.func_180495_p(blockpos);
               Block block1 = iblockstate.func_177230_c();
               if (block1 != block) {
                  throw new CommandException("commands.testforblock.failed.tile", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p(), block1.func_149732_F(), block.func_149732_F()});
               } else if (p_184881_3_.length >= 5 && !CommandBase.func_190791_b(block, p_184881_3_[4]).apply(iblockstate)) {
                  try {
                     int i = iblockstate.func_177230_c().func_176201_c(iblockstate);
                     throw new CommandException("commands.testforblock.failed.data", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p(), i, Integer.parseInt(p_184881_3_[4])});
                  } catch (NumberFormatException var13) {
                     throw new CommandException("commands.testforblock.failed.data", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p(), iblockstate.toString(), p_184881_3_[4]});
                  }
               } else {
                  if (flag) {
                     TileEntity tileentity = world.func_175625_s(blockpos);
                     if (tileentity == null) {
                        throw new CommandException("commands.testforblock.failed.tileEntity", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
                     }

                     NBTTagCompound nbttagcompound1 = tileentity.func_189515_b(new NBTTagCompound());
                     if (!NBTUtil.func_181123_a(nbttagcompound, nbttagcompound1, true)) {
                        throw new CommandException("commands.testforblock.failed.nbt", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
                     }
                  }

                  p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
                  func_152373_a(p_184881_2_, this, "commands.testforblock.success", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
               }
            }
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length > 0 && p_184883_3_.length <= 3) {
         return func_175771_a(p_184883_3_, 0, p_184883_4_);
      } else {
         return p_184883_3_.length == 4 ? func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b()) : Collections.emptyList();
      }
   }
}
