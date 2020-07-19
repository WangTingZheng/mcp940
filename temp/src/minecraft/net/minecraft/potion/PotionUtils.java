package net.minecraft.potion;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class PotionUtils {
   public static List<PotionEffect> func_185189_a(ItemStack p_185189_0_) {
      return func_185185_a(p_185189_0_.func_77978_p());
   }

   public static List<PotionEffect> func_185186_a(PotionType p_185186_0_, Collection<PotionEffect> p_185186_1_) {
      List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
      list.addAll(p_185186_0_.func_185170_a());
      list.addAll(p_185186_1_);
      return list;
   }

   public static List<PotionEffect> func_185185_a(@Nullable NBTTagCompound p_185185_0_) {
      List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
      list.addAll(func_185187_c(p_185185_0_).func_185170_a());
      func_185193_a(p_185185_0_, list);
      return list;
   }

   public static List<PotionEffect> func_185190_b(ItemStack p_185190_0_) {
      return func_185192_b(p_185190_0_.func_77978_p());
   }

   public static List<PotionEffect> func_185192_b(@Nullable NBTTagCompound p_185192_0_) {
      List<PotionEffect> list = Lists.<PotionEffect>newArrayList();
      func_185193_a(p_185192_0_, list);
      return list;
   }

   public static void func_185193_a(@Nullable NBTTagCompound p_185193_0_, List<PotionEffect> p_185193_1_) {
      if (p_185193_0_ != null && p_185193_0_.func_150297_b("CustomPotionEffects", 9)) {
         NBTTagList nbttaglist = p_185193_0_.func_150295_c("CustomPotionEffects", 10);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
            PotionEffect potioneffect = PotionEffect.func_82722_b(nbttagcompound);
            if (potioneffect != null) {
               p_185193_1_.add(potioneffect);
            }
         }
      }

   }

   public static int func_190932_c(ItemStack p_190932_0_) {
      NBTTagCompound nbttagcompound = p_190932_0_.func_77978_p();
      if (nbttagcompound != null && nbttagcompound.func_150297_b("CustomPotionColor", 99)) {
         return nbttagcompound.func_74762_e("CustomPotionColor");
      } else {
         return func_185191_c(p_190932_0_) == PotionTypes.field_185229_a ? 16253176 : func_185181_a(func_185189_a(p_190932_0_));
      }
   }

   public static int func_185183_a(PotionType p_185183_0_) {
      return p_185183_0_ == PotionTypes.field_185229_a ? 16253176 : func_185181_a(p_185183_0_.func_185170_a());
   }

   public static int func_185181_a(Collection<PotionEffect> p_185181_0_) {
      int i = 3694022;
      if (p_185181_0_.isEmpty()) {
         return 3694022;
      } else {
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         int j = 0;

         for(PotionEffect potioneffect : p_185181_0_) {
            if (potioneffect.func_188418_e()) {
               int k = potioneffect.func_188419_a().func_76401_j();
               int l = potioneffect.func_76458_c() + 1;
               f += (float)(l * (k >> 16 & 255)) / 255.0F;
               f1 += (float)(l * (k >> 8 & 255)) / 255.0F;
               f2 += (float)(l * (k >> 0 & 255)) / 255.0F;
               j += l;
            }
         }

         if (j == 0) {
            return 0;
         } else {
            f = f / (float)j * 255.0F;
            f1 = f1 / (float)j * 255.0F;
            f2 = f2 / (float)j * 255.0F;
            return (int)f << 16 | (int)f1 << 8 | (int)f2;
         }
      }
   }

   public static PotionType func_185191_c(ItemStack p_185191_0_) {
      return func_185187_c(p_185191_0_.func_77978_p());
   }

   public static PotionType func_185187_c(@Nullable NBTTagCompound p_185187_0_) {
      return p_185187_0_ == null ? PotionTypes.field_185229_a : PotionType.func_185168_a(p_185187_0_.func_74779_i("Potion"));
   }

   public static ItemStack func_185188_a(ItemStack p_185188_0_, PotionType p_185188_1_) {
      ResourceLocation resourcelocation = PotionType.field_185176_a.func_177774_c(p_185188_1_);
      if (p_185188_1_ == PotionTypes.field_185229_a) {
         if (p_185188_0_.func_77942_o()) {
            NBTTagCompound nbttagcompound = p_185188_0_.func_77978_p();
            nbttagcompound.func_82580_o("Potion");
            if (nbttagcompound.func_82582_d()) {
               p_185188_0_.func_77982_d((NBTTagCompound)null);
            }
         }
      } else {
         NBTTagCompound nbttagcompound1 = p_185188_0_.func_77942_o() ? p_185188_0_.func_77978_p() : new NBTTagCompound();
         nbttagcompound1.func_74778_a("Potion", resourcelocation.toString());
         p_185188_0_.func_77982_d(nbttagcompound1);
      }

      return p_185188_0_;
   }

   public static ItemStack func_185184_a(ItemStack p_185184_0_, Collection<PotionEffect> p_185184_1_) {
      if (p_185184_1_.isEmpty()) {
         return p_185184_0_;
      } else {
         NBTTagCompound nbttagcompound = (NBTTagCompound)MoreObjects.firstNonNull(p_185184_0_.func_77978_p(), new NBTTagCompound());
         NBTTagList nbttaglist = nbttagcompound.func_150295_c("CustomPotionEffects", 9);

         for(PotionEffect potioneffect : p_185184_1_) {
            nbttaglist.func_74742_a(potioneffect.func_82719_a(new NBTTagCompound()));
         }

         nbttagcompound.func_74782_a("CustomPotionEffects", nbttaglist);
         p_185184_0_.func_77982_d(nbttagcompound);
         return p_185184_0_;
      }
   }

   public static void func_185182_a(ItemStack p_185182_0_, List<String> p_185182_1_, float p_185182_2_) {
      List<PotionEffect> list = func_185189_a(p_185182_0_);
      List<Tuple<String, AttributeModifier>> list1 = Lists.<Tuple<String, AttributeModifier>>newArrayList();
      if (list.isEmpty()) {
         String s = I18n.func_74838_a("effect.none").trim();
         p_185182_1_.add(TextFormatting.GRAY + s);
      } else {
         for(PotionEffect potioneffect : list) {
            String s1 = I18n.func_74838_a(potioneffect.func_76453_d()).trim();
            Potion potion = potioneffect.func_188419_a();
            Map<IAttribute, AttributeModifier> map = potion.func_111186_k();
            if (!map.isEmpty()) {
               for(Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.func_111166_b(), potion.func_111183_a(potioneffect.func_76458_c(), attributemodifier), attributemodifier.func_111169_c());
                  list1.add(new Tuple(((IAttribute)entry.getKey()).func_111108_a(), attributemodifier1));
               }
            }

            if (potioneffect.func_76458_c() > 0) {
               s1 = s1 + " " + I18n.func_74838_a("potion.potency." + potioneffect.func_76458_c()).trim();
            }

            if (potioneffect.func_76459_b() > 20) {
               s1 = s1 + " (" + Potion.func_188410_a(potioneffect, p_185182_2_) + ")";
            }

            if (potion.func_76398_f()) {
               p_185182_1_.add(TextFormatting.RED + s1);
            } else {
               p_185182_1_.add(TextFormatting.BLUE + s1);
            }
         }
      }

      if (!list1.isEmpty()) {
         p_185182_1_.add("");
         p_185182_1_.add(TextFormatting.DARK_PURPLE + I18n.func_74838_a("potion.whenDrank"));

         for(Tuple<String, AttributeModifier> tuple : list1) {
            AttributeModifier attributemodifier2 = tuple.func_76340_b();
            double d0 = attributemodifier2.func_111164_d();
            double d1;
            if (attributemodifier2.func_111169_c() != 1 && attributemodifier2.func_111169_c() != 2) {
               d1 = attributemodifier2.func_111164_d();
            } else {
               d1 = attributemodifier2.func_111164_d() * 100.0D;
            }

            if (d0 > 0.0D) {
               p_185182_1_.add(TextFormatting.BLUE + I18n.func_74837_a("attribute.modifier.plus." + attributemodifier2.func_111169_c(), ItemStack.field_111284_a.format(d1), I18n.func_74838_a("attribute.name." + (String)tuple.func_76341_a())));
            } else if (d0 < 0.0D) {
               d1 = d1 * -1.0D;
               p_185182_1_.add(TextFormatting.RED + I18n.func_74837_a("attribute.modifier.take." + attributemodifier2.func_111169_c(), ItemStack.field_111284_a.format(d1), I18n.func_74838_a("attribute.name." + (String)tuple.func_76341_a())));
            }
         }
      }

   }
}
