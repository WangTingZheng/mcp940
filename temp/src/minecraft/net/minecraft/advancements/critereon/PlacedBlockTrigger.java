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
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class PlacedBlockTrigger implements ICriterionTrigger<PlacedBlockTrigger.Instance> {
   private static final ResourceLocation field_193174_a = new ResourceLocation("placed_block");
   private final Map<PlayerAdvancements, PlacedBlockTrigger.Listeners> field_193175_b = Maps.<PlayerAdvancements, PlacedBlockTrigger.Listeners>newHashMap();

   public ResourceLocation func_192163_a() {
      return field_193174_a;
   }

   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_192165_2_) {
      PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_192165_1_);
      if (placedblocktrigger$listeners == null) {
         placedblocktrigger$listeners = new PlacedBlockTrigger.Listeners(p_192165_1_);
         this.field_193175_b.put(p_192165_1_, placedblocktrigger$listeners);
      }

      placedblocktrigger$listeners.func_193490_a(p_192165_2_);
   }

   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_192164_2_) {
      PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_192164_1_);
      if (placedblocktrigger$listeners != null) {
         placedblocktrigger$listeners.func_193487_b(p_192164_2_);
         if (placedblocktrigger$listeners.func_193488_a()) {
            this.field_193175_b.remove(p_192164_1_);
         }
      }

   }

   public void func_192167_a(PlayerAdvancements p_192167_1_) {
      this.field_193175_b.remove(p_192167_1_);
   }

   public PlacedBlockTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
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

      LocationPredicate locationpredicate = LocationPredicate.func_193454_a(p_192166_1_.get("location"));
      ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
      return new PlacedBlockTrigger.Instance(block, map, locationpredicate, itempredicate);
   }

   public void func_193173_a(EntityPlayerMP p_193173_1_, BlockPos p_193173_2_, ItemStack p_193173_3_) {
      IBlockState iblockstate = p_193173_1_.field_70170_p.func_180495_p(p_193173_2_);
      PlacedBlockTrigger.Listeners placedblocktrigger$listeners = this.field_193175_b.get(p_193173_1_.func_192039_O());
      if (placedblocktrigger$listeners != null) {
         placedblocktrigger$listeners.func_193489_a(iblockstate, p_193173_2_, p_193173_1_.func_71121_q(), p_193173_3_);
      }

   }

   public static class Instance extends AbstractCriterionInstance {
      private final Block field_193211_a;
      private final Map<IProperty<?>, Object> field_193212_b;
      private final LocationPredicate field_193213_c;
      private final ItemPredicate field_193214_d;

      public Instance(@Nullable Block p_i47566_1_, @Nullable Map<IProperty<?>, Object> p_i47566_2_, LocationPredicate p_i47566_3_, ItemPredicate p_i47566_4_) {
         super(PlacedBlockTrigger.field_193174_a);
         this.field_193211_a = p_i47566_1_;
         this.field_193212_b = p_i47566_2_;
         this.field_193213_c = p_i47566_3_;
         this.field_193214_d = p_i47566_4_;
      }

      public boolean func_193210_a(IBlockState p_193210_1_, BlockPos p_193210_2_, WorldServer p_193210_3_, ItemStack p_193210_4_) {
         if (this.field_193211_a != null && p_193210_1_.func_177230_c() != this.field_193211_a) {
            return false;
         } else {
            if (this.field_193212_b != null) {
               for(Entry<IProperty<?>, Object> entry : this.field_193212_b.entrySet()) {
                  if (p_193210_1_.func_177229_b(entry.getKey()) != entry.getValue()) {
                     return false;
                  }
               }
            }

            if (!this.field_193213_c.func_193453_a(p_193210_3_, (float)p_193210_2_.func_177958_n(), (float)p_193210_2_.func_177956_o(), (float)p_193210_2_.func_177952_p())) {
               return false;
            } else {
               return this.field_193214_d.func_192493_a(p_193210_4_);
            }
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193491_a;
      private final Set<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> field_193492_b = Sets.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements p_i47567_1_) {
         this.field_193491_a = p_i47567_1_;
      }

      public boolean func_193488_a() {
         return this.field_193492_b.isEmpty();
      }

      public void func_193490_a(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_193490_1_) {
         this.field_193492_b.add(p_193490_1_);
      }

      public void func_193487_b(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> p_193487_1_) {
         this.field_193492_b.remove(p_193487_1_);
      }

      public void func_193489_a(IBlockState p_193489_1_, BlockPos p_193489_2_, WorldServer p_193489_3_, ItemStack p_193489_4_) {
         List<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> list = null;

         for(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener : this.field_193492_b) {
            if (((PlacedBlockTrigger.Instance)listener.func_192158_a()).func_193210_a(p_193489_1_, p_193489_2_, p_193489_3_, p_193489_4_)) {
               if (list == null) {
                  list = Lists.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> listener1 : list) {
               listener1.func_192159_a(this.field_193491_a);
            }
         }

      }
   }
}
