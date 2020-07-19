package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.datafix.IFixableData;

public class PaintingDirection implements IFixableData {
   public int func_188216_a() {
      return 111;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      String s = p_188217_1_.func_74779_i("id");
      boolean flag = "Painting".equals(s);
      boolean flag1 = "ItemFrame".equals(s);
      if ((flag || flag1) && !p_188217_1_.func_150297_b("Facing", 99)) {
         EnumFacing enumfacing;
         if (p_188217_1_.func_150297_b("Direction", 99)) {
            enumfacing = EnumFacing.func_176731_b(p_188217_1_.func_74771_c("Direction"));
            p_188217_1_.func_74768_a("TileX", p_188217_1_.func_74762_e("TileX") + enumfacing.func_82601_c());
            p_188217_1_.func_74768_a("TileY", p_188217_1_.func_74762_e("TileY") + enumfacing.func_96559_d());
            p_188217_1_.func_74768_a("TileZ", p_188217_1_.func_74762_e("TileZ") + enumfacing.func_82599_e());
            p_188217_1_.func_82580_o("Direction");
            if (flag1 && p_188217_1_.func_150297_b("ItemRotation", 99)) {
               p_188217_1_.func_74774_a("ItemRotation", (byte)(p_188217_1_.func_74771_c("ItemRotation") * 2));
            }
         } else {
            enumfacing = EnumFacing.func_176731_b(p_188217_1_.func_74771_c("Dir"));
            p_188217_1_.func_82580_o("Dir");
         }

         p_188217_1_.func_74774_a("Facing", (byte)enumfacing.func_176736_b());
      }

      return p_188217_1_;
   }
}
