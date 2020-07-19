package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

public interface IRegistryDelegate<T>
{
    T get();

    ResourceLocation name();

    Class<T> type();
}
