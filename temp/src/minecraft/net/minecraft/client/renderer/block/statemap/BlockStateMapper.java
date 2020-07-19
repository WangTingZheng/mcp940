package net.minecraft.client.renderer.block.statemap;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class BlockStateMapper {
   private final Map<Block, IStateMapper> field_178450_a = Maps.<Block, IStateMapper>newIdentityHashMap();
   private final Set<Block> field_178449_b = Sets.<Block>newIdentityHashSet();

   public void func_178447_a(Block p_178447_1_, IStateMapper p_178447_2_) {
      this.field_178450_a.put(p_178447_1_, p_178447_2_);
   }

   public void func_178448_a(Block... p_178448_1_) {
      Collections.addAll(this.field_178449_b, p_178448_1_);
   }

   public Map<IBlockState, ModelResourceLocation> func_178446_a() {
      Map<IBlockState, ModelResourceLocation> map = Maps.<IBlockState, ModelResourceLocation>newIdentityHashMap();

      for(Block block : Block.field_149771_c) {
         map.putAll(this.func_188181_b(block));
      }

      return map;
   }

   public Set<ResourceLocation> func_188182_a(Block p_188182_1_) {
      if (this.field_178449_b.contains(p_188182_1_)) {
         return Collections.<ResourceLocation>emptySet();
      } else {
         IStateMapper istatemapper = this.field_178450_a.get(p_188182_1_);
         if (istatemapper == null) {
            return Collections.<ResourceLocation>singleton(Block.field_149771_c.func_177774_c(p_188182_1_));
         } else {
            Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();

            for(ModelResourceLocation modelresourcelocation : istatemapper.func_178130_a(p_188182_1_).values()) {
               set.add(new ResourceLocation(modelresourcelocation.func_110624_b(), modelresourcelocation.func_110623_a()));
            }

            return set;
         }
      }
   }

   public Map<IBlockState, ModelResourceLocation> func_188181_b(Block p_188181_1_) {
      return this.field_178449_b.contains(p_188181_1_) ? Collections.emptyMap() : ((IStateMapper)MoreObjects.firstNonNull(this.field_178450_a.get(p_188181_1_), new DefaultStateMapper())).func_178130_a(p_188181_1_);
   }
}
