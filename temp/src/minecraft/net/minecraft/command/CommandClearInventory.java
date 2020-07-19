package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandClearInventory extends CommandBase {
   public String func_71517_b() {
      return "clear";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.clear.usage";
   }

   public int func_82362_a() {
      return 2;
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      EntityPlayerMP entityplayermp = p_184881_3_.length == 0 ? func_71521_c(p_184881_2_) : func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]);
      Item item = p_184881_3_.length >= 2 ? func_147179_f(p_184881_2_, p_184881_3_[1]) : null;
      int i = p_184881_3_.length >= 3 ? func_180528_a(p_184881_3_[2], -1) : -1;
      int j = p_184881_3_.length >= 4 ? func_180528_a(p_184881_3_[3], -1) : -1;
      NBTTagCompound nbttagcompound = null;
      if (p_184881_3_.length >= 5) {
         try {
            nbttagcompound = JsonToNBT.func_180713_a(func_180529_a(p_184881_3_, 4));
         } catch (NBTException nbtexception) {
            throw new CommandException("commands.clear.tagError", new Object[]{nbtexception.getMessage()});
         }
      }

      if (p_184881_3_.length >= 2 && item == null) {
         throw new CommandException("commands.clear.failure", new Object[]{entityplayermp.func_70005_c_()});
      } else {
         int k = entityplayermp.field_71071_by.func_174925_a(item, i, j, nbttagcompound);
         entityplayermp.field_71069_bz.func_75142_b();
         if (!entityplayermp.field_71075_bZ.field_75098_d) {
            entityplayermp.func_71113_k();
         }

         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, k);
         if (k == 0) {
            throw new CommandException("commands.clear.failure", new Object[]{entityplayermp.func_70005_c_()});
         } else {
            if (j == 0) {
               p_184881_2_.func_145747_a(new TextComponentTranslation("commands.clear.testing", new Object[]{entityplayermp.func_70005_c_(), k}));
            } else {
               func_152373_a(p_184881_2_, this, "commands.clear.success", new Object[]{entityplayermp.func_70005_c_(), k});
            }

         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length == 2 ? func_175762_a(p_184883_3_, Item.field_150901_e.func_148742_b()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
