package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySheep extends EntityAnimal {
   private static final DataParameter<Byte> field_184774_bv = EntityDataManager.<Byte>func_187226_a(EntitySheep.class, DataSerializers.field_187191_a);
   private final InventoryCrafting field_90016_e = new InventoryCrafting(new Container() {
      public boolean func_75145_c(EntityPlayer p_75145_1_) {
         return false;
      }
   }, 2, 1);
   private static final Map<EnumDyeColor, float[]> field_175514_bm = Maps.newEnumMap(EnumDyeColor.class);
   private int field_70899_e;
   private EntityAIEatGrass field_146087_bs;

   private static float[] func_192020_c(EnumDyeColor p_192020_0_) {
      float[] afloat = p_192020_0_.func_193349_f();
      float f = 0.75F;
      return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
   }

   public static float[] func_175513_a(EnumDyeColor p_175513_0_) {
      return field_175514_bm.get(p_175513_0_);
   }

   public EntitySheep(World p_i1691_1_) {
      super(p_i1691_1_);
      this.func_70105_a(0.9F, 1.3F);
      this.field_90016_e.func_70299_a(0, new ItemStack(Items.field_151100_aR));
      this.field_90016_e.func_70299_a(1, new ItemStack(Items.field_151100_aR));
   }

   protected void func_184651_r() {
      this.field_146087_bs = new EntityAIEatGrass(this);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.25D));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.1D, Items.field_151015_O, false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.1D));
      this.field_70714_bg.func_75776_a(5, this.field_146087_bs);
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   protected void func_70619_bc() {
      this.field_70899_e = this.field_146087_bs.func_151499_f();
      super.func_70619_bc();
   }

   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.field_70899_e = Math.max(0, this.field_70899_e - 1);
      }

      super.func_70636_d();
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184774_bv, Byte.valueOf((byte)0));
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      if (this.func_70892_o()) {
         return LootTableList.field_186403_K;
      } else {
         switch(this.func_175509_cj()) {
         case WHITE:
         default:
            return LootTableList.field_186404_L;
         case ORANGE:
            return LootTableList.field_186405_M;
         case MAGENTA:
            return LootTableList.field_186406_N;
         case LIGHT_BLUE:
            return LootTableList.field_186407_O;
         case YELLOW:
            return LootTableList.field_186408_P;
         case LIME:
            return LootTableList.field_186409_Q;
         case PINK:
            return LootTableList.field_186410_R;
         case GRAY:
            return LootTableList.field_186411_S;
         case SILVER:
            return LootTableList.field_186412_T;
         case CYAN:
            return LootTableList.field_186413_U;
         case PURPLE:
            return LootTableList.field_186414_V;
         case BLUE:
            return LootTableList.field_186415_W;
         case BROWN:
            return LootTableList.field_186416_X;
         case GREEN:
            return LootTableList.field_186417_Y;
         case RED:
            return LootTableList.field_186418_Z;
         case BLACK:
            return LootTableList.field_186376_aa;
         }
      }
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 10) {
         this.field_70899_e = 40;
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   public float func_70894_j(float p_70894_1_) {
      if (this.field_70899_e <= 0) {
         return 0.0F;
      } else if (this.field_70899_e >= 4 && this.field_70899_e <= 36) {
         return 1.0F;
      } else {
         return this.field_70899_e < 4 ? ((float)this.field_70899_e - p_70894_1_) / 4.0F : -((float)(this.field_70899_e - 40) - p_70894_1_) / 4.0F;
      }
   }

   public float func_70890_k(float p_70890_1_) {
      if (this.field_70899_e > 4 && this.field_70899_e <= 36) {
         float f = ((float)(this.field_70899_e - 4) - p_70890_1_) / 32.0F;
         return 0.62831855F + 0.21991149F * MathHelper.func_76126_a(f * 28.7F);
      } else {
         return this.field_70899_e > 0 ? 0.62831855F : this.field_70125_A * 0.017453292F;
      }
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      if (itemstack.func_77973_b() == Items.field_151097_aZ && !this.func_70892_o() && !this.func_70631_g_()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_70893_e(true);
            int i = 1 + this.field_70146_Z.nextInt(3);

            for(int j = 0; j < i; ++j) {
               EntityItem entityitem = this.func_70099_a(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, this.func_175509_cj().func_176765_a()), 1.0F);
               entityitem.field_70181_x += (double)(this.field_70146_Z.nextFloat() * 0.05F);
               entityitem.field_70159_w += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
               entityitem.field_70179_y += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
            }
         }

         itemstack.func_77972_a(1, p_184645_1_);
         this.func_184185_a(SoundEvents.field_187763_eJ, 1.0F, 1.0F);
      }

      return super.func_184645_a(p_184645_1_, p_184645_2_);
   }

   public static void func_189802_b(DataFixer p_189802_0_) {
      EntityLiving.func_189752_a(p_189802_0_, EntitySheep.class);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("Sheared", this.func_70892_o());
      p_70014_1_.func_74774_a("Color", (byte)this.func_175509_cj().func_176765_a());
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_70893_e(p_70037_1_.func_74767_n("Sheared"));
      this.func_175512_b(EnumDyeColor.func_176764_b(p_70037_1_.func_74771_c("Color")));
   }

   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187757_eG;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187761_eI;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187759_eH;
   }

   protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
      this.func_184185_a(SoundEvents.field_187765_eK, 0.15F, 1.0F);
   }

   public EnumDyeColor func_175509_cj() {
      return EnumDyeColor.func_176764_b(((Byte)this.field_70180_af.func_187225_a(field_184774_bv)).byteValue() & 15);
   }

   public void func_175512_b(EnumDyeColor p_175512_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184774_bv)).byteValue();
      this.field_70180_af.func_187227_b(field_184774_bv, Byte.valueOf((byte)(b0 & 240 | p_175512_1_.func_176765_a() & 15)));
   }

   public boolean func_70892_o() {
      return (((Byte)this.field_70180_af.func_187225_a(field_184774_bv)).byteValue() & 16) != 0;
   }

   public void func_70893_e(boolean p_70893_1_) {
      byte b0 = ((Byte)this.field_70180_af.func_187225_a(field_184774_bv)).byteValue();
      if (p_70893_1_) {
         this.field_70180_af.func_187227_b(field_184774_bv, Byte.valueOf((byte)(b0 | 16)));
      } else {
         this.field_70180_af.func_187227_b(field_184774_bv, Byte.valueOf((byte)(b0 & -17)));
      }

   }

   public static EnumDyeColor func_175510_a(Random p_175510_0_) {
      int i = p_175510_0_.nextInt(100);
      if (i < 5) {
         return EnumDyeColor.BLACK;
      } else if (i < 10) {
         return EnumDyeColor.GRAY;
      } else if (i < 15) {
         return EnumDyeColor.SILVER;
      } else if (i < 18) {
         return EnumDyeColor.BROWN;
      } else {
         return p_175510_0_.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE;
      }
   }

   public EntitySheep func_90011_a(EntityAgeable p_90011_1_) {
      EntitySheep entitysheep = (EntitySheep)p_90011_1_;
      EntitySheep entitysheep1 = new EntitySheep(this.field_70170_p);
      entitysheep1.func_175512_b(this.func_175511_a(this, entitysheep));
      return entitysheep1;
   }

   public void func_70615_aA() {
      this.func_70893_e(false);
      if (this.func_70631_g_()) {
         this.func_110195_a(60);
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
      this.func_175512_b(func_175510_a(this.field_70170_p.field_73012_v));
      return p_180482_2_;
   }

   private EnumDyeColor func_175511_a(EntityAnimal p_175511_1_, EntityAnimal p_175511_2_) {
      int i = ((EntitySheep)p_175511_1_).func_175509_cj().func_176767_b();
      int j = ((EntitySheep)p_175511_2_).func_175509_cj().func_176767_b();
      this.field_90016_e.func_70301_a(0).func_77964_b(i);
      this.field_90016_e.func_70301_a(1).func_77964_b(j);
      ItemStack itemstack = CraftingManager.func_82787_a(this.field_90016_e, ((EntitySheep)p_175511_1_).field_70170_p);
      int k;
      if (itemstack.func_77973_b() == Items.field_151100_aR) {
         k = itemstack.func_77960_j();
      } else {
         k = this.field_70170_p.field_73012_v.nextBoolean() ? i : j;
      }

      return EnumDyeColor.func_176766_a(k);
   }

   public float func_70047_e() {
      return 0.95F * this.field_70131_O;
   }

   static {
      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         field_175514_bm.put(enumdyecolor, func_192020_c(enumdyecolor));
      }

      field_175514_bm.put(EnumDyeColor.WHITE, new float[]{0.9019608F, 0.9019608F, 0.9019608F});
   }
}
