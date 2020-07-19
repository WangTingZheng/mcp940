package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;

public class TileEntitySkull extends TileEntity implements ITickable {
   private int field_145908_a;
   private int field_145910_i;
   private GameProfile field_152110_j;
   private int field_184296_h;
   private boolean field_184297_i;
   private static PlayerProfileCache field_184298_j;
   private static MinecraftSessionService field_184299_k;

   public static void func_184293_a(PlayerProfileCache p_184293_0_) {
      field_184298_j = p_184293_0_;
   }

   public static void func_184294_a(MinecraftSessionService p_184294_0_) {
      field_184299_k = p_184294_0_;
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74774_a("SkullType", (byte)(this.field_145908_a & 255));
      p_189515_1_.func_74774_a("Rot", (byte)(this.field_145910_i & 255));
      if (this.field_152110_j != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         NBTUtil.func_180708_a(nbttagcompound, this.field_152110_j);
         p_189515_1_.func_74782_a("Owner", nbttagcompound);
      }

      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145908_a = p_145839_1_.func_74771_c("SkullType");
      this.field_145910_i = p_145839_1_.func_74771_c("Rot");
      if (this.field_145908_a == 3) {
         if (p_145839_1_.func_150297_b("Owner", 10)) {
            this.field_152110_j = NBTUtil.func_152459_a(p_145839_1_.func_74775_l("Owner"));
         } else if (p_145839_1_.func_150297_b("ExtraType", 8)) {
            String s = p_145839_1_.func_74779_i("ExtraType");
            if (!StringUtils.func_151246_b(s)) {
               this.field_152110_j = new GameProfile((UUID)null, s);
               this.func_152109_d();
            }
         }
      }

   }

   public void func_73660_a() {
      if (this.field_145908_a == 5) {
         if (this.field_145850_b.func_175640_z(this.field_174879_c)) {
            this.field_184297_i = true;
            ++this.field_184296_h;
         } else {
            this.field_184297_i = false;
         }
      }

   }

   public float func_184295_a(float p_184295_1_) {
      return this.field_184297_i ? (float)this.field_184296_h + p_184295_1_ : (float)this.field_184296_h;
   }

   @Nullable
   public GameProfile func_152108_a() {
      return this.field_152110_j;
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 4, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public void func_152107_a(int p_152107_1_) {
      this.field_145908_a = p_152107_1_;
      this.field_152110_j = null;
   }

   public void func_152106_a(@Nullable GameProfile p_152106_1_) {
      this.field_145908_a = 3;
      this.field_152110_j = p_152106_1_;
      this.func_152109_d();
   }

   private void func_152109_d() {
      this.field_152110_j = func_174884_b(this.field_152110_j);
      this.func_70296_d();
   }

   public static GameProfile func_174884_b(GameProfile p_174884_0_) {
      if (p_174884_0_ != null && !StringUtils.func_151246_b(p_174884_0_.getName())) {
         if (p_174884_0_.isComplete() && p_174884_0_.getProperties().containsKey("textures")) {
            return p_174884_0_;
         } else if (field_184298_j != null && field_184299_k != null) {
            GameProfile gameprofile = field_184298_j.func_152655_a(p_174884_0_.getName());
            if (gameprofile == null) {
               return p_174884_0_;
            } else {
               Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object)null);
               if (property == null) {
                  gameprofile = field_184299_k.fillProfileProperties(gameprofile, true);
               }

               return gameprofile;
            }
         } else {
            return p_174884_0_;
         }
      } else {
         return p_174884_0_;
      }
   }

   public int func_145904_a() {
      return this.field_145908_a;
   }

   public int func_145906_b() {
      return this.field_145910_i;
   }

   public void func_145903_a(int p_145903_1_) {
      this.field_145910_i = p_145903_1_;
   }

   public void func_189668_a(Mirror p_189668_1_) {
      if (this.field_145850_b != null && this.field_145850_b.func_180495_p(this.func_174877_v()).func_177229_b(BlockSkull.field_176418_a) == EnumFacing.UP) {
         this.field_145910_i = p_189668_1_.func_185802_a(this.field_145910_i, 16);
      }

   }

   public void func_189667_a(Rotation p_189667_1_) {
      if (this.field_145850_b != null && this.field_145850_b.func_180495_p(this.func_174877_v()).func_177229_b(BlockSkull.field_176418_a) == EnumFacing.UP) {
         this.field_145910_i = p_189667_1_.func_185833_a(this.field_145910_i, 16);
      }

   }
}
