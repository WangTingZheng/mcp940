package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
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
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RenderManager {
   private final Map<Class<? extends Entity>, Render<? extends Entity>> field_78729_o = Maps.<Class<? extends Entity>, Render<? extends Entity>>newHashMap();
   private final Map<String, RenderPlayer> field_178636_l = Maps.<String, RenderPlayer>newHashMap();
   private final RenderPlayer field_178637_m;
   private FontRenderer field_78736_p;
   private double field_78725_b;
   private double field_78726_c;
   private double field_78723_d;
   public TextureManager field_78724_e;
   public World field_78722_g;
   public Entity field_78734_h;
   public Entity field_147941_i;
   public float field_78735_i;
   public float field_78732_j;
   public GameSettings field_78733_k;
   public double field_78730_l;
   public double field_78731_m;
   public double field_78728_n;
   private boolean field_178639_r;
   private boolean field_178638_s = true;
   private boolean field_85095_o;

   public RenderManager(TextureManager p_i46180_1_, RenderItem p_i46180_2_) {
      this.field_78724_e = p_i46180_1_;
      this.field_78729_o.put(EntityCaveSpider.class, new RenderCaveSpider(this));
      this.field_78729_o.put(EntitySpider.class, new RenderSpider(this));
      this.field_78729_o.put(EntityPig.class, new RenderPig(this));
      this.field_78729_o.put(EntitySheep.class, new RenderSheep(this));
      this.field_78729_o.put(EntityCow.class, new RenderCow(this));
      this.field_78729_o.put(EntityMooshroom.class, new RenderMooshroom(this));
      this.field_78729_o.put(EntityWolf.class, new RenderWolf(this));
      this.field_78729_o.put(EntityChicken.class, new RenderChicken(this));
      this.field_78729_o.put(EntityOcelot.class, new RenderOcelot(this));
      this.field_78729_o.put(EntityRabbit.class, new RenderRabbit(this));
      this.field_78729_o.put(EntityParrot.class, new RenderParrot(this));
      this.field_78729_o.put(EntitySilverfish.class, new RenderSilverfish(this));
      this.field_78729_o.put(EntityEndermite.class, new RenderEndermite(this));
      this.field_78729_o.put(EntityCreeper.class, new RenderCreeper(this));
      this.field_78729_o.put(EntityEnderman.class, new RenderEnderman(this));
      this.field_78729_o.put(EntitySnowman.class, new RenderSnowMan(this));
      this.field_78729_o.put(EntitySkeleton.class, new RenderSkeleton(this));
      this.field_78729_o.put(EntityWitherSkeleton.class, new RenderWitherSkeleton(this));
      this.field_78729_o.put(EntityStray.class, new RenderStray(this));
      this.field_78729_o.put(EntityWitch.class, new RenderWitch(this));
      this.field_78729_o.put(EntityBlaze.class, new RenderBlaze(this));
      this.field_78729_o.put(EntityPigZombie.class, new RenderPigZombie(this));
      this.field_78729_o.put(EntityZombie.class, new RenderZombie(this));
      this.field_78729_o.put(EntityZombieVillager.class, new RenderZombieVillager(this));
      this.field_78729_o.put(EntityHusk.class, new RenderHusk(this));
      this.field_78729_o.put(EntitySlime.class, new RenderSlime(this));
      this.field_78729_o.put(EntityMagmaCube.class, new RenderMagmaCube(this));
      this.field_78729_o.put(EntityGiantZombie.class, new RenderGiantZombie(this, 6.0F));
      this.field_78729_o.put(EntityGhast.class, new RenderGhast(this));
      this.field_78729_o.put(EntitySquid.class, new RenderSquid(this));
      this.field_78729_o.put(EntityVillager.class, new RenderVillager(this));
      this.field_78729_o.put(EntityIronGolem.class, new RenderIronGolem(this));
      this.field_78729_o.put(EntityBat.class, new RenderBat(this));
      this.field_78729_o.put(EntityGuardian.class, new RenderGuardian(this));
      this.field_78729_o.put(EntityElderGuardian.class, new RenderElderGuardian(this));
      this.field_78729_o.put(EntityShulker.class, new RenderShulker(this));
      this.field_78729_o.put(EntityPolarBear.class, new RenderPolarBear(this));
      this.field_78729_o.put(EntityEvoker.class, new RenderEvoker(this));
      this.field_78729_o.put(EntityVindicator.class, new RenderVindicator(this));
      this.field_78729_o.put(EntityVex.class, new RenderVex(this));
      this.field_78729_o.put(EntityIllusionIllager.class, new RenderIllusionIllager(this));
      this.field_78729_o.put(EntityDragon.class, new RenderDragon(this));
      this.field_78729_o.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
      this.field_78729_o.put(EntityWither.class, new RenderWither(this));
      this.field_78729_o.put(Entity.class, new RenderEntity(this));
      this.field_78729_o.put(EntityPainting.class, new RenderPainting(this));
      this.field_78729_o.put(EntityItemFrame.class, new RenderItemFrame(this, p_i46180_2_));
      this.field_78729_o.put(EntityLeashKnot.class, new RenderLeashKnot(this));
      this.field_78729_o.put(EntityTippedArrow.class, new RenderTippedArrow(this));
      this.field_78729_o.put(EntitySpectralArrow.class, new RenderSpectralArrow(this));
      this.field_78729_o.put(EntitySnowball.class, new RenderSnowball(this, Items.field_151126_ay, p_i46180_2_));
      this.field_78729_o.put(EntityEnderPearl.class, new RenderSnowball(this, Items.field_151079_bi, p_i46180_2_));
      this.field_78729_o.put(EntityEnderEye.class, new RenderSnowball(this, Items.field_151061_bv, p_i46180_2_));
      this.field_78729_o.put(EntityEgg.class, new RenderSnowball(this, Items.field_151110_aK, p_i46180_2_));
      this.field_78729_o.put(EntityPotion.class, new RenderPotion(this, p_i46180_2_));
      this.field_78729_o.put(EntityExpBottle.class, new RenderSnowball(this, Items.field_151062_by, p_i46180_2_));
      this.field_78729_o.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.field_151152_bP, p_i46180_2_));
      this.field_78729_o.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
      this.field_78729_o.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
      this.field_78729_o.put(EntityDragonFireball.class, new RenderDragonFireball(this));
      this.field_78729_o.put(EntityWitherSkull.class, new RenderWitherSkull(this));
      this.field_78729_o.put(EntityShulkerBullet.class, new RenderShulkerBullet(this));
      this.field_78729_o.put(EntityItem.class, new RenderEntityItem(this, p_i46180_2_));
      this.field_78729_o.put(EntityXPOrb.class, new RenderXPOrb(this));
      this.field_78729_o.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
      this.field_78729_o.put(EntityFallingBlock.class, new RenderFallingBlock(this));
      this.field_78729_o.put(EntityArmorStand.class, new RenderArmorStand(this));
      this.field_78729_o.put(EntityEvokerFangs.class, new RenderEvokerFangs(this));
      this.field_78729_o.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
      this.field_78729_o.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
      this.field_78729_o.put(EntityMinecart.class, new RenderMinecart(this));
      this.field_78729_o.put(EntityBoat.class, new RenderBoat(this));
      this.field_78729_o.put(EntityFishHook.class, new RenderFish(this));
      this.field_78729_o.put(EntityAreaEffectCloud.class, new RenderAreaEffectCloud(this));
      this.field_78729_o.put(EntityHorse.class, new RenderHorse(this));
      this.field_78729_o.put(EntitySkeletonHorse.class, new RenderAbstractHorse(this));
      this.field_78729_o.put(EntityZombieHorse.class, new RenderAbstractHorse(this));
      this.field_78729_o.put(EntityMule.class, new RenderAbstractHorse(this, 0.92F));
      this.field_78729_o.put(EntityDonkey.class, new RenderAbstractHorse(this, 0.87F));
      this.field_78729_o.put(EntityLlama.class, new RenderLlama(this));
      this.field_78729_o.put(EntityLlamaSpit.class, new RenderLlamaSpit(this));
      this.field_78729_o.put(EntityLightningBolt.class, new RenderLightningBolt(this));
      this.field_178637_m = new RenderPlayer(this);
      this.field_178636_l.put("default", this.field_178637_m);
      this.field_178636_l.put("slim", new RenderPlayer(this, true));
   }

   public void func_178628_a(double p_178628_1_, double p_178628_3_, double p_178628_5_) {
      this.field_78725_b = p_178628_1_;
      this.field_78726_c = p_178628_3_;
      this.field_78723_d = p_178628_5_;
   }

   public <T extends Entity> Render<T> func_78715_a(Class<? extends Entity> p_78715_1_) {
      Render<? extends Entity> render = (Render)this.field_78729_o.get(p_78715_1_);
      if (render == null && p_78715_1_ != Entity.class) {
         render = this.<Entity>func_78715_a(p_78715_1_.getSuperclass());
         this.field_78729_o.put(p_78715_1_, render);
      }

      return render;
   }

   @Nullable
   public <T extends Entity> Render<T> func_78713_a(Entity p_78713_1_) {
      if (p_78713_1_ instanceof AbstractClientPlayer) {
         String s = ((AbstractClientPlayer)p_78713_1_).func_175154_l();
         RenderPlayer renderplayer = this.field_178636_l.get(s);
         return renderplayer != null ? renderplayer : this.field_178637_m;
      } else {
         return this.<T>func_78715_a(p_78713_1_.getClass());
      }
   }

   public void func_180597_a(World p_180597_1_, FontRenderer p_180597_2_, Entity p_180597_3_, Entity p_180597_4_, GameSettings p_180597_5_, float p_180597_6_) {
      this.field_78722_g = p_180597_1_;
      this.field_78733_k = p_180597_5_;
      this.field_78734_h = p_180597_3_;
      this.field_147941_i = p_180597_4_;
      this.field_78736_p = p_180597_2_;
      if (p_180597_3_ instanceof EntityLivingBase && ((EntityLivingBase)p_180597_3_).func_70608_bn()) {
         IBlockState iblockstate = p_180597_1_.func_180495_p(new BlockPos(p_180597_3_));
         Block block = iblockstate.func_177230_c();
         if (block == Blocks.field_150324_C) {
            int i = ((EnumFacing)iblockstate.func_177229_b(BlockBed.field_185512_D)).func_176736_b();
            this.field_78735_i = (float)(i * 90 + 180);
            this.field_78732_j = 0.0F;
         }
      } else {
         this.field_78735_i = p_180597_3_.field_70126_B + (p_180597_3_.field_70177_z - p_180597_3_.field_70126_B) * p_180597_6_;
         this.field_78732_j = p_180597_3_.field_70127_C + (p_180597_3_.field_70125_A - p_180597_3_.field_70127_C) * p_180597_6_;
      }

      if (p_180597_5_.field_74320_O == 2) {
         this.field_78735_i += 180.0F;
      }

      this.field_78730_l = p_180597_3_.field_70142_S + (p_180597_3_.field_70165_t - p_180597_3_.field_70142_S) * (double)p_180597_6_;
      this.field_78731_m = p_180597_3_.field_70137_T + (p_180597_3_.field_70163_u - p_180597_3_.field_70137_T) * (double)p_180597_6_;
      this.field_78728_n = p_180597_3_.field_70136_U + (p_180597_3_.field_70161_v - p_180597_3_.field_70136_U) * (double)p_180597_6_;
   }

   public void func_178631_a(float p_178631_1_) {
      this.field_78735_i = p_178631_1_;
   }

   public boolean func_178627_a() {
      return this.field_178638_s;
   }

   public void func_178633_a(boolean p_178633_1_) {
      this.field_178638_s = p_178633_1_;
   }

   public void func_178629_b(boolean p_178629_1_) {
      this.field_85095_o = p_178629_1_;
   }

   public boolean func_178634_b() {
      return this.field_85095_o;
   }

   public boolean func_188390_b(Entity p_188390_1_) {
      return this.func_78713_a(p_188390_1_).func_188295_H_();
   }

   public boolean func_178635_a(Entity p_178635_1_, ICamera p_178635_2_, double p_178635_3_, double p_178635_5_, double p_178635_7_) {
      Render<Entity> render = this.<Entity>func_78713_a(p_178635_1_);
      return render != null && render.func_177071_a(p_178635_1_, p_178635_2_, p_178635_3_, p_178635_5_, p_178635_7_);
   }

   public void func_188388_a(Entity p_188388_1_, float p_188388_2_, boolean p_188388_3_) {
      if (p_188388_1_.field_70173_aa == 0) {
         p_188388_1_.field_70142_S = p_188388_1_.field_70165_t;
         p_188388_1_.field_70137_T = p_188388_1_.field_70163_u;
         p_188388_1_.field_70136_U = p_188388_1_.field_70161_v;
      }

      double d0 = p_188388_1_.field_70142_S + (p_188388_1_.field_70165_t - p_188388_1_.field_70142_S) * (double)p_188388_2_;
      double d1 = p_188388_1_.field_70137_T + (p_188388_1_.field_70163_u - p_188388_1_.field_70137_T) * (double)p_188388_2_;
      double d2 = p_188388_1_.field_70136_U + (p_188388_1_.field_70161_v - p_188388_1_.field_70136_U) * (double)p_188388_2_;
      float f = p_188388_1_.field_70126_B + (p_188388_1_.field_70177_z - p_188388_1_.field_70126_B) * p_188388_2_;
      int i = p_188388_1_.func_70070_b();
      if (p_188388_1_.func_70027_ad()) {
         i = 15728880;
      }

      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_188391_a(p_188388_1_, d0 - this.field_78725_b, d1 - this.field_78726_c, d2 - this.field_78723_d, f, p_188388_2_, p_188388_3_);
   }

   public void func_188391_a(Entity p_188391_1_, double p_188391_2_, double p_188391_4_, double p_188391_6_, float p_188391_8_, float p_188391_9_, boolean p_188391_10_) {
      Render<Entity> render = null;

      try {
         render = this.<Entity>func_78713_a(p_188391_1_);
         if (render != null && this.field_78724_e != null) {
            try {
               render.func_188297_a(this.field_178639_r);
               render.func_76986_a(p_188391_1_, p_188391_2_, p_188391_4_, p_188391_6_, p_188391_8_, p_188391_9_);
            } catch (Throwable throwable1) {
               throw new ReportedException(CrashReport.func_85055_a(throwable1, "Rendering entity in world"));
            }

            try {
               if (!this.field_178639_r) {
                  render.func_76979_b(p_188391_1_, p_188391_2_, p_188391_4_, p_188391_6_, p_188391_8_, p_188391_9_);
               }
            } catch (Throwable throwable2) {
               throw new ReportedException(CrashReport.func_85055_a(throwable2, "Post-rendering entity in world"));
            }

            if (this.field_85095_o && !p_188391_1_.func_82150_aj() && !p_188391_10_ && !Minecraft.func_71410_x().func_189648_am()) {
               try {
                  this.func_85094_b(p_188391_1_, p_188391_2_, p_188391_4_, p_188391_6_, p_188391_8_, p_188391_9_);
               } catch (Throwable throwable) {
                  throw new ReportedException(CrashReport.func_85055_a(throwable, "Rendering entity hitbox in world"));
               }
            }
         }

      } catch (Throwable throwable3) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable3, "Rendering entity in world");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being rendered");
         p_188391_1_.func_85029_a(crashreportcategory);
         CrashReportCategory crashreportcategory1 = crashreport.func_85058_a("Renderer details");
         crashreportcategory1.func_71507_a("Assigned renderer", render);
         crashreportcategory1.func_71507_a("Location", CrashReportCategory.func_85074_a(p_188391_2_, p_188391_4_, p_188391_6_));
         crashreportcategory1.func_71507_a("Rotation", Float.valueOf(p_188391_8_));
         crashreportcategory1.func_71507_a("Delta", Float.valueOf(p_188391_9_));
         throw new ReportedException(crashreport);
      }
   }

   public void func_188389_a(Entity p_188389_1_, float p_188389_2_) {
      if (p_188389_1_.field_70173_aa == 0) {
         p_188389_1_.field_70142_S = p_188389_1_.field_70165_t;
         p_188389_1_.field_70137_T = p_188389_1_.field_70163_u;
         p_188389_1_.field_70136_U = p_188389_1_.field_70161_v;
      }

      double d0 = p_188389_1_.field_70142_S + (p_188389_1_.field_70165_t - p_188389_1_.field_70142_S) * (double)p_188389_2_;
      double d1 = p_188389_1_.field_70137_T + (p_188389_1_.field_70163_u - p_188389_1_.field_70137_T) * (double)p_188389_2_;
      double d2 = p_188389_1_.field_70136_U + (p_188389_1_.field_70161_v - p_188389_1_.field_70136_U) * (double)p_188389_2_;
      float f = p_188389_1_.field_70126_B + (p_188389_1_.field_70177_z - p_188389_1_.field_70126_B) * p_188389_2_;
      int i = p_188389_1_.func_70070_b();
      if (p_188389_1_.func_70027_ad()) {
         i = 15728880;
      }

      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Render<Entity> render = this.<Entity>func_78713_a(p_188389_1_);
      if (render != null && this.field_78724_e != null) {
         render.func_188300_b(p_188389_1_, d0 - this.field_78725_b, d1 - this.field_78726_c, d2 - this.field_78723_d, f, p_188389_2_);
      }

   }

   private void func_85094_b(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      float f = p_85094_1_.field_70130_N / 2.0F;
      AxisAlignedBB axisalignedbb = p_85094_1_.func_174813_aQ();
      RenderGlobal.func_189694_a(axisalignedbb.field_72340_a - p_85094_1_.field_70165_t + p_85094_2_, axisalignedbb.field_72338_b - p_85094_1_.field_70163_u + p_85094_4_, axisalignedbb.field_72339_c - p_85094_1_.field_70161_v + p_85094_6_, axisalignedbb.field_72336_d - p_85094_1_.field_70165_t + p_85094_2_, axisalignedbb.field_72337_e - p_85094_1_.field_70163_u + p_85094_4_, axisalignedbb.field_72334_f - p_85094_1_.field_70161_v + p_85094_6_, 1.0F, 1.0F, 1.0F, 1.0F);
      Entity[] aentity = p_85094_1_.func_70021_al();
      if (aentity != null) {
         for(Entity entity : aentity) {
            double d0 = (entity.field_70165_t - entity.field_70169_q) * (double)p_85094_9_;
            double d1 = (entity.field_70163_u - entity.field_70167_r) * (double)p_85094_9_;
            double d2 = (entity.field_70161_v - entity.field_70166_s) * (double)p_85094_9_;
            AxisAlignedBB axisalignedbb1 = entity.func_174813_aQ();
            RenderGlobal.func_189694_a(axisalignedbb1.field_72340_a - this.field_78725_b + d0, axisalignedbb1.field_72338_b - this.field_78726_c + d1, axisalignedbb1.field_72339_c - this.field_78723_d + d2, axisalignedbb1.field_72336_d - this.field_78725_b + d0, axisalignedbb1.field_72337_e - this.field_78726_c + d1, axisalignedbb1.field_72334_f - this.field_78723_d + d2, 0.25F, 1.0F, 0.0F, 1.0F);
         }
      }

      if (p_85094_1_ instanceof EntityLivingBase) {
         float f1 = 0.01F;
         RenderGlobal.func_189694_a(p_85094_2_ - (double)f, p_85094_4_ + (double)p_85094_1_.func_70047_e() - 0.009999999776482582D, p_85094_6_ - (double)f, p_85094_2_ + (double)f, p_85094_4_ + (double)p_85094_1_.func_70047_e() + 0.009999999776482582D, p_85094_6_ + (double)f, 1.0F, 0.0F, 0.0F, 1.0F);
      }

      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      Vec3d vec3d = p_85094_1_.func_70676_i(p_85094_9_);
      bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      bufferbuilder.func_181662_b(p_85094_2_, p_85094_4_ + (double)p_85094_1_.func_70047_e(), p_85094_6_).func_181669_b(0, 0, 255, 255).func_181675_d();
      bufferbuilder.func_181662_b(p_85094_2_ + vec3d.field_72450_a * 2.0D, p_85094_4_ + (double)p_85094_1_.func_70047_e() + vec3d.field_72448_b * 2.0D, p_85094_6_ + vec3d.field_72449_c * 2.0D).func_181669_b(0, 0, 255, 255).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179145_e();
      GlStateManager.func_179089_o();
      GlStateManager.func_179084_k();
      GlStateManager.func_179132_a(true);
   }

   public void func_78717_a(@Nullable World p_78717_1_) {
      this.field_78722_g = p_78717_1_;
      if (p_78717_1_ == null) {
         this.field_78734_h = null;
      }

   }

   public double func_78714_a(double p_78714_1_, double p_78714_3_, double p_78714_5_) {
      double d0 = p_78714_1_ - this.field_78730_l;
      double d1 = p_78714_3_ - this.field_78731_m;
      double d2 = p_78714_5_ - this.field_78728_n;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public FontRenderer func_78716_a() {
      return this.field_78736_p;
   }

   public void func_178632_c(boolean p_178632_1_) {
      this.field_178639_r = p_178632_1_;
   }
}
