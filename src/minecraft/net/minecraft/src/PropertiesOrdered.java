package net.minecraft.src;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered extends Properties
{
    private Set<Object> keysOrdered = new LinkedHashSet<Object>();

    public synchronized Object put(Object p_put_1_, Object p_put_2_)
    {
        this.keysOrdered.add(p_put_1_);
        return super.put(p_put_1_, p_put_2_);
    }

    public Set<Object> keySet()
    {
        Set<Object> set = super.keySet();
        this.keysOrdered.retainAll(set);
        return Collections.<Object>unmodifiableSet(this.keysOrdered);
    }

    public synchronized Enumeration<Object> keys()
    {
        return Collections.<Object>enumeration(this.keySet());
    }
}
