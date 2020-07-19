package net.minecraft.world.chunk.storage;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface IChunkLoader {
   @Nullable
   Chunk func_75815_a(World var1, int var2, int var3) throws IOException;

   void func_75816_a(World var1, Chunk var2) throws MinecraftException, IOException;

   void func_75819_b(World var1, Chunk var2) throws IOException;

   void func_75817_a();

   void func_75818_b();

   boolean func_191063_a(int var1, int var2);
}
