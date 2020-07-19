package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements ITickable {
   private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic() {
      public void func_98267_a(int p_98267_1_) {
         TileEntityMobSpawner.this.field_145850_b.func_175641_c(TileEntityMobSpawner.this.field_174879_c, Blocks.field_150474_ac, p_98267_1_, 0);
      }

      public World func_98271_a() {
         return TileEntityMobSpawner.this.field_145850_b;
      }

      public BlockPos func_177221_b() {
         return TileEntityMobSpawner.this.field_174879_c;
      }

      public void func_184993_a(WeightedSpawnerEntity p_184993_1_) {
         super.func_184993_a(p_184993_1_);
         if (this.func_98271_a() != null) {
            IBlockState iblockstate = this.func_98271_a().func_180495_p(this.func_177221_b());
            this.func_98271_a().func_184138_a(TileEntityMobSpawner.this.field_174879_c, iblockstate, iblockstate, 4);
         }

      }
   };

   public static void func_189684_a(DataFixer p_189684_0_) {
      p_189684_0_.func_188258_a(FixTypes.BLOCK_ENTITY, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (TileEntity.func_190559_a(TileEntityMobSpawner.class).equals(new ResourceLocation(p_188266_2_.func_74779_i("id")))) {
               if (p_188266_2_.func_150297_b("SpawnPotentials", 9)) {
                  NBTTagList nbttaglist = p_188266_2_.func_150295_c("SpawnPotentials", 10);

                  for(int i = 0; i < nbttaglist.func_74745_c(); ++i) {
                     NBTTagCompound nbttagcompound = nbttaglist.func_150305_b(i);
                     nbttagcompound.func_74782_a("Entity", p_188266_1_.func_188251_a(FixTypes.ENTITY, nbttagcompound.func_74775_l("Entity"), p_188266_3_));
                  }
               }

               p_188266_2_.func_74782_a("SpawnData", p_188266_1_.func_188251_a(FixTypes.ENTITY, p_188266_2_.func_74775_l("SpawnData"), p_188266_3_));
            }

            return p_188266_2_;
         }
      });
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_145882_a.func_98270_a(p_145839_1_);
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      this.field_145882_a.func_189530_b(p_189515_1_);
      return p_189515_1_;
   }

   public void func_73660_a() {
      this.field_145882_a.func_98278_g();
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 1, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      NBTTagCompound nbttagcompound = this.func_189515_b(new NBTTagCompound());
      nbttagcompound.func_82580_o("SpawnPotentials");
      return nbttagcompound;
   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      return this.field_145882_a.func_98268_b(p_145842_1_) ? true : super.func_145842_c(p_145842_1_, p_145842_2_);
   }

   public boolean func_183000_F() {
      return true;
   }

   public MobSpawnerBaseLogic func_145881_a() {
      return this.field_145882_a;
   }
}
