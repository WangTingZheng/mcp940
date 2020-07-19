package net.minecraft.command;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public interface ICommandSender {
   String func_70005_c_();

   default ITextComponent func_145748_c_() {
      return new TextComponentString(this.func_70005_c_());
   }

   default void func_145747_a(ITextComponent p_145747_1_) {
   }

   boolean func_70003_b(int var1, String var2);

   default BlockPos func_180425_c() {
      return BlockPos.field_177992_a;
   }

   default Vec3d func_174791_d() {
      return Vec3d.field_186680_a;
   }

   World func_130014_f_();

   @Nullable
   default Entity func_174793_f() {
      return null;
   }

   default boolean func_174792_t_() {
      return false;
   }

   default void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_) {
   }

   @Nullable
   MinecraftServer func_184102_h();
}
