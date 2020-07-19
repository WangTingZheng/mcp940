package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.ReportedException;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkSystem {
   private static final Logger field_151275_b = LogManager.getLogger();
   public static final LazyLoadBase<NioEventLoopGroup> field_151276_c = new LazyLoadBase<NioEventLoopGroup>() {
      protected NioEventLoopGroup func_179280_b() {
         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<EpollEventLoopGroup> field_181141_b = new LazyLoadBase<EpollEventLoopGroup>() {
      protected EpollEventLoopGroup func_179280_b() {
         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<LocalEventLoopGroup> field_180232_b = new LazyLoadBase<LocalEventLoopGroup>() {
      protected LocalEventLoopGroup func_179280_b() {
         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
      }
   };
   private final MinecraftServer field_151273_d;
   public volatile boolean field_151277_a;
   private final List<ChannelFuture> field_151274_e = Collections.<ChannelFuture>synchronizedList(Lists.newArrayList());
   private final List<NetworkManager> field_151272_f = Collections.<NetworkManager>synchronizedList(Lists.newArrayList());

   public NetworkSystem(MinecraftServer p_i45292_1_) {
      this.field_151273_d = p_i45292_1_;
      this.field_151277_a = true;
   }

   public void func_151265_a(InetAddress p_151265_1_, int p_151265_2_) throws IOException {
      synchronized(this.field_151274_e) {
         Class<? extends ServerSocketChannel> oclass;
         LazyLoadBase<? extends EventLoopGroup> lazyloadbase;
         if (Epoll.isAvailable() && this.field_151273_d.func_181035_ah()) {
            oclass = EpollServerSocketChannel.class;
            lazyloadbase = field_181141_b;
            field_151275_b.info("Using epoll channel type");
         } else {
            oclass = NioServerSocketChannel.class;
            lazyloadbase = field_151276_c;
            field_151275_b.info("Using default channel type");
         }

         this.field_151274_e.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(oclass)).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_initChannel_1_) throws Exception {
               try {
                  p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
               } catch (ChannelException var3) {
                  ;
               }

               p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new LegacyPingHandler(NetworkSystem.this)).addLast("splitter", new NettyVarint21FrameDecoder()).addLast("decoder", new NettyPacketDecoder(EnumPacketDirection.SERVERBOUND)).addLast("prepender", new NettyVarint21FrameEncoder()).addLast("encoder", new NettyPacketEncoder(EnumPacketDirection.CLIENTBOUND));
               NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
               NetworkSystem.this.field_151272_f.add(networkmanager);
               p_initChannel_1_.pipeline().addLast("packet_handler", networkmanager);
               networkmanager.func_150719_a(new NetHandlerHandshakeTCP(NetworkSystem.this.field_151273_d, networkmanager));
            }
         }).group(lazyloadbase.func_179281_c()).localAddress(p_151265_1_, p_151265_2_)).bind().syncUninterruptibly());
      }
   }

   public SocketAddress func_151270_a() {
      ChannelFuture channelfuture;
      synchronized(this.field_151274_e) {
         channelfuture = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_initChannel_1_) throws Exception {
               NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
               networkmanager.func_150719_a(new NetHandlerHandshakeMemory(NetworkSystem.this.field_151273_d, networkmanager));
               NetworkSystem.this.field_151272_f.add(networkmanager);
               p_initChannel_1_.pipeline().addLast("packet_handler", networkmanager);
            }
         }).group(field_151276_c.func_179281_c()).localAddress(LocalAddress.ANY)).bind().syncUninterruptibly();
         this.field_151274_e.add(channelfuture);
      }

      return channelfuture.channel().localAddress();
   }

   public void func_151268_b() {
      this.field_151277_a = false;

      for(ChannelFuture channelfuture : this.field_151274_e) {
         try {
            channelfuture.channel().close().sync();
         } catch (InterruptedException var4) {
            field_151275_b.error("Interrupted whilst closing channel");
         }
      }

   }

   public void func_151269_c() {
      synchronized(this.field_151272_f) {
         Iterator<NetworkManager> iterator = this.field_151272_f.iterator();

         while(iterator.hasNext()) {
            final NetworkManager networkmanager = iterator.next();
            if (!networkmanager.func_179291_h()) {
               if (networkmanager.func_150724_d()) {
                  try {
                     networkmanager.func_74428_b();
                  } catch (Exception exception) {
                     if (networkmanager.func_150731_c()) {
                        CrashReport crashreport = CrashReport.func_85055_a(exception, "Ticking memory connection");
                        CrashReportCategory crashreportcategory = crashreport.func_85058_a("Ticking connection");
                        crashreportcategory.func_189529_a("Connection", new ICrashReportDetail<String>() {
                           public String call() throws Exception {
                              return networkmanager.toString();
                           }
                        });
                        throw new ReportedException(crashreport);
                     }

                     field_151275_b.warn("Failed to handle packet for {}", networkmanager.func_74430_c(), exception);
                     final TextComponentString textcomponentstring = new TextComponentString("Internal server error");
                     networkmanager.func_179288_a(new SPacketDisconnect(textcomponentstring), new GenericFutureListener<Future<? super Void>>() {
                        public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
                           networkmanager.func_150718_a(textcomponentstring);
                        }
                     });
                     networkmanager.func_150721_g();
                  }
               } else {
                  iterator.remove();
                  networkmanager.func_179293_l();
               }
            }
         }

      }
   }

   public MinecraftServer func_151267_d() {
      return this.field_151273_d;
   }
}
