package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {
   public String func_71517_b() {
      return "spreadplayers";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.spreadplayers.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
      } else {
         int i = 0;
         BlockPos blockpos = p_184881_2_.func_180425_c();
         double d0 = func_175761_b((double)blockpos.func_177958_n(), p_184881_3_[i++], true);
         double d1 = func_175761_b((double)blockpos.func_177952_p(), p_184881_3_[i++], true);
         double d2 = func_180526_a(p_184881_3_[i++], 0.0D);
         double d3 = func_180526_a(p_184881_3_[i++], d2 + 1.0D);
         boolean flag = func_180527_d(p_184881_3_[i++]);
         List<Entity> list = Lists.<Entity>newArrayList();

         while(i < p_184881_3_.length) {
            String s = p_184881_3_[i++];
            if (EntitySelector.func_82378_b(s)) {
               List<Entity> list1 = EntitySelector.<Entity>func_179656_b(p_184881_2_, s, Entity.class);
               if (list1.isEmpty()) {
                  throw new EntityNotFoundException("commands.generic.selector.notFound", new Object[]{s});
               }

               list.addAll(list1);
            } else {
               EntityPlayer entityplayer = p_184881_1_.func_184103_al().func_152612_a(s);
               if (entityplayer == null) {
                  throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[]{s});
               }

               list.add(entityplayer);
            }
         }

         p_184881_2_.func_174794_a(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
         if (list.isEmpty()) {
            throw new EntityNotFoundException("commands.spreadplayers.noop");
         } else {
            p_184881_2_.func_145747_a(new TextComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[]{list.size(), d3, d0, d1, d2}));
            this.func_110669_a(p_184881_2_, list, new CommandSpreadPlayers.Position(d0, d1), d2, d3, (list.get(0)).field_70170_p, flag);
         }
      }
   }

   private void func_110669_a(ICommandSender p_110669_1_, List<Entity> p_110669_2_, CommandSpreadPlayers.Position p_110669_3_, double p_110669_4_, double p_110669_6_, World p_110669_8_, boolean p_110669_9_) throws CommandException {
      Random random = new Random();
      double d0 = p_110669_3_.field_111101_a - p_110669_6_;
      double d1 = p_110669_3_.field_111100_b - p_110669_6_;
      double d2 = p_110669_3_.field_111101_a + p_110669_6_;
      double d3 = p_110669_3_.field_111100_b + p_110669_6_;
      CommandSpreadPlayers.Position[] acommandspreadplayers$position = this.func_110670_a(random, p_110669_9_ ? this.func_110667_a(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
      int i = this.func_110668_a(p_110669_3_, p_110669_4_, p_110669_8_, random, d0, d1, d2, d3, acommandspreadplayers$position, p_110669_9_);
      double d4 = this.func_110671_a(p_110669_2_, p_110669_8_, acommandspreadplayers$position, p_110669_9_);
      func_152373_a(p_110669_1_, this, "commands.spreadplayers.success." + (p_110669_9_ ? "teams" : "players"), new Object[]{acommandspreadplayers$position.length, p_110669_3_.field_111101_a, p_110669_3_.field_111100_b});
      if (acommandspreadplayers$position.length > 1) {
         p_110669_1_.func_145747_a(new TextComponentTranslation("commands.spreadplayers.info." + (p_110669_9_ ? "teams" : "players"), new Object[]{String.format("%.2f", d4), i}));
      }

   }

   private int func_110667_a(List<Entity> p_110667_1_) {
      Set<Team> set = Sets.<Team>newHashSet();

      for(Entity entity : p_110667_1_) {
         if (entity instanceof EntityPlayer) {
            set.add(entity.func_96124_cp());
         } else {
            set.add((Object)null);
         }
      }

      return set.size();
   }

   private int func_110668_a(CommandSpreadPlayers.Position p_110668_1_, double p_110668_2_, World p_110668_4_, Random p_110668_5_, double p_110668_6_, double p_110668_8_, double p_110668_10_, double p_110668_12_, CommandSpreadPlayers.Position[] p_110668_14_, boolean p_110668_15_) throws CommandException {
      boolean flag = true;
      double d0 = 3.4028234663852886E38D;

      int i;
      for(i = 0; i < 10000 && flag; ++i) {
         flag = false;
         d0 = 3.4028234663852886E38D;

         for(int j = 0; j < p_110668_14_.length; ++j) {
            CommandSpreadPlayers.Position commandspreadplayers$position = p_110668_14_[j];
            int k = 0;
            CommandSpreadPlayers.Position commandspreadplayers$position1 = new CommandSpreadPlayers.Position();

            for(int l = 0; l < p_110668_14_.length; ++l) {
               if (j != l) {
                  CommandSpreadPlayers.Position commandspreadplayers$position2 = p_110668_14_[l];
                  double d1 = commandspreadplayers$position.func_111099_a(commandspreadplayers$position2);
                  d0 = Math.min(d1, d0);
                  if (d1 < p_110668_2_) {
                     ++k;
                     commandspreadplayers$position1.field_111101_a += commandspreadplayers$position2.field_111101_a - commandspreadplayers$position.field_111101_a;
                     commandspreadplayers$position1.field_111100_b += commandspreadplayers$position2.field_111100_b - commandspreadplayers$position.field_111100_b;
                  }
               }
            }

            if (k > 0) {
               commandspreadplayers$position1.field_111101_a /= (double)k;
               commandspreadplayers$position1.field_111100_b /= (double)k;
               double d2 = (double)commandspreadplayers$position1.func_111096_b();
               if (d2 > 0.0D) {
                  commandspreadplayers$position1.func_111095_a();
                  commandspreadplayers$position.func_111094_b(commandspreadplayers$position1);
               } else {
                  commandspreadplayers$position.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
               }

               flag = true;
            }

            if (commandspreadplayers$position.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_)) {
               flag = true;
            }
         }

         if (!flag) {
            for(CommandSpreadPlayers.Position commandspreadplayers$position3 : p_110668_14_) {
               if (!commandspreadplayers$position3.func_111098_b(p_110668_4_)) {
                  commandspreadplayers$position3.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
                  flag = true;
               }
            }
         }
      }

      if (i >= 10000) {
         throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_ ? "teams" : "players"), new Object[]{p_110668_14_.length, p_110668_1_.field_111101_a, p_110668_1_.field_111100_b, String.format("%.2f", d0)});
      } else {
         return i;
      }
   }

   private double func_110671_a(List<Entity> p_110671_1_, World p_110671_2_, CommandSpreadPlayers.Position[] p_110671_3_, boolean p_110671_4_) {
      double d0 = 0.0D;
      int i = 0;
      Map<Team, CommandSpreadPlayers.Position> map = Maps.<Team, CommandSpreadPlayers.Position>newHashMap();

      for(int j = 0; j < p_110671_1_.size(); ++j) {
         Entity entity = p_110671_1_.get(j);
         CommandSpreadPlayers.Position commandspreadplayers$position;
         if (p_110671_4_) {
            Team team = entity instanceof EntityPlayer ? entity.func_96124_cp() : null;
            if (!map.containsKey(team)) {
               map.put(team, p_110671_3_[i++]);
            }

            commandspreadplayers$position = map.get(team);
         } else {
            commandspreadplayers$position = p_110671_3_[i++];
         }

         entity.func_70634_a((double)((float)MathHelper.func_76128_c(commandspreadplayers$position.field_111101_a) + 0.5F), (double)commandspreadplayers$position.func_111092_a(p_110671_2_), (double)MathHelper.func_76128_c(commandspreadplayers$position.field_111100_b) + 0.5D);
         double d2 = Double.MAX_VALUE;

         for(CommandSpreadPlayers.Position commandspreadplayers$position1 : p_110671_3_) {
            if (commandspreadplayers$position != commandspreadplayers$position1) {
               double d1 = commandspreadplayers$position.func_111099_a(commandspreadplayers$position1);
               d2 = Math.min(d1, d2);
            }
         }

         d0 += d2;
      }

      d0 = d0 / (double)p_110671_1_.size();
      return d0;
   }

   private CommandSpreadPlayers.Position[] func_110670_a(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
      CommandSpreadPlayers.Position[] acommandspreadplayers$position = new CommandSpreadPlayers.Position[p_110670_2_];

      for(int i = 0; i < acommandspreadplayers$position.length; ++i) {
         CommandSpreadPlayers.Position commandspreadplayers$position = new CommandSpreadPlayers.Position();
         commandspreadplayers$position.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
         acommandspreadplayers$position[i] = commandspreadplayers$position;
      }

      return acommandspreadplayers$position;
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length >= 1 && p_184883_3_.length <= 2 ? func_181043_b(p_184883_3_, 0, p_184883_4_) : Collections.emptyList();
   }

   static class Position {
      double field_111101_a;
      double field_111100_b;

      Position() {
      }

      Position(double p_i1358_1_, double p_i1358_3_) {
         this.field_111101_a = p_i1358_1_;
         this.field_111100_b = p_i1358_3_;
      }

      double func_111099_a(CommandSpreadPlayers.Position p_111099_1_) {
         double d0 = this.field_111101_a - p_111099_1_.field_111101_a;
         double d1 = this.field_111100_b - p_111099_1_.field_111100_b;
         return Math.sqrt(d0 * d0 + d1 * d1);
      }

      void func_111095_a() {
         double d0 = (double)this.func_111096_b();
         this.field_111101_a /= d0;
         this.field_111100_b /= d0;
      }

      float func_111096_b() {
         return MathHelper.func_76133_a(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
      }

      public void func_111094_b(CommandSpreadPlayers.Position p_111094_1_) {
         this.field_111101_a -= p_111094_1_.field_111101_a;
         this.field_111100_b -= p_111094_1_.field_111100_b;
      }

      public boolean func_111093_a(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
         boolean flag = false;
         if (this.field_111101_a < p_111093_1_) {
            this.field_111101_a = p_111093_1_;
            flag = true;
         } else if (this.field_111101_a > p_111093_5_) {
            this.field_111101_a = p_111093_5_;
            flag = true;
         }

         if (this.field_111100_b < p_111093_3_) {
            this.field_111100_b = p_111093_3_;
            flag = true;
         } else if (this.field_111100_b > p_111093_7_) {
            this.field_111100_b = p_111093_7_;
            flag = true;
         }

         return flag;
      }

      public int func_111092_a(World p_111092_1_) {
         BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);

         while(blockpos.func_177956_o() > 0) {
            blockpos = blockpos.func_177977_b();
            if (p_111092_1_.func_180495_p(blockpos).func_185904_a() != Material.field_151579_a) {
               return blockpos.func_177956_o() + 1;
            }
         }

         return 257;
      }

      public boolean func_111098_b(World p_111098_1_) {
         BlockPos blockpos = new BlockPos(this.field_111101_a, 256.0D, this.field_111100_b);

         while(blockpos.func_177956_o() > 0) {
            blockpos = blockpos.func_177977_b();
            Material material = p_111098_1_.func_180495_p(blockpos).func_185904_a();
            if (material != Material.field_151579_a) {
               return !material.func_76224_d() && material != Material.field_151581_o;
            }
         }

         return false;
      }

      public void func_111097_a(Random p_111097_1_, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
         this.field_111101_a = MathHelper.func_82716_a(p_111097_1_, p_111097_2_, p_111097_6_);
         this.field_111100_b = MathHelper.func_82716_a(p_111097_1_, p_111097_4_, p_111097_8_);
      }
   }
}
