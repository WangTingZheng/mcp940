package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
   protected static final ResourceLocation field_177188_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected T field_177189_c;
   protected T field_177186_d;
   private final RenderLivingBase<?> field_177190_a;
   private float field_177187_e = 1.0F;
   private float field_177184_f = 1.0F;
   private float field_177185_g = 1.0F;
   private float field_177192_h = 1.0F;
   private boolean field_177193_i;
   private static final Map<String, ResourceLocation> field_177191_j = Maps.<String, ResourceLocation>newHashMap();

   public LayerArmorBase(RenderLivingBase<?> p_i46125_1_) {
      this.field_177190_a = p_i46125_1_;
      this.func_177177_a();
   }

   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      this.func_188361_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, EntityEquipmentSlot.CHEST);
      this.func_188361_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, EntityEquipmentSlot.LEGS);
      this.func_188361_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, EntityEquipmentSlot.FEET);
      this.func_188361_a(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, EntityEquipmentSlot.HEAD);
   }

   public boolean func_177142_b() {
      return false;
   }

   private void func_188361_a(EntityLivingBase p_188361_1_, float p_188361_2_, float p_188361_3_, float p_188361_4_, float p_188361_5_, float p_188361_6_, float p_188361_7_, float p_188361_8_, EntityEquipmentSlot p_188361_9_) {
      ItemStack itemstack = p_188361_1_.func_184582_a(p_188361_9_);
      if (itemstack.func_77973_b() instanceof ItemArmor) {
         ItemArmor itemarmor = (ItemArmor)itemstack.func_77973_b();
         if (itemarmor.func_185083_B_() == p_188361_9_) {
            T t = this.func_188360_a(p_188361_9_);
            t.func_178686_a(this.field_177190_a.func_177087_b());
            t.func_78086_a(p_188361_1_, p_188361_2_, p_188361_3_, p_188361_4_);
            this.func_188359_a(t, p_188361_9_);
            boolean flag = this.func_188363_b(p_188361_9_);
            this.field_177190_a.func_110776_a(this.func_177181_a(itemarmor, flag));
            switch(itemarmor.func_82812_d()) {
            case LEATHER:
               int i = itemarmor.func_82814_b(itemstack);
               float f = (float)(i >> 16 & 255) / 255.0F;
               float f1 = (float)(i >> 8 & 255) / 255.0F;
               float f2 = (float)(i & 255) / 255.0F;
               GlStateManager.func_179131_c(this.field_177184_f * f, this.field_177185_g * f1, this.field_177192_h * f2, this.field_177187_e);
               t.func_78088_a(p_188361_1_, p_188361_2_, p_188361_3_, p_188361_5_, p_188361_6_, p_188361_7_, p_188361_8_);
               this.field_177190_a.func_110776_a(this.func_177178_a(itemarmor, flag, "overlay"));
            case CHAIN:
            case IRON:
            case GOLD:
            case DIAMOND:
               GlStateManager.func_179131_c(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
               t.func_78088_a(p_188361_1_, p_188361_2_, p_188361_3_, p_188361_5_, p_188361_6_, p_188361_7_, p_188361_8_);
            default:
               if (!this.field_177193_i && itemstack.func_77948_v()) {
                  func_188364_a(this.field_177190_a, p_188361_1_, t, p_188361_2_, p_188361_3_, p_188361_4_, p_188361_5_, p_188361_6_, p_188361_7_, p_188361_8_);
               }

            }
         }
      }
   }

   public T func_188360_a(EntityEquipmentSlot p_188360_1_) {
      return (T)(this.func_188363_b(p_188360_1_) ? this.field_177189_c : this.field_177186_d);
   }

   private boolean func_188363_b(EntityEquipmentSlot p_188363_1_) {
      return p_188363_1_ == EntityEquipmentSlot.LEGS;
   }

   public static void func_188364_a(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelBase p_188364_2_, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_) {
      float f = (float)p_188364_1_.field_70173_aa + p_188364_5_;
      p_188364_0_.func_110776_a(field_177188_b);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
      GlStateManager.func_179147_l();
      GlStateManager.func_179143_c(514);
      GlStateManager.func_179132_a(false);
      float f1 = 0.5F;
      GlStateManager.func_179131_c(0.5F, 0.5F, 0.5F, 1.0F);

      for(int i = 0; i < 2; ++i) {
         GlStateManager.func_179140_f();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
         float f2 = 0.76F;
         GlStateManager.func_179131_c(0.38F, 0.19F, 0.608F, 1.0F);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         float f3 = 0.33333334F;
         GlStateManager.func_179152_a(0.33333334F, 0.33333334F, 0.33333334F);
         GlStateManager.func_179114_b(30.0F - (float)i * 60.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179109_b(0.0F, f * (0.001F + (float)i * 0.003F) * 20.0F, 0.0F);
         GlStateManager.func_179128_n(5888);
         p_188364_2_.func_78088_a(p_188364_1_, p_188364_3_, p_188364_4_, p_188364_6_, p_188364_7_, p_188364_8_, p_188364_9_);
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      }

      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179145_e();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179084_k();
      Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
   }

   private ResourceLocation func_177181_a(ItemArmor p_177181_1_, boolean p_177181_2_) {
      return this.func_177178_a(p_177181_1_, p_177181_2_, (String)null);
   }

   private ResourceLocation func_177178_a(ItemArmor p_177178_1_, boolean p_177178_2_, String p_177178_3_) {
      String s = String.format("textures/models/armor/%s_layer_%d%s.png", p_177178_1_.func_82812_d().func_179242_c(), p_177178_2_ ? 2 : 1, p_177178_3_ == null ? "" : String.format("_%s", p_177178_3_));
      ResourceLocation resourcelocation = field_177191_j.get(s);
      if (resourcelocation == null) {
         resourcelocation = new ResourceLocation(s);
         field_177191_j.put(s, resourcelocation);
      }

      return resourcelocation;
   }

   protected abstract void func_177177_a();

   protected abstract void func_188359_a(T var1, EntityEquipmentSlot var2);
}
