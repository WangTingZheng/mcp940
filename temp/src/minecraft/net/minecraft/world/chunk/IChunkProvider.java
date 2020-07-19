package net.minecraft.world.chunk;

import javax.annotation.Nullable;

public interface IChunkProvider {
   @Nullable
   Chunk func_186026_b(int var1, int var2);

   Chunk func_186025_d(int var1, int var2);

   boolean func_73156_b();

   String func_73148_d();

   boolean func_191062_e(int var1, int var2);
}
