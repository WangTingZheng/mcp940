package net.minecraft.network.play.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;

public class SPacketPlayerListItem implements Packet<INetHandlerPlayClient> {
   private SPacketPlayerListItem.Action field_179770_a;
   private final List<SPacketPlayerListItem.AddPlayerData> field_179769_b = Lists.<SPacketPlayerListItem.AddPlayerData>newArrayList();

   public SPacketPlayerListItem() {
   }

   public SPacketPlayerListItem(SPacketPlayerListItem.Action p_i46929_1_, EntityPlayerMP... p_i46929_2_) {
      this.field_179770_a = p_i46929_1_;

      for(EntityPlayerMP entityplayermp : p_i46929_2_) {
         this.field_179769_b.add(new SPacketPlayerListItem.AddPlayerData(entityplayermp.func_146103_bH(), entityplayermp.field_71138_i, entityplayermp.field_71134_c.func_73081_b(), entityplayermp.func_175396_E()));
      }

   }

   public SPacketPlayerListItem(SPacketPlayerListItem.Action p_i46930_1_, Iterable<EntityPlayerMP> p_i46930_2_) {
      this.field_179770_a = p_i46930_1_;

      for(EntityPlayerMP entityplayermp : p_i46930_2_) {
         this.field_179769_b.add(new SPacketPlayerListItem.AddPlayerData(entityplayermp.func_146103_bH(), entityplayermp.field_71138_i, entityplayermp.field_71134_c.func_73081_b(), entityplayermp.func_175396_E()));
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179770_a = (SPacketPlayerListItem.Action)p_148837_1_.func_179257_a(SPacketPlayerListItem.Action.class);
      int i = p_148837_1_.func_150792_a();

      for(int j = 0; j < i; ++j) {
         GameProfile gameprofile = null;
         int k = 0;
         GameType gametype = null;
         ITextComponent itextcomponent = null;
         switch(this.field_179770_a) {
         case ADD_PLAYER:
            gameprofile = new GameProfile(p_148837_1_.func_179253_g(), p_148837_1_.func_150789_c(16));
            int l = p_148837_1_.func_150792_a();
            int i1 = 0;

            for(; i1 < l; ++i1) {
               String s = p_148837_1_.func_150789_c(32767);
               String s1 = p_148837_1_.func_150789_c(32767);
               if (p_148837_1_.readBoolean()) {
                  gameprofile.getProperties().put(s, new Property(s, s1, p_148837_1_.func_150789_c(32767)));
               } else {
                  gameprofile.getProperties().put(s, new Property(s, s1));
               }
            }

            gametype = GameType.func_77146_a(p_148837_1_.func_150792_a());
            k = p_148837_1_.func_150792_a();
            if (p_148837_1_.readBoolean()) {
               itextcomponent = p_148837_1_.func_179258_d();
            }
            break;
         case UPDATE_GAME_MODE:
            gameprofile = new GameProfile(p_148837_1_.func_179253_g(), (String)null);
            gametype = GameType.func_77146_a(p_148837_1_.func_150792_a());
            break;
         case UPDATE_LATENCY:
            gameprofile = new GameProfile(p_148837_1_.func_179253_g(), (String)null);
            k = p_148837_1_.func_150792_a();
            break;
         case UPDATE_DISPLAY_NAME:
            gameprofile = new GameProfile(p_148837_1_.func_179253_g(), (String)null);
            if (p_148837_1_.readBoolean()) {
               itextcomponent = p_148837_1_.func_179258_d();
            }
            break;
         case REMOVE_PLAYER:
            gameprofile = new GameProfile(p_148837_1_.func_179253_g(), (String)null);
         }

         this.field_179769_b.add(new SPacketPlayerListItem.AddPlayerData(gameprofile, k, gametype, itextcomponent));
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_179770_a);
      p_148840_1_.func_150787_b(this.field_179769_b.size());

      for(SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata : this.field_179769_b) {
         switch(this.field_179770_a) {
         case ADD_PLAYER:
            p_148840_1_.func_179252_a(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
            p_148840_1_.func_180714_a(spacketplayerlistitem$addplayerdata.func_179962_a().getName());
            p_148840_1_.func_150787_b(spacketplayerlistitem$addplayerdata.func_179962_a().getProperties().size());

            for(Property property : spacketplayerlistitem$addplayerdata.func_179962_a().getProperties().values()) {
               p_148840_1_.func_180714_a(property.getName());
               p_148840_1_.func_180714_a(property.getValue());
               if (property.hasSignature()) {
                  p_148840_1_.writeBoolean(true);
                  p_148840_1_.func_180714_a(property.getSignature());
               } else {
                  p_148840_1_.writeBoolean(false);
               }
            }

            p_148840_1_.func_150787_b(spacketplayerlistitem$addplayerdata.func_179960_c().func_77148_a());
            p_148840_1_.func_150787_b(spacketplayerlistitem$addplayerdata.func_179963_b());
            if (spacketplayerlistitem$addplayerdata.func_179961_d() == null) {
               p_148840_1_.writeBoolean(false);
            } else {
               p_148840_1_.writeBoolean(true);
               p_148840_1_.func_179256_a(spacketplayerlistitem$addplayerdata.func_179961_d());
            }
            break;
         case UPDATE_GAME_MODE:
            p_148840_1_.func_179252_a(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
            p_148840_1_.func_150787_b(spacketplayerlistitem$addplayerdata.func_179960_c().func_77148_a());
            break;
         case UPDATE_LATENCY:
            p_148840_1_.func_179252_a(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
            p_148840_1_.func_150787_b(spacketplayerlistitem$addplayerdata.func_179963_b());
            break;
         case UPDATE_DISPLAY_NAME:
            p_148840_1_.func_179252_a(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
            if (spacketplayerlistitem$addplayerdata.func_179961_d() == null) {
               p_148840_1_.writeBoolean(false);
            } else {
               p_148840_1_.writeBoolean(true);
               p_148840_1_.func_179256_a(spacketplayerlistitem$addplayerdata.func_179961_d());
            }
            break;
         case REMOVE_PLAYER:
            p_148840_1_.func_179252_a(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
         }
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147256_a(this);
   }

   public List<SPacketPlayerListItem.AddPlayerData> func_179767_a() {
      return this.field_179769_b;
   }

   public SPacketPlayerListItem.Action func_179768_b() {
      return this.field_179770_a;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.field_179770_a).add("entries", this.field_179769_b).toString();
   }

   public static enum Action {
      ADD_PLAYER,
      UPDATE_GAME_MODE,
      UPDATE_LATENCY,
      UPDATE_DISPLAY_NAME,
      REMOVE_PLAYER;
   }

   public class AddPlayerData {
      private final int field_179966_b;
      private final GameType field_179967_c;
      private final GameProfile field_179964_d;
      private final ITextComponent field_179965_e;

      public AddPlayerData(GameProfile p_i46663_2_, int p_i46663_3_, GameType p_i46663_4_, @Nullable ITextComponent p_i46663_5_) {
         this.field_179964_d = p_i46663_2_;
         this.field_179966_b = p_i46663_3_;
         this.field_179967_c = p_i46663_4_;
         this.field_179965_e = p_i46663_5_;
      }

      public GameProfile func_179962_a() {
         return this.field_179964_d;
      }

      public int func_179963_b() {
         return this.field_179966_b;
      }

      public GameType func_179960_c() {
         return this.field_179967_c;
      }

      @Nullable
      public ITextComponent func_179961_d() {
         return this.field_179965_e;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("latency", this.field_179966_b).add("gameMode", this.field_179967_c).add("profile", this.field_179964_d).add("displayName", this.field_179965_e == null ? null : ITextComponent.Serializer.func_150696_a(this.field_179965_e)).toString();
      }
   }
}
