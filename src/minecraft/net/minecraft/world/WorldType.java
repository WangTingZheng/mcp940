package net.minecraft.world;

public class WorldType
{
    /** List of world types. */
    public static final WorldType[] WORLD_TYPES = new WorldType[16];

    /** Default world type. */
    public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).setVersioned();

    /** Flat world type. */
    public static final WorldType FLAT = new WorldType(1, "flat");

    /** Large Biome world Type. */
    public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");

    /** amplified world type */
    public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).enableInfoNotice();
    public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
    public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType(5, "debug_all_block_states");

    /** Default (1.1) world type. */
    public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);

    /** ID for this world type. */
    private final int id;
    private final String name;

    /** The int version of the ChunkProvider that generated this world. */
    private final int version;

    /**
     * Whether this world type can be generated. Normally true; set to false for out-of-date generator versions.
     */
    private boolean canBeCreated;

    /** Whether this WorldType has a version or not. */
    private boolean versioned;
    private boolean hasInfoNotice;

    private WorldType(int id, String name)
    {
        this(id, name, 0);
    }

    private WorldType(int id, String name, int version)
    {
        this.name = name;
        this.version = version;
        this.canBeCreated = true;
        this.id = id;
        WORLD_TYPES[id] = this;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the translation key for the name of this world type.
     */
    public String getTranslationKey()
    {
        return "generator." + this.name;
    }

    /**
     * Gets the translation key for the info text for this world type.
     */
    public String getInfoTranslationKey()
    {
        return this.getTranslationKey() + ".info";
    }

    /**
     * Returns generatorVersion.
     */
    public int getVersion()
    {
        return this.version;
    }

    public WorldType getWorldTypeForGeneratorVersion(int version)
    {
        return this == DEFAULT && version == 0 ? DEFAULT_1_1 : this;
    }

    /**
     * Sets canBeCreated to the provided value, and returns this.
     */
    private WorldType setCanBeCreated(boolean enable)
    {
        this.canBeCreated = enable;
        return this;
    }

    /**
     * Gets whether this WorldType can be used to generate a new world.
     */
    public boolean canBeCreated()
    {
        return this.canBeCreated;
    }

    /**
     * Flags this world type as having an associated version.
     */
    private WorldType setVersioned()
    {
        this.versioned = true;
        return this;
    }

    /**
     * Returns true if this world Type has a version associated with it.
     */
    public boolean isVersioned()
    {
        return this.versioned;
    }

    public static WorldType parseWorldType(String type)
    {
        for (WorldType worldtype : WORLD_TYPES)
        {
            if (worldtype != null && worldtype.name.equalsIgnoreCase(type))
            {
                return worldtype;
            }
        }

        return null;
    }

    public int getId()
    {
        return this.id;
    }

    /**
     * returns true if selecting this worldtype from the customize menu should display the generator.[worldtype].info
     * message
     */
    public boolean hasInfoNotice()
    {
        return this.hasInfoNotice;
    }

    /**
     * enables the display of generator.[worldtype].info message on the customize world menu
     */
    private WorldType enableInfoNotice()
    {
        this.hasInfoNotice = true;
        return this;
    }
}
