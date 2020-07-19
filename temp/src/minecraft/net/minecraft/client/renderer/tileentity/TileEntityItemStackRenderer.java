package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.StringUtils;

public class TileEntityItemStackRenderer {
   private static final TileEntityShulkerBox[] field_191274_b = new TileEntityShulkerBox[16];
   public static TileEntityItemStackRenderer field_147719_a;
   private final TileEntityChest field_147717_b = new TileEntityChest(BlockChest.Type.BASIC);
   private final TileEntityChest field_147718_c = new TileEntityChest(BlockChest.Type.TRAP);
   private final TileEntityEnderChest field_147716_d = new TileEntityEnderChest();
   private final TileEntityBanner field_179024_e = new TileEntityBanner();
   private final TileEntityBed field_193843_g = new TileEntityBed();
   private final TileEntitySkull field_179023_f = new TileEntitySkull();
   private final ModelShield field_187318_g = new ModelShield();

   public void func_179022_a(ItemStack p_179022_1_) {
      this.func_192838_a(p_179022_1_, 1.0F);
   }

   public void func_192838_a(ItemStack p_192838_1_, float p_192838_2_) {
      Item item = p_192838_1_.func_77973_b();
      if (item == Items.field_179564_cE) {
         this.field_179024_e.func_175112_a(p_192838_1_, false);
         TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.field_179024_e, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
      } else if (item == Items.field_151104_aV) {
         this.field_193843_g.func_193051_a(p_192838_1_);
         TileEntityRendererDispatcher.field_147556_a.func_147549_a(this.field_193843_g, 0.0D, 0.0D, 0.0D, 0.0F);
      } else if (item == Items.field_185159_cQ) {
         if (p_192838_1_.func_179543_a("BlockEntityTag") != null) {
            this.field_179024_e.func_175112_a(p_192838_1_, true);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(BannerTextures.field_187485_b.func_187478_a(this.field_179024_e.func_175116_e(), this.field_179024_e.func_175114_c(), this.field_179024_e.func_175110_d()));
         } else {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(BannerTextures.field_187486_c);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
         this.field_187318_g.func_187062_a();
         GlStateManager.func_179121_F();
      } else if (item == Items.field_151144_bL) {
         GameProfile gameprofile = null;
         if (p_192838_1_.func_77942_o()) {
            NBTTagCompound nbttagcompound = p_192838_1_.func_77978_p();
            if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
               gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
            } else if (nbttagcompound.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.func_74779_i("SkullOwner"))) {
               GameProfile gameprofile1 = new GameProfile((UUID)null, nbttagcompound.func_74779_i("SkullOwner"));
               gameprofile = TileEntitySkull.func_174884_b(gameprofile1);
               nbttagcompound.func_82580_o("SkullOwner");
               nbttagcompound.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), gameprofile));
            }
         }

         if (TileEntitySkullRenderer.field_147536_b != null) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179129_p();
            TileEntitySkullRenderer.field_147536_b.func_188190_a(0.0F, 0.0F, 0.0F, EnumFacing.UP, 180.0F, p_192838_1_.func_77960_j(), gameprofile, -1, 0.0F);
            GlStateManager.func_179089_o();
            GlStateManager.func_179121_F();
         }
      } else if (item == Item.func_150898_a(Blocks.field_150477_bB)) {
         TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.field_147716_d, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
      } else if (item == Item.func_150898_a(Blocks.field_150447_bR)) {
         TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
      } else if (Block.func_149634_a(item) instanceof BlockShulkerBox) {
         TileEntityRendererDispatcher.field_147556_a.func_192855_a(field_191274_b[BlockShulkerBox.func_190955_b(item).func_176765_a()], 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
      } else {
         TileEntityRendererDispatcher.field_147556_a.func_192855_a(this.field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
      }

   }

   static {
      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         field_191274_b[enumdyecolor.func_176765_a()] = new TileEntityShulkerBox(enumdyecolor);
      }

      field_147719_a = new TileEntityItemStackRenderer();
   }
}
