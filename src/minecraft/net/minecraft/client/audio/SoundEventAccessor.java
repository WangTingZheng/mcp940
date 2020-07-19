package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SoundEventAccessor implements ISoundEventAccessor<Sound>
{
    private final List<ISoundEventAccessor<Sound>> accessorList = Lists.<ISoundEventAccessor<Sound>>newArrayList();
    private final Random rnd = new Random();
    private final ResourceLocation location;
    private final ITextComponent subtitle;

    public SoundEventAccessor(ResourceLocation locationIn, @Nullable String subtitleIn)
    {
        this.location = locationIn;
        this.subtitle = subtitleIn == null ? null : new TextComponentTranslation(subtitleIn, new Object[0]);
    }

    public int getWeight()
    {
        int i = 0;

        for (ISoundEventAccessor<Sound> isoundeventaccessor : this.accessorList)
        {
            i += isoundeventaccessor.getWeight();
        }

        return i;
    }

    public Sound cloneEntry()
    {
        int i = this.getWeight();

        if (!this.accessorList.isEmpty() && i != 0)
        {
            int j = this.rnd.nextInt(i);

            for (ISoundEventAccessor<Sound> isoundeventaccessor : this.accessorList)
            {
                j -= isoundeventaccessor.getWeight();

                if (j < 0)
                {
                    return isoundeventaccessor.cloneEntry();
                }
            }

            return SoundHandler.MISSING_SOUND;
        }
        else
        {
            return SoundHandler.MISSING_SOUND;
        }
    }

    public void addSound(ISoundEventAccessor<Sound> p_188715_1_)
    {
        this.accessorList.add(p_188715_1_);
    }

    public ResourceLocation getLocation()
    {
        return this.location;
    }

    @Nullable
    public ITextComponent getSubtitle()
    {
        return this.subtitle;
    }
}
