package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAdvancementManager {
   private static final Logger field_192800_a = LogManager.getLogger();
   private final Minecraft field_192801_b;
   private final AdvancementList field_192802_c = new AdvancementList();
   private final Map<Advancement, AdvancementProgress> field_192803_d = Maps.<Advancement, AdvancementProgress>newHashMap();
   @Nullable
   private ClientAdvancementManager.IListener field_192804_e;
   @Nullable
   private Advancement field_194231_f;

   public ClientAdvancementManager(Minecraft p_i47380_1_) {
      this.field_192801_b = p_i47380_1_;
   }

   public void func_192799_a(SPacketAdvancementInfo p_192799_1_) {
      if (p_192799_1_.func_192602_d()) {
         this.field_192802_c.func_192087_a();
         this.field_192803_d.clear();
      }

      this.field_192802_c.func_192085_a(p_192799_1_.func_192600_b());
      this.field_192802_c.func_192083_a(p_192799_1_.func_192603_a());

      for(Entry<ResourceLocation, AdvancementProgress> entry : p_192799_1_.func_192604_c().entrySet()) {
         Advancement advancement = this.field_192802_c.func_192084_a(entry.getKey());
         if (advancement != null) {
            AdvancementProgress advancementprogress = entry.getValue();
            advancementprogress.func_192099_a(advancement.func_192073_f(), advancement.func_192074_h());
            this.field_192803_d.put(advancement, advancementprogress);
            if (this.field_192804_e != null) {
               this.field_192804_e.func_191933_a(advancement, advancementprogress);
            }

            if (!p_192799_1_.func_192602_d() && advancementprogress.func_192105_a() && advancement.func_192068_c() != null && advancement.func_192068_c().func_193223_h()) {
               this.field_192801_b.func_193033_an().func_192988_a(new AdvancementToast(advancement));
            }
         } else {
            field_192800_a.warn("Server informed client about progress for unknown advancement " + entry.getKey());
         }
      }

   }

   public AdvancementList func_194229_a() {
      return this.field_192802_c;
   }

   public void func_194230_a(@Nullable Advancement p_194230_1_, boolean p_194230_2_) {
      NetHandlerPlayClient nethandlerplayclient = this.field_192801_b.func_147114_u();
      if (nethandlerplayclient != null && p_194230_1_ != null && p_194230_2_) {
         nethandlerplayclient.func_147297_a(CPacketSeenAdvancements.func_194163_a(p_194230_1_));
      }

      if (this.field_194231_f != p_194230_1_) {
         this.field_194231_f = p_194230_1_;
         if (this.field_192804_e != null) {
            this.field_192804_e.func_193982_e(p_194230_1_);
         }
      }

   }

   public void func_192798_a(@Nullable ClientAdvancementManager.IListener p_192798_1_) {
      this.field_192804_e = p_192798_1_;
      this.field_192802_c.func_192086_a(p_192798_1_);
      if (p_192798_1_ != null) {
         for(Entry<Advancement, AdvancementProgress> entry : this.field_192803_d.entrySet()) {
            p_192798_1_.func_191933_a(entry.getKey(), entry.getValue());
         }

         p_192798_1_.func_193982_e(this.field_194231_f);
      }

   }

   public interface IListener extends AdvancementList.Listener {
      void func_191933_a(Advancement var1, AdvancementProgress var2);

      void func_193982_e(@Nullable Advancement var1);
   }
}
