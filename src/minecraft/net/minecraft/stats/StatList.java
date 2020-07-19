package net.minecraft.stats;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class StatList
{
    protected static final Map<String, StatBase> ID_TO_STAT_MAP = Maps.<String, StatBase>newHashMap();
    public static final List<StatBase> ALL_STATS = Lists.<StatBase>newArrayList();
    public static final List<StatBase> BASIC_STATS = Lists.<StatBase>newArrayList();
    public static final List<StatCrafting> USE_ITEM_STATS = Lists.<StatCrafting>newArrayList();
    public static final List<StatCrafting> MINE_BLOCK_STATS = Lists.<StatCrafting>newArrayList();

    /** number of times you've left a game */
    public static final StatBase LEAVE_GAME = (new StatBasic("stat.leaveGame", new TextComponentTranslation("stat.leaveGame", new Object[0]))).initIndependentStat().registerStat();
    public static final StatBase PLAY_ONE_MINUTE = (new StatBasic("stat.playOneMinute", new TextComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
    public static final StatBase TIME_SINCE_DEATH = (new StatBasic("stat.timeSinceDeath", new TextComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
    public static final StatBase SNEAK_TIME = (new StatBasic("stat.sneakTime", new TextComponentTranslation("stat.sneakTime", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
    public static final StatBase WALK_ONE_CM = (new StatBasic("stat.walkOneCm", new TextComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase CROUCH_ONE_CM = (new StatBasic("stat.crouchOneCm", new TextComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase SPRINT_ONE_CM = (new StatBasic("stat.sprintOneCm", new TextComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();

    /** distance you have swam */
    public static final StatBase SWIM_ONE_CM = (new StatBasic("stat.swimOneCm", new TextComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();

    /** the distance you have fallen */
    public static final StatBase FALL_ONE_CM = (new StatBasic("stat.fallOneCm", new TextComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase CLIMB_ONE_CM = (new StatBasic("stat.climbOneCm", new TextComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase FLY_ONE_CM = (new StatBasic("stat.flyOneCm", new TextComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase DIVE_ONE_CM = (new StatBasic("stat.diveOneCm", new TextComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase MINECART_ONE_CM = (new StatBasic("stat.minecartOneCm", new TextComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase BOAT_ONE_CM = (new StatBasic("stat.boatOneCm", new TextComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase PIG_ONE_CM = (new StatBasic("stat.pigOneCm", new TextComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase HORSE_ONE_CM = (new StatBasic("stat.horseOneCm", new TextComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
    public static final StatBase AVIATE_ONE_CM = (new StatBasic("stat.aviateOneCm", new TextComponentTranslation("stat.aviateOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();

    /** the times you've jumped */
    public static final StatBase JUMP = (new StatBasic("stat.jump", new TextComponentTranslation("stat.jump", new Object[0]))).initIndependentStat().registerStat();

    /** the distance you've dropped (or times you've fallen?) */
    public static final StatBase DROP = (new StatBasic("stat.drop", new TextComponentTranslation("stat.drop", new Object[0]))).initIndependentStat().registerStat();
    public static final StatBase DAMAGE_DEALT = (new StatBasic("stat.damageDealt", new TextComponentTranslation("stat.damageDealt", new Object[0]), StatBase.divideByTen)).registerStat();
    public static final StatBase DAMAGE_TAKEN = (new StatBasic("stat.damageTaken", new TextComponentTranslation("stat.damageTaken", new Object[0]), StatBase.divideByTen)).registerStat();
    public static final StatBase DEATHS = (new StatBasic("stat.deaths", new TextComponentTranslation("stat.deaths", new Object[0]))).registerStat();
    public static final StatBase MOB_KILLS = (new StatBasic("stat.mobKills", new TextComponentTranslation("stat.mobKills", new Object[0]))).registerStat();

    /** the number of animals you have bred */
    public static final StatBase ANIMALS_BRED = (new StatBasic("stat.animalsBred", new TextComponentTranslation("stat.animalsBred", new Object[0]))).registerStat();

    /** counts the number of times you've killed a player */
    public static final StatBase PLAYER_KILLS = (new StatBasic("stat.playerKills", new TextComponentTranslation("stat.playerKills", new Object[0]))).registerStat();
    public static final StatBase FISH_CAUGHT = (new StatBasic("stat.fishCaught", new TextComponentTranslation("stat.fishCaught", new Object[0]))).registerStat();
    public static final StatBase TALKED_TO_VILLAGER = (new StatBasic("stat.talkedToVillager", new TextComponentTranslation("stat.talkedToVillager", new Object[0]))).registerStat();
    public static final StatBase TRADED_WITH_VILLAGER = (new StatBasic("stat.tradedWithVillager", new TextComponentTranslation("stat.tradedWithVillager", new Object[0]))).registerStat();
    public static final StatBase CAKE_SLICES_EATEN = (new StatBasic("stat.cakeSlicesEaten", new TextComponentTranslation("stat.cakeSlicesEaten", new Object[0]))).registerStat();
    public static final StatBase CAULDRON_FILLED = (new StatBasic("stat.cauldronFilled", new TextComponentTranslation("stat.cauldronFilled", new Object[0]))).registerStat();
    public static final StatBase CAULDRON_USED = (new StatBasic("stat.cauldronUsed", new TextComponentTranslation("stat.cauldronUsed", new Object[0]))).registerStat();
    public static final StatBase ARMOR_CLEANED = (new StatBasic("stat.armorCleaned", new TextComponentTranslation("stat.armorCleaned", new Object[0]))).registerStat();
    public static final StatBase BANNER_CLEANED = (new StatBasic("stat.bannerCleaned", new TextComponentTranslation("stat.bannerCleaned", new Object[0]))).registerStat();
    public static final StatBase BREWINGSTAND_INTERACTION = (new StatBasic("stat.brewingstandInteraction", new TextComponentTranslation("stat.brewingstandInteraction", new Object[0]))).registerStat();
    public static final StatBase BEACON_INTERACTION = (new StatBasic("stat.beaconInteraction", new TextComponentTranslation("stat.beaconInteraction", new Object[0]))).registerStat();
    public static final StatBase DROPPER_INSPECTED = (new StatBasic("stat.dropperInspected", new TextComponentTranslation("stat.dropperInspected", new Object[0]))).registerStat();
    public static final StatBase HOPPER_INSPECTED = (new StatBasic("stat.hopperInspected", new TextComponentTranslation("stat.hopperInspected", new Object[0]))).registerStat();
    public static final StatBase DISPENSER_INSPECTED = (new StatBasic("stat.dispenserInspected", new TextComponentTranslation("stat.dispenserInspected", new Object[0]))).registerStat();
    public static final StatBase NOTEBLOCK_PLAYED = (new StatBasic("stat.noteblockPlayed", new TextComponentTranslation("stat.noteblockPlayed", new Object[0]))).registerStat();
    public static final StatBase NOTEBLOCK_TUNED = (new StatBasic("stat.noteblockTuned", new TextComponentTranslation("stat.noteblockTuned", new Object[0]))).registerStat();
    public static final StatBase FLOWER_POTTED = (new StatBasic("stat.flowerPotted", new TextComponentTranslation("stat.flowerPotted", new Object[0]))).registerStat();
    public static final StatBase TRAPPED_CHEST_TRIGGERED = (new StatBasic("stat.trappedChestTriggered", new TextComponentTranslation("stat.trappedChestTriggered", new Object[0]))).registerStat();
    public static final StatBase ENDERCHEST_OPENED = (new StatBasic("stat.enderchestOpened", new TextComponentTranslation("stat.enderchestOpened", new Object[0]))).registerStat();
    public static final StatBase ITEM_ENCHANTED = (new StatBasic("stat.itemEnchanted", new TextComponentTranslation("stat.itemEnchanted", new Object[0]))).registerStat();
    public static final StatBase RECORD_PLAYED = (new StatBasic("stat.recordPlayed", new TextComponentTranslation("stat.recordPlayed", new Object[0]))).registerStat();
    public static final StatBase FURNACE_INTERACTION = (new StatBasic("stat.furnaceInteraction", new TextComponentTranslation("stat.furnaceInteraction", new Object[0]))).registerStat();
    public static final StatBase CRAFTING_TABLE_INTERACTION = (new StatBasic("stat.craftingTableInteraction", new TextComponentTranslation("stat.workbenchInteraction", new Object[0]))).registerStat();
    public static final StatBase CHEST_OPENED = (new StatBasic("stat.chestOpened", new TextComponentTranslation("stat.chestOpened", new Object[0]))).registerStat();
    public static final StatBase SLEEP_IN_BED = (new StatBasic("stat.sleepInBed", new TextComponentTranslation("stat.sleepInBed", new Object[0]))).registerStat();
    public static final StatBase OPEN_SHULKER_BOX = (new StatBasic("stat.shulkerBoxOpened", new TextComponentTranslation("stat.shulkerBoxOpened", new Object[0]))).registerStat();
    private static final StatBase[] BLOCKS_STATS = new StatBase[4096];
    private static final StatBase[] CRAFTS_STATS = new StatBase[32000];

    /** Tracks the number of times a given block or item has been used. */
    private static final StatBase[] OBJECT_USE_STATS = new StatBase[32000];

    /** Tracks the number of times a given block or item has been broken. */
    private static final StatBase[] OBJECT_BREAK_STATS = new StatBase[32000];
    private static final StatBase[] OBJECTS_PICKED_UP_STATS = new StatBase[32000];
    private static final StatBase[] OBJECTS_DROPPED_STATS = new StatBase[32000];

    @Nullable
    public static StatBase getBlockStats(Block blockIn)
    {
        return BLOCKS_STATS[Block.getIdFromBlock(blockIn)];
    }

    @Nullable
    public static StatBase getCraftStats(Item itemIn)
    {
        return CRAFTS_STATS[Item.getIdFromItem(itemIn)];
    }

    @Nullable
    public static StatBase getObjectUseStats(Item itemIn)
    {
        return OBJECT_USE_STATS[Item.getIdFromItem(itemIn)];
    }

    @Nullable
    public static StatBase getObjectBreakStats(Item itemIn)
    {
        return OBJECT_BREAK_STATS[Item.getIdFromItem(itemIn)];
    }

    @Nullable
    public static StatBase getObjectsPickedUpStats(Item itemIn)
    {
        return OBJECTS_PICKED_UP_STATS[Item.getIdFromItem(itemIn)];
    }

    @Nullable
    public static StatBase getDroppedObjectStats(Item itemIn)
    {
        return OBJECTS_DROPPED_STATS[Item.getIdFromItem(itemIn)];
    }

    public static void init()
    {
        initMiningStats();
        initStats();
        initItemDepleteStats();
        initCraftableStats();
        initPickedUpAndDroppedStats();
    }

    /**
     * Initializes statistics related to craftable items. Is only called after both block and item stats have been
     * initialized.
     */
    private static void initCraftableStats()
    {
        Set<Item> set = Sets.<Item>newHashSet();

        for (IRecipe irecipe : CraftingManager.REGISTRY)
        {
            ItemStack itemstack = irecipe.getRecipeOutput();

            if (!itemstack.isEmpty())
            {
                set.add(irecipe.getRecipeOutput().getItem());
            }
        }

        for (ItemStack itemstack1 : FurnaceRecipes.instance().getSmeltingList().values())
        {
            set.add(itemstack1.getItem());
        }

        for (Item item : set)
        {
            if (item != null)
            {
                int i = Item.getIdFromItem(item);
                String s = getItemName(item);

                if (s != null)
                {
                    CRAFTS_STATS[i] = (new StatCrafting("stat.craftItem.", s, new TextComponentTranslation("stat.craftItem", new Object[] {(new ItemStack(item)).getTextComponent()}), item)).registerStat();
                }
            }
        }

        replaceAllSimilarBlocks(CRAFTS_STATS);
    }

    private static void initMiningStats()
    {
        for (Block block : Block.REGISTRY)
        {
            Item item = Item.getItemFromBlock(block);

            if (item != Items.AIR)
            {
                int i = Block.getIdFromBlock(block);
                String s = getItemName(item);

                if (s != null && block.getEnableStats())
                {
                    BLOCKS_STATS[i] = (new StatCrafting("stat.mineBlock.", s, new TextComponentTranslation("stat.mineBlock", new Object[] {(new ItemStack(block)).getTextComponent()}), item)).registerStat();
                    MINE_BLOCK_STATS.add((StatCrafting)BLOCKS_STATS[i]);
                }
            }
        }

        replaceAllSimilarBlocks(BLOCKS_STATS);
    }

    private static void initStats()
    {
        for (Item item : Item.REGISTRY)
        {
            if (item != null)
            {
                int i = Item.getIdFromItem(item);
                String s = getItemName(item);

                if (s != null)
                {
                    OBJECT_USE_STATS[i] = (new StatCrafting("stat.useItem.", s, new TextComponentTranslation("stat.useItem", new Object[] {(new ItemStack(item)).getTextComponent()}), item)).registerStat();

                    if (!(item instanceof ItemBlock))
                    {
                        USE_ITEM_STATS.add((StatCrafting)OBJECT_USE_STATS[i]);
                    }
                }
            }
        }

        replaceAllSimilarBlocks(OBJECT_USE_STATS);
    }

    private static void initItemDepleteStats()
    {
        for (Item item : Item.REGISTRY)
        {
            if (item != null)
            {
                int i = Item.getIdFromItem(item);
                String s = getItemName(item);

                if (s != null && item.isDamageable())
                {
                    OBJECT_BREAK_STATS[i] = (new StatCrafting("stat.breakItem.", s, new TextComponentTranslation("stat.breakItem", new Object[] {(new ItemStack(item)).getTextComponent()}), item)).registerStat();
                }
            }
        }

        replaceAllSimilarBlocks(OBJECT_BREAK_STATS);
    }

    private static void initPickedUpAndDroppedStats()
    {
        for (Item item : Item.REGISTRY)
        {
            if (item != null)
            {
                int i = Item.getIdFromItem(item);
                String s = getItemName(item);

                if (s != null)
                {
                    OBJECTS_PICKED_UP_STATS[i] = (new StatCrafting("stat.pickup.", s, new TextComponentTranslation("stat.pickup", new Object[] {(new ItemStack(item)).getTextComponent()}), item)).registerStat();
                    OBJECTS_DROPPED_STATS[i] = (new StatCrafting("stat.drop.", s, new TextComponentTranslation("stat.drop", new Object[] {(new ItemStack(item)).getTextComponent()}), item)).registerStat();
                }
            }
        }

        replaceAllSimilarBlocks(OBJECT_BREAK_STATS);
    }

    private static String getItemName(Item itemIn)
    {
        ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(itemIn);
        return resourcelocation != null ? resourcelocation.toString().replace(':', '.') : null;
    }

    /**
     * Forces all dual blocks to count for each other on the stats list
     */
    private static void replaceAllSimilarBlocks(StatBase[] stat)
    {
        mergeStatBases(stat, Blocks.WATER, Blocks.FLOWING_WATER);
        mergeStatBases(stat, Blocks.LAVA, Blocks.FLOWING_LAVA);
        mergeStatBases(stat, Blocks.LIT_PUMPKIN, Blocks.PUMPKIN);
        mergeStatBases(stat, Blocks.LIT_FURNACE, Blocks.FURNACE);
        mergeStatBases(stat, Blocks.LIT_REDSTONE_ORE, Blocks.REDSTONE_ORE);
        mergeStatBases(stat, Blocks.POWERED_REPEATER, Blocks.UNPOWERED_REPEATER);
        mergeStatBases(stat, Blocks.POWERED_COMPARATOR, Blocks.UNPOWERED_COMPARATOR);
        mergeStatBases(stat, Blocks.REDSTONE_TORCH, Blocks.UNLIT_REDSTONE_TORCH);
        mergeStatBases(stat, Blocks.LIT_REDSTONE_LAMP, Blocks.REDSTONE_LAMP);
        mergeStatBases(stat, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB);
        mergeStatBases(stat, Blocks.DOUBLE_WOODEN_SLAB, Blocks.WOODEN_SLAB);
        mergeStatBases(stat, Blocks.DOUBLE_STONE_SLAB2, Blocks.STONE_SLAB2);
        mergeStatBases(stat, Blocks.GRASS, Blocks.DIRT);
        mergeStatBases(stat, Blocks.FARMLAND, Blocks.DIRT);
    }

    /**
     * Merge {@link StatBase} object references for similar blocks
     */
    private static void mergeStatBases(StatBase[] statBaseIn, Block block1, Block block2)
    {
        int i = Block.getIdFromBlock(block1);
        int j = Block.getIdFromBlock(block2);

        if (statBaseIn[i] != null && statBaseIn[j] == null)
        {
            statBaseIn[j] = statBaseIn[i];
        }
        else
        {
            ALL_STATS.remove(statBaseIn[i]);
            MINE_BLOCK_STATS.remove(statBaseIn[i]);
            BASIC_STATS.remove(statBaseIn[i]);
            statBaseIn[i] = statBaseIn[j];
        }
    }

    public static StatBase getStatKillEntity(EntityList.EntityEggInfo eggInfo)
    {
        String s = EntityList.getTranslationName(eggInfo.spawnedID);
        return s == null ? null : (new StatBase("stat.killEntity." + s, new TextComponentTranslation("stat.entityKill", new Object[] {new TextComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }

    public static StatBase getStatEntityKilledBy(EntityList.EntityEggInfo eggInfo)
    {
        String s = EntityList.getTranslationName(eggInfo.spawnedID);
        return s == null ? null : (new StatBase("stat.entityKilledBy." + s, new TextComponentTranslation("stat.entityKilledBy", new Object[] {new TextComponentTranslation("entity." + s + ".name", new Object[0])}))).registerStat();
    }

    @Nullable
    public static StatBase getOneShotStat(String statName)
    {
        return ID_TO_STAT_MAP.get(statName);
    }
}
