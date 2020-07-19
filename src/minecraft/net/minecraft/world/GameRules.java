package net.minecraft.world;

import java.util.Set;
import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;

public class GameRules
{
    private final TreeMap<String, GameRules.Value> rules = new TreeMap<String, GameRules.Value>();

    public GameRules()
    {
        this.addGameRule("doFireTick", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("mobGriefing", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("keepInventory", "false", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobSpawning", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobLoot", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doTileDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doEntityDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("commandBlockOutput", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("naturalRegeneration", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doDaylightCycle", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("logAdminCommands", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("showDeathMessages", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("randomTickSpeed", "3", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("sendCommandFeedback", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("reducedDebugInfo", "false", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("spectatorsGenerateChunks", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("spawnRadius", "10", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("disableElytraMovementCheck", "false", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("maxEntityCramming", "24", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("doWeatherCycle", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("doLimitedCrafting", "false", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("maxCommandChainLength", "65536", GameRules.ValueType.NUMERICAL_VALUE);
        this.addGameRule("announceAdvancements", "true", GameRules.ValueType.BOOLEAN_VALUE);
        this.addGameRule("gameLoopFunction", "-", GameRules.ValueType.FUNCTION);
    }

    public void addGameRule(String key, String value, GameRules.ValueType type)
    {
        this.rules.put(key, new GameRules.Value(value, type));
    }

    public void setOrCreateGameRule(String key, String ruleValue)
    {
        GameRules.Value gamerules$value = this.rules.get(key);

        if (gamerules$value != null)
        {
            gamerules$value.setValue(ruleValue);
        }
        else
        {
            this.addGameRule(key, ruleValue, GameRules.ValueType.ANY_VALUE);
        }
    }

    /**
     * Gets the string Game Rule value.
     */
    public String getString(String name)
    {
        GameRules.Value gamerules$value = this.rules.get(name);
        return gamerules$value != null ? gamerules$value.getString() : "";
    }

    /**
     * Gets the boolean Game Rule value.
     */
    public boolean getBoolean(String name)
    {
        GameRules.Value gamerules$value = this.rules.get(name);
        return gamerules$value != null ? gamerules$value.getBoolean() : false;
    }

    public int getInt(String name)
    {
        GameRules.Value gamerules$value = this.rules.get(name);
        return gamerules$value != null ? gamerules$value.getInt() : 0;
    }

    /**
     * Return the defined game rules as NBT.
     */
    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for (String s : this.rules.keySet())
        {
            GameRules.Value gamerules$value = this.rules.get(s);
            nbttagcompound.setString(s, gamerules$value.getString());
        }

        return nbttagcompound;
    }

    /**
     * Set defined game rules from NBT.
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        for (String s : nbt.getKeySet())
        {
            this.setOrCreateGameRule(s, nbt.getString(s));
        }
    }

    /**
     * Return the defined game rules.
     */
    public String[] getRules()
    {
        Set<String> set = this.rules.keySet();
        return (String[])set.toArray(new String[set.size()]);
    }

    /**
     * Return whether the specified game rule is defined.
     */
    public boolean hasRule(String name)
    {
        return this.rules.containsKey(name);
    }

    public boolean areSameType(String key, GameRules.ValueType otherValue)
    {
        GameRules.Value gamerules$value = this.rules.get(key);
        return gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == GameRules.ValueType.ANY_VALUE);
    }

    static class Value
    {
        private String valueString;
        private boolean valueBoolean;
        private int valueInteger;
        private double valueDouble;
        private final GameRules.ValueType type;

        public Value(String value, GameRules.ValueType type)
        {
            this.type = type;
            this.setValue(value);
        }

        public void setValue(String value)
        {
            this.valueString = value;

            if (value != null)
            {
                if (value.equals("false"))
                {
                    this.valueBoolean = false;
                    return;
                }

                if (value.equals("true"))
                {
                    this.valueBoolean = true;
                    return;
                }
            }

            this.valueBoolean = Boolean.parseBoolean(value);
            this.valueInteger = this.valueBoolean ? 1 : 0;

            try
            {
                this.valueInteger = Integer.parseInt(value);
            }
            catch (NumberFormatException var4)
            {
                ;
            }

            try
            {
                this.valueDouble = Double.parseDouble(value);
            }
            catch (NumberFormatException var3)
            {
                ;
            }
        }

        public String getString()
        {
            return this.valueString;
        }

        public boolean getBoolean()
        {
            return this.valueBoolean;
        }

        public int getInt()
        {
            return this.valueInteger;
        }

        public GameRules.ValueType getType()
        {
            return this.type;
        }
    }

    public static enum ValueType
    {
        ANY_VALUE,
        BOOLEAN_VALUE,
        NUMERICAL_VALUE,
        FUNCTION;
    }
}
