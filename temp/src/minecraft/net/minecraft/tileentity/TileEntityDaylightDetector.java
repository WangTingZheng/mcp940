package net.minecraft.tileentity;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.util.ITickable;

public class TileEntityDaylightDetector extends TileEntity implements ITickable {
   public void func_73660_a() {
      if (this.field_145850_b != null && !this.field_145850_b.field_72995_K && this.field_145850_b.func_82737_E() % 20L == 0L) {
         this.field_145854_h = this.func_145838_q();
         if (this.field_145854_h instanceof BlockDaylightDetector) {
            ((BlockDaylightDetector)this.field_145854_h).func_180677_d(this.field_145850_b, this.field_174879_c);
         }
      }

   }
}
