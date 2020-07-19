package net.minecraft.world.storage;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerFileData {
   void func_75753_a(EntityPlayer var1);

   @Nullable
   NBTTagCompound func_75752_b(EntityPlayer var1);

   String[] func_75754_f();
}
