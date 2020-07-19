package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class SPacketServerInfo implements Packet<INetHandlerStatusClient> {
   private static final Gson field_149297_a = (new GsonBuilder()).registerTypeAdapter(ServerStatusResponse.Version.class, new ServerStatusResponse.Version.Serializer()).registerTypeAdapter(ServerStatusResponse.Players.class, new ServerStatusResponse.Players.Serializer()).registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
   private ServerStatusResponse field_149296_b;

   public SPacketServerInfo() {
   }

   public SPacketServerInfo(ServerStatusResponse p_i46848_1_) {
      this.field_149296_b = p_i46848_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149296_b = (ServerStatusResponse)JsonUtils.func_188178_a(field_149297_a, p_148837_1_.func_150789_c(32767), ServerStatusResponse.class);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(field_149297_a.toJson(this.field_149296_b));
   }

   public void func_148833_a(INetHandlerStatusClient p_148833_1_) {
      p_148833_1_.func_147397_a(this);
   }

   public ServerStatusResponse func_149294_c() {
      return this.field_149296_b;
   }
}
