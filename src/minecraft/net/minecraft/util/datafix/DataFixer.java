package net.minecraft.util.datafix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixer implements IDataFixer
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<IFixType, List<IDataWalker>> walkerMap = Maps.<IFixType, List<IDataWalker>>newHashMap();
    private final Map<IFixType, List<IFixableData>> fixMap = Maps.<IFixType, List<IFixableData>>newHashMap();
    private final int version;

    public DataFixer(int versionIn)
    {
        this.version = versionIn;
    }

    public NBTTagCompound process(IFixType type, NBTTagCompound compound)
    {
        int i = compound.hasKey("DataVersion", 99) ? compound.getInteger("DataVersion") : -1;
        return i >= 1139 ? compound : this.process(type, compound, i);
    }

    public NBTTagCompound process(IFixType type, NBTTagCompound compound, int versionIn)
    {
        if (versionIn < this.version)
        {
            compound = this.processFixes(type, compound, versionIn);
            compound = this.processWalkers(type, compound, versionIn);
        }

        return compound;
    }

    private NBTTagCompound processFixes(IFixType type, NBTTagCompound compound, int versionIn)
    {
        List<IFixableData> list = (List)this.fixMap.get(type);

        if (list != null)
        {
            for (int i = 0; i < list.size(); ++i)
            {
                IFixableData ifixabledata = list.get(i);

                if (ifixabledata.getFixVersion() > versionIn)
                {
                    compound = ifixabledata.fixTagCompound(compound);
                }
            }
        }

        return compound;
    }

    private NBTTagCompound processWalkers(IFixType type, NBTTagCompound compound, int versionIn)
    {
        List<IDataWalker> list = (List)this.walkerMap.get(type);

        if (list != null)
        {
            for (int i = 0; i < list.size(); ++i)
            {
                compound = ((IDataWalker)list.get(i)).process(this, compound, versionIn);
            }
        }

        return compound;
    }

    public void registerWalker(FixTypes type, IDataWalker walker)
    {
        this.registerVanillaWalker(type, walker);
    }

    /**
     * Do not invoke this method, use registerWalker instead. It is expected to be removed in future versions.
     */
    public void registerVanillaWalker(IFixType type, IDataWalker walker)
    {
        this.getTypeList(this.walkerMap, type).add(walker);
    }

    public void registerFix(IFixType type, IFixableData fixable)
    {
        List<IFixableData> list = this.<IFixableData>getTypeList(this.fixMap, type);
        int i = fixable.getFixVersion();

        if (i > this.version)
        {
            LOGGER.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", Integer.valueOf(i), Integer.valueOf(this.version));
        }
        else
        {
            if (!list.isEmpty() && ((IFixableData)Util.getLastElement(list)).getFixVersion() > i)
            {
                for (int j = 0; j < list.size(); ++j)
                {
                    if (((IFixableData)list.get(j)).getFixVersion() > i)
                    {
                        list.add(j, fixable);
                        break;
                    }
                }
            }
            else
            {
                list.add(fixable);
            }
        }
    }

    private <V> List<V> getTypeList(Map<IFixType, List<V>> map, IFixType type)
    {
        List<V> list = (List)map.get(type);

        if (list == null)
        {
            list = Lists.<V>newArrayList();
            map.put(type, list);
        }

        return list;
    }
}
