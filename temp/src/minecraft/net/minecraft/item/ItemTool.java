package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {
   private final Set<Block> field_150914_c;
   protected float field_77864_a;
   protected float field_77865_bY;
   protected float field_185065_c;
   protected Item.ToolMaterial field_77862_b;

   protected ItemTool(float p_i46745_1_, float p_i46745_2_, Item.ToolMaterial p_i46745_3_, Set<Block> p_i46745_4_) {
      this.field_77864_a = 4.0F;
      this.field_77862_b = p_i46745_3_;
      this.field_150914_c = p_i46745_4_;
      this.field_77777_bU = 1;
      this.func_77656_e(p_i46745_3_.func_77997_a());
      this.field_77864_a = p_i46745_3_.func_77998_b();
      this.field_77865_bY = p_i46745_1_ + p_i46745_3_.func_78000_c();
      this.field_185065_c = p_i46745_2_;
      this.func_77637_a(CreativeTabs.field_78040_i);
   }

   protected ItemTool(Item.ToolMaterial p_i46746_1_, Set<Block> p_i46746_2_) {
      this(0.0F, 0.0F, p_i46746_1_, p_i46746_2_);
   }

   public float func_150893_a(ItemStack p_150893_1_, IBlockState p_150893_2_) {
      return this.field_150914_c.contains(p_150893_2_.func_177230_c()) ? this.field_77864_a : 1.0F;
   }

   public boolean func_77644_a(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
      p_77644_1_.func_77972_a(2, p_77644_3_);
      return true;
   }

   public boolean func_179218_a(ItemStack p_179218_1_, World p_179218_2_, IBlockState p_179218_3_, BlockPos p_179218_4_, EntityLivingBase p_179218_5_) {
      if (!p_179218_2_.field_72995_K && (double)p_179218_3_.func_185887_b(p_179218_2_, p_179218_4_) != 0.0D) {
         p_179218_1_.func_77972_a(1, p_179218_5_);
      }

      return true;
   }

   public boolean func_77662_d() {
      return true;
   }

   public int func_77619_b() {
      return this.field_77862_b.func_77995_e();
   }

   public String func_77861_e() {
      return this.field_77862_b.toString();
   }

   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return this.field_77862_b.func_150995_f() == p_82789_2_.func_77973_b() ? true : super.func_82789_a(p_82789_1_, p_82789_2_);
   }

   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot p_111205_1_) {
      Multimap<String, AttributeModifier> multimap = super.func_111205_h(p_111205_1_);
      if (p_111205_1_ == EntityEquipmentSlot.MAINHAND) {
         multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.field_77865_bY, 0));
         multimap.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Tool modifier", (double)this.field_185065_c, 0));
      }

      return multimap;
   }
}
