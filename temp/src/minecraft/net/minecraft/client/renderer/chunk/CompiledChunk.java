package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

public class CompiledChunk {
   public static final CompiledChunk field_178502_a = new CompiledChunk() {
      protected void func_178486_a(BlockRenderLayer p_178486_1_) {
         throw new UnsupportedOperationException();
      }

      public void func_178493_c(BlockRenderLayer p_178493_1_) {
         throw new UnsupportedOperationException();
      }

      public boolean func_178495_a(EnumFacing p_178495_1_, EnumFacing p_178495_2_) {
         return false;
      }
   };
   private final boolean[] field_178500_b = new boolean[BlockRenderLayer.values().length];
   private final boolean[] field_178501_c = new boolean[BlockRenderLayer.values().length];
   private boolean field_178498_d = true;
   private final List<TileEntity> field_178499_e = Lists.<TileEntity>newArrayList();
   private SetVisibility field_178496_f = new SetVisibility();
   private BufferBuilder.State field_178497_g;

   public boolean func_178489_a() {
      return this.field_178498_d;
   }

   protected void func_178486_a(BlockRenderLayer p_178486_1_) {
      this.field_178498_d = false;
      this.field_178500_b[p_178486_1_.ordinal()] = true;
   }

   public boolean func_178491_b(BlockRenderLayer p_178491_1_) {
      return !this.field_178500_b[p_178491_1_.ordinal()];
   }

   public void func_178493_c(BlockRenderLayer p_178493_1_) {
      this.field_178501_c[p_178493_1_.ordinal()] = true;
   }

   public boolean func_178492_d(BlockRenderLayer p_178492_1_) {
      return this.field_178501_c[p_178492_1_.ordinal()];
   }

   public List<TileEntity> func_178485_b() {
      return this.field_178499_e;
   }

   public void func_178490_a(TileEntity p_178490_1_) {
      this.field_178499_e.add(p_178490_1_);
   }

   public boolean func_178495_a(EnumFacing p_178495_1_, EnumFacing p_178495_2_) {
      return this.field_178496_f.func_178621_a(p_178495_1_, p_178495_2_);
   }

   public void func_178488_a(SetVisibility p_178488_1_) {
      this.field_178496_f = p_178488_1_;
   }

   public BufferBuilder.State func_178487_c() {
      return this.field_178497_g;
   }

   public void func_178494_a(BufferBuilder.State p_178494_1_) {
      this.field_178497_g = p_178494_1_;
   }
}
