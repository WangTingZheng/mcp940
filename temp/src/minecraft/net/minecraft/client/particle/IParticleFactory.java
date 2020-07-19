package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.world.World;

public interface IParticleFactory {
   @Nullable
   Particle func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15);
}
