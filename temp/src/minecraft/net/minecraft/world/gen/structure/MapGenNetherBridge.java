package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MapGenNetherBridge extends MapGenStructure {
   private final List<Biome.SpawnListEntry> field_75060_e = Lists.<Biome.SpawnListEntry>newArrayList();

   public MapGenNetherBridge() {
      this.field_75060_e.add(new Biome.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
      this.field_75060_e.add(new Biome.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
      this.field_75060_e.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 8, 5, 5));
      this.field_75060_e.add(new Biome.SpawnListEntry(EntitySkeleton.class, 2, 5, 5));
      this.field_75060_e.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
   }

   public String func_143025_a() {
      return "Fortress";
   }

   public List<Biome.SpawnListEntry> func_75059_a() {
      return this.field_75060_e;
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int i = p_75047_1_ >> 4;
      int j = p_75047_2_ >> 4;
      this.field_75038_b.setSeed((long)(i ^ j << 4) ^ this.field_75039_c.func_72905_C());
      this.field_75038_b.nextInt();
      if (this.field_75038_b.nextInt(3) != 0) {
         return false;
      } else if (p_75047_1_ != (i << 4) + 4 + this.field_75038_b.nextInt(8)) {
         return false;
      } else {
         return p_75047_2_ == (j << 4) + 4 + this.field_75038_b.nextInt(8);
      }
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new MapGenNetherBridge.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_);
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
                  if (this.func_75047_a(k1, l1) && (!p_180706_3_ || !p_180706_1_.func_190526_b(k1, l1))) {
                     return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                  }
               }
            }
         }
      }

      return null;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World p_i2040_1_, Random p_i2040_2_, int p_i2040_3_, int p_i2040_4_) {
         super(p_i2040_3_, p_i2040_4_);
         StructureNetherBridgePieces.Start structurenetherbridgepieces$start = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
         this.field_75075_a.add(structurenetherbridgepieces$start);
         structurenetherbridgepieces$start.func_74861_a(structurenetherbridgepieces$start, this.field_75075_a, p_i2040_2_);
         List<StructureComponent> list = structurenetherbridgepieces$start.field_74967_d;

         while(!list.isEmpty()) {
            int i = p_i2040_2_.nextInt(list.size());
            StructureComponent structurecomponent = list.remove(i);
            structurecomponent.func_74861_a(structurenetherbridgepieces$start, this.field_75075_a, p_i2040_2_);
         }

         this.func_75072_c();
         this.func_75070_a(p_i2040_1_, p_i2040_2_, 48, 70);
      }
   }
}
