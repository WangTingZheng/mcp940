package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;

public class ItemAir extends Item {
   private final Block field_190904_a;

   public ItemAir(Block p_i47264_1_) {
      this.field_190904_a = p_i47264_1_;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return this.field_190904_a.func_149739_a();
   }

   public String func_77658_a() {
      return this.field_190904_a.func_149739_a();
   }

   public void func_77624_a(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<String> p_77624_3_, ITooltipFlag p_77624_4_) {
      super.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
      this.field_190904_a.func_190948_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
   }
}
