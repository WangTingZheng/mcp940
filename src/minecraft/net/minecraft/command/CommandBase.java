package net.minecraft.command;

import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Doubles;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.exception.ExceptionUtils;

public abstract class CommandBase implements ICommand
{
    private static ICommandListener commandListener;
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);

    /**
     * Convert a JsonParseException into a user-friendly exception
     */
    protected static SyntaxErrorException toSyntaxException(JsonParseException e)
    {
        Throwable throwable = ExceptionUtils.getRootCause(e);
        String s = "";

        if (throwable != null)
        {
            s = throwable.getMessage();

            if (s.contains("setLenient"))
            {
                s = s.substring(s.indexOf("to accept ") + 10);
            }
        }

        return new SyntaxErrorException("commands.tellraw.jsonException", new Object[] {s});
    }

    public static NBTTagCompound entityToNBT(Entity theEntity)
    {
        NBTTagCompound nbttagcompound = theEntity.writeToNBT(new NBTTagCompound());

        if (theEntity instanceof EntityPlayer)
        {
            ItemStack itemstack = ((EntityPlayer)theEntity).inventory.getCurrentItem();

            if (!itemstack.isEmpty())
            {
                nbttagcompound.setTag("SelectedItem", itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        return nbttagcompound;
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    public List<String> getAliases()
    {
        return Collections.<String>emptyList();
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return Collections.<String>emptyList();
    }

    public static int parseInt(String input) throws NumberInvalidException
    {
        try
        {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException var2)
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
        }
    }

    public static int parseInt(String input, int min) throws NumberInvalidException
    {
        return parseInt(input, min, Integer.MAX_VALUE);
    }

    public static int parseInt(String input, int min, int max) throws NumberInvalidException
    {
        int i = parseInt(input);

        if (i < min)
        {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {i, min});
        }
        else if (i > max)
        {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {i, max});
        }
        else
        {
            return i;
        }
    }

    public static long parseLong(String input) throws NumberInvalidException
    {
        try
        {
            return Long.parseLong(input);
        }
        catch (NumberFormatException var2)
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
        }
    }

    public static long parseLong(String input, long min, long max) throws NumberInvalidException
    {
        long i = parseLong(input);

        if (i < min)
        {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {i, min});
        }
        else if (i > max)
        {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {i, max});
        }
        else
        {
            return i;
        }
    }

    public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex, boolean centerBlock) throws NumberInvalidException
    {
        BlockPos blockpos = sender.getPosition();
        return new BlockPos(parseDouble((double)blockpos.getX(), args[startIndex], -30000000, 30000000, centerBlock), parseDouble((double)blockpos.getY(), args[startIndex + 1], 0, 256, false), parseDouble((double)blockpos.getZ(), args[startIndex + 2], -30000000, 30000000, centerBlock));
    }

    public static double parseDouble(String input) throws NumberInvalidException
    {
        try
        {
            double d0 = Double.parseDouble(input);

            if (!Doubles.isFinite(d0))
            {
                throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
            }
            else
            {
                return d0;
            }
        }
        catch (NumberFormatException var3)
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
        }
    }

    public static double parseDouble(String input, double min) throws NumberInvalidException
    {
        return parseDouble(input, min, Double.MAX_VALUE);
    }

    public static double parseDouble(String input, double min, double max) throws NumberInvalidException
    {
        double d0 = parseDouble(input);

        if (d0 < min)
        {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {String.format("%.2f", d0), String.format("%.2f", min)});
        }
        else if (d0 > max)
        {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {String.format("%.2f", d0), String.format("%.2f", max)});
        }
        else
        {
            return d0;
        }
    }

    public static boolean parseBoolean(String input) throws CommandException
    {
        if (!"true".equals(input) && !"1".equals(input))
        {
            if (!"false".equals(input) && !"0".equals(input))
            {
                throw new CommandException("commands.generic.boolean.invalid", new Object[] {input});
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns the given ICommandSender as a EntityPlayer or throw an exception.
     */
    public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException
    {
        if (sender instanceof EntityPlayerMP)
        {
            return (EntityPlayerMP)sender;
        }
        else
        {
            throw new PlayerNotFoundException("commands.generic.player.unspecified");
        }
    }

    public static List<EntityPlayerMP> getPlayers(MinecraftServer p_193513_0_, ICommandSender p_193513_1_, String p_193513_2_) throws CommandException
    {
        List<EntityPlayerMP> list = EntitySelector.getPlayers(p_193513_1_, p_193513_2_);
        return (List<EntityPlayerMP>)(list.isEmpty() ? Lists.newArrayList(getPlayer(p_193513_0_, (EntityPlayerMP)null, p_193513_2_)) : list);
    }

    public static EntityPlayerMP getPlayer(MinecraftServer server, ICommandSender sender, String target) throws PlayerNotFoundException, CommandException
    {
        return getPlayer(server, EntitySelector.matchOnePlayer(sender, target), target);
    }

    private static EntityPlayerMP getPlayer(MinecraftServer p_193512_0_, @Nullable EntityPlayerMP p_193512_1_, String p_193512_2_) throws CommandException
    {
        if (p_193512_1_ == null)
        {
            try
            {
                p_193512_1_ = p_193512_0_.getPlayerList().getPlayerByUUID(UUID.fromString(p_193512_2_));
            }
            catch (IllegalArgumentException var4)
            {
                ;
            }
        }

        if (p_193512_1_ == null)
        {
            p_193512_1_ = p_193512_0_.getPlayerList().getPlayerByUsername(p_193512_2_);
        }

        if (p_193512_1_ == null)
        {
            throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {p_193512_2_});
        }
        else
        {
            return p_193512_1_;
        }
    }

    public static Entity getEntity(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException
    {
        return getEntity(server, sender, target, Entity.class);
    }

    public static <T extends Entity> T getEntity(MinecraftServer server, ICommandSender sender, String target, Class <? extends T > targetClass) throws EntityNotFoundException, CommandException
    {
        Entity entity = EntitySelector.matchOneEntity(sender, target, targetClass);

        if (entity == null)
        {
            entity = server.getPlayerList().getPlayerByUsername(target);
        }

        if (entity == null)
        {
            try
            {
                UUID uuid = UUID.fromString(target);
                entity = server.getEntityFromUuid(uuid);

                if (entity == null)
                {
                    entity = server.getPlayerList().getPlayerByUUID(uuid);
                }
            }
            catch (IllegalArgumentException var6)
            {
                if (target.split("-").length == 5)
                {
                    throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[] {target});
                }
            }
        }

        if (entity != null && targetClass.isAssignableFrom(entity.getClass()))
        {
            return (T)entity;
        }
        else
        {
            throw new EntityNotFoundException(target);
        }
    }

    public static List<Entity> getEntityList(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException
    {
        return (List<Entity>)(EntitySelector.isSelector(target) ? EntitySelector.matchEntities(sender, target, Entity.class) : Lists.newArrayList(getEntity(server, sender, target)));
    }

    public static String getPlayerName(MinecraftServer server, ICommandSender sender, String target) throws PlayerNotFoundException, CommandException
    {
        try
        {
            return getPlayer(server, sender, target).getName();
        }
        catch (CommandException commandexception)
        {
            if (EntitySelector.isSelector(target))
            {
                throw commandexception;
            }
            else
            {
                return target;
            }
        }
    }

    public static String getEntityName(MinecraftServer server, ICommandSender sender, String target) throws EntityNotFoundException, CommandException
    {
        try
        {
            return getPlayer(server, sender, target).getName();
        }
        catch (PlayerNotFoundException var6)
        {
            try
            {
                return getEntity(server, sender, target).getCachedUniqueIdString();
            }
            catch (EntityNotFoundException entitynotfoundexception)
            {
                if (EntitySelector.isSelector(target))
                {
                    throw entitynotfoundexception;
                }
                else
                {
                    return target;
                }
            }
        }
    }

    public static ITextComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index) throws CommandException, PlayerNotFoundException
    {
        return getChatComponentFromNthArg(sender, args, index, false);
    }

    public static ITextComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException, CommandException
    {
        ITextComponent itextcomponent = new TextComponentString("");

        for (int i = index; i < args.length; ++i)
        {
            if (i > index)
            {
                itextcomponent.appendText(" ");
            }

            ITextComponent itextcomponent1 = new TextComponentString(args[i]);

            if (p_147176_3_)
            {
                ITextComponent itextcomponent2 = EntitySelector.matchEntitiesToTextComponent(sender, args[i]);

                if (itextcomponent2 == null)
                {
                    if (EntitySelector.isSelector(args[i]))
                    {
                        throw new PlayerNotFoundException("commands.generic.selector.notFound", new Object[] {args[i]});
                    }
                }
                else
                {
                    itextcomponent1 = itextcomponent2;
                }
            }

            itextcomponent.appendSibling(itextcomponent1);
        }

        return itextcomponent;
    }

    /**
     * Builds a string starting at startPos
     */
    public static String buildString(String[] args, int startPos)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = startPos; i < args.length; ++i)
        {
            if (i > startPos)
            {
                stringbuilder.append(" ");
            }

            String s = args[i];
            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static CommandBase.CoordinateArg parseCoordinate(double base, String selectorArg, boolean centerBlock) throws NumberInvalidException
    {
        return parseCoordinate(base, selectorArg, -30000000, 30000000, centerBlock);
    }

    public static CommandBase.CoordinateArg parseCoordinate(double base, String selectorArg, int min, int max, boolean centerBlock) throws NumberInvalidException
    {
        boolean flag = selectorArg.startsWith("~");

        if (flag && Double.isNaN(base))
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {base});
        }
        else
        {
            double d0 = 0.0D;

            if (!flag || selectorArg.length() > 1)
            {
                boolean flag1 = selectorArg.contains(".");

                if (flag)
                {
                    selectorArg = selectorArg.substring(1);
                }

                d0 += parseDouble(selectorArg);

                if (!flag1 && !flag && centerBlock)
                {
                    d0 += 0.5D;
                }
            }

            double d1 = d0 + (flag ? base : 0.0D);

            if (min != 0 || max != 0)
            {
                if (d1 < (double)min)
                {
                    throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {String.format("%.2f", d1), min});
                }

                if (d1 > (double)max)
                {
                    throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {String.format("%.2f", d1), max});
                }
            }

            return new CommandBase.CoordinateArg(d1, d0, flag);
        }
    }

    public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException
    {
        return parseDouble(base, input, -30000000, 30000000, centerBlock);
    }

    public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException
    {
        boolean flag = input.startsWith("~");

        if (flag && Double.isNaN(base))
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {base});
        }
        else
        {
            double d0 = flag ? base : 0.0D;

            if (!flag || input.length() > 1)
            {
                boolean flag1 = input.contains(".");

                if (flag)
                {
                    input = input.substring(1);
                }

                d0 += parseDouble(input);

                if (!flag1 && !flag && centerBlock)
                {
                    d0 += 0.5D;
                }
            }

            if (min != 0 || max != 0)
            {
                if (d0 < (double)min)
                {
                    throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {String.format("%.2f", d0), min});
                }

                if (d0 > (double)max)
                {
                    throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {String.format("%.2f", d0), max});
                }
            }

            return d0;
        }
    }

    /**
     * Gets the Item specified by the given text string.  First checks the item registry, then tries by parsing the
     * string as an integer ID (deprecated).  Warns the sender if we matched by parsing the ID.  Throws if the item
     * wasn't found.  Returns the item if it was found.
     */
    public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException
    {
        ResourceLocation resourcelocation = new ResourceLocation(id);
        Item item = Item.REGISTRY.getObject(resourcelocation);

        if (item == null)
        {
            throw new NumberInvalidException("commands.give.item.notFound", new Object[] {resourcelocation});
        }
        else
        {
            return item;
        }
    }

    /**
     * Gets the Block specified by the given text string.  First checks the block registry, then tries by parsing the
     * string as an integer ID (deprecated).  Warns the sender if we matched by parsing the ID.  Throws if the block
     * wasn't found.  Returns the block if it was found.
     */
    public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException
    {
        ResourceLocation resourcelocation = new ResourceLocation(id);

        if (!Block.REGISTRY.containsKey(resourcelocation))
        {
            throw new NumberInvalidException("commands.give.block.notFound", new Object[] {resourcelocation});
        }
        else
        {
            return Block.REGISTRY.getObject(resourcelocation);
        }
    }

    public static IBlockState convertArgToBlockState(Block p_190794_0_, String p_190794_1_) throws NumberInvalidException, InvalidBlockStateException
    {
        try
        {
            int i = Integer.parseInt(p_190794_1_);

            if (i < 0)
            {
                throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] {i, Integer.valueOf(0)});
            }
            else if (i > 15)
            {
                throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] {i, Integer.valueOf(15)});
            }
            else
            {
                return p_190794_0_.getStateFromMeta(Integer.parseInt(p_190794_1_));
            }
        }
        catch (RuntimeException var7)
        {
            try
            {
                Map < IProperty<?>, Comparable<? >> map = getBlockStatePropertyValueMap(p_190794_0_, p_190794_1_);
                IBlockState iblockstate = p_190794_0_.getDefaultState();

                for (Entry < IProperty<?>, Comparable<? >> entry : map.entrySet())
                {
                    iblockstate = getBlockState(iblockstate, entry.getKey(), entry.getValue());
                }

                return iblockstate;
            }
            catch (RuntimeException var6)
            {
                throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[] {p_190794_1_, Block.REGISTRY.getNameForObject(p_190794_0_)});
            }
        }
    }

    private static <T extends Comparable<T>> IBlockState getBlockState(IBlockState p_190793_0_, IProperty<T> p_190793_1_, Comparable<?> p_190793_2_)
    {
        return p_190793_0_.withProperty(p_190793_1_, (T)p_190793_2_);
    }

    public static Predicate<IBlockState> convertArgToBlockStatePredicate(final Block p_190791_0_, String p_190791_1_) throws InvalidBlockStateException
    {
        if (!"*".equals(p_190791_1_) && !"-1".equals(p_190791_1_))
        {
            try
            {
                final int i = Integer.parseInt(p_190791_1_);
                return new Predicate<IBlockState>()
                {
                    public boolean apply(@Nullable IBlockState p_apply_1_)
                    {
                        return i == p_apply_1_.getBlock().getMetaFromState(p_apply_1_);
                    }
                };
            }
            catch (RuntimeException var3)
            {
                final Map < IProperty<?>, Comparable<? >> map = getBlockStatePropertyValueMap(p_190791_0_, p_190791_1_);
                return new Predicate<IBlockState>()
                {
                    public boolean apply(@Nullable IBlockState p_apply_1_)
                    {
                        if (p_apply_1_ != null && p_190791_0_ == p_apply_1_.getBlock())
                        {
                            for (Entry < IProperty<?>, Comparable<? >> entry : map.entrySet())
                            {
                                if (!p_apply_1_.getValue(entry.getKey()).equals(entry.getValue()))
                                {
                                    return false;
                                }
                            }

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                };
            }
        }
        else
        {
            return Predicates.alwaysTrue();
        }
    }

    private static Map < IProperty<?>, Comparable<? >> getBlockStatePropertyValueMap(Block p_190795_0_, String p_190795_1_) throws InvalidBlockStateException
    {
        Map < IProperty<?>, Comparable<? >> map = Maps. < IProperty<?>, Comparable<? >> newHashMap();

        if ("default".equals(p_190795_1_))
        {
            return p_190795_0_.getDefaultState().getProperties();
        }
        else
        {
            BlockStateContainer blockstatecontainer = p_190795_0_.getBlockState();
            Iterator iterator = COMMA_SPLITTER.split(p_190795_1_).iterator();

            while (true)
            {
                if (!iterator.hasNext())
                {
                    return map;
                }

                String s = (String)iterator.next();
                Iterator<String> iterator1 = EQUAL_SPLITTER.split(s).iterator();

                if (!iterator1.hasNext())
                {
                    break;
                }

                IProperty<?> iproperty = blockstatecontainer.getProperty(iterator1.next());

                if (iproperty == null || !iterator1.hasNext())
                {
                    break;
                }

                Comparable<?> comparable = getValueHelper(iproperty, iterator1.next());

                if (comparable == null)
                {
                    break;
                }

                map.put(iproperty, comparable);
            }

            throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[] {p_190795_1_, Block.REGISTRY.getNameForObject(p_190795_0_)});
        }
    }

    @Nullable
    private static <T extends Comparable<T>> T getValueHelper(IProperty<T> p_190792_0_, String p_190792_1_)
    {
        return (T)(p_190792_0_.parseValue(p_190792_1_).orNull());
    }

    /**
     * Creates a linguistic series joining the input objects together.  Examples: 1) {} --> "",  2) {"Steve"} -->
     * "Steve",  3) {"Steve", "Phil"} --> "Steve and Phil",  4) {"Steve", "Phil", "Mark"} --> "Steve, Phil and Mark"
     */
    public static String joinNiceString(Object[] elements)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < elements.length; ++i)
        {
            String s = elements[i].toString();

            if (i > 0)
            {
                if (i == elements.length - 1)
                {
                    stringbuilder.append(" and ");
                }
                else
                {
                    stringbuilder.append(", ");
                }
            }

            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static ITextComponent join(List<ITextComponent> components)
    {
        ITextComponent itextcomponent = new TextComponentString("");

        for (int i = 0; i < components.size(); ++i)
        {
            if (i > 0)
            {
                if (i == components.size() - 1)
                {
                    itextcomponent.appendText(" and ");
                }
                else if (i > 0)
                {
                    itextcomponent.appendText(", ");
                }
            }

            itextcomponent.appendSibling(components.get(i));
        }

        return itextcomponent;
    }

    /**
     * Creates a linguistic series joining together the elements of the given collection.  Examples: 1) {} --> "",  2)
     * {"Steve"} --> "Steve",  3) {"Steve", "Phil"} --> "Steve and Phil",  4) {"Steve", "Phil", "Mark"} --> "Steve, Phil
     * and Mark"
     */
    public static String joinNiceStringFromCollection(Collection<String> strings)
    {
        return joinNiceString(strings.toArray(new String[strings.size()]));
    }

    public static List<String> getTabCompletionCoordinate(String[] inputArgs, int index, @Nullable BlockPos pos)
    {
        if (pos == null)
        {
            return Lists.newArrayList("~");
        }
        else
        {
            int i = inputArgs.length - 1;
            String s;

            if (i == index)
            {
                s = Integer.toString(pos.getX());
            }
            else if (i == index + 1)
            {
                s = Integer.toString(pos.getY());
            }
            else
            {
                if (i != index + 2)
                {
                    return Collections.<String>emptyList();
                }

                s = Integer.toString(pos.getZ());
            }

            return Lists.newArrayList(s);
        }
    }

    public static List<String> getTabCompletionCoordinateXZ(String[] inputArgs, int index, @Nullable BlockPos lookedPos)
    {
        if (lookedPos == null)
        {
            return Lists.newArrayList("~");
        }
        else
        {
            int i = inputArgs.length - 1;
            String s;

            if (i == index)
            {
                s = Integer.toString(lookedPos.getX());
            }
            else
            {
                if (i != index + 1)
                {
                    return Collections.<String>emptyList();
                }

                s = Integer.toString(lookedPos.getZ());
            }

            return Lists.newArrayList(s);
        }
    }

    /**
     * Returns true if the given substring is exactly equal to the start of the given string (case insensitive).
     */
    public static boolean doesStringStartWith(String original, String region)
    {
        return region.regionMatches(true, 0, original, 0, original.length());
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities)
    {
        return getListOfStringsMatchingLastWord(args, Arrays.asList(possibilities));
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] inputArgs, Collection<?> possibleCompletions)
    {
        String s = inputArgs[inputArgs.length - 1];
        List<String> list = Lists.<String>newArrayList();

        if (!possibleCompletions.isEmpty())
        {
            for (String s1 : Iterables.transform(possibleCompletions, Functions.toStringFunction()))
            {
                if (doesStringStartWith(s, s1))
                {
                    list.add(s1);
                }
            }

            if (list.isEmpty())
            {
                for (Object object : possibleCompletions)
                {
                    if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath()))
                    {
                        list.add(String.valueOf(object));
                    }
                }
            }
        }

        return list;
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }

    public static void notifyCommandListener(ICommandSender sender, ICommand command, String translationKey, Object... translationArgs)
    {
        notifyCommandListener(sender, command, 0, translationKey, translationArgs);
    }

    public static void notifyCommandListener(ICommandSender sender, ICommand command, int flags, String translationKey, Object... translationArgs)
    {
        if (commandListener != null)
        {
            commandListener.notifyListener(sender, command, flags, translationKey, translationArgs);
        }
    }

    /**
     * Sets the command listener responsable for notifying server operators when asked to by commands
     */
    public static void setCommandListener(ICommandListener listener)
    {
        commandListener = listener;
    }

    public int compareTo(ICommand p_compareTo_1_)
    {
        return this.getName().compareTo(p_compareTo_1_.getName());
    }

    public static class CoordinateArg
    {
        private final double result;
        private final double amount;
        private final boolean isRelative;

        protected CoordinateArg(double resultIn, double amountIn, boolean relative)
        {
            this.result = resultIn;
            this.amount = amountIn;
            this.isRelative = relative;
        }

        public double getResult()
        {
            return this.result;
        }

        public double getAmount()
        {
            return this.amount;
        }

        public boolean isRelative()
        {
            return this.isRelative;
        }
    }
}
