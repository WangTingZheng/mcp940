package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandEnchant extends CommandBase {
   public String func_71517_b() {
      return "enchant";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.enchant.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.enchant.usage", new Object[0]);
      } else {
         EntityLivingBase entitylivingbase = (EntityLivingBase)func_184884_a(p_184881_1_, p_184881_2_, p_184881_3_[0], EntityLivingBase.class);
         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);

         Enchantment enchantment;
         try {
            enchantment = Enchantment.func_185262_c(func_180528_a(p_184881_3_[1], 0));
         } catch (NumberInvalidException var12) {
            enchantment = Enchantment.func_180305_b(p_184881_3_[1]);
         }

         if (enchantment == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[]{p_184881_3_[1]});
         } else {
            int i = 1;
            ItemStack itemstack = entitylivingbase.func_184614_ca();
            if (itemstack.func_190926_b()) {
               throw new CommandException("commands.enchant.noItem", new Object[0]);
            } else if (!enchantment.func_92089_a(itemstack)) {
               throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
            } else {
               if (p_184881_3_.length >= 3) {
                  i = func_175764_a(p_184881_3_[2], enchantment.func_77319_d(), enchantment.func_77325_b());
               }

               if (itemstack.func_77942_o()) {
                  NBTTagList nbttaglist = itemstack.func_77986_q();

                  for(int j = 0; j < nbttaglist.func_74745_c(); ++j) {
                     int k = nbttaglist.func_150305_b(j).func_74765_d("id");
                     if (Enchantment.func_185262_c(k) != null) {
                        Enchantment enchantment1 = Enchantment.func_185262_c(k);
                        if (!enchantment.func_191560_c(enchantment1)) {
                           throw new CommandException("commands.enchant.cantCombine", new Object[]{enchantment.func_77316_c(i), enchantment1.func_77316_c(nbttaglist.func_150305_b(j).func_74765_d("lvl"))});
                        }
                     }
                  }
               }

               itemstack.func_77966_a(enchantment, i);
               func_152373_a(p_184881_2_, this, "commands.enchant.success", new Object[0]);
               p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 1);
            }
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length == 2 ? func_175762_a(p_184883_3_, Enchantment.field_185264_b.func_148742_b()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
