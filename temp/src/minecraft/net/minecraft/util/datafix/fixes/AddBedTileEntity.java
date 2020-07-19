package net.minecraft.util.datafix.fixes;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IFixableData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddBedTileEntity implements IFixableData {
   private static final Logger field_193842_a = LogManager.getLogger();

   public int func_188216_a() {
      return 1125;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      int i = 416;

      try {
         NBTTagCompound nbttagcompound = p_188217_1_.func_74775_l("Level");
         int j = nbttagcompound.func_74762_e("xPos");
         int k = nbttagcompound.func_74762_e("zPos");
         NBTTagList nbttaglist = nbttagcompound.func_150295_c("TileEntities", 10);
         NBTTagList nbttaglist1 = nbttagcompound.func_150295_c("Sections", 10);

         for(int l = 0; l < nbttaglist1.func_74745_c(); ++l) {
            NBTTagCompound nbttagcompound1 = nbttaglist1.func_150305_b(l);
            int i1 = nbttagcompound1.func_74771_c("Y");
            byte[] abyte = nbttagcompound1.func_74770_j("Blocks");

            for(int j1 = 0; j1 < abyte.length; ++j1) {
               if (416 == (abyte[j1] & 255) << 4) {
                  int k1 = j1 & 15;
                  int l1 = j1 >> 8 & 15;
                  int i2 = j1 >> 4 & 15;
                  NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                  nbttagcompound2.func_74778_a("id", "bed");
                  nbttagcompound2.func_74768_a("x", k1 + (j << 4));
                  nbttagcompound2.func_74768_a("y", l1 + (i1 << 4));
                  nbttagcompound2.func_74768_a("z", i2 + (k << 4));
                  nbttaglist.func_74742_a(nbttagcompound2);
               }
            }
         }
      } catch (Exception var17) {
         field_193842_a.warn("Unable to datafix Bed blocks, level format may be missing tags.");
      }

      return p_188217_1_;
   }
}
