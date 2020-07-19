package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector {
   private static final AtomicInteger field_148551_a = new AtomicInteger(0);
   private static final Logger field_148550_b = LogManager.getLogger();

   public static class LanServerList {
      private final List<LanServerInfo> field_77555_b = Lists.<LanServerInfo>newArrayList();
      boolean field_77556_a;

      public synchronized boolean func_77553_a() {
         return this.field_77556_a;
      }

      public synchronized void func_77552_b() {
         this.field_77556_a = false;
      }

      public synchronized List<LanServerInfo> func_77554_c() {
         return Collections.<LanServerInfo>unmodifiableList(this.field_77555_b);
      }

      public synchronized void func_77551_a(String p_77551_1_, InetAddress p_77551_2_) {
         String s = ThreadLanServerPing.func_77524_a(p_77551_1_);
         String s1 = ThreadLanServerPing.func_77523_b(p_77551_1_);
         if (s1 != null) {
            s1 = p_77551_2_.getHostAddress() + ":" + s1;
            boolean flag = false;

            for(LanServerInfo lanserverinfo : this.field_77555_b) {
               if (lanserverinfo.func_77488_b().equals(s1)) {
                  lanserverinfo.func_77489_c();
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               this.field_77555_b.add(new LanServerInfo(s, s1));
               this.field_77556_a = true;
            }

         }
      }
   }

   public static class ThreadLanServerFind extends Thread {
      private final LanServerDetector.LanServerList field_77500_a;
      private final InetAddress field_77498_b;
      private final MulticastSocket field_77499_c;

      public ThreadLanServerFind(LanServerDetector.LanServerList p_i1320_1_) throws IOException {
         super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
         this.field_77500_a = p_i1320_1_;
         this.setDaemon(true);
         this.field_77499_c = new MulticastSocket(4445);
         this.field_77498_b = InetAddress.getByName("224.0.2.60");
         this.field_77499_c.setSoTimeout(5000);
         this.field_77499_c.joinGroup(this.field_77498_b);
      }

      public void run() {
         byte[] abyte = new byte[1024];

         while(!this.isInterrupted()) {
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);

            try {
               this.field_77499_c.receive(datagrampacket);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException ioexception) {
               LanServerDetector.field_148550_b.error("Couldn't ping server", (Throwable)ioexception);
               break;
            }

            String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength(), StandardCharsets.UTF_8);
            LanServerDetector.field_148550_b.debug("{}: {}", datagrampacket.getAddress(), s);
            this.field_77500_a.func_77551_a(s, datagrampacket.getAddress());
         }

         try {
            this.field_77499_c.leaveGroup(this.field_77498_b);
         } catch (IOException var4) {
            ;
         }

         this.field_77499_c.close();
      }
   }
}
