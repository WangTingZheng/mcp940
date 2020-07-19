package net.minecraft.world.gen.structure;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMesa;

public class MapGenMineshaft extends MapGenStructure {
   private double field_82673_e = 0.004D;

   public MapGenMineshaft() {
   }

   public String func_143025_a() {
      return "Mineshaft";
   }

   public MapGenMineshaft(Map<String, String> p_i2034_1_) {
      for(Entry<String, String> entry : p_i2034_1_.entrySet()) {
         if (((String)entry.getKey()).equals("chance")) {
            this.field_82673_e = MathHelper.func_82712_a(entry.getValue(), this.field_82673_e);
         }
      }

   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      return this.field_75038_b.nextDouble() < this.field_82673_e && this.field_75038_b.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_));
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      int i = 1000;
      int j = p_180706_2_.func_177958_n() >> 4;
      int k = p_180706_2_.func_177952_p() >> 4;

      for(int l = 0; l <= 1000; ++l) {
         for(int i1 = -l; i1 <= l; ++i1) {
            boolean flag = i1 == -l || i1 == l;

            for(int j1 = -l; j1 <= l; ++j1) {
               boolean flag1 = j1 == -l || j1 == l;
               if (flag || flag1) {
                  int k1 = j + i1;
                  int l1 = k + j1;
                  this.field_75038_b.setSeed((long)(k1 ^ l1) ^ p_180706_1_.func_72905_C());
                  this.field_75038_b.nextInt();
                  if (this.func_75047_a(k1, l1) && (!p_180706_3_ || !p_180706_1_.func_190526_b(k1, l1))) {
                     return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                  }
               }
            }
         }
      }

      return null;
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      Biome biome = this.field_75039_c.func_180494_b(new BlockPos((p_75049_1_ << 4) + 8, 64, (p_75049_2_ << 4) + 8));
      MapGenMineshaft.Type mapgenmineshaft$type = biome instanceof BiomeMesa ? MapGenMineshaft.Type.MESA : MapGenMineshaft.Type.NORMAL;
      return new StructureMineshaftStart(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_, mapgenmineshaft$type);
   }

   public static enum Type {
      NORMAL,
      MESA;

      public static MapGenMineshaft.Type func_189910_a(int p_189910_0_) {
         return p_189910_0_ >= 0 && p_189910_0_ < values().length ? values()[p_189910_0_] : NORMAL;
      }
   }
}
