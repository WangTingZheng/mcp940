package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public abstract class StateMapperBase implements IStateMapper {
   protected Map<IBlockState, ModelResourceLocation> field_178133_b = Maps.<IBlockState, ModelResourceLocation>newLinkedHashMap();

   public String func_178131_a(Map<IProperty<?>, Comparable<?>> p_178131_1_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(Entry<IProperty<?>, Comparable<?>> entry : p_178131_1_.entrySet()) {
         if (stringbuilder.length() != 0) {
            stringbuilder.append(",");
         }

         IProperty<?> iproperty = (IProperty)entry.getKey();
         stringbuilder.append(iproperty.func_177701_a());
         stringbuilder.append("=");
         stringbuilder.append(this.func_187489_a(iproperty, entry.getValue()));
      }

      if (stringbuilder.length() == 0) {
         stringbuilder.append("normal");
      }

      return stringbuilder.toString();
   }

   private <T extends Comparable<T>> String func_187489_a(IProperty<T> p_187489_1_, Comparable<?> p_187489_2_) {
      return p_187489_1_.func_177702_a(p_187489_2_);
   }

   public Map<IBlockState, ModelResourceLocation> func_178130_a(Block p_178130_1_) {
      UnmodifiableIterator unmodifiableiterator = p_178130_1_.func_176194_O().func_177619_a().iterator();

      while(unmodifiableiterator.hasNext()) {
         IBlockState iblockstate = (IBlockState)unmodifiableiterator.next();
         this.field_178133_b.put(iblockstate, this.func_178132_a(iblockstate));
      }

      return this.field_178133_b;
   }

   protected abstract ModelResourceLocation func_178132_a(IBlockState var1);
}
