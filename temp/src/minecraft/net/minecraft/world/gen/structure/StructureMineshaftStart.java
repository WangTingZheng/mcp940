package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.world.World;

public class StructureMineshaftStart extends StructureStart {
   private MapGenMineshaft.Type field_189908_c;

   public StructureMineshaftStart() {
   }

   public StructureMineshaftStart(World p_i47149_1_, Random p_i47149_2_, int p_i47149_3_, int p_i47149_4_, MapGenMineshaft.Type p_i47149_5_) {
      super(p_i47149_3_, p_i47149_4_);
      this.field_189908_c = p_i47149_5_;
      StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, p_i47149_2_, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2, this.field_189908_c);
      this.field_75075_a.add(structuremineshaftpieces$room);
      structuremineshaftpieces$room.func_74861_a(structuremineshaftpieces$room, this.field_75075_a, p_i47149_2_);
      this.func_75072_c();
      if (p_i47149_5_ == MapGenMineshaft.Type.MESA) {
         int i = -5;
         int j = p_i47149_1_.func_181545_F() - this.field_75074_b.field_78894_e + this.field_75074_b.func_78882_c() / 2 - -5;
         this.field_75074_b.func_78886_a(0, j, 0);

         for(StructureComponent structurecomponent : this.field_75075_a) {
            structurecomponent.func_181138_a(0, j, 0);
         }
      } else {
         this.func_75067_a(p_i47149_1_, p_i47149_2_, 10);
      }

   }
}
