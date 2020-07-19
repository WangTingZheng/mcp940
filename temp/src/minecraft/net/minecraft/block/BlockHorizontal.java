package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public abstract class BlockHorizontal extends Block {
   public static final PropertyDirection field_185512_D = PropertyDirection.func_177712_a("facing", EnumFacing.Plane.HORIZONTAL);

   protected BlockHorizontal(Material p_i46685_1_) {
      super(p_i46685_1_);
   }

   protected BlockHorizontal(Material p_i46686_1_, MapColor p_i46686_2_) {
      super(p_i46686_1_, p_i46686_2_);
   }
}
