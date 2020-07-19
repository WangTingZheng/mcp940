package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

public class SPacketTeams implements Packet<INetHandlerPlayClient> {
   private String field_149320_a = "";
   private String field_149318_b = "";
   private String field_149319_c = "";
   private String field_149316_d = "";
   private String field_179816_e;
   private String field_186976_f;
   private int field_179815_f;
   private final Collection<String> field_149317_e;
   private int field_149314_f;
   private int field_149315_g;

   public SPacketTeams() {
      this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
      this.field_186976_f = Team.CollisionRule.ALWAYS.field_186693_e;
      this.field_179815_f = -1;
      this.field_149317_e = Lists.<String>newArrayList();
   }

   public SPacketTeams(ScorePlayerTeam p_i46907_1_, int p_i46907_2_) {
      this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
      this.field_186976_f = Team.CollisionRule.ALWAYS.field_186693_e;
      this.field_179815_f = -1;
      this.field_149317_e = Lists.<String>newArrayList();
      this.field_149320_a = p_i46907_1_.func_96661_b();
      this.field_149314_f = p_i46907_2_;
      if (p_i46907_2_ == 0 || p_i46907_2_ == 2) {
         this.field_149318_b = p_i46907_1_.func_96669_c();
         this.field_149319_c = p_i46907_1_.func_96668_e();
         this.field_149316_d = p_i46907_1_.func_96663_f();
         this.field_149315_g = p_i46907_1_.func_98299_i();
         this.field_179816_e = p_i46907_1_.func_178770_i().field_178830_e;
         this.field_186976_f = p_i46907_1_.func_186681_k().field_186693_e;
         this.field_179815_f = p_i46907_1_.func_178775_l().func_175746_b();
      }

      if (p_i46907_2_ == 0) {
         this.field_149317_e.addAll(p_i46907_1_.func_96670_d());
      }

   }

   public SPacketTeams(ScorePlayerTeam p_i46908_1_, Collection<String> p_i46908_2_, int p_i46908_3_) {
      this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
      this.field_186976_f = Team.CollisionRule.ALWAYS.field_186693_e;
      this.field_179815_f = -1;
      this.field_149317_e = Lists.<String>newArrayList();
      if (p_i46908_3_ != 3 && p_i46908_3_ != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      } else if (p_i46908_2_ != null && !p_i46908_2_.isEmpty()) {
         this.field_149314_f = p_i46908_3_;
         this.field_149320_a = p_i46908_1_.func_96661_b();
         this.field_149317_e.addAll(p_i46908_2_);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149320_a = p_148837_1_.func_150789_c(16);
      this.field_149314_f = p_148837_1_.readByte();
      if (this.field_149314_f == 0 || this.field_149314_f == 2) {
         this.field_149318_b = p_148837_1_.func_150789_c(32);
         this.field_149319_c = p_148837_1_.func_150789_c(16);
         this.field_149316_d = p_148837_1_.func_150789_c(16);
         this.field_149315_g = p_148837_1_.readByte();
         this.field_179816_e = p_148837_1_.func_150789_c(32);
         this.field_186976_f = p_148837_1_.func_150789_c(32);
         this.field_179815_f = p_148837_1_.readByte();
      }

      if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
         int i = p_148837_1_.func_150792_a();

         for(int j = 0; j < i; ++j) {
            this.field_149317_e.add(p_148837_1_.func_150789_c(40));
         }
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_149320_a);
      p_148840_1_.writeByte(this.field_149314_f);
      if (this.field_149314_f == 0 || this.field_149314_f == 2) {
         p_148840_1_.func_180714_a(this.field_149318_b);
         p_148840_1_.func_180714_a(this.field_149319_c);
         p_148840_1_.func_180714_a(this.field_149316_d);
         p_148840_1_.writeByte(this.field_149315_g);
         p_148840_1_.func_180714_a(this.field_179816_e);
         p_148840_1_.func_180714_a(this.field_186976_f);
         p_148840_1_.writeByte(this.field_179815_f);
      }

      if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
         p_148840_1_.func_150787_b(this.field_149317_e.size());

         for(String s : this.field_149317_e) {
            p_148840_1_.func_180714_a(s);
         }
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147247_a(this);
   }

   public String func_149312_c() {
      return this.field_149320_a;
   }

   public String func_149306_d() {
      return this.field_149318_b;
   }

   public String func_149311_e() {
      return this.field_149319_c;
   }

   public String func_149309_f() {
      return this.field_149316_d;
   }

   public Collection<String> func_149310_g() {
      return this.field_149317_e;
   }

   public int func_149307_h() {
      return this.field_149314_f;
   }

   public int func_149308_i() {
      return this.field_149315_g;
   }

   public int func_179813_h() {
      return this.field_179815_f;
   }

   public String func_179814_i() {
      return this.field_179816_e;
   }

   public String func_186975_j() {
      return this.field_186976_f;
   }
}
