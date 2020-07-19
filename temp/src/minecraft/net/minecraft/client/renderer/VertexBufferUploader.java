package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends WorldVertexBufferUploader {
   private VertexBuffer field_178179_a;

   public void func_181679_a(BufferBuilder p_181679_1_) {
      p_181679_1_.func_178965_a();
      this.field_178179_a.func_181722_a(p_181679_1_.func_178966_f());
   }

   public void func_178178_a(VertexBuffer p_178178_1_) {
      this.field_178179_a = p_178178_1_;
   }
}
