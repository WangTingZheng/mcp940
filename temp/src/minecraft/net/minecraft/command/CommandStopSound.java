package net.minecraft.command;

import io.netty.buffer.Unpooled;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class CommandStopSound extends CommandBase {
   public String func_71517_b() {
      return "stopsound";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.stopsound.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length >= 1 && p_184881_3_.length <= 3) {
         int i = 0;
         EntityPlayerMP entityplayermp = func_184888_a(p_184881_1_, p_184881_2_, p_184881_3_[i++]);
         String s = "";
         String s1 = "";
         if (p_184881_3_.length >= 2) {
            String s2 = p_184881_3_[i++];
            SoundCategory soundcategory = SoundCategory.func_187950_a(s2);
            if (soundcategory == null) {
               throw new CommandException("commands.stopsound.unknownSoundSource", new Object[]{s2});
            }

            s = soundcategory.func_187948_a();
         }

         if (p_184881_3_.length == 3) {
            s1 = p_184881_3_[i++];
         }

         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
         packetbuffer.func_180714_a(s);
         packetbuffer.func_180714_a(s1);
         entityplayermp.field_71135_a.func_147359_a(new SPacketCustomPayload("MC|StopSound", packetbuffer));
         if (s.isEmpty() && s1.isEmpty()) {
            func_152373_a(p_184881_2_, this, "commands.stopsound.success.all", new Object[]{entityplayermp.func_70005_c_()});
         } else if (s1.isEmpty()) {
            func_152373_a(p_184881_2_, this, "commands.stopsound.success.soundSource", new Object[]{s, entityplayermp.func_70005_c_()});
         } else {
            func_152373_a(p_184881_2_, this, "commands.stopsound.success.individualSound", new Object[]{s1, s, entityplayermp.func_70005_c_()});
         }

      } else {
         throw new WrongUsageException(this.func_71518_a(p_184881_2_), new Object[0]);
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else if (p_184883_3_.length == 2) {
         return func_175762_a(p_184883_3_, SoundCategory.func_187949_b());
      } else {
         return p_184883_3_.length == 3 ? func_175762_a(p_184883_3_, SoundEvent.field_187505_a.func_148742_b()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
