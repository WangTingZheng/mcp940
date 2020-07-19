package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugRendererNeighborsUpdate implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_191554_a;
   private final Map<Long, Map<BlockPos, Integer>> field_191555_b = Maps.newTreeMap(Ordering.natural().reverse());

   DebugRendererNeighborsUpdate(Minecraft p_i47365_1_) {
      this.field_191554_a = p_i47365_1_;
   }

   public void func_191553_a(long p_191553_1_, BlockPos p_191553_3_) {
      Map<BlockPos, Integer> map = (Map)this.field_191555_b.get(Long.valueOf(p_191553_1_));
      if (map == null) {
         map = Maps.<BlockPos, Integer>newHashMap();
         this.field_191555_b.put(Long.valueOf(p_191553_1_), map);
      }

      Integer integer = map.get(p_191553_3_);
      if (integer == null) {
         integer = Integer.valueOf(0);
      }

      map.put(p_191553_3_, Integer.valueOf(integer.intValue() + 1));
   }

   public void func_190060_a(float p_190060_1_, long p_190060_2_) {
      long i = this.field_191554_a.field_71441_e.func_82737_E();
      EntityPlayer entityplayer = this.field_191554_a.field_71439_g;
      double d0 = entityplayer.field_70142_S + (entityplayer.field_70165_t - entityplayer.field_70142_S) * (double)p_190060_1_;
      double d1 = entityplayer.field_70137_T + (entityplayer.field_70163_u - entityplayer.field_70137_T) * (double)p_190060_1_;
      double d2 = entityplayer.field_70136_U + (entityplayer.field_70161_v - entityplayer.field_70136_U) * (double)p_190060_1_;
      World world = this.field_191554_a.field_71439_g.field_70170_p;
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      int j = 200;
      double d3 = 0.0025D;
      Set<BlockPos> set = Sets.<BlockPos>newHashSet();
      Map<BlockPos, Integer> map = Maps.<BlockPos, Integer>newHashMap();
      Iterator<Entry<Long, Map<BlockPos, Integer>>> iterator = this.field_191555_b.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> entry = (Entry)iterator.next();
         Long olong = entry.getKey();
         Map<BlockPos, Integer> map1 = (Map)entry.getValue();
         long k = i - olong.longValue();
         if (k > 200L) {
            iterator.remove();
         } else {
            for(Entry<BlockPos, Integer> entry1 : map1.entrySet()) {
               BlockPos blockpos = entry1.getKey();
               Integer integer = entry1.getValue();
               if (set.add(blockpos)) {
                  RenderGlobal.func_189697_a((new AxisAlignedBB(BlockPos.field_177992_a)).func_186662_g(0.002D).func_186664_h(0.0025D * (double)k).func_72317_d((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p()).func_72317_d(-d0, -d1, -d2), 1.0F, 1.0F, 1.0F, 1.0F);
                  map.put(blockpos, integer);
               }
            }
         }
      }

      for(Entry<BlockPos, Integer> entry2 : map.entrySet()) {
         BlockPos blockpos1 = entry2.getKey();
         Integer integer1 = entry2.getValue();
         DebugRenderer.func_191556_a(String.valueOf((Object)integer1), blockpos1.func_177958_n(), blockpos1.func_177956_o(), blockpos1.func_177952_p(), p_190060_1_, -1);
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
