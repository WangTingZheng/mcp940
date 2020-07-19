package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandParticle extends CommandBase {
   public String func_71517_b() {
      return "particle";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.particle.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 8) {
         throw new WrongUsageException("commands.particle.usage", new Object[0]);
      } else {
         boolean flag = false;
         EnumParticleTypes enumparticletypes = EnumParticleTypes.func_186831_a(p_184881_3_[0]);
         if (enumparticletypes == null) {
            throw new CommandException("commands.particle.notFound", new Object[]{p_184881_3_[0]});
         } else {
            String s = p_184881_3_[0];
            Vec3d vec3d = p_184881_2_.func_174791_d();
            double d0 = (double)((float)func_175761_b(vec3d.field_72450_a, p_184881_3_[1], true));
            double d1 = (double)((float)func_175761_b(vec3d.field_72448_b, p_184881_3_[2], true));
            double d2 = (double)((float)func_175761_b(vec3d.field_72449_c, p_184881_3_[3], true));
            double d3 = (double)((float)func_175765_c(p_184881_3_[4]));
            double d4 = (double)((float)func_175765_c(p_184881_3_[5]));
            double d5 = (double)((float)func_175765_c(p_184881_3_[6]));
            double d6 = (double)((float)func_175765_c(p_184881_3_[7]));
            int i = 0;
            if (p_184881_3_.length > 8) {
               i = func_180528_a(p_184881_3_[8], 0);
            }

            boolean flag1 = false;
            if (p_184881_3_.length > 9 && "force".equals(p_184881_3_[9])) {
               flag1 = true;
            }

            EntityPlayerMP entityplayermp;
            if (p_184881_3_.length > 10) {
               entityplayermp = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[10]);
            } else {
               entityplayermp = null;
            }

            int[] aint = new int[enumparticletypes.func_179345_d()];

            for(int j = 0; j < aint.length; ++j) {
               if (p_184881_3_.length > 11 + j) {
                  try {
                     aint[j] = Integer.parseInt(p_184881_3_[11 + j]);
                  } catch (NumberFormatException var28) {
                     throw new CommandException("commands.particle.invalidParam", new Object[]{p_184881_3_[11 + j]});
                  }
               }
            }

            World world = p_184881_2_.func_130014_f_();
            if (world instanceof WorldServer) {
               WorldServer worldserver = (WorldServer)world;
               if (entityplayermp == null) {
                  worldserver.func_180505_a(enumparticletypes, flag1, d0, d1, d2, i, d3, d4, d5, d6, aint);
               } else {
                  worldserver.func_184161_a(entityplayermp, enumparticletypes, flag1, d0, d1, d2, i, d3, d4, d5, d6, aint);
               }

               func_152373_a(p_184881_2_, this, "commands.particle.success", new Object[]{s, Math.max(i, 1)});
            }

         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_175762_a(p_184883_3_, EnumParticleTypes.func_186832_a());
      } else if (p_184883_3_.length > 1 && p_184883_3_.length <= 4) {
         return func_175771_a(p_184883_3_, 1, p_184883_4_);
      } else if (p_184883_3_.length == 10) {
         return func_71530_a(p_184883_3_, new String[]{"normal", "force"});
      } else {
         return p_184883_3_.length == 11 ? func_71530_a(p_184883_3_, p_184883_1_.func_71213_z()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 10;
   }
}
