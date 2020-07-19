package net.minecraft.client.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelBase {
   public float field_78095_p;
   public boolean field_78093_q;
   public boolean field_78091_s = true;
   public List<ModelRenderer> field_78092_r = Lists.<ModelRenderer>newArrayList();
   private final Map<String, TextureOffset> field_78094_a = Maps.<String, TextureOffset>newHashMap();
   public int field_78090_t = 64;
   public int field_78089_u = 32;

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
   }

   public void func_78086_a(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
   }

   public ModelRenderer func_85181_a(Random p_85181_1_) {
      return this.field_78092_r.get(p_85181_1_.nextInt(this.field_78092_r.size()));
   }

   protected void func_78085_a(String p_78085_1_, int p_78085_2_, int p_78085_3_) {
      this.field_78094_a.put(p_78085_1_, new TextureOffset(p_78085_2_, p_78085_3_));
   }

   public TextureOffset func_78084_a(String p_78084_1_) {
      return this.field_78094_a.get(p_78084_1_);
   }

   public static void func_178685_a(ModelRenderer p_178685_0_, ModelRenderer p_178685_1_) {
      p_178685_1_.field_78795_f = p_178685_0_.field_78795_f;
      p_178685_1_.field_78796_g = p_178685_0_.field_78796_g;
      p_178685_1_.field_78808_h = p_178685_0_.field_78808_h;
      p_178685_1_.field_78800_c = p_178685_0_.field_78800_c;
      p_178685_1_.field_78797_d = p_178685_0_.field_78797_d;
      p_178685_1_.field_78798_e = p_178685_0_.field_78798_e;
   }

   public void func_178686_a(ModelBase p_178686_1_) {
      this.field_78095_p = p_178686_1_.field_78095_p;
      this.field_78093_q = p_178686_1_.field_78093_q;
      this.field_78091_s = p_178686_1_.field_78091_s;
   }
}
