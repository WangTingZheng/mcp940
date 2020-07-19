package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessor>
{
    private Map<ResourceLocation, SoundEventAccessor> soundRegistry;

    protected Map<ResourceLocation, SoundEventAccessor> createUnderlyingMap()
    {
        this.soundRegistry = Maps.<ResourceLocation, SoundEventAccessor>newHashMap();
        return this.soundRegistry;
    }

    public void add(SoundEventAccessor accessor)
    {
        this.putObject(accessor.getLocation(), accessor);
    }

    /**
     * Reset the underlying sound map (Called on resource manager reload)
     */
    public void clearMap()
    {
        this.soundRegistry.clear();
    }
}
