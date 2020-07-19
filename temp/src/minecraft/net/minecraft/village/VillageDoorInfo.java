package net.minecraft.village;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class VillageDoorInfo {
   private final BlockPos field_179859_a;
   private final BlockPos field_179857_b;
   private final EnumFacing field_179858_c;
   private int field_75475_f;
   private boolean field_75476_g;
   private int field_75482_h;

   public VillageDoorInfo(BlockPos p_i45871_1_, int p_i45871_2_, int p_i45871_3_, int p_i45871_4_) {
      this(p_i45871_1_, func_179854_a(p_i45871_2_, p_i45871_3_), p_i45871_4_);
   }

   private static EnumFacing func_179854_a(int p_179854_0_, int p_179854_1_) {
      if (p_179854_0_ < 0) {
         return EnumFacing.WEST;
      } else if (p_179854_0_ > 0) {
         return EnumFacing.EAST;
      } else {
         return p_179854_1_ < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH;
      }
   }

   public VillageDoorInfo(BlockPos p_i45872_1_, EnumFacing p_i45872_2_, int p_i45872_3_) {
      this.field_179859_a = p_i45872_1_;
      this.field_179858_c = p_i45872_2_;
      this.field_179857_b = p_i45872_1_.func_177967_a(p_i45872_2_, 2);
      this.field_75475_f = p_i45872_3_;
   }

   public int func_75474_b(int p_75474_1_, int p_75474_2_, int p_75474_3_) {
      return (int)this.field_179859_a.func_177954_c((double)p_75474_1_, (double)p_75474_2_, (double)p_75474_3_);
   }

   public int func_179848_a(BlockPos p_179848_1_) {
      return (int)p_179848_1_.func_177951_i(this.func_179852_d());
   }

   public int func_179846_b(BlockPos p_179846_1_) {
      return (int)this.field_179857_b.func_177951_i(p_179846_1_);
   }

   public boolean func_179850_c(BlockPos p_179850_1_) {
      int i = p_179850_1_.func_177958_n() - this.field_179859_a.func_177958_n();
      int j = p_179850_1_.func_177952_p() - this.field_179859_a.func_177956_o();
      return i * this.field_179858_c.func_82601_c() + j * this.field_179858_c.func_82599_e() >= 0;
   }

   public void func_75466_d() {
      this.field_75482_h = 0;
   }

   public void func_75470_e() {
      ++this.field_75482_h;
   }

   public int func_75468_f() {
      return this.field_75482_h;
   }

   public BlockPos func_179852_d() {
      return this.field_179859_a;
   }

   public BlockPos func_179856_e() {
      return this.field_179857_b;
   }

   public int func_179847_f() {
      return this.field_179858_c.func_82601_c() * 2;
   }

   public int func_179855_g() {
      return this.field_179858_c.func_82599_e() * 2;
   }

   public int func_75473_b() {
      return this.field_75475_f;
   }

   public void func_179849_a(int p_179849_1_) {
      this.field_75475_f = p_179849_1_;
   }

   public boolean func_179851_i() {
      return this.field_75476_g;
   }

   public void func_179853_a(boolean p_179853_1_) {
      this.field_75476_g = p_179853_1_;
   }

   public EnumFacing func_188567_j() {
      return this.field_179858_c;
   }
}
