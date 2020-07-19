package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class CommandBlockBaseLogic implements ICommandSender {
   private static final SimpleDateFormat field_145766_a = new SimpleDateFormat("HH:mm:ss");
   private long field_193041_b = -1L;
   private boolean field_193042_c = true;
   private int field_145764_b;
   private boolean field_145765_c = true;
   private ITextComponent field_145762_d;
   private String field_145763_e = "";
   private String field_145761_f = "@";
   private final CommandResultStats field_175575_g = new CommandResultStats();

   public int func_145760_g() {
      return this.field_145764_b;
   }

   public void func_184167_a(int p_184167_1_) {
      this.field_145764_b = p_184167_1_;
   }

   public ITextComponent func_145749_h() {
      return (ITextComponent)(this.field_145762_d == null ? new TextComponentString("") : this.field_145762_d);
   }

   public NBTTagCompound func_189510_a(NBTTagCompound p_189510_1_) {
      p_189510_1_.func_74778_a("Command", this.field_145763_e);
      p_189510_1_.func_74768_a("SuccessCount", this.field_145764_b);
      p_189510_1_.func_74778_a("CustomName", this.field_145761_f);
      p_189510_1_.func_74757_a("TrackOutput", this.field_145765_c);
      if (this.field_145762_d != null && this.field_145765_c) {
         p_189510_1_.func_74778_a("LastOutput", ITextComponent.Serializer.func_150696_a(this.field_145762_d));
      }

      p_189510_1_.func_74757_a("UpdateLastExecution", this.field_193042_c);
      if (this.field_193042_c && this.field_193041_b > 0L) {
         p_189510_1_.func_74772_a("LastExecution", this.field_193041_b);
      }

      this.field_175575_g.func_179670_b(p_189510_1_);
      return p_189510_1_;
   }

   public void func_145759_b(NBTTagCompound p_145759_1_) {
      this.field_145763_e = p_145759_1_.func_74779_i("Command");
      this.field_145764_b = p_145759_1_.func_74762_e("SuccessCount");
      if (p_145759_1_.func_150297_b("CustomName", 8)) {
         this.field_145761_f = p_145759_1_.func_74779_i("CustomName");
      }

      if (p_145759_1_.func_150297_b("TrackOutput", 1)) {
         this.field_145765_c = p_145759_1_.func_74767_n("TrackOutput");
      }

      if (p_145759_1_.func_150297_b("LastOutput", 8) && this.field_145765_c) {
         try {
            this.field_145762_d = ITextComponent.Serializer.func_150699_a(p_145759_1_.func_74779_i("LastOutput"));
         } catch (Throwable throwable) {
            this.field_145762_d = new TextComponentString(throwable.getMessage());
         }
      } else {
         this.field_145762_d = null;
      }

      if (p_145759_1_.func_74764_b("UpdateLastExecution")) {
         this.field_193042_c = p_145759_1_.func_74767_n("UpdateLastExecution");
      }

      if (this.field_193042_c && p_145759_1_.func_74764_b("LastExecution")) {
         this.field_193041_b = p_145759_1_.func_74763_f("LastExecution");
      } else {
         this.field_193041_b = -1L;
      }

      this.field_175575_g.func_179668_a(p_145759_1_);
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return p_70003_1_ <= 2;
   }

   public void func_145752_a(String p_145752_1_) {
      this.field_145763_e = p_145752_1_;
      this.field_145764_b = 0;
   }

   public String func_145753_i() {
      return this.field_145763_e;
   }

   public boolean func_145755_a(World p_145755_1_) {
      if (!p_145755_1_.field_72995_K && p_145755_1_.func_82737_E() != this.field_193041_b) {
         if ("Searge".equalsIgnoreCase(this.field_145763_e)) {
            this.field_145762_d = new TextComponentString("#itzlipofutzli");
            this.field_145764_b = 1;
            return true;
         } else {
            MinecraftServer minecraftserver = this.func_184102_h();
            if (minecraftserver != null && minecraftserver.func_175578_N() && minecraftserver.func_82356_Z()) {
               try {
                  this.field_145762_d = null;
                  this.field_145764_b = minecraftserver.func_71187_D().func_71556_a(this, this.field_145763_e);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.func_85055_a(throwable, "Executing command block");
                  CrashReportCategory crashreportcategory = crashreport.func_85058_a("Command to be executed");
                  crashreportcategory.func_189529_a("Command", new ICrashReportDetail<String>() {
                     public String call() throws Exception {
                        return CommandBlockBaseLogic.this.func_145753_i();
                     }
                  });
                  crashreportcategory.func_189529_a("Name", new ICrashReportDetail<String>() {
                     public String call() throws Exception {
                        return CommandBlockBaseLogic.this.func_70005_c_();
                     }
                  });
                  throw new ReportedException(crashreport);
               }
            } else {
               this.field_145764_b = 0;
            }

            if (this.field_193042_c) {
               this.field_193041_b = p_145755_1_.func_82737_E();
            } else {
               this.field_193041_b = -1L;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String func_70005_c_() {
      return this.field_145761_f;
   }

   public void func_145754_b(String p_145754_1_) {
      this.field_145761_f = p_145754_1_;
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
      if (this.field_145765_c && this.func_130014_f_() != null && !this.func_130014_f_().field_72995_K) {
         this.field_145762_d = (new TextComponentString("[" + field_145766_a.format(new Date()) + "] ")).func_150257_a(p_145747_1_);
         this.func_145756_e();
      }

   }

   public boolean func_174792_t_() {
      MinecraftServer minecraftserver = this.func_184102_h();
      return minecraftserver == null || !minecraftserver.func_175578_N() || minecraftserver.field_71305_c[0].func_82736_K().func_82766_b("commandBlockOutput");
   }

   public void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_) {
      this.field_175575_g.func_184932_a(this.func_184102_h(), this, p_174794_1_, p_174794_2_);
   }

   public abstract void func_145756_e();

   public abstract int func_145751_f();

   public abstract void func_145757_a(ByteBuf var1);

   public void func_145750_b(@Nullable ITextComponent p_145750_1_) {
      this.field_145762_d = p_145750_1_;
   }

   public void func_175573_a(boolean p_175573_1_) {
      this.field_145765_c = p_175573_1_;
   }

   public boolean func_175571_m() {
      return this.field_145765_c;
   }

   public boolean func_175574_a(EntityPlayer p_175574_1_) {
      if (!p_175574_1_.func_189808_dh()) {
         return false;
      } else {
         if (p_175574_1_.func_130014_f_().field_72995_K) {
            p_175574_1_.func_184809_a(this);
         }

         return true;
      }
   }

   public CommandResultStats func_175572_n() {
      return this.field_175575_g;
   }
}
