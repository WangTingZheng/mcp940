package net.minecraft.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DisplayInfo {
   private final ITextComponent field_192300_a;
   private final ITextComponent field_193225_b;
   private final ItemStack field_192301_b;
   private final ResourceLocation field_192302_c;
   private final FrameType field_192303_d;
   private final boolean field_193226_f;
   private final boolean field_193227_g;
   private final boolean field_193228_h;
   private float field_192304_e;
   private float field_192305_f;

   public DisplayInfo(ItemStack p_i47586_1_, ITextComponent p_i47586_2_, ITextComponent p_i47586_3_, @Nullable ResourceLocation p_i47586_4_, FrameType p_i47586_5_, boolean p_i47586_6_, boolean p_i47586_7_, boolean p_i47586_8_) {
      this.field_192300_a = p_i47586_2_;
      this.field_193225_b = p_i47586_3_;
      this.field_192301_b = p_i47586_1_;
      this.field_192302_c = p_i47586_4_;
      this.field_192303_d = p_i47586_5_;
      this.field_193226_f = p_i47586_6_;
      this.field_193227_g = p_i47586_7_;
      this.field_193228_h = p_i47586_8_;
   }

   public void func_192292_a(float p_192292_1_, float p_192292_2_) {
      this.field_192304_e = p_192292_1_;
      this.field_192305_f = p_192292_2_;
   }

   public ITextComponent func_192297_a() {
      return this.field_192300_a;
   }

   public ITextComponent func_193222_b() {
      return this.field_193225_b;
   }

   public ItemStack func_192298_b() {
      return this.field_192301_b;
   }

   @Nullable
   public ResourceLocation func_192293_c() {
      return this.field_192302_c;
   }

   public FrameType func_192291_d() {
      return this.field_192303_d;
   }

   public float func_192299_e() {
      return this.field_192304_e;
   }

   public float func_192296_f() {
      return this.field_192305_f;
   }

   public boolean func_193223_h() {
      return this.field_193226_f;
   }

   public boolean func_193220_i() {
      return this.field_193227_g;
   }

   public boolean func_193224_j() {
      return this.field_193228_h;
   }

   public static DisplayInfo func_192294_a(JsonObject p_192294_0_, JsonDeserializationContext p_192294_1_) {
      ITextComponent itextcomponent = (ITextComponent)JsonUtils.func_188174_a(p_192294_0_, "title", p_192294_1_, ITextComponent.class);
      ITextComponent itextcomponent1 = (ITextComponent)JsonUtils.func_188174_a(p_192294_0_, "description", p_192294_1_, ITextComponent.class);
      if (itextcomponent != null && itextcomponent1 != null) {
         ItemStack itemstack = func_193221_a(JsonUtils.func_152754_s(p_192294_0_, "icon"));
         ResourceLocation resourcelocation = p_192294_0_.has("background") ? new ResourceLocation(JsonUtils.func_151200_h(p_192294_0_, "background")) : null;
         FrameType frametype = p_192294_0_.has("frame") ? FrameType.func_192308_a(JsonUtils.func_151200_h(p_192294_0_, "frame")) : FrameType.TASK;
         boolean flag = JsonUtils.func_151209_a(p_192294_0_, "show_toast", true);
         boolean flag1 = JsonUtils.func_151209_a(p_192294_0_, "announce_to_chat", true);
         boolean flag2 = JsonUtils.func_151209_a(p_192294_0_, "hidden", false);
         return new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, flag1, flag2);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack func_193221_a(JsonObject p_193221_0_) {
      if (!p_193221_0_.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item item = JsonUtils.func_188180_i(p_193221_0_, "item");
         int i = JsonUtils.func_151208_a(p_193221_0_, "data", 0);
         return new ItemStack(item, 1, i);
      }
   }

   public void func_192290_a(PacketBuffer p_192290_1_) {
      p_192290_1_.func_179256_a(this.field_192300_a);
      p_192290_1_.func_179256_a(this.field_193225_b);
      p_192290_1_.func_150788_a(this.field_192301_b);
      p_192290_1_.func_179249_a(this.field_192303_d);
      int i = 0;
      if (this.field_192302_c != null) {
         i |= 1;
      }

      if (this.field_193226_f) {
         i |= 2;
      }

      if (this.field_193228_h) {
         i |= 4;
      }

      p_192290_1_.writeInt(i);
      if (this.field_192302_c != null) {
         p_192290_1_.func_192572_a(this.field_192302_c);
      }

      p_192290_1_.writeFloat(this.field_192304_e);
      p_192290_1_.writeFloat(this.field_192305_f);
   }

   public static DisplayInfo func_192295_b(PacketBuffer p_192295_0_) throws IOException {
      ITextComponent itextcomponent = p_192295_0_.func_179258_d();
      ITextComponent itextcomponent1 = p_192295_0_.func_179258_d();
      ItemStack itemstack = p_192295_0_.func_150791_c();
      FrameType frametype = (FrameType)p_192295_0_.func_179257_a(FrameType.class);
      int i = p_192295_0_.readInt();
      ResourceLocation resourcelocation = (i & 1) != 0 ? p_192295_0_.func_192575_l() : null;
      boolean flag = (i & 2) != 0;
      boolean flag1 = (i & 4) != 0;
      DisplayInfo displayinfo = new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, false, flag1);
      displayinfo.func_192292_a(p_192295_0_.readFloat(), p_192295_0_.readFloat());
      return displayinfo;
   }
}
