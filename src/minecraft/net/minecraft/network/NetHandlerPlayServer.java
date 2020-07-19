package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRecipePlacement;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable
{
    private static final Logger LOGGER = LogManager.getLogger();
    public final NetworkManager netManager;
    private final MinecraftServer serverController;
    public EntityPlayerMP player;
    private int networkTickCount;
    private int keepAliveId;
    private long lastPingTime;
    private long lastSentPingPacket;

    /**
     * Incremented by 20 each time a user sends a chat message, decreased by one every tick. Non-ops kicked when over
     * 200
     */
    private int chatSpamThresholdCount;
    private int itemDropThreshold;
    private final IntHashMap<Short> pendingTransactions = new IntHashMap<Short>();
    private double firstGoodX;
    private double firstGoodY;
    private double firstGoodZ;
    private double lastGoodX;
    private double lastGoodY;
    private double lastGoodZ;
    private Entity lowestRiddenEnt;
    private double lowestRiddenX;
    private double lowestRiddenY;
    private double lowestRiddenZ;
    private double lowestRiddenX1;
    private double lowestRiddenY1;
    private double lowestRiddenZ1;
    private Vec3d targetPos;
    private int teleportId;
    private int lastPositionUpdate;
    private boolean floating;

    /**
     * Used to keep track of how the player is floating while gamerules should prevent that. Surpassing 80 ticks means
     * kick
     */
    private int floatingTickCount;
    private boolean vehicleFloating;

    /**
     * Used to keep track of how long the player is floating in a vehicle. Surpassing 80 means a kick
     */
    private int vehicleFloatingTickCount;
    private int movePacketCounter;
    private int lastMovePacketCounter;

    public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn)
    {
        this.serverController = server;
        this.netManager = networkManagerIn;
        networkManagerIn.setNetHandler(this);
        this.player = playerIn;
        playerIn.connection = this;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        this.captureCurrentPosition();
        this.player.onUpdateEntity();
        this.player.setPositionAndRotation(this.firstGoodX, this.firstGoodY, this.firstGoodZ, this.player.rotationYaw, this.player.rotationPitch);
        ++this.networkTickCount;
        this.lastMovePacketCounter = this.movePacketCounter;

        if (this.floating)
        {
            if (++this.floatingTickCount > 80)
            {
                LOGGER.warn("{} was kicked for floating too long!", (Object)this.player.getName());
                this.disconnect(new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
                return;
            }
        }
        else
        {
            this.floating = false;
            this.floatingTickCount = 0;
        }

        this.lowestRiddenEnt = this.player.getLowestRidingEntity();

        if (this.lowestRiddenEnt != this.player && this.lowestRiddenEnt.getControllingPassenger() == this.player)
        {
            this.lowestRiddenX = this.lowestRiddenEnt.posX;
            this.lowestRiddenY = this.lowestRiddenEnt.posY;
            this.lowestRiddenZ = this.lowestRiddenEnt.posZ;
            this.lowestRiddenX1 = this.lowestRiddenEnt.posX;
            this.lowestRiddenY1 = this.lowestRiddenEnt.posY;
            this.lowestRiddenZ1 = this.lowestRiddenEnt.posZ;

            if (this.vehicleFloating && this.player.getLowestRidingEntity().getControllingPassenger() == this.player)
            {
                if (++this.vehicleFloatingTickCount > 80)
                {
                    LOGGER.warn("{} was kicked for floating a vehicle too long!", (Object)this.player.getName());
                    this.disconnect(new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
                    return;
                }
            }
            else
            {
                this.vehicleFloating = false;
                this.vehicleFloatingTickCount = 0;
            }
        }
        else
        {
            this.lowestRiddenEnt = null;
            this.vehicleFloating = false;
            this.vehicleFloatingTickCount = 0;
        }

        this.serverController.profiler.startSection("keepAlive");

        if ((long)this.networkTickCount - this.lastSentPingPacket > 40L)
        {
            this.lastSentPingPacket = (long)this.networkTickCount;
            this.lastPingTime = this.currentTimeMillis();
            this.keepAliveId = (int)this.lastPingTime;
            this.sendPacket(new SPacketKeepAlive(this.keepAliveId));
        }

        this.serverController.profiler.endSection();

        if (this.chatSpamThresholdCount > 0)
        {
            --this.chatSpamThresholdCount;
        }

        if (this.itemDropThreshold > 0)
        {
            --this.itemDropThreshold;
        }

        if (this.player.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.player.getLastActiveTime() > (long)(this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60))
        {
            this.disconnect(new TextComponentTranslation("multiplayer.disconnect.idling", new Object[0]));
        }
    }

    private void captureCurrentPosition()
    {
        this.firstGoodX = this.player.posX;
        this.firstGoodY = this.player.posY;
        this.firstGoodZ = this.player.posZ;
        this.lastGoodX = this.player.posX;
        this.lastGoodY = this.player.posY;
        this.lastGoodZ = this.player.posZ;
    }

    public NetworkManager getNetworkManager()
    {
        return this.netManager;
    }

    /**
     * Disconnect the player with a specified reason
     */
    public void disconnect(final ITextComponent textComponent)
    {
        this.netManager.sendPacket(new SPacketDisconnect(textComponent), new GenericFutureListener < Future <? super Void >> ()
        {
            public void operationComplete(Future <? super Void > p_operationComplete_1_) throws Exception
            {
                NetHandlerPlayServer.this.netManager.closeChannel(textComponent);
            }
        });
        this.netManager.disableAutoRead();
        Futures.getUnchecked(this.serverController.addScheduledTask(new Runnable()
        {
            public void run()
            {
                NetHandlerPlayServer.this.netManager.checkDisconnected();
            }
        }));
    }

    /**
     * Processes player movement input. Includes walking, strafing, jumping, sneaking; excludes riding and toggling
     * flying/sprinting
     */
    public void processInput(CPacketInput packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(), packetIn.isSneaking());
    }

    private static boolean isMovePlayerPacketInvalid(CPacketPlayer packetIn)
    {
        if (Doubles.isFinite(packetIn.getX(0.0D)) && Doubles.isFinite(packetIn.getY(0.0D)) && Doubles.isFinite(packetIn.getZ(0.0D)) && Floats.isFinite(packetIn.getPitch(0.0F)) && Floats.isFinite(packetIn.getYaw(0.0F)))
        {
            return Math.abs(packetIn.getX(0.0D)) > 3.0E7D || Math.abs(packetIn.getY(0.0D)) > 3.0E7D || Math.abs(packetIn.getZ(0.0D)) > 3.0E7D;
        }
        else
        {
            return true;
        }
    }

    private static boolean isMoveVehiclePacketInvalid(CPacketVehicleMove packetIn)
    {
        return !Doubles.isFinite(packetIn.getX()) || !Doubles.isFinite(packetIn.getY()) || !Doubles.isFinite(packetIn.getZ()) || !Floats.isFinite(packetIn.getPitch()) || !Floats.isFinite(packetIn.getYaw());
    }

    public void processVehicleMove(CPacketVehicleMove packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (isMoveVehiclePacketInvalid(packetIn))
        {
            this.disconnect(new TextComponentTranslation("multiplayer.disconnect.invalid_vehicle_movement", new Object[0]));
        }
        else
        {
            Entity entity = this.player.getLowestRidingEntity();

            if (entity != this.player && entity.getControllingPassenger() == this.player && entity == this.lowestRiddenEnt)
            {
                WorldServer worldserver = this.player.getServerWorld();
                double d0 = entity.posX;
                double d1 = entity.posY;
                double d2 = entity.posZ;
                double d3 = packetIn.getX();
                double d4 = packetIn.getY();
                double d5 = packetIn.getZ();
                float f = packetIn.getYaw();
                float f1 = packetIn.getPitch();
                double d6 = d3 - this.lowestRiddenX;
                double d7 = d4 - this.lowestRiddenY;
                double d8 = d5 - this.lowestRiddenZ;
                double d9 = entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ;
                double d10 = d6 * d6 + d7 * d7 + d8 * d8;

                if (d10 - d9 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(entity.getName())))
                {
                    LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", entity.getName(), this.player.getName(), Double.valueOf(d6), Double.valueOf(d7), Double.valueOf(d8));
                    this.netManager.sendPacket(new SPacketMoveVehicle(entity));
                    return;
                }

                boolean flag = worldserver.getCollisionBoxes(entity, entity.getEntityBoundingBox().shrink(0.0625D)).isEmpty();
                d6 = d3 - this.lowestRiddenX1;
                d7 = d4 - this.lowestRiddenY1 - 1.0E-6D;
                d8 = d5 - this.lowestRiddenZ1;
                entity.move(MoverType.PLAYER, d6, d7, d8);
                double d11 = d7;
                d6 = d3 - entity.posX;
                d7 = d4 - entity.posY;

                if (d7 > -0.5D || d7 < 0.5D)
                {
                    d7 = 0.0D;
                }

                d8 = d5 - entity.posZ;
                d10 = d6 * d6 + d7 * d7 + d8 * d8;
                boolean flag1 = false;

                if (d10 > 0.0625D)
                {
                    flag1 = true;
                    LOGGER.warn("{} moved wrongly!", (Object)entity.getName());
                }

                entity.setPositionAndRotation(d3, d4, d5, f, f1);
                boolean flag2 = worldserver.getCollisionBoxes(entity, entity.getEntityBoundingBox().shrink(0.0625D)).isEmpty();

                if (flag && (flag1 || !flag2))
                {
                    entity.setPositionAndRotation(d0, d1, d2, f, f1);
                    this.netManager.sendPacket(new SPacketMoveVehicle(entity));
                    return;
                }

                this.serverController.getPlayerList().serverUpdateMovingPlayer(this.player);
                this.player.addMovementStat(this.player.posX - d0, this.player.posY - d1, this.player.posZ - d2);
                this.vehicleFloating = d11 >= -0.03125D && !this.serverController.isFlightAllowed() && !worldserver.checkBlockCollision(entity.getEntityBoundingBox().grow(0.0625D).expand(0.0D, -0.55D, 0.0D));
                this.lowestRiddenX1 = entity.posX;
                this.lowestRiddenY1 = entity.posY;
                this.lowestRiddenZ1 = entity.posZ;
            }
        }
    }

    public void processConfirmTeleport(CPacketConfirmTeleport packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (packetIn.getTeleportId() == this.teleportId)
        {
            this.player.setPositionAndRotation(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.player.rotationYaw, this.player.rotationPitch);

            if (this.player.isInvulnerableDimensionChange())
            {
                this.lastGoodX = this.targetPos.x;
                this.lastGoodY = this.targetPos.y;
                this.lastGoodZ = this.targetPos.z;
                this.player.clearInvulnerableDimensionChange();
            }

            this.targetPos = null;
        }
    }

    public void handleRecipeBookUpdate(CPacketRecipeInfo p_191984_1_)
    {
        PacketThreadUtil.checkThreadAndEnqueue(p_191984_1_, this, this.player.getServerWorld());

        if (p_191984_1_.getPurpose() == CPacketRecipeInfo.Purpose.SHOWN)
        {
            this.player.getRecipeBook().setRecipeSeen(p_191984_1_.getRecipe());
        }
        else if (p_191984_1_.getPurpose() == CPacketRecipeInfo.Purpose.SETTINGS)
        {
            this.player.getRecipeBook().setGuiOpen(p_191984_1_.isGuiOpen());
            this.player.getRecipeBook().setFilteringCraftable(p_191984_1_.isFilteringCraftable());
        }
    }

    public void handleSeenAdvancements(CPacketSeenAdvancements p_194027_1_)
    {
        PacketThreadUtil.checkThreadAndEnqueue(p_194027_1_, this, this.player.getServerWorld());

        if (p_194027_1_.getAction() == CPacketSeenAdvancements.Action.OPENED_TAB)
        {
            ResourceLocation resourcelocation = p_194027_1_.getTab();
            Advancement advancement = this.serverController.getAdvancementManager().getAdvancement(resourcelocation);

            if (advancement != null)
            {
                this.player.getAdvancements().setSelectedTab(advancement);
            }
        }
    }

    /**
     * Processes clients perspective on player positioning and/or orientation
     */
    public void processPlayer(CPacketPlayer packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (isMovePlayerPacketInvalid(packetIn))
        {
            this.disconnect(new TextComponentTranslation("multiplayer.disconnect.invalid_player_movement", new Object[0]));
        }
        else
        {
            WorldServer worldserver = this.serverController.getWorld(this.player.dimension);

            if (!this.player.queuedEndExit)
            {
                if (this.networkTickCount == 0)
                {
                    this.captureCurrentPosition();
                }

                if (this.targetPos != null)
                {
                    if (this.networkTickCount - this.lastPositionUpdate > 20)
                    {
                        this.lastPositionUpdate = this.networkTickCount;
                        this.setPlayerLocation(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.player.rotationYaw, this.player.rotationPitch);
                    }
                }
                else
                {
                    this.lastPositionUpdate = this.networkTickCount;

                    if (this.player.isRiding())
                    {
                        this.player.setPositionAndRotation(this.player.posX, this.player.posY, this.player.posZ, packetIn.getYaw(this.player.rotationYaw), packetIn.getPitch(this.player.rotationPitch));
                        this.serverController.getPlayerList().serverUpdateMovingPlayer(this.player);
                    }
                    else
                    {
                        double d0 = this.player.posX;
                        double d1 = this.player.posY;
                        double d2 = this.player.posZ;
                        double d3 = this.player.posY;
                        double d4 = packetIn.getX(this.player.posX);
                        double d5 = packetIn.getY(this.player.posY);
                        double d6 = packetIn.getZ(this.player.posZ);
                        float f = packetIn.getYaw(this.player.rotationYaw);
                        float f1 = packetIn.getPitch(this.player.rotationPitch);
                        double d7 = d4 - this.firstGoodX;
                        double d8 = d5 - this.firstGoodY;
                        double d9 = d6 - this.firstGoodZ;
                        double d10 = this.player.motionX * this.player.motionX + this.player.motionY * this.player.motionY + this.player.motionZ * this.player.motionZ;
                        double d11 = d7 * d7 + d8 * d8 + d9 * d9;

                        if (this.player.isPlayerSleeping())
                        {
                            if (d11 > 1.0D)
                            {
                                this.setPlayerLocation(this.player.posX, this.player.posY, this.player.posZ, packetIn.getYaw(this.player.rotationYaw), packetIn.getPitch(this.player.rotationPitch));
                            }
                        }
                        else
                        {
                            ++this.movePacketCounter;
                            int i = this.movePacketCounter - this.lastMovePacketCounter;

                            if (i > 5)
                            {
                                LOGGER.debug("{} is sending move packets too frequently ({} packets since last tick)", this.player.getName(), Integer.valueOf(i));
                                i = 1;
                            }

                            if (!this.player.isInvulnerableDimensionChange() && (!this.player.getServerWorld().getGameRules().getBoolean("disableElytraMovementCheck") || !this.player.isElytraFlying()))
                            {
                                float f2 = this.player.isElytraFlying() ? 300.0F : 100.0F;

                                if (d11 - d10 > (double)(f2 * (float)i) && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.player.getName())))
                                {
                                    LOGGER.warn("{} moved too quickly! {},{},{}", this.player.getName(), Double.valueOf(d7), Double.valueOf(d8), Double.valueOf(d9));
                                    this.setPlayerLocation(this.player.posX, this.player.posY, this.player.posZ, this.player.rotationYaw, this.player.rotationPitch);
                                    return;
                                }
                            }

                            boolean flag2 = worldserver.getCollisionBoxes(this.player, this.player.getEntityBoundingBox().shrink(0.0625D)).isEmpty();
                            d7 = d4 - this.lastGoodX;
                            d8 = d5 - this.lastGoodY;
                            d9 = d6 - this.lastGoodZ;

                            if (this.player.onGround && !packetIn.isOnGround() && d8 > 0.0D)
                            {
                                this.player.jump();
                            }

                            this.player.move(MoverType.PLAYER, d7, d8, d9);
                            this.player.onGround = packetIn.isOnGround();
                            double d12 = d8;
                            d7 = d4 - this.player.posX;
                            d8 = d5 - this.player.posY;

                            if (d8 > -0.5D || d8 < 0.5D)
                            {
                                d8 = 0.0D;
                            }

                            d9 = d6 - this.player.posZ;
                            d11 = d7 * d7 + d8 * d8 + d9 * d9;
                            boolean flag = false;

                            if (!this.player.isInvulnerableDimensionChange() && d11 > 0.0625D && !this.player.isPlayerSleeping() && !this.player.interactionManager.isCreative() && this.player.interactionManager.getGameType() != GameType.SPECTATOR)
                            {
                                flag = true;
                                LOGGER.warn("{} moved wrongly!", (Object)this.player.getName());
                            }

                            this.player.setPositionAndRotation(d4, d5, d6, f, f1);
                            this.player.addMovementStat(this.player.posX - d0, this.player.posY - d1, this.player.posZ - d2);

                            if (!this.player.noClip && !this.player.isPlayerSleeping())
                            {
                                boolean flag1 = worldserver.getCollisionBoxes(this.player, this.player.getEntityBoundingBox().shrink(0.0625D)).isEmpty();

                                if (flag2 && (flag || !flag1))
                                {
                                    this.setPlayerLocation(d0, d1, d2, f, f1);
                                    return;
                                }
                            }

                            this.floating = d12 >= -0.03125D;
                            this.floating &= !this.serverController.isFlightAllowed() && !this.player.capabilities.allowFlying;
                            this.floating &= !this.player.isPotionActive(MobEffects.LEVITATION) && !this.player.isElytraFlying() && !worldserver.checkBlockCollision(this.player.getEntityBoundingBox().grow(0.0625D).expand(0.0D, -0.55D, 0.0D));
                            this.player.onGround = packetIn.isOnGround();
                            this.serverController.getPlayerList().serverUpdateMovingPlayer(this.player);
                            this.player.handleFalling(this.player.posY - d3, packetIn.isOnGround());
                            this.lastGoodX = this.player.posX;
                            this.lastGoodY = this.player.posY;
                            this.lastGoodZ = this.player.posZ;
                        }
                    }
                }
            }
        }
    }

    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch)
    {
        this.setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
    }

    /**
     * Teleports the player position to the (relative) values specified, and syncs to the client
     */
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<SPacketPlayerPosLook.EnumFlags> relativeSet)
    {
        double d0 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X) ? this.player.posX : 0.0D;
        double d1 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y) ? this.player.posY : 0.0D;
        double d2 = relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Z) ? this.player.posZ : 0.0D;
        this.targetPos = new Vec3d(x + d0, y + d1, z + d2);
        float f = yaw;
        float f1 = pitch;

        if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT))
        {
            f = yaw + this.player.rotationYaw;
        }

        if (relativeSet.contains(SPacketPlayerPosLook.EnumFlags.X_ROT))
        {
            f1 = pitch + this.player.rotationPitch;
        }

        if (++this.teleportId == Integer.MAX_VALUE)
        {
            this.teleportId = 0;
        }

        this.lastPositionUpdate = this.networkTickCount;
        this.player.setPositionAndRotation(this.targetPos.x, this.targetPos.y, this.targetPos.z, f, f1);
        this.player.connection.sendPacket(new SPacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet, this.teleportId));
    }

    /**
     * Processes the player initiating/stopping digging on a particular spot, as well as a player dropping items
     */
    public void processPlayerDigging(CPacketPlayerDigging packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        WorldServer worldserver = this.serverController.getWorld(this.player.dimension);
        BlockPos blockpos = packetIn.getPosition();
        this.player.markPlayerActive();

        switch (packetIn.getAction())
        {
            case SWAP_HELD_ITEMS:
                if (!this.player.isSpectator())
                {
                    ItemStack itemstack = this.player.getHeldItem(EnumHand.OFF_HAND);
                    this.player.setHeldItem(EnumHand.OFF_HAND, this.player.getHeldItem(EnumHand.MAIN_HAND));
                    this.player.setHeldItem(EnumHand.MAIN_HAND, itemstack);
                }

                return;

            case DROP_ITEM:
                if (!this.player.isSpectator())
                {
                    this.player.dropItem(false);
                }

                return;

            case DROP_ALL_ITEMS:
                if (!this.player.isSpectator())
                {
                    this.player.dropItem(true);
                }

                return;

            case RELEASE_USE_ITEM:
                this.player.stopActiveHand();
                return;

            case START_DESTROY_BLOCK:
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK:
                double d0 = this.player.posX - ((double)blockpos.getX() + 0.5D);
                double d1 = this.player.posY - ((double)blockpos.getY() + 0.5D) + 1.5D;
                double d2 = this.player.posZ - ((double)blockpos.getZ() + 0.5D);
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 > 36.0D)
                {
                    return;
                }
                else if (blockpos.getY() >= this.serverController.getBuildLimit())
                {
                    return;
                }
                else
                {
                    if (packetIn.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK)
                    {
                        if (!this.serverController.isBlockProtected(worldserver, blockpos, this.player) && worldserver.getWorldBorder().contains(blockpos))
                        {
                            this.player.interactionManager.onBlockClicked(blockpos, packetIn.getFacing());
                        }
                        else
                        {
                            this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos));
                        }
                    }
                    else
                    {
                        if (packetIn.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)
                        {
                            this.player.interactionManager.blockRemoving(blockpos);
                        }
                        else if (packetIn.getAction() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK)
                        {
                            this.player.interactionManager.cancelDestroyingBlock();
                        }

                        if (worldserver.getBlockState(blockpos).getMaterial() != Material.AIR)
                        {
                            this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos));
                        }
                    }

                    return;
                }

            default:
                throw new IllegalArgumentException("Invalid player action");
        }
    }

    public void processTryUseItemOnBlock(CPacketPlayerTryUseItemOnBlock packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        WorldServer worldserver = this.serverController.getWorld(this.player.dimension);
        EnumHand enumhand = packetIn.getHand();
        ItemStack itemstack = this.player.getHeldItem(enumhand);
        BlockPos blockpos = packetIn.getPos();
        EnumFacing enumfacing = packetIn.getDirection();
        this.player.markPlayerActive();

        if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())
        {
            if (this.targetPos == null && this.player.getDistanceSq((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.5D, (double)blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected(worldserver, blockpos, this.player) && worldserver.getWorldBorder().contains(blockpos))
            {
                this.player.interactionManager.processRightClickBlock(this.player, worldserver, itemstack, enumhand, blockpos, enumfacing, packetIn.getFacingX(), packetIn.getFacingY(), packetIn.getFacingZ());
            }
        }
        else
        {
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("build.tooHigh", new Object[] {this.serverController.getBuildLimit()});
            textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
            this.player.connection.sendPacket(new SPacketChat(textcomponenttranslation, ChatType.GAME_INFO));
        }

        this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos));
        this.player.connection.sendPacket(new SPacketBlockChange(worldserver, blockpos.offset(enumfacing)));
    }

    /**
     * Called when a client is using an item while not pointing at a block, but simply using an item
     */
    public void processTryUseItem(CPacketPlayerTryUseItem packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        WorldServer worldserver = this.serverController.getWorld(this.player.dimension);
        EnumHand enumhand = packetIn.getHand();
        ItemStack itemstack = this.player.getHeldItem(enumhand);
        this.player.markPlayerActive();

        if (!itemstack.isEmpty())
        {
            this.player.interactionManager.processRightClick(this.player, worldserver, itemstack, enumhand);
        }
    }

    public void handleSpectate(CPacketSpectate packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (this.player.isSpectator())
        {
            Entity entity = null;

            for (WorldServer worldserver : this.serverController.worlds)
            {
                if (worldserver != null)
                {
                    entity = packetIn.getEntity(worldserver);

                    if (entity != null)
                    {
                        break;
                    }
                }
            }

            if (entity != null)
            {
                this.player.setSpectatingEntity(this.player);
                this.player.dismountRidingEntity();

                if (entity.world == this.player.world)
                {
                    this.player.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                }
                else
                {
                    WorldServer worldserver1 = this.player.getServerWorld();
                    WorldServer worldserver2 = (WorldServer)entity.world;
                    this.player.dimension = entity.dimension;
                    this.sendPacket(new SPacketRespawn(this.player.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.player.interactionManager.getGameType()));
                    this.serverController.getPlayerList().updatePermissionLevel(this.player);
                    worldserver1.removeEntityDangerously(this.player);
                    this.player.isDead = false;
                    this.player.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

                    if (this.player.isEntityAlive())
                    {
                        worldserver1.updateEntityWithOptionalForce(this.player, false);
                        worldserver2.spawnEntity(this.player);
                        worldserver2.updateEntityWithOptionalForce(this.player, false);
                    }

                    this.player.setWorld(worldserver2);
                    this.serverController.getPlayerList().preparePlayer(this.player, worldserver1);
                    this.player.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                    this.player.interactionManager.setWorld(worldserver2);
                    this.serverController.getPlayerList().updateTimeAndWeatherForPlayer(this.player, worldserver2);
                    this.serverController.getPlayerList().syncPlayerInventory(this.player);
                }
            }
        }
    }

    public void handleResourcePackStatus(CPacketResourcePackStatus packetIn)
    {
    }

    public void processSteerBoat(CPacketSteerBoat packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        Entity entity = this.player.getRidingEntity();

        if (entity instanceof EntityBoat)
        {
            ((EntityBoat)entity).setPaddleState(packetIn.getLeft(), packetIn.getRight());
        }
    }

    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    public void onDisconnect(ITextComponent reason)
    {
        LOGGER.info("{} lost connection: {}", this.player.getName(), reason.getUnformattedText());
        this.serverController.refreshStatusNextTick();
        TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("multiplayer.player.left", new Object[] {this.player.getDisplayName()});
        textcomponenttranslation.getStyle().setColor(TextFormatting.YELLOW);
        this.serverController.getPlayerList().sendMessage(textcomponenttranslation);
        this.player.mountEntityAndWakeUp();
        this.serverController.getPlayerList().playerLoggedOut(this.player);

        if (this.serverController.isSinglePlayer() && this.player.getName().equals(this.serverController.getServerOwner()))
        {
            LOGGER.info("Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }

    public void sendPacket(final Packet<?> packetIn)
    {
        if (packetIn instanceof SPacketChat)
        {
            SPacketChat spacketchat = (SPacketChat)packetIn;
            EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.player.getChatVisibility();

            if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN && spacketchat.getType() != ChatType.GAME_INFO)
            {
                return;
            }

            if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !spacketchat.isSystem())
            {
                return;
            }
        }

        try
        {
            this.netManager.sendPacket(packetIn);
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
            crashreportcategory.addDetail("Packet class", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    return packetIn.getClass().getCanonicalName();
                }
            });
            throw new ReportedException(crashreport);
        }
    }

    /**
     * Updates which quickbar slot is selected
     */
    public void processHeldItemChange(CPacketHeldItemChange packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize())
        {
            this.player.inventory.currentItem = packetIn.getSlotId();
            this.player.markPlayerActive();
        }
        else
        {
            LOGGER.warn("{} tried to set an invalid carried item", (Object)this.player.getName());
        }
    }

    /**
     * Process chat messages (broadcast back to clients) and commands (executes)
     */
    public void processChatMessage(CPacketChatMessage packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (this.player.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("chat.cannotSend", new Object[0]);
            textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
            this.sendPacket(new SPacketChat(textcomponenttranslation));
        }
        else
        {
            this.player.markPlayerActive();
            String s = packetIn.getMessage();
            s = StringUtils.normalizeSpace(s);

            for (int i = 0; i < s.length(); ++i)
            {
                if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i)))
                {
                    this.disconnect(new TextComponentTranslation("multiplayer.disconnect.illegal_characters", new Object[0]));
                    return;
                }
            }

            if (s.startsWith("/"))
            {
                this.handleSlashCommand(s);
            }
            else
            {
                ITextComponent itextcomponent = new TextComponentTranslation("chat.type.text", new Object[] {this.player.getDisplayName(), s});
                this.serverController.getPlayerList().sendMessage(itextcomponent, false);
            }

            this.chatSpamThresholdCount += 20;

            if (this.chatSpamThresholdCount > 200 && !this.serverController.getPlayerList().canSendCommands(this.player.getGameProfile()))
            {
                this.disconnect(new TextComponentTranslation("disconnect.spam", new Object[0]));
            }
        }
    }

    /**
     * Handle commands that start with a /
     */
    private void handleSlashCommand(String command)
    {
        this.serverController.getCommandManager().executeCommand(this.player, command);
    }

    public void handleAnimation(CPacketAnimation packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        this.player.swingArm(packetIn.getHand());
    }

    /**
     * Processes a range of action-types: sneaking, sprinting, waking from sleep, opening the inventory or setting jump
     * height of the horse the player is riding
     */
    public void processEntityAction(CPacketEntityAction packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();

        switch (packetIn.getAction())
        {
            case START_SNEAKING:
                this.player.setSneaking(true);
                break;

            case STOP_SNEAKING:
                this.player.setSneaking(false);
                break;

            case START_SPRINTING:
                this.player.setSprinting(true);
                break;

            case STOP_SPRINTING:
                this.player.setSprinting(false);
                break;

            case STOP_SLEEPING:
                if (this.player.isPlayerSleeping())
                {
                    this.player.wakeUpPlayer(false, true, true);
                    this.targetPos = new Vec3d(this.player.posX, this.player.posY, this.player.posZ);
                }

                break;

            case START_RIDING_JUMP:
                if (this.player.getRidingEntity() instanceof IJumpingMount)
                {
                    IJumpingMount ijumpingmount1 = (IJumpingMount)this.player.getRidingEntity();
                    int i = packetIn.getAuxData();

                    if (ijumpingmount1.canJump() && i > 0)
                    {
                        ijumpingmount1.handleStartJump(i);
                    }
                }

                break;

            case STOP_RIDING_JUMP:
                if (this.player.getRidingEntity() instanceof IJumpingMount)
                {
                    IJumpingMount ijumpingmount = (IJumpingMount)this.player.getRidingEntity();
                    ijumpingmount.handleStopJump();
                }

                break;

            case OPEN_INVENTORY:
                if (this.player.getRidingEntity() instanceof AbstractHorse)
                {
                    ((AbstractHorse)this.player.getRidingEntity()).openGUI(this.player);
                }

                break;

            case START_FALL_FLYING:
                if (!this.player.onGround && this.player.motionY < 0.0D && !this.player.isElytraFlying() && !this.player.isInWater())
                {
                    ItemStack itemstack = this.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

                    if (itemstack.getItem() == Items.ELYTRA && ItemElytra.isUsable(itemstack))
                    {
                        this.player.setElytraFlying();
                    }
                }
                else
                {
                    this.player.clearElytraFlying();
                }

                break;

            default:
                throw new IllegalArgumentException("Invalid client command!");
        }
    }

    /**
     * Processes left and right clicks on entities
     */
    public void processUseEntity(CPacketUseEntity packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        WorldServer worldserver = this.serverController.getWorld(this.player.dimension);
        Entity entity = packetIn.getEntityFromWorld(worldserver);
        this.player.markPlayerActive();

        if (entity != null)
        {
            boolean flag = this.player.canEntityBeSeen(entity);
            double d0 = 36.0D;

            if (!flag)
            {
                d0 = 9.0D;
            }

            if (this.player.getDistanceSqToEntity(entity) < d0)
            {
                if (packetIn.getAction() == CPacketUseEntity.Action.INTERACT)
                {
                    EnumHand enumhand = packetIn.getHand();
                    this.player.interactOn(entity, enumhand);
                }
                else if (packetIn.getAction() == CPacketUseEntity.Action.INTERACT_AT)
                {
                    EnumHand enumhand1 = packetIn.getHand();
                    entity.applyPlayerInteraction(this.player, packetIn.getHitVec(), enumhand1);
                }
                else if (packetIn.getAction() == CPacketUseEntity.Action.ATTACK)
                {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow || entity == this.player)
                    {
                        this.disconnect(new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked", new Object[0]));
                        this.serverController.logWarning("Player " + this.player.getName() + " tried to attack an invalid entity");
                        return;
                    }

                    this.player.attackTargetEntityWithCurrentItem(entity);
                }
            }
        }
    }

    /**
     * Processes the client status updates: respawn attempt from player, opening statistics or achievements, or
     * acquiring 'open inventory' achievement
     */
    public void processClientStatus(CPacketClientStatus packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        CPacketClientStatus.State cpacketclientstatus$state = packetIn.getStatus();

        switch (cpacketclientstatus$state)
        {
            case PERFORM_RESPAWN:
                if (this.player.queuedEndExit)
                {
                    this.player.queuedEndExit = false;
                    this.player = this.serverController.getPlayerList().recreatePlayerEntity(this.player, 0, true);
                    CriteriaTriggers.CHANGED_DIMENSION.trigger(this.player, DimensionType.THE_END, DimensionType.OVERWORLD);
                }
                else
                {
                    if (this.player.getHealth() > 0.0F)
                    {
                        return;
                    }

                    this.player = this.serverController.getPlayerList().recreatePlayerEntity(this.player, 0, false);

                    if (this.serverController.isHardcore())
                    {
                        this.player.setGameType(GameType.SPECTATOR);
                        this.player.getServerWorld().getGameRules().setOrCreateGameRule("spectatorsGenerateChunks", "false");
                    }
                }

                break;

            case REQUEST_STATS:
                this.player.getStatFile().sendStats(this.player);
        }
    }

    /**
     * Processes the client closing windows (container)
     */
    public void processCloseWindow(CPacketCloseWindow packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.closeContainer();
    }

    /**
     * Executes a container/inventory slot manipulation as indicated by the packet. Sends the serverside result if they
     * didn't match the indicated result and prevents further manipulation by the player until he confirms that it has
     * the same open container/inventory
     */
    public void processClickWindow(CPacketClickWindow packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();

        if (this.player.openContainer.windowId == packetIn.getWindowId() && this.player.openContainer.getCanCraft(this.player))
        {
            if (this.player.isSpectator())
            {
                NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>create();

                for (int i = 0; i < this.player.openContainer.inventorySlots.size(); ++i)
                {
                    nonnulllist.add(((Slot)this.player.openContainer.inventorySlots.get(i)).getStack());
                }

                this.player.sendAllContents(this.player.openContainer, nonnulllist);
            }
            else
            {
                ItemStack itemstack2 = this.player.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getClickType(), this.player);

                if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack2))
                {
                    this.player.connection.sendPacket(new SPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
                    this.player.isChangingQuantityOnly = true;
                    this.player.openContainer.detectAndSendChanges();
                    this.player.updateHeldItem();
                    this.player.isChangingQuantityOnly = false;
                }
                else
                {
                    this.pendingTransactions.addKey(this.player.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
                    this.player.connection.sendPacket(new SPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
                    this.player.openContainer.setCanCraft(this.player, false);
                    NonNullList<ItemStack> nonnulllist1 = NonNullList.<ItemStack>create();

                    for (int j = 0; j < this.player.openContainer.inventorySlots.size(); ++j)
                    {
                        ItemStack itemstack = ((Slot)this.player.openContainer.inventorySlots.get(j)).getStack();
                        ItemStack itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
                        nonnulllist1.add(itemstack1);
                    }

                    this.player.sendAllContents(this.player.openContainer, nonnulllist1);
                }
            }
        }
    }

    public void handleRecipePlacement(CPacketRecipePlacement p_191985_1_)
    {
        PacketThreadUtil.checkThreadAndEnqueue(p_191985_1_, this, this.player.getServerWorld());
        this.player.markPlayerActive();

        if (this.player.openContainer.windowId == p_191985_1_.getContainerId() && this.player.openContainer.getCanCraft(this.player))
        {
            this.player.connection.sendPacket(new SPacketConfirmTransaction(p_191985_1_.getContainerId(), p_191985_1_.getUid(), true));

            if (!p_191985_1_.getMoveItemsFromGrid().isEmpty())
            {
                for (CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove : p_191985_1_.getMoveItemsFromGrid())
                {
                    ItemStack itemstack = this.player.openContainer.getSlot(cpacketrecipeplacement$itemmove.srcSlot).getStack();

                    if (this.checkIfMoveItemMatch(cpacketrecipeplacement$itemmove.stack, itemstack))
                    {
                        int i = cpacketrecipeplacement$itemmove.stack.getCount();

                        if (cpacketrecipeplacement$itemmove.destSlot == -1)
                        {
                            this.player.dropItem(cpacketrecipeplacement$itemmove.stack, true);
                        }
                        else
                        {
                            ItemStack itemstack1 = this.player.inventory.getStackInSlot(cpacketrecipeplacement$itemmove.destSlot);

                            if (itemstack1.isEmpty())
                            {
                                this.player.inventory.setInventorySlotContents(cpacketrecipeplacement$itemmove.destSlot, cpacketrecipeplacement$itemmove.stack);
                            }
                            else
                            {
                                itemstack1.grow(i);
                            }
                        }

                        if (itemstack.getCount() == i)
                        {
                            this.player.openContainer.putStackInSlot(cpacketrecipeplacement$itemmove.srcSlot, ItemStack.EMPTY);
                        }
                        else
                        {
                            itemstack.shrink(i);
                        }
                    }
                }
            }

            if (!p_191985_1_.getMoveItemsToGrid().isEmpty())
            {
                for (CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove1 : p_191985_1_.getMoveItemsToGrid())
                {
                    ItemStack itemstack2 = this.player.inventory.getStackInSlot(cpacketrecipeplacement$itemmove1.destSlot);

                    if (this.checkIfMoveItemMatch(cpacketrecipeplacement$itemmove1.stack, itemstack2))
                    {
                        int j = cpacketrecipeplacement$itemmove1.stack.getCount();

                        if (itemstack2.getCount() == j)
                        {
                            this.player.inventory.removeStackFromSlot(cpacketrecipeplacement$itemmove1.destSlot);
                        }
                        else
                        {
                            itemstack2.shrink(j);
                        }

                        this.player.openContainer.addItem(cpacketrecipeplacement$itemmove1.srcSlot, cpacketrecipeplacement$itemmove1.stack);
                    }
                }
            }

            this.player.openContainer.detectAndSendChanges();
        }
    }

    private boolean checkIfMoveItemMatch(ItemStack p_193074_1_, ItemStack p_193074_2_)
    {
        ItemStack itemstack = p_193074_2_.copy();

        if (itemstack.getCount() < p_193074_1_.getCount())
        {
            return false;
        }
        else
        {
            itemstack.setCount(p_193074_1_.getCount());
            return ItemStack.areItemStacksEqual(p_193074_1_, itemstack);
        }
    }

    /**
     * Enchants the item identified by the packet given some convoluted conditions (matching window, which
     * should/shouldn't be in use?)
     */
    public void processEnchantItem(CPacketEnchantItem packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();

        if (this.player.openContainer.windowId == packetIn.getWindowId() && this.player.openContainer.getCanCraft(this.player) && !this.player.isSpectator())
        {
            this.player.openContainer.enchantItem(this.player, packetIn.getButton());
            this.player.openContainer.detectAndSendChanges();
        }
    }

    /**
     * Update the server with an ItemStack in a slot.
     */
    public void processCreativeInventoryAction(CPacketCreativeInventoryAction packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());

        if (this.player.interactionManager.isCreative())
        {
            boolean flag = packetIn.getSlotId() < 0;
            ItemStack itemstack = packetIn.getStack();

            if (!itemstack.isEmpty() && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10))
            {
                NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");

                if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z"))
                {
                    BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
                    TileEntity tileentity = this.player.world.getTileEntity(blockpos);

                    if (tileentity != null)
                    {
                        NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
                        nbttagcompound1.removeTag("x");
                        nbttagcompound1.removeTag("y");
                        nbttagcompound1.removeTag("z");
                        itemstack.setTagInfo("BlockEntityTag", nbttagcompound1);
                    }
                }
            }

            boolean flag1 = packetIn.getSlotId() >= 1 && packetIn.getSlotId() <= 45;
            boolean flag2 = itemstack.isEmpty() || itemstack.getMetadata() >= 0 && itemstack.getCount() <= 64 && !itemstack.isEmpty();

            if (flag1 && flag2)
            {
                if (itemstack.isEmpty())
                {
                    this.player.inventoryContainer.putStackInSlot(packetIn.getSlotId(), ItemStack.EMPTY);
                }
                else
                {
                    this.player.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
                }

                this.player.inventoryContainer.setCanCraft(this.player, true);
            }
            else if (flag && flag2 && this.itemDropThreshold < 200)
            {
                this.itemDropThreshold += 20;
                EntityItem entityitem = this.player.dropItem(itemstack, true);

                if (entityitem != null)
                {
                    entityitem.setAgeToCreativeDespawnTime();
                }
            }
        }
    }

    /**
     * Received in response to the server requesting to confirm that the client-side open container matches the servers'
     * after a mismatched container-slot manipulation. It will unlock the player's ability to manipulate the container
     * contents
     */
    public void processConfirmTransaction(CPacketConfirmTransaction packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        Short oshort = this.pendingTransactions.lookup(this.player.openContainer.windowId);

        if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.player.openContainer.windowId == packetIn.getWindowId() && !this.player.openContainer.getCanCraft(this.player) && !this.player.isSpectator())
        {
            this.player.openContainer.setCanCraft(this.player, true);
        }
    }

    public void processUpdateSign(CPacketUpdateSign packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.markPlayerActive();
        WorldServer worldserver = this.serverController.getWorld(this.player.dimension);
        BlockPos blockpos = packetIn.getPosition();

        if (worldserver.isBlockLoaded(blockpos))
        {
            IBlockState iblockstate = worldserver.getBlockState(blockpos);
            TileEntity tileentity = worldserver.getTileEntity(blockpos);

            if (!(tileentity instanceof TileEntitySign))
            {
                return;
            }

            TileEntitySign tileentitysign = (TileEntitySign)tileentity;

            if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.player)
            {
                this.serverController.logWarning("Player " + this.player.getName() + " just tried to change non-editable sign");
                return;
            }

            String[] astring = packetIn.getLines();

            for (int i = 0; i < astring.length; ++i)
            {
                tileentitysign.signText[i] = new TextComponentString(TextFormatting.getTextWithoutFormattingCodes(astring[i]));
            }

            tileentitysign.markDirty();
            worldserver.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
        }
    }

    /**
     * Updates a players' ping statistics
     */
    public void processKeepAlive(CPacketKeepAlive packetIn)
    {
        if (packetIn.getKey() == this.keepAliveId)
        {
            int i = (int)(this.currentTimeMillis() - this.lastPingTime);
            this.player.ping = (this.player.ping * 3 + i) / 4;
        }
    }

    private long currentTimeMillis()
    {
        return System.nanoTime() / 1000000L;
    }

    /**
     * Processes a player starting/stopping flying
     */
    public void processPlayerAbilities(CPacketPlayerAbilities packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.capabilities.isFlying = packetIn.isFlying() && this.player.capabilities.allowFlying;
    }

    /**
     * Retrieves possible tab completions for the requested command string and sends them to the client
     */
    public void processTabComplete(CPacketTabComplete packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        List<String> list = Lists.<String>newArrayList();

        for (String s : this.serverController.getTabCompletions(this.player, packetIn.getMessage(), packetIn.getTargetBlock(), packetIn.hasTargetBlock()))
        {
            list.add(s);
        }

        this.player.connection.sendPacket(new SPacketTabComplete((String[])list.toArray(new String[list.size()])));
    }

    /**
     * Updates serverside copy of client settings: language, render distance, chat visibility, chat colours, difficulty,
     * and whether to show the cape
     */
    public void processClientSettings(CPacketClientSettings packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        this.player.handleClientSettings(packetIn);
    }

    /**
     * Synchronizes serverside and clientside book contents and signing
     */
    public void processCustomPayload(CPacketCustomPayload packetIn)
    {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        String s = packetIn.getChannelName();

        if ("MC|BEdit".equals(s))
        {
            PacketBuffer packetbuffer = packetIn.getBufferData();

            try
            {
                ItemStack itemstack = packetbuffer.readItemStack();

                if (itemstack.isEmpty())
                {
                    return;
                }

                if (!ItemWritableBook.isNBTValid(itemstack.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack1 = this.player.getHeldItemMainhand();

                if (itemstack1.isEmpty())
                {
                    return;
                }

                if (itemstack.getItem() == Items.WRITABLE_BOOK && itemstack.getItem() == itemstack1.getItem())
                {
                    itemstack1.setTagInfo("pages", itemstack.getTagCompound().getTagList("pages", 8));
                }
            }
            catch (Exception exception6)
            {
                LOGGER.error("Couldn't handle book info", (Throwable)exception6);
            }
        }
        else if ("MC|BSign".equals(s))
        {
            PacketBuffer packetbuffer1 = packetIn.getBufferData();

            try
            {
                ItemStack itemstack3 = packetbuffer1.readItemStack();

                if (itemstack3.isEmpty())
                {
                    return;
                }

                if (!ItemWrittenBook.validBookTagContents(itemstack3.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                ItemStack itemstack4 = this.player.getHeldItemMainhand();

                if (itemstack4.isEmpty())
                {
                    return;
                }

                if (itemstack3.getItem() == Items.WRITABLE_BOOK && itemstack4.getItem() == Items.WRITABLE_BOOK)
                {
                    ItemStack itemstack2 = new ItemStack(Items.WRITTEN_BOOK);
                    itemstack2.setTagInfo("author", new NBTTagString(this.player.getName()));
                    itemstack2.setTagInfo("title", new NBTTagString(itemstack3.getTagCompound().getString("title")));
                    NBTTagList nbttaglist = itemstack3.getTagCompound().getTagList("pages", 8);

                    for (int i = 0; i < nbttaglist.tagCount(); ++i)
                    {
                        String s1 = nbttaglist.getStringTagAt(i);
                        ITextComponent itextcomponent = new TextComponentString(s1);
                        s1 = ITextComponent.Serializer.componentToJson(itextcomponent);
                        nbttaglist.set(i, new NBTTagString(s1));
                    }

                    itemstack2.setTagInfo("pages", nbttaglist);
                    this.player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemstack2);
                }
            }
            catch (Exception exception7)
            {
                LOGGER.error("Couldn't sign book", (Throwable)exception7);
            }
        }
        else if ("MC|TrSel".equals(s))
        {
            try
            {
                int k = packetIn.getBufferData().readInt();
                Container container = this.player.openContainer;

                if (container instanceof ContainerMerchant)
                {
                    ((ContainerMerchant)container).setCurrentRecipeIndex(k);
                }
            }
            catch (Exception exception5)
            {
                LOGGER.error("Couldn't select trade", (Throwable)exception5);
            }
        }
        else if ("MC|AdvCmd".equals(s))
        {
            if (!this.serverController.isCommandBlockEnabled())
            {
                this.player.sendMessage(new TextComponentTranslation("advMode.notEnabled", new Object[0]));
                return;
            }

            if (!this.player.canUseCommandBlock())
            {
                this.player.sendMessage(new TextComponentTranslation("advMode.notAllowed", new Object[0]));
                return;
            }

            PacketBuffer packetbuffer2 = packetIn.getBufferData();

            try
            {
                int l = packetbuffer2.readByte();
                CommandBlockBaseLogic commandblockbaselogic1 = null;

                if (l == 0)
                {
                    TileEntity tileentity = this.player.world.getTileEntity(new BlockPos(packetbuffer2.readInt(), packetbuffer2.readInt(), packetbuffer2.readInt()));

                    if (tileentity instanceof TileEntityCommandBlock)
                    {
                        commandblockbaselogic1 = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
                    }
                }
                else if (l == 1)
                {
                    Entity entity = this.player.world.getEntityByID(packetbuffer2.readInt());

                    if (entity instanceof EntityMinecartCommandBlock)
                    {
                        commandblockbaselogic1 = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
                    }
                }

                String s6 = packetbuffer2.readString(packetbuffer2.readableBytes());
                boolean flag2 = packetbuffer2.readBoolean();

                if (commandblockbaselogic1 != null)
                {
                    commandblockbaselogic1.setCommand(s6);
                    commandblockbaselogic1.setTrackOutput(flag2);

                    if (!flag2)
                    {
                        commandblockbaselogic1.setLastOutput((ITextComponent)null);
                    }

                    commandblockbaselogic1.updateCommand();
                    this.player.sendMessage(new TextComponentTranslation("advMode.setCommand.success", new Object[] {s6}));
                }
            }
            catch (Exception exception4)
            {
                LOGGER.error("Couldn't set command block", (Throwable)exception4);
            }
        }
        else if ("MC|AutoCmd".equals(s))
        {
            if (!this.serverController.isCommandBlockEnabled())
            {
                this.player.sendMessage(new TextComponentTranslation("advMode.notEnabled", new Object[0]));
                return;
            }

            if (!this.player.canUseCommandBlock())
            {
                this.player.sendMessage(new TextComponentTranslation("advMode.notAllowed", new Object[0]));
                return;
            }

            PacketBuffer packetbuffer3 = packetIn.getBufferData();

            try
            {
                CommandBlockBaseLogic commandblockbaselogic = null;
                TileEntityCommandBlock tileentitycommandblock = null;
                BlockPos blockpos1 = new BlockPos(packetbuffer3.readInt(), packetbuffer3.readInt(), packetbuffer3.readInt());
                TileEntity tileentity2 = this.player.world.getTileEntity(blockpos1);

                if (tileentity2 instanceof TileEntityCommandBlock)
                {
                    tileentitycommandblock = (TileEntityCommandBlock)tileentity2;
                    commandblockbaselogic = tileentitycommandblock.getCommandBlockLogic();
                }

                String s7 = packetbuffer3.readString(packetbuffer3.readableBytes());
                boolean flag3 = packetbuffer3.readBoolean();
                TileEntityCommandBlock.Mode tileentitycommandblock$mode = TileEntityCommandBlock.Mode.valueOf(packetbuffer3.readString(16));
                boolean flag = packetbuffer3.readBoolean();
                boolean flag1 = packetbuffer3.readBoolean();

                if (commandblockbaselogic != null)
                {
                    EnumFacing enumfacing = (EnumFacing)this.player.world.getBlockState(blockpos1).getValue(BlockCommandBlock.FACING);

                    switch (tileentitycommandblock$mode)
                    {
                        case SEQUENCE:
                            IBlockState iblockstate3 = Blocks.CHAIN_COMMAND_BLOCK.getDefaultState();
                            this.player.world.setBlockState(blockpos1, iblockstate3.withProperty(BlockCommandBlock.FACING, enumfacing).withProperty(BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
                            break;

                        case AUTO:
                            IBlockState iblockstate2 = Blocks.REPEATING_COMMAND_BLOCK.getDefaultState();
                            this.player.world.setBlockState(blockpos1, iblockstate2.withProperty(BlockCommandBlock.FACING, enumfacing).withProperty(BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
                            break;

                        case REDSTONE:
                            IBlockState iblockstate = Blocks.COMMAND_BLOCK.getDefaultState();
                            this.player.world.setBlockState(blockpos1, iblockstate.withProperty(BlockCommandBlock.FACING, enumfacing).withProperty(BlockCommandBlock.CONDITIONAL, Boolean.valueOf(flag)), 2);
                    }

                    tileentity2.validate();
                    this.player.world.setTileEntity(blockpos1, tileentity2);
                    commandblockbaselogic.setCommand(s7);
                    commandblockbaselogic.setTrackOutput(flag3);

                    if (!flag3)
                    {
                        commandblockbaselogic.setLastOutput((ITextComponent)null);
                    }

                    tileentitycommandblock.setAuto(flag1);
                    commandblockbaselogic.updateCommand();

                    if (!net.minecraft.util.StringUtils.isNullOrEmpty(s7))
                    {
                        this.player.sendMessage(new TextComponentTranslation("advMode.setCommand.success", new Object[] {s7}));
                    }
                }
            }
            catch (Exception exception3)
            {
                LOGGER.error("Couldn't set command block", (Throwable)exception3);
            }
        }
        else if ("MC|Beacon".equals(s))
        {
            if (this.player.openContainer instanceof ContainerBeacon)
            {
                try
                {
                    PacketBuffer packetbuffer4 = packetIn.getBufferData();
                    int i1 = packetbuffer4.readInt();
                    int k1 = packetbuffer4.readInt();
                    ContainerBeacon containerbeacon = (ContainerBeacon)this.player.openContainer;
                    Slot slot = containerbeacon.getSlot(0);

                    if (slot.getHasStack())
                    {
                        slot.decrStackSize(1);
                        IInventory iinventory = containerbeacon.getTileEntity();
                        iinventory.setField(1, i1);
                        iinventory.setField(2, k1);
                        iinventory.markDirty();
                    }
                }
                catch (Exception exception2)
                {
                    LOGGER.error("Couldn't set beacon", (Throwable)exception2);
                }
            }
        }
        else if ("MC|ItemName".equals(s))
        {
            if (this.player.openContainer instanceof ContainerRepair)
            {
                ContainerRepair containerrepair = (ContainerRepair)this.player.openContainer;

                if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1)
                {
                    String s5 = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readString(32767));

                    if (s5.length() <= 35)
                    {
                        containerrepair.updateItemName(s5);
                    }
                }
                else
                {
                    containerrepair.updateItemName("");
                }
            }
        }
        else if ("MC|Struct".equals(s))
        {
            if (!this.player.canUseCommandBlock())
            {
                return;
            }

            PacketBuffer packetbuffer5 = packetIn.getBufferData();

            try
            {
                BlockPos blockpos = new BlockPos(packetbuffer5.readInt(), packetbuffer5.readInt(), packetbuffer5.readInt());
                IBlockState iblockstate1 = this.player.world.getBlockState(blockpos);
                TileEntity tileentity1 = this.player.world.getTileEntity(blockpos);

                if (tileentity1 instanceof TileEntityStructure)
                {
                    TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity1;
                    int l1 = packetbuffer5.readByte();
                    String s8 = packetbuffer5.readString(32);
                    tileentitystructure.setMode(TileEntityStructure.Mode.valueOf(s8));
                    tileentitystructure.setName(packetbuffer5.readString(64));
                    int i2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
                    int j2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
                    int k2 = MathHelper.clamp(packetbuffer5.readInt(), -32, 32);
                    tileentitystructure.setPosition(new BlockPos(i2, j2, k2));
                    int l2 = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
                    int i3 = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
                    int j = MathHelper.clamp(packetbuffer5.readInt(), 0, 32);
                    tileentitystructure.setSize(new BlockPos(l2, i3, j));
                    String s2 = packetbuffer5.readString(32);
                    tileentitystructure.setMirror(Mirror.valueOf(s2));
                    String s3 = packetbuffer5.readString(32);
                    tileentitystructure.setRotation(Rotation.valueOf(s3));
                    tileentitystructure.setMetadata(packetbuffer5.readString(128));
                    tileentitystructure.setIgnoresEntities(packetbuffer5.readBoolean());
                    tileentitystructure.setShowAir(packetbuffer5.readBoolean());
                    tileentitystructure.setShowBoundingBox(packetbuffer5.readBoolean());
                    tileentitystructure.setIntegrity(MathHelper.clamp(packetbuffer5.readFloat(), 0.0F, 1.0F));
                    tileentitystructure.setSeed(packetbuffer5.readVarLong());
                    String s4 = tileentitystructure.getName();

                    if (l1 == 2)
                    {
                        if (tileentitystructure.save())
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.save_success", new Object[] {s4}), false);
                        }
                        else
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.save_failure", new Object[] {s4}), false);
                        }
                    }
                    else if (l1 == 3)
                    {
                        if (!tileentitystructure.isStructureLoadable())
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.load_not_found", new Object[] {s4}), false);
                        }
                        else if (tileentitystructure.load())
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.load_success", new Object[] {s4}), false);
                        }
                        else
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.load_prepare", new Object[] {s4}), false);
                        }
                    }
                    else if (l1 == 4)
                    {
                        if (tileentitystructure.detectSize())
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.size_success", new Object[] {s4}), false);
                        }
                        else
                        {
                            this.player.sendStatusMessage(new TextComponentTranslation("structure_block.size_failure", new Object[0]), false);
                        }
                    }

                    tileentitystructure.markDirty();
                    this.player.world.notifyBlockUpdate(blockpos, iblockstate1, iblockstate1, 3);
                }
            }
            catch (Exception exception1)
            {
                LOGGER.error("Couldn't set structure block", (Throwable)exception1);
            }
        }
        else if ("MC|PickItem".equals(s))
        {
            PacketBuffer packetbuffer6 = packetIn.getBufferData();

            try
            {
                int j1 = packetbuffer6.readVarInt();
                this.player.inventory.pickItem(j1);
                this.player.connection.sendPacket(new SPacketSetSlot(-2, this.player.inventory.currentItem, this.player.inventory.getStackInSlot(this.player.inventory.currentItem)));
                this.player.connection.sendPacket(new SPacketSetSlot(-2, j1, this.player.inventory.getStackInSlot(j1)));
                this.player.connection.sendPacket(new SPacketHeldItemChange(this.player.inventory.currentItem));
            }
            catch (Exception exception)
            {
                LOGGER.error("Couldn't pick item", (Throwable)exception);
            }
        }
    }
}
