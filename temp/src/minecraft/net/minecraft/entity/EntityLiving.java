package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public abstract class EntityLiving extends EntityLivingBase {
   private static final DataParameter<Byte> field_184654_a = EntityDataManager.<Byte>func_187226_a(EntityLiving.class, DataSerializers.field_187191_a);
   public int field_70757_a;
   protected int field_70728_aV;
   private final EntityLookHelper field_70749_g;
   protected EntityMoveHelper field_70765_h;
   protected EntityJumpHelper field_70767_i;
   private final EntityBodyHelper field_70762_j;
   protected PathNavigate field_70699_by;
   protected final EntityAITasks field_70714_bg;
   protected final EntityAITasks field_70715_bh;
   private EntityLivingBase field_70696_bz;
   private final EntitySenses field_70723_bA;
   private final NonNullList<ItemStack> field_184656_bv = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
   protected float[] field_82174_bp = new float[2];
   private final NonNullList<ItemStack> field_184657_bw = NonNullList.<ItemStack>func_191197_a(4, ItemStack.field_190927_a);
   protected float[] field_184655_bs = new float[4];
   private boolean field_82172_bs;
   private boolean field_82179_bU;
   private final Map<PathNodeType, Float> field_184658_bz = Maps.newEnumMap(PathNodeType.class);
   private ResourceLocation field_184659_bA;
   private long field_184653_bB;
   private boolean field_110169_bv;
   private Entity field_110168_bw;
   private NBTTagCompound field_110170_bx;

   public EntityLiving(World p_i1595_1_) {
      super(p_i1595_1_);
      this.field_70714_bg = new EntityAITasks(p_i1595_1_ != null && p_i1595_1_.field_72984_F != null ? p_i1595_1_.field_72984_F : null);
      this.field_70715_bh = new EntityAITasks(p_i1595_1_ != null && p_i1595_1_.field_72984_F != null ? p_i1595_1_.field_72984_F : null);
      this.field_70749_g = new EntityLookHelper(this);
      this.field_70765_h = new EntityMoveHelper(this);
      this.field_70767_i = new EntityJumpHelper(this);
      this.field_70762_j = this.func_184650_s();
      this.field_70699_by = this.func_175447_b(p_i1595_1_);
      this.field_70723_bA = new EntitySenses(this);
      Arrays.fill(this.field_184655_bs, 0.085F);
      Arrays.fill(this.field_82174_bp, 0.085F);
      if (p_i1595_1_ != null && !p_i1595_1_.field_72995_K) {
         this.func_184651_r();
      }

   }

   protected void func_184651_r() {
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
   }

   protected PathNavigate func_175447_b(World p_175447_1_) {
      return new PathNavigateGround(this, p_175447_1_);
   }

   public float func_184643_a(PathNodeType p_184643_1_) {
      Float f = this.field_184658_bz.get(p_184643_1_);
      return f == null ? p_184643_1_.func_186289_a() : f.floatValue();
   }

   public void func_184644_a(PathNodeType p_184644_1_, float p_184644_2_) {
      this.field_184658_bz.put(p_184644_1_, Float.valueOf(p_184644_2_));
   }

   protected EntityBodyHelper func_184650_s() {
      return new EntityBodyHelper(this);
   }

   public EntityLookHelper func_70671_ap() {
      return this.field_70749_g;
   }

   public EntityMoveHelper func_70605_aq() {
      return this.field_70765_h;
   }

   public EntityJumpHelper func_70683_ar() {
      return this.field_70767_i;
   }

   public PathNavigate func_70661_as() {
      return this.field_70699_by;
   }

   public EntitySenses func_70635_at() {
      return this.field_70723_bA;
   }

   @Nullable
   public EntityLivingBase func_70638_az() {
      return this.field_70696_bz;
   }

   public void func_70624_b(@Nullable EntityLivingBase p_70624_1_) {
      this.field_70696_bz = p_70624_1_;
   }

   public boolean func_70686_a(Class<? extends EntityLivingBase> p_70686_1_) {
      return p_70686_1_ != EntityGhast.class;
   }

   public void func_70615_aA() {
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184654_a, Byte.valueOf((byte)0));
   }

   public int func_70627_aG() {
      return 80;
   }

   public void func_70642_aH() {
      SoundEvent soundevent = this.func_184639_G();
      if (soundevent != null) {
         this.func_184185_a(soundevent, this.func_70599_aP(), this.func_70647_i());
      }

   }

   public void func_70030_z() {
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("mobBaseTick");
      if (this.func_70089_S() && this.field_70146_Z.nextInt(1000) < this.field_70757_a++) {
         this.func_175456_n();
         this.func_70642_aH();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_184581_c(DamageSource p_184581_1_) {
      this.func_175456_n();
      super.func_184581_c(p_184581_1_);
   }

   private void func_175456_n() {
      this.field_70757_a = -this.func_70627_aG();
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      if (this.field_70728_aV > 0) {
         int i = this.field_70728_aV;

         for(int j = 0; j < this.field_184657_bw.size(); ++j) {
            if (!((ItemStack)this.field_184657_bw.get(j)).func_190926_b() && this.field_184655_bs[j] <= 1.0F) {
               i += 1 + this.field_70146_Z.nextInt(3);
            }
         }

         for(int k = 0; k < this.field_184656_bv.size(); ++k) {
            if (!((ItemStack)this.field_184656_bv.get(k)).func_190926_b() && this.field_82174_bp[k] <= 1.0F) {
               i += 1 + this.field_70146_Z.nextInt(3);
            }
         }

         return i;
      } else {
         return this.field_70728_aV;
      }
   }

   public void func_70656_aK() {
      if (this.field_70170_p.field_72995_K) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - d0 * 10.0D, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O) - d1 * 10.0D, this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - d2 * 10.0D, d0, d1, d2);
         }
      } else {
         this.field_70170_p.func_72960_a(this, (byte)20);
      }

   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 20) {
         this.func_70656_aK();
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         this.func_110159_bB();
         if (this.field_70173_aa % 5 == 0) {
            boolean flag = !(this.func_184179_bs() instanceof EntityLiving);
            boolean flag1 = !(this.func_184187_bx() instanceof EntityBoat);
            this.field_70714_bg.func_188527_a(1, flag);
            this.field_70714_bg.func_188527_a(4, flag && flag1);
            this.field_70714_bg.func_188527_a(2, flag);
         }
      }

   }

   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
      this.field_70762_j.func_75664_a();
      return p_110146_2_;
   }

   @Nullable
   protected SoundEvent func_184639_G() {
      return null;
   }

   @Nullable
   protected Item func_146068_u() {
      return null;
   }

   protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
      Item item = this.func_146068_u();
      if (item != null) {
         int i = this.field_70146_Z.nextInt(3);
         if (p_70628_2_ > 0) {
            i += this.field_70146_Z.nextInt(p_70628_2_ + 1);
         }

         for(int j = 0; j < i; ++j) {
            this.func_145779_a(item, 1);
         }
      }

   }

   public static void func_189752_a(DataFixer p_189752_0_, Class<?> p_189752_1_) {
      p_189752_0_.func_188258_a(FixTypes.ENTITY, new ItemStackDataLists(p_189752_1_, new String[]{"ArmorItems", "HandItems"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("CanPickUpLoot", this.func_98052_bS());
      p_70014_1_.func_74757_a("PersistenceRequired", this.field_82179_bU);
      NBTTagList nbttaglist = new NBTTagList();

      for(ItemStack itemstack : this.field_184657_bw) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         if (!itemstack.func_190926_b()) {
            itemstack.func_77955_b(nbttagcompound);
         }

         nbttaglist.func_74742_a(nbttagcompound);
      }

      p_70014_1_.func_74782_a("ArmorItems", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(ItemStack itemstack1 : this.field_184656_bv) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         if (!itemstack1.func_190926_b()) {
            itemstack1.func_77955_b(nbttagcompound1);
         }

         nbttaglist1.func_74742_a(nbttagcompound1);
      }

      p_70014_1_.func_74782_a("HandItems", nbttaglist1);
      NBTTagList nbttaglist2 = new NBTTagList();

      for(float f : this.field_184655_bs) {
         nbttaglist2.func_74742_a(new NBTTagFloat(f));
      }

      p_70014_1_.func_74782_a("ArmorDropChances", nbttaglist2);
      NBTTagList nbttaglist3 = new NBTTagList();

      for(float f1 : this.field_82174_bp) {
         nbttaglist3.func_74742_a(new NBTTagFloat(f1));
      }

      p_70014_1_.func_74782_a("HandDropChances", nbttaglist3);
      p_70014_1_.func_74757_a("Leashed", this.field_110169_bv);
      if (this.field_110168_bw != null) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         if (this.field_110168_bw instanceof EntityLivingBase) {
            UUID uuid = this.field_110168_bw.func_110124_au();
            nbttagcompound2.func_186854_a("UUID", uuid);
         } else if (this.field_110168_bw instanceof EntityHanging) {
            BlockPos blockpos = ((EntityHanging)this.field_110168_bw).func_174857_n();
            nbttagcompound2.func_74768_a("X", blockpos.func_177958_n());
            nbttagcompound2.func_74768_a("Y", blockpos.func_177956_o());
            nbttagcompound2.func_74768_a("Z", blockpos.func_177952_p());
         }

         p_70014_1_.func_74782_a("Leash", nbttagcompound2);
      }

      p_70014_1_.func_74757_a("LeftHanded", this.func_184638_cS());
      if (this.field_184659_bA != null) {
         p_70014_1_.func_74778_a("DeathLootTable", this.field_184659_bA.toString());
         if (this.field_184653_bB != 0L) {
            p_70014_1_.func_74772_a("DeathLootTableSeed", this.field_184653_bB);
         }
      }

      if (this.func_175446_cd()) {
         p_70014_1_.func_74757_a("NoAI", this.func_175446_cd());
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("CanPickUpLoot", 1)) {
         this.func_98053_h(p_70037_1_.func_74767_n("CanPickUpLoot"));
      }

      this.field_82179_bU = p_70037_1_.func_74767_n("PersistenceRequired");
      if (p_70037_1_.func_150297_b("ArmorItems", 9)) {
         NBTTagList nbttaglist = p_70037_1_.func_150295_c("ArmorItems", 10);

         for(int i = 0; i < this.field_184657_bw.size(); ++i) {
            this.field_184657_bw.set(i, new ItemStack(nbttaglist.func_150305_b(i)));
         }
      }

      if (p_70037_1_.func_150297_b("HandItems", 9)) {
         NBTTagList nbttaglist1 = p_70037_1_.func_150295_c("HandItems", 10);

         for(int j = 0; j < this.field_184656_bv.size(); ++j) {
            this.field_184656_bv.set(j, new ItemStack(nbttaglist1.func_150305_b(j)));
         }
      }

      if (p_70037_1_.func_150297_b("ArmorDropChances", 9)) {
         NBTTagList nbttaglist2 = p_70037_1_.func_150295_c("ArmorDropChances", 5);

         for(int k = 0; k < nbttaglist2.func_74745_c(); ++k) {
            this.field_184655_bs[k] = nbttaglist2.func_150308_e(k);
         }
      }

      if (p_70037_1_.func_150297_b("HandDropChances", 9)) {
         NBTTagList nbttaglist3 = p_70037_1_.func_150295_c("HandDropChances", 5);

         for(int l = 0; l < nbttaglist3.func_74745_c(); ++l) {
            this.field_82174_bp[l] = nbttaglist3.func_150308_e(l);
         }
      }

      this.field_110169_bv = p_70037_1_.func_74767_n("Leashed");
      if (this.field_110169_bv && p_70037_1_.func_150297_b("Leash", 10)) {
         this.field_110170_bx = p_70037_1_.func_74775_l("Leash");
      }

      this.func_184641_n(p_70037_1_.func_74767_n("LeftHanded"));
      if (p_70037_1_.func_150297_b("DeathLootTable", 8)) {
         this.field_184659_bA = new ResourceLocation(p_70037_1_.func_74779_i("DeathLootTable"));
         this.field_184653_bB = p_70037_1_.func_74763_f("DeathLootTableSeed");
      }

      this.func_94061_f(p_70037_1_.func_74767_n("NoAI"));
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return null;
   }

   protected void func_184610_a(boolean p_184610_1_, int p_184610_2_, DamageSource p_184610_3_) {
      ResourceLocation resourcelocation = this.field_184659_bA;
      if (resourcelocation == null) {
         resourcelocation = this.func_184647_J();
      }

      if (resourcelocation != null) {
         LootTable loottable = this.field_70170_p.func_184146_ak().func_186521_a(resourcelocation);
         this.field_184659_bA = null;
         LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)this.field_70170_p)).func_186472_a(this).func_186473_a(p_184610_3_);
         if (p_184610_1_ && this.field_70717_bb != null) {
            lootcontext$builder = lootcontext$builder.func_186470_a(this.field_70717_bb).func_186469_a(this.field_70717_bb.func_184817_da());
         }

         for(ItemStack itemstack : loottable.func_186462_a(this.field_184653_bB == 0L ? this.field_70146_Z : new Random(this.field_184653_bB), lootcontext$builder.func_186471_a())) {
            this.func_70099_a(itemstack, 0.0F);
         }

         this.func_82160_b(p_184610_1_, p_184610_2_);
      } else {
         super.func_184610_a(p_184610_1_, p_184610_2_, p_184610_3_);
      }

   }

   public void func_191989_p(float p_191989_1_) {
      this.field_191988_bg = p_191989_1_;
   }

   public void func_70657_f(float p_70657_1_) {
      this.field_70701_bs = p_70657_1_;
   }

   public void func_184646_p(float p_184646_1_) {
      this.field_70702_br = p_184646_1_;
   }

   public void func_70659_e(float p_70659_1_) {
      super.func_70659_e(p_70659_1_);
      this.func_191989_p(p_70659_1_);
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.field_70170_p.field_72984_F.func_76320_a("looting");
      if (!this.field_70170_p.field_72995_K && this.func_98052_bS() && !this.field_70729_aU && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
         for(EntityItem entityitem : this.field_70170_p.func_72872_a(EntityItem.class, this.func_174813_aQ().func_72314_b(1.0D, 0.0D, 1.0D))) {
            if (!entityitem.field_70128_L && !entityitem.func_92059_d().func_190926_b() && !entityitem.func_174874_s()) {
               this.func_175445_a(entityitem);
            }
         }
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_175445_a(EntityItem p_175445_1_) {
      ItemStack itemstack = p_175445_1_.func_92059_d();
      EntityEquipmentSlot entityequipmentslot = func_184640_d(itemstack);
      boolean flag = true;
      ItemStack itemstack1 = this.func_184582_a(entityequipmentslot);
      if (!itemstack1.func_190926_b()) {
         if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.HAND) {
            if (itemstack.func_77973_b() instanceof ItemSword && !(itemstack1.func_77973_b() instanceof ItemSword)) {
               flag = true;
            } else if (itemstack.func_77973_b() instanceof ItemSword && itemstack1.func_77973_b() instanceof ItemSword) {
               ItemSword itemsword = (ItemSword)itemstack.func_77973_b();
               ItemSword itemsword1 = (ItemSword)itemstack1.func_77973_b();
               if (itemsword.func_150931_i() == itemsword1.func_150931_i()) {
                  flag = itemstack.func_77960_j() > itemstack1.func_77960_j() || itemstack.func_77942_o() && !itemstack1.func_77942_o();
               } else {
                  flag = itemsword.func_150931_i() > itemsword1.func_150931_i();
               }
            } else if (itemstack.func_77973_b() instanceof ItemBow && itemstack1.func_77973_b() instanceof ItemBow) {
               flag = itemstack.func_77942_o() && !itemstack1.func_77942_o();
            } else {
               flag = false;
            }
         } else if (itemstack.func_77973_b() instanceof ItemArmor && !(itemstack1.func_77973_b() instanceof ItemArmor)) {
            flag = true;
         } else if (itemstack.func_77973_b() instanceof ItemArmor && itemstack1.func_77973_b() instanceof ItemArmor && !EnchantmentHelper.func_190938_b(itemstack1)) {
            ItemArmor itemarmor = (ItemArmor)itemstack.func_77973_b();
            ItemArmor itemarmor1 = (ItemArmor)itemstack1.func_77973_b();
            if (itemarmor.field_77879_b == itemarmor1.field_77879_b) {
               flag = itemstack.func_77960_j() > itemstack1.func_77960_j() || itemstack.func_77942_o() && !itemstack1.func_77942_o();
            } else {
               flag = itemarmor.field_77879_b > itemarmor1.field_77879_b;
            }
         } else {
            flag = false;
         }
      }

      if (flag && this.func_175448_a(itemstack)) {
         double d0;
         switch(entityequipmentslot.func_188453_a()) {
         case HAND:
            d0 = (double)this.field_82174_bp[entityequipmentslot.func_188454_b()];
            break;
         case ARMOR:
            d0 = (double)this.field_184655_bs[entityequipmentslot.func_188454_b()];
            break;
         default:
            d0 = 0.0D;
         }

         if (!itemstack1.func_190926_b() && (double)(this.field_70146_Z.nextFloat() - 0.1F) < d0) {
            this.func_70099_a(itemstack1, 0.0F);
         }

         this.func_184201_a(entityequipmentslot, itemstack);
         switch(entityequipmentslot.func_188453_a()) {
         case HAND:
            this.field_82174_bp[entityequipmentslot.func_188454_b()] = 2.0F;
            break;
         case ARMOR:
            this.field_184655_bs[entityequipmentslot.func_188454_b()] = 2.0F;
         }

         this.field_82179_bU = true;
         this.func_71001_a(p_175445_1_, itemstack.func_190916_E());
         p_175445_1_.func_70106_y();
      }

   }

   protected boolean func_175448_a(ItemStack p_175448_1_) {
      return true;
   }

   protected boolean func_70692_ba() {
      return true;
   }

   protected void func_70623_bb() {
      if (this.field_82179_bU) {
         this.field_70708_bq = 0;
      } else {
         Entity entity = this.field_70170_p.func_72890_a(this, -1.0D);
         if (entity != null) {
            double d0 = entity.field_70165_t - this.field_70165_t;
            double d1 = entity.field_70163_u - this.field_70163_u;
            double d2 = entity.field_70161_v - this.field_70161_v;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (this.func_70692_ba() && d3 > 16384.0D) {
               this.func_70106_y();
            }

            if (this.field_70708_bq > 600 && this.field_70146_Z.nextInt(800) == 0 && d3 > 1024.0D && this.func_70692_ba()) {
               this.func_70106_y();
            } else if (d3 < 1024.0D) {
               this.field_70708_bq = 0;
            }
         }

      }
   }

   protected final void func_70626_be() {
      ++this.field_70708_bq;
      this.field_70170_p.field_72984_F.func_76320_a("checkDespawn");
      this.func_70623_bb();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("sensing");
      this.field_70723_bA.func_75523_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("targetSelector");
      this.field_70715_bh.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("goalSelector");
      this.field_70714_bg.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("navigation");
      this.field_70699_by.func_75501_e();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("mob tick");
      this.func_70619_bc();
      this.field_70170_p.field_72984_F.func_76319_b();
      if (this.func_184218_aH() && this.func_184187_bx() instanceof EntityLiving) {
         EntityLiving entityliving = (EntityLiving)this.func_184187_bx();
         entityliving.func_70661_as().func_75484_a(this.func_70661_as().func_75505_d(), 1.5D);
         entityliving.func_70605_aq().func_188487_a(this.func_70605_aq());
      }

      this.field_70170_p.field_72984_F.func_76320_a("controls");
      this.field_70170_p.field_72984_F.func_76320_a("move");
      this.field_70765_h.func_75641_c();
      this.field_70170_p.field_72984_F.func_76318_c("look");
      this.field_70749_g.func_75649_a();
      this.field_70170_p.field_72984_F.func_76318_c("jump");
      this.field_70767_i.func_75661_b();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_70619_bc() {
   }

   public int func_70646_bf() {
      return 40;
   }

   public int func_184649_cE() {
      return 10;
   }

   public void func_70625_a(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {
      double d0 = p_70625_1_.field_70165_t - this.field_70165_t;
      double d2 = p_70625_1_.field_70161_v - this.field_70161_v;
      double d1;
      if (p_70625_1_ instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)p_70625_1_;
         d1 = entitylivingbase.field_70163_u + (double)entitylivingbase.func_70047_e() - (this.field_70163_u + (double)this.func_70047_e());
      } else {
         d1 = (p_70625_1_.func_174813_aQ().field_72338_b + p_70625_1_.func_174813_aQ().field_72337_e) / 2.0D - (this.field_70163_u + (double)this.func_70047_e());
      }

      double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d2 * d2);
      float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.2957763671875D) - 90.0F;
      float f1 = (float)(-(MathHelper.func_181159_b(d1, d3) * 57.2957763671875D));
      this.field_70125_A = this.func_70663_b(this.field_70125_A, f1, p_70625_3_);
      this.field_70177_z = this.func_70663_b(this.field_70177_z, f, p_70625_2_);
   }

   private float func_70663_b(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
      float f = MathHelper.func_76142_g(p_70663_2_ - p_70663_1_);
      if (f > p_70663_3_) {
         f = p_70663_3_;
      }

      if (f < -p_70663_3_) {
         f = -p_70663_3_;
      }

      return p_70663_1_ + f;
   }

   public boolean func_70601_bi() {
      IBlockState iblockstate = this.field_70170_p.func_180495_p((new BlockPos(this)).func_177977_b());
      return iblockstate.func_189884_a(this);
   }

   public boolean func_70058_J() {
      return !this.field_70170_p.func_72953_d(this.func_174813_aQ()) && this.field_70170_p.func_184144_a(this, this.func_174813_aQ()).isEmpty() && this.field_70170_p.func_72917_a(this.func_174813_aQ(), this);
   }

   public float func_70603_bj() {
      return 1.0F;
   }

   public int func_70641_bl() {
      return 4;
   }

   public int func_82143_as() {
      if (this.func_70638_az() == null) {
         return 3;
      } else {
         int i = (int)(this.func_110143_aJ() - this.func_110138_aP() * 0.33F);
         i = i - (3 - this.field_70170_p.func_175659_aa().func_151525_a()) * 4;
         if (i < 0) {
            i = 0;
         }

         return i + 3;
      }
   }

   public Iterable<ItemStack> func_184214_aD() {
      return this.field_184656_bv;
   }

   public Iterable<ItemStack> func_184193_aE() {
      return this.field_184657_bw;
   }

   public ItemStack func_184582_a(EntityEquipmentSlot p_184582_1_) {
      switch(p_184582_1_.func_188453_a()) {
      case HAND:
         return this.field_184656_bv.get(p_184582_1_.func_188454_b());
      case ARMOR:
         return this.field_184657_bw.get(p_184582_1_.func_188454_b());
      default:
         return ItemStack.field_190927_a;
      }
   }

   public void func_184201_a(EntityEquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
      switch(p_184201_1_.func_188453_a()) {
      case HAND:
         this.field_184656_bv.set(p_184201_1_.func_188454_b(), p_184201_2_);
         break;
      case ARMOR:
         this.field_184657_bw.set(p_184201_1_.func_188454_b(), p_184201_2_);
      }

   }

   protected void func_82160_b(boolean p_82160_1_, int p_82160_2_) {
      for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
         ItemStack itemstack = this.func_184582_a(entityequipmentslot);
         double d0;
         switch(entityequipmentslot.func_188453_a()) {
         case HAND:
            d0 = (double)this.field_82174_bp[entityequipmentslot.func_188454_b()];
            break;
         case ARMOR:
            d0 = (double)this.field_184655_bs[entityequipmentslot.func_188454_b()];
            break;
         default:
            d0 = 0.0D;
         }

         boolean flag = d0 > 1.0D;
         if (!itemstack.func_190926_b() && !EnchantmentHelper.func_190939_c(itemstack) && (p_82160_1_ || flag) && (double)(this.field_70146_Z.nextFloat() - (float)p_82160_2_ * 0.01F) < d0) {
            if (!flag && itemstack.func_77984_f()) {
               itemstack.func_77964_b(itemstack.func_77958_k() - this.field_70146_Z.nextInt(1 + this.field_70146_Z.nextInt(Math.max(itemstack.func_77958_k() - 3, 1))));
            }

            this.func_70099_a(itemstack, 0.0F);
         }
      }

   }

   protected void func_180481_a(DifficultyInstance p_180481_1_) {
      if (this.field_70146_Z.nextFloat() < 0.15F * p_180481_1_.func_180170_c()) {
         int i = this.field_70146_Z.nextInt(2);
         float f = this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD ? 0.1F : 0.25F;
         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++i;
         }

         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++i;
         }

         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++i;
         }

         boolean flag = true;

         for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
            if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
               ItemStack itemstack = this.func_184582_a(entityequipmentslot);
               if (!flag && this.field_70146_Z.nextFloat() < f) {
                  break;
               }

               flag = false;
               if (itemstack.func_190926_b()) {
                  Item item = func_184636_a(entityequipmentslot, i);
                  if (item != null) {
                     this.func_184201_a(entityequipmentslot, new ItemStack(item));
                  }
               }
            }
         }
      }

   }

   public static EntityEquipmentSlot func_184640_d(ItemStack p_184640_0_) {
      if (p_184640_0_.func_77973_b() != Item.func_150898_a(Blocks.field_150423_aK) && p_184640_0_.func_77973_b() != Items.field_151144_bL) {
         if (p_184640_0_.func_77973_b() instanceof ItemArmor) {
            return ((ItemArmor)p_184640_0_.func_77973_b()).field_77881_a;
         } else if (p_184640_0_.func_77973_b() == Items.field_185160_cR) {
            return EntityEquipmentSlot.CHEST;
         } else {
            return p_184640_0_.func_77973_b() == Items.field_185159_cQ ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
         }
      } else {
         return EntityEquipmentSlot.HEAD;
      }
   }

   @Nullable
   public static Item func_184636_a(EntityEquipmentSlot p_184636_0_, int p_184636_1_) {
      switch(p_184636_0_) {
      case HEAD:
         if (p_184636_1_ == 0) {
            return Items.field_151024_Q;
         } else if (p_184636_1_ == 1) {
            return Items.field_151169_ag;
         } else if (p_184636_1_ == 2) {
            return Items.field_151020_U;
         } else if (p_184636_1_ == 3) {
            return Items.field_151028_Y;
         } else if (p_184636_1_ == 4) {
            return Items.field_151161_ac;
         }
      case CHEST:
         if (p_184636_1_ == 0) {
            return Items.field_151027_R;
         } else if (p_184636_1_ == 1) {
            return Items.field_151171_ah;
         } else if (p_184636_1_ == 2) {
            return Items.field_151023_V;
         } else if (p_184636_1_ == 3) {
            return Items.field_151030_Z;
         } else if (p_184636_1_ == 4) {
            return Items.field_151163_ad;
         }
      case LEGS:
         if (p_184636_1_ == 0) {
            return Items.field_151026_S;
         } else if (p_184636_1_ == 1) {
            return Items.field_151149_ai;
         } else if (p_184636_1_ == 2) {
            return Items.field_151022_W;
         } else if (p_184636_1_ == 3) {
            return Items.field_151165_aa;
         } else if (p_184636_1_ == 4) {
            return Items.field_151173_ae;
         }
      case FEET:
         if (p_184636_1_ == 0) {
            return Items.field_151021_T;
         } else if (p_184636_1_ == 1) {
            return Items.field_151151_aj;
         } else if (p_184636_1_ == 2) {
            return Items.field_151029_X;
         } else if (p_184636_1_ == 3) {
            return Items.field_151167_ab;
         } else if (p_184636_1_ == 4) {
            return Items.field_151175_af;
         }
      default:
         return null;
      }
   }

   protected void func_180483_b(DifficultyInstance p_180483_1_) {
      float f = p_180483_1_.func_180170_c();
      if (!this.func_184614_ca().func_190926_b() && this.field_70146_Z.nextFloat() < 0.25F * f) {
         this.func_184201_a(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_184614_ca(), (int)(5.0F + f * (float)this.field_70146_Z.nextInt(18)), false));
      }

      for(EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
         if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
            ItemStack itemstack = this.func_184582_a(entityequipmentslot);
            if (!itemstack.func_190926_b() && this.field_70146_Z.nextFloat() < 0.5F * f) {
               this.func_184201_a(entityequipmentslot, EnchantmentHelper.func_77504_a(this.field_70146_Z, itemstack, (int)(5.0F + f * (float)this.field_70146_Z.nextInt(18)), false));
            }
         }
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random spawn bonus", this.field_70146_Z.nextGaussian() * 0.05D, 1));
      if (this.field_70146_Z.nextFloat() < 0.05F) {
         this.func_184641_n(true);
      } else {
         this.func_184641_n(false);
      }

      return p_180482_2_;
   }

   public boolean func_82171_bF() {
      return false;
   }

   public void func_110163_bv() {
      this.field_82179_bU = true;
   }

   public void func_184642_a(EntityEquipmentSlot p_184642_1_, float p_184642_2_) {
      switch(p_184642_1_.func_188453_a()) {
      case HAND:
         this.field_82174_bp[p_184642_1_.func_188454_b()] = p_184642_2_;
         break;
      case ARMOR:
         this.field_184655_bs[p_184642_1_.func_188454_b()] = p_184642_2_;
      }

   }

   public boolean func_98052_bS() {
      return this.field_82172_bs;
   }

   public void func_98053_h(boolean p_98053_1_) {
      this.field_82172_bs = p_98053_1_;
   }

   public boolean func_104002_bU() {
      return this.field_82179_bU;
   }

   public final boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      if (this.func_110167_bD() && this.func_110166_bE() == p_184230_1_) {
         this.func_110160_i(true, !p_184230_1_.field_71075_bZ.field_75098_d);
         return true;
      } else {
         ItemStack itemstack = p_184230_1_.func_184586_b(p_184230_2_);
         if (itemstack.func_77973_b() == Items.field_151058_ca && this.func_184652_a(p_184230_1_)) {
            this.func_110162_b(p_184230_1_, true);
            itemstack.func_190918_g(1);
            return true;
         } else {
            return this.func_184645_a(p_184230_1_, p_184230_2_) ? true : super.func_184230_a(p_184230_1_, p_184230_2_);
         }
      }
   }

   protected boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      return false;
   }

   protected void func_110159_bB() {
      if (this.field_110170_bx != null) {
         this.func_110165_bF();
      }

      if (this.field_110169_bv) {
         if (!this.func_70089_S()) {
            this.func_110160_i(true, true);
         }

         if (this.field_110168_bw == null || this.field_110168_bw.field_70128_L) {
            this.func_110160_i(true, true);
         }
      }
   }

   public void func_110160_i(boolean p_110160_1_, boolean p_110160_2_) {
      if (this.field_110169_bv) {
         this.field_110169_bv = false;
         this.field_110168_bw = null;
         if (!this.field_70170_p.field_72995_K && p_110160_2_) {
            this.func_145779_a(Items.field_151058_ca, 1);
         }

         if (!this.field_70170_p.field_72995_K && p_110160_1_ && this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityAttach(this, (Entity)null));
         }
      }

   }

   public boolean func_184652_a(EntityPlayer p_184652_1_) {
      return !this.func_110167_bD() && !(this instanceof IMob);
   }

   public boolean func_110167_bD() {
      return this.field_110169_bv;
   }

   public Entity func_110166_bE() {
      return this.field_110168_bw;
   }

   public void func_110162_b(Entity p_110162_1_, boolean p_110162_2_) {
      this.field_110169_bv = true;
      this.field_110168_bw = p_110162_1_;
      if (!this.field_70170_p.field_72995_K && p_110162_2_ && this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityAttach(this, this.field_110168_bw));
      }

      if (this.func_184218_aH()) {
         this.func_184210_p();
      }

   }

   public boolean func_184205_a(Entity p_184205_1_, boolean p_184205_2_) {
      boolean flag = super.func_184205_a(p_184205_1_, p_184205_2_);
      if (flag && this.func_110167_bD()) {
         this.func_110160_i(true, true);
      }

      return flag;
   }

   private void func_110165_bF() {
      if (this.field_110169_bv && this.field_110170_bx != null) {
         if (this.field_110170_bx.func_186855_b("UUID")) {
            UUID uuid = this.field_110170_bx.func_186857_a("UUID");

            for(EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_186662_g(10.0D))) {
               if (entitylivingbase.func_110124_au().equals(uuid)) {
                  this.func_110162_b(entitylivingbase, true);
                  break;
               }
            }
         } else if (this.field_110170_bx.func_150297_b("X", 99) && this.field_110170_bx.func_150297_b("Y", 99) && this.field_110170_bx.func_150297_b("Z", 99)) {
            BlockPos blockpos = new BlockPos(this.field_110170_bx.func_74762_e("X"), this.field_110170_bx.func_74762_e("Y"), this.field_110170_bx.func_74762_e("Z"));
            EntityLeashKnot entityleashknot = EntityLeashKnot.func_174863_b(this.field_70170_p, blockpos);
            if (entityleashknot == null) {
               entityleashknot = EntityLeashKnot.func_174862_a(this.field_70170_p, blockpos);
            }

            this.func_110162_b(entityleashknot, true);
         } else {
            this.func_110160_i(false, true);
         }
      }

      this.field_110170_bx = null;
   }

   public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
      EntityEquipmentSlot entityequipmentslot;
      if (p_174820_1_ == 98) {
         entityequipmentslot = EntityEquipmentSlot.MAINHAND;
      } else if (p_174820_1_ == 99) {
         entityequipmentslot = EntityEquipmentSlot.OFFHAND;
      } else if (p_174820_1_ == 100 + EntityEquipmentSlot.HEAD.func_188454_b()) {
         entityequipmentslot = EntityEquipmentSlot.HEAD;
      } else if (p_174820_1_ == 100 + EntityEquipmentSlot.CHEST.func_188454_b()) {
         entityequipmentslot = EntityEquipmentSlot.CHEST;
      } else if (p_174820_1_ == 100 + EntityEquipmentSlot.LEGS.func_188454_b()) {
         entityequipmentslot = EntityEquipmentSlot.LEGS;
      } else {
         if (p_174820_1_ != 100 + EntityEquipmentSlot.FEET.func_188454_b()) {
            return false;
         }

         entityequipmentslot = EntityEquipmentSlot.FEET;
      }

      if (!p_174820_2_.func_190926_b() && !func_184648_b(entityequipmentslot, p_174820_2_) && entityequipmentslot != EntityEquipmentSlot.HEAD) {
         return false;
      } else {
         this.func_184201_a(entityequipmentslot, p_174820_2_);
         return true;
      }
   }

   public boolean func_184186_bw() {
      return this.func_82171_bF() && super.func_184186_bw();
   }

   public static boolean func_184648_b(EntityEquipmentSlot p_184648_0_, ItemStack p_184648_1_) {
      EntityEquipmentSlot entityequipmentslot = func_184640_d(p_184648_1_);
      return entityequipmentslot == p_184648_0_ || entityequipmentslot == EntityEquipmentSlot.MAINHAND && p_184648_0_ == EntityEquipmentSlot.OFFHAND || entityequipmentslot == EntityEquipmentSlot.OFFHAND && p_184648_0_ == EntityEquipmentSlot.MAINHAND;
   }

   public boolean func_70613_aW() {
      return super.func_70613_aW() && !this.func_175446_cd();
   }

   public void func_94061_f(boolean p_94061_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184654_a)).byteValue();
      this.field_70180_af.func_187227_b(field_184654_a, Byte.valueOf(p_94061_1_ ? (byte)(b0 | 1) : (byte)(b0 & -2)));
   }

   public void func_184641_n(boolean p_184641_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184654_a)).byteValue();
      this.field_70180_af.func_187227_b(field_184654_a, Byte.valueOf(p_184641_1_ ? (byte)(b0 | 2) : (byte)(b0 & -3)));
   }

   public boolean func_175446_cd() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184654_a)).byteValue() & 1) != 0;
   }

   public boolean func_184638_cS() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184654_a)).byteValue() & 2) != 0;
   }

   public EnumHandSide func_184591_cq() {
      return this.func_184638_cS() ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
   }

   public static enum SpawnPlacementType {
      ON_GROUND,
      IN_AIR,
      IN_WATER;
   }
}
