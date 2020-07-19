package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity {
   private boolean field_184259_a;
   private boolean field_184260_f;
   private boolean field_184261_g;
   private boolean field_184262_h;
   private final CommandBlockBaseLogic field_145994_a = new CommandBlockBaseLogic() {
      public BlockPos func_180425_c() {
         return TileEntityCommandBlock.this.field_174879_c;
      }

      public Vec3d func_174791_d() {
         return new Vec3d((double)TileEntityCommandBlock.this.field_174879_c.func_177958_n() + 0.5D, (double)TileEntityCommandBlock.this.field_174879_c.func_177956_o() + 0.5D, (double)TileEntityCommandBlock.this.field_174879_c.func_177952_p() + 0.5D);
      }

      public World func_130014_f_() {
         return TileEntityCommandBlock.this.func_145831_w();
      }

      public void func_145752_a(String p_145752_1_) {
         super.func_145752_a(p_145752_1_);
         TileEntityCommandBlock.this.func_70296_d();
      }

      public void func_145756_e() {
         IBlockState iblockstate = TileEntityCommandBlock.this.field_145850_b.func_180495_p(TileEntityCommandBlock.this.field_174879_c);
         TileEntityCommandBlock.this.func_145831_w().func_184138_a(TileEntityCommandBlock.this.field_174879_c, iblockstate, iblockstate, 3);
      }

      public int func_145751_f() {
         return 0;
      }

      public void func_145757_a(ByteBuf p_145757_1_) {
         p_145757_1_.writeInt(TileEntityCommandBlock.this.field_174879_c.func_177958_n());
         p_145757_1_.writeInt(TileEntityCommandBlock.this.field_174879_c.func_177956_o());
         p_145757_1_.writeInt(TileEntityCommandBlock.this.field_174879_c.func_177952_p());
      }

      public MinecraftServer func_184102_h() {
         return TileEntityCommandBlock.this.field_145850_b.func_73046_m();
      }
   };

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      this.field_145994_a.func_189510_a(p_189515_1_);
      p_189515_1_.func_74757_a("powered", this.func_184255_d());
      p_189515_1_.func_74757_a("conditionMet", this.func_184256_g());
      p_189515_1_.func_74757_a("auto", this.func_184254_e());
      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145994_a.func_145759_b(p_145839_1_);
      this.field_184259_a = p_145839_1_.func_74767_n("powered");
      this.field_184261_g = p_145839_1_.func_74767_n("conditionMet");
      this.func_184253_b(p_145839_1_.func_74767_n("auto"));
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      if (this.func_184257_h()) {
         this.func_184252_d(false);
         NBTTagCompound nbttagcompound = this.func_189515_b(new NBTTagCompound());
         return new SPacketUpdateTileEntity(this.field_174879_c, 2, nbttagcompound);
      } else {
         return null;
      }
   }

   public boolean func_183000_F() {
      return true;
   }

   public CommandBlockBaseLogic func_145993_a() {
      return this.field_145994_a;
   }

   public CommandResultStats func_175124_c() {
      return this.field_145994_a.func_175572_n();
   }

   public void func_184250_a(boolean p_184250_1_) {
      this.field_184259_a = p_184250_1_;
   }

   public boolean func_184255_d() {
      return this.field_184259_a;
   }

   public boolean func_184254_e() {
      return this.field_184260_f;
   }

   public void func_184253_b(boolean p_184253_1_) {
      boolean flag = this.field_184260_f;
      this.field_184260_f = p_184253_1_;
      if (!flag && p_184253_1_ && !this.field_184259_a && this.field_145850_b != null && this.func_184251_i() != TileEntityCommandBlock.Mode.SEQUENCE) {
         Block block = this.func_145838_q();
         if (block instanceof BlockCommandBlock) {
            this.func_184249_c();
            this.field_145850_b.func_175684_a(this.field_174879_c, block, block.func_149738_a(this.field_145850_b));
         }
      }

   }

   public boolean func_184256_g() {
      return this.field_184261_g;
   }

   public boolean func_184249_c() {
      this.field_184261_g = true;
      if (this.func_184258_j()) {
         BlockPos blockpos = this.field_174879_c.func_177972_a(((EnumFacing)this.field_145850_b.func_180495_p(this.field_174879_c).func_177229_b(BlockCommandBlock.field_185564_a)).func_176734_d());
         if (this.field_145850_b.func_180495_p(blockpos).func_177230_c() instanceof BlockCommandBlock) {
            TileEntity tileentity = this.field_145850_b.func_175625_s(blockpos);
            this.field_184261_g = tileentity instanceof TileEntityCommandBlock && ((TileEntityCommandBlock)tileentity).func_145993_a().func_145760_g() > 0;
         } else {
            this.field_184261_g = false;
         }
      }

      return this.field_184261_g;
   }

   public boolean func_184257_h() {
      return this.field_184262_h;
   }

   public void func_184252_d(boolean p_184252_1_) {
      this.field_184262_h = p_184252_1_;
   }

   public TileEntityCommandBlock.Mode func_184251_i() {
      Block block = this.func_145838_q();
      if (block == Blocks.field_150483_bI) {
         return TileEntityCommandBlock.Mode.REDSTONE;
      } else if (block == Blocks.field_185776_dc) {
         return TileEntityCommandBlock.Mode.AUTO;
      } else {
         return block == Blocks.field_185777_dd ? TileEntityCommandBlock.Mode.SEQUENCE : TileEntityCommandBlock.Mode.REDSTONE;
      }
   }

   public boolean func_184258_j() {
      IBlockState iblockstate = this.field_145850_b.func_180495_p(this.func_174877_v());
      return iblockstate.func_177230_c() instanceof BlockCommandBlock ? ((Boolean)iblockstate.func_177229_b(BlockCommandBlock.field_185565_b)).booleanValue() : false;
   }

   public void func_145829_t() {
      this.field_145854_h = null;
      super.func_145829_t();
   }

   public static enum Mode {
      SEQUENCE,
      AUTO,
      REDSTONE;
   }
}
