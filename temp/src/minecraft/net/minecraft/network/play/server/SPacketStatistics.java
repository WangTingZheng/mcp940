package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class SPacketStatistics implements Packet<INetHandlerPlayClient> {
   private Map<StatBase, Integer> field_148976_a;

   public SPacketStatistics() {
   }

   public SPacketStatistics(Map<StatBase, Integer> p_i46969_1_) {
      this.field_148976_a = p_i46969_1_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147293_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      int i = p_148837_1_.func_150792_a();
      this.field_148976_a = Maps.<StatBase, Integer>newHashMap();

      for(int j = 0; j < i; ++j) {
         StatBase statbase = StatList.func_151177_a(p_148837_1_.func_150789_c(32767));
         int k = p_148837_1_.func_150792_a();
         if (statbase != null) {
            this.field_148976_a.put(statbase, Integer.valueOf(k));
         }
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_148976_a.size());

      for(Entry<StatBase, Integer> entry : this.field_148976_a.entrySet()) {
         p_148840_1_.func_180714_a((entry.getKey()).field_75975_e);
         p_148840_1_.func_150787_b(((Integer)entry.getValue()).intValue());
      }

   }

   public Map<StatBase, Integer> func_148974_c() {
      return this.field_148976_a;
   }
}
