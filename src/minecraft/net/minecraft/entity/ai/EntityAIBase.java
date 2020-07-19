package net.minecraft.entity.ai;

public abstract class EntityAIBase
{
    /**
     * A bitmask telling which other tasks may not run concurrently. The test is a simple bitwise AND - if it yields
     * zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
     */
    private int mutexBits;

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public abstract boolean shouldExecute();

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute();
    }

    /**
     * Determine if this AI Task is interruptible by a higher (= lower value) priority task. All vanilla AITask have
     * this value set to true.
     */
    public boolean isInterruptible()
    {
        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
    }

    /**
     * Sets the mutex bitflags, see getMutexBits. Flag 1 for motion, flag 2 for look/head movement, flag 4 for
     * swimming/misc. Flags can be OR'ed.
     */
    public void setMutexBits(int mutexBitsIn)
    {
        this.mutexBits = mutexBitsIn;
    }

    /**
     * Get what actions this task will take that may potentially conflict with other tasks. The test is a simple bitwise
     * AND - if it yields zero, the two tasks may run concurrently, if not - they must run exclusively from each other.
     * See setMutextBits.
     */
    public int getMutexBits()
    {
        return this.mutexBits;
    }
}
