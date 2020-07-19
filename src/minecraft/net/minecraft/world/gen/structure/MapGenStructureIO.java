package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapGenStructureIO
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map < String, Class <? extends StructureStart >> startNameToClassMap = Maps. < String, Class <? extends StructureStart >> newHashMap();
    private static final Map < Class <? extends StructureStart > , String > startClassToNameMap = Maps. < Class <? extends StructureStart > , String > newHashMap();
    private static final Map < String, Class <? extends StructureComponent >> componentNameToClassMap = Maps. < String, Class <? extends StructureComponent >> newHashMap();
    private static final Map < Class <? extends StructureComponent > , String > componentClassToNameMap = Maps. < Class <? extends StructureComponent > , String > newHashMap();

    private static void registerStructure(Class <? extends StructureStart > startClass, String structureName)
    {
        startNameToClassMap.put(structureName, startClass);
        startClassToNameMap.put(startClass, structureName);
    }

    static void registerStructureComponent(Class <? extends StructureComponent > componentClass, String componentName)
    {
        componentNameToClassMap.put(componentName, componentClass);
        componentClassToNameMap.put(componentClass, componentName);
    }

    public static String getStructureStartName(StructureStart start)
    {
        return startClassToNameMap.get(start.getClass());
    }

    public static String getStructureComponentName(StructureComponent component)
    {
        return componentClassToNameMap.get(component.getClass());
    }

    @Nullable
    public static StructureStart getStructureStart(NBTTagCompound tagCompound, World worldIn)
    {
        StructureStart structurestart = null;

        try
        {
            Class <? extends StructureStart > oclass = (Class)startNameToClassMap.get(tagCompound.getString("id"));

            if (oclass != null)
            {
                structurestart = oclass.newInstance();
            }
        }
        catch (Exception exception)
        {
            LOGGER.warn("Failed Start with id {}", (Object)tagCompound.getString("id"));
            exception.printStackTrace();
        }

        if (structurestart != null)
        {
            structurestart.readStructureComponentsFromNBT(worldIn, tagCompound);
        }
        else
        {
            LOGGER.warn("Skipping Structure with id {}", (Object)tagCompound.getString("id"));
        }

        return structurestart;
    }

    public static StructureComponent getStructureComponent(NBTTagCompound tagCompound, World worldIn)
    {
        StructureComponent structurecomponent = null;

        try
        {
            Class <? extends StructureComponent > oclass = (Class)componentNameToClassMap.get(tagCompound.getString("id"));

            if (oclass != null)
            {
                structurecomponent = oclass.newInstance();
            }
        }
        catch (Exception exception)
        {
            LOGGER.warn("Failed Piece with id {}", (Object)tagCompound.getString("id"));
            exception.printStackTrace();
        }

        if (structurecomponent != null)
        {
            structurecomponent.readStructureBaseNBT(worldIn, tagCompound);
        }
        else
        {
            LOGGER.warn("Skipping Piece with id {}", (Object)tagCompound.getString("id"));
        }

        return structurecomponent;
    }

    static
    {
        registerStructure(StructureMineshaftStart.class, "Mineshaft");
        registerStructure(MapGenVillage.Start.class, "Village");
        registerStructure(MapGenNetherBridge.Start.class, "Fortress");
        registerStructure(MapGenStronghold.Start.class, "Stronghold");
        registerStructure(MapGenScatteredFeature.Start.class, "Temple");
        registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
        registerStructure(MapGenEndCity.Start.class, "EndCity");
        registerStructure(WoodlandMansion.Start.class, "Mansion");
        StructureMineshaftPieces.registerStructurePieces();
        StructureVillagePieces.registerVillagePieces();
        StructureNetherBridgePieces.registerNetherFortressPieces();
        StructureStrongholdPieces.registerStrongholdPieces();
        ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
        StructureOceanMonumentPieces.registerOceanMonumentPieces();
        StructureEndCityPieces.registerPieces();
        WoodlandMansionPieces.registerWoodlandMansionPieces();
    }
}
