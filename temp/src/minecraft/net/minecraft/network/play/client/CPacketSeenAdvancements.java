package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;

public class CPacketSeenAdvancements implements Packet<INetHandlerPlayServer> {
   private CPacketSeenAdvancements.Action field_194166_a;
   private ResourceLocation field_194167_b;

   public CPacketSeenAdvancements() {
   }

   public CPacketSeenAdvancements(CPacketSeenAdvancements.Action p_i47595_1_, @Nullable ResourceLocation p_i47595_2_) {
      this.field_194166_a = p_i47595_1_;
      this.field_194167_b = p_i47595_2_;
   }

   public static CPacketSeenAdvancements func_194163_a(Advancement p_194163_0_) {
      return new CPacketSeenAdvancements(CPacketSeenAdvancements.Action.OPENED_TAB, p_194163_0_.func_192067_g());
   }

   public static CPacketSeenAdvancements func_194164_a() {
      return new CPacketSeenAdvancements(CPacketSeenAdvancements.Action.CLOSED_SCREEN, (ResourceLocation)null);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_194166_a = (CPacketSeenAdvancements.Action)p_148837_1_.func_179257_a(CPacketSeenAdvancements.Action.class);
      if (this.field_194166_a == CPacketSeenAdvancements.Action.OPENED_TAB) {
         this.field_194167_b = p_148837_1_.func_192575_l();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_194166_a);
      if (this.field_194166_a == CPacketSeenAdvancements.Action.OPENED_TAB) {
         p_148840_1_.func_192572_a(this.field_194167_b);
      }

   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_194027_a(this);
   }

   public CPacketSeenAdvancements.Action func_194162_b() {
      return this.field_194166_a;
   }

   public ResourceLocation func_194165_c() {
      return this.field_194167_b;
   }

   public static enum Action {
      OPENED_TAB,
      CLOSED_SCREEN;
   }
}
