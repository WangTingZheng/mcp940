package net.minecraft.world.gen.structure.template;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class PlacementSettings {
   private Mirror field_186228_a = Mirror.NONE;
   private Rotation field_186229_b = Rotation.NONE;
   private boolean field_186230_c;
   @Nullable
   private Block field_186231_d;
   @Nullable
   private ChunkPos field_186232_e;
   @Nullable
   private StructureBoundingBox field_186233_f;
   private boolean field_186234_g = true;
   private float field_189951_h = 1.0F;
   @Nullable
   private Random field_189952_i;
   @Nullable
   private Long field_189953_j;

   public PlacementSettings func_186217_a() {
      PlacementSettings placementsettings = new PlacementSettings();
      placementsettings.field_186228_a = this.field_186228_a;
      placementsettings.field_186229_b = this.field_186229_b;
      placementsettings.field_186230_c = this.field_186230_c;
      placementsettings.field_186231_d = this.field_186231_d;
      placementsettings.field_186232_e = this.field_186232_e;
      placementsettings.field_186233_f = this.field_186233_f;
      placementsettings.field_186234_g = this.field_186234_g;
      placementsettings.field_189951_h = this.field_189951_h;
      placementsettings.field_189952_i = this.field_189952_i;
      placementsettings.field_189953_j = this.field_189953_j;
      return placementsettings;
   }

   public PlacementSettings func_186214_a(Mirror p_186214_1_) {
      this.field_186228_a = p_186214_1_;
      return this;
   }

   public PlacementSettings func_186220_a(Rotation p_186220_1_) {
      this.field_186229_b = p_186220_1_;
      return this;
   }

   public PlacementSettings func_186222_a(boolean p_186222_1_) {
      this.field_186230_c = p_186222_1_;
      return this;
   }

   public PlacementSettings func_186225_a(Block p_186225_1_) {
      this.field_186231_d = p_186225_1_;
      return this;
   }

   public PlacementSettings func_186218_a(ChunkPos p_186218_1_) {
      this.field_186232_e = p_186218_1_;
      return this;
   }

   public PlacementSettings func_186223_a(StructureBoundingBox p_186223_1_) {
      this.field_186233_f = p_186223_1_;
      return this;
   }

   public PlacementSettings func_189949_a(@Nullable Long p_189949_1_) {
      this.field_189953_j = p_189949_1_;
      return this;
   }

   public PlacementSettings func_189950_a(@Nullable Random p_189950_1_) {
      this.field_189952_i = p_189950_1_;
      return this;
   }

   public PlacementSettings func_189946_a(float p_189946_1_) {
      this.field_189951_h = p_189946_1_;
      return this;
   }

   public Mirror func_186212_b() {
      return this.field_186228_a;
   }

   public PlacementSettings func_186226_b(boolean p_186226_1_) {
      this.field_186234_g = p_186226_1_;
      return this;
   }

   public Rotation func_186215_c() {
      return this.field_186229_b;
   }

   public Random func_189947_a(@Nullable BlockPos p_189947_1_) {
      if (this.field_189952_i != null) {
         return this.field_189952_i;
      } else if (this.field_189953_j != null) {
         return this.field_189953_j.longValue() == 0L ? new Random(System.currentTimeMillis()) : new Random(this.field_189953_j.longValue());
      } else if (p_189947_1_ == null) {
         return new Random(System.currentTimeMillis());
      } else {
         int i = p_189947_1_.func_177958_n();
         int j = p_189947_1_.func_177952_p();
         return new Random((long)(i * i * 4987142 + i * 5947611) + (long)(j * j) * 4392871L + (long)(j * 389711) ^ 987234911L);
      }
   }

   public float func_189948_f() {
      return this.field_189951_h;
   }

   public boolean func_186221_e() {
      return this.field_186230_c;
   }

   @Nullable
   public Block func_186219_f() {
      return this.field_186231_d;
   }

   @Nullable
   public StructureBoundingBox func_186213_g() {
      if (this.field_186233_f == null && this.field_186232_e != null) {
         this.func_186224_i();
      }

      return this.field_186233_f;
   }

   public boolean func_186227_h() {
      return this.field_186234_g;
   }

   void func_186224_i() {
      this.field_186233_f = this.func_186216_b(this.field_186232_e);
   }

   @Nullable
   private StructureBoundingBox func_186216_b(@Nullable ChunkPos p_186216_1_) {
      if (p_186216_1_ == null) {
         return null;
      } else {
         int i = p_186216_1_.field_77276_a * 16;
         int j = p_186216_1_.field_77275_b * 16;
         return new StructureBoundingBox(i, 0, j, i + 16 - 1, 255, j + 16 - 1);
      }
   }
}
