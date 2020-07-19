package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class SPacketEntity implements Packet<INetHandlerPlayClient> {
   protected int field_149074_a;
   protected int field_149072_b;
   protected int field_149073_c;
   protected int field_149070_d;
   protected byte field_149071_e;
   protected byte field_149068_f;
   protected boolean field_179743_g;
   protected boolean field_149069_g;

   public SPacketEntity() {
   }

   public SPacketEntity(int p_i46936_1_) {
      this.field_149074_a = p_i46936_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149074_a = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149074_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147259_a(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   public Entity func_149065_a(World p_149065_1_) {
      return p_149065_1_.func_73045_a(this.field_149074_a);
   }

   public int func_186952_a() {
      return this.field_149072_b;
   }

   public int func_186953_b() {
      return this.field_149073_c;
   }

   public int func_186951_c() {
      return this.field_149070_d;
   }

   public byte func_149066_f() {
      return this.field_149071_e;
   }

   public byte func_149063_g() {
      return this.field_149068_f;
   }

   public boolean func_149060_h() {
      return this.field_149069_g;
   }

   public boolean func_179742_g() {
      return this.field_179743_g;
   }

   public static class S15PacketEntityRelMove extends SPacketEntity {
      public S15PacketEntityRelMove() {
      }

      public S15PacketEntityRelMove(int p_i47083_1_, long p_i47083_2_, long p_i47083_4_, long p_i47083_6_, boolean p_i47083_8_) {
         super(p_i47083_1_);
         this.field_149072_b = (int)p_i47083_2_;
         this.field_149073_c = (int)p_i47083_4_;
         this.field_149070_d = (int)p_i47083_6_;
         this.field_179743_g = p_i47083_8_;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         super.func_148837_a(p_148837_1_);
         this.field_149072_b = p_148837_1_.readShort();
         this.field_149073_c = p_148837_1_.readShort();
         this.field_149070_d = p_148837_1_.readShort();
         this.field_179743_g = p_148837_1_.readBoolean();
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         super.func_148840_b(p_148840_1_);
         p_148840_1_.writeShort(this.field_149072_b);
         p_148840_1_.writeShort(this.field_149073_c);
         p_148840_1_.writeShort(this.field_149070_d);
         p_148840_1_.writeBoolean(this.field_179743_g);
      }
   }

   public static class S16PacketEntityLook extends SPacketEntity {
      public S16PacketEntityLook() {
         this.field_149069_g = true;
      }

      public S16PacketEntityLook(int p_i47081_1_, byte p_i47081_2_, byte p_i47081_3_, boolean p_i47081_4_) {
         super(p_i47081_1_);
         this.field_149071_e = p_i47081_2_;
         this.field_149068_f = p_i47081_3_;
         this.field_149069_g = true;
         this.field_179743_g = p_i47081_4_;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         super.func_148837_a(p_148837_1_);
         this.field_149071_e = p_148837_1_.readByte();
         this.field_149068_f = p_148837_1_.readByte();
         this.field_179743_g = p_148837_1_.readBoolean();
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         super.func_148840_b(p_148840_1_);
         p_148840_1_.writeByte(this.field_149071_e);
         p_148840_1_.writeByte(this.field_149068_f);
         p_148840_1_.writeBoolean(this.field_179743_g);
      }
   }

   public static class S17PacketEntityLookMove extends SPacketEntity {
      public S17PacketEntityLookMove() {
         this.field_149069_g = true;
      }

      public S17PacketEntityLookMove(int p_i47082_1_, long p_i47082_2_, long p_i47082_4_, long p_i47082_6_, byte p_i47082_8_, byte p_i47082_9_, boolean p_i47082_10_) {
         super(p_i47082_1_);
         this.field_149072_b = (int)p_i47082_2_;
         this.field_149073_c = (int)p_i47082_4_;
         this.field_149070_d = (int)p_i47082_6_;
         this.field_149071_e = p_i47082_8_;
         this.field_149068_f = p_i47082_9_;
         this.field_179743_g = p_i47082_10_;
         this.field_149069_g = true;
      }

      public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
         super.func_148837_a(p_148837_1_);
         this.field_149072_b = p_148837_1_.readShort();
         this.field_149073_c = p_148837_1_.readShort();
         this.field_149070_d = p_148837_1_.readShort();
         this.field_149071_e = p_148837_1_.readByte();
         this.field_149068_f = p_148837_1_.readByte();
         this.field_179743_g = p_148837_1_.readBoolean();
      }

      public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
         super.func_148840_b(p_148840_1_);
         p_148840_1_.writeShort(this.field_149072_b);
         p_148840_1_.writeShort(this.field_149073_c);
         p_148840_1_.writeShort(this.field_149070_d);
         p_148840_1_.writeByte(this.field_149071_e);
         p_148840_1_.writeByte(this.field_149068_f);
         p_148840_1_.writeBoolean(this.field_179743_g);
      }
   }
}
