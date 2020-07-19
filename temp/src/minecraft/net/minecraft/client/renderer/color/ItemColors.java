package net.minecraft.client.renderer.color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;

public class ItemColors {
   private final ObjectIntIdentityMap<IItemColor> field_186732_a = new ObjectIntIdentityMap<IItemColor>(32);

   public static ItemColors func_186729_a(final BlockColors p_186729_0_) {
      ItemColors itemcolors = new ItemColors();
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            return p_186726_2_ > 0 ? -1 : ((ItemArmor)p_186726_1_.func_77973_b()).func_82814_b(p_186726_1_);
         }
      }, Items.field_151024_Q, Items.field_151027_R, Items.field_151026_S, Items.field_151021_T);
      itemcolors.func_186731_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.func_176938_a(p_186726_1_.func_77960_j());
            return blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN ? -1 : ColorizerGrass.func_77480_a(0.5D, 1.0D);
         }
      }, Blocks.field_150398_cm);
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            if (p_186726_2_ != 1) {
               return -1;
            } else {
               NBTBase nbtbase = ItemFireworkCharge.func_150903_a(p_186726_1_, "Colors");
               if (!(nbtbase instanceof NBTTagIntArray)) {
                  return 9079434;
               } else {
                  int[] aint = ((NBTTagIntArray)nbtbase).func_150302_c();
                  if (aint.length == 1) {
                     return aint[0];
                  } else {
                     int i = 0;
                     int j = 0;
                     int k = 0;

                     for(int l : aint) {
                        i += (l & 16711680) >> 16;
                        j += (l & '\uff00') >> 8;
                        k += (l & 255) >> 0;
                     }

                     i = i / aint.length;
                     j = j / aint.length;
                     k = k / aint.length;
                     return i << 16 | j << 8 | k;
                  }
               }
            }
         }
      }, Items.field_151154_bQ);
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            return p_186726_2_ > 0 ? -1 : PotionUtils.func_190932_c(p_186726_1_);
         }
      }, Items.field_151068_bn, Items.field_185155_bH, Items.field_185156_bI);
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.field_75627_a.get(ItemMonsterPlacer.func_190908_h(p_186726_1_));
            if (entitylist$entityegginfo == null) {
               return -1;
            } else {
               return p_186726_2_ == 0 ? entitylist$entityegginfo.field_75611_b : entitylist$entityegginfo.field_75612_c;
            }
         }
      }, Items.field_151063_bx);
      itemcolors.func_186731_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            IBlockState iblockstate = ((ItemBlock)p_186726_1_.func_77973_b()).func_179223_d().func_176203_a(p_186726_1_.func_77960_j());
            return p_186729_0_.func_186724_a(iblockstate, (IBlockAccess)null, (BlockPos)null, p_186726_2_);
         }
      }, Blocks.field_150349_c, Blocks.field_150329_H, Blocks.field_150395_bd, Blocks.field_150362_t, Blocks.field_150361_u, Blocks.field_150392_bi);
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            return p_186726_2_ == 0 ? PotionUtils.func_190932_c(p_186726_1_) : -1;
         }
      }, Items.field_185167_i);
      itemcolors.func_186730_a(new IItemColor() {
         public int func_186726_a(ItemStack p_186726_1_, int p_186726_2_) {
            return p_186726_2_ == 0 ? -1 : ItemMap.func_190907_h(p_186726_1_);
         }
      }, Items.field_151098_aY);
      return itemcolors;
   }

   public int func_186728_a(ItemStack p_186728_1_, int p_186728_2_) {
      IItemColor iitemcolor = this.field_186732_a.func_148745_a(Item.field_150901_e.func_148757_b(p_186728_1_.func_77973_b()));
      return iitemcolor == null ? -1 : iitemcolor.func_186726_a(p_186728_1_, p_186728_2_);
   }

   public void func_186731_a(IItemColor p_186731_1_, Block... p_186731_2_) {
      for(Block block : p_186731_2_) {
         this.field_186732_a.func_148746_a(p_186731_1_, Item.func_150891_b(Item.func_150898_a(block)));
      }

   }

   public void func_186730_a(IItemColor p_186730_1_, Item... p_186730_2_) {
      for(Item item : p_186730_2_) {
         this.field_186732_a.func_148746_a(p_186730_1_, Item.func_150891_b(item));
      }

   }
}
