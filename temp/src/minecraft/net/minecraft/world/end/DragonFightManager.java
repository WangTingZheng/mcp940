package net.minecraft.world.end;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager {
   private static final Logger field_186107_a = LogManager.getLogger();
   private static final Predicate<EntityPlayerMP> field_186108_b = Predicates.<EntityPlayerMP>and(EntitySelectors.field_94557_a, EntitySelectors.func_188443_a(0.0D, 128.0D, 0.0D, 192.0D));
   private final BossInfoServer field_186109_c = (BossInfoServer)(new BossInfoServer(new TextComponentTranslation("entity.EnderDragon.name", new Object[0]), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).func_186742_b(true).func_186743_c(true);
   private final WorldServer field_186110_d;
   private final List<Integer> field_186111_e = Lists.<Integer>newArrayList();
   private final BlockPattern field_186112_f;
   private int field_186113_g;
   private int field_186114_h;
   private int field_186115_i;
   private int field_186116_j;
   private boolean field_186117_k;
   private boolean field_186118_l;
   private UUID field_186119_m;
   private boolean field_186120_n = true;
   private BlockPos field_186121_o;
   private DragonSpawnManager field_186122_p;
   private int field_186123_q;
   private List<EntityEnderCrystal> field_186124_r;

   public DragonFightManager(WorldServer p_i46669_1_, NBTTagCompound p_i46669_2_) {
      this.field_186110_d = p_i46669_1_;
      if (p_i46669_2_.func_150297_b("DragonKilled", 99)) {
         if (p_i46669_2_.func_186855_b("DragonUUID")) {
            this.field_186119_m = p_i46669_2_.func_186857_a("DragonUUID");
         }

         this.field_186117_k = p_i46669_2_.func_74767_n("DragonKilled");
         this.field_186118_l = p_i46669_2_.func_74767_n("PreviouslyKilled");
         if (p_i46669_2_.func_74767_n("IsRespawning")) {
            this.field_186122_p = DragonSpawnManager.START;
         }

         if (p_i46669_2_.func_150297_b("ExitPortalLocation", 10)) {
            this.field_186121_o = NBTUtil.func_186861_c(p_i46669_2_.func_74775_l("ExitPortalLocation"));
         }
      } else {
         this.field_186117_k = true;
         this.field_186118_l = true;
      }

      if (p_i46669_2_.func_150297_b("Gateways", 9)) {
         NBTTagList nbttaglist = p_i46669_2_.func_150295_c("Gateways", 3);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            this.field_186111_e.add(Integer.valueOf(nbttaglist.func_186858_c(i)));
         }
      } else {
         this.field_186111_e.addAll(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(20)), DiscreteDomain.integers()));
         Collections.shuffle(this.field_186111_e, new Random(p_i46669_1_.func_72905_C()));
      }

      this.field_186112_f = FactoryBlockPattern.func_177660_a().func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").func_177659_a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").func_177659_a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").func_177659_a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").func_177662_a('#', BlockWorldState.func_177510_a(BlockMatcher.func_177642_a(Blocks.field_150357_h))).func_177661_b();
   }

   public NBTTagCompound func_186088_a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (this.field_186119_m != null) {
         nbttagcompound.func_186854_a("DragonUUID", this.field_186119_m);
      }

      nbttagcompound.func_74757_a("DragonKilled", this.field_186117_k);
      nbttagcompound.func_74757_a("PreviouslyKilled", this.field_186118_l);
      if (this.field_186121_o != null) {
         nbttagcompound.func_74782_a("ExitPortalLocation", NBTUtil.func_186859_a(this.field_186121_o));
      }

      NBTTagList nbttaglist = new NBTTagList();
      Iterator iterator = this.field_186111_e.iterator();

      while(iterator.hasNext()) {
         int i = ((Integer)iterator.next()).intValue();
         nbttaglist.func_74742_a(new NBTTagInt(i));
      }

      nbttagcompound.func_74782_a("Gateways", nbttaglist);
      return nbttagcompound;
   }

   public void func_186105_b() {
      this.field_186109_c.func_186758_d(!this.field_186117_k);
      if (++this.field_186116_j >= 20) {
         this.func_186100_j();
         this.field_186116_j = 0;
      }

      if (!this.field_186109_c.func_186757_c().isEmpty()) {
         if (this.field_186120_n) {
            field_186107_a.info("Scanning for legacy world dragon fight...");
            this.func_186103_i();
            this.field_186120_n = false;
            boolean flag = this.func_186104_g();
            if (flag) {
               field_186107_a.info("Found that the dragon has been killed in this world already.");
               this.field_186118_l = true;
            } else {
               field_186107_a.info("Found that the dragon has not yet been killed in this world.");
               this.field_186118_l = false;
               this.func_186094_a(false);
            }

            List<EntityDragon> list = this.field_186110_d.func_175644_a(EntityDragon.class, EntitySelectors.field_94557_a);
            if (list.isEmpty()) {
               this.field_186117_k = true;
            } else {
               EntityDragon entitydragon = list.get(0);
               this.field_186119_m = entitydragon.func_110124_au();
               field_186107_a.info("Found that there's a dragon still alive ({})", (Object)entitydragon);
               this.field_186117_k = false;
               if (!flag) {
                  field_186107_a.info("But we didn't have a portal, let's remove it.");
                  entitydragon.func_70106_y();
                  this.field_186119_m = null;
               }
            }

            if (!this.field_186118_l && this.field_186117_k) {
               this.field_186117_k = false;
            }
         }

         if (this.field_186122_p != null) {
            if (this.field_186124_r == null) {
               this.field_186122_p = null;
               this.func_186106_e();
            }

            this.field_186122_p.func_186079_a(this.field_186110_d, this, this.field_186124_r, this.field_186123_q++, this.field_186121_o);
         }

         if (!this.field_186117_k) {
            if (this.field_186119_m == null || ++this.field_186113_g >= 1200) {
               this.func_186103_i();
               List<EntityDragon> list1 = this.field_186110_d.func_175644_a(EntityDragon.class, EntitySelectors.field_94557_a);
               if (list1.isEmpty()) {
                  field_186107_a.debug("Haven't seen the dragon, respawning it");
                  this.func_192445_m();
               } else {
                  field_186107_a.debug("Haven't seen our dragon, but found another one to use.");
                  this.field_186119_m = ((EntityDragon)list1.get(0)).func_110124_au();
               }

               this.field_186113_g = 0;
            }

            if (++this.field_186115_i >= 100) {
               this.func_186101_k();
               this.field_186115_i = 0;
            }
         }
      }

   }

   protected void func_186095_a(DragonSpawnManager p_186095_1_) {
      if (this.field_186122_p == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.field_186123_q = 0;
         if (p_186095_1_ == DragonSpawnManager.END) {
            this.field_186122_p = null;
            this.field_186117_k = false;
            EntityDragon entitydragon = this.func_192445_m();

            for(EntityPlayerMP entityplayermp : this.field_186109_c.func_186757_c()) {
               CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, entitydragon);
            }
         } else {
            this.field_186122_p = p_186095_1_;
         }

      }
   }

   private boolean func_186104_g() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            Chunk chunk = this.field_186110_d.func_72964_e(i, j);

            for(TileEntity tileentity : chunk.func_177434_r().values()) {
               if (tileentity instanceof TileEntityEndPortal) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.PatternHelper func_186091_h() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            Chunk chunk = this.field_186110_d.func_72964_e(i, j);

            for(TileEntity tileentity : chunk.func_177434_r().values()) {
               if (tileentity instanceof TileEntityEndPortal) {
                  BlockPattern.PatternHelper blockpattern$patternhelper = this.field_186112_f.func_177681_a(this.field_186110_d, tileentity.func_174877_v());
                  if (blockpattern$patternhelper != null) {
                     BlockPos blockpos = blockpattern$patternhelper.func_177670_a(3, 3, 3).func_177508_d();
                     if (this.field_186121_o == null && blockpos.func_177958_n() == 0 && blockpos.func_177952_p() == 0) {
                        this.field_186121_o = blockpos;
                     }

                     return blockpattern$patternhelper;
                  }
               }
            }
         }
      }

      int k = this.field_186110_d.func_175645_m(WorldGenEndPodium.field_186139_a).func_177956_o();

      for(int l = k; l >= 0; --l) {
         BlockPattern.PatternHelper blockpattern$patternhelper1 = this.field_186112_f.func_177681_a(this.field_186110_d, new BlockPos(WorldGenEndPodium.field_186139_a.func_177958_n(), l, WorldGenEndPodium.field_186139_a.func_177952_p()));
         if (blockpattern$patternhelper1 != null) {
            if (this.field_186121_o == null) {
               this.field_186121_o = blockpattern$patternhelper1.func_177670_a(3, 3, 3).func_177508_d();
            }

            return blockpattern$patternhelper1;
         }
      }

      return null;
   }

   private void func_186103_i() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            this.field_186110_d.func_72964_e(i, j);
         }
      }

   }

   private void func_186100_j() {
      Set<EntityPlayerMP> set = Sets.<EntityPlayerMP>newHashSet();

      for(EntityPlayerMP entityplayermp : this.field_186110_d.func_175661_b(EntityPlayerMP.class, field_186108_b)) {
         this.field_186109_c.func_186760_a(entityplayermp);
         set.add(entityplayermp);
      }

      Set<EntityPlayerMP> set1 = Sets.newHashSet(this.field_186109_c.func_186757_c());
      set1.removeAll(set);

      for(EntityPlayerMP entityplayermp1 : set1) {
         this.field_186109_c.func_186761_b(entityplayermp1);
      }

   }

   private void func_186101_k() {
      this.field_186115_i = 0;
      this.field_186114_h = 0;

      for(WorldGenSpikes.EndSpike worldgenspikes$endspike : BiomeEndDecorator.func_185426_a(this.field_186110_d)) {
         this.field_186114_h += this.field_186110_d.func_72872_a(EntityEnderCrystal.class, worldgenspikes$endspike.func_186153_f()).size();
      }

      field_186107_a.debug("Found {} end crystals still alive", (int)this.field_186114_h);
   }

   public void func_186096_a(EntityDragon p_186096_1_) {
      if (p_186096_1_.func_110124_au().equals(this.field_186119_m)) {
         this.field_186109_c.func_186735_a(0.0F);
         this.field_186109_c.func_186758_d(false);
         this.func_186094_a(true);
         this.func_186097_l();
         if (!this.field_186118_l) {
            this.field_186110_d.func_175656_a(this.field_186110_d.func_175645_m(WorldGenEndPodium.field_186139_a), Blocks.field_150380_bt.func_176223_P());
         }

         this.field_186118_l = true;
         this.field_186117_k = true;
      }

   }

   private void func_186097_l() {
      if (!this.field_186111_e.isEmpty()) {
         int i = ((Integer)this.field_186111_e.remove(this.field_186111_e.size() - 1)).intValue();
         int j = (int)(96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)i)));
         int k = (int)(96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)i)));
         this.func_186089_a(new BlockPos(j, 75, k));
      }
   }

   private void func_186089_a(BlockPos p_186089_1_) {
      this.field_186110_d.func_175718_b(3000, p_186089_1_, 0);
      (new WorldGenEndGateway()).func_180709_b(this.field_186110_d, new Random(), p_186089_1_);
   }

   private void func_186094_a(boolean p_186094_1_) {
      WorldGenEndPodium worldgenendpodium = new WorldGenEndPodium(p_186094_1_);
      if (this.field_186121_o == null) {
         for(this.field_186121_o = this.field_186110_d.func_175672_r(WorldGenEndPodium.field_186139_a).func_177977_b(); this.field_186110_d.func_180495_p(this.field_186121_o).func_177230_c() == Blocks.field_150357_h && this.field_186121_o.func_177956_o() > this.field_186110_d.func_181545_F(); this.field_186121_o = this.field_186121_o.func_177977_b()) {
            ;
         }
      }

      worldgenendpodium.func_180709_b(this.field_186110_d, new Random(), this.field_186121_o);
   }

   private EntityDragon func_192445_m() {
      this.field_186110_d.func_175726_f(new BlockPos(0, 128, 0));
      EntityDragon entitydragon = new EntityDragon(this.field_186110_d);
      entitydragon.func_184670_cT().func_188758_a(PhaseList.field_188741_a);
      entitydragon.func_70012_b(0.0D, 128.0D, 0.0D, this.field_186110_d.field_73012_v.nextFloat() * 360.0F, 0.0F);
      this.field_186110_d.func_72838_d(entitydragon);
      this.field_186119_m = entitydragon.func_110124_au();
      return entitydragon;
   }

   public void func_186099_b(EntityDragon p_186099_1_) {
      if (p_186099_1_.func_110124_au().equals(this.field_186119_m)) {
         this.field_186109_c.func_186735_a(p_186099_1_.func_110143_aJ() / p_186099_1_.func_110138_aP());
         this.field_186113_g = 0;
         if (p_186099_1_.func_145818_k_()) {
            this.field_186109_c.func_186739_a(p_186099_1_.func_145748_c_());
         }
      }

   }

   public int func_186092_c() {
      return this.field_186114_h;
   }

   public void func_186090_a(EntityEnderCrystal p_186090_1_, DamageSource p_186090_2_) {
      if (this.field_186122_p != null && this.field_186124_r.contains(p_186090_1_)) {
         field_186107_a.debug("Aborting respawn sequence");
         this.field_186122_p = null;
         this.field_186123_q = 0;
         this.func_186087_f();
         this.func_186094_a(true);
      } else {
         this.func_186101_k();
         Entity entity = this.field_186110_d.func_175733_a(this.field_186119_m);
         if (entity instanceof EntityDragon) {
            ((EntityDragon)entity).func_184672_a(p_186090_1_, new BlockPos(p_186090_1_), p_186090_2_);
         }
      }

   }

   public boolean func_186102_d() {
      return this.field_186118_l;
   }

   public void func_186106_e() {
      if (this.field_186117_k && this.field_186122_p == null) {
         BlockPos blockpos = this.field_186121_o;
         if (blockpos == null) {
            field_186107_a.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.PatternHelper blockpattern$patternhelper = this.func_186091_h();
            if (blockpattern$patternhelper == null) {
               field_186107_a.debug("Couldn't find a portal, so we made one.");
               this.func_186094_a(true);
            } else {
               field_186107_a.debug("Found the exit portal & temporarily using it.");
            }

            blockpos = this.field_186121_o;
         }

         List<EntityEnderCrystal> list1 = Lists.<EntityEnderCrystal>newArrayList();
         BlockPos blockpos1 = blockpos.func_177981_b(1);

         for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            List<EntityEnderCrystal> list = this.field_186110_d.func_72872_a(EntityEnderCrystal.class, new AxisAlignedBB(blockpos1.func_177967_a(enumfacing, 2)));
            if (list.isEmpty()) {
               return;
            }

            list1.addAll(list);
         }

         field_186107_a.debug("Found all crystals, respawning dragon.");
         this.func_186093_a(list1);
      }

   }

   private void func_186093_a(List<EntityEnderCrystal> p_186093_1_) {
      if (this.field_186117_k && this.field_186122_p == null) {
         for(BlockPattern.PatternHelper blockpattern$patternhelper = this.func_186091_h(); blockpattern$patternhelper != null; blockpattern$patternhelper = this.func_186091_h()) {
            for(int i = 0; i < this.field_186112_f.func_177684_c(); ++i) {
               for(int j = 0; j < this.field_186112_f.func_177685_b(); ++j) {
                  for(int k = 0; k < this.field_186112_f.func_185922_a(); ++k) {
                     BlockWorldState blockworldstate = blockpattern$patternhelper.func_177670_a(i, j, k);
                     if (blockworldstate.func_177509_a().func_177230_c() == Blocks.field_150357_h || blockworldstate.func_177509_a().func_177230_c() == Blocks.field_150384_bq) {
                        this.field_186110_d.func_175656_a(blockworldstate.func_177508_d(), Blocks.field_150377_bs.func_176223_P());
                     }
                  }
               }
            }
         }

         this.field_186122_p = DragonSpawnManager.START;
         this.field_186123_q = 0;
         this.func_186094_a(false);
         this.field_186124_r = p_186093_1_;
      }

   }

   public void func_186087_f() {
      for(WorldGenSpikes.EndSpike worldgenspikes$endspike : BiomeEndDecorator.func_185426_a(this.field_186110_d)) {
         for(EntityEnderCrystal entityendercrystal : this.field_186110_d.func_72872_a(EntityEnderCrystal.class, worldgenspikes$endspike.func_186153_f())) {
            entityendercrystal.func_184224_h(false);
            entityendercrystal.func_184516_a((BlockPos)null);
         }
      }

   }
}
