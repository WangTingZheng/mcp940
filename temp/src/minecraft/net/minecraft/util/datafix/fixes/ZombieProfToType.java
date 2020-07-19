package net.minecraft.util.datafix.fixes;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ZombieProfToType implements IFixableData {
   private static final Random field_190049_a = new Random();

   public int func_188216_a() {
      return 502;
   }

   public NBTTagCompound func_188217_a(NBTTagCompound p_188217_1_) {
      if ("Zombie".equals(p_188217_1_.func_74779_i("id")) && p_188217_1_.func_74767_n("IsVillager")) {
         if (!p_188217_1_.func_150297_b("ZombieType", 99)) {
            int i = -1;
            if (p_188217_1_.func_150297_b("VillagerProfession", 99)) {
               try {
                  i = this.func_191277_a(p_188217_1_.func_74762_e("VillagerProfession"));
               } catch (RuntimeException var4) {
                  ;
               }
            }

            if (i == -1) {
               i = this.func_191277_a(field_190049_a.nextInt(6));
            }

            p_188217_1_.func_74768_a("ZombieType", i);
         }

         p_188217_1_.func_82580_o("IsVillager");
      }

      return p_188217_1_;
   }

   private int func_191277_a(int p_191277_1_) {
      return p_191277_1_ >= 0 && p_191277_1_ < 6 ? p_191277_1_ : -1;
   }
}
