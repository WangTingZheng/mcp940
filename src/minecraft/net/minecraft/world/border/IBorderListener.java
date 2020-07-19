package net.minecraft.world.border;

public interface IBorderListener
{
    void onSizeChanged(WorldBorder border, double newSize);

    void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time);

    void onCenterChanged(WorldBorder border, double x, double z);

    void onWarningTimeChanged(WorldBorder border, int newTime);

    void onWarningDistanceChanged(WorldBorder border, int newDistance);

    void onDamageAmountChanged(WorldBorder border, double newAmount);

    void onDamageBufferChanged(WorldBorder border, double newSize);
}
