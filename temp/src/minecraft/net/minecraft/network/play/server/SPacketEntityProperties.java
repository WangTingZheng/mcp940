package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityProperties implements Packet<INetHandlerPlayClient> {
   private int field_149445_a;
   private final List<SPacketEntityProperties.Snapshot> field_149444_b = Lists.<SPacketEntityProperties.Snapshot>newArrayList();

   public SPacketEntityProperties() {
   }

   public SPacketEntityProperties(int p_i46892_1_, Collection<IAttributeInstance> p_i46892_2_) {
      this.field_149445_a = p_i46892_1_;

      for(IAttributeInstance iattributeinstance : p_i46892_2_) {
         this.field_149444_b.add(new SPacketEntityProperties.Snapshot(iattributeinstance.func_111123_a().func_111108_a(), iattributeinstance.func_111125_b(), iattributeinstance.func_111122_c()));
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149445_a = p_148837_1_.func_150792_a();
      int i = p_148837_1_.readInt();

      for(int j = 0; j < i; ++j) {
         String s = p_148837_1_.func_150789_c(64);
         double d0 = p_148837_1_.readDouble();
         List<AttributeModifier> list = Lists.<AttributeModifier>newArrayList();
         int k = p_148837_1_.func_150792_a();

         for(int l = 0; l < k; ++l) {
            UUID uuid = p_148837_1_.func_179253_g();
            list.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", p_148837_1_.readDouble(), p_148837_1_.readByte()));
         }

         this.field_149444_b.add(new SPacketEntityProperties.Snapshot(s, d0, list));
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149445_a);
      p_148840_1_.writeInt(this.field_149444_b.size());

      for(SPacketEntityProperties.Snapshot spacketentityproperties$snapshot : this.field_149444_b) {
         p_148840_1_.func_180714_a(spacketentityproperties$snapshot.func_151409_a());
         p_148840_1_.writeDouble(spacketentityproperties$snapshot.func_151410_b());
         p_148840_1_.func_150787_b(spacketentityproperties$snapshot.func_151408_c().size());

         for(AttributeModifier attributemodifier : spacketentityproperties$snapshot.func_151408_c()) {
            p_148840_1_.func_179252_a(attributemodifier.func_111167_a());
            p_148840_1_.writeDouble(attributemodifier.func_111164_d());
            p_148840_1_.writeByte(attributemodifier.func_111169_c());
         }
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147290_a(this);
   }

   public int func_149442_c() {
      return this.field_149445_a;
   }

   public List<SPacketEntityProperties.Snapshot> func_149441_d() {
      return this.field_149444_b;
   }

   public class Snapshot {
      private final String field_151412_b;
      private final double field_151413_c;
      private final Collection<AttributeModifier> field_151411_d;

      public Snapshot(String p_i47075_2_, double p_i47075_3_, Collection<AttributeModifier> p_i47075_5_) {
         this.field_151412_b = p_i47075_2_;
         this.field_151413_c = p_i47075_3_;
         this.field_151411_d = p_i47075_5_;
      }

      public String func_151409_a() {
         return this.field_151412_b;
      }

      public double func_151410_b() {
         return this.field_151413_c;
      }

      public Collection<AttributeModifier> func_151408_c() {
         return this.field_151411_d;
      }
   }
}
