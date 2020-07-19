package net.minecraft.client.renderer.entity.layers;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class LayerEntityOnShoulder implements LayerRenderer<EntityPlayer> {
   private final RenderManager field_192867_c;
   protected RenderLivingBase<? extends EntityLivingBase> field_192865_a;
   private ModelBase field_192868_d;
   private ResourceLocation field_192869_e;
   private UUID field_192870_f;
   private Class<?> field_192871_g;
   protected RenderLivingBase<? extends EntityLivingBase> field_192866_b;
   private ModelBase field_192872_h;
   private ResourceLocation field_192873_i;
   private UUID field_192874_j;
   private Class<?> field_192875_k;

   public LayerEntityOnShoulder(RenderManager p_i47370_1_) {
      this.field_192867_c = p_i47370_1_;
   }

   public void func_177141_a(EntityPlayer p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      if (p_177141_1_.func_192023_dk() != null || p_177141_1_.func_192025_dl() != null) {
         GlStateManager.func_179091_B();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         NBTTagCompound nbttagcompound = p_177141_1_.func_192023_dk();
         if (!nbttagcompound.func_82582_d()) {
            LayerEntityOnShoulder.DataHolder layerentityonshoulder$dataholder = this.func_192864_a(p_177141_1_, this.field_192870_f, nbttagcompound, this.field_192865_a, this.field_192868_d, this.field_192869_e, this.field_192871_g, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, true);
            this.field_192870_f = layerentityonshoulder$dataholder.field_192882_a;
            this.field_192865_a = layerentityonshoulder$dataholder.field_192883_b;
            this.field_192869_e = layerentityonshoulder$dataholder.field_192885_d;
            this.field_192868_d = layerentityonshoulder$dataholder.field_192884_c;
            this.field_192871_g = layerentityonshoulder$dataholder.field_192886_e;
         }

         NBTTagCompound nbttagcompound1 = p_177141_1_.func_192025_dl();
         if (!nbttagcompound1.func_82582_d()) {
            LayerEntityOnShoulder.DataHolder layerentityonshoulder$dataholder1 = this.func_192864_a(p_177141_1_, this.field_192874_j, nbttagcompound1, this.field_192866_b, this.field_192872_h, this.field_192873_i, this.field_192875_k, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_, false);
            this.field_192874_j = layerentityonshoulder$dataholder1.field_192882_a;
            this.field_192866_b = layerentityonshoulder$dataholder1.field_192883_b;
            this.field_192873_i = layerentityonshoulder$dataholder1.field_192885_d;
            this.field_192872_h = layerentityonshoulder$dataholder1.field_192884_c;
            this.field_192875_k = layerentityonshoulder$dataholder1.field_192886_e;
         }

         GlStateManager.func_179101_C();
      }
   }

   private LayerEntityOnShoulder.DataHolder func_192864_a(EntityPlayer p_192864_1_, @Nullable UUID p_192864_2_, NBTTagCompound p_192864_3_, RenderLivingBase<? extends EntityLivingBase> p_192864_4_, ModelBase p_192864_5_, ResourceLocation p_192864_6_, Class<?> p_192864_7_, float p_192864_8_, float p_192864_9_, float p_192864_10_, float p_192864_11_, float p_192864_12_, float p_192864_13_, float p_192864_14_, boolean p_192864_15_) {
      if (p_192864_2_ == null || !p_192864_2_.equals(p_192864_3_.func_186857_a("UUID"))) {
         p_192864_2_ = p_192864_3_.func_186857_a("UUID");
         p_192864_7_ = EntityList.func_192839_a(p_192864_3_.func_74779_i("id"));
         if (p_192864_7_ == EntityParrot.class) {
            p_192864_4_ = new RenderParrot(this.field_192867_c);
            p_192864_5_ = new ModelParrot();
            p_192864_6_ = RenderParrot.field_192862_a[p_192864_3_.func_74762_e("Variant")];
         }
      }

      p_192864_4_.func_110776_a(p_192864_6_);
      GlStateManager.func_179094_E();
      float f = p_192864_1_.func_70093_af() ? -1.3F : -1.5F;
      float f1 = p_192864_15_ ? 0.4F : -0.4F;
      GlStateManager.func_179109_b(f1, f, 0.0F);
      if (p_192864_7_ == EntityParrot.class) {
         p_192864_11_ = 0.0F;
      }

      p_192864_5_.func_78086_a(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_10_);
      p_192864_5_.func_78087_a(p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_, p_192864_1_);
      p_192864_5_.func_78088_a(p_192864_1_, p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_);
      GlStateManager.func_179121_F();
      return new LayerEntityOnShoulder.DataHolder(p_192864_2_, p_192864_4_, p_192864_5_, p_192864_6_, p_192864_7_);
   }

   public boolean func_177142_b() {
      return false;
   }

   class DataHolder {
      public UUID field_192882_a;
      public RenderLivingBase<? extends EntityLivingBase> field_192883_b;
      public ModelBase field_192884_c;
      public ResourceLocation field_192885_d;
      public Class<?> field_192886_e;

      public DataHolder(UUID p_i47463_2_, RenderLivingBase<? extends EntityLivingBase> p_i47463_3_, ModelBase p_i47463_4_, ResourceLocation p_i47463_5_, Class<?> p_i47463_6_) {
         this.field_192882_a = p_i47463_2_;
         this.field_192883_b = p_i47463_3_;
         this.field_192884_c = p_i47463_4_;
         this.field_192885_d = p_i47463_5_;
         this.field_192886_e = p_i47463_6_;
      }
   }
}
