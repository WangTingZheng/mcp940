package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer<SoundList> {
   public SoundList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
      JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "entry");
      boolean flag = JsonUtils.func_151209_a(jsonobject, "replace", false);
      String s = JsonUtils.func_151219_a(jsonobject, "subtitle", (String)null);
      List<Sound> list = this.func_188733_a(jsonobject);
      return new SoundList(list, flag, s);
   }

   private List<Sound> func_188733_a(JsonObject p_188733_1_) {
      List<Sound> list = Lists.<Sound>newArrayList();
      if (p_188733_1_.has("sounds")) {
         JsonArray jsonarray = JsonUtils.func_151214_t(p_188733_1_, "sounds");

         for(int i = 0; i < jsonarray.size(); ++i) {
            JsonElement jsonelement = jsonarray.get(i);
            if (JsonUtils.func_151211_a(jsonelement)) {
               String s = JsonUtils.func_151206_a(jsonelement, "sound");
               list.add(new Sound(s, 1.0F, 1.0F, 1, Sound.Type.FILE, false));
            } else {
               list.add(this.func_188734_b(JsonUtils.func_151210_l(jsonelement, "sound")));
            }
         }
      }

      return list;
   }

   private Sound func_188734_b(JsonObject p_188734_1_) {
      String s = JsonUtils.func_151200_h(p_188734_1_, "name");
      Sound.Type sound$type = this.func_188732_a(p_188734_1_, Sound.Type.FILE);
      float f = JsonUtils.func_151221_a(p_188734_1_, "volume", 1.0F);
      Validate.isTrue(f > 0.0F, "Invalid volume");
      float f1 = JsonUtils.func_151221_a(p_188734_1_, "pitch", 1.0F);
      Validate.isTrue(f1 > 0.0F, "Invalid pitch");
      int i = JsonUtils.func_151208_a(p_188734_1_, "weight", 1);
      Validate.isTrue(i > 0, "Invalid weight");
      boolean flag = JsonUtils.func_151209_a(p_188734_1_, "stream", false);
      return new Sound(s, f, f1, i, sound$type, flag);
   }

   private Sound.Type func_188732_a(JsonObject p_188732_1_, Sound.Type p_188732_2_) {
      Sound.Type sound$type = p_188732_2_;
      if (p_188732_1_.has("type")) {
         sound$type = Sound.Type.func_188704_a(JsonUtils.func_151200_h(p_188732_1_, "type"));
         Validate.notNull(sound$type, "Invalid type");
      }

      return sound$type;
   }
}
