package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CPacketUseEntity implements Packet<INetHandlerPlayServer> {
   private int field_149567_a;
   private CPacketUseEntity.Action field_149566_b;
   private Vec3d field_179713_c;
   private EnumHand field_186995_d;

   public CPacketUseEntity() {
   }

   public CPacketUseEntity(Entity p_i46877_1_) {
      this.field_149567_a = p_i46877_1_.func_145782_y();
      this.field_149566_b = CPacketUseEntity.Action.ATTACK;
   }

   public CPacketUseEntity(Entity p_i46878_1_, EnumHand p_i46878_2_) {
      this.field_149567_a = p_i46878_1_.func_145782_y();
      this.field_149566_b = CPacketUseEntity.Action.INTERACT;
      this.field_186995_d = p_i46878_2_;
   }

   public CPacketUseEntity(Entity p_i47098_1_, EnumHand p_i47098_2_, Vec3d p_i47098_3_) {
      this.field_149567_a = p_i47098_1_.func_145782_y();
      this.field_149566_b = CPacketUseEntity.Action.INTERACT_AT;
      this.field_186995_d = p_i47098_2_;
      this.field_179713_c = p_i47098_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149567_a = p_148837_1_.func_150792_a();
      this.field_149566_b = (CPacketUseEntity.Action)p_148837_1_.func_179257_a(CPacketUseEntity.Action.class);
      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         this.field_179713_c = new Vec3d((double)p_148837_1_.readFloat(), (double)p_148837_1_.readFloat(), (double)p_148837_1_.readFloat());
      }

      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT || this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         this.field_186995_d = (EnumHand)p_148837_1_.func_179257_a(EnumHand.class);
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149567_a);
      p_148840_1_.func_179249_a(this.field_149566_b);
      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         p_148840_1_.writeFloat((float)this.field_179713_c.field_72450_a);
         p_148840_1_.writeFloat((float)this.field_179713_c.field_72448_b);
         p_148840_1_.writeFloat((float)this.field_179713_c.field_72449_c);
      }

      if (this.field_149566_b == CPacketUseEntity.Action.INTERACT || this.field_149566_b == CPacketUseEntity.Action.INTERACT_AT) {
         p_148840_1_.func_179249_a(this.field_186995_d);
      }

   }

   public void func_148833_a(INetHandlerPlayServer p_148833_1_) {
      p_148833_1_.func_147340_a(this);
   }

   @Nullable
   public Entity func_149564_a(World p_149564_1_) {
      return p_149564_1_.func_73045_a(this.field_149567_a);
   }

   public CPacketUseEntity.Action func_149565_c() {
      return this.field_149566_b;
   }

   public EnumHand func_186994_b() {
      return this.field_186995_d;
   }

   public Vec3d func_179712_b() {
      return this.field_179713_c;
   }

   public static enum Action {
      INTERACT,
      ATTACK,
      INTERACT_AT;
   }
}
