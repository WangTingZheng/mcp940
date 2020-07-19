package net.minecraft.client.audio;

import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public interface ISound
{
    ResourceLocation getSoundLocation();

    @Nullable
    SoundEventAccessor createAccessor(SoundHandler handler);

    Sound getSound();

    SoundCategory getCategory();

    boolean canRepeat();

    int getRepeatDelay();

    float getVolume();

    float getPitch();

    float getXPosF();

    float getYPosF();

    float getZPosF();

    ISound.AttenuationType getAttenuationType();

    public static enum AttenuationType
    {
        NONE(0),
        LINEAR(2);

        private final int type;

        private AttenuationType(int typeIn)
        {
            this.type = typeIn;
        }

        public int getTypeInt()
        {
            return this.type;
        }
    }
}
