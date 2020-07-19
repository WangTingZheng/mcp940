package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
   ListenableFuture<Object> func_152344_a(Runnable var1);

   boolean func_152345_ab();
}
