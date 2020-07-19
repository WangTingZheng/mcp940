package net.minecraft.advancements;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Arrays;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

public class AdvancementRewards {
   public static final AdvancementRewards field_192114_a = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.field_193519_a);
   private final int field_192115_b;
   private final ResourceLocation[] field_192116_c;
   private final ResourceLocation[] field_192117_d;
   private final FunctionObject.CacheableFunction field_193129_e;

   public AdvancementRewards(int p_i47587_1_, ResourceLocation[] p_i47587_2_, ResourceLocation[] p_i47587_3_, FunctionObject.CacheableFunction p_i47587_4_) {
      this.field_192115_b = p_i47587_1_;
      this.field_192116_c = p_i47587_2_;
      this.field_192117_d = p_i47587_3_;
      this.field_193129_e = p_i47587_4_;
   }

   public void func_192113_a(final EntityPlayerMP p_192113_1_) {
      p_192113_1_.func_71023_q(this.field_192115_b);
      LootContext lootcontext = (new LootContext.Builder(p_192113_1_.func_71121_q())).func_186472_a(p_192113_1_).func_186471_a();
      boolean flag = false;

      for(ResourceLocation resourcelocation : this.field_192116_c) {
         for(ItemStack itemstack : p_192113_1_.field_70170_p.func_184146_ak().func_186521_a(resourcelocation).func_186462_a(p_192113_1_.func_70681_au(), lootcontext)) {
            if (p_192113_1_.func_191521_c(itemstack)) {
               p_192113_1_.field_70170_p.func_184148_a((EntityPlayer)null, p_192113_1_.field_70165_t, p_192113_1_.field_70163_u, p_192113_1_.field_70161_v, SoundEvents.field_187638_cR, SoundCategory.PLAYERS, 0.2F, ((p_192113_1_.func_70681_au().nextFloat() - p_192113_1_.func_70681_au().nextFloat()) * 0.7F + 1.0F) * 2.0F);
               flag = true;
            } else {
               EntityItem entityitem = p_192113_1_.func_71019_a(itemstack, false);
               if (entityitem != null) {
                  entityitem.func_174868_q();
                  entityitem.func_145797_a(p_192113_1_.func_70005_c_());
               }
            }
         }
      }

      if (flag) {
         p_192113_1_.field_71069_bz.func_75142_b();
      }

      if (this.field_192117_d.length > 0) {
         p_192113_1_.func_193102_a(this.field_192117_d);
      }

      final MinecraftServer minecraftserver = p_192113_1_.field_71133_b;
      FunctionObject functionobject = this.field_193129_e.func_193518_a(minecraftserver.func_193030_aL());
      if (functionobject != null) {
         ICommandSender icommandsender = new ICommandSender() {
            public String func_70005_c_() {
               return p_192113_1_.func_70005_c_();
            }

            public ITextComponent func_145748_c_() {
               return p_192113_1_.func_145748_c_();
            }

            public void func_145747_a(ITextComponent p_145747_1_) {
            }

            public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
               return p_70003_1_ <= 2;
            }

            public BlockPos func_180425_c() {
               return p_192113_1_.func_180425_c();
            }

            public Vec3d func_174791_d() {
               return p_192113_1_.func_174791_d();
            }

            public World func_130014_f_() {
               return p_192113_1_.field_70170_p;
            }

            public Entity func_174793_f() {
               return p_192113_1_;
            }

            public boolean func_174792_t_() {
               return minecraftserver.field_71305_c[0].func_82736_K().func_82766_b("commandBlockOutput");
            }

            public void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_) {
               p_192113_1_.func_174794_a(p_174794_1_, p_174794_2_);
            }

            public MinecraftServer func_184102_h() {
               return p_192113_1_.func_184102_h();
            }
         };
         minecraftserver.func_193030_aL().func_194019_a(functionobject, icommandsender);
      }

   }

   public String toString() {
      return "AdvancementRewards{experience=" + this.field_192115_b + ", loot=" + Arrays.toString((Object[])this.field_192116_c) + ", recipes=" + Arrays.toString((Object[])this.field_192117_d) + ", function=" + this.field_193129_e + '}';
   }

   public static class Deserializer implements JsonDeserializer<AdvancementRewards> {
      public AdvancementRewards deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JsonUtils.func_151210_l(p_deserialize_1_, "rewards");
         int i = JsonUtils.func_151208_a(jsonobject, "experience", 0);
         JsonArray jsonarray = JsonUtils.func_151213_a(jsonobject, "loot", new JsonArray());
         ResourceLocation[] aresourcelocation = new ResourceLocation[jsonarray.size()];

         for(int j = 0; j < aresourcelocation.length; ++j) {
            aresourcelocation[j] = new ResourceLocation(JsonUtils.func_151206_a(jsonarray.get(j), "loot[" + j + "]"));
         }

         JsonArray jsonarray1 = JsonUtils.func_151213_a(jsonobject, "recipes", new JsonArray());
         ResourceLocation[] aresourcelocation1 = new ResourceLocation[jsonarray1.size()];

         for(int k = 0; k < aresourcelocation1.length; ++k) {
            aresourcelocation1[k] = new ResourceLocation(JsonUtils.func_151206_a(jsonarray1.get(k), "recipes[" + k + "]"));
            IRecipe irecipe = CraftingManager.func_193373_a(aresourcelocation1[k]);
            if (irecipe == null) {
               throw new JsonSyntaxException("Unknown recipe '" + aresourcelocation1[k] + "'");
            }
         }

         FunctionObject.CacheableFunction functionobject$cacheablefunction;
         if (jsonobject.has("function")) {
            functionobject$cacheablefunction = new FunctionObject.CacheableFunction(new ResourceLocation(JsonUtils.func_151200_h(jsonobject, "function")));
         } else {
            functionobject$cacheablefunction = FunctionObject.CacheableFunction.field_193519_a;
         }

         return new AdvancementRewards(i, aresourcelocation, aresourcelocation1, functionobject$cacheablefunction);
      }
   }
}
