package net.minecraft.util;

public abstract class LazyLoadBase<T>
{
    private T value;
    private boolean isLoaded;

    public T getValue()
    {
        if (!this.isLoaded)
        {
            this.isLoaded = true;
            this.value = (T)this.load();
        }

        return this.value;
    }

    protected abstract T load();
}
