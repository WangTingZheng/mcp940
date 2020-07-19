package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class WorldProvider {
   public static final float[] field_111203_a = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected World field_76579_a;
   private WorldType field_76577_b;
   private String field_82913_c;
   protected BiomeProvider field_76578_c;
   protected boolean field_76575_d;
   protected boolean field_76576_e;
   protected boolean field_191067_f;
   protected final float[] field_76573_f = new float[16];
   private final float[] field_76580_h = new float[4];

   public final void func_76558_a(World p_76558_1_) {
      this.field_76579_a = p_76558_1_;
      this.field_76577_b = p_76558_1_.func_72912_H().func_76067_t();
      this.field_82913_c = p_76558_1_.func_72912_H().func_82571_y();
      this.func_76572_b();
      this.func_76556_a();
   }

   protected void func_76556_a() {
      float f = 0.0F;

      for(int i = 0; i <= 15; ++i) {
         float f1 = 1.0F - (float)i / 15.0F;
         this.field_76573_f[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
      }

   }

   protected void func_76572_b() {
      this.field_191067_f = true;
      WorldType worldtype = this.field_76579_a.func_72912_H().func_76067_t();
      if (worldtype == WorldType.field_77138_c) {
         FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.func_82651_a(this.field_76579_a.func_72912_H().func_82571_y());
         this.field_76578_c = new BiomeProviderSingle(Biome.func_180276_a(flatgeneratorinfo.func_82648_a(), Biomes.field_180279_ad));
      } else if (worldtype == WorldType.field_180272_g) {
         this.field_76578_c = new BiomeProviderSingle(Biomes.field_76772_c);
      } else {
         this.field_76578_c = new BiomeProvider(this.field_76579_a.func_72912_H());
      }

   }

   public IChunkGenerator func_186060_c() {
      if (this.field_76577_b == WorldType.field_77138_c) {
         return new ChunkGeneratorFlat(this.field_76579_a, this.field_76579_a.func_72905_C(), this.field_76579_a.func_72912_H().func_76089_r(), this.field_82913_c);
      } else if (this.field_76577_b == WorldType.field_180272_g) {
         return new ChunkGeneratorDebug(this.field_76579_a);
      } else {
         return this.field_76577_b == WorldType.field_180271_f ? new ChunkGeneratorOverworld(this.field_76579_a, this.field_76579_a.func_72905_C(), this.field_76579_a.func_72912_H().func_76089_r(), this.field_82913_c) : new ChunkGeneratorOverworld(this.field_76579_a, this.field_76579_a.func_72905_C(), this.field_76579_a.func_72912_H().func_76089_r(), this.field_82913_c);
      }
   }

   public boolean func_76566_a(int p_76566_1_, int p_76566_2_) {
      BlockPos blockpos = new BlockPos(p_76566_1_, 0, p_76566_2_);
      if (this.field_76579_a.func_180494_b(blockpos).func_185352_i()) {
         return true;
      } else {
         return this.field_76579_a.func_184141_c(blockpos).func_177230_c() == Blocks.field_150349_c;
      }
   }

   public float func_76563_a(long p_76563_1_, float p_76563_3_) {
      int i = (int)(p_76563_1_ % 24000L);
      float f = ((float)i + p_76563_3_) / 24000.0F - 0.25F;
      if (f < 0.0F) {
         ++f;
      }

      if (f > 1.0F) {
         --f;
      }

      float f1 = 1.0F - (float)((Math.cos((double)f * 3.141592653589793D) + 1.0D) / 2.0D);
      f = f + (f1 - f) / 3.0F;
      return f;
   }

   public int func_76559_b(long p_76559_1_) {
      return (int)(p_76559_1_ / 24000L % 8L + 8L) % 8;
   }

   public boolean func_76569_d() {
      return true;
   }

   @Nullable
   public float[] func_76560_a(float p_76560_1_, float p_76560_2_) {
      float f = 0.4F;
      float f1 = MathHelper.func_76134_b(p_76560_1_ * 6.2831855F) - 0.0F;
      float f2 = -0.0F;
      if (f1 >= -0.4F && f1 <= 0.4F) {
         float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
         float f4 = 1.0F - (1.0F - MathHelper.func_76126_a(f3 * 3.1415927F)) * 0.99F;
         f4 = f4 * f4;
         this.field_76580_h[0] = f3 * 0.3F + 0.7F;
         this.field_76580_h[1] = f3 * f3 * 0.7F + 0.2F;
         this.field_76580_h[2] = f3 * f3 * 0.0F + 0.2F;
         this.field_76580_h[3] = f4;
         return this.field_76580_h;
      } else {
         return null;
      }
   }

   public Vec3d func_76562_b(float p_76562_1_, float p_76562_2_) {
      float f = MathHelper.func_76134_b(p_76562_1_ * 6.2831855F) * 2.0F + 0.5F;
      f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
      float f1 = 0.7529412F;
      float f2 = 0.84705883F;
      float f3 = 1.0F;
      f1 = f1 * (f * 0.94F + 0.06F);
      f2 = f2 * (f * 0.94F + 0.06F);
      f3 = f3 * (f * 0.91F + 0.09F);
      return new Vec3d((double)f1, (double)f2, (double)f3);
   }

   public boolean func_76567_e() {
      return true;
   }

   public float func_76571_f() {
      return 128.0F;
   }

   public boolean func_76561_g() {
      return true;
   }

   @Nullable
   public BlockPos func_177496_h() {
      return null;
   }

   public int func_76557_i() {
      return this.field_76577_b == WorldType.field_77138_c ? 4 : this.field_76579_a.func_181545_F() + 1;
   }

   public double func_76565_k() {
      return this.field_76577_b == WorldType.field_77138_c ? 1.0D : 0.03125D;
   }

   public boolean func_76568_b(int p_76568_1_, int p_76568_2_) {
      return false;
   }

   public BiomeProvider func_177499_m() {
      return this.field_76578_c;
   }

   public boolean func_177500_n() {
      return this.field_76575_d;
   }

   public boolean func_191066_m() {
      return this.field_191067_f;
   }

   public boolean func_177495_o() {
      return this.field_76576_e;
   }

   public float[] func_177497_p() {
      return this.field_76573_f;
   }

   public WorldBorder func_177501_r() {
      return new WorldBorder();
   }

   public void func_186061_a(EntityPlayerMP p_186061_1_) {
   }

   public void func_186062_b(EntityPlayerMP p_186062_1_) {
   }

   public abstract DimensionType func_186058_p();

   public void func_186057_q() {
   }

   public void func_186059_r() {
   }

   public boolean func_186056_c(int p_186056_1_, int p_186056_2_) {
      return true;
   }
}
