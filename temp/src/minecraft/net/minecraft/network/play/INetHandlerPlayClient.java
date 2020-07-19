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

public interface INetHandlerPlayClient extends INetHandler {
   void func_147235_a(SPacketSpawnObject var1);

   void func_147286_a(SPacketSpawnExperienceOrb var1);

   void func_147292_a(SPacketSpawnGlobalEntity var1);

   void func_147281_a(SPacketSpawnMob var1);

   void func_147291_a(SPacketScoreboardObjective var1);

   void func_147288_a(SPacketSpawnPainting var1);

   void func_147237_a(SPacketSpawnPlayer var1);

   void func_147279_a(SPacketAnimation var1);

   void func_147293_a(SPacketStatistics var1);

   void func_191980_a(SPacketRecipeBook var1);

   void func_147294_a(SPacketBlockBreakAnim var1);

   void func_147268_a(SPacketSignEditorOpen var1);

   void func_147273_a(SPacketUpdateTileEntity var1);

   void func_147261_a(SPacketBlockAction var1);

   void func_147234_a(SPacketBlockChange var1);

   void func_147251_a(SPacketChat var1);

   void func_147274_a(SPacketTabComplete var1);

   void func_147287_a(SPacketMultiBlockChange var1);

   void func_147264_a(SPacketMaps var1);

   void func_147239_a(SPacketConfirmTransaction var1);

   void func_147276_a(SPacketCloseWindow var1);

   void func_147241_a(SPacketWindowItems var1);

   void func_147265_a(SPacketOpenWindow var1);

   void func_147245_a(SPacketWindowProperty var1);

   void func_147266_a(SPacketSetSlot var1);

   void func_147240_a(SPacketCustomPayload var1);

   void func_147253_a(SPacketDisconnect var1);

   void func_147278_a(SPacketUseBed var1);

   void func_147236_a(SPacketEntityStatus var1);

   void func_147243_a(SPacketEntityAttach var1);

   void func_184328_a(SPacketSetPassengers var1);

   void func_147283_a(SPacketExplosion var1);

   void func_147252_a(SPacketChangeGameState var1);

   void func_147272_a(SPacketKeepAlive var1);

   void func_147263_a(SPacketChunkData var1);

   void func_184326_a(SPacketUnloadChunk var1);

   void func_147277_a(SPacketEffect var1);

   void func_147282_a(SPacketJoinGame var1);

   void func_147259_a(SPacketEntity var1);

   void func_184330_a(SPacketPlayerPosLook var1);

   void func_147289_a(SPacketParticles var1);

   void func_147270_a(SPacketPlayerAbilities var1);

   void func_147256_a(SPacketPlayerListItem var1);

   void func_147238_a(SPacketDestroyEntities var1);

   void func_147262_a(SPacketRemoveEntityEffect var1);

   void func_147280_a(SPacketRespawn var1);

   void func_147267_a(SPacketEntityHeadLook var1);

   void func_147257_a(SPacketHeldItemChange var1);

   void func_147254_a(SPacketDisplayObjective var1);

   void func_147284_a(SPacketEntityMetadata var1);

   void func_147244_a(SPacketEntityVelocity var1);

   void func_147242_a(SPacketEntityEquipment var1);

   void func_147295_a(SPacketSetExperience var1);

   void func_147249_a(SPacketUpdateHealth var1);

   void func_147247_a(SPacketTeams var1);

   void func_147250_a(SPacketUpdateScore var1);

   void func_147271_a(SPacketSpawnPosition var1);

   void func_147285_a(SPacketTimeUpdate var1);

   void func_184327_a(SPacketSoundEffect var1);

   void func_184329_a(SPacketCustomSound var1);

   void func_147246_a(SPacketCollectItem var1);

   void func_147275_a(SPacketEntityTeleport var1);

   void func_147290_a(SPacketEntityProperties var1);

   void func_147260_a(SPacketEntityEffect var1);

   void func_175098_a(SPacketCombatEvent var1);

   void func_175101_a(SPacketServerDifficulty var1);

   void func_175094_a(SPacketCamera var1);

   void func_175093_a(SPacketWorldBorder var1);

   void func_175099_a(SPacketTitle var1);

   void func_175096_a(SPacketPlayerListHeaderFooter var1);

   void func_175095_a(SPacketResourcePackSend var1);

   void func_184325_a(SPacketUpdateBossInfo var1);

   void func_184324_a(SPacketCooldown var1);

   void func_184323_a(SPacketMoveVehicle var1);

   void func_191981_a(SPacketAdvancementInfo var1);

   void func_194022_a(SPacketSelectAdvancementsTab var1);
}
