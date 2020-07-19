package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandClone extends CommandBase {
   public String func_71517_b() {
      return "clone";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.clone.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 9) {
         throw new WrongUsageException("commands.clone.usage", new Object[0]);
      } else {
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, false);
         BlockPos blockpos1 = func_175757_a(p_184881_2_, p_184881_3_, 3, false);
         BlockPos blockpos2 = func_175757_a(p_184881_2_, p_184881_3_, 6, false);
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(blockpos, blockpos1);
         StructureBoundingBox structureboundingbox1 = new StructureBoundingBox(blockpos2, blockpos2.func_177971_a(structureboundingbox.func_175896_b()));
         int i = structureboundingbox.func_78883_b() * structureboundingbox.func_78882_c() * structureboundingbox.func_78880_d();
         if (i > 32768) {
            throw new CommandException("commands.clone.tooManyBlocks", new Object[]{i, Integer.valueOf(32768)});
         } else {
            boolean flag = false;
            Block block = null;
            Predicate<IBlockState> predicate = null;
            if ((p_184881_3_.length < 11 || !"force".equals(p_184881_3_[10]) && !"move".equals(p_184881_3_[10])) && structureboundingbox.func_78884_a(structureboundingbox1)) {
               throw new CommandException("commands.clone.noOverlap", new Object[0]);
            } else {
               if (p_184881_3_.length >= 11 && "move".equals(p_184881_3_[10])) {
                  flag = true;
               }

               if (structureboundingbox.field_78895_b >= 0 && structureboundingbox.field_78894_e < 256 && structureboundingbox1.field_78895_b >= 0 && structureboundingbox1.field_78894_e < 256) {
                  World world = p_184881_2_.func_130014_f_();
                  if (world.func_175711_a(structureboundingbox) && world.func_175711_a(structureboundingbox1)) {
                     boolean flag1 = false;
                     if (p_184881_3_.length >= 10) {
                        if ("masked".equals(p_184881_3_[9])) {
                           flag1 = true;
                        } else if ("filtered".equals(p_184881_3_[9])) {
                           if (p_184881_3_.length < 12) {
                              throw new WrongUsageException("commands.clone.usage", new Object[0]);
                           }

                           block = func_147180_g(p_184881_2_, p_184881_3_[11]);
                           if (p_184881_3_.length >= 13) {
                              predicate = func_190791_b(block, p_184881_3_[12]);
                           }
                        }
                     }

                     List<CommandClone.StaticCloneData> list = Lists.<CommandClone.StaticCloneData>newArrayList();
                     List<CommandClone.StaticCloneData> list1 = Lists.<CommandClone.StaticCloneData>newArrayList();
                     List<CommandClone.StaticCloneData> list2 = Lists.<CommandClone.StaticCloneData>newArrayList();
                     Deque<BlockPos> deque = Lists.<BlockPos>newLinkedList();
                     BlockPos blockpos3 = new BlockPos(structureboundingbox1.field_78897_a - structureboundingbox.field_78897_a, structureboundingbox1.field_78895_b - structureboundingbox.field_78895_b, structureboundingbox1.field_78896_c - structureboundingbox.field_78896_c);

                     for(int j = structureboundingbox.field_78896_c; j <= structureboundingbox.field_78892_f; ++j) {
                        for(int k = structureboundingbox.field_78895_b; k <= structureboundingbox.field_78894_e; ++k) {
                           for(int l = structureboundingbox.field_78897_a; l <= structureboundingbox.field_78893_d; ++l) {
                              BlockPos blockpos4 = new BlockPos(l, k, j);
                              BlockPos blockpos5 = blockpos4.func_177971_a(blockpos3);
                              IBlockState iblockstate = world.func_180495_p(blockpos4);
                              if ((!flag1 || iblockstate.func_177230_c() != Blocks.field_150350_a) && (block == null || iblockstate.func_177230_c() == block && (predicate == null || predicate.apply(iblockstate)))) {
                                 TileEntity tileentity = world.func_175625_s(blockpos4);
                                 if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = tileentity.func_189515_b(new NBTTagCompound());
                                    list1.add(new CommandClone.StaticCloneData(blockpos5, iblockstate, nbttagcompound));
                                    deque.addLast(blockpos4);
                                 } else if (!iblockstate.func_185913_b() && !iblockstate.func_185917_h()) {
                                    list2.add(new CommandClone.StaticCloneData(blockpos5, iblockstate, (NBTTagCompound)null));
                                    deque.addFirst(blockpos4);
                                 } else {
                                    list.add(new CommandClone.StaticCloneData(blockpos5, iblockstate, (NBTTagCompound)null));
                                    deque.addLast(blockpos4);
                                 }
                              }
                           }
                        }
                     }

                     if (flag) {
                        for(BlockPos blockpos6 : deque) {
                           TileEntity tileentity1 = world.func_175625_s(blockpos6);
                           if (tileentity1 instanceof IInventory) {
                              ((IInventory)tileentity1).func_174888_l();
                           }

                           world.func_180501_a(blockpos6, Blocks.field_180401_cv.func_176223_P(), 2);
                        }

                        for(BlockPos blockpos7 : deque) {
                           world.func_180501_a(blockpos7, Blocks.field_150350_a.func_176223_P(), 3);
                        }
                     }

                     List<CommandClone.StaticCloneData> list3 = Lists.<CommandClone.StaticCloneData>newArrayList();
                     list3.addAll(list);
                     list3.addAll(list1);
                     list3.addAll(list2);
                     List<CommandClone.StaticCloneData> list4 = Lists.<CommandClone.StaticCloneData>reverse(list3);

                     for(CommandClone.StaticCloneData commandclone$staticclonedata : list4) {
                        TileEntity tileentity2 = world.func_175625_s(commandclone$staticclonedata.field_179537_a);
                        if (tileentity2 instanceof IInventory) {
                           ((IInventory)tileentity2).func_174888_l();
                        }

                        world.func_180501_a(commandclone$staticclonedata.field_179537_a, Blocks.field_180401_cv.func_176223_P(), 2);
                     }

                     i = 0;

                     for(CommandClone.StaticCloneData commandclone$staticclonedata1 : list3) {
                        if (world.func_180501_a(commandclone$staticclonedata1.field_179537_a, commandclone$staticclonedata1.field_179535_b, 2)) {
                           ++i;
                        }
                     }

                     for(CommandClone.StaticCloneData commandclone$staticclonedata2 : list1) {
                        TileEntity tileentity3 = world.func_175625_s(commandclone$staticclonedata2.field_179537_a);
                        if (commandclone$staticclonedata2.field_184953_c != null && tileentity3 != null) {
                           commandclone$staticclonedata2.field_184953_c.func_74768_a("x", commandclone$staticclonedata2.field_179537_a.func_177958_n());
                           commandclone$staticclonedata2.field_184953_c.func_74768_a("y", commandclone$staticclonedata2.field_179537_a.func_177956_o());
                           commandclone$staticclonedata2.field_184953_c.func_74768_a("z", commandclone$staticclonedata2.field_179537_a.func_177952_p());
                           tileentity3.func_145839_a(commandclone$staticclonedata2.field_184953_c);
                           tileentity3.func_70296_d();
                        }

                        world.func_180501_a(commandclone$staticclonedata2.field_179537_a, commandclone$staticclonedata2.field_179535_b, 2);
                     }

                     for(CommandClone.StaticCloneData commandclone$staticclonedata3 : list4) {
                        world.func_175722_b(commandclone$staticclonedata3.field_179537_a, commandclone$staticclonedata3.field_179535_b.func_177230_c(), false);
                     }

                     List<NextTickListEntry> list5 = world.func_175712_a(structureboundingbox, false);
                     if (list5 != null) {
                        for(NextTickListEntry nextticklistentry : list5) {
                           if (structureboundingbox.func_175898_b(nextticklistentry.field_180282_a)) {
                              BlockPos blockpos8 = nextticklistentry.field_180282_a.func_177971_a(blockpos3);
                              world.func_180497_b(blockpos8, nextticklistentry.func_151351_a(), (int)(nextticklistentry.field_77180_e - world.func_72912_H().func_82573_f()), nextticklistentry.field_82754_f);
                           }
                        }
                     }

                     if (i <= 0) {
                        throw new CommandException("commands.clone.failed", new Object[0]);
                     } else {
                        p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, i);
                        func_152373_a(p_184881_2_, this, "commands.clone.success", new Object[]{i});
                     }
                  } else {
                     throw new CommandException("commands.clone.outOfWorld", new Object[0]);
                  }
               } else {
                  throw new CommandException("commands.clone.outOfWorld", new Object[0]);
               }
            }
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length > 0 && p_184883_3_.length <= 3) {
         return func_175771_a(p_184883_3_, 0, p_184883_4_);
      } else if (p_184883_3_.length > 3 && p_184883_3_.length <= 6) {
         return func_175771_a(p_184883_3_, 3, p_184883_4_);
      } else if (p_184883_3_.length > 6 && p_184883_3_.length <= 9) {
         return func_175771_a(p_184883_3_, 6, p_184883_4_);
      } else if (p_184883_3_.length == 10) {
         return func_71530_a(p_184883_3_, new String[]{"replace", "masked", "filtered"});
      } else if (p_184883_3_.length == 11) {
         return func_71530_a(p_184883_3_, new String[]{"normal", "force", "move"});
      } else {
         return p_184883_3_.length == 12 && "filtered".equals(p_184883_3_[9]) ? func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b()) : Collections.emptyList();
      }
   }

   static class StaticCloneData {
      public final BlockPos field_179537_a;
      public final IBlockState field_179535_b;
      public final NBTTagCompound field_184953_c;

      public StaticCloneData(BlockPos p_i46037_1_, IBlockState p_i46037_2_, NBTTagCompound p_i46037_3_) {
         this.field_179537_a = p_i46037_1_;
         this.field_179535_b = p_i46037_2_;
         this.field_184953_c = p_i46037_3_;
      }
   }
}
