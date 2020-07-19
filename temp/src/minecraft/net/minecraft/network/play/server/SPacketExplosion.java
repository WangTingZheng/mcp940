package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SPacketExplosion implements Packet<INetHandlerPlayClient> {
   private double field_149158_a;
   private double field_149156_b;
   private double field_149157_c;
   private float field_149154_d;
   private List<BlockPos> field_149155_e;
   private float field_149152_f;
   private float field_149153_g;
   private float field_149159_h;

   public SPacketExplosion() {
   }

   public SPacketExplosion(double p_i47099_1_, double p_i47099_3_, double p_i47099_5_, float p_i47099_7_, List<BlockPos> p_i47099_8_, Vec3d p_i47099_9_) {
      this.field_149158_a = p_i47099_1_;
      this.field_149156_b = p_i47099_3_;
      this.field_149157_c = p_i47099_5_;
      this.field_149154_d = p_i47099_7_;
      this.field_149155_e = Lists.newArrayList(p_i47099_8_);
      if (p_i47099_9_ != null) {
         this.field_149152_f = (float)p_i47099_9_.field_72450_a;
         this.field_149153_g = (float)p_i47099_9_.field_72448_b;
         this.field_149159_h = (float)p_i47099_9_.field_72449_c;
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149158_a = (double)p_148837_1_.readFloat();
      this.field_149156_b = (double)p_148837_1_.readFloat();
      this.field_149157_c = (double)p_148837_1_.readFloat();
      this.field_149154_d = p_148837_1_.readFloat();
      int i = p_148837_1_.readInt();
      this.field_149155_e = Lists.<BlockPos>newArrayListWithCapacity(i);
      int j = (int)this.field_149158_a;
      int k = (int)this.field_149156_b;
      int l = (int)this.field_149157_c;

      for(int i1 = 0; i1 < i; ++i1) {
         int j1 = p_148837_1_.readByte() + j;
         int k1 = p_148837_1_.readByte() + k;
         int l1 = p_148837_1_.readByte() + l;
         this.field_149155_e.add(new BlockPos(j1, k1, l1));
      }

      this.field_149152_f = p_148837_1_.readFloat();
      this.field_149153_g = p_148837_1_.readFloat();
      this.field_149159_h = p_148837_1_.readFloat();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeFloat((float)this.field_149158_a);
      p_148840_1_.writeFloat((float)this.field_149156_b);
      p_148840_1_.writeFloat((float)this.field_149157_c);
      p_148840_1_.writeFloat(this.field_149154_d);
      p_148840_1_.writeInt(this.field_149155_e.size());
      int i = (int)this.field_149158_a;
      int j = (int)this.field_149156_b;
      int k = (int)this.field_149157_c;

      for(BlockPos blockpos : this.field_149155_e) {
         int l = blockpos.func_177958_n() - i;
         int i1 = blockpos.func_177956_o() - j;
         int j1 = blockpos.func_177952_p() - k;
         p_148840_1_.writeByte(l);
         p_148840_1_.writeByte(i1);
         p_148840_1_.writeByte(j1);
      }

      p_148840_1_.writeFloat(this.field_149152_f);
      p_148840_1_.writeFloat(this.field_149153_g);
      p_148840_1_.writeFloat(this.field_149159_h);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147283_a(this);
   }

   public float func_149149_c() {
      return this.field_149152_f;
   }

   public float func_149144_d() {
      return this.field_149153_g;
   }

   public float func_149147_e() {
      return this.field_149159_h;
   }

   public double func_149148_f() {
      return this.field_149158_a;
   }

   public double func_149143_g() {
      return this.field_149156_b;
   }

   public double func_149145_h() {
      return this.field_149157_c;
   }

   public float func_149146_i() {
      return this.field_149154_d;
   }

   public List<BlockPos> func_149150_j() {
      return this.field_149155_e;
   }
}
