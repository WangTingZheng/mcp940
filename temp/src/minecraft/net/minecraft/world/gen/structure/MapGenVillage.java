package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MapGenVillage extends MapGenStructure {
   public static final List<Biome> field_75055_e = Arrays.<Biome>asList(Biomes.field_76772_c, Biomes.field_76769_d, Biomes.field_150588_X, Biomes.field_76768_g);
   private int field_75054_f;
   private int field_82665_g;
   private final int field_82666_h;

   public MapGenVillage() {
      this.field_82665_g = 32;
      this.field_82666_h = 8;
   }

   public MapGenVillage(Map<String, String> p_i2093_1_) {
      this();

      for(Entry<String, String> entry : p_i2093_1_.entrySet()) {
         if (((String)entry.getKey()).equals("size")) {
            this.field_75054_f = MathHelper.func_82714_a(entry.getValue(), this.field_75054_f, 0);
         } else if (((String)entry.getKey()).equals("distance")) {
            this.field_82665_g = MathHelper.func_82714_a(entry.getValue(), this.field_82665_g, 9);
         }
      }

   }

   public String func_143025_a() {
      return "Village";
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int i = p_75047_1_;
      int j = p_75047_2_;
      if (p_75047_1_ < 0) {
         p_75047_1_ -= this.field_82665_g - 1;
      }

      if (p_75047_2_ < 0) {
         p_75047_2_ -= this.field_82665_g - 1;
      }

      int k = p_75047_1_ / this.field_82665_g;
      int l = p_75047_2_ / this.field_82665_g;
      Random random = this.field_75039_c.func_72843_D(k, l, 10387312);
      k = k * this.field_82665_g;
      l = l * this.field_82665_g;
      k = k + random.nextInt(this.field_82665_g - 8);
      l = l + random.nextInt(this.field_82665_g - 8);
      if (i == k && j == l) {
         boolean flag = this.field_75039_c.func_72959_q().func_76940_a(i * 16 + 8, j * 16 + 8, 0, field_75055_e);
         if (flag) {
            return true;
         }
      }

      return false;
   }

   public BlockPos func_180706_b(World p_180706_1_, BlockPos p_180706_2_, boolean p_180706_3_) {
      this.field_75039_c = p_180706_1_;
      return func_191069_a(p_180706_1_, this, p_180706_2_, this.field_82665_g, 8, 10387312, false, 100, p_180706_3_);
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new MapGenVillage.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_, this.field_75054_f);
   }

   public static class Start extends StructureStart {
      private boolean field_75076_c;

      public Start() {
      }

      public Start(World p_i2092_1_, Random p_i2092_2_, int p_i2092_3_, int p_i2092_4_, int p_i2092_5_) {
         super(p_i2092_3_, p_i2092_4_);
         List<StructureVillagePieces.PieceWeight> list = StructureVillagePieces.func_75084_a(p_i2092_2_, p_i2092_5_);
         StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(p_i2092_1_.func_72959_q(), 0, p_i2092_2_, (p_i2092_3_ << 4) + 2, (p_i2092_4_ << 4) + 2, list, p_i2092_5_);
         this.field_75075_a.add(structurevillagepieces$start);
         structurevillagepieces$start.func_74861_a(structurevillagepieces$start, this.field_75075_a, p_i2092_2_);
         List<StructureComponent> list1 = structurevillagepieces$start.field_74930_j;
         List<StructureComponent> list2 = structurevillagepieces$start.field_74932_i;

         while(!list1.isEmpty() || !list2.isEmpty()) {
            if (list1.isEmpty()) {
               int i = p_i2092_2_.nextInt(list2.size());
               StructureComponent structurecomponent = list2.remove(i);
               structurecomponent.func_74861_a(structurevillagepieces$start, this.field_75075_a, p_i2092_2_);
            } else {
               int j = p_i2092_2_.nextInt(list1.size());
               StructureComponent structurecomponent2 = list1.remove(j);
               structurecomponent2.func_74861_a(structurevillagepieces$start, this.field_75075_a, p_i2092_2_);
            }
         }

         this.func_75072_c();
         int k = 0;

         for(StructureComponent structurecomponent1 : this.field_75075_a) {
            if (!(structurecomponent1 instanceof StructureVillagePieces.Road)) {
               ++k;
            }
         }

         this.field_75076_c = k > 2;
      }

      public boolean func_75069_d() {
         return this.field_75076_c;
      }

      public void func_143022_a(NBTTagCompound p_143022_1_) {
         super.func_143022_a(p_143022_1_);
         p_143022_1_.func_74757_a("Valid", this.field_75076_c);
      }

      public void func_143017_b(NBTTagCompound p_143017_1_) {
         super.func_143017_b(p_143017_1_);
         this.field_75076_c = p_143017_1_.func_74767_n("Valid");
      }
   }
}
