package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>>
{
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final Gson gson;
    private final File saveFile;
    private final Map<String, V> values = Maps.<String, V>newHashMap();
    private boolean lanServer = true;
    private static final ParameterizedType USER_LIST_ENTRY_TYPE = new ParameterizedType()
    {
        public Type[] getActualTypeArguments()
        {
            return new Type[] {UserListEntry.class};
        }
        public Type getRawType()
        {
            return List.class;
        }
        public Type getOwnerType()
        {
            return null;
        }
    };

    public UserList(File saveFile)
    {
        this.saveFile = saveFile;
        GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
        gsonbuilder.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
        this.gson = gsonbuilder.create();
    }

    public boolean isLanServer()
    {
        return this.lanServer;
    }

    public void setLanServer(boolean state)
    {
        this.lanServer = state;
    }

    /**
     * Adds an entry to the list
     */
    public void addEntry(V entry)
    {
        this.values.put(this.getObjectKey(entry.getValue()), entry);

        try
        {
            this.writeChanges();
        }
        catch (IOException ioexception)
        {
            LOGGER.warn("Could not save the list after adding a user.", (Throwable)ioexception);
        }
    }

    public V getEntry(K obj)
    {
        this.removeExpired();
        return (V)(this.values.get(this.getObjectKey(obj)));
    }

    public void removeEntry(K entry)
    {
        this.values.remove(this.getObjectKey(entry));

        try
        {
            this.writeChanges();
        }
        catch (IOException ioexception)
        {
            LOGGER.warn("Could not save the list after removing a user.", (Throwable)ioexception);
        }
    }

    public String[] getKeys()
    {
        return (String[])this.values.keySet().toArray(new String[this.values.size()]);
    }

    /**
     * Gets the key value for the given object
     */
    protected String getObjectKey(K obj)
    {
        return obj.toString();
    }

    protected boolean hasEntry(K entry)
    {
        return this.values.containsKey(this.getObjectKey(entry));
    }

    /**
     * Removes expired bans from the list. See {@link BanEntry#hasBanExpired}
     */
    private void removeExpired()
    {
        List<K> list = Lists.<K>newArrayList();

        for (V v : this.values.values())
        {
            if (v.hasBanExpired())
            {
                list.add(v.getValue());
            }
        }

        for (K k : list)
        {
            this.values.remove(k);
        }
    }

    protected UserListEntry<K> createEntry(JsonObject entryData)
    {
        return new UserListEntry<K>(null, entryData);
    }

    protected Map<String, V> getValues()
    {
        return this.values;
    }

    public void writeChanges() throws IOException
    {
        Collection<V> collection = this.values.values();
        String s = this.gson.toJson(collection);
        BufferedWriter bufferedwriter = null;

        try
        {
            bufferedwriter = Files.newWriter(this.saveFile, StandardCharsets.UTF_8);
            bufferedwriter.write(s);
        }
        finally
        {
            IOUtils.closeQuietly((Writer)bufferedwriter);
        }
    }

    class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>>
    {
        private Serializer()
        {
        }

        public JsonElement serialize(UserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            JsonObject jsonobject = new JsonObject();
            p_serialize_1_.onSerialization(jsonobject);
            return jsonobject;
        }

        public UserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            if (p_deserialize_1_.isJsonObject())
            {
                JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
                return UserList.this.createEntry(jsonobject);
            }
            else
            {
                return null;
            }
        }
    }
}
