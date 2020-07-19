package net.minecraft.tileentity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityEndGateway extends TileEntityEndPortal implements ITickable {
   private static final Logger field_184314_a = LogManager.getLogger();
   private long field_184315_f;
   private int field_184316_g;
   private BlockPos field_184317_h;
   private boolean field_184318_i;

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74772_a("Age", this.field_184315_f);
      if (this.field_184317_h != null) {
         p_189515_1_.func_74782_a("ExitPortal", NBTUtil.func_186859_a(this.field_184317_h));
      }

      if (this.field_184318_i) {
         p_189515_1_.func_74757_a("ExactTeleport", this.field_184318_i);
      }

      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_184315_f = p_145839_1_.func_74763_f("Age");
      if (p_145839_1_.func_150297_b("ExitPortal", 10)) {
         this.field_184317_h = NBTUtil.func_186861_c(p_145839_1_.func_74775_l("ExitPortal"));
      }

      this.field_184318_i = p_145839_1_.func_74767_n("ExactTeleport");
   }

   public double func_145833_n() {
      return 65536.0D;
   }

   public void func_73660_a() {
      boolean flag = this.func_184309_b();
      boolean flag1 = this.func_184310_d();
      ++this.field_184315_f;
      if (flag1) {
         --this.field_184316_g;
      } else if (!this.field_145850_b.field_72995_K) {
         List<Entity> list = this.field_145850_b.<Entity>func_72872_a(Entity.class, new AxisAlignedBB(this.func_174877_v()));
         if (!list.isEmpty()) {
            this.func_184306_a(list.get(0));
         }

         if (this.field_184315_f % 2400L == 0L) {
            this.func_184300_h();
         }
      }

      if (flag != this.func_184309_b() || flag1 != this.func_184310_d()) {
         this.func_70296_d();
      }

   }

   public boolean func_184309_b() {
      return this.field_184315_f < 200L;
   }

   public boolean func_184310_d() {
      return this.field_184316_g > 0;
   }

   public float func_184302_e(float p_184302_1_) {
      return MathHelper.func_76131_a(((float)this.field_184315_f + p_184302_1_) / 200.0F, 0.0F, 1.0F);
   }

   public float func_184305_g(float p_184305_1_) {
      return 1.0F - MathHelper.func_76131_a(((float)this.field_184316_g - p_184305_1_) / 40.0F, 0.0F, 1.0F);
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 8, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public void func_184300_h() {
      if (!this.field_145850_b.field_72995_K) {
         this.field_184316_g = 40;
         this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_145838_q(), 1, 0);
         this.func_70296_d();
      }

   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      if (p_145842_1_ == 1) {
         this.field_184316_g = 40;
         return true;
      } else {
         return super.func_145842_c(p_145842_1_, p_145842_2_);
      }
   }

   public void func_184306_a(Entity p_184306_1_) {
      if (!this.field_145850_b.field_72995_K && !this.func_184310_d()) {
         this.field_184316_g = 100;
         if (this.field_184317_h == null && this.field_145850_b.field_73011_w instanceof WorldProviderEnd) {
            this.func_184311_k();
         }

         if (this.field_184317_h != null) {
            BlockPos blockpos = this.field_184318_i ? this.field_184317_h : this.func_184303_j();
            p_184306_1_.func_70634_a((double)blockpos.func_177958_n() + 0.5D, (double)blockpos.func_177956_o() + 0.5D, (double)blockpos.func_177952_p() + 0.5D);
         }

         this.func_184300_h();
      }
   }

   private BlockPos func_184303_j() {
      BlockPos blockpos = func_184308_a(this.field_145850_b, this.field_184317_h, 5, false);
      field_184314_a.debug("Best exit position for portal at {} is {}", this.field_184317_h, blockpos);
      return blockpos.func_177984_a();
   }

   private void func_184311_k() {
      Vec3d vec3d = (new Vec3d((double)this.func_174877_v().func_177958_n(), 0.0D, (double)this.func_174877_v().func_177952_p())).func_72432_b();
      Vec3d vec3d1 = vec3d.func_186678_a(1024.0D);

      for(int i = 16; func_184301_a(this.field_145850_b, vec3d1).func_76625_h() > 0 && i-- > 0; vec3d1 = vec3d1.func_178787_e(vec3d.func_186678_a(-16.0D))) {
         field_184314_a.debug("Skipping backwards past nonempty chunk at {}", (Object)vec3d1);
      }

      for(int j = 16; func_184301_a(this.field_145850_b, vec3d1).func_76625_h() == 0 && j-- > 0; vec3d1 = vec3d1.func_178787_e(vec3d.func_186678_a(16.0D))) {
         field_184314_a.debug("Skipping forward past empty chunk at {}", (Object)vec3d1);
      }

      field_184314_a.debug("Found chunk at {}", (Object)vec3d1);
      Chunk chunk = func_184301_a(this.field_145850_b, vec3d1);
      this.field_184317_h = func_184307_a(chunk);
      if (this.field_184317_h == null) {
         this.field_184317_h = new BlockPos(vec3d1.field_72450_a + 0.5D, 75.0D, vec3d1.field_72449_c + 0.5D);
         field_184314_a.debug("Failed to find suitable block, settling on {}", (Object)this.field_184317_h);
         (new WorldGenEndIsland()).func_180709_b(this.field_145850_b, new Random(this.field_184317_h.func_177986_g()), this.field_184317_h);
      } else {
         field_184314_a.debug("Found block at {}", (Object)this.field_184317_h);
      }

      this.field_184317_h = func_184308_a(this.field_145850_b, this.field_184317_h, 16, true);
      field_184314_a.debug("Creating portal at {}", (Object)this.field_184317_h);
      this.field_184317_h = this.field_184317_h.func_177981_b(10);
      this.func_184312_b(this.field_184317_h);
      this.func_70296_d();
   }

   private static BlockPos func_184308_a(World p_184308_0_, BlockPos p_184308_1_, int p_184308_2_, boolean p_184308_3_) {
      BlockPos blockpos = null;

      for(int i = -p_184308_2_; i <= p_184308_2_; ++i) {
         for(int j = -p_184308_2_; j <= p_184308_2_; ++j) {
            if (i != 0 || j != 0 || p_184308_3_) {
               for(int k = 255; k > (blockpos == null ? 0 : blockpos.func_177956_o()); --k) {
                  BlockPos blockpos1 = new BlockPos(p_184308_1_.func_177958_n() + i, k, p_184308_1_.func_177952_p() + j);
                  IBlockState iblockstate = p_184308_0_.func_180495_p(blockpos1);
                  if (iblockstate.func_185898_k() && (p_184308_3_ || iblockstate.func_177230_c() != Blocks.field_150357_h)) {
                     blockpos = blockpos1;
                     break;
                  }
               }
            }
         }
      }

      return blockpos == null ? p_184308_1_ : blockpos;
   }

   private static Chunk func_184301_a(World p_184301_0_, Vec3d p_184301_1_) {
      return p_184301_0_.func_72964_e(MathHelper.func_76128_c(p_184301_1_.field_72450_a / 16.0D), MathHelper.func_76128_c(p_184301_1_.field_72449_c / 16.0D));
   }

   @Nullable
   private static BlockPos func_184307_a(Chunk p_184307_0_) {
      BlockPos blockpos = new BlockPos(p_184307_0_.field_76635_g * 16, 30, p_184307_0_.field_76647_h * 16);
      int i = p_184307_0_.func_76625_h() + 16 - 1;
      BlockPos blockpos1 = new BlockPos(p_184307_0_.field_76635_g * 16 + 16 - 1, i, p_184307_0_.field_76647_h * 16 + 16 - 1);
      BlockPos blockpos2 = null;
      double d0 = 0.0D;

      for(BlockPos blockpos3 : BlockPos.func_177980_a(blockpos, blockpos1)) {
         IBlockState iblockstate = p_184307_0_.func_177435_g(blockpos3);
         if (iblockstate.func_177230_c() == Blocks.field_150377_bs && !p_184307_0_.func_177435_g(blockpos3.func_177981_b(1)).func_185898_k() && !p_184307_0_.func_177435_g(blockpos3.func_177981_b(2)).func_185898_k()) {
            double d1 = blockpos3.func_177957_d(0.0D, 0.0D, 0.0D);
            if (blockpos2 == null || d1 < d0) {
               blockpos2 = blockpos3;
               d0 = d1;
            }
         }
      }

      return blockpos2;
   }

   private void func_184312_b(BlockPos p_184312_1_) {
      (new WorldGenEndGateway()).func_180709_b(this.field_145850_b, new Random(), p_184312_1_);
      TileEntity tileentity = this.field_145850_b.func_175625_s(p_184312_1_);
      if (tileentity instanceof TileEntityEndGateway) {
         TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
         tileentityendgateway.field_184317_h = new BlockPos(this.func_174877_v());
         tileentityendgateway.func_70296_d();
      } else {
         field_184314_a.warn("Couldn't save exit portal at {}", (Object)p_184312_1_);
      }

   }

   public boolean func_184313_a(EnumFacing p_184313_1_) {
      return this.func_145838_q().func_176223_P().func_185894_c(this.field_145850_b, this.func_174877_v(), p_184313_1_);
   }

   public int func_184304_i() {
      int i = 0;

      for(EnumFacing enumfacing : EnumFacing.values()) {
         i += this.func_184313_a(enumfacing) ? 1 : 0;
      }

      return i;
   }

   public void func_190603_b(BlockPos p_190603_1_) {
      this.field_184318_i = true;
      this.field_184317_h = p_190603_1_;
   }
}
