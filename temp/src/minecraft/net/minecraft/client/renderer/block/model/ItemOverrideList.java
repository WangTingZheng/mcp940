package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverrideList {
   public static final ItemOverrideList field_188022_a = new ItemOverrideList();
   private final List<ItemOverride> field_188023_b = Lists.<ItemOverride>newArrayList();

   private ItemOverrideList() {
   }

   public ItemOverrideList(List<ItemOverride> p_i46570_1_) {
      for(int i = p_i46570_1_.size() - 1; i >= 0; --i) {
         this.field_188023_b.add(p_i46570_1_.get(i));
      }

   }

   @Nullable
   public ResourceLocation func_188021_a(ItemStack p_188021_1_, @Nullable World p_188021_2_, @Nullable EntityLivingBase p_188021_3_) {
      if (!this.field_188023_b.isEmpty()) {
         for(ItemOverride itemoverride : this.field_188023_b) {
            if (itemoverride.func_188027_a(p_188021_1_, p_188021_2_, p_188021_3_)) {
               return itemoverride.func_188026_a();
            }
         }
      }

      return null;
   }
}
