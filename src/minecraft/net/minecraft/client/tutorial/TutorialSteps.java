package net.minecraft.client.tutorial;

import java.util.function.Function;

public enum TutorialSteps
{
    MOVEMENT("movement", MovementStep::new),
    FIND_TREE("find_tree", FindTreeStep::new),
    PUNCH_TREE("punch_tree", PunchTreeStep::new),
    OPEN_INVENTORY("open_inventory", OpenInventoryStep::new),
    CRAFT_PLANKS("craft_planks", CraftPlanksStep::new),
    NONE("none", CompletedTutorialStep::new);

    private final String name;
    private final Function < Tutorial, ? extends ITutorialStep > tutorial;

    private <T extends ITutorialStep> TutorialSteps(String nameIn, Function<Tutorial, T> constructor)
    {
        this.name = nameIn;
        this.tutorial = constructor;
    }

    public ITutorialStep create(Tutorial tutorial)
    {
        return this.tutorial.apply(tutorial);
    }

    public String getName()
    {
        return this.name;
    }

    public static TutorialSteps getTutorial(String tutorialName)
    {
        for (TutorialSteps tutorialsteps : values())
        {
            if (tutorialsteps.name.equals(tutorialName))
            {
                return tutorialsteps;
            }
        }

        return NONE;
    }
}
