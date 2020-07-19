package net.minecraft.client.audio;

public interface ISoundEventAccessor<T>
{
    int getWeight();

    T cloneEntry();
}
