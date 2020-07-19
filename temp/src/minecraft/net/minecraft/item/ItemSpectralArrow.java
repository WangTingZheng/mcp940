package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.World;

public class ItemSpectralArrow extends ItemArrow {
   public EntityArrow func_185052_a(World p_185052_1_, ItemStack p_185052_2_, EntityLivingBase p_185052_3_) {
      return new EntitySpectralArrow(p_185052_1_, p_185052_3_);
   }
}
