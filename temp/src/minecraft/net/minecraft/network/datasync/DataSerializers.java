package net.minecraft.network.datasync;

import com.google.common.base.Optional;
import java.io.IOException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers {
   private static final IntIdentityHashBiMap<DataSerializer<?>> field_187204_n = new IntIdentityHashBiMap<DataSerializer<?>>(16);
   public static final DataSerializer<Byte> field_187191_a = new DataSerializer<Byte>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Byte p_187160_2_) {
         p_187160_1_.writeByte(p_187160_2_.byteValue());
      }

      public Byte func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.readByte();
      }

      public DataParameter<Byte> func_187161_a(int p_187161_1_) {
         return new DataParameter<Byte>(p_187161_1_, this);
      }

      public Byte func_192717_a(Byte p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Integer> field_187192_b = new DataSerializer<Integer>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Integer p_187160_2_) {
         p_187160_1_.func_150787_b(p_187160_2_.intValue());
      }

      public Integer func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_150792_a();
      }

      public DataParameter<Integer> func_187161_a(int p_187161_1_) {
         return new DataParameter<Integer>(p_187161_1_, this);
      }

      public Integer func_192717_a(Integer p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Float> field_187193_c = new DataSerializer<Float>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Float p_187160_2_) {
         p_187160_1_.writeFloat(p_187160_2_.floatValue());
      }

      public Float func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.readFloat();
      }

      public DataParameter<Float> func_187161_a(int p_187161_1_) {
         return new DataParameter<Float>(p_187161_1_, this);
      }

      public Float func_192717_a(Float p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<String> field_187194_d = new DataSerializer<String>() {
      public void func_187160_a(PacketBuffer p_187160_1_, String p_187160_2_) {
         p_187160_1_.func_180714_a(p_187160_2_);
      }

      public String func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_150789_c(32767);
      }

      public DataParameter<String> func_187161_a(int p_187161_1_) {
         return new DataParameter<String>(p_187161_1_, this);
      }

      public String func_192717_a(String p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<ITextComponent> field_187195_e = new DataSerializer<ITextComponent>() {
      public void func_187160_a(PacketBuffer p_187160_1_, ITextComponent p_187160_2_) {
         p_187160_1_.func_179256_a(p_187160_2_);
      }

      public ITextComponent func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_179258_d();
      }

      public DataParameter<ITextComponent> func_187161_a(int p_187161_1_) {
         return new DataParameter<ITextComponent>(p_187161_1_, this);
      }

      public ITextComponent func_192717_a(ITextComponent p_192717_1_) {
         return p_192717_1_.func_150259_f();
      }
   };
   public static final DataSerializer<ItemStack> field_187196_f = new DataSerializer<ItemStack>() {
      public void func_187160_a(PacketBuffer p_187160_1_, ItemStack p_187160_2_) {
         p_187160_1_.func_150788_a(p_187160_2_);
      }

      public ItemStack func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_150791_c();
      }

      public DataParameter<ItemStack> func_187161_a(int p_187161_1_) {
         return new DataParameter<ItemStack>(p_187161_1_, this);
      }

      public ItemStack func_192717_a(ItemStack p_192717_1_) {
         return p_192717_1_.func_77946_l();
      }
   };
   public static final DataSerializer<Optional<IBlockState>> field_187197_g = new DataSerializer<Optional<IBlockState>>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Optional<IBlockState> p_187160_2_) {
         if (p_187160_2_.isPresent()) {
            p_187160_1_.func_150787_b(Block.func_176210_f(p_187160_2_.get()));
         } else {
            p_187160_1_.func_150787_b(0);
         }

      }

      public Optional<IBlockState> func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         int i = p_187159_1_.func_150792_a();
         return i == 0 ? Optional.absent() : Optional.of(Block.func_176220_d(i));
      }

      public DataParameter<Optional<IBlockState>> func_187161_a(int p_187161_1_) {
         return new DataParameter<Optional<IBlockState>>(p_187161_1_, this);
      }

      public Optional<IBlockState> func_192717_a(Optional<IBlockState> p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Boolean> field_187198_h = new DataSerializer<Boolean>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Boolean p_187160_2_) {
         p_187160_1_.writeBoolean(p_187160_2_.booleanValue());
      }

      public Boolean func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.readBoolean();
      }

      public DataParameter<Boolean> func_187161_a(int p_187161_1_) {
         return new DataParameter<Boolean>(p_187161_1_, this);
      }

      public Boolean func_192717_a(Boolean p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Rotations> field_187199_i = new DataSerializer<Rotations>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Rotations p_187160_2_) {
         p_187160_1_.writeFloat(p_187160_2_.func_179415_b());
         p_187160_1_.writeFloat(p_187160_2_.func_179416_c());
         p_187160_1_.writeFloat(p_187160_2_.func_179413_d());
      }

      public Rotations func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return new Rotations(p_187159_1_.readFloat(), p_187159_1_.readFloat(), p_187159_1_.readFloat());
      }

      public DataParameter<Rotations> func_187161_a(int p_187161_1_) {
         return new DataParameter<Rotations>(p_187161_1_, this);
      }

      public Rotations func_192717_a(Rotations p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<BlockPos> field_187200_j = new DataSerializer<BlockPos>() {
      public void func_187160_a(PacketBuffer p_187160_1_, BlockPos p_187160_2_) {
         p_187160_1_.func_179255_a(p_187160_2_);
      }

      public BlockPos func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_179259_c();
      }

      public DataParameter<BlockPos> func_187161_a(int p_187161_1_) {
         return new DataParameter<BlockPos>(p_187161_1_, this);
      }

      public BlockPos func_192717_a(BlockPos p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Optional<BlockPos>> field_187201_k = new DataSerializer<Optional<BlockPos>>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Optional<BlockPos> p_187160_2_) {
         p_187160_1_.writeBoolean(p_187160_2_.isPresent());
         if (p_187160_2_.isPresent()) {
            p_187160_1_.func_179255_a(p_187160_2_.get());
         }

      }

      public Optional<BlockPos> func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return !p_187159_1_.readBoolean() ? Optional.absent() : Optional.of(p_187159_1_.func_179259_c());
      }

      public DataParameter<Optional<BlockPos>> func_187161_a(int p_187161_1_) {
         return new DataParameter<Optional<BlockPos>>(p_187161_1_, this);
      }

      public Optional<BlockPos> func_192717_a(Optional<BlockPos> p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<EnumFacing> field_187202_l = new DataSerializer<EnumFacing>() {
      public void func_187160_a(PacketBuffer p_187160_1_, EnumFacing p_187160_2_) {
         p_187160_1_.func_179249_a(p_187160_2_);
      }

      public EnumFacing func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return (EnumFacing)p_187159_1_.func_179257_a(EnumFacing.class);
      }

      public DataParameter<EnumFacing> func_187161_a(int p_187161_1_) {
         return new DataParameter<EnumFacing>(p_187161_1_, this);
      }

      public EnumFacing func_192717_a(EnumFacing p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<Optional<UUID>> field_187203_m = new DataSerializer<Optional<UUID>>() {
      public void func_187160_a(PacketBuffer p_187160_1_, Optional<UUID> p_187160_2_) {
         p_187160_1_.writeBoolean(p_187160_2_.isPresent());
         if (p_187160_2_.isPresent()) {
            p_187160_1_.func_179252_a(p_187160_2_.get());
         }

      }

      public Optional<UUID> func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return !p_187159_1_.readBoolean() ? Optional.absent() : Optional.of(p_187159_1_.func_179253_g());
      }

      public DataParameter<Optional<UUID>> func_187161_a(int p_187161_1_) {
         return new DataParameter<Optional<UUID>>(p_187161_1_, this);
      }

      public Optional<UUID> func_192717_a(Optional<UUID> p_192717_1_) {
         return p_192717_1_;
      }
   };
   public static final DataSerializer<NBTTagCompound> field_192734_n = new DataSerializer<NBTTagCompound>() {
      public void func_187160_a(PacketBuffer p_187160_1_, NBTTagCompound p_187160_2_) {
         p_187160_1_.func_150786_a(p_187160_2_);
      }

      public NBTTagCompound func_187159_a(PacketBuffer p_187159_1_) throws IOException {
         return p_187159_1_.func_150793_b();
      }

      public DataParameter<NBTTagCompound> func_187161_a(int p_187161_1_) {
         return new DataParameter<NBTTagCompound>(p_187161_1_, this);
      }

      public NBTTagCompound func_192717_a(NBTTagCompound p_192717_1_) {
         return p_192717_1_.func_74737_b();
      }
   };

   public static void func_187189_a(DataSerializer<?> p_187189_0_) {
      field_187204_n.func_186808_c(p_187189_0_);
   }

   @Nullable
   public static DataSerializer<?> func_187190_a(int p_187190_0_) {
      return (DataSerializer)field_187204_n.func_186813_a(p_187190_0_);
   }

   public static int func_187188_b(DataSerializer<?> p_187188_0_) {
      return field_187204_n.func_186815_a(p_187188_0_);
   }

   static {
      func_187189_a(field_187191_a);
      func_187189_a(field_187192_b);
      func_187189_a(field_187193_c);
      func_187189_a(field_187194_d);
      func_187189_a(field_187195_e);
      func_187189_a(field_187196_f);
      func_187189_a(field_187198_h);
      func_187189_a(field_187199_i);
      func_187189_a(field_187200_j);
      func_187189_a(field_187201_k);
      func_187189_a(field_187202_l);
      func_187189_a(field_187203_m);
      func_187189_a(field_187197_g);
      func_187189_a(field_192734_n);
   }
}
