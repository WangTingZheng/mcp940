package net.minecraft.client.model;

import net.minecraft.client.renderer.BufferBuilder;

public class ModelBox {
   private final PositionTextureVertex[] field_78253_h;
   private final TexturedQuad[] field_78254_i;
   public final float field_78252_a;
   public final float field_78250_b;
   public final float field_78251_c;
   public final float field_78248_d;
   public final float field_78249_e;
   public final float field_78246_f;
   public String field_78247_g;

   public ModelBox(ModelRenderer p_i46359_1_, int p_i46359_2_, int p_i46359_3_, float p_i46359_4_, float p_i46359_5_, float p_i46359_6_, int p_i46359_7_, int p_i46359_8_, int p_i46359_9_, float p_i46359_10_) {
      this(p_i46359_1_, p_i46359_2_, p_i46359_3_, p_i46359_4_, p_i46359_5_, p_i46359_6_, p_i46359_7_, p_i46359_8_, p_i46359_9_, p_i46359_10_, p_i46359_1_.field_78809_i);
   }

   public ModelBox(ModelRenderer p_i46301_1_, int p_i46301_2_, int p_i46301_3_, float p_i46301_4_, float p_i46301_5_, float p_i46301_6_, int p_i46301_7_, int p_i46301_8_, int p_i46301_9_, float p_i46301_10_, boolean p_i46301_11_) {
      this.field_78252_a = p_i46301_4_;
      this.field_78250_b = p_i46301_5_;
      this.field_78251_c = p_i46301_6_;
      this.field_78248_d = p_i46301_4_ + (float)p_i46301_7_;
      this.field_78249_e = p_i46301_5_ + (float)p_i46301_8_;
      this.field_78246_f = p_i46301_6_ + (float)p_i46301_9_;
      this.field_78253_h = new PositionTextureVertex[8];
      this.field_78254_i = new TexturedQuad[6];
      float f = p_i46301_4_ + (float)p_i46301_7_;
      float f1 = p_i46301_5_ + (float)p_i46301_8_;
      float f2 = p_i46301_6_ + (float)p_i46301_9_;
      p_i46301_4_ = p_i46301_4_ - p_i46301_10_;
      p_i46301_5_ = p_i46301_5_ - p_i46301_10_;
      p_i46301_6_ = p_i46301_6_ - p_i46301_10_;
      f = f + p_i46301_10_;
      f1 = f1 + p_i46301_10_;
      f2 = f2 + p_i46301_10_;
      if (p_i46301_11_) {
         float f3 = f;
         f = p_i46301_4_;
         p_i46301_4_ = f3;
      }

      PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, p_i46301_6_, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i46301_5_, p_i46301_6_, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i46301_6_, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i46301_4_, f1, p_i46301_6_, 8.0F, 0.0F);
      PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i46301_4_, p_i46301_5_, f2, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i46301_5_, f2, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i46301_4_, f1, f2, 8.0F, 0.0F);
      this.field_78253_h[0] = positiontexturevertex7;
      this.field_78253_h[1] = positiontexturevertex;
      this.field_78253_h[2] = positiontexturevertex1;
      this.field_78253_h[3] = positiontexturevertex2;
      this.field_78253_h[4] = positiontexturevertex3;
      this.field_78253_h[5] = positiontexturevertex4;
      this.field_78253_h[6] = positiontexturevertex5;
      this.field_78253_h[7] = positiontexturevertex6;
      this.field_78254_i[0] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5}, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      this.field_78254_i[1] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2}, p_i46301_2_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      this.field_78254_i[2] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex}, p_i46301_2_ + p_i46301_9_, p_i46301_3_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      this.field_78254_i[3] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5}, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_7_, p_i46301_3_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      this.field_78254_i[4] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1}, p_i46301_2_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      this.field_78254_i[5] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6}, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_, p_i46301_3_ + p_i46301_9_, p_i46301_2_ + p_i46301_9_ + p_i46301_7_ + p_i46301_9_ + p_i46301_7_, p_i46301_3_ + p_i46301_9_ + p_i46301_8_, p_i46301_1_.field_78801_a, p_i46301_1_.field_78799_b);
      if (p_i46301_11_) {
         for(TexturedQuad texturedquad : this.field_78254_i) {
            texturedquad.func_78235_a();
         }
      }

   }

   public void func_178780_a(BufferBuilder p_178780_1_, float p_178780_2_) {
      for(TexturedQuad texturedquad : this.field_78254_i) {
         texturedquad.func_178765_a(p_178780_1_, p_178780_2_);
      }

   }

   public ModelBox func_78244_a(String p_78244_1_) {
      this.field_78247_g = p_78244_1_;
      return this;
   }
}
