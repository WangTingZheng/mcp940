package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketAdvancementInfo implements Packet<INetHandlerPlayClient> {
   private boolean field_192605_a;
   private Map<ResourceLocation, Advancement.Builder> field_192606_b;
   private Set<ResourceLocation> field_192607_c;
   private Map<ResourceLocation, AdvancementProgress> field_192608_d;

   public SPacketAdvancementInfo() {
   }

   public SPacketAdvancementInfo(boolean p_i47519_1_, Collection<Advancement> p_i47519_2_, Set<ResourceLocation> p_i47519_3_, Map<ResourceLocation, AdvancementProgress> p_i47519_4_) {
      this.field_192605_a = p_i47519_1_;
      this.field_192606_b = Maps.<ResourceLocation, Advancement.Builder>newHashMap();

      for(Advancement advancement : p_i47519_2_) {
         this.field_192606_b.put(advancement.func_192067_g(), advancement.func_192075_a());
      }

      this.field_192607_c = p_i47519_3_;
      this.field_192608_d = Maps.<ResourceLocation, AdvancementProgress>newHashMap(p_i47519_4_);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_191981_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_192605_a = p_148837_1_.readBoolean();
      this.field_192606_b = Maps.<ResourceLocation, Advancement.Builder>newHashMap();
      this.field_192607_c = Sets.<ResourceLocation>newLinkedHashSet();
      this.field_192608_d = Maps.<ResourceLocation, AdvancementProgress>newHashMap();
      int i = p_148837_1_.func_150792_a();

      for(int j = 0; j < i; ++j) {
         ResourceLocation resourcelocation = p_148837_1_.func_192575_l();
         Advancement.Builder advancement$builder = Advancement.Builder.func_192060_b(p_148837_1_);
         this.field_192606_b.put(resourcelocation, advancement$builder);
      }

      i = p_148837_1_.func_150792_a();

      for(int k = 0; k < i; ++k) {
         ResourceLocation resourcelocation1 = p_148837_1_.func_192575_l();
         this.field_192607_c.add(resourcelocation1);
      }

      i = p_148837_1_.func_150792_a();

      for(int l = 0; l < i; ++l) {
         ResourceLocation resourcelocation2 = p_148837_1_.func_192575_l();
         this.field_192608_d.put(resourcelocation2, AdvancementProgress.func_192100_b(p_148837_1_));
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeBoolean(this.field_192605_a);
      p_148840_1_.func_150787_b(this.field_192606_b.size());

      for(Entry<ResourceLocation, Advancement.Builder> entry : this.field_192606_b.entrySet()) {
         ResourceLocation resourcelocation = entry.getKey();
         Advancement.Builder advancement$builder = entry.getValue();
         p_148840_1_.func_192572_a(resourcelocation);
         advancement$builder.func_192057_a(p_148840_1_);
      }

      p_148840_1_.func_150787_b(this.field_192607_c.size());

      for(ResourceLocation resourcelocation1 : this.field_192607_c) {
         p_148840_1_.func_192572_a(resourcelocation1);
      }

      p_148840_1_.func_150787_b(this.field_192608_d.size());

      for(Entry<ResourceLocation, AdvancementProgress> entry1 : this.field_192608_d.entrySet()) {
         p_148840_1_.func_192572_a(entry1.getKey());
         ((AdvancementProgress)entry1.getValue()).func_192104_a(p_148840_1_);
      }

   }

   public Map<ResourceLocation, Advancement.Builder> func_192603_a() {
      return this.field_192606_b;
   }

   public Set<ResourceLocation> func_192600_b() {
      return this.field_192607_c;
   }

   public Map<ResourceLocation, AdvancementProgress> func_192604_c() {
      return this.field_192608_d;
   }

   public boolean func_192602_d() {
      return this.field_192605_a;
   }
}
