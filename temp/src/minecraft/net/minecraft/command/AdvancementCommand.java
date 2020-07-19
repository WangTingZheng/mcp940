package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class AdvancementCommand extends CommandBase {
   public String func_71517_b() {
      return "advancement";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.advancement.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.advancement.usage", new Object[0]);
      } else {
         AdvancementCommand.ActionType advancementcommand$actiontype = AdvancementCommand.ActionType.func_193536_a(p_184881_3_[0]);
         if (advancementcommand$actiontype != null) {
            if (p_184881_3_.length < 3) {
               throw advancementcommand$actiontype.func_193534_a();
            }

            EntityPlayerMP entityplayermp = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[1]);
            AdvancementCommand.Mode advancementcommand$actiontype$mode = AdvancementCommand.Mode.func_193547_a(p_184881_3_[2]);
            if (advancementcommand$actiontype$mode == null) {
               throw advancementcommand$actiontype.func_193534_a();
            }

            this.func_193516_a(p_184881_1_, p_184881_2_, p_184881_3_, entityplayermp, advancementcommand$actiontype, advancementcommand$actiontype$mode);
         } else {
            if (!"test".equals(p_184881_3_[0])) {
               throw new WrongUsageException("commands.advancement.usage", new Object[0]);
            }

            if (p_184881_3_.length == 3) {
               this.func_192552_c(p_184881_2_, func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[1]), func_192551_a(p_184881_1_, p_184881_3_[2]));
            } else {
               if (p_184881_3_.length != 4) {
                  throw new WrongUsageException("commands.advancement.test.usage", new Object[0]);
               }

               this.func_192554_c(p_184881_2_, func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[1]), func_192551_a(p_184881_1_, p_184881_3_[2]), p_184881_3_[3]);
            }
         }

      }
   }

   private void func_193516_a(MinecraftServer p_193516_1_, ICommandSender p_193516_2_, String[] p_193516_3_, EntityPlayerMP p_193516_4_, AdvancementCommand.ActionType p_193516_5_, AdvancementCommand.Mode p_193516_6_) throws CommandException {
      if (p_193516_6_ == AdvancementCommand.Mode.EVERYTHING) {
         if (p_193516_3_.length == 3) {
            int j = p_193516_5_.func_193532_a(p_193516_4_, p_193516_1_.func_191949_aK().func_192780_b());
            if (j == 0) {
               throw p_193516_6_.func_193543_a(p_193516_5_, p_193516_4_.func_70005_c_());
            } else {
               p_193516_6_.func_193546_a(p_193516_2_, this, p_193516_5_, p_193516_4_.func_70005_c_(), j);
            }
         } else {
            throw p_193516_6_.func_193544_a(p_193516_5_);
         }
      } else if (p_193516_3_.length < 4) {
         throw p_193516_6_.func_193544_a(p_193516_5_);
      } else {
         Advancement advancement = func_192551_a(p_193516_1_, p_193516_3_[3]);
         if (p_193516_6_ == AdvancementCommand.Mode.ONLY && p_193516_3_.length == 5) {
            String s = p_193516_3_[4];
            if (!advancement.func_192073_f().keySet().contains(s)) {
               throw new CommandException("commands.advancement.criterionNotFound", new Object[]{advancement.func_192067_g(), p_193516_3_[4]});
            }

            if (!p_193516_5_.func_193535_a(p_193516_4_, advancement, s)) {
               throw new CommandException(p_193516_5_.field_193541_d + ".criterion.failed", new Object[]{advancement.func_192067_g(), p_193516_4_.func_70005_c_(), s});
            }

            func_152373_a(p_193516_2_, this, p_193516_5_.field_193541_d + ".criterion.success", new Object[]{advancement.func_192067_g(), p_193516_4_.func_70005_c_(), s});
         } else {
            if (p_193516_3_.length != 4) {
               throw p_193516_6_.func_193544_a(p_193516_5_);
            }

            List<Advancement> list = this.func_193514_a(advancement, p_193516_6_);
            int i = p_193516_5_.func_193532_a(p_193516_4_, list);
            if (i == 0) {
               throw p_193516_6_.func_193543_a(p_193516_5_, advancement.func_192067_g(), p_193516_4_.func_70005_c_());
            }

            p_193516_6_.func_193546_a(p_193516_2_, this, p_193516_5_, advancement.func_192067_g(), p_193516_4_.func_70005_c_(), i);
         }

      }
   }

   private void func_193515_a(Advancement p_193515_1_, List<Advancement> p_193515_2_) {
      for(Advancement advancement : p_193515_1_.func_192069_e()) {
         p_193515_2_.add(advancement);
         this.func_193515_a(advancement, p_193515_2_);
      }

   }

   private List<Advancement> func_193514_a(Advancement p_193514_1_, AdvancementCommand.Mode p_193514_2_) {
      List<Advancement> list = Lists.<Advancement>newArrayList();
      if (p_193514_2_.field_193555_h) {
         for(Advancement advancement = p_193514_1_.func_192070_b(); advancement != null; advancement = advancement.func_192070_b()) {
            list.add(advancement);
         }
      }

      list.add(p_193514_1_);
      if (p_193514_2_.field_193556_i) {
         this.func_193515_a(p_193514_1_, list);
      }

      return list;
   }

   private void func_192554_c(ICommandSender p_192554_1_, EntityPlayerMP p_192554_2_, Advancement p_192554_3_, String p_192554_4_) throws CommandException {
      PlayerAdvancements playeradvancements = p_192554_2_.func_192039_O();
      CriterionProgress criterionprogress = playeradvancements.func_192747_a(p_192554_3_).func_192106_c(p_192554_4_);
      if (criterionprogress == null) {
         throw new CommandException("commands.advancement.criterionNotFound", new Object[]{p_192554_3_.func_192067_g(), p_192554_4_});
      } else if (!criterionprogress.func_192151_a()) {
         throw new CommandException("commands.advancement.test.criterion.notDone", new Object[]{p_192554_2_.func_70005_c_(), p_192554_3_.func_192067_g(), p_192554_4_});
      } else {
         func_152373_a(p_192554_1_, this, "commands.advancement.test.criterion.success", new Object[]{p_192554_2_.func_70005_c_(), p_192554_3_.func_192067_g(), p_192554_4_});
      }
   }

   private void func_192552_c(ICommandSender p_192552_1_, EntityPlayerMP p_192552_2_, Advancement p_192552_3_) throws CommandException {
      AdvancementProgress advancementprogress = p_192552_2_.func_192039_O().func_192747_a(p_192552_3_);
      if (!advancementprogress.func_192105_a()) {
         throw new CommandException("commands.advancement.test.advancement.notDone", new Object[]{p_192552_2_.func_70005_c_(), p_192552_3_.func_192067_g()});
      } else {
         func_152373_a(p_192552_1_, this, "commands.advancement.test.advancement.success", new Object[]{p_192552_2_.func_70005_c_(), p_192552_3_.func_192067_g()});
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, new String[]{"grant", "revoke", "test"});
      } else {
         AdvancementCommand.ActionType advancementcommand$actiontype = AdvancementCommand.ActionType.func_193536_a(p_184883_3_[0]);
         if (advancementcommand$actiontype != null) {
            if (p_184883_3_.length == 2) {
               return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
            }

            if (p_184883_3_.length == 3) {
               return func_71530_a(p_184883_3_, AdvancementCommand.Mode.field_193553_f);
            }

            AdvancementCommand.Mode advancementcommand$actiontype$mode = AdvancementCommand.Mode.func_193547_a(p_184883_3_[2]);
            if (advancementcommand$actiontype$mode != null && advancementcommand$actiontype$mode != AdvancementCommand.Mode.EVERYTHING) {
               if (p_184883_3_.length == 4) {
                  return func_175762_a(p_184883_3_, this.func_193517_a(p_184883_1_));
               }

               if (p_184883_3_.length == 5 && advancementcommand$actiontype$mode == AdvancementCommand.Mode.ONLY) {
                  Advancement advancement = p_184883_1_.func_191949_aK().func_192778_a(new ResourceLocation(p_184883_3_[3]));
                  if (advancement != null) {
                     return func_175762_a(p_184883_3_, advancement.func_192073_f().keySet());
                  }
               }
            }
         }

         if ("test".equals(p_184883_3_[0])) {
            if (p_184883_3_.length == 2) {
               return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
            }

            if (p_184883_3_.length == 3) {
               return func_175762_a(p_184883_3_, this.func_193517_a(p_184883_1_));
            }

            if (p_184883_3_.length == 4) {
               Advancement advancement1 = p_184883_1_.func_191949_aK().func_192778_a(new ResourceLocation(p_184883_3_[2]));
               if (advancement1 != null) {
                  return func_175762_a(p_184883_3_, advancement1.func_192073_f().keySet());
               }
            }
         }

         return Collections.<String>emptyList();
      }
   }

   private List<ResourceLocation> func_193517_a(MinecraftServer p_193517_1_) {
      List<ResourceLocation> list = Lists.<ResourceLocation>newArrayList();

      for(Advancement advancement : p_193517_1_.func_191949_aK().func_192780_b()) {
         list.add(advancement.func_192067_g());
      }

      return list;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_1_.length > 1 && ("grant".equals(p_82358_1_[0]) || "revoke".equals(p_82358_1_[0]) || "test".equals(p_82358_1_[0])) && p_82358_2_ == 1;
   }

   public static Advancement func_192551_a(MinecraftServer p_192551_0_, String p_192551_1_) throws CommandException {
      Advancement advancement = p_192551_0_.func_191949_aK().func_192778_a(new ResourceLocation(p_192551_1_));
      if (advancement == null) {
         throw new CommandException("commands.advancement.advancementNotFound", new Object[]{p_192551_1_});
      } else {
         return advancement;
      }
   }

   static enum ActionType {
      GRANT("grant") {
         protected boolean func_193537_a(EntityPlayerMP p_193537_1_, Advancement p_193537_2_) {
            AdvancementProgress advancementprogress = p_193537_1_.func_192039_O().func_192747_a(p_193537_2_);
            if (advancementprogress.func_192105_a()) {
               return false;
            } else {
               for(String s : advancementprogress.func_192107_d()) {
                  p_193537_1_.func_192039_O().func_192750_a(p_193537_2_, s);
               }

               return true;
            }
         }

         protected boolean func_193535_a(EntityPlayerMP p_193535_1_, Advancement p_193535_2_, String p_193535_3_) {
            return p_193535_1_.func_192039_O().func_192750_a(p_193535_2_, p_193535_3_);
         }
      },
      REVOKE("revoke") {
         protected boolean func_193537_a(EntityPlayerMP p_193537_1_, Advancement p_193537_2_) {
            AdvancementProgress advancementprogress = p_193537_1_.func_192039_O().func_192747_a(p_193537_2_);
            if (!advancementprogress.func_192108_b()) {
               return false;
            } else {
               for(String s : advancementprogress.func_192102_e()) {
                  p_193537_1_.func_192039_O().func_192744_b(p_193537_2_, s);
               }

               return true;
            }
         }

         protected boolean func_193535_a(EntityPlayerMP p_193535_1_, Advancement p_193535_2_, String p_193535_3_) {
            return p_193535_1_.func_192039_O().func_192744_b(p_193535_2_, p_193535_3_);
         }
      };

      final String field_193540_c;
      final String field_193541_d;

      private ActionType(String p_i47557_3_) {
         this.field_193540_c = p_i47557_3_;
         this.field_193541_d = "commands.advancement." + p_i47557_3_;
      }

      @Nullable
      static AdvancementCommand.ActionType func_193536_a(String p_193536_0_) {
         for(AdvancementCommand.ActionType advancementcommand$actiontype : values()) {
            if (advancementcommand$actiontype.field_193540_c.equals(p_193536_0_)) {
               return advancementcommand$actiontype;
            }
         }

         return null;
      }

      CommandException func_193534_a() {
         return new CommandException(this.field_193541_d + ".usage", new Object[0]);
      }

      public int func_193532_a(EntityPlayerMP p_193532_1_, Iterable<Advancement> p_193532_2_) {
         int i = 0;

         for(Advancement advancement : p_193532_2_) {
            if (this.func_193537_a(p_193532_1_, advancement)) {
               ++i;
            }
         }

         return i;
      }

      protected abstract boolean func_193537_a(EntityPlayerMP var1, Advancement var2);

      protected abstract boolean func_193535_a(EntityPlayerMP var1, Advancement var2, String var3);
   }

   static enum Mode {
      ONLY("only", false, false),
      THROUGH("through", true, true),
      FROM("from", false, true),
      UNTIL("until", true, false),
      EVERYTHING("everything", true, true);

      static final String[] field_193553_f = new String[values().length];
      final String field_193554_g;
      final boolean field_193555_h;
      final boolean field_193556_i;

      private Mode(String p_i47556_3_, boolean p_i47556_4_, boolean p_i47556_5_) {
         this.field_193554_g = p_i47556_3_;
         this.field_193555_h = p_i47556_4_;
         this.field_193556_i = p_i47556_5_;
      }

      CommandException func_193543_a(AdvancementCommand.ActionType p_193543_1_, Object... p_193543_2_) {
         return new CommandException(p_193543_1_.field_193541_d + "." + this.field_193554_g + ".failed", p_193543_2_);
      }

      CommandException func_193544_a(AdvancementCommand.ActionType p_193544_1_) {
         return new CommandException(p_193544_1_.field_193541_d + "." + this.field_193554_g + ".usage", new Object[0]);
      }

      void func_193546_a(ICommandSender p_193546_1_, AdvancementCommand p_193546_2_, AdvancementCommand.ActionType p_193546_3_, Object... p_193546_4_) {
         CommandBase.func_152373_a(p_193546_1_, p_193546_2_, p_193546_3_.field_193541_d + "." + this.field_193554_g + ".success", p_193546_4_);
      }

      @Nullable
      static AdvancementCommand.Mode func_193547_a(String p_193547_0_) {
         for(AdvancementCommand.Mode advancementcommand$actiontype$mode : values()) {
            if (advancementcommand$actiontype$mode.field_193554_g.equals(p_193547_0_)) {
               return advancementcommand$actiontype$mode;
            }
         }

         return null;
      }

      static {
         for(int i = 0; i < values().length; ++i) {
            field_193553_f[i] = values()[i].field_193554_g;
         }

      }
   }
}
