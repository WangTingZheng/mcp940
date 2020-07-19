package net.minecraft.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
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
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import org.apache.logging.log4j.LogManager;

public enum EnumConnectionState {
   HANDSHAKING(-1) {
      {
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
      }
   },
   PLAY(0) {
      {
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnObject.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnExperienceOrb.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnGlobalEntity.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnMob.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPainting.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPlayer.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketAnimation.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketStatistics.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketBlockBreakAnim.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUpdateTileEntity.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketBlockAction.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketBlockChange.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUpdateBossInfo.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketServerDifficulty.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketTabComplete.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketChat.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketMultiBlockChange.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketConfirmTransaction.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCloseWindow.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketOpenWindow.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketWindowItems.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketWindowProperty.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSetSlot.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCooldown.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCustomPayload.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCustomSound.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketDisconnect.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityStatus.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketExplosion.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUnloadChunk.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketChangeGameState.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketKeepAlive.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketChunkData.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEffect.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketParticles.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketJoinGame.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketMaps.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntity.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S15PacketEntityRelMove.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S17PacketEntityLookMove.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S16PacketEntityLook.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketMoveVehicle.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSignEditorOpen.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketPlayerAbilities.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCombatEvent.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListItem.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketPlayerPosLook.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUseBed.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketRecipeBook.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketDestroyEntities.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketRemoveEntityEffect.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketResourcePackSend.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketRespawn.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityHeadLook.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSelectAdvancementsTab.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketWorldBorder.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCamera.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketHeldItemChange.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketDisplayObjective.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityMetadata.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityAttach.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityVelocity.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityEquipment.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSetExperience.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUpdateHealth.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketScoreboardObjective.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSetPassengers.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketTeams.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketUpdateScore.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPosition.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketTimeUpdate.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketTitle.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketSoundEffect.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListHeaderFooter.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketCollectItem.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityTeleport.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketAdvancementInfo.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityProperties.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEntityEffect.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketConfirmTeleport.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketRecipePlacement.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketTabComplete.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketChatMessage.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketClientStatus.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketClientSettings.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketConfirmTransaction.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketEnchantItem.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketClickWindow.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketCloseWindow.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketCustomPayload.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketUseEntity.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketKeepAlive.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayer.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Position.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayer.PositionRotation.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Rotation.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketVehicleMove.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketSteerBoat.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayerAbilities.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayerDigging.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketEntityAction.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketInput.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketRecipeInfo.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketResourcePackStatus.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketSeenAdvancements.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketHeldItemChange.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketCreativeInventoryAction.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketUpdateSign.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketAnimation.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketSpectate.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItemOnBlock.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItem.class);
      }
   },
   STATUS(1) {
      {
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketServerQuery.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketServerInfo.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketPing.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketPong.class);
      }
   },
   LOGIN(2) {
      {
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, net.minecraft.network.login.server.SPacketDisconnect.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEncryptionRequest.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketLoginSuccess.class);
         this.func_179245_a(EnumPacketDirection.CLIENTBOUND, SPacketEnableCompression.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketLoginStart.class);
         this.func_179245_a(EnumPacketDirection.SERVERBOUND, CPacketEncryptionResponse.class);
      }
   };

   private static final EnumConnectionState[] field_150764_e = new EnumConnectionState[4];
   private static final Map<Class<? extends Packet<?>>, EnumConnectionState> field_150761_f = Maps.<Class<? extends Packet<?>>, EnumConnectionState>newHashMap();
   private final int field_150762_g;
   private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet<?>>>> field_179247_h;

   private EnumConnectionState(int p_i45152_3_) {
      this.field_179247_h = Maps.newEnumMap(EnumPacketDirection.class);
      this.field_150762_g = p_i45152_3_;
   }

   protected EnumConnectionState func_179245_a(EnumPacketDirection p_179245_1_, Class<? extends Packet<?>> p_179245_2_) {
      BiMap<Integer, Class<? extends Packet<?>>> bimap = (BiMap)this.field_179247_h.get(p_179245_1_);
      if (bimap == null) {
         bimap = HashBiMap.<Integer, Class<? extends Packet<?>>>create();
         this.field_179247_h.put(p_179245_1_, bimap);
      }

      if (bimap.containsValue(p_179245_2_)) {
         String s = p_179245_1_ + " packet " + p_179245_2_ + " is already known to ID " + bimap.inverse().get(p_179245_2_);
         LogManager.getLogger().fatal(s);
         throw new IllegalArgumentException(s);
      } else {
         bimap.put(Integer.valueOf(bimap.size()), p_179245_2_);
         return this;
      }
   }

   public Integer func_179246_a(EnumPacketDirection p_179246_1_, Packet<?> p_179246_2_) throws Exception {
      return (Integer)((BiMap)this.field_179247_h.get(p_179246_1_)).inverse().get(p_179246_2_.getClass());
   }

   @Nullable
   public Packet<?> func_179244_a(EnumPacketDirection p_179244_1_, int p_179244_2_) throws InstantiationException, IllegalAccessException {
      Class<? extends Packet<?>> oclass = (Class)((BiMap)this.field_179247_h.get(p_179244_1_)).get(Integer.valueOf(p_179244_2_));
      return oclass == null ? null : (Packet)oclass.newInstance();
   }

   public int func_150759_c() {
      return this.field_150762_g;
   }

   public static EnumConnectionState func_150760_a(int p_150760_0_) {
      return p_150760_0_ >= -1 && p_150760_0_ <= 2 ? field_150764_e[p_150760_0_ - -1] : null;
   }

   public static EnumConnectionState func_150752_a(Packet<?> p_150752_0_) {
      return field_150761_f.get(p_150752_0_.getClass());
   }

   static {
      for(EnumConnectionState enumconnectionstate : values()) {
         int i = enumconnectionstate.func_150759_c();
         if (i < -1 || i > 2) {
            throw new Error("Invalid protocol ID " + Integer.toString(i));
         }

         field_150764_e[i - -1] = enumconnectionstate;

         for(EnumPacketDirection enumpacketdirection : enumconnectionstate.field_179247_h.keySet()) {
            for(Class<? extends Packet<?>> oclass : ((BiMap)enumconnectionstate.field_179247_h.get(enumpacketdirection)).values()) {
               if (field_150761_f.containsKey(oclass) && field_150761_f.get(oclass) != enumconnectionstate) {
                  throw new Error("Packet " + oclass + " is already assigned to protocol " + field_150761_f.get(oclass) + " - can't reassign to " + enumconnectionstate);
               }

               try {
                  oclass.newInstance();
               } catch (Throwable var10) {
                  throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
               }

               field_150761_f.put(oclass, enumconnectionstate);
            }
         }
      }

   }
}
