package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;

public interface IBlockStatePalette {
   int func_186041_a(IBlockState var1);

   @Nullable
   IBlockState func_186039_a(int var1);

   void func_186038_a(PacketBuffer var1);

   void func_186037_b(PacketBuffer var1);

   int func_186040_a();
}
