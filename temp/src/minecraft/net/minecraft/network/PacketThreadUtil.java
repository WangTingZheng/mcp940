package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
   public static <T extends INetHandler> void func_180031_a(final Packet<T> p_180031_0_, final T p_180031_1_, IThreadListener p_180031_2_) throws ThreadQuickExitException {
      if (!p_180031_2_.func_152345_ab()) {
         p_180031_2_.func_152344_a(new Runnable() {
            public void run() {
               p_180031_0_.func_148833_a(p_180031_1_);
            }
         });
         throw ThreadQuickExitException.field_179886_a;
      }
   }
}
