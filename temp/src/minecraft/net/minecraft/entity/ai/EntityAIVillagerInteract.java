package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {
   private int field_179478_e;
   private final EntityVillager field_179477_f;

   public EntityAIVillagerInteract(EntityVillager p_i45886_1_) {
      super(p_i45886_1_, EntityVillager.class, 3.0F, 0.02F);
      this.field_179477_f = p_i45886_1_;
   }

   public void func_75249_e() {
      super.func_75249_e();
      if (this.field_179477_f.func_175555_cq() && this.field_75334_a instanceof EntityVillager && ((EntityVillager)this.field_75334_a).func_175557_cr()) {
         this.field_179478_e = 10;
      } else {
         this.field_179478_e = 0;
      }

   }

   public void func_75246_d() {
      super.func_75246_d();
      if (this.field_179478_e > 0) {
         --this.field_179478_e;
         if (this.field_179478_e == 0) {
            InventoryBasic inventorybasic = this.field_179477_f.func_175551_co();

            for(int i = 0; i < inventorybasic.func_70302_i_(); ++i) {
               ItemStack itemstack = inventorybasic.func_70301_a(i);
               ItemStack itemstack1 = ItemStack.field_190927_a;
               if (!itemstack.func_190926_b()) {
                  Item item = itemstack.func_77973_b();
                  if ((item == Items.field_151025_P || item == Items.field_151174_bG || item == Items.field_151172_bF || item == Items.field_185164_cV) && itemstack.func_190916_E() > 3) {
                     int l = itemstack.func_190916_E() / 2;
                     itemstack.func_190918_g(l);
                     itemstack1 = new ItemStack(item, l, itemstack.func_77960_j());
                  } else if (item == Items.field_151015_O && itemstack.func_190916_E() > 5) {
                     int j = itemstack.func_190916_E() / 2 / 3 * 3;
                     int k = j / 3;
                     itemstack.func_190918_g(j);
                     itemstack1 = new ItemStack(Items.field_151025_P, k, 0);
                  }

                  if (itemstack.func_190926_b()) {
                     inventorybasic.func_70299_a(i, ItemStack.field_190927_a);
                  }
               }

               if (!itemstack1.func_190926_b()) {
                  double d0 = this.field_179477_f.field_70163_u - 0.30000001192092896D + (double)this.field_179477_f.func_70047_e();
                  EntityItem entityitem = new EntityItem(this.field_179477_f.field_70170_p, this.field_179477_f.field_70165_t, d0, this.field_179477_f.field_70161_v, itemstack1);
                  float f = 0.3F;
                  float f1 = this.field_179477_f.field_70759_as;
                  float f2 = this.field_179477_f.field_70125_A;
                  entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(f1 * 0.017453292F) * MathHelper.func_76134_b(f2 * 0.017453292F) * 0.3F);
                  entityitem.field_70179_y = (double)(MathHelper.func_76134_b(f1 * 0.017453292F) * MathHelper.func_76134_b(f2 * 0.017453292F) * 0.3F);
                  entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(f2 * 0.017453292F) * 0.3F + 0.1F);
                  entityitem.func_174869_p();
                  this.field_179477_f.field_70170_p.func_72838_d(entityitem);
                  break;
               }
            }
         }
      }

   }
}
