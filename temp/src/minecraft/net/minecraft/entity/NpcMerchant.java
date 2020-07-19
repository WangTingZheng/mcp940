package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class NpcMerchant implements IMerchant {
   private final InventoryMerchant field_70937_a;
   private final EntityPlayer field_70935_b;
   private MerchantRecipeList field_70936_c;
   private final ITextComponent field_175548_d;

   public NpcMerchant(EntityPlayer p_i45817_1_, ITextComponent p_i45817_2_) {
      this.field_70935_b = p_i45817_1_;
      this.field_175548_d = p_i45817_2_;
      this.field_70937_a = new InventoryMerchant(p_i45817_1_, this);
   }

   @Nullable
   public EntityPlayer func_70931_l_() {
      return this.field_70935_b;
   }

   public void func_70932_a_(@Nullable EntityPlayer p_70932_1_) {
   }

   @Nullable
   public MerchantRecipeList func_70934_b(EntityPlayer p_70934_1_) {
      return this.field_70936_c;
   }

   public void func_70930_a(@Nullable MerchantRecipeList p_70930_1_) {
      this.field_70936_c = p_70930_1_;
   }

   public void func_70933_a(MerchantRecipe p_70933_1_) {
      p_70933_1_.func_77399_f();
   }

   public void func_110297_a_(ItemStack p_110297_1_) {
   }

   public ITextComponent func_145748_c_() {
      return (ITextComponent)(this.field_175548_d != null ? this.field_175548_d : new TextComponentTranslation("entity.Villager.name", new Object[0]));
   }

   public World func_190670_t_() {
      return this.field_70935_b.field_70170_p;
   }

   public BlockPos func_190671_u_() {
      return new BlockPos(this.field_70935_b);
   }
}
