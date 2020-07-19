package net.minecraft.network.play;

import net.minecraft.network.INetHandler;
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

public interface INetHandlerPlayServer extends INetHandler {
   void func_175087_a(CPacketAnimation var1);

   void func_147354_a(CPacketChatMessage var1);

   void func_147341_a(CPacketTabComplete var1);

   void func_147342_a(CPacketClientStatus var1);

   void func_147352_a(CPacketClientSettings var1);

   void func_147339_a(CPacketConfirmTransaction var1);

   void func_147338_a(CPacketEnchantItem var1);

   void func_147351_a(CPacketClickWindow var1);

   void func_191985_a(CPacketRecipePlacement var1);

   void func_147356_a(CPacketCloseWindow var1);

   void func_147349_a(CPacketCustomPayload var1);

   void func_147340_a(CPacketUseEntity var1);

   void func_147353_a(CPacketKeepAlive var1);

   void func_147347_a(CPacketPlayer var1);

   void func_147348_a(CPacketPlayerAbilities var1);

   void func_147345_a(CPacketPlayerDigging var1);

   void func_147357_a(CPacketEntityAction var1);

   void func_147358_a(CPacketInput var1);

   void func_147355_a(CPacketHeldItemChange var1);

   void func_147344_a(CPacketCreativeInventoryAction var1);

   void func_147343_a(CPacketUpdateSign var1);

   void func_184337_a(CPacketPlayerTryUseItemOnBlock var1);

   void func_147346_a(CPacketPlayerTryUseItem var1);

   void func_175088_a(CPacketSpectate var1);

   void func_175086_a(CPacketResourcePackStatus var1);

   void func_184340_a(CPacketSteerBoat var1);

   void func_184338_a(CPacketVehicleMove var1);

   void func_184339_a(CPacketConfirmTeleport var1);

   void func_191984_a(CPacketRecipeInfo var1);

   void func_194027_a(CPacketSeenAdvancements var1);
}
