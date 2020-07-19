package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class CooldownTracker
{
    private final Map<Item, CooldownTracker.Cooldown> cooldowns = Maps.<Item, CooldownTracker.Cooldown>newHashMap();
    private int ticks;

    public boolean hasCooldown(Item itemIn)
    {
        return this.getCooldown(itemIn, 0.0F) > 0.0F;
    }

    public float getCooldown(Item itemIn, float partialTicks)
    {
        CooldownTracker.Cooldown cooldowntracker$cooldown = this.cooldowns.get(itemIn);

        if (cooldowntracker$cooldown != null)
        {
            float f = (float)(cooldowntracker$cooldown.expireTicks - cooldowntracker$cooldown.createTicks);
            float f1 = (float)cooldowntracker$cooldown.expireTicks - ((float)this.ticks + partialTicks);
            return MathHelper.clamp(f1 / f, 0.0F, 1.0F);
        }
        else
        {
            return 0.0F;
        }
    }

    public void tick()
    {
        ++this.ticks;

        if (!this.cooldowns.isEmpty())
        {
            Iterator<Entry<Item, CooldownTracker.Cooldown>> iterator = this.cooldowns.entrySet().iterator();

            while (iterator.hasNext())
            {
                Entry<Item, CooldownTracker.Cooldown> entry = (Entry)iterator.next();

                if ((entry.getValue()).expireTicks <= this.ticks)
                {
                    iterator.remove();
                    this.notifyOnRemove(entry.getKey());
                }
            }
        }
    }

    public void setCooldown(Item itemIn, int ticksIn)
    {
        this.cooldowns.put(itemIn, new CooldownTracker.Cooldown(this.ticks, this.ticks + ticksIn));
        this.notifyOnSet(itemIn, ticksIn);
    }

    public void removeCooldown(Item itemIn)
    {
        this.cooldowns.remove(itemIn);
        this.notifyOnRemove(itemIn);
    }

    protected void notifyOnSet(Item itemIn, int ticksIn)
    {
    }

    protected void notifyOnRemove(Item itemIn)
    {
    }

    class Cooldown
    {
        final int createTicks;
        final int expireTicks;

        private Cooldown(int createTicksIn, int expireTicksIn)
        {
            this.createTicks = createTicksIn;
            this.expireTicks = expireTicksIn;
        }
    }
}
