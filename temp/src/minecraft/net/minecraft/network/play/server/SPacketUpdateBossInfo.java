package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class SPacketUpdateBossInfo implements Packet<INetHandlerPlayClient> {
   private UUID field_186911_a;
   private SPacketUpdateBossInfo.Operation field_186912_b;
   private ITextComponent field_186913_c;
   private float field_186914_d;
   private BossInfo.Color field_186915_e;
   private BossInfo.Overlay field_186916_f;
   private boolean field_186917_g;
   private boolean field_186918_h;
   private boolean field_186919_i;

   public SPacketUpdateBossInfo() {
   }

   public SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation p_i46964_1_, BossInfo p_i46964_2_) {
      this.field_186912_b = p_i46964_1_;
      this.field_186911_a = p_i46964_2_.func_186737_d();
      this.field_186913_c = p_i46964_2_.func_186744_e();
      this.field_186914_d = p_i46964_2_.func_186738_f();
      this.field_186915_e = p_i46964_2_.func_186736_g();
      this.field_186916_f = p_i46964_2_.func_186740_h();
      this.field_186917_g = p_i46964_2_.func_186734_i();
      this.field_186918_h = p_i46964_2_.func_186747_j();
      this.field_186919_i = p_i46964_2_.func_186748_k();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_186911_a = p_148837_1_.func_179253_g();
      this.field_186912_b = (SPacketUpdateBossInfo.Operation)p_148837_1_.func_179257_a(SPacketUpdateBossInfo.Operation.class);
      switch(this.field_186912_b) {
      case ADD:
         this.field_186913_c = p_148837_1_.func_179258_d();
         this.field_186914_d = p_148837_1_.readFloat();
         this.field_186915_e = (BossInfo.Color)p_148837_1_.func_179257_a(BossInfo.Color.class);
         this.field_186916_f = (BossInfo.Overlay)p_148837_1_.func_179257_a(BossInfo.Overlay.class);
         this.func_186903_a(p_148837_1_.readUnsignedByte());
      case REMOVE:
      default:
         break;
      case UPDATE_PCT:
         this.field_186914_d = p_148837_1_.readFloat();
         break;
      case UPDATE_NAME:
         this.field_186913_c = p_148837_1_.func_179258_d();
         break;
      case UPDATE_STYLE:
         this.field_186915_e = (BossInfo.Color)p_148837_1_.func_179257_a(BossInfo.Color.class);
         this.field_186916_f = (BossInfo.Overlay)p_148837_1_.func_179257_a(BossInfo.Overlay.class);
         break;
      case UPDATE_PROPERTIES:
         this.func_186903_a(p_148837_1_.readUnsignedByte());
      }

   }

   private void func_186903_a(int p_186903_1_) {
      this.field_186917_g = (p_186903_1_ & 1) > 0;
      this.field_186918_h = (p_186903_1_ & 2) > 0;
      this.field_186919_i = (p_186903_1_ & 2) > 0;
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179252_a(this.field_186911_a);
      p_148840_1_.func_179249_a(this.field_186912_b);
      switch(this.field_186912_b) {
      case ADD:
         p_148840_1_.func_179256_a(this.field_186913_c);
         p_148840_1_.writeFloat(this.field_186914_d);
         p_148840_1_.func_179249_a(this.field_186915_e);
         p_148840_1_.func_179249_a(this.field_186916_f);
         p_148840_1_.writeByte(this.func_186905_j());
      case REMOVE:
      default:
         break;
      case UPDATE_PCT:
         p_148840_1_.writeFloat(this.field_186914_d);
         break;
      case UPDATE_NAME:
         p_148840_1_.func_179256_a(this.field_186913_c);
         break;
      case UPDATE_STYLE:
         p_148840_1_.func_179249_a(this.field_186915_e);
         p_148840_1_.func_179249_a(this.field_186916_f);
         break;
      case UPDATE_PROPERTIES:
         p_148840_1_.writeByte(this.func_186905_j());
      }

   }

   private int func_186905_j() {
      int i = 0;
      if (this.field_186917_g) {
         i |= 1;
      }

      if (this.field_186918_h) {
         i |= 2;
      }

      if (this.field_186919_i) {
         i |= 2;
      }

      return i;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_184325_a(this);
   }

   public UUID func_186908_a() {
      return this.field_186911_a;
   }

   public SPacketUpdateBossInfo.Operation func_186902_b() {
      return this.field_186912_b;
   }

   public ITextComponent func_186907_c() {
      return this.field_186913_c;
   }

   public float func_186906_d() {
      return this.field_186914_d;
   }

   public BossInfo.Color func_186900_e() {
      return this.field_186915_e;
   }

   public BossInfo.Overlay func_186904_f() {
      return this.field_186916_f;
   }

   public boolean func_186909_g() {
      return this.field_186917_g;
   }

   public boolean func_186910_h() {
      return this.field_186918_h;
   }

   public boolean func_186901_i() {
      return this.field_186919_i;
   }

   public static enum Operation {
      ADD,
      REMOVE,
      UPDATE_PCT,
      UPDATE_NAME,
      UPDATE_STYLE,
      UPDATE_PROPERTIES;
   }
}
