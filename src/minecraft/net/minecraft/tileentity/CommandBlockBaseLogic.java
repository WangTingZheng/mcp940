package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class CommandBlockBaseLogic implements ICommandSender
{
    /** The formatting for the timestamp on commands run. */
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private long lastExecution = -1L;
    private boolean updateLastExecution = true;

    /** The number of successful commands run. (used for redstone output) */
    private int successCount;
    private boolean trackOutput = true;

    /** The previously run command. */
    private ITextComponent lastOutput;

    /** The command stored in the command block. */
    private String commandStored = "";

    /** The custom name of the command block. (defaults to "@") */
    private String customName = "@";
    private final CommandResultStats resultStats = new CommandResultStats();

    /**
     * returns the successCount int.
     */
    public int getSuccessCount()
    {
        return this.successCount;
    }

    public void setSuccessCount(int successCountIn)
    {
        this.successCount = successCountIn;
    }

    /**
     * Returns the lastOutput.
     */
    public ITextComponent getLastOutput()
    {
        return (ITextComponent)(this.lastOutput == null ? new TextComponentString("") : this.lastOutput);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound p_189510_1_)
    {
        p_189510_1_.setString("Command", this.commandStored);
        p_189510_1_.setInteger("SuccessCount", this.successCount);
        p_189510_1_.setString("CustomName", this.customName);
        p_189510_1_.setBoolean("TrackOutput", this.trackOutput);

        if (this.lastOutput != null && this.trackOutput)
        {
            p_189510_1_.setString("LastOutput", ITextComponent.Serializer.componentToJson(this.lastOutput));
        }

        p_189510_1_.setBoolean("UpdateLastExecution", this.updateLastExecution);

        if (this.updateLastExecution && this.lastExecution > 0L)
        {
            p_189510_1_.setLong("LastExecution", this.lastExecution);
        }

        this.resultStats.writeStatsToNBT(p_189510_1_);
        return p_189510_1_;
    }

    /**
     * Reads NBT formatting and stored data into variables.
     */
    public void readDataFromNBT(NBTTagCompound nbt)
    {
        this.commandStored = nbt.getString("Command");
        this.successCount = nbt.getInteger("SuccessCount");

        if (nbt.hasKey("CustomName", 8))
        {
            this.customName = nbt.getString("CustomName");
        }

        if (nbt.hasKey("TrackOutput", 1))
        {
            this.trackOutput = nbt.getBoolean("TrackOutput");
        }

        if (nbt.hasKey("LastOutput", 8) && this.trackOutput)
        {
            try
            {
                this.lastOutput = ITextComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
            }
            catch (Throwable throwable)
            {
                this.lastOutput = new TextComponentString(throwable.getMessage());
            }
        }
        else
        {
            this.lastOutput = null;
        }

        if (nbt.hasKey("UpdateLastExecution"))
        {
            this.updateLastExecution = nbt.getBoolean("UpdateLastExecution");
        }

        if (this.updateLastExecution && nbt.hasKey("LastExecution"))
        {
            this.lastExecution = nbt.getLong("LastExecution");
        }
        else
        {
            this.lastExecution = -1L;
        }

        this.resultStats.readStatsFromNBT(nbt);
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    public boolean canUseCommand(int permLevel, String commandName)
    {
        return permLevel <= 2;
    }

    /**
     * Sets the command.
     */
    public void setCommand(String command)
    {
        this.commandStored = command;
        this.successCount = 0;
    }

    /**
     * Returns the command of the command block.
     */
    public String getCommand()
    {
        return this.commandStored;
    }

    public boolean trigger(World worldIn)
    {
        if (!worldIn.isRemote && worldIn.getTotalWorldTime() != this.lastExecution)
        {
            if ("Searge".equalsIgnoreCase(this.commandStored))
            {
                this.lastOutput = new TextComponentString("#itzlipofutzli");
                this.successCount = 1;
                return true;
            }
            else
            {
                MinecraftServer minecraftserver = this.getServer();

                if (minecraftserver != null && minecraftserver.isAnvilFileSet() && minecraftserver.isCommandBlockEnabled())
                {
                    try
                    {
                        this.lastOutput = null;
                        this.successCount = minecraftserver.getCommandManager().executeCommand(this, this.commandStored);
                    }
                    catch (Throwable throwable)
                    {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
                        crashreportcategory.addDetail("Command", new ICrashReportDetail<String>()
                        {
                            public String call() throws Exception
                            {
                                return CommandBlockBaseLogic.this.getCommand();
                            }
                        });
                        crashreportcategory.addDetail("Name", new ICrashReportDetail<String>()
                        {
                            public String call() throws Exception
                            {
                                return CommandBlockBaseLogic.this.getName();
                            }
                        });
                        throw new ReportedException(crashreport);
                    }
                }
                else
                {
                    this.successCount = 0;
                }

                if (this.updateLastExecution)
                {
                    this.lastExecution = worldIn.getTotalWorldTime();
                }
                else
                {
                    this.lastExecution = -1L;
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.customName;
    }

    public void setName(String name)
    {
        this.customName = name;
    }

    /**
     * Send a chat message to the CommandSender
     */
    public void sendMessage(ITextComponent component)
    {
        if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote)
        {
            this.lastOutput = (new TextComponentString("[" + TIMESTAMP_FORMAT.format(new Date()) + "] ")).appendSibling(component);
            this.updateCommand();
        }
    }

    /**
     * Returns true if the command sender should be sent feedback about executed commands
     */
    public boolean sendCommandFeedback()
    {
        MinecraftServer minecraftserver = this.getServer();
        return minecraftserver == null || !minecraftserver.isAnvilFileSet() || minecraftserver.worlds[0].getGameRules().getBoolean("commandBlockOutput");
    }

    public void setCommandStat(CommandResultStats.Type type, int amount)
    {
        this.resultStats.setCommandStatForSender(this.getServer(), this, type, amount);
    }

    public abstract void updateCommand();

    /**
     * Currently this returns 0 for the traditional command block, and 1 for the minecart command block
     */
    public abstract int getCommandBlockType();

    /**
     * Fills in information about the command block for the packet. X/Y/Z for the minecart version, and entityId for the
     * traditional version
     */
    public abstract void fillInInfo(ByteBuf buf);

    public void setLastOutput(@Nullable ITextComponent lastOutputMessage)
    {
        this.lastOutput = lastOutputMessage;
    }

    public void setTrackOutput(boolean shouldTrackOutput)
    {
        this.trackOutput = shouldTrackOutput;
    }

    public boolean shouldTrackOutput()
    {
        return this.trackOutput;
    }

    public boolean tryOpenEditCommandBlock(EntityPlayer playerIn)
    {
        if (!playerIn.canUseCommandBlock())
        {
            return false;
        }
        else
        {
            if (playerIn.getEntityWorld().isRemote)
            {
                playerIn.displayGuiEditCommandCart(this);
            }

            return true;
        }
    }

    public CommandResultStats getCommandResultStats()
    {
        return this.resultStats;
    }
}
