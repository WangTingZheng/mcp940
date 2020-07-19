package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger field_147415_a = LogManager.getLogger();
   private final Minecraft field_78859_a;
   private final List<ServerData> field_78858_b = Lists.<ServerData>newArrayList();

   public ServerList(Minecraft p_i1194_1_) {
      this.field_78859_a = p_i1194_1_;
      this.func_78853_a();
   }

   public void func_78853_a() {
      try {
         this.field_78858_b.clear();
         NBTTagCompound nbttagcompound = CompressedStreamTools.func_74797_a(new File(this.field_78859_a.field_71412_D, "servers.dat"));
         if (nbttagcompound == null) {
            return;
         }

         NBTTagList nbttaglist = nbttagcompound.func_150295_c("servers", 10);

         for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            this.field_78858_b.add(ServerData.func_78837_a(nbttaglist.func_150305_b(i)));
         }
      } catch (Exception exception) {
         field_147415_a.error("Couldn't load server list", (Throwable)exception);
      }

   }

   public void func_78855_b() {
      try {
         NBTTagList nbttaglist = new NBTTagList();

         for(ServerData serverdata : this.field_78858_b) {
            nbttaglist.func_74742_a(serverdata.func_78836_a());
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.func_74782_a("servers", nbttaglist);
         CompressedStreamTools.func_74793_a(nbttagcompound, new File(this.field_78859_a.field_71412_D, "servers.dat"));
      } catch (Exception exception) {
         field_147415_a.error("Couldn't save server list", (Throwable)exception);
      }

   }

   public ServerData func_78850_a(int p_78850_1_) {
      return this.field_78858_b.get(p_78850_1_);
   }

   public void func_78851_b(int p_78851_1_) {
      this.field_78858_b.remove(p_78851_1_);
   }

   public void func_78849_a(ServerData p_78849_1_) {
      this.field_78858_b.add(p_78849_1_);
   }

   public int func_78856_c() {
      return this.field_78858_b.size();
   }

   public void func_78857_a(int p_78857_1_, int p_78857_2_) {
      ServerData serverdata = this.func_78850_a(p_78857_1_);
      this.field_78858_b.set(p_78857_1_, this.func_78850_a(p_78857_2_));
      this.field_78858_b.set(p_78857_2_, serverdata);
      this.func_78855_b();
   }

   public void func_147413_a(int p_147413_1_, ServerData p_147413_2_) {
      this.field_78858_b.set(p_147413_1_, p_147413_2_);
   }

   public static void func_147414_b(ServerData p_147414_0_) {
      ServerList serverlist = new ServerList(Minecraft.func_71410_x());
      serverlist.func_78853_a();

      for(int i = 0; i < serverlist.func_78856_c(); ++i) {
         ServerData serverdata = serverlist.func_78850_a(i);
         if (serverdata.field_78847_a.equals(p_147414_0_.field_78847_a) && serverdata.field_78845_b.equals(p_147414_0_.field_78845_b)) {
            serverlist.func_147413_a(i, p_147414_0_);
            break;
         }
      }

      serverlist.func_78855_b();
   }
}
