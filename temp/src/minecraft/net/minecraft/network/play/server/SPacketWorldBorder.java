package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class SPacketWorldBorder implements Packet<INetHandlerPlayClient> {
   private SPacketWorldBorder.Action field_179795_a;
   private int field_179793_b;
   private double field_179794_c;
   private double field_179791_d;
   private double field_179792_e;
   private double field_179789_f;
   private long field_179790_g;
   private int field_179796_h;
   private int field_179797_i;

   public SPacketWorldBorder() {
   }

   public SPacketWorldBorder(WorldBorder p_i46921_1_, SPacketWorldBorder.Action p_i46921_2_) {
      this.field_179795_a = p_i46921_2_;
      this.field_179794_c = p_i46921_1_.func_177731_f();
      this.field_179791_d = p_i46921_1_.func_177721_g();
      this.field_179789_f = p_i46921_1_.func_177741_h();
      this.field_179792_e = p_i46921_1_.func_177751_j();
      this.field_179790_g = p_i46921_1_.func_177732_i();
      this.field_179793_b = p_i46921_1_.func_177722_l();
      this.field_179797_i = p_i46921_1_.func_177748_q();
      this.field_179796_h = p_i46921_1_.func_177740_p();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179795_a = (SPacketWorldBorder.Action)p_148837_1_.func_179257_a(SPacketWorldBorder.Action.class);
      switch(this.field_179795_a) {
      case SET_SIZE:
         this.field_179792_e = p_148837_1_.readDouble();
         break;
      case LERP_SIZE:
         this.field_179789_f = p_148837_1_.readDouble();
         this.field_179792_e = p_148837_1_.readDouble();
         this.field_179790_g = p_148837_1_.func_179260_f();
         break;
      case SET_CENTER:
         this.field_179794_c = p_148837_1_.readDouble();
         this.field_179791_d = p_148837_1_.readDouble();
         break;
      case SET_WARNING_BLOCKS:
         this.field_179797_i = p_148837_1_.func_150792_a();
         break;
      case SET_WARNING_TIME:
         this.field_179796_h = p_148837_1_.func_150792_a();
         break;
      case INITIALIZE:
         this.field_179794_c = p_148837_1_.readDouble();
         this.field_179791_d = p_148837_1_.readDouble();
         this.field_179789_f = p_148837_1_.readDouble();
         this.field_179792_e = p_148837_1_.readDouble();
         this.field_179790_g = p_148837_1_.func_179260_f();
         this.field_179793_b = p_148837_1_.func_150792_a();
         this.field_179797_i = p_148837_1_.func_150792_a();
         this.field_179796_h = p_148837_1_.func_150792_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_179795_a);
      switch(this.field_179795_a) {
      case SET_SIZE:
         p_148840_1_.writeDouble(this.field_179792_e);
         break;
      case LERP_SIZE:
         p_148840_1_.writeDouble(this.field_179789_f);
         p_148840_1_.writeDouble(this.field_179792_e);
         p_148840_1_.func_179254_b(this.field_179790_g);
         break;
      case SET_CENTER:
         p_148840_1_.writeDouble(this.field_179794_c);
         p_148840_1_.writeDouble(this.field_179791_d);
         break;
      case SET_WARNING_BLOCKS:
         p_148840_1_.func_150787_b(this.field_179797_i);
         break;
      case SET_WARNING_TIME:
         p_148840_1_.func_150787_b(this.field_179796_h);
         break;
      case INITIALIZE:
         p_148840_1_.writeDouble(this.field_179794_c);
         p_148840_1_.writeDouble(this.field_179791_d);
         p_148840_1_.writeDouble(this.field_179789_f);
         p_148840_1_.writeDouble(this.field_179792_e);
         p_148840_1_.func_179254_b(this.field_179790_g);
         p_148840_1_.func_150787_b(this.field_179793_b);
         p_148840_1_.func_150787_b(this.field_179797_i);
         p_148840_1_.func_150787_b(this.field_179796_h);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175093_a(this);
   }

   public void func_179788_a(WorldBorder p_179788_1_) {
      switch(this.field_179795_a) {
      case SET_SIZE:
         p_179788_1_.func_177750_a(this.field_179792_e);
         break;
      case LERP_SIZE:
         p_179788_1_.func_177738_a(this.field_179789_f, this.field_179792_e, this.field_179790_g);
         break;
      case SET_CENTER:
         p_179788_1_.func_177739_c(this.field_179794_c, this.field_179791_d);
         break;
      case SET_WARNING_BLOCKS:
         p_179788_1_.func_177747_c(this.field_179797_i);
         break;
      case SET_WARNING_TIME:
         p_179788_1_.func_177723_b(this.field_179796_h);
         break;
      case INITIALIZE:
         p_179788_1_.func_177739_c(this.field_179794_c, this.field_179791_d);
         if (this.field_179790_g > 0L) {
            p_179788_1_.func_177738_a(this.field_179789_f, this.field_179792_e, this.field_179790_g);
         } else {
            p_179788_1_.func_177750_a(this.field_179792_e);
         }

         p_179788_1_.func_177725_a(this.field_179793_b);
         p_179788_1_.func_177747_c(this.field_179797_i);
         p_179788_1_.func_177723_b(this.field_179796_h);
      }

   }

   public static enum Action {
      SET_SIZE,
      LERP_SIZE,
      SET_CENTER,
      INITIALIZE,
      SET_WARNING_TIME,
      SET_WARNING_BLOCKS;
   }
}
