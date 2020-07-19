package net.minecraft.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LevitationTrigger;
import net.minecraft.advancements.critereon.NetherTravelTrigger;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.advancements.critereon.PositionTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.advancements.critereon.VillagerTradeTrigger;
import net.minecraft.util.ResourceLocation;

public class CriteriaTriggers
{
    private static final Map < ResourceLocation, ICriterionTrigger<? >> REGISTRY = Maps. < ResourceLocation, ICriterionTrigger<? >> newHashMap();
    public static final ImpossibleTrigger IMPOSSIBLE = (ImpossibleTrigger)register(new ImpossibleTrigger());
    public static final KilledTrigger PLAYER_KILLED_ENTITY = (KilledTrigger)register(new KilledTrigger(new ResourceLocation("player_killed_entity")));
    public static final KilledTrigger ENTITY_KILLED_PLAYER = (KilledTrigger)register(new KilledTrigger(new ResourceLocation("entity_killed_player")));
    public static final EnterBlockTrigger ENTER_BLOCK = (EnterBlockTrigger)register(new EnterBlockTrigger());
    public static final InventoryChangeTrigger INVENTORY_CHANGED = (InventoryChangeTrigger)register(new InventoryChangeTrigger());
    public static final RecipeUnlockedTrigger RECIPE_UNLOCKED = (RecipeUnlockedTrigger)register(new RecipeUnlockedTrigger());
    public static final PlayerHurtEntityTrigger PLAYER_HURT_ENTITY = (PlayerHurtEntityTrigger)register(new PlayerHurtEntityTrigger());
    public static final EntityHurtPlayerTrigger ENTITY_HURT_PLAYER = (EntityHurtPlayerTrigger)register(new EntityHurtPlayerTrigger());
    public static final EnchantedItemTrigger ENCHANTED_ITEM = (EnchantedItemTrigger)register(new EnchantedItemTrigger());
    public static final BrewedPotionTrigger BREWED_POTION = (BrewedPotionTrigger)register(new BrewedPotionTrigger());
    public static final ConstructBeaconTrigger CONSTRUCT_BEACON = (ConstructBeaconTrigger)register(new ConstructBeaconTrigger());
    public static final UsedEnderEyeTrigger USED_ENDER_EYE = (UsedEnderEyeTrigger)register(new UsedEnderEyeTrigger());
    public static final SummonedEntityTrigger SUMMONED_ENTITY = (SummonedEntityTrigger)register(new SummonedEntityTrigger());
    public static final BredAnimalsTrigger BRED_ANIMALS = (BredAnimalsTrigger)register(new BredAnimalsTrigger());
    public static final PositionTrigger LOCATION = (PositionTrigger)register(new PositionTrigger(new ResourceLocation("location")));
    public static final PositionTrigger SLEPT_IN_BED = (PositionTrigger)register(new PositionTrigger(new ResourceLocation("slept_in_bed")));
    public static final CuredZombieVillagerTrigger CURED_ZOMBIE_VILLAGER = (CuredZombieVillagerTrigger)register(new CuredZombieVillagerTrigger());
    public static final VillagerTradeTrigger VILLAGER_TRADE = (VillagerTradeTrigger)register(new VillagerTradeTrigger());
    public static final ItemDurabilityTrigger ITEM_DURABILITY_CHANGED = (ItemDurabilityTrigger)register(new ItemDurabilityTrigger());
    public static final LevitationTrigger LEVITATION = (LevitationTrigger)register(new LevitationTrigger());
    public static final ChangeDimensionTrigger CHANGED_DIMENSION = (ChangeDimensionTrigger)register(new ChangeDimensionTrigger());
    public static final TickTrigger TICK = (TickTrigger)register(new TickTrigger());
    public static final TameAnimalTrigger TAME_ANIMAL = (TameAnimalTrigger)register(new TameAnimalTrigger());
    public static final PlacedBlockTrigger PLACED_BLOCK = (PlacedBlockTrigger)register(new PlacedBlockTrigger());
    public static final ConsumeItemTrigger CONSUME_ITEM = (ConsumeItemTrigger)register(new ConsumeItemTrigger());
    public static final EffectsChangedTrigger EFFECTS_CHANGED = (EffectsChangedTrigger)register(new EffectsChangedTrigger());
    public static final UsedTotemTrigger USED_TOTEM = (UsedTotemTrigger)register(new UsedTotemTrigger());
    public static final NetherTravelTrigger NETHER_TRAVEL = (NetherTravelTrigger)register(new NetherTravelTrigger());

    private static <T extends ICriterionTrigger> T register(T criterion)
    {
        if (REGISTRY.containsKey(criterion.getId()))
        {
            throw new IllegalArgumentException("Duplicate criterion id " + criterion.getId());
        }
        else
        {
            REGISTRY.put(criterion.getId(), criterion);
            return criterion;
        }
    }

    @Nullable
    public static <T extends ICriterionInstance> ICriterionTrigger<T> get(ResourceLocation id)
    {
        return (ICriterionTrigger)REGISTRY.get(id);
    }

    public static Iterable <? extends ICriterionTrigger<? >> getAll()
    {
        return REGISTRY.values();
    }
}
