package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.primitives.Doubles;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderDispatcher {
   private static final Logger field_178523_a = LogManager.getLogger();
   private static final ThreadFactory field_178521_b = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
   private final int field_188249_c;
   private final List<Thread> field_188250_d = Lists.<Thread>newArrayList();
   private final List<ChunkRenderWorker> field_178522_c = Lists.<ChunkRenderWorker>newArrayList();
   private final PriorityBlockingQueue<ChunkCompileTaskGenerator> field_178519_d = Queues.<ChunkCompileTaskGenerator>newPriorityBlockingQueue();
   private final BlockingQueue<RegionRenderCacheBuilder> field_178520_e;
   private final WorldVertexBufferUploader field_178517_f = new WorldVertexBufferUploader();
   private final VertexBufferUploader field_178518_g = new VertexBufferUploader();
   private final Queue<ChunkRenderDispatcher.PendingUpload> field_178524_h = Queues.<ChunkRenderDispatcher.PendingUpload>newPriorityQueue();
   private final ChunkRenderWorker field_178525_i;

   public ChunkRenderDispatcher() {
      int i = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3D) / 10485760);
      int j = Math.max(1, MathHelper.func_76125_a(Runtime.getRuntime().availableProcessors(), 1, i / 5));
      this.field_188249_c = MathHelper.func_76125_a(j * 10, 1, i);
      if (j > 1) {
         for(int k = 0; k < j; ++k) {
            ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
            Thread thread = field_178521_b.newThread(chunkrenderworker);
            thread.start();
            this.field_178522_c.add(chunkrenderworker);
            this.field_188250_d.add(thread);
         }
      }

      this.field_178520_e = Queues.<RegionRenderCacheBuilder>newArrayBlockingQueue(this.field_188249_c);

      for(int l = 0; l < this.field_188249_c; ++l) {
         this.field_178520_e.add(new RegionRenderCacheBuilder());
      }

      this.field_178525_i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public String func_178504_a() {
      return this.field_188250_d.isEmpty() ? String.format("pC: %03d, single-threaded", this.field_178519_d.size()) : String.format("pC: %03d, pU: %1d, aB: %1d", this.field_178519_d.size(), this.field_178524_h.size(), this.field_178520_e.size());
   }

   public boolean func_178516_a(long p_178516_1_) {
      boolean flag = false;

      while(true) {
         boolean flag1 = false;
         if (this.field_188250_d.isEmpty()) {
            ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.field_178519_d.poll();
            if (chunkcompiletaskgenerator != null) {
               try {
                  this.field_178525_i.func_178474_a(chunkcompiletaskgenerator);
                  flag1 = true;
               } catch (InterruptedException var8) {
                  field_178523_a.warn("Skipped task due to interrupt");
               }
            }
         }

         synchronized(this.field_178524_h) {
            if (!this.field_178524_h.isEmpty()) {
               (this.field_178524_h.poll()).field_188241_b.run();
               flag1 = true;
               flag = true;
            }
         }

         if (p_178516_1_ == 0L || !flag1 || p_178516_1_ < System.nanoTime()) {
            break;
         }
      }

      return flag;
   }

   public boolean func_178507_a(RenderChunk p_178507_1_) {
      p_178507_1_.func_178579_c().lock();

      boolean flag1;
      try {
         final ChunkCompileTaskGenerator chunkcompiletaskgenerator = p_178507_1_.func_178574_d();
         chunkcompiletaskgenerator.func_178539_a(new Runnable() {
            public void run() {
               ChunkRenderDispatcher.this.field_178519_d.remove(chunkcompiletaskgenerator);
            }
         });
         boolean flag = this.field_178519_d.offer(chunkcompiletaskgenerator);
         if (!flag) {
            chunkcompiletaskgenerator.func_178542_e();
         }

         flag1 = flag;
      } finally {
         p_178507_1_.func_178579_c().unlock();
      }

      return flag1;
   }

   public boolean func_178505_b(RenderChunk p_178505_1_) {
      p_178505_1_.func_178579_c().lock();

      boolean flag;
      try {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = p_178505_1_.func_178574_d();

         try {
            this.field_178525_i.func_178474_a(chunkcompiletaskgenerator);
         } catch (InterruptedException var7) {
            ;
         }

         flag = true;
      } finally {
         p_178505_1_.func_178579_c().unlock();
      }

      return flag;
   }

   public void func_178514_b() {
      this.func_178513_e();
      List<RegionRenderCacheBuilder> list = Lists.<RegionRenderCacheBuilder>newArrayList();

      while(list.size() != this.field_188249_c) {
         this.func_178516_a(Long.MAX_VALUE);

         try {
            list.add(this.func_178515_c());
         } catch (InterruptedException var3) {
            ;
         }
      }

      this.field_178520_e.addAll(list);
   }

   public void func_178512_a(RegionRenderCacheBuilder p_178512_1_) {
      this.field_178520_e.add(p_178512_1_);
   }

   public RegionRenderCacheBuilder func_178515_c() throws InterruptedException {
      return this.field_178520_e.take();
   }

   public ChunkCompileTaskGenerator func_178511_d() throws InterruptedException {
      return this.field_178519_d.take();
   }

   public boolean func_178509_c(RenderChunk p_178509_1_) {
      p_178509_1_.func_178579_c().lock();

      boolean flag;
      try {
         final ChunkCompileTaskGenerator chunkcompiletaskgenerator = p_178509_1_.func_178582_e();
         if (chunkcompiletaskgenerator == null) {
            flag = true;
            return flag;
         }

         chunkcompiletaskgenerator.func_178539_a(new Runnable() {
            public void run() {
               ChunkRenderDispatcher.this.field_178519_d.remove(chunkcompiletaskgenerator);
            }
         });
         flag = this.field_178519_d.offer(chunkcompiletaskgenerator);
      } finally {
         p_178509_1_.func_178579_c().unlock();
      }

      return flag;
   }

   public ListenableFuture<Object> func_188245_a(final BlockRenderLayer p_188245_1_, final BufferBuilder p_188245_2_, final RenderChunk p_188245_3_, final CompiledChunk p_188245_4_, final double p_188245_5_) {
      if (Minecraft.func_71410_x().func_152345_ab()) {
         if (OpenGlHelper.func_176075_f()) {
            this.func_178506_a(p_188245_2_, p_188245_3_.func_178565_b(p_188245_1_.ordinal()));
         } else {
            this.func_178510_a(p_188245_2_, ((ListedRenderChunk)p_188245_3_).func_178600_a(p_188245_1_, p_188245_4_), p_188245_3_);
         }

         p_188245_2_.func_178969_c(0.0D, 0.0D, 0.0D);
         return Futures.<Object>immediateFuture((Object)null);
      } else {
         ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.<Object>create(new Runnable() {
            public void run() {
               ChunkRenderDispatcher.this.func_188245_a(p_188245_1_, p_188245_2_, p_188245_3_, p_188245_4_, p_188245_5_);
            }
         }, (Object)null);
         synchronized(this.field_178524_h) {
            this.field_178524_h.add(new ChunkRenderDispatcher.PendingUpload(listenablefuturetask, p_188245_5_));
            return listenablefuturetask;
         }
      }
   }

   private void func_178510_a(BufferBuilder p_178510_1_, int p_178510_2_, RenderChunk p_178510_3_) {
      GlStateManager.func_187423_f(p_178510_2_, 4864);
      GlStateManager.func_179094_E();
      p_178510_3_.func_178572_f();
      this.field_178517_f.func_181679_a(p_178510_1_);
      GlStateManager.func_179121_F();
      GlStateManager.func_187415_K();
   }

   private void func_178506_a(BufferBuilder p_178506_1_, VertexBuffer p_178506_2_) {
      this.field_178518_g.func_178178_a(p_178506_2_);
      this.field_178518_g.func_181679_a(p_178506_1_);
   }

   public void func_178513_e() {
      while(!this.field_178519_d.isEmpty()) {
         ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.field_178519_d.poll();
         if (chunkcompiletaskgenerator != null) {
            chunkcompiletaskgenerator.func_178542_e();
         }
      }

   }

   public boolean func_188247_f() {
      return this.field_178519_d.isEmpty() && this.field_178524_h.isEmpty();
   }

   public void func_188244_g() {
      this.func_178513_e();

      for(ChunkRenderWorker chunkrenderworker : this.field_178522_c) {
         chunkrenderworker.func_188264_a();
      }

      for(Thread thread : this.field_188250_d) {
         try {
            thread.interrupt();
            thread.join();
         } catch (InterruptedException interruptedexception) {
            field_178523_a.warn("Interrupted whilst waiting for worker to die", (Throwable)interruptedexception);
         }
      }

      this.field_178520_e.clear();
   }

   public boolean func_188248_h() {
      return this.field_178520_e.isEmpty();
   }

   class PendingUpload implements Comparable<ChunkRenderDispatcher.PendingUpload> {
      private final ListenableFutureTask<Object> field_188241_b;
      private final double field_188242_c;

      public PendingUpload(ListenableFutureTask<Object> p_i46994_2_, double p_i46994_3_) {
         this.field_188241_b = p_i46994_2_;
         this.field_188242_c = p_i46994_3_;
      }

      public int compareTo(ChunkRenderDispatcher.PendingUpload p_compareTo_1_) {
         return Doubles.compare(this.field_188242_c, p_compareTo_1_.field_188242_c);
      }
   }
}
