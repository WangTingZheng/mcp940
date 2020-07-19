package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHoe extends Item {
   private final float field_185072_b;
   protected Item.ToolMaterial field_77843_a;

   public ItemHoe(Item.ToolMaterial p_i45343_1_) {
      this.field_77843_a = p_i45343_1_;
      this.field_77777_bU = 1;
      this.func_77656_e(p_i45343_1_.func_77997_a());
      this.func_77637_a(CreativeTabs.field_78040_i);
      this.field_185072_b = p_i45343_1_.func_78000_c() + 1.0F;
   }

   public EnumActionResult func_180614_a(EntityPlayer p_180614_1_, World p_180614_2_, BlockPos p_180614_3_, EnumHand p_180614_4_, EnumFacing p_180614_5_, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
      ItemStack itemstack = p_180614_1_.func_184586_b(p_180614_4_);
      if (!p_180614_1_.func_175151_a(p_180614_3_.func_177972_a(p_180614_5_), p_180614_5_, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         IBlockState iblockstate = p_180614_2_.func_180495_p(p_180614_3_);
         Block block = iblockstate.func_177230_c();
         if (p_180614_5_ != EnumFacing.DOWN && p_180614_2_.func_180495_p(p_180614_3_.func_177984_a()).func_185904_a() == Material.field_151579_a) {
            if (block == Blocks.field_150349_c || block == Blocks.field_185774_da) {
               this.func_185071_a(itemstack, p_180614_1_, p_180614_2_, p_180614_3_, Blocks.field_150458_ak.func_176223_P());
               return EnumActionResult.SUCCESS;
            }

            if (block == Blocks.field_150346_d) {
               switch((BlockDirt.DirtType)iblockstate.func_177229_b(BlockDirt.field_176386_a)) {
               case DIRT:
                  this.func_185071_a(itemstack, p_180614_1_, p_180614_2_, p_180614_3_, Blocks.field_150458_ak.func_176223_P());
                  return EnumActionResult.SUCCESS;
               case COARSE_DIRT:
                  this.func_185071_a(itemstack, p_180614_1_, p_180614_2_, p_180614_3_, Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.DIRT));
                  return EnumActionResult.SUCCESS;
               }
            }
         }

         return EnumActionResult.PASS;
      }
   }

   public boolean func_77644_a(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
      p_77644_1_.func_77972_a(1, p_77644_3_);
      return true;
   }

   protected void func_185071_a(ItemStack p_185071_1_, EntityPlayer p_185071_2_, World p_185071_3_, BlockPos p_185071_4_, IBlockState p_185071_5_) {
      p_185071_3_.func_184133_a(p_185071_2_, p_185071_4_, SoundEvents.field_187693_cj, SoundCategory.BLOCKS, 1.0F, 1.0F);
      if (!p_185071_3_.field_72995_K) {
         p_185071_3_.func_180501_a(p_185071_4_, p_185071_5_, 11);
         p_185071_1_.func_77972_a(1, p_185071_2_);
      }

   }

   public boolean func_77662_d() {
      return true;
   }

   public String func_77842_f() {
      return this.field_77843_a.toString();
   }

   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot p_111205_1_) {
      Multimap<String, AttributeModifier> multimap = super.func_111205_h(p_111205_1_);
      if (p_111205_1_ == EntityEquipmentSlot.MAINHAND) {
         multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", 0.0D, 0));
         multimap.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Weapon modifier", (double)(this.field_185072_b - 4.0F), 0));
      }

      return multimap;
   }
}
