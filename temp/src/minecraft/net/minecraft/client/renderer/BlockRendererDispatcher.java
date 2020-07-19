package net.minecraft.client.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private final BlockModelShapes field_175028_a;
   private final BlockModelRenderer field_175027_c;
   private final ChestRenderer field_175024_d = new ChestRenderer();
   private final BlockFluidRenderer field_175025_e;

   public BlockRendererDispatcher(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
      this.field_175028_a = p_i46577_1_;
      this.field_175027_c = new BlockModelRenderer(p_i46577_2_);
      this.field_175025_e = new BlockFluidRenderer(p_i46577_2_);
   }

   public BlockModelShapes func_175023_a() {
      return this.field_175028_a;
   }

   public void func_175020_a(IBlockState p_175020_1_, BlockPos p_175020_2_, TextureAtlasSprite p_175020_3_, IBlockAccess p_175020_4_) {
      if (p_175020_1_.func_185901_i() == EnumBlockRenderType.MODEL) {
         p_175020_1_ = p_175020_1_.func_185899_b(p_175020_4_, p_175020_2_);
         IBakedModel ibakedmodel = this.field_175028_a.func_178125_b(p_175020_1_);
         IBakedModel ibakedmodel1 = (new SimpleBakedModel.Builder(p_175020_1_, ibakedmodel, p_175020_3_, p_175020_2_)).func_177645_b();
         this.field_175027_c.func_178267_a(p_175020_4_, ibakedmodel1, p_175020_1_, p_175020_2_, Tessellator.func_178181_a().func_178180_c(), true);
      }
   }

   public boolean func_175018_a(IBlockState p_175018_1_, BlockPos p_175018_2_, IBlockAccess p_175018_3_, BufferBuilder p_175018_4_) {
      try {
         EnumBlockRenderType enumblockrendertype = p_175018_1_.func_185901_i();
         if (enumblockrendertype == EnumBlockRenderType.INVISIBLE) {
            return false;
         } else {
            if (p_175018_3_.func_175624_G() != WorldType.field_180272_g) {
               try {
                  p_175018_1_ = p_175018_1_.func_185899_b(p_175018_3_, p_175018_2_);
               } catch (Exception var8) {
                  ;
               }
            }

            switch(enumblockrendertype) {
            case MODEL:
               return this.field_175027_c.func_178267_a(p_175018_3_, this.func_184389_a(p_175018_1_), p_175018_1_, p_175018_2_, p_175018_4_, true);
            case ENTITYBLOCK_ANIMATED:
               return false;
            case LIQUID:
               return this.field_175025_e.func_178270_a(p_175018_3_, p_175018_1_, p_175018_2_, p_175018_4_);
            default:
               return false;
            }
         }
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Tesselating block in world");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block being tesselated");
         CrashReportCategory.func_180523_a(crashreportcategory, p_175018_2_, p_175018_1_.func_177230_c(), p_175018_1_.func_177230_c().func_176201_c(p_175018_1_));
         throw new ReportedException(crashreport);
      }
   }

   public BlockModelRenderer func_175019_b() {
      return this.field_175027_c;
   }

   public IBakedModel func_184389_a(IBlockState p_184389_1_) {
      return this.field_175028_a.func_178125_b(p_184389_1_);
   }

   public void func_175016_a(IBlockState p_175016_1_, float p_175016_2_) {
      EnumBlockRenderType enumblockrendertype = p_175016_1_.func_185901_i();
      if (enumblockrendertype != EnumBlockRenderType.INVISIBLE) {
         switch(enumblockrendertype) {
         case MODEL:
            IBakedModel ibakedmodel = this.func_184389_a(p_175016_1_);
            this.field_175027_c.func_178266_a(ibakedmodel, p_175016_1_, p_175016_2_, true);
            break;
         case ENTITYBLOCK_ANIMATED:
            this.field_175024_d.func_178175_a(p_175016_1_.func_177230_c(), p_175016_2_);
         case LIQUID:
         }

      }
   }

   public void func_110549_a(IResourceManager p_110549_1_) {
      this.field_175025_e.func_178268_a();
   }
}
