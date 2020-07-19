package net.minecraft.init;

import com.mojang.authlib.GameProfile;
import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.server.DebugLoggingPrintStream;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   public static final PrintStream field_179872_a = System.out;
   private static boolean field_151355_a;
   public static boolean field_194219_b;
   private static final Logger field_179871_c = LogManager.getLogger();

   public static boolean func_179869_a() {
      return field_151355_a;
   }

   static void func_151353_a() {
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151032_g, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            EntityTippedArrow entitytippedarrow = new EntityTippedArrow(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
            entitytippedarrow.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return entitytippedarrow;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185167_i, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            EntityTippedArrow entitytippedarrow = new EntityTippedArrow(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
            entitytippedarrow.func_184555_a(p_82499_3_);
            entitytippedarrow.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return entitytippedarrow;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185166_h, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            EntityArrow entityarrow = new EntitySpectralArrow(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
            entityarrow.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return entityarrow;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151110_aK, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return new EntityEgg(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151126_ay, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return new EntitySnowball(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151062_by, new BehaviorProjectileDispense() {
         protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
            return new EntityExpBottle(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c());
         }

         protected float func_82498_a() {
            return super.func_82498_a() * 0.5F;
         }

         protected float func_82500_b() {
            return super.func_82500_b() * 1.25F;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185155_bH, new IBehaviorDispenseItem() {
         public ItemStack func_82482_a(IBlockSource p_82482_1_, final ItemStack p_82482_2_) {
            return (new BehaviorProjectileDispense() {
               protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
                  return new EntityPotion(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c(), p_82482_2_.func_77946_l());
               }

               protected float func_82498_a() {
                  return super.func_82498_a() * 0.5F;
               }

               protected float func_82500_b() {
                  return super.func_82500_b() * 1.25F;
               }
            }).func_82482_a(p_82482_1_, p_82482_2_);
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185156_bI, new IBehaviorDispenseItem() {
         public ItemStack func_82482_a(IBlockSource p_82482_1_, final ItemStack p_82482_2_) {
            return (new BehaviorProjectileDispense() {
               protected IProjectile func_82499_a(World p_82499_1_, IPosition p_82499_2_, ItemStack p_82499_3_) {
                  return new EntityPotion(p_82499_1_, p_82499_2_.func_82615_a(), p_82499_2_.func_82617_b(), p_82499_2_.func_82616_c(), p_82482_2_.func_77946_l());
               }

               protected float func_82498_a() {
                  return super.func_82498_a() * 0.5F;
               }

               protected float func_82500_b() {
                  return super.func_82500_b() * 1.25F;
               }
            }).func_82482_a(p_82482_1_, p_82482_2_);
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151063_bx, new BehaviorDefaultDispenseItem() {
         public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            double d0 = p_82487_1_.func_82615_a() + (double)enumfacing.func_82601_c();
            double d1 = (double)((float)(p_82487_1_.func_180699_d().func_177956_o() + enumfacing.func_96559_d()) + 0.2F);
            double d2 = p_82487_1_.func_82616_c() + (double)enumfacing.func_82599_e();
            Entity entity = ItemMonsterPlacer.func_77840_a(p_82487_1_.func_82618_k(), ItemMonsterPlacer.func_190908_h(p_82487_2_), d0, d1, d2);
            if (entity instanceof EntityLivingBase && p_82487_2_.func_82837_s()) {
               entity.func_96094_a(p_82487_2_.func_82833_r());
            }

            ItemMonsterPlacer.func_185079_a(p_82487_1_.func_82618_k(), (EntityPlayer)null, p_82487_2_, entity);
            p_82487_2_.func_190918_g(1);
            return p_82487_2_;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151152_bP, new BehaviorDefaultDispenseItem() {
         public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            double d0 = p_82487_1_.func_82615_a() + (double)enumfacing.func_82601_c();
            double d1 = (double)((float)p_82487_1_.func_180699_d().func_177956_o() + 0.2F);
            double d2 = p_82487_1_.func_82616_c() + (double)enumfacing.func_82599_e();
            EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(p_82487_1_.func_82618_k(), d0, d1, d2, p_82487_2_);
            p_82487_1_.func_82618_k().func_72838_d(entityfireworkrocket);
            p_82487_2_.func_190918_g(1);
            return p_82487_2_;
         }

         protected void func_82485_a(IBlockSource p_82485_1_) {
            p_82485_1_.func_82618_k().func_175718_b(1004, p_82485_1_.func_180699_d(), 0);
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151059_bz, new BehaviorDefaultDispenseItem() {
         public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            IPosition iposition = BlockDispenser.func_149939_a(p_82487_1_);
            double d0 = iposition.func_82615_a() + (double)((float)enumfacing.func_82601_c() * 0.3F);
            double d1 = iposition.func_82617_b() + (double)((float)enumfacing.func_96559_d() * 0.3F);
            double d2 = iposition.func_82616_c() + (double)((float)enumfacing.func_82599_e() * 0.3F);
            World world = p_82487_1_.func_82618_k();
            Random random = world.field_73012_v;
            double d3 = random.nextGaussian() * 0.05D + (double)enumfacing.func_82601_c();
            double d4 = random.nextGaussian() * 0.05D + (double)enumfacing.func_96559_d();
            double d5 = random.nextGaussian() * 0.05D + (double)enumfacing.func_82599_e();
            world.func_72838_d(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
            p_82487_2_.func_190918_g(1);
            return p_82487_2_;
         }

         protected void func_82485_a(IBlockSource p_82485_1_) {
            p_82485_1_.func_82618_k().func_175718_b(1018, p_82485_1_.func_180699_d(), 0);
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151124_az, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.OAK));
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185150_aH, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.SPRUCE));
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185151_aI, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.BIRCH));
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185152_aJ, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.JUNGLE));
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185154_aL, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.DARK_OAK));
      BlockDispenser.field_149943_a.func_82595_a(Items.field_185153_aK, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.ACACIA));
      IBehaviorDispenseItem ibehaviordispenseitem = new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

         public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            ItemBucket itembucket = (ItemBucket)p_82487_2_.func_77973_b();
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            return itembucket.func_180616_a((EntityPlayer)null, p_82487_1_.func_82618_k(), blockpos) ? new ItemStack(Items.field_151133_ar) : this.field_150841_b.func_82482_a(p_82487_1_, p_82487_2_);
         }
      };
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151129_at, ibehaviordispenseitem);
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151131_as, ibehaviordispenseitem);
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151133_ar, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();

         public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.func_82618_k();
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            IBlockState iblockstate = world.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            Material material = iblockstate.func_185904_a();
            Item item;
            if (Material.field_151586_h.equals(material) && block instanceof BlockLiquid && ((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() == 0) {
               item = Items.field_151131_as;
            } else {
               if (!Material.field_151587_i.equals(material) || !(block instanceof BlockLiquid) || ((Integer)iblockstate.func_177229_b(BlockLiquid.field_176367_b)).intValue() != 0) {
                  return super.func_82487_b(p_82487_1_, p_82487_2_);
               }

               item = Items.field_151129_at;
            }

            world.func_175698_g(blockpos);
            p_82487_2_.func_190918_g(1);
            if (p_82487_2_.func_190926_b()) {
               return new ItemStack(item);
            } else {
               if (((TileEntityDispenser)p_82487_1_.func_150835_j()).func_146019_a(new ItemStack(item)) < 0) {
                  this.field_150840_b.func_82482_a(p_82487_1_, new ItemStack(item));
               }

               return p_82487_2_;
            }
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151033_d, new Bootstrap.BehaviorDispenseOptional() {
         protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.func_82618_k();
            this.field_190911_b = true;
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            if (world.func_175623_d(blockpos)) {
               world.func_175656_a(blockpos, Blocks.field_150480_ab.func_176223_P());
               if (p_82487_2_.func_96631_a(1, world.field_73012_v, (EntityPlayerMP)null)) {
                  p_82487_2_.func_190920_e(0);
               }
            } else if (world.func_180495_p(blockpos).func_177230_c() == Blocks.field_150335_W) {
               Blocks.field_150335_W.func_176206_d(world, blockpos, Blocks.field_150335_W.func_176223_P().func_177226_a(BlockTNT.field_176246_a, Boolean.valueOf(true)));
               world.func_175698_g(blockpos);
            } else {
               this.field_190911_b = false;
            }

            return p_82487_2_;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151100_aR, new Bootstrap.BehaviorDispenseOptional() {
         protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            this.field_190911_b = true;
            if (EnumDyeColor.WHITE == EnumDyeColor.func_176766_a(p_82487_2_.func_77960_j())) {
               World world = p_82487_1_.func_82618_k();
               BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
               if (ItemDye.func_179234_a(p_82487_2_, world, blockpos)) {
                  if (!world.field_72995_K) {
                     world.func_175718_b(2005, blockpos, 0);
                  }
               } else {
                  this.field_190911_b = false;
               }

               return p_82487_2_;
            } else {
               return super.func_82487_b(p_82487_1_, p_82487_2_);
            }
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Item.func_150898_a(Blocks.field_150335_W), new BehaviorDefaultDispenseItem() {
         protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.func_82618_k();
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p() + 0.5D, (EntityLivingBase)null);
            world.func_72838_d(entitytntprimed);
            world.func_184148_a((EntityPlayer)null, entitytntprimed.field_70165_t, entitytntprimed.field_70163_u, entitytntprimed.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
            p_82487_2_.func_190918_g(1);
            return p_82487_2_;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Items.field_151144_bL, new Bootstrap.BehaviorDispenseOptional() {
         protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.func_82618_k();
            EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a(enumfacing);
            BlockSkull blockskull = Blocks.field_150465_bP;
            this.field_190911_b = true;
            if (world.func_175623_d(blockpos) && blockskull.func_176415_b(world, blockpos, p_82487_2_)) {
               if (!world.field_72995_K) {
                  world.func_180501_a(blockpos, blockskull.func_176223_P().func_177226_a(BlockSkull.field_176418_a, EnumFacing.UP), 3);
                  TileEntity tileentity = world.func_175625_s(blockpos);
                  if (tileentity instanceof TileEntitySkull) {
                     if (p_82487_2_.func_77960_j() == 3) {
                        GameProfile gameprofile = null;
                        if (p_82487_2_.func_77942_o()) {
                           NBTTagCompound nbttagcompound = p_82487_2_.func_77978_p();
                           if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
                              gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
                           } else if (nbttagcompound.func_150297_b("SkullOwner", 8)) {
                              String s = nbttagcompound.func_74779_i("SkullOwner");
                              if (!StringUtils.func_151246_b(s)) {
                                 gameprofile = new GameProfile((UUID)null, s);
                              }
                           }
                        }

                        ((TileEntitySkull)tileentity).func_152106_a(gameprofile);
                     } else {
                        ((TileEntitySkull)tileentity).func_152107_a(p_82487_2_.func_77960_j());
                     }

                     ((TileEntitySkull)tileentity).func_145903_a(enumfacing.func_176734_d().func_176736_b() * 4);
                     Blocks.field_150465_bP.func_180679_a(world, blockpos, (TileEntitySkull)tileentity);
                  }

                  p_82487_2_.func_190918_g(1);
               }
            } else if (ItemArmor.func_185082_a(p_82487_1_, p_82487_2_).func_190926_b()) {
               this.field_190911_b = false;
            }

            return p_82487_2_;
         }
      });
      BlockDispenser.field_149943_a.func_82595_a(Item.func_150898_a(Blocks.field_150423_aK), new Bootstrap.BehaviorDispenseOptional() {
         protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
            World world = p_82487_1_.func_82618_k();
            BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a((EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.field_150423_aK;
            this.field_190911_b = true;
            if (world.func_175623_d(blockpos) && blockpumpkin.func_176390_d(world, blockpos)) {
               if (!world.field_72995_K) {
                  world.func_180501_a(blockpos, blockpumpkin.func_176223_P(), 3);
               }

               p_82487_2_.func_190918_g(1);
            } else {
               ItemStack itemstack = ItemArmor.func_185082_a(p_82487_1_, p_82487_2_);
               if (itemstack.func_190926_b()) {
                  this.field_190911_b = false;
               }
            }

            return p_82487_2_;
         }
      });

      for(EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         BlockDispenser.field_149943_a.func_82595_a(Item.func_150898_a(BlockShulkerBox.func_190952_a(enumdyecolor)), new Bootstrap.BehaviorDispenseShulkerBox());
      }

   }

   public static void func_151354_b() {
      if (!field_151355_a) {
         field_151355_a = true;
         func_179868_d();
         SoundEvent.func_187504_b();
         Block.func_149671_p();
         BlockFire.func_149843_e();
         Potion.func_188411_k();
         Enchantment.func_185257_f();
         Item.func_150900_l();
         PotionType.func_185175_b();
         PotionHelper.func_185207_a();
         EntityList.func_151514_a();
         Biome.func_185358_q();
         func_151353_a();
         if (!CraftingManager.func_193377_a()) {
            field_194219_b = true;
            field_179871_c.error("Errors with built-in recipes!");
         }

         StatList.func_151178_a();
         if (field_179871_c.isDebugEnabled()) {
            if ((new AdvancementManager((File)null)).func_193767_b()) {
               field_194219_b = true;
               field_179871_c.error("Errors with built-in advancements!");
            }

            if (!LootTableList.func_193579_b()) {
               field_194219_b = true;
               field_179871_c.error("Errors with built-in loot tables");
            }
         }

      }
   }

   private static void func_179868_d() {
      if (field_179871_c.isDebugEnabled()) {
         System.setErr(new DebugLoggingPrintStream("STDERR", System.err));
         System.setOut(new DebugLoggingPrintStream("STDOUT", field_179872_a));
      } else {
         System.setErr(new LoggingPrintStream("STDERR", System.err));
         System.setOut(new LoggingPrintStream("STDOUT", field_179872_a));
      }

   }

   public static void func_179870_a(String p_179870_0_) {
      field_179872_a.println(p_179870_0_);
   }

   public static class BehaviorDispenseBoat extends BehaviorDefaultDispenseItem {
      private final BehaviorDefaultDispenseItem field_185026_b = new BehaviorDefaultDispenseItem();
      private final EntityBoat.Type field_185027_c;

      public BehaviorDispenseBoat(EntityBoat.Type p_i47023_1_) {
         this.field_185027_c = p_i47023_1_;
      }

      public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
         EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
         World world = p_82487_1_.func_82618_k();
         double d0 = p_82487_1_.func_82615_a() + (double)((float)enumfacing.func_82601_c() * 1.125F);
         double d1 = p_82487_1_.func_82617_b() + (double)((float)enumfacing.func_96559_d() * 1.125F);
         double d2 = p_82487_1_.func_82616_c() + (double)((float)enumfacing.func_82599_e() * 1.125F);
         BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a(enumfacing);
         Material material = world.func_180495_p(blockpos).func_185904_a();
         double d3;
         if (Material.field_151586_h.equals(material)) {
            d3 = 1.0D;
         } else {
            if (!Material.field_151579_a.equals(material) || !Material.field_151586_h.equals(world.func_180495_p(blockpos.func_177977_b()).func_185904_a())) {
               return this.field_185026_b.func_82482_a(p_82487_1_, p_82487_2_);
            }

            d3 = 0.0D;
         }

         EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
         entityboat.func_184458_a(this.field_185027_c);
         entityboat.field_70177_z = enumfacing.func_185119_l();
         world.func_72838_d(entityboat);
         p_82487_2_.func_190918_g(1);
         return p_82487_2_;
      }

      protected void func_82485_a(IBlockSource p_82485_1_) {
         p_82485_1_.func_82618_k().func_175718_b(1000, p_82485_1_.func_180699_d(), 0);
      }
   }

   public abstract static class BehaviorDispenseOptional extends BehaviorDefaultDispenseItem {
      protected boolean field_190911_b = true;

      protected void func_82485_a(IBlockSource p_82485_1_) {
         p_82485_1_.func_82618_k().func_175718_b(this.field_190911_b ? 1000 : 1001, p_82485_1_.func_180699_d(), 0);
      }
   }

   static class BehaviorDispenseShulkerBox extends Bootstrap.BehaviorDispenseOptional {
      private BehaviorDispenseShulkerBox() {
      }

      protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
         Block block = Block.func_149634_a(p_82487_2_.func_77973_b());
         World world = p_82487_1_.func_82618_k();
         EnumFacing enumfacing = (EnumFacing)p_82487_1_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
         BlockPos blockpos = p_82487_1_.func_180699_d().func_177972_a(enumfacing);
         this.field_190911_b = world.func_190527_a(block, blockpos, false, EnumFacing.DOWN, (Entity)null);
         if (this.field_190911_b) {
            EnumFacing enumfacing1 = world.func_175623_d(blockpos.func_177977_b()) ? enumfacing : EnumFacing.UP;
            IBlockState iblockstate = block.func_176223_P().func_177226_a(BlockShulkerBox.field_190957_a, enumfacing1);
            world.func_175656_a(blockpos, iblockstate);
            TileEntity tileentity = world.func_175625_s(blockpos);
            ItemStack itemstack = p_82487_2_.func_77979_a(1);
            if (itemstack.func_77942_o()) {
               ((TileEntityShulkerBox)tileentity).func_190586_e(itemstack.func_77978_p().func_74775_l("BlockEntityTag"));
            }

            if (itemstack.func_82837_s()) {
               ((TileEntityShulkerBox)tileentity).func_190575_a(itemstack.func_82833_r());
            }

            world.func_175666_e(blockpos, iblockstate.func_177230_c());
         }

         return p_82487_2_;
      }
   }
}
