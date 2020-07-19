package net.minecraft.util.text;

import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class TextComponentUtils {
   public static ITextComponent func_179985_a(ICommandSender p_179985_0_, ITextComponent p_179985_1_, Entity p_179985_2_) throws CommandException {
      ITextComponent itextcomponent;
      if (p_179985_1_ instanceof TextComponentScore) {
         TextComponentScore textcomponentscore = (TextComponentScore)p_179985_1_;
         String s = textcomponentscore.func_179995_g();
         if (EntitySelector.func_82378_b(s)) {
            List<Entity> list = EntitySelector.<Entity>func_179656_b(p_179985_0_, s, Entity.class);
            if (list.size() != 1) {
               throw new EntityNotFoundException("commands.generic.selector.notFound", new Object[]{s});
            }

            Entity entity = list.get(0);
            if (entity instanceof EntityPlayer) {
               s = entity.func_70005_c_();
            } else {
               s = entity.func_189512_bd();
            }
         }

         String s2 = p_179985_2_ != null && s.equals("*") ? p_179985_2_.func_70005_c_() : s;
         itextcomponent = new TextComponentScore(s2, textcomponentscore.func_179994_h());
         ((TextComponentScore)itextcomponent).func_179997_b(textcomponentscore.func_150261_e());
         ((TextComponentScore)itextcomponent).func_186876_a(p_179985_0_);
      } else if (p_179985_1_ instanceof TextComponentSelector) {
         String s1 = ((TextComponentSelector)p_179985_1_).func_179992_g();
         itextcomponent = EntitySelector.func_150869_b(p_179985_0_, s1);
         if (itextcomponent == null) {
            itextcomponent = new TextComponentString("");
         }
      } else if (p_179985_1_ instanceof TextComponentString) {
         itextcomponent = new TextComponentString(((TextComponentString)p_179985_1_).func_150265_g());
      } else if (p_179985_1_ instanceof TextComponentKeybind) {
         itextcomponent = new TextComponentKeybind(((TextComponentKeybind)p_179985_1_).func_193633_h());
      } else {
         if (!(p_179985_1_ instanceof TextComponentTranslation)) {
            return p_179985_1_;
         }

         Object[] aobject = ((TextComponentTranslation)p_179985_1_).func_150271_j();

         for(int i = 0; i < aobject.length; ++i) {
            Object object = aobject[i];
            if (object instanceof ITextComponent) {
               aobject[i] = func_179985_a(p_179985_0_, (ITextComponent)object, p_179985_2_);
            }
         }

         itextcomponent = new TextComponentTranslation(((TextComponentTranslation)p_179985_1_).func_150268_i(), aobject);
      }

      Style style = p_179985_1_.func_150256_b();
      if (style != null) {
         itextcomponent.func_150255_a(style.func_150232_l());
      }

      for(ITextComponent itextcomponent1 : p_179985_1_.func_150253_a()) {
         itextcomponent.func_150257_a(func_179985_a(p_179985_0_, itextcomponent1, p_179985_2_));
      }

      return itextcomponent;
   }
}
