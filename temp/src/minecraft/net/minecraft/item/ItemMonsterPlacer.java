package net.minecraft.item;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {
   public ItemMonsterPlacer() {
      this.func_77637_a(CreativeTabs.field_78026_f);
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      String s = ("" + I18n.func_74838_a(this.func_77658_a() + ".name")).trim();
      String s1 = EntityList.func_191302_a(func_190908_h(p_77653_1_));
      if (s1 != null) {
         s = s + " " + I18n.func_74838_a("entity." + s1 + ".name");
      }

      return s;
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (p_180614_2_.field_72995_K) {
         return EnumActionResult.SUCCESS;
      } else if (!p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
         Block block = iblockstate.func_177230_c();
         if (block == Blocks.field_150474_ac) {
            TileEntity tileentity = p_180614_2_.func_175625_s(p_180614_3_);
            if (tileentity instanceof TileEntityMobSpawner) {
               MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).func_145881_a();
               mobspawnerbaselogic.func_190894_a(func_190908_h(itemstack));
               tileentity.func_70296_d();
               p_180614_2_.func_184138_a(p_180614_3_, iblockstate, iblockstate, 3);
               if (!p_180614_1_.field_71075_bZ.field_75098_d) {
                  itemstack.func_190918_g(1);
               }

               return EnumActionResult.SUCCESS;
            }
         }

         BlockPos blockpos = p_180614_3_.func_177972_a(p_180614_5_);
         double d0 = this.func_190909_a(p_180614_2_, blockpos);
         Entity entity = func_77840_a(p_180614_2_, func_190908_h(itemstack), (double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + d0, (double)blockpos.func_177952_p() + 0.5D);
         if (entity != null) {
            if (entity instanceof EntityLivingBase && itemstack.func_82837_s()) {
               entity.func_96094_a(itemstack.func_82833_r());
            }

            func_185079_a(p_180614_2_, p_180614_1_, itemstack, entity);
            if (!p_180614_1_.field_71075_bZ.field_75098_d) {
               itemstack.func_190918_g(1);
            }
         }

         return EnumActionResult.SUCCESS;
      }
   }

   protected double func_190909_a(World p_190909_1_, BlockPos p_190909_2_) {
      AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).func_72321_a(0.0D, -1.0D, 0.0D);
      List<AxisAlignedBB> list = p_190909_1_.func_184144_a((Entity)null, axisalignedbb);
      if (list.isEmpty()) {
         return 0.0D;
      } else {
         double d0 = axisalignedbb.field_72338_b;

         for(AxisAlignedBB axisalignedbb1 : list) {
            d0 = Math.max(axisalignedbb1.field_72337_e, d0);
         }

         return d0 - (double)p_190909_2_.func_177956_o();
      }
   }

   public static void func_185079_a(World p_185079_0_, @Nullable EntityPlayer p_185079_1_, ItemStack p_185079_2_, @Nullable Entity p_185079_3_) {
      MinecraftServer minecraftserver = p_185079_0_.func_73046_m();
      if (minecraftserver != null && p_185079_3_ != null) {
         NBTTagCompound nbttagcompound = p_185079_2_.func_77978_p();
         if (nbttagcompound != null && nbttagcompound.func_150297_b("EntityTag", 10)) {
            if (!p_185079_0_.field_72995_K && p_185079_3_.func_184213_bq() && (p_185079_1_ == null || !minecraftserver.func_184103_al().func_152596_g(p_185079_1_.func_146103_bH()))) {
               return;
            }

            NBTTagCompound nbttagcompound1 = p_185079_3_.func_189511_e(new NBTTagCompound());
            UUID uuid = p_185079_3_.func_110124_au();
            nbttagcompound1.func_179237_a(nbttagcompound.func_74775_l("EntityTag"));
            p_185079_3_.func_184221_a(uuid);
            p_185079_3_.func_70020_e(nbttagcompound1);
         }

      }
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (p_77659_1_.field_72995_K) {
         return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
      } else {
         RayTraceResult raytraceresult = this.func_77621_a(p_77659_1_, p_77659_2_, true);
         if (raytraceresult != null && raytraceresult.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = raytraceresult.func_178782_a();
            if (!(p_77659_1_.func_180495_p(blockpos).func_177230_c() instanceof BlockLiquid)) {
               return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            } else if (p_77659_1_.func_175660_a(p_77659_2_, blockpos) && p_77659_2_.func_175151_a(blockpos, raytraceresult.field_178784_b, itemstack)) {
               Entity entity = func_77840_a(p_77659_1_, func_190908_h(itemstack), (double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.5D, (double)blockpos.func_177952_p() + 0.5D);
               if (entity == null) {
                  return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
               } else {
                  if (entity instanceof EntityLivingBase && itemstack.func_82837_s()) {
                     entity.func_96094_a(itemstack.func_82833_r());
                  }

                  func_185079_a(p_77659_1_, p_77659_2_, itemstack, entity);
                  if (!p_77659_2_.field_71075_bZ.field_75098_d) {
                     itemstack.func_190918_g(1);
                  }

                  p_77659_2_.func_71029_a(StatList.func_188057_b(this));
                  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
               }
            } else {
               return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }
         } else {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
         }
      }
   }

   @Nullable
   public static Entity func_77840_a(World p_77840_0_, @Nullable ResourceLocation p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_) {
      if (p_77840_1_ != null && EntityList.field_75627_a.containsKey(p_77840_1_)) {
         Entity entity = null;

         for(int i = 0; i < 1; ++i) {
            entity = EntityList.func_188429_b(p_77840_1_, p_77840_0_);
            if (entity instanceof EntityLiving) {
               EntityLiving entityliving = (EntityLiving)entity;
               entity.func_70012_b(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.func_76142_g(p_77840_0_.field_73012_v.nextFloat() * 360.0F), 0.0F);
               entityliving.field_70759_as = entityliving.field_70177_z;
               entityliving.field_70761_aq = entityliving.field_70177_z;
               entityliving.func_180482_a(p_77840_0_.func_175649_E(new BlockPos(entityliving)), (IEntityLivingData)null);
               p_77840_0_.func_72838_d(entity);
               entityliving.func_70642_aH();
            }
         }

         return entity;
      } else {
         return null;
      }
   }

   public void func_150895_a(CreativeTabs p_150895_1_, NonNullList<ItemStack> p_150895_2_) {
      if (this.func_194125_a(p_150895_1_)) {
         for(EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.field_75627_a.values()) {
            ItemStack itemstack = new ItemStack(this, 1);
            func_185078_a(itemstack, entitylist$entityegginfo.field_75613_a);
            p_150895_2_.add(itemstack);
         }
      }

   }

   public static void func_185078_a(ItemStack p_185078_0_, ResourceLocation p_185078_1_) {
      NBTTagCompound nbttagcompound = p_185078_0_.func_77942_o() ? p_185078_0_.func_77978_p() : new NBTTagCompound();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      nbttagcompound1.func_74778_a("id", p_185078_1_.toString());
      nbttagcompound.func_74782_a("EntityTag", nbttagcompound1);
      p_185078_0_.func_77982_d(nbttagcompound);
   }

   @Nullable
   public static ResourceLocation func_190908_h(ItemStack p_190908_0_) {
      NBTTagCompound nbttagcompound = p_190908_0_.func_77978_p();
      if (nbttagcompound == null) {
         return null;
      } else if (!nbttagcompound.func_150297_b("EntityTag", 10)) {
         return null;
      } else {
         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("EntityTag");
         if (!nbttagcompound1.func_150297_b("id", 8)) {
            return null;
         } else {
            String s = nbttagcompound1.func_74779_i("id");
            ResourceLocation resourcelocation = new ResourceLocation(s);
            if (!s.contains(":")) {
               nbttagcompound1.func_74778_a("id", resourcelocation.toString());
            }

            return resourcelocation;
         }
      }
   }
}
