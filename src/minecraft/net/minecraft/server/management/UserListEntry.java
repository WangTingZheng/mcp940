package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry<T>
{
    private final T value;

    public UserListEntry(T valueIn)
    {
        this.value = valueIn;
    }

    protected UserListEntry(T valueIn, JsonObject json)
    {
        this.value = valueIn;
    }

    T getValue()
    {
        return this.value;
    }

    boolean hasBanExpired()
    {
        return false;
    }

    protected void onSerialization(JsonObject data)
    {
    }
}
