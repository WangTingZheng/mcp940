package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench extends Block {
   protected BlockWorkbench() {
      super(Material.field_151575_d);
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (p_180639_1_.field_72995_K) {
         return true;
      } else {
         p_180639_4_.func_180468_a(new BlockWorkbench.InterfaceCraftingTable(p_180639_1_, p_180639_2_));
         p_180639_4_.func_71029_a(StatList.field_188062_ab);
         return true;
      }
   }

   public static class InterfaceCraftingTable implements IInteractionObject {
      private final World field_175128_a;
      private final BlockPos field_175127_b;

      public InterfaceCraftingTable(World p_i45730_1_, BlockPos p_i45730_2_) {
         this.field_175128_a = p_i45730_1_;
         this.field_175127_b = p_i45730_2_;
      }

      public String func_70005_c_() {
         return "crafting_table";
      }

      public boolean func_145818_k_() {
         return false;
      }

      public ITextComponent func_145748_c_() {
         return new TextComponentTranslation(Blocks.field_150462_ai.func_149739_a() + ".name", new Object[0]);
      }

      public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
         return new ContainerWorkbench(p_174876_1_, this.field_175128_a, this.field_175127_b);
      }

      public String func_174875_k() {
         return "minecraft:crafting_table";
      }
   }
}
