package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {
   public EntityMinecartEmpty(World p_i1722_1_) {
      super(p_i1722_1_);
   }

   public EntityMinecartEmpty(World p_i1723_1_, double p_i1723_2_, double p_i1723_4_, double p_i1723_6_) {
      super(p_i1723_1_, p_i1723_2_, p_i1723_4_, p_i1723_6_);
   }

   public static void func_189673_a(DataFixer p_189673_0_) {
      EntityMinecart.func_189669_a(p_189673_0_, EntityMinecartEmpty.class);
   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      if (p_184230_1_.func_70093_af()) {
         return false;
      } else if (this.func_184207_aI()) {
         return true;
      } else {
         if (!this.field_70170_p.field_72995_K) {
            p_184230_1_.func_184220_m(this);
         }

         return true;
      }
   }

   public void func_96095_a(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_) {
      if (p_96095_4_) {
         if (this.func_184207_aI()) {
            this.func_184226_ay();
         }

         if (this.func_70496_j() == 0) {
            this.func_70494_i(-this.func_70493_k());
            this.func_70497_h(10);
            this.func_70492_c(50.0F);
            this.func_70018_K();
         }
      }

   }

   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.RIDEABLE;
   }
}
