package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JsonUtils;

public class DamagePredicate
{
    public static DamagePredicate ANY = new DamagePredicate();
    private final MinMaxBounds dealt;
    private final MinMaxBounds taken;
    private final EntityPredicate sourceEntity;
    private final Boolean blocked;
    private final DamageSourcePredicate type;

    public DamagePredicate()
    {
        this.dealt = MinMaxBounds.UNBOUNDED;
        this.taken = MinMaxBounds.UNBOUNDED;
        this.sourceEntity = EntityPredicate.ANY;
        this.blocked = null;
        this.type = DamageSourcePredicate.ANY;
    }

    public DamagePredicate(MinMaxBounds dealt, MinMaxBounds taken, EntityPredicate sourceEntity, @Nullable Boolean blocked, DamageSourcePredicate type)
    {
        this.dealt = dealt;
        this.taken = taken;
        this.sourceEntity = sourceEntity;
        this.blocked = blocked;
        this.type = type;
    }

    public boolean test(EntityPlayerMP player, DamageSource source, float dealt, float taken, boolean blocked)
    {
        if (this == ANY)
        {
            return true;
        }
        else if (!this.dealt.test(dealt))
        {
            return false;
        }
        else if (!this.taken.test(taken))
        {
            return false;
        }
        else if (!this.sourceEntity.test(player, source.getTrueSource()))
        {
            return false;
        }
        else if (this.blocked != null && this.blocked.booleanValue() != blocked)
        {
            return false;
        }
        else
        {
            return this.type.test(player, source);
        }
    }

    public static DamagePredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "damage");
            MinMaxBounds minmaxbounds = MinMaxBounds.deserialize(jsonobject.get("dealt"));
            MinMaxBounds minmaxbounds1 = MinMaxBounds.deserialize(jsonobject.get("taken"));
            Boolean obool = jsonobject.has("blocked") ? JsonUtils.getBoolean(jsonobject, "blocked") : null;
            EntityPredicate entitypredicate = EntityPredicate.deserialize(jsonobject.get("source_entity"));
            DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.deserialize(jsonobject.get("type"));
            return new DamagePredicate(minmaxbounds, minmaxbounds1, entitypredicate, obool, damagesourcepredicate);
        }
        else
        {
            return ANY;
        }
    }
}
