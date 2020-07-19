package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CommandPlaySound extends CommandBase {
   public String func_71517_b() {
      return "playsound";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.playsound.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 2) {
         throw new WrongUsageException(this.func_71518_a(p_184881_2_), new Object[0]);
      } else {
         int i = 0;
         String s = p_184881_3_[i++];
         String s1 = p_184881_3_[i++];
         SoundCategory soundcategory = SoundCategory.func_187950_a(s1);
         if (soundcategory == null) {
            throw new CommandException("commands.playsound.unknownSoundSource", new Object[]{s1});
         } else {
            EntityPlayerMP entityplayermp = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[i++]);
            Vec3d vec3d = p_184881_2_.func_174791_d();
            double d0 = p_184881_3_.length > i ? func_175761_b(vec3d.field_72450_a, p_184881_3_[i++], true) : vec3d.field_72450_a;
            double d1 = p_184881_3_.length > i ? func_175769_b(vec3d.field_72448_b, p_184881_3_[i++], 0, 0, false) : vec3d.field_72448_b;
            double d2 = p_184881_3_.length > i ? func_175761_b(vec3d.field_72449_c, p_184881_3_[i++], true) : vec3d.field_72449_c;
            double d3 = p_184881_3_.length > i ? func_175756_a(p_184881_3_[i++], 0.0D, 3.4028234663852886E38D) : 1.0D;
            double d4 = p_184881_3_.length > i ? func_175756_a(p_184881_3_[i++], 0.0D, 2.0D) : 1.0D;
            double d5 = p_184881_3_.length > i ? func_175756_a(p_184881_3_[i], 0.0D, 1.0D) : 0.0D;
            double d6 = d3 > 1.0D ? d3 * 16.0D : 16.0D;
            double d7 = entityplayermp.func_70011_f(d0, d1, d2);
            if (d7 > d6) {
               if (d5 <= 0.0D) {
                  throw new CommandException("commands.playsound.playerTooFar", new Object[]{entityplayermp.func_70005_c_()});
               }

               double d8 = d0 - entityplayermp.field_70165_t;
               double d9 = d1 - entityplayermp.field_70163_u;
               double d10 = d2 - entityplayermp.field_70161_v;
               double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
               if (d11 > 0.0D) {
                  d0 = entityplayermp.field_70165_t + d8 / d11 * 2.0D;
                  d1 = entityplayermp.field_70163_u + d9 / d11 * 2.0D;
                  d2 = entityplayermp.field_70161_v + d10 / d11 * 2.0D;
               }

               d3 = d5;
            }

            entityplayermp.field_71135_a.func_147359_a(new SPacketCustomSound(s, soundcategory, d0, d1, d2, (float)d3, (float)d4));
            func_152373_a(p_184881_2_, this, "commands.playsound.success", new Object[]{s, entityplayermp.func_70005_c_()});
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_175762_a(p_184883_3_, SoundEvent.field_187505_a.func_148742_b());
      } else if (p_184883_3_.length == 2) {
         return func_175762_a(p_184883_3_, SoundCategory.func_187949_b());
      } else if (p_184883_3_.length == 3) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else {
         return p_184883_3_.length > 3 && p_184883_3_.length <= 6 ? func_175771_a(p_184883_3_, 3, p_184883_4_) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 2;
   }
}
