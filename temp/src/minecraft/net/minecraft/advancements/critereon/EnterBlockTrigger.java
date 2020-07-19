package net.minecraft.advancements.critereon;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class EnterBlockTrigger implements ICriterionTrigger<EnterBlockTrigger.Instance> {
   private static final ResourceLocation field_192196_a = new ResourceLocation("enter_block");
   private final Map<PlayerAdvancements, EnterBlockTrigger.Listeners> field_192197_b = Maps.<PlayerAdvancements, EnterBlockTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_192196_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192165_2_) {
      EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192165_1_);
      if (enterblocktrigger$listeners == null) {
         enterblocktrigger$listeners = new EnterBlockTrigger.Listeners(p_192165_1_);
         this.field_192197_b.put(p_192165_1_, enterblocktrigger$listeners);
      }

      enterblocktrigger$listeners.func_192472_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192164_2_) {
      EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192164_1_);
      if (enterblocktrigger$listeners != null) {
         enterblocktrigger$listeners.func_192469_b(p_192164_2_);
         if (enterblocktrigger$listeners.func_192470_a()) {
            this.field_192197_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_192197_b.remove(p_192167_1_);
   }

   public EnterBlockTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      Block block = null;
      if (p_192166_1_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.func_151200_h(p_192166_1_, "block"));
         if (!Block.field_149771_c.func_148741_d(resourcelocation)) {
            throw new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         }

         block = Block.field_149771_c.func_82594_a(resourcelocation);
      }

      Map<IProperty<?>, Object> map = null;
      if (p_192166_1_.has("state")) {
         if (block == null) {
            throw new JsonSyntaxException("Can't define block state without a specific block type");
         }

         BlockStateContainer blockstatecontainer = block.func_176194_O();

         for(Entry<String, JsonElement> entry : JsonUtils.func_152754_s(p_192166_1_, "state").entrySet()) {
            IProperty<?> iproperty = blockstatecontainer.func_185920_a(entry.getKey());
            if (iproperty == null) {
               throw new JsonSyntaxException("Unknown block state property '" + (String)entry.getKey() + "' for block '" + Block.field_149771_c.func_177774_c(block) + "'");
            }

            String s = JsonUtils.func_151206_a(entry.getValue(), entry.getKey());
            Optional<?> optional = iproperty.func_185929_b(s);
            if (!optional.isPresent()) {
               throw new JsonSyntaxException("Invalid block state value '" + s + "' for property '" + (String)entry.getKey() + "' on block '" + Block.field_149771_c.func_177774_c(block) + "'");
            }

            if (map == null) {
               map = Maps.<IProperty<?>, Object>newHashMap();
            }

            map.put(iproperty, optional.get());
         }
      }

      return new EnterBlockTrigger.Instance(block, map);
   }

   public void func_192193_a(EntityPlayerMP p_192193_1_, IBlockState p_192193_2_) {
      EnterBlockTrigger.Listeners enterblocktrigger$listeners = this.field_192197_b.get(p_192193_1_.func_192039_O());
      if (enterblocktrigger$listeners != null) {
         enterblocktrigger$listeners.func_192471_a(p_192193_2_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final Block field_192261_a;
      private final Map<IProperty<?>, Object> field_192262_b;

      public Instance(@Nullable Block p_i47451_1_, @Nullable Map<IProperty<?>, Object> p_i47451_2_) {
         super(EnterBlockTrigger.field_192196_a);
         this.field_192261_a = p_i47451_1_;
         this.field_192262_b = p_i47451_2_;
      }

      public boolean func_192260_a(IBlockState p_192260_1_) {
         if (this.field_192261_a != null && p_192260_1_.func_177230_c() != this.field_192261_a) {
            return false;
         } else {
            if (this.field_192262_b != null) {
               for(Entry<IProperty<?>, Object> entry : this.field_192262_b.entrySet()) {
                  if (p_192260_1_.func_177229_b(entry.getKey()) != entry.getValue()) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192473_a;
      private final Set<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> field_192474_b = Sets.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47452_1_) {
         this.field_192473_a = p_i47452_1_;
      }

      public boolean func_192470_a() {
         return this.field_192474_b.isEmpty();
      }

      public void func_192472_a(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192472_1_) {
         this.field_192474_b.add(p_192472_1_);
      }

      public void func_192469_b(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> p_192469_1_) {
         this.field_192474_b.remove(p_192469_1_);
      }

      public void func_192471_a(IBlockState p_192471_1_) {
         List<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener : this.field_192474_b) {
            if (((EnterBlockTrigger.Instance)listener.func_192158_a()).func_192260_a(p_192471_1_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_192473_a);
            }
         }

      }
   }
}
