package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class CommandGive extends CommandBase {
   public String func_71517_b() {
      return "give";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.give.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.give.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[0]);
         Item item = func_147179_f(p_184881_2_, p_184881_3_[1]);
         int i = p_184881_3_.length >= 3 ? func_175764_a(p_184881_3_[2], 1, item.func_77639_j()) : 1;
         int j = p_184881_3_.length >= 4 ? func_175755_a(p_184881_3_[3]) : 0;
         ItemStack itemstack = new ItemStack(item, i, j);
         if (p_184881_3_.length >= 5) {
            String s = func_180529_a(p_184881_3_, 4);

            try {
               itemstack.func_77982_d(JsonToNBT.func_180713_a(s));
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.give.tagError", new Object[]{nbtexception.getMessage()});
            }
         }

         boolean flag = entityplayer.field_71071_by.func_70441_a(itemstack);
         if (flag) {
            entityplayer.field_70170_p.func_184148_a((EntityPlayer)null, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, SoundEvents.field_187638_cR, SoundCategory.PLAYERS, 0.2F, ((entityplayer.func_70681_au().nextFloat() - entityplayer.func_70681_au().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.field_71069_bz.func_75142_b();
         }

         if (flag && itemstack.func_190926_b()) {
            itemstack.func_190920_e(1);
            p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, i);
            EntityItem entityitem1 = entityplayer.func_71019_a(itemstack, false);
            if (entityitem1 != null) {
               entityitem1.func_174870_v();
            }
         } else {
            p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, i - itemstack.func_190916_E());
            EntityItem entityitem = entityplayer.func_71019_a(itemstack, false);
            if (entityitem != null) {
               entityitem.func_174868_q();
               entityitem.func_145797_a(entityplayer.func_70005_c_());
            }
         }

         func_152373_a(p_184881_2_, this, "commands.give.success", new Object[]{itemstack.func_151000_E(), i, entityplayer.func_70005_c_()});
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
