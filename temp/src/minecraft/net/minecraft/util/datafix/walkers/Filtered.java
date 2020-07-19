package net.minecraft.util.datafix.walkers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;

public abstract class Filtered implements IDataWalker {
   private final ResourceLocation field_188272_a;

   public Filtered(Class<?> p_i47309_1_) {
      if (Entity.class.isAssignableFrom(p_i47309_1_)) {
         this.field_188272_a = EntityList.func_191306_a(p_i47309_1_);
      } else if (TileEntity.class.isAssignableFrom(p_i47309_1_)) {
         this.field_188272_a = TileEntity.func_190559_a(p_i47309_1_);
      } else {
         this.field_188272_a = null;
      }

   }

   public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
      if ((new ResourceLocation(p_188266_2_.func_74779_i("id"))).equals(this.field_188272_a)) {
         p_188266_2_ = this.func_188271_b(p_188266_1_, p_188266_2_, p_188266_3_);
      }

      return p_188266_2_;
   }

   abstract NBTTagCompound func_188271_b(IDataFixer var1, NBTTagCompound var2, int var3);
}
