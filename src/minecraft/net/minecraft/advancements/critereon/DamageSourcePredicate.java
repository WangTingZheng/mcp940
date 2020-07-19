package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JsonUtils;

public class DamageSourcePredicate
{
    public static DamageSourcePredicate ANY = new DamageSourcePredicate();
    private final Boolean isProjectile;
    private final Boolean isExplosion;
    private final Boolean bypassesArmor;
    private final Boolean bypassesInvulnerability;
    private final Boolean bypassesMagic;
    private final Boolean isFire;
    private final Boolean isMagic;
    private final EntityPredicate directEntity;
    private final EntityPredicate sourceEntity;

    public DamageSourcePredicate()
    {
        this.isProjectile = null;
        this.isExplosion = null;
        this.bypassesArmor = null;
        this.bypassesInvulnerability = null;
        this.bypassesMagic = null;
        this.isFire = null;
        this.isMagic = null;
        this.directEntity = EntityPredicate.ANY;
        this.sourceEntity = EntityPredicate.ANY;
    }

    public DamageSourcePredicate(@Nullable Boolean isProjectile, @Nullable Boolean isExplosion, @Nullable Boolean bypassesArmor, @Nullable Boolean bypassesInvulnerability, @Nullable Boolean bypassesMagic, @Nullable Boolean isFire, @Nullable Boolean isMagic, EntityPredicate directEntity, EntityPredicate sourceEntity)
    {
        this.isProjectile = isProjectile;
        this.isExplosion = isExplosion;
        this.bypassesArmor = bypassesArmor;
        this.bypassesInvulnerability = bypassesInvulnerability;
        this.bypassesMagic = bypassesMagic;
        this.isFire = isFire;
        this.isMagic = isMagic;
        this.directEntity = directEntity;
        this.sourceEntity = sourceEntity;
    }

    public boolean test(EntityPlayerMP player, DamageSource source)
    {
        if (this == ANY)
        {
            return true;
        }
        else if (this.isProjectile != null && this.isProjectile.booleanValue() != source.isProjectile())
        {
            return false;
        }
        else if (this.isExplosion != null && this.isExplosion.booleanValue() != source.isExplosion())
        {
            return false;
        }
        else if (this.bypassesArmor != null && this.bypassesArmor.booleanValue() != source.isUnblockable())
        {
            return false;
        }
        else if (this.bypassesInvulnerability != null && this.bypassesInvulnerability.booleanValue() != source.canHarmInCreative())
        {
            return false;
        }
        else if (this.bypassesMagic != null && this.bypassesMagic.booleanValue() != source.isDamageAbsolute())
        {
            return false;
        }
        else if (this.isFire != null && this.isFire.booleanValue() != source.isFireDamage())
        {
            return false;
        }
        else if (this.isMagic != null && this.isMagic.booleanValue() != source.isMagicDamage())
        {
            return false;
        }
        else if (!this.directEntity.test(player, source.getImmediateSource()))
        {
            return false;
        }
        else
        {
            return this.sourceEntity.test(player, source.getTrueSource());
        }
    }

    public static DamageSourcePredicate deserialize(@Nullable JsonElement element)
    {
        if (element != null && !element.isJsonNull())
        {
            JsonObject jsonobject = JsonUtils.getJsonObject(element, "damage type");
            Boolean obool = optionalBoolean(jsonobject, "is_projectile");
            Boolean obool1 = optionalBoolean(jsonobject, "is_explosion");
            Boolean obool2 = optionalBoolean(jsonobject, "bypasses_armor");
            Boolean obool3 = optionalBoolean(jsonobject, "bypasses_invulnerability");
            Boolean obool4 = optionalBoolean(jsonobject, "bypasses_magic");
            Boolean obool5 = optionalBoolean(jsonobject, "is_fire");
            Boolean obool6 = optionalBoolean(jsonobject, "is_magic");
            EntityPredicate entitypredicate = EntityPredicate.deserialize(jsonobject.get("direct_entity"));
            EntityPredicate entitypredicate1 = EntityPredicate.deserialize(jsonobject.get("source_entity"));
            return new DamageSourcePredicate(obool, obool1, obool2, obool3, obool4, obool5, obool6, entitypredicate, entitypredicate1);
        }
        else
        {
            return ANY;
        }
    }

    @Nullable
    private static Boolean optionalBoolean(JsonObject object, String memberName)
    {
        return object.has(memberName) ? JsonUtils.getBoolean(object, memberName) : null;
    }
}
