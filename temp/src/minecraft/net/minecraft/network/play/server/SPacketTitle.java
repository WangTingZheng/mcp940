package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketTitle implements Packet<INetHandlerPlayClient> {
   private SPacketTitle.Type field_179812_a;
   private ITextComponent field_179810_b;
   private int field_179811_c;
   private int field_179808_d;
   private int field_179809_e;

   public SPacketTitle() {
   }

   public SPacketTitle(SPacketTitle.Type p_i46899_1_, ITextComponent p_i46899_2_) {
      this(p_i46899_1_, p_i46899_2_, -1, -1, -1);
   }

   public SPacketTitle(int p_i46900_1_, int p_i46900_2_, int p_i46900_3_) {
      this(SPacketTitle.Type.TIMES, (ITextComponent)null, p_i46900_1_, p_i46900_2_, p_i46900_3_);
   }

   public SPacketTitle(SPacketTitle.Type p_i46901_1_, @Nullable ITextComponent p_i46901_2_, int p_i46901_3_, int p_i46901_4_, int p_i46901_5_) {
      this.field_179812_a = p_i46901_1_;
      this.field_179810_b = p_i46901_2_;
      this.field_179811_c = p_i46901_3_;
      this.field_179808_d = p_i46901_4_;
      this.field_179809_e = p_i46901_5_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179812_a = (SPacketTitle.Type)p_148837_1_.func_179257_a(SPacketTitle.Type.class);
      if (this.field_179812_a == SPacketTitle.Type.TITLE || this.field_179812_a == SPacketTitle.Type.SUBTITLE || this.field_179812_a == SPacketTitle.Type.ACTIONBAR) {
         this.field_179810_b = p_148837_1_.func_179258_d();
      }

      if (this.field_179812_a == SPacketTitle.Type.TIMES) {
         this.field_179811_c = p_148837_1_.readInt();
         this.field_179808_d = p_148837_1_.readInt();
         this.field_179809_e = p_148837_1_.readInt();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_179812_a);
      if (this.field_179812_a == SPacketTitle.Type.TITLE || this.field_179812_a == SPacketTitle.Type.SUBTITLE || this.field_179812_a == SPacketTitle.Type.ACTIONBAR) {
         p_148840_1_.func_179256_a(this.field_179810_b);
      }

      if (this.field_179812_a == SPacketTitle.Type.TIMES) {
         p_148840_1_.writeInt(this.field_179811_c);
         p_148840_1_.writeInt(this.field_179808_d);
         p_148840_1_.writeInt(this.field_179809_e);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175099_a(this);
   }

   public SPacketTitle.Type func_179807_a() {
      return this.field_179812_a;
   }

   public ITextComponent func_179805_b() {
      return this.field_179810_b;
   }

   public int func_179806_c() {
      return this.field_179811_c;
   }

   public int func_179804_d() {
      return this.field_179808_d;
   }

   public int func_179803_e() {
      return this.field_179809_e;
   }

   public static enum Type {
      TITLE,
      SUBTITLE,
      ACTIONBAR,
      TIMES,
      CLEAR,
      RESET;

      public static SPacketTitle.Type func_179969_a(String p_179969_0_) {
         for(SPacketTitle.Type spackettitle$type : values()) {
            if (spackettitle$type.name().equalsIgnoreCase(p_179969_0_)) {
               return spackettitle$type;
            }
         }

         return TITLE;
      }

      public static String[] func_179971_a() {
         String[] astring = new String[values().length];
         int i = 0;

         for(SPacketTitle.Type spackettitle$type : values()) {
            astring[i++] = spackettitle$type.name().toLowerCase(Locale.ROOT);
         }

         return astring;
      }
   }
}
