package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityHorse extends AbstractHorse {
   private static final UUID field_184786_bD = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final DataParameter<Integer> field_184789_bG = EntityDataManager.<Integer>func_187226_a(EntityHorse.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184791_bI = EntityDataManager.<Integer>func_187226_a(EntityHorse.class, DataSerializers.field_187192_b);
   private static final String[] field_110268_bz = new String[]{"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
   private static final String[] field_110269_bA = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
   private static final String[] field_110291_bB = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
   private static final String[] field_110292_bC = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
   private String field_110286_bQ;
   private final String[] field_110280_bR = new String[3];

   public EntityHorse(World p_i1685_1_) {
      super(p_i1685_1_);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184789_bG, Integer.valueOf(0));
      this.field_70180_af.func_187214_a(field_184791_bI, Integer.valueOf(HorseArmorType.NONE.func_188579_a()));
   }

   public static void func_189803_b(DataFixer p_189803_0_) {
      AbstractHorse.func_190683_c(p_189803_0_, EntityHorse.class);
      p_189803_0_.func_188258_a(FixTypes.ENTITY, new ItemStackData(EntityHorse.class, new String[]{"ArmorItem"}));
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Variant", this.func_110202_bQ());
      if (!this.field_110296_bG.func_70301_a(1).func_190926_b()) {
         p_70014_1_.func_74782_a("ArmorItem", this.field_110296_bG.func_70301_a(1).func_77955_b(new NBTTagCompound()));
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_110235_q(p_70037_1_.func_74762_e("Variant"));
      if (p_70037_1_.func_150297_b("ArmorItem", 10)) {
         ItemStack itemstack = new ItemStack(p_70037_1_.func_74775_l("ArmorItem"));
         if (!itemstack.func_190926_b() && HorseArmorType.func_188577_b(itemstack.func_77973_b())) {
            this.field_110296_bG.func_70299_a(1, itemstack);
         }
      }

      this.func_110232_cE();
   }

   public void func_110235_q(int p_110235_1_) {
      this.field_70180_af.func_187227_b(field_184789_bG, Integer.valueOf(p_110235_1_));
      this.func_110230_cF();
   }

   public int func_110202_bQ() {
      return ((Integer)this.field_70180_af.func_187225_a(field_184789_bG)).intValue();
   }

   private void func_110230_cF() {
      this.field_110286_bQ = null;
   }

   private void func_110247_cG() {
      int i = this.func_110202_bQ();
      int j = (i & 255) % 7;
      int k = ((i & '\uff00') >> 8) % 5;
      HorseArmorType horsearmortype = this.func_184783_dl();
      this.field_110280_bR[0] = field_110268_bz[j];
      this.field_110280_bR[1] = field_110291_bB[k];
      this.field_110280_bR[2] = horsearmortype.func_188574_d();
      this.field_110286_bQ = "horse/" + field_110269_bA[j] + field_110292_bC[k] + horsearmortype.func_188573_b();
   }

   public String func_110264_co() {
      if (this.field_110286_bQ == null) {
         this.func_110247_cG();
      }

      return this.field_110286_bQ;
   }

   public String[] func_110212_cp() {
      if (this.field_110286_bQ == null) {
         this.func_110247_cG();
      }

      return this.field_110280_bR;
   }

   protected void func_110232_cE() {
      super.func_110232_cE();
      this.func_146086_d(this.field_110296_bG.func_70301_a(1));
   }

   public void func_146086_d(ItemStack p_146086_1_) {
      HorseArmorType horsearmortype = HorseArmorType.func_188580_a(p_146086_1_);
      this.field_70180_af.func_187227_b(field_184791_bI, Integer.valueOf(horsearmortype.func_188579_a()));
      this.func_110230_cF();
      if (!this.field_70170_p.field_72995_K) {
         this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_188479_b(field_184786_bD);
         int i = horsearmortype.func_188578_c();
         if (i != 0) {
            this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111121_a((new AttributeModifier(field_184786_bD, "Horse armor bonus", (double)i, 0)).func_111168_a(false));
         }
      }

   }

   public HorseArmorType func_184783_dl() {
      return HorseArmorType.func_188575_a(((Integer)this.field_70180_af.func_187225_a(field_184791_bI)).intValue());
   }

   public void func_76316_a(IInventory p_76316_1_) {
      HorseArmorType horsearmortype = this.func_184783_dl();
      super.func_76316_a(p_76316_1_);
      HorseArmorType horsearmortype1 = this.func_184783_dl();
      if (this.field_70173_aa > 20 && horsearmortype != horsearmortype1 && horsearmortype1 != HorseArmorType.NONE) {
         this.func_184185_a(SoundEvents.field_187702_cm, 0.5F, 1.0F);
      }

   }

   protected void func_190680_a(SoundType p_190680_1_) {
      super.func_190680_a(p_190680_1_);
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_184185_a(SoundEvents.field_187705_cn, p_190680_1_.func_185843_a() * 0.6F, p_190680_1_.func_185847_b());
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.func_110267_cL());
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(this.func_110203_cN());
      this.func_110148_a(field_110271_bv).func_111128_a(this.func_110245_cM());
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K && this.field_70180_af.func_187223_a()) {
         this.field_70180_af.func_187230_e();
         this.func_110230_cF();
      }

   }

   protected SoundEvent func_184639_G() {
      super.func_184639_G();
      return SoundEvents.field_187696_ck;
   }

   protected SoundEvent func_184615_bR() {
      super.func_184615_bR();
      return SoundEvents.field_187708_co;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      super.func_184601_bQ(p_184601_1_);
      return SoundEvents.field_187717_cr;
   }

   protected SoundEvent func_184785_dv() {
      super.func_184785_dv();
      return SoundEvents.field_187699_cl;
   }

   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186396_D;
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      boolean flag = !itemstack.func_190926_b();
      if (flag && itemstack.func_77973_b() == Items.field_151063_bx) {
         return super.func_184645_a(p_184645_1_, p_184645_2_);
      } else {
         if (!this.func_70631_g_()) {
            if (this.func_110248_bS() && p_184645_1_.func_70093_af()) {
               this.func_110199_f(p_184645_1_);
               return true;
            }

            if (this.func_184207_aI()) {
               return super.func_184645_a(p_184645_1_, p_184645_2_);
            }
         }

         if (flag) {
            if (this.func_190678_b(p_184645_1_, itemstack)) {
               if (!p_184645_1_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }

               return true;
            }

            if (itemstack.func_111282_a(p_184645_1_, this, p_184645_2_)) {
               return true;
            }

            if (!this.func_110248_bS()) {
               this.func_190687_dF();
               return true;
            }

            boolean flag1 = HorseArmorType.func_188580_a(itemstack) != HorseArmorType.NONE;
            boolean flag2 = !this.func_70631_g_() && !this.func_110257_ck() && itemstack.func_77973_b() == Items.field_151141_av;
            if (flag1 || flag2) {
               this.func_110199_f(p_184645_1_);
               return true;
            }
         }

         if (this.func_70631_g_()) {
            return super.func_184645_a(p_184645_1_, p_184645_2_);
         } else {
            this.func_110237_h(p_184645_1_);
            return true;
         }
      }
   }

   public boolean func_70878_b(EntityAnimal p_70878_1_) {
      if (p_70878_1_ == this) {
         return false;
      } else if (!(p_70878_1_ instanceof EntityDonkey) && !(p_70878_1_ instanceof EntityHorse)) {
         return false;
      } else {
         return this.func_110200_cJ() && ((AbstractHorse)p_70878_1_).func_110200_cJ();
      }
   }

   public EntityAgeable func_90011_a(EntityAgeable p_90011_1_) {
      AbstractHorse abstracthorse;
      if (p_90011_1_ instanceof EntityDonkey) {
         abstracthorse = new EntityMule(this.field_70170_p);
      } else {
         EntityHorse entityhorse = (EntityHorse)p_90011_1_;
         abstracthorse = new EntityHorse(this.field_70170_p);
         int j = this.field_70146_Z.nextInt(9);
         int i;
         if (j < 4) {
            i = this.func_110202_bQ() & 255;
         } else if (j < 8) {
            i = entityhorse.func_110202_bQ() & 255;
         } else {
            i = this.field_70146_Z.nextInt(7);
         }

         int k = this.field_70146_Z.nextInt(5);
         if (k < 2) {
            i = i | this.func_110202_bQ() & '\uff00';
         } else if (k < 4) {
            i = i | entityhorse.func_110202_bQ() & '\uff00';
         } else {
            i = i | this.field_70146_Z.nextInt(5) << 8 & '\uff00';
         }

         ((EntityHorse)abstracthorse).func_110235_q(i);
      }

      this.func_190681_a(p_90011_1_, abstracthorse);
      return abstracthorse;
   }

   public boolean func_190677_dK() {
      return true;
   }

   public boolean func_190682_f(ItemStack p_190682_1_) {
      return HorseArmorType.func_188577_b(p_190682_1_.func_77973_b());
   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      int i;
      if (p_180482_2_ instanceof EntityHorse.GroupData) {
         i = ((EntityHorse.GroupData)p_180482_2_).field_190885_a;
      } else {
         i = this.field_70146_Z.nextInt(7);
         p_180482_2_ = new EntityHorse.GroupData(i);
      }

      this.func_110235_q(i | this.field_70146_Z.nextInt(5) << 8);
      return p_180482_2_;
   }

   public static class GroupData implements IEntityLivingData {
      public int field_190885_a;

      public GroupData(int p_i47337_1_) {
         this.field_190885_a = p_i47337_1_;
      }
   }
}
