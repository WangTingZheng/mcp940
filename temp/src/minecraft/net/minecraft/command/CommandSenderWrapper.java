package net.minecraft.command;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class CommandSenderWrapper implements ICommandSender {
   private final ICommandSender field_193043_a;
   @Nullable
   private final Vec3d field_194002_b;
   @Nullable
   private final BlockPos field_194003_c;
   @Nullable
   private final Integer field_194004_d;
   @Nullable
   private final Entity field_194005_e;
   @Nullable
   private final Boolean field_194006_f;

   public CommandSenderWrapper(ICommandSender p_i47599_1_, @Nullable Vec3d p_i47599_2_, @Nullable BlockPos p_i47599_3_, @Nullable Integer p_i47599_4_, @Nullable Entity p_i47599_5_, @Nullable Boolean p_i47599_6_) {
      this.field_193043_a = p_i47599_1_;
      this.field_194002_b = p_i47599_2_;
      this.field_194003_c = p_i47599_3_;
      this.field_194004_d = p_i47599_4_;
      this.field_194005_e = p_i47599_5_;
      this.field_194006_f = p_i47599_6_;
   }

   public static CommandSenderWrapper func_193998_a(ICommandSender p_193998_0_) {
      return p_193998_0_ instanceof CommandSenderWrapper ? (CommandSenderWrapper)p_193998_0_ : new CommandSenderWrapper(p_193998_0_, (Vec3d)null, (BlockPos)null, (Integer)null, (Entity)null, (Boolean)null);
   }

   public CommandSenderWrapper func_193997_a(Entity p_193997_1_, Vec3d p_193997_2_) {
      return this.field_194005_e == p_193997_1_ && Objects.equals(this.field_194002_b, p_193997_2_) ? this : new CommandSenderWrapper(this.field_193043_a, p_193997_2_, new BlockPos(p_193997_2_), this.field_194004_d, p_193997_1_, this.field_194006_f);
   }

   public CommandSenderWrapper func_193999_a(int p_193999_1_) {
      return this.field_194004_d != null && this.field_194004_d.intValue() <= p_193999_1_ ? this : new CommandSenderWrapper(this.field_193043_a, this.field_194002_b, this.field_194003_c, p_193999_1_, this.field_194005_e, this.field_194006_f);
   }

   public CommandSenderWrapper func_194001_a(boolean p_194001_1_) {
      return this.field_194006_f == null || this.field_194006_f.booleanValue() && !p_194001_1_ ? new CommandSenderWrapper(this.field_193043_a, this.field_194002_b, this.field_194003_c, this.field_194004_d, this.field_194005_e, p_194001_1_) : this;
   }

   public CommandSenderWrapper func_194000_i() {
      return this.field_194002_b != null ? this : new CommandSenderWrapper(this.field_193043_a, this.func_174791_d(), this.func_180425_c(), this.field_194004_d, this.field_194005_e, this.field_194006_f);
   }

   public String func_70005_c_() {
      return this.field_194005_e != null ? this.field_194005_e.func_70005_c_() : this.field_193043_a.func_70005_c_();
   }

   public ITextComponent func_145748_c_() {
      return this.field_194005_e != null ? this.field_194005_e.func_145748_c_() : this.field_193043_a.func_145748_c_();
   }

   public void func_145747_a(ITextComponent p_145747_1_) {
      if (this.field_194006_f == null || this.field_194006_f.booleanValue()) {
         this.field_193043_a.func_145747_a(p_145747_1_);
      }
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return this.field_194004_d != null && this.field_194004_d.intValue() < p_70003_1_ ? false : this.field_193043_a.func_70003_b(p_70003_1_, p_70003_2_);
   }

   public BlockPos func_180425_c() {
      if (this.field_194003_c != null) {
         return this.field_194003_c;
      } else {
         return this.field_194005_e != null ? this.field_194005_e.func_180425_c() : this.field_193043_a.func_180425_c();
      }
   }

   public Vec3d func_174791_d() {
      if (this.field_194002_b != null) {
         return this.field_194002_b;
      } else {
         return this.field_194005_e != null ? this.field_194005_e.func_174791_d() : this.field_193043_a.func_174791_d();
      }
   }

   public World func_130014_f_() {
      return this.field_194005_e != null ? this.field_194005_e.func_130014_f_() : this.field_193043_a.func_130014_f_();
   }

   @Nullable
   public Entity func_174793_f() {
      return this.field_194005_e != null ? this.field_194005_e.func_174793_f() : this.field_193043_a.func_174793_f();
   }

   public boolean func_174792_t_() {
      return this.field_194006_f != null ? this.field_194006_f.booleanValue() : this.field_193043_a.func_174792_t_();
   }

   public void func_174794_a(CommandResultStats.Type p_174794_1_, int p_174794_2_) {
      if (this.field_194005_e != null) {
         this.field_194005_e.func_174794_a(p_174794_1_, p_174794_2_);
      } else {
         this.field_193043_a.func_174794_a(p_174794_1_, p_174794_2_);
      }
   }

   @Nullable
   public MinecraftServer func_184102_h() {
      return this.field_193043_a.func_184102_h();
   }
}
