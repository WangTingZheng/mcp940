package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {
   private static final Logger field_152478_a = LogManager.getLogger();
   private final ChunkRenderDispatcher field_178477_b;
   private final RegionRenderCacheBuilder field_178478_c;
   private boolean field_188265_d;

   public ChunkRenderWorker(ChunkRenderDispatcher p_i46201_1_) {
      this(p_i46201_1_, (RegionRenderCacheBuilder)null);
   }

   public ChunkRenderWorker(ChunkRenderDispatcher p_i46202_1_, @Nullable RegionRenderCacheBuilder p_i46202_2_) {
      this.field_188265_d = true;
      this.field_178477_b = p_i46202_1_;
      this.field_178478_c = p_i46202_2_;
   }

   public void run() {
      while(this.field_188265_d) {
         try {
            this.func_178474_a(this.field_178477_b.func_178511_d());
         } catch (InterruptedException var3) {
            field_152478_a.debug("Stopping chunk worker due to interrupt");
            return;
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Batching chunks");
            Minecraft.func_71410_x().func_71404_a(Minecraft.func_71410_x().func_71396_d(crashreport));
            return;
         }
      }

   }

   protected void func_178474_a(final ChunkCompileTaskGenerator p_178474_1_) throws InterruptedException {
      p_178474_1_.func_178540_f().lock();

      try {
         if (p_178474_1_.func_178546_a() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (!p_178474_1_.func_178537_h()) {
               field_152478_a.warn("Chunk render task was {} when I expected it to be pending; ignoring task", (Object)p_178474_1_.func_178546_a());
            }

            return;
         }

         BlockPos blockpos = new BlockPos(Minecraft.func_71410_x().field_71439_g);
         BlockPos blockpos1 = p_178474_1_.func_178536_b().func_178568_j();
         int i = 16;
         int j = 8;
         int k = 24;
         if (blockpos1.func_177982_a(8, 8, 8).func_177951_i(blockpos) > 576.0D) {
            World world = p_178474_1_.func_178536_b().func_188283_p();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(blockpos1);
            if (!this.func_188263_a(blockpos$mutableblockpos.func_189533_g(blockpos1).func_189534_c(EnumFacing.WEST, 16), world) || !this.func_188263_a(blockpos$mutableblockpos.func_189533_g(blockpos1).func_189534_c(EnumFacing.NORTH, 16), world) || !this.func_188263_a(blockpos$mutableblockpos.func_189533_g(blockpos1).func_189534_c(EnumFacing.EAST, 16), world) || !this.func_188263_a(blockpos$mutableblockpos.func_189533_g(blockpos1).func_189534_c(EnumFacing.SOUTH, 16), world)) {
               return;
            }
         }

         p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.COMPILING);
      } finally {
         p_178474_1_.func_178540_f().unlock();
      }

      Entity entity = Minecraft.func_71410_x().func_175606_aa();
      if (lvt_2_2_ == null) {
         p_178474_1_.func_178542_e();
      } else {
         p_178474_1_.func_178541_a(this.func_178475_b());
         float f = (float)entity.field_70165_t;
         float f1 = (float)entity.field_70163_u + entity.func_70047_e();
         float f2 = (float)entity.field_70161_v;
         ChunkCompileTaskGenerator.Type chunkcompiletaskgenerator$type = p_178474_1_.func_178538_g();
         if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
            p_178474_1_.func_178536_b().func_178581_b(f, f1, f2, p_178474_1_);
         } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
            p_178474_1_.func_178536_b().func_178570_a(f, f1, f2, p_178474_1_);
         }

         p_178474_1_.func_178540_f().lock();

         try {
            if (p_178474_1_.func_178546_a() != ChunkCompileTaskGenerator.Status.COMPILING) {
               if (!p_178474_1_.func_178537_h()) {
                  field_152478_a.warn("Chunk render task was {} when I expected it to be compiling; aborting task", (Object)p_178474_1_.func_178546_a());
               }

               this.func_178473_b(p_178474_1_);
               return;
            }

            p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.UPLOADING);
         } finally {
            p_178474_1_.func_178540_f().unlock();
         }

         final CompiledChunk compiledchunk = p_178474_1_.func_178544_c();
         ArrayList arraylist = Lists.newArrayList();
         if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
            for(BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
               if (compiledchunk.func_178492_d(blockrenderlayer)) {
                  arraylist.add(this.field_178477_b.func_188245_a(blockrenderlayer, p_178474_1_.func_178545_d().func_179038_a(blockrenderlayer), p_178474_1_.func_178536_b(), compiledchunk, p_178474_1_.func_188228_i()));
               }
            }
         } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
            arraylist.add(this.field_178477_b.func_188245_a(BlockRenderLayer.TRANSLUCENT, p_178474_1_.func_178545_d().func_179038_a(BlockRenderLayer.TRANSLUCENT), p_178474_1_.func_178536_b(), compiledchunk, p_178474_1_.func_188228_i()));
         }

         final ListenableFuture<List<Object>> listenablefuture = Futures.allAsList(arraylist);
         p_178474_1_.func_178539_a(new Runnable() {
            public void run() {
               listenablefuture.cancel(false);
            }
         });
         Futures.addCallback(listenablefuture, new FutureCallback<List<Object>>() {
            public void onSuccess(@Nullable List<Object> p_onSuccess_1_) {
               ChunkRenderWorker.this.func_178473_b(p_178474_1_);
               p_178474_1_.func_178540_f().lock();

               label49: {
                  try {
                     if (p_178474_1_.func_178546_a() == ChunkCompileTaskGenerator.Status.UPLOADING) {
                        p_178474_1_.func_178535_a(ChunkCompileTaskGenerator.Status.DONE);
                        break label49;
                     }

                     if (!p_178474_1_.func_178537_h()) {
                        ChunkRenderWorker.field_152478_a.warn("Chunk render task was {} when I expected it to be uploading; aborting task", (Object)p_178474_1_.func_178546_a());
                     }
                  } finally {
                     p_178474_1_.func_178540_f().unlock();
                  }

                  return;
               }

               p_178474_1_.func_178536_b().func_178580_a(compiledchunk);
            }

            public void onFailure(Throwable p_onFailure_1_) {
               ChunkRenderWorker.this.func_178473_b(p_178474_1_);
               if (!(p_onFailure_1_ instanceof CancellationException) && !(p_onFailure_1_ instanceof InterruptedException)) {
                  Minecraft.func_71410_x().func_71404_a(CrashReport.func_85055_a(p_onFailure_1_, "Rendering chunk"));
               }

            }
         });
      }
   }

   private boolean func_188263_a(BlockPos p_188263_1_, World p_188263_2_) {
      return !p_188263_2_.func_72964_e(p_188263_1_.func_177958_n() >> 4, p_188263_1_.func_177952_p() >> 4).func_76621_g();
   }

   private RegionRenderCacheBuilder func_178475_b() throws InterruptedException {
      return this.field_178478_c != null ? this.field_178478_c : this.field_178477_b.func_178515_c();
   }

   private void func_178473_b(ChunkCompileTaskGenerator p_178473_1_) {
      if (this.field_178478_c == null) {
         this.field_178477_b.func_178512_a(p_178473_1_.func_178545_d());
      }

   }

   public void func_188264_a() {
      this.field_188265_d = false;
   }
}
