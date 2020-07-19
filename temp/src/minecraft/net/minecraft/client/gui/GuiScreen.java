package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private static final Logger field_175287_a = LogManager.getLogger();
   private static final Set<String> field_175284_f = Sets.newHashSet("http", "https");
   private static final Splitter field_175285_g = Splitter.on('\n');
   protected Minecraft field_146297_k;
   protected RenderItem field_146296_j;
   public int field_146294_l;
   public int field_146295_m;
   protected List<GuiButton> field_146292_n = Lists.<GuiButton>newArrayList();
   protected List<GuiLabel> field_146293_o = Lists.<GuiLabel>newArrayList();
   public boolean field_146291_p;
   protected FontRenderer field_146289_q;
   protected GuiButton field_146290_a;
   private int field_146287_f;
   private long field_146288_g;
   private int field_146298_h;
   private URI field_175286_t;
   private boolean field_193977_u;

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      for(int i = 0; i < this.field_146292_n.size(); ++i) {
         ((GuiButton)this.field_146292_n.get(i)).func_191745_a(this.field_146297_k, p_73863_1_, p_73863_2_, p_73863_3_);
      }

      for(int j = 0; j < this.field_146293_o.size(); ++j) {
         ((GuiLabel)this.field_146293_o.get(j)).func_146159_a(this.field_146297_k, p_73863_1_, p_73863_2_);
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if (p_73869_2_ == 1) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
         if (this.field_146297_k.field_71462_r == null) {
            this.field_146297_k.func_71381_h();
         }
      }

   }

   protected <T extends GuiButton> T func_189646_b(T p_189646_1_) {
      this.field_146292_n.add(p_189646_1_);
      return p_189646_1_;
   }

   public static String func_146277_j() {
      try {
         Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
         if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)transferable.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
         ;
      }

      return "";
   }

   public static void func_146275_d(String p_146275_0_) {
      if (!StringUtils.isEmpty(p_146275_0_)) {
         try {
            StringSelection stringselection = new StringSelection(p_146275_0_);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner)null);
         } catch (Exception var2) {
            ;
         }

      }
   }

   protected void func_146285_a(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_) {
      this.func_146283_a(this.func_191927_a(p_146285_1_), p_146285_2_, p_146285_3_);
   }

   public List<String> func_191927_a(ItemStack p_191927_1_) {
      List<String> list = p_191927_1_.func_82840_a(this.field_146297_k.field_71439_g, this.field_146297_k.field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

      for(int i = 0; i < list.size(); ++i) {
         if (i == 0) {
            list.set(i, p_191927_1_.func_77953_t().field_77937_e + (String)list.get(i));
         } else {
            list.set(i, TextFormatting.GRAY + (String)list.get(i));
         }
      }

      return list;
   }

   public void func_146279_a(String p_146279_1_, int p_146279_2_, int p_146279_3_) {
      this.func_146283_a(Arrays.asList(p_146279_1_), p_146279_2_, p_146279_3_);
   }

   public void func_193975_a(boolean p_193975_1_) {
      this.field_193977_u = p_193975_1_;
   }

   public boolean func_193976_p() {
      return this.field_193977_u;
   }

   public void func_146283_a(List<String> p_146283_1_, int p_146283_2_, int p_146283_3_) {
      if (!p_146283_1_.isEmpty()) {
         GlStateManager.func_179101_C();
         RenderHelper.func_74518_a();
         GlStateManager.func_179140_f();
         GlStateManager.func_179097_i();
         int i = 0;

         for(String s : p_146283_1_) {
            int j = this.field_146289_q.func_78256_a(s);
            if (j > i) {
               i = j;
            }
         }

         int l1 = p_146283_2_ + 12;
         int i2 = p_146283_3_ - 12;
         int k = 8;
         if (p_146283_1_.size() > 1) {
            k += 2 + (p_146283_1_.size() - 1) * 10;
         }

         if (l1 + i > this.field_146294_l) {
            l1 -= 28 + i;
         }

         if (i2 + k + 6 > this.field_146295_m) {
            i2 = this.field_146295_m - k - 6;
         }

         this.field_73735_i = 300.0F;
         this.field_146296_j.field_77023_b = 300.0F;
         int l = -267386864;
         this.func_73733_a(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
         this.func_73733_a(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
         this.func_73733_a(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
         this.func_73733_a(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
         this.func_73733_a(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
         int i1 = 1347420415;
         int j1 = 1344798847;
         this.func_73733_a(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
         this.func_73733_a(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

         for(int k1 = 0; k1 < p_146283_1_.size(); ++k1) {
            String s1 = p_146283_1_.get(k1);
            this.field_146289_q.func_175063_a(s1, (float)l1, (float)i2, -1);
            if (k1 == 0) {
               i2 += 2;
            }

            i2 += 10;
         }

         this.field_73735_i = 0.0F;
         this.field_146296_j.field_77023_b = 0.0F;
         GlStateManager.func_179145_e();
         GlStateManager.func_179126_j();
         RenderHelper.func_74519_b();
         GlStateManager.func_179091_B();
      }
   }

   protected void func_175272_a(ITextComponent p_175272_1_, int p_175272_2_, int p_175272_3_) {
      if (p_175272_1_ != null && p_175272_1_.func_150256_b().func_150210_i() != null) {
         HoverEvent hoverevent = p_175272_1_.func_150256_b().func_150210_i();
         if (hoverevent.func_150701_a() == HoverEvent.Action.SHOW_ITEM) {
            ItemStack itemstack = ItemStack.field_190927_a;

            try {
               NBTBase nbtbase = JsonToNBT.func_180713_a(hoverevent.func_150702_b().func_150260_c());
               if (nbtbase instanceof NBTTagCompound) {
                  itemstack = new ItemStack((NBTTagCompound)nbtbase);
               }
            } catch (NBTException var9) {
               ;
            }

            if (itemstack.func_190926_b()) {
               this.func_146279_a(TextFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
            } else {
               this.func_146285_a(itemstack, p_175272_2_, p_175272_3_);
            }
         } else if (hoverevent.func_150701_a() == HoverEvent.Action.SHOW_ENTITY) {
            if (this.field_146297_k.field_71474_y.field_82882_x) {
               try {
                  NBTTagCompound nbttagcompound = JsonToNBT.func_180713_a(hoverevent.func_150702_b().func_150260_c());
                  List<String> list = Lists.<String>newArrayList();
                  list.add(nbttagcompound.func_74779_i("name"));
                  if (nbttagcompound.func_150297_b("type", 8)) {
                     String s = nbttagcompound.func_74779_i("type");
                     list.add("Type: " + s);
                  }

                  list.add(nbttagcompound.func_74779_i("id"));
                  this.func_146283_a(list, p_175272_2_, p_175272_3_);
               } catch (NBTException var8) {
                  this.func_146279_a(TextFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
               }
            }
         } else if (hoverevent.func_150701_a() == HoverEvent.Action.SHOW_TEXT) {
            this.func_146283_a(this.field_146297_k.field_71466_p.func_78271_c(hoverevent.func_150702_b().func_150254_d(), Math.max(this.field_146294_l / 2, 200)), p_175272_2_, p_175272_3_);
         }

         GlStateManager.func_179140_f();
      }
   }

   protected void func_175274_a(String p_175274_1_, boolean p_175274_2_) {
   }

   public boolean func_175276_a(ITextComponent p_175276_1_) {
      if (p_175276_1_ == null) {
         return false;
      } else {
         ClickEvent clickevent = p_175276_1_.func_150256_b().func_150235_h();
         if (func_146272_n()) {
            if (p_175276_1_.func_150256_b().func_179986_j() != null) {
               this.func_175274_a(p_175276_1_.func_150256_b().func_179986_j(), false);
            }
         } else if (clickevent != null) {
            if (clickevent.func_150669_a() == ClickEvent.Action.OPEN_URL) {
               if (!this.field_146297_k.field_71474_y.field_74359_p) {
                  return false;
               }

               try {
                  URI uri = new URI(clickevent.func_150668_b());
                  String s = uri.getScheme();
                  if (s == null) {
                     throw new URISyntaxException(clickevent.func_150668_b(), "Missing protocol");
                  }

                  if (!field_175284_f.contains(s.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(clickevent.func_150668_b(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                  }

                  if (this.field_146297_k.field_71474_y.field_74358_q) {
                     this.field_175286_t = uri;
                     this.field_146297_k.func_147108_a(new GuiConfirmOpenLink(this, clickevent.func_150668_b(), 31102009, false));
                  } else {
                     this.func_175282_a(uri);
                  }
               } catch (URISyntaxException urisyntaxexception) {
                  field_175287_a.error("Can't open url for {}", clickevent, urisyntaxexception);
               }
            } else if (clickevent.func_150669_a() == ClickEvent.Action.OPEN_FILE) {
               URI uri1 = (new File(clickevent.func_150668_b())).toURI();
               this.func_175282_a(uri1);
            } else if (clickevent.func_150669_a() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.func_175274_a(clickevent.func_150668_b(), true);
            } else if (clickevent.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
               this.func_175281_b(clickevent.func_150668_b(), false);
            } else {
               field_175287_a.error("Don't know how to handle {}", (Object)clickevent);
            }

            return true;
         }

         return false;
      }
   }

   public void func_175275_f(String p_175275_1_) {
      this.func_175281_b(p_175275_1_, true);
   }

   public void func_175281_b(String p_175281_1_, boolean p_175281_2_) {
      if (p_175281_2_) {
         this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(p_175281_1_);
      }

      this.field_146297_k.field_71439_g.func_71165_d(p_175281_1_);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      if (p_73864_3_ == 0) {
         for(int i = 0; i < this.field_146292_n.size(); ++i) {
            GuiButton guibutton = this.field_146292_n.get(i);
            if (guibutton.func_146116_c(this.field_146297_k, p_73864_1_, p_73864_2_)) {
               this.field_146290_a = guibutton;
               guibutton.func_146113_a(this.field_146297_k.func_147118_V());
               this.func_146284_a(guibutton);
            }
         }
      }

   }

   protected void func_146286_b(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
      if (this.field_146290_a != null && p_146286_3_ == 0) {
         this.field_146290_a.func_146118_a(p_146286_1_, p_146286_2_);
         this.field_146290_a = null;
      }

   }

   protected void func_146273_a(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
   }

   public void func_146280_a(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
      this.field_146297_k = p_146280_1_;
      this.field_146296_j = p_146280_1_.func_175599_af();
      this.field_146289_q = p_146280_1_.field_71466_p;
      this.field_146294_l = p_146280_2_;
      this.field_146295_m = p_146280_3_;
      this.field_146292_n.clear();
      this.func_73866_w_();
   }

   public void func_183500_a(int p_183500_1_, int p_183500_2_) {
      this.field_146294_l = p_183500_1_;
      this.field_146295_m = p_183500_2_;
   }

   public void func_73866_w_() {
   }

   public void func_146269_k() throws IOException {
      if (Mouse.isCreated()) {
         while(Mouse.next()) {
            this.func_146274_d();
         }
      }

      if (Keyboard.isCreated()) {
         while(Keyboard.next()) {
            this.func_146282_l();
         }
      }

   }

   public void func_146274_d() throws IOException {
      int i = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
      int j = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
      int k = Mouse.getEventButton();
      if (Mouse.getEventButtonState()) {
         if (this.field_146297_k.field_71474_y.field_85185_A && this.field_146298_h++ > 0) {
            return;
         }

         this.field_146287_f = k;
         this.field_146288_g = Minecraft.func_71386_F();
         this.func_73864_a(i, j, this.field_146287_f);
      } else if (k != -1) {
         if (this.field_146297_k.field_71474_y.field_85185_A && --this.field_146298_h > 0) {
            return;
         }

         this.field_146287_f = -1;
         this.func_146286_b(i, j, k);
      } else if (this.field_146287_f != -1 && this.field_146288_g > 0L) {
         long l = Minecraft.func_71386_F() - this.field_146288_g;
         this.func_146273_a(i, j, this.field_146287_f, l);
      }

   }

   public void func_146282_l() throws IOException {
      char c0 = Keyboard.getEventCharacter();
      if (Keyboard.getEventKey() == 0 && c0 >= ' ' || Keyboard.getEventKeyState()) {
         this.func_73869_a(c0, Keyboard.getEventKey());
      }

      this.field_146297_k.func_152348_aa();
   }

   public void func_73876_c() {
   }

   public void func_146281_b() {
   }

   public void func_146276_q_() {
      this.func_146270_b(0);
   }

   public void func_146270_b(int p_146270_1_) {
      if (this.field_146297_k.field_71441_e != null) {
         this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, -1072689136, -804253680);
      } else {
         this.func_146278_c(p_146270_1_);
      }

   }

   public void func_146278_c(int p_146278_1_) {
      GlStateManager.func_179140_f();
      GlStateManager.func_179106_n();
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder bufferbuilder = tessellator.func_178180_c();
      this.field_146297_k.func_110434_K().func_110577_a(field_110325_k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      bufferbuilder.func_181662_b(0.0D, (double)this.field_146295_m, 0.0D).func_187315_a(0.0D, (double)((float)this.field_146295_m / 32.0F + (float)p_146278_1_)).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)this.field_146294_l, (double)this.field_146295_m, 0.0D).func_187315_a((double)((float)this.field_146294_l / 32.0F), (double)((float)this.field_146295_m / 32.0F + (float)p_146278_1_)).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b((double)this.field_146294_l, 0.0D, 0.0D).func_187315_a((double)((float)this.field_146294_l / 32.0F), (double)p_146278_1_).func_181669_b(64, 64, 64, 255).func_181675_d();
      bufferbuilder.func_181662_b(0.0D, 0.0D, 0.0D).func_187315_a(0.0D, (double)p_146278_1_).func_181669_b(64, 64, 64, 255).func_181675_d();
      tessellator.func_78381_a();
   }

   public boolean func_73868_f() {
      return true;
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if (p_73878_2_ == 31102009) {
         if (p_73878_1_) {
            this.func_175282_a(this.field_175286_t);
         }

         this.field_175286_t = null;
         this.field_146297_k.func_147108_a(this);
      }

   }

   private void func_175282_a(URI p_175282_1_) {
      try {
         Class<?> oclass = Class.forName("java.awt.Desktop");
         Object object = oclass.getMethod("getDesktop").invoke((Object)null);
         oclass.getMethod("browse", URI.class).invoke(object, p_175282_1_);
      } catch (Throwable throwable1) {
         Throwable throwable = throwable1.getCause();
         field_175287_a.error("Couldn't open link: {}", (Object)(throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
      }

   }

   public static boolean func_146271_m() {
      if (Minecraft.field_142025_a) {
         return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
      } else {
         return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
      }
   }

   public static boolean func_146272_n() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public static boolean func_175283_s() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public static boolean func_175277_d(int p_175277_0_) {
      return p_175277_0_ == 45 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175279_e(int p_175279_0_) {
      return p_175279_0_ == 47 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175280_f(int p_175280_0_) {
      return p_175280_0_ == 46 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175278_g(int p_175278_0_) {
      return p_175278_0_ == 30 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public void func_175273_b(Minecraft p_175273_1_, int p_175273_2_, int p_175273_3_) {
      this.func_146280_a(p_175273_1_, p_175273_2_, p_175273_3_);
   }
}
