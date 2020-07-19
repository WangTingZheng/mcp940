package net.minecraft.item;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemArmor extends Item {
   private static final int[] field_77882_bY = new int[]{13, 15, 16, 11};
   private static final UUID[] field_185084_n = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
   public static final String[] field_94603_a = new String[]{"minecraft:items/empty_armor_slot_boots", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_helmet"};
   public static final IBehaviorDispenseItem field_96605_cw = new BehaviorDefaultDispenseItem() {
      protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
         ItemStack itemstack = ItemArmor.func_185082_a(p_82487_1_, p_82487_2_);
         return itemstack.func_190926_b() ? super.func_82487_b(p_82487_1_, p_82487_2_) : itemstack;
      }
   };
   public final EntityEquipmentSlot field_77881_a;
   public final int field_77879_b;
   public final float field_189415_e;
   public final int field_77880_c;
   private final ItemArmor.ArmorMaterial field_77878_bZ;

   public static ItemStack func_185082_a(IBlockSource p_185082_0_, ItemStack p_185082_1_) {
      BlockPos blockpos = p_185082_0_.func_180699_d().func_177972_a((EnumFacing)p_185082_0_.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
      List<EntityLivingBase> list = p_185082_0_.func_82618_k().<EntityLivingBase>func_175647_a(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.and(EntitySelectors.field_180132_d, new EntitySelectors.ArmoredMob(p_185082_1_)));
      if (list.isEmpty()) {
         return ItemStack.field_190927_a;
      } else {
         EntityLivingBase entitylivingbase = list.get(0);
         EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(p_185082_1_);
         ItemStack itemstack = p_185082_1_.func_77979_a(1);
         entitylivingbase.func_184201_a(entityequipmentslot, itemstack);
         if (entitylivingbase instanceof EntityLiving) {
            ((EntityLiving)entitylivingbase).func_184642_a(entityequipmentslot, 2.0F);
         }

         return p_185082_1_;
      }
   }

   public ItemArmor(ItemArmor.ArmorMaterial p_i46750_1_, int p_i46750_2_, EntityEquipmentSlot p_i46750_3_) {
      this.field_77878_bZ = p_i46750_1_;
      this.field_77881_a = p_i46750_3_;
      this.field_77880_c = p_i46750_2_;
      this.field_77879_b = p_i46750_1_.func_78044_b(p_i46750_3_);
      this.func_77656_e(p_i46750_1_.func_78046_a(p_i46750_3_));
      this.field_189415_e = p_i46750_1_.func_189416_e();
      this.field_77777_bU = 1;
      this.func_77637_a(CreativeTabs.field_78037_j);
      BlockDispenser.field_149943_a.func_82595_a(this, field_96605_cw);
   }

   public EntityEquipmentSlot func_185083_B_() {
      return this.field_77881_a;
   }

   public int func_77619_b() {
      return this.field_77878_bZ.func_78045_a();
   }

   public ItemArmor.ArmorMaterial func_82812_d() {
      return this.field_77878_bZ;
   }

   public boolean func_82816_b_(ItemStack p_82816_1_) {
      if (this.field_77878_bZ != ItemArmor.ArmorMaterial.LEATHER) {
         return false;
      } else {
         NBTTagCompound nbttagcompound = p_82816_1_.func_77978_p();
         return nbttagcompound != null && nbttagcompound.func_150297_b("display", 10) ? nbttagcompound.func_74775_l("display").func_150297_b("color", 3) : false;
      }
   }

   public int func_82814_b(ItemStack p_82814_1_) {
      if (this.field_77878_bZ != ItemArmor.ArmorMaterial.LEATHER) {
         return 16777215;
      } else {
         NBTTagCompound nbttagcompound = p_82814_1_.func_77978_p();
         if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("display");
            if (nbttagcompound1 != null && nbttagcompound1.func_150297_b("color", 3)) {
               return nbttagcompound1.func_74762_e("color");
            }
         }

         return 10511680;
      }
   }

   public void func_82815_c(ItemStack p_82815_1_) {
      if (this.field_77878_bZ == ItemArmor.ArmorMaterial.LEATHER) {
         NBTTagCompound nbttagcompound = p_82815_1_.func_77978_p();
         if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("display");
            if (nbttagcompound1.func_74764_b("color")) {
               nbttagcompound1.func_82580_o("color");
            }

         }
      }
   }

   public void func_82813_b(ItemStack p_82813_1_, int p_82813_2_) {
      if (this.field_77878_bZ != ItemArmor.ArmorMaterial.LEATHER) {
         throw new UnsupportedOperationException("Can't dye non-leather!");
      } else {
         NBTTagCompound nbttagcompound = p_82813_1_.func_77978_p();
         if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            p_82813_1_.func_77982_d(nbttagcompound);
         }

         NBTTagCompound nbttagcompound1 = nbttagcompound.func_74775_l("display");
         if (!nbttagcompound.func_150297_b("display", 10)) {
            nbttagcompound.func_74782_a("display", nbttagcompound1);
         }

         nbttagcompound1.func_74768_a("color", p_82813_2_);
      }
   }

   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return this.field_77878_bZ.func_151685_b() == p_82789_2_.func_77973_b() ? true : super.func_82789_a(p_82789_1_, p_82789_2_);
   }

   public ActionResult<ItemStack> func_77659_a(World p_77659_1_, EntityPlayer p_77659_2_, EnumHand p_77659_3_) {
      ItemStack itemstack = p_77659_2_.func_184586_b(p_77659_3_);
      EntityEquipmentSlot entityequipmentslot = EntityLiving.func_184640_d(itemstack);
      ItemStack itemstack1 = p_77659_2_.func_184582_a(entityequipmentslot);
      if (itemstack1.func_190926_b()) {
         p_77659_2_.func_184201_a(entityequipmentslot, itemstack.func_77946_l());
         itemstack.func_190920_e(0);
         return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
      } else {
         return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
      }
   }

   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot p_111205_1_) {
      Multimap<String, AttributeModifier> multimap = super.func_111205_h(p_111205_1_);
      if (p_111205_1_ == this.field_77881_a) {
         multimap.put(SharedMonsterAttributes.field_188791_g.func_111108_a(), new AttributeModifier(field_185084_n[p_111205_1_.func_188454_b()], "Armor modifier", (double)this.field_77879_b, 0));
         multimap.put(SharedMonsterAttributes.field_189429_h.func_111108_a(), new AttributeModifier(field_185084_n[p_111205_1_.func_188454_b()], "Armor toughness", (double)this.field_189415_e, 0));
      }

      return multimap;
   }

   public static enum ArmorMaterial {
      LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.field_187728_s, 0.0F),
      CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.field_187713_n, 0.0F),
      IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.field_187725_r, 0.0F),
      GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.field_187722_q, 0.0F),
      DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.field_187716_o, 2.0F);

      private final String field_179243_f;
      private final int field_78048_f;
      private final int[] field_78049_g;
      private final int field_78055_h;
      private final SoundEvent field_185020_j;
      private final float field_189417_k;

      private ArmorMaterial(String p_i47117_3_, int p_i47117_4_, int[] p_i47117_5_, int p_i47117_6_, SoundEvent p_i47117_7_, float p_i47117_8_) {
         this.field_179243_f = p_i47117_3_;
         this.field_78048_f = p_i47117_4_;
         this.field_78049_g = p_i47117_5_;
         this.field_78055_h = p_i47117_6_;
         this.field_185020_j = p_i47117_7_;
         this.field_189417_k = p_i47117_8_;
      }

      public int func_78046_a(EntityEquipmentSlot p_78046_1_) {
         return ItemArmor.field_77882_bY[p_78046_1_.func_188454_b()] * this.field_78048_f;
      }

      public int func_78044_b(EntityEquipmentSlot p_78044_1_) {
         return this.field_78049_g[p_78044_1_.func_188454_b()];
      }

      public int func_78045_a() {
         return this.field_78055_h;
      }

      public SoundEvent func_185017_b() {
         return this.field_185020_j;
      }

      public Item func_151685_b() {
         if (this == LEATHER) {
            return Items.field_151116_aA;
         } else if (this == CHAIN) {
            return Items.field_151042_j;
         } else if (this == GOLD) {
            return Items.field_151043_k;
         } else if (this == IRON) {
            return Items.field_151042_j;
         } else {
            return this == DIAMOND ? Items.field_151045_i : null;
         }
      }

      public String func_179242_c() {
         return this.field_179243_f;
      }

      public float func_189416_e() {
         return this.field_189417_k;
      }
   }
}
