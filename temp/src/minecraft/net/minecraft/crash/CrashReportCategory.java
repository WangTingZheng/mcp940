package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class CrashReportCategory {
   private final CrashReport field_85078_a;
   private final String field_85076_b;
   private final List<CrashReportCategory.Entry> field_85077_c = Lists.<CrashReportCategory.Entry>newArrayList();
   private StackTraceElement[] field_85075_d = new StackTraceElement[0];

   public CrashReportCategory(CrashReport p_i1353_1_, String p_i1353_2_) {
      this.field_85078_a = p_i1353_1_;
      this.field_85076_b = p_i1353_2_;
   }

   public static String func_85074_a(double p_85074_0_, double p_85074_2_, double p_85074_4_) {
      return String.format("%.2f,%.2f,%.2f - %s", p_85074_0_, p_85074_2_, p_85074_4_, func_180522_a(new BlockPos(p_85074_0_, p_85074_2_, p_85074_4_)));
   }

   public static String func_180522_a(BlockPos p_180522_0_) {
      return func_184876_a(p_180522_0_.func_177958_n(), p_180522_0_.func_177956_o(), p_180522_0_.func_177952_p());
   }

   public static String func_184876_a(int p_184876_0_, int p_184876_1_, int p_184876_2_) {
      StringBuilder stringbuilder = new StringBuilder();

      try {
         stringbuilder.append(String.format("World: (%d,%d,%d)", p_184876_0_, p_184876_1_, p_184876_2_));
      } catch (Throwable var16) {
         stringbuilder.append("(Error finding world loc)");
      }

      stringbuilder.append(", ");

      try {
         int i = p_184876_0_ >> 4;
         int j = p_184876_2_ >> 4;
         int k = p_184876_0_ & 15;
         int l = p_184876_1_ >> 4;
         int i1 = p_184876_2_ & 15;
         int j1 = i << 4;
         int k1 = j << 4;
         int l1 = (i + 1 << 4) - 1;
         int i2 = (j + 1 << 4) - 1;
         stringbuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", k, l, i1, i, j, j1, k1, l1, i2));
      } catch (Throwable var15) {
         stringbuilder.append("(Error finding chunk loc)");
      }

      stringbuilder.append(", ");

      try {
         int k2 = p_184876_0_ >> 9;
         int l2 = p_184876_2_ >> 9;
         int i3 = k2 << 5;
         int j3 = l2 << 5;
         int k3 = (k2 + 1 << 5) - 1;
         int l3 = (l2 + 1 << 5) - 1;
         int i4 = k2 << 9;
         int j4 = l2 << 9;
         int k4 = (k2 + 1 << 9) - 1;
         int j2 = (l2 + 1 << 9) - 1;
         stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", k2, l2, i3, j3, k3, l3, i4, j4, k4, j2));
      } catch (Throwable var14) {
         stringbuilder.append("(Error finding world loc)");
      }

      return stringbuilder.toString();
   }

   public void func_189529_a(String p_189529_1_, ICrashReportDetail<String> p_189529_2_) {
      try {
         this.func_71507_a(p_189529_1_, p_189529_2_.call());
      } catch (Throwable throwable) {
         this.func_71499_a(p_189529_1_, throwable);
      }

   }

   public void func_71507_a(String p_71507_1_, Object p_71507_2_) {
      this.field_85077_c.add(new CrashReportCategory.Entry(p_71507_1_, p_71507_2_));
   }

   public void func_71499_a(String p_71499_1_, Throwable p_71499_2_) {
      this.func_71507_a(p_71499_1_, p_71499_2_);
   }

   public int func_85073_a(int p_85073_1_) {
      StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
      if (astacktraceelement.length <= 0) {
         return 0;
      } else {
         this.field_85075_d = new StackTraceElement[astacktraceelement.length - 3 - p_85073_1_];
         System.arraycopy(astacktraceelement, 3 + p_85073_1_, this.field_85075_d, 0, this.field_85075_d.length);
         return this.field_85075_d.length;
      }
   }

   public boolean func_85069_a(StackTraceElement p_85069_1_, StackTraceElement p_85069_2_) {
      if (this.field_85075_d.length != 0 && p_85069_1_ != null) {
         StackTraceElement stacktraceelement = this.field_85075_d[0];
         if (stacktraceelement.isNativeMethod() == p_85069_1_.isNativeMethod() && stacktraceelement.getClassName().equals(p_85069_1_.getClassName()) && stacktraceelement.getFileName().equals(p_85069_1_.getFileName()) && stacktraceelement.getMethodName().equals(p_85069_1_.getMethodName())) {
            if (p_85069_2_ != null != this.field_85075_d.length > 1) {
               return false;
            } else if (p_85069_2_ != null && !this.field_85075_d[1].equals(p_85069_2_)) {
               return false;
            } else {
               this.field_85075_d[0] = p_85069_1_;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void func_85070_b(int p_85070_1_) {
      StackTraceElement[] astacktraceelement = new StackTraceElement[this.field_85075_d.length - p_85070_1_];
      System.arraycopy(this.field_85075_d, 0, astacktraceelement, 0, astacktraceelement.length);
      this.field_85075_d = astacktraceelement;
   }

   public void func_85072_a(StringBuilder p_85072_1_) {
      p_85072_1_.append("-- ").append(this.field_85076_b).append(" --\n");
      p_85072_1_.append("Details:");

      for(CrashReportCategory.Entry crashreportcategory$entry : this.field_85077_c) {
         p_85072_1_.append("\n\t");
         p_85072_1_.append(crashreportcategory$entry.func_85089_a());
         p_85072_1_.append(": ");
         p_85072_1_.append(crashreportcategory$entry.func_85090_b());
      }

      if (this.field_85075_d != null && this.field_85075_d.length > 0) {
         p_85072_1_.append("\nStacktrace:");

         for(StackTraceElement stacktraceelement : this.field_85075_d) {
            p_85072_1_.append("\n\tat ");
            p_85072_1_.append((Object)stacktraceelement);
         }
      }

   }

   public StackTraceElement[] func_147152_a() {
      return this.field_85075_d;
   }

   public static void func_180523_a(CrashReportCategory p_180523_0_, final BlockPos p_180523_1_, final Block p_180523_2_, final int p_180523_3_) {
      final int i = Block.func_149682_b(p_180523_2_);
      p_180523_0_.func_189529_a("Block type", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            try {
               return String.format("ID #%d (%s // %s)", i, p_180523_2_.func_149739_a(), p_180523_2_.getClass().getCanonicalName());
            } catch (Throwable var2) {
               return "ID #" + i;
            }
         }
      });
      p_180523_0_.func_189529_a("Block data value", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            if (p_180523_3_ < 0) {
               return "Unknown? (Got " + p_180523_3_ + ")";
            } else {
               String s = String.format("%4s", Integer.toBinaryString(p_180523_3_)).replace(" ", "0");
               return String.format("%1$d / 0x%1$X / 0b%2$s", p_180523_3_, s);
            }
         }
      });
      p_180523_0_.func_189529_a("Block location", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return CrashReportCategory.func_180522_a(p_180523_1_);
         }
      });
   }

   public static void func_175750_a(CrashReportCategory p_175750_0_, final BlockPos p_175750_1_, final IBlockState p_175750_2_) {
      p_175750_0_.func_189529_a("Block", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return p_175750_2_.toString();
         }
      });
      p_175750_0_.func_189529_a("Block location", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return CrashReportCategory.func_180522_a(p_175750_1_);
         }
      });
   }

   static class Entry {
      private final String field_85092_a;
      private final String field_85091_b;

      public Entry(String p_i1352_1_, Object p_i1352_2_) {
         this.field_85092_a = p_i1352_1_;
         if (p_i1352_2_ == null) {
            this.field_85091_b = "~~NULL~~";
         } else if (p_i1352_2_ instanceof Throwable) {
            Throwable throwable = (Throwable)p_i1352_2_;
            this.field_85091_b = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
         } else {
            this.field_85091_b = p_i1352_2_.toString();
         }

      }

      public String func_85089_a() {
         return this.field_85092_a;
      }

      public String func_85090_b() {
         return this.field_85091_b;
      }
   }
}
