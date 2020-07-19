package net.minecraft.entity.item;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityArmorStand extends EntityLivingBase {
   private static final Rotations field_175435_a = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations field_175433_b = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations field_175434_c = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations field_175431_d = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations field_175432_e = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations field_175429_f = new Rotations(1.0F, 0.0F, 1.0F);
   public static final DataParameter<Byte> field_184801_a = EntityDataManager.<Byte>func_187226_a(EntityArmorStand.class, DataSerializers.field_187191_a);
   public static final DataParameter<Rotations> field_184802_b = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184803_c = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184804_d = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184805_e = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184806_f = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184807_g = EntityDataManager.<Rotations>func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   private static final Predicate<Entity> field_184798_bv = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof EntityMinecart && ((EntityMinecart)p_apply_1_).func_184264_v() == EntityMinecart.Type.RIDEABLE;
      }
   };
   private final NonNullList<ItemStack> field_184799_bw;
   private final NonNullList<ItemStack> field_184800_bx;
   private boolean field_175436_h;
   public long field_175437_i;
   private int field_175442_bg;
   private boolean field_181028_bj;
   private Rotations field_175443_bh;
   private Rotations field_175444_bi;
   private Rotations field_175438_bj;
   private Rotations field_175439_bk;
   private Rotations field_175440_bl;
   private Rotations field_175441_bm;

   public EntityArmorStand(World p_i45854_1_) {
      super(p_i45854_1_);
      this.field_184799_bw = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
      this.field_184800_bx = NonNullList.<ItemStack>func_191197_a(4, ItemStack.field_190927_a);
      this.field_175443_bh = field_175435_a;
      this.field_175444_bi = field_175433_b;
      this.field_175438_bj = field_175434_c;
      this.field_175439_bk = field_175431_d;
      this.field_175440_bl = field_175432_e;
      this.field_175441_bm = field_175429_f;
      this.field_70145_X = this.func_189652_ae();
      this.func_70105_a(0.5F, 1.975F);
   }

   public EntityArmorStand(World p_i45855_1_, double p_i45855_2_, double p_i45855_4_, double p_i45855_6_) {
      this(p_i45855_1_);
      this.func_70107_b(p_i45855_2_, p_i45855_4_, p_i45855_6_);
   }

   protected final void func_70105_a(float p_70105_1_, float p_70105_2_) {
      double d0 = this.field_70165_t;
      double d1 = this.field_70163_u;
      double d2 = this.field_70161_v;
      float f = this.func_181026_s() ? 0.0F : (this.func_70631_g_() ? 0.5F : 1.0F);
      super.func_70105_a(p_70105_1_ * f, p_70105_2_ * f);
      this.func_70107_b(d0, d1, d2);
   }

   public boolean func_70613_aW() {
      return super.func_70613_aW() && !this.func_189652_ae();
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184801_a, Byte.valueOf((byte)0));
      this.field_70180_af.func_187214_a(field_184802_b, field_175435_a);
      this.field_70180_af.func_187214_a(field_184803_c, field_175433_b);
      this.field_70180_af.func_187214_a(field_184804_d, field_175434_c);
      this.field_70180_af.func_187214_a(field_184805_e, field_175431_d);
      this.field_70180_af.func_187214_a(field_184806_f, field_175432_e);
      this.field_70180_af.func_187214_a(field_184807_g, field_175429_f);
   }

   public Iterable<ItemStack> func_184214_aD() {
      return this.field_184799_bw;
   }

   public Iterable<ItemStack> func_184193_aE() {
      return this.field_184800_bx;
   }

   public ItemStack func_184582_a(EntityEquipmentSlot p_184582_1_) {
      switch(p_184582_1_.func_188453_a()) {
      case HAND:
         return this.field_184799_bw.get(p_184582_1_.func_188454_b());
      case ARMOR:
         return this.field_184800_bx.get(p_184582_1_.func_188454_b());
      default:
         return ItemStack.field_190927_a;
      }
   }

   public void func_184201_a(EntityEquipmentSlot p_184201_1_, ItemStack p_184201_2_) {
      switch(p_184201_1_.func_188453_a()) {
      case HAND:
         this.func_184606_a_(p_184201_2_);
         this.field_184799_bw.set(p_184201_1_.func_188454_b(), p_184201_2_);
         break;
      case ARMOR:
         this.func_184606_a_(p_184201_2_);
         this.field_184800_bx.set(p_184201_1_.func_188454_b(), p_184201_2_);
      }

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

      if (!p_174820_2_.func_190926_b() && !EntityLiving.func_184648_b(entityequipmentslot, p_174820_2_) && entityequipmentslot != EntityEquipmentSlot.HEAD) {
         return false;
      } else {
         this.func_184201_a(entityequipmentslot, p_174820_2_);
         return true;
      }
   }

   public static void func_189805_a(DataFixer p_189805_0_) {
      p_189805_0_.func_188258_a(FixTypes.ENTITY, new ItemStackDataLists(EntityArmorStand.class, new String[]{"ArmorItems", "HandItems"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      NBTTagList nbttaglist = new NBTTagList();

      for(ItemStack itemstack : this.field_184800_bx) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         if (!itemstack.func_190926_b()) {
            itemstack.func_77955_b(nbttagcompound);
         }

         nbttaglist.func_74742_a(nbttagcompound);
      }

      p_70014_1_.func_74782_a("ArmorItems", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(ItemStack itemstack1 : this.field_184799_bw) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         if (!itemstack1.func_190926_b()) {
            itemstack1.func_77955_b(nbttagcompound1);
         }

         nbttaglist1.func_74742_a(nbttagcompound1);
      }

      p_70014_1_.func_74782_a("HandItems", nbttaglist1);
      p_70014_1_.func_74757_a("Invisible", this.func_82150_aj());
      p_70014_1_.func_74757_a("Small", this.func_175410_n());
      p_70014_1_.func_74757_a("ShowArms", this.func_175402_q());
      p_70014_1_.func_74768_a("DisabledSlots", this.field_175442_bg);
      p_70014_1_.func_74757_a("NoBasePlate", this.func_175414_r());
      if (this.func_181026_s()) {
         p_70014_1_.func_74757_a("Marker", this.func_181026_s());
      }

      p_70014_1_.func_74782_a("Pose", this.func_175419_y());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      if (p_70037_1_.func_150297_b("ArmorItems", 9)) {
         NBTTagList nbttaglist = p_70037_1_.func_150295_c("ArmorItems", 10);

         for(int i = 0; i < this.field_184800_bx.size(); ++i) {
            this.field_184800_bx.set(i, new ItemStack(nbttaglist.func_150305_b(i)));
         }
      }

      if (p_70037_1_.func_150297_b("HandItems", 9)) {
         NBTTagList nbttaglist1 = p_70037_1_.func_150295_c("HandItems", 10);

         for(int j = 0; j < this.field_184799_bw.size(); ++j) {
            this.field_184799_bw.set(j, new ItemStack(nbttaglist1.func_150305_b(j)));
         }
      }

      this.func_82142_c(p_70037_1_.func_74767_n("Invisible"));
      this.func_175420_a(p_70037_1_.func_74767_n("Small"));
      this.func_175413_k(p_70037_1_.func_74767_n("ShowArms"));
      this.field_175442_bg = p_70037_1_.func_74762_e("DisabledSlots");
      this.func_175426_l(p_70037_1_.func_74767_n("NoBasePlate"));
      this.func_181027_m(p_70037_1_.func_74767_n("Marker"));
      this.field_181028_bj = !this.func_181026_s();
      this.field_70145_X = this.func_189652_ae();
      NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("Pose");
      this.func_175416_h(nbttagcompound);
   }

   private void func_175416_h(NBTTagCompound p_175416_1_) {
      NBTTagList nbttaglist = p_175416_1_.func_150295_c("Head", 5);
      this.func_175415_a(nbttaglist.func_82582_d() ? field_175435_a : new Rotations(nbttaglist));
      NBTTagList nbttaglist1 = p_175416_1_.func_150295_c("Body", 5);
      this.func_175424_b(nbttaglist1.func_82582_d() ? field_175433_b : new Rotations(nbttaglist1));
      NBTTagList nbttaglist2 = p_175416_1_.func_150295_c("LeftArm", 5);
      this.func_175405_c(nbttaglist2.func_82582_d() ? field_175434_c : new Rotations(nbttaglist2));
      NBTTagList nbttaglist3 = p_175416_1_.func_150295_c("RightArm", 5);
      this.func_175428_d(nbttaglist3.func_82582_d() ? field_175431_d : new Rotations(nbttaglist3));
      NBTTagList nbttaglist4 = p_175416_1_.func_150295_c("LeftLeg", 5);
      this.func_175417_e(nbttaglist4.func_82582_d() ? field_175432_e : new Rotations(nbttaglist4));
      NBTTagList nbttaglist5 = p_175416_1_.func_150295_c("RightLeg", 5);
      this.func_175427_f(nbttaglist5.func_82582_d() ? field_175429_f : new Rotations(nbttaglist5));
   }

   private NBTTagCompound func_175419_y() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (!field_175435_a.equals(this.field_175443_bh)) {
         nbttagcompound.func_74782_a("Head", this.field_175443_bh.func_179414_a());
      }

      if (!field_175433_b.equals(this.field_175444_bi)) {
         nbttagcompound.func_74782_a("Body", this.field_175444_bi.func_179414_a());
      }

      if (!field_175434_c.equals(this.field_175438_bj)) {
         nbttagcompound.func_74782_a("LeftArm", this.field_175438_bj.func_179414_a());
      }

      if (!field_175431_d.equals(this.field_175439_bk)) {
         nbttagcompound.func_74782_a("RightArm", this.field_175439_bk.func_179414_a());
      }

      if (!field_175432_e.equals(this.field_175440_bl)) {
         nbttagcompound.func_74782_a("LeftLeg", this.field_175440_bl.func_179414_a());
      }

      if (!field_175429_f.equals(this.field_175441_bm)) {
         nbttagcompound.func_74782_a("RightLeg", this.field_175441_bm.func_179414_a());
      }

      return nbttagcompound;
   }

   public boolean func_70104_M() {
      return false;
   }

   protected void func_82167_n(Entity p_82167_1_) {
   }

   protected void func_85033_bc() {
      List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ(), field_184798_bv);

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = list.get(i);
         if (this.func_70068_e(entity) <= 0.2D) {
            entity.func_70108_f(this);
         }
      }

   }

   public EnumActionResult func_184199_a(EntityPlayer p_184199_1_, Vec3d p_184199_2_, EnumHand p_184199_3_) {
      ItemStack itemstack = p_184199_1_.func_184586_b(p_184199_3_);
      if (!this.func_181026_s() && itemstack.func_77973_b() != Items.field_151057_cb) {
         if (!this.field_70170_p.field_72995_K && !p_184199_1_.func_175149_v()) {
            EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
            if (itemstack.func_190926_b()) {
               EntityEquipmentSlot entityequipmentslot1 = this.func_190772_a(p_184199_2_);
               EntityEquipmentSlot entityequipmentslot2 = this.func_184796_b(entityequipmentslot1) ? entityequipmentslot : entityequipmentslot1;
               if (this.func_190630_a(entityequipmentslot2)) {
                  this.func_184795_a(p_184199_1_, entityequipmentslot2, itemstack, p_184199_3_);
               }
            } else {
               if (this.func_184796_b(entityequipmentslot)) {
                  return EnumActionResult.FAIL;
               }

               if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.HAND && !this.func_175402_q()) {
                  return EnumActionResult.FAIL;
               }

               this.func_184795_a(p_184199_1_, entityequipmentslot, itemstack, p_184199_3_);
            }

            return EnumActionResult.SUCCESS;
         } else {
            return EnumActionResult.SUCCESS;
         }
      } else {
         return EnumActionResult.PASS;
      }
   }

   protected EntityEquipmentSlot func_190772_a(Vec3d p_190772_1_) {
      EntityEquipmentSlot entityequipmentslot = EntityEquipmentSlot.MAINHAND;
      boolean flag = this.func_175410_n();
      double d0 = flag ? p_190772_1_.field_72448_b * 2.0D : p_190772_1_.field_72448_b;
      EntityEquipmentSlot entityequipmentslot1 = EntityEquipmentSlot.FEET;
      if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) && this.func_190630_a(entityequipmentslot1)) {
         entityequipmentslot = EntityEquipmentSlot.FEET;
      } else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D) && this.func_190630_a(EntityEquipmentSlot.CHEST)) {
         entityequipmentslot = EntityEquipmentSlot.CHEST;
      } else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) && this.func_190630_a(EntityEquipmentSlot.LEGS)) {
         entityequipmentslot = EntityEquipmentSlot.LEGS;
      } else if (d0 >= 1.6D && this.func_190630_a(EntityEquipmentSlot.HEAD)) {
         entityequipmentslot = EntityEquipmentSlot.HEAD;
      }

      return entityequipmentslot;
   }

   private boolean func_184796_b(EntityEquipmentSlot p_184796_1_) {
      return (this.field_175442_bg & 1 << p_184796_1_.func_188452_c()) != 0;
   }

   private void func_184795_a(EntityPlayer p_184795_1_, EntityEquipmentSlot p_184795_2_, ItemStack p_184795_3_, EnumHand p_184795_4_) {
      ItemStack itemstack = this.func_184582_a(p_184795_2_);
      if (itemstack.func_190926_b() || (this.field_175442_bg & 1 << p_184795_2_.func_188452_c() + 8) == 0) {
         if (!itemstack.func_190926_b() || (this.field_175442_bg & 1 << p_184795_2_.func_188452_c() + 16) == 0) {
            if (p_184795_1_.field_71075_bZ.field_75098_d && itemstack.func_190926_b() && !p_184795_3_.func_190926_b()) {
               ItemStack itemstack2 = p_184795_3_.func_77946_l();
               itemstack2.func_190920_e(1);
               this.func_184201_a(p_184795_2_, itemstack2);
            } else if (!p_184795_3_.func_190926_b() && p_184795_3_.func_190916_E() > 1) {
               if (itemstack.func_190926_b()) {
                  ItemStack itemstack1 = p_184795_3_.func_77946_l();
                  itemstack1.func_190920_e(1);
                  this.func_184201_a(p_184795_2_, itemstack1);
                  p_184795_3_.func_190918_g(1);
               }
            } else {
               this.func_184201_a(p_184795_2_, p_184795_3_);
               p_184795_1_.func_184611_a(p_184795_4_, itemstack);
            }
         }
      }
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         if (DamageSource.field_76380_i.equals(p_70097_1_)) {
            this.func_70106_y();
            return false;
         } else if (!this.func_180431_b(p_70097_1_) && !this.field_175436_h && !this.func_181026_s()) {
            if (p_70097_1_.func_94541_c()) {
               this.func_175409_C();
               this.func_70106_y();
               return false;
            } else if (DamageSource.field_76372_a.equals(p_70097_1_)) {
               if (this.func_70027_ad()) {
                  this.func_175406_a(0.15F);
               } else {
                  this.func_70015_d(5);
               }

               return false;
            } else if (DamageSource.field_76370_b.equals(p_70097_1_) && this.func_110143_aJ() > 0.5F) {
               this.func_175406_a(4.0F);
               return false;
            } else {
               boolean flag = "arrow".equals(p_70097_1_.func_76355_l());
               boolean flag1 = "player".equals(p_70097_1_.func_76355_l());
               if (!flag1 && !flag) {
                  return false;
               } else {
                  if (p_70097_1_.func_76364_f() instanceof EntityArrow) {
                     p_70097_1_.func_76364_f().func_70106_y();
                  }

                  if (p_70097_1_.func_76346_g() instanceof EntityPlayer && !((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75099_e) {
                     return false;
                  } else if (p_70097_1_.func_180136_u()) {
                     this.func_190773_I();
                     this.func_175412_z();
                     this.func_70106_y();
                     return false;
                  } else {
                     long i = this.field_70170_p.func_82737_E();
                     if (i - this.field_175437_i > 5L && !flag) {
                        this.field_70170_p.func_72960_a(this, (byte)32);
                        this.field_175437_i = i;
                     } else {
                        this.func_175421_A();
                        this.func_175412_z();
                        this.func_70106_y();
                     }

                     return false;
                  }
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 32) {
         if (this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187707_l, this.func_184176_by(), 0.3F, 1.0F, false);
            this.field_175437_i = this.field_70170_p.func_82737_E();
         }
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public boolean func_70112_a(double p_70112_1_) {
      double d0 = this.func_174813_aQ().func_72320_b() * 4.0D;
      if (Double.isNaN(d0) || d0 == 0.0D) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_70112_1_ < d0 * d0;
   }

   private void func_175412_z() {
      if (this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.BLOCK_DUST, this.field_70165_t, this.field_70163_u + (double)this.field_70131_O / 1.5D, this.field_70161_v, 10, (double)(this.field_70130_N / 4.0F), (double)(this.field_70131_O / 4.0F), (double)(this.field_70130_N / 4.0F), 0.05D, Block.func_176210_f(Blocks.field_150344_f.func_176223_P()));
      }

   }

   private void func_175406_a(float p_175406_1_) {
      float f = this.func_110143_aJ();
      f = f - p_175406_1_;
      if (f <= 0.5F) {
         this.func_175409_C();
         this.func_70106_y();
      } else {
         this.func_70606_j(f);
      }

   }

   private void func_175421_A() {
      Block.func_180635_a(this.field_70170_p, new BlockPos(this), new ItemStack(Items.field_179565_cj));
      this.func_175409_C();
   }

   private void func_175409_C() {
      this.func_190773_I();

      for(int i = 0; i < this.field_184799_bw.size(); ++i) {
         ItemStack itemstack = this.field_184799_bw.get(i);
         if (!itemstack.func_190926_b()) {
            Block.func_180635_a(this.field_70170_p, (new BlockPos(this)).func_177984_a(), itemstack);
            this.field_184799_bw.set(i, ItemStack.field_190927_a);
         }
      }

      for(int j = 0; j < this.field_184800_bx.size(); ++j) {
         ItemStack itemstack1 = this.field_184800_bx.get(j);
         if (!itemstack1.func_190926_b()) {
            Block.func_180635_a(this.field_70170_p, (new BlockPos(this)).func_177984_a(), itemstack1);
            this.field_184800_bx.set(j, ItemStack.field_190927_a);
         }
      }

   }

   private void func_190773_I() {
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187701_j, this.func_184176_by(), 1.0F, 1.0F);
   }

   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
      this.field_70760_ar = this.field_70126_B;
      this.field_70761_aq = this.field_70177_z;
      return 0.0F;
   }

   public float func_70047_e() {
      return this.func_70631_g_() ? this.field_70131_O * 0.5F : this.field_70131_O * 0.9F;
   }

   public double func_70033_W() {
      return this.func_181026_s() ? 0.0D : 0.10000000149011612D;
   }

   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
      if (!this.func_189652_ae()) {
         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
      }
   }

   public void func_181013_g(float p_181013_1_) {
      this.field_70760_ar = this.field_70126_B = p_181013_1_;
      this.field_70758_at = this.field_70759_as = p_181013_1_;
   }

   public void func_70034_d(float p_70034_1_) {
      this.field_70760_ar = this.field_70126_B = p_70034_1_;
      this.field_70758_at = this.field_70759_as = p_70034_1_;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      Rotations rotations = (Rotations)this.field_70180_af.func_187225_a(field_184802_b);
      if (!this.field_175443_bh.equals(rotations)) {
         this.func_175415_a(rotations);
      }

      Rotations rotations1 = (Rotations)this.field_70180_af.func_187225_a(field_184803_c);
      if (!this.field_175444_bi.equals(rotations1)) {
         this.func_175424_b(rotations1);
      }

      Rotations rotations2 = (Rotations)this.field_70180_af.func_187225_a(field_184804_d);
      if (!this.field_175438_bj.equals(rotations2)) {
         this.func_175405_c(rotations2);
      }

      Rotations rotations3 = (Rotations)this.field_70180_af.func_187225_a(field_184805_e);
      if (!this.field_175439_bk.equals(rotations3)) {
         this.func_175428_d(rotations3);
      }

      Rotations rotations4 = (Rotations)this.field_70180_af.func_187225_a(field_184806_f);
      if (!this.field_175440_bl.equals(rotations4)) {
         this.func_175417_e(rotations4);
      }

      Rotations rotations5 = (Rotations)this.field_70180_af.func_187225_a(field_184807_g);
      if (!this.field_175441_bm.equals(rotations5)) {
         this.func_175427_f(rotations5);
      }

      boolean flag = this.func_181026_s();
      if (this.field_181028_bj != flag) {
         this.func_181550_a(flag);
         this.field_70156_m = !flag;
         this.field_181028_bj = flag;
      }

   }

   private void func_181550_a(boolean p_181550_1_) {
      if (p_181550_1_) {
         this.func_70105_a(0.0F, 0.0F);
      } else {
         this.func_70105_a(0.5F, 1.975F);
      }

   }

   protected void func_175135_B() {
      this.func_82142_c(this.field_175436_h);
   }

   public void func_82142_c(boolean p_82142_1_) {
      this.field_175436_h = p_82142_1_;
      super.func_82142_c(p_82142_1_);
   }

   public boolean func_70631_g_() {
      return this.func_175410_n();
   }

   public void func_174812_G() {
      this.func_70106_y();
   }

   public boolean func_180427_aV() {
      return this.func_82150_aj();
   }

   public EnumPushReaction func_184192_z() {
      return this.func_181026_s() ? EnumPushReaction.IGNORE : super.func_184192_z();
   }

   private void func_175420_a(boolean p_175420_1_) {
      this.field_70180_af.func_187227_b(field_184801_a, Byte.valueOf(this.func_184797_a(((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue(), 1, p_175420_1_)));
      this.func_70105_a(0.5F, 1.975F);
   }

   public boolean func_175410_n() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue() & 1) != 0;
   }

   private void func_175413_k(boolean p_175413_1_) {
      this.field_70180_af.func_187227_b(field_184801_a, Byte.valueOf(this.func_184797_a(((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue(), 4, p_175413_1_)));
   }

   public boolean func_175402_q() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue() & 4) != 0;
   }

   private void func_175426_l(boolean p_175426_1_) {
      this.field_70180_af.func_187227_b(field_184801_a, Byte.valueOf(this.func_184797_a(((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue(), 8, p_175426_1_)));
   }

   public boolean func_175414_r() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue() & 8) != 0;
   }

   private void func_181027_m(boolean p_181027_1_) {
      this.field_70180_af.func_187227_b(field_184801_a, Byte.valueOf(this.func_184797_a(((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue(), 16, p_181027_1_)));
      this.func_70105_a(0.5F, 1.975F);
   }

   public boolean func_181026_s() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184801_a)).byteValue() & 16) != 0;
   }

   private byte func_184797_a(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
      if (p_184797_3_) {
         p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
      } else {
         p_184797_1_ = (byte)(p_184797_1_ & ~p_184797_2_);
      }

      return p_184797_1_;
   }

   public void func_175415_a(Rotations p_175415_1_) {
      this.field_175443_bh = p_175415_1_;
      this.field_70180_af.func_187227_b(field_184802_b, p_175415_1_);
   }

   public void func_175424_b(Rotations p_175424_1_) {
      this.field_175444_bi = p_175424_1_;
      this.field_70180_af.func_187227_b(field_184803_c, p_175424_1_);
   }

   public void func_175405_c(Rotations p_175405_1_) {
      this.field_175438_bj = p_175405_1_;
      this.field_70180_af.func_187227_b(field_184804_d, p_175405_1_);
   }

   public void func_175428_d(Rotations p_175428_1_) {
      this.field_175439_bk = p_175428_1_;
      this.field_70180_af.func_187227_b(field_184805_e, p_175428_1_);
   }

   public void func_175417_e(Rotations p_175417_1_) {
      this.field_175440_bl = p_175417_1_;
      this.field_70180_af.func_187227_b(field_184806_f, p_175417_1_);
   }

   public void func_175427_f(Rotations p_175427_1_) {
      this.field_175441_bm = p_175427_1_;
      this.field_70180_af.func_187227_b(field_184807_g, p_175427_1_);
   }

   public Rotations func_175418_s() {
      return this.field_175443_bh;
   }

   public Rotations func_175408_t() {
      return this.field_175444_bi;
   }

   public Rotations func_175404_u() {
      return this.field_175438_bj;
   }

   public Rotations func_175411_v() {
      return this.field_175439_bk;
   }

   public Rotations func_175403_w() {
      return this.field_175440_bl;
   }

   public Rotations func_175407_x() {
      return this.field_175441_bm;
   }

   public boolean func_70067_L() {
      return super.func_70067_L() && !this.func_181026_s();
   }

   public EnumHandSide func_184591_cq() {
      return EnumHandSide.RIGHT;
   }

   protected SoundEvent func_184588_d(int p_184588_1_) {
      return SoundEvents.field_187704_k;
   }

   @Nullable
   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187707_l;
   }

   @Nullable
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187701_j;
   }

   public void func_70077_a(EntityLightningBolt p_70077_1_) {
   }

   public boolean func_184603_cC() {
      return false;
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      if (field_184801_a.equals(p_184206_1_)) {
         this.func_70105_a(0.5F, 1.975F);
      }

      super.func_184206_a(p_184206_1_);
   }

   public boolean func_190631_cK() {
      return false;
   }
}
