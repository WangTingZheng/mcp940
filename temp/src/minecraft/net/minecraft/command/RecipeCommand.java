package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RecipeCommand extends CommandBase {
   public String func_71517_b() {
      return "recipe";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.recipe.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.recipe.usage", new Object[0]);
      } else {
         boolean flag = "give".equalsIgnoreCase(p_184881_3_[0]);
         boolean flag1 = "take".equalsIgnoreCase(p_184881_3_[0]);
         if (!flag && !flag1) {
            throw new WrongUsageException("commands.recipe.usage", new Object[0]);
         } else {
            for(EntityPlayerMP entityplayermp : func_193513_a(p_184881_1_, p_184881_2_, p_184881_3_[1])) {
               if ("*".equals(p_184881_3_[2])) {
                  if (flag) {
                     entityplayermp.func_192021_a(this.func_192556_d());
                     func_152373_a(p_184881_2_, this, "commands.recipe.give.success.all", new Object[]{entityplayermp.func_70005_c_()});
                  } else {
                     entityplayermp.func_192022_b(this.func_192556_d());
                     func_152373_a(p_184881_2_, this, "commands.recipe.take.success.all", new Object[]{entityplayermp.func_70005_c_()});
                  }
               } else {
                  IRecipe irecipe = CraftingManager.func_193373_a(new ResourceLocation(p_184881_3_[2]));
                  if (irecipe == null) {
                     throw new CommandException("commands.recipe.unknownrecipe", new Object[]{p_184881_3_[2]});
                  }

                  if (irecipe.func_192399_d()) {
                     throw new CommandException("commands.recipe.unsupported", new Object[]{p_184881_3_[2]});
                  }

                  List<IRecipe> list = Lists.newArrayList(irecipe);
                  if (flag == entityplayermp.func_192037_E().func_193830_f(irecipe)) {
                     String s = flag ? "commands.recipe.alreadyHave" : "commands.recipe.dontHave";
                     throw new CommandException(s, new Object[]{entityplayermp.func_70005_c_(), irecipe.func_77571_b().func_82833_r()});
                  }

                  if (flag) {
                     entityplayermp.func_192021_a(list);
                     func_152373_a(p_184881_2_, this, "commands.recipe.give.success.one", new Object[]{entityplayermp.func_70005_c_(), irecipe.func_77571_b().func_82833_r()});
                  } else {
                     entityplayermp.func_192022_b(list);
                     func_152373_a(p_184881_2_, this, "commands.recipe.take.success.one", new Object[]{irecipe.func_77571_b().func_82833_r(), entityplayermp.func_70005_c_()});
                  }
               }
            }

         }
      }
   }

   private List<IRecipe> func_192556_d() {
      return Lists.newArrayList(CraftingManager.field_193380_a);
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"give", "take"});
      } else if (p_184883_3_.length == 2) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length == 3 ? func_175762_a(p_184883_3_, CraftingManager.field_193380_a.func_148742_b()) : Collections.emptyList();
      }
   }
}
