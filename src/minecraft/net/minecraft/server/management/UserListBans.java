package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListBans extends UserList<GameProfile, UserListBansEntry>
{
    public UserListBans(File bansFile)
    {
        super(bansFile);
    }

    protected UserListEntry<GameProfile> createEntry(JsonObject entryData)
    {
        return new UserListBansEntry(entryData);
    }

    public boolean isBanned(GameProfile profile)
    {
        return this.hasEntry(profile);
    }

    public String[] getKeys()
    {
        String[] astring = new String[this.getValues().size()];
        int i = 0;

        for (UserListBansEntry userlistbansentry : this.getValues().values())
        {
            astring[i++] = ((GameProfile)userlistbansentry.getValue()).getName();
        }

        return astring;
    }

    /**
     * Gets the key value for the given object
     */
    protected String getObjectKey(GameProfile obj)
    {
        return obj.getId().toString();
    }

    /**
     * Get a {@link GameProfile} that is a member of this list by its user name. Returns {@code null} if no entry was
     * found.
     */
    public GameProfile getBannedProfile(String username)
    {
        for (UserListBansEntry userlistbansentry : this.getValues().values())
        {
            if (username.equalsIgnoreCase(((GameProfile)userlistbansentry.getValue()).getName()))
            {
                return (GameProfile)userlistbansentry.getValue();
            }
        }

        return null;
    }
}
