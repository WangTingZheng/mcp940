package net.minecraft.entity;

public interface IJumpingMount
{
    void setJumpPower(int jumpPowerIn);

    boolean canJump();

    void handleStartJump(int p_184775_1_);

    void handleStopJump();
}
