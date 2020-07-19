package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;

public class ChunkCompileTaskGenerator implements Comparable<ChunkCompileTaskGenerator> {
   private final RenderChunk field_178553_a;
   private final ReentrantLock field_178551_b = new ReentrantLock();
   private final List<Runnable> field_178552_c = Lists.<Runnable>newArrayList();
   private final ChunkCompileTaskGenerator.Type field_178549_d;
   private final double field_188229_e;
   private RegionRenderCacheBuilder field_178550_e;
   private CompiledChunk field_178547_f;
   private ChunkCompileTaskGenerator.Status field_178548_g = ChunkCompileTaskGenerator.Status.PENDING;
   private boolean field_178554_h;

   public ChunkCompileTaskGenerator(RenderChunk p_i46560_1_, ChunkCompileTaskGenerator.Type p_i46560_2_, double p_i46560_3_) {
      this.field_178553_a = p_i46560_1_;
      this.field_178549_d = p_i46560_2_;
      this.field_188229_e = p_i46560_3_;
   }

   public ChunkCompileTaskGenerator.Status func_178546_a() {
      return this.field_178548_g;
   }

   public RenderChunk func_178536_b() {
      return this.field_178553_a;
   }

   public CompiledChunk func_178544_c() {
      return this.field_178547_f;
   }

   public void func_178543_a(CompiledChunk p_178543_1_) {
      this.field_178547_f = p_178543_1_;
   }

   public RegionRenderCacheBuilder func_178545_d() {
      return this.field_178550_e;
   }

   public void func_178541_a(RegionRenderCacheBuilder p_178541_1_) {
      this.field_178550_e = p_178541_1_;
   }

   public void func_178535_a(ChunkCompileTaskGenerator.Status p_178535_1_) {
      this.field_178551_b.lock();

      try {
         this.field_178548_g = p_178535_1_;
      } finally {
         this.field_178551_b.unlock();
      }

   }

   public void func_178542_e() {
      this.field_178551_b.lock();

      try {
         if (this.field_178549_d == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK && this.field_178548_g != ChunkCompileTaskGenerator.Status.DONE) {
            this.field_178553_a.func_178575_a(false);
         }

         this.field_178554_h = true;
         this.field_178548_g = ChunkCompileTaskGenerator.Status.DONE;

         for(Runnable runnable : this.field_178552_c) {
            runnable.run();
         }
      } finally {
         this.field_178551_b.unlock();
      }

   }

   public void func_178539_a(Runnable p_178539_1_) {
      this.field_178551_b.lock();

      try {
         this.field_178552_c.add(p_178539_1_);
         if (this.field_178554_h) {
            p_178539_1_.run();
         }
      } finally {
         this.field_178551_b.unlock();
      }

   }

   public ReentrantLock func_178540_f() {
      return this.field_178551_b;
   }

   public ChunkCompileTaskGenerator.Type func_178538_g() {
      return this.field_178549_d;
   }

   public boolean func_178537_h() {
      return this.field_178554_h;
   }

   public int compareTo(ChunkCompileTaskGenerator p_compareTo_1_) {
      return Doubles.compare(this.field_188229_e, p_compareTo_1_.field_188229_e);
   }

   public double func_188228_i() {
      return this.field_188229_e;
   }

   public static enum Status {
      PENDING,
      COMPILING,
      UPLOADING,
      DONE;
   }

   public static enum Type {
      REBUILD_CHUNK,
      RESORT_TRANSPARENCY;
   }
}
