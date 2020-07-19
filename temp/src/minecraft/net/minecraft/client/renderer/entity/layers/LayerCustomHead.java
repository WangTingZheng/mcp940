package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.StringUtils;

public class LayerCustomHead implements LayerRenderer<EntityLivingBase> {
   private final ModelRenderer field_177209_a;

   public LayerCustomHead(ModelRenderer p_i46120_1_) {
      this.field_177209_a = p_i46120_1_;
   }

   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      ItemStack itemstack = p_177141_1_.func_184582_a(EntityEquipmentSlot.HEAD);
      if (!itemstack.func_190926_b()) {
         Item item = itemstack.func_77973_b();
         Minecraft minecraft = Minecraft.func_71410_x();
         GlStateManager.func_179094_E();
         if (p_177141_1_.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         boolean flag = p_177141_1_ instanceof EntityVillager || p_177141_1_ instanceof EntityZombieVillager;
         if (p_177141_1_.func_70631_g_() && !(p_177141_1_ instanceof EntityVillager)) {
            float f = 2.0F;
            float f1 = 1.4F;
            GlStateManager.func_179109_b(0.0F, 0.5F * p_177141_8_, 0.0F);
            GlStateManager.func_179152_a(0.7F, 0.7F, 0.7F);
            GlStateManager.func_179109_b(0.0F, 16.0F * p_177141_8_, 0.0F);
         }

         this.field_177209_a.func_78794_c(0.0625F);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         if (item == Items.field_151144_bL) {
            float f2 = 1.1875F;
            GlStateManager.func_179152_a(1.1875F, -1.1875F, -1.1875F);
            if (flag) {
               GlStateManager.func_179109_b(0.0F, 0.0625F, 0.0F);
            }

            GameProfile gameprofile = null;
            if (itemstack.func_77942_o()) {
               NBTTagCompound nbttagcompound = itemstack.func_77978_p();
               if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
                  gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
               } else if (nbttagcompound.func_150297_b("SkullOwner", 8)) {
                  String s = nbttagcompound.func_74779_i("SkullOwner");
                  if (!StringUtils.isBlank(s)) {
                     gameprofile = TileEntitySkull.func_174884_b(new GameProfile((UUID)null, s));
                     nbttagcompound.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), gameprofile));
                  }
               }
            }

            TileEntitySkullRenderer.field_147536_b.func_188190_a(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.func_77960_j(), gameprofile, -1, p_177141_2_);
         } else if (!(item instanceof ItemArmor) || ((ItemArmor)item).func_185083_B_() != EntityEquipmentSlot.HEAD) {
            float f3 = 0.625F;
            GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179152_a(0.625F, -0.625F, -0.625F);
            if (flag) {
               GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
            }

            minecraft.func_175597_ag().func_178099_a(p_177141_1_, itemstack, ItemCameraTransforms.TransformType.HEAD);
         }

         GlStateManager.func_179121_F();
      }
   }

   public boolean func_177142_b() {
      return false;
   }
}
