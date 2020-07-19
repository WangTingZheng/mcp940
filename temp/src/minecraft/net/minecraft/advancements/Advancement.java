package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
   private final Advancement field_192076_a;
   private final DisplayInfo field_192077_b;
   private final AdvancementRewards field_192078_c;
   private final ResourceLocation field_192079_d;
   private final Map<String, Criterion> field_192080_e;
   private final String[][] field_192081_f;
   private final Set<Advancement> field_192082_g = Sets.<Advancement>newLinkedHashSet();
   private final ITextComponent field_193125_h;

   public Advancement(ResourceLocation p_i47472_1_, @Nullable Advancement p_i47472_2_, @Nullable DisplayInfo p_i47472_3_, AdvancementRewards p_i47472_4_, Map<String, Criterion> p_i47472_5_, String[][] p_i47472_6_) {
      this.field_192079_d = p_i47472_1_;
      this.field_192077_b = p_i47472_3_;
      this.field_192080_e = ImmutableMap.copyOf(p_i47472_5_);
      this.field_192076_a = p_i47472_2_;
      this.field_192078_c = p_i47472_4_;
      this.field_192081_f = p_i47472_6_;
      if (p_i47472_2_ != null) {
         p_i47472_2_.func_192071_a(this);
      }

      if (p_i47472_3_ == null) {
         this.field_193125_h = new TextComponentString(p_i47472_1_.toString());
      } else {
         this.field_193125_h = new TextComponentString("[");
         this.field_193125_h.func_150256_b().func_150238_a(p_i47472_3_.func_192291_d().func_193229_c());
         ITextComponent itextcomponent = p_i47472_3_.func_192297_a().func_150259_f();
         ITextComponent itextcomponent1 = new TextComponentString("");
         ITextComponent itextcomponent2 = itextcomponent.func_150259_f();
         itextcomponent2.func_150256_b().func_150238_a(p_i47472_3_.func_192291_d().func_193229_c());
         itextcomponent1.func_150257_a(itextcomponent2);
         itextcomponent1.func_150258_a("\n");
         itextcomponent1.func_150257_a(p_i47472_3_.func_193222_b());
         itextcomponent.func_150256_b().func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, itextcomponent1));
         this.field_193125_h.func_150257_a(itextcomponent);
         this.field_193125_h.func_150258_a("]");
      }

   }

   public Advancement.Builder func_192075_a() {
      return new Advancement.Builder(this.field_192076_a == null ? null : this.field_192076_a.func_192067_g(), this.field_192077_b, this.field_192078_c, this.field_192080_e, this.field_192081_f);
   }

   @Nullable
   public Advancement func_192070_b() {
      return this.field_192076_a;
   }

   @Nullable
   public DisplayInfo func_192068_c() {
      return this.field_192077_b;
   }

   public AdvancementRewards func_192072_d() {
      return this.field_192078_c;
   }

   public String toString() {
      return "SimpleAdvancement{id=" + this.func_192067_g() + ", parent=" + (this.field_192076_a == null ? "null" : this.field_192076_a.func_192067_g()) + ", display=" + this.field_192077_b + ", rewards=" + this.field_192078_c + ", criteria=" + this.field_192080_e + ", requirements=" + Arrays.deepToString(this.field_192081_f) + '}';
   }

   public Iterable<Advancement> func_192069_e() {
      return this.field_192082_g;
   }

   public Map<String, Criterion> func_192073_f() {
      return this.field_192080_e;
   }

   public int func_193124_g() {
      return this.field_192081_f.length;
   }

   public void func_192071_a(Advancement p_192071_1_) {
      this.field_192082_g.add(p_192071_1_);
   }

   public ResourceLocation func_192067_g() {
      return this.field_192079_d;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Advancement)) {
         return false;
      } else {
         Advancement advancement = (Advancement)p_equals_1_;
         return this.field_192079_d.equals(advancement.field_192079_d);
      }
   }

   public int hashCode() {
      return this.field_192079_d.hashCode();
   }

   public String[][] func_192074_h() {
      return this.field_192081_f;
   }

   public ITextComponent func_193123_j() {
      return this.field_193125_h;
   }

   public static class Builder {
      private final ResourceLocation field_192061_a;
      private Advancement field_192062_b;
      private final DisplayInfo field_192063_c;
      private final AdvancementRewards field_192064_d;
      private final Map<String, Criterion> field_192065_e;
      private final String[][] field_192066_f;

      Builder(@Nullable ResourceLocation p_i47414_1_, @Nullable DisplayInfo p_i47414_2_, AdvancementRewards p_i47414_3_, Map<String, Criterion> p_i47414_4_, String[][] p_i47414_5_) {
         this.field_192061_a = p_i47414_1_;
         this.field_192063_c = p_i47414_2_;
         this.field_192064_d = p_i47414_3_;
         this.field_192065_e = p_i47414_4_;
         this.field_192066_f = p_i47414_5_;
      }

      public boolean func_192058_a(Function<ResourceLocation, Advancement> p_192058_1_) {
         if (this.field_192061_a == null) {
            return true;
         } else {
            this.field_192062_b = p_192058_1_.apply(this.field_192061_a);
            return this.field_192062_b != null;
         }
      }

      public Advancement func_192056_a(ResourceLocation p_192056_1_) {
         return new Advancement(p_192056_1_, this.field_192062_b, this.field_192063_c, this.field_192064_d, this.field_192065_e, this.field_192066_f);
      }

      public void func_192057_a(PacketBuffer p_192057_1_) {
         if (this.field_192061_a == null) {
            p_192057_1_.writeBoolean(false);
         } else {
            p_192057_1_.writeBoolean(true);
            p_192057_1_.func_192572_a(this.field_192061_a);
         }

         if (this.field_192063_c == null) {
            p_192057_1_.writeBoolean(false);
         } else {
            p_192057_1_.writeBoolean(true);
            this.field_192063_c.func_192290_a(p_192057_1_);
         }

         Criterion.func_192141_a(this.field_192065_e, p_192057_1_);
         p_192057_1_.func_150787_b(this.field_192066_f.length);

         for(String[] astring : this.field_192066_f) {
            p_192057_1_.func_150787_b(astring.length);

            for(String s : astring) {
               p_192057_1_.func_180714_a(s);
            }
         }

      }

      public String toString() {
         return "Task Advancement{parentId=" + this.field_192061_a + ", display=" + this.field_192063_c + ", rewards=" + this.field_192064_d + ", criteria=" + this.field_192065_e + ", requirements=" + Arrays.deepToString(this.field_192066_f) + '}';
      }

      public static Advancement.Builder func_192059_a(JsonObject p_192059_0_, JsonDeserializationContext p_192059_1_) {
         ResourceLocation resourcelocation = p_192059_0_.has("parent") ? new ResourceLocation(JsonUtils.func_151200_h(p_192059_0_, "parent")) : null;
         DisplayInfo displayinfo = p_192059_0_.has("display") ? DisplayInfo.func_192294_a(JsonUtils.func_152754_s(p_192059_0_, "display"), p_192059_1_) : null;
         AdvancementRewards advancementrewards = (AdvancementRewards)JsonUtils.func_188177_a(p_192059_0_, "rewards", AdvancementRewards.field_192114_a, p_192059_1_, AdvancementRewards.class);
         Map<String, Criterion> map = Criterion.func_192144_b(JsonUtils.func_152754_s(p_192059_0_, "criteria"), p_192059_1_);
         if (map.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray jsonarray = JsonUtils.func_151213_a(p_192059_0_, "requirements", new JsonArray());
            String[][] astring = new String[jsonarray.size()][];

            for(int i = 0; i < jsonarray.size(); ++i) {
               JsonArray jsonarray1 = JsonUtils.func_151207_m(jsonarray.get(i), "requirements[" + i + "]");
               astring[i] = new String[jsonarray1.size()];

               for(int j = 0; j < jsonarray1.size(); ++j) {
                  astring[i][j] = JsonUtils.func_151206_a(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
               }
            }

            if (astring.length == 0) {
               astring = new String[map.size()][];
               int k = 0;

               for(String s2 : map.keySet()) {
                  astring[k++] = new String[]{s2};
               }
            }

            for(String[] astring1 : astring) {
               if (astring1.length == 0 && map.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for(String s : astring1) {
                  if (!map.containsKey(s)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + s + "'");
                  }
               }
            }

            for(String s1 : map.keySet()) {
               boolean flag = false;

               for(String[] astring2 : astring) {
                  if (ArrayUtils.contains(astring2, s1)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  throw new JsonSyntaxException("Criterion '" + s1 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
               }
            }

            return new Advancement.Builder(resourcelocation, displayinfo, advancementrewards, map, astring);
         }
      }

      public static Advancement.Builder func_192060_b(PacketBuffer p_192060_0_) throws IOException {
         ResourceLocation resourcelocation = p_192060_0_.readBoolean() ? p_192060_0_.func_192575_l() : null;
         DisplayInfo displayinfo = p_192060_0_.readBoolean() ? DisplayInfo.func_192295_b(p_192060_0_) : null;
         Map<String, Criterion> map = Criterion.func_192142_c(p_192060_0_);
         String[][] astring = new String[p_192060_0_.func_150792_a()][];

         for(int i = 0; i < astring.length; ++i) {
            astring[i] = new String[p_192060_0_.func_150792_a()];

            for(int j = 0; j < astring[i].length; ++j) {
               astring[i][j] = p_192060_0_.func_150789_c(32767);
            }
         }

         return new Advancement.Builder(resourcelocation, displayinfo, AdvancementRewards.field_192114_a, map, astring);
      }
   }
}
