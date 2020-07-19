package net.minecraft.block.state;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
   private final World field_177515_a;
   private final BlockPos field_177513_b;
   private final boolean field_181628_c;
   private IBlockState field_177514_c;
   private TileEntity field_177511_d;
   private boolean field_177512_e;

   public BlockWorldState(World p_i46451_1_, BlockPos p_i46451_2_, boolean p_i46451_3_) {
      this.field_177515_a = p_i46451_1_;
      this.field_177513_b = p_i46451_2_;
      this.field_181628_c = p_i46451_3_;
   }

   public IBlockState func_177509_a() {
      if (this.field_177514_c == null && (this.field_181628_c || this.field_177515_a.func_175667_e(this.field_177513_b))) {
         this.field_177514_c = this.field_177515_a.func_180495_p(this.field_177513_b);
      }

      return this.field_177514_c;
   }

   @Nullable
   public TileEntity func_177507_b() {
      if (this.field_177511_d == null && !this.field_177512_e) {
         this.field_177511_d = this.field_177515_a.func_175625_s(this.field_177513_b);
         this.field_177512_e = true;
      }

      return this.field_177511_d;
   }

   public BlockPos func_177508_d() {
      return this.field_177513_b;
   }

   public static Predicate<BlockWorldState> func_177510_a(final Predicate<IBlockState> p_177510_0_) {
      return new Predicate<BlockWorldState>() {
         public boolean apply(@Nullable BlockWorldState p_apply_1_) {
            return p_apply_1_ != null && p_177510_0_.apply(p_apply_1_.func_177509_a());
         }
      };
   }
}
