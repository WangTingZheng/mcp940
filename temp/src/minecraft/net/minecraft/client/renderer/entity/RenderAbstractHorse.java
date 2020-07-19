package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.util.ResourceLocation;

public class RenderAbstractHorse extends RenderLiving<AbstractHorse> {
   private static final Map<Class<?>, ResourceLocation> field_191359_a = Maps.<Class<?>, ResourceLocation>newHashMap();
   private final float field_191360_j;

   public RenderAbstractHorse(RenderManager p_i47212_1_) {
      this(p_i47212_1_, 1.0F);
   }

   public RenderAbstractHorse(RenderManager p_i47213_1_, float p_i47213_2_) {
      super(p_i47213_1_, new ModelHorse(), 0.75F);
      this.field_191360_j = p_i47213_2_;
   }

   protected void func_77041_b(AbstractHorse p_77041_1_, float p_77041_2_) {
      GlStateManager.func_179152_a(this.field_191360_j, this.field_191360_j, this.field_191360_j);
      super.func_77041_b(p_77041_1_, p_77041_2_);
   }

   protected ResourceLocation func_110775_a(AbstractHorse p_110775_1_) {
      return field_191359_a.get(p_110775_1_.getClass());
   }

   static {
      field_191359_a.put(EntityDonkey.class, new ResourceLocation("textures/entity/horse/donkey.png"));
      field_191359_a.put(EntityMule.class, new ResourceLocation("textures/entity/horse/mule.png"));
      field_191359_a.put(EntityZombieHorse.class, new ResourceLocation("textures/entity/horse/horse_zombie.png"));
      field_191359_a.put(EntitySkeletonHorse.class, new ResourceLocation("textures/entity/horse/horse_skeleton.png"));
   }
}
