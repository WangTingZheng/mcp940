package net.minecraft.util.datafix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixer implements IDataFixer {
   private static final Logger field_188259_a = LogManager.getLogger();
   private final Map<IFixType, List<IDataWalker>> field_188260_b = Maps.<IFixType, List<IDataWalker>>newHashMap();
   private final Map<IFixType, List<IFixableData>> field_188261_c = Maps.<IFixType, List<IFixableData>>newHashMap();
   private final int field_188262_d;

   public DataFixer(int p_i46828_1_) {
      this.field_188262_d = p_i46828_1_;
   }

   public NBTTagCompound func_188257_a(IFixType p_188257_1_, NBTTagCompound p_188257_2_) {
      int i = p_188257_2_.func_150297_b("DataVersion", 99) ? p_188257_2_.func_74762_e("DataVersion") : -1;
      return i >= 1139 ? p_188257_2_ : this.func_188251_a(p_188257_1_, p_188257_2_, i);
   }

   public NBTTagCompound func_188251_a(IFixType p_188251_1_, NBTTagCompound p_188251_2_, int p_188251_3_) {
      if (p_188251_3_ < this.field_188262_d) {
         p_188251_2_ = this.func_188252_b(p_188251_1_, p_188251_2_, p_188251_3_);
         p_188251_2_ = this.func_188253_c(p_188251_1_, p_188251_2_, p_188251_3_);
      }

      return p_188251_2_;
   }

   private NBTTagCompound func_188252_b(IFixType p_188252_1_, NBTTagCompound p_188252_2_, int p_188252_3_) {
      List<IFixableData> list = (List)this.field_188261_c.get(p_188252_1_);
      if (list != null) {
         for(int i = 0; i < list.size(); ++i) {
            IFixableData ifixabledata = list.get(i);
            if (ifixabledata.func_188216_a() > p_188252_3_) {
               p_188252_2_ = ifixabledata.func_188217_a(p_188252_2_);
            }
         }
      }

      return p_188252_2_;
   }

   private NBTTagCompound func_188253_c(IFixType p_188253_1_, NBTTagCompound p_188253_2_, int p_188253_3_) {
      List<IDataWalker> list = (List)this.field_188260_b.get(p_188253_1_);
      if (list != null) {
         for(int i = 0; i < list.size(); ++i) {
            p_188253_2_ = ((IDataWalker)list.get(i)).func_188266_a(this, p_188253_2_, p_188253_3_);
         }
      }

      return p_188253_2_;
   }

   public void func_188258_a(FixTypes p_188258_1_, IDataWalker p_188258_2_) {
      this.func_188255_a(p_188258_1_, p_188258_2_);
   }

   public void func_188255_a(IFixType p_188255_1_, IDataWalker p_188255_2_) {
      this.func_188254_a(this.field_188260_b, p_188255_1_).add(p_188255_2_);
   }

   public void func_188256_a(IFixType p_188256_1_, IFixableData p_188256_2_) {
      List<IFixableData> list = this.<IFixableData>func_188254_a(this.field_188261_c, p_188256_1_);
      int i = p_188256_2_.func_188216_a();
      if (i > this.field_188262_d) {
         field_188259_a.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", Integer.valueOf(i), Integer.valueOf(this.field_188262_d));
      } else {
         if (!list.isEmpty() && ((IFixableData)Util.func_184878_a(list)).func_188216_a() > i) {
            for(int j = 0; j < list.size(); ++j) {
               if (((IFixableData)list.get(j)).func_188216_a() > i) {
                  list.add(j, p_188256_2_);
                  break;
               }
            }
         } else {
            list.add(p_188256_2_);
         }

      }
   }

   private <V> List<V> func_188254_a(Map<IFixType, List<V>> p_188254_1_, IFixType p_188254_2_) {
      List<V> list = (List)p_188254_1_.get(p_188254_2_);
      if (list == null) {
         list = Lists.<V>newArrayList();
         p_188254_1_.put(p_188254_2_, list);
      }

      return list;
   }
}
