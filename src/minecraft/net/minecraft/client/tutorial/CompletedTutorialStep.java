package net.minecraft.client.tutorial;

public class CompletedTutorialStep implements ITutorialStep
{
    private final Tutorial tutorial;

    public CompletedTutorialStep(Tutorial tutorial)
    {
        this.tutorial = tutorial;
    }
}
