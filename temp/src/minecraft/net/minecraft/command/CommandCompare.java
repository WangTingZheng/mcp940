package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandCompare extends CommandBase {
   public String func_71517_b() {
      return "testforblocks";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.compare.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 9) {
         throw new WrongUsageException("commands.compare.usage", new Object[0]);
      } else {
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
         BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 0, false);
         BlockPos blockpos1 = func_175757_a(p_184881_2_, p_184881_3_, 3, false);
         BlockPos blockpos2 = func_175757_a(p_184881_2_, p_184881_3_, 6, false);
         StructureBoundingBox structureboundingbox = new StructureBoundingBox(blockpos, blockpos1);
         StructureBoundingBox structureboundingbox1 = new StructureBoundingBox(blockpos2, blockpos2.func_177971_a(structureboundingbox.func_175896_b()));
         int i = structureboundingbox.func_78883_b() * structureboundingbox.func_78882_c() * structureboundingbox.func_78880_d();
         if (i > 524288) {
            throw new CommandException("commands.compare.tooManyBlocks", new Object[]{i, 524288});
         } else if (structureboundingbox.field_78895_b >= 0 && structureboundingbox.field_78894_e < 256 && structureboundingbox1.field_78895_b >= 0 && structureboundingbox1.field_78894_e < 256) {
            World world = p_184881_2_.func_130014_f_();
            if (world.func_175711_a(structureboundingbox) && world.func_175711_a(structureboundingbox1)) {
               boolean flag = false;
               if (p_184881_3_.length > 9 && "masked".equals(p_184881_3_[9])) {
                  flag = true;
               }

               i = 0;
               BlockPos blockpos3 = new BlockPos(structureboundingbox1.field_78897_a - structureboundingbox.field_78897_a, structureboundingbox1.field_78895_b - structureboundingbox.field_78895_b, structureboundingbox1.field_78896_c - structureboundingbox.field_78896_c);
               BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
               BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

               for(int j = structureboundingbox.field_78896_c; j <= structureboundingbox.field_78892_f; ++j) {
                  for(int k = structureboundingbox.field_78895_b; k <= structureboundingbox.field_78894_e; ++k) {
                     for(int l = structureboundingbox.field_78897_a; l <= structureboundingbox.field_78893_d; ++l) {
                        blockpos$mutableblockpos.func_181079_c(l, k, j);
                        blockpos$mutableblockpos1.func_181079_c(l + blockpos3.func_177958_n(), k + blockpos3.func_177956_o(), j + blockpos3.func_177952_p());
                        boolean flag1 = false;
                        IBlockState iblockstate = world.func_180495_p(blockpos$mutableblockpos);
                        if (!flag || iblockstate.func_177230_c() != Blocks.field_150350_a) {
                           if (iblockstate == world.func_180495_p(blockpos$mutableblockpos1)) {
                              TileEntity tileentity = world.func_175625_s(blockpos$mutableblockpos);
                              TileEntity tileentity1 = world.func_175625_s(blockpos$mutableblockpos1);
                              if (tileentity != null && tileentity1 != null) {
                                 NBTTagCompound nbttagcompound = tileentity.func_189515_b(new NBTTagCompound());
                                 nbttagcompound.func_82580_o("x");
                                 nbttagcompound.func_82580_o("y");
                                 nbttagcompound.func_82580_o("z");
                                 NBTTagCompound nbttagcompound1 = tileentity1.func_189515_b(new NBTTagCompound());
                                 nbttagcompound1.func_82580_o("x");
                                 nbttagcompound1.func_82580_o("y");
                                 nbttagcompound1.func_82580_o("z");
                                 if (!nbttagcompound.equals(nbttagcompound1)) {
                                    flag1 = true;
                                 }
                              } else if (tileentity != null) {
                                 flag1 = true;
                              }
                           } else {
                              flag1 = true;
                           }

                           ++i;
                           if (flag1) {
                              throw new CommandException("commands.compare.failed", new Object[0]);
                           }
                        }
                     }
                  }
               }

               p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, i);
               func_152373_a(p_184881_2_, this, "commands.compare.success", new Object[]{i});
            } else {
               throw new CommandException("commands.compare.outOfWorld", new Object[0]);
            }
         } else {
            throw new CommandException("commands.compare.outOfWorld", new Object[0]);
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
      } else {
         return p_184883_3_.length == 10 ? func_71530_a(p_184883_3_, new String[]{"masked", "all"}) : Collections.emptyList();
      }
   }
}
