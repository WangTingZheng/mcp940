package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityBeacon extends TileEntityLockable implements ITickable, ISidedInventory {
   public static final Potion[][] field_146009_a = new Potion[][]{{MobEffects.field_76424_c, MobEffects.field_76422_e}, {MobEffects.field_76429_m, MobEffects.field_76430_j}, {MobEffects.field_76420_g}, {MobEffects.field_76428_l}};
   private static final Set<Potion> field_184280_f = Sets.<Potion>newHashSet();
   private final List<TileEntityBeacon.BeamSegment> field_174909_f = Lists.<TileEntityBeacon.BeamSegment>newArrayList();
   private long field_146016_i;
   private float field_146014_j;
   private boolean field_146015_k;
   private int field_146012_l = -1;
   @Nullable
   private Potion field_146013_m;
   @Nullable
   private Potion field_146010_n;
   private ItemStack field_146011_o = ItemStack.field_190927_a;
   private String field_146008_p;

   public void func_73660_a() {
      if (this.field_145850_b.func_82737_E() % 80L == 0L) {
         this.func_174908_m();
      }

   }

   public void func_174908_m() {
      if (this.field_145850_b != null) {
         this.func_146003_y();
         this.func_146000_x();
      }

   }

   private void func_146000_x() {
      if (this.field_146015_k && this.field_146012_l > 0 && !this.field_145850_b.field_72995_K && this.field_146013_m != null) {
         double d0 = (double)(this.field_146012_l * 10 + 10);
         int i = 0;
         if (this.field_146012_l >= 4 && this.field_146013_m == this.field_146010_n) {
            i = 1;
         }

         int j = (9 + this.field_146012_l * 2) * 20;
         int k = this.field_174879_c.func_177958_n();
         int l = this.field_174879_c.func_177956_o();
         int i1 = this.field_174879_c.func_177952_p();
         AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))).func_186662_g(d0).func_72321_a(0.0D, (double)this.field_145850_b.func_72800_K(), 0.0D);
         List<EntityPlayer> list = this.field_145850_b.<EntityPlayer>func_72872_a(EntityPlayer.class, axisalignedbb);

         for(EntityPlayer entityplayer : list) {
            entityplayer.func_70690_d(new PotionEffect(this.field_146013_m, j, i, true, true));
         }

         if (this.field_146012_l >= 4 && this.field_146013_m != this.field_146010_n && this.field_146010_n != null) {
            for(EntityPlayer entityplayer1 : list) {
               entityplayer1.func_70690_d(new PotionEffect(this.field_146010_n, j, 0, true, true));
            }
         }
      }

   }

   private void func_146003_y() {
      int i = this.field_174879_c.func_177958_n();
      int j = this.field_174879_c.func_177956_o();
      int k = this.field_174879_c.func_177952_p();
      int l = this.field_146012_l;
      this.field_146012_l = 0;
      this.field_174909_f.clear();
      this.field_146015_k = true;
      TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = new TileEntityBeacon.BeamSegment(EnumDyeColor.WHITE.func_193349_f());
      this.field_174909_f.add(tileentitybeacon$beamsegment);
      boolean flag = true;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i1 = j + 1; i1 < 256; ++i1) {
         IBlockState iblockstate = this.field_145850_b.func_180495_p(blockpos$mutableblockpos.func_181079_c(i, i1, k));
         float[] afloat;
         if (iblockstate.func_177230_c() == Blocks.field_150399_cn) {
            afloat = ((EnumDyeColor)iblockstate.func_177229_b(BlockStainedGlass.field_176547_a)).func_193349_f();
         } else {
            if (iblockstate.func_177230_c() != Blocks.field_150397_co) {
               if (iblockstate.func_185891_c() >= 15 && iblockstate.func_177230_c() != Blocks.field_150357_h) {
                  this.field_146015_k = false;
                  this.field_174909_f.clear();
                  break;
               }

               tileentitybeacon$beamsegment.func_177262_a();
               continue;
            }

            afloat = ((EnumDyeColor)iblockstate.func_177229_b(BlockStainedGlassPane.field_176245_a)).func_193349_f();
         }

         if (!flag) {
            afloat = new float[]{(tileentitybeacon$beamsegment.func_177263_b()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.func_177263_b()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.func_177263_b()[2] + afloat[2]) / 2.0F};
         }

         if (Arrays.equals(afloat, tileentitybeacon$beamsegment.func_177263_b())) {
            tileentitybeacon$beamsegment.func_177262_a();
         } else {
            tileentitybeacon$beamsegment = new TileEntityBeacon.BeamSegment(afloat);
            this.field_174909_f.add(tileentitybeacon$beamsegment);
         }

         flag = false;
      }

      if (this.field_146015_k) {
         for(int l1 = 1; l1 <= 4; this.field_146012_l = l1++) {
            int i2 = j - l1;
            if (i2 < 0) {
               break;
            }

            boolean flag1 = true;

            for(int j1 = i - l1; j1 <= i + l1 && flag1; ++j1) {
               for(int k1 = k - l1; k1 <= k + l1; ++k1) {
                  Block block = this.field_145850_b.func_180495_p(new BlockPos(j1, i2, k1)).func_177230_c();
                  if (block != Blocks.field_150475_bE && block != Blocks.field_150340_R && block != Blocks.field_150484_ah && block != Blocks.field_150339_S) {
                     flag1 = false;
                     break;
                  }
               }
            }

            if (!flag1) {
               break;
            }
         }

         if (this.field_146012_l == 0) {
            this.field_146015_k = false;
         }
      }

      if (!this.field_145850_b.field_72995_K && l < this.field_146012_l) {
         for(EntityPlayerMP entityplayermp : this.field_145850_b.func_72872_a(EntityPlayerMP.class, (new AxisAlignedBB((double)i, (double)j, (double)k, (double)i, (double)(j - 4), (double)k)).func_72314_b(10.0D, 5.0D, 10.0D))) {
            CriteriaTriggers.field_192131_k.func_192180_a(entityplayermp, this);
         }
      }

   }

   public List<TileEntityBeacon.BeamSegment> func_174907_n() {
      return this.field_174909_f;
   }

   public float func_146002_i() {
      if (!this.field_146015_k) {
         return 0.0F;
      } else {
         int i = (int)(this.field_145850_b.func_82737_E() - this.field_146016_i);
         this.field_146016_i = this.field_145850_b.func_82737_E();
         if (i > 1) {
            this.field_146014_j -= (float)i / 40.0F;
            if (this.field_146014_j < 0.0F) {
               this.field_146014_j = 0.0F;
            }
         }

         this.field_146014_j += 0.025F;
         if (this.field_146014_j > 1.0F) {
            this.field_146014_j = 1.0F;
         }

         return this.field_146014_j;
      }
   }

   public int func_191979_s() {
      return this.field_146012_l;
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 3, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public double func_145833_n() {
      return 65536.0D;
   }

   @Nullable
   private static Potion func_184279_f(int p_184279_0_) {
      Potion potion = Potion.func_188412_a(p_184279_0_);
      return field_184280_f.contains(potion) ? potion : null;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_146013_m = func_184279_f(p_145839_1_.func_74762_e("Primary"));
      this.field_146010_n = func_184279_f(p_145839_1_.func_74762_e("Secondary"));
      this.field_146012_l = p_145839_1_.func_74762_e("Levels");
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74768_a("Primary", Potion.func_188409_a(this.field_146013_m));
      p_189515_1_.func_74768_a("Secondary", Potion.func_188409_a(this.field_146010_n));
      p_189515_1_.func_74768_a("Levels", this.field_146012_l);
      return p_189515_1_;
   }

   public int func_70302_i_() {
      return 1;
   }

   public boolean func_191420_l() {
      return this.field_146011_o.func_190926_b();
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return p_70301_1_ == 0 ? this.field_146011_o : ItemStack.field_190927_a;
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      if (p_70298_1_ == 0 && !this.field_146011_o.func_190926_b()) {
         if (p_70298_2_ >= this.field_146011_o.func_190916_E()) {
            ItemStack itemstack = this.field_146011_o;
            this.field_146011_o = ItemStack.field_190927_a;
            return itemstack;
         } else {
            return this.field_146011_o.func_77979_a(p_70298_2_);
         }
      } else {
         return ItemStack.field_190927_a;
      }
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      if (p_70304_1_ == 0) {
         ItemStack itemstack = this.field_146011_o;
         this.field_146011_o = ItemStack.field_190927_a;
         return itemstack;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      if (p_70299_1_ == 0) {
         this.field_146011_o = p_70299_2_;
      }

   }

   public String func_70005_c_() {
      return this.func_145818_k_() ? this.field_146008_p : "container.beacon";
   }

   public boolean func_145818_k_() {
      return this.field_146008_p != null && !this.field_146008_p.isEmpty();
   }

   public void func_145999_a(String p_145999_1_) {
      this.field_146008_p = p_145999_1_;
   }

   public int func_70297_j_() {
      return 1;
   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return p_70300_1_.func_70092_e((double)this.field_174879_c.func_177958_n() + 0.5D, (double)this.field_174879_c.func_177956_o() + 0.5D, (double)this.field_174879_c.func_177952_p() + 0.5D) <= 64.0D;
      }
   }

   public void func_174889_b(EntityPlayer p_174889_1_) {
   }

   public void func_174886_c(EntityPlayer p_174886_1_) {
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return p_94041_2_.func_77973_b() == Items.field_151166_bC || p_94041_2_.func_77973_b() == Items.field_151045_i || p_94041_2_.func_77973_b() == Items.field_151043_k || p_94041_2_.func_77973_b() == Items.field_151042_j;
   }

   public String func_174875_k() {
      return "minecraft:beacon";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      return new ContainerBeacon(p_174876_1_, this);
   }

   public int func_174887_a_(int p_174887_1_) {
      switch(p_174887_1_) {
      case 0:
         return this.field_146012_l;
      case 1:
         return Potion.func_188409_a(this.field_146013_m);
      case 2:
         return Potion.func_188409_a(this.field_146010_n);
      default:
         return 0;
      }
   }

   public void func_174885_b(int p_174885_1_, int p_174885_2_) {
      switch(p_174885_1_) {
      case 0:
         this.field_146012_l = p_174885_2_;
         break;
      case 1:
         this.field_146013_m = func_184279_f(p_174885_2_);
         break;
      case 2:
         this.field_146010_n = func_184279_f(p_174885_2_);
      }

   }

   public int func_174890_g() {
      return 3;
   }

   public void func_174888_l() {
      this.field_146011_o = ItemStack.field_190927_a;
   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      if (p_145842_1_ == 1) {
         this.func_174908_m();
         return true;
      } else {
         return super.func_145842_c(p_145842_1_, p_145842_2_);
      }
   }

   public int[] func_180463_a(EnumFacing p_180463_1_) {
      return new int[0];
   }

   public boolean func_180462_a(int p_180462_1_, ItemStack p_180462_2_, EnumFacing p_180462_3_) {
      return false;
   }

   public boolean func_180461_b(int p_180461_1_, ItemStack p_180461_2_, EnumFacing p_180461_3_) {
      return false;
   }

   static {
      for(Potion[] apotion : field_146009_a) {
         Collections.addAll(field_184280_f, apotion);
      }

   }

   public static class BeamSegment {
      private final float[] field_177266_a;
      private int field_177265_b;

      public BeamSegment(float[] p_i45669_1_) {
         this.field_177266_a = p_i45669_1_;
         this.field_177265_b = 1;
      }

      protected void func_177262_a() {
         ++this.field_177265_b;
      }

      public float[] func_177263_b() {
         return this.field_177266_a;
      }

      public int func_177264_c() {
         return this.field_177265_b;
      }
   }
}
