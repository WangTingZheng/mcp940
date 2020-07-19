package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class MobEffectsPredicate
{
    /** The predicate that matches any set of effects. */
    public static final MobEffectsPredicate ANY = new MobEffectsPredicate(Collections.emptyMap());
    private final Map<Potion, MobEffectsPredicate.InstancePredicate> effects;

    public MobEffectsPredicate(Map<Potion, MobEffectsPredicate.InstancePredicate> effects)
    {
        this.effects = effects;
    }

    public boolean test(Entity entityIn)
    {
        if (this == ANY)
        {
            return true;
        }
        else
        {
            return entityIn instanceof EntityLivingBase ? this.test(((EntityLivingBase)entityIn).getActivePotionMap()) : false;
        }
    }

    public boolean test(EntityLivingBase entityIn)
    {
        return this == ANY ? true : this.test(entityIn.getActivePotionMap());
    }

    public boolean test(Map<Potion, PotionEffect> potions)
    {
        if (this == ANY)
        {
            return true;
        }
        else
        {
            for (Entry<Potion, MobEffectsPredicate.InstancePredicate> entry : this.effects.entrySet())
            {
                PotionEffect potioneffect = potions.get(entry.getKey());

                if (!((MobEffectsPredicate.InstancePredicate)entry.getValue()).test(potioneffect))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public static MobEffectsPredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "effects");
            Map<Potion, MobEffectsPredicate.InstancePredicate> map = Maps.<Potion, MobEffectsPredicate.InstancePredicate>newHashMap();

            for (Entry<String, JsonElement> entry : jsonobject.entrySet())
            {
                ResourceLocation resourcelocation = new ResourceLocation(entry.getKey());
                Potion potion = Potion.REGISTRY.getObject(resourcelocation);

                if (potion == null)
                {
                    throw new JsonSyntaxException("Unknown effect '" + resourcelocation + "'");
                }

                MobEffectsPredicate.InstancePredicate mobeffectspredicate$instancepredicate = MobEffectsPredicate.InstancePredicate.deserialize(JsonUtils.getJsonObject(entry.getValue(), entry.getKey()));
                map.put(potion, mobeffectspredicate$instancepredicate);
            }

            return new MobEffectsPredicate(map);
        }
        else
        {
            return ANY;
        }
    }

    public static class InstancePredicate
    {
        private final MinMaxBounds amplifier;
        private final MinMaxBounds duration;
        @Nullable
        private final Boolean ambient;
        @Nullable
        private final Boolean visible;

        public InstancePredicate(MinMaxBounds amplifier, MinMaxBounds duration, @Nullable Boolean ambient, @Nullable Boolean visible)
        {
            this.amplifier = amplifier;
            this.duration = duration;
            this.ambient = ambient;
            this.visible = visible;
        }

        public boolean test(@Nullable PotionEffect effect)
        {
            if (effect == null)
            {
                return false;
            }
            else if (!this.amplifier.test((float)effect.getAmplifier()))
            {
                return false;
            }
            else if (!this.duration.test((float)effect.getDuration()))
            {
                return false;
            }
            else if (this.ambient != null && this.ambient.booleanValue() != effect.getIsAmbient())
            {
                return false;
            }
            else
            {
                return this.visible == null || this.visible.booleanValue() == effect.doesShowParticles();
            }
        }

        public static MobEffectsPredicate.InstancePredicate deserialize(JsonObject object)
        {
            MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(object.get("amplifier"));
            MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(object.get("duration"));
            Boolean obool = object.has("ambient") ? JsonUtils.getBoolean(object, "ambient") : null;
            Boolean obool1 = object.has("visible") ? JsonUtils.getBoolean(object, "visible") : null;
            return new MobEffectsPredicate.InstancePredicate(minmaxbounds, minmaxbounds1, obool, obool1);
        }
    }
}
