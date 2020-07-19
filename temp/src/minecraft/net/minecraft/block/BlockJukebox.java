package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
   public static final PropertyBool field_176432_a = PropertyBool.func_177716_a("has_record");

   public static void func_189873_a(DataFixer p_189873_0_) {
      p_189873_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new ItemStackData(BlockJukebox.TileEntityJukebox.class, new String[]{"RecordItem"}));
   }

   protected BlockJukebox() {
      super(Material.field_151575_d, MapColor.field_151664_l);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176432_a, Boolean.valueOf(false)));
      this.func_149647_a(CreativeTabs.field_78031_c);
   }

   public boolean func_180639_a(World p_180639_1_, BlockPos p_180639_2_, IBlockState p_180639_3_, EntityPlayer p_180639_4_, EnumHand p_180639_5_, EnumFacing p_180639_6_, float p_180639_7_, float p_180639_8_, float p_180639_9_) {
      if (((Boolean)p_180639_3_.func_177229_b(field_176432_a)).booleanValue()) {
         this.func_180678_e(p_180639_1_, p_180639_2_, p_180639_3_);
         p_180639_3_ = p_180639_3_.func_177226_a(field_176432_a, Boolean.valueOf(false));
         p_180639_1_.func_180501_a(p_180639_2_, p_180639_3_, 2);
         return true;
      } else {
         return false;
      }
   }

   public void func_176431_a(World p_176431_1_, BlockPos p_176431_2_, IBlockState p_176431_3_, ItemStack p_176431_4_) {
      TileEntity tileentity = p_176431_1_.func_175625_s(p_176431_2_);
      if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
         ((BlockJukebox.TileEntityJukebox)tileentity).func_145857_a(p_176431_4_.func_77946_l());
         p_176431_1_.func_180501_a(p_176431_2_, p_176431_3_.func_177226_a(field_176432_a, Boolean.valueOf(true)), 2);
      }
   }

   private void func_180678_e(World p_180678_1_, BlockPos p_180678_2_, IBlockState p_180678_3_) {
      if (!p_180678_1_.field_72995_K) {
         TileEntity tileentity = p_180678_1_.func_175625_s(p_180678_2_);
         if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
            BlockJukebox.TileEntityJukebox blockjukebox$tileentityjukebox = (BlockJukebox.TileEntityJukebox)tileentity;
            ItemStack itemstack = blockjukebox$tileentityjukebox.func_145856_a();
            if (!itemstack.func_190926_b()) {
               p_180678_1_.func_175718_b(1010, p_180678_2_, 0);
               p_180678_1_.func_184149_a(p_180678_2_, (SoundEvent)null);
               blockjukebox$tileentityjukebox.func_145857_a(ItemStack.field_190927_a);
               float f = 0.7F;
               double d0 = (double)(p_180678_1_.field_73012_v.nextFloat() * 0.7F) + 0.15000000596046448D;
               double d1 = (double)(p_180678_1_.field_73012_v.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
               double d2 = (double)(p_180678_1_.field_73012_v.nextFloat() * 0.7F) + 0.15000000596046448D;
               ItemStack itemstack1 = itemstack.func_77946_l();
               EntityItem entityitem = new EntityItem(p_180678_1_, (double)p_180678_2_.func_177958_n() + d0, (double)p_180678_2_.func_177956_o() + d1, (double)p_180678_2_.func_177952_p() + d2, itemstack1);
               entityitem.func_174869_p();
               p_180678_1_.func_72838_d(entityitem);
            }
         }
      }
   }

   public void func_180663_b(World p_180663_1_, BlockPos p_180663_2_, IBlockState p_180663_3_) {
      this.func_180678_e(p_180663_1_, p_180663_2_, p_180663_3_);
      super.func_180663_b(p_180663_1_, p_180663_2_, p_180663_3_);
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      if (!p_180653_1_.field_72995_K) {
         super.func_180653_a(p_180653_1_, p_180653_2_, p_180653_3_, p_180653_4_, 0);
      }
   }

   public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
      return new BlockJukebox.TileEntityJukebox();
   }

   public boolean func_149740_M(IBlockState p_149740_1_) {
      return true;
   }

   public int func_180641_l(IBlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
      TileEntity tileentity = p_180641_2_.func_175625_s(p_180641_3_);
      if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
         ItemStack itemstack = ((BlockJukebox.TileEntityJukebox)tileentity).func_145856_a();
         if (!itemstack.func_190926_b()) {
            return Item.func_150891_b(itemstack.func_77973_b()) + 1 - Item.func_150891_b(Items.field_151096_cd);
         }
      }

      return 0;
   }

   public EnumBlockRenderType func_149645_b(IBlockState p_149645_1_) {
      return EnumBlockRenderType.MODEL;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176432_a, Boolean.valueOf(p_176203_1_ > 0));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Boolean)p_176201_1_.func_177229_b(field_176432_a)).booleanValue() ? 1 : 0;
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{field_176432_a});
   }

   public static class TileEntityJukebox extends TileEntity {
      private ItemStack field_145858_a = ItemStack.field_190927_a;

      public void func_145839_a(NBTTagCompound p_145839_1_) {
         super.func_145839_a(p_145839_1_);
         if (p_145839_1_.func_150297_b("RecordItem", 10)) {
            this.func_145857_a(new ItemStack(p_145839_1_.func_74775_l("RecordItem")));
         } else if (p_145839_1_.func_74762_e("Record") > 0) {
            this.func_145857_a(new ItemStack(Item.func_150899_d(p_145839_1_.func_74762_e("Record"))));
         }

      }

      public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
         super.func_189515_b(p_189515_1_);
         if (!this.func_145856_a().func_190926_b()) {
            p_189515_1_.func_74782_a("RecordItem", this.func_145856_a().func_77955_b(new NBTTagCompound()));
         }

         return p_189515_1_;
      }

      public ItemStack func_145856_a() {
         return this.field_145858_a;
      }

      public void func_145857_a(ItemStack p_145857_1_) {
         this.field_145858_a = p_145857_1_;
         this.func_70296_d();
      }
   }
}
