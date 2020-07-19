package net.minecraft.client.audio;

public interface ISoundEventListener
{
    void soundPlay(ISound soundIn, SoundEventAccessor accessor);
}
