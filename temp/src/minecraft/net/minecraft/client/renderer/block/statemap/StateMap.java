package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class StateMap extends StateMapperBase {
   private final IProperty<?> field_178142_a;
   private final String field_178141_c;
   private final List<IProperty<?>> field_178140_d;

   private StateMap(@Nullable IProperty<?> p_i46210_1_, @Nullable String p_i46210_2_, List<IProperty<?>> p_i46210_3_) {
      this.field_178142_a = p_i46210_1_;
      this.field_178141_c = p_i46210_2_;
      this.field_178140_d = p_i46210_3_;
   }

   protected ModelResourceLocation func_178132_a(IBlockState p_178132_1_) {
      Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(p_178132_1_.func_177228_b());
      String s;
      if (this.field_178142_a == null) {
         s = ((ResourceLocation)Block.field_149771_c.func_177774_c(p_178132_1_.func_177230_c())).toString();
      } else {
         s = this.func_187490_a(this.field_178142_a, map);
      }

      if (this.field_178141_c != null) {
         s = s + this.field_178141_c;
      }

      for(IProperty<?> iproperty : this.field_178140_d) {
         map.remove(iproperty);
      }

      return new ModelResourceLocation(s, this.func_178131_a(map));
   }

   private <T extends Comparable<T>> String func_187490_a(IProperty<T> p_187490_1_, Map<IProperty<?>, Comparable<?>> p_187490_2_) {
      return p_187490_1_.func_177702_a(p_187490_2_.remove(this.field_178142_a));
   }

   public static class Builder {
      private IProperty<?> field_178445_a;
      private String field_178443_b;
      private final List<IProperty<?>> field_178444_c = Lists.<IProperty<?>>newArrayList();

      public StateMap.Builder func_178440_a(IProperty<?> p_178440_1_) {
         this.field_178445_a = p_178440_1_;
         return this;
      }

      public StateMap.Builder func_178439_a(String p_178439_1_) {
         this.field_178443_b = p_178439_1_;
         return this;
      }

      public StateMap.Builder func_178442_a(IProperty<?>... p_178442_1_) {
         Collections.addAll(this.field_178444_c, p_178442_1_);
         return this;
      }

      public StateMap func_178441_a() {
         return new StateMap(this.field_178445_a, this.field_178443_b, this.field_178444_c);
      }
   }
}
