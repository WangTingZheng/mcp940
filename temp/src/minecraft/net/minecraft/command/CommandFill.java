package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandFill extends CommandBase {
   public String func_71517_b() {
      return "fill";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.fill.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 7) {
         throw new WrongUsageException("commands.fill.usage", new Object[0]);
      } else {
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, false);
         BlockPos blockpos1 = func_175757_a(p_184881_2_, p_184881_3_, 3, false);
         Block block = CommandBase.func_147180_g(p_184881_2_, p_184881_3_[6]);
         IBlockState iblockstate;
         if (p_184881_3_.length >= 8) {
            iblockstate = func_190794_a(block, p_184881_3_[7]);
         } else {
            iblockstate = block.func_176223_P();
         }

         BlockPos blockpos2 = new BlockPos(Math.min(blockpos.func_177958_n(), blockpos1.func_177958_n()), Math.min(blockpos.func_177956_o(), blockpos1.func_177956_o()), Math.min(blockpos.func_177952_p(), blockpos1.func_177952_p()));
         BlockPos blockpos3 = new BlockPos(Math.max(blockpos.func_177958_n(), blockpos1.func_177958_n()), Math.max(blockpos.func_177956_o(), blockpos1.func_177956_o()), Math.max(blockpos.func_177952_p(), blockpos1.func_177952_p()));
         int i = (blockpos3.func_177958_n() - blockpos2.func_177958_n() + 1) * (blockpos3.func_177956_o() - blockpos2.func_177956_o() + 1) * (blockpos3.func_177952_p() - blockpos2.func_177952_p() + 1);
         if (i > 32768) {
            throw new CommandException("commands.fill.tooManyBlocks", new Object[]{i, Integer.valueOf(32768)});
         } else if (blockpos2.func_177956_o() >= 0 && blockpos3.func_177956_o() < 256) {
            World world = p_184881_2_.func_130014_f_();

            for(int j = blockpos2.func_177952_p(); j <= blockpos3.func_177952_p(); j += 16) {
               for(int k = blockpos2.func_177958_n(); k <= blockpos3.func_177958_n(); k += 16) {
                  if (!world.func_175667_e(new BlockPos(k, blockpos3.func_177956_o() - blockpos2.func_177956_o(), j))) {
                     throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                  }
               }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;
            if (p_184881_3_.length >= 10 && block.func_149716_u()) {
               String s = func_180529_a(p_184881_3_, 9);

               try {
                  nbttagcompound = JsonToNBT.func_180713_a(s);
                  flag = true;
               } catch (NBTException nbtexception) {
                  throw new CommandException("commands.fill.tagError", new Object[]{nbtexception.getMessage()});
               }
            }

            List<BlockPos> list = Lists.<BlockPos>newArrayList();
            i = 0;

            for(int l = blockpos2.func_177952_p(); l <= blockpos3.func_177952_p(); ++l) {
               for(int i1 = blockpos2.func_177956_o(); i1 <= blockpos3.func_177956_o(); ++i1) {
                  for(int j1 = blockpos2.func_177958_n(); j1 <= blockpos3.func_177958_n(); ++j1) {
                     BlockPos blockpos4 = new BlockPos(j1, i1, l);
                     if (p_184881_3_.length >= 9) {
                        if (!"outline".equals(p_184881_3_[8]) && !"hollow".equals(p_184881_3_[8])) {
                           if ("destroy".equals(p_184881_3_[8])) {
                              world.func_175655_b(blockpos4, true);
                           } else if ("keep".equals(p_184881_3_[8])) {
                              if (!world.func_175623_d(blockpos4)) {
                                 continue;
                              }
                           } else if ("replace".equals(p_184881_3_[8]) && !block.func_149716_u() && p_184881_3_.length > 9) {
                              Block block1 = CommandBase.func_147180_g(p_184881_2_, p_184881_3_[9]);
                              if (world.func_180495_p(blockpos4).func_177230_c() != block1 || p_184881_3_.length > 10 && !"-1".equals(p_184881_3_[10]) && !"*".equals(p_184881_3_[10]) && !CommandBase.func_190791_b(block1, p_184881_3_[10]).apply(world.func_180495_p(blockpos4))) {
                                 continue;
                              }
                           }
                        } else if (j1 != blockpos2.func_177958_n() && j1 != blockpos3.func_177958_n() && i1 != blockpos2.func_177956_o() && i1 != blockpos3.func_177956_o() && l != blockpos2.func_177952_p() && l != blockpos3.func_177952_p()) {
                           if ("hollow".equals(p_184881_3_[8])) {
                              world.func_180501_a(blockpos4, Blocks.field_150350_a.func_176223_P(), 2);
                              list.add(blockpos4);
                           }
                           continue;
                        }
                     }

                     TileEntity tileentity1 = world.func_175625_s(blockpos4);
                     if (tileentity1 != null && tileentity1 instanceof IInventory) {
                        ((IInventory)tileentity1).func_174888_l();
                     }

                     if (world.func_180501_a(blockpos4, iblockstate, 2)) {
                        list.add(blockpos4);
                        ++i;
                        if (flag) {
                           TileEntity tileentity = world.func_175625_s(blockpos4);
                           if (tileentity != null) {
                              nbttagcompound.func_74768_a("x", blockpos4.func_177958_n());
                              nbttagcompound.func_74768_a("y", blockpos4.func_177956_o());
                              nbttagcompound.func_74768_a("z", blockpos4.func_177952_p());
                              tileentity.func_145839_a(nbttagcompound);
                           }
                        }
                     }
                  }
               }
            }

            for(BlockPos blockpos5 : list) {
               Block block2 = world.func_180495_p(blockpos5).func_177230_c();
               world.func_175722_b(blockpos5, block2, false);
            }

            if (i <= 0) {
               throw new CommandException("commands.fill.failed", new Object[0]);
            } else {
               p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, i);
               func_152373_a(p_184881_2_, this, "commands.fill.success", new Object[]{i});
            }
         } else {
            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length > 0 && p_184883_3_.length <= 3) {
         return func_175771_a(p_184883_3_, 0, p_184883_4_);
      } else if (p_184883_3_.length > 3 && p_184883_3_.length <= 6) {
         return func_175771_a(p_184883_3_, 3, p_184883_4_);
      } else if (p_184883_3_.length == 7) {
         return func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b());
      } else if (p_184883_3_.length == 9) {
         return func_71530_a(p_184883_3_, new String[]{"replace", "destroy", "keep", "hollow", "outline"});
      } else {
         return p_184883_3_.length == 10 && "replace".equals(p_184883_3_[8]) ? func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b()) : Collections.emptyList();
      }
   }
}
