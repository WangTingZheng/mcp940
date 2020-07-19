package net.minecraft.world.storage;

public interface IThreadedFileIO
{
    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    boolean writeNextIO();
}
