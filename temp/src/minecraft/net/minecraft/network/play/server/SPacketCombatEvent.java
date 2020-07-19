package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class SPacketCombatEvent implements Packet<INetHandlerPlayClient> {
   public SPacketCombatEvent.Event field_179776_a;
   public int field_179774_b;
   public int field_179775_c;
   public int field_179772_d;
   public ITextComponent field_179773_e;

   public SPacketCombatEvent() {
   }

   public SPacketCombatEvent(CombatTracker p_i46931_1_, SPacketCombatEvent.Event p_i46931_2_) {
      this(p_i46931_1_, p_i46931_2_, true);
   }

   public SPacketCombatEvent(CombatTracker p_i46932_1_, SPacketCombatEvent.Event p_i46932_2_, boolean p_i46932_3_) {
      this.field_179776_a = p_i46932_2_;
      EntityLivingBase entitylivingbase = p_i46932_1_.func_94550_c();
      switch(p_i46932_2_) {
      case END_COMBAT:
         this.field_179772_d = p_i46932_1_.func_180134_f();
         this.field_179775_c = entitylivingbase == null ? -1 : entitylivingbase.func_145782_y();
         break;
      case ENTITY_DIED:
         this.field_179774_b = p_i46932_1_.func_180135_h().func_145782_y();
         this.field_179775_c = entitylivingbase == null ? -1 : entitylivingbase.func_145782_y();
         if (p_i46932_3_) {
            this.field_179773_e = p_i46932_1_.func_151521_b();
         } else {
            this.field_179773_e = new TextComponentString("");
         }
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179776_a = (SPacketCombatEvent.Event)p_148837_1_.func_179257_a(SPacketCombatEvent.Event.class);
      if (this.field_179776_a == SPacketCombatEvent.Event.END_COMBAT) {
         this.field_179772_d = p_148837_1_.func_150792_a();
         this.field_179775_c = p_148837_1_.readInt();
      } else if (this.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         this.field_179774_b = p_148837_1_.func_150792_a();
         this.field_179775_c = p_148837_1_.readInt();
         this.field_179773_e = p_148837_1_.func_179258_d();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_179776_a);
      if (this.field_179776_a == SPacketCombatEvent.Event.END_COMBAT) {
         p_148840_1_.func_150787_b(this.field_179772_d);
         p_148840_1_.writeInt(this.field_179775_c);
      } else if (this.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         p_148840_1_.func_150787_b(this.field_179774_b);
         p_148840_1_.writeInt(this.field_179775_c);
         p_148840_1_.func_179256_a(this.field_179773_e);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175098_a(this);
   }

   public static enum Event {
      ENTER_COMBAT,
      END_COMBAT,
      ENTITY_DIED;
   }
}
