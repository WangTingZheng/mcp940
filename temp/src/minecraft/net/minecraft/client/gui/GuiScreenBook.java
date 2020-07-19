package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen {
   private static final Logger field_146473_a = LogManager.getLogger();
   private static final ResourceLocation field_146466_f = new ResourceLocation("textures/gui/book.png");
   private final EntityPlayer field_146468_g;
   private final ItemStack field_146474_h;
   private final boolean field_146475_i;
   private boolean field_146481_r;
   private boolean field_146480_s;
   private int field_146479_t;
   private final int field_146478_u = 192;
   private final int field_146477_v = 192;
   private int field_146476_w = 1;
   private int field_146484_x;
   private NBTTagList field_146483_y;
   private String field_146482_z = "";
   private List<ITextComponent> field_175386_A;
   private int field_175387_B = -1;
   private GuiScreenBook.NextPageButton field_146470_A;
   private GuiScreenBook.NextPageButton field_146471_B;
   private GuiButton field_146472_C;
   private GuiButton field_146465_D;
   private GuiButton field_146467_E;
   private GuiButton field_146469_F;

   public GuiScreenBook(EntityPlayer p_i1080_1_, ItemStack p_i1080_2_, boolean p_i1080_3_) {
      this.field_146468_g = p_i1080_1_;
      this.field_146474_h = p_i1080_2_;
      this.field_146475_i = p_i1080_3_;
      if (p_i1080_2_.func_77942_o()) {
         NBTTagCompound nbttagcompound = p_i1080_2_.func_77978_p();
         this.field_146483_y = nbttagcompound.func_150295_c("pages", 8).func_74737_b();
         this.field_146476_w = this.field_146483_y.func_74745_c();
         if (this.field_146476_w < 1) {
            this.field_146476_w = 1;
         }
      }

      if (this.field_146483_y == null && p_i1080_3_) {
         this.field_146483_y = new NBTTagList();
         this.field_146483_y.func_74742_a(new NBTTagString(""));
         this.field_146476_w = 1;
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146479_t;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      Keyboard.enableRepeatEvents(true);
      if (this.field_146475_i) {
         this.field_146465_D = this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 - 100, 196, 98, 20, I18n.func_135052_a("book.signButton")));
         this.field_146472_C = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 + 2, 196, 98, 20, I18n.func_135052_a("gui.done")));
         this.field_146467_E = this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 100, 196, 98, 20, I18n.func_135052_a("book.finalizeButton")));
         this.field_146469_F = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 2, 196, 98, 20, I18n.func_135052_a("gui.cancel")));
      } else {
         this.field_146472_C = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, 196, 200, 20, I18n.func_135052_a("gui.done")));
      }

      int i = (this.field_146294_l - 192) / 2;
      int j = 2;
      this.field_146470_A = (GuiScreenBook.NextPageButton)this.func_189646_b(new GuiScreenBook.NextPageButton(1, i + 120, 156, true));
      this.field_146471_B = (GuiScreenBook.NextPageButton)this.func_189646_b(new GuiScreenBook.NextPageButton(2, i + 38, 156, false));
      this.func_146464_h();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   private void func_146464_h() {
      this.field_146470_A.field_146125_m = !this.field_146480_s && (this.field_146484_x < this.field_146476_w - 1 || this.field_146475_i);
      this.field_146471_B.field_146125_m = !this.field_146480_s && this.field_146484_x > 0;
      this.field_146472_C.field_146125_m = !this.field_146475_i || !this.field_146480_s;
      if (this.field_146475_i) {
         this.field_146465_D.field_146125_m = !this.field_146480_s;
         this.field_146469_F.field_146125_m = this.field_146480_s;
         this.field_146467_E.field_146125_m = this.field_146480_s;
         this.field_146467_E.field_146124_l = !this.field_146482_z.trim().isEmpty();
      }

   }

   private void func_146462_a(boolean p_146462_1_) throws IOException {
      if (this.field_146475_i && this.field_146481_r) {
         if (this.field_146483_y != null) {
            while(this.field_146483_y.func_74745_c() > 1) {
               String s = this.field_146483_y.func_150307_f(this.field_146483_y.func_74745_c() - 1);
               if (!s.isEmpty()) {
                  break;
               }

               this.field_146483_y.func_74744_a(this.field_146483_y.func_74745_c() - 1);
            }

            if (this.field_146474_h.func_77942_o()) {
               NBTTagCompound nbttagcompound = this.field_146474_h.func_77978_p();
               nbttagcompound.func_74782_a("pages", this.field_146483_y);
            } else {
               this.field_146474_h.func_77983_a("pages", this.field_146483_y);
            }

            String s1 = "MC|BEdit";
            if (p_146462_1_) {
               s1 = "MC|BSign";
               this.field_146474_h.func_77983_a("author", new NBTTagString(this.field_146468_g.func_70005_c_()));
               this.field_146474_h.func_77983_a("title", new NBTTagString(this.field_146482_z.trim()));
            }

            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
            packetbuffer.func_150788_a(this.field_146474_h);
            this.field_146297_k.func_147114_u().func_147297_a(new CPacketCustomPayload(s1, packetbuffer));
         }

      }
   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if (p_146284_1_.field_146124_l) {
         if (p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
            this.func_146462_a(false);
         } else if (p_146284_1_.field_146127_k == 3 && this.field_146475_i) {
            this.field_146480_s = true;
         } else if (p_146284_1_.field_146127_k == 1) {
            if (this.field_146484_x < this.field_146476_w - 1) {
               ++this.field_146484_x;
            } else if (this.field_146475_i) {
               this.func_146461_i();
               if (this.field_146484_x < this.field_146476_w - 1) {
                  ++this.field_146484_x;
               }
            }
         } else if (p_146284_1_.field_146127_k == 2) {
            if (this.field_146484_x > 0) {
               --this.field_146484_x;
            }
         } else if (p_146284_1_.field_146127_k == 5 && this.field_146480_s) {
            this.func_146462_a(true);
            this.field_146297_k.func_147108_a((GuiScreen)null);
         } else if (p_146284_1_.field_146127_k == 4 && this.field_146480_s) {
            this.field_146480_s = false;
         }

         this.func_146464_h();
      }
   }

   private void func_146461_i() {
      if (this.field_146483_y != null && this.field_146483_y.func_74745_c() < 50) {
         this.field_146483_y.func_74742_a(new NBTTagString(""));
         ++this.field_146476_w;
         this.field_146481_r = true;
      }
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      super.func_73869_a(p_73869_1_, p_73869_2_);
      if (this.field_146475_i) {
         if (this.field_146480_s) {
            this.func_146460_c(p_73869_1_, p_73869_2_);
         } else {
            this.func_146463_b(p_73869_1_, p_73869_2_);
         }

      }
   }

   private void func_146463_b(char p_146463_1_, int p_146463_2_) {
      if (GuiScreen.func_175279_e(p_146463_2_)) {
         this.func_146459_b(GuiScreen.func_146277_j());
      } else {
         switch(p_146463_2_) {
         case 14:
            String s = this.func_146456_p();
            if (!s.isEmpty()) {
               this.func_146457_a(s.substring(0, s.length() - 1));
            }

            return;
         case 28:
         case 156:
            this.func_146459_b("\n");
            return;
         default:
            if (ChatAllowedCharacters.func_71566_a(p_146463_1_)) {
               this.func_146459_b(Character.toString(p_146463_1_));
            }
         }
      }
   }

   private void func_146460_c(char p_146460_1_, int p_146460_2_) throws IOException {
      switch(p_146460_2_) {
      case 14:
         if (!this.field_146482_z.isEmpty()) {
            this.field_146482_z = this.field_146482_z.substring(0, this.field_146482_z.length() - 1);
            this.func_146464_h();
         }

         return;
      case 28:
      case 156:
         if (!this.field_146482_z.isEmpty()) {
            this.func_146462_a(true);
            this.field_146297_k.func_147108_a((GuiScreen)null);
         }

         return;
      default:
         if (this.field_146482_z.length() < 16 && ChatAllowedCharacters.func_71566_a(p_146460_1_)) {
            this.field_146482_z = this.field_146482_z + Character.toString(p_146460_1_);
            this.func_146464_h();
            this.field_146481_r = true;
         }

      }
   }

   private String func_146456_p() {
      return this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.func_74745_c() ? this.field_146483_y.func_150307_f(this.field_146484_x) : "";
   }

   private void func_146457_a(String p_146457_1_) {
      if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.func_74745_c()) {
         this.field_146483_y.func_150304_a(this.field_146484_x, new NBTTagString(p_146457_1_));
         this.field_146481_r = true;
      }

   }

   private void func_146459_b(String p_146459_1_) {
      String s = this.func_146456_p();
      String s1 = s + p_146459_1_;
      int i = this.field_146289_q.func_78267_b(s1 + "" + TextFormatting.BLACK + "_", 118);
      if (i <= 128 && s1.length() < 256) {
         this.func_146457_a(s1);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_146466_f);
      int i = (this.field_146294_l - 192) / 2;
      int j = 2;
      this.func_73729_b(i, 2, 0, 0, 192, 192);
      if (this.field_146480_s) {
         String s = this.field_146482_z;
         if (this.field_146475_i) {
            if (this.field_146479_t / 6 % 2 == 0) {
               s = s + "" + TextFormatting.BLACK + "_";
            } else {
               s = s + "" + TextFormatting.GRAY + "_";
            }
         }

         String s1 = I18n.func_135052_a("book.editTitle");
         int k = this.field_146289_q.func_78256_a(s1);
         this.field_146289_q.func_78276_b(s1, i + 36 + (116 - k) / 2, 34, 0);
         int l = this.field_146289_q.func_78256_a(s);
         this.field_146289_q.func_78276_b(s, i + 36 + (116 - l) / 2, 50, 0);
         String s2 = I18n.func_135052_a("book.byAuthor", this.field_146468_g.func_70005_c_());
         int i1 = this.field_146289_q.func_78256_a(s2);
         this.field_146289_q.func_78276_b(TextFormatting.DARK_GRAY + s2, i + 36 + (116 - i1) / 2, 60, 0);
         String s3 = I18n.func_135052_a("book.finalizeWarning");
         this.field_146289_q.func_78279_b(s3, i + 36, 82, 116, 0);
      } else {
         String s4 = I18n.func_135052_a("book.pageIndicator", this.field_146484_x + 1, this.field_146476_w);
         String s5 = "";
         if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.func_74745_c()) {
            s5 = this.field_146483_y.func_150307_f(this.field_146484_x);
         }

         if (this.field_146475_i) {
            if (this.field_146289_q.func_78260_a()) {
               s5 = s5 + "_";
            } else if (this.field_146479_t / 6 % 2 == 0) {
               s5 = s5 + "" + TextFormatting.BLACK + "_";
            } else {
               s5 = s5 + "" + TextFormatting.GRAY + "_";
            }
         } else if (this.field_175387_B != this.field_146484_x) {
            if (ItemWrittenBook.func_77828_a(this.field_146474_h.func_77978_p())) {
               try {
                  ITextComponent itextcomponent = ITextComponent.Serializer.func_150699_a(s5);
                  this.field_175386_A = itextcomponent != null ? GuiUtilRenderComponents.func_178908_a(itextcomponent, 116, this.field_146289_q, true, true) : null;
               } catch (JsonParseException var13) {
                  this.field_175386_A = null;
               }
            } else {
               TextComponentString textcomponentstring = new TextComponentString(TextFormatting.DARK_RED + "* Invalid book tag *");
               this.field_175386_A = Lists.newArrayList(textcomponentstring);
            }

            this.field_175387_B = this.field_146484_x;
         }

         int j1 = this.field_146289_q.func_78256_a(s4);
         this.field_146289_q.func_78276_b(s4, i - j1 + 192 - 44, 18, 0);
         if (this.field_175386_A == null) {
            this.field_146289_q.func_78279_b(s5, i + 36, 34, 116, 0);
         } else {
            int k1 = Math.min(128 / this.field_146289_q.field_78288_b, this.field_175386_A.size());

            for(int l1 = 0; l1 < k1; ++l1) {
               ITextComponent itextcomponent2 = this.field_175386_A.get(l1);
               this.field_146289_q.func_78276_b(itextcomponent2.func_150260_c(), i + 36, 34 + l1 * this.field_146289_q.field_78288_b, 0);
            }

            ITextComponent itextcomponent1 = this.func_175385_b(p_73863_1_, p_73863_2_);
            if (itextcomponent1 != null) {
               this.func_175272_a(itextcomponent1, p_73863_1_, p_73863_2_);
            }
         }
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
      if (p_73864_3_ == 0) {
         ITextComponent itextcomponent = this.func_175385_b(p_73864_1_, p_73864_2_);
         if (itextcomponent != null && this.func_175276_a(itextcomponent)) {
            return;
         }
      }

      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public boolean func_175276_a(ITextComponent p_175276_1_) {
      ClickEvent clickevent = p_175276_1_.func_150256_b().func_150235_h();
      if (clickevent == null) {
         return false;
      } else if (clickevent.func_150669_a() == ClickEvent.Action.CHANGE_PAGE) {
         String s = clickevent.func_150668_b();

         try {
            int i = Integer.parseInt(s) - 1;
            if (i >= 0 && i < this.field_146476_w && i != this.field_146484_x) {
               this.field_146484_x = i;
               this.func_146464_h();
               return true;
            }
         } catch (Throwable var5) {
            ;
         }

         return false;
      } else {
         boolean flag = super.func_175276_a(p_175276_1_);
         if (flag && clickevent.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
            this.field_146297_k.func_147108_a((GuiScreen)null);
         }

         return flag;
      }
   }

   @Nullable
   public ITextComponent func_175385_b(int p_175385_1_, int p_175385_2_) {
      if (this.field_175386_A == null) {
         return null;
      } else {
         int i = p_175385_1_ - (this.field_146294_l - 192) / 2 - 36;
         int j = p_175385_2_ - 2 - 16 - 16;
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / this.field_146289_q.field_78288_b, this.field_175386_A.size());
            if (i <= 116 && j < this.field_146297_k.field_71466_p.field_78288_b * k + k) {
               int l = j / this.field_146297_k.field_71466_p.field_78288_b;
               if (l >= 0 && l < this.field_175386_A.size()) {
                  ITextComponent itextcomponent = this.field_175386_A.get(l);
                  int i1 = 0;

                  for(ITextComponent itextcomponent1 : itextcomponent) {
                     if (itextcomponent1 instanceof TextComponentString) {
                        i1 += this.field_146297_k.field_71466_p.func_78256_a(((TextComponentString)itextcomponent1).func_150265_g());
                        if (i1 > i) {
                           return itextcomponent1;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   static class NextPageButton extends GuiButton {
      private final boolean field_146151_o;

      public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_) {
         super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
         this.field_146151_o = p_i46316_4_;
      }

      public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
         if (this.field_146125_m) {
            boolean flag = p_191745_2_ >= this.field_146128_h && p_191745_3_ >= this.field_146129_i && p_191745_2_ < this.field_146128_h + this.field_146120_f && p_191745_3_ < this.field_146129_i + this.field_146121_g;
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            p_191745_1_.func_110434_K().func_110577_a(GuiScreenBook.field_146466_f);
            int i = 0;
            int j = 192;
            if (flag) {
               i += 23;
            }

            if (!this.field_146151_o) {
               j += 13;
            }

            this.func_73729_b(this.field_146128_h, this.field_146129_i, i, j, 23, 13);
         }
      }
   }
}
