package net.minecraft.world.storage;

import java.io.File;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   String func_154333_a();

   ISaveHandler func_75804_a(String var1, boolean var2);

   List<WorldSummary> func_75799_b() throws AnvilConverterException;

   void func_75800_d();

   @Nullable
   WorldInfo func_75803_c(String var1);

   boolean func_154335_d(String var1);

   boolean func_75802_e(String var1);

   void func_75806_a(String var1, String var2);

   boolean func_154334_a(String var1);

   boolean func_75801_b(String var1);

   boolean func_75805_a(String var1, IProgressUpdate var2);

   boolean func_90033_f(String var1);

   File func_186352_b(String var1, String var2);
}
