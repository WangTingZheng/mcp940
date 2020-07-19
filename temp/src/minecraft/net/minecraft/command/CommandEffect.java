package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandEffect extends CommandBase {
   public String func_71517_b() {
      return "effect";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.effect.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException("commands.effect.usage", new Object[0]);
      } else {
         EntityLivingBase entitylivingbase = (EntityLivingBase)func_184884_a(p_184881_1_, p_184881_2_, p_184881_3_[0], EntityLivingBase.class);
         if ("clear".equals(p_184881_3_[1])) {
            if (entitylivingbase.func_70651_bq().isEmpty()) {
               throw new CommandException("commands.effect.failure.notActive.all", new Object[]{entitylivingbase.func_70005_c_()});
            } else {
               entitylivingbase.func_70674_bp();
               func_152373_a(p_184881_2_, this, "commands.effect.success.removed.all", new Object[]{entitylivingbase.func_70005_c_()});
            }
         } else {
            Potion potion;
            try {
               potion = Potion.func_188412_a(func_180528_a(p_184881_3_[1], 1));
            } catch (NumberInvalidException var11) {
               potion = Potion.func_180142_b(p_184881_3_[1]);
            }

            if (potion == null) {
               throw new NumberInvalidException("commands.effect.notFound", new Object[]{p_184881_3_[1]});
            } else {
               int i = 600;
               int j = 30;
               int k = 0;
               if (p_184881_3_.length >= 3) {
                  j = func_175764_a(p_184881_3_[2], 0, 1000000);
                  if (potion.func_76403_b()) {
                     i = j;
                  } else {
                     i = j * 20;
                  }
               } else if (potion.func_76403_b()) {
                  i = 1;
               }

               if (p_184881_3_.length >= 4) {
                  k = func_175764_a(p_184881_3_[3], 0, 255);
               }

               boolean flag = true;
               if (p_184881_3_.length >= 5 && "true".equalsIgnoreCase(p_184881_3_[4])) {
                  flag = false;
               }

               if (j > 0) {
                  PotionEffect potioneffect = new PotionEffect(potion, i, k, false, flag);
                  entitylivingbase.func_70690_d(potioneffect);
                  func_152373_a(p_184881_2_, this, "commands.effect.success", new Object[]{new TextComponentTranslation(potioneffect.func_76453_d(), new Object[0]), Potion.func_188409_a(potion), k, entitylivingbase.func_70005_c_(), j});
               } else if (entitylivingbase.func_70644_a(potion)) {
                  entitylivingbase.func_184589_d(potion);
                  func_152373_a(p_184881_2_, this, "commands.effect.success.removed", new Object[]{new TextComponentTranslation(potion.func_76393_a(), new Object[0]), entitylivingbase.func_70005_c_()});
               } else {
                  throw new CommandException("commands.effect.failure.notActive", new Object[]{new TextComponentTranslation(potion.func_76393_a(), new Object[0]), entitylivingbase.func_70005_c_()});
               }
            }
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else if (p_184883_3_.length == 2) {
         return func_175762_a(p_184883_3_, Potion.field_188414_b.func_148742_b());
      } else {
         return p_184883_3_.length == 5 ? func_71530_a(p_184883_3_, new String[]{"true", "false"}) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
