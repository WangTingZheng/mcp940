package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List<ISpectatorMenuObject> field_178672_a = Lists.<ISpectatorMenuObject>newArrayList();

   public TeleportToTeam() {
      Minecraft minecraft = Minecraft.func_71410_x();

      for(ScorePlayerTeam scoreplayerteam : minecraft.field_71441_e.func_96441_U().func_96525_g()) {
         this.field_178672_a.add(new TeleportToTeam.TeamSelectionObject(scoreplayerteam));
      }

   }

   public List<ISpectatorMenuObject> func_178669_a() {
      return this.field_178672_a;
   }

   public ITextComponent func_178670_b() {
      return new TextComponentTranslation("spectatorMenu.team_teleport.prompt", new Object[0]);
   }

   public void func_178661_a(SpectatorMenu p_178661_1_) {
      p_178661_1_.func_178647_a(this);
   }

   public ITextComponent func_178664_z_() {
      return new TextComponentTranslation("spectatorMenu.team_teleport", new Object[0]);
   }

   public void func_178663_a(float p_178663_1_, int p_178663_2_) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiSpectator.field_175269_a);
      Gui.func_146110_a(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public boolean func_178662_A_() {
      for(ISpectatorMenuObject ispectatormenuobject : this.field_178672_a) {
         if (ispectatormenuobject.func_178662_A_()) {
            return true;
         }
      }

      return false;
   }

   class TeamSelectionObject implements ISpectatorMenuObject {
      private final ScorePlayerTeam field_178676_b;
      private final ResourceLocation field_178677_c;
      private final List<NetworkPlayerInfo> field_178675_d;

      public TeamSelectionObject(ScorePlayerTeam p_i45492_2_) {
         this.field_178676_b = p_i45492_2_;
         this.field_178675_d = Lists.<NetworkPlayerInfo>newArrayList();

         for(String s : p_i45492_2_.func_96670_d()) {
            NetworkPlayerInfo networkplayerinfo = Minecraft.func_71410_x().func_147114_u().func_175104_a(s);
            if (networkplayerinfo != null) {
               this.field_178675_d.add(networkplayerinfo);
            }
         }

         if (this.field_178675_d.isEmpty()) {
            this.field_178677_c = DefaultPlayerSkin.func_177335_a();
         } else {
            String s1 = ((NetworkPlayerInfo)this.field_178675_d.get((new Random()).nextInt(this.field_178675_d.size()))).func_178845_a().getName();
            this.field_178677_c = AbstractClientPlayer.func_110311_f(s1);
            AbstractClientPlayer.func_110304_a(this.field_178677_c, s1);
         }

      }

      public void func_178661_a(SpectatorMenu p_178661_1_) {
         p_178661_1_.func_178647_a(new TeleportToPlayer(this.field_178675_d));
      }

      public ITextComponent func_178664_z_() {
         return new TextComponentString(this.field_178676_b.func_96669_c());
      }

      public void func_178663_a(float p_178663_1_, int p_178663_2_) {
         int i = -1;
         String s = FontRenderer.func_78282_e(this.field_178676_b.func_96668_e());
         if (s.length() >= 2) {
            i = Minecraft.func_71410_x().field_71466_p.func_175064_b(s.charAt(1));
         }

         if (i >= 0) {
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            Gui.func_73734_a(1, 1, 15, 15, MathHelper.func_180183_b(f * p_178663_1_, f1 * p_178663_1_, f2 * p_178663_1_) | p_178663_2_ << 24);
         }

         Minecraft.func_71410_x().func_110434_K().func_110577_a(this.field_178677_c);
         GlStateManager.func_179131_c(p_178663_1_, p_178663_1_, p_178663_1_, (float)p_178663_2_ / 255.0F);
         Gui.func_152125_a(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
         Gui.func_152125_a(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
      }

      public boolean func_178662_A_() {
         return !this.field_178675_d.isEmpty();
      }
   }
}
