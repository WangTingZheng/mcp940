package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public abstract class AbstractClientPlayer extends EntityPlayer {
   private NetworkPlayerInfo field_175157_a;
   public float field_184835_a;
   public float field_184836_b;
   public float field_184837_c;

   public AbstractClientPlayer(World p_i45074_1_, GameProfile p_i45074_2_) {
      super(p_i45074_1_, p_i45074_2_);
   }

   public boolean func_175149_v() {
      NetworkPlayerInfo networkplayerinfo = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_146103_bH().getId());
      return networkplayerinfo != null && networkplayerinfo.func_178848_b() == GameType.SPECTATOR;
   }

   public boolean func_184812_l_() {
      NetworkPlayerInfo networkplayerinfo = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_146103_bH().getId());
      return networkplayerinfo != null && networkplayerinfo.func_178848_b() == GameType.CREATIVE;
   }

   public boolean func_152122_n() {
      return this.func_175155_b() != null;
   }

   @Nullable
   protected NetworkPlayerInfo func_175155_b() {
      if (this.field_175157_a == null) {
         this.field_175157_a = Minecraft.func_71410_x().func_147114_u().func_175102_a(this.func_110124_au());
      }

      return this.field_175157_a;
   }

   public boolean func_152123_o() {
      NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
      return networkplayerinfo != null && networkplayerinfo.func_178856_e();
   }

   public ResourceLocation func_110306_p() {
      NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
      return networkplayerinfo == null ? DefaultPlayerSkin.func_177334_a(this.func_110124_au()) : networkplayerinfo.func_178837_g();
   }

   @Nullable
   public ResourceLocation func_110303_q() {
      NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
      return networkplayerinfo == null ? null : networkplayerinfo.func_178861_h();
   }

   public boolean func_184833_s() {
      return this.func_175155_b() != null;
   }

   @Nullable
   public ResourceLocation func_184834_t() {
      NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
      return networkplayerinfo == null ? null : networkplayerinfo.func_187106_i();
   }

   public static ThreadDownloadImageData func_110304_a(ResourceLocation p_110304_0_, String p_110304_1_) {
      TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
      ITextureObject itextureobject = texturemanager.func_110581_b(p_110304_0_);
      if (itextureobject == null) {
         itextureobject = new ThreadDownloadImageData((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.func_76338_a(p_110304_1_)), DefaultPlayerSkin.func_177334_a(func_175147_b(p_110304_1_)), new ImageBufferDownload());
         texturemanager.func_110579_a(p_110304_0_, itextureobject);
      }

      return (ThreadDownloadImageData)itextureobject;
   }

   public static ResourceLocation func_110311_f(String p_110311_0_) {
      return new ResourceLocation("skins/" + StringUtils.func_76338_a(p_110311_0_));
   }

   public String func_175154_l() {
      NetworkPlayerInfo networkplayerinfo = this.func_175155_b();
      return networkplayerinfo == null ? DefaultPlayerSkin.func_177332_b(this.func_110124_au()) : networkplayerinfo.func_178851_f();
   }

   public float func_175156_o() {
      float f = 1.0F;
      if (this.field_71075_bZ.field_75100_b) {
         f *= 1.1F;
      }

      IAttributeInstance iattributeinstance = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      f = (float)((double)f * ((iattributeinstance.func_111126_e() / (double)this.field_71075_bZ.func_75094_b() + 1.0D) / 2.0D));
      if (this.field_71075_bZ.func_75094_b() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      if (this.func_184587_cr() && this.func_184607_cu().func_77973_b() == Items.field_151031_f) {
         int i = this.func_184612_cw();
         float f1 = (float)i / 20.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F;
         } else {
            f1 = f1 * f1;
         }

         f *= 1.0F - f1 * 0.15F;
      }

      return f;
   }
}
