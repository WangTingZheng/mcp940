package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
   private static final ITextComponent field_183007_a = new TextComponentString("Status request has been handled.");
   private final MinecraftServer field_147314_a;
   private final NetworkManager field_147313_b;
   private boolean field_183008_d;

   public NetHandlerStatusServer(MinecraftServer p_i45299_1_, NetworkManager p_i45299_2_) {
      this.field_147314_a = p_i45299_1_;
      this.field_147313_b = p_i45299_2_;
   }

   public void func_147231_a(ITextComponent p_147231_1_) {
   }

   public void func_147312_a(CPacketServerQuery p_147312_1_) {
      if (this.field_183008_d) {
         this.field_147313_b.func_150718_a(field_183007_a);
      } else {
         this.field_183008_d = true;
         this.field_147313_b.func_179290_a(new SPacketServerInfo(this.field_147314_a.func_147134_at()));
      }
   }

   public void func_147311_a(CPacketPing p_147311_1_) {
      this.field_147313_b.func_179290_a(new SPacketPong(p_147311_1_.func_149289_c()));
      this.field_147313_b.func_150718_a(field_183007_a);
   }
}
