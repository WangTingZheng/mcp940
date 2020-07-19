package net.minecraft.command.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandTestFor extends CommandBase {
   public String func_71517_b() {
      return "testfor";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.testfor.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.testfor.usage", new Object[0]);
      } else {
         Entity entity = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         NBTTagCompound nbttagcompound = null;
         if (p_184881_3_.length >= 2) {
            try {
               nbttagcompound = JsonToNBT.func_180713_a(func_180529_a(p_184881_3_, 1));
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.testfor.tagError", new Object[]{nbtexception.getMessage()});
            }
         }

         if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = func_184887_a(entity);
            if (!NBTUtil.func_181123_a(nbttagcompound, nbttagcompound1, true)) {
               throw new CommandException("commands.testfor.failure", new Object[]{entity.func_70005_c_()});
            }
         }

         func_152373_a(p_184881_2_, this, "commands.testfor.success", new Object[]{entity.func_70005_c_()});
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length == 1 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
   }
}
