package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(World p_i1714_1_) {
      super(p_i1714_1_);
   }

   public EntityMinecartChest(World p_i1715_1_, double p_i1715_2_, double p_i1715_4_, double p_i1715_6_) {
      super(p_i1715_1_, p_i1715_2_, p_i1715_4_, p_i1715_6_);
   }

   public static void func_189681_a(DataFixer p_189681_0_) {
      EntityMinecartContainer.func_190574_b(p_189681_0_, EntityMinecartChest.class);
   }

   public void func_94095_a(DamageSource p_94095_1_) {
      super.func_94095_a(p_94095_1_);
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         this.func_145778_a(Item.func_150898_a(Blocks.field_150486_ae), 1, 0.0F);
      }

   }

   public int func_70302_i_() {
      return 27;
   }

   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.CHEST;
   }

   public IBlockState func_180457_u() {
      return Blocks.field_150486_ae.func_176223_P().func_177226_a(BlockChest.field_176459_a, EnumFacing.NORTH);
   }

   public int func_94085_r() {
      return 8;
   }

   public String func_174875_k() {
      return "minecraft:chest";
   }

   public Container func_174876_a(InventoryPlayer p_174876_1_, EntityPlayer p_174876_2_) {
      this.func_184288_f(p_174876_2_);
      return new ContainerChest(p_174876_1_, this, p_174876_2_);
   }
}
