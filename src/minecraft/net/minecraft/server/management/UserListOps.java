package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry>
{
    public UserListOps(File saveFile)
    {
        super(saveFile);
    }

    protected UserListEntry<GameProfile> createEntry(JsonObject entryData)
    {
        return new UserListOpsEntry(entryData);
    }

    public String[] getKeys()
    {
        String[] astring = new String[this.getValues().size()];
        int i = 0;

        for (UserListOpsEntry userlistopsentry : this.getValues().values())
        {
            astring[i++] = ((GameProfile)userlistopsentry.getValue()).getName();
        }

        return astring;
    }

    /**
     * Get the OP permission level this player has
     */
    public int getPermissionLevel(GameProfile profile)
    {
        UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.getEntry(profile);
        return userlistopsentry != null ? userlistopsentry.getPermissionLevel() : 0;
    }

    public boolean bypassesPlayerLimit(GameProfile profile)
    {
        UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.getEntry(profile);
        return userlistopsentry != null ? userlistopsentry.bypassesPlayerLimit() : false;
    }

    /**
     * Gets the key value for the given object
     */
    protected String getObjectKey(GameProfile obj)
    {
        return obj.getId().toString();
    }

    /**
     * Gets the GameProfile of based on the provided username.
     */
    public GameProfile getGameProfileFromName(String username)
    {
        for (UserListOpsEntry userlistopsentry : this.getValues().values())
        {
            if (username.equalsIgnoreCase(((GameProfile)userlistopsentry.getValue()).getName()))
            {
                return (GameProfile)userlistopsentry.getValue();
            }
        }

        return null;
    }
}
