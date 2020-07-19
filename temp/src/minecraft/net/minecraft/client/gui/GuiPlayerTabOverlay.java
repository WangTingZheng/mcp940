package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class GuiPlayerTabOverlay extends Gui {
   private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
   private final Minecraft field_175250_f;
   private final GuiIngame field_175251_g;
   private ITextComponent field_175255_h;
   private ITextComponent field_175256_i;
   private long field_175253_j;
   private boolean field_175254_k;

   public GuiPlayerTabOverlay(Minecraft p_i45529_1_, GuiIngame p_i45529_2_) {
      this.field_175250_f = p_i45529_1_;
      this.field_175251_g = p_i45529_2_;
   }

   public String func_175243_a(NetworkPlayerInfo p_175243_1_) {
      return p_175243_1_.func_178854_k() != null ? p_175243_1_.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a(p_175243_1_.func_178850_i(), p_175243_1_.func_178845_a().getName());
   }

   public void func_175246_a(boolean p_175246_1_) {
      if (p_175246_1_ && !this.field_175254_k) {
         this.field_175253_j = Minecraft.func_71386_F();
      }

      this.field_175254_k = p_175246_1_;
   }

   public void func_175249_a(int p_175249_1_, Scoreboard p_175249_2_, @Nullable ScoreObjective p_175249_3_) {
      NetHandlerPlayClient nethandlerplayclient = this.field_175250_f.field_71439_g.field_71174_a;
      List<NetworkPlayerInfo> list = field_175252_a.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.func_175106_d());
      int i = 0;
      int j = 0;

      for(NetworkPlayerInfo networkplayerinfo : list) {
         int k = this.field_175250_f.field_71466_p.func_78256_a(this.func_175243_a(networkplayerinfo));
         i = Math.max(i, k);
         if (p_175249_3_ != null && p_175249_3_.func_178766_e() != IScoreCriteria.EnumRenderType.HEARTS) {
            k = this.field_175250_f.field_71466_p.func_78256_a(" " + p_175249_2_.func_96529_a(networkplayerinfo.func_178845_a().getName(), p_175249_3_).func_96652_c());
            j = Math.max(j, k);
         }
      }

      list = list.subList(0, Math.min(list.size(), 80));
      int l3 = list.size();
      int i4 = l3;

      int j4;
      for(j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
         ++j4;
      }

      boolean flag = this.field_175250_f.func_71387_A() || this.field_175250_f.func_147114_u().func_147298_b().func_179292_f();
      int l;
      if (p_175249_3_ != null) {
         if (p_175249_3_.func_178766_e() == IScoreCriteria.EnumRenderType.HEARTS) {
            l = 90;
         } else {
            l = j;
         }
      } else {
         l = 0;
      }

      int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), p_175249_1_ - 50) / j4;
      int j1 = p_175249_1_ / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
      int k1 = 10;
      int l1 = i1 * j4 + (j4 - 1) * 5;
      List<String> list1 = null;
      if (this.field_175256_i != null) {
         list1 = this.field_175250_f.field_71466_p.func_78271_c(this.field_175256_i.func_150254_d(), p_175249_1_ - 50);

         for(String s : list1) {
            l1 = Math.max(l1, this.field_175250_f.field_71466_p.func_78256_a(s));
         }
      }

      List<String> list2 = null;
      if (this.field_175255_h != null) {
         list2 = this.field_175250_f.field_71466_p.func_78271_c(this.field_175255_h.func_150254_d(), p_175249_1_ - 50);

         for(String s1 : list2) {
            l1 = Math.max(l1, this.field_175250_f.field_71466_p.func_78256_a(s1));
         }
      }

      if (list1 != null) {
         func_73734_a(p_175249_1_ / 2 - l1 / 2 - 1, k1 - 1, p_175249_1_ / 2 + l1 / 2 + 1, k1 + list1.size() * this.field_175250_f.field_71466_p.field_78288_b, Integer.MIN_VALUE);

         for(String s2 : list1) {
            int i2 = this.field_175250_f.field_71466_p.func_78256_a(s2);
            this.field_175250_f.field_71466_p.func_175063_a(s2, (float)(p_175249_1_ / 2 - i2 / 2), (float)k1, -1);
            k1 += this.field_175250_f.field_71466_p.field_78288_b;
         }

         ++k1;
      }

      func_73734_a(p_175249_1_ / 2 - l1 / 2 - 1, k1 - 1, p_175249_1_ / 2 + l1 / 2 + 1, k1 + i4 * 9, Integer.MIN_VALUE);

      for(int k4 = 0; k4 < l3; ++k4) {
         int l4 = k4 / i4;
         int i5 = k4 % i4;
         int j2 = j1 + l4 * i1 + l4 * 5;
         int k2 = k1 + i5 * 9;
         func_73734_a(j2, k2, j2 + i1, k2 + 8, 553648127);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179141_d();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
         if (k4 < list.size()) {
            NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
            GameProfile gameprofile = networkplayerinfo1.func_178845_a();
            if (flag) {
               EntityPlayer entityplayer = this.field_175250_f.field_71441_e.func_152378_a(gameprofile.getId());
               boolean flag1 = entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
               this.field_175250_f.func_110434_K().func_110577_a(networkplayerinfo1.func_178837_g());
               int l2 = 8 + (flag1 ? 8 : 0);
               int i3 = 8 * (flag1 ? -1 : 1);
               Gui.func_152125_a(j2, k2, 8.0F, (float)l2, 8, i3, 8, 8, 64.0F, 64.0F);
               if (entityplayer != null && entityplayer.func_175148_a(EnumPlayerModelParts.HAT)) {
                  int j3 = 8 + (flag1 ? 8 : 0);
                  int k3 = 8 * (flag1 ? -1 : 1);
                  Gui.func_152125_a(j2, k2, 40.0F, (float)j3, 8, k3, 8, 8, 64.0F, 64.0F);
               }

               j2 += 9;
            }

            String s4 = this.func_175243_a(networkplayerinfo1);
            if (networkplayerinfo1.func_178848_b() == GameType.SPECTATOR) {
               this.field_175250_f.field_71466_p.func_175063_a(TextFormatting.ITALIC + s4, (float)j2, (float)k2, -1862270977);
            } else {
               this.field_175250_f.field_71466_p.func_175063_a(s4, (float)j2, (float)k2, -1);
            }

            if (p_175249_3_ != null && networkplayerinfo1.func_178848_b() != GameType.SPECTATOR) {
               int k5 = j2 + i + 1;
               int l5 = k5 + l;
               if (l5 - k5 > 5) {
                  this.func_175247_a(p_175249_3_, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
               }
            }

            this.func_175245_a(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
         }
      }

      if (list2 != null) {
         k1 = k1 + i4 * 9 + 1;
         func_73734_a(p_175249_1_ / 2 - l1 / 2 - 1, k1 - 1, p_175249_1_ / 2 + l1 / 2 + 1, k1 + list2.size() * this.field_175250_f.field_71466_p.field_78288_b, Integer.MIN_VALUE);

         for(String s3 : list2) {
            int j5 = this.field_175250_f.field_71466_p.func_78256_a(s3);
            this.field_175250_f.field_71466_p.func_175063_a(s3, (float)(p_175249_1_ / 2 - j5 / 2), (float)k1, -1);
            k1 += this.field_175250_f.field_71466_p.field_78288_b;
         }
      }

   }

   protected void func_175245_a(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo p_175245_4_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_175250_f.func_110434_K().func_110577_a(field_110324_m);
      int i = 0;
      int j;
      if (p_175245_4_.func_178853_c() < 0) {
         j = 5;
      } else if (p_175245_4_.func_178853_c() < 150) {
         j = 0;
      } else if (p_175245_4_.func_178853_c() < 300) {
         j = 1;
      } else if (p_175245_4_.func_178853_c() < 600) {
         j = 2;
      } else if (p_175245_4_.func_178853_c() < 1000) {
         j = 3;
      } else {
         j = 4;
      }

      this.field_73735_i += 100.0F;
      this.func_73729_b(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0, 176 + j * 8, 10, 8);
      this.field_73735_i -= 100.0F;
   }

   private void func_175247_a(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_) {
      int i = p_175247_1_.func_96682_a().func_96529_a(p_175247_3_, p_175247_1_).func_96652_c();
      if (p_175247_1_.func_178766_e() == IScoreCriteria.EnumRenderType.HEARTS) {
         this.field_175250_f.func_110434_K().func_110577_a(field_110324_m);
         if (this.field_175253_j == p_175247_6_.func_178855_p()) {
            if (i < p_175247_6_.func_178835_l()) {
               p_175247_6_.func_178846_a(Minecraft.func_71386_F());
               p_175247_6_.func_178844_b((long)(this.field_175251_g.func_73834_c() + 20));
            } else if (i > p_175247_6_.func_178835_l()) {
               p_175247_6_.func_178846_a(Minecraft.func_71386_F());
               p_175247_6_.func_178844_b((long)(this.field_175251_g.func_73834_c() + 10));
            }
         }

         if (Minecraft.func_71386_F() - p_175247_6_.func_178847_n() > 1000L || this.field_175253_j != p_175247_6_.func_178855_p()) {
            p_175247_6_.func_178836_b(i);
            p_175247_6_.func_178857_c(i);
            p_175247_6_.func_178846_a(Minecraft.func_71386_F());
         }

         p_175247_6_.func_178843_c(this.field_175253_j);
         p_175247_6_.func_178836_b(i);
         int j = MathHelper.func_76123_f((float)Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
         int k = Math.max(MathHelper.func_76123_f((float)(i / 2)), Math.max(MathHelper.func_76123_f((float)(p_175247_6_.func_178860_m() / 2)), 10));
         boolean flag = p_175247_6_.func_178858_o() > (long)this.field_175251_g.func_73834_c() && (p_175247_6_.func_178858_o() - (long)this.field_175251_g.func_73834_c()) / 3L % 2L == 1L;
         if (j > 0) {
            float f = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0F);
            if (f > 3.0F) {
               for(int l = j; l < k; ++l) {
                  this.func_175174_a((float)p_175247_4_ + (float)l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
               }

               for(int j1 = 0; j1 < j; ++j1) {
                  this.func_175174_a((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                  if (flag) {
                     if (j1 * 2 + 1 < p_175247_6_.func_178860_m()) {
                        this.func_175174_a((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 70, 0, 9, 9);
                     }

                     if (j1 * 2 + 1 == p_175247_6_.func_178860_m()) {
                        this.func_175174_a((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 79, 0, 9, 9);
                     }
                  }

                  if (j1 * 2 + 1 < i) {
                     this.func_175174_a((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (j1 * 2 + 1 == i) {
                     this.func_175174_a((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float f1 = MathHelper.func_76131_a((float)i / 20.0F, 0.0F, 1.0F);
               int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
               String s = "" + (float)i / 2.0F;
               if (p_175247_5_ - this.field_175250_f.field_71466_p.func_78256_a(s + "hp") >= p_175247_4_) {
                  s = s + "hp";
               }

               this.field_175250_f.field_71466_p.func_175063_a(s, (float)((p_175247_5_ + p_175247_4_) / 2 - this.field_175250_f.field_71466_p.func_78256_a(s) / 2), (float)p_175247_2_, i1);
            }
         }
      } else {
         String s1 = TextFormatting.YELLOW + "" + i;
         this.field_175250_f.field_71466_p.func_175063_a(s1, (float)(p_175247_5_ - this.field_175250_f.field_71466_p.func_78256_a(s1)), (float)p_175247_2_, 16777215);
      }

   }

   public void func_175248_a(@Nullable ITextComponent p_175248_1_) {
      this.field_175255_h = p_175248_1_;
   }

   public void func_175244_b(@Nullable ITextComponent p_175244_1_) {
      this.field_175256_i = p_175244_1_;
   }

   public void func_181030_a() {
      this.field_175256_i = null;
      this.field_175255_h = null;
   }

   static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
         ScorePlayerTeam scoreplayerteam = p_compare_1_.func_178850_i();
         ScorePlayerTeam scoreplayerteam1 = p_compare_2_.func_178850_i();
         return ComparisonChain.start().compareTrueFirst(p_compare_1_.func_178848_b() != GameType.SPECTATOR, p_compare_2_.func_178848_b() != GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.func_96661_b() : "", scoreplayerteam1 != null ? scoreplayerteam1.func_96661_b() : "").compare(p_compare_1_.func_178845_a().getName(), p_compare_2_.func_178845_a().getName()).result();
      }
   }
}
