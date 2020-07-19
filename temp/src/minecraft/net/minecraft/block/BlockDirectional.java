package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;

public abstract class BlockDirectional extends Block {
   public static final PropertyDirection field_176387_N = PropertyDirection.func_177714_a("facing");

   protected BlockDirectional(Material p_i45401_1_) {
      super(p_i45401_1_);
   }
}
