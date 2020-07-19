package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple<K, V> implements IRegistry<K, V>
{
    private static final Logger LOGGER = LogManager.getLogger();
    protected final Map<K, V> registryObjects = this.createUnderlyingMap();
    private Object[] values;

    protected Map<K, V> createUnderlyingMap()
    {
        return Maps.<K, V>newHashMap();
    }

    @Nullable
    public V getObject(@Nullable K name)
    {
        return this.registryObjects.get(name);
    }

    /**
     * Register an object on this registry.
     */
    public void putObject(K key, V value)
    {
        Validate.notNull(key);
        Validate.notNull(value);
        this.values = null;

        if (this.registryObjects.containsKey(key))
        {
            LOGGER.debug("Adding duplicate key '{}' to registry", key);
        }

        this.registryObjects.put(key, value);
    }

    public Set<K> getKeys()
    {
        return Collections.<K>unmodifiableSet(this.registryObjects.keySet());
    }

    @Nullable
    public V getRandomObject(Random random)
    {
        if (this.values == null)
        {
            Collection<?> collection = this.registryObjects.values();

            if (collection.isEmpty())
            {
                return (V)null;
            }

            this.values = collection.toArray(new Object[collection.size()]);
        }

        return (V)this.values[random.nextInt(this.values.length)];
    }

    /**
     * Does this registry contain an entry for the given key?
     */
    public boolean containsKey(K key)
    {
        return this.registryObjects.containsKey(key);
    }

    public Iterator<V> iterator()
    {
        return this.registryObjects.values().iterator();
    }
}
