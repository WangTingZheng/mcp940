package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class TileEntityRendererDispatcher {
   private final Map<Class<? extends TileEntity>, TileEntitySpecialRenderer<? extends TileEntity>> field_147559_m = Maps.<Class<? extends TileEntity>, TileEntitySpecialRenderer<? extends TileEntity>>newHashMap();
   public static TileEntityRendererDispatcher field_147556_a = new TileEntityRendererDispatcher();
   private FontRenderer field_147557_n;
   public static double field_147554_b;
   public static double field_147555_c;
   public static double field_147552_d;
   public TextureManager field_147553_e;
   public World field_147550_f;
   public Entity field_147551_g;
   public float field_147562_h;
   public float field_147563_i;
   public RayTraceResult field_190057_j;
   public double field_147560_j;
   public double field_147561_k;
   public double field_147558_l;

   private TileEntityRendererDispatcher() {
      this.field_147559_m.put(TileEntitySign.class, new TileEntitySignRenderer());
      this.field_147559_m.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
      this.field_147559_m.put(TileEntityPiston.class, new TileEntityPistonRenderer());
      this.field_147559_m.put(TileEntityChest.class, new TileEntityChestRenderer());
      this.field_147559_m.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
      this.field_147559_m.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
      this.field_147559_m.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
      this.field_147559_m.put(TileEntityEndGateway.class, new TileEntityEndGatewayRenderer());
      this.field_147559_m.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
      this.field_147559_m.put(TileEntitySkull.class, new TileEntitySkullRenderer());
      this.field_147559_m.put(TileEntityBanner.class, new TileEntityBannerRenderer());
      this.field_147559_m.put(TileEntityStructure.class, new TileEntityStructureRenderer());
      this.field_147559_m.put(TileEntityShulkerBox.class, new TileEntityShulkerBoxRenderer(new ModelShulker()));
      this.field_147559_m.put(TileEntityBed.class, new TileEntityBedRenderer());

      for(TileEntitySpecialRenderer<?> tileentityspecialrenderer : this.field_147559_m.values()) {
         tileentityspecialrenderer.func_147497_a(this);
      }

   }

   public <T extends TileEntity> TileEntitySpecialRenderer<T> func_147546_a(Class<? extends TileEntity> p_147546_1_) {
      TileEntitySpecialRenderer<? extends TileEntity> tileentityspecialrenderer = (TileEntitySpecialRenderer)this.field_147559_m.get(p_147546_1_);
      if (tileentityspecialrenderer == null && p_147546_1_ != TileEntity.class) {
         tileentityspecialrenderer = this.<TileEntity>func_147546_a(p_147546_1_.getSuperclass());
         this.field_147559_m.put(p_147546_1_, tileentityspecialrenderer);
      }

      return tileentityspecialrenderer;
   }

   @Nullable
   public <T extends TileEntity> TileEntitySpecialRenderer<T> func_147547_b(@Nullable TileEntity p_147547_1_) {
      return p_147547_1_ == null ? null : this.func_147546_a(p_147547_1_.getClass());
   }

   public void func_190056_a(World p_190056_1_, TextureManager p_190056_2_, FontRenderer p_190056_3_, Entity p_190056_4_, RayTraceResult p_190056_5_, float p_190056_6_) {
      if (this.field_147550_f != p_190056_1_) {
         this.func_147543_a(p_190056_1_);
      }

      this.field_147553_e = p_190056_2_;
      this.field_147551_g = p_190056_4_;
      this.field_147557_n = p_190056_3_;
      this.field_190057_j = p_190056_5_;
      this.field_147562_h = p_190056_4_.field_70126_B + (p_190056_4_.field_70177_z - p_190056_4_.field_70126_B) * p_190056_6_;
      this.field_147563_i = p_190056_4_.field_70127_C + (p_190056_4_.field_70125_A - p_190056_4_.field_70127_C) * p_190056_6_;
      this.field_147560_j = p_190056_4_.field_70142_S + (p_190056_4_.field_70165_t - p_190056_4_.field_70142_S) * (double)p_190056_6_;
      this.field_147561_k = p_190056_4_.field_70137_T + (p_190056_4_.field_70163_u - p_190056_4_.field_70137_T) * (double)p_190056_6_;
      this.field_147558_l = p_190056_4_.field_70136_U + (p_190056_4_.field_70161_v - p_190056_4_.field_70136_U) * (double)p_190056_6_;
   }

   public void func_180546_a(TileEntity p_180546_1_, float p_180546_2_, int p_180546_3_) {
      if (p_180546_1_.func_145835_a(this.field_147560_j, this.field_147561_k, this.field_147558_l) < p_180546_1_.func_145833_n()) {
         RenderHelper.func_74519_b();
         int i = this.field_147550_f.func_175626_b(p_180546_1_.func_174877_v(), 0);
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos blockpos = p_180546_1_.func_174877_v();
         this.func_192854_a(p_180546_1_, (double)blockpos.func_177958_n() - field_147554_b, (double)blockpos.func_177956_o() - field_147555_c, (double)blockpos.func_177952_p() - field_147552_d, p_180546_2_, p_180546_3_, 1.0F);
      }

   }

   public void func_147549_a(TileEntity p_147549_1_, double p_147549_2_, double p_147549_4_, double p_147549_6_, float p_147549_8_) {
      this.func_192855_a(p_147549_1_, p_147549_2_, p_147549_4_, p_147549_6_, p_147549_8_, 1.0F);
   }

   public void func_192855_a(TileEntity p_192855_1_, double p_192855_2_, double p_192855_4_, double p_192855_6_, float p_192855_8_, float p_192855_9_) {
      this.func_192854_a(p_192855_1_, p_192855_2_, p_192855_4_, p_192855_6_, p_192855_8_, -1, p_192855_9_);
   }

   public void func_192854_a(TileEntity p_192854_1_, double p_192854_2_, double p_192854_4_, double p_192854_6_, float p_192854_8_, int p_192854_9_, float p_192854_10_) {
      TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = this.<TileEntity>func_147547_b(p_192854_1_);
      if (tileentityspecialrenderer != null) {
         try {
            tileentityspecialrenderer.func_192841_a(p_192854_1_, p_192854_2_, p_192854_4_, p_192854_6_, p_192854_8_, p_192854_9_, p_192854_10_);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Rendering Block Entity");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block Entity Details");
            p_192854_1_.func_145828_a(crashreportcategory);
            throw new ReportedException(crashreport);
         }
      }

   }

   public void func_147543_a(@Nullable World p_147543_1_) {
      this.field_147550_f = p_147543_1_;
      if (p_147543_1_ == null) {
         this.field_147551_g = null;
      }

   }

   public FontRenderer func_147548_a() {
      return this.field_147557_n;
   }
}
