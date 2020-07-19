package net.minecraft.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IInteractionObject extends IWorldNameable {
   Container func_174876_a(InventoryPlayer var1, EntityPlayer var2);

   String func_174875_k();
}
