package net.minecraft.command;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class CommandResultStats
{
    /** The number of result command result types that are possible. */
    private static final int NUM_RESULT_TYPES = CommandResultStats.Type.values().length;
    private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];

    /**
     * List of entityID who set a stat, username for a player, UUID for all entities
     */
    private String[] entitiesID;

    /** List of all the Objectives names */
    private String[] objectives;

    public CommandResultStats()
    {
        this.entitiesID = STRING_RESULT_TYPES;
        this.objectives = STRING_RESULT_TYPES;
    }

    public void setCommandStatForSender(MinecraftServer server, final ICommandSender sender, CommandResultStats.Type typeIn, int p_184932_4_)
    {
        String s = this.entitiesID[typeIn.getTypeID()];

        if (s != null)
        {
            ICommandSender icommandsender = new ICommandSender()
            {
                public String getName()
                {
                    return sender.getName();
                }
                public ITextComponent getDisplayName()
                {
                    return sender.getDisplayName();
                }
                public void sendMessage(ITextComponent component)
                {
                    sender.sendMessage(component);
                }
                public boolean canUseCommand(int permLevel, String commandName)
                {
                    return true;
                }
                public BlockPos getPosition()
                {
                    return sender.getPosition();
                }
                public Vec3d getPositionVector()
                {
                    return sender.getPositionVector();
                }
                public World getEntityWorld()
                {
                    return sender.getEntityWorld();
                }
                public Entity getCommandSenderEntity()
                {
                    return sender.getCommandSenderEntity();
                }
                public boolean sendCommandFeedback()
                {
                    return sender.sendCommandFeedback();
                }
                public void setCommandStat(CommandResultStats.Type type, int amount)
                {
                    sender.setCommandStat(type, amount);
                }
                public MinecraftServer getServer()
                {
                    return sender.getServer();
                }
            };
            String s1;

            try
            {
                s1 = CommandBase.getEntityName(server, icommandsender, s);
            }
            catch (CommandException var12)
            {
                return;
            }

            String s2 = this.objectives[typeIn.getTypeID()];

            if (s2 != null)
            {
                Scoreboard scoreboard = sender.getEntityWorld().getScoreboard();
                ScoreObjective scoreobjective = scoreboard.getObjective(s2);

                if (scoreobjective != null)
                {
                    if (scoreboard.entityHasObjective(s1, scoreobjective))
                    {
                        Score score = scoreboard.getOrCreateScore(s1, scoreobjective);
                        score.setScorePoints(p_184932_4_);
                    }
                }
            }
        }
    }

    public void readStatsFromNBT(NBTTagCompound tagcompound)
    {
        if (tagcompound.hasKey("CommandStats", 10))
        {
            NBTTagCompound nbttagcompound = tagcompound.getCompoundTag("CommandStats");

            for (CommandResultStats.Type commandresultstats$type : CommandResultStats.Type.values())
            {
                String s = commandresultstats$type.getTypeName() + "Name";
                String s1 = commandresultstats$type.getTypeName() + "Objective";

                if (nbttagcompound.hasKey(s, 8) && nbttagcompound.hasKey(s1, 8))
                {
                    String s2 = nbttagcompound.getString(s);
                    String s3 = nbttagcompound.getString(s1);
                    setScoreBoardStat(this, commandresultstats$type, s2, s3);
                }
            }
        }
    }

    public void writeStatsToNBT(NBTTagCompound tagcompound)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for (CommandResultStats.Type commandresultstats$type : CommandResultStats.Type.values())
        {
            String s = this.entitiesID[commandresultstats$type.getTypeID()];
            String s1 = this.objectives[commandresultstats$type.getTypeID()];

            if (s != null && s1 != null)
            {
                nbttagcompound.setString(commandresultstats$type.getTypeName() + "Name", s);
                nbttagcompound.setString(commandresultstats$type.getTypeName() + "Objective", s1);
            }
        }

        if (!nbttagcompound.hasNoTags())
        {
            tagcompound.setTag("CommandStats", nbttagcompound);
        }
    }

    /**
     * Set a stat in the scoreboard
     */
    public static void setScoreBoardStat(CommandResultStats stats, CommandResultStats.Type resultType, @Nullable String entityID, @Nullable String objectiveName)
    {
        if (entityID != null && !entityID.isEmpty() && objectiveName != null && !objectiveName.isEmpty())
        {
            if (stats.entitiesID == STRING_RESULT_TYPES || stats.objectives == STRING_RESULT_TYPES)
            {
                stats.entitiesID = new String[NUM_RESULT_TYPES];
                stats.objectives = new String[NUM_RESULT_TYPES];
            }

            stats.entitiesID[resultType.getTypeID()] = entityID;
            stats.objectives[resultType.getTypeID()] = objectiveName;
        }
        else
        {
            removeScoreBoardStat(stats, resultType);
        }
    }

    /**
     * Remove a stat from the scoreboard
     */
    private static void removeScoreBoardStat(CommandResultStats resultStatsIn, CommandResultStats.Type resultTypeIn)
    {
        if (resultStatsIn.entitiesID != STRING_RESULT_TYPES && resultStatsIn.objectives != STRING_RESULT_TYPES)
        {
            resultStatsIn.entitiesID[resultTypeIn.getTypeID()] = null;
            resultStatsIn.objectives[resultTypeIn.getTypeID()] = null;
            boolean flag = true;

            for (CommandResultStats.Type commandresultstats$type : CommandResultStats.Type.values())
            {
                if (resultStatsIn.entitiesID[commandresultstats$type.getTypeID()] != null && resultStatsIn.objectives[commandresultstats$type.getTypeID()] != null)
                {
                    flag = false;
                    break;
                }
            }

            if (flag)
            {
                resultStatsIn.entitiesID = STRING_RESULT_TYPES;
                resultStatsIn.objectives = STRING_RESULT_TYPES;
            }
        }
    }

    /**
     * Add all stats in the CommandResultStats
     */
    public void addAllStats(CommandResultStats resultStatsIn)
    {
        for (CommandResultStats.Type commandresultstats$type : CommandResultStats.Type.values())
        {
            setScoreBoardStat(this, commandresultstats$type, resultStatsIn.entitiesID[commandresultstats$type.getTypeID()], resultStatsIn.objectives[commandresultstats$type.getTypeID()]);
        }
    }

    public static enum Type
    {
        SUCCESS_COUNT(0, "SuccessCount"),
        AFFECTED_BLOCKS(1, "AffectedBlocks"),
        AFFECTED_ENTITIES(2, "AffectedEntities"),
        AFFECTED_ITEMS(3, "AffectedItems"),
        QUERY_RESULT(4, "QueryResult");

        final int typeID;
        final String typeName;

        private Type(int id, String name)
        {
            this.typeID = id;
            this.typeName = name;
        }

        public int getTypeID()
        {
            return this.typeID;
        }

        public String getTypeName()
        {
            return this.typeName;
        }

        public static String[] getTypeNames()
        {
            String[] astring = new String[values().length];
            int i = 0;

            for (CommandResultStats.Type commandresultstats$type : values())
            {
                astring[i++] = commandresultstats$type.getTypeName();
            }

            return astring;
        }

        @Nullable
        public static CommandResultStats.Type getTypeByName(String name)
        {
            for (CommandResultStats.Type commandresultstats$type : values())
            {
                if (commandresultstats$type.getTypeName().equals(name))
                {
                    return commandresultstats$type;
                }
            }

            return null;
        }
    }
}
