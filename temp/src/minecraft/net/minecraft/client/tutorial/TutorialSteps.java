package net.minecraft.client.tutorial;

import java.util.function.Function;

public enum TutorialSteps {
   MOVEMENT("movement", MovementStep::new),
   FIND_TREE("find_tree", FindTreeStep::new),
   PUNCH_TREE("punch_tree", PunchTreeStep::new),
   OPEN_INVENTORY("open_inventory", OpenInventoryStep::new),
   CRAFT_PLANKS("craft_planks", CraftPlanksStep::new),
   NONE("none", CompletedTutorialStep::new);

   private final String field_193316_g;
   private final Function<Tutorial, ? extends ITutorialStep> field_193317_h;

   private <T extends ITutorialStep> TutorialSteps(String p_i47577_3_, Function<Tutorial, T> p_i47577_4_) {
      this.field_193316_g = p_i47577_3_;
      this.field_193317_h = p_i47577_4_;
   }

   public ITutorialStep func_193309_a(Tutorial p_193309_1_) {
      return this.field_193317_h.apply(p_193309_1_);
   }

   public String func_193308_a() {
      return this.field_193316_g;
   }

   public static TutorialSteps func_193307_a(String p_193307_0_) {
      for(TutorialSteps tutorialsteps : values()) {
         if (tutorialsteps.field_193316_g.equals(p_193307_0_)) {
            return tutorialsteps;
         }
      }

      return NONE;
   }
}
