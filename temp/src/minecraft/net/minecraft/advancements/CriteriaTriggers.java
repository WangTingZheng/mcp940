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

public class CriteriaTriggers {
   private static final Map<ResourceLocation, ICriterionTrigger<?>> field_192139_s = Maps.<ResourceLocation, ICriterionTrigger<?>>newHashMap();
   public static final ImpossibleTrigger field_192121_a = (ImpossibleTrigger)func_192118_a(new ImpossibleTrigger());
   public static final KilledTrigger field_192122_b = (KilledTrigger)func_192118_a(new KilledTrigger(new ResourceLocation("player_killed_entity")));
   public static final KilledTrigger field_192123_c = (KilledTrigger)func_192118_a(new KilledTrigger(new ResourceLocation("entity_killed_player")));
   public static final EnterBlockTrigger field_192124_d = (EnterBlockTrigger)func_192118_a(new EnterBlockTrigger());
   public static final InventoryChangeTrigger field_192125_e = (InventoryChangeTrigger)func_192118_a(new InventoryChangeTrigger());
   public static final RecipeUnlockedTrigger field_192126_f = (RecipeUnlockedTrigger)func_192118_a(new RecipeUnlockedTrigger());
   public static final PlayerHurtEntityTrigger field_192127_g = (PlayerHurtEntityTrigger)func_192118_a(new PlayerHurtEntityTrigger());
   public static final EntityHurtPlayerTrigger field_192128_h = (EntityHurtPlayerTrigger)func_192118_a(new EntityHurtPlayerTrigger());
   public static final EnchantedItemTrigger field_192129_i = (EnchantedItemTrigger)func_192118_a(new EnchantedItemTrigger());
   public static final BrewedPotionTrigger field_192130_j = (BrewedPotionTrigger)func_192118_a(new BrewedPotionTrigger());
   public static final ConstructBeaconTrigger field_192131_k = (ConstructBeaconTrigger)func_192118_a(new ConstructBeaconTrigger());
   public static final UsedEnderEyeTrigger field_192132_l = (UsedEnderEyeTrigger)func_192118_a(new UsedEnderEyeTrigger());
   public static final SummonedEntityTrigger field_192133_m = (SummonedEntityTrigger)func_192118_a(new SummonedEntityTrigger());
   public static final BredAnimalsTrigger field_192134_n = (BredAnimalsTrigger)func_192118_a(new BredAnimalsTrigger());
   public static final PositionTrigger field_192135_o = (PositionTrigger)func_192118_a(new PositionTrigger(new ResourceLocation("location")));
   public static final PositionTrigger field_192136_p = (PositionTrigger)func_192118_a(new PositionTrigger(new ResourceLocation("slept_in_bed")));
   public static final CuredZombieVillagerTrigger field_192137_q = (CuredZombieVillagerTrigger)func_192118_a(new CuredZombieVillagerTrigger());
   public static final VillagerTradeTrigger field_192138_r = (VillagerTradeTrigger)func_192118_a(new VillagerTradeTrigger());
   public static final ItemDurabilityTrigger field_193132_s = (ItemDurabilityTrigger)func_192118_a(new ItemDurabilityTrigger());
   public static final LevitationTrigger field_193133_t = (LevitationTrigger)func_192118_a(new LevitationTrigger());
   public static final ChangeDimensionTrigger field_193134_u = (ChangeDimensionTrigger)func_192118_a(new ChangeDimensionTrigger());
   public static final TickTrigger field_193135_v = (TickTrigger)func_192118_a(new TickTrigger());
   public static final TameAnimalTrigger field_193136_w = (TameAnimalTrigger)func_192118_a(new TameAnimalTrigger());
   public static final PlacedBlockTrigger field_193137_x = (PlacedBlockTrigger)func_192118_a(new PlacedBlockTrigger());
   public static final ConsumeItemTrigger field_193138_y = (ConsumeItemTrigger)func_192118_a(new ConsumeItemTrigger());
   public static final EffectsChangedTrigger field_193139_z = (EffectsChangedTrigger)func_192118_a(new EffectsChangedTrigger());
   public static final UsedTotemTrigger field_193130_A = (UsedTotemTrigger)func_192118_a(new UsedTotemTrigger());
   public static final NetherTravelTrigger field_193131_B = (NetherTravelTrigger)func_192118_a(new NetherTravelTrigger());

   private static <T extends ICriterionTrigger> T func_192118_a(T p_192118_0_) {
      if (field_192139_s.containsKey(p_192118_0_.func_192163_a())) {
         throw new IllegalArgumentException("Duplicate criterion id " + p_192118_0_.func_192163_a());
      } else {
         field_192139_s.put(p_192118_0_.func_192163_a(), p_192118_0_);
         return p_192118_0_;
      }
   }

   @Nullable
   public static <T extends ICriterionInstance> ICriterionTrigger<T> func_192119_a(ResourceLocation p_192119_0_) {
      return (ICriterionTrigger)field_192139_s.get(p_192119_0_);
   }

   public static Iterable<? extends ICriterionTrigger<?>> func_192120_a() {
      return field_192139_s.values();
   }
}
