package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class GuiUtilRenderComponents {
   public static String func_178909_a(String p_178909_0_, boolean p_178909_1_) {
      return !p_178909_1_ && !Minecraft.func_71410_x().field_71474_y.field_74344_o ? TextFormatting.func_110646_a(p_178909_0_) : p_178909_0_;
   }

   public static List<ITextComponent> func_178908_a(ITextComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
      int i = 0;
      ITextComponent itextcomponent = new TextComponentString("");
      List<ITextComponent> list = Lists.<ITextComponent>newArrayList();
      List<ITextComponent> list1 = Lists.newArrayList(p_178908_0_);

      for(int j = 0; j < list1.size(); ++j) {
         ITextComponent itextcomponent1 = list1.get(j);
         String s = itextcomponent1.func_150261_e();
         boolean flag = false;
         if (s.contains("\n")) {
            int k = s.indexOf(10);
            String s1 = s.substring(k + 1);
            s = s.substring(0, k + 1);
            ITextComponent itextcomponent2 = new TextComponentString(s1);
            itextcomponent2.func_150255_a(itextcomponent1.func_150256_b().func_150232_l());
            list1.add(j + 1, itextcomponent2);
            flag = true;
         }

         String s4 = func_178909_a(itextcomponent1.func_150256_b().func_150218_j() + s, p_178908_4_);
         String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
         int i1 = p_178908_2_.func_78256_a(s5);
         TextComponentString textcomponentstring = new TextComponentString(s5);
         textcomponentstring.func_150255_a(itextcomponent1.func_150256_b().func_150232_l());
         if (i + i1 > p_178908_1_) {
            String s2 = p_178908_2_.func_78262_a(s4, p_178908_1_ - i, false);
            String s3 = s2.length() < s4.length() ? s4.substring(s2.length()) : null;
            if (s3 != null && !s3.isEmpty()) {
               int l = s2.lastIndexOf(32);
               if (l >= 0 && p_178908_2_.func_78256_a(s4.substring(0, l)) > 0) {
                  s2 = s4.substring(0, l);
                  if (p_178908_3_) {
                     ++l;
                  }

                  s3 = s4.substring(l);
               } else if (i > 0 && !s4.contains(" ")) {
                  s2 = "";
                  s3 = s4;
               }

               TextComponentString textcomponentstring1 = new TextComponentString(s3);
               textcomponentstring1.func_150255_a(itextcomponent1.func_150256_b().func_150232_l());
               list1.add(j + 1, textcomponentstring1);
            }

            i1 = p_178908_2_.func_78256_a(s2);
            textcomponentstring = new TextComponentString(s2);
            textcomponentstring.func_150255_a(itextcomponent1.func_150256_b().func_150232_l());
            flag = true;
         }

         if (i + i1 <= p_178908_1_) {
            i += i1;
            itextcomponent.func_150257_a(textcomponentstring);
         } else {
            flag = true;
         }

         if (flag) {
            list.add(itextcomponent);
            i = 0;
            itextcomponent = new TextComponentString("");
         }
      }

      list.add(itextcomponent);
      return list;
   }
}
