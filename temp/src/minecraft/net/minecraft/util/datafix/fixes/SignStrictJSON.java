package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.StringUtils;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class SignStrictJSON implements IFixableData {
   public static final Gson field_188225_a = (new GsonBuilder()).registerTypeAdapter(ITextComponent.class, new JsonDeserializer<ITextComponent>() {
      public ITextComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         if (p_deserialize_1_.isJsonPrimitive()) {
            return new TextComponentString(p_deserialize_1_.getAsString());
         } else if (p_deserialize_1_.isJsonArray()) {
            JsonArray jsonarray = p_deserialize_1_.getAsJsonArray();
            ITextComponent itextcomponent = null;

            for(JsonElement jsonelement : jsonarray) {
               ITextComponent itextcomponent1 = this.deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
               if (itextcomponent == null) {
                  itextcomponent = itextcomponent1;
               } else {
                  itextcomponent.func_150257_a(itextcomponent1);
               }
            }

            return itextcomponent;
         } else {
            throw new JsonParseException("Don't know how to turn " + p_deserialize_1_ + " into a Component");
         }
      }
   }).create();

   public int func_188216_a() {
      return 101;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("Sign".equals(p_188217_1_.func_74779_i("id"))) {
         this.func_188224_a(p_188217_1_, "Text1");
         this.func_188224_a(p_188217_1_, "Text2");
         this.func_188224_a(p_188217_1_, "Text3");
         this.func_188224_a(p_188217_1_, "Text4");
      }

      return p_188217_1_;
   }

   private void func_188224_a(NBTTagCompound p_188224_1_, String p_188224_2_) {
      String s = p_188224_1_.func_74779_i(p_188224_2_);
      ITextComponent itextcomponent = null;
      if (!"null".equals(s) && !StringUtils.func_151246_b(s)) {
         if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"' || s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
            try {
               itextcomponent = (ITextComponent)JsonUtils.func_188176_a(field_188225_a, s, ITextComponent.class, true);
               if (itextcomponent == null) {
                  itextcomponent = new TextComponentString("");
               }
            } catch (JsonParseException var8) {
               ;
            }

            if (itextcomponent == null) {
               try {
                  itextcomponent = ITextComponent.Serializer.func_150699_a(s);
               } catch (JsonParseException var7) {
                  ;
               }
            }

            if (itextcomponent == null) {
               try {
                  itextcomponent = ITextComponent.Serializer.func_186877_b(s);
               } catch (JsonParseException var6) {
                  ;
               }
            }

            if (itextcomponent == null) {
               itextcomponent = new TextComponentString(s);
            }
         } else {
            itextcomponent = new TextComponentString(s);
         }
      } else {
         itextcomponent = new TextComponentString("");
      }

      p_188224_1_.func_74778_a(p_188224_2_, ITextComponent.Serializer.func_150696_a(itextcomponent));
   }
}
