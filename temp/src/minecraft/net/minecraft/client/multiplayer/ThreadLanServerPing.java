package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing extends Thread {
   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
   private static final Logger field_148657_b = LogManager.getLogger();
   private final String field_77528_b;
   private final DatagramSocket field_77529_c;
   private boolean field_77526_d = true;
   private final String field_77527_e;

   public ThreadLanServerPing(String p_i1321_1_, String p_i1321_2_) throws IOException {
      super("LanServerPinger #" + field_148658_a.incrementAndGet());
      this.field_77528_b = p_i1321_1_;
      this.field_77527_e = p_i1321_2_;
      this.setDaemon(true);
      this.field_77529_c = new DatagramSocket();
   }

   public void run() {
      String s = func_77525_a(this.field_77528_b, this.field_77527_e);
      byte[] abyte = s.getBytes(StandardCharsets.UTF_8);

      while(!this.isInterrupted() && this.field_77526_d) {
         try {
            InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
            this.field_77529_c.send(datagrampacket);
         } catch (IOException ioexception) {
            field_148657_b.warn("LanServerPinger: {}", (Object)ioexception.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException var5) {
            ;
         }
      }

   }

   public void interrupt() {
      super.interrupt();
      this.field_77526_d = false;
   }

   public static String func_77525_a(String p_77525_0_, String p_77525_1_) {
      return "[MOTD]" + p_77525_0_ + "[/MOTD][AD]" + p_77525_1_ + "[/AD]";
   }

   public static String func_77524_a(String p_77524_0_) {
      int i = p_77524_0_.indexOf("[MOTD]");
      if (i < 0) {
         return "missing no";
      } else {
         int j = p_77524_0_.indexOf("[/MOTD]", i + "[MOTD]".length());
         return j < i ? "missing no" : p_77524_0_.substring(i + "[MOTD]".length(), j);
      }
   }

   public static String func_77523_b(String p_77523_0_) {
      int i = p_77523_0_.indexOf("[/MOTD]");
      if (i < 0) {
         return null;
      } else {
         int j = p_77523_0_.indexOf("[/MOTD]", i + "[/MOTD]".length());
         if (j >= 0) {
            return null;
         } else {
            int k = p_77523_0_.indexOf("[AD]", i + "[/MOTD]".length());
            if (k < 0) {
               return null;
            } else {
               int l = p_77523_0_.indexOf("[/AD]", k + "[AD]".length());
               return l < k ? null : p_77523_0_.substring(k + "[AD]".length(), l);
            }
         }
      }
   }
}
