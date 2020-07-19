package net.minecraft.client.gui.advancements;

public enum AdvancementState
{
    OBTAINED(0),
    UNOBTAINED(1);

    private final int id;

    private AdvancementState(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
}
