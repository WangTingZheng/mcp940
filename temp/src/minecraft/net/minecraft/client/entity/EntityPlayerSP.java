package net.minecraft.client.entity;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ElytraSound;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditCommandBlockMinecart;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiEditStructure;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class EntityPlayerSP extends AbstractClientPlayer {
   public final NetHandlerPlayClient field_71174_a;
   private final StatisticsManager field_146108_bO;
   private final RecipeBook field_192036_cb;
   private int field_184845_bX = 0;
   private double field_175172_bI;
   private double field_175166_bJ;
   private double field_175167_bK;
   private float field_175164_bL;
   private float field_175165_bM;
   private boolean field_184841_cd;
   private boolean field_175170_bN;
   private boolean field_175171_bO;
   private int field_175168_bP;
   private boolean field_175169_bQ;
   private String field_142022_ce;
   public MovementInput field_71158_b;
   protected Minecraft field_71159_c;
   protected int field_71156_d;
   public int field_71157_e;
   public float field_71154_f;
   public float field_71155_g;
   public float field_71163_h;
   public float field_71164_i;
   private int field_110320_a;
   private float field_110321_bQ;
   public float field_71086_bY;
   public float field_71080_cy;
   private boolean field_184842_cm;
   private EnumHand field_184843_cn;
   private boolean field_184844_co;
   private boolean field_189811_cr = true;
   private int field_189812_cs;
   private boolean field_189813_ct;

   public EntityPlayerSP(Minecraft p_i47378_1_, World p_i47378_2_, NetHandlerPlayClient p_i47378_3_, StatisticsManager p_i47378_4_, RecipeBook p_i47378_5_) {
      super(p_i47378_2_, p_i47378_3_.func_175105_e());
      this.field_71174_a = p_i47378_3_;
      this.field_146108_bO = p_i47378_4_;
      this.field_192036_cb = p_i47378_5_;
      this.field_71159_c = p_i47378_1_;
      this.field_71093_bK = 0;
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      return false;
   }

   public void func_70691_i(float p_70691_1_) {
   }

   public boolean func_184205_a(Entity p_184205_1_, boolean p_184205_2_) {
      if (!super.func_184205_a(p_184205_1_, p_184205_2_)) {
         return false;
      } else {
         if (p_184205_1_ instanceof EntityMinecart) {
            this.field_71159_c.func_147118_V().func_147682_a(new MovingSoundMinecartRiding(this, (EntityMinecart)p_184205_1_));
         }

         if (p_184205_1_ instanceof EntityBoat) {
            this.field_70126_B = p_184205_1_.field_70177_z;
            this.field_70177_z = p_184205_1_.field_70177_z;
            this.func_70034_d(p_184205_1_.field_70177_z);
         }

         return true;
      }
   }

   public void func_184210_p() {
      super.func_184210_p();
      this.field_184844_co = false;
   }

   public Vec3d func_70676_i(float p_70676_1_) {
      return this.func_174806_f(this.field_70125_A, this.field_70177_z);
   }

   public void func_70071_h_() {
      if (this.field_70170_p.func_175667_e(new BlockPos(this.field_70165_t, 0.0D, this.field_70161_v))) {
         super.func_70071_h_();
         if (this.func_184218_aH()) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(this.field_70177_z, this.field_70125_A, this.field_70122_E));
            this.field_71174_a.func_147297_a(new CPacketInput(this.field_70702_br, this.field_191988_bg, this.field_71158_b.field_78901_c, this.field_71158_b.field_78899_d));
            Entity entity = this.func_184208_bv();
            if (entity != this && entity.func_184186_bw()) {
               this.field_71174_a.func_147297_a(new CPacketVehicleMove(entity));
            }
         } else {
            this.func_175161_p();
         }

      }
   }

   private void func_175161_p() {
      boolean flag = this.func_70051_ag();
      if (flag != this.field_175171_bO) {
         if (flag) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SPRINTING));
         } else {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SPRINTING));
         }

         this.field_175171_bO = flag;
      }

      boolean flag1 = this.func_70093_af();
      if (flag1 != this.field_175170_bN) {
         if (flag1) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING));
         } else {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING));
         }

         this.field_175170_bN = flag1;
      }

      if (this.func_175160_A()) {
         AxisAlignedBB axisalignedbb = this.func_174813_aQ();
         double d0 = this.field_70165_t - this.field_175172_bI;
         double d1 = axisalignedbb.field_72338_b - this.field_175166_bJ;
         double d2 = this.field_70161_v - this.field_175167_bK;
         double d3 = (double)(this.field_70177_z - this.field_175164_bL);
         double d4 = (double)(this.field_70125_A - this.field_175165_bM);
         ++this.field_175168_bP;
         boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.field_175168_bP >= 20;
         boolean flag3 = d3 != 0.0D || d4 != 0.0D;
         if (this.func_184218_aH()) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(this.field_70159_w, -999.0D, this.field_70179_y, this.field_70177_z, this.field_70125_A, this.field_70122_E));
            flag2 = false;
         } else if (flag2 && flag3) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(this.field_70165_t, axisalignedbb.field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E));
         } else if (flag2) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Position(this.field_70165_t, axisalignedbb.field_72338_b, this.field_70161_v, this.field_70122_E));
         } else if (flag3) {
            this.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(this.field_70177_z, this.field_70125_A, this.field_70122_E));
         } else if (this.field_184841_cd != this.field_70122_E) {
            this.field_71174_a.func_147297_a(new CPacketPlayer(this.field_70122_E));
         }

         if (flag2) {
            this.field_175172_bI = this.field_70165_t;
            this.field_175166_bJ = axisalignedbb.field_72338_b;
            this.field_175167_bK = this.field_70161_v;
            this.field_175168_bP = 0;
         }

         if (flag3) {
            this.field_175164_bL = this.field_70177_z;
            this.field_175165_bM = this.field_70125_A;
         }

         this.field_184841_cd = this.field_70122_E;
         this.field_189811_cr = this.field_71159_c.field_71474_y.field_189989_R;
      }

   }

   @Nullable
   public EntityItem func_71040_bB(boolean p_71040_1_) {
      CPacketPlayerDigging.Action cpacketplayerdigging$action = p_71040_1_ ? CPacketPlayerDigging.Action.DROP_ALL_ITEMS : CPacketPlayerDigging.Action.DROP_ITEM;
      this.field_71174_a.func_147297_a(new CPacketPlayerDigging(cpacketplayerdigging$action, BlockPos.field_177992_a, EnumFacing.DOWN));
      return null;
   }

   protected ItemStack func_184816_a(EntityItem p_184816_1_) {
      return ItemStack.field_190927_a;
   }

   public void func_71165_d(String p_71165_1_) {
      this.field_71174_a.func_147297_a(new CPacketChatMessage(p_71165_1_));
   }

   public void func_184609_a(EnumHand p_184609_1_) {
      super.func_184609_a(p_184609_1_);
      this.field_71174_a.func_147297_a(new CPacketAnimation(p_184609_1_));
   }

   public void func_71004_bE() {
      this.field_71174_a.func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
   }

   protected void func_70665_d(DamageSource p_70665_1_, float p_70665_2_) {
      if (!this.func_180431_b(p_70665_1_)) {
         this.func_70606_j(this.func_110143_aJ() - p_70665_2_);
      }
   }

   public void func_71053_j() {
      this.field_71174_a.func_147297_a(new CPacketCloseWindow(this.field_71070_bA.field_75152_c));
      this.func_175159_q();
   }

   public void func_175159_q() {
      this.field_71071_by.func_70437_b(ItemStack.field_190927_a);
      super.func_71053_j();
      this.field_71159_c.func_147108_a((GuiScreen)null);
   }

   public void func_71150_b(float p_71150_1_) {
      if (this.field_175169_bQ) {
         float f = this.func_110143_aJ() - p_71150_1_;
         if (f <= 0.0F) {
            this.func_70606_j(p_71150_1_);
            if (f < 0.0F) {
               this.field_70172_ad = this.field_70771_an / 2;
            }
         } else {
            this.field_110153_bc = f;
            this.func_70606_j(this.func_110143_aJ());
            this.field_70172_ad = this.field_70771_an;
            this.func_70665_d(DamageSource.field_76377_j, f);
            this.field_70738_aO = 10;
            this.field_70737_aN = this.field_70738_aO;
         }
      } else {
         this.func_70606_j(p_71150_1_);
         this.field_175169_bQ = true;
      }

   }

   public void func_71064_a(StatBase p_71064_1_, int p_71064_2_) {
      if (p_71064_1_ != null) {
         if (p_71064_1_.field_75972_f) {
            super.func_71064_a(p_71064_1_, p_71064_2_);
         }

      }
   }

   public void func_71016_p() {
      this.field_71174_a.func_147297_a(new CPacketPlayerAbilities(this.field_71075_bZ));
   }

   public boolean func_175144_cb() {
      return true;
   }

   protected void func_110318_g() {
      this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_RIDING_JUMP, MathHelper.func_76141_d(this.func_110319_bJ() * 100.0F)));
   }

   public void func_175163_u() {
      this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.OPEN_INVENTORY));
   }

   public void func_175158_f(String p_175158_1_) {
      this.field_142022_ce = p_175158_1_;
   }

   public String func_142021_k() {
      return this.field_142022_ce;
   }

   public StatisticsManager func_146107_m() {
      return this.field_146108_bO;
   }

   public RecipeBook func_192035_E() {
      return this.field_192036_cb;
   }

   public void func_193103_a(IRecipe p_193103_1_) {
      if (this.field_192036_cb.func_194076_e(p_193103_1_)) {
         this.field_192036_cb.func_194074_f(p_193103_1_);
         this.field_71174_a.func_147297_a(new CPacketRecipeInfo(p_193103_1_));
      }

   }

   public int func_184840_I() {
      return this.field_184845_bX;
   }

   public void func_184839_n(int p_184839_1_) {
      this.field_184845_bX = p_184839_1_;
   }

   public void func_146105_b(ITextComponent p_146105_1_, boolean p_146105_2_) {
      if (p_146105_2_) {
         this.field_71159_c.field_71456_v.func_175188_a(p_146105_1_, false);
      } else {
         this.field_71159_c.field_71456_v.func_146158_b().func_146227_a(p_146105_1_);
      }

   }

   protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_) {
      if (this.field_70145_X) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos(p_145771_1_, p_145771_3_, p_145771_5_);
         double d0 = p_145771_1_ - (double)blockpos.func_177958_n();
         double d1 = p_145771_5_ - (double)blockpos.func_177952_p();
         if (!this.func_175162_d(blockpos)) {
            int i = -1;
            double d2 = 9999.0D;
            if (this.func_175162_d(blockpos.func_177976_e()) && d0 < d2) {
               d2 = d0;
               i = 0;
            }

            if (this.func_175162_d(blockpos.func_177974_f()) && 1.0D - d0 < d2) {
               d2 = 1.0D - d0;
               i = 1;
            }

            if (this.func_175162_d(blockpos.func_177978_c()) && d1 < d2) {
               d2 = d1;
               i = 4;
            }

            if (this.func_175162_d(blockpos.func_177968_d()) && 1.0D - d1 < d2) {
               d2 = 1.0D - d1;
               i = 5;
            }

            float f = 0.1F;
            if (i == 0) {
               this.field_70159_w = -0.10000000149011612D;
            }

            if (i == 1) {
               this.field_70159_w = 0.10000000149011612D;
            }

            if (i == 4) {
               this.field_70179_y = -0.10000000149011612D;
            }

            if (i == 5) {
               this.field_70179_y = 0.10000000149011612D;
            }
         }

         return false;
      }
   }

   private boolean func_175162_d(BlockPos p_175162_1_) {
      return !this.field_70170_p.func_180495_p(p_175162_1_).func_185915_l() && !this.field_70170_p.func_180495_p(p_175162_1_.func_177984_a()).func_185915_l();
   }

   public void func_70031_b(boolean p_70031_1_) {
      super.func_70031_b(p_70031_1_);
      this.field_71157_e = 0;
   }

   public void func_71152_a(float p_71152_1_, int p_71152_2_, int p_71152_3_) {
      this.field_71106_cc = p_71152_1_;
      this.field_71067_cb = p_71152_2_;
      this.field_71068_ca = p_71152_3_;
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
      this.field_71159_c.field_71456_v.func_146158_b().func_146227_a(p_145747_1_);
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return p_70003_1_ <= this.func_184840_I();
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ >= 24 && p_70103_1_ <= 28) {
         this.func_184839_n(p_70103_1_ - 24);
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public BlockPos func_180425_c() {
      return new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D);
   }

   public void func_184185_a(SoundEvent p_184185_1_, float p_184185_2_, float p_184185_3_) {
      this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, p_184185_1_, this.func_184176_by(), p_184185_2_, p_184185_3_, false);
   }

   public boolean func_70613_aW() {
      return true;
   }

   public void func_184598_c(EnumHand p_184598_1_) {
      ItemStack itemstack = this.func_184586_b(p_184598_1_);
      if (!itemstack.func_190926_b() && !this.func_184587_cr()) {
         super.func_184598_c(p_184598_1_);
         this.field_184842_cm = true;
         this.field_184843_cn = p_184598_1_;
      }
   }

   public boolean func_184587_cr() {
      return this.field_184842_cm;
   }

   public void func_184602_cy() {
      super.func_184602_cy();
      this.field_184842_cm = false;
   }

   public EnumHand func_184600_cs() {
      return this.field_184843_cn;
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      super.func_184206_a(p_184206_1_);
      if (field_184621_as.equals(p_184206_1_)) {
         boolean flag = (((Byte)this.field_70180_af.func_187225_a(field_184621_as)).byteValue() & 1) > 0;
         EnumHand enumhand = (((Byte)this.field_70180_af.func_187225_a(field_184621_as)).byteValue() & 2) > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
         if (flag && !this.field_184842_cm) {
            this.func_184598_c(enumhand);
         } else if (!flag && this.field_184842_cm) {
            this.func_184602_cy();
         }
      }

      if (field_184240_ax.equals(p_184206_1_) && this.func_184613_cA() && !this.field_189813_ct) {
         this.field_71159_c.func_147118_V().func_147682_a(new ElytraSound(this));
      }

   }

   public boolean func_110317_t() {
      Entity entity = this.func_184187_bx();
      return this.func_184218_aH() && entity instanceof IJumpingMount && ((IJumpingMount)entity).func_184776_b();
   }

   public float func_110319_bJ() {
      return this.field_110321_bQ;
   }

   public void func_175141_a(TileEntitySign p_175141_1_) {
      this.field_71159_c.func_147108_a(new GuiEditSign(p_175141_1_));
   }

   public void func_184809_a(CommandBlockBaseLogic p_184809_1_) {
      this.field_71159_c.func_147108_a(new GuiEditCommandBlockMinecart(p_184809_1_));
   }

   public void func_184824_a(TileEntityCommandBlock p_184824_1_) {
      this.field_71159_c.func_147108_a(new GuiCommandBlock(p_184824_1_));
   }

   public void func_189807_a(TileEntityStructure p_189807_1_) {
      this.field_71159_c.func_147108_a(new GuiEditStructure(p_189807_1_));
   }

   public void func_184814_a(ItemStack p_184814_1_, EnumHand p_184814_2_) {
      Item item = p_184814_1_.func_77973_b();
      if (item == Items.field_151099_bA) {
         this.field_71159_c.func_147108_a(new GuiScreenBook(this, p_184814_1_, true));
      }

   }

   public void func_71007_a(IInventory p_71007_1_) {
      String s = p_71007_1_ instanceof IInteractionObject ? ((IInteractionObject)p_71007_1_).func_174875_k() : "minecraft:container";
      if ("minecraft:chest".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiChest(this.field_71071_by, p_71007_1_));
      } else if ("minecraft:hopper".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiHopper(this.field_71071_by, p_71007_1_));
      } else if ("minecraft:furnace".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiFurnace(this.field_71071_by, p_71007_1_));
      } else if ("minecraft:brewing_stand".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiBrewingStand(this.field_71071_by, p_71007_1_));
      } else if ("minecraft:beacon".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiBeacon(this.field_71071_by, p_71007_1_));
      } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
         if ("minecraft:shulker_box".equals(s)) {
            this.field_71159_c.func_147108_a(new GuiShulkerBox(this.field_71071_by, p_71007_1_));
         } else {
            this.field_71159_c.func_147108_a(new GuiChest(this.field_71071_by, p_71007_1_));
         }
      } else {
         this.field_71159_c.func_147108_a(new GuiDispenser(this.field_71071_by, p_71007_1_));
      }

   }

   public void func_184826_a(AbstractHorse p_184826_1_, IInventory p_184826_2_) {
      this.field_71159_c.func_147108_a(new GuiScreenHorseInventory(this.field_71071_by, p_184826_2_, p_184826_1_));
   }

   public void func_180468_a(IInteractionObject p_180468_1_) {
      String s = p_180468_1_.func_174875_k();
      if ("minecraft:crafting_table".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiCrafting(this.field_71071_by, this.field_70170_p));
      } else if ("minecraft:enchanting_table".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiEnchantment(this.field_71071_by, this.field_70170_p, p_180468_1_));
      } else if ("minecraft:anvil".equals(s)) {
         this.field_71159_c.func_147108_a(new GuiRepair(this.field_71071_by, this.field_70170_p));
      }

   }

   public void func_180472_a(IMerchant p_180472_1_) {
      this.field_71159_c.func_147108_a(new GuiMerchant(this.field_71071_by, p_180472_1_, this.field_70170_p));
   }

   public void func_71009_b(Entity p_71009_1_) {
      this.field_71159_c.field_71452_i.func_178926_a(p_71009_1_, EnumParticleTypes.CRIT);
   }

   public void func_71047_c(Entity p_71047_1_) {
      this.field_71159_c.field_71452_i.func_178926_a(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
   }

   public boolean func_70093_af() {
      boolean flag = this.field_71158_b != null && this.field_71158_b.field_78899_d;
      return flag && !this.field_71083_bS;
   }

   public void func_70626_be() {
      super.func_70626_be();
      if (this.func_175160_A()) {
         this.field_70702_br = this.field_71158_b.field_78902_a;
         this.field_191988_bg = this.field_71158_b.field_192832_b;
         this.field_70703_bu = this.field_71158_b.field_78901_c;
         this.field_71163_h = this.field_71154_f;
         this.field_71164_i = this.field_71155_g;
         this.field_71155_g = (float)((double)this.field_71155_g + (double)(this.field_70125_A - this.field_71155_g) * 0.5D);
         this.field_71154_f = (float)((double)this.field_71154_f + (double)(this.field_70177_z - this.field_71154_f) * 0.5D);
      }

   }

   protected boolean func_175160_A() {
      return this.field_71159_c.func_175606_aa() == this;
   }

   public void func_70636_d() {
      ++this.field_71157_e;
      if (this.field_71156_d > 0) {
         --this.field_71156_d;
      }

      this.field_71080_cy = this.field_71086_bY;
      if (this.field_71087_bX) {
         if (this.field_71159_c.field_71462_r != null && !this.field_71159_c.field_71462_r.func_73868_f()) {
            this.field_71159_c.func_147108_a((GuiScreen)null);
         }

         if (this.field_71086_bY == 0.0F) {
            this.field_71159_c.func_147118_V().func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187814_ei, this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
         }

         this.field_71086_bY += 0.0125F;
         if (this.field_71086_bY >= 1.0F) {
            this.field_71086_bY = 1.0F;
         }

         this.field_71087_bX = false;
      } else if (this.func_70644_a(MobEffects.field_76431_k) && this.func_70660_b(MobEffects.field_76431_k).func_76459_b() > 60) {
         this.field_71086_bY += 0.006666667F;
         if (this.field_71086_bY > 1.0F) {
            this.field_71086_bY = 1.0F;
         }
      } else {
         if (this.field_71086_bY > 0.0F) {
            this.field_71086_bY -= 0.05F;
         }

         if (this.field_71086_bY < 0.0F) {
            this.field_71086_bY = 0.0F;
         }
      }

      if (this.field_71088_bW > 0) {
         --this.field_71088_bW;
      }

      boolean flag = this.field_71158_b.field_78901_c;
      boolean flag1 = this.field_71158_b.field_78899_d;
      float f = 0.8F;
      boolean flag2 = this.field_71158_b.field_192832_b >= 0.8F;
      this.field_71158_b.func_78898_a();
      this.field_71159_c.func_193032_ao().func_193293_a(this.field_71158_b);
      if (this.func_184587_cr() && !this.func_184218_aH()) {
         this.field_71158_b.field_78902_a *= 0.2F;
         this.field_71158_b.field_192832_b *= 0.2F;
         this.field_71156_d = 0;
      }

      boolean flag3 = false;
      if (this.field_189812_cs > 0) {
         --this.field_189812_cs;
         flag3 = true;
         this.field_71158_b.field_78901_c = true;
      }

      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35D, axisalignedbb.field_72338_b + 0.5D, this.field_70161_v + (double)this.field_70130_N * 0.35D);
      this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35D, axisalignedbb.field_72338_b + 0.5D, this.field_70161_v - (double)this.field_70130_N * 0.35D);
      this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35D, axisalignedbb.field_72338_b + 0.5D, this.field_70161_v - (double)this.field_70130_N * 0.35D);
      this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35D, axisalignedbb.field_72338_b + 0.5D, this.field_70161_v + (double)this.field_70130_N * 0.35D);
      boolean flag4 = (float)this.func_71024_bL().func_75116_a() > 6.0F || this.field_71075_bZ.field_75101_c;
      if (this.field_70122_E && !flag1 && !flag2 && this.field_71158_b.field_192832_b >= 0.8F && !this.func_70051_ag() && flag4 && !this.func_184587_cr() && !this.func_70644_a(MobEffects.field_76440_q)) {
         if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
            this.field_71156_d = 7;
         } else {
            this.func_70031_b(true);
         }
      }

      if (!this.func_70051_ag() && this.field_71158_b.field_192832_b >= 0.8F && flag4 && !this.func_184587_cr() && !this.func_70644_a(MobEffects.field_76440_q) && this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
         this.func_70031_b(true);
      }

      if (this.func_70051_ag() && (this.field_71158_b.field_192832_b < 0.8F || this.field_70123_F || !flag4)) {
         this.func_70031_b(false);
      }

      if (this.field_71075_bZ.field_75101_c) {
         if (this.field_71159_c.field_71442_b.func_178887_k()) {
            if (!this.field_71075_bZ.field_75100_b) {
               this.field_71075_bZ.field_75100_b = true;
               this.func_71016_p();
            }
         } else if (!flag && this.field_71158_b.field_78901_c && !flag3) {
            if (this.field_71101_bC == 0) {
               this.field_71101_bC = 7;
            } else {
               this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
               this.func_71016_p();
               this.field_71101_bC = 0;
            }
         }
      }

      if (this.field_71158_b.field_78901_c && !flag && !this.field_70122_E && this.field_70181_x < 0.0D && !this.func_184613_cA() && !this.field_71075_bZ.field_75100_b) {
         ItemStack itemstack = this.func_184582_a(EntityEquipmentSlot.CHEST);
         if (itemstack.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemstack)) {
            this.field_71174_a.func_147297_a(new CPacketEntityAction(this, CPacketEntityAction.Action.START_FALL_FLYING));
         }
      }

      this.field_189813_ct = this.func_184613_cA();
      if (this.field_71075_bZ.field_75100_b && this.func_175160_A()) {
         if (this.field_71158_b.field_78899_d) {
            this.field_71158_b.field_78902_a = (float)((double)this.field_71158_b.field_78902_a / 0.3D);
            this.field_71158_b.field_192832_b = (float)((double)this.field_71158_b.field_192832_b / 0.3D);
            this.field_70181_x -= (double)(this.field_71075_bZ.func_75093_a() * 3.0F);
         }

         if (this.field_71158_b.field_78901_c) {
            this.field_70181_x += (double)(this.field_71075_bZ.func_75093_a() * 3.0F);
         }
      }

      if (this.func_110317_t()) {
         IJumpingMount ijumpingmount = (IJumpingMount)this.func_184187_bx();
         if (this.field_110320_a < 0) {
            ++this.field_110320_a;
            if (this.field_110320_a == 0) {
               this.field_110321_bQ = 0.0F;
            }
         }

         if (flag && !this.field_71158_b.field_78901_c) {
            this.field_110320_a = -10;
            ijumpingmount.func_110206_u(MathHelper.func_76141_d(this.func_110319_bJ() * 100.0F));
            this.func_110318_g();
         } else if (!flag && this.field_71158_b.field_78901_c) {
            this.field_110320_a = 0;
            this.field_110321_bQ = 0.0F;
         } else if (flag) {
            ++this.field_110320_a;
            if (this.field_110320_a < 10) {
               this.field_110321_bQ = (float)this.field_110320_a * 0.1F;
            } else {
               this.field_110321_bQ = 0.8F + 2.0F / (float)(this.field_110320_a - 9) * 0.1F;
            }
         }
      } else {
         this.field_110321_bQ = 0.0F;
      }

      super.func_70636_d();
      if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
         this.field_71075_bZ.field_75100_b = false;
         this.func_71016_p();
      }

   }

   public void func_70098_U() {
      super.func_70098_U();
      this.field_184844_co = false;
      if (this.func_184187_bx() instanceof EntityBoat) {
         EntityBoat entityboat = (EntityBoat)this.func_184187_bx();
         entityboat.func_184442_a(this.field_71158_b.field_187257_e, this.field_71158_b.field_187258_f, this.field_71158_b.field_187255_c, this.field_71158_b.field_187256_d);
         this.field_184844_co |= this.field_71158_b.field_187257_e || this.field_71158_b.field_187258_f || this.field_71158_b.field_187255_c || this.field_71158_b.field_187256_d;
      }

   }

   public boolean func_184838_M() {
      return this.field_184844_co;
   }

   @Nullable
   public PotionEffect func_184596_c(@Nullable Potion p_184596_1_) {
      if (p_184596_1_ == MobEffects.field_76431_k) {
         this.field_71080_cy = 0.0F;
         this.field_71086_bY = 0.0F;
      }

      return super.func_184596_c(p_184596_1_);
   }

   public void func_70091_d(MoverType p_70091_1_, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
      double d0 = this.field_70165_t;
      double d1 = this.field_70161_v;
      super.func_70091_d(p_70091_1_, p_70091_2_, p_70091_4_, p_70091_6_);
      this.func_189810_i((float)(this.field_70165_t - d0), (float)(this.field_70161_v - d1));
   }

   public boolean func_189809_N() {
      return this.field_189811_cr;
   }

   protected void func_189810_i(float p_189810_1_, float p_189810_2_) {
      if (this.func_189809_N()) {
         if (this.field_189812_cs <= 0 && this.field_70122_E && !this.func_70093_af() && !this.func_184218_aH()) {
            Vec2f vec2f = this.field_71158_b.func_190020_b();
            if (vec2f.field_189982_i != 0.0F || vec2f.field_189983_j != 0.0F) {
               Vec3d vec3d = new Vec3d(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
               double d0 = this.field_70165_t + (double)p_189810_1_;
               double d1 = this.field_70161_v + (double)p_189810_2_;
               Vec3d vec3d1 = new Vec3d(d0, this.func_174813_aQ().field_72338_b, d1);
               Vec3d vec3d2 = new Vec3d((double)p_189810_1_, 0.0D, (double)p_189810_2_);
               float f = this.func_70689_ay();
               float f1 = (float)vec3d2.func_189985_c();
               if (f1 <= 0.001F) {
                  float f2 = f * vec2f.field_189982_i;
                  float f3 = f * vec2f.field_189983_j;
                  float f4 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
                  float f5 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
                  vec3d2 = new Vec3d((double)(f2 * f5 - f3 * f4), vec3d2.field_72448_b, (double)(f3 * f5 + f2 * f4));
                  f1 = (float)vec3d2.func_189985_c();
                  if (f1 <= 0.001F) {
                     return;
                  }
               }

               float f12 = (float)MathHelper.func_181161_i((double)f1);
               Vec3d vec3d12 = vec3d2.func_186678_a((double)f12);
               Vec3d vec3d13 = this.func_189651_aD();
               float f13 = (float)(vec3d13.field_72450_a * vec3d12.field_72450_a + vec3d13.field_72449_c * vec3d12.field_72449_c);
               if (f13 >= -0.15F) {
                  BlockPos blockpos = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72337_e, this.field_70161_v);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                  if (iblockstate.func_185890_d(this.field_70170_p, blockpos) == null) {
                     blockpos = blockpos.func_177984_a();
                     IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos);
                     if (iblockstate1.func_185890_d(this.field_70170_p, blockpos) == null) {
                        float f6 = 7.0F;
                        float f7 = 1.2F;
                        if (this.func_70644_a(MobEffects.field_76430_j)) {
                           f7 += (float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.75F;
                        }

                        float f8 = Math.max(f * 7.0F, 1.0F / f12);
                        Vec3d vec3d4 = vec3d1.func_178787_e(vec3d12.func_186678_a((double)f8));
                        float f9 = this.field_70130_N;
                        float f10 = this.field_70131_O;
                        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(vec3d, vec3d4.func_72441_c(0.0D, (double)f10, 0.0D))).func_72314_b((double)f9, 0.0D, (double)f9);
                        Vec3d lvt_19_1_ = vec3d.func_72441_c(0.0D, 0.5099999904632568D, 0.0D);
                        vec3d4 = vec3d4.func_72441_c(0.0D, 0.5099999904632568D, 0.0D);
                        Vec3d vec3d5 = vec3d12.func_72431_c(new Vec3d(0.0D, 1.0D, 0.0D));
                        Vec3d vec3d6 = vec3d5.func_186678_a((double)(f9 * 0.5F));
                        Vec3d vec3d7 = lvt_19_1_.func_178788_d(vec3d6);
                        Vec3d vec3d8 = vec3d4.func_178788_d(vec3d6);
                        Vec3d vec3d9 = lvt_19_1_.func_178787_e(vec3d6);
                        Vec3d vec3d10 = vec3d4.func_178787_e(vec3d6);
                        List<AxisAlignedBB> list = this.field_70170_p.func_184144_a(this, axisalignedbb);
                        if (!list.isEmpty()) {
                           ;
                        }

                        float f11 = Float.MIN_VALUE;

                        label86:
                        for(AxisAlignedBB axisalignedbb2 : list) {
                           if (axisalignedbb2.func_189973_a(vec3d7, vec3d8) || axisalignedbb2.func_189973_a(vec3d9, vec3d10)) {
                              f11 = (float)axisalignedbb2.field_72337_e;
                              Vec3d vec3d11 = axisalignedbb2.func_189972_c();
                              BlockPos blockpos1 = new BlockPos(vec3d11);
                              int i = 1;

                              while(true) {
                                 if ((float)i >= f7) {
                                    break label86;
                                 }

                                 BlockPos blockpos2 = blockpos1.func_177981_b(i);
                                 IBlockState iblockstate2 = this.field_70170_p.func_180495_p(blockpos2);
                                 AxisAlignedBB axisalignedbb1;
                                 if ((axisalignedbb1 = iblockstate2.func_185890_d(this.field_70170_p, blockpos2)) != null) {
                                    f11 = (float)axisalignedbb1.field_72337_e + (float)blockpos2.func_177956_o();
                                    if ((double)f11 - this.func_174813_aQ().field_72338_b > (double)f7) {
                                       return;
                                    }
                                 }

                                 if (i > 1) {
                                    blockpos = blockpos.func_177984_a();
                                    IBlockState iblockstate3 = this.field_70170_p.func_180495_p(blockpos);
                                    if (iblockstate3.func_185890_d(this.field_70170_p, blockpos) != null) {
                                       return;
                                    }
                                 }

                                 ++i;
                              }
                           }
                        }

                        if (f11 != Float.MIN_VALUE) {
                           float f14 = (float)((double)f11 - this.func_174813_aQ().field_72338_b);
                           if (f14 > 0.5F && f14 <= f7) {
                              this.field_189812_cs = 1;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
