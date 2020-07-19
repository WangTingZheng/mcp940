package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderEnd extends WorldProvider {
   private DragonFightManager field_186064_g;

   public void func_76572_b() {
      this.field_76578_c = new BiomeProviderSingle(Biomes.field_76779_k);
      NBTTagCompound nbttagcompound = this.field_76579_a.func_72912_H().func_186347_a(DimensionType.THE_END);
      this.field_186064_g = this.field_76579_a instanceof WorldServer ? new DragonFightManager((WorldServer)this.field_76579_a, nbttagcompound.func_74775_l("DragonFight")) : null;
   }

   public IChunkGenerator func_186060_c() {
      return new ChunkGeneratorEnd(this.field_76579_a, this.field_76579_a.func_72912_H().func_76089_r(), this.field_76579_a.func_72905_C(), this.func_177496_h());
   }

   public float func_76563_a(long p_76563_1_, float p_76563_3_) {
      return 0.0F;
   }

   @Nullable
   public float[] func_76560_a(float p_76560_1_, float p_76560_2_) {
      return null;
   }

   public Vec3d func_76562_b(float p_76562_1_, float p_76562_2_) {
      int i = 10518688;
      float f = MathHelper.func_76134_b(p_76562_1_ * 6.2831855F) * 2.0F + 0.5F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      float f1 = 0.627451F;
      float f2 = 0.5019608F;
      float f3 = 0.627451F;
      f1 = f1 * (f * 0.0F + 0.15F);
      f2 = f2 * (f * 0.0F + 0.15F);
      f3 = f3 * (f * 0.0F + 0.15F);
      return new Vec3d((double)f1, (double)f2, (double)f3);
   }

   public boolean func_76561_g() {
      return false;
   }

   public boolean func_76567_e() {
      return false;
   }

   public boolean func_76569_d() {
      return false;
   }

   public float func_76571_f() {
      return 8.0F;
   }

   public boolean func_76566_a(int p_76566_1_, int p_76566_2_) {
      return this.field_76579_a.func_184141_c(new BlockPos(p_76566_1_, 0, p_76566_2_)).func_185904_a().func_76230_c();
   }

   public BlockPos func_177496_h() {
      return new BlockPos(100, 50, 0);
   }

   public int func_76557_i() {
      return 50;
   }

   public boolean func_76568_b(int p_76568_1_, int p_76568_2_) {
      return false;
   }

   public DimensionType func_186058_p() {
      return DimensionType.THE_END;
   }

   public void func_186057_q() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (this.field_186064_g != null) {
         nbttagcompound.func_74782_a("DragonFight", this.field_186064_g.func_186088_a());
      }

      this.field_76579_a.func_72912_H().func_186345_a(DimensionType.THE_END, nbttagcompound);
   }

   public void func_186059_r() {
      if (this.field_186064_g != null) {
         this.field_186064_g.func_186105_b();
      }

   }

   @Nullable
   public DragonFightManager func_186063_s() {
      return this.field_186064_g;
   }
}
