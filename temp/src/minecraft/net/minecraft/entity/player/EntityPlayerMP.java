package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.CooldownTrackerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements IContainerListener {
   private static final Logger field_147102_bM = LogManager.getLogger();
   private String field_71148_cg = "en_US";
   public NetHandlerPlayServer field_71135_a;
   public final MinecraftServer field_71133_b;
   public final PlayerInteractionManager field_71134_c;
   public double field_71131_d;
   public double field_71132_e;
   private final List<Integer> field_71130_g = Lists.<Integer>newLinkedList();
   private final PlayerAdvancements field_192042_bX;
   private final StatisticsManagerServer field_147103_bO;
   private float field_130068_bO = Float.MIN_VALUE;
   private int field_184852_bV = Integer.MIN_VALUE;
   private int field_184853_bW = Integer.MIN_VALUE;
   private int field_184854_bX = Integer.MIN_VALUE;
   private int field_184855_bY = Integer.MIN_VALUE;
   private int field_184856_bZ = Integer.MIN_VALUE;
   private float field_71149_ch = -1.0E8F;
   private int field_71146_ci = -99999999;
   private boolean field_71147_cj = true;
   private int field_71144_ck = -99999999;
   private int field_147101_bU = 60;
   private EntityPlayer.EnumChatVisibility field_71143_cn;
   private boolean field_71140_co = true;
   private long field_143005_bX = System.currentTimeMillis();
   private Entity field_175401_bS;
   private boolean field_184851_cj;
   private boolean field_192040_cp;
   private final RecipeBookServer field_192041_cq = new RecipeBookServer();
   private Vec3d field_193107_ct;
   private int field_193108_cu;
   private boolean field_193109_cv;
   private Vec3d field_193110_cw;
   private int field_71139_cq;
   public boolean field_71137_h;
   public int field_71138_i;
   public boolean field_71136_j;

   public EntityPlayerMP(MinecraftServer p_i45285_1_, WorldServer p_i45285_2_, GameProfile p_i45285_3_, PlayerInteractionManager p_i45285_4_) {
      super(p_i45285_2_, p_i45285_3_);
      p_i45285_4_.field_73090_b = this;
      this.field_71134_c = p_i45285_4_;
      BlockPos blockpos = p_i45285_2_.func_175694_M();
      if (p_i45285_2_.field_73011_w.func_191066_m() && p_i45285_2_.func_72912_H().func_76077_q() != GameType.ADVENTURE) {
         int i = Math.max(0, p_i45285_1_.func_184108_a(p_i45285_2_));
         int j = MathHelper.func_76128_c(p_i45285_2_.func_175723_af().func_177729_b((double)blockpos.func_177958_n(), (double)blockpos.func_177952_p()));
         if (j < i) {
            i = j;
         }

         if (j <= 1) {
            i = 1;
         }

         blockpos = p_i45285_2_.func_175672_r(blockpos.func_177982_a(this.field_70146_Z.nextInt(i * 2 + 1) - i, 0, this.field_70146_Z.nextInt(i * 2 + 1) - i));
      }

      this.field_71133_b = p_i45285_1_;
      this.field_147103_bO = p_i45285_1_.func_184103_al().func_152602_a(this);
      this.field_192042_bX = p_i45285_1_.func_184103_al().func_192054_h(this);
      this.field_70138_W = 1.0F;
      this.func_174828_a(blockpos, 0.0F, 0.0F);

      while(!p_i45285_2_.func_184144_a(this, this.func_174813_aQ()).isEmpty() && this.field_70163_u < 255.0D) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v);
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("playerGameType", 99)) {
         if (this.func_184102_h().func_104056_am()) {
            this.field_71134_c.func_73076_a(this.func_184102_h().func_71265_f());
         } else {
            this.field_71134_c.func_73076_a(GameType.func_77146_a(p_70037_1_.func_74762_e("playerGameType")));
         }
      }

      if (p_70037_1_.func_150297_b("enteredNetherPosition", 10)) {
         NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("enteredNetherPosition");
         this.field_193110_cw = new Vec3d(nbttagcompound.func_74769_h("x"), nbttagcompound.func_74769_h("y"), nbttagcompound.func_74769_h("z"));
      }

      this.field_192040_cp = p_70037_1_.func_74767_n("seenCredits");
      if (p_70037_1_.func_150297_b("recipeBook", 10)) {
         this.field_192041_cq.func_192825_a(p_70037_1_.func_74775_l("recipeBook"));
      }

   }

   public static void func_191522_a(DataFixer p_191522_0_) {
      p_191522_0_.func_188258_a(FixTypes.PLAYER, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (p_188266_2_.func_150297_b("RootVehicle", 10)) {
               NBTTagCompound nbttagcompound = p_188266_2_.func_74775_l("RootVehicle");
               if (nbttagcompound.func_150297_b("Entity", 10)) {
                  nbttagcompound.func_74782_a("Entity", p_188266_1_.func_188251_a(FixTypes.ENTITY, nbttagcompound.func_74775_l("Entity"), p_188266_3_));
               }
            }

            return p_188266_2_;
         }
      });
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("playerGameType", this.field_71134_c.func_73081_b().func_77148_a());
      p_70014_1_.func_74757_a("seenCredits", this.field_192040_cp);
      if (this.field_193110_cw != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74780_a("x", this.field_193110_cw.field_72450_a);
         nbttagcompound.func_74780_a("y", this.field_193110_cw.field_72448_b);
         nbttagcompound.func_74780_a("z", this.field_193110_cw.field_72449_c);
         p_70014_1_.func_74782_a("enteredNetherPosition", nbttagcompound);
      }

      Entity entity1 = this.func_184208_bv();
      Entity entity = this.func_184187_bx();
      if (entity != null && entity1 != this && entity1.func_184180_b(EntityPlayerMP.class).size() == 1) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         entity1.func_70039_c(nbttagcompound2);
         nbttagcompound1.func_186854_a("Attach", entity.func_110124_au());
         nbttagcompound1.func_74782_a("Entity", nbttagcompound2);
         p_70014_1_.func_74782_a("RootVehicle", nbttagcompound1);
      }

      p_70014_1_.func_74782_a("recipeBook", this.field_192041_cq.func_192824_e());
   }

   public void func_82242_a(int p_82242_1_) {
      super.func_82242_a(p_82242_1_);
      this.field_71144_ck = -1;
   }

   public void func_192024_a(ItemStack p_192024_1_, int p_192024_2_) {
      super.func_192024_a(p_192024_1_, p_192024_2_);
      this.field_71144_ck = -1;
   }

   public void func_71116_b() {
      this.field_71070_bA.func_75132_a(this);
   }

   public void func_152111_bt() {
      super.func_152111_bt();
      this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTER_COMBAT));
   }

   public void func_152112_bu() {
      super.func_152112_bu();
      this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.END_COMBAT));
   }

   protected void func_191955_a(IBlockState p_191955_1_) {
      CriteriaTriggers.field_192124_d.func_192193_a(this, p_191955_1_);
   }

   protected CooldownTracker func_184815_l() {
      return new CooldownTrackerServer(this);
   }

   public void func_70071_h_() {
      this.field_71134_c.func_73075_a();
      --this.field_147101_bU;
      if (this.field_70172_ad > 0) {
         --this.field_70172_ad;
      }

      this.field_71070_bA.func_75142_b();
      if (!this.field_70170_p.field_72995_K && !this.field_71070_bA.func_75145_c(this)) {
         this.func_71053_j();
         this.field_71070_bA = this.field_71069_bz;
      }

      while(!this.field_71130_g.isEmpty()) {
         int i = Math.min(this.field_71130_g.size(), Integer.MAX_VALUE);
         int[] aint = new int[i];
         Iterator<Integer> iterator = this.field_71130_g.iterator();
         int j = 0;

         while(iterator.hasNext() && j < i) {
            aint[j++] = ((Integer)iterator.next()).intValue();
            iterator.remove();
         }

         this.field_71135_a.func_147359_a(new SPacketDestroyEntities(aint));
      }

      Entity entity = this.func_175398_C();
      if (entity != this) {
         if (entity.func_70089_S()) {
            this.func_70080_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70177_z, entity.field_70125_A);
            this.field_71133_b.func_184103_al().func_72358_d(this);
            if (this.func_70093_af()) {
               this.func_175399_e(this);
            }
         } else {
            this.func_175399_e(this);
         }
      }

      CriteriaTriggers.field_193135_v.func_193182_a(this);
      if (this.field_193107_ct != null) {
         CriteriaTriggers.field_193133_t.func_193162_a(this, this.field_193107_ct, this.field_70173_aa - this.field_193108_cu);
      }

      this.field_192042_bX.func_192741_b(this);
   }

   public void func_71127_g() {
      try {
         super.func_70071_h_();

         for(int i = 0; i < this.field_71071_by.func_70302_i_(); ++i) {
            ItemStack itemstack = this.field_71071_by.func_70301_a(i);
            if (!itemstack.func_190926_b() && itemstack.func_77973_b().func_77643_m_()) {
               Packet<?> packet = ((ItemMapBase)itemstack.func_77973_b()).func_150911_c(itemstack, this.field_70170_p, this);
               if (packet != null) {
                  this.field_71135_a.func_147359_a(packet);
               }
            }
         }

         if (this.func_110143_aJ() != this.field_71149_ch || this.field_71146_ci != this.field_71100_bB.func_75116_a() || this.field_71100_bB.func_75115_e() == 0.0F != this.field_71147_cj) {
            this.field_71135_a.func_147359_a(new SPacketUpdateHealth(this.func_110143_aJ(), this.field_71100_bB.func_75116_a(), this.field_71100_bB.func_75115_e()));
            this.field_71149_ch = this.func_110143_aJ();
            this.field_71146_ci = this.field_71100_bB.func_75116_a();
            this.field_71147_cj = this.field_71100_bB.func_75115_e() == 0.0F;
         }

         if (this.func_110143_aJ() + this.func_110139_bj() != this.field_130068_bO) {
            this.field_130068_bO = this.func_110143_aJ() + this.func_110139_bj();
            this.func_184849_a(IScoreCriteria.field_96638_f, MathHelper.func_76123_f(this.field_130068_bO));
         }

         if (this.field_71100_bB.func_75116_a() != this.field_184852_bV) {
            this.field_184852_bV = this.field_71100_bB.func_75116_a();
            this.func_184849_a(IScoreCriteria.field_186698_h, MathHelper.func_76123_f((float)this.field_184852_bV));
         }

         if (this.func_70086_ai() != this.field_184853_bW) {
            this.field_184853_bW = this.func_70086_ai();
            this.func_184849_a(IScoreCriteria.field_186699_i, MathHelper.func_76123_f((float)this.field_184853_bW));
         }

         if (this.func_70658_aO() != this.field_184854_bX) {
            this.field_184854_bX = this.func_70658_aO();
            this.func_184849_a(IScoreCriteria.field_186700_j, MathHelper.func_76123_f((float)this.field_184854_bX));
         }

         if (this.field_71067_cb != this.field_184856_bZ) {
            this.field_184856_bZ = this.field_71067_cb;
            this.func_184849_a(IScoreCriteria.field_186701_k, MathHelper.func_76123_f((float)this.field_184856_bZ));
         }

         if (this.field_71068_ca != this.field_184855_bY) {
            this.field_184855_bY = this.field_71068_ca;
            this.func_184849_a(IScoreCriteria.field_186702_l, MathHelper.func_76123_f((float)this.field_184855_bY));
         }

         if (this.field_71067_cb != this.field_71144_ck) {
            this.field_71144_ck = this.field_71067_cb;
            this.field_71135_a.func_147359_a(new SPacketSetExperience(this.field_71106_cc, this.field_71067_cb, this.field_71068_ca));
         }

         if (this.field_70173_aa % 20 == 0) {
            CriteriaTriggers.field_192135_o.func_192215_a(this);
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Ticking player");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Player being ticked");
         this.func_85029_a(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   private void func_184849_a(IScoreCriteria p_184849_1_, int p_184849_2_) {
      for(ScoreObjective scoreobjective : this.func_96123_co().func_96520_a(p_184849_1_)) {
         Score score = this.func_96123_co().func_96529_a(this.func_70005_c_(), scoreobjective);
         score.func_96647_c(p_184849_2_);
      }

   }

   public void func_70645_a(DamageSource p_70645_1_) {
      boolean flag = this.field_70170_p.func_82736_K().func_82766_b("showDeathMessages");
      this.field_71135_a.func_147359_a(new SPacketCombatEvent(this.func_110142_aN(), SPacketCombatEvent.Event.ENTITY_DIED, flag));
      if (flag) {
         Team team = this.func_96124_cp();
         if (team != null && team.func_178771_j() != Team.EnumVisible.ALWAYS) {
            if (team.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
               this.field_71133_b.func_184103_al().func_177453_a(this, this.func_110142_aN().func_151521_b());
            } else if (team.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
               this.field_71133_b.func_184103_al().func_177452_b(this, this.func_110142_aN().func_151521_b());
            }
         } else {
            this.field_71133_b.func_184103_al().func_148539_a(this.func_110142_aN().func_151521_b());
         }
      }

      this.func_192030_dh();
      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         this.func_190776_cN();
         this.field_71071_by.func_70436_m();
      }

      for(ScoreObjective scoreobjective : this.field_70170_p.func_96441_U().func_96520_a(IScoreCriteria.field_96642_c)) {
         Score score = this.func_96123_co().func_96529_a(this.func_70005_c_(), scoreobjective);
         score.func_96648_a();
      }

      EntityLivingBase entitylivingbase = this.func_94060_bK();
      if (entitylivingbase != null) {
         EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.field_75627_a.get(EntityList.func_191301_a(entitylivingbase));
         if (entitylist$entityegginfo != null) {
            this.func_71029_a(entitylist$entityegginfo.field_151513_e);
         }

         entitylivingbase.func_191956_a(this, this.field_70744_aE, p_70645_1_);
      }

      this.func_71029_a(StatList.field_188069_A);
      this.func_175145_a(StatList.field_188098_h);
      this.func_70066_B();
      this.func_70052_a(0, false);
      this.func_110142_aN().func_94549_h();
   }

   public void func_191956_a(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
      if (p_191956_1_ != this) {
         super.func_191956_a(p_191956_1_, p_191956_2_, p_191956_3_);
         this.func_85039_t(p_191956_2_);
         Collection<ScoreObjective> collection = this.func_96123_co().func_96520_a(IScoreCriteria.field_96640_e);
         if (p_191956_1_ instanceof EntityPlayer) {
            this.func_71029_a(StatList.field_75932_A);
            collection.addAll(this.func_96123_co().func_96520_a(IScoreCriteria.field_96639_d));
         } else {
            this.func_71029_a(StatList.field_188070_B);
         }

         collection.addAll(this.func_192038_E(p_191956_1_));

         for(ScoreObjective scoreobjective : collection) {
            this.func_96123_co().func_96529_a(this.func_70005_c_(), scoreobjective).func_96648_a();
         }

         CriteriaTriggers.field_192122_b.func_192211_a(this, p_191956_1_, p_191956_3_);
      }
   }

   private Collection<ScoreObjective> func_192038_E(Entity p_192038_1_) {
      String s = p_192038_1_ instanceof EntityPlayer ? p_192038_1_.func_70005_c_() : p_192038_1_.func_189512_bd();
      ScorePlayerTeam scoreplayerteam = this.func_96123_co().func_96509_i(this.func_70005_c_());
      if (scoreplayerteam != null) {
         int i = scoreplayerteam.func_178775_l().func_175746_b();
         if (i >= 0 && i < IScoreCriteria.field_178793_i.length) {
            for(ScoreObjective scoreobjective : this.func_96123_co().func_96520_a(IScoreCriteria.field_178793_i[i])) {
               Score score = this.func_96123_co().func_96529_a(s, scoreobjective);
               score.func_96648_a();
            }
         }
      }

      ScorePlayerTeam scoreplayerteam1 = this.func_96123_co().func_96509_i(s);
      if (scoreplayerteam1 != null) {
         int j = scoreplayerteam1.func_178775_l().func_175746_b();
         if (j >= 0 && j < IScoreCriteria.field_178792_h.length) {
            return this.func_96123_co().func_96520_a(IScoreCriteria.field_178792_h[j]);
         }
      }

      return Lists.<ScoreObjective>newArrayList();
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (this.func_180431_b(p_70097_1_)) {
         return false;
      } else {
         boolean flag = this.field_71133_b.func_71262_S() && this.func_175400_cq() && "fall".equals(p_70097_1_.field_76373_n);
         if (!flag && this.field_147101_bU > 0 && p_70097_1_ != DamageSource.field_76380_i) {
            return false;
         } else {
            if (p_70097_1_ instanceof EntityDamageSource) {
               Entity entity = p_70097_1_.func_76346_g();
               if (entity instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)entity)) {
                  return false;
               }

               if (entity instanceof EntityArrow) {
                  EntityArrow entityarrow = (EntityArrow)entity;
                  if (entityarrow.field_70250_c instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)entityarrow.field_70250_c)) {
                     return false;
                  }
               }
            }

            return super.func_70097_a(p_70097_1_, p_70097_2_);
         }
      }
   }

   public boolean func_96122_a(EntityPlayer p_96122_1_) {
      return !this.func_175400_cq() ? false : super.func_96122_a(p_96122_1_);
   }

   private boolean func_175400_cq() {
      return this.field_71133_b.func_71219_W();
   }

   @Nullable
   public Entity func_184204_a(int p_184204_1_) {
      this.field_184851_cj = true;
      if (this.field_71093_bK == 0 && p_184204_1_ == -1) {
         this.field_193110_cw = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      } else if (this.field_71093_bK != -1 && p_184204_1_ != 0) {
         this.field_193110_cw = null;
      }

      if (this.field_71093_bK == 1 && p_184204_1_ == 1) {
         this.field_70170_p.func_72900_e(this);
         if (!this.field_71136_j) {
            this.field_71136_j = true;
            this.field_71135_a.func_147359_a(new SPacketChangeGameState(4, this.field_192040_cp ? 0.0F : 1.0F));
            this.field_192040_cp = true;
         }

         return this;
      } else {
         if (this.field_71093_bK == 0 && p_184204_1_ == 1) {
            p_184204_1_ = 1;
         }

         this.field_71133_b.func_184103_al().func_187242_a(this, p_184204_1_);
         this.field_71135_a.func_147359_a(new SPacketEffect(1032, BlockPos.field_177992_a, 0, false));
         this.field_71144_ck = -1;
         this.field_71149_ch = -1.0F;
         this.field_71146_ci = -1;
         return this;
      }
   }

   public boolean func_174827_a(EntityPlayerMP p_174827_1_) {
      if (p_174827_1_.func_175149_v()) {
         return this.func_175398_C() == this;
      } else {
         return this.func_175149_v() ? false : super.func_174827_a(p_174827_1_);
      }
   }

   private void func_147097_b(TileEntity p_147097_1_) {
      if (p_147097_1_ != null) {
         SPacketUpdateTileEntity spacketupdatetileentity = p_147097_1_.func_189518_D_();
         if (spacketupdatetileentity != null) {
            this.field_71135_a.func_147359_a(spacketupdatetileentity);
         }
      }

   }

   public void func_71001_a(Entity p_71001_1_, int p_71001_2_) {
      super.func_71001_a(p_71001_1_, p_71001_2_);
      this.field_71070_bA.func_75142_b();
   }

   public EntityPlayer.SleepResult func_180469_a(BlockPos p_180469_1_) {
      EntityPlayer.SleepResult entityplayer$sleepresult = super.func_180469_a(p_180469_1_);
      if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
         this.func_71029_a(StatList.field_188064_ad);
         Packet<?> packet = new SPacketUseBed(this, p_180469_1_);
         this.func_71121_q().func_73039_n().func_151247_a(this, packet);
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         this.field_71135_a.func_147359_a(packet);
         CriteriaTriggers.field_192136_p.func_192215_a(this);
      }

      return entityplayer$sleepresult;
   }

   public void func_70999_a(boolean p_70999_1_, boolean p_70999_2_, boolean p_70999_3_) {
      if (this.func_70608_bn()) {
         this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(this, 2));
      }

      super.func_70999_a(p_70999_1_, p_70999_2_, p_70999_3_);
      if (this.field_71135_a != null) {
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      }

   }

   public boolean func_184205_a(Entity p_184205_1_, boolean p_184205_2_) {
      Entity entity = this.func_184187_bx();
      if (!super.func_184205_a(p_184205_1_, p_184205_2_)) {
         return false;
      } else {
         Entity entity1 = this.func_184187_bx();
         if (entity1 != entity && this.field_71135_a != null) {
            this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         }

         return true;
      }
   }

   public void func_184210_p() {
      Entity entity = this.func_184187_bx();
      super.func_184210_p();
      Entity entity1 = this.func_184187_bx();
      if (entity1 != entity && this.field_71135_a != null) {
         this.field_71135_a.func_147364_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      }

   }

   public boolean func_180431_b(DamageSource p_180431_1_) {
      return super.func_180431_b(p_180431_1_) || this.func_184850_K();
   }

   protected void func_184231_a(double p_184231_1_, boolean p_184231_3_, IBlockState p_184231_4_, BlockPos p_184231_5_) {
   }

   protected void func_184594_b(BlockPos p_184594_1_) {
      if (!this.func_175149_v()) {
         super.func_184594_b(p_184594_1_);
      }

   }

   public void func_71122_b(double p_71122_1_, boolean p_71122_3_) {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
      if (iblockstate.func_185904_a() == Material.field_151579_a) {
         BlockPos blockpos1 = blockpos.func_177977_b();
         IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
         Block block = iblockstate1.func_177230_c();
         if (block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate) {
            blockpos = blockpos1;
            iblockstate = iblockstate1;
         }
      }

      super.func_184231_a(p_71122_1_, p_71122_3_, iblockstate, blockpos);
   }

   public void func_175141_a(TileEntitySign p_175141_1_) {
      p_175141_1_.func_145912_a(this);
      this.field_71135_a.func_147359_a(new SPacketSignEditorOpen(p_175141_1_.func_174877_v()));
   }

   private void func_71117_bO() {
      this.field_71139_cq = this.field_71139_cq % 100 + 1;
   }

   public void func_180468_a(IInteractionObject p_180468_1_) {
      if (p_180468_1_ instanceof ILootContainer && ((ILootContainer)p_180468_1_).func_184276_b() != null && this.func_175149_v()) {
         this.func_146105_b((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).func_150255_a((new Style()).func_150238_a(TextFormatting.RED)), true);
      } else {
         this.func_71117_bO();
         this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, p_180468_1_.func_174875_k(), p_180468_1_.func_145748_c_()));
         this.field_71070_bA = p_180468_1_.func_174876_a(this.field_71071_by, this);
         this.field_71070_bA.field_75152_c = this.field_71139_cq;
         this.field_71070_bA.func_75132_a(this);
      }
   }

   public void func_71007_a(IInventory p_71007_1_) {
      if (p_71007_1_ instanceof ILootContainer && ((ILootContainer)p_71007_1_).func_184276_b() != null && this.func_175149_v()) {
         this.func_146105_b((new TextComponentTranslation("container.spectatorCantOpen", new Object[0])).func_150255_a((new Style()).func_150238_a(TextFormatting.RED)), true);
      } else {
         if (this.field_71070_bA != this.field_71069_bz) {
            this.func_71053_j();
         }

         if (p_71007_1_ instanceof ILockableContainer) {
            ILockableContainer ilockablecontainer = (ILockableContainer)p_71007_1_;
            if (ilockablecontainer.func_174893_q_() && !this.func_175146_a(ilockablecontainer.func_174891_i()) && !this.func_175149_v()) {
               this.field_71135_a.func_147359_a(new SPacketChat(new TextComponentTranslation("container.isLocked", new Object[]{p_71007_1_.func_145748_c_()}), ChatType.GAME_INFO));
               this.field_71135_a.func_147359_a(new SPacketSoundEffect(SoundEvents.field_187654_U, SoundCategory.BLOCKS, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, 1.0F));
               return;
            }
         }

         this.func_71117_bO();
         if (p_71007_1_ instanceof IInteractionObject) {
            this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, ((IInteractionObject)p_71007_1_).func_174875_k(), p_71007_1_.func_145748_c_(), p_71007_1_.func_70302_i_()));
            this.field_71070_bA = ((IInteractionObject)p_71007_1_).func_174876_a(this.field_71071_by, this);
         } else {
            this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "minecraft:container", p_71007_1_.func_145748_c_(), p_71007_1_.func_70302_i_()));
            this.field_71070_bA = new ContainerChest(this.field_71071_by, p_71007_1_, this);
         }

         this.field_71070_bA.field_75152_c = this.field_71139_cq;
         this.field_71070_bA.func_75132_a(this);
      }
   }

   public void func_180472_a(IMerchant p_180472_1_) {
      this.func_71117_bO();
      this.field_71070_bA = new ContainerMerchant(this.field_71071_by, p_180472_1_, this.field_70170_p);
      this.field_71070_bA.field_75152_c = this.field_71139_cq;
      this.field_71070_bA.func_75132_a(this);
      IInventory iinventory = ((ContainerMerchant)this.field_71070_bA).func_75174_d();
      ITextComponent itextcomponent = p_180472_1_.func_145748_c_();
      this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "minecraft:villager", itextcomponent, iinventory.func_70302_i_()));
      MerchantRecipeList merchantrecipelist = p_180472_1_.func_70934_b(this);
      if (merchantrecipelist != null) {
         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
         packetbuffer.writeInt(this.field_71139_cq);
         merchantrecipelist.func_151391_a(packetbuffer);
         this.field_71135_a.func_147359_a(new SPacketCustomPayload("MC|TrList", packetbuffer));
      }

   }

   public void func_184826_a(AbstractHorse p_184826_1_, IInventory p_184826_2_) {
      if (this.field_71070_bA != this.field_71069_bz) {
         this.func_71053_j();
      }

      this.func_71117_bO();
      this.field_71135_a.func_147359_a(new SPacketOpenWindow(this.field_71139_cq, "EntityHorse", p_184826_2_.func_145748_c_(), p_184826_2_.func_70302_i_(), p_184826_1_.func_145782_y()));
      this.field_71070_bA = new ContainerHorseInventory(this.field_71071_by, p_184826_2_, p_184826_1_, this);
      this.field_71070_bA.field_75152_c = this.field_71139_cq;
      this.field_71070_bA.func_75132_a(this);
   }

   public void func_184814_a(ItemStack p_184814_1_, EnumHand p_184814_2_) {
      Item item = p_184814_1_.func_77973_b();
      if (item == Items.field_151164_bB) {
         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
         packetbuffer.func_179249_a(p_184814_2_);
         this.field_71135_a.func_147359_a(new SPacketCustomPayload("MC|BOpen", packetbuffer));
      }

   }

   public void func_184824_a(TileEntityCommandBlock p_184824_1_) {
      p_184824_1_.func_184252_d(true);
      this.func_147097_b(p_184824_1_);
   }

   public void func_71111_a(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
      if (!(p_71111_1_.func_75139_a(p_71111_2_) instanceof SlotCrafting)) {
         if (p_71111_1_ == this.field_71069_bz) {
            CriteriaTriggers.field_192125_e.func_192208_a(this, this.field_71071_by);
         }

         if (!this.field_71137_h) {
            this.field_71135_a.func_147359_a(new SPacketSetSlot(p_71111_1_.field_75152_c, p_71111_2_, p_71111_3_));
         }
      }
   }

   public void func_71120_a(Container p_71120_1_) {
      this.func_71110_a(p_71120_1_, p_71120_1_.func_75138_a());
   }

   public void func_71110_a(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
      this.field_71135_a.func_147359_a(new SPacketWindowItems(p_71110_1_.field_75152_c, p_71110_2_));
      this.field_71135_a.func_147359_a(new SPacketSetSlot(-1, -1, this.field_71071_by.func_70445_o()));
   }

   public void func_71112_a(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {
      this.field_71135_a.func_147359_a(new SPacketWindowProperty(p_71112_1_.field_75152_c, p_71112_2_, p_71112_3_));
   }

   public void func_175173_a(Container p_175173_1_, IInventory p_175173_2_) {
      for(int i = 0; i < p_175173_2_.func_174890_g(); ++i) {
         this.field_71135_a.func_147359_a(new SPacketWindowProperty(p_175173_1_.field_75152_c, i, p_175173_2_.func_174887_a_(i)));
      }

   }

   public void func_71053_j() {
      this.field_71135_a.func_147359_a(new SPacketCloseWindow(this.field_71070_bA.field_75152_c));
      this.func_71128_l();
   }

   public void func_71113_k() {
      if (!this.field_71137_h) {
         this.field_71135_a.func_147359_a(new SPacketSetSlot(-1, -1, this.field_71071_by.func_70445_o()));
      }
   }

   public void func_71128_l() {
      this.field_71070_bA.func_75134_a(this);
      this.field_71070_bA = this.field_71069_bz;
   }

   public void func_110430_a(float p_110430_1_, float p_110430_2_, boolean p_110430_3_, boolean p_110430_4_) {
      if (this.func_184218_aH()) {
         if (p_110430_1_ >= -1.0F && p_110430_1_ <= 1.0F) {
            this.field_70702_br = p_110430_1_;
         }

         if (p_110430_2_ >= -1.0F && p_110430_2_ <= 1.0F) {
            this.field_191988_bg = p_110430_2_;
         }

         this.field_70703_bu = p_110430_3_;
         this.func_70095_a(p_110430_4_);
      }

   }

   public void func_71064_a(StatBase p_71064_1_, int p_71064_2_) {
      if (p_71064_1_ != null) {
         this.field_147103_bO.func_150871_b(this, p_71064_1_, p_71064_2_);

         for(ScoreObjective scoreobjective : this.func_96123_co().func_96520_a(p_71064_1_.func_150952_k())) {
            this.func_96123_co().func_96529_a(this.func_70005_c_(), scoreobjective).func_96649_a(p_71064_2_);
         }

      }
   }

   public void func_175145_a(StatBase p_175145_1_) {
      if (p_175145_1_ != null) {
         this.field_147103_bO.func_150873_a(this, p_175145_1_, 0);

         for(ScoreObjective scoreobjective : this.func_96123_co().func_96520_a(p_175145_1_.func_150952_k())) {
            this.func_96123_co().func_96529_a(this.func_70005_c_(), scoreobjective).func_96647_c(0);
         }

      }
   }

   public void func_192021_a(List<IRecipe> p_192021_1_) {
      this.field_192041_cq.func_193835_a(p_192021_1_, this);
   }

   public void func_193102_a(ResourceLocation[] p_193102_1_) {
      List<IRecipe> list = Lists.<IRecipe>newArrayList();

      for(ResourceLocation resourcelocation : p_193102_1_) {
         list.add(CraftingManager.func_193373_a(resourcelocation));
      }

      this.func_192021_a(list);
   }

   public void func_192022_b(List<IRecipe> p_192022_1_) {
      this.field_192041_cq.func_193834_b(p_192022_1_, this);
   }

   public void func_71123_m() {
      this.field_193109_cv = true;
      this.func_184226_ay();
      if (this.field_71083_bS) {
         this.func_70999_a(true, false, false);
      }

   }

   public boolean func_193105_t() {
      return this.field_193109_cv;
   }

   public void func_71118_n() {
      this.field_71149_ch = -1.0E8F;
   }

   public void func_146105_b(ITextComponent p_146105_1_, boolean p_146105_2_) {
      this.field_71135_a.func_147359_a(new SPacketChat(p_146105_1_, p_146105_2_ ? ChatType.GAME_INFO : ChatType.CHAT));
   }

   protected void func_71036_o() {
      if (!this.field_184627_bm.func_190926_b() && this.func_184587_cr()) {
         this.field_71135_a.func_147359_a(new SPacketEntityStatus(this, (byte)9));
         super.func_71036_o();
      }

   }

   public void func_193104_a(EntityPlayerMP p_193104_1_, boolean p_193104_2_) {
      if (p_193104_2_) {
         this.field_71071_by.func_70455_b(p_193104_1_.field_71071_by);
         this.func_70606_j(p_193104_1_.func_110143_aJ());
         this.field_71100_bB = p_193104_1_.field_71100_bB;
         this.field_71068_ca = p_193104_1_.field_71068_ca;
         this.field_71067_cb = p_193104_1_.field_71067_cb;
         this.field_71106_cc = p_193104_1_.field_71106_cc;
         this.func_85040_s(p_193104_1_.func_71037_bA());
         this.field_181016_an = p_193104_1_.field_181016_an;
         this.field_181017_ao = p_193104_1_.field_181017_ao;
         this.field_181018_ap = p_193104_1_.field_181018_ap;
      } else if (this.field_70170_p.func_82736_K().func_82766_b("keepInventory") || p_193104_1_.func_175149_v()) {
         this.field_71071_by.func_70455_b(p_193104_1_.field_71071_by);
         this.field_71068_ca = p_193104_1_.field_71068_ca;
         this.field_71067_cb = p_193104_1_.field_71067_cb;
         this.field_71106_cc = p_193104_1_.field_71106_cc;
         this.func_85040_s(p_193104_1_.func_71037_bA());
      }

      this.field_175152_f = p_193104_1_.field_175152_f;
      this.field_71078_a = p_193104_1_.field_71078_a;
      this.func_184212_Q().func_187227_b(field_184827_bp, p_193104_1_.func_184212_Q().func_187225_a(field_184827_bp));
      this.field_71144_ck = -1;
      this.field_71149_ch = -1.0F;
      this.field_71146_ci = -1;
      this.field_192041_cq.func_193824_a(p_193104_1_.field_192041_cq);
      this.field_71130_g.addAll(p_193104_1_.field_71130_g);
      this.field_192040_cp = p_193104_1_.field_192040_cp;
      this.field_193110_cw = p_193104_1_.field_193110_cw;
      this.func_192029_h(p_193104_1_.func_192023_dk());
      this.func_192031_i(p_193104_1_.func_192025_dl());
   }

   protected void func_70670_a(PotionEffect p_70670_1_) {
      super.func_70670_a(p_70670_1_);
      this.field_71135_a.func_147359_a(new SPacketEntityEffect(this.func_145782_y(), p_70670_1_));
      if (p_70670_1_.func_188419_a() == MobEffects.field_188424_y) {
         this.field_193108_cu = this.field_70173_aa;
         this.field_193107_ct = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }

      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   protected void func_70695_b(PotionEffect p_70695_1_, boolean p_70695_2_) {
      super.func_70695_b(p_70695_1_, p_70695_2_);
      this.field_71135_a.func_147359_a(new SPacketEntityEffect(this.func_145782_y(), p_70695_1_));
      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   protected void func_70688_c(PotionEffect p_70688_1_) {
      super.func_70688_c(p_70688_1_);
      this.field_71135_a.func_147359_a(new SPacketRemoveEntityEffect(this.func_145782_y(), p_70688_1_.func_188419_a()));
      if (p_70688_1_.func_188419_a() == MobEffects.field_188424_y) {
         this.field_193107_ct = null;
      }

      CriteriaTriggers.field_193139_z.func_193153_a(this);
   }

   public void func_70634_a(double p_70634_1_, double p_70634_3_, double p_70634_5_) {
      this.field_71135_a.func_147364_a(p_70634_1_, p_70634_3_, p_70634_5_, this.field_70177_z, this.field_70125_A);
   }

   public void func_71009_b(Entity p_71009_1_) {
      this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(p_71009_1_, 4));
   }

   public void func_71047_c(Entity p_71047_1_) {
      this.func_71121_q().func_73039_n().func_151248_b(this, new SPacketAnimation(p_71047_1_, 5));
   }

   public void func_71016_p() {
      if (this.field_71135_a != null) {
         this.field_71135_a.func_147359_a(new SPacketPlayerAbilities(this.field_71075_bZ));
         this.func_175135_B();
      }
   }

   public WorldServer func_71121_q() {
      return (WorldServer)this.field_70170_p;
   }

   public void func_71033_a(GameType p_71033_1_) {
      this.field_71134_c.func_73076_a(p_71033_1_);
      this.field_71135_a.func_147359_a(new SPacketChangeGameState(3, (float)p_71033_1_.func_77148_a()));
      if (p_71033_1_ == GameType.SPECTATOR) {
         this.func_192030_dh();
         this.func_184210_p();
      } else {
         this.func_175399_e(this);
      }

      this.func_71016_p();
      this.func_175136_bO();
   }

   public boolean func_175149_v() {
      return this.field_71134_c.func_73081_b() == GameType.SPECTATOR;
   }

   public boolean func_184812_l_() {
      return this.field_71134_c.func_73081_b() == GameType.CREATIVE;
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
      this.field_71135_a.func_147359_a(new SPacketChat(p_145747_1_));
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      if ("seed".equals(p_70003_2_) && !this.field_71133_b.func_71262_S()) {
         return true;
      } else if (!"tell".equals(p_70003_2_) && !"help".equals(p_70003_2_) && !"me".equals(p_70003_2_) && !"trigger".equals(p_70003_2_)) {
         if (this.field_71133_b.func_184103_al().func_152596_g(this.func_146103_bH())) {
            UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.field_71133_b.func_184103_al().func_152603_m().func_152683_b(this.func_146103_bH());
            if (userlistopsentry != null) {
               return userlistopsentry.func_152644_a() >= p_70003_1_;
            } else {
               return this.field_71133_b.func_110455_j() >= p_70003_1_;
            }
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public String func_71114_r() {
      String s = this.field_71135_a.field_147371_a.func_74430_c().toString();
      s = s.substring(s.indexOf("/") + 1);
      s = s.substring(0, s.indexOf(":"));
      return s;
   }

   public void func_147100_a(CPacketClientSettings p_147100_1_) {
      this.field_71148_cg = p_147100_1_.func_149524_c();
      this.field_71143_cn = p_147100_1_.func_149523_e();
      this.field_71140_co = p_147100_1_.func_149520_f();
      this.func_184212_Q().func_187227_b(field_184827_bp, Byte.valueOf((byte)p_147100_1_.func_149521_d()));
      this.func_184212_Q().func_187227_b(field_184828_bq, Byte.valueOf((byte)(p_147100_1_.func_186991_f() == EnumHandSide.LEFT ? 0 : 1)));
   }

   public EntityPlayer.EnumChatVisibility func_147096_v() {
      return this.field_71143_cn;
   }

   public void func_175397_a(String p_175397_1_, String p_175397_2_) {
      this.field_71135_a.func_147359_a(new SPacketResourcePackSend(p_175397_1_, p_175397_2_));
   }

   public BlockPos func_180425_c() {
      return new BlockPos(this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v);
   }

   public void func_143004_u() {
      this.field_143005_bX = MinecraftServer.func_130071_aq();
   }

   public StatisticsManagerServer func_147099_x() {
      return this.field_147103_bO;
   }

   public RecipeBookServer func_192037_E() {
      return this.field_192041_cq;
   }

   public void func_152339_d(Entity p_152339_1_) {
      if (p_152339_1_ instanceof EntityPlayer) {
         this.field_71135_a.func_147359_a(new SPacketDestroyEntities(new int[]{p_152339_1_.func_145782_y()}));
      } else {
         this.field_71130_g.add(Integer.valueOf(p_152339_1_.func_145782_y()));
      }

   }

   public void func_184848_d(Entity p_184848_1_) {
      this.field_71130_g.remove(Integer.valueOf(p_184848_1_.func_145782_y()));
   }

   protected void func_175135_B() {
      if (this.func_175149_v()) {
         this.func_175133_bi();
         this.func_82142_c(true);
      } else {
         super.func_175135_B();
      }

      this.func_71121_q().func_73039_n().func_180245_a(this);
   }

   public Entity func_175398_C() {
      return (Entity)(this.field_175401_bS == null ? this : this.field_175401_bS);
   }

   public void func_175399_e(Entity p_175399_1_) {
      Entity entity = this.func_175398_C();
      this.field_175401_bS = (Entity)(p_175399_1_ == null ? this : p_175399_1_);
      if (entity != this.field_175401_bS) {
         this.field_71135_a.func_147359_a(new SPacketCamera(this.field_175401_bS));
         this.func_70634_a(this.field_175401_bS.field_70165_t, this.field_175401_bS.field_70163_u, this.field_175401_bS.field_70161_v);
      }

   }

   protected void func_184173_H() {
      if (this.field_71088_bW > 0 && !this.field_184851_cj) {
         --this.field_71088_bW;
      }

   }

   public void func_71059_n(Entity p_71059_1_) {
      if (this.field_71134_c.func_73081_b() == GameType.SPECTATOR) {
         this.func_175399_e(p_71059_1_);
      } else {
         super.func_71059_n(p_71059_1_);
      }

   }

   public long func_154331_x() {
      return this.field_143005_bX;
   }

   @Nullable
   public ITextComponent func_175396_E() {
      return null;
   }

   public void func_184609_a(EnumHand p_184609_1_) {
      super.func_184609_a(p_184609_1_);
      this.func_184821_cY();
   }

   public boolean func_184850_K() {
      return this.field_184851_cj;
   }

   public void func_184846_L() {
      this.field_184851_cj = false;
   }

   public void func_184847_M() {
      this.func_70052_a(7, true);
   }

   public void func_189103_N() {
      this.func_70052_a(7, true);
      this.func_70052_a(7, false);
   }

   public PlayerAdvancements func_192039_O() {
      return this.field_192042_bX;
   }

   @Nullable
   public Vec3d func_193106_Q() {
      return this.field_193110_cw;
   }
}
