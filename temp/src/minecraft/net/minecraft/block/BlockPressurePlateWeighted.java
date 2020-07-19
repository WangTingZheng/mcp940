package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
   public static final PropertyInteger field_176579_a = PropertyInteger.func_177719_a("power", 0, 15);
   private final int field_150068_a;

   protected BlockPressurePlateWeighted(Material p_i46379_1_, int p_i46379_2_) {
      this(p_i46379_1_, p_i46379_2_, p_i46379_1_.func_151565_r());
   }

   protected BlockPressurePlateWeighted(Material p_i46380_1_, int p_i46380_2_, MapColor p_i46380_3_) {
      super(p_i46380_1_, p_i46380_3_);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176579_a, Integer.valueOf(0)));
      this.field_150068_a = p_i46380_2_;
   }

   protected int func_180669_e(World p_180669_1_, BlockPos p_180669_2_) {
      int i = Math.min(p_180669_1_.func_72872_a(Entity.class, field_185511_c.func_186670_a(p_180669_2_)).size(), this.field_150068_a);
      if (i > 0) {
         float f = (float)Math.min(this.field_150068_a, i) / (float)this.field_150068_a;
         return MathHelper.func_76123_f(f * 15.0F);
      } else {
         return 0;
      }
   }

   protected void func_185507_b(World p_185507_1_, BlockPos p_185507_2_) {
      p_185507_1_.func_184133_a((EntityPlayer)null, p_185507_2_, SoundEvents.field_187776_dp, SoundCategory.BLOCKS, 0.3F, 0.90000004F);
   }

   protected void func_185508_c(World p_185508_1_, BlockPos p_185508_2_) {
      p_185508_1_.func_184133_a((EntityPlayer)null, p_185508_2_, SoundEvents.field_187774_do, SoundCategory.BLOCKS, 0.3F, 0.75F);
   }

   protected int func_176576_e(IBlockState p_176576_1_) {
      return ((Integer)p_176576_1_.func_177229_b(field_176579_a)).intValue();
   }

   protected IBlockState func_176575_a(IBlockState p_176575_1_, int p_176575_2_) {
      return p_176575_1_.func_177226_a(field_176579_a, Integer.valueOf(p_176575_2_));
   }

   public int func_149738_a(World p_149738_1_) {
      return 10;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176579_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176579_a)).intValue();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176579_a});
   }
}
