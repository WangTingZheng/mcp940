package net.minecraft.client.audio;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class MovingSound extends PositionedSound implements ITickableSound {
   protected boolean field_147668_j;

   protected MovingSound(SoundEvent p_i46532_1_, SoundCategory p_i46532_2_) {
      super(p_i46532_1_, p_i46532_2_);
   }

   public boolean func_147667_k() {
      return this.field_147668_j;
   }
}
