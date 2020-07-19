package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiListWorldSelection extends GuiListExtended {
   private static final Logger field_186797_u = LogManager.getLogger();
   private final GuiWorldSelection field_186798_v;
   private final List<GuiListWorldSelectionEntry> field_186799_w = Lists.<GuiListWorldSelectionEntry>newArrayList();
   private int field_186800_x = -1;

   public GuiListWorldSelection(GuiWorldSelection p_i46590_1_, Minecraft p_i46590_2_, int p_i46590_3_, int p_i46590_4_, int p_i46590_5_, int p_i46590_6_, int p_i46590_7_) {
      super(p_i46590_2_, p_i46590_3_, p_i46590_4_, p_i46590_5_, p_i46590_6_, p_i46590_7_);
      this.field_186798_v = p_i46590_1_;
      this.func_186795_e();
   }

   public void func_186795_e() {
      ISaveFormat isaveformat = this.field_148161_k.func_71359_d();

      List<WorldSummary> list;
      try {
         list = isaveformat.func_75799_b();
      } catch (AnvilConverterException anvilconverterexception) {
         field_186797_u.error("Couldn't load level list", (Throwable)anvilconverterexception);
         this.field_148161_k.func_147108_a(new GuiErrorScreen(I18n.func_135052_a("selectWorld.unable_to_load"), anvilconverterexception.getMessage()));
         return;
      }

      Collections.sort(list);

      for(WorldSummary worldsummary : list) {
         this.field_186799_w.add(new GuiListWorldSelectionEntry(this, worldsummary, this.field_148161_k.func_71359_d()));
      }

   }

   public GuiListWorldSelectionEntry func_148180_b(int p_148180_1_) {
      return this.field_186799_w.get(p_148180_1_);
   }

   protected int func_148127_b() {
      return this.field_186799_w.size();
   }

   protected int func_148137_d() {
      return super.func_148137_d() + 20;
   }

   public int func_148139_c() {
      return super.func_148139_c() + 50;
   }

   public void func_186792_d(int p_186792_1_) {
      this.field_186800_x = p_186792_1_;
      this.field_186798_v.func_184863_a(this.func_186794_f());
   }

   protected boolean func_148131_a(int p_148131_1_) {
      return p_148131_1_ == this.field_186800_x;
   }

   @Nullable
   public GuiListWorldSelectionEntry func_186794_f() {
      return this.field_186800_x >= 0 && this.field_186800_x < this.func_148127_b() ? this.func_148180_b(this.field_186800_x) : null;
   }

   public GuiWorldSelection func_186796_g() {
      return this.field_186798_v;
   }
}
