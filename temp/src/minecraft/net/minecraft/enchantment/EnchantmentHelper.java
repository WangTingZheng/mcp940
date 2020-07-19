package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;

public class EnchantmentHelper {
   private static final EnchantmentHelper.ModifierDamage field_77520_b = new EnchantmentHelper.ModifierDamage();
   private static final EnchantmentHelper.ModifierLiving field_77521_c = new EnchantmentHelper.ModifierLiving();
   private static final EnchantmentHelper.HurtIterator field_151388_d = new EnchantmentHelper.HurtIterator();
   private static final EnchantmentHelper.DamageIterator field_151389_e = new EnchantmentHelper.DamageIterator();

   public static int func_77506_a(Enchantment p_77506_0_, ItemStack p_77506_1_) {
      if (p_77506_1_.func_190926_b()) {
         return 0;
      } else {
         NBTTagList nbttaglist = p_77506_1_.func_77986_q();

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
            Enchantment enchantment = Enchantment.func_185262_c(nbttagcompound.func_74765_d("id"));
            int j = nbttagcompound.func_74765_d("lvl");
            if (enchantment == p_77506_0_) {
               return j;
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> func_82781_a(ItemStack p_82781_0_) {
      Map<Enchantment, Integer> map = Maps.<Enchantment, Integer>newLinkedHashMap();
      NBTTagList nbttaglist = p_82781_0_.func_77973_b() == Items.field_151134_bR ? ItemEnchantedBook.func_92110_g(p_82781_0_) : p_82781_0_.func_77986_q();

      for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
         Enchantment enchantment = Enchantment.func_185262_c(nbttagcompound.func_74765_d("id"));
         int j = nbttagcompound.func_74765_d("lvl");
         map.put(enchantment, Integer.valueOf(j));
      }

      return map;
   }

   public static void func_82782_a(Map<Enchantment, Integer> p_82782_0_, ItemStack p_82782_1_) {
      NBTTagList nbttaglist = new NBTTagList();

      for(Entry<Enchantment, Integer> entry : p_82782_0_.entrySet()) {
         Enchantment enchantment = entry.getKey();
         if (enchantment != null) {
            int i = ((Integer)entry.getValue()).intValue();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.func_74777_a("id", (short)Enchantment.func_185258_b(enchantment));
            nbttagcompound.func_74777_a("lvl", (short)i);
            nbttaglist.func_74742_a(nbttagcompound);
            if (p_82782_1_.func_77973_b() == Items.field_151134_bR) {
               ItemEnchantedBook.func_92115_a(p_82782_1_, new EnchantmentData(enchantment, i));
            }
         }
      }

      if (nbttaglist.func_82582_d()) {
         if (p_82782_1_.func_77942_o()) {
            p_82782_1_.func_77978_p().func_82580_o("ench");
         }
      } else if (p_82782_1_.func_77973_b() != Items.field_151134_bR) {
         p_82782_1_.func_77983_a("ench", nbttaglist);
      }

   }

   private static void func_77518_a(EnchantmentHelper.IModifier p_77518_0_, ItemStack p_77518_1_) {
      if (!p_77518_1_.func_190926_b()) {
         NBTTagList nbttaglist = p_77518_1_.func_77986_q();

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            int j = nbttaglist.func_150305_b(i).func_74765_d("id");
            int k = nbttaglist.func_150305_b(i).func_74765_d("lvl");
            if (Enchantment.func_185262_c(j) != null) {
               p_77518_0_.func_77493_a(Enchantment.func_185262_c(j), k);
            }
         }

      }
   }

   private static void func_77516_a(EnchantmentHelper.IModifier p_77516_0_, Iterable<ItemStack> p_77516_1_) {
      for(ItemStack itemstack : p_77516_1_) {
         func_77518_a(p_77516_0_, itemstack);
      }

   }

   public static int func_77508_a(Iterable<ItemStack> p_77508_0_, DamageSource p_77508_1_) {
      field_77520_b.field_77497_a = 0;
      field_77520_b.field_77496_b = p_77508_1_;
      func_77516_a(field_77520_b, p_77508_0_);
      return field_77520_b.field_77497_a;
   }

   public static float func_152377_a(ItemStack p_152377_0_, EnumCreatureAttribute p_152377_1_) {
      field_77521_c.field_77495_a = 0.0F;
      field_77521_c.field_77494_b = p_152377_1_;
      func_77518_a(field_77521_c, p_152377_0_);
      return field_77521_c.field_77495_a;
   }

   public static float func_191527_a(EntityLivingBase p_191527_0_) {
      int i = func_185284_a(Enchantments.field_191530_r, p_191527_0_);
      return i > 0 ? EnchantmentSweepingEdge.func_191526_e(i) : 0.0F;
   }

   public static void func_151384_a(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
      field_151388_d.field_151363_b = p_151384_1_;
      field_151388_d.field_151364_a = p_151384_0_;
      if (p_151384_0_ != null) {
         func_77516_a(field_151388_d, p_151384_0_.func_184209_aF());
      }

      if (p_151384_1_ instanceof EntityPlayer) {
         func_77518_a(field_151388_d, p_151384_0_.func_184614_ca());
      }

   }

   public static void func_151385_b(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
      field_151389_e.field_151366_a = p_151385_0_;
      field_151389_e.field_151365_b = p_151385_1_;
      if (p_151385_0_ != null) {
         func_77516_a(field_151389_e, p_151385_0_.func_184209_aF());
      }

      if (p_151385_0_ instanceof EntityPlayer) {
         func_77518_a(field_151389_e, p_151385_0_.func_184614_ca());
      }

   }

   public static int func_185284_a(Enchantment p_185284_0_, EntityLivingBase p_185284_1_) {
      Iterable<ItemStack> iterable = p_185284_0_.func_185260_a(p_185284_1_);
      if (iterable == null) {
         return 0;
      } else {
         int i = 0;

         for(ItemStack itemstack : iterable) {
            int j = func_77506_a(p_185284_0_, itemstack);
            if (j > i) {
               i = j;
            }
         }

         return i;
      }
   }

   public static int func_77501_a(EntityLivingBase p_77501_0_) {
      return func_185284_a(Enchantments.field_180313_o, p_77501_0_);
   }

   public static int func_90036_a(EntityLivingBase p_90036_0_) {
      return func_185284_a(Enchantments.field_77334_n, p_90036_0_);
   }

   public static int func_185292_c(EntityLivingBase p_185292_0_) {
      return func_185284_a(Enchantments.field_185298_f, p_185292_0_);
   }

   public static int func_185294_d(EntityLivingBase p_185294_0_) {
      return func_185284_a(Enchantments.field_185300_i, p_185294_0_);
   }

   public static int func_185293_e(EntityLivingBase p_185293_0_) {
      return func_185284_a(Enchantments.field_185305_q, p_185293_0_);
   }

   public static int func_191529_b(ItemStack p_191529_0_) {
      return func_77506_a(Enchantments.field_151370_z, p_191529_0_);
   }

   public static int func_191528_c(ItemStack p_191528_0_) {
      return func_77506_a(Enchantments.field_151369_A, p_191528_0_);
   }

   public static int func_185283_h(EntityLivingBase p_185283_0_) {
      return func_185284_a(Enchantments.field_185304_p, p_185283_0_);
   }

   public static boolean func_185287_i(EntityLivingBase p_185287_0_) {
      return func_185284_a(Enchantments.field_185299_g, p_185287_0_) > 0;
   }

   public static boolean func_189869_j(EntityLivingBase p_189869_0_) {
      return func_185284_a(Enchantments.field_185301_j, p_189869_0_) > 0;
   }

   public static boolean func_190938_b(ItemStack p_190938_0_) {
      return func_77506_a(Enchantments.field_190941_k, p_190938_0_) > 0;
   }

   public static boolean func_190939_c(ItemStack p_190939_0_) {
      return func_77506_a(Enchantments.field_190940_C, p_190939_0_) > 0;
   }

   public static ItemStack func_92099_a(Enchantment p_92099_0_, EntityLivingBase p_92099_1_) {
      List<ItemStack> list = p_92099_0_.func_185260_a(p_92099_1_);
      if (list.isEmpty()) {
         return ItemStack.field_190927_a;
      } else {
         List<ItemStack> list1 = Lists.<ItemStack>newArrayList();

         for(ItemStack itemstack : list) {
            if (!itemstack.func_190926_b() && func_77506_a(p_92099_0_, itemstack) > 0) {
               list1.add(itemstack);
            }
         }

         return list1.isEmpty() ? ItemStack.field_190927_a : (ItemStack)list1.get(p_92099_1_.func_70681_au().nextInt(list1.size()));
      }
   }

   public static int func_77514_a(Random p_77514_0_, int p_77514_1_, int p_77514_2_, ItemStack p_77514_3_) {
      Item item = p_77514_3_.func_77973_b();
      int i = item.func_77619_b();
      if (i <= 0) {
         return 0;
      } else {
         if (p_77514_2_ > 15) {
            p_77514_2_ = 15;
         }

         int j = p_77514_0_.nextInt(8) + 1 + (p_77514_2_ >> 1) + p_77514_0_.nextInt(p_77514_2_ + 1);
         if (p_77514_1_ == 0) {
            return Math.max(j / 3, 1);
         } else {
            return p_77514_1_ == 1 ? j * 2 / 3 + 1 : Math.max(j, p_77514_2_ * 2);
         }
      }
   }

   public static ItemStack func_77504_a(Random p_77504_0_, ItemStack p_77504_1_, int p_77504_2_, boolean p_77504_3_) {
      List<EnchantmentData> list = func_77513_b(p_77504_0_, p_77504_1_, p_77504_2_, p_77504_3_);
      boolean flag = p_77504_1_.func_77973_b() == Items.field_151122_aG;
      if (flag) {
         p_77504_1_ = new ItemStack(Items.field_151134_bR);
      }

      for(EnchantmentData enchantmentdata : list) {
         if (flag) {
            ItemEnchantedBook.func_92115_a(p_77504_1_, enchantmentdata);
         } else {
            p_77504_1_.func_77966_a(enchantmentdata.field_76302_b, enchantmentdata.field_76303_c);
         }
      }

      return p_77504_1_;
   }

   public static List<EnchantmentData> func_77513_b(Random p_77513_0_, ItemStack p_77513_1_, int p_77513_2_, boolean p_77513_3_) {
      List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
      Item item = p_77513_1_.func_77973_b();
      int i = item.func_77619_b();
      if (i <= 0) {
         return list;
      } else {
         p_77513_2_ = p_77513_2_ + 1 + p_77513_0_.nextInt(i / 4 + 1) + p_77513_0_.nextInt(i / 4 + 1);
         float f = (p_77513_0_.nextFloat() + p_77513_0_.nextFloat() - 1.0F) * 0.15F;
         p_77513_2_ = MathHelper.func_76125_a(Math.round((float)p_77513_2_ + (float)p_77513_2_ * f), 1, Integer.MAX_VALUE);
         List<EnchantmentData> list1 = func_185291_a(p_77513_2_, p_77513_1_, p_77513_3_);
         if (!list1.isEmpty()) {
            list.add(WeightedRandom.func_76271_a(p_77513_0_, list1));

            while(p_77513_0_.nextInt(50) <= p_77513_2_) {
               func_185282_a(list1, (EnchantmentData)Util.func_184878_a(list));
               if (list1.isEmpty()) {
                  break;
               }

               list.add(WeightedRandom.func_76271_a(p_77513_0_, list1));
               p_77513_2_ /= 2;
            }
         }

         return list;
      }
   }

   public static void func_185282_a(List<EnchantmentData> p_185282_0_, EnchantmentData p_185282_1_) {
      Iterator<EnchantmentData> iterator = p_185282_0_.iterator();

      while(iterator.hasNext()) {
         if (!p_185282_1_.field_76302_b.func_191560_c((iterator.next()).field_76302_b)) {
            iterator.remove();
         }
      }

   }

   public static List<EnchantmentData> func_185291_a(int p_185291_0_, ItemStack p_185291_1_, boolean p_185291_2_) {
      List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
      Item item = p_185291_1_.func_77973_b();
      boolean flag = p_185291_1_.func_77973_b() == Items.field_151122_aG;

      for(Enchantment enchantment : Enchantment.field_185264_b) {
         if ((!enchantment.func_185261_e() || p_185291_2_) && (enchantment.field_77351_y.func_77557_a(item) || flag)) {
            for(int i = enchantment.func_77325_b(); i > enchantment.func_77319_d() - 1; --i) {
               if (p_185291_0_ >= enchantment.func_77321_a(i) && p_185291_0_ <= enchantment.func_77317_b(i)) {
                  list.add(new EnchantmentData(enchantment, i));
                  break;
               }
            }
         }
      }

      return list;
   }

   static final class DamageIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase field_151366_a;
      public Entity field_151365_b;

      private DamageIterator() {
      }

      public void func_77493_a(Enchantment p_77493_1_, int p_77493_2_) {
         p_77493_1_.func_151368_a(this.field_151366_a, this.field_151365_b, p_77493_2_);
      }
   }

   static final class HurtIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase field_151364_a;
      public Entity field_151363_b;

      private HurtIterator() {
      }

      public void func_77493_a(Enchantment p_77493_1_, int p_77493_2_) {
         p_77493_1_.func_151367_b(this.field_151364_a, this.field_151363_b, p_77493_2_);
      }
   }

   interface IModifier {
      void func_77493_a(Enchantment var1, int var2);
   }

   static final class ModifierDamage implements EnchantmentHelper.IModifier {
      public int field_77497_a;
      public DamageSource field_77496_b;

      private ModifierDamage() {
      }

      public void func_77493_a(Enchantment p_77493_1_, int p_77493_2_) {
         this.field_77497_a += p_77493_1_.func_77318_a(p_77493_2_, this.field_77496_b);
      }
   }

   static final class ModifierLiving implements EnchantmentHelper.IModifier {
      public float field_77495_a;
      public EnumCreatureAttribute field_77494_b;

      private ModifierLiving() {
      }

      public void func_77493_a(Enchantment p_77493_1_, int p_77493_2_) {
         this.field_77495_a += p_77493_1_.func_152376_a(p_77493_2_, this.field_77494_b);
      }
   }
}
