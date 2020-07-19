package net.minecraft.block.state;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockProperties {
   Material func_185904_a();

   boolean func_185913_b();

   boolean func_189884_a(Entity var1);

   int func_185891_c();

   int func_185906_d();

   boolean func_185895_e();

   boolean func_185916_f();

   MapColor func_185909_g(IBlockAccess var1, BlockPos var2);

   IBlockState func_185907_a(Rotation var1);

   IBlockState func_185902_a(Mirror var1);

   boolean func_185917_h();

   boolean func_191057_i();

   EnumBlockRenderType func_185901_i();

   int func_185889_a(IBlockAccess var1, BlockPos var2);

   float func_185892_j();

   boolean func_185898_k();

   boolean func_185915_l();

   boolean func_185897_m();

   int func_185911_a(IBlockAccess var1, BlockPos var2, EnumFacing var3);

   boolean func_185912_n();

   int func_185888_a(World var1, BlockPos var2);

   float func_185887_b(World var1, BlockPos var2);

   float func_185903_a(EntityPlayer var1, World var2, BlockPos var3);

   int func_185893_b(IBlockAccess var1, BlockPos var2, EnumFacing var3);

   EnumPushReaction func_185905_o();

   IBlockState func_185899_b(IBlockAccess var1, BlockPos var2);

   AxisAlignedBB func_185918_c(World var1, BlockPos var2);

   boolean func_185894_c(IBlockAccess var1, BlockPos var2, EnumFacing var3);

   boolean func_185914_p();

   @Nullable
   AxisAlignedBB func_185890_d(IBlockAccess var1, BlockPos var2);

   void func_185908_a(World var1, BlockPos var2, AxisAlignedBB var3, List<AxisAlignedBB> var4, @Nullable Entity var5, boolean var6);

   AxisAlignedBB func_185900_c(IBlockAccess var1, BlockPos var2);

   RayTraceResult func_185910_a(World var1, BlockPos var2, Vec3d var3, Vec3d var4);

   boolean func_185896_q();

   Vec3d func_191059_e(IBlockAccess var1, BlockPos var2);

   boolean func_191058_s();

   BlockFaceShape func_193401_d(IBlockAccess var1, BlockPos var2, EnumFacing var3);
}
