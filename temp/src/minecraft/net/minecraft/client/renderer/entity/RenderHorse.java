package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

public class RenderHorse extends RenderLiving<EntityHorse> {
   private static final Map<String, ResourceLocation> field_110852_a = Maps.<String, ResourceLocation>newHashMap();

   public RenderHorse(RenderManager p_i47205_1_) {
      super(p_i47205_1_, new ModelHorse(), 0.75F);
   }

   protected ResourceLocation func_110775_a(EntityHorse p_110775_1_) {
      String s = p_110775_1_.func_110264_co();
      ResourceLocation resourcelocation = field_110852_a.get(s);
      if (resourcelocation == null) {
         resourcelocation = new ResourceLocation(s);
         Minecraft.func_71410_x().func_110434_K().func_110579_a(resourcelocation, new LayeredTexture(p_110775_1_.func_110212_cp()));
         field_110852_a.put(s, resourcelocation);
      }

      return resourcelocation;
   }
}
