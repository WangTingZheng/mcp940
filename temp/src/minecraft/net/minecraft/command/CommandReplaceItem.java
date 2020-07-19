package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandReplaceItem extends CommandBase {
   private static final Map<String, Integer> field_175785_a = Maps.<String, Integer>newHashMap();

   public String func_71517_b() {
      return "replaceitem";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.replaceitem.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
      } else {
         boolean flag;
         if ("entity".equals(p_184881_3_[0])) {
            flag = false;
         } else {
            if (!"block".equals(p_184881_3_[0])) {
               throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }

            flag = true;
         }

         int i;
         if (flag) {
            if (p_184881_3_.length < 6) {
               throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }

            i = 4;
         } else {
            if (p_184881_3_.length < 4) {
               throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
            }

            i = 2;
         }

         String s = p_184881_3_[i];
         int j = this.func_175783_e(p_184881_3_[i++]);

         Item item;
         try {
            item = func_147179_f(p_184881_2_, p_184881_3_[i]);
         } catch (NumberInvalidException numberinvalidexception) {
            if (Block.func_149684_b(p_184881_3_[i]) != Blocks.field_150350_a) {
               throw numberinvalidexception;
            }

            item = null;
         }

         ++i;
         int k = p_184881_3_.length > i ? func_175764_a(p_184881_3_[i++], 1, item.func_77639_j()) : 1;
         int l = p_184881_3_.length > i ? func_175755_a(p_184881_3_[i++]) : 0;
         ItemStack itemstack = new ItemStack(item, k, l);
         if (p_184881_3_.length > i) {
            String s1 = func_180529_a(p_184881_3_, i);

            try {
               itemstack.func_77982_d(JsonToNBT.func_180713_a(s1));
            } catch (NBTException nbtexception) {
               throw new CommandException("commands.replaceitem.tagError", new Object[]{nbtexception.getMessage()});
            }
         }

         if (flag) {
            p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            BlockPos blockpos = func_175757_a(p_184881_2_, p_184881_3_, 1, false);
            World world = p_184881_2_.func_130014_f_();
            TileEntity tileentity = world.func_175625_s(blockpos);
            if (tileentity == null || !(tileentity instanceof IInventory)) {
               throw new CommandException("commands.replaceitem.noContainer", new Object[]{blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()});
            }

            IInventory iinventory = (IInventory)tileentity;
            if (j >= 0 && j < iinventory.func_70302_i_()) {
               iinventory.func_70299_a(j, itemstack);
            }
         } else {
            Entity entity = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[1]);
            p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            if (entity instanceof EntityPlayer) {
               ((EntityPlayer)entity).field_71069_bz.func_75142_b();
            }

            if (!entity.func_174820_d(j, itemstack)) {
               throw new CommandException("commands.replaceitem.failed", new Object[]{s, k, itemstack.func_190926_b() ? "Air" : itemstack.func_151000_E()});
            }

            if (entity instanceof EntityPlayer) {
               ((EntityPlayer)entity).field_71069_bz.func_75142_b();
            }
         }

         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, k);
         func_152373_a(p_184881_2_, this, "commands.replaceitem.success", new Object[]{s, k, itemstack.func_190926_b() ? "Air" : itemstack.func_151000_E()});
      }
   }

   private int func_175783_e(String p_175783_1_) throws CommandException {
      if (!field_175785_a.containsKey(p_175783_1_)) {
         throw new CommandException("commands.generic.parameter.invalid", new Object[]{p_175783_1_});
      } else {
         return ((Integer)field_175785_a.get(p_175783_1_)).intValue();
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"entity", "block"});
      } else if (p_184883_3_.length == 2 && "entity".equals(p_184883_3_[0])) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else if (p_184883_3_.length >= 2 && p_184883_3_.length <= 4 && "block".equals(p_184883_3_[0])) {
         return func_175771_a(p_184883_3_, 1, p_184883_4_);
      } else if ((p_184883_3_.length != 3 || !"entity".equals(p_184883_3_[0])) && (p_184883_3_.length != 5 || !"block".equals(p_184883_3_[0]))) {
         return (p_184883_3_.length != 4 || !"entity".equals(p_184883_3_[0])) && (p_184883_3_.length != 6 || !"block".equals(p_184883_3_[0])) ? Collections.emptyList() : func_175762_a(p_184883_3_, Item.field_150901_e.func_148742_b());
      } else {
         return func_175762_a(p_184883_3_, field_175785_a.keySet());
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_1_.length > 0 && "entity".equals(p_82358_1_[0]) && p_82358_2_ == 1;
   }

   static {
      for(int i = 0; i < 54; ++i) {
         field_175785_a.put("slot.container." + i, Integer.valueOf(i));
      }

      for(int j = 0; j < 9; ++j) {
         field_175785_a.put("slot.hotbar." + j, Integer.valueOf(j));
      }

      for(int k = 0; k < 27; ++k) {
         field_175785_a.put("slot.inventory." + k, Integer.valueOf(9 + k));
      }

      for(int l = 0; l < 27; ++l) {
         field_175785_a.put("slot.enderchest." + l, Integer.valueOf(200 + l));
      }

      for(int i1 = 0; i1 < 8; ++i1) {
         field_175785_a.put("slot.villager." + i1, Integer.valueOf(300 + i1));
      }

      for(int j1 = 0; j1 < 15; ++j1) {
         field_175785_a.put("slot.horse." + j1, Integer.valueOf(500 + j1));
      }

      field_175785_a.put("slot.weapon", Integer.valueOf(98));
      field_175785_a.put("slot.weapon.mainhand", Integer.valueOf(98));
      field_175785_a.put("slot.weapon.offhand", Integer.valueOf(99));
      field_175785_a.put("slot.armor.head", Integer.valueOf(100 + EntityEquipmentSlot.HEAD.func_188454_b()));
      field_175785_a.put("slot.armor.chest", Integer.valueOf(100 + EntityEquipmentSlot.CHEST.func_188454_b()));
      field_175785_a.put("slot.armor.legs", Integer.valueOf(100 + EntityEquipmentSlot.LEGS.func_188454_b()));
      field_175785_a.put("slot.armor.feet", Integer.valueOf(100 + EntityEquipmentSlot.FEET.func_188454_b()));
      field_175785_a.put("slot.horse.saddle", Integer.valueOf(400));
      field_175785_a.put("slot.horse.armor", Integer.valueOf(401));
      field_175785_a.put("slot.horse.chest", Integer.valueOf(499));
   }
}
