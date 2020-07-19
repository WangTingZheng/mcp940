package net.minecraft.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ModelGuardian extends ModelBase {
   private final ModelRenderer field_178710_a;
   private final ModelRenderer field_178708_b;
   private final ModelRenderer[] field_178709_c;
   private final ModelRenderer[] field_178707_d;

   public ModelGuardian() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_178709_c = new ModelRenderer[12];
      this.field_178710_a = new ModelRenderer(this);
      this.field_178710_a.func_78784_a(0, 0).func_78789_a(-6.0F, 10.0F, -8.0F, 12, 12, 16);
      this.field_178710_a.func_78784_a(0, 28).func_78789_a(-8.0F, 10.0F, -6.0F, 2, 12, 12);
      this.field_178710_a.func_78784_a(0, 28).func_178769_a(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
      this.field_178710_a.func_78784_a(16, 40).func_78789_a(-6.0F, 8.0F, -6.0F, 12, 2, 12);
      this.field_178710_a.func_78784_a(16, 40).func_78789_a(-6.0F, 22.0F, -6.0F, 12, 2, 12);

      for(int i = 0; i < this.field_178709_c.length; ++i) {
         this.field_178709_c[i] = new ModelRenderer(this, 0, 0);
         this.field_178709_c[i].func_78789_a(-1.0F, -4.5F, -1.0F, 2, 9, 2);
         this.field_178710_a.func_78792_a(this.field_178709_c[i]);
      }

      this.field_178708_b = new ModelRenderer(this, 8, 0);
      this.field_178708_b.func_78789_a(-1.0F, 15.0F, 0.0F, 2, 2, 1);
      this.field_178710_a.func_78792_a(this.field_178708_b);
      this.field_178707_d = new ModelRenderer[3];
      this.field_178707_d[0] = new ModelRenderer(this, 40, 0);
      this.field_178707_d[0].func_78789_a(-2.0F, 14.0F, 7.0F, 4, 4, 8);
      this.field_178707_d[1] = new ModelRenderer(this, 0, 54);
      this.field_178707_d[1].func_78789_a(0.0F, 14.0F, 0.0F, 3, 3, 7);
      this.field_178707_d[2] = new ModelRenderer(this);
      this.field_178707_d[2].func_78784_a(41, 32).func_78789_a(0.0F, 14.0F, 0.0F, 2, 2, 6);
      this.field_178707_d[2].func_78784_a(25, 19).func_78789_a(1.0F, 10.5F, 3.0F, 1, 9, 9);
      this.field_178710_a.func_78792_a(this.field_178707_d[0]);
      this.field_178707_d[0].func_78792_a(this.field_178707_d[1]);
      this.field_178707_d[1].func_78792_a(this.field_178707_d[2]);
   }

   public void func_78088_a(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      this.func_78087_a(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
      this.field_178710_a.func_78785_a(p_78088_7_);
   }

   public void func_78087_a(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      EntityGuardian entityguardian = (EntityGuardian)p_78087_7_;
      float f = p_78087_3_ - (float)entityguardian.field_70173_aa;
      this.field_178710_a.field_78796_g = p_78087_4_ * 0.017453292F;
      this.field_178710_a.field_78795_f = p_78087_5_ * 0.017453292F;
      float[] afloat = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
      float[] afloat1 = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
      float[] afloat2 = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
      float[] afloat3 = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
      float[] afloat4 = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
      float[] afloat5 = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
      float f1 = (1.0F - entityguardian.func_175469_o(f)) * 0.55F;

      for(int i = 0; i < 12; ++i) {
         this.field_178709_c[i].field_78795_f = 3.1415927F * afloat[i];
         this.field_178709_c[i].field_78796_g = 3.1415927F * afloat1[i];
         this.field_178709_c[i].field_78808_h = 3.1415927F * afloat2[i];
         this.field_178709_c[i].field_78800_c = afloat3[i] * (1.0F + MathHelper.func_76134_b(p_78087_3_ * 1.5F + (float)i) * 0.01F - f1);
         this.field_178709_c[i].field_78797_d = 16.0F + afloat4[i] * (1.0F + MathHelper.func_76134_b(p_78087_3_ * 1.5F + (float)i) * 0.01F - f1);
         this.field_178709_c[i].field_78798_e = afloat5[i] * (1.0F + MathHelper.func_76134_b(p_78087_3_ * 1.5F + (float)i) * 0.01F - f1);
      }

      this.field_178708_b.field_78798_e = -8.25F;
      Entity entity = Minecraft.func_71410_x().func_175606_aa();
      if (entityguardian.func_175474_cn()) {
         entity = entityguardian.func_175466_co();
      }

      if (entity != null) {
         Vec3d vec3d = entity.func_174824_e(0.0F);
         Vec3d vec3d1 = p_78087_7_.func_174824_e(0.0F);
         double d0 = vec3d.field_72448_b - vec3d1.field_72448_b;
         if (d0 > 0.0D) {
            this.field_178708_b.field_78797_d = 0.0F;
         } else {
            this.field_178708_b.field_78797_d = 1.0F;
         }

         Vec3d vec3d2 = p_78087_7_.func_70676_i(0.0F);
         vec3d2 = new Vec3d(vec3d2.field_72450_a, 0.0D, vec3d2.field_72449_c);
         Vec3d vec3d3 = (new Vec3d(vec3d1.field_72450_a - vec3d.field_72450_a, 0.0D, vec3d1.field_72449_c - vec3d.field_72449_c)).func_72432_b().func_178785_b(1.5707964F);
         double d1 = vec3d2.func_72430_b(vec3d3);
         this.field_178708_b.field_78800_c = MathHelper.func_76129_c((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
      }

      this.field_178708_b.field_78806_j = true;
      float f2 = entityguardian.func_175471_a(f);
      this.field_178707_d[0].field_78796_g = MathHelper.func_76126_a(f2) * 3.1415927F * 0.05F;
      this.field_178707_d[1].field_78796_g = MathHelper.func_76126_a(f2) * 3.1415927F * 0.1F;
      this.field_178707_d[1].field_78800_c = -1.5F;
      this.field_178707_d[1].field_78797_d = 0.5F;
      this.field_178707_d[1].field_78798_e = 14.0F;
      this.field_178707_d[2].field_78796_g = MathHelper.func_76126_a(f2) * 3.1415927F * 0.15F;
      this.field_178707_d[2].field_78800_c = 0.5F;
      this.field_178707_d[2].field_78797_d = 0.5F;
      this.field_178707_d[2].field_78798_e = 6.0F;
   }
}
