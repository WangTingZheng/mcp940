package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ServerData {
   public String field_78847_a;
   public String field_78845_b;
   public String field_78846_c;
   public String field_78843_d;
   public long field_78844_e;
   public int field_82821_f = 335;
   public String field_82822_g = "1.12";
   public boolean field_78841_f;
   public String field_147412_i;
   private ServerData.ServerResourceMode field_152587_j = ServerData.ServerResourceMode.PROMPT;
   private String field_147411_m;
   private boolean field_181042_l;

   public ServerData(String p_i46420_1_, String p_i46420_2_, boolean p_i46420_3_) {
      this.field_78847_a = p_i46420_1_;
      this.field_78845_b = p_i46420_2_;
      this.field_181042_l = p_i46420_3_;
   }

   public NBTTagCompound func_78836_a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.func_74778_a("name", this.field_78847_a);
      nbttagcompound.func_74778_a("ip", this.field_78845_b);
      if (this.field_147411_m != null) {
         nbttagcompound.func_74778_a("icon", this.field_147411_m);
      }

      if (this.field_152587_j == ServerData.ServerResourceMode.ENABLED) {
         nbttagcompound.func_74757_a("acceptTextures", true);
      } else if (this.field_152587_j == ServerData.ServerResourceMode.DISABLED) {
         nbttagcompound.func_74757_a("acceptTextures", false);
      }

      return nbttagcompound;
   }

   public ServerData.ServerResourceMode func_152586_b() {
      return this.field_152587_j;
   }

   public void func_152584_a(ServerData.ServerResourceMode p_152584_1_) {
      this.field_152587_j = p_152584_1_;
   }

   public static ServerData func_78837_a(NBTTagCompound p_78837_0_) {
      ServerData serverdata = new ServerData(p_78837_0_.func_74779_i("name"), p_78837_0_.func_74779_i("ip"), false);
      if (p_78837_0_.func_150297_b("icon", 8)) {
         serverdata.func_147407_a(p_78837_0_.func_74779_i("icon"));
      }

      if (p_78837_0_.func_150297_b("acceptTextures", 1)) {
         if (p_78837_0_.func_74767_n("acceptTextures")) {
            serverdata.func_152584_a(ServerData.ServerResourceMode.ENABLED);
         } else {
            serverdata.func_152584_a(ServerData.ServerResourceMode.DISABLED);
         }
      } else {
         serverdata.func_152584_a(ServerData.ServerResourceMode.PROMPT);
      }

      return serverdata;
   }

   public String func_147409_e() {
      return this.field_147411_m;
   }

   public void func_147407_a(String p_147407_1_) {
      this.field_147411_m = p_147407_1_;
   }

   public boolean func_181041_d() {
      return this.field_181042_l;
   }

   public void func_152583_a(ServerData p_152583_1_) {
      this.field_78845_b = p_152583_1_.field_78845_b;
      this.field_78847_a = p_152583_1_.field_78847_a;
      this.func_152584_a(p_152583_1_.func_152586_b());
      this.field_147411_m = p_152583_1_.field_147411_m;
      this.field_181042_l = p_152583_1_.field_181042_l;
   }

   public static enum ServerResourceMode {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final ITextComponent field_152594_d;

      private ServerResourceMode(String p_i1053_3_) {
         this.field_152594_d = new TextComponentTranslation("addServer.resourcePack." + p_i1053_3_, new Object[0]);
      }

      public ITextComponent func_152589_a() {
         return this.field_152594_d;
      }
   }
}
