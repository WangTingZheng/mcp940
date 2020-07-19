package net.minecraft.client.renderer.block.model;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.multipart.Multipart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.texture.ITextureMapPopulator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.registry.RegistrySimple;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
   private static final Set<ResourceLocation> field_177602_b = Sets.newHashSet(new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/water_overlay"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots"), new ResourceLocation("items/empty_armor_slot_shield"), new ResourceLocation("blocks/shulker_top_white"), new ResourceLocation("blocks/shulker_top_orange"), new ResourceLocation("blocks/shulker_top_magenta"), new ResourceLocation("blocks/shulker_top_light_blue"), new ResourceLocation("blocks/shulker_top_yellow"), new ResourceLocation("blocks/shulker_top_lime"), new ResourceLocation("blocks/shulker_top_pink"), new ResourceLocation("blocks/shulker_top_gray"), new ResourceLocation("blocks/shulker_top_silver"), new ResourceLocation("blocks/shulker_top_cyan"), new ResourceLocation("blocks/shulker_top_purple"), new ResourceLocation("blocks/shulker_top_blue"), new ResourceLocation("blocks/shulker_top_brown"), new ResourceLocation("blocks/shulker_top_green"), new ResourceLocation("blocks/shulker_top_red"), new ResourceLocation("blocks/shulker_top_black"));
   private static final Logger field_177603_c = LogManager.getLogger();
   protected static final ModelResourceLocation field_177604_a = new ModelResourceLocation("builtin/missing", "missing");
   private static final String field_188641_d = "{    'textures': {       'particle': 'missingno',       'missingno': 'missingno'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}".replaceAll("'", "\"");
   private static final Map<String, String> field_177600_d = Maps.<String, String>newHashMap();
   private static final Joiner field_177601_e = Joiner.on(" -> ");
   private final IResourceManager field_177598_f;
   private final Map<ResourceLocation, TextureAtlasSprite> field_177599_g = Maps.<ResourceLocation, TextureAtlasSprite>newHashMap();
   private final Map<ResourceLocation, ModelBlock> field_177611_h = Maps.<ResourceLocation, ModelBlock>newLinkedHashMap();
   private final Map<ModelResourceLocation, VariantList> field_177612_i = Maps.<ModelResourceLocation, VariantList>newLinkedHashMap();
   private final Map<ModelBlockDefinition, Collection<ModelResourceLocation>> field_188642_k = Maps.<ModelBlockDefinition, Collection<ModelResourceLocation>>newLinkedHashMap();
   private final TextureMap field_177609_j;
   private final BlockModelShapes field_177610_k;
   private final FaceBakery field_177607_l = new FaceBakery();
   private final ItemModelGenerator field_177608_m = new ItemModelGenerator();
   private final RegistrySimple<ModelResourceLocation, IBakedModel> field_177605_n = new RegistrySimple<ModelResourceLocation, IBakedModel>();
   private static final String field_188643_q = "{    'elements': [        {   'from': [0, 0, 0],            'to': [16, 16, 16],            'faces': {                'down': {'uv': [0, 0, 16, 16], 'texture': '' }            }        }    ]}".replaceAll("'", "\"");
   private static final ModelBlock field_177606_o = ModelBlock.func_178294_a(field_188643_q);
   private static final ModelBlock field_177616_r = ModelBlock.func_178294_a(field_188643_q);
   private final Map<String, ResourceLocation> field_177615_s = Maps.<String, ResourceLocation>newLinkedHashMap();
   private final Map<ResourceLocation, ModelBlockDefinition> field_177614_t = Maps.<ResourceLocation, ModelBlockDefinition>newHashMap();
   private final Map<Item, List<String>> field_177613_u = Maps.<Item, List<String>>newIdentityHashMap();

   public ModelBakery(IResourceManager p_i46085_1_, TextureMap p_i46085_2_, BlockModelShapes p_i46085_3_) {
      this.field_177598_f = p_i46085_1_;
      this.field_177609_j = p_i46085_2_;
      this.field_177610_k = p_i46085_3_;
   }

   public IRegistry<ModelResourceLocation, IBakedModel> func_177570_a() {
      this.func_188640_b();
      this.func_177577_b();
      this.func_177597_h();
      this.func_177572_j();
      this.func_177593_l();
      this.func_177588_f();
      this.func_188635_i();
      return this.field_177605_n;
   }

   private void func_188640_b() {
      BlockStateMapper blockstatemapper = this.field_177610_k.func_178120_a();

      for(Block block : Block.field_149771_c) {
         for(final ResourceLocation resourcelocation : blockstatemapper.func_188182_a(block)) {
            try {
               ModelBlockDefinition modelblockdefinition = this.func_177586_a(resourcelocation);
               Map<IBlockState, ModelResourceLocation> map = blockstatemapper.func_188181_b(block);
               if (modelblockdefinition.func_188002_b()) {
                  Collection<ModelResourceLocation> collection = Sets.newHashSet(map.values());
                  modelblockdefinition.func_188001_c().func_188138_a(block.func_176194_O());
                  Collection<ModelResourceLocation> collection1 = (Collection)this.field_188642_k.get(modelblockdefinition);
                  if (collection1 == null) {
                     collection1 = Lists.<ModelResourceLocation>newArrayList();
                     this.field_188642_k.put(modelblockdefinition, collection1);
                  }

                  collection1.addAll(Lists.newArrayList(Iterables.filter(collection, new Predicate<ModelResourceLocation>() {
                     public boolean apply(@Nullable ModelResourceLocation p_apply_1_) {
                        return resourcelocation.equals(p_apply_1_);
                     }
                  })));
               }

               for(Entry<IBlockState, ModelResourceLocation> entry : map.entrySet()) {
                  ModelResourceLocation modelresourcelocation = entry.getValue();
                  if (resourcelocation.equals(modelresourcelocation)) {
                     try {
                        this.field_177612_i.put(modelresourcelocation, modelblockdefinition.func_188004_c(modelresourcelocation.func_177518_c()));
                     } catch (RuntimeException var12) {
                        if (!modelblockdefinition.func_188002_b()) {
                           field_177603_c.warn("Unable to load variant: {} from {}", modelresourcelocation.func_177518_c(), modelresourcelocation);
                        }
                     }
                  }
               }
            } catch (Exception exception) {
               field_177603_c.warn("Unable to load definition {}", resourcelocation, exception);
            }
         }
      }

   }

   private void func_177577_b() {
      this.field_177612_i.put(field_177604_a, new VariantList(Lists.newArrayList(new Variant(new ResourceLocation(field_177604_a.func_110623_a()), ModelRotation.X0_Y0, false, 1))));
      this.func_191401_d();
      this.func_177595_c();
      this.func_188637_e();
      this.func_177590_d();
   }

   private void func_191401_d() {
      ResourceLocation resourcelocation = new ResourceLocation("item_frame");
      ModelBlockDefinition modelblockdefinition = this.func_177586_a(resourcelocation);
      this.func_177569_a(modelblockdefinition, new ModelResourceLocation(resourcelocation, "normal"));
      this.func_177569_a(modelblockdefinition, new ModelResourceLocation(resourcelocation, "map"));
   }

   private void func_177569_a(ModelBlockDefinition p_177569_1_, ModelResourceLocation p_177569_2_) {
      try {
         this.field_177612_i.put(p_177569_2_, p_177569_1_.func_188004_c(p_177569_2_.func_177518_c()));
      } catch (RuntimeException var4) {
         if (!p_177569_1_.func_188002_b()) {
            field_177603_c.warn("Unable to load variant: {} from {}", p_177569_2_.func_177518_c(), p_177569_2_);
         }
      }

   }

   private ModelBlockDefinition func_177586_a(ResourceLocation p_177586_1_) {
      ResourceLocation resourcelocation = this.func_188631_b(p_177586_1_);
      ModelBlockDefinition modelblockdefinition = this.field_177614_t.get(resourcelocation);
      if (modelblockdefinition == null) {
         modelblockdefinition = this.func_188632_a(p_177586_1_, resourcelocation);
         this.field_177614_t.put(resourcelocation, modelblockdefinition);
      }

      return modelblockdefinition;
   }

   private ModelBlockDefinition func_188632_a(ResourceLocation p_188632_1_, ResourceLocation p_188632_2_) {
      List<ModelBlockDefinition> list = Lists.<ModelBlockDefinition>newArrayList();

      try {
         for(IResource iresource : this.field_177598_f.func_135056_b(p_188632_2_)) {
            list.add(this.func_188636_a(p_188632_1_, iresource));
         }
      } catch (IOException ioexception) {
         throw new RuntimeException("Encountered an exception when loading model definition of model " + p_188632_2_, ioexception);
      }

      return new ModelBlockDefinition(list);
   }

   private ModelBlockDefinition func_188636_a(ResourceLocation p_188636_1_, IResource p_188636_2_) {
      InputStream inputstream = null;

      ModelBlockDefinition lvt_4_1_;
      try {
         inputstream = p_188636_2_.func_110527_b();
         lvt_4_1_ = ModelBlockDefinition.func_178331_a(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
      } catch (Exception exception) {
         throw new RuntimeException("Encountered an exception when loading model definition of '" + p_188636_1_ + "' from: '" + p_188636_2_.func_177241_a() + "' in resourcepack: '" + p_188636_2_.func_177240_d() + "'", exception);
      } finally {
         IOUtils.closeQuietly(inputstream);
      }

      return lvt_4_1_;
   }

   private ResourceLocation func_188631_b(ResourceLocation p_188631_1_) {
      return new ResourceLocation(p_188631_1_.func_110624_b(), "blockstates/" + p_188631_1_.func_110623_a() + ".json");
   }

   private void func_177595_c() {
      for(Entry<ModelResourceLocation, VariantList> entry : this.field_177612_i.entrySet()) {
         this.func_188638_a(entry.getKey(), entry.getValue());
      }

   }

   private void func_188637_e() {
      for(Entry<ModelBlockDefinition, Collection<ModelResourceLocation>> entry : this.field_188642_k.entrySet()) {
         ModelResourceLocation modelresourcelocation = (ModelResourceLocation)(entry.getValue()).iterator().next();

         for(VariantList variantlist : (entry.getKey()).func_188003_a()) {
            this.func_188638_a(modelresourcelocation, variantlist);
         }
      }

   }

   private void func_188638_a(ModelResourceLocation p_188638_1_, VariantList p_188638_2_) {
      for(Variant variant : p_188638_2_.func_188114_a()) {
         ResourceLocation resourcelocation = variant.func_188046_a();
         if (this.field_177611_h.get(resourcelocation) == null) {
            try {
               this.field_177611_h.put(resourcelocation, this.func_177594_c(resourcelocation));
            } catch (Exception exception) {
               field_177603_c.warn("Unable to load block model: '{}' for variant: '{}': {} ", resourcelocation, p_188638_1_, exception);
            }
         }
      }

   }

   private ModelBlock func_177594_c(ResourceLocation p_177594_1_) throws IOException {
      Reader reader = null;
      IResource iresource = null;

      ModelBlock lvt_5_2_;
      try {
         String s = p_177594_1_.func_110623_a();
         if (!"builtin/generated".equals(s)) {
            if ("builtin/entity".equals(s)) {
               lvt_5_2_ = field_177616_r;
               return lvt_5_2_;
            }

            if (s.startsWith("builtin/")) {
               String s2 = s.substring("builtin/".length());
               String s1 = field_177600_d.get(s2);
               if (s1 == null) {
                  throw new FileNotFoundException(p_177594_1_.toString());
               }

               reader = new StringReader(s1);
            } else {
               iresource = this.field_177598_f.func_110536_a(this.func_177580_d(p_177594_1_));
               reader = new InputStreamReader(iresource.func_110527_b(), StandardCharsets.UTF_8);
            }

            lvt_5_2_ = ModelBlock.func_178307_a(reader);
            lvt_5_2_.field_178317_b = p_177594_1_.toString();
            ModelBlock modelblock1 = lvt_5_2_;
            return modelblock1;
         }

         lvt_5_2_ = field_177606_o;
      } finally {
         IOUtils.closeQuietly(reader);
         IOUtils.closeQuietly((Closeable)iresource);
      }

      return lvt_5_2_;
   }

   private ResourceLocation func_177580_d(ResourceLocation p_177580_1_) {
      return new ResourceLocation(p_177580_1_.func_110624_b(), "models/" + p_177580_1_.func_110623_a() + ".json");
   }

   private void func_177590_d() {
      this.func_177592_e();

      for(Item item : Item.field_150901_e) {
         for(String s : this.func_177596_a(item)) {
            ResourceLocation resourcelocation = this.func_177583_a(s);
            ResourceLocation resourcelocation1 = Item.field_150901_e.func_177774_c(item);
            this.func_188634_a(s, resourcelocation, resourcelocation1);
            if (item.func_185040_i()) {
               ModelBlock modelblock = this.field_177611_h.get(resourcelocation);
               if (modelblock != null) {
                  for(ResourceLocation resourcelocation2 : modelblock.func_187965_e()) {
                     this.func_188634_a(resourcelocation2.toString(), resourcelocation2, resourcelocation1);
                  }
               }
            }
         }
      }

   }

   private void func_188634_a(String p_188634_1_, ResourceLocation p_188634_2_, ResourceLocation p_188634_3_) {
      this.field_177615_s.put(p_188634_1_, p_188634_2_);
      if (this.field_177611_h.get(p_188634_2_) == null) {
         try {
            ModelBlock modelblock = this.func_177594_c(p_188634_2_);
            this.field_177611_h.put(p_188634_2_, modelblock);
         } catch (Exception exception) {
            field_177603_c.warn("Unable to load item model: '{}' for item: '{}'", p_188634_2_, p_188634_3_, exception);
         }

      }
   }

   private void func_177592_e() {
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150348_b), Lists.newArrayList("stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150346_d), Lists.newArrayList("dirt", "coarse_dirt", "podzol"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150344_f), Lists.newArrayList("oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150345_g), Lists.newArrayList("oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150354_m), Lists.newArrayList("sand", "red_sand"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150364_r), Lists.newArrayList("oak_log", "spruce_log", "birch_log", "jungle_log"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150362_t), Lists.newArrayList("oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150360_v), Lists.newArrayList("sponge", "sponge_wet"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150322_A), Lists.newArrayList("sandstone", "chiseled_sandstone", "smooth_sandstone"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_180395_cM), Lists.newArrayList("red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150329_H), Lists.newArrayList("dead_bush", "tall_grass", "fern"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150330_I), Lists.newArrayList("dead_bush"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150325_L), Lists.newArrayList("black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150327_N), Lists.newArrayList("dandelion"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150328_O), Lists.newArrayList("poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150333_U), Lists.newArrayList("stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_180389_cP), Lists.newArrayList("red_sandstone_slab"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150399_cn), Lists.newArrayList("black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150418_aU), Lists.newArrayList("stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150417_aV), Lists.newArrayList("stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150376_bx), Lists.newArrayList("oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150463_bK), Lists.newArrayList("cobblestone_wall", "mossy_cobblestone_wall"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150467_bQ), Lists.newArrayList("anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150371_ca), Lists.newArrayList("quartz_block", "chiseled_quartz_block", "quartz_column"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150406_ce), Lists.newArrayList("black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150397_co), Lists.newArrayList("black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150361_u), Lists.newArrayList("acacia_leaves", "dark_oak_leaves"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150363_s), Lists.newArrayList("acacia_log", "dark_oak_log"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_180397_cI), Lists.newArrayList("prismarine", "prismarine_bricks", "dark_prismarine"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150404_cg), Lists.newArrayList("black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150398_cm), Lists.newArrayList("sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia"));
      this.field_177613_u.put(Items.field_151044_h, Lists.newArrayList("coal", "charcoal"));
      this.field_177613_u.put(Items.field_151115_aP, Lists.newArrayList("cod", "salmon", "clownfish", "pufferfish"));
      this.field_177613_u.put(Items.field_179566_aV, Lists.newArrayList("cooked_cod", "cooked_salmon"));
      this.field_177613_u.put(Items.field_151100_aR, Lists.newArrayList("dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white"));
      this.field_177613_u.put(Items.field_151068_bn, Lists.newArrayList("bottle_drinkable"));
      this.field_177613_u.put(Items.field_151144_bL, Lists.newArrayList("skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper", "skull_dragon"));
      this.field_177613_u.put(Items.field_185155_bH, Lists.newArrayList("bottle_splash"));
      this.field_177613_u.put(Items.field_185156_bI, Lists.newArrayList("bottle_lingering"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_192443_dR), Lists.newArrayList("black_concrete", "red_concrete", "green_concrete", "brown_concrete", "blue_concrete", "purple_concrete", "cyan_concrete", "silver_concrete", "gray_concrete", "pink_concrete", "lime_concrete", "yellow_concrete", "light_blue_concrete", "magenta_concrete", "orange_concrete", "white_concrete"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_192444_dS), Lists.newArrayList("black_concrete_powder", "red_concrete_powder", "green_concrete_powder", "brown_concrete_powder", "blue_concrete_powder", "purple_concrete_powder", "cyan_concrete_powder", "silver_concrete_powder", "gray_concrete_powder", "pink_concrete_powder", "lime_concrete_powder", "yellow_concrete_powder", "light_blue_concrete_powder", "magenta_concrete_powder", "orange_concrete_powder", "white_concrete_powder"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_150350_a), Collections.emptyList());
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_180390_bo), Lists.newArrayList("oak_fence_gate"));
      this.field_177613_u.put(Item.func_150898_a(Blocks.field_180407_aO), Lists.newArrayList("oak_fence"));
      this.field_177613_u.put(Items.field_179570_aq, Lists.newArrayList("oak_door"));
      this.field_177613_u.put(Items.field_151124_az, Lists.newArrayList("oak_boat"));
      this.field_177613_u.put(Items.field_190929_cY, Lists.newArrayList("totem"));
   }

   private List<String> func_177596_a(Item p_177596_1_) {
      List<String> list = (List)this.field_177613_u.get(p_177596_1_);
      if (list == null) {
         list = Collections.<String>singletonList(((ResourceLocation)Item.field_150901_e.func_177774_c(p_177596_1_)).toString());
      }

      return list;
   }

   private ResourceLocation func_177583_a(String p_177583_1_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_177583_1_);
      return new ResourceLocation(resourcelocation.func_110624_b(), "item/" + resourcelocation.func_110623_a());
   }

   private void func_177588_f() {
      for(ModelResourceLocation modelresourcelocation : this.field_177612_i.keySet()) {
         IBakedModel ibakedmodel = this.func_188639_a(this.field_177612_i.get(modelresourcelocation), modelresourcelocation.toString());
         if (ibakedmodel != null) {
            this.field_177605_n.func_82595_a(modelresourcelocation, ibakedmodel);
         }
      }

      for(Entry<ModelBlockDefinition, Collection<ModelResourceLocation>> entry : this.field_188642_k.entrySet()) {
         ModelBlockDefinition modelblockdefinition = entry.getKey();
         Multipart multipart = modelblockdefinition.func_188001_c();
         String s = ((ResourceLocation)Block.field_149771_c.func_177774_c(multipart.func_188135_c().func_177622_c())).toString();
         MultipartBakedModel.Builder multipartbakedmodel$builder = new MultipartBakedModel.Builder();

         for(Selector selector : multipart.func_188136_a()) {
            IBakedModel ibakedmodel1 = this.func_188639_a(selector.func_188165_a(), "selector of " + s);
            if (ibakedmodel1 != null) {
               multipartbakedmodel$builder.func_188648_a(selector.func_188166_a(multipart.func_188135_c()), ibakedmodel1);
            }
         }

         IBakedModel ibakedmodel2 = multipartbakedmodel$builder.func_188647_a();

         for(ModelResourceLocation modelresourcelocation1 : entry.getValue()) {
            if (!modelblockdefinition.func_188000_b(modelresourcelocation1.func_177518_c())) {
               this.field_177605_n.func_82595_a(modelresourcelocation1, ibakedmodel2);
            }
         }
      }

   }

   @Nullable
   private IBakedModel func_188639_a(VariantList p_188639_1_, String p_188639_2_) {
      if (p_188639_1_.func_188114_a().isEmpty()) {
         return null;
      } else {
         WeightedBakedModel.Builder weightedbakedmodel$builder = new WeightedBakedModel.Builder();
         int i = 0;

         for(Variant variant : p_188639_1_.func_188114_a()) {
            ModelBlock modelblock = this.field_177611_h.get(variant.func_188046_a());
            if (modelblock != null && modelblock.func_178303_d()) {
               if (modelblock.func_178298_a().isEmpty()) {
                  field_177603_c.warn("Missing elements for: {}", (Object)p_188639_2_);
               } else {
                  IBakedModel ibakedmodel = this.func_177578_a(modelblock, variant.func_188048_b(), variant.func_188049_c());
                  if (ibakedmodel != null) {
                     ++i;
                     weightedbakedmodel$builder.func_177677_a(ibakedmodel, variant.func_188047_d());
                  }
               }
            } else {
               field_177603_c.warn("Missing model for: {}", (Object)p_188639_2_);
            }
         }

         IBakedModel ibakedmodel1 = null;
         if (i == 0) {
            field_177603_c.warn("No weighted models for: {}", (Object)p_188639_2_);
         } else if (i == 1) {
            ibakedmodel1 = weightedbakedmodel$builder.func_177675_b();
         } else {
            ibakedmodel1 = weightedbakedmodel$builder.func_177676_a();
         }

         return ibakedmodel1;
      }
   }

   private void func_188635_i() {
      for(Entry<String, ResourceLocation> entry : this.field_177615_s.entrySet()) {
         ResourceLocation resourcelocation = entry.getValue();
         ModelResourceLocation modelresourcelocation = new ModelResourceLocation(entry.getKey(), "inventory");
         ModelBlock modelblock = this.field_177611_h.get(resourcelocation);
         if (modelblock != null && modelblock.func_178303_d()) {
            if (modelblock.func_178298_a().isEmpty()) {
               field_177603_c.warn("Missing elements for: {}", (Object)resourcelocation);
            } else if (this.func_177587_c(modelblock)) {
               this.field_177605_n.func_82595_a(modelresourcelocation, new BuiltInModel(modelblock.func_181682_g(), modelblock.func_187967_g()));
            } else {
               IBakedModel ibakedmodel = this.func_177578_a(modelblock, ModelRotation.X0_Y0, false);
               if (ibakedmodel != null) {
                  this.field_177605_n.func_82595_a(modelresourcelocation, ibakedmodel);
               }
            }
         } else {
            field_177603_c.warn("Missing model for: {}", (Object)resourcelocation);
         }
      }

   }

   private Set<ResourceLocation> func_177575_g() {
      Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();
      List<ModelResourceLocation> list = Lists.newArrayList(this.field_177612_i.keySet());
      Collections.sort(list, new Comparator<ModelResourceLocation>() {
         public int compare(ModelResourceLocation p_compare_1_, ModelResourceLocation p_compare_2_) {
            return p_compare_1_.toString().compareTo(p_compare_2_.toString());
         }
      });

      for(ModelResourceLocation modelresourcelocation : list) {
         VariantList variantlist = this.field_177612_i.get(modelresourcelocation);

         for(Variant variant : variantlist.func_188114_a()) {
            ModelBlock modelblock = this.field_177611_h.get(variant.func_188046_a());
            if (modelblock == null) {
               field_177603_c.warn("Missing model for: {}", (Object)modelresourcelocation);
            } else {
               set.addAll(this.func_177585_a(modelblock));
            }
         }
      }

      for(ModelBlockDefinition modelblockdefinition : this.field_188642_k.keySet()) {
         for(VariantList variantlist1 : modelblockdefinition.func_188001_c().func_188137_b()) {
            for(Variant variant1 : variantlist1.func_188114_a()) {
               ModelBlock modelblock1 = this.field_177611_h.get(variant1.func_188046_a());
               if (modelblock1 == null) {
                  field_177603_c.warn("Missing model for: {}", Block.field_149771_c.func_177774_c(modelblockdefinition.func_188001_c().func_188135_c().func_177622_c()));
               } else {
                  set.addAll(this.func_177585_a(modelblock1));
               }
            }
         }
      }

      set.addAll(field_177602_b);
      return set;
   }

   @Nullable
   private IBakedModel func_177578_a(ModelBlock p_177578_1_, ModelRotation p_177578_2_, boolean p_177578_3_) {
      TextureAtlasSprite textureatlassprite = this.field_177599_g.get(new ResourceLocation(p_177578_1_.func_178308_c("particle")));
      SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(p_177578_1_, p_177578_1_.func_187967_g())).func_177646_a(textureatlassprite);
      if (p_177578_1_.func_178298_a().isEmpty()) {
         return null;
      } else {
         for(BlockPart blockpart : p_177578_1_.func_178298_a()) {
            for(EnumFacing enumfacing : blockpart.field_178240_c.keySet()) {
               BlockPartFace blockpartface = blockpart.field_178240_c.get(enumfacing);
               TextureAtlasSprite textureatlassprite1 = this.field_177599_g.get(new ResourceLocation(p_177578_1_.func_178308_c(blockpartface.field_178242_d)));
               if (blockpartface.field_178244_b == null) {
                  simplebakedmodel$builder.func_177648_a(this.func_177589_a(blockpart, blockpartface, textureatlassprite1, enumfacing, p_177578_2_, p_177578_3_));
               } else {
                  simplebakedmodel$builder.func_177650_a(p_177578_2_.func_177523_a(blockpartface.field_178244_b), this.func_177589_a(blockpart, blockpartface, textureatlassprite1, enumfacing, p_177578_2_, p_177578_3_));
               }
            }
         }

         return simplebakedmodel$builder.func_177645_b();
      }
   }

   private BakedQuad func_177589_a(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, ModelRotation p_177589_5_, boolean p_177589_6_) {
      return this.field_177607_l.func_178414_a(p_177589_1_.field_178241_a, p_177589_1_.field_178239_b, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.field_178237_d, p_177589_6_, p_177589_1_.field_178238_e);
   }

   private void func_177597_h() {
      this.func_177574_i();

      for(ModelBlock modelblock : this.field_177611_h.values()) {
         modelblock.func_178299_a(this.field_177611_h);
      }

      ModelBlock.func_178312_b(this.field_177611_h);
   }

   private void func_177574_i() {
      Deque<ResourceLocation> deque = Queues.<ResourceLocation>newArrayDeque();
      Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();

      for(ResourceLocation resourcelocation : this.field_177611_h.keySet()) {
         set.add(resourcelocation);
         this.func_188633_a(deque, set, this.field_177611_h.get(resourcelocation));
      }

      while(!deque.isEmpty()) {
         ResourceLocation resourcelocation1 = deque.pop();

         try {
            if (this.field_177611_h.get(resourcelocation1) != null) {
               continue;
            }

            ModelBlock modelblock = this.func_177594_c(resourcelocation1);
            this.field_177611_h.put(resourcelocation1, modelblock);
            this.func_188633_a(deque, set, modelblock);
         } catch (Exception exception) {
            field_177603_c.warn("In parent chain: {}; unable to load model: '{}'", field_177601_e.join(this.func_177573_e(resourcelocation1)), resourcelocation1, exception);
         }

         set.add(resourcelocation1);
      }

   }

   private void func_188633_a(Deque<ResourceLocation> p_188633_1_, Set<ResourceLocation> p_188633_2_, ModelBlock p_188633_3_) {
      ResourceLocation resourcelocation = p_188633_3_.func_178305_e();
      if (resourcelocation != null && !p_188633_2_.contains(resourcelocation)) {
         p_188633_1_.add(resourcelocation);
      }

   }

   private List<ResourceLocation> func_177573_e(ResourceLocation p_177573_1_) {
      List<ResourceLocation> list = Lists.newArrayList(p_177573_1_);
      ResourceLocation resourcelocation = p_177573_1_;

      while((resourcelocation = this.func_177576_f(resourcelocation)) != null) {
         list.add(0, resourcelocation);
      }

      return list;
   }

   @Nullable
   private ResourceLocation func_177576_f(ResourceLocation p_177576_1_) {
      for(Entry<ResourceLocation, ModelBlock> entry : this.field_177611_h.entrySet()) {
         ModelBlock modelblock = entry.getValue();
         if (modelblock != null && p_177576_1_.equals(modelblock.func_178305_e())) {
            return entry.getKey();
         }
      }

      return null;
   }

   private Set<ResourceLocation> func_177585_a(ModelBlock p_177585_1_) {
      Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();

      for(BlockPart blockpart : p_177585_1_.func_178298_a()) {
         for(BlockPartFace blockpartface : blockpart.field_178240_c.values()) {
            ResourceLocation resourcelocation = new ResourceLocation(p_177585_1_.func_178308_c(blockpartface.field_178242_d));
            set.add(resourcelocation);
         }
      }

      set.add(new ResourceLocation(p_177585_1_.func_178308_c("particle")));
      return set;
   }

   private void func_177572_j() {
      final Set<ResourceLocation> set = this.func_177575_g();
      set.addAll(this.func_177571_k());
      set.remove(TextureMap.field_174945_f);
      ITextureMapPopulator itexturemappopulator = new ITextureMapPopulator() {
         public void func_177059_a(TextureMap p_177059_1_) {
            for(ResourceLocation resourcelocation : set) {
               TextureAtlasSprite textureatlassprite = p_177059_1_.func_174942_a(resourcelocation);
               ModelBakery.this.field_177599_g.put(resourcelocation, textureatlassprite);
            }

         }
      };
      this.field_177609_j.func_174943_a(this.field_177598_f, itexturemappopulator);
      this.field_177599_g.put(new ResourceLocation("missingno"), this.field_177609_j.func_174944_f());
   }

   private Set<ResourceLocation> func_177571_k() {
      Set<ResourceLocation> set = Sets.<ResourceLocation>newHashSet();

      for(ResourceLocation resourcelocation : this.field_177615_s.values()) {
         ModelBlock modelblock = this.field_177611_h.get(resourcelocation);
         if (modelblock != null) {
            set.add(new ResourceLocation(modelblock.func_178308_c("particle")));
            if (this.func_177581_b(modelblock)) {
               for(String s : ItemModelGenerator.field_178398_a) {
                  set.add(new ResourceLocation(modelblock.func_178308_c(s)));
               }
            } else if (!this.func_177587_c(modelblock)) {
               for(BlockPart blockpart : modelblock.func_178298_a()) {
                  for(BlockPartFace blockpartface : blockpart.field_178240_c.values()) {
                     ResourceLocation resourcelocation1 = new ResourceLocation(modelblock.func_178308_c(blockpartface.field_178242_d));
                     set.add(resourcelocation1);
                  }
               }
            }
         }
      }

      return set;
   }

   private boolean func_177581_b(@Nullable ModelBlock p_177581_1_) {
      if (p_177581_1_ == null) {
         return false;
      } else {
         return p_177581_1_.func_178310_f() == field_177606_o;
      }
   }

   private boolean func_177587_c(@Nullable ModelBlock p_177587_1_) {
      if (p_177587_1_ == null) {
         return false;
      } else {
         ModelBlock modelblock = p_177587_1_.func_178310_f();
         return modelblock == field_177616_r;
      }
   }

   private void func_177593_l() {
      for(ResourceLocation resourcelocation : this.field_177615_s.values()) {
         ModelBlock modelblock = this.field_177611_h.get(resourcelocation);
         if (this.func_177581_b(modelblock)) {
            ModelBlock modelblock1 = this.func_177582_d(modelblock);
            if (modelblock1 != null) {
               modelblock1.field_178317_b = resourcelocation.toString();
            }

            this.field_177611_h.put(resourcelocation, modelblock1);
         } else if (this.func_177587_c(modelblock)) {
            this.field_177611_h.put(resourcelocation, modelblock);
         }
      }

      for(TextureAtlasSprite textureatlassprite : this.field_177599_g.values()) {
         if (!textureatlassprite.func_130098_m()) {
            textureatlassprite.func_130103_l();
         }
      }

   }

   private ModelBlock func_177582_d(ModelBlock p_177582_1_) {
      return this.field_177608_m.func_178392_a(this.field_177609_j, p_177582_1_);
   }

   static {
      field_177600_d.put("missing", field_188641_d);
      field_177606_o.field_178317_b = "generation marker";
      field_177616_r.field_178317_b = "block entity marker";
   }
}
