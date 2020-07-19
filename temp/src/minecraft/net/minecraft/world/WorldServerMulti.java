package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {
   private final WorldServer field_175743_a;

   public WorldServerMulti(MinecraftServer p_i45923_1_, ISaveHandler p_i45923_2_, int p_i45923_3_, WorldServer p_i45923_4_, Profiler p_i45923_5_) {
      super(p_i45923_1_, p_i45923_2_, new DerivedWorldInfo(p_i45923_4_.func_72912_H()), p_i45923_3_, p_i45923_5_);
      this.field_175743_a = p_i45923_4_;
      p_i45923_4_.func_175723_af().func_177737_a(new IBorderListener() {
         public void func_177694_a(WorldBorder p_177694_1_, double p_177694_2_) {
            WorldServerMulti.this.func_175723_af().func_177750_a(p_177694_2_);
         }

         public void func_177692_a(WorldBorder p_177692_1_, double p_177692_2_, double p_177692_4_, long p_177692_6_) {
            WorldServerMulti.this.func_175723_af().func_177738_a(p_177692_2_, p_177692_4_, p_177692_6_);
         }

         public void func_177693_a(WorldBorder p_177693_1_, double p_177693_2_, double p_177693_4_) {
            WorldServerMulti.this.func_175723_af().func_177739_c(p_177693_2_, p_177693_4_);
         }

         public void func_177691_a(WorldBorder p_177691_1_, int p_177691_2_) {
            WorldServerMulti.this.func_175723_af().func_177723_b(p_177691_2_);
         }

         public void func_177690_b(WorldBorder p_177690_1_, int p_177690_2_) {
            WorldServerMulti.this.func_175723_af().func_177747_c(p_177690_2_);
         }

         public void func_177696_b(WorldBorder p_177696_1_, double p_177696_2_) {
            WorldServerMulti.this.func_175723_af().func_177744_c(p_177696_2_);
         }

         public void func_177695_c(WorldBorder p_177695_1_, double p_177695_2_) {
            WorldServerMulti.this.func_175723_af().func_177724_b(p_177695_2_);
         }
      });
   }

   protected void func_73042_a() throws MinecraftException {
   }

   public World func_175643_b() {
      this.field_72988_C = this.field_175743_a.func_175693_T();
      this.field_96442_D = this.field_175743_a.func_96441_U();
      this.field_184151_B = this.field_175743_a.func_184146_ak();
      this.field_191951_C = this.field_175743_a.func_191952_z();
      String s = VillageCollection.func_176062_a(this.field_73011_w);
      VillageCollection villagecollection = (VillageCollection)this.field_72988_C.func_75742_a(VillageCollection.class, s);
      if (villagecollection == null) {
         this.field_72982_D = new VillageCollection(this);
         this.field_72988_C.func_75745_a(s, this.field_72982_D);
      } else {
         this.field_72982_D = villagecollection;
         this.field_72982_D.func_82566_a(this);
      }

      return this;
   }

   public void func_184166_c() {
      this.field_73011_w.func_186057_q();
   }
}
