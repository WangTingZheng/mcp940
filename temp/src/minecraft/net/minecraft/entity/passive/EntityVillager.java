package net.minecraft.entity.passive;

import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant {
   private static final Logger field_190674_bx = LogManager.getLogger();
   private static final DataParameter<Integer> field_184752_bw = EntityDataManager.<Integer>func_187226_a(EntityVillager.class, DataSerializers.field_187192_b);
   private int field_70955_e;
   private boolean field_70952_f;
   private boolean field_70953_g;
   Village field_70954_d;
   @Nullable
   private EntityPlayer field_70962_h;
   @Nullable
   private MerchantRecipeList field_70963_i;
   private int field_70961_j;
   private boolean field_70959_by;
   private boolean field_175565_bs;
   private int field_70956_bz;
   private String field_82189_bL;
   private int field_175563_bv;
   private int field_175562_bw;
   private boolean field_82190_bM;
   private boolean field_175564_by;
   private final InventoryBasic field_175560_bz;
   private static final EntityVillager.ITradeList[][][][] field_175561_bA = new EntityVillager.ITradeList[][][][]{{{{new EntityVillager.EmeraldForItems(Items.field_151015_O, new EntityVillager.PriceInfo(18, 22)), new EntityVillager.EmeraldForItems(Items.field_151174_bG, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.EmeraldForItems(Items.field_151172_bF, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.ListItemForEmeralds(Items.field_151025_P, new EntityVillager.PriceInfo(-4, -2))}, {new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150423_aK), new EntityVillager.PriceInfo(8, 13)), new EntityVillager.ListItemForEmeralds(Items.field_151158_bO, new EntityVillager.PriceInfo(-3, -2))}, {new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150440_ba), new EntityVillager.PriceInfo(7, 12)), new EntityVillager.ListItemForEmeralds(Items.field_151034_e, new EntityVillager.PriceInfo(-7, -5))}, {new EntityVillager.ListItemForEmeralds(Items.field_151106_aX, new EntityVillager.PriceInfo(-10, -6)), new EntityVillager.ListItemForEmeralds(Items.field_151105_aU, new EntityVillager.PriceInfo(1, 1))}}, {{new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ItemAndEmeraldToItem(Items.field_151115_aP, new EntityVillager.PriceInfo(6, 6), Items.field_179566_aV, new EntityVillager.PriceInfo(6, 6))}, {new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151112_aM, new EntityVillager.PriceInfo(7, 8))}}, {{new EntityVillager.EmeraldForItems(Item.func_150898_a(Blocks.field_150325_L), new EntityVillager.PriceInfo(16, 22)), new EntityVillager.ListItemForEmeralds(Items.field_151097_aZ, new EntityVillager.PriceInfo(3, 4))}, {new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L)), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 1), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 2), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 3), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 4), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 5), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 6), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 7), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 8), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 9), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 10), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 11), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 12), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 13), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 14), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, 15), new EntityVillager.PriceInfo(1, 2))}}, {{new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.ListItemForEmeralds(Items.field_151032_g, new EntityVillager.PriceInfo(-12, -8))}, {new EntityVillager.ListItemForEmeralds(Items.field_151031_f, new EntityVillager.PriceInfo(2, 3)), new EntityVillager.ItemAndEmeraldToItem(Item.func_150898_a(Blocks.field_150351_n), new EntityVillager.PriceInfo(10, 10), Items.field_151145_ak, new EntityVillager.PriceInfo(6, 10))}}}, {{{new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36)), new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.EmeraldForItems(Items.field_151122_aG, new EntityVillager.PriceInfo(8, 10)), new EntityVillager.ListItemForEmeralds(Items.field_151111_aL, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150342_X), new EntityVillager.PriceInfo(3, 4))}, {new EntityVillager.EmeraldForItems(Items.field_151164_bB, new EntityVillager.PriceInfo(2, 2)), new EntityVillager.ListItemForEmeralds(Items.field_151113_aN, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150359_w), new EntityVillager.PriceInfo(-5, -3))}, {new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.ListItemForEmeralds(Items.field_151057_cb, new EntityVillager.PriceInfo(20, 22))}}, {{new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36))}, {new EntityVillager.EmeraldForItems(Items.field_151111_aL, new EntityVillager.PriceInfo(1, 1))}, {new EntityVillager.ListItemForEmeralds(Items.field_151148_bJ, new EntityVillager.PriceInfo(7, 11))}, {new EntityVillager.TreasureMapForEmeralds(new EntityVillager.PriceInfo(12, 20), "Monument", MapDecoration.Type.MONUMENT), new EntityVillager.TreasureMapForEmeralds(new EntityVillager.PriceInfo(16, 28), "Mansion", MapDecoration.Type.MANSION)}}}, {{{new EntityVillager.EmeraldForItems(Items.field_151078_bh, new EntityVillager.PriceInfo(36, 40)), new EntityVillager.EmeraldForItems(Items.field_151043_k, new EntityVillager.PriceInfo(8, 10))}, {new EntityVillager.ListItemForEmeralds(Items.field_151137_ax, new EntityVillager.PriceInfo(-4, -1)), new EntityVillager.ListItemForEmeralds(new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.BLUE.func_176767_b()), new EntityVillager.PriceInfo(-2, -1))}, {new EntityVillager.ListItemForEmeralds(Items.field_151079_bi, new EntityVillager.PriceInfo(4, 7)), new EntityVillager.ListItemForEmeralds(Item.func_150898_a(Blocks.field_150426_aN), new EntityVillager.PriceInfo(-3, -1))}, {new EntityVillager.ListItemForEmeralds(Items.field_151062_by, new EntityVillager.PriceInfo(3, 11))}}}, {{{new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151028_Y, new EntityVillager.PriceInfo(4, 6))}, {new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListItemForEmeralds(Items.field_151030_Z, new EntityVillager.PriceInfo(10, 14))}, {new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151163_ad, new EntityVillager.PriceInfo(16, 19))}, {new EntityVillager.ListItemForEmeralds(Items.field_151029_X, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.field_151022_W, new EntityVillager.PriceInfo(9, 11)), new EntityVillager.ListItemForEmeralds(Items.field_151020_U, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.field_151023_V, new EntityVillager.PriceInfo(11, 15))}}, {{new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151036_c, new EntityVillager.PriceInfo(6, 8))}, {new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151040_l, new EntityVillager.PriceInfo(9, 10))}, {new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151048_u, new EntityVillager.PriceInfo(12, 15)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151056_x, new EntityVillager.PriceInfo(9, 12))}}, {{new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151037_a, new EntityVillager.PriceInfo(5, 7))}, {new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151035_b, new EntityVillager.PriceInfo(9, 11))}, {new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151046_w, new EntityVillager.PriceInfo(12, 15))}}}, {{{new EntityVillager.EmeraldForItems(Items.field_151147_al, new EntityVillager.PriceInfo(14, 18)), new EntityVillager.EmeraldForItems(Items.field_151076_bf, new EntityVillager.PriceInfo(14, 18))}, {new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.field_151157_am, new EntityVillager.PriceInfo(-7, -5)), new EntityVillager.ListItemForEmeralds(Items.field_151077_bg, new EntityVillager.PriceInfo(-8, -6))}}, {{new EntityVillager.EmeraldForItems(Items.field_151116_aA, new EntityVillager.PriceInfo(9, 12)), new EntityVillager.ListItemForEmeralds(Items.field_151026_S, new EntityVillager.PriceInfo(2, 4))}, {new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151027_R, new EntityVillager.PriceInfo(7, 12))}, {new EntityVillager.ListItemForEmeralds(Items.field_151141_av, new EntityVillager.PriceInfo(8, 10))}}}, {new EntityVillager.ITradeList[0][]}};

   public EntityVillager(World p_i1747_1_) {
      this(p_i1747_1_, 0);
   }

   public EntityVillager(World p_i1748_1_, int p_i1748_2_) {
      super(p_i1748_1_);
      this.field_175560_bz = new InventoryBasic("Items", false, 8);
      this.func_70938_b(p_i1748_2_);
      this.func_70105_a(0.6F, 1.95F);
      ((PathNavigateGround)this.func_70661_as()).func_179688_b(true);
      this.func_98053_h(true);
   }

   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
      this.field_70714_bg.func_75776_a(1, new EntityAITradePlayer(this));
      this.field_70714_bg.func_75776_a(1, new EntityAILookAtTradePlayer(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIMoveIndoors(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIRestrictOpenDoor(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
      this.field_70714_bg.func_75776_a(6, new EntityAIVillagerMate(this));
      this.field_70714_bg.func_75776_a(7, new EntityAIFollowGolem(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(9, new EntityAIVillagerInteract(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIWanderAvoidWater(this, 0.6D));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
   }

   private void func_175552_ct() {
      if (!this.field_175564_by) {
         this.field_175564_by = true;
         if (this.func_70631_g_()) {
            this.field_70714_bg.func_75776_a(8, new EntityAIPlay(this, 0.32D));
         } else if (this.func_70946_n() == 0) {
            this.field_70714_bg.func_75776_a(6, new EntityAIHarvestFarmland(this, 0.6D));
         }

      }
   }

   protected void func_175500_n() {
      if (this.func_70946_n() == 0) {
         this.field_70714_bg.func_75776_a(8, new EntityAIHarvestFarmland(this, 0.6D));
      }

      super.func_175500_n();
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
   }

   protected void func_70619_bc() {
      if (--this.field_70955_e <= 0) {
         BlockPos blockpos = new BlockPos(this);
         this.field_70170_p.func_175714_ae().func_176060_a(blockpos);
         this.field_70955_e = 70 + this.field_70146_Z.nextInt(50);
         this.field_70954_d = this.field_70170_p.func_175714_ae().func_176056_a(blockpos, 32);
         if (this.field_70954_d == null) {
            this.func_110177_bN();
         } else {
            BlockPos blockpos1 = this.field_70954_d.func_180608_a();
            this.func_175449_a(blockpos1, this.field_70954_d.func_75568_b());
            if (this.field_82190_bM) {
               this.field_82190_bM = false;
               this.field_70954_d.func_82683_b(5);
            }
         }
      }

      if (!this.func_70940_q() && this.field_70961_j > 0) {
         --this.field_70961_j;
         if (this.field_70961_j <= 0) {
            if (this.field_70959_by) {
               for(MerchantRecipe merchantrecipe : this.field_70963_i) {
                  if (merchantrecipe.func_82784_g()) {
                     merchantrecipe.func_82783_a(this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(6) + 2);
                  }
               }

               this.func_175554_cu();
               this.field_70959_by = false;
               if (this.field_70954_d != null && this.field_82189_bL != null) {
                  this.field_70170_p.func_72960_a(this, (byte)14);
                  this.field_70954_d.func_82688_a(this.field_82189_bL, 1);
               }
            }

            this.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200, 0));
         }
      }

      super.func_70619_bc();
   }

   public boolean func_184645_a(EntityPlayer p_184645_1_, EnumHand p_184645_2_) {
      ItemStack itemstack = p_184645_1_.func_184586_b(p_184645_2_);
      boolean flag = itemstack.func_77973_b() == Items.field_151057_cb;
      if (flag) {
         itemstack.func_111282_a(p_184645_1_, this, p_184645_2_);
         return true;
      } else if (!this.func_190669_a(itemstack, this.getClass()) && this.func_70089_S() && !this.func_70940_q() && !this.func_70631_g_()) {
         if (this.field_70963_i == null) {
            this.func_175554_cu();
         }

         if (p_184645_2_ == EnumHand.MAIN_HAND) {
            p_184645_1_.func_71029_a(StatList.field_188074_H);
         }

         if (!this.field_70170_p.field_72995_K && !this.field_70963_i.isEmpty()) {
            this.func_70932_a_(p_184645_1_);
            p_184645_1_.func_180472_a(this);
         } else if (this.field_70963_i.isEmpty()) {
            return super.func_184645_a(p_184645_1_, p_184645_2_);
         }

         return true;
      } else {
         return super.func_184645_a(p_184645_1_, p_184645_2_);
      }
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184752_bw, Integer.valueOf(0));
   }

   public static void func_189785_b(DataFixer p_189785_0_) {
      EntityLiving.func_189752_a(p_189785_0_, EntityVillager.class);
      p_189785_0_.func_188258_a(FixTypes.ENTITY, new ItemStackDataLists(EntityVillager.class, new String[]{"Inventory"}));
      p_189785_0_.func_188258_a(FixTypes.ENTITY, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (EntityList.func_191306_a(EntityVillager.class).equals(new ResourceLocation(p_188266_2_.func_74779_i("id"))) && p_188266_2_.func_150297_b("Offers", 10)) {
               NBTTagCompound nbttagcompound = p_188266_2_.func_74775_l("Offers");
               if (nbttagcompound.func_150297_b("Recipes", 9)) {
                  NBTTagList nbttaglist = nbttagcompound.func_150295_c("Recipes", 10);

                  for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                     NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
                     DataFixesManager.func_188277_a(p_188266_1_, nbttagcompound1, p_188266_3_, "buy");
                     DataFixesManager.func_188277_a(p_188266_1_, nbttagcompound1, p_188266_3_, "buyB");
                     DataFixesManager.func_188277_a(p_188266_1_, nbttagcompound1, p_188266_3_, "sell");
                     nbttaglist.func_150304_a(i, nbttagcompound1);
                  }
               }
            }

            return p_188266_2_;
         }
      });
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74768_a("Profession", this.func_70946_n());
      p_70014_1_.func_74768_a("Riches", this.field_70956_bz);
      p_70014_1_.func_74768_a("Career", this.field_175563_bv);
      p_70014_1_.func_74768_a("CareerLevel", this.field_175562_bw);
      p_70014_1_.func_74757_a("Willing", this.field_175565_bs);
      if (this.field_70963_i != null) {
         p_70014_1_.func_74782_a("Offers", this.field_70963_i.func_77202_a());
      }

      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_175560_bz.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            nbttaglist.func_74742_a(itemstack.func_77955_b(new NBTTagCompound()));
         }
      }

      p_70014_1_.func_74782_a("Inventory", nbttaglist);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_70938_b(p_70037_1_.func_74762_e("Profession"));
      this.field_70956_bz = p_70037_1_.func_74762_e("Riches");
      this.field_175563_bv = p_70037_1_.func_74762_e("Career");
      this.field_175562_bw = p_70037_1_.func_74762_e("CareerLevel");
      this.field_175565_bs = p_70037_1_.func_74767_n("Willing");
      if (p_70037_1_.func_150297_b("Offers", 10)) {
         NBTTagCompound nbttagcompound = p_70037_1_.func_74775_l("Offers");
         this.field_70963_i = new MerchantRecipeList(nbttagcompound);
      }

      NBTTagList nbttaglist = p_70037_1_.func_150295_c("Inventory", 10);

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         ItemStack itemstack = new ItemStack(nbttaglist.func_150305_b(i));
         if (!itemstack.func_190926_b()) {
            this.field_175560_bz.func_174894_a(itemstack);
         }
      }

      this.func_98053_h(true);
      this.func_175552_ct();
   }

   protected boolean func_70692_ba() {
      return false;
   }

   protected SoundEvent func_184639_G() {
      return this.func_70940_q() ? SoundEvents.field_187914_gn : SoundEvents.field_187910_gj;
   }

   protected SoundEvent func_184601_bQ(DamageSource p_184601_1_) {
      return SoundEvents.field_187912_gl;
   }

   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187911_gk;
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191184_at;
   }

   public void func_70938_b(int p_70938_1_) {
      this.field_70180_af.func_187227_b(field_184752_bw, Integer.valueOf(p_70938_1_));
   }

   public int func_70946_n() {
      return Math.max(((Integer)this.field_70180_af.func_187225_a(field_184752_bw)).intValue() % 6, 0);
   }

   public boolean func_70941_o() {
      return this.field_70952_f;
   }

   public void func_70947_e(boolean p_70947_1_) {
      this.field_70952_f = p_70947_1_;
   }

   public void func_70939_f(boolean p_70939_1_) {
      this.field_70953_g = p_70939_1_;
   }

   public boolean func_70945_p() {
      return this.field_70953_g;
   }

   public void func_70604_c(@Nullable EntityLivingBase p_70604_1_) {
      super.func_70604_c(p_70604_1_);
      if (this.field_70954_d != null && p_70604_1_ != null) {
         this.field_70954_d.func_75575_a(p_70604_1_);
         if (p_70604_1_ instanceof EntityPlayer) {
            int i = -1;
            if (this.func_70631_g_()) {
               i = -3;
            }

            this.field_70954_d.func_82688_a(p_70604_1_.func_70005_c_(), i);
            if (this.func_70089_S()) {
               this.field_70170_p.func_72960_a(this, (byte)13);
            }
         }
      }

   }

   public void func_70645_a(DamageSource p_70645_1_) {
      if (this.field_70954_d != null) {
         Entity entity = p_70645_1_.func_76346_g();
         if (entity != null) {
            if (entity instanceof EntityPlayer) {
               this.field_70954_d.func_82688_a(entity.func_70005_c_(), -2);
            } else if (entity instanceof IMob) {
               this.field_70954_d.func_82692_h();
            }
         } else {
            EntityPlayer entityplayer = this.field_70170_p.func_72890_a(this, 16.0D);
            if (entityplayer != null) {
               this.field_70954_d.func_82692_h();
            }
         }
      }

      super.func_70645_a(p_70645_1_);
   }

   public void func_70932_a_(@Nullable EntityPlayer p_70932_1_) {
      this.field_70962_h = p_70932_1_;
   }

   @Nullable
   public EntityPlayer func_70931_l_() {
      return this.field_70962_h;
   }

   public boolean func_70940_q() {
      return this.field_70962_h != null;
   }

   public boolean func_175550_n(boolean p_175550_1_) {
      if (!this.field_175565_bs && p_175550_1_ && this.func_175553_cp()) {
         boolean flag = false;

         for(int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
            ItemStack itemstack = this.field_175560_bz.func_70301_a(i);
            if (!itemstack.func_190926_b()) {
               if (itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3) {
                  flag = true;
                  this.field_175560_bz.func_70298_a(i, 3);
               } else if ((itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF) && itemstack.func_190916_E() >= 12) {
                  flag = true;
                  this.field_175560_bz.func_70298_a(i, 12);
               }
            }

            if (flag) {
               this.field_70170_p.func_72960_a(this, (byte)18);
               this.field_175565_bs = true;
               break;
            }
         }
      }

      return this.field_175565_bs;
   }

   public void func_175549_o(boolean p_175549_1_) {
      this.field_175565_bs = p_175549_1_;
   }

   public void func_70933_a(MerchantRecipe p_70933_1_) {
      p_70933_1_.func_77399_f();
      this.field_70757_a = -this.func_70627_aG();
      this.func_184185_a(SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
      int i = 3 + this.field_70146_Z.nextInt(4);
      if (p_70933_1_.func_180321_e() == 1 || this.field_70146_Z.nextInt(5) == 0) {
         this.field_70961_j = 40;
         this.field_70959_by = true;
         this.field_175565_bs = true;
         if (this.field_70962_h != null) {
            this.field_82189_bL = this.field_70962_h.func_70005_c_();
         } else {
            this.field_82189_bL = null;
         }

         i += 5;
      }

      if (p_70933_1_.func_77394_a().func_77973_b() == Items.field_151166_bC) {
         this.field_70956_bz += p_70933_1_.func_77394_a().func_190916_E();
      }

      if (p_70933_1_.func_180322_j()) {
         this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, i));
      }

      if (this.field_70962_h instanceof EntityPlayerMP) {
         CriteriaTriggers.field_192138_r.func_192234_a((EntityPlayerMP)this.field_70962_h, this, p_70933_1_.func_77397_d());
      }

   }

   public void func_110297_a_(ItemStack p_110297_1_) {
      if (!this.field_70170_p.field_72995_K && this.field_70757_a > -this.func_70627_aG() + 20) {
         this.field_70757_a = -this.func_70627_aG();
         this.func_184185_a(p_110297_1_.func_190926_b() ? SoundEvents.field_187913_gm : SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
      }

   }

   @Nullable
   public MerchantRecipeList func_70934_b(EntityPlayer p_70934_1_) {
      if (this.field_70963_i == null) {
         this.func_175554_cu();
      }

      return this.field_70963_i;
   }

   private void func_175554_cu() {
      EntityVillager.ITradeList[][][] aentityvillager$itradelist = field_175561_bA[this.func_70946_n()];
      if (this.field_175563_bv != 0 && this.field_175562_bw != 0) {
         ++this.field_175562_bw;
      } else {
         this.field_175563_bv = this.field_70146_Z.nextInt(aentityvillager$itradelist.length) + 1;
         this.field_175562_bw = 1;
      }

      if (this.field_70963_i == null) {
         this.field_70963_i = new MerchantRecipeList();
      }

      int i = this.field_175563_bv - 1;
      int j = this.field_175562_bw - 1;
      if (i >= 0 && i < aentityvillager$itradelist.length) {
         EntityVillager.ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];
         if (j >= 0 && j < aentityvillager$itradelist1.length) {
            EntityVillager.ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j];

            for(EntityVillager.ITradeList entityvillager$itradelist : aentityvillager$itradelist2) {
               entityvillager$itradelist.func_190888_a(this, this.field_70963_i, this.field_70146_Z);
            }
         }

      }
   }

   public void func_70930_a(@Nullable MerchantRecipeList p_70930_1_) {
   }

   public World func_190670_t_() {
      return this.field_70170_p;
   }

   public BlockPos func_190671_u_() {
      return new BlockPos(this);
   }

   public ITextComponent func_145748_c_() {
      Team team = this.func_96124_cp();
      String s = this.func_95999_t();
      if (s != null && !s.isEmpty()) {
         TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.func_96667_a(team, s));
         textcomponentstring.func_150256_b().func_150209_a(this.func_174823_aP());
         textcomponentstring.func_150256_b().func_179989_a(this.func_189512_bd());
         return textcomponentstring;
      } else {
         if (this.field_70963_i == null) {
            this.func_175554_cu();
         }

         String s1 = null;
         switch(this.func_70946_n()) {
         case 0:
            if (this.field_175563_bv == 1) {
               s1 = "farmer";
            } else if (this.field_175563_bv == 2) {
               s1 = "fisherman";
            } else if (this.field_175563_bv == 3) {
               s1 = "shepherd";
            } else if (this.field_175563_bv == 4) {
               s1 = "fletcher";
            }
            break;
         case 1:
            if (this.field_175563_bv == 1) {
               s1 = "librarian";
            } else if (this.field_175563_bv == 2) {
               s1 = "cartographer";
            }
            break;
         case 2:
            s1 = "cleric";
            break;
         case 3:
            if (this.field_175563_bv == 1) {
               s1 = "armor";
            } else if (this.field_175563_bv == 2) {
               s1 = "weapon";
            } else if (this.field_175563_bv == 3) {
               s1 = "tool";
            }
            break;
         case 4:
            if (this.field_175563_bv == 1) {
               s1 = "butcher";
            } else if (this.field_175563_bv == 2) {
               s1 = "leather";
            }
            break;
         case 5:
            s1 = "nitwit";
         }

         if (s1 != null) {
            ITextComponent itextcomponent = new TextComponentTranslation("entity.Villager." + s1, new Object[0]);
            itextcomponent.func_150256_b().func_150209_a(this.func_174823_aP());
            itextcomponent.func_150256_b().func_179989_a(this.func_189512_bd());
            if (team != null) {
               itextcomponent.func_150256_b().func_150238_a(team.func_178775_l());
            }

            return itextcomponent;
         } else {
            return super.func_145748_c_();
         }
      }
   }

   public float func_70047_e() {
      return this.func_70631_g_() ? 0.81F : 1.62F;
   }

   public void func_70103_a(byte p_70103_1_) {
      if (p_70103_1_ == 12) {
         this.func_180489_a(EnumParticleTypes.HEART);
      } else if (p_70103_1_ == 13) {
         this.func_180489_a(EnumParticleTypes.VILLAGER_ANGRY);
      } else if (p_70103_1_ == 14) {
         this.func_180489_a(EnumParticleTypes.VILLAGER_HAPPY);
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   private void func_180489_a(EnumParticleTypes p_180489_1_) {
      for(int i = 0; i < 5; ++i) {
         double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
         double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
         double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
         this.field_70170_p.func_175688_a(p_180489_1_, this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 1.0D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d0, d1, d2);
      }

   }

   @Nullable
   public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, @Nullable IEntityLivingData p_180482_2_) {
      return this.func_190672_a(p_180482_1_, p_180482_2_, true);
   }

   public IEntityLivingData func_190672_a(DifficultyInstance p_190672_1_, @Nullable IEntityLivingData p_190672_2_, boolean p_190672_3_) {
      p_190672_2_ = super.func_180482_a(p_190672_1_, p_190672_2_);
      if (p_190672_3_) {
         this.func_70938_b(this.field_70170_p.field_73012_v.nextInt(6));
      }

      this.func_175552_ct();
      this.func_175554_cu();
      return p_190672_2_;
   }

   public void func_82187_q() {
      this.field_82190_bM = true;
   }

   public EntityVillager func_90011_a(EntityAgeable p_90011_1_) {
      EntityVillager entityvillager = new EntityVillager(this.field_70170_p);
      entityvillager.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entityvillager)), (IEntityLivingData)null);
      return entityvillager;
   }

   public boolean func_184652_a(EntityPlayer p_184652_1_) {
      return false;
   }

   public void func_70077_a(EntityLightningBolt p_70077_1_) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         EntityWitch entitywitch = new EntityWitch(this.field_70170_p);
         entitywitch.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         entitywitch.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(entitywitch)), (IEntityLivingData)null);
         entitywitch.func_94061_f(this.func_175446_cd());
         if (this.func_145818_k_()) {
            entitywitch.func_96094_a(this.func_95999_t());
            entitywitch.func_174805_g(this.func_174833_aM());
         }

         this.field_70170_p.func_72838_d(entitywitch);
         this.func_70106_y();
      }
   }

   public InventoryBasic func_175551_co() {
      return this.field_175560_bz;
   }

   protected void func_175445_a(EntityItem p_175445_1_) {
      ItemStack itemstack = p_175445_1_.func_92059_d();
      Item item = itemstack.func_77973_b();
      if (this.func_175558_a(item)) {
         ItemStack itemstack1 = this.field_175560_bz.func_174894_a(itemstack);
         if (itemstack1.func_190926_b()) {
            p_175445_1_.func_70106_y();
         } else {
            itemstack.func_190920_e(itemstack1.func_190916_E());
         }
      }

   }

   private boolean func_175558_a(Item p_175558_1_) {
      return p_175558_1_ == Items.field_151025_P || p_175558_1_ == Items.field_151174_bG || p_175558_1_ == Items.field_151172_bF || p_175558_1_ == Items.field_151015_O || p_175558_1_ == Items.field_151014_N || p_175558_1_ == Items.field_185164_cV || p_175558_1_ == Items.field_185163_cU;
   }

   public boolean func_175553_cp() {
      return this.func_175559_s(1);
   }

   public boolean func_175555_cq() {
      return this.func_175559_s(2);
   }

   public boolean func_175557_cr() {
      boolean flag = this.func_70946_n() == 0;
      if (flag) {
         return !this.func_175559_s(5);
      } else {
         return !this.func_175559_s(1);
      }
   }

   private boolean func_175559_s(int p_175559_1_) {
      boolean flag = this.func_70946_n() == 0;

      for(int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_175560_bz.func_70301_a(i);
         if (!itemstack.func_190926_b()) {
            if (itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3 * p_175559_1_ || itemstack.func_77973_b() == Items.field_151174_bG && itemstack.func_190916_E() >= 12 * p_175559_1_ || itemstack.func_77973_b() == Items.field_151172_bF && itemstack.func_190916_E() >= 12 * p_175559_1_ || itemstack.func_77973_b() == Items.field_185164_cV && itemstack.func_190916_E() >= 12 * p_175559_1_) {
               return true;
            }

            if (flag && itemstack.func_77973_b() == Items.field_151015_O && itemstack.func_190916_E() >= 9 * p_175559_1_) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean func_175556_cs() {
      for(int i = 0; i < this.field_175560_bz.func_70302_i_(); ++i) {
         ItemStack itemstack = this.field_175560_bz.func_70301_a(i);
         if (!itemstack.func_190926_b() && (itemstack.func_77973_b() == Items.field_151014_N || itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF || itemstack.func_77973_b() == Items.field_185163_cU)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
      if (super.func_174820_d(p_174820_1_, p_174820_2_)) {
         return true;
      } else {
         int i = p_174820_1_ - 300;
         if (i >= 0 && i < this.field_175560_bz.func_70302_i_()) {
            this.field_175560_bz.func_70299_a(i, p_174820_2_);
            return true;
         } else {
            return false;
         }
      }
   }

   static class EmeraldForItems implements EntityVillager.ITradeList {
      public Item field_179405_a;
      public EntityVillager.PriceInfo field_179404_b;

      public EmeraldForItems(Item p_i45815_1_, EntityVillager.PriceInfo p_i45815_2_) {
         this.field_179405_a = p_i45815_1_;
         this.field_179404_b = p_i45815_2_;
      }

      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         int i = 1;
         if (this.field_179404_b != null) {
            i = this.field_179404_b.func_179412_a(p_190888_3_);
         }

         p_190888_2_.add(new MerchantRecipe(new ItemStack(this.field_179405_a, i, 0), Items.field_151166_bC));
      }
   }

   interface ITradeList {
      void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3);
   }

   static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {
      public ItemStack field_179411_a;
      public EntityVillager.PriceInfo field_179409_b;
      public ItemStack field_179410_c;
      public EntityVillager.PriceInfo field_179408_d;

      public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
         this.field_179411_a = new ItemStack(p_i45813_1_);
         this.field_179409_b = p_i45813_2_;
         this.field_179410_c = new ItemStack(p_i45813_3_);
         this.field_179408_d = p_i45813_4_;
      }

      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         int i = this.field_179409_b.func_179412_a(p_190888_3_);
         int j = this.field_179408_d.func_179412_a(p_190888_3_);
         p_190888_2_.add(new MerchantRecipe(new ItemStack(this.field_179411_a.func_77973_b(), i, this.field_179411_a.func_77960_j()), new ItemStack(Items.field_151166_bC), new ItemStack(this.field_179410_c.func_77973_b(), j, this.field_179410_c.func_77960_j())));
      }
   }

   static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         Enchantment enchantment = (Enchantment)Enchantment.field_185264_b.func_186801_a(p_190888_3_);
         int i = MathHelper.func_76136_a(p_190888_3_, enchantment.func_77319_d(), enchantment.func_77325_b());
         ItemStack itemstack = ItemEnchantedBook.func_92111_a(new EnchantmentData(enchantment, i));
         int j = 2 + p_190888_3_.nextInt(5 + i * 10) + 3 * i;
         if (enchantment.func_185261_e()) {
            j *= 2;
         }

         if (j > 64) {
            j = 64;
         }

         p_190888_2_.add(new MerchantRecipe(new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151166_bC, j), itemstack));
      }
   }

   static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack field_179407_a;
      public EntityVillager.PriceInfo field_179406_b;

      public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
         this.field_179407_a = new ItemStack(p_i45814_1_);
         this.field_179406_b = p_i45814_2_;
      }

      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         int i = 1;
         if (this.field_179406_b != null) {
            i = this.field_179406_b.func_179412_a(p_190888_3_);
         }

         ItemStack itemstack = new ItemStack(Items.field_151166_bC, i, 0);
         ItemStack itemstack1 = EnchantmentHelper.func_77504_a(p_190888_3_, new ItemStack(this.field_179407_a.func_77973_b(), 1, this.field_179407_a.func_77960_j()), 5 + p_190888_3_.nextInt(15), false);
         p_190888_2_.add(new MerchantRecipe(itemstack, itemstack1));
      }
   }

   static class ListItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack field_179403_a;
      public EntityVillager.PriceInfo field_179402_b;

      public ListItemForEmeralds(Item p_i45811_1_, EntityVillager.PriceInfo p_i45811_2_) {
         this.field_179403_a = new ItemStack(p_i45811_1_);
         this.field_179402_b = p_i45811_2_;
      }

      public ListItemForEmeralds(ItemStack p_i45812_1_, EntityVillager.PriceInfo p_i45812_2_) {
         this.field_179403_a = p_i45812_1_;
         this.field_179402_b = p_i45812_2_;
      }

      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         int i = 1;
         if (this.field_179402_b != null) {
            i = this.field_179402_b.func_179412_a(p_190888_3_);
         }

         ItemStack itemstack;
         ItemStack itemstack1;
         if (i < 0) {
            itemstack = new ItemStack(Items.field_151166_bC);
            itemstack1 = new ItemStack(this.field_179403_a.func_77973_b(), -i, this.field_179403_a.func_77960_j());
         } else {
            itemstack = new ItemStack(Items.field_151166_bC, i, 0);
            itemstack1 = new ItemStack(this.field_179403_a.func_77973_b(), 1, this.field_179403_a.func_77960_j());
         }

         p_190888_2_.add(new MerchantRecipe(itemstack, itemstack1));
      }
   }

   static class PriceInfo extends Tuple<Integer, Integer> {
      public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
         super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
         if (p_i45810_2_ < p_i45810_1_) {
            EntityVillager.field_190674_bx.warn("PriceRange({}, {}) invalid, {} smaller than {}", Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_), Integer.valueOf(p_i45810_2_), Integer.valueOf(p_i45810_1_));
         }

      }

      public int func_179412_a(Random p_179412_1_) {
         return ((Integer)this.func_76341_a()).intValue() >= ((Integer)this.func_76340_b()).intValue() ? ((Integer)this.func_76341_a()).intValue() : ((Integer)this.func_76341_a()).intValue() + p_179412_1_.nextInt(((Integer)this.func_76340_b()).intValue() - ((Integer)this.func_76341_a()).intValue() + 1);
      }
   }

   static class TreasureMapForEmeralds implements EntityVillager.ITradeList {
      public EntityVillager.PriceInfo field_190889_a;
      public String field_190890_b;
      public MapDecoration.Type field_190891_c;

      public TreasureMapForEmeralds(EntityVillager.PriceInfo p_i47340_1_, String p_i47340_2_, MapDecoration.Type p_i47340_3_) {
         this.field_190889_a = p_i47340_1_;
         this.field_190890_b = p_i47340_2_;
         this.field_190891_c = p_i47340_3_;
      }

      public void func_190888_a(IMerchant p_190888_1_, MerchantRecipeList p_190888_2_, Random p_190888_3_) {
         int i = this.field_190889_a.func_179412_a(p_190888_3_);
         World world = p_190888_1_.func_190670_t_();
         BlockPos blockpos = world.func_190528_a(this.field_190890_b, p_190888_1_.func_190671_u_(), true);
         if (blockpos != null) {
            ItemStack itemstack = ItemMap.func_190906_a(world, (double)blockpos.func_177958_n(), (double)blockpos.func_177952_p(), (byte)2, true, true);
            ItemMap.func_190905_a(world, itemstack);
            MapData.func_191094_a(itemstack, blockpos, "+", this.field_190891_c);
            itemstack.func_190924_f("filled_map." + this.field_190890_b.toLowerCase(Locale.ROOT));
            p_190888_2_.add(new MerchantRecipe(new ItemStack(Items.field_151166_bC, i), new ItemStack(Items.field_151111_aL), itemstack));
         }

      }
   }
}
