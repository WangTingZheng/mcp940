package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketWorldBorder;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerList {
   public static final File field_152613_a = new File("banned-players.json");
   public static final File field_152614_b = new File("banned-ips.json");
   public static final File field_152615_c = new File("ops.json");
   public static final File field_152616_d = new File("whitelist.json");
   private static final Logger field_148546_d = LogManager.getLogger();
   private static final SimpleDateFormat field_72403_e = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer field_72400_f;
   private final List<EntityPlayerMP> field_72404_b = Lists.<EntityPlayerMP>newArrayList();
   private final Map<UUID, EntityPlayerMP> field_177454_f = Maps.<UUID, EntityPlayerMP>newHashMap();
   private final UserListBans field_72401_g;
   private final UserListIPBans field_72413_h;
   private final UserListOps field_72414_i;
   private final UserListWhitelist field_72411_j;
   private final Map<UUID, StatisticsManagerServer> field_148547_k;
   private final Map<UUID, PlayerAdvancements> field_192055_p;
   private IPlayerFileData field_72412_k;
   private boolean field_72409_l;
   protected int field_72405_c;
   private int field_72402_d;
   private GameType field_72410_m;
   private boolean field_72407_n;
   private int field_72408_o;

   public PlayerList(MinecraftServer p_i1500_1_) {
      this.field_72401_g = new UserListBans(field_152613_a);
      this.field_72413_h = new UserListIPBans(field_152614_b);
      this.field_72414_i = new UserListOps(field_152615_c);
      this.field_72411_j = new UserListWhitelist(field_152616_d);
      this.field_148547_k = Maps.<UUID, StatisticsManagerServer>newHashMap();
      this.field_192055_p = Maps.<UUID, PlayerAdvancements>newHashMap();
      this.field_72400_f = p_i1500_1_;
      this.field_72401_g.func_152686_a(false);
      this.field_72413_h.func_152686_a(false);
      this.field_72405_c = 8;
   }

   public void func_72355_a(NetworkManager p_72355_1_, EntityPlayerMP p_72355_2_) {
      GameProfile gameprofile = p_72355_2_.func_146103_bH();
      PlayerProfileCache playerprofilecache = this.field_72400_f.func_152358_ax();
      GameProfile gameprofile1 = playerprofilecache.func_152652_a(gameprofile.getId());
      String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
      playerprofilecache.func_152649_a(gameprofile);
      NBTTagCompound nbttagcompound = this.func_72380_a(p_72355_2_);
      p_72355_2_.func_70029_a(this.field_72400_f.func_71218_a(p_72355_2_.field_71093_bK));
      p_72355_2_.field_71134_c.func_73080_a((WorldServer)p_72355_2_.field_70170_p);
      String s1 = "local";
      if (p_72355_1_.func_74430_c() != null) {
         s1 = p_72355_1_.func_74430_c().toString();
      }

      field_148546_d.info("{}[{}] logged in with entity id {} at ({}, {}, {})", p_72355_2_.func_70005_c_(), s1, Integer.valueOf(p_72355_2_.func_145782_y()), Double.valueOf(p_72355_2_.field_70165_t), Double.valueOf(p_72355_2_.field_70163_u), Double.valueOf(p_72355_2_.field_70161_v));
      WorldServer worldserver = this.field_72400_f.func_71218_a(p_72355_2_.field_71093_bK);
      WorldInfo worldinfo = worldserver.func_72912_H();
      this.func_72381_a(p_72355_2_, (EntityPlayerMP)null, worldserver);
      NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.field_72400_f, p_72355_1_, p_72355_2_);
      nethandlerplayserver.func_147359_a(new SPacketJoinGame(p_72355_2_.func_145782_y(), p_72355_2_.field_71134_c.func_73081_b(), worldinfo.func_76093_s(), worldserver.field_73011_w.func_186058_p().func_186068_a(), worldserver.func_175659_aa(), this.func_72352_l(), worldinfo.func_76067_t(), worldserver.func_82736_K().func_82766_b("reducedDebugInfo")));
      nethandlerplayserver.func_147359_a(new SPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).func_180714_a(this.func_72365_p().getServerModName())));
      nethandlerplayserver.func_147359_a(new SPacketServerDifficulty(worldinfo.func_176130_y(), worldinfo.func_176123_z()));
      nethandlerplayserver.func_147359_a(new SPacketPlayerAbilities(p_72355_2_.field_71075_bZ));
      nethandlerplayserver.func_147359_a(new SPacketHeldItemChange(p_72355_2_.field_71071_by.field_70461_c));
      this.func_187243_f(p_72355_2_);
      p_72355_2_.func_147099_x().func_150877_d();
      p_72355_2_.func_192037_E().func_192826_c(p_72355_2_);
      this.func_96456_a((ServerScoreboard)worldserver.func_96441_U(), p_72355_2_);
      this.field_72400_f.func_147132_au();
      TextComponentTranslation textcomponenttranslation;
      if (p_72355_2_.func_70005_c_().equalsIgnoreCase(s)) {
         textcomponenttranslation = new TextComponentTranslation("multiplayer.player.joined", new Object[]{p_72355_2_.func_145748_c_()});
      } else {
         textcomponenttranslation = new TextComponentTranslation("multiplayer.player.joined.renamed", new Object[]{p_72355_2_.func_145748_c_(), s});
      }

      textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.YELLOW);
      this.func_148539_a(textcomponenttranslation);
      this.func_72377_c(p_72355_2_);
      nethandlerplayserver.func_147364_a(p_72355_2_.field_70165_t, p_72355_2_.field_70163_u, p_72355_2_.field_70161_v, p_72355_2_.field_70177_z, p_72355_2_.field_70125_A);
      this.func_72354_b(p_72355_2_, worldserver);
      if (!this.field_72400_f.func_147133_T().isEmpty()) {
         p_72355_2_.func_175397_a(this.field_72400_f.func_147133_T(), this.field_72400_f.func_175581_ab());
      }

      for(PotionEffect potioneffect : p_72355_2_.func_70651_bq()) {
         nethandlerplayserver.func_147359_a(new SPacketEntityEffect(p_72355_2_.func_145782_y(), potioneffect));
      }

      if (nbttagcompound != null && nbttagcompound.func_150297_b("RootVehicle", 10)) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("RootVehicle");
         Entity entity1 = AnvilChunkLoader.func_186051_a(nbttagcompound1.func_74775_l("Entity"), worldserver, true);
         if (entity1 != null) {
            UUID uuid = nbttagcompound1.func_186857_a("Attach");
            if (entity1.func_110124_au().equals(uuid)) {
               p_72355_2_.func_184205_a(entity1, true);
            } else {
               for(Entity entity : entity1.func_184182_bu()) {
                  if (entity.func_110124_au().equals(uuid)) {
                     p_72355_2_.func_184205_a(entity, true);
                     break;
                  }
               }
            }

            if (!p_72355_2_.func_184218_aH()) {
               field_148546_d.warn("Couldn't reattach entity to player");
               worldserver.func_72973_f(entity1);

               for(Entity entity2 : entity1.func_184182_bu()) {
                  worldserver.func_72973_f(entity2);
               }
            }
         }
      }

      p_72355_2_.func_71116_b();
   }

   protected void func_96456_a(ServerScoreboard p_96456_1_, EntityPlayerMP p_96456_2_) {
      Set<ScoreObjective> set = Sets.<ScoreObjective>newHashSet();

      for(ScorePlayerTeam scoreplayerteam : p_96456_1_.func_96525_g()) {
         p_96456_2_.field_71135_a.func_147359_a(new SPacketTeams(scoreplayerteam, 0));
      }

      for(int i = 0; i < 19; ++i) {
         ScoreObjective scoreobjective = p_96456_1_.func_96539_a(i);
         if (scoreobjective != null && !set.contains(scoreobjective)) {
            for(Packet<?> packet : p_96456_1_.func_96550_d(scoreobjective)) {
               p_96456_2_.field_71135_a.func_147359_a(packet);
            }

            set.add(scoreobjective);
         }
      }

   }

   public void func_72364_a(WorldServer[] p_72364_1_) {
      this.field_72412_k = p_72364_1_[0].func_72860_G().func_75756_e();
      p_72364_1_[0].func_175723_af().func_177737_a(new IBorderListener() {
         public void func_177694_a(WorldBorder p_177694_1_, double p_177694_2_) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(p_177694_1_, SPacketWorldBorder.Action.SET_SIZE));
         }

         public void func_177692_a(WorldBorder p_177692_1_, double p_177692_2_, double p_177692_4_, long p_177692_6_) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(p_177692_1_, SPacketWorldBorder.Action.LERP_SIZE));
         }

         public void func_177693_a(WorldBorder p_177693_1_, double p_177693_2_, double p_177693_4_) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(p_177693_1_, SPacketWorldBorder.Action.SET_CENTER));
         }

         public void func_177691_a(WorldBorder p_177691_1_, int p_177691_2_) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(p_177691_1_, SPacketWorldBorder.Action.SET_WARNING_TIME));
         }

         public void func_177690_b(WorldBorder p_177690_1_, int p_177690_2_) {
            PlayerList.this.func_148540_a(new SPacketWorldBorder(p_177690_1_, SPacketWorldBorder.Action.SET_WARNING_BLOCKS));
         }

         public void func_177696_b(WorldBorder p_177696_1_, double p_177696_2_) {
         }

         public void func_177695_c(WorldBorder p_177695_1_, double p_177695_2_) {
         }
      });
   }

   public void func_72375_a(EntityPlayerMP p_72375_1_, @Nullable WorldServer p_72375_2_) {
      WorldServer worldserver = p_72375_1_.func_71121_q();
      if (p_72375_2_ != null) {
         p_72375_2_.func_184164_w().func_72695_c(p_72375_1_);
      }

      worldserver.func_184164_w().func_72683_a(p_72375_1_);
      worldserver.func_72863_F().func_186025_d((int)p_72375_1_.field_70165_t >> 4, (int)p_72375_1_.field_70161_v >> 4);
      if (p_72375_2_ != null) {
         CriteriaTriggers.field_193134_u.func_193143_a(p_72375_1_, p_72375_2_.field_73011_w.func_186058_p(), worldserver.field_73011_w.func_186058_p());
         if (p_72375_2_.field_73011_w.func_186058_p() == DimensionType.NETHER && p_72375_1_.field_70170_p.field_73011_w.func_186058_p() == DimensionType.OVERWORLD && p_72375_1_.func_193106_Q() != null) {
            CriteriaTriggers.field_193131_B.func_193168_a(p_72375_1_, p_72375_1_.func_193106_Q());
         }
      }

   }

   public int func_72372_a() {
      return PlayerChunkMap.func_72686_a(this.func_72395_o());
   }

   @Nullable
   public NBTTagCompound func_72380_a(EntityPlayerMP p_72380_1_) {
      NBTTagCompound nbttagcompound = this.field_72400_f.field_71305_c[0].func_72912_H().func_76072_h();
      NBTTagCompound nbttagcompound1;
      if (p_72380_1_.func_70005_c_().equals(this.field_72400_f.func_71214_G()) && nbttagcompound != null) {
         nbttagcompound1 = nbttagcompound;
         p_72380_1_.func_70020_e(nbttagcompound);
         field_148546_d.debug("loading single player");
      } else {
         nbttagcompound1 = this.field_72412_k.func_75752_b(p_72380_1_);
      }

      return nbttagcompound1;
   }

   protected void func_72391_b(EntityPlayerMP p_72391_1_) {
      this.field_72412_k.func_75753_a(p_72391_1_);
      StatisticsManagerServer statisticsmanagerserver = this.field_148547_k.get(p_72391_1_.func_110124_au());
      if (statisticsmanagerserver != null) {
         statisticsmanagerserver.func_150883_b();
      }

      PlayerAdvancements playeradvancements = this.field_192055_p.get(p_72391_1_.func_110124_au());
      if (playeradvancements != null) {
         playeradvancements.func_192749_b();
      }

   }

   public void func_72377_c(EntityPlayerMP p_72377_1_) {
      this.field_72404_b.add(p_72377_1_);
      this.field_177454_f.put(p_72377_1_.func_110124_au(), p_72377_1_);
      this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[]{p_72377_1_}));
      WorldServer worldserver = this.field_72400_f.func_71218_a(p_72377_1_.field_71093_bK);

      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         p_72377_1_.field_71135_a.func_147359_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[]{this.field_72404_b.get(i)}));
      }

      worldserver.func_72838_d(p_72377_1_);
      this.func_72375_a(p_72377_1_, (WorldServer)null);
   }

   public void func_72358_d(EntityPlayerMP p_72358_1_) {
      p_72358_1_.func_71121_q().func_184164_w().func_72685_d(p_72358_1_);
   }

   public void func_72367_e(EntityPlayerMP p_72367_1_) {
      WorldServer worldserver = p_72367_1_.func_71121_q();
      p_72367_1_.func_71029_a(StatList.field_75947_j);
      this.func_72391_b(p_72367_1_);
      if (p_72367_1_.func_184218_aH()) {
         Entity entity = p_72367_1_.func_184208_bv();
         if (entity.func_184180_b(EntityPlayerMP.class).size() == 1) {
            field_148546_d.debug("Removing player mount");
            p_72367_1_.func_184210_p();
            worldserver.func_72973_f(entity);

            for(Entity entity1 : entity.func_184182_bu()) {
               worldserver.func_72973_f(entity1);
            }

            worldserver.func_72964_e(p_72367_1_.field_70176_ah, p_72367_1_.field_70164_aj).func_76630_e();
         }
      }

      worldserver.func_72900_e(p_72367_1_);
      worldserver.func_184164_w().func_72695_c(p_72367_1_);
      p_72367_1_.func_192039_O().func_192745_a();
      this.field_72404_b.remove(p_72367_1_);
      UUID uuid = p_72367_1_.func_110124_au();
      EntityPlayerMP entityplayermp = this.field_177454_f.get(uuid);
      if (entityplayermp == p_72367_1_) {
         this.field_177454_f.remove(uuid);
         this.field_148547_k.remove(uuid);
         this.field_192055_p.remove(uuid);
      }

      this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[]{p_72367_1_}));
   }

   public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_) {
      if (this.field_72401_g.func_152702_a(p_148542_2_)) {
         UserListBansEntry userlistbansentry = (UserListBansEntry)this.field_72401_g.func_152683_b(p_148542_2_);
         String s1 = "You are banned from this server!\nReason: " + userlistbansentry.func_73686_f();
         if (userlistbansentry.func_73680_d() != null) {
            s1 = s1 + "\nYour ban will be removed on " + field_72403_e.format(userlistbansentry.func_73680_d());
         }

         return s1;
      } else if (!this.func_152607_e(p_148542_2_)) {
         return "You are not white-listed on this server!";
      } else if (this.field_72413_h.func_152708_a(p_148542_1_)) {
         UserListIPBansEntry userlistipbansentry = this.field_72413_h.func_152709_b(p_148542_1_);
         String s = "Your IP address is banned from this server!\nReason: " + userlistipbansentry.func_73686_f();
         if (userlistipbansentry.func_73680_d() != null) {
            s = s + "\nYour ban will be removed on " + field_72403_e.format(userlistipbansentry.func_73680_d());
         }

         return s;
      } else {
         return this.field_72404_b.size() >= this.field_72405_c && !this.func_183023_f(p_148542_2_) ? "The server is full!" : null;
      }
   }

   public EntityPlayerMP func_148545_a(GameProfile p_148545_1_) {
      UUID uuid = EntityPlayer.func_146094_a(p_148545_1_);
      List<EntityPlayerMP> list = Lists.<EntityPlayerMP>newArrayList();

      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         EntityPlayerMP entityplayermp = this.field_72404_b.get(i);
         if (entityplayermp.func_110124_au().equals(uuid)) {
            list.add(entityplayermp);
         }
      }

      EntityPlayerMP entityplayermp2 = this.field_177454_f.get(p_148545_1_.getId());
      if (entityplayermp2 != null && !list.contains(entityplayermp2)) {
         list.add(entityplayermp2);
      }

      for(EntityPlayerMP entityplayermp1 : list) {
         entityplayermp1.field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.duplicate_login", new Object[0]));
      }

      PlayerInteractionManager playerinteractionmanager;
      if (this.field_72400_f.func_71242_L()) {
         playerinteractionmanager = new DemoPlayerInteractionManager(this.field_72400_f.func_71218_a(0));
      } else {
         playerinteractionmanager = new PlayerInteractionManager(this.field_72400_f.func_71218_a(0));
      }

      return new EntityPlayerMP(this.field_72400_f, this.field_72400_f.func_71218_a(0), p_148545_1_, playerinteractionmanager);
   }

   public EntityPlayerMP func_72368_a(EntityPlayerMP p_72368_1_, int p_72368_2_, boolean p_72368_3_) {
      p_72368_1_.func_71121_q().func_73039_n().func_72787_a(p_72368_1_);
      p_72368_1_.func_71121_q().func_73039_n().func_72790_b(p_72368_1_);
      p_72368_1_.func_71121_q().func_184164_w().func_72695_c(p_72368_1_);
      this.field_72404_b.remove(p_72368_1_);
      this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK).func_72973_f(p_72368_1_);
      BlockPos blockpos = p_72368_1_.func_180470_cg();
      boolean flag = p_72368_1_.func_82245_bX();
      p_72368_1_.field_71093_bK = p_72368_2_;
      PlayerInteractionManager playerinteractionmanager;
      if (this.field_72400_f.func_71242_L()) {
         playerinteractionmanager = new DemoPlayerInteractionManager(this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK));
      } else {
         playerinteractionmanager = new PlayerInteractionManager(this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK));
      }

      EntityPlayerMP entityplayermp = new EntityPlayerMP(this.field_72400_f, this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK), p_72368_1_.func_146103_bH(), playerinteractionmanager);
      entityplayermp.field_71135_a = p_72368_1_.field_71135_a;
      entityplayermp.func_193104_a(p_72368_1_, p_72368_3_);
      entityplayermp.func_145769_d(p_72368_1_.func_145782_y());
      entityplayermp.func_174817_o(p_72368_1_);
      entityplayermp.func_184819_a(p_72368_1_.func_184591_cq());

      for(String s : p_72368_1_.func_184216_O()) {
         entityplayermp.func_184211_a(s);
      }

      WorldServer worldserver = this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK);
      this.func_72381_a(entityplayermp, p_72368_1_, worldserver);
      if (blockpos != null) {
         BlockPos blockpos1 = EntityPlayer.func_180467_a(this.field_72400_f.func_71218_a(p_72368_1_.field_71093_bK), blockpos, flag);
         if (blockpos1 != null) {
            entityplayermp.func_70012_b((double)((float)blockpos1.func_177958_n() + 0.5F), (double)((float)blockpos1.func_177956_o() + 0.1F), (double)((float)blockpos1.func_177952_p() + 0.5F), 0.0F, 0.0F);
            entityplayermp.func_180473_a(blockpos, flag);
         } else {
            entityplayermp.field_71135_a.func_147359_a(new SPacketChangeGameState(0, 0.0F));
         }
      }

      worldserver.func_72863_F().func_186025_d((int)entityplayermp.field_70165_t >> 4, (int)entityplayermp.field_70161_v >> 4);

      while(!worldserver.func_184144_a(entityplayermp, entityplayermp.func_174813_aQ()).isEmpty() && entityplayermp.field_70163_u < 256.0D) {
         entityplayermp.func_70107_b(entityplayermp.field_70165_t, entityplayermp.field_70163_u + 1.0D, entityplayermp.field_70161_v);
      }

      entityplayermp.field_71135_a.func_147359_a(new SPacketRespawn(entityplayermp.field_71093_bK, entityplayermp.field_70170_p.func_175659_aa(), entityplayermp.field_70170_p.func_72912_H().func_76067_t(), entityplayermp.field_71134_c.func_73081_b()));
      BlockPos blockpos2 = worldserver.func_175694_M();
      entityplayermp.field_71135_a.func_147364_a(entityplayermp.field_70165_t, entityplayermp.field_70163_u, entityplayermp.field_70161_v, entityplayermp.field_70177_z, entityplayermp.field_70125_A);
      entityplayermp.field_71135_a.func_147359_a(new SPacketSpawnPosition(blockpos2));
      entityplayermp.field_71135_a.func_147359_a(new SPacketSetExperience(entityplayermp.field_71106_cc, entityplayermp.field_71067_cb, entityplayermp.field_71068_ca));
      this.func_72354_b(entityplayermp, worldserver);
      this.func_187243_f(entityplayermp);
      worldserver.func_184164_w().func_72683_a(entityplayermp);
      worldserver.func_72838_d(entityplayermp);
      this.field_72404_b.add(entityplayermp);
      this.field_177454_f.put(entityplayermp.func_110124_au(), entityplayermp);
      entityplayermp.func_71116_b();
      entityplayermp.func_70606_j(entityplayermp.func_110143_aJ());
      return entityplayermp;
   }

   public void func_187243_f(EntityPlayerMP p_187243_1_) {
      GameProfile gameprofile = p_187243_1_.func_146103_bH();
      int i = this.func_152596_g(gameprofile) ? this.field_72414_i.func_187452_a(gameprofile) : 0;
      i = this.field_72400_f.func_71264_H() && this.field_72400_f.field_71305_c[0].func_72912_H().func_76086_u() ? 4 : i;
      i = this.field_72407_n ? 4 : i;
      this.func_187245_a(p_187243_1_, i);
   }

   public void func_187242_a(EntityPlayerMP p_187242_1_, int p_187242_2_) {
      int i = p_187242_1_.field_71093_bK;
      WorldServer worldserver = this.field_72400_f.func_71218_a(p_187242_1_.field_71093_bK);
      p_187242_1_.field_71093_bK = p_187242_2_;
      WorldServer worldserver1 = this.field_72400_f.func_71218_a(p_187242_1_.field_71093_bK);
      p_187242_1_.field_71135_a.func_147359_a(new SPacketRespawn(p_187242_1_.field_71093_bK, p_187242_1_.field_70170_p.func_175659_aa(), p_187242_1_.field_70170_p.func_72912_H().func_76067_t(), p_187242_1_.field_71134_c.func_73081_b()));
      this.func_187243_f(p_187242_1_);
      worldserver.func_72973_f(p_187242_1_);
      p_187242_1_.field_70128_L = false;
      this.func_82448_a(p_187242_1_, i, worldserver, worldserver1);
      this.func_72375_a(p_187242_1_, worldserver);
      p_187242_1_.field_71135_a.func_147364_a(p_187242_1_.field_70165_t, p_187242_1_.field_70163_u, p_187242_1_.field_70161_v, p_187242_1_.field_70177_z, p_187242_1_.field_70125_A);
      p_187242_1_.field_71134_c.func_73080_a(worldserver1);
      p_187242_1_.field_71135_a.func_147359_a(new SPacketPlayerAbilities(p_187242_1_.field_71075_bZ));
      this.func_72354_b(p_187242_1_, worldserver1);
      this.func_72385_f(p_187242_1_);

      for(PotionEffect potioneffect : p_187242_1_.func_70651_bq()) {
         p_187242_1_.field_71135_a.func_147359_a(new SPacketEntityEffect(p_187242_1_.func_145782_y(), potioneffect));
      }

   }

   public void func_82448_a(Entity p_82448_1_, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_) {
      double d0 = p_82448_1_.field_70165_t;
      double d1 = p_82448_1_.field_70161_v;
      double d2 = 8.0D;
      float f = p_82448_1_.field_70177_z;
      p_82448_3_.field_72984_F.func_76320_a("moving");
      if (p_82448_1_.field_71093_bK == -1) {
         d0 = MathHelper.func_151237_a(d0 / 8.0D, p_82448_4_.func_175723_af().func_177726_b() + 16.0D, p_82448_4_.func_175723_af().func_177728_d() - 16.0D);
         d1 = MathHelper.func_151237_a(d1 / 8.0D, p_82448_4_.func_175723_af().func_177736_c() + 16.0D, p_82448_4_.func_175723_af().func_177733_e() - 16.0D);
         p_82448_1_.func_70012_b(d0, p_82448_1_.field_70163_u, d1, p_82448_1_.field_70177_z, p_82448_1_.field_70125_A);
         if (p_82448_1_.func_70089_S()) {
            p_82448_3_.func_72866_a(p_82448_1_, false);
         }
      } else if (p_82448_1_.field_71093_bK == 0) {
         d0 = MathHelper.func_151237_a(d0 * 8.0D, p_82448_4_.func_175723_af().func_177726_b() + 16.0D, p_82448_4_.func_175723_af().func_177728_d() - 16.0D);
         d1 = MathHelper.func_151237_a(d1 * 8.0D, p_82448_4_.func_175723_af().func_177736_c() + 16.0D, p_82448_4_.func_175723_af().func_177733_e() - 16.0D);
         p_82448_1_.func_70012_b(d0, p_82448_1_.field_70163_u, d1, p_82448_1_.field_70177_z, p_82448_1_.field_70125_A);
         if (p_82448_1_.func_70089_S()) {
            p_82448_3_.func_72866_a(p_82448_1_, false);
         }
      } else {
         BlockPos blockpos;
         if (p_82448_2_ == 1) {
            blockpos = p_82448_4_.func_175694_M();
         } else {
            blockpos = p_82448_4_.func_180504_m();
         }

         d0 = (double)blockpos.func_177958_n();
         p_82448_1_.field_70163_u = (double)blockpos.func_177956_o();
         d1 = (double)blockpos.func_177952_p();
         p_82448_1_.func_70012_b(d0, p_82448_1_.field_70163_u, d1, 90.0F, 0.0F);
         if (p_82448_1_.func_70089_S()) {
            p_82448_3_.func_72866_a(p_82448_1_, false);
         }
      }

      p_82448_3_.field_72984_F.func_76319_b();
      if (p_82448_2_ != 1) {
         p_82448_3_.field_72984_F.func_76320_a("placing");
         d0 = (double)MathHelper.func_76125_a((int)d0, -29999872, 29999872);
         d1 = (double)MathHelper.func_76125_a((int)d1, -29999872, 29999872);
         if (p_82448_1_.func_70089_S()) {
            p_82448_1_.func_70012_b(d0, p_82448_1_.field_70163_u, d1, p_82448_1_.field_70177_z, p_82448_1_.field_70125_A);
            p_82448_4_.func_85176_s().func_180266_a(p_82448_1_, f);
            p_82448_4_.func_72838_d(p_82448_1_);
            p_82448_4_.func_72866_a(p_82448_1_, false);
         }

         p_82448_3_.field_72984_F.func_76319_b();
      }

      p_82448_1_.func_70029_a(p_82448_4_);
   }

   public void func_72374_b() {
      if (++this.field_72408_o > 600) {
         this.func_148540_a(new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_LATENCY, this.field_72404_b));
         this.field_72408_o = 0;
      }

   }

   public void func_148540_a(Packet<?> p_148540_1_) {
      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         (this.field_72404_b.get(i)).field_71135_a.func_147359_a(p_148540_1_);
      }

   }

   public void func_148537_a(Packet<?> p_148537_1_, int p_148537_2_) {
      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         EntityPlayerMP entityplayermp = this.field_72404_b.get(i);
         if (entityplayermp.field_71093_bK == p_148537_2_) {
            entityplayermp.field_71135_a.func_147359_a(p_148537_1_);
         }
      }

   }

   public void func_177453_a(EntityPlayer p_177453_1_, ITextComponent p_177453_2_) {
      Team team = p_177453_1_.func_96124_cp();
      if (team != null) {
         for(String s : team.func_96670_d()) {
            EntityPlayerMP entityplayermp = this.func_152612_a(s);
            if (entityplayermp != null && entityplayermp != p_177453_1_) {
               entityplayermp.func_145747_a(p_177453_2_);
            }
         }

      }
   }

   public void func_177452_b(EntityPlayer p_177452_1_, ITextComponent p_177452_2_) {
      Team team = p_177452_1_.func_96124_cp();
      if (team == null) {
         this.func_148539_a(p_177452_2_);
      } else {
         for(int i = 0; i < this.field_72404_b.size(); ++i) {
            EntityPlayerMP entityplayermp = this.field_72404_b.get(i);
            if (entityplayermp.func_96124_cp() != team) {
               entityplayermp.func_145747_a(p_177452_2_);
            }
         }

      }
   }

   public String func_181058_b(boolean p_181058_1_) {
      String s = "";
      List<EntityPlayerMP> list = Lists.newArrayList(this.field_72404_b);

      for(int i = 0; i < list.size(); ++i) {
         if (i > 0) {
            s = s + ", ";
         }

         s = s + ((EntityPlayerMP)list.get(i)).func_70005_c_();
         if (p_181058_1_) {
            s = s + " (" + ((EntityPlayerMP)list.get(i)).func_189512_bd() + ")";
         }
      }

      return s;
   }

   public String[] func_72369_d() {
      String[] astring = new String[this.field_72404_b.size()];

      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         astring[i] = ((EntityPlayerMP)this.field_72404_b.get(i)).func_70005_c_();
      }

      return astring;
   }

   public GameProfile[] func_152600_g() {
      GameProfile[] agameprofile = new GameProfile[this.field_72404_b.size()];

      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         agameprofile[i] = ((EntityPlayerMP)this.field_72404_b.get(i)).func_146103_bH();
      }

      return agameprofile;
   }

   public UserListBans func_152608_h() {
      return this.field_72401_g;
   }

   public UserListIPBans func_72363_f() {
      return this.field_72413_h;
   }

   public void func_152605_a(GameProfile p_152605_1_) {
      int i = this.field_72400_f.func_110455_j();
      this.field_72414_i.func_152687_a(new UserListOpsEntry(p_152605_1_, this.field_72400_f.func_110455_j(), this.field_72414_i.func_183026_b(p_152605_1_)));
      this.func_187245_a(this.func_177451_a(p_152605_1_.getId()), i);
   }

   public void func_152610_b(GameProfile p_152610_1_) {
      this.field_72414_i.func_152684_c(p_152610_1_);
      this.func_187245_a(this.func_177451_a(p_152610_1_.getId()), 0);
   }

   private void func_187245_a(EntityPlayerMP p_187245_1_, int p_187245_2_) {
      if (p_187245_1_ != null && p_187245_1_.field_71135_a != null) {
         byte b0;
         if (p_187245_2_ <= 0) {
            b0 = 24;
         } else if (p_187245_2_ >= 4) {
            b0 = 28;
         } else {
            b0 = (byte)(24 + p_187245_2_);
         }

         p_187245_1_.field_71135_a.func_147359_a(new SPacketEntityStatus(p_187245_1_, b0));
      }

   }

   public boolean func_152607_e(GameProfile p_152607_1_) {
      return !this.field_72409_l || this.field_72414_i.func_152692_d(p_152607_1_) || this.field_72411_j.func_152692_d(p_152607_1_);
   }

   public boolean func_152596_g(GameProfile p_152596_1_) {
      return this.field_72414_i.func_152692_d(p_152596_1_) || this.field_72400_f.func_71264_H() && this.field_72400_f.field_71305_c[0].func_72912_H().func_76086_u() && this.field_72400_f.func_71214_G().equalsIgnoreCase(p_152596_1_.getName()) || this.field_72407_n;
   }

   @Nullable
   public EntityPlayerMP func_152612_a(String p_152612_1_) {
      for(EntityPlayerMP entityplayermp : this.field_72404_b) {
         if (entityplayermp.func_70005_c_().equalsIgnoreCase(p_152612_1_)) {
            return entityplayermp;
         }
      }

      return null;
   }

   public void func_148543_a(@Nullable EntityPlayer p_148543_1_, double p_148543_2_, double p_148543_4_, double p_148543_6_, double p_148543_8_, int p_148543_10_, Packet<?> p_148543_11_) {
      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         EntityPlayerMP entityplayermp = this.field_72404_b.get(i);
         if (entityplayermp != p_148543_1_ && entityplayermp.field_71093_bK == p_148543_10_) {
            double d0 = p_148543_2_ - entityplayermp.field_70165_t;
            double d1 = p_148543_4_ - entityplayermp.field_70163_u;
            double d2 = p_148543_6_ - entityplayermp.field_70161_v;
            if (d0 * d0 + d1 * d1 + d2 * d2 < p_148543_8_ * p_148543_8_) {
               entityplayermp.field_71135_a.func_147359_a(p_148543_11_);
            }
         }
      }

   }

   public void func_72389_g() {
      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         this.func_72391_b(this.field_72404_b.get(i));
      }

   }

   public void func_152601_d(GameProfile p_152601_1_) {
      this.field_72411_j.func_152687_a(new UserListWhitelistEntry(p_152601_1_));
   }

   public void func_152597_c(GameProfile p_152597_1_) {
      this.field_72411_j.func_152684_c(p_152597_1_);
   }

   public UserListWhitelist func_152599_k() {
      return this.field_72411_j;
   }

   public String[] func_152598_l() {
      return this.field_72411_j.func_152685_a();
   }

   public UserListOps func_152603_m() {
      return this.field_72414_i;
   }

   public String[] func_152606_n() {
      return this.field_72414_i.func_152685_a();
   }

   public void func_187244_a() {
   }

   public void func_72354_b(EntityPlayerMP p_72354_1_, WorldServer p_72354_2_) {
      WorldBorder worldborder = this.field_72400_f.field_71305_c[0].func_175723_af();
      p_72354_1_.field_71135_a.func_147359_a(new SPacketWorldBorder(worldborder, SPacketWorldBorder.Action.INITIALIZE));
      p_72354_1_.field_71135_a.func_147359_a(new SPacketTimeUpdate(p_72354_2_.func_82737_E(), p_72354_2_.func_72820_D(), p_72354_2_.func_82736_K().func_82766_b("doDaylightCycle")));
      BlockPos blockpos = p_72354_2_.func_175694_M();
      p_72354_1_.field_71135_a.func_147359_a(new SPacketSpawnPosition(blockpos));
      if (p_72354_2_.func_72896_J()) {
         p_72354_1_.field_71135_a.func_147359_a(new SPacketChangeGameState(1, 0.0F));
         p_72354_1_.field_71135_a.func_147359_a(new SPacketChangeGameState(7, p_72354_2_.func_72867_j(1.0F)));
         p_72354_1_.field_71135_a.func_147359_a(new SPacketChangeGameState(8, p_72354_2_.func_72819_i(1.0F)));
      }

   }

   public void func_72385_f(EntityPlayerMP p_72385_1_) {
      p_72385_1_.func_71120_a(p_72385_1_.field_71069_bz);
      p_72385_1_.func_71118_n();
      p_72385_1_.field_71135_a.func_147359_a(new SPacketHeldItemChange(p_72385_1_.field_71071_by.field_70461_c));
   }

   public int func_72394_k() {
      return this.field_72404_b.size();
   }

   public int func_72352_l() {
      return this.field_72405_c;
   }

   public String[] func_72373_m() {
      return this.field_72400_f.field_71305_c[0].func_72860_G().func_75756_e().func_75754_f();
   }

   public void func_72371_a(boolean p_72371_1_) {
      this.field_72409_l = p_72371_1_;
   }

   public List<EntityPlayerMP> func_72382_j(String p_72382_1_) {
      List<EntityPlayerMP> list = Lists.<EntityPlayerMP>newArrayList();

      for(EntityPlayerMP entityplayermp : this.field_72404_b) {
         if (entityplayermp.func_71114_r().equals(p_72382_1_)) {
            list.add(entityplayermp);
         }
      }

      return list;
   }

   public int func_72395_o() {
      return this.field_72402_d;
   }

   public MinecraftServer func_72365_p() {
      return this.field_72400_f;
   }

   public NBTTagCompound func_72378_q() {
      return null;
   }

   public void func_152604_a(GameType p_152604_1_) {
      this.field_72410_m = p_152604_1_;
   }

   private void func_72381_a(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World p_72381_3_) {
      if (p_72381_2_ != null) {
         p_72381_1_.field_71134_c.func_73076_a(p_72381_2_.field_71134_c.func_73081_b());
      } else if (this.field_72410_m != null) {
         p_72381_1_.field_71134_c.func_73076_a(this.field_72410_m);
      }

      p_72381_1_.field_71134_c.func_73077_b(p_72381_3_.func_72912_H().func_76077_q());
   }

   public void func_72387_b(boolean p_72387_1_) {
      this.field_72407_n = p_72387_1_;
   }

   public void func_72392_r() {
      for(int i = 0; i < this.field_72404_b.size(); ++i) {
         (this.field_72404_b.get(i)).field_71135_a.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.server_shutdown", new Object[0]));
      }

   }

   public void func_148544_a(ITextComponent p_148544_1_, boolean p_148544_2_) {
      this.field_72400_f.func_145747_a(p_148544_1_);
      ChatType chattype = p_148544_2_ ? ChatType.SYSTEM : ChatType.CHAT;
      this.func_148540_a(new SPacketChat(p_148544_1_, chattype));
   }

   public void func_148539_a(ITextComponent p_148539_1_) {
      this.func_148544_a(p_148539_1_, true);
   }

   public StatisticsManagerServer func_152602_a(EntityPlayer p_152602_1_) {
      UUID uuid = p_152602_1_.func_110124_au();
      StatisticsManagerServer statisticsmanagerserver = uuid == null ? null : (StatisticsManagerServer)this.field_148547_k.get(uuid);
      if (statisticsmanagerserver == null) {
         File file1 = new File(this.field_72400_f.func_71218_a(0).func_72860_G().func_75765_b(), "stats");
         File file2 = new File(file1, uuid + ".json");
         if (!file2.exists()) {
            File file3 = new File(file1, p_152602_1_.func_70005_c_() + ".json");
            if (file3.exists() && file3.isFile()) {
               file3.renameTo(file2);
            }
         }

         statisticsmanagerserver = new StatisticsManagerServer(this.field_72400_f, file2);
         statisticsmanagerserver.func_150882_a();
         this.field_148547_k.put(uuid, statisticsmanagerserver);
      }

      return statisticsmanagerserver;
   }

   public PlayerAdvancements func_192054_h(EntityPlayerMP p_192054_1_) {
      UUID uuid = p_192054_1_.func_110124_au();
      PlayerAdvancements playeradvancements = this.field_192055_p.get(uuid);
      if (playeradvancements == null) {
         File file1 = new File(this.field_72400_f.func_71218_a(0).func_72860_G().func_75765_b(), "advancements");
         File file2 = new File(file1, uuid + ".json");
         playeradvancements = new PlayerAdvancements(this.field_72400_f, file2, p_192054_1_);
         this.field_192055_p.put(uuid, playeradvancements);
      }

      playeradvancements.func_192739_a(p_192054_1_);
      return playeradvancements;
   }

   public void func_152611_a(int p_152611_1_) {
      this.field_72402_d = p_152611_1_;
      if (this.field_72400_f.field_71305_c != null) {
         for(WorldServer worldserver : this.field_72400_f.field_71305_c) {
            if (worldserver != null) {
               worldserver.func_184164_w().func_152622_a(p_152611_1_);
               worldserver.func_73039_n().func_187252_a(p_152611_1_);
            }
         }

      }
   }

   public List<EntityPlayerMP> func_181057_v() {
      return this.field_72404_b;
   }

   public EntityPlayerMP func_177451_a(UUID p_177451_1_) {
      return this.field_177454_f.get(p_177451_1_);
   }

   public boolean func_183023_f(GameProfile p_183023_1_) {
      return false;
   }

   public void func_193244_w() {
      for(PlayerAdvancements playeradvancements : this.field_192055_p.values()) {
         playeradvancements.func_193766_b();
      }

   }
}
