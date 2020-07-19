package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener
{
    ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule);

    boolean isCallingFromMinecraftThread();
}
