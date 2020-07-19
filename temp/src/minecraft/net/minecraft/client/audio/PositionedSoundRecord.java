package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class PositionedSoundRecord extends PositionedSound {
   public PositionedSoundRecord(SoundEvent p_i46527_1_, SoundCategory p_i46527_2_, float p_i46527_3_, float p_i46527_4_, BlockPos p_i46527_5_) {
      this(p_i46527_1_, p_i46527_2_, p_i46527_3_, p_i46527_4_, (float)p_i46527_5_.func_177958_n() + 0.5F, (float)p_i46527_5_.func_177956_o() + 0.5F, (float)p_i46527_5_.func_177952_p() + 0.5F);
   }

   public static PositionedSoundRecord func_184371_a(SoundEvent p_184371_0_, float p_184371_1_) {
      return func_194007_a(p_184371_0_, p_184371_1_, 0.25F);
   }

   public static PositionedSoundRecord func_194007_a(SoundEvent p_194007_0_, float p_194007_1_, float p_194007_2_) {
      return new PositionedSoundRecord(p_194007_0_, SoundCategory.MASTER, p_194007_2_, p_194007_1_, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static PositionedSoundRecord func_184370_a(SoundEvent p_184370_0_) {
      return new PositionedSoundRecord(p_184370_0_, SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
   }

   public static PositionedSoundRecord func_184372_a(SoundEvent p_184372_0_, float p_184372_1_, float p_184372_2_, float p_184372_3_) {
      return new PositionedSoundRecord(p_184372_0_, SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, p_184372_1_, p_184372_2_, p_184372_3_);
   }

   public PositionedSoundRecord(SoundEvent p_i46528_1_, SoundCategory p_i46528_2_, float p_i46528_3_, float p_i46528_4_, float p_i46528_5_, float p_i46528_6_, float p_i46528_7_) {
      this(p_i46528_1_, p_i46528_2_, p_i46528_3_, p_i46528_4_, false, 0, ISound.AttenuationType.LINEAR, p_i46528_5_, p_i46528_6_, p_i46528_7_);
   }

   private PositionedSoundRecord(SoundEvent p_i46529_1_, SoundCategory p_i46529_2_, float p_i46529_3_, float p_i46529_4_, boolean p_i46529_5_, int p_i46529_6_, ISound.AttenuationType p_i46529_7_, float p_i46529_8_, float p_i46529_9_, float p_i46529_10_) {
      this(p_i46529_1_.func_187503_a(), p_i46529_2_, p_i46529_3_, p_i46529_4_, p_i46529_5_, p_i46529_6_, p_i46529_7_, p_i46529_8_, p_i46529_9_, p_i46529_10_);
   }

   public PositionedSoundRecord(ResourceLocation p_i46530_1_, SoundCategory p_i46530_2_, float p_i46530_3_, float p_i46530_4_, boolean p_i46530_5_, int p_i46530_6_, ISound.AttenuationType p_i46530_7_, float p_i46530_8_, float p_i46530_9_, float p_i46530_10_) {
      super(p_i46530_1_, p_i46530_2_);
      this.field_147662_b = p_i46530_3_;
      this.field_147663_c = p_i46530_4_;
      this.field_147660_d = p_i46530_8_;
      this.field_147661_e = p_i46530_9_;
      this.field_147658_f = p_i46530_10_;
      this.field_147659_g = p_i46530_5_;
      this.field_147665_h = p_i46530_6_;
      this.field_147666_i = p_i46530_7_;
   }
}
