package net.minecraft.block;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class BlockFlower extends BlockBush {
   protected PropertyEnum<BlockFlower.EnumFlowerType> field_176496_a;

   protected BlockFlower() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(this.func_176494_l(), this.func_176495_j() == BlockFlower.EnumFlowerColor.RED ? BlockFlower.EnumFlowerType.POPPY : BlockFlower.EnumFlowerType.DANDELION));
   }

   public AxisAlignedBB func_185496_a(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
      return super.func_185496_a(p_185496_1_, p_185496_2_, p_185496_3_).func_191194_a(p_185496_1_.func_191059_e(p_185496_2_, p_185496_3_));
   }

   public int func_180651_a(IBlockState p_180651_1_) {
      return ((BlockFlower.EnumFlowerType)p_180651_1_.func_177229_b(this.func_176494_l())).func_176968_b();
   }

   public void func_149666_a(CreativeTabs p_149666_1_, NonNullList<ItemStack> p_149666_2_) {
      for(BlockFlower.EnumFlowerType blockflower$enumflowertype : BlockFlower.EnumFlowerType.func_176966_a(this.func_176495_j())) {
         p_149666_2_.add(new ItemStack(this, 1, blockflower$enumflowertype.func_176968_b()));
      }

   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(this.func_176494_l(), BlockFlower.EnumFlowerType.func_176967_a(this.func_176495_j(), p_176203_1_));
   }

   public abstract BlockFlower.EnumFlowerColor func_176495_j();

   public IProperty<BlockFlower.EnumFlowerType> func_176494_l() {
      if (this.field_176496_a == null) {
         this.field_176496_a = PropertyEnum.<BlockFlower.EnumFlowerType>func_177708_a("type", BlockFlower.EnumFlowerType.class, new Predicate<BlockFlower.EnumFlowerType>() {
            public boolean apply(@Nullable BlockFlower.EnumFlowerType p_apply_1_) {
               return p_apply_1_.func_176964_a() == BlockFlower.this.func_176495_j();
            }
         });
      }

      return this.field_176496_a;
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((BlockFlower.EnumFlowerType)p_176201_1_.func_177229_b(this.func_176494_l())).func_176968_b();
   }

   protected BlockStateContainer func_180661_e() {
      return new BlockStateContainer(this, new IProperty[]{this.func_176494_l()});
   }

   public Block.EnumOffsetType func_176218_Q() {
      return Block.EnumOffsetType.XZ;
   }

   public static enum EnumFlowerColor {
      YELLOW,
      RED;

      public BlockFlower func_180346_a() {
         return this == YELLOW ? Blocks.field_150327_N : Blocks.field_150328_O;
      }
   }

   public static enum EnumFlowerType implements IStringSerializable {
      DANDELION(BlockFlower.EnumFlowerColor.YELLOW, 0, "dandelion"),
      POPPY(BlockFlower.EnumFlowerColor.RED, 0, "poppy"),
      BLUE_ORCHID(BlockFlower.EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"),
      ALLIUM(BlockFlower.EnumFlowerColor.RED, 2, "allium"),
      HOUSTONIA(BlockFlower.EnumFlowerColor.RED, 3, "houstonia"),
      RED_TULIP(BlockFlower.EnumFlowerColor.RED, 4, "red_tulip", "tulipRed"),
      ORANGE_TULIP(BlockFlower.EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"),
      WHITE_TULIP(BlockFlower.EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"),
      PINK_TULIP(BlockFlower.EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"),
      OXEYE_DAISY(BlockFlower.EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");

      private static final BlockFlower.EnumFlowerType[][] field_176981_k = new BlockFlower.EnumFlowerType[BlockFlower.EnumFlowerColor.values().length][];
      private final BlockFlower.EnumFlowerColor field_176978_l;
      private final int field_176979_m;
      private final String field_176976_n;
      private final String field_176977_o;

      private EnumFlowerType(BlockFlower.EnumFlowerColor p_i45718_3_, int p_i45718_4_, String p_i45718_5_) {
         this(p_i45718_3_, p_i45718_4_, p_i45718_5_, p_i45718_5_);
      }

      private EnumFlowerType(BlockFlower.EnumFlowerColor p_i45719_3_, int p_i45719_4_, String p_i45719_5_, String p_i45719_6_) {
         this.field_176978_l = p_i45719_3_;
         this.field_176979_m = p_i45719_4_;
         this.field_176976_n = p_i45719_5_;
         this.field_176977_o = p_i45719_6_;
      }

      public BlockFlower.EnumFlowerColor func_176964_a() {
         return this.field_176978_l;
      }

      public int func_176968_b() {
         return this.field_176979_m;
      }

      public static BlockFlower.EnumFlowerType func_176967_a(BlockFlower.EnumFlowerColor p_176967_0_, int p_176967_1_) {
         BlockFlower.EnumFlowerType[] ablockflower$enumflowertype = field_176981_k[p_176967_0_.ordinal()];
         if (p_176967_1_ < 0 || p_176967_1_ >= ablockflower$enumflowertype.length) {
            p_176967_1_ = 0;
         }

         return ablockflower$enumflowertype[p_176967_1_];
      }

      public static BlockFlower.EnumFlowerType[] func_176966_a(BlockFlower.EnumFlowerColor p_176966_0_) {
         return field_176981_k[p_176966_0_.ordinal()];
      }

      public String toString() {
         return this.field_176976_n;
      }

      public String func_176610_l() {
         return this.field_176976_n;
      }

      public String func_176963_d() {
         return this.field_176977_o;
      }

      static {
         for(final BlockFlower.EnumFlowerColor blockflower$enumflowercolor : BlockFlower.EnumFlowerColor.values()) {
            Collection<BlockFlower.EnumFlowerType> collection = Collections2.<BlockFlower.EnumFlowerType>filter(Lists.newArrayList(values()), new Predicate<BlockFlower.EnumFlowerType>() {
               public boolean apply(@Nullable BlockFlower.EnumFlowerType p_apply_1_) {
                  return p_apply_1_.func_176964_a() == blockflower$enumflowercolor;
               }
            });
            field_176981_k[blockflower$enumflowercolor.ordinal()] = (BlockFlower.EnumFlowerType[])collection.toArray(new BlockFlower.EnumFlowerType[collection.size()]);
         }

      }
   }
}
