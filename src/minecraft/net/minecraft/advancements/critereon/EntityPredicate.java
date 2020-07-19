package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EntityPredicate
{
    /** The predicate that matches any entity. */
    public static final EntityPredicate ANY = new EntityPredicate((ResourceLocation)null, DistancePredicate.ANY, LocationPredicate.ANY, MobEffectsPredicate.ANY, NBTPredicate.ANY);
    private final ResourceLocation type;
    private final DistancePredicate distance;
    private final LocationPredicate location;
    private final MobEffectsPredicate effects;
    private final NBTPredicate nbt;

    public EntityPredicate(@Nullable ResourceLocation type, DistancePredicate distance, LocationPredicate location, MobEffectsPredicate effects, NBTPredicate nbt)
    {
        this.type = type;
        this.distance = distance;
        this.location = location;
        this.effects = effects;
        this.nbt = nbt;
    }

    public boolean test(EntityPlayerMP player, @Nullable Entity entity)
    {
        if (this == ANY)
        {
            return true;
        }
        else if (entity == null)
        {
            return false;
        }
        else if (this.type != null && !EntityList.isMatchingName(entity, this.type))
        {
            return false;
        }
        else if (!this.distance.test(player.posX, player.posY, player.posZ, entity.posX, entity.posY, entity.posZ))
        {
            return false;
        }
        else if (!this.location.test(player.getServerWorld(), entity.posX, entity.posY, entity.posZ))
        {
            return false;
        }
        else if (!this.effects.test(entity))
        {
            return false;
        }
        else
        {
            return this.nbt.test(entity);
        }
    }

    public static EntityPredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "entity");
            ResourceLocation resourcelocation = null;

            if (jsonobject.has("type"))
            {
                resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "type"));

                if (!EntityList.isRegistered(resourcelocation))
                {
                    throw new JsonSyntaxException("Unknown entity type '" + resourcelocation + "', valid types are: " + EntityList.getValidTypeNames());
                }
            }

            DistancePredicate distancepredicate = DistancePredicate.deserialize(jsonobject.get("distance"));
            LocationPredicate locationpredicate = LocationPredicate.deserialize(jsonobject.get("location"));
            MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.deserialize(jsonobject.get("effects"));
            NBTPredicate nbtpredicate = NBTPredicate.deserialize(jsonobject.get("nbt"));
            return new EntityPredicate(resourcelocation, distancepredicate, locationpredicate, mobeffectspredicate, nbtpredicate);
        }
        else
        {
            return ANY;
        }
    }
}
