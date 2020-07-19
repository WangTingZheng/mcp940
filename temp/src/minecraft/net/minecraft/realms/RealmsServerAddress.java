package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;

public class RealmsServerAddress {
   private final String host;
   private final int port;

   protected RealmsServerAddress(String p_i1121_1_, int p_i1121_2_) {
      this.host = p_i1121_1_;
      this.port = p_i1121_2_;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public static RealmsServerAddress parseString(String p_parseString_0_) {
      ServerAddress serveraddress = ServerAddress.func_78860_a(p_parseString_0_);
      return new RealmsServerAddress(serveraddress.func_78861_a(), serveraddress.func_78864_b());
   }
}
