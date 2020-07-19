package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemFood extends Item {
   public final int field_77855_a;
   private final int field_77853_b;
   private final float field_77854_c;
   private final boolean field_77856_bY;
   private boolean field_77852_bZ;
   private PotionEffect field_77851_ca;
   private float field_77858_cd;

   public ItemFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
      this.field_77855_a = 32;
      this.field_77853_b = p_i45339_1_;
      this.field_77856_bY = p_i45339_3_;
      this.field_77854_c = p_i45339_2_;
      this.func_77637_a(CreativeTabs.field_78039_h);
   }

   public ItemFood(int p_i45340_1_, boolean p_i45340_2_) {
      this(p_i45340_1_, 0.6F, p_i45340_2_);
   }

   public ItemStack func_77654_b(ItemStack p_77654_1_, World p_77654_2_, EntityLivingBase p_77654_3_) {
      if (p_77654_3_ instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)p_77654_3_;
         entityplayer.func_71024_bL().func_151686_a(this, p_77654_1_);
         p_77654_2_.func_184148_a((EntityPlayer)null, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, SoundEvents.field_187739_dZ, SoundCategory.PLAYERS, 0.5F, p_77654_2_.field_73012_v.nextFloat() * 0.1F + 0.9F);
         this.func_77849_c(p_77654_1_, p_77654_2_, entityplayer);
         entityplayer.func_71029_a(StatList.func_188057_b(this));
         if (entityplayer instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)entityplayer, p_77654_1_);
         }
      }

      p_77654_1_.func_190918_g(1);
      return p_77654_1_;
   }

   protected void func_77849_c(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_) {
      if (!p_77849_2_.field_72995_K && this.field_77851_ca != null && p_77849_2_.field_73012_v.nextFloat() < this.field_77858_cd) {
         p_77849_3_.func_70690_d(new PotionEffect(this.field_77851_ca));
      }

   }

   public int func_77626_a(ItemStack p_77626_1_) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack p_77661_1_) {
      return EnumAction.EAT;
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      if (p_77659_2_.func_71043_e(this.field_77852_bZ)) {
         p_77659_2_.func_184598_c(p_77659_3_);
         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      } else {
         return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
      }
   }

   public int func_150905_g(ItemStack p_150905_1_) {
      return this.field_77853_b;
   }

   public float func_150906_h(ItemStack p_150906_1_) {
      return this.field_77854_c;
   }

   public boolean func_77845_h() {
      return this.field_77856_bY;
   }

   public ItemFood func_185070_a(PotionEffect p_185070_1_, float p_185070_2_) {
      this.field_77851_ca = p_185070_1_;
      this.field_77858_cd = p_185070_2_;
      return this;
   }

   public ItemFood func_77848_i() {
      this.field_77852_bZ = true;
      return this;
   }
}
