package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonParseException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.StringUtils;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class BookPagesStrictJSON implements IFixableData {
   public int func_188216_a() {
      return 165;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("minecraft:written_book".equals(p_188217_1_.func_74779_i("id"))) {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("tag");
         if (nbttagcompound.func_150297_b("pages", 9)) {
            NBTTagList nbttaglist = nbttagcompound.func_150295_c("pages", 8);

            for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
               String s = nbttaglist.func_150307_f(i);
               ITextComponent itextcomponent = null;
               if (!"null".equals(s) && !StringUtils.func_151246_b(s)) {
                  if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"' || s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
                     try {
                        itextcomponent = (ITextComponent)JsonUtils.func_188176_a(SignStrictJSON.field_188225_a, s, ITextComponent.class, true);
                        if (itextcomponent == null) {
                           itextcomponent = new TextComponentString("");
                        }
                     } catch (JsonParseException var10) {
                        ;
                     }

                     if (itextcomponent == null) {
                        try {
                           itextcomponent = ITextComponent.Serializer.func_150699_a(s);
                        } catch (JsonParseException var9) {
                           ;
                        }
                     }

                     if (itextcomponent == null) {
                        try {
                           itextcomponent = ITextComponent.Serializer.func_186877_b(s);
                        } catch (JsonParseException var8) {
                           ;
                        }
                     }

                     if (itextcomponent == null) {
                        itextcomponent = new TextComponentString(s);
                     }
                  } else {
                     itextcomponent = new TextComponentString(s);
                  }
               } else {
                  itextcomponent = new TextComponentString("");
               }

               nbttaglist.func_150304_a(i, new NBTTagString(ITextComponent.Serializer.func_150696_a(itextcomponent)));
            }

            nbttagcompound.func_74782_a("pages", nbttaglist);
         }
      }

      return p_188217_1_;
   }
}
