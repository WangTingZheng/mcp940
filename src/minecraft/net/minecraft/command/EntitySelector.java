package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public class EntitySelector
{
    /**
     * This matches the at-tokens introduced for command blocks, including their arguments, if any.
     */
    private static final Pattern TOKEN_PATTERN = Pattern.compile("^@([pares])(?:\\[([^ ]*)\\])?$");
    private static final Splitter COMMA_SPLITTER = Splitter.on(',').omitEmptyStrings();
    private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);
    private static final Set<String> VALID_ARGUMENTS = Sets.<String>newHashSet();
    private static final String ARGUMENT_RANGE_MAX = addArgument("r");
    private static final String ARGUMENT_RANGE_MIN = addArgument("rm");
    private static final String ARGUMENT_LEVEL_MAX = addArgument("l");
    private static final String ARGUMENT_LEVEL_MIN = addArgument("lm");
    private static final String ARGUMENT_COORDINATE_X = addArgument("x");
    private static final String ARGUMENT_COORDINATE_Y = addArgument("y");
    private static final String ARGUMENT_COORDINATE_Z = addArgument("z");
    private static final String ARGUMENT_DELTA_X = addArgument("dx");
    private static final String ARGUMENT_DELTA_Y = addArgument("dy");
    private static final String ARGUMENT_DELTA_Z = addArgument("dz");
    private static final String ARGUMENT_ROTX_MAX = addArgument("rx");
    private static final String ARGUMENT_ROTX_MIN = addArgument("rxm");
    private static final String ARGUMENT_ROTY_MAX = addArgument("ry");
    private static final String ARGUMENT_ROTY_MIN = addArgument("rym");
    private static final String ARGUMENT_COUNT = addArgument("c");
    private static final String ARGUMENT_MODE = addArgument("m");
    private static final String ARGUMENT_TEAM_NAME = addArgument("team");
    private static final String ARGUMENT_PLAYER_NAME = addArgument("name");
    private static final String ARGUMENT_ENTITY_TYPE = addArgument("type");
    private static final String ARGUMENT_ENTITY_TAG = addArgument("tag");
    private static final Predicate<String> IS_VALID_ARGUMENT = new Predicate<String>()
    {
        public boolean apply(@Nullable String p_apply_1_)
        {
            return p_apply_1_ != null && (EntitySelector.VALID_ARGUMENTS.contains(p_apply_1_) || p_apply_1_.length() > "score_".length() && p_apply_1_.startsWith("score_"));
        }
    };
    private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet(ARGUMENT_COORDINATE_X, ARGUMENT_COORDINATE_Y, ARGUMENT_COORDINATE_Z, ARGUMENT_DELTA_X, ARGUMENT_DELTA_Y, ARGUMENT_DELTA_Z, ARGUMENT_RANGE_MIN, ARGUMENT_RANGE_MAX);

    private static String addArgument(String argument)
    {
        VALID_ARGUMENTS.add(argument);
        return argument;
    }

    @Nullable

    /**
     * Returns the one player that matches the given at-token.  Returns null if more than one player matches.
     */
    public static EntityPlayerMP matchOnePlayer(ICommandSender sender, String token) throws CommandException
    {
        return (EntityPlayerMP)matchOneEntity(sender, token, EntityPlayerMP.class);
    }

    public static List<EntityPlayerMP> getPlayers(ICommandSender sender, String token) throws CommandException
    {
        return matchEntities(sender, token, EntityPlayerMP.class);
    }

    @Nullable
    public static <T extends Entity> T matchOneEntity(ICommandSender sender, String token, Class <? extends T > targetClass) throws CommandException
    {
        List<T> list = matchEntities(sender, token, targetClass);
        return (T)(list.size() == 1 ? (Entity)list.get(0) : null);
    }

    @Nullable
    public static ITextComponent matchEntitiesToTextComponent(ICommandSender sender, String token) throws CommandException
    {
        List<Entity> list = matchEntities(sender, token, Entity.class);

        if (list.isEmpty())
        {
            return null;
        }
        else
        {
            List<ITextComponent> list1 = Lists.<ITextComponent>newArrayList();

            for (Entity entity : list)
            {
                list1.add(entity.getDisplayName());
            }

            return CommandBase.join(list1);
        }
    }

    public static <T extends Entity> List<T> matchEntities(ICommandSender sender, String token, Class <? extends T > targetClass) throws CommandException
    {
        Matcher matcher = TOKEN_PATTERN.matcher(token);

        if (matcher.matches() && sender.canUseCommand(1, "@"))
        {
            Map<String, String> map = getArgumentMap(matcher.group(2));

            if (!isEntityTypeValid(sender, map))
            {
                return Collections.<T>emptyList();
            }
            else
            {
                String s = matcher.group(1);
                BlockPos blockpos = getBlockPosFromArguments(map, sender.getPosition());
                Vec3d vec3d = getPosFromArguments(map, sender.getPositionVector());
                List<World> list = getWorlds(sender, map);
                List<T> list1 = Lists.<T>newArrayList();

                for (World world : list)
                {
                    if (world != null)
                    {
                        List<Predicate<Entity>> list2 = Lists.<Predicate<Entity>>newArrayList();
                        list2.addAll(getTypePredicates(map, s));
                        list2.addAll(getXpLevelPredicates(map));
                        list2.addAll(getGamemodePredicates(map));
                        list2.addAll(getTeamPredicates(map));
                        list2.addAll(getScorePredicates(sender, map));
                        list2.addAll(getNamePredicates(map));
                        list2.addAll(getTagPredicates(map));
                        list2.addAll(getRadiusPredicates(map, vec3d));
                        list2.addAll(getRotationsPredicates(map));

                        if ("s".equalsIgnoreCase(s))
                        {
                            Entity entity = sender.getCommandSenderEntity();

                            if (entity != null && targetClass.isAssignableFrom(entity.getClass()))
                            {
                                if (map.containsKey(ARGUMENT_DELTA_X) || map.containsKey(ARGUMENT_DELTA_Y) || map.containsKey(ARGUMENT_DELTA_Z))
                                {
                                    int i = getInt(map, ARGUMENT_DELTA_X, 0);
                                    int j = getInt(map, ARGUMENT_DELTA_Y, 0);
                                    int k = getInt(map, ARGUMENT_DELTA_Z, 0);
                                    AxisAlignedBB axisalignedbb = getAABB(blockpos, i, j, k);

                                    if (!axisalignedbb.intersects(entity.getEntityBoundingBox()))
                                    {
                                        return Collections.<T>emptyList();
                                    }
                                }

                                for (Predicate<Entity> predicate : list2)
                                {
                                    if (!predicate.apply(entity))
                                    {
                                        return Collections.<T>emptyList();
                                    }
                                }

                                return Lists.newArrayList((T)entity);
                            }

                            return Collections.<T>emptyList();
                        }

                        list1.addAll(filterResults(map, targetClass, list2, s, world, blockpos));
                    }
                }

                return getEntitiesFromPredicates(list1, map, sender, targetClass, s, vec3d);
            }
        }
        else
        {
            return Collections.<T>emptyList();
        }
    }

    private static List<World> getWorlds(ICommandSender sender, Map<String, String> argumentMap)
    {
        List<World> list = Lists.<World>newArrayList();

        if (hasArgument(argumentMap))
        {
            list.add(sender.getEntityWorld());
        }
        else
        {
            Collections.addAll(list, sender.getServer().worlds);
        }

        return list;
    }

    private static <T extends Entity> boolean isEntityTypeValid(ICommandSender commandSender, Map<String, String> params)
    {
        String s = getArgument(params, ARGUMENT_ENTITY_TYPE);

        if (s == null)
        {
            return true;
        }
        else
        {
            ResourceLocation resourcelocation = new ResourceLocation(s.startsWith("!") ? s.substring(1) : s);

            if (EntityList.isRegistered(resourcelocation))
            {
                return true;
            }
            else
            {
                TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.generic.entity.invalidType", new Object[] {resourcelocation});
                textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
                commandSender.sendMessage(textcomponenttranslation);
                return false;
            }
        }
    }

    private static List<Predicate<Entity>> getTypePredicates(Map<String, String> params, String type)
    {
        String s = getArgument(params, ARGUMENT_ENTITY_TYPE);

        if (s == null || !type.equals("e") && !type.equals("r") && !type.equals("s"))
        {
            return !type.equals("e") && !type.equals("s") ? Collections.singletonList(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    return p_apply_1_ instanceof EntityPlayer;
                }
            }) : Collections.emptyList();
        }
        else
        {
            final boolean flag = s.startsWith("!");
            final ResourceLocation resourcelocation = new ResourceLocation(flag ? s.substring(1) : s);
            return Collections.singletonList(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    return EntityList.isMatchingName(p_apply_1_, resourcelocation) != flag;
                }
            });
        }
    }

    private static List<Predicate<Entity>> getXpLevelPredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();
        final int i = getInt(params, ARGUMENT_LEVEL_MIN, -1);
        final int j = getInt(params, ARGUMENT_LEVEL_MAX, -1);

        if (i > -1 || j > -1)
        {
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (!(p_apply_1_ instanceof EntityPlayerMP))
                    {
                        return false;
                    }
                    else
                    {
                        EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
                        return (i <= -1 || entityplayermp.experienceLevel >= i) && (j <= -1 || entityplayermp.experienceLevel <= j);
                    }
                }
            });
        }

        return list;
    }

    private static List<Predicate<Entity>> getGamemodePredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();
        String s = getArgument(params, ARGUMENT_MODE);

        if (s == null)
        {
            return list;
        }
        else
        {
            final boolean flag = s.startsWith("!");

            if (flag)
            {
                s = s.substring(1);
            }

            GameType gametype;

            try
            {
                int i = Integer.parseInt(s);
                gametype = GameType.parseGameTypeWithDefault(i, GameType.NOT_SET);
            }
            catch (Throwable var6)
            {
                gametype = GameType.parseGameTypeWithDefault(s, GameType.NOT_SET);
            }

            final GameType type = gametype;
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (!(p_apply_1_ instanceof EntityPlayerMP))
                    {
                        return false;
                    }
                    else
                    {
                        EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
                        GameType gametype1 = entityplayermp.interactionManager.getGameType();
                        return flag ? gametype1 != type : gametype1 == type;
                    }
                }
            });
            return list;
        }
    }

    private static List<Predicate<Entity>> getTeamPredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();
        String s = getArgument(params, ARGUMENT_TEAM_NAME);
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        if (s != null)
        {
            final String s_f_ = s;
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (!(p_apply_1_ instanceof EntityLivingBase))
                    {
                        return false;
                    }
                    else
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
                        Team team = entitylivingbase.getTeam();
                        String s1 = team == null ? "" : team.getName();
                        return s1.equals(s_f_) != flag;
                    }
                }
            });
        }

        return list;
    }

    private static List<Predicate<Entity>> getScorePredicates(final ICommandSender sender, Map<String, String> params)
    {
        final Map<String, Integer> map = getScoreMap(params);
        return (map.isEmpty() ? Collections.emptyList() : Lists.newArrayList(new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                if (p_apply_1_ == null)
                {
                    return false;
                }
                else
                {
                    Scoreboard scoreboard = sender.getServer().getWorld(0).getScoreboard();

                    for (Entry<String, Integer> entry : map.entrySet())
                    {
                        String s = entry.getKey();
                        boolean flag = false;

                        if (s.endsWith("_min") && s.length() > 4)
                        {
                            flag = true;
                            s = s.substring(0, s.length() - 4);
                        }

                        ScoreObjective scoreobjective = scoreboard.getObjective(s);

                        if (scoreobjective == null)
                        {
                            return false;
                        }

                        String s1 = p_apply_1_ instanceof EntityPlayerMP ? p_apply_1_.getName() : p_apply_1_.getCachedUniqueIdString();

                        if (!scoreboard.entityHasObjective(s1, scoreobjective))
                        {
                            return false;
                        }

                        Score score = scoreboard.getOrCreateScore(s1, scoreobjective);
                        int i = score.getScorePoints();

                        if (i < ((Integer)entry.getValue()).intValue() && flag)
                        {
                            return false;
                        }

                        if (i > ((Integer)entry.getValue()).intValue() && !flag)
                        {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }));
    }

    private static List<Predicate<Entity>> getNamePredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();
        String s = getArgument(params, ARGUMENT_PLAYER_NAME);
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        if (s != null)
        {
            final String s_f_ = s;
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    return p_apply_1_ != null && p_apply_1_.getName().equals(s_f_) != flag;
                }
            });
        }

        return list;
    }

    private static List<Predicate<Entity>> getTagPredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();
        String s = getArgument(params, ARGUMENT_ENTITY_TAG);
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        if (s != null)
        {
            final String s_f_ = s;
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (p_apply_1_ == null)
                    {
                        return false;
                    }
                    else if ("".equals(s_f_))
                    {
                        return p_apply_1_.getTags().isEmpty() != flag;
                    }
                    else
                    {
                        return p_apply_1_.getTags().contains(s_f_) != flag;
                    }
                }
            });
        }

        return list;
    }

    private static List<Predicate<Entity>> getRadiusPredicates(Map<String, String> params, final Vec3d pos)
    {
        double d0 = (double)getInt(params, ARGUMENT_RANGE_MIN, -1);
        double d1 = (double)getInt(params, ARGUMENT_RANGE_MAX, -1);
        final boolean flag = d0 < -0.5D;
        final boolean flag1 = d1 < -0.5D;

        if (flag && flag1)
        {
            return Collections.<Predicate<Entity>>emptyList();
        }
        else
        {
            double d2 = Math.max(d0, 1.0E-4D);
            final double d3 = d2 * d2;
            double d4 = Math.max(d1, 1.0E-4D);
            final double d5 = d4 * d4;
            return Lists.newArrayList(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (p_apply_1_ == null)
                    {
                        return false;
                    }
                    else
                    {
                        double d6 = pos.squareDistanceTo(p_apply_1_.posX, p_apply_1_.posY, p_apply_1_.posZ);
                        return (flag || d6 >= d3) && (flag1 || d6 <= d5);
                    }
                }
            });
        }
    }

    private static List<Predicate<Entity>> getRotationsPredicates(Map<String, String> params)
    {
        List<Predicate<Entity>> list = Lists.<Predicate<Entity>>newArrayList();

        if (params.containsKey(ARGUMENT_ROTY_MIN) || params.containsKey(ARGUMENT_ROTY_MAX))
        {
            final int i = MathHelper.clampAngle(getInt(params, ARGUMENT_ROTY_MIN, 0));
            final int j = MathHelper.clampAngle(getInt(params, ARGUMENT_ROTY_MAX, 359));
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (p_apply_1_ == null)
                    {
                        return false;
                    }
                    else
                    {
                        int i1 = MathHelper.clampAngle(MathHelper.floor(p_apply_1_.rotationYaw));

                        if (i > j)
                        {
                            return i1 >= i || i1 <= j;
                        }
                        else
                        {
                            return i1 >= i && i1 <= j;
                        }
                    }
                }
            });
        }

        if (params.containsKey(ARGUMENT_ROTX_MIN) || params.containsKey(ARGUMENT_ROTX_MAX))
        {
            final int k = MathHelper.clampAngle(getInt(params, ARGUMENT_ROTX_MIN, 0));
            final int l = MathHelper.clampAngle(getInt(params, ARGUMENT_ROTX_MAX, 359));
            list.add(new Predicate<Entity>()
            {
                public boolean apply(@Nullable Entity p_apply_1_)
                {
                    if (p_apply_1_ == null)
                    {
                        return false;
                    }
                    else
                    {
                        int i1 = MathHelper.clampAngle(MathHelper.floor(p_apply_1_.rotationPitch));

                        if (k > l)
                        {
                            return i1 >= k || i1 <= l;
                        }
                        else
                        {
                            return i1 >= k && i1 <= l;
                        }
                    }
                }
            });
        }

        return list;
    }

    private static <T extends Entity> List<T> filterResults(Map<String, String> params, Class <? extends T > entityClass, List<Predicate<Entity>> inputList, String type, World worldIn, BlockPos position)
    {
        List<T> list = Lists.<T>newArrayList();
        String s = getArgument(params, ARGUMENT_ENTITY_TYPE);
        s = s != null && s.startsWith("!") ? s.substring(1) : s;
        boolean flag = !type.equals("e");
        boolean flag1 = type.equals("r") && s != null;
        int i = getInt(params, ARGUMENT_DELTA_X, 0);
        int j = getInt(params, ARGUMENT_DELTA_Y, 0);
        int k = getInt(params, ARGUMENT_DELTA_Z, 0);
        int l = getInt(params, ARGUMENT_RANGE_MAX, -1);
        Predicate<Entity> predicate = Predicates.and(inputList);
        Predicate<Entity> predicate1 = Predicates.<Entity> and (EntitySelectors.IS_ALIVE, predicate);

        if (!params.containsKey(ARGUMENT_DELTA_X) && !params.containsKey(ARGUMENT_DELTA_Y) && !params.containsKey(ARGUMENT_DELTA_Z))
        {
            if (l >= 0)
            {
                AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((double)(position.getX() - l), (double)(position.getY() - l), (double)(position.getZ() - l), (double)(position.getX() + l + 1), (double)(position.getY() + l + 1), (double)(position.getZ() + l + 1));

                if (flag && !flag1)
                {
                    list.addAll(worldIn.getPlayers(entityClass, predicate1));
                }
                else
                {
                    list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb1, predicate1));
                }
            }
            else if (type.equals("a"))
            {
                list.addAll(worldIn.getPlayers(entityClass, predicate));
            }
            else if (!type.equals("p") && (!type.equals("r") || flag1))
            {
                list.addAll(worldIn.getEntities(entityClass, predicate1));
            }
            else
            {
                list.addAll(worldIn.getPlayers(entityClass, predicate1));
            }
        }
        else
        {
            final AxisAlignedBB axisalignedbb = getAABB(position, i, j, k);

            if (flag && !flag1)
            {
                Predicate<Entity> predicate2 = new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_ != null && axisalignedbb.intersects(p_apply_1_.getEntityBoundingBox());
                    }
                };
                list.addAll(worldIn.getPlayers(entityClass, Predicates.and(predicate1, predicate2)));
            }
            else
            {
                list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb, predicate1));
            }
        }

        return list;
    }

    private static <T extends Entity> List<T> getEntitiesFromPredicates(List<T> matchingEntities, Map<String, String> params, ICommandSender sender, Class <? extends T > targetClass, String type, final Vec3d pos)
    {
        int i = getInt(params, ARGUMENT_COUNT, !type.equals("a") && !type.equals("e") ? 1 : 0);

        if (!type.equals("p") && !type.equals("a") && !type.equals("e"))
        {
            if (type.equals("r"))
            {
                Collections.shuffle(matchingEntities);
            }
        }
        else
        {
            Collections.sort(matchingEntities, new Comparator<Entity>()
            {
                public int compare(Entity p_compare_1_, Entity p_compare_2_)
                {
                    return ComparisonChain.start().compare(p_compare_1_.getDistanceSq(pos.x, pos.y, pos.z), p_compare_2_.getDistanceSq(pos.x, pos.y, pos.z)).result();
                }
            });
        }

        Entity entity = sender.getCommandSenderEntity();

        if (entity != null && targetClass.isAssignableFrom(entity.getClass()) && i == 1 && matchingEntities.contains(entity) && !"r".equals(type))
        {
            matchingEntities = Lists.newArrayList((T)entity);
        }

        if (i != 0)
        {
            if (i < 0)
            {
                Collections.reverse(matchingEntities);
            }

            matchingEntities = matchingEntities.subList(0, Math.min(Math.abs(i), matchingEntities.size()));
        }

        return matchingEntities;
    }

    private static AxisAlignedBB getAABB(BlockPos pos, int x, int y, int z)
    {
        boolean flag = x < 0;
        boolean flag1 = y < 0;
        boolean flag2 = z < 0;
        int i = pos.getX() + (flag ? x : 0);
        int j = pos.getY() + (flag1 ? y : 0);
        int k = pos.getZ() + (flag2 ? z : 0);
        int l = pos.getX() + (flag ? 0 : x) + 1;
        int i1 = pos.getY() + (flag1 ? 0 : y) + 1;
        int j1 = pos.getZ() + (flag2 ? 0 : z) + 1;
        return new AxisAlignedBB((double)i, (double)j, (double)k, (double)l, (double)i1, (double)j1);
    }

    private static BlockPos getBlockPosFromArguments(Map<String, String> params, BlockPos pos)
    {
        return new BlockPos(getInt(params, ARGUMENT_COORDINATE_X, pos.getX()), getInt(params, ARGUMENT_COORDINATE_Y, pos.getY()), getInt(params, ARGUMENT_COORDINATE_Z, pos.getZ()));
    }

    private static Vec3d getPosFromArguments(Map<String, String> params, Vec3d pos)
    {
        return new Vec3d(getCoordinate(params, ARGUMENT_COORDINATE_X, pos.x, true), getCoordinate(params, ARGUMENT_COORDINATE_Y, pos.y, false), getCoordinate(params, ARGUMENT_COORDINATE_Z, pos.z, true));
    }

    private static double getCoordinate(Map<String, String> params, String key, double defaultD, boolean offset)
    {
        return params.containsKey(key) ? (double)MathHelper.getInt(params.get(key), MathHelper.floor(defaultD)) + (offset ? 0.5D : 0.0D) : defaultD;
    }

    private static boolean hasArgument(Map<String, String> params)
    {
        for (String s : WORLD_BINDING_ARGS)
        {
            if (params.containsKey(s))
            {
                return true;
            }
        }

        return false;
    }

    private static int getInt(Map<String, String> params, String key, int defaultI)
    {
        return params.containsKey(key) ? MathHelper.getInt(params.get(key), defaultI) : defaultI;
    }

    @Nullable
    private static String getArgument(Map<String, String> params, String key)
    {
        return params.get(key);
    }

    public static Map<String, Integer> getScoreMap(Map<String, String> params)
    {
        Map<String, Integer> map = Maps.<String, Integer>newHashMap();

        for (String s : params.keySet())
        {
            if (s.startsWith("score_") && s.length() > "score_".length())
            {
                map.put(s.substring("score_".length()), Integer.valueOf(MathHelper.getInt(params.get(s), 1)));
            }
        }

        return map;
    }

    /**
     * Returns whether the given pattern can match more than one player.
     */
    public static boolean matchesMultiplePlayers(String selectorStr) throws CommandException
    {
        Matcher matcher = TOKEN_PATTERN.matcher(selectorStr);

        if (!matcher.matches())
        {
            return false;
        }
        else
        {
            Map<String, String> map = getArgumentMap(matcher.group(2));
            String s = matcher.group(1);
            int i = !"a".equals(s) && !"e".equals(s) ? 1 : 0;
            return getInt(map, ARGUMENT_COUNT, i) != 1;
        }
    }

    /**
     * Returns whether the given string represents a selector.
     */
    public static boolean isSelector(String selectorStr)
    {
        return TOKEN_PATTERN.matcher(selectorStr).matches();
    }

    private static Map<String, String> getArgumentMap(@Nullable String argumentString) throws CommandException
    {
        Map<String, String> map = Maps.<String, String>newHashMap();

        if (argumentString == null)
        {
            return map;
        }
        else
        {
            for (String s : COMMA_SPLITTER.split(argumentString))
            {
                Iterator<String> iterator = EQUAL_SPLITTER.split(s).iterator();
                String s1 = iterator.next();

                if (!IS_VALID_ARGUMENT.apply(s1))
                {
                    throw new CommandException("commands.generic.selector_argument", new Object[] {s});
                }

                map.put(s1, iterator.hasNext() ? (String)iterator.next() : "");
            }

            return map;
        }
    }
}
