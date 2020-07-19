package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;

public interface INetHandlerPlayClient extends INetHandler
{
    /**
     * Spawns an instance of the objecttype indicated by the packet and sets its position and momentum
     */
    void handleSpawnObject(SPacketSpawnObject packetIn);

    /**
     * Spawns an experience orb and sets its value (amount of XP)
     */
    void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn);

    /**
     * Handles globally visible entities. Used in vanilla for lightning bolts
     */
    void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn);

    /**
     * Spawns the mob entity at the specified location, with the specified rotation, momentum and type. Updates the
     * entities Datawatchers with the entity metadata specified in the packet
     */
    void handleSpawnMob(SPacketSpawnMob packetIn);

    /**
     * May create a scoreboard objective, remove an objective from the scoreboard or update an objectives' displayname
     */
    void handleScoreboardObjective(SPacketScoreboardObjective packetIn);

    /**
     * Handles the spawning of a painting object
     */
    void handleSpawnPainting(SPacketSpawnPainting packetIn);

    /**
     * Handles the creation of a nearby player entity, sets the position and held item
     */
    void handleSpawnPlayer(SPacketSpawnPlayer packetIn);

    /**
     * Renders a specified animation: Waking up a player, a living entity swinging its currently held item, being hurt
     * or receiving a critical hit by normal or magical means
     */
    void handleAnimation(SPacketAnimation packetIn);

    /**
     * Updates the players statistics or achievements
     */
    void handleStatistics(SPacketStatistics packetIn);

    void handleRecipeBook(SPacketRecipeBook packetIn);

    /**
     * Updates all registered IWorldAccess instances with destroyBlockInWorldPartially
     */
    void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn);

    /**
     * Creates a sign in the specified location if it didn't exist and opens the GUI to edit its text
     */
    void handleSignEditorOpen(SPacketSignEditorOpen packetIn);

    /**
     * Updates the NBTTagCompound metadata of instances of the following entitytypes: Mob spawners, command blocks,
     * beacons, skulls, flowerpot
     */
    void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn);

    /**
     * Triggers Block.onBlockEventReceived, which is implemented in BlockPistonBase for extension/retraction, BlockNote
     * for setting the instrument (including audiovisual feedback) and in BlockContainer to set the number of players
     * accessing a (Ender)Chest
     */
    void handleBlockAction(SPacketBlockAction packetIn);

    /**
     * Updates the block and metadata and generates a blockupdate (and notify the clients)
     */
    void handleBlockChange(SPacketBlockChange packetIn);

    /**
     * Prints a chatmessage in the chat GUI
     */
    void handleChat(SPacketChat packetIn);

    /**
     * Displays the available command-completion options the server knows of
     */
    void handleTabComplete(SPacketTabComplete packetIn);

    /**
     * Received from the servers PlayerManager if between 1 and 64 blocks in a chunk are changed. If only one block
     * requires an update, the server sends S23PacketBlockChange and if 64 or more blocks are changed, the server sends
     * S21PacketChunkData
     */
    void handleMultiBlockChange(SPacketMultiBlockChange packetIn);

    /**
     * Updates the worlds MapStorage with the specified MapData for the specified map-identifier and invokes a
     * MapItemRenderer for it
     */
    void handleMaps(SPacketMaps packetIn);

    /**
     * Verifies that the server and client are synchronized with respect to the inventory/container opened by the player
     * and confirms if it is the case.
     */
    void handleConfirmTransaction(SPacketConfirmTransaction packetIn);

    /**
     * Resets the ItemStack held in hand and closes the window that is opened
     */
    void handleCloseWindow(SPacketCloseWindow packetIn);

    /**
     * Handles the placement of a specified ItemStack in a specified container/inventory slot
     */
    void handleWindowItems(SPacketWindowItems packetIn);

    /**
     * Displays a GUI by ID. In order starting from id 0: Chest, Workbench, Furnace, Dispenser, Enchanting table,
     * Brewing stand, Villager merchant, Beacon, Anvil, Hopper, Dropper, Horse
     */
    void handleOpenWindow(SPacketOpenWindow packetIn);

    /**
     * Sets the progressbar of the opened window to the specified value
     */
    void handleWindowProperty(SPacketWindowProperty packetIn);

    /**
     * Handles pickin up an ItemStack or dropping one in your inventory or an open (non-creative) container
     */
    void handleSetSlot(SPacketSetSlot packetIn);

    /**
     * Handles packets that have room for a channel specification. Vanilla implemented channels are "MC|TrList" to
     * acquire a MerchantRecipeList trades for a villager merchant, "MC|Brand" which sets the server brand? on the
     * player instance and finally "MC|RPack" which the server uses to communicate the identifier of the default server
     * resourcepack for the client to load.
     */
    void handleCustomPayload(SPacketCustomPayload packetIn);

    /**
     * Closes the network channel
     */
    void handleDisconnect(SPacketDisconnect packetIn);

    /**
     * Retrieves the player identified by the packet, puts him to sleep if possible (and flags whether all players are
     * asleep)
     */
    void handleUseBed(SPacketUseBed packetIn);

    /**
     * Invokes the entities' handleUpdateHealth method which is implemented in LivingBase (hurt/death),
     * MinecartMobSpawner (spawn delay), FireworkRocket & MinecartTNT (explosion), IronGolem (throwing,...), Witch
     * (spawn particles), Zombie (villager transformation), Animal (breeding mode particles), Horse (breeding/smoke
     * particles), Sheep (...), Tameable (...), Villager (particles for breeding mode, angry and happy), Wolf (...)
     */
    void handleEntityStatus(SPacketEntityStatus packetIn);

    void handleEntityAttach(SPacketEntityAttach packetIn);

    void handleSetPassengers(SPacketSetPassengers packetIn);

    /**
     * Initiates a new explosion (sound, particles, drop spawn) for the affected blocks indicated by the packet.
     */
    void handleExplosion(SPacketExplosion packetIn);

    void handleChangeGameState(SPacketChangeGameState packetIn);

    void handleKeepAlive(SPacketKeepAlive packetIn);

    /**
     * Updates the specified chunk with the supplied data, marks it for re-rendering and lighting recalculation
     */
    void handleChunkData(SPacketChunkData packetIn);

    void processChunkUnload(SPacketUnloadChunk packetIn);

    void handleEffect(SPacketEffect packetIn);

    /**
     * Registers some server properties (gametype,hardcore-mode,terraintype,difficulty,player limit), creates a new
     * WorldClient and sets the player initial dimension
     */
    void handleJoinGame(SPacketJoinGame packetIn);

    /**
     * Updates the specified entity's position by the specified relative moment and absolute rotation. Note that
     * subclassing of the packet allows for the specification of a subset of this data (e.g. only rel. position, abs.
     * rotation or both).
     */
    void handleEntityMovement(SPacketEntity packetIn);

    void handlePlayerPosLook(SPacketPlayerPosLook packetIn);

    /**
     * Spawns a specified number of particles at the specified location with a randomized displacement according to
     * specified bounds
     */
    void handleParticles(SPacketParticles packetIn);

    void handlePlayerAbilities(SPacketPlayerAbilities packetIn);

    void handlePlayerListItem(SPacketPlayerListItem packetIn);

    /**
     * Locally eliminates the entities. Invoked by the server when the items are in fact destroyed, or the player is no
     * longer registered as required to monitor them. The latter  happens when distance between the player and item
     * increases beyond a certain treshold (typically the viewing distance)
     */
    void handleDestroyEntities(SPacketDestroyEntities packetIn);

    void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn);

    void handleRespawn(SPacketRespawn packetIn);

    /**
     * Updates the direction in which the specified entity is looking, normally this head rotation is independent of the
     * rotation of the entity itself
     */
    void handleEntityHeadLook(SPacketEntityHeadLook packetIn);

    /**
     * Updates which hotbar slot of the player is currently selected
     */
    void handleHeldItemChange(SPacketHeldItemChange packetIn);

    /**
     * Removes or sets the ScoreObjective to be displayed at a particular scoreboard position (list, sidebar, below
     * name)
     */
    void handleDisplayObjective(SPacketDisplayObjective packetIn);

    /**
     * Invoked when the server registers new proximate objects in your watchlist or when objects in your watchlist have
     * changed -> Registers any changes locally
     */
    void handleEntityMetadata(SPacketEntityMetadata packetIn);

    /**
     * Sets the velocity of the specified entity to the specified value
     */
    void handleEntityVelocity(SPacketEntityVelocity packetIn);

    void handleEntityEquipment(SPacketEntityEquipment packetIn);

    void handleSetExperience(SPacketSetExperience packetIn);

    void handleUpdateHealth(SPacketUpdateHealth packetIn);

    /**
     * Updates a team managed by the scoreboard: Create/Remove the team registration, Register/Remove the player-team-
     * memberships, Set team displayname/prefix/suffix and/or whether friendly fire is enabled
     */
    void handleTeams(SPacketTeams packetIn);

    /**
     * Either updates the score with a specified value or removes the score for an objective
     */
    void handleUpdateScore(SPacketUpdateScore packetIn);

    void handleSpawnPosition(SPacketSpawnPosition packetIn);

    void handleTimeUpdate(SPacketTimeUpdate packetIn);

    void handleSoundEffect(SPacketSoundEffect packetIn);

    void handleCustomSound(SPacketCustomSound packetIn);

    void handleCollectItem(SPacketCollectItem packetIn);

    /**
     * Updates an entity's position and rotation as specified by the packet
     */
    void handleEntityTeleport(SPacketEntityTeleport packetIn);

    /**
     * Updates en entity's attributes and their respective modifiers, which are used for speed bonusses (player
     * sprinting, animals fleeing, baby speed), weapon/tool attackDamage, hostiles followRange randomization, zombie
     * maxHealth and knockback resistance as well as reinforcement spawning chance.
     */
    void handleEntityProperties(SPacketEntityProperties packetIn);

    void handleEntityEffect(SPacketEntityEffect packetIn);

    void handleCombatEvent(SPacketCombatEvent packetIn);

    void handleServerDifficulty(SPacketServerDifficulty packetIn);

    void handleCamera(SPacketCamera packetIn);

    void handleWorldBorder(SPacketWorldBorder packetIn);

    void handleTitle(SPacketTitle packetIn);

    void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn);

    void handleResourcePack(SPacketResourcePackSend packetIn);

    void handleUpdateBossInfo(SPacketUpdateBossInfo packetIn);

    void handleCooldown(SPacketCooldown packetIn);

    void handleMoveVehicle(SPacketMoveVehicle packetIn);

    void handleAdvancementInfo(SPacketAdvancementInfo packetIn);

    void handleSelectAdvancementsTab(SPacketSelectAdvancementsTab packetIn);
}
