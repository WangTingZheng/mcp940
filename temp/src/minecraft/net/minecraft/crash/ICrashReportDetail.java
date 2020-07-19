package net.minecraft.crash;

import java.util.concurrent.Callable;

public interface ICrashReportDetail<V> extends Callable<V> {
}
