package net.minecraft.network.datasync;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ReportedException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDataManager {
   private static final Logger field_190303_a = LogManager.getLogger();
   private static final Map<Class<? extends Entity>, Integer> field_187232_a = Maps.<Class<? extends Entity>, Integer>newHashMap();
   private final Entity field_187233_b;
   private final Map<Integer, EntityDataManager.DataEntry<?>> field_187234_c = Maps.<Integer, EntityDataManager.DataEntry<?>>newHashMap();
   private final ReadWriteLock field_187235_d = new ReentrantReadWriteLock();
   private boolean field_187236_e = true;
   private boolean field_187237_f;

   public EntityDataManager(Entity p_i46840_1_) {
      this.field_187233_b = p_i46840_1_;
   }

   public static <T> DataParameter<T> func_187226_a(Class<? extends Entity> p_187226_0_, DataSerializer<T> p_187226_1_) {
      if (field_190303_a.isDebugEnabled()) {
         try {
            Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!oclass.equals(p_187226_0_)) {
               field_190303_a.debug("defineId called for: {} from {}", p_187226_0_, oclass, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
            ;
         }
      }

      int j;
      if (field_187232_a.containsKey(p_187226_0_)) {
         j = ((Integer)field_187232_a.get(p_187226_0_)).intValue() + 1;
      } else {
         int i = 0;
         Class<?> oclass1 = p_187226_0_;

         while(oclass1 != Entity.class) {
            oclass1 = oclass1.getSuperclass();
            if (field_187232_a.containsKey(oclass1)) {
               i = ((Integer)field_187232_a.get(oclass1)).intValue() + 1;
               break;
            }
         }

         j = i;
      }

      if (j > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + j + "! (Max is " + 254 + ")");
      } else {
         field_187232_a.put(p_187226_0_, Integer.valueOf(j));
         return p_187226_1_.func_187161_a(j);
      }
   }

   public <T> void func_187214_a(DataParameter<T> p_187214_1_, T p_187214_2_) {
      int i = p_187214_1_.func_187155_a();
      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
      } else if (this.field_187234_c.containsKey(Integer.valueOf(i))) {
         throw new IllegalArgumentException("Duplicate id value for " + i + "!");
      } else if (DataSerializers.func_187188_b(p_187214_1_.func_187156_b()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + p_187214_1_.func_187156_b() + " for " + i + "!");
      } else {
         this.func_187222_c(p_187214_1_, p_187214_2_);
      }
   }

   private <T> void func_187222_c(DataParameter<T> p_187222_1_, T p_187222_2_) {
      EntityDataManager.DataEntry<T> dataentry = new EntityDataManager.DataEntry<T>(p_187222_1_, p_187222_2_);
      this.field_187235_d.writeLock().lock();
      this.field_187234_c.put(Integer.valueOf(p_187222_1_.func_187155_a()), dataentry);
      this.field_187236_e = false;
      this.field_187235_d.writeLock().unlock();
   }

   private <T> EntityDataManager.DataEntry<T> func_187219_c(DataParameter<T> p_187219_1_) {
      this.field_187235_d.readLock().lock();

      EntityDataManager.DataEntry<T> dataentry;
      try {
         dataentry = (EntityDataManager.DataEntry)this.field_187234_c.get(Integer.valueOf(p_187219_1_.func_187155_a()));
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.func_85055_a(throwable, "Getting synched entity data");
         CrashReportCategory crashreportcategory = crashreport.func_85058_a("Synched entity data");
         crashreportcategory.func_71507_a("Data ID", p_187219_1_);
         throw new ReportedException(crashreport);
      }

      this.field_187235_d.readLock().unlock();
      return dataentry;
   }

   public <T> T func_187225_a(DataParameter<T> p_187225_1_) {
      return (T)this.func_187219_c(p_187225_1_).func_187206_b();
   }

   public <T> void func_187227_b(DataParameter<T> p_187227_1_, T p_187227_2_) {
      EntityDataManager.DataEntry<T> dataentry = this.<T>func_187219_c(p_187227_1_);
      if (ObjectUtils.notEqual(p_187227_2_, dataentry.func_187206_b())) {
         dataentry.func_187210_a(p_187227_2_);
         this.field_187233_b.func_184206_a(p_187227_1_);
         dataentry.func_187208_a(true);
         this.field_187237_f = true;
      }

   }

   public <T> void func_187217_b(DataParameter<T> p_187217_1_) {
      this.func_187219_c(p_187217_1_).field_187213_c = true;
      this.field_187237_f = true;
   }

   public boolean func_187223_a() {
      return this.field_187237_f;
   }

   public static void func_187229_a(List<EntityDataManager.DataEntry<?>> p_187229_0_, PacketBuffer p_187229_1_) throws IOException {
      if (p_187229_0_ != null) {
         int i = 0;

         for(int j = p_187229_0_.size(); i < j; ++i) {
            EntityDataManager.DataEntry<?> dataentry = (EntityDataManager.DataEntry)p_187229_0_.get(i);
            func_187220_a(p_187229_1_, dataentry);
         }
      }

      p_187229_1_.writeByte(255);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> func_187221_b() {
      List<EntityDataManager.DataEntry<?>> list = null;
      if (this.field_187237_f) {
         this.field_187235_d.readLock().lock();

         for(EntityDataManager.DataEntry<?> dataentry : this.field_187234_c.values()) {
            if (dataentry.func_187209_c()) {
               dataentry.func_187208_a(false);
               if (list == null) {
                  list = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
               }

               list.add(dataentry.func_192735_d());
            }
         }

         this.field_187235_d.readLock().unlock();
      }

      this.field_187237_f = false;
      return list;
   }

   public void func_187216_a(PacketBuffer p_187216_1_) throws IOException {
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> dataentry : this.field_187234_c.values()) {
         func_187220_a(p_187216_1_, dataentry);
      }

      this.field_187235_d.readLock().unlock();
      p_187216_1_.writeByte(255);
   }

   @Nullable
   public List<EntityDataManager.DataEntry<?>> func_187231_c() {
      List<EntityDataManager.DataEntry<?>> list = null;
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> dataentry : this.field_187234_c.values()) {
         if (list == null) {
            list = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
         }

         list.add(dataentry.func_192735_d());
      }

      this.field_187235_d.readLock().unlock();
      return list;
   }

   private static <T> void func_187220_a(PacketBuffer p_187220_0_, EntityDataManager.DataEntry<T> p_187220_1_) throws IOException {
      DataParameter<T> dataparameter = p_187220_1_.func_187205_a();
      int i = DataSerializers.func_187188_b(dataparameter.func_187156_b());
      if (i < 0) {
         throw new EncoderException("Unknown serializer type " + dataparameter.func_187156_b());
      } else {
         p_187220_0_.writeByte(dataparameter.func_187155_a());
         p_187220_0_.func_150787_b(i);
         dataparameter.func_187156_b().func_187160_a(p_187220_0_, p_187220_1_.func_187206_b());
      }
   }

   @Nullable
   public static List<EntityDataManager.DataEntry<?>> func_187215_b(PacketBuffer p_187215_0_) throws IOException {
      List<EntityDataManager.DataEntry<?>> list = null;

      int i;
      while((i = p_187215_0_.readUnsignedByte()) != 255) {
         if (list == null) {
            list = Lists.<EntityDataManager.DataEntry<?>>newArrayList();
         }

         int j = p_187215_0_.func_150792_a();
         DataSerializer<?> dataserializer = DataSerializers.func_187190_a(j);
         if (dataserializer == null) {
            throw new DecoderException("Unknown serializer type " + j);
         }

         list.add(new EntityDataManager.DataEntry(dataserializer.func_187161_a(i), dataserializer.func_187159_a(p_187215_0_)));
      }

      return list;
   }

   public void func_187218_a(List<EntityDataManager.DataEntry<?>> p_187218_1_) {
      this.field_187235_d.writeLock().lock();

      for(EntityDataManager.DataEntry<?> dataentry : p_187218_1_) {
         EntityDataManager.DataEntry<?> dataentry1 = (EntityDataManager.DataEntry)this.field_187234_c.get(Integer.valueOf(dataentry.func_187205_a().func_187155_a()));
         if (dataentry1 != null) {
            this.func_187224_a(dataentry1, dataentry);
            this.field_187233_b.func_184206_a(dataentry.func_187205_a());
         }
      }

      this.field_187235_d.writeLock().unlock();
      this.field_187237_f = true;
   }

   protected <T> void func_187224_a(EntityDataManager.DataEntry<T> p_187224_1_, EntityDataManager.DataEntry<?> p_187224_2_) {
      p_187224_1_.func_187210_a(p_187224_2_.func_187206_b());
   }

   public boolean func_187228_d() {
      return this.field_187236_e;
   }

   public void func_187230_e() {
      this.field_187237_f = false;
      this.field_187235_d.readLock().lock();

      for(EntityDataManager.DataEntry<?> dataentry : this.field_187234_c.values()) {
         dataentry.func_187208_a(false);
      }

      this.field_187235_d.readLock().unlock();
   }

   public static class DataEntry<T> {
      private final DataParameter<T> field_187211_a;
      private T field_187212_b;
      private boolean field_187213_c;

      public DataEntry(DataParameter<T> p_i47010_1_, T p_i47010_2_) {
         this.field_187211_a = p_i47010_1_;
         this.field_187212_b = p_i47010_2_;
         this.field_187213_c = true;
      }

      public DataParameter<T> func_187205_a() {
         return this.field_187211_a;
      }

      public void func_187210_a(T p_187210_1_) {
         this.field_187212_b = p_187210_1_;
      }

      public T func_187206_b() {
         return this.field_187212_b;
      }

      public boolean func_187209_c() {
         return this.field_187213_c;
      }

      public void func_187208_a(boolean p_187208_1_) {
         this.field_187213_c = p_187208_1_;
      }

      public EntityDataManager.DataEntry<T> func_192735_d() {
         return new EntityDataManager.DataEntry<T>(this.field_187211_a, this.field_187211_a.func_187156_b().func_192717_a(this.field_187212_b));
      }
   }
}
