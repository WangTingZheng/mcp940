package net.minecraft.block.material;

public class Material
{
    public static final Material AIR = new MaterialTransparent(MapColor.AIR);
    public static final Material GRASS = new Material(MapColor.GRASS);
    public static final Material GROUND = new Material(MapColor.DIRT);
    public static final Material WOOD = (new Material(MapColor.WOOD)).setBurning();
    public static final Material ROCK = (new Material(MapColor.STONE)).setRequiresTool();
    public static final Material IRON = (new Material(MapColor.IRON)).setRequiresTool();
    public static final Material ANVIL = (new Material(MapColor.IRON)).setRequiresTool().setImmovableMobility();
    public static final Material WATER = (new MaterialLiquid(MapColor.WATER)).setNoPushMobility();
    public static final Material LAVA = (new MaterialLiquid(MapColor.TNT)).setNoPushMobility();
    public static final Material LEAVES = (new Material(MapColor.FOLIAGE)).setBurning().setTranslucent().setNoPushMobility();
    public static final Material PLANTS = (new MaterialLogic(MapColor.FOLIAGE)).setNoPushMobility();
    public static final Material VINE = (new MaterialLogic(MapColor.FOLIAGE)).setBurning().setNoPushMobility().setReplaceable();
    public static final Material SPONGE = new Material(MapColor.YELLOW);
    public static final Material CLOTH = (new Material(MapColor.CLOTH)).setBurning();
    public static final Material FIRE = (new MaterialTransparent(MapColor.AIR)).setNoPushMobility();
    public static final Material SAND = new Material(MapColor.SAND);
    public static final Material CIRCUITS = (new MaterialLogic(MapColor.AIR)).setNoPushMobility();
    public static final Material CARPET = (new MaterialLogic(MapColor.CLOTH)).setBurning();
    public static final Material GLASS = (new Material(MapColor.AIR)).setTranslucent().setAdventureModeExempt();
    public static final Material REDSTONE_LIGHT = (new Material(MapColor.AIR)).setAdventureModeExempt();
    public static final Material TNT = (new Material(MapColor.TNT)).setBurning().setTranslucent();
    public static final Material CORAL = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
    public static final Material ICE = (new Material(MapColor.ICE)).setTranslucent().setAdventureModeExempt();
    public static final Material PACKED_ICE = (new Material(MapColor.ICE)).setAdventureModeExempt();
    public static final Material SNOW = (new MaterialLogic(MapColor.SNOW)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();

    /** The material for crafted snow. */
    public static final Material CRAFTED_SNOW = (new Material(MapColor.SNOW)).setRequiresTool();
    public static final Material CACTUS = (new Material(MapColor.FOLIAGE)).setTranslucent().setNoPushMobility();
    public static final Material CLAY = new Material(MapColor.CLAY);
    public static final Material GOURD = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
    public static final Material DRAGON_EGG = (new Material(MapColor.FOLIAGE)).setNoPushMobility();
    public static final Material PORTAL = (new MaterialPortal(MapColor.AIR)).setImmovableMobility();
    public static final Material CAKE = (new Material(MapColor.AIR)).setNoPushMobility();
    public static final Material WEB = (new Material(MapColor.CLOTH)
    {
        public boolean blocksMovement()
        {
            return false;
        }
    }).setRequiresTool().setNoPushMobility();

    /** Pistons' material. */
    public static final Material PISTON = (new Material(MapColor.STONE)).setImmovableMobility();
    public static final Material BARRIER = (new Material(MapColor.AIR)).setRequiresTool().setImmovableMobility();
    public static final Material STRUCTURE_VOID = new MaterialTransparent(MapColor.AIR);

    /** Bool defining if the block can burn or not. */
    private boolean canBurn;

    /**
     * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
     * and tall grass.
     */
    private boolean replaceable;

    /** Indicates if the material is translucent */
    private boolean isTranslucent;

    /** The color index used to draw the blocks of this material on maps. */
    private final MapColor materialMapColor;

    /**
     * Determines if the material can be harvested without a tool (or with the wrong tool)
     */
    private boolean requiresNoTool = true;

    /**
     * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
     * indicates that it can't be pushed.
     */
    private EnumPushReaction mobilityFlag = EnumPushReaction.NORMAL;
    private boolean isAdventureModeExempt;

    public Material(MapColor color)
    {
        this.materialMapColor = color;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return false;
    }

    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    public boolean isSolid()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean blocksLight()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return true;
    }

    /**
     * Marks the material as translucent
     */
    private Material setTranslucent()
    {
        this.isTranslucent = true;
        return this;
    }

    /**
     * Makes blocks with this material require the correct tool to be harvested.
     */
    protected Material setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }

    /**
     * Set the canBurn bool to True and return the current object.
     */
    protected Material setBurning()
    {
        this.canBurn = true;
        return this;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean getCanBurn()
    {
        return this.canBurn;
    }

    /**
     * Sets {@link #replaceable} to true.
     */
    public Material setReplaceable()
    {
        this.replaceable = true;
        return this;
    }

    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    public boolean isReplaceable()
    {
        return this.replaceable;
    }

    /**
     * Indicate if the material is opaque
     */
    public boolean isOpaque()
    {
        return this.isTranslucent ? false : this.blocksMovement();
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isToolNotRequired()
    {
        return this.requiresNoTool;
    }

    public EnumPushReaction getMobilityFlag()
    {
        return this.mobilityFlag;
    }

    /**
     * This type of material can't be pushed, but pistons can move over it.
     */
    protected Material setNoPushMobility()
    {
        this.mobilityFlag = EnumPushReaction.DESTROY;
        return this;
    }

    /**
     * This type of material can't be pushed, and pistons are blocked to move.
     */
    protected Material setImmovableMobility()
    {
        this.mobilityFlag = EnumPushReaction.BLOCK;
        return this;
    }

    /**
     * @see #isAdventureModeExempt()
     */
    protected Material setAdventureModeExempt()
    {
        this.isAdventureModeExempt = true;
        return this;
    }

    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    public MapColor getMaterialMapColor()
    {
        return this.materialMapColor;
    }
}
