package net.minecraft.util.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class Style {
   private Style field_150249_a;
   private TextFormatting field_150247_b;
   private Boolean field_150248_c;
   private Boolean field_150245_d;
   private Boolean field_150246_e;
   private Boolean field_150243_f;
   private Boolean field_150244_g;
   private ClickEvent field_150251_h;
   private HoverEvent field_150252_i;
   private String field_179990_j;
   private static final Style field_150250_j = new Style() {
      @Nullable
      public TextFormatting func_150215_a() {
         return null;
      }

      public boolean func_150223_b() {
         return false;
      }

      public boolean func_150242_c() {
         return false;
      }

      public boolean func_150236_d() {
         return false;
      }

      public boolean func_150234_e() {
         return false;
      }

      public boolean func_150233_f() {
         return false;
      }

      @Nullable
      public ClickEvent func_150235_h() {
         return null;
      }

      @Nullable
      public HoverEvent func_150210_i() {
         return null;
      }

      @Nullable
      public String func_179986_j() {
         return null;
      }

      public Style func_150238_a(TextFormatting p_150238_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150227_a(Boolean p_150227_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150217_b(Boolean p_150217_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150225_c(Boolean p_150225_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150228_d(Boolean p_150228_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150237_e(Boolean p_150237_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150241_a(ClickEvent p_150241_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150209_a(HoverEvent p_150209_1_) {
         throw new UnsupportedOperationException();
      }

      public Style func_150221_a(Style p_150221_1_) {
         throw new UnsupportedOperationException();
      }

      public String toString() {
         return "Style.ROOT";
      }

      public Style func_150232_l() {
         return this;
      }

      public Style func_150206_m() {
         return this;
      }

      public String func_150218_j() {
         return "";
      }
   };

   @Nullable
   public TextFormatting func_150215_a() {
      return this.field_150247_b == null ? this.func_150224_n().func_150215_a() : this.field_150247_b;
   }

   public boolean func_150223_b() {
      return this.field_150248_c == null ? this.func_150224_n().func_150223_b() : this.field_150248_c.booleanValue();
   }

   public boolean func_150242_c() {
      return this.field_150245_d == null ? this.func_150224_n().func_150242_c() : this.field_150245_d.booleanValue();
   }

   public boolean func_150236_d() {
      return this.field_150243_f == null ? this.func_150224_n().func_150236_d() : this.field_150243_f.booleanValue();
   }

   public boolean func_150234_e() {
      return this.field_150246_e == null ? this.func_150224_n().func_150234_e() : this.field_150246_e.booleanValue();
   }

   public boolean func_150233_f() {
      return this.field_150244_g == null ? this.func_150224_n().func_150233_f() : this.field_150244_g.booleanValue();
   }

   public boolean func_150229_g() {
      return this.field_150248_c == null && this.field_150245_d == null && this.field_150243_f == null && this.field_150246_e == null && this.field_150244_g == null && this.field_150247_b == null && this.field_150251_h == null && this.field_150252_i == null && this.field_179990_j == null;
   }

   @Nullable
   public ClickEvent func_150235_h() {
      return this.field_150251_h == null ? this.func_150224_n().func_150235_h() : this.field_150251_h;
   }

   @Nullable
   public HoverEvent func_150210_i() {
      return this.field_150252_i == null ? this.func_150224_n().func_150210_i() : this.field_150252_i;
   }

   @Nullable
   public String func_179986_j() {
      return this.field_179990_j == null ? this.func_150224_n().func_179986_j() : this.field_179990_j;
   }

   public Style func_150238_a(TextFormatting p_150238_1_) {
      this.field_150247_b = p_150238_1_;
      return this;
   }

   public Style func_150227_a(Boolean p_150227_1_) {
      this.field_150248_c = p_150227_1_;
      return this;
   }

   public Style func_150217_b(Boolean p_150217_1_) {
      this.field_150245_d = p_150217_1_;
      return this;
   }

   public Style func_150225_c(Boolean p_150225_1_) {
      this.field_150243_f = p_150225_1_;
      return this;
   }

   public Style func_150228_d(Boolean p_150228_1_) {
      this.field_150246_e = p_150228_1_;
      return this;
   }

   public Style func_150237_e(Boolean p_150237_1_) {
      this.field_150244_g = p_150237_1_;
      return this;
   }

   public Style func_150241_a(ClickEvent p_150241_1_) {
      this.field_150251_h = p_150241_1_;
      return this;
   }

   public Style func_150209_a(HoverEvent p_150209_1_) {
      this.field_150252_i = p_150209_1_;
      return this;
   }

   public Style func_179989_a(String p_179989_1_) {
      this.field_179990_j = p_179989_1_;
      return this;
   }

   public Style func_150221_a(Style p_150221_1_) {
      this.field_150249_a = p_150221_1_;
      return this;
   }

   public String func_150218_j() {
      if (this.func_150229_g()) {
         return this.field_150249_a != null ? this.field_150249_a.func_150218_j() : "";
      } else {
         StringBuilder stringbuilder = new StringBuilder();
         if (this.func_150215_a() != null) {
            stringbuilder.append((Object)this.func_150215_a());
         }

         if (this.func_150223_b()) {
            stringbuilder.append((Object)TextFormatting.BOLD);
         }

         if (this.func_150242_c()) {
            stringbuilder.append((Object)TextFormatting.ITALIC);
         }

         if (this.func_150234_e()) {
            stringbuilder.append((Object)TextFormatting.UNDERLINE);
         }

         if (this.func_150233_f()) {
            stringbuilder.append((Object)TextFormatting.OBFUSCATED);
         }

         if (this.func_150236_d()) {
            stringbuilder.append((Object)TextFormatting.STRIKETHROUGH);
         }

         return stringbuilder.toString();
      }
   }

   private Style func_150224_n() {
      return this.field_150249_a == null ? field_150250_j : this.field_150249_a;
   }

   public String toString() {
      return "Style{hasParent=" + (this.field_150249_a != null) + ", color=" + this.field_150247_b + ", bold=" + this.field_150248_c + ", italic=" + this.field_150245_d + ", underlined=" + this.field_150246_e + ", obfuscated=" + this.field_150244_g + ", clickEvent=" + this.func_150235_h() + ", hoverEvent=" + this.func_150210_i() + ", insertion=" + this.func_179986_j() + '}';
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Style)) {
         return false;
      } else {
         boolean flag;
         label77: {
            Style style = (Style)p_equals_1_;
            if (this.func_150223_b() == style.func_150223_b() && this.func_150215_a() == style.func_150215_a() && this.func_150242_c() == style.func_150242_c() && this.func_150233_f() == style.func_150233_f() && this.func_150236_d() == style.func_150236_d() && this.func_150234_e() == style.func_150234_e()) {
               label71: {
                  if (this.func_150235_h() != null) {
                     if (!this.func_150235_h().equals(style.func_150235_h())) {
                        break label71;
                     }
                  } else if (style.func_150235_h() != null) {
                     break label71;
                  }

                  if (this.func_150210_i() != null) {
                     if (!this.func_150210_i().equals(style.func_150210_i())) {
                        break label71;
                     }
                  } else if (style.func_150210_i() != null) {
                     break label71;
                  }

                  if (this.func_179986_j() != null) {
                     if (this.func_179986_j().equals(style.func_179986_j())) {
                        break label77;
                     }
                  } else if (style.func_179986_j() == null) {
                     break label77;
                  }
               }
            }

            flag = false;
            return flag;
         }

         flag = true;
         return flag;
      }
   }

   public int hashCode() {
      int i = this.field_150247_b.hashCode();
      i = 31 * i + this.field_150248_c.hashCode();
      i = 31 * i + this.field_150245_d.hashCode();
      i = 31 * i + this.field_150246_e.hashCode();
      i = 31 * i + this.field_150243_f.hashCode();
      i = 31 * i + this.field_150244_g.hashCode();
      i = 31 * i + this.field_150251_h.hashCode();
      i = 31 * i + this.field_150252_i.hashCode();
      i = 31 * i + this.field_179990_j.hashCode();
      return i;
   }

   public Style func_150232_l() {
      Style style = new Style();
      style.field_150248_c = this.field_150248_c;
      style.field_150245_d = this.field_150245_d;
      style.field_150243_f = this.field_150243_f;
      style.field_150246_e = this.field_150246_e;
      style.field_150244_g = this.field_150244_g;
      style.field_150247_b = this.field_150247_b;
      style.field_150251_h = this.field_150251_h;
      style.field_150252_i = this.field_150252_i;
      style.field_150249_a = this.field_150249_a;
      style.field_179990_j = this.field_179990_j;
      return style;
   }

   public Style func_150206_m() {
      Style style = new Style();
      style.func_150227_a(Boolean.valueOf(this.func_150223_b()));
      style.func_150217_b(Boolean.valueOf(this.func_150242_c()));
      style.func_150225_c(Boolean.valueOf(this.func_150236_d()));
      style.func_150228_d(Boolean.valueOf(this.func_150234_e()));
      style.func_150237_e(Boolean.valueOf(this.func_150233_f()));
      style.func_150238_a(this.func_150215_a());
      style.func_150241_a(this.func_150235_h());
      style.func_150209_a(this.func_150210_i());
      style.func_179989_a(this.func_179986_j());
      return style;
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      @Nullable
      public Style deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonObject()) {
            Style style = new Style();
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            if (jsonobject == null) {
               return null;
            } else {
               if (jsonobject.has("bold")) {
                  style.field_150248_c = jsonobject.get("bold").getAsBoolean();
               }

               if (jsonobject.has("italic")) {
                  style.field_150245_d = jsonobject.get("italic").getAsBoolean();
               }

               if (jsonobject.has("underlined")) {
                  style.field_150246_e = jsonobject.get("underlined").getAsBoolean();
               }

               if (jsonobject.has("strikethrough")) {
                  style.field_150243_f = jsonobject.get("strikethrough").getAsBoolean();
               }

               if (jsonobject.has("obfuscated")) {
                  style.field_150244_g = jsonobject.get("obfuscated").getAsBoolean();
               }

               if (jsonobject.has("color")) {
                  style.field_150247_b = (TextFormatting)p_deserialize_3_.deserialize(jsonobject.get("color"), TextFormatting.class);
               }

               if (jsonobject.has("insertion")) {
                  style.field_179990_j = jsonobject.get("insertion").getAsString();
               }

               if (jsonobject.has("clickEvent")) {
                  JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
                  if (jsonobject1 != null) {
                     JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
                     ClickEvent.Action clickevent$action = jsonprimitive == null ? null : ClickEvent.Action.func_150672_a(jsonprimitive.getAsString());
                     JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
                     String s = jsonprimitive1 == null ? null : jsonprimitive1.getAsString();
                     if (clickevent$action != null && s != null && clickevent$action.func_150674_a()) {
                        style.field_150251_h = new ClickEvent(clickevent$action, s);
                     }
                  }
               }

               if (jsonobject.has("hoverEvent")) {
                  JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
                  if (jsonobject2 != null) {
                     JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
                     HoverEvent.Action hoverevent$action = jsonprimitive2 == null ? null : HoverEvent.Action.func_150684_a(jsonprimitive2.getAsString());
                     ITextComponent itextcomponent = (ITextComponent)p_deserialize_3_.deserialize(jsonobject2.get("value"), ITextComponent.class);
                     if (hoverevent$action != null && itextcomponent != null && hoverevent$action.func_150686_a()) {
                        style.field_150252_i = new HoverEvent(hoverevent$action, itextcomponent);
                     }
                  }
               }

               return style;
            }
         } else {
            return null;
         }
      }

      @Nullable
      public JsonElement serialize(Style p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         if (p_serialize_1_.func_150229_g()) {
            return null;
         } else {
            JsonObject jsonobject = new JsonObject();
            if (p_serialize_1_.field_150248_c != null) {
               jsonobject.addProperty("bold", p_serialize_1_.field_150248_c);
            }

            if (p_serialize_1_.field_150245_d != null) {
               jsonobject.addProperty("italic", p_serialize_1_.field_150245_d);
            }

            if (p_serialize_1_.field_150246_e != null) {
               jsonobject.addProperty("underlined", p_serialize_1_.field_150246_e);
            }

            if (p_serialize_1_.field_150243_f != null) {
               jsonobject.addProperty("strikethrough", p_serialize_1_.field_150243_f);
            }

            if (p_serialize_1_.field_150244_g != null) {
               jsonobject.addProperty("obfuscated", p_serialize_1_.field_150244_g);
            }

            if (p_serialize_1_.field_150247_b != null) {
               jsonobject.add("color", p_serialize_3_.serialize(p_serialize_1_.field_150247_b));
            }

            if (p_serialize_1_.field_179990_j != null) {
               jsonobject.add("insertion", p_serialize_3_.serialize(p_serialize_1_.field_179990_j));
            }

            if (p_serialize_1_.field_150251_h != null) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("action", p_serialize_1_.field_150251_h.func_150669_a().func_150673_b());
               jsonobject1.addProperty("value", p_serialize_1_.field_150251_h.func_150668_b());
               jsonobject.add("clickEvent", jsonobject1);
            }

            if (p_serialize_1_.field_150252_i != null) {
               JsonObject jsonobject2 = new JsonObject();
               jsonobject2.addProperty("action", p_serialize_1_.field_150252_i.func_150701_a().func_150685_b());
               jsonobject2.add("value", p_serialize_3_.serialize(p_serialize_1_.field_150252_i.func_150702_b()));
               jsonobject.add("hoverEvent", jsonobject2);
            }

            return jsonobject;
         }
      }
   }
}
