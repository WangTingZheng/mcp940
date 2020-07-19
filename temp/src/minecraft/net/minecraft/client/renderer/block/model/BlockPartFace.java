package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;

public class BlockPartFace {
   public static final EnumFacing field_178246_a = null;
   public final EnumFacing field_178244_b;
   public final int field_178245_c;
   public final String field_178242_d;
   public final BlockFaceUV field_178243_e;

   public BlockPartFace(@Nullable EnumFacing p_i46230_1_, int p_i46230_2_, String p_i46230_3_, BlockFaceUV p_i46230_4_) {
      this.field_178244_b = p_i46230_1_;
      this.field_178245_c = p_i46230_2_;
      this.field_178242_d = p_i46230_3_;
      this.field_178243_e = p_i46230_4_;
   }

   static class Deserializer implements JsonDeserializer<BlockPartFace> {
      public BlockPartFace deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
         EnumFacing enumfacing = this.func_178339_c(jsonobject);
         int i = this.func_178337_a(jsonobject);
         String s = this.func_178340_b(jsonobject);
         BlockFaceUV blockfaceuv = (BlockFaceUV)p_deserialize_3_.deserialize(jsonobject, BlockFaceUV.class);
         return new BlockPartFace(enumfacing, i, s, blockfaceuv);
      }

      protected int func_178337_a(JsonObject p_178337_1_) {
         return JsonUtils.func_151208_a(p_178337_1_, "tintindex", -1);
      }

      private String func_178340_b(JsonObject p_178340_1_) {
         return JsonUtils.func_151200_h(p_178340_1_, "texture");
      }

      @Nullable
      private EnumFacing func_178339_c(JsonObject p_178339_1_) {
         String s = JsonUtils.func_151219_a(p_178339_1_, "cullface", "");
         return EnumFacing.func_176739_a(s);
      }
   }
}
