package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.LowerStringMap;

public class AttributeMap extends AbstractAttributeMap
{
    private final Set<IAttributeInstance> dirtyInstances = Sets.<IAttributeInstance>newHashSet();
    protected final Map<String, IAttributeInstance> instancesByName = new LowerStringMap();

    public ModifiableAttributeInstance getAttributeInstance(IAttribute attribute)
    {
        return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
    }

    public ModifiableAttributeInstance getAttributeInstanceByName(String attributeName)
    {
        IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(attributeName);

        if (iattributeinstance == null)
        {
            iattributeinstance = this.instancesByName.get(attributeName);
        }

        return (ModifiableAttributeInstance)iattributeinstance;
    }

    /**
     * Registers an attribute with this AttributeMap, returns a modifiable AttributeInstance associated with this map
     */
    public IAttributeInstance registerAttribute(IAttribute attribute)
    {
        IAttributeInstance iattributeinstance = super.registerAttribute(attribute);

        if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null)
        {
            this.instancesByName.put(((RangedAttribute)attribute).getDescription(), iattributeinstance);
        }

        return iattributeinstance;
    }

    protected IAttributeInstance createInstance(IAttribute attribute)
    {
        return new ModifiableAttributeInstance(this, attribute);
    }

    public void onAttributeModified(IAttributeInstance instance)
    {
        if (instance.getAttribute().getShouldWatch())
        {
            this.dirtyInstances.add(instance);
        }

        for (IAttribute iattribute : this.descendantsByParent.get(instance.getAttribute()))
        {
            ModifiableAttributeInstance modifiableattributeinstance = this.getAttributeInstance(iattribute);

            if (modifiableattributeinstance != null)
            {
                modifiableattributeinstance.flagForUpdate();
            }
        }
    }

    public Set<IAttributeInstance> getDirtyInstances()
    {
        return this.dirtyInstances;
    }

    public Collection<IAttributeInstance> getWatchedAttributes()
    {
        Set<IAttributeInstance> set = Sets.<IAttributeInstance>newHashSet();

        for (IAttributeInstance iattributeinstance : this.getAllAttributes())
        {
            if (iattributeinstance.getAttribute().getShouldWatch())
            {
                set.add(iattributeinstance);
            }
        }

        return set;
    }
}
