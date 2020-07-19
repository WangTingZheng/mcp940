package net.minecraft.client.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
   private static final Logger field_148304_a = LogManager.getLogger();
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
   private static final ResourceLocation field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
   private final GuiMultiplayer field_148303_c;
   private final Minecraft field_148300_d;
   private final ServerData field_148301_e;
   private final ResourceLocation field_148306_i;
   private String field_148299_g;
   private DynamicTexture field_148305_h;
   private long field_148298_f;

   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData p_i45048_2_) {
      this.field_148303_c = p_i45048_1_;
      this.field_148301_e = p_i45048_2_;
      this.field_148300_d = Minecraft.func_71410_x();
      this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.field_78845_b + "/icon");
      this.field_148305_h = (DynamicTexture)this.field_148300_d.func_110434_K().func_110581_b(this.field_148306_i);
   }

   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
      if (!this.field_148301_e.field_78841_f) {
         this.field_148301_e.field_78841_f = true;
         this.field_148301_e.field_78844_e = -2L;
         this.field_148301_e.field_78843_d = "";
         this.field_148301_e.field_78846_c = "";
         field_148302_b.submit(new Runnable() {
            public void run() {
               try {
                  ServerListEntryNormal.this.field_148303_c.func_146789_i().func_147224_a(ServerListEntryNormal.this.field_148301_e);
               } catch (UnknownHostException var2) {
                  ServerListEntryNormal.this.field_148301_e.field_78844_e = -1L;
                  ServerListEntryNormal.this.field_148301_e.field_78843_d = TextFormatting.DARK_RED + I18n.func_135052_a("multiplayer.status.cannot_resolve");
               } catch (Exception var3) {
                  ServerListEntryNormal.this.field_148301_e.field_78844_e = -1L;
                  ServerListEntryNormal.this.field_148301_e.field_78843_d = TextFormatting.DARK_RED + I18n.func_135052_a("multiplayer.status.cannot_connect");
               }

            }
         });
      }

      boolean flag = this.field_148301_e.field_82821_f > 335;
      boolean flag1 = this.field_148301_e.field_82821_f < 335;
      boolean flag2 = flag || flag1;
      this.field_148300_d.field_71466_p.func_78276_b(this.field_148301_e.field_78847_a, p_192634_2_ + 32 + 3, p_192634_3_ + 1, 16777215);
      List<String> list = this.field_148300_d.field_71466_p.func_78271_c(this.field_148301_e.field_78843_d, p_192634_4_ - 32 - 2);

      for(int i = 0; i < Math.min(list.size(), 2); ++i) {
         this.field_148300_d.field_71466_p.func_78276_b(list.get(i), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + this.field_148300_d.field_71466_p.field_78288_b * i, 8421504);
      }

      String s2 = flag2 ? TextFormatting.DARK_RED + this.field_148301_e.field_82822_g : this.field_148301_e.field_78846_c;
      int j = this.field_148300_d.field_71466_p.func_78256_a(s2);
      this.field_148300_d.field_71466_p.func_78276_b(s2, p_192634_2_ + p_192634_4_ - j - 15 - 2, p_192634_3_ + 1, 8421504);
      int k = 0;
      String s = null;
      int l;
      String s1;
      if (flag2) {
         l = 5;
         s1 = I18n.func_135052_a(flag ? "multiplayer.status.client_out_of_date" : "multiplayer.status.server_out_of_date");
         s = this.field_148301_e.field_147412_i;
      } else if (this.field_148301_e.field_78841_f && this.field_148301_e.field_78844_e != -2L) {
         if (this.field_148301_e.field_78844_e < 0L) {
            l = 5;
         } else if (this.field_148301_e.field_78844_e < 150L) {
            l = 0;
         } else if (this.field_148301_e.field_78844_e < 300L) {
            l = 1;
         } else if (this.field_148301_e.field_78844_e < 600L) {
            l = 2;
         } else if (this.field_148301_e.field_78844_e < 1000L) {
            l = 3;
         } else {
            l = 4;
         }

         if (this.field_148301_e.field_78844_e < 0L) {
            s1 = I18n.func_135052_a("multiplayer.status.no_connection");
         } else {
            s1 = this.field_148301_e.field_78844_e + "ms";
            s = this.field_148301_e.field_147412_i;
         }
      } else {
         k = 1;
         l = (int)(Minecraft.func_71386_F() / 100L + (long)(p_192634_1_ * 2) & 7L);
         if (l > 4) {
            l = 8 - l;
         }

         s1 = I18n.func_135052_a("multiplayer.status.pinging");
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_148300_d.func_110434_K().func_110577_a(Gui.field_110324_m);
      Gui.func_146110_a(p_192634_2_ + p_192634_4_ - 15, p_192634_3_, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256.0F, 256.0F);
      if (this.field_148301_e.func_147409_e() != null && !this.field_148301_e.func_147409_e().equals(this.field_148299_g)) {
         this.field_148299_g = this.field_148301_e.func_147409_e();
         this.func_148297_b();
         this.field_148303_c.func_146795_p().func_78855_b();
      }

      if (this.field_148305_h != null) {
         this.func_178012_a(p_192634_2_, p_192634_3_, this.field_148306_i);
      } else {
         this.func_178012_a(p_192634_2_, p_192634_3_, field_178015_c);
      }

      int i1 = p_192634_6_ - p_192634_2_;
      int j1 = p_192634_7_ - p_192634_3_;
      if (i1 >= p_192634_4_ - 15 && i1 <= p_192634_4_ - 5 && j1 >= 0 && j1 <= 8) {
         this.field_148303_c.func_146793_a(s1);
      } else if (i1 >= p_192634_4_ - j - 15 - 2 && i1 <= p_192634_4_ - 15 - 2 && j1 >= 0 && j1 <= 8) {
         this.field_148303_c.func_146793_a(s);
      }

      if (this.field_148300_d.field_71474_y.field_85185_A || p_192634_8_) {
         this.field_148300_d.func_110434_K().func_110577_a(field_178014_d);
         Gui.func_73734_a(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int k1 = p_192634_6_ - p_192634_2_;
         int l1 = p_192634_7_ - p_192634_3_;
         if (this.func_178013_b()) {
            if (k1 < 32 && k1 > 16) {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175392_a(this, p_192634_1_)) {
            if (k1 < 16 && l1 < 16) {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175394_b(this, p_192634_1_)) {
            if (k1 < 16 && l1 > 16) {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(p_192634_2_, p_192634_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }

   }

   protected void func_178012_a(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
      this.field_148300_d.func_110434_K().func_110577_a(p_178012_3_);
      GlStateManager.func_179147_l();
      Gui.func_146110_a(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.func_179084_k();
   }

   private boolean func_178013_b() {
      return true;
   }

   private void func_148297_b() {
      if (this.field_148301_e.func_147409_e() == null) {
         this.field_148300_d.func_110434_K().func_147645_c(this.field_148306_i);
         this.field_148305_h = null;
      } else {
         ByteBuf bytebuf = Unpooled.copiedBuffer((CharSequence)this.field_148301_e.func_147409_e(), StandardCharsets.UTF_8);
         ByteBuf bytebuf1 = null;

         BufferedImage bufferedimage;
         label99: {
            try {
               bytebuf1 = Base64.decode(bytebuf);
               bufferedimage = TextureUtil.func_177053_a(new ByteBufInputStream(bytebuf1));
               Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
               Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
               break label99;
            } catch (Throwable throwable) {
               field_148304_a.error("Invalid icon for server {} ({})", this.field_148301_e.field_78847_a, this.field_148301_e.field_78845_b, throwable);
               this.field_148301_e.func_147407_a((String)null);
            } finally {
               bytebuf.release();
               if (bytebuf1 != null) {
                  bytebuf1.release();
               }

            }

            return;
         }

         if (this.field_148305_h == null) {
            this.field_148305_h = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
            this.field_148300_d.func_110434_K().func_110579_a(this.field_148306_i, this.field_148305_h);
         }

         bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.field_148305_h.func_110565_c(), 0, bufferedimage.getWidth());
         this.field_148305_h.func_110564_a();
      }

   }

   public boolean func_148278_a(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
      if (p_148278_5_ <= 32) {
         if (p_148278_5_ < 32 && p_148278_5_ > 16 && this.func_178013_b()) {
            this.field_148303_c.func_146790_a(p_148278_1_);
            this.field_148303_c.func_146796_h();
            return true;
         }

         if (p_148278_5_ < 16 && p_148278_6_ < 16 && this.field_148303_c.func_175392_a(this, p_148278_1_)) {
            this.field_148303_c.func_175391_a(this, p_148278_1_, GuiScreen.func_146272_n());
            return true;
         }

         if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.field_148303_c.func_175394_b(this, p_148278_1_)) {
            this.field_148303_c.func_175393_b(this, p_148278_1_, GuiScreen.func_146272_n());
            return true;
         }
      }

      this.field_148303_c.func_146790_a(p_148278_1_);
      if (Minecraft.func_71386_F() - this.field_148298_f < 250L) {
         this.field_148303_c.func_146796_h();
      }

      this.field_148298_f = Minecraft.func_71386_F();
      return false;
   }

   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
   }

   public void func_148277_b(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
   }

   public ServerData func_148296_a() {
      return this.field_148301_e;
   }
}
