package net.minecraft.entity;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger field_151262_p = LogManager.getLogger();
   private final Entity field_73132_a;
   private final int field_73130_b;
   private int field_187262_f;
   private final int field_73131_c;
   private long field_73128_d;
   private long field_73129_e;
   private long field_73126_f;
   private int field_73127_g;
   private int field_73139_h;
   private int field_73140_i;
   private double field_73137_j;
   private double field_73138_k;
   private double field_73135_l;
   public int field_73136_m;
   private double field_73147_p;
   private double field_73146_q;
   private double field_73145_r;
   private boolean field_73144_s;
   private final boolean field_73143_t;
   private int field_73142_u;
   private List<Entity> field_187263_w = Collections.<Entity>emptyList();
   private boolean field_73141_v;
   private boolean field_180234_y;
   public boolean field_73133_n;
   private final Set<EntityPlayerMP> field_73134_o = Sets.<EntityPlayerMP>newHashSet();

   public EntityTrackerEntry(Entity p_i46837_1_, int p_i46837_2_, int p_i46837_3_, int p_i46837_4_, boolean p_i46837_5_) {
      this.field_73132_a = p_i46837_1_;
      this.field_73130_b = p_i46837_2_;
      this.field_187262_f = p_i46837_3_;
      this.field_73131_c = p_i46837_4_;
      this.field_73143_t = p_i46837_5_;
      this.field_73128_d = EntityTracker.func_187253_a(p_i46837_1_.field_70165_t);
      this.field_73129_e = EntityTracker.func_187253_a(p_i46837_1_.field_70163_u);
      this.field_73126_f = EntityTracker.func_187253_a(p_i46837_1_.field_70161_v);
      this.field_73127_g = MathHelper.func_76141_d(p_i46837_1_.field_70177_z * 256.0F / 360.0F);
      this.field_73139_h = MathHelper.func_76141_d(p_i46837_1_.field_70125_A * 256.0F / 360.0F);
      this.field_73140_i = MathHelper.func_76141_d(p_i46837_1_.func_70079_am() * 256.0F / 360.0F);
      this.field_180234_y = p_i46837_1_.field_70122_E;
   }

   public boolean equals(Object p_equals_1_) {
      if (p_equals_1_ instanceof EntityTrackerEntry) {
         return ((EntityTrackerEntry)p_equals_1_).field_73132_a.func_145782_y() == this.field_73132_a.func_145782_y();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_73132_a.func_145782_y();
   }

   public void func_73122_a(List<EntityPlayer> p_73122_1_) {
      this.field_73133_n = false;
      if (!this.field_73144_s || this.field_73132_a.func_70092_e(this.field_73147_p, this.field_73146_q, this.field_73145_r) > 16.0D) {
         this.field_73147_p = this.field_73132_a.field_70165_t;
         this.field_73146_q = this.field_73132_a.field_70163_u;
         this.field_73145_r = this.field_73132_a.field_70161_v;
         this.field_73144_s = true;
         this.field_73133_n = true;
         this.func_73125_b(p_73122_1_);
      }

      List<Entity> list = this.field_73132_a.func_184188_bt();
      if (!list.equals(this.field_187263_w)) {
         this.field_187263_w = list;
         this.func_151259_a(new SPacketSetPassengers(this.field_73132_a));
      }

      if (this.field_73132_a instanceof EntityItemFrame && this.field_73136_m % 10 == 0) {
         EntityItemFrame entityitemframe = (EntityItemFrame)this.field_73132_a;
         ItemStack itemstack = entityitemframe.func_82335_i();
         if (itemstack.func_77973_b() instanceof ItemMap) {
            MapData mapdata = Items.field_151098_aY.func_77873_a(itemstack, this.field_73132_a.field_70170_p);

            for(EntityPlayer entityplayer : p_73122_1_) {
               EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
               mapdata.func_76191_a(entityplayermp, itemstack);
               Packet<?> packet = Items.field_151098_aY.func_150911_c(itemstack, this.field_73132_a.field_70170_p, entityplayermp);
               if (packet != null) {
                  entityplayermp.field_71135_a.func_147359_a(packet);
               }
            }
         }

         this.func_111190_b();
      }

      if (this.field_73136_m % this.field_73131_c == 0 || this.field_73132_a.field_70160_al || this.field_73132_a.func_184212_Q().func_187223_a()) {
         if (this.field_73132_a.func_184218_aH()) {
            int j1 = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
            int l1 = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
            boolean flag3 = Math.abs(j1 - this.field_73127_g) >= 1 || Math.abs(l1 - this.field_73139_h) >= 1;
            if (flag3) {
               this.func_151259_a(new SPacketEntity.S16PacketEntityLook(this.field_73132_a.func_145782_y(), (byte)j1, (byte)l1, this.field_73132_a.field_70122_E));
               this.field_73127_g = j1;
               this.field_73139_h = l1;
            }

            this.field_73128_d = EntityTracker.func_187253_a(this.field_73132_a.field_70165_t);
            this.field_73129_e = EntityTracker.func_187253_a(this.field_73132_a.field_70163_u);
            this.field_73126_f = EntityTracker.func_187253_a(this.field_73132_a.field_70161_v);
            this.func_111190_b();
            this.field_73141_v = true;
         } else {
            ++this.field_73142_u;
            long i1 = EntityTracker.func_187253_a(this.field_73132_a.field_70165_t);
            long i2 = EntityTracker.func_187253_a(this.field_73132_a.field_70163_u);
            long j2 = EntityTracker.func_187253_a(this.field_73132_a.field_70161_v);
            int k2 = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
            int i = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
            long j = i1 - this.field_73128_d;
            long k = i2 - this.field_73129_e;
            long l = j2 - this.field_73126_f;
            Packet<?> packet1 = null;
            boolean flag = j * j + k * k + l * l >= 128L || this.field_73136_m % 60 == 0;
            boolean flag1 = Math.abs(k2 - this.field_73127_g) >= 1 || Math.abs(i - this.field_73139_h) >= 1;
            if (this.field_73136_m > 0 || this.field_73132_a instanceof EntityArrow) {
               if (j >= -32768L && j < 32768L && k >= -32768L && k < 32768L && l >= -32768L && l < 32768L && this.field_73142_u <= 400 && !this.field_73141_v && this.field_180234_y == this.field_73132_a.field_70122_E) {
                  if ((!flag || !flag1) && !(this.field_73132_a instanceof EntityArrow)) {
                     if (flag) {
                        packet1 = new SPacketEntity.S15PacketEntityRelMove(this.field_73132_a.func_145782_y(), j, k, l, this.field_73132_a.field_70122_E);
                     } else if (flag1) {
                        packet1 = new SPacketEntity.S16PacketEntityLook(this.field_73132_a.func_145782_y(), (byte)k2, (byte)i, this.field_73132_a.field_70122_E);
                     }
                  } else {
                     packet1 = new SPacketEntity.S17PacketEntityLookMove(this.field_73132_a.func_145782_y(), j, k, l, (byte)k2, (byte)i, this.field_73132_a.field_70122_E);
                  }
               } else {
                  this.field_180234_y = this.field_73132_a.field_70122_E;
                  this.field_73142_u = 0;
                  this.func_187261_c();
                  packet1 = new SPacketEntityTeleport(this.field_73132_a);
               }
            }

            boolean flag2 = this.field_73143_t;
            if (this.field_73132_a instanceof EntityLivingBase && ((EntityLivingBase)this.field_73132_a).func_184613_cA()) {
               flag2 = true;
            }

            if (flag2 && this.field_73136_m > 0) {
               double d0 = this.field_73132_a.field_70159_w - this.field_73137_j;
               double d1 = this.field_73132_a.field_70181_x - this.field_73138_k;
               double d2 = this.field_73132_a.field_70179_y - this.field_73135_l;
               double d3 = 0.02D;
               double d4 = d0 * d0 + d1 * d1 + d2 * d2;
               if (d4 > 4.0E-4D || d4 > 0.0D && this.field_73132_a.field_70159_w == 0.0D && this.field_73132_a.field_70181_x == 0.0D && this.field_73132_a.field_70179_y == 0.0D) {
                  this.field_73137_j = this.field_73132_a.field_70159_w;
                  this.field_73138_k = this.field_73132_a.field_70181_x;
                  this.field_73135_l = this.field_73132_a.field_70179_y;
                  this.func_151259_a(new SPacketEntityVelocity(this.field_73132_a.func_145782_y(), this.field_73137_j, this.field_73138_k, this.field_73135_l));
               }
            }

            if (packet1 != null) {
               this.func_151259_a(packet1);
            }

            this.func_111190_b();
            if (flag) {
               this.field_73128_d = i1;
               this.field_73129_e = i2;
               this.field_73126_f = j2;
            }

            if (flag1) {
               this.field_73127_g = k2;
               this.field_73139_h = i;
            }

            this.field_73141_v = false;
         }

         int k1 = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);
         if (Math.abs(k1 - this.field_73140_i) >= 1) {
            this.func_151259_a(new SPacketEntityHeadLook(this.field_73132_a, (byte)k1));
            this.field_73140_i = k1;
         }

         this.field_73132_a.field_70160_al = false;
      }

      ++this.field_73136_m;
      if (this.field_73132_a.field_70133_I) {
         this.func_151261_b(new SPacketEntityVelocity(this.field_73132_a));
         this.field_73132_a.field_70133_I = false;
      }

   }

   private void func_111190_b() {
      EntityDataManager entitydatamanager = this.field_73132_a.func_184212_Q();
      if (entitydatamanager.func_187223_a()) {
         this.func_151261_b(new SPacketEntityMetadata(this.field_73132_a.func_145782_y(), entitydatamanager, false));
      }

      if (this.field_73132_a instanceof EntityLivingBase) {
         AttributeMap attributemap = (AttributeMap)((EntityLivingBase)this.field_73132_a).func_110140_aT();
         Set<IAttributeInstance> set = attributemap.func_111161_b();
         if (!set.isEmpty()) {
            this.func_151261_b(new SPacketEntityProperties(this.field_73132_a.func_145782_y(), set));
         }

         set.clear();
      }

   }

   public void func_151259_a(Packet<?> p_151259_1_) {
      for(EntityPlayerMP entityplayermp : this.field_73134_o) {
         entityplayermp.field_71135_a.func_147359_a(p_151259_1_);
      }

   }

   public void func_151261_b(Packet<?> p_151261_1_) {
      this.func_151259_a(p_151261_1_);
      if (this.field_73132_a instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.field_73132_a).field_71135_a.func_147359_a(p_151261_1_);
      }

   }

   public void func_73119_a() {
      for(EntityPlayerMP entityplayermp : this.field_73134_o) {
         this.field_73132_a.func_184203_c(entityplayermp);
         entityplayermp.func_152339_d(this.field_73132_a);
      }

   }

   public void func_73118_a(EntityPlayerMP p_73118_1_) {
      if (this.field_73134_o.contains(p_73118_1_)) {
         this.field_73132_a.func_184203_c(p_73118_1_);
         p_73118_1_.func_152339_d(this.field_73132_a);
         this.field_73134_o.remove(p_73118_1_);
      }

   }

   public void func_73117_b(EntityPlayerMP p_73117_1_) {
      if (p_73117_1_ != this.field_73132_a) {
         if (this.func_180233_c(p_73117_1_)) {
            if (!this.field_73134_o.contains(p_73117_1_) && (this.func_73121_d(p_73117_1_) || this.field_73132_a.field_98038_p)) {
               this.field_73134_o.add(p_73117_1_);
               Packet<?> packet = this.func_151260_c();
               p_73117_1_.field_71135_a.func_147359_a(packet);
               if (!this.field_73132_a.func_184212_Q().func_187228_d()) {
                  p_73117_1_.field_71135_a.func_147359_a(new SPacketEntityMetadata(this.field_73132_a.func_145782_y(), this.field_73132_a.func_184212_Q(), true));
               }

               boolean flag = this.field_73143_t;
               if (this.field_73132_a instanceof EntityLivingBase) {
                  AttributeMap attributemap = (AttributeMap)((EntityLivingBase)this.field_73132_a).func_110140_aT();
                  Collection<IAttributeInstance> collection = attributemap.func_111160_c();
                  if (!collection.isEmpty()) {
                     p_73117_1_.field_71135_a.func_147359_a(new SPacketEntityProperties(this.field_73132_a.func_145782_y(), collection));
                  }

                  if (((EntityLivingBase)this.field_73132_a).func_184613_cA()) {
                     flag = true;
                  }
               }

               this.field_73137_j = this.field_73132_a.field_70159_w;
               this.field_73138_k = this.field_73132_a.field_70181_x;
               this.field_73135_l = this.field_73132_a.field_70179_y;
               if (flag && !(packet instanceof SPacketSpawnMob)) {
                  p_73117_1_.field_71135_a.func_147359_a(new SPacketEntityVelocity(this.field_73132_a.func_145782_y(), this.field_73132_a.field_70159_w, this.field_73132_a.field_70181_x, this.field_73132_a.field_70179_y));
               }

               if (this.field_73132_a instanceof EntityLivingBase) {
                  for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
                     ItemStack itemstack = ((EntityLivingBase)this.field_73132_a).func_184582_a(entityequipmentslot);
                     if (!itemstack.func_190926_b()) {
                        p_73117_1_.field_71135_a.func_147359_a(new SPacketEntityEquipment(this.field_73132_a.func_145782_y(), entityequipmentslot, itemstack));
                     }
                  }
               }

               if (this.field_73132_a instanceof EntityPlayer) {
                  EntityPlayer entityplayer = (EntityPlayer)this.field_73132_a;
                  if (entityplayer.func_70608_bn()) {
                     p_73117_1_.field_71135_a.func_147359_a(new SPacketUseBed(entityplayer, new BlockPos(this.field_73132_a)));
                  }
               }

               if (this.field_73132_a instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)this.field_73132_a;

                  for(PotionEffect potioneffect : entitylivingbase.func_70651_bq()) {
                     p_73117_1_.field_71135_a.func_147359_a(new SPacketEntityEffect(this.field_73132_a.func_145782_y(), potioneffect));
                  }
               }

               if (!this.field_73132_a.func_184188_bt().isEmpty()) {
                  p_73117_1_.field_71135_a.func_147359_a(new SPacketSetPassengers(this.field_73132_a));
               }

               if (this.field_73132_a.func_184218_aH()) {
                  p_73117_1_.field_71135_a.func_147359_a(new SPacketSetPassengers(this.field_73132_a.func_184187_bx()));
               }

               this.field_73132_a.func_184178_b(p_73117_1_);
               p_73117_1_.func_184848_d(this.field_73132_a);
            }
         } else if (this.field_73134_o.contains(p_73117_1_)) {
            this.field_73134_o.remove(p_73117_1_);
            this.field_73132_a.func_184203_c(p_73117_1_);
            p_73117_1_.func_152339_d(this.field_73132_a);
         }

      }
   }

   public boolean func_180233_c(EntityPlayerMP p_180233_1_) {
      double d0 = p_180233_1_.field_70165_t - (double)this.field_73128_d / 4096.0D;
      double d1 = p_180233_1_.field_70161_v - (double)this.field_73126_f / 4096.0D;
      int i = Math.min(this.field_73130_b, this.field_187262_f);
      return d0 >= (double)(-i) && d0 <= (double)i && d1 >= (double)(-i) && d1 <= (double)i && this.field_73132_a.func_174827_a(p_180233_1_);
   }

   private boolean func_73121_d(EntityPlayerMP p_73121_1_) {
      return p_73121_1_.func_71121_q().func_184164_w().func_72694_a(p_73121_1_, this.field_73132_a.field_70176_ah, this.field_73132_a.field_70164_aj);
   }

   public void func_73125_b(List<EntityPlayer> p_73125_1_) {
      for(int i = 0; i < p_73125_1_.size(); ++i) {
         this.func_73117_b((EntityPlayerMP)p_73125_1_.get(i));
      }

   }

   private Packet<?> func_151260_c() {
      if (this.field_73132_a.field_70128_L) {
         field_151262_p.warn("Fetching addPacket for removed entity");
      }

      if (this.field_73132_a instanceof EntityPlayerMP) {
         return new SPacketSpawnPlayer((EntityPlayer)this.field_73132_a);
      } else if (this.field_73132_a instanceof IAnimals) {
         this.field_73140_i = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);
         return new SPacketSpawnMob((EntityLivingBase)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityPainting) {
         return new SPacketSpawnPainting((EntityPainting)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityItem) {
         return new SPacketSpawnObject(this.field_73132_a, 2, 1);
      } else if (this.field_73132_a instanceof EntityMinecart) {
         EntityMinecart entityminecart = (EntityMinecart)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 10, entityminecart.func_184264_v().func_184956_a());
      } else if (this.field_73132_a instanceof EntityBoat) {
         return new SPacketSpawnObject(this.field_73132_a, 1);
      } else if (this.field_73132_a instanceof EntityXPOrb) {
         return new SPacketSpawnExperienceOrb((EntityXPOrb)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityFishHook) {
         Entity entity2 = ((EntityFishHook)this.field_73132_a).func_190619_l();
         return new SPacketSpawnObject(this.field_73132_a, 90, entity2 == null ? this.field_73132_a.func_145782_y() : entity2.func_145782_y());
      } else if (this.field_73132_a instanceof EntitySpectralArrow) {
         Entity entity1 = ((EntitySpectralArrow)this.field_73132_a).field_70250_c;
         return new SPacketSpawnObject(this.field_73132_a, 91, 1 + (entity1 == null ? this.field_73132_a.func_145782_y() : entity1.func_145782_y()));
      } else if (this.field_73132_a instanceof EntityTippedArrow) {
         Entity entity = ((EntityArrow)this.field_73132_a).field_70250_c;
         return new SPacketSpawnObject(this.field_73132_a, 60, 1 + (entity == null ? this.field_73132_a.func_145782_y() : entity.func_145782_y()));
      } else if (this.field_73132_a instanceof EntitySnowball) {
         return new SPacketSpawnObject(this.field_73132_a, 61);
      } else if (this.field_73132_a instanceof EntityLlamaSpit) {
         return new SPacketSpawnObject(this.field_73132_a, 68);
      } else if (this.field_73132_a instanceof EntityPotion) {
         return new SPacketSpawnObject(this.field_73132_a, 73);
      } else if (this.field_73132_a instanceof EntityExpBottle) {
         return new SPacketSpawnObject(this.field_73132_a, 75);
      } else if (this.field_73132_a instanceof EntityEnderPearl) {
         return new SPacketSpawnObject(this.field_73132_a, 65);
      } else if (this.field_73132_a instanceof EntityEnderEye) {
         return new SPacketSpawnObject(this.field_73132_a, 72);
      } else if (this.field_73132_a instanceof EntityFireworkRocket) {
         return new SPacketSpawnObject(this.field_73132_a, 76);
      } else if (this.field_73132_a instanceof EntityFireball) {
         EntityFireball entityfireball = (EntityFireball)this.field_73132_a;
         SPacketSpawnObject spacketspawnobject = null;
         int i = 63;
         if (this.field_73132_a instanceof EntitySmallFireball) {
            i = 64;
         } else if (this.field_73132_a instanceof EntityDragonFireball) {
            i = 93;
         } else if (this.field_73132_a instanceof EntityWitherSkull) {
            i = 66;
         }

         if (entityfireball.field_70235_a != null) {
            spacketspawnobject = new SPacketSpawnObject(this.field_73132_a, i, ((EntityFireball)this.field_73132_a).field_70235_a.func_145782_y());
         } else {
            spacketspawnobject = new SPacketSpawnObject(this.field_73132_a, i, 0);
         }

         spacketspawnobject.func_149003_d((int)(entityfireball.field_70232_b * 8000.0D));
         spacketspawnobject.func_149000_e((int)(entityfireball.field_70233_c * 8000.0D));
         spacketspawnobject.func_149007_f((int)(entityfireball.field_70230_d * 8000.0D));
         return spacketspawnobject;
      } else if (this.field_73132_a instanceof EntityShulkerBullet) {
         SPacketSpawnObject spacketspawnobject1 = new SPacketSpawnObject(this.field_73132_a, 67, 0);
         spacketspawnobject1.func_149003_d((int)(this.field_73132_a.field_70159_w * 8000.0D));
         spacketspawnobject1.func_149000_e((int)(this.field_73132_a.field_70181_x * 8000.0D));
         spacketspawnobject1.func_149007_f((int)(this.field_73132_a.field_70179_y * 8000.0D));
         return spacketspawnobject1;
      } else if (this.field_73132_a instanceof EntityEgg) {
         return new SPacketSpawnObject(this.field_73132_a, 62);
      } else if (this.field_73132_a instanceof EntityEvokerFangs) {
         return new SPacketSpawnObject(this.field_73132_a, 79);
      } else if (this.field_73132_a instanceof EntityTNTPrimed) {
         return new SPacketSpawnObject(this.field_73132_a, 50);
      } else if (this.field_73132_a instanceof EntityEnderCrystal) {
         return new SPacketSpawnObject(this.field_73132_a, 51);
      } else if (this.field_73132_a instanceof EntityFallingBlock) {
         EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 70, Block.func_176210_f(entityfallingblock.func_175131_l()));
      } else if (this.field_73132_a instanceof EntityArmorStand) {
         return new SPacketSpawnObject(this.field_73132_a, 78);
      } else if (this.field_73132_a instanceof EntityItemFrame) {
         EntityItemFrame entityitemframe = (EntityItemFrame)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 71, entityitemframe.field_174860_b.func_176736_b(), entityitemframe.func_174857_n());
      } else if (this.field_73132_a instanceof EntityLeashKnot) {
         EntityLeashKnot entityleashknot = (EntityLeashKnot)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 77, 0, entityleashknot.func_174857_n());
      } else if (this.field_73132_a instanceof EntityAreaEffectCloud) {
         return new SPacketSpawnObject(this.field_73132_a, 3);
      } else {
         throw new IllegalArgumentException("Don't know how to add " + this.field_73132_a.getClass() + "!");
      }
   }

   public void func_73123_c(EntityPlayerMP p_73123_1_) {
      if (this.field_73134_o.contains(p_73123_1_)) {
         this.field_73134_o.remove(p_73123_1_);
         this.field_73132_a.func_184203_c(p_73123_1_);
         p_73123_1_.func_152339_d(this.field_73132_a);
      }

   }

   public Entity func_187260_b() {
      return this.field_73132_a;
   }

   public void func_187259_a(int p_187259_1_) {
      this.field_187262_f = p_187259_1_;
   }

   public void func_187261_c() {
      this.field_73144_s = false;
   }
}
