package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleItemPickup;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.renderer.debug.DebugRendererNeighborsUpdate;
import net.minecraft.client.renderer.debug.DebugRendererPathfinding;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
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
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private static final Logger field_147301_d = LogManager.getLogger();
   private final NetworkManager field_147302_e;
   private final GameProfile field_175107_d;
   private final GuiScreen field_147307_j;
   private Minecraft field_147299_f;
   private WorldClient field_147300_g;
   private boolean field_147309_h;
   private final Map<UUID, NetworkPlayerInfo> field_147310_i = Maps.<UUID, NetworkPlayerInfo>newHashMap();
   public int field_147304_c = 20;
   private boolean field_147308_k;
   private final ClientAdvancementManager field_191983_k;
   private final Random field_147306_l = new Random();

   public NetHandlerPlayClient(Minecraft p_i46300_1_, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
      this.field_147299_f = p_i46300_1_;
      this.field_147307_j = p_i46300_2_;
      this.field_147302_e = p_i46300_3_;
      this.field_175107_d = p_i46300_4_;
      this.field_191983_k = new ClientAdvancementManager(p_i46300_1_);
   }

   public void func_147296_c() {
      this.field_147300_g = null;
   }

   public void func_147282_a(SPacketJoinGame p_147282_1_) {
      PacketThreadUtil.func_180031_a(p_147282_1_, this, this.field_147299_f);
      this.field_147299_f.field_71442_b = new PlayerControllerMP(this.field_147299_f, this);
      this.field_147300_g = new WorldClient(this, new WorldSettings(0L, p_147282_1_.func_149198_e(), false, p_147282_1_.func_149195_d(), p_147282_1_.func_149196_i()), p_147282_1_.func_149194_f(), p_147282_1_.func_149192_g(), this.field_147299_f.field_71424_I);
      this.field_147299_f.field_71474_y.field_74318_M = p_147282_1_.func_149192_g();
      this.field_147299_f.func_71403_a(this.field_147300_g);
      this.field_147299_f.field_71439_g.field_71093_bK = p_147282_1_.func_149194_f();
      this.field_147299_f.func_147108_a(new GuiDownloadTerrain(this));
      this.field_147299_f.field_71439_g.func_145769_d(p_147282_1_.func_149197_c());
      this.field_147304_c = p_147282_1_.func_149193_h();
      this.field_147299_f.field_71439_g.func_175150_k(p_147282_1_.func_179744_h());
      this.field_147299_f.field_71442_b.func_78746_a(p_147282_1_.func_149198_e());
      this.field_147299_f.field_71474_y.func_82879_c();
      this.field_147302_e.func_179290_a(new CPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).func_180714_a(ClientBrandRetriever.getClientModName())));
   }

   public void func_147235_a(SPacketSpawnObject p_147235_1_) {
      PacketThreadUtil.func_180031_a(p_147235_1_, this, this.field_147299_f);
      double d0 = p_147235_1_.func_186880_c();
      double d1 = p_147235_1_.func_186882_d();
      double d2 = p_147235_1_.func_186881_e();
      Entity entity = null;
      if (p_147235_1_.func_148993_l() == 10) {
         entity = EntityMinecart.func_184263_a(this.field_147300_g, d0, d1, d2, EntityMinecart.Type.func_184955_a(p_147235_1_.func_149009_m()));
      } else if (p_147235_1_.func_148993_l() == 90) {
         Entity entity1 = this.field_147300_g.func_73045_a(p_147235_1_.func_149009_m());
         if (entity1 instanceof EntityPlayer) {
            entity = new EntityFishHook(this.field_147300_g, (EntityPlayer)entity1, d0, d1, d2);
         }

         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 60) {
         entity = new EntityTippedArrow(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 91) {
         entity = new EntitySpectralArrow(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 61) {
         entity = new EntitySnowball(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 68) {
         entity = new EntityLlamaSpit(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
      } else if (p_147235_1_.func_148993_l() == 71) {
         entity = new EntityItemFrame(this.field_147300_g, new BlockPos(d0, d1, d2), EnumFacing.func_176731_b(p_147235_1_.func_149009_m()));
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 77) {
         entity = new EntityLeashKnot(this.field_147300_g, new BlockPos(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d1), MathHelper.func_76128_c(d2)));
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 65) {
         entity = new EntityEnderPearl(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 72) {
         entity = new EntityEnderEye(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 76) {
         entity = new EntityFireworkRocket(this.field_147300_g, d0, d1, d2, ItemStack.field_190927_a);
      } else if (p_147235_1_.func_148993_l() == 63) {
         entity = new EntityLargeFireball(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 93) {
         entity = new EntityDragonFireball(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 64) {
         entity = new EntitySmallFireball(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 66) {
         entity = new EntityWitherSkull(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 67) {
         entity = new EntityShulkerBullet(this.field_147300_g, d0, d1, d2, (double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 62) {
         entity = new EntityEgg(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 79) {
         entity = new EntityEvokerFangs(this.field_147300_g, d0, d1, d2, 0.0F, 0, (EntityLivingBase)null);
      } else if (p_147235_1_.func_148993_l() == 73) {
         entity = new EntityPotion(this.field_147300_g, d0, d1, d2, ItemStack.field_190927_a);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 75) {
         entity = new EntityExpBottle(this.field_147300_g, d0, d1, d2);
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 1) {
         entity = new EntityBoat(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 50) {
         entity = new EntityTNTPrimed(this.field_147300_g, d0, d1, d2, (EntityLivingBase)null);
      } else if (p_147235_1_.func_148993_l() == 78) {
         entity = new EntityArmorStand(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 51) {
         entity = new EntityEnderCrystal(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 2) {
         entity = new EntityItem(this.field_147300_g, d0, d1, d2);
      } else if (p_147235_1_.func_148993_l() == 70) {
         entity = new EntityFallingBlock(this.field_147300_g, d0, d1, d2, Block.func_176220_d(p_147235_1_.func_149009_m() & '\uffff'));
         p_147235_1_.func_149002_g(0);
      } else if (p_147235_1_.func_148993_l() == 3) {
         entity = new EntityAreaEffectCloud(this.field_147300_g, d0, d1, d2);
      }

      if (entity != null) {
         EntityTracker.func_187254_a(entity, d0, d1, d2);
         entity.field_70125_A = (float)(p_147235_1_.func_149008_j() * 360) / 256.0F;
         entity.field_70177_z = (float)(p_147235_1_.func_149006_k() * 360) / 256.0F;
         Entity[] aentity = entity.func_70021_al();
         if (aentity != null) {
            int i = p_147235_1_.func_149001_c() - entity.func_145782_y();

            for(Entity entity2 : aentity) {
               entity2.func_145769_d(entity2.func_145782_y() + i);
            }
         }

         entity.func_145769_d(p_147235_1_.func_149001_c());
         entity.func_184221_a(p_147235_1_.func_186879_b());
         this.field_147300_g.func_73027_a(p_147235_1_.func_149001_c(), entity);
         if (p_147235_1_.func_149009_m() > 0) {
            if (p_147235_1_.func_148993_l() == 60 || p_147235_1_.func_148993_l() == 91) {
               Entity entity3 = this.field_147300_g.func_73045_a(p_147235_1_.func_149009_m() - 1);
               if (entity3 instanceof EntityLivingBase && entity instanceof EntityArrow) {
                  ((EntityArrow)entity).field_70250_c = entity3;
               }
            }

            entity.func_70016_h((double)p_147235_1_.func_149010_g() / 8000.0D, (double)p_147235_1_.func_149004_h() / 8000.0D, (double)p_147235_1_.func_148999_i() / 8000.0D);
         }
      }

   }

   public void func_147286_a(SPacketSpawnExperienceOrb p_147286_1_) {
      PacketThreadUtil.func_180031_a(p_147286_1_, this, this.field_147299_f);
      double d0 = p_147286_1_.func_186885_b();
      double d1 = p_147286_1_.func_186886_c();
      double d2 = p_147286_1_.func_186884_d();
      Entity entity = new EntityXPOrb(this.field_147300_g, d0, d1, d2, p_147286_1_.func_148986_g());
      EntityTracker.func_187254_a(entity, d0, d1, d2);
      entity.field_70177_z = 0.0F;
      entity.field_70125_A = 0.0F;
      entity.func_145769_d(p_147286_1_.func_148985_c());
      this.field_147300_g.func_73027_a(p_147286_1_.func_148985_c(), entity);
   }

   public void func_147292_a(SPacketSpawnGlobalEntity p_147292_1_) {
      PacketThreadUtil.func_180031_a(p_147292_1_, this, this.field_147299_f);
      double d0 = p_147292_1_.func_186888_b();
      double d1 = p_147292_1_.func_186889_c();
      double d2 = p_147292_1_.func_186887_d();
      Entity entity = null;
      if (p_147292_1_.func_149053_g() == 1) {
         entity = new EntityLightningBolt(this.field_147300_g, d0, d1, d2, false);
      }

      if (entity != null) {
         EntityTracker.func_187254_a(entity, d0, d1, d2);
         entity.field_70177_z = 0.0F;
         entity.field_70125_A = 0.0F;
         entity.func_145769_d(p_147292_1_.func_149052_c());
         this.field_147300_g.func_72942_c(entity);
      }

   }

   public void func_147288_a(SPacketSpawnPainting p_147288_1_) {
      PacketThreadUtil.func_180031_a(p_147288_1_, this, this.field_147299_f);
      EntityPainting entitypainting = new EntityPainting(this.field_147300_g, p_147288_1_.func_179837_b(), p_147288_1_.func_179836_c(), p_147288_1_.func_148961_h());
      entitypainting.func_184221_a(p_147288_1_.func_186895_b());
      this.field_147300_g.func_73027_a(p_147288_1_.func_148965_c(), entitypainting);
   }

   public void func_147244_a(SPacketEntityVelocity p_147244_1_) {
      PacketThreadUtil.func_180031_a(p_147244_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147244_1_.func_149412_c());
      if (entity != null) {
         entity.func_70016_h((double)p_147244_1_.func_149411_d() / 8000.0D, (double)p_147244_1_.func_149410_e() / 8000.0D, (double)p_147244_1_.func_149409_f() / 8000.0D);
      }
   }

   public void func_147284_a(SPacketEntityMetadata p_147284_1_) {
      PacketThreadUtil.func_180031_a(p_147284_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147284_1_.func_149375_d());
      if (entity != null && p_147284_1_.func_149376_c() != null) {
         entity.func_184212_Q().func_187218_a(p_147284_1_.func_149376_c());
      }

   }

   public void func_147237_a(SPacketSpawnPlayer p_147237_1_) {
      PacketThreadUtil.func_180031_a(p_147237_1_, this, this.field_147299_f);
      double d0 = p_147237_1_.func_186898_d();
      double d1 = p_147237_1_.func_186897_e();
      double d2 = p_147237_1_.func_186899_f();
      float f = (float)(p_147237_1_.func_148941_i() * 360) / 256.0F;
      float f1 = (float)(p_147237_1_.func_148945_j() * 360) / 256.0F;
      EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(this.field_147299_f.field_71441_e, this.func_175102_a(p_147237_1_.func_179819_c()).func_178845_a());
      entityotherplayermp.field_70169_q = d0;
      entityotherplayermp.field_70142_S = d0;
      entityotherplayermp.field_70167_r = d1;
      entityotherplayermp.field_70137_T = d1;
      entityotherplayermp.field_70166_s = d2;
      entityotherplayermp.field_70136_U = d2;
      EntityTracker.func_187254_a(entityotherplayermp, d0, d1, d2);
      entityotherplayermp.func_70080_a(d0, d1, d2, f, f1);
      this.field_147300_g.func_73027_a(p_147237_1_.func_148943_d(), entityotherplayermp);
      List<EntityDataManager.DataEntry<?>> list = p_147237_1_.func_148944_c();
      if (list != null) {
         entityotherplayermp.func_184212_Q().func_187218_a(list);
      }

   }

   public void func_147275_a(SPacketEntityTeleport p_147275_1_) {
      PacketThreadUtil.func_180031_a(p_147275_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147275_1_.func_149451_c());
      if (entity != null) {
         double d0 = p_147275_1_.func_186982_b();
         double d1 = p_147275_1_.func_186983_c();
         double d2 = p_147275_1_.func_186981_d();
         EntityTracker.func_187254_a(entity, d0, d1, d2);
         if (!entity.func_184186_bw()) {
            float f = (float)(p_147275_1_.func_149450_g() * 360) / 256.0F;
            float f1 = (float)(p_147275_1_.func_149447_h() * 360) / 256.0F;
            if (Math.abs(entity.field_70165_t - d0) < 0.03125D && Math.abs(entity.field_70163_u - d1) < 0.015625D && Math.abs(entity.field_70161_v - d2) < 0.03125D) {
               entity.func_180426_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, f, f1, 0, true);
            } else {
               entity.func_180426_a(d0, d1, d2, f, f1, 3, true);
            }

            entity.field_70122_E = p_147275_1_.func_179697_g();
         }

      }
   }

   public void func_147257_a(SPacketHeldItemChange p_147257_1_) {
      PacketThreadUtil.func_180031_a(p_147257_1_, this, this.field_147299_f);
      if (InventoryPlayer.func_184435_e(p_147257_1_.func_149385_c())) {
         this.field_147299_f.field_71439_g.field_71071_by.field_70461_c = p_147257_1_.func_149385_c();
      }

   }

   public void func_147259_a(SPacketEntity p_147259_1_) {
      PacketThreadUtil.func_180031_a(p_147259_1_, this, this.field_147299_f);
      Entity entity = p_147259_1_.func_149065_a(this.field_147300_g);
      if (entity != null) {
         entity.field_70118_ct += (long)p_147259_1_.func_186952_a();
         entity.field_70117_cu += (long)p_147259_1_.func_186953_b();
         entity.field_70116_cv += (long)p_147259_1_.func_186951_c();
         double d0 = (double)entity.field_70118_ct / 4096.0D;
         double d1 = (double)entity.field_70117_cu / 4096.0D;
         double d2 = (double)entity.field_70116_cv / 4096.0D;
         if (!entity.func_184186_bw()) {
            float f = p_147259_1_.func_149060_h() ? (float)(p_147259_1_.func_149066_f() * 360) / 256.0F : entity.field_70177_z;
            float f1 = p_147259_1_.func_149060_h() ? (float)(p_147259_1_.func_149063_g() * 360) / 256.0F : entity.field_70125_A;
            entity.func_180426_a(d0, d1, d2, f, f1, 3, false);
            entity.field_70122_E = p_147259_1_.func_179742_g();
         }

      }
   }

   public void func_147267_a(SPacketEntityHeadLook p_147267_1_) {
      PacketThreadUtil.func_180031_a(p_147267_1_, this, this.field_147299_f);
      Entity entity = p_147267_1_.func_149381_a(this.field_147300_g);
      if (entity != null) {
         float f = (float)(p_147267_1_.func_149380_c() * 360) / 256.0F;
         entity.func_70034_d(f);
      }
   }

   public void func_147238_a(SPacketDestroyEntities p_147238_1_) {
      PacketThreadUtil.func_180031_a(p_147238_1_, this, this.field_147299_f);

      for(int i = 0; i < p_147238_1_.func_149098_c().length; ++i) {
         this.field_147300_g.func_73028_b(p_147238_1_.func_149098_c()[i]);
      }

   }

   public void func_184330_a(SPacketPlayerPosLook p_184330_1_) {
      PacketThreadUtil.func_180031_a(p_184330_1_, this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      double d0 = p_184330_1_.func_148932_c();
      double d1 = p_184330_1_.func_148928_d();
      double d2 = p_184330_1_.func_148933_e();
      float f = p_184330_1_.func_148931_f();
      float f1 = p_184330_1_.func_148930_g();
      if (p_184330_1_.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X)) {
         d0 += entityplayer.field_70165_t;
      } else {
         entityplayer.field_70159_w = 0.0D;
      }

      if (p_184330_1_.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
         d1 += entityplayer.field_70163_u;
      } else {
         entityplayer.field_70181_x = 0.0D;
      }

      if (p_184330_1_.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
         d2 += entityplayer.field_70161_v;
      } else {
         entityplayer.field_70179_y = 0.0D;
      }

      if (p_184330_1_.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
         f1 += entityplayer.field_70125_A;
      }

      if (p_184330_1_.func_179834_f().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
         f += entityplayer.field_70177_z;
      }

      entityplayer.func_70080_a(d0, d1, d2, f, f1);
      this.field_147302_e.func_179290_a(new CPacketConfirmTeleport(p_184330_1_.func_186965_f()));
      this.field_147302_e.func_179290_a(new CPacketPlayer.PositionRotation(entityplayer.field_70165_t, entityplayer.func_174813_aQ().field_72338_b, entityplayer.field_70161_v, entityplayer.field_70177_z, entityplayer.field_70125_A, false));
      if (!this.field_147309_h) {
         this.field_147299_f.field_71439_g.field_70169_q = this.field_147299_f.field_71439_g.field_70165_t;
         this.field_147299_f.field_71439_g.field_70167_r = this.field_147299_f.field_71439_g.field_70163_u;
         this.field_147299_f.field_71439_g.field_70166_s = this.field_147299_f.field_71439_g.field_70161_v;
         this.field_147309_h = true;
         this.field_147299_f.func_147108_a((GuiScreen)null);
      }

   }

   public void func_147287_a(SPacketMultiBlockChange p_147287_1_) {
      PacketThreadUtil.func_180031_a(p_147287_1_, this, this.field_147299_f);

      for(SPacketMultiBlockChange.BlockUpdateData spacketmultiblockchange$blockupdatedata : p_147287_1_.func_179844_a()) {
         this.field_147300_g.func_180503_b(spacketmultiblockchange$blockupdatedata.func_180090_a(), spacketmultiblockchange$blockupdatedata.func_180088_c());
      }

   }

   public void func_147263_a(SPacketChunkData p_147263_1_) {
      PacketThreadUtil.func_180031_a(p_147263_1_, this, this.field_147299_f);
      if (p_147263_1_.func_149274_i()) {
         this.field_147300_g.func_73025_a(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f(), true);
      }

      this.field_147300_g.func_73031_a(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
      Chunk chunk = this.field_147300_g.func_72964_e(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f());
      chunk.func_186033_a(p_147263_1_.func_186946_a(), p_147263_1_.func_149276_g(), p_147263_1_.func_149274_i());
      this.field_147300_g.func_147458_c(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
      if (!p_147263_1_.func_149274_i() || !(this.field_147300_g.field_73011_w instanceof WorldProviderSurface)) {
         chunk.func_76613_n();
      }

      for(NBTTagCompound nbttagcompound : p_147263_1_.func_189554_f()) {
         BlockPos blockpos = new BlockPos(nbttagcompound.func_74762_e("x"), nbttagcompound.func_74762_e("y"), nbttagcompound.func_74762_e("z"));
         TileEntity tileentity = this.field_147300_g.func_175625_s(blockpos);
         if (tileentity != null) {
            tileentity.func_145839_a(nbttagcompound);
         }
      }

   }

   public void func_184326_a(SPacketUnloadChunk p_184326_1_) {
      PacketThreadUtil.func_180031_a(p_184326_1_, this, this.field_147299_f);
      this.field_147300_g.func_73025_a(p_184326_1_.func_186940_a(), p_184326_1_.func_186941_b(), false);
   }

   public void func_147234_a(SPacketBlockChange p_147234_1_) {
      PacketThreadUtil.func_180031_a(p_147234_1_, this, this.field_147299_f);
      this.field_147300_g.func_180503_b(p_147234_1_.func_179827_b(), p_147234_1_.func_180728_a());
   }

   public void func_147253_a(SPacketDisconnect p_147253_1_) {
      this.field_147302_e.func_150718_a(p_147253_1_.func_149165_c());
   }

   public void func_147231_a(ITextComponent p_147231_1_) {
      this.field_147299_f.func_71403_a((WorldClient)null);
      if (this.field_147307_j != null) {
         if (this.field_147307_j instanceof GuiScreenRealmsProxy) {
            this.field_147299_f.func_147108_a((new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.field_147307_j).func_154321_a(), "disconnect.lost", p_147231_1_)).getProxy());
         } else {
            this.field_147299_f.func_147108_a(new GuiDisconnected(this.field_147307_j, "disconnect.lost", p_147231_1_));
         }
      } else {
         this.field_147299_f.func_147108_a(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", p_147231_1_));
      }

   }

   public void func_147297_a(Packet<?> p_147297_1_) {
      this.field_147302_e.func_179290_a(p_147297_1_);
   }

   public void func_147246_a(SPacketCollectItem p_147246_1_) {
      PacketThreadUtil.func_180031_a(p_147246_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147246_1_.func_149354_c());
      EntityLivingBase entitylivingbase = (EntityLivingBase)this.field_147300_g.func_73045_a(p_147246_1_.func_149353_d());
      if (entitylivingbase == null) {
         entitylivingbase = this.field_147299_f.field_71439_g;
      }

      if (entity != null) {
         if (entity instanceof EntityXPOrb) {
            this.field_147300_g.func_184134_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187604_bf, SoundCategory.PLAYERS, 0.1F, (this.field_147306_l.nextFloat() - this.field_147306_l.nextFloat()) * 0.35F + 0.9F, false);
         } else {
            this.field_147300_g.func_184134_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187638_cR, SoundCategory.PLAYERS, 0.2F, (this.field_147306_l.nextFloat() - this.field_147306_l.nextFloat()) * 1.4F + 2.0F, false);
         }

         if (entity instanceof EntityItem) {
            ((EntityItem)entity).func_92059_d().func_190920_e(p_147246_1_.func_191208_c());
         }

         this.field_147299_f.field_71452_i.func_78873_a(new ParticleItemPickup(this.field_147300_g, entity, entitylivingbase, 0.5F));
         this.field_147300_g.func_73028_b(p_147246_1_.func_149354_c());
      }

   }

   public void func_147251_a(SPacketChat p_147251_1_) {
      PacketThreadUtil.func_180031_a(p_147251_1_, this, this.field_147299_f);
      this.field_147299_f.field_71456_v.func_191742_a(p_147251_1_.func_192590_c(), p_147251_1_.func_148915_c());
   }

   public void func_147279_a(SPacketAnimation p_147279_1_) {
      PacketThreadUtil.func_180031_a(p_147279_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147279_1_.func_148978_c());
      if (entity != null) {
         if (p_147279_1_.func_148977_d() == 0) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
            entitylivingbase.func_184609_a(EnumHand.MAIN_HAND);
         } else if (p_147279_1_.func_148977_d() == 3) {
            EntityLivingBase entitylivingbase1 = (EntityLivingBase)entity;
            entitylivingbase1.func_184609_a(EnumHand.OFF_HAND);
         } else if (p_147279_1_.func_148977_d() == 1) {
            entity.func_70057_ab();
         } else if (p_147279_1_.func_148977_d() == 2) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            entityplayer.func_70999_a(false, false, false);
         } else if (p_147279_1_.func_148977_d() == 4) {
            this.field_147299_f.field_71452_i.func_178926_a(entity, EnumParticleTypes.CRIT);
         } else if (p_147279_1_.func_148977_d() == 5) {
            this.field_147299_f.field_71452_i.func_178926_a(entity, EnumParticleTypes.CRIT_MAGIC);
         }

      }
   }

   public void func_147278_a(SPacketUseBed p_147278_1_) {
      PacketThreadUtil.func_180031_a(p_147278_1_, this, this.field_147299_f);
      p_147278_1_.func_149091_a(this.field_147300_g).func_180469_a(p_147278_1_.func_179798_a());
   }

   public void func_147281_a(SPacketSpawnMob p_147281_1_) {
      PacketThreadUtil.func_180031_a(p_147281_1_, this, this.field_147299_f);
      double d0 = p_147281_1_.func_186891_e();
      double d1 = p_147281_1_.func_186892_f();
      double d2 = p_147281_1_.func_186893_g();
      float f = (float)(p_147281_1_.func_149028_l() * 360) / 256.0F;
      float f1 = (float)(p_147281_1_.func_149030_m() * 360) / 256.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.func_75616_a(p_147281_1_.func_149025_e(), this.field_147299_f.field_71441_e);
      if (entitylivingbase != null) {
         EntityTracker.func_187254_a(entitylivingbase, d0, d1, d2);
         entitylivingbase.field_70761_aq = (float)(p_147281_1_.func_149032_n() * 360) / 256.0F;
         entitylivingbase.field_70759_as = (float)(p_147281_1_.func_149032_n() * 360) / 256.0F;
         Entity[] aentity = entitylivingbase.func_70021_al();
         if (aentity != null) {
            int i = p_147281_1_.func_149024_d() - entitylivingbase.func_145782_y();

            for(Entity entity : aentity) {
               entity.func_145769_d(entity.func_145782_y() + i);
            }
         }

         entitylivingbase.func_145769_d(p_147281_1_.func_149024_d());
         entitylivingbase.func_184221_a(p_147281_1_.func_186890_c());
         entitylivingbase.func_70080_a(d0, d1, d2, f, f1);
         entitylivingbase.field_70159_w = (double)((float)p_147281_1_.func_149026_i() / 8000.0F);
         entitylivingbase.field_70181_x = (double)((float)p_147281_1_.func_149033_j() / 8000.0F);
         entitylivingbase.field_70179_y = (double)((float)p_147281_1_.func_149031_k() / 8000.0F);
         this.field_147300_g.func_73027_a(p_147281_1_.func_149024_d(), entitylivingbase);
         List<EntityDataManager.DataEntry<?>> list = p_147281_1_.func_149027_c();
         if (list != null) {
            entitylivingbase.func_184212_Q().func_187218_a(list);
         }
      } else {
         field_147301_d.warn("Skipping Entity with id {}", (int)p_147281_1_.func_149025_e());
      }

   }

   public void func_147285_a(SPacketTimeUpdate p_147285_1_) {
      PacketThreadUtil.func_180031_a(p_147285_1_, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_82738_a(p_147285_1_.func_149366_c());
      this.field_147299_f.field_71441_e.func_72877_b(p_147285_1_.func_149365_d());
   }

   public void func_147271_a(SPacketSpawnPosition p_147271_1_) {
      PacketThreadUtil.func_180031_a(p_147271_1_, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_180473_a(p_147271_1_.func_179800_a(), true);
      this.field_147299_f.field_71441_e.func_72912_H().func_176143_a(p_147271_1_.func_179800_a());
   }

   public void func_184328_a(SPacketSetPassengers p_184328_1_) {
      PacketThreadUtil.func_180031_a(p_184328_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_184328_1_.func_186972_b());
      if (entity == null) {
         field_147301_d.warn("Received passengers for unknown entity");
      } else {
         boolean flag = entity.func_184215_y(this.field_147299_f.field_71439_g);
         entity.func_184226_ay();

         for(int i : p_184328_1_.func_186971_a()) {
            Entity entity1 = this.field_147300_g.func_73045_a(i);
            if (entity1 != null) {
               entity1.func_184205_a(entity, true);
               if (entity1 == this.field_147299_f.field_71439_g && !flag) {
                  this.field_147299_f.field_71456_v.func_110326_a(I18n.func_135052_a("mount.onboard", GameSettings.func_74298_c(this.field_147299_f.field_71474_y.field_74311_E.func_151463_i())), false);
               }
            }
         }

      }
   }

   public void func_147243_a(SPacketEntityAttach p_147243_1_) {
      PacketThreadUtil.func_180031_a(p_147243_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147243_1_.func_149403_d());
      Entity entity1 = this.field_147300_g.func_73045_a(p_147243_1_.func_149402_e());
      if (entity instanceof EntityLiving) {
         if (entity1 != null) {
            ((EntityLiving)entity).func_110162_b(entity1, false);
         } else {
            ((EntityLiving)entity).func_110160_i(false, false);
         }
      }

   }

   public void func_147236_a(SPacketEntityStatus p_147236_1_) {
      PacketThreadUtil.func_180031_a(p_147236_1_, this, this.field_147299_f);
      Entity entity = p_147236_1_.func_149161_a(this.field_147300_g);
      if (entity != null) {
         if (p_147236_1_.func_149160_c() == 21) {
            this.field_147299_f.func_147118_V().func_147682_a(new GuardianSound((EntityGuardian)entity));
         } else if (p_147236_1_.func_149160_c() == 35) {
            int i = 40;
            this.field_147299_f.field_71452_i.func_191271_a(entity, EnumParticleTypes.TOTEM, 30);
            this.field_147300_g.func_184134_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_191263_gW, entity.func_184176_by(), 1.0F, 1.0F, false);
            if (entity == this.field_147299_f.field_71439_g) {
               this.field_147299_f.field_71460_t.func_190565_a(new ItemStack(Items.field_190929_cY));
            }
         } else {
            entity.func_70103_a(p_147236_1_.func_149160_c());
         }
      }

   }

   public void func_147249_a(SPacketUpdateHealth p_147249_1_) {
      PacketThreadUtil.func_180031_a(p_147249_1_, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_71150_b(p_147249_1_.func_149332_c());
      this.field_147299_f.field_71439_g.func_71024_bL().func_75114_a(p_147249_1_.func_149330_d());
      this.field_147299_f.field_71439_g.func_71024_bL().func_75119_b(p_147249_1_.func_149331_e());
   }

   public void func_147295_a(SPacketSetExperience p_147295_1_) {
      PacketThreadUtil.func_180031_a(p_147295_1_, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_71152_a(p_147295_1_.func_149397_c(), p_147295_1_.func_149396_d(), p_147295_1_.func_149395_e());
   }

   public void func_147280_a(SPacketRespawn p_147280_1_) {
      PacketThreadUtil.func_180031_a(p_147280_1_, this, this.field_147299_f);
      if (p_147280_1_.func_149082_c() != this.field_147299_f.field_71439_g.field_71093_bK) {
         this.field_147309_h = false;
         Scoreboard scoreboard = this.field_147300_g.func_96441_U();
         this.field_147300_g = new WorldClient(this, new WorldSettings(0L, p_147280_1_.func_149083_e(), false, this.field_147299_f.field_71441_e.func_72912_H().func_76093_s(), p_147280_1_.func_149080_f()), p_147280_1_.func_149082_c(), p_147280_1_.func_149081_d(), this.field_147299_f.field_71424_I);
         this.field_147300_g.func_96443_a(scoreboard);
         this.field_147299_f.func_71403_a(this.field_147300_g);
         this.field_147299_f.field_71439_g.field_71093_bK = p_147280_1_.func_149082_c();
         this.field_147299_f.func_147108_a(new GuiDownloadTerrain(this));
      }

      this.field_147299_f.func_71354_a(p_147280_1_.func_149082_c());
      this.field_147299_f.field_71442_b.func_78746_a(p_147280_1_.func_149083_e());
   }

   public void func_147283_a(SPacketExplosion p_147283_1_) {
      PacketThreadUtil.func_180031_a(p_147283_1_, this, this.field_147299_f);
      Explosion explosion = new Explosion(this.field_147299_f.field_71441_e, (Entity)null, p_147283_1_.func_149148_f(), p_147283_1_.func_149143_g(), p_147283_1_.func_149145_h(), p_147283_1_.func_149146_i(), p_147283_1_.func_149150_j());
      explosion.func_77279_a(true);
      this.field_147299_f.field_71439_g.field_70159_w += (double)p_147283_1_.func_149149_c();
      this.field_147299_f.field_71439_g.field_70181_x += (double)p_147283_1_.func_149144_d();
      this.field_147299_f.field_71439_g.field_70179_y += (double)p_147283_1_.func_149147_e();
   }

   public void func_147265_a(SPacketOpenWindow p_147265_1_) {
      PacketThreadUtil.func_180031_a(p_147265_1_, this, this.field_147299_f);
      EntityPlayerSP entityplayersp = this.field_147299_f.field_71439_g;
      if ("minecraft:container".equals(p_147265_1_.func_148902_e())) {
         entityplayersp.func_71007_a(new InventoryBasic(p_147265_1_.func_179840_c(), p_147265_1_.func_148898_f()));
         entityplayersp.field_71070_bA.field_75152_c = p_147265_1_.func_148901_c();
      } else if ("minecraft:villager".equals(p_147265_1_.func_148902_e())) {
         entityplayersp.func_180472_a(new NpcMerchant(entityplayersp, p_147265_1_.func_179840_c()));
         entityplayersp.field_71070_bA.field_75152_c = p_147265_1_.func_148901_c();
      } else if ("EntityHorse".equals(p_147265_1_.func_148902_e())) {
         Entity entity = this.field_147300_g.func_73045_a(p_147265_1_.func_148897_h());
         if (entity instanceof AbstractHorse) {
            entityplayersp.func_184826_a((AbstractHorse)entity, new ContainerHorseChest(p_147265_1_.func_179840_c(), p_147265_1_.func_148898_f()));
            entityplayersp.field_71070_bA.field_75152_c = p_147265_1_.func_148901_c();
         }
      } else if (!p_147265_1_.func_148900_g()) {
         entityplayersp.func_180468_a(new LocalBlockIntercommunication(p_147265_1_.func_148902_e(), p_147265_1_.func_179840_c()));
         entityplayersp.field_71070_bA.field_75152_c = p_147265_1_.func_148901_c();
      } else {
         IInventory iinventory = new ContainerLocalMenu(p_147265_1_.func_148902_e(), p_147265_1_.func_179840_c(), p_147265_1_.func_148898_f());
         entityplayersp.func_71007_a(iinventory);
         entityplayersp.field_71070_bA.field_75152_c = p_147265_1_.func_148901_c();
      }

   }

   public void func_147266_a(SPacketSetSlot p_147266_1_) {
      PacketThreadUtil.func_180031_a(p_147266_1_, this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      ItemStack itemstack = p_147266_1_.func_149174_e();
      int i = p_147266_1_.func_149173_d();
      this.field_147299_f.func_193032_ao().func_193301_a(itemstack);
      if (p_147266_1_.func_149175_c() == -1) {
         entityplayer.field_71071_by.func_70437_b(itemstack);
      } else if (p_147266_1_.func_149175_c() == -2) {
         entityplayer.field_71071_by.func_70299_a(i, itemstack);
      } else {
         boolean flag = false;
         if (this.field_147299_f.field_71462_r instanceof GuiContainerCreative) {
            GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.field_147299_f.field_71462_r;
            flag = guicontainercreative.func_147056_g() != CreativeTabs.field_78036_m.func_78021_a();
         }

         if (p_147266_1_.func_149175_c() == 0 && p_147266_1_.func_149173_d() >= 36 && i < 45) {
            if (!itemstack.func_190926_b()) {
               ItemStack itemstack1 = entityplayer.field_71069_bz.func_75139_a(i).func_75211_c();
               if (itemstack1.func_190926_b() || itemstack1.func_190916_E() < itemstack.func_190916_E()) {
                  itemstack.func_190915_d(5);
               }
            }

            entityplayer.field_71069_bz.func_75141_a(i, itemstack);
         } else if (p_147266_1_.func_149175_c() == entityplayer.field_71070_bA.field_75152_c && (p_147266_1_.func_149175_c() != 0 || !flag)) {
            entityplayer.field_71070_bA.func_75141_a(i, itemstack);
         }
      }

   }

   public void func_147239_a(SPacketConfirmTransaction p_147239_1_) {
      PacketThreadUtil.func_180031_a(p_147239_1_, this, this.field_147299_f);
      Container container = null;
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      if (p_147239_1_.func_148889_c() == 0) {
         container = entityplayer.field_71069_bz;
      } else if (p_147239_1_.func_148889_c() == entityplayer.field_71070_bA.field_75152_c) {
         container = entityplayer.field_71070_bA;
      }

      if (container != null && !p_147239_1_.func_148888_e()) {
         this.func_147297_a(new CPacketConfirmTransaction(p_147239_1_.func_148889_c(), p_147239_1_.func_148890_d(), true));
      }

   }

   public void func_147241_a(SPacketWindowItems p_147241_1_) {
      PacketThreadUtil.func_180031_a(p_147241_1_, this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      if (p_147241_1_.func_148911_c() == 0) {
         entityplayer.field_71069_bz.func_190896_a(p_147241_1_.func_148910_d());
      } else if (p_147241_1_.func_148911_c() == entityplayer.field_71070_bA.field_75152_c) {
         entityplayer.field_71070_bA.func_190896_a(p_147241_1_.func_148910_d());
      }

   }

   public void func_147268_a(SPacketSignEditorOpen p_147268_1_) {
      PacketThreadUtil.func_180031_a(p_147268_1_, this, this.field_147299_f);
      TileEntity tileentity = this.field_147300_g.func_175625_s(p_147268_1_.func_179777_a());
      if (!(tileentity instanceof TileEntitySign)) {
         tileentity = new TileEntitySign();
         tileentity.func_145834_a(this.field_147300_g);
         tileentity.func_174878_a(p_147268_1_.func_179777_a());
      }

      this.field_147299_f.field_71439_g.func_175141_a((TileEntitySign)tileentity);
   }

   public void func_147273_a(SPacketUpdateTileEntity p_147273_1_) {
      PacketThreadUtil.func_180031_a(p_147273_1_, this, this.field_147299_f);
      if (this.field_147299_f.field_71441_e.func_175667_e(p_147273_1_.func_179823_a())) {
         TileEntity tileentity = this.field_147299_f.field_71441_e.func_175625_s(p_147273_1_.func_179823_a());
         int i = p_147273_1_.func_148853_f();
         boolean flag = i == 2 && tileentity instanceof TileEntityCommandBlock;
         if (i == 1 && tileentity instanceof TileEntityMobSpawner || flag || i == 3 && tileentity instanceof TileEntityBeacon || i == 4 && tileentity instanceof TileEntitySkull || i == 5 && tileentity instanceof TileEntityFlowerPot || i == 6 && tileentity instanceof TileEntityBanner || i == 7 && tileentity instanceof TileEntityStructure || i == 8 && tileentity instanceof TileEntityEndGateway || i == 9 && tileentity instanceof TileEntitySign || i == 10 && tileentity instanceof TileEntityShulkerBox || i == 11 && tileentity instanceof TileEntityBed) {
            tileentity.func_145839_a(p_147273_1_.func_148857_g());
         }

         if (flag && this.field_147299_f.field_71462_r instanceof GuiCommandBlock) {
            ((GuiCommandBlock)this.field_147299_f.field_71462_r).func_184075_a();
         }
      }

   }

   public void func_147245_a(SPacketWindowProperty p_147245_1_) {
      PacketThreadUtil.func_180031_a(p_147245_1_, this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      if (entityplayer.field_71070_bA != null && entityplayer.field_71070_bA.field_75152_c == p_147245_1_.func_149182_c()) {
         entityplayer.field_71070_bA.func_75137_b(p_147245_1_.func_149181_d(), p_147245_1_.func_149180_e());
      }

   }

   public void func_147242_a(SPacketEntityEquipment p_147242_1_) {
      PacketThreadUtil.func_180031_a(p_147242_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147242_1_.func_149389_d());
      if (entity != null) {
         entity.func_184201_a(p_147242_1_.func_186969_c(), p_147242_1_.func_149390_c());
      }

   }

   public void func_147276_a(SPacketCloseWindow p_147276_1_) {
      PacketThreadUtil.func_180031_a(p_147276_1_, this, this.field_147299_f);
      this.field_147299_f.field_71439_g.func_175159_q();
   }

   public void func_147261_a(SPacketBlockAction p_147261_1_) {
      PacketThreadUtil.func_180031_a(p_147261_1_, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_175641_c(p_147261_1_.func_179825_a(), p_147261_1_.func_148868_c(), p_147261_1_.func_148869_g(), p_147261_1_.func_148864_h());
   }

   public void func_147294_a(SPacketBlockBreakAnim p_147294_1_) {
      PacketThreadUtil.func_180031_a(p_147294_1_, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_175715_c(p_147294_1_.func_148845_c(), p_147294_1_.func_179821_b(), p_147294_1_.func_148846_g());
   }

   public void func_147252_a(SPacketChangeGameState p_147252_1_) {
      PacketThreadUtil.func_180031_a(p_147252_1_, this, this.field_147299_f);
      EntityPlayer entityplayer = this.field_147299_f.field_71439_g;
      int i = p_147252_1_.func_149138_c();
      float f = p_147252_1_.func_149137_d();
      int j = MathHelper.func_76141_d(f + 0.5F);
      if (i >= 0 && i < SPacketChangeGameState.field_149142_a.length && SPacketChangeGameState.field_149142_a[i] != null) {
         entityplayer.func_146105_b(new TextComponentTranslation(SPacketChangeGameState.field_149142_a[i], new Object[0]), false);
      }

      if (i == 1) {
         this.field_147300_g.func_72912_H().func_76084_b(true);
         this.field_147300_g.func_72894_k(0.0F);
      } else if (i == 2) {
         this.field_147300_g.func_72912_H().func_76084_b(false);
         this.field_147300_g.func_72894_k(1.0F);
      } else if (i == 3) {
         this.field_147299_f.field_71442_b.func_78746_a(GameType.func_77146_a(j));
      } else if (i == 4) {
         if (j == 0) {
            this.field_147299_f.field_71439_g.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            this.field_147299_f.func_147108_a(new GuiDownloadTerrain(this));
         } else if (j == 1) {
            this.field_147299_f.func_147108_a(new GuiWinGame(true, () -> {
               this.field_147299_f.field_71439_g.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            }));
         }
      } else if (i == 5) {
         GameSettings gamesettings = this.field_147299_f.field_71474_y;
         if (f == 0.0F) {
            this.field_147299_f.func_147108_a(new GuiScreenDemo());
         } else if (f == 101.0F) {
            this.field_147299_f.field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("demo.help.movement", new Object[]{GameSettings.func_74298_c(gamesettings.field_74351_w.func_151463_i()), GameSettings.func_74298_c(gamesettings.field_74370_x.func_151463_i()), GameSettings.func_74298_c(gamesettings.field_74368_y.func_151463_i()), GameSettings.func_74298_c(gamesettings.field_74366_z.func_151463_i())}));
         } else if (f == 102.0F) {
            this.field_147299_f.field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("demo.help.jump", new Object[]{GameSettings.func_74298_c(gamesettings.field_74314_A.func_151463_i())}));
         } else if (f == 103.0F) {
            this.field_147299_f.field_71456_v.func_146158_b().func_146227_a(new TextComponentTranslation("demo.help.inventory", new Object[]{GameSettings.func_74298_c(gamesettings.field_151445_Q.func_151463_i())}));
         }
      } else if (i == 6) {
         this.field_147300_g.func_184148_a(entityplayer, entityplayer.field_70165_t, entityplayer.field_70163_u + (double)entityplayer.func_70047_e(), entityplayer.field_70161_v, SoundEvents.field_187734_u, SoundCategory.PLAYERS, 0.18F, 0.45F);
      } else if (i == 7) {
         this.field_147300_g.func_72894_k(f);
      } else if (i == 8) {
         this.field_147300_g.func_147442_i(f);
      } else if (i == 10) {
         this.field_147300_g.func_175688_a(EnumParticleTypes.MOB_APPEARANCE, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
         this.field_147300_g.func_184148_a(entityplayer, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, SoundEvents.field_187514_aD, SoundCategory.HOSTILE, 1.0F, 1.0F);
      }

   }

   public void func_147264_a(SPacketMaps p_147264_1_) {
      PacketThreadUtil.func_180031_a(p_147264_1_, this, this.field_147299_f);
      MapItemRenderer mapitemrenderer = this.field_147299_f.field_71460_t.func_147701_i();
      MapData mapdata = ItemMap.func_150912_a(p_147264_1_.func_149188_c(), this.field_147299_f.field_71441_e);
      if (mapdata == null) {
         String s = "map_" + p_147264_1_.func_149188_c();
         mapdata = new MapData(s);
         if (mapitemrenderer.func_191205_a(s) != null) {
            MapData mapdata1 = mapitemrenderer.func_191207_a(mapitemrenderer.func_191205_a(s));
            if (mapdata1 != null) {
               mapdata = mapdata1;
            }
         }

         this.field_147299_f.field_71441_e.func_72823_a(s, mapdata);
      }

      p_147264_1_.func_179734_a(mapdata);
      mapitemrenderer.func_148246_a(mapdata);
   }

   public void func_147277_a(SPacketEffect p_147277_1_) {
      PacketThreadUtil.func_180031_a(p_147277_1_, this, this.field_147299_f);
      if (p_147277_1_.func_149244_c()) {
         this.field_147299_f.field_71441_e.func_175669_a(p_147277_1_.func_149242_d(), p_147277_1_.func_179746_d(), p_147277_1_.func_149241_e());
      } else {
         this.field_147299_f.field_71441_e.func_175718_b(p_147277_1_.func_149242_d(), p_147277_1_.func_179746_d(), p_147277_1_.func_149241_e());
      }

   }

   public void func_191981_a(SPacketAdvancementInfo p_191981_1_) {
      PacketThreadUtil.func_180031_a(p_191981_1_, this, this.field_147299_f);
      this.field_191983_k.func_192799_a(p_191981_1_);
   }

   public void func_194022_a(SPacketSelectAdvancementsTab p_194022_1_) {
      PacketThreadUtil.func_180031_a(p_194022_1_, this, this.field_147299_f);
      ResourceLocation resourcelocation = p_194022_1_.func_194154_a();
      if (resourcelocation == null) {
         this.field_191983_k.func_194230_a((Advancement)null, false);
      } else {
         Advancement advancement = this.field_191983_k.func_194229_a().func_192084_a(resourcelocation);
         this.field_191983_k.func_194230_a(advancement, false);
      }

   }

   public void func_147293_a(SPacketStatistics p_147293_1_) {
      PacketThreadUtil.func_180031_a(p_147293_1_, this, this.field_147299_f);

      for(Entry<StatBase, Integer> entry : p_147293_1_.func_148974_c().entrySet()) {
         StatBase statbase = entry.getKey();
         int k = ((Integer)entry.getValue()).intValue();
         this.field_147299_f.field_71439_g.func_146107_m().func_150873_a(this.field_147299_f.field_71439_g, statbase, k);
      }

      this.field_147308_k = true;
      if (this.field_147299_f.field_71462_r instanceof IProgressMeter) {
         ((IProgressMeter)this.field_147299_f.field_71462_r).func_193026_g();
      }

   }

   public void func_191980_a(SPacketRecipeBook p_191980_1_) {
      RecipeBook recipebook;
      PacketThreadUtil.func_180031_a(p_191980_1_, this, this.field_147299_f);
      recipebook = this.field_147299_f.field_71439_g.func_192035_E();
      recipebook.func_192813_a(p_191980_1_.func_192593_c());
      recipebook.func_192810_b(p_191980_1_.func_192594_d());
      SPacketRecipeBook.State spacketrecipebook$state = p_191980_1_.func_194151_e();
      label21:
      switch(spacketrecipebook$state) {
      case REMOVE:
         Iterator iterator = p_191980_1_.func_192595_a().iterator();

         while(true) {
            if (!iterator.hasNext()) {
               break label21;
            }

            IRecipe irecipe = (IRecipe)iterator.next();
            recipebook.func_193831_b(irecipe);
         }
      case INIT:
         p_191980_1_.func_192595_a().forEach(recipebook::func_194073_a);
         p_191980_1_.func_193644_b().forEach(recipebook::func_193825_e);
         break;
      case ADD:
         p_191980_1_.func_192595_a().forEach((p_194025_2_) -> {
            p_194025_1_.func_194073_a(p_194025_2_);
            p_194025_1_.func_193825_e(p_194025_2_);
            RecipeToast.func_193665_a(this.field_147299_f.func_193033_an(), p_194025_2_);
         });
      }

      RecipeBookClient.field_194087_f.forEach((p_194023_1_) -> {
         p_194023_1_.func_194214_a(p_194023_0_);
      });
      if (this.field_147299_f.field_71462_r instanceof IRecipeShownListener) {
         ((IRecipeShownListener)this.field_147299_f.field_71462_r).func_192043_J_();
      }

   }

   public void func_147260_a(SPacketEntityEffect p_147260_1_) {
      PacketThreadUtil.func_180031_a(p_147260_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147260_1_.func_149426_d());
      if (entity instanceof EntityLivingBase) {
         Potion potion = Potion.func_188412_a(p_147260_1_.func_149427_e());
         if (potion != null) {
            PotionEffect potioneffect = new PotionEffect(potion, p_147260_1_.func_180755_e(), p_147260_1_.func_149428_f(), p_147260_1_.func_186984_g(), p_147260_1_.func_179707_f());
            potioneffect.func_100012_b(p_147260_1_.func_149429_c());
            ((EntityLivingBase)entity).func_70690_d(potioneffect);
         }
      }
   }

   public void func_175098_a(SPacketCombatEvent p_175098_1_) {
      PacketThreadUtil.func_180031_a(p_175098_1_, this, this.field_147299_f);
      if (p_175098_1_.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         Entity entity = this.field_147300_g.func_73045_a(p_175098_1_.field_179774_b);
         if (entity == this.field_147299_f.field_71439_g) {
            this.field_147299_f.func_147108_a(new GuiGameOver(p_175098_1_.field_179773_e));
         }
      }

   }

   public void func_175101_a(SPacketServerDifficulty p_175101_1_) {
      PacketThreadUtil.func_180031_a(p_175101_1_, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_72912_H().func_176144_a(p_175101_1_.func_179831_b());
      this.field_147299_f.field_71441_e.func_72912_H().func_180783_e(p_175101_1_.func_179830_a());
   }

   public void func_175094_a(SPacketCamera p_175094_1_) {
      PacketThreadUtil.func_180031_a(p_175094_1_, this, this.field_147299_f);
      Entity entity = p_175094_1_.func_179780_a(this.field_147300_g);
      if (entity != null) {
         this.field_147299_f.func_175607_a(entity);
      }

   }

   public void func_175093_a(SPacketWorldBorder p_175093_1_) {
      PacketThreadUtil.func_180031_a(p_175093_1_, this, this.field_147299_f);
      p_175093_1_.func_179788_a(this.field_147300_g.func_175723_af());
   }

   public void func_175099_a(SPacketTitle p_175099_1_) {
      PacketThreadUtil.func_180031_a(p_175099_1_, this, this.field_147299_f);
      SPacketTitle.Type spackettitle$type = p_175099_1_.func_179807_a();
      String s = null;
      String s1 = null;
      String s2 = p_175099_1_.func_179805_b() != null ? p_175099_1_.func_179805_b().func_150254_d() : "";
      switch(spackettitle$type) {
      case TITLE:
         s = s2;
         break;
      case SUBTITLE:
         s1 = s2;
         break;
      case ACTIONBAR:
         this.field_147299_f.field_71456_v.func_110326_a(s2, false);
         return;
      case RESET:
         this.field_147299_f.field_71456_v.func_175178_a("", "", -1, -1, -1);
         this.field_147299_f.field_71456_v.func_175177_a();
         return;
      }

      this.field_147299_f.field_71456_v.func_175178_a(s, s1, p_175099_1_.func_179806_c(), p_175099_1_.func_179804_d(), p_175099_1_.func_179803_e());
   }

   public void func_175096_a(SPacketPlayerListHeaderFooter p_175096_1_) {
      this.field_147299_f.field_71456_v.func_175181_h().func_175244_b(p_175096_1_.func_179700_a().func_150254_d().isEmpty() ? null : p_175096_1_.func_179700_a());
      this.field_147299_f.field_71456_v.func_175181_h().func_175248_a(p_175096_1_.func_179701_b().func_150254_d().isEmpty() ? null : p_175096_1_.func_179701_b());
   }

   public void func_147262_a(SPacketRemoveEntityEffect p_147262_1_) {
      PacketThreadUtil.func_180031_a(p_147262_1_, this, this.field_147299_f);
      Entity entity = p_147262_1_.func_186967_a(this.field_147300_g);
      if (entity instanceof EntityLivingBase) {
         ((EntityLivingBase)entity).func_184596_c(p_147262_1_.func_186968_a());
      }

   }

   public void func_147256_a(SPacketPlayerListItem p_147256_1_) {
      PacketThreadUtil.func_180031_a(p_147256_1_, this, this.field_147299_f);

      for(SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata : p_147256_1_.func_179767_a()) {
         if (p_147256_1_.func_179768_b() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
            this.field_147310_i.remove(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
         } else {
            NetworkPlayerInfo networkplayerinfo = this.field_147310_i.get(spacketplayerlistitem$addplayerdata.func_179962_a().getId());
            if (p_147256_1_.func_179768_b() == SPacketPlayerListItem.Action.ADD_PLAYER) {
               networkplayerinfo = new NetworkPlayerInfo(spacketplayerlistitem$addplayerdata);
               this.field_147310_i.put(networkplayerinfo.func_178845_a().getId(), networkplayerinfo);
            }

            if (networkplayerinfo != null) {
               switch(p_147256_1_.func_179768_b()) {
               case ADD_PLAYER:
                  networkplayerinfo.func_178839_a(spacketplayerlistitem$addplayerdata.func_179960_c());
                  networkplayerinfo.func_178838_a(spacketplayerlistitem$addplayerdata.func_179963_b());
                  break;
               case UPDATE_GAME_MODE:
                  networkplayerinfo.func_178839_a(spacketplayerlistitem$addplayerdata.func_179960_c());
                  break;
               case UPDATE_LATENCY:
                  networkplayerinfo.func_178838_a(spacketplayerlistitem$addplayerdata.func_179963_b());
                  break;
               case UPDATE_DISPLAY_NAME:
                  networkplayerinfo.func_178859_a(spacketplayerlistitem$addplayerdata.func_179961_d());
               }
            }
         }
      }

   }

   public void func_147272_a(SPacketKeepAlive p_147272_1_) {
      this.func_147297_a(new CPacketKeepAlive(p_147272_1_.func_149134_c()));
   }

   public void func_147270_a(SPacketPlayerAbilities p_147270_1_) {
      PacketThreadUtil.func_180031_a(p_147270_1_, this, this.field_147299_f);
      EntityPlayer entityplayer1 = this.field_147299_f.field_71439_g;
      entityplayer1.field_71075_bZ.field_75100_b = p_147270_1_.func_149106_d();
      entityplayer1.field_71075_bZ.field_75098_d = p_147270_1_.func_149103_f();
      entityplayer1.field_71075_bZ.field_75102_a = p_147270_1_.func_149112_c();
      entityplayer1.field_71075_bZ.field_75101_c = p_147270_1_.func_149105_e();
      entityplayer1.field_71075_bZ.func_75092_a(p_147270_1_.func_149101_g());
      entityplayer1.field_71075_bZ.func_82877_b(p_147270_1_.func_149107_h());
   }

   public void func_147274_a(SPacketTabComplete p_147274_1_) {
      PacketThreadUtil.func_180031_a(p_147274_1_, this, this.field_147299_f);
      String[] astring = p_147274_1_.func_149630_c();
      Arrays.sort((Object[])astring);
      if (this.field_147299_f.field_71462_r instanceof ITabCompleter) {
         ((ITabCompleter)this.field_147299_f.field_71462_r).func_184072_a(astring);
      }

   }

   public void func_184327_a(SPacketSoundEffect p_184327_1_) {
      PacketThreadUtil.func_180031_a(p_184327_1_, this, this.field_147299_f);
      this.field_147299_f.field_71441_e.func_184148_a(this.field_147299_f.field_71439_g, p_184327_1_.func_149207_d(), p_184327_1_.func_149211_e(), p_184327_1_.func_149210_f(), p_184327_1_.func_186978_a(), p_184327_1_.func_186977_b(), p_184327_1_.func_149208_g(), p_184327_1_.func_149209_h());
   }

   public void func_184329_a(SPacketCustomSound p_184329_1_) {
      PacketThreadUtil.func_180031_a(p_184329_1_, this, this.field_147299_f);
      this.field_147299_f.func_147118_V().func_147682_a(new PositionedSoundRecord(new ResourceLocation(p_184329_1_.func_186930_a()), p_184329_1_.func_186929_b(), p_184329_1_.func_186927_f(), p_184329_1_.func_186928_g(), false, 0, ISound.AttenuationType.LINEAR, (float)p_184329_1_.func_186932_c(), (float)p_184329_1_.func_186926_d(), (float)p_184329_1_.func_186925_e()));
   }

   public void func_175095_a(SPacketResourcePackSend p_175095_1_) {
      final String s = p_175095_1_.func_179783_a();
      final String s1 = p_175095_1_.func_179784_b();
      if (this.func_189688_b(s)) {
         if (s.startsWith("level://")) {
            try {
               String s2 = URLDecoder.decode(s.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File file1 = new File(this.field_147299_f.field_71412_D, "saves");
               File file2 = new File(file1, s2);
               if (file2.isFile()) {
                  this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
                  Futures.addCallback(this.field_147299_f.func_110438_M().func_177319_a(file2), this.func_189686_f());
                  return;
               }
            } catch (UnsupportedEncodingException var7) {
               ;
            }

            this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         } else {
            ServerData serverdata = this.field_147299_f.func_147104_D();
            if (serverdata != null && serverdata.func_152586_b() == ServerData.ServerResourceMode.ENABLED) {
               this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
               Futures.addCallback(this.field_147299_f.func_110438_M().func_180601_a(s, s1), this.func_189686_f());
            } else if (serverdata != null && serverdata.func_152586_b() != ServerData.ServerResourceMode.PROMPT) {
               this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
            } else {
               this.field_147299_f.func_152344_a(new Runnable() {
                  public void run() {
                     NetHandlerPlayClient.this.field_147299_f.func_147108_a(new GuiYesNo(new GuiYesNoCallback() {
                        public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
                           NetHandlerPlayClient.this.field_147299_f = Minecraft.func_71410_x();
                           ServerData serverdata1 = NetHandlerPlayClient.this.field_147299_f.func_147104_D();
                           if (p_73878_1_) {
                              if (serverdata1 != null) {
                                 serverdata1.func_152584_a(ServerData.ServerResourceMode.ENABLED);
                              }

                              NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
                              Futures.addCallback(NetHandlerPlayClient.this.field_147299_f.func_110438_M().func_180601_a(s, s1), NetHandlerPlayClient.this.func_189686_f());
                           } else {
                              if (serverdata1 != null) {
                                 serverdata1.func_152584_a(ServerData.ServerResourceMode.DISABLED);
                              }

                              NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
                           }

                           ServerList.func_147414_b(serverdata1);
                           NetHandlerPlayClient.this.field_147299_f.func_147108_a((GuiScreen)null);
                        }
                     }, I18n.func_135052_a("multiplayer.texturePrompt.line1"), I18n.func_135052_a("multiplayer.texturePrompt.line2"), 0));
                  }
               });
            }

         }
      }
   }

   private boolean func_189688_b(String p_189688_1_) {
      try {
         URI uri = new URI(p_189688_1_);
         String s = uri.getScheme();
         boolean flag = "level".equals(s);
         if (!"http".equals(s) && !"https".equals(s) && !flag) {
            throw new URISyntaxException(p_189688_1_, "Wrong protocol");
         } else if (!flag || !p_189688_1_.contains("..") && p_189688_1_.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(p_189688_1_, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var5) {
         this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         return false;
      }
   }

   private FutureCallback<Object> func_189686_f() {
      return new FutureCallback<Object>() {
         public void onSuccess(@Nullable Object p_onSuccess_1_) {
            NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
         }

         public void onFailure(Throwable p_onFailure_1_) {
            NetHandlerPlayClient.this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         }
      };
   }

   public void func_184325_a(SPacketUpdateBossInfo p_184325_1_) {
      PacketThreadUtil.func_180031_a(p_184325_1_, this, this.field_147299_f);
      this.field_147299_f.field_71456_v.func_184046_j().func_184055_a(p_184325_1_);
   }

   public void func_184324_a(SPacketCooldown p_184324_1_) {
      PacketThreadUtil.func_180031_a(p_184324_1_, this, this.field_147299_f);
      if (p_184324_1_.func_186922_b() == 0) {
         this.field_147299_f.field_71439_g.func_184811_cZ().func_185142_b(p_184324_1_.func_186920_a());
      } else {
         this.field_147299_f.field_71439_g.func_184811_cZ().func_185145_a(p_184324_1_.func_186920_a(), p_184324_1_.func_186922_b());
      }

   }

   public void func_184323_a(SPacketMoveVehicle p_184323_1_) {
      PacketThreadUtil.func_180031_a(p_184323_1_, this, this.field_147299_f);
      Entity entity = this.field_147299_f.field_71439_g.func_184208_bv();
      if (entity != this.field_147299_f.field_71439_g && entity.func_184186_bw()) {
         entity.func_70080_a(p_184323_1_.func_186957_a(), p_184323_1_.func_186955_b(), p_184323_1_.func_186956_c(), p_184323_1_.func_186959_d(), p_184323_1_.func_186958_e());
         this.field_147302_e.func_179290_a(new CPacketVehicleMove(entity));
      }

   }

   public void func_147240_a(SPacketCustomPayload p_147240_1_) {
      PacketThreadUtil.func_180031_a(p_147240_1_, this, this.field_147299_f);
      if ("MC|TrList".equals(p_147240_1_.func_149169_c())) {
         PacketBuffer packetbuffer = p_147240_1_.func_180735_b();

         try {
            int k = packetbuffer.readInt();
            GuiScreen guiscreen = this.field_147299_f.field_71462_r;
            if (guiscreen != null && guiscreen instanceof GuiMerchant && k == this.field_147299_f.field_71439_g.field_71070_bA.field_75152_c) {
               IMerchant imerchant = ((GuiMerchant)guiscreen).func_147035_g();
               MerchantRecipeList merchantrecipelist = MerchantRecipeList.func_151390_b(packetbuffer);
               imerchant.func_70930_a(merchantrecipelist);
            }
         } catch (IOException ioexception) {
            field_147301_d.error("Couldn't load trade info", (Throwable)ioexception);
         } finally {
            packetbuffer.release();
         }
      } else if ("MC|Brand".equals(p_147240_1_.func_149169_c())) {
         this.field_147299_f.field_71439_g.func_175158_f(p_147240_1_.func_180735_b().func_150789_c(32767));
      } else if ("MC|BOpen".equals(p_147240_1_.func_149169_c())) {
         EnumHand enumhand = (EnumHand)p_147240_1_.func_180735_b().func_179257_a(EnumHand.class);
         ItemStack itemstack = enumhand == EnumHand.OFF_HAND ? this.field_147299_f.field_71439_g.func_184592_cb() : this.field_147299_f.field_71439_g.func_184614_ca();
         if (itemstack.func_77973_b() == Items.field_151164_bB) {
            this.field_147299_f.func_147108_a(new GuiScreenBook(this.field_147299_f.field_71439_g, itemstack, false));
         }
      } else if ("MC|DebugPath".equals(p_147240_1_.func_149169_c())) {
         PacketBuffer packetbuffer1 = p_147240_1_.func_180735_b();
         int l = packetbuffer1.readInt();
         float f1 = packetbuffer1.readFloat();
         Path path = Path.func_186311_b(packetbuffer1);
         ((DebugRendererPathfinding)this.field_147299_f.field_184132_p.field_188286_a).func_188289_a(l, path, f1);
      } else if ("MC|DebugNeighborsUpdate".equals(p_147240_1_.func_149169_c())) {
         PacketBuffer packetbuffer2 = p_147240_1_.func_180735_b();
         long i1 = packetbuffer2.func_179260_f();
         BlockPos blockpos = packetbuffer2.func_179259_c();
         ((DebugRendererNeighborsUpdate)this.field_147299_f.field_184132_p.field_191557_f).func_191553_a(i1, blockpos);
      } else if ("MC|StopSound".equals(p_147240_1_.func_149169_c())) {
         PacketBuffer packetbuffer3 = p_147240_1_.func_180735_b();
         String s = packetbuffer3.func_150789_c(32767);
         String s1 = packetbuffer3.func_150789_c(256);
         this.field_147299_f.func_147118_V().func_189520_a(s1, SoundCategory.func_187950_a(s));
      }

   }

   public void func_147291_a(SPacketScoreboardObjective p_147291_1_) {
      PacketThreadUtil.func_180031_a(p_147291_1_, this, this.field_147299_f);
      Scoreboard scoreboard = this.field_147300_g.func_96441_U();
      if (p_147291_1_.func_149338_e() == 0) {
         ScoreObjective scoreobjective = scoreboard.func_96535_a(p_147291_1_.func_149339_c(), IScoreCriteria.field_96641_b);
         scoreobjective.func_96681_a(p_147291_1_.func_149337_d());
         scoreobjective.func_178767_a(p_147291_1_.func_179817_d());
      } else {
         ScoreObjective scoreobjective1 = scoreboard.func_96518_b(p_147291_1_.func_149339_c());
         if (p_147291_1_.func_149338_e() == 1) {
            scoreboard.func_96519_k(scoreobjective1);
         } else if (p_147291_1_.func_149338_e() == 2) {
            scoreobjective1.func_96681_a(p_147291_1_.func_149337_d());
            scoreobjective1.func_178767_a(p_147291_1_.func_179817_d());
         }
      }

   }

   public void func_147250_a(SPacketUpdateScore p_147250_1_) {
      PacketThreadUtil.func_180031_a(p_147250_1_, this, this.field_147299_f);
      Scoreboard scoreboard = this.field_147300_g.func_96441_U();
      ScoreObjective scoreobjective = scoreboard.func_96518_b(p_147250_1_.func_149321_d());
      if (p_147250_1_.func_180751_d() == SPacketUpdateScore.Action.CHANGE) {
         Score score = scoreboard.func_96529_a(p_147250_1_.func_149324_c(), scoreobjective);
         score.func_96647_c(p_147250_1_.func_149323_e());
      } else if (p_147250_1_.func_180751_d() == SPacketUpdateScore.Action.REMOVE) {
         if (StringUtils.func_151246_b(p_147250_1_.func_149321_d())) {
            scoreboard.func_178822_d(p_147250_1_.func_149324_c(), (ScoreObjective)null);
         } else if (scoreobjective != null) {
            scoreboard.func_178822_d(p_147250_1_.func_149324_c(), scoreobjective);
         }
      }

   }

   public void func_147254_a(SPacketDisplayObjective p_147254_1_) {
      PacketThreadUtil.func_180031_a(p_147254_1_, this, this.field_147299_f);
      Scoreboard scoreboard = this.field_147300_g.func_96441_U();
      if (p_147254_1_.func_149370_d().isEmpty()) {
         scoreboard.func_96530_a(p_147254_1_.func_149371_c(), (ScoreObjective)null);
      } else {
         ScoreObjective scoreobjective = scoreboard.func_96518_b(p_147254_1_.func_149370_d());
         scoreboard.func_96530_a(p_147254_1_.func_149371_c(), scoreobjective);
      }

   }

   public void func_147247_a(SPacketTeams p_147247_1_) {
      PacketThreadUtil.func_180031_a(p_147247_1_, this, this.field_147299_f);
      Scoreboard scoreboard = this.field_147300_g.func_96441_U();
      ScorePlayerTeam scoreplayerteam;
      if (p_147247_1_.func_149307_h() == 0) {
         scoreplayerteam = scoreboard.func_96527_f(p_147247_1_.func_149312_c());
      } else {
         scoreplayerteam = scoreboard.func_96508_e(p_147247_1_.func_149312_c());
      }

      if (p_147247_1_.func_149307_h() == 0 || p_147247_1_.func_149307_h() == 2) {
         scoreplayerteam.func_96664_a(p_147247_1_.func_149306_d());
         scoreplayerteam.func_96666_b(p_147247_1_.func_149311_e());
         scoreplayerteam.func_96662_c(p_147247_1_.func_149309_f());
         scoreplayerteam.func_178774_a(TextFormatting.func_175744_a(p_147247_1_.func_179813_h()));
         scoreplayerteam.func_98298_a(p_147247_1_.func_149308_i());
         Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(p_147247_1_.func_179814_i());
         if (team$enumvisible != null) {
            scoreplayerteam.func_178772_a(team$enumvisible);
         }

         Team.CollisionRule team$collisionrule = Team.CollisionRule.func_186686_a(p_147247_1_.func_186975_j());
         if (team$collisionrule != null) {
            scoreplayerteam.func_186682_a(team$collisionrule);
         }
      }

      if (p_147247_1_.func_149307_h() == 0 || p_147247_1_.func_149307_h() == 3) {
         for(String s : p_147247_1_.func_149310_g()) {
            scoreboard.func_151392_a(s, p_147247_1_.func_149312_c());
         }
      }

      if (p_147247_1_.func_149307_h() == 4) {
         for(String s1 : p_147247_1_.func_149310_g()) {
            scoreboard.func_96512_b(s1, scoreplayerteam);
         }
      }

      if (p_147247_1_.func_149307_h() == 1) {
         scoreboard.func_96511_d(scoreplayerteam);
      }

   }

   public void func_147289_a(SPacketParticles p_147289_1_) {
      PacketThreadUtil.func_180031_a(p_147289_1_, this, this.field_147299_f);
      if (p_147289_1_.func_149222_k() == 0) {
         double d0 = (double)(p_147289_1_.func_149227_j() * p_147289_1_.func_149221_g());
         double d2 = (double)(p_147289_1_.func_149227_j() * p_147289_1_.func_149224_h());
         double d4 = (double)(p_147289_1_.func_149227_j() * p_147289_1_.func_149223_i());

         try {
            this.field_147300_g.func_175682_a(p_147289_1_.func_179749_a(), p_147289_1_.func_179750_b(), p_147289_1_.func_149220_d(), p_147289_1_.func_149226_e(), p_147289_1_.func_149225_f(), d0, d2, d4, p_147289_1_.func_179748_k());
         } catch (Throwable var17) {
            field_147301_d.warn("Could not spawn particle effect {}", (Object)p_147289_1_.func_179749_a());
         }
      } else {
         for(int k = 0; k < p_147289_1_.func_149222_k(); ++k) {
            double d1 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149221_g();
            double d3 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149224_h();
            double d5 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149223_i();
            double d6 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149227_j();
            double d7 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149227_j();
            double d8 = this.field_147306_l.nextGaussian() * (double)p_147289_1_.func_149227_j();

            try {
               this.field_147300_g.func_175682_a(p_147289_1_.func_179749_a(), p_147289_1_.func_179750_b(), p_147289_1_.func_149220_d() + d1, p_147289_1_.func_149226_e() + d3, p_147289_1_.func_149225_f() + d5, d6, d7, d8, p_147289_1_.func_179748_k());
            } catch (Throwable var16) {
               field_147301_d.warn("Could not spawn particle effect {}", (Object)p_147289_1_.func_179749_a());
               return;
            }
         }
      }

   }

   public void func_147290_a(SPacketEntityProperties p_147290_1_) {
      PacketThreadUtil.func_180031_a(p_147290_1_, this, this.field_147299_f);
      Entity entity = this.field_147300_g.func_73045_a(p_147290_1_.func_149442_c());
      if (entity != null) {
         if (!(entity instanceof EntityLivingBase)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
         } else {
            AbstractAttributeMap abstractattributemap = ((EntityLivingBase)entity).func_110140_aT();

            for(SPacketEntityProperties.Snapshot spacketentityproperties$snapshot : p_147290_1_.func_149441_d()) {
               IAttributeInstance iattributeinstance = abstractattributemap.func_111152_a(spacketentityproperties$snapshot.func_151409_a());
               if (iattributeinstance == null) {
                  iattributeinstance = abstractattributemap.func_111150_b(new RangedAttribute((IAttribute)null, spacketentityproperties$snapshot.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
               }

               iattributeinstance.func_111128_a(spacketentityproperties$snapshot.func_151410_b());
               iattributeinstance.func_142049_d();

               for(AttributeModifier attributemodifier : spacketentityproperties$snapshot.func_151408_c()) {
                  iattributeinstance.func_111121_a(attributemodifier);
               }
            }

         }
      }
   }

   public NetworkManager func_147298_b() {
      return this.field_147302_e;
   }

   public Collection<NetworkPlayerInfo> func_175106_d() {
      return this.field_147310_i.values();
   }

   public NetworkPlayerInfo func_175102_a(UUID p_175102_1_) {
      return this.field_147310_i.get(p_175102_1_);
   }

   @Nullable
   public NetworkPlayerInfo func_175104_a(String p_175104_1_) {
      for(NetworkPlayerInfo networkplayerinfo : this.field_147310_i.values()) {
         if (networkplayerinfo.func_178845_a().getName().equals(p_175104_1_)) {
            return networkplayerinfo;
         }
      }

      return null;
   }

   public GameProfile func_175105_e() {
      return this.field_175107_d;
   }

   public ClientAdvancementManager func_191982_f() {
      return this.field_191983_k;
   }
}
