package net.minecraft.world.storage.loot;

import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;

public class LootContext {
   private final float field_186498_a;
   private final WorldServer field_186499_b;
   private final LootTableManager field_186500_c;
   @Nullable
   private final Entity field_186501_d;
   @Nullable
   private final EntityPlayer field_186502_e;
   @Nullable
   private final DamageSource field_186503_f;
   private final Set<LootTable> field_186504_g = Sets.<LootTable>newLinkedHashSet();

   public LootContext(float p_i46640_1_, WorldServer p_i46640_2_, LootTableManager p_i46640_3_, @Nullable Entity p_i46640_4_, @Nullable EntityPlayer p_i46640_5_, @Nullable DamageSource p_i46640_6_) {
      this.field_186498_a = p_i46640_1_;
      this.field_186499_b = p_i46640_2_;
      this.field_186500_c = p_i46640_3_;
      this.field_186501_d = p_i46640_4_;
      this.field_186502_e = p_i46640_5_;
      this.field_186503_f = p_i46640_6_;
   }

   @Nullable
   public Entity func_186493_a() {
      return this.field_186501_d;
   }

   @Nullable
   public Entity func_186495_b() {
      return this.field_186502_e;
   }

   @Nullable
   public Entity func_186492_c() {
      return this.field_186503_f == null ? null : this.field_186503_f.func_76346_g();
   }

   public boolean func_186496_a(LootTable p_186496_1_) {
      return this.field_186504_g.add(p_186496_1_);
   }

   public void func_186490_b(LootTable p_186490_1_) {
      this.field_186504_g.remove(p_186490_1_);
   }

   public LootTableManager func_186497_e() {
      return this.field_186500_c;
   }

   public float func_186491_f() {
      return this.field_186498_a;
   }

   @Nullable
   public Entity func_186494_a(LootContext.EntityTarget p_186494_1_) {
      switch(p_186494_1_) {
      case THIS:
         return this.func_186493_a();
      case KILLER:
         return this.func_186492_c();
      case KILLER_PLAYER:
         return this.func_186495_b();
      default:
         return null;
      }
   }

   public static class Builder {
      private final WorldServer field_186474_a;
      private float field_186475_b;
      private Entity field_186476_c;
      private EntityPlayer field_186477_d;
      private DamageSource field_186478_e;

      public Builder(WorldServer p_i46993_1_) {
         this.field_186474_a = p_i46993_1_;
      }

      public LootContext.Builder func_186469_a(float p_186469_1_) {
         this.field_186475_b = p_186469_1_;
         return this;
      }

      public LootContext.Builder func_186472_a(Entity p_186472_1_) {
         this.field_186476_c = p_186472_1_;
         return this;
      }

      public LootContext.Builder func_186470_a(EntityPlayer p_186470_1_) {
         this.field_186477_d = p_186470_1_;
         return this;
      }

      public LootContext.Builder func_186473_a(DamageSource p_186473_1_) {
         this.field_186478_e = p_186473_1_;
         return this;
      }

      public LootContext func_186471_a() {
         return new LootContext(this.field_186475_b, this.field_186474_a, this.field_186474_a.func_184146_ak(), this.field_186476_c, this.field_186477_d, this.field_186478_e);
      }
   }

   public static enum EntityTarget {
      THIS("this"),
      KILLER("killer"),
      KILLER_PLAYER("killer_player");

      private final String field_186488_d;

      private EntityTarget(String p_i46992_3_) {
         this.field_186488_d = p_i46992_3_;
      }

      public static LootContext.EntityTarget func_186482_a(String p_186482_0_) {
         for(LootContext.EntityTarget lootcontext$entitytarget : values()) {
            if (lootcontext$entitytarget.field_186488_d.equals(p_186482_0_)) {
               return lootcontext$entitytarget;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + p_186482_0_);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter p_write_1_, LootContext.EntityTarget p_write_2_) throws IOException {
            p_write_1_.value(p_write_2_.field_186488_d);
         }

         public LootContext.EntityTarget read(JsonReader p_read_1_) throws IOException {
            return LootContext.EntityTarget.func_186482_a(p_read_1_.nextString());
         }
      }
   }
}
