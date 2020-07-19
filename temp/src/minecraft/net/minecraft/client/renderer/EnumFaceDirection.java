package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum EnumFaceDirection {
   DOWN(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a)}),
   UP(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d)}),
   NORTH(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d)}),
   SOUTH(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a)}),
   WEST(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179176_f, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a)}),
   EAST(new EnumFaceDirection.VertexInformation[]{new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179181_a), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179178_e, EnumFaceDirection.Constants.field_179177_d), new EnumFaceDirection.VertexInformation(EnumFaceDirection.Constants.field_179180_c, EnumFaceDirection.Constants.field_179179_b, EnumFaceDirection.Constants.field_179177_d)});

   private static final EnumFaceDirection[] field_179029_g = new EnumFaceDirection[6];
   private final EnumFaceDirection.VertexInformation[] field_179035_h;

   public static EnumFaceDirection func_179027_a(EnumFacing p_179027_0_) {
      return field_179029_g[p_179027_0_.func_176745_a()];
   }

   private EnumFaceDirection(EnumFaceDirection.VertexInformation[] p_i46272_3_) {
      this.field_179035_h = p_i46272_3_;
   }

   public EnumFaceDirection.VertexInformation func_179025_a(int p_179025_1_) {
      return this.field_179035_h[p_179025_1_];
   }

   static {
      field_179029_g[EnumFaceDirection.Constants.field_179178_e] = DOWN;
      field_179029_g[EnumFaceDirection.Constants.field_179179_b] = UP;
      field_179029_g[EnumFaceDirection.Constants.field_179177_d] = NORTH;
      field_179029_g[EnumFaceDirection.Constants.field_179181_a] = SOUTH;
      field_179029_g[EnumFaceDirection.Constants.field_179176_f] = WEST;
      field_179029_g[EnumFaceDirection.Constants.field_179180_c] = EAST;
   }

   public static final class Constants {
      public static final int field_179181_a = EnumFacing.SOUTH.func_176745_a();
      public static final int field_179179_b = EnumFacing.UP.func_176745_a();
      public static final int field_179180_c = EnumFacing.EAST.func_176745_a();
      public static final int field_179177_d = EnumFacing.NORTH.func_176745_a();
      public static final int field_179178_e = EnumFacing.DOWN.func_176745_a();
      public static final int field_179176_f = EnumFacing.WEST.func_176745_a();
   }

   public static class VertexInformation {
      public final int field_179184_a;
      public final int field_179182_b;
      public final int field_179183_c;

      private VertexInformation(int p_i46270_1_, int p_i46270_2_, int p_i46270_3_) {
         this.field_179184_a = p_i46270_1_;
         this.field_179182_b = p_i46270_2_;
         this.field_179183_c = p_i46270_3_;
      }
   }
}
