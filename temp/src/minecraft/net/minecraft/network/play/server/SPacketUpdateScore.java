package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class SPacketUpdateScore implements Packet<INetHandlerPlayClient> {
   private String field_149329_a = "";
   private String field_149327_b = "";
   private int field_149328_c;
   private SPacketUpdateScore.Action field_149326_d;

   public SPacketUpdateScore() {
   }

   public SPacketUpdateScore(Score p_i46904_1_) {
      this.field_149329_a = p_i46904_1_.func_96653_e();
      this.field_149327_b = p_i46904_1_.func_96645_d().func_96679_b();
      this.field_149328_c = p_i46904_1_.func_96652_c();
      this.field_149326_d = SPacketUpdateScore.Action.CHANGE;
   }

   public SPacketUpdateScore(String p_i46905_1_) {
      this.field_149329_a = p_i46905_1_;
      this.field_149327_b = "";
      this.field_149328_c = 0;
      this.field_149326_d = SPacketUpdateScore.Action.REMOVE;
   }

   public SPacketUpdateScore(String p_i46906_1_, ScoreObjective p_i46906_2_) {
      this.field_149329_a = p_i46906_1_;
      this.field_149327_b = p_i46906_2_.func_96679_b();
      this.field_149328_c = 0;
      this.field_149326_d = SPacketUpdateScore.Action.REMOVE;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149329_a = p_148837_1_.func_150789_c(40);
      this.field_149326_d = (SPacketUpdateScore.Action)p_148837_1_.func_179257_a(SPacketUpdateScore.Action.class);
      this.field_149327_b = p_148837_1_.func_150789_c(16);
      if (this.field_149326_d != SPacketUpdateScore.Action.REMOVE) {
         this.field_149328_c = p_148837_1_.func_150792_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_149329_a);
      p_148840_1_.func_179249_a(this.field_149326_d);
      p_148840_1_.func_180714_a(this.field_149327_b);
      if (this.field_149326_d != SPacketUpdateScore.Action.REMOVE) {
         p_148840_1_.func_150787_b(this.field_149328_c);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147250_a(this);
   }

   public String func_149324_c() {
      return this.field_149329_a;
   }

   public String func_149321_d() {
      return this.field_149327_b;
   }

   public int func_149323_e() {
      return this.field_149328_c;
   }

   public SPacketUpdateScore.Action func_180751_d() {
      return this.field_149326_d;
   }

   public static enum Action {
      CHANGE,
      REMOVE;
   }
}
