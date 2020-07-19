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

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
   private static final Logger field_147370_c = LogManager.getLogger();
   public final NetworkManager field_147371_a;
   private final MinecraftServer field_147367_d;
   public EntityPlayerMP field_147369_b;
   private int field_147368_e;
   private int field_147378_h;
   private long field_147379_i;
   private long field_147377_k;
   private int field_147374_l;
   private int field_147375_m;
   private final IntHashMap<Short> field_147372_n = new IntHashMap<Short>();
   private double field_184349_l;
   private double field_184350_m;
   private double field_184351_n;
   private double field_184352_o;
   private double field_184353_p;
   private double field_184354_q;
   private Entity field_184355_r;
   private double field_184356_s;
   private double field_184357_t;
   private double field_184358_u;
   private double field_184359_v;
   private double field_184360_w;
   private double field_184361_x;
   private Vec3d field_184362_y;
   private int field_184363_z;
   private int field_184343_A;
   private boolean field_184344_B;
   private int field_147365_f;
   private boolean field_184345_D;
   private int field_184346_E;
   private int field_184347_F;
   private int field_184348_G;

   public NetHandlerPlayServer(MinecraftServer p_i1530_1_, NetworkManager p_i1530_2_, EntityPlayerMP p_i1530_3_) {
      this.field_147367_d = p_i1530_1_;
      this.field_147371_a = p_i1530_2_;
      p_i1530_2_.func_150719_a(this);
      this.field_147369_b = p_i1530_3_;
      p_i1530_3_.field_71135_a = this;
   }

   public void func_73660_a() {
      this.func_184342_d();
      this.field_147369_b.func_71127_g();
      this.field_147369_b.func_70080_a(this.field_184349_l, this.field_184350_m, this.field_184351_n, this.field_147369_b.field_70177_z, this.field_147369_b.field_70125_A);
      ++this.field_147368_e;
      this.field_184348_G = this.field_184347_F;
      if (this.field_184344_B) {
         if (++this.field_147365_f > 80) {
            field_147370_c.warn("{} was kicked for floating too long!", (Object)this.field_147369_b.func_70005_c_());
            this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
            return;
         }
      } else {
         this.field_184344_B = false;
         this.field_147365_f = 0;
      }

      this.field_184355_r = this.field_147369_b.func_184208_bv();
      if (this.field_184355_r != this.field_147369_b && this.field_184355_r.func_184179_bs() == this.field_147369_b) {
         this.field_184356_s = this.field_184355_r.field_70165_t;
         this.field_184357_t = this.field_184355_r.field_70163_u;
         this.field_184358_u = this.field_184355_r.field_70161_v;
         this.field_184359_v = this.field_184355_r.field_70165_t;
         this.field_184360_w = this.field_184355_r.field_70163_u;
         this.field_184361_x = this.field_184355_r.field_70161_v;
         if (this.field_184345_D && this.field_147369_b.func_184208_bv().func_184179_bs() == this.field_147369_b) {
            if (++this.field_184346_E > 80) {
               field_147370_c.warn("{} was kicked for floating a vehicle too long!", (Object)this.field_147369_b.func_70005_c_());
               this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.flying", new Object[0]));
               return;
            }
         } else {
            this.field_184345_D = false;
            this.field_184346_E = 0;
         }
      } else {
         this.field_184355_r = null;
         this.field_184345_D = false;
         this.field_184346_E = 0;
      }

      this.field_147367_d.field_71304_b.func_76320_a("keepAlive");
      if ((long)this.field_147368_e - this.field_147377_k > 40L) {
         this.field_147377_k = (long)this.field_147368_e;
         this.field_147379_i = this.func_147363_d();
         this.field_147378_h = (int)this.field_147379_i;
         this.func_147359_a(new SPacketKeepAlive(this.field_147378_h));
      }

      this.field_147367_d.field_71304_b.func_76319_b();
      if (this.field_147374_l > 0) {
         --this.field_147374_l;
      }

      if (this.field_147375_m > 0) {
         --this.field_147375_m;
      }

      if (this.field_147369_b.func_154331_x() > 0L && this.field_147367_d.func_143007_ar() > 0 && MinecraftServer.func_130071_aq() - this.field_147369_b.func_154331_x() > (long)(this.field_147367_d.func_143007_ar() * 1000 * 60)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.idling", new Object[0]));
      }

   }

   private void func_184342_d() {
      this.field_184349_l = this.field_147369_b.field_70165_t;
      this.field_184350_m = this.field_147369_b.field_70163_u;
      this.field_184351_n = this.field_147369_b.field_70161_v;
      this.field_184352_o = this.field_147369_b.field_70165_t;
      this.field_184353_p = this.field_147369_b.field_70163_u;
      this.field_184354_q = this.field_147369_b.field_70161_v;
   }

   public NetworkManager func_147362_b() {
      return this.field_147371_a;
   }

   public void func_194028_b(final ITextComponent p_194028_1_) {
      this.field_147371_a.func_179288_a(new SPacketDisconnect(p_194028_1_), new GenericFutureListener<Future<? super Void>>() {
         public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
            NetHandlerPlayServer.this.field_147371_a.func_150718_a(p_194028_1_);
         }
      });
      this.field_147371_a.func_150721_g();
      Futures.getUnchecked(this.field_147367_d.func_152344_a(new Runnable() {
         public void run() {
            NetHandlerPlayServer.this.field_147371_a.func_179293_l();
         }
      }));
   }

   public void func_147358_a(CPacketInput p_147358_1_) {
      PacketThreadUtil.func_180031_a(p_147358_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_110430_a(p_147358_1_.func_149620_c(), p_147358_1_.func_192620_b(), p_147358_1_.func_149618_e(), p_147358_1_.func_149617_f());
   }

   private static boolean func_183006_b(CPacketPlayer p_183006_0_) {
      if (Doubles.isFinite(p_183006_0_.func_186997_a(0.0D)) && Doubles.isFinite(p_183006_0_.func_186996_b(0.0D)) && Doubles.isFinite(p_183006_0_.func_187000_c(0.0D)) && Floats.isFinite(p_183006_0_.func_186998_b(0.0F)) && Floats.isFinite(p_183006_0_.func_186999_a(0.0F))) {
         return Math.abs(p_183006_0_.func_186997_a(0.0D)) > 3.0E7D || Math.abs(p_183006_0_.func_186996_b(0.0D)) > 3.0E7D || Math.abs(p_183006_0_.func_187000_c(0.0D)) > 3.0E7D;
      } else {
         return true;
      }
   }

   private static boolean func_184341_b(CPacketVehicleMove p_184341_0_) {
      return !Doubles.isFinite(p_184341_0_.func_187004_a()) || !Doubles.isFinite(p_184341_0_.func_187002_b()) || !Doubles.isFinite(p_184341_0_.func_187003_c()) || !Floats.isFinite(p_184341_0_.func_187005_e()) || !Floats.isFinite(p_184341_0_.func_187006_d());
   }

   public void func_184338_a(CPacketVehicleMove p_184338_1_) {
      PacketThreadUtil.func_180031_a(p_184338_1_, this, this.field_147369_b.func_71121_q());
      if (func_184341_b(p_184338_1_)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_vehicle_movement", new Object[0]));
      } else {
         Entity entity = this.field_147369_b.func_184208_bv();
         if (entity != this.field_147369_b && entity.func_184179_bs() == this.field_147369_b && entity == this.field_184355_r) {
            WorldServer worldserver = this.field_147369_b.func_71121_q();
            double d0 = entity.field_70165_t;
            double d1 = entity.field_70163_u;
            double d2 = entity.field_70161_v;
            double d3 = p_184338_1_.func_187004_a();
            double d4 = p_184338_1_.func_187002_b();
            double d5 = p_184338_1_.func_187003_c();
            float f = p_184338_1_.func_187006_d();
            float f1 = p_184338_1_.func_187005_e();
            double d6 = d3 - this.field_184356_s;
            double d7 = d4 - this.field_184357_t;
            double d8 = d5 - this.field_184358_u;
            double d9 = entity.field_70159_w * entity.field_70159_w + entity.field_70181_x * entity.field_70181_x + entity.field_70179_y * entity.field_70179_y;
            double d10 = d6 * d6 + d7 * d7 + d8 * d8;
            if (d10 - d9 > 100.0D && (!this.field_147367_d.func_71264_H() || !this.field_147367_d.func_71214_G().equals(entity.func_70005_c_()))) {
               field_147370_c.warn("{} (vehicle of {}) moved too quickly! {},{},{}", entity.func_70005_c_(), this.field_147369_b.func_70005_c_(), Double.valueOf(d6), Double.valueOf(d7), Double.valueOf(d8));
               this.field_147371_a.func_179290_a(new SPacketMoveVehicle(entity));
               return;
            }

            boolean flag = worldserver.func_184144_a(entity, entity.func_174813_aQ().func_186664_h(0.0625D)).isEmpty();
            d6 = d3 - this.field_184359_v;
            d7 = d4 - this.field_184360_w - 1.0E-6D;
            d8 = d5 - this.field_184361_x;
            entity.func_70091_d(MoverType.PLAYER, d6, d7, d8);
            double d11 = d7;
            d6 = d3 - entity.field_70165_t;
            d7 = d4 - entity.field_70163_u;
            if (d7 > -0.5D || d7 < 0.5D) {
               d7 = 0.0D;
            }

            d8 = d5 - entity.field_70161_v;
            d10 = d6 * d6 + d7 * d7 + d8 * d8;
            boolean flag1 = false;
            if (d10 > 0.0625D) {
               flag1 = true;
               field_147370_c.warn("{} moved wrongly!", (Object)entity.func_70005_c_());
            }

            entity.func_70080_a(d3, d4, d5, f, f1);
            boolean flag2 = worldserver.func_184144_a(entity, entity.func_174813_aQ().func_186664_h(0.0625D)).isEmpty();
            if (flag && (flag1 || !flag2)) {
               entity.func_70080_a(d0, d1, d2, f, f1);
               this.field_147371_a.func_179290_a(new SPacketMoveVehicle(entity));
               return;
            }

            this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
            this.field_147369_b.func_71000_j(this.field_147369_b.field_70165_t - d0, this.field_147369_b.field_70163_u - d1, this.field_147369_b.field_70161_v - d2);
            this.field_184345_D = d11 >= -0.03125D && !this.field_147367_d.func_71231_X() && !worldserver.func_72829_c(entity.func_174813_aQ().func_186662_g(0.0625D).func_72321_a(0.0D, -0.55D, 0.0D));
            this.field_184359_v = entity.field_70165_t;
            this.field_184360_w = entity.field_70163_u;
            this.field_184361_x = entity.field_70161_v;
         }

      }
   }

   public void func_184339_a(CPacketConfirmTeleport p_184339_1_) {
      PacketThreadUtil.func_180031_a(p_184339_1_, this, this.field_147369_b.func_71121_q());
      if (p_184339_1_.func_186987_a() == this.field_184363_z) {
         this.field_147369_b.func_70080_a(this.field_184362_y.field_72450_a, this.field_184362_y.field_72448_b, this.field_184362_y.field_72449_c, this.field_147369_b.field_70177_z, this.field_147369_b.field_70125_A);
         if (this.field_147369_b.func_184850_K()) {
            this.field_184352_o = this.field_184362_y.field_72450_a;
            this.field_184353_p = this.field_184362_y.field_72448_b;
            this.field_184354_q = this.field_184362_y.field_72449_c;
            this.field_147369_b.func_184846_L();
         }

         this.field_184362_y = null;
      }

   }

   public void func_191984_a(CPacketRecipeInfo p_191984_1_) {
      PacketThreadUtil.func_180031_a(p_191984_1_, this, this.field_147369_b.func_71121_q());
      if (p_191984_1_.func_194156_a() == CPacketRecipeInfo.Purpose.SHOWN) {
         this.field_147369_b.func_192037_E().func_194074_f(p_191984_1_.func_193648_b());
      } else if (p_191984_1_.func_194156_a() == CPacketRecipeInfo.Purpose.SETTINGS) {
         this.field_147369_b.func_192037_E().func_192813_a(p_191984_1_.func_192624_c());
         this.field_147369_b.func_192037_E().func_192810_b(p_191984_1_.func_192625_d());
      }

   }

   public void func_194027_a(CPacketSeenAdvancements p_194027_1_) {
      PacketThreadUtil.func_180031_a(p_194027_1_, this, this.field_147369_b.func_71121_q());
      if (p_194027_1_.func_194162_b() == CPacketSeenAdvancements.Action.OPENED_TAB) {
         ResourceLocation resourcelocation = p_194027_1_.func_194165_c();
         Advancement advancement = this.field_147367_d.func_191949_aK().func_192778_a(resourcelocation);
         if (advancement != null) {
            this.field_147369_b.func_192039_O().func_194220_a(advancement);
         }
      }

   }

   public void func_147347_a(CPacketPlayer p_147347_1_) {
      PacketThreadUtil.func_180031_a(p_147347_1_, this, this.field_147369_b.func_71121_q());
      if (func_183006_b(p_147347_1_)) {
         this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_player_movement", new Object[0]));
      } else {
         WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
         if (!this.field_147369_b.field_71136_j) {
            if (this.field_147368_e == 0) {
               this.func_184342_d();
            }

            if (this.field_184362_y != null) {
               if (this.field_147368_e - this.field_184343_A > 20) {
                  this.field_184343_A = this.field_147368_e;
                  this.func_147364_a(this.field_184362_y.field_72450_a, this.field_184362_y.field_72448_b, this.field_184362_y.field_72449_c, this.field_147369_b.field_70177_z, this.field_147369_b.field_70125_A);
               }

            } else {
               this.field_184343_A = this.field_147368_e;
               if (this.field_147369_b.func_184218_aH()) {
                  this.field_147369_b.func_70080_a(this.field_147369_b.field_70165_t, this.field_147369_b.field_70163_u, this.field_147369_b.field_70161_v, p_147347_1_.func_186999_a(this.field_147369_b.field_70177_z), p_147347_1_.func_186998_b(this.field_147369_b.field_70125_A));
                  this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
               } else {
                  double d0 = this.field_147369_b.field_70165_t;
                  double d1 = this.field_147369_b.field_70163_u;
                  double d2 = this.field_147369_b.field_70161_v;
                  double d3 = this.field_147369_b.field_70163_u;
                  double d4 = p_147347_1_.func_186997_a(this.field_147369_b.field_70165_t);
                  double d5 = p_147347_1_.func_186996_b(this.field_147369_b.field_70163_u);
                  double d6 = p_147347_1_.func_187000_c(this.field_147369_b.field_70161_v);
                  float f = p_147347_1_.func_186999_a(this.field_147369_b.field_70177_z);
                  float f1 = p_147347_1_.func_186998_b(this.field_147369_b.field_70125_A);
                  double d7 = d4 - this.field_184349_l;
                  double d8 = d5 - this.field_184350_m;
                  double d9 = d6 - this.field_184351_n;
                  double d10 = this.field_147369_b.field_70159_w * this.field_147369_b.field_70159_w + this.field_147369_b.field_70181_x * this.field_147369_b.field_70181_x + this.field_147369_b.field_70179_y * this.field_147369_b.field_70179_y;
                  double d11 = d7 * d7 + d8 * d8 + d9 * d9;
                  if (this.field_147369_b.func_70608_bn()) {
                     if (d11 > 1.0D) {
                        this.func_147364_a(this.field_147369_b.field_70165_t, this.field_147369_b.field_70163_u, this.field_147369_b.field_70161_v, p_147347_1_.func_186999_a(this.field_147369_b.field_70177_z), p_147347_1_.func_186998_b(this.field_147369_b.field_70125_A));
                     }

                  } else {
                     ++this.field_184347_F;
                     int i = this.field_184347_F - this.field_184348_G;
                     if (i > 5) {
                        field_147370_c.debug("{} is sending move packets too frequently ({} packets since last tick)", this.field_147369_b.func_70005_c_(), Integer.valueOf(i));
                        i = 1;
                     }

                     if (!this.field_147369_b.func_184850_K() && (!this.field_147369_b.func_71121_q().func_82736_K().func_82766_b("disableElytraMovementCheck") || !this.field_147369_b.func_184613_cA())) {
                        float f2 = this.field_147369_b.func_184613_cA() ? 300.0F : 100.0F;
                        if (d11 - d10 > (double)(f2 * (float)i) && (!this.field_147367_d.func_71264_H() || !this.field_147367_d.func_71214_G().equals(this.field_147369_b.func_70005_c_()))) {
                           field_147370_c.warn("{} moved too quickly! {},{},{}", this.field_147369_b.func_70005_c_(), Double.valueOf(d7), Double.valueOf(d8), Double.valueOf(d9));
                           this.func_147364_a(this.field_147369_b.field_70165_t, this.field_147369_b.field_70163_u, this.field_147369_b.field_70161_v, this.field_147369_b.field_70177_z, this.field_147369_b.field_70125_A);
                           return;
                        }
                     }

                     boolean flag2 = worldserver.func_184144_a(this.field_147369_b, this.field_147369_b.func_174813_aQ().func_186664_h(0.0625D)).isEmpty();
                     d7 = d4 - this.field_184352_o;
                     d8 = d5 - this.field_184353_p;
                     d9 = d6 - this.field_184354_q;
                     if (this.field_147369_b.field_70122_E && !p_147347_1_.func_149465_i() && d8 > 0.0D) {
                        this.field_147369_b.func_70664_aZ();
                     }

                     this.field_147369_b.func_70091_d(MoverType.PLAYER, d7, d8, d9);
                     this.field_147369_b.field_70122_E = p_147347_1_.func_149465_i();
                     double d12 = d8;
                     d7 = d4 - this.field_147369_b.field_70165_t;
                     d8 = d5 - this.field_147369_b.field_70163_u;
                     if (d8 > -0.5D || d8 < 0.5D) {
                        d8 = 0.0D;
                     }

                     d9 = d6 - this.field_147369_b.field_70161_v;
                     d11 = d7 * d7 + d8 * d8 + d9 * d9;
                     boolean flag = false;
                     if (!this.field_147369_b.func_184850_K() && d11 > 0.0625D && !this.field_147369_b.func_70608_bn() && !this.field_147369_b.field_71134_c.func_73083_d() && this.field_147369_b.field_71134_c.func_73081_b() != GameType.SPECTATOR) {
                        flag = true;
                        field_147370_c.warn("{} moved wrongly!", (Object)this.field_147369_b.func_70005_c_());
                     }

                     this.field_147369_b.func_70080_a(d4, d5, d6, f, f1);
                     this.field_147369_b.func_71000_j(this.field_147369_b.field_70165_t - d0, this.field_147369_b.field_70163_u - d1, this.field_147369_b.field_70161_v - d2);
                     if (!this.field_147369_b.field_70145_X && !this.field_147369_b.func_70608_bn()) {
                        boolean flag1 = worldserver.func_184144_a(this.field_147369_b, this.field_147369_b.func_174813_aQ().func_186664_h(0.0625D)).isEmpty();
                        if (flag2 && (flag || !flag1)) {
                           this.func_147364_a(d0, d1, d2, f, f1);
                           return;
                        }
                     }

                     this.field_184344_B = d12 >= -0.03125D;
                     this.field_184344_B &= !this.field_147367_d.func_71231_X() && !this.field_147369_b.field_71075_bZ.field_75101_c;
                     this.field_184344_B &= !this.field_147369_b.func_70644_a(MobEffects.field_188424_y) && !this.field_147369_b.func_184613_cA() && !worldserver.func_72829_c(this.field_147369_b.func_174813_aQ().func_186662_g(0.0625D).func_72321_a(0.0D, -0.55D, 0.0D));
                     this.field_147369_b.field_70122_E = p_147347_1_.func_149465_i();
                     this.field_147367_d.func_184103_al().func_72358_d(this.field_147369_b);
                     this.field_147369_b.func_71122_b(this.field_147369_b.field_70163_u - d3, p_147347_1_.func_149465_i());
                     this.field_184352_o = this.field_147369_b.field_70165_t;
                     this.field_184353_p = this.field_147369_b.field_70163_u;
                     this.field_184354_q = this.field_147369_b.field_70161_v;
                  }
               }
            }
         }
      }
   }

   public void func_147364_a(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_) {
      this.func_175089_a(p_147364_1_, p_147364_3_, p_147364_5_, p_147364_7_, p_147364_8_, Collections.emptySet());
   }

   public void func_175089_a(double p_175089_1_, double p_175089_3_, double p_175089_5_, float p_175089_7_, float p_175089_8_, Set<SPacketPlayerPosLook.EnumFlags> p_175089_9_) {
      double d0 = p_175089_9_.contains(SPacketPlayerPosLook.EnumFlags.X) ? this.field_147369_b.field_70165_t : 0.0D;
      double d1 = p_175089_9_.contains(SPacketPlayerPosLook.EnumFlags.Y) ? this.field_147369_b.field_70163_u : 0.0D;
      double d2 = p_175089_9_.contains(SPacketPlayerPosLook.EnumFlags.Z) ? this.field_147369_b.field_70161_v : 0.0D;
      this.field_184362_y = new Vec3d(p_175089_1_ + d0, p_175089_3_ + d1, p_175089_5_ + d2);
      float f = p_175089_7_;
      float f1 = p_175089_8_;
      if (p_175089_9_.contains(SPacketPlayerPosLook.EnumFlags.Y_ROT)) {
         f = p_175089_7_ + this.field_147369_b.field_70177_z;
      }

      if (p_175089_9_.contains(SPacketPlayerPosLook.EnumFlags.X_ROT)) {
         f1 = p_175089_8_ + this.field_147369_b.field_70125_A;
      }

      if (++this.field_184363_z == Integer.MAX_VALUE) {
         this.field_184363_z = 0;
      }

      this.field_184343_A = this.field_147368_e;
      this.field_147369_b.func_70080_a(this.field_184362_y.field_72450_a, this.field_184362_y.field_72448_b, this.field_184362_y.field_72449_c, f, f1);
      this.field_147369_b.field_71135_a.func_147359_a(new SPacketPlayerPosLook(p_175089_1_, p_175089_3_, p_175089_5_, p_175089_7_, p_175089_8_, p_175089_9_, this.field_184363_z));
   }

   public void func_147345_a(CPacketPlayerDigging p_147345_1_) {
      PacketThreadUtil.func_180031_a(p_147345_1_, this, this.field_147369_b.func_71121_q());
      WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      BlockPos blockpos = p_147345_1_.func_179715_a();
      this.field_147369_b.func_143004_u();
      switch(p_147345_1_.func_180762_c()) {
      case SWAP_HELD_ITEMS:
         if (!this.field_147369_b.func_175149_v()) {
            ItemStack itemstack = this.field_147369_b.func_184586_b(EnumHand.OFF_HAND);
            this.field_147369_b.func_184611_a(EnumHand.OFF_HAND, this.field_147369_b.func_184586_b(EnumHand.MAIN_HAND));
            this.field_147369_b.func_184611_a(EnumHand.MAIN_HAND, itemstack);
         }

         return;
      case DROP_ITEM:
         if (!this.field_147369_b.func_175149_v()) {
            this.field_147369_b.func_71040_bB(false);
         }

         return;
      case DROP_ALL_ITEMS:
         if (!this.field_147369_b.func_175149_v()) {
            this.field_147369_b.func_71040_bB(true);
         }

         return;
      case RELEASE_USE_ITEM:
         this.field_147369_b.func_184597_cx();
         return;
      case START_DESTROY_BLOCK:
      case ABORT_DESTROY_BLOCK:
      case STOP_DESTROY_BLOCK:
         double d0 = this.field_147369_b.field_70165_t - ((double)blockpos.func_177958_n() + 0.5D);
         double d1 = this.field_147369_b.field_70163_u - ((double)blockpos.func_177956_o() + 0.5D) + 1.5D;
         double d2 = this.field_147369_b.field_70161_v - ((double)blockpos.func_177952_p() + 0.5D);
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 > 36.0D) {
            return;
         } else if (blockpos.func_177956_o() >= this.field_147367_d.func_71207_Z()) {
            return;
         } else {
            if (p_147345_1_.func_180762_c() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
               if (!this.field_147367_d.func_175579_a(worldserver, blockpos, this.field_147369_b) && worldserver.func_175723_af().func_177746_a(blockpos)) {
                  this.field_147369_b.field_71134_c.func_180784_a(blockpos, p_147345_1_.func_179714_b());
               } else {
                  this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(worldserver, blockpos));
               }
            } else {
               if (p_147345_1_.func_180762_c() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                  this.field_147369_b.field_71134_c.func_180785_a(blockpos);
               } else if (p_147345_1_.func_180762_c() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                  this.field_147369_b.field_71134_c.func_180238_e();
               }

               if (worldserver.func_180495_p(blockpos).func_185904_a() != Material.field_151579_a) {
                  this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(worldserver, blockpos));
               }
            }

            return;
         }
      default:
         throw new IllegalArgumentException("Invalid player action");
      }
   }

   public void func_184337_a(CPacketPlayerTryUseItemOnBlock p_184337_1_) {
      PacketThreadUtil.func_180031_a(p_184337_1_, this, this.field_147369_b.func_71121_q());
      WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      EnumHand enumhand = p_184337_1_.func_187022_c();
      ItemStack itemstack = this.field_147369_b.func_184586_b(enumhand);
      BlockPos blockpos = p_184337_1_.func_187023_a();
      EnumFacing enumfacing = p_184337_1_.func_187024_b();
      this.field_147369_b.func_143004_u();
      if (blockpos.func_177956_o() < this.field_147367_d.func_71207_Z() - 1 || enumfacing != EnumFacing.UP && blockpos.func_177956_o() < this.field_147367_d.func_71207_Z()) {
         if (this.field_184362_y == null && this.field_147369_b.func_70092_e((double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.5D, (double)blockpos.func_177952_p() + 0.5D) < 64.0D && !this.field_147367_d.func_175579_a(worldserver, blockpos, this.field_147369_b) && worldserver.func_175723_af().func_177746_a(blockpos)) {
            this.field_147369_b.field_71134_c.func_187251_a(this.field_147369_b, worldserver, itemstack, enumhand, blockpos, enumfacing, p_184337_1_.func_187026_d(), p_184337_1_.func_187025_e(), p_184337_1_.func_187020_f());
         }
      } else {
         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("build.tooHigh", new Object[]{this.field_147367_d.func_71207_Z()});
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.RED);
         this.field_147369_b.field_71135_a.func_147359_a(new SPacketChat(textcomponenttranslation, ChatType.GAME_INFO));
      }

      this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(worldserver, blockpos));
      this.field_147369_b.field_71135_a.func_147359_a(new SPacketBlockChange(worldserver, blockpos.func_177972_a(enumfacing)));
   }

   public void func_147346_a(CPacketPlayerTryUseItem p_147346_1_) {
      PacketThreadUtil.func_180031_a(p_147346_1_, this, this.field_147369_b.func_71121_q());
      WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      EnumHand enumhand = p_147346_1_.func_187028_a();
      ItemStack itemstack = this.field_147369_b.func_184586_b(enumhand);
      this.field_147369_b.func_143004_u();
      if (!itemstack.func_190926_b()) {
         this.field_147369_b.field_71134_c.func_187250_a(this.field_147369_b, worldserver, itemstack, enumhand);
      }
   }

   public void func_175088_a(CPacketSpectate p_175088_1_) {
      PacketThreadUtil.func_180031_a(p_175088_1_, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_175149_v()) {
         Entity entity = null;

         for(WorldServer worldserver : this.field_147367_d.field_71305_c) {
            if (worldserver != null) {
               entity = p_175088_1_.func_179727_a(worldserver);
               if (entity != null) {
                  break;
               }
            }
         }

         if (entity != null) {
            this.field_147369_b.func_175399_e(this.field_147369_b);
            this.field_147369_b.func_184210_p();
            if (entity.field_70170_p == this.field_147369_b.field_70170_p) {
               this.field_147369_b.func_70634_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
            } else {
               WorldServer worldserver1 = this.field_147369_b.func_71121_q();
               WorldServer worldserver2 = (WorldServer)entity.field_70170_p;
               this.field_147369_b.field_71093_bK = entity.field_71093_bK;
               this.func_147359_a(new SPacketRespawn(this.field_147369_b.field_71093_bK, worldserver1.func_175659_aa(), worldserver1.func_72912_H().func_76067_t(), this.field_147369_b.field_71134_c.func_73081_b()));
               this.field_147367_d.func_184103_al().func_187243_f(this.field_147369_b);
               worldserver1.func_72973_f(this.field_147369_b);
               this.field_147369_b.field_70128_L = false;
               this.field_147369_b.func_70012_b(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70177_z, entity.field_70125_A);
               if (this.field_147369_b.func_70089_S()) {
                  worldserver1.func_72866_a(this.field_147369_b, false);
                  worldserver2.func_72838_d(this.field_147369_b);
                  worldserver2.func_72866_a(this.field_147369_b, false);
               }

               this.field_147369_b.func_70029_a(worldserver2);
               this.field_147367_d.func_184103_al().func_72375_a(this.field_147369_b, worldserver1);
               this.field_147369_b.func_70634_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
               this.field_147369_b.field_71134_c.func_73080_a(worldserver2);
               this.field_147367_d.func_184103_al().func_72354_b(this.field_147369_b, worldserver2);
               this.field_147367_d.func_184103_al().func_72385_f(this.field_147369_b);
            }
         }
      }

   }

   public void func_175086_a(CPacketResourcePackStatus p_175086_1_) {
   }

   public void func_184340_a(CPacketSteerBoat p_184340_1_) {
      PacketThreadUtil.func_180031_a(p_184340_1_, this, this.field_147369_b.func_71121_q());
      Entity entity = this.field_147369_b.func_184187_bx();
      if (entity instanceof EntityBoat) {
         ((EntityBoat)entity).func_184445_a(p_184340_1_.func_187012_a(), p_184340_1_.func_187014_b());
      }

   }

   public void func_147231_a(ITextComponent p_147231_1_) {
      field_147370_c.info("{} lost connection: {}", this.field_147369_b.func_70005_c_(), p_147231_1_.func_150260_c());
      this.field_147367_d.func_147132_au();
      TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("multiplayer.player.left", new Object[]{this.field_147369_b.func_145748_c_()});
      textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.YELLOW);
      this.field_147367_d.func_184103_al().func_148539_a(textcomponenttranslation);
      this.field_147369_b.func_71123_m();
      this.field_147367_d.func_184103_al().func_72367_e(this.field_147369_b);
      if (this.field_147367_d.func_71264_H() && this.field_147369_b.func_70005_c_().equals(this.field_147367_d.func_71214_G())) {
         field_147370_c.info("Stopping singleplayer server as player logged out");
         this.field_147367_d.func_71263_m();
      }

   }

   public void func_147359_a(final Packet<?> p_147359_1_) {
      if (p_147359_1_ instanceof SPacketChat) {
         SPacketChat spacketchat = (SPacketChat)p_147359_1_;
         EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.field_147369_b.func_147096_v();
         if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN && spacketchat.func_192590_c() != ChatType.GAME_INFO) {
            return;
         }

         if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !spacketchat.func_148916_d()) {
            return;
         }
      }

      try {
         this.field_147371_a.func_179290_a(p_147359_1_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Sending packet");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Packet being sent");
         crashreportcategory.func_189529_a("Packet class", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               return p_147359_1_.getClass().getCanonicalName();
            }
         });
         throw new ReportedException(crashreport);
      }
   }

   public void func_147355_a(CPacketHeldItemChange p_147355_1_) {
      PacketThreadUtil.func_180031_a(p_147355_1_, this, this.field_147369_b.func_71121_q());
      if (p_147355_1_.func_149614_c() >= 0 && p_147355_1_.func_149614_c() < InventoryPlayer.func_70451_h()) {
         this.field_147369_b.field_71071_by.field_70461_c = p_147355_1_.func_149614_c();
         this.field_147369_b.func_143004_u();
      } else {
         field_147370_c.warn("{} tried to set an invalid carried item", (Object)this.field_147369_b.func_70005_c_());
      }
   }

   public void func_147354_a(CPacketChatMessage p_147354_1_) {
      PacketThreadUtil.func_180031_a(p_147354_1_, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("chat.cannotSend", new Object[0]);
         textcomponenttranslation.func_150256_b().func_150238_a(TextFormatting.RED);
         this.func_147359_a(new SPacketChat(textcomponenttranslation));
      } else {
         this.field_147369_b.func_143004_u();
         String s = p_147354_1_.func_149439_c();
         s = StringUtils.normalizeSpace(s);

         for(int i = 0; i < s.length(); ++i) {
            if (!ChatAllowedCharacters.func_71566_a(s.charAt(i))) {
               this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.illegal_characters", new Object[0]));
               return;
            }
         }

         if (s.startsWith("/")) {
            this.func_147361_d(s);
         } else {
            ITextComponent itextcomponent = new TextComponentTranslation("chat.type.text", new Object[]{this.field_147369_b.func_145748_c_(), s});
            this.field_147367_d.func_184103_al().func_148544_a(itextcomponent, false);
         }

         this.field_147374_l += 20;
         if (this.field_147374_l > 200 && !this.field_147367_d.func_184103_al().func_152596_g(this.field_147369_b.func_146103_bH())) {
            this.func_194028_b(new TextComponentTranslation("disconnect.spam", new Object[0]));
         }

      }
   }

   private void func_147361_d(String p_147361_1_) {
      this.field_147367_d.func_71187_D().func_71556_a(this.field_147369_b, p_147361_1_);
   }

   public void func_175087_a(CPacketAnimation p_175087_1_) {
      PacketThreadUtil.func_180031_a(p_175087_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      this.field_147369_b.func_184609_a(p_175087_1_.func_187018_a());
   }

   public void func_147357_a(CPacketEntityAction p_147357_1_) {
      PacketThreadUtil.func_180031_a(p_147357_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      switch(p_147357_1_.func_180764_b()) {
      case START_SNEAKING:
         this.field_147369_b.func_70095_a(true);
         break;
      case STOP_SNEAKING:
         this.field_147369_b.func_70095_a(false);
         break;
      case START_SPRINTING:
         this.field_147369_b.func_70031_b(true);
         break;
      case STOP_SPRINTING:
         this.field_147369_b.func_70031_b(false);
         break;
      case STOP_SLEEPING:
         if (this.field_147369_b.func_70608_bn()) {
            this.field_147369_b.func_70999_a(false, true, true);
            this.field_184362_y = new Vec3d(this.field_147369_b.field_70165_t, this.field_147369_b.field_70163_u, this.field_147369_b.field_70161_v);
         }
         break;
      case START_RIDING_JUMP:
         if (this.field_147369_b.func_184187_bx() instanceof IJumpingMount) {
            IJumpingMount ijumpingmount1 = (IJumpingMount)this.field_147369_b.func_184187_bx();
            int i = p_147357_1_.func_149512_e();
            if (ijumpingmount1.func_184776_b() && i > 0) {
               ijumpingmount1.func_184775_b(i);
            }
         }
         break;
      case STOP_RIDING_JUMP:
         if (this.field_147369_b.func_184187_bx() instanceof IJumpingMount) {
            IJumpingMount ijumpingmount = (IJumpingMount)this.field_147369_b.func_184187_bx();
            ijumpingmount.func_184777_r_();
         }
         break;
      case OPEN_INVENTORY:
         if (this.field_147369_b.func_184187_bx() instanceof AbstractHorse) {
            ((AbstractHorse)this.field_147369_b.func_184187_bx()).func_110199_f(this.field_147369_b);
         }
         break;
      case START_FALL_FLYING:
         if (!this.field_147369_b.field_70122_E && this.field_147369_b.field_70181_x < 0.0D && !this.field_147369_b.func_184613_cA() && !this.field_147369_b.func_70090_H()) {
            ItemStack itemstack = this.field_147369_b.func_184582_a(EntityEquipmentSlot.CHEST);
            if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack)) {
               this.field_147369_b.func_184847_M();
            }
         } else {
            this.field_147369_b.func_189103_N();
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid client command!");
      }

   }

   public void func_147340_a(CPacketUseEntity p_147340_1_) {
      PacketThreadUtil.func_180031_a(p_147340_1_, this, this.field_147369_b.func_71121_q());
      WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      Entity entity = p_147340_1_.func_149564_a(worldserver);
      this.field_147369_b.func_143004_u();
      if (entity != null) {
         boolean flag = this.field_147369_b.func_70685_l(entity);
         double d0 = 36.0D;
         if (!flag) {
            d0 = 9.0D;
         }

         if (this.field_147369_b.func_70068_e(entity) < d0) {
            if (p_147340_1_.func_149565_c() == CPacketUseEntity.Action.INTERACT) {
               EnumHand enumhand = p_147340_1_.func_186994_b();
               this.field_147369_b.func_190775_a(entity, enumhand);
            } else if (p_147340_1_.func_149565_c() == CPacketUseEntity.Action.INTERACT_AT) {
               EnumHand enumhand1 = p_147340_1_.func_186994_b();
               entity.func_184199_a(this.field_147369_b, p_147340_1_.func_179712_b(), enumhand1);
            } else if (p_147340_1_.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
               if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow || entity == this.field_147369_b) {
                  this.func_194028_b(new TextComponentTranslation("multiplayer.disconnect.invalid_entity_attacked", new Object[0]));
                  this.field_147367_d.func_71236_h("Player " + this.field_147369_b.func_70005_c_() + " tried to attack an invalid entity");
                  return;
               }

               this.field_147369_b.func_71059_n(entity);
            }
         }
      }

   }

   public void func_147342_a(CPacketClientStatus p_147342_1_) {
      PacketThreadUtil.func_180031_a(p_147342_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      CPacketClientStatus.State cpacketclientstatus$state = p_147342_1_.func_149435_c();
      switch(cpacketclientstatus$state) {
      case PERFORM_RESPAWN:
         if (this.field_147369_b.field_71136_j) {
            this.field_147369_b.field_71136_j = false;
            this.field_147369_b = this.field_147367_d.func_184103_al().func_72368_a(this.field_147369_b, 0, true);
            CriteriaTriggers.field_193134_u.func_193143_a(this.field_147369_b, DimensionType.THE_END, DimensionType.OVERWORLD);
         } else {
            if (this.field_147369_b.func_110143_aJ() > 0.0F) {
               return;
            }

            this.field_147369_b = this.field_147367_d.func_184103_al().func_72368_a(this.field_147369_b, 0, false);
            if (this.field_147367_d.func_71199_h()) {
               this.field_147369_b.func_71033_a(GameType.SPECTATOR);
               this.field_147369_b.func_71121_q().func_82736_K().func_82764_b("spectatorsGenerateChunks", "false");
            }
         }
         break;
      case REQUEST_STATS:
         this.field_147369_b.func_147099_x().func_150876_a(this.field_147369_b);
      }

   }

   public void func_147356_a(CPacketCloseWindow p_147356_1_) {
      PacketThreadUtil.func_180031_a(p_147356_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_71128_l();
   }

   public void func_147351_a(CPacketClickWindow p_147351_1_) {
      PacketThreadUtil.func_180031_a(p_147351_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (this.field_147369_b.field_71070_bA.field_75152_c == p_147351_1_.func_149548_c() && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)) {
         if (this.field_147369_b.func_175149_v()) {
            NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>func_191196_a();

            for(int i = 0; i < this.field_147369_b.field_71070_bA.field_75151_b.size(); ++i) {
               nonnulllist.add(((Slot)this.field_147369_b.field_71070_bA.field_75151_b.get(i)).func_75211_c());
            }

            this.field_147369_b.func_71110_a(this.field_147369_b.field_71070_bA, nonnulllist);
         } else {
            ItemStack itemstack2 = this.field_147369_b.field_71070_bA.func_184996_a(p_147351_1_.func_149544_d(), p_147351_1_.func_149543_e(), p_147351_1_.func_186993_f(), this.field_147369_b);
            if (ItemStack.func_77989_b(p_147351_1_.func_149546_g(), itemstack2)) {
               this.field_147369_b.field_71135_a.func_147359_a(new SPacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), true));
               this.field_147369_b.field_71137_h = true;
               this.field_147369_b.field_71070_bA.func_75142_b();
               this.field_147369_b.func_71113_k();
               this.field_147369_b.field_71137_h = false;
            } else {
               this.field_147372_n.func_76038_a(this.field_147369_b.field_71070_bA.field_75152_c, Short.valueOf(p_147351_1_.func_149547_f()));
               this.field_147369_b.field_71135_a.func_147359_a(new SPacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), false));
               this.field_147369_b.field_71070_bA.func_75128_a(this.field_147369_b, false);
               NonNullList<ItemStack> nonnulllist1 = NonNullList.<ItemStack>func_191196_a();

               for(int j = 0; j < this.field_147369_b.field_71070_bA.field_75151_b.size(); ++j) {
                  ItemStack itemstack = ((Slot)this.field_147369_b.field_71070_bA.field_75151_b.get(j)).func_75211_c();
                  ItemStack itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack;
                  nonnulllist1.add(itemstack1);
               }

               this.field_147369_b.func_71110_a(this.field_147369_b.field_71070_bA, nonnulllist1);
            }
         }
      }

   }

   public void func_191985_a(CPacketRecipePlacement p_191985_1_) {
      PacketThreadUtil.func_180031_a(p_191985_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (this.field_147369_b.field_71070_bA.field_75152_c == p_191985_1_.func_192613_a() && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b)) {
         this.field_147369_b.field_71135_a.func_147359_a(new SPacketConfirmTransaction(p_191985_1_.func_192613_a(), p_191985_1_.func_192614_b(), true));
         if (!p_191985_1_.func_192610_d().isEmpty()) {
            for(CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove : p_191985_1_.func_192610_d()) {
               ItemStack itemstack = this.field_147369_b.field_71070_bA.func_75139_a(cpacketrecipeplacement$itemmove.field_192674_b).func_75211_c();
               if (this.func_193074_a(cpacketrecipeplacement$itemmove.field_192673_a, itemstack)) {
                  int i = cpacketrecipeplacement$itemmove.field_192673_a.func_190916_E();
                  if (cpacketrecipeplacement$itemmove.field_192675_c == -1) {
                     this.field_147369_b.func_71019_a(cpacketrecipeplacement$itemmove.field_192673_a, true);
                  } else {
                     ItemStack itemstack1 = this.field_147369_b.field_71071_by.func_70301_a(cpacketrecipeplacement$itemmove.field_192675_c);
                     if (itemstack1.func_190926_b()) {
                        this.field_147369_b.field_71071_by.func_70299_a(cpacketrecipeplacement$itemmove.field_192675_c, cpacketrecipeplacement$itemmove.field_192673_a);
                     } else {
                        itemstack1.func_190917_f(i);
                     }
                  }

                  if (itemstack.func_190916_E() == i) {
                     this.field_147369_b.field_71070_bA.func_75141_a(cpacketrecipeplacement$itemmove.field_192674_b, ItemStack.field_190927_a);
                  } else {
                     itemstack.func_190918_g(i);
                  }
               }
            }
         }

         if (!p_191985_1_.func_192615_c().isEmpty()) {
            for(CPacketRecipePlacement.ItemMove cpacketrecipeplacement$itemmove1 : p_191985_1_.func_192615_c()) {
               ItemStack itemstack2 = this.field_147369_b.field_71071_by.func_70301_a(cpacketrecipeplacement$itemmove1.field_192675_c);
               if (this.func_193074_a(cpacketrecipeplacement$itemmove1.field_192673_a, itemstack2)) {
                  int j = cpacketrecipeplacement$itemmove1.field_192673_a.func_190916_E();
                  if (itemstack2.func_190916_E() == j) {
                     this.field_147369_b.field_71071_by.func_70304_b(cpacketrecipeplacement$itemmove1.field_192675_c);
                  } else {
                     itemstack2.func_190918_g(j);
                  }

                  this.field_147369_b.field_71070_bA.func_192388_b(cpacketrecipeplacement$itemmove1.field_192674_b, cpacketrecipeplacement$itemmove1.field_192673_a);
               }
            }
         }

         this.field_147369_b.field_71070_bA.func_75142_b();
      }
   }

   private boolean func_193074_a(ItemStack p_193074_1_, ItemStack p_193074_2_) {
      ItemStack itemstack = p_193074_2_.func_77946_l();
      if (itemstack.func_190916_E() < p_193074_1_.func_190916_E()) {
         return false;
      } else {
         itemstack.func_190920_e(p_193074_1_.func_190916_E());
         return ItemStack.func_77989_b(p_193074_1_, itemstack);
      }
   }

   public void func_147338_a(CPacketEnchantItem p_147338_1_) {
      PacketThreadUtil.func_180031_a(p_147338_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      if (this.field_147369_b.field_71070_bA.field_75152_c == p_147338_1_.func_149539_c() && this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b) && !this.field_147369_b.func_175149_v()) {
         this.field_147369_b.field_71070_bA.func_75140_a(this.field_147369_b, p_147338_1_.func_149537_d());
         this.field_147369_b.field_71070_bA.func_75142_b();
      }

   }

   public void func_147344_a(CPacketCreativeInventoryAction p_147344_1_) {
      PacketThreadUtil.func_180031_a(p_147344_1_, this, this.field_147369_b.func_71121_q());
      if (this.field_147369_b.field_71134_c.func_73083_d()) {
         boolean flag = p_147344_1_.func_149627_c() < 0;
         ItemStack itemstack = p_147344_1_.func_149625_d();
         if (!itemstack.func_190926_b() && itemstack.func_77942_o() && itemstack.func_77978_p().func_150297_b("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound = itemstack.func_77978_p().func_74775_l("BlockEntityTag");
            if (nbttagcompound.func_74764_b("x") && nbttagcompound.func_74764_b("y") && nbttagcompound.func_74764_b("z")) {
               BlockPos blockpos = new BlockPos(nbttagcompound.func_74762_e("x"), nbttagcompound.func_74762_e("y"), nbttagcompound.func_74762_e("z"));
               TileEntity tileentity = this.field_147369_b.field_70170_p.func_175625_s(blockpos);
               if (tileentity != null) {
                  NBTTagCompound nbttagcompound1 = tileentity.func_189515_b(new NBTTagCompound());
                  nbttagcompound1.func_82580_o("x");
                  nbttagcompound1.func_82580_o("y");
                  nbttagcompound1.func_82580_o("z");
                  itemstack.func_77983_a("BlockEntityTag", nbttagcompound1);
               }
            }
         }

         boolean flag1 = p_147344_1_.func_149627_c() >= 1 && p_147344_1_.func_149627_c() <= 45;
         boolean flag2 = itemstack.func_190926_b() || itemstack.func_77960_j() >= 0 && itemstack.func_190916_E() <= 64 && !itemstack.func_190926_b();
         if (flag1 && flag2) {
            if (itemstack.func_190926_b()) {
               this.field_147369_b.field_71069_bz.func_75141_a(p_147344_1_.func_149627_c(), ItemStack.field_190927_a);
            } else {
               this.field_147369_b.field_71069_bz.func_75141_a(p_147344_1_.func_149627_c(), itemstack);
            }

            this.field_147369_b.field_71069_bz.func_75128_a(this.field_147369_b, true);
         } else if (flag && flag2 && this.field_147375_m < 200) {
            this.field_147375_m += 20;
            EntityItem entityitem = this.field_147369_b.func_71019_a(itemstack, true);
            if (entityitem != null) {
               entityitem.func_70288_d();
            }
         }
      }

   }

   public void func_147339_a(CPacketConfirmTransaction p_147339_1_) {
      PacketThreadUtil.func_180031_a(p_147339_1_, this, this.field_147369_b.func_71121_q());
      Short oshort = this.field_147372_n.func_76041_a(this.field_147369_b.field_71070_bA.field_75152_c);
      if (oshort != null && p_147339_1_.func_149533_d() == oshort.shortValue() && this.field_147369_b.field_71070_bA.field_75152_c == p_147339_1_.func_149532_c() && !this.field_147369_b.field_71070_bA.func_75129_b(this.field_147369_b) && !this.field_147369_b.func_175149_v()) {
         this.field_147369_b.field_71070_bA.func_75128_a(this.field_147369_b, true);
      }

   }

   public void func_147343_a(CPacketUpdateSign p_147343_1_) {
      PacketThreadUtil.func_180031_a(p_147343_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_143004_u();
      WorldServer worldserver = this.field_147367_d.func_71218_a(this.field_147369_b.field_71093_bK);
      BlockPos blockpos = p_147343_1_.func_179722_a();
      if (worldserver.func_175667_e(blockpos)) {
         IBlockState iblockstate = worldserver.func_180495_p(blockpos);
         TileEntity tileentity = worldserver.func_175625_s(blockpos);
         if (!(tileentity instanceof TileEntitySign)) {
            return;
         }

         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
         if (!tileentitysign.func_145914_a() || tileentitysign.func_145911_b() != this.field_147369_b) {
            this.field_147367_d.func_71236_h("Player " + this.field_147369_b.func_70005_c_() + " just tried to change non-editable sign");
            return;
         }

         String[] astring = p_147343_1_.func_187017_b();

         for(int i = 0; i < astring.length; ++i) {
            tileentitysign.field_145915_a[i] = new TextComponentString(TextFormatting.func_110646_a(astring[i]));
         }

         tileentitysign.func_70296_d();
         worldserver.func_184138_a(blockpos, iblockstate, iblockstate, 3);
      }

   }

   public void func_147353_a(CPacketKeepAlive p_147353_1_) {
      if (p_147353_1_.func_149460_c() == this.field_147378_h) {
         int i = (int)(this.func_147363_d() - this.field_147379_i);
         this.field_147369_b.field_71138_i = (this.field_147369_b.field_71138_i * 3 + i) / 4;
      }

   }

   private long func_147363_d() {
      return System.nanoTime() / 1000000L;
   }

   public void func_147348_a(CPacketPlayerAbilities p_147348_1_) {
      PacketThreadUtil.func_180031_a(p_147348_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.field_71075_bZ.field_75100_b = p_147348_1_.func_149488_d() && this.field_147369_b.field_71075_bZ.field_75101_c;
   }

   public void func_147341_a(CPacketTabComplete p_147341_1_) {
      PacketThreadUtil.func_180031_a(p_147341_1_, this, this.field_147369_b.func_71121_q());
      List<String> list = Lists.<String>newArrayList();

      for(String s : this.field_147367_d.func_184104_a(this.field_147369_b, p_147341_1_.func_149419_c(), p_147341_1_.func_179709_b(), p_147341_1_.func_186989_c())) {
         list.add(s);
      }

      this.field_147369_b.field_71135_a.func_147359_a(new SPacketTabComplete((String[])list.toArray(new String[list.size()])));
   }

   public void func_147352_a(CPacketClientSettings p_147352_1_) {
      PacketThreadUtil.func_180031_a(p_147352_1_, this, this.field_147369_b.func_71121_q());
      this.field_147369_b.func_147100_a(p_147352_1_);
   }

   public void func_147349_a(CPacketCustomPayload p_147349_1_) {
      PacketThreadUtil.func_180031_a(p_147349_1_, this, this.field_147369_b.func_71121_q());
      String s = p_147349_1_.func_149559_c();
      if ("MC|BEdit".equals(s)) {
         PacketBuffer packetbuffer = p_147349_1_.func_180760_b();

         try {
            ItemStack itemstack = packetbuffer.func_150791_c();
            if (itemstack.func_190926_b()) {
               return;
            }

            if (!ItemWritableBook.func_150930_a(itemstack.func_77978_p())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack itemstack1 = this.field_147369_b.func_184614_ca();
            if (itemstack1.func_190926_b()) {
               return;
            }

            if (itemstack.func_77973_b() == Items.field_151099_bA && itemstack.func_77973_b() == itemstack1.func_77973_b()) {
               itemstack1.func_77983_a("pages", itemstack.func_77978_p().func_150295_c("pages", 8));
            }
         } catch (Exception exception6) {
            field_147370_c.error("Couldn't handle book info", (Throwable)exception6);
         }
      } else if ("MC|BSign".equals(s)) {
         PacketBuffer packetbuffer1 = p_147349_1_.func_180760_b();

         try {
            ItemStack itemstack3 = packetbuffer1.func_150791_c();
            if (itemstack3.func_190926_b()) {
               return;
            }

            if (!ItemWrittenBook.func_77828_a(itemstack3.func_77978_p())) {
               throw new IOException("Invalid book tag!");
            }

            ItemStack itemstack4 = this.field_147369_b.func_184614_ca();
            if (itemstack4.func_190926_b()) {
               return;
            }

            if (itemstack3.func_77973_b() == Items.field_151099_bA && itemstack4.func_77973_b() == Items.field_151099_bA) {
               ItemStack itemstack2 = new ItemStack(Items.field_151164_bB);
               itemstack2.func_77983_a("author", new NBTTagString(this.field_147369_b.func_70005_c_()));
               itemstack2.func_77983_a("title", new NBTTagString(itemstack3.func_77978_p().func_74779_i("title")));
               NBTTagList nbttaglist = itemstack3.func_77978_p().func_150295_c("pages", 8);

               for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                  String s1 = nbttaglist.func_150307_f(i);
                  ITextComponent itextcomponent = new TextComponentString(s1);
                  s1 = ITextComponent.Serializer.func_150696_a(itextcomponent);
                  nbttaglist.func_150304_a(i, new NBTTagString(s1));
               }

               itemstack2.func_77983_a("pages", nbttaglist);
               this.field_147369_b.func_184201_a(EntityEquipmentSlot.MAINHAND, itemstack2);
            }
         } catch (Exception exception7) {
            field_147370_c.error("Couldn't sign book", (Throwable)exception7);
         }
      } else if ("MC|TrSel".equals(s)) {
         try {
            int k = p_147349_1_.func_180760_b().readInt();
            Container container = this.field_147369_b.field_71070_bA;
            if (container instanceof ContainerMerchant) {
               ((ContainerMerchant)container).func_75175_c(k);
            }
         } catch (Exception exception5) {
            field_147370_c.error("Couldn't select trade", (Throwable)exception5);
         }
      } else if ("MC|AdvCmd".equals(s)) {
         if (!this.field_147367_d.func_82356_Z()) {
            this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notEnabled", new Object[0]));
            return;
         }

         if (!this.field_147369_b.func_189808_dh()) {
            this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notAllowed", new Object[0]));
            return;
         }

         PacketBuffer packetbuffer2 = p_147349_1_.func_180760_b();

         try {
            int l = packetbuffer2.readByte();
            CommandBlockBaseLogic commandblockbaselogic1 = null;
            if (l == 0) {
               TileEntity tileentity = this.field_147369_b.field_70170_p.func_175625_s(new BlockPos(packetbuffer2.readInt(), packetbuffer2.readInt(), packetbuffer2.readInt()));
               if (tileentity instanceof TileEntityCommandBlock) {
                  commandblockbaselogic1 = ((TileEntityCommandBlock)tileentity).func_145993_a();
               }
            } else if (l == 1) {
               Entity entity = this.field_147369_b.field_70170_p.func_73045_a(packetbuffer2.readInt());
               if (entity instanceof EntityMinecartCommandBlock) {
                  commandblockbaselogic1 = ((EntityMinecartCommandBlock)entity).func_145822_e();
               }
            }

            String s6 = packetbuffer2.func_150789_c(packetbuffer2.readableBytes());
            boolean flag2 = packetbuffer2.readBoolean();
            if (commandblockbaselogic1 != null) {
               commandblockbaselogic1.func_145752_a(s6);
               commandblockbaselogic1.func_175573_a(flag2);
               if (!flag2) {
                  commandblockbaselogic1.func_145750_b((ITextComponent)null);
               }

               commandblockbaselogic1.func_145756_e();
               this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.setCommand.success", new Object[]{s6}));
            }
         } catch (Exception exception4) {
            field_147370_c.error("Couldn't set command block", (Throwable)exception4);
         }
      } else if ("MC|AutoCmd".equals(s)) {
         if (!this.field_147367_d.func_82356_Z()) {
            this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notEnabled", new Object[0]));
            return;
         }

         if (!this.field_147369_b.func_189808_dh()) {
            this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.notAllowed", new Object[0]));
            return;
         }

         PacketBuffer packetbuffer3 = p_147349_1_.func_180760_b();

         try {
            CommandBlockBaseLogic commandblockbaselogic = null;
            TileEntityCommandBlock tileentitycommandblock = null;
            BlockPos blockpos1 = new BlockPos(packetbuffer3.readInt(), packetbuffer3.readInt(), packetbuffer3.readInt());
            TileEntity tileentity2 = this.field_147369_b.field_70170_p.func_175625_s(blockpos1);
            if (tileentity2 instanceof TileEntityCommandBlock) {
               tileentitycommandblock = (TileEntityCommandBlock)tileentity2;
               commandblockbaselogic = tileentitycommandblock.func_145993_a();
            }

            String s7 = packetbuffer3.func_150789_c(packetbuffer3.readableBytes());
            boolean flag3 = packetbuffer3.readBoolean();
            TileEntityCommandBlock.Mode tileentitycommandblock$mode = TileEntityCommandBlock.Mode.valueOf(packetbuffer3.func_150789_c(16));
            boolean flag = packetbuffer3.readBoolean();
            boolean flag1 = packetbuffer3.readBoolean();
            if (commandblockbaselogic != null) {
               EnumFacing enumfacing = (EnumFacing)this.field_147369_b.field_70170_p.func_180495_p(blockpos1).func_177229_b(BlockCommandBlock.field_185564_a);
               switch(tileentitycommandblock$mode) {
               case SEQUENCE:
                  IBlockState iblockstate3 = Blocks.field_185777_dd.func_176223_P();
                  this.field_147369_b.field_70170_p.func_180501_a(blockpos1, iblockstate3.func_177226_a(BlockCommandBlock.field_185564_a, enumfacing).func_177226_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(flag)), 2);
                  break;
               case AUTO:
                  IBlockState iblockstate2 = Blocks.field_185776_dc.func_176223_P();
                  this.field_147369_b.field_70170_p.func_180501_a(blockpos1, iblockstate2.func_177226_a(BlockCommandBlock.field_185564_a, enumfacing).func_177226_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(flag)), 2);
                  break;
               case REDSTONE:
                  IBlockState iblockstate = Blocks.field_150483_bI.func_176223_P();
                  this.field_147369_b.field_70170_p.func_180501_a(blockpos1, iblockstate.func_177226_a(BlockCommandBlock.field_185564_a, enumfacing).func_177226_a(BlockCommandBlock.field_185565_b, Boolean.valueOf(flag)), 2);
               }

               tileentity2.func_145829_t();
               this.field_147369_b.field_70170_p.func_175690_a(blockpos1, tileentity2);
               commandblockbaselogic.func_145752_a(s7);
               commandblockbaselogic.func_175573_a(flag3);
               if (!flag3) {
                  commandblockbaselogic.func_145750_b((ITextComponent)null);
               }

               tileentitycommandblock.func_184253_b(flag1);
               commandblockbaselogic.func_145756_e();
               if (!net.minecraft.util.StringUtils.func_151246_b(s7)) {
                  this.field_147369_b.func_145747_a(new TextComponentTranslation("advMode.setCommand.success", new Object[]{s7}));
               }
            }
         } catch (Exception exception3) {
            field_147370_c.error("Couldn't set command block", (Throwable)exception3);
         }
      } else if ("MC|Beacon".equals(s)) {
         if (this.field_147369_b.field_71070_bA instanceof ContainerBeacon) {
            try {
               PacketBuffer packetbuffer4 = p_147349_1_.func_180760_b();
               int i1 = packetbuffer4.readInt();
               int k1 = packetbuffer4.readInt();
               ContainerBeacon containerbeacon = (ContainerBeacon)this.field_147369_b.field_71070_bA;
               Slot slot = containerbeacon.func_75139_a(0);
               if (slot.func_75216_d()) {
                  slot.func_75209_a(1);
                  IInventory iinventory = containerbeacon.func_180611_e();
                  iinventory.func_174885_b(1, i1);
                  iinventory.func_174885_b(2, k1);
                  iinventory.func_70296_d();
               }
            } catch (Exception exception2) {
               field_147370_c.error("Couldn't set beacon", (Throwable)exception2);
            }
         }
      } else if ("MC|ItemName".equals(s)) {
         if (this.field_147369_b.field_71070_bA instanceof ContainerRepair) {
            ContainerRepair containerrepair = (ContainerRepair)this.field_147369_b.field_71070_bA;
            if (p_147349_1_.func_180760_b() != null && p_147349_1_.func_180760_b().readableBytes() >= 1) {
               String s5 = ChatAllowedCharacters.func_71565_a(p_147349_1_.func_180760_b().func_150789_c(32767));
               if (s5.length() <= 35) {
                  containerrepair.func_82850_a(s5);
               }
            } else {
               containerrepair.func_82850_a("");
            }
         }
      } else if ("MC|Struct".equals(s)) {
         if (!this.field_147369_b.func_189808_dh()) {
            return;
         }

         PacketBuffer packetbuffer5 = p_147349_1_.func_180760_b();

         try {
            BlockPos blockpos = new BlockPos(packetbuffer5.readInt(), packetbuffer5.readInt(), packetbuffer5.readInt());
            IBlockState iblockstate1 = this.field_147369_b.field_70170_p.func_180495_p(blockpos);
            TileEntity tileentity1 = this.field_147369_b.field_70170_p.func_175625_s(blockpos);
            if (tileentity1 instanceof TileEntityStructure) {
               TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity1;
               int l1 = packetbuffer5.readByte();
               String s8 = packetbuffer5.func_150789_c(32);
               tileentitystructure.func_184405_a(TileEntityStructure.Mode.valueOf(s8));
               tileentitystructure.func_184404_a(packetbuffer5.func_150789_c(64));
               int i2 = MathHelper.func_76125_a(packetbuffer5.readInt(), -32, 32);
               int j2 = MathHelper.func_76125_a(packetbuffer5.readInt(), -32, 32);
               int k2 = MathHelper.func_76125_a(packetbuffer5.readInt(), -32, 32);
               tileentitystructure.func_184414_b(new BlockPos(i2, j2, k2));
               int l2 = MathHelper.func_76125_a(packetbuffer5.readInt(), 0, 32);
               int i3 = MathHelper.func_76125_a(packetbuffer5.readInt(), 0, 32);
               int j = MathHelper.func_76125_a(packetbuffer5.readInt(), 0, 32);
               tileentitystructure.func_184409_c(new BlockPos(l2, i3, j));
               String s2 = packetbuffer5.func_150789_c(32);
               tileentitystructure.func_184411_a(Mirror.valueOf(s2));
               String s3 = packetbuffer5.func_150789_c(32);
               tileentitystructure.func_184408_a(Rotation.valueOf(s3));
               tileentitystructure.func_184410_b(packetbuffer5.func_150789_c(128));
               tileentitystructure.func_184406_a(packetbuffer5.readBoolean());
               tileentitystructure.func_189703_e(packetbuffer5.readBoolean());
               tileentitystructure.func_189710_f(packetbuffer5.readBoolean());
               tileentitystructure.func_189718_a(MathHelper.func_76131_a(packetbuffer5.readFloat(), 0.0F, 1.0F));
               tileentitystructure.func_189725_a(packetbuffer5.func_179260_f());
               String s4 = tileentitystructure.func_189715_d();
               if (l1 == 2) {
                  if (tileentitystructure.func_184419_m()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.save_success", new Object[]{s4}), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.save_failure", new Object[]{s4}), false);
                  }
               } else if (l1 == 3) {
                  if (!tileentitystructure.func_189709_F()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_not_found", new Object[]{s4}), false);
                  } else if (tileentitystructure.func_184412_n()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_success", new Object[]{s4}), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.load_prepare", new Object[]{s4}), false);
                  }
               } else if (l1 == 4) {
                  if (tileentitystructure.func_184417_l()) {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.size_success", new Object[]{s4}), false);
                  } else {
                     this.field_147369_b.func_146105_b(new TextComponentTranslation("structure_block.size_failure", new Object[0]), false);
                  }
               }

               tileentitystructure.func_70296_d();
               this.field_147369_b.field_70170_p.func_184138_a(blockpos, iblockstate1, iblockstate1, 3);
            }
         } catch (Exception exception1) {
            field_147370_c.error("Couldn't set structure block", (Throwable)exception1);
         }
      } else if ("MC|PickItem".equals(s)) {
         PacketBuffer packetbuffer6 = p_147349_1_.func_180760_b();

         try {
            int j1 = packetbuffer6.func_150792_a();
            this.field_147369_b.field_71071_by.func_184430_d(j1);
            this.field_147369_b.field_71135_a.func_147359_a(new SPacketSetSlot(-2, this.field_147369_b.field_71071_by.field_70461_c, this.field_147369_b.field_71071_by.func_70301_a(this.field_147369_b.field_71071_by.field_70461_c)));
            this.field_147369_b.field_71135_a.func_147359_a(new SPacketSetSlot(-2, j1, this.field_147369_b.field_71071_by.func_70301_a(j1)));
            this.field_147369_b.field_71135_a.func_147359_a(new SPacketHeldItemChange(this.field_147369_b.field_71071_by.field_70461_c));
         } catch (Exception exception) {
            field_147370_c.error("Couldn't pick item", (Throwable)exception);
         }
      }

   }
}
