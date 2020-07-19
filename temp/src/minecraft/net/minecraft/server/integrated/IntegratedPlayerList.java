package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerList;

public class IntegratedPlayerList extends PlayerList {
   private NBTTagCompound field_72416_e;

   public IntegratedPlayerList(IntegratedServer p_i1314_1_) {
      super(p_i1314_1_);
      this.func_152611_a(10);
   }

   protected void func_72391_b(EntityPlayerMP p_72391_1_) {
      if (p_72391_1_.func_70005_c_().equals(this.func_72365_p().func_71214_G())) {
         this.field_72416_e = p_72391_1_.func_189511_e(new NBTTagCompound());
      }

      super.func_72391_b(p_72391_1_);
   }

   public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_) {
      return p_148542_2_.getName().equalsIgnoreCase(this.func_72365_p().func_71214_G()) && this.func_152612_a(p_148542_2_.getName()) != null ? "That name is already taken." : super.func_148542_a(p_148542_1_, p_148542_2_);
   }

   public IntegratedServer func_72365_p() {
      return (IntegratedServer)super.func_72365_p();
   }

   public NBTTagCompound func_72378_q() {
      return this.field_72416_e;
   }
}
