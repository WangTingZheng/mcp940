package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;

public class GuiBossOverlay extends Gui {
   private static final ResourceLocation field_184058_a = new ResourceLocation("textures/gui/bars.png");
   private final Minecraft field_184059_f;
   private final Map<UUID, BossInfoClient> field_184060_g = Maps.<UUID, BossInfoClient>newLinkedHashMap();

   public GuiBossOverlay(Minecraft p_i46606_1_) {
      this.field_184059_f = p_i46606_1_;
   }

   public void func_184051_a() {
      if (!this.field_184060_g.isEmpty()) {
         ScaledResolution scaledresolution = new ScaledResolution(this.field_184059_f);
         int i = scaledresolution.func_78326_a();
         int j = 12;

         for(BossInfoClient bossinfoclient : this.field_184060_g.values()) {
            int k = i / 2 - 91;
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_184059_f.func_110434_K().func_110577_a(field_184058_a);
            this.func_184052_a(k, j, bossinfoclient);
            String s = bossinfoclient.func_186744_e().func_150254_d();
            this.field_184059_f.field_71466_p.func_175063_a(s, (float)(i / 2 - this.field_184059_f.field_71466_p.func_78256_a(s) / 2), (float)(j - 9), 16777215);
            j += 10 + this.field_184059_f.field_71466_p.field_78288_b;
            if (j >= scaledresolution.func_78328_b() / 3) {
               break;
            }
         }

      }
   }

   private void func_184052_a(int p_184052_1_, int p_184052_2_, BossInfo p_184052_3_) {
      this.func_73729_b(p_184052_1_, p_184052_2_, 0, p_184052_3_.func_186736_g().ordinal() * 5 * 2, 182, 5);
      if (p_184052_3_.func_186740_h() != BossInfo.Overlay.PROGRESS) {
         this.func_73729_b(p_184052_1_, p_184052_2_, 0, 80 + (p_184052_3_.func_186740_h().ordinal() - 1) * 5 * 2, 182, 5);
      }

      int i = (int)(p_184052_3_.func_186738_f() * 183.0F);
      if (i > 0) {
         this.func_73729_b(p_184052_1_, p_184052_2_, 0, p_184052_3_.func_186736_g().ordinal() * 5 * 2 + 5, i, 5);
         if (p_184052_3_.func_186740_h() != BossInfo.Overlay.PROGRESS) {
            this.func_73729_b(p_184052_1_, p_184052_2_, 0, 80 + (p_184052_3_.func_186740_h().ordinal() - 1) * 5 * 2 + 5, i, 5);
         }
      }

   }

   public void func_184055_a(SPacketUpdateBossInfo p_184055_1_) {
      if (p_184055_1_.func_186902_b() == SPacketUpdateBossInfo.Operation.ADD) {
         this.field_184060_g.put(p_184055_1_.func_186908_a(), new BossInfoClient(p_184055_1_));
      } else if (p_184055_1_.func_186902_b() == SPacketUpdateBossInfo.Operation.REMOVE) {
         this.field_184060_g.remove(p_184055_1_.func_186908_a());
      } else {
         ((BossInfoClient)this.field_184060_g.get(p_184055_1_.func_186908_a())).func_186765_a(p_184055_1_);
      }

   }

   public void func_184057_b() {
      this.field_184060_g.clear();
   }

   public boolean func_184054_d() {
      if (!this.field_184060_g.isEmpty()) {
         for(BossInfo bossinfo : this.field_184060_g.values()) {
            if (bossinfo.func_186747_j()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean func_184053_e() {
      if (!this.field_184060_g.isEmpty()) {
         for(BossInfo bossinfo : this.field_184060_g.values()) {
            if (bossinfo.func_186734_i()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean func_184056_f() {
      if (!this.field_184060_g.isEmpty()) {
         for(BossInfo bossinfo : this.field_184060_g.values()) {
            if (bossinfo.func_186748_k()) {
               return true;
            }
         }
      }

      return false;
   }
}
