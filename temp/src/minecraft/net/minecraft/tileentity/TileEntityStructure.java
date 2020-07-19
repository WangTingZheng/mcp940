package net.minecraft.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class TileEntityStructure extends TileEntity {
   private String field_184420_a = "";
   private String field_184421_f = "";
   private String field_184422_g = "";
   private BlockPos field_184423_h = new BlockPos(0, 1, 0);
   private BlockPos field_184424_i = BlockPos.field_177992_a;
   private Mirror field_184425_j = Mirror.NONE;
   private Rotation field_184426_k = Rotation.NONE;
   private TileEntityStructure.Mode field_184427_l = TileEntityStructure.Mode.DATA;
   private boolean field_184428_m = true;
   private boolean field_189727_n;
   private boolean field_189728_o;
   private boolean field_189729_p = true;
   private float field_189730_q = 1.0F;
   private long field_189731_r;

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      super.func_189515_b(p_189515_1_);
      p_189515_1_.func_74778_a("name", this.field_184420_a);
      p_189515_1_.func_74778_a("author", this.field_184421_f);
      p_189515_1_.func_74778_a("metadata", this.field_184422_g);
      p_189515_1_.func_74768_a("posX", this.field_184423_h.func_177958_n());
      p_189515_1_.func_74768_a("posY", this.field_184423_h.func_177956_o());
      p_189515_1_.func_74768_a("posZ", this.field_184423_h.func_177952_p());
      p_189515_1_.func_74768_a("sizeX", this.field_184424_i.func_177958_n());
      p_189515_1_.func_74768_a("sizeY", this.field_184424_i.func_177956_o());
      p_189515_1_.func_74768_a("sizeZ", this.field_184424_i.func_177952_p());
      p_189515_1_.func_74778_a("rotation", this.field_184426_k.toString());
      p_189515_1_.func_74778_a("mirror", this.field_184425_j.toString());
      p_189515_1_.func_74778_a("mode", this.field_184427_l.toString());
      p_189515_1_.func_74757_a("ignoreEntities", this.field_184428_m);
      p_189515_1_.func_74757_a("powered", this.field_189727_n);
      p_189515_1_.func_74757_a("showair", this.field_189728_o);
      p_189515_1_.func_74757_a("showboundingbox", this.field_189729_p);
      p_189515_1_.func_74776_a("integrity", this.field_189730_q);
      p_189515_1_.func_74772_a("seed", this.field_189731_r);
      return p_189515_1_;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.func_184404_a(p_145839_1_.func_74779_i("name"));
      this.field_184421_f = p_145839_1_.func_74779_i("author");
      this.field_184422_g = p_145839_1_.func_74779_i("metadata");
      int i = MathHelper.func_76125_a(p_145839_1_.func_74762_e("posX"), -32, 32);
      int j = MathHelper.func_76125_a(p_145839_1_.func_74762_e("posY"), -32, 32);
      int k = MathHelper.func_76125_a(p_145839_1_.func_74762_e("posZ"), -32, 32);
      this.field_184423_h = new BlockPos(i, j, k);
      int l = MathHelper.func_76125_a(p_145839_1_.func_74762_e("sizeX"), 0, 32);
      int i1 = MathHelper.func_76125_a(p_145839_1_.func_74762_e("sizeY"), 0, 32);
      int j1 = MathHelper.func_76125_a(p_145839_1_.func_74762_e("sizeZ"), 0, 32);
      this.field_184424_i = new BlockPos(l, i1, j1);

      try {
         this.field_184426_k = Rotation.valueOf(p_145839_1_.func_74779_i("rotation"));
      } catch (IllegalArgumentException var11) {
         this.field_184426_k = Rotation.NONE;
      }

      try {
         this.field_184425_j = Mirror.valueOf(p_145839_1_.func_74779_i("mirror"));
      } catch (IllegalArgumentException var10) {
         this.field_184425_j = Mirror.NONE;
      }

      try {
         this.field_184427_l = TileEntityStructure.Mode.valueOf(p_145839_1_.func_74779_i("mode"));
      } catch (IllegalArgumentException var9) {
         this.field_184427_l = TileEntityStructure.Mode.DATA;
      }

      this.field_184428_m = p_145839_1_.func_74767_n("ignoreEntities");
      this.field_189727_n = p_145839_1_.func_74767_n("powered");
      this.field_189728_o = p_145839_1_.func_74767_n("showair");
      this.field_189729_p = p_145839_1_.func_74767_n("showboundingbox");
      if (p_145839_1_.func_74764_b("integrity")) {
         this.field_189730_q = p_145839_1_.func_74760_g("integrity");
      } else {
         this.field_189730_q = 1.0F;
      }

      this.field_189731_r = p_145839_1_.func_74763_f("seed");
      this.func_189704_J();
   }

   private void func_189704_J() {
      if (this.field_145850_b != null) {
         BlockPos blockpos = this.func_174877_v();
         IBlockState iblockstate = this.field_145850_b.func_180495_p(blockpos);
         if (iblockstate.func_177230_c() == Blocks.field_185779_df) {
            this.field_145850_b.func_180501_a(blockpos, iblockstate.func_177226_a(BlockStructure.field_185587_a, this.field_184427_l), 2);
         }

      }
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 7, this.func_189517_E_());
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189515_b(new NBTTagCompound());
   }

   public boolean func_189701_a(EntityPlayer p_189701_1_) {
      if (!p_189701_1_.func_189808_dh()) {
         return false;
      } else {
         if (p_189701_1_.func_130014_f_().field_72995_K) {
            p_189701_1_.func_189807_a(this);
         }

         return true;
      }
   }

   public String func_189715_d() {
      return this.field_184420_a;
   }

   public void func_184404_a(String p_184404_1_) {
      String s = p_184404_1_;

      for(char c0 : ChatAllowedCharacters.field_189861_b) {
         s = s.replace(c0, '_');
      }

      this.field_184420_a = s;
   }

   public void func_189720_a(EntityLivingBase p_189720_1_) {
      if (!StringUtils.func_151246_b(p_189720_1_.func_70005_c_())) {
         this.field_184421_f = p_189720_1_.func_70005_c_();
      }

   }

   public BlockPos func_189711_e() {
      return this.field_184423_h;
   }

   public void func_184414_b(BlockPos p_184414_1_) {
      this.field_184423_h = p_184414_1_;
   }

   public BlockPos func_189717_g() {
      return this.field_184424_i;
   }

   public void func_184409_c(BlockPos p_184409_1_) {
      this.field_184424_i = p_184409_1_;
   }

   public Mirror func_189716_h() {
      return this.field_184425_j;
   }

   public void func_184411_a(Mirror p_184411_1_) {
      this.field_184425_j = p_184411_1_;
   }

   public Rotation func_189726_i() {
      return this.field_184426_k;
   }

   public void func_184408_a(Rotation p_184408_1_) {
      this.field_184426_k = p_184408_1_;
   }

   public String func_189708_j() {
      return this.field_184422_g;
   }

   public void func_184410_b(String p_184410_1_) {
      this.field_184422_g = p_184410_1_;
   }

   public TileEntityStructure.Mode func_189700_k() {
      return this.field_184427_l;
   }

   public void func_184405_a(TileEntityStructure.Mode p_184405_1_) {
      this.field_184427_l = p_184405_1_;
      IBlockState iblockstate = this.field_145850_b.func_180495_p(this.func_174877_v());
      if (iblockstate.func_177230_c() == Blocks.field_185779_df) {
         this.field_145850_b.func_180501_a(this.func_174877_v(), iblockstate.func_177226_a(BlockStructure.field_185587_a, p_184405_1_), 2);
      }

   }

   public void func_189724_l() {
      switch(this.func_189700_k()) {
      case SAVE:
         this.func_184405_a(TileEntityStructure.Mode.LOAD);
         break;
      case LOAD:
         this.func_184405_a(TileEntityStructure.Mode.CORNER);
         break;
      case CORNER:
         this.func_184405_a(TileEntityStructure.Mode.DATA);
         break;
      case DATA:
         this.func_184405_a(TileEntityStructure.Mode.SAVE);
      }

   }

   public boolean func_189713_m() {
      return this.field_184428_m;
   }

   public void func_184406_a(boolean p_184406_1_) {
      this.field_184428_m = p_184406_1_;
   }

   public float func_189702_n() {
      return this.field_189730_q;
   }

   public void func_189718_a(float p_189718_1_) {
      this.field_189730_q = p_189718_1_;
   }

   public long func_189719_o() {
      return this.field_189731_r;
   }

   public void func_189725_a(long p_189725_1_) {
      this.field_189731_r = p_189725_1_;
   }

   public boolean func_184417_l() {
      if (this.field_184427_l != TileEntityStructure.Mode.SAVE) {
         return false;
      } else {
         BlockPos blockpos = this.func_174877_v();
         int i = 80;
         BlockPos blockpos1 = new BlockPos(blockpos.func_177958_n() - 80, 0, blockpos.func_177952_p() - 80);
         BlockPos blockpos2 = new BlockPos(blockpos.func_177958_n() + 80, 255, blockpos.func_177952_p() + 80);
         List<TileEntityStructure> list = this.func_184418_a(blockpos1, blockpos2);
         List<TileEntityStructure> list1 = this.func_184415_a(list);
         if (list1.size() < 1) {
            return false;
         } else {
            StructureBoundingBox structureboundingbox = this.func_184416_a(blockpos, list1);
            if (structureboundingbox.field_78893_d - structureboundingbox.field_78897_a > 1 && structureboundingbox.field_78894_e - structureboundingbox.field_78895_b > 1 && structureboundingbox.field_78892_f - structureboundingbox.field_78896_c > 1) {
               this.field_184423_h = new BlockPos(structureboundingbox.field_78897_a - blockpos.func_177958_n() + 1, structureboundingbox.field_78895_b - blockpos.func_177956_o() + 1, structureboundingbox.field_78896_c - blockpos.func_177952_p() + 1);
               this.field_184424_i = new BlockPos(structureboundingbox.field_78893_d - structureboundingbox.field_78897_a - 1, structureboundingbox.field_78894_e - structureboundingbox.field_78895_b - 1, structureboundingbox.field_78892_f - structureboundingbox.field_78896_c - 1);
               this.func_70296_d();
               IBlockState iblockstate = this.field_145850_b.func_180495_p(blockpos);
               this.field_145850_b.func_184138_a(blockpos, iblockstate, iblockstate, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<TileEntityStructure> func_184415_a(List<TileEntityStructure> p_184415_1_) {
      Iterable<TileEntityStructure> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntityStructure>() {
         public boolean apply(@Nullable TileEntityStructure p_apply_1_) {
            return p_apply_1_.field_184427_l == TileEntityStructure.Mode.CORNER && TileEntityStructure.this.field_184420_a.equals(p_apply_1_.field_184420_a);
         }
      });
      return Lists.newArrayList(iterable);
   }

   private List<TileEntityStructure> func_184418_a(BlockPos p_184418_1_, BlockPos p_184418_2_) {
      List<TileEntityStructure> list = Lists.<TileEntityStructure>newArrayList();

      for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.func_177975_b(p_184418_1_, p_184418_2_)) {
         IBlockState iblockstate = this.field_145850_b.func_180495_p(blockpos$mutableblockpos);
         if (iblockstate.func_177230_c() == Blocks.field_185779_df) {
            TileEntity tileentity = this.field_145850_b.func_175625_s(blockpos$mutableblockpos);
            if (tileentity != null && tileentity instanceof TileEntityStructure) {
               list.add((TileEntityStructure)tileentity);
            }
         }
      }

      return list;
   }

   private StructureBoundingBox func_184416_a(BlockPos p_184416_1_, List<TileEntityStructure> p_184416_2_) {
      StructureBoundingBox structureboundingbox;
      if (p_184416_2_.size() > 1) {
         BlockPos blockpos = ((TileEntityStructure)p_184416_2_.get(0)).func_174877_v();
         structureboundingbox = new StructureBoundingBox(blockpos, blockpos);
      } else {
         structureboundingbox = new StructureBoundingBox(p_184416_1_, p_184416_1_);
      }

      for(TileEntityStructure tileentitystructure : p_184416_2_) {
         BlockPos blockpos1 = tileentitystructure.func_174877_v();
         if (blockpos1.func_177958_n() < structureboundingbox.field_78897_a) {
            structureboundingbox.field_78897_a = blockpos1.func_177958_n();
         } else if (blockpos1.func_177958_n() > structureboundingbox.field_78893_d) {
            structureboundingbox.field_78893_d = blockpos1.func_177958_n();
         }

         if (blockpos1.func_177956_o() < structureboundingbox.field_78895_b) {
            structureboundingbox.field_78895_b = blockpos1.func_177956_o();
         } else if (blockpos1.func_177956_o() > structureboundingbox.field_78894_e) {
            structureboundingbox.field_78894_e = blockpos1.func_177956_o();
         }

         if (blockpos1.func_177952_p() < structureboundingbox.field_78896_c) {
            structureboundingbox.field_78896_c = blockpos1.func_177952_p();
         } else if (blockpos1.func_177952_p() > structureboundingbox.field_78892_f) {
            structureboundingbox.field_78892_f = blockpos1.func_177952_p();
         }
      }

      return structureboundingbox;
   }

   public void func_189705_a(ByteBuf p_189705_1_) {
      p_189705_1_.writeInt(this.field_174879_c.func_177958_n());
      p_189705_1_.writeInt(this.field_174879_c.func_177956_o());
      p_189705_1_.writeInt(this.field_174879_c.func_177952_p());
   }

   public boolean func_184419_m() {
      return this.func_189712_b(true);
   }

   public boolean func_189712_b(boolean p_189712_1_) {
      if (this.field_184427_l == TileEntityStructure.Mode.SAVE && !this.field_145850_b.field_72995_K && !StringUtils.func_151246_b(this.field_184420_a)) {
         BlockPos blockpos = this.func_174877_v().func_177971_a(this.field_184423_h);
         WorldServer worldserver = (WorldServer)this.field_145850_b;
         MinecraftServer minecraftserver = this.field_145850_b.func_73046_m();
         TemplateManager templatemanager = worldserver.func_184163_y();
         Template template = templatemanager.func_186237_a(minecraftserver, new ResourceLocation(this.field_184420_a));
         template.func_186254_a(this.field_145850_b, blockpos, this.field_184424_i, !this.field_184428_m, Blocks.field_189881_dj);
         template.func_186252_a(this.field_184421_f);
         return !p_189712_1_ || templatemanager.func_186238_c(minecraftserver, new ResourceLocation(this.field_184420_a));
      } else {
         return false;
      }
   }

   public boolean func_184412_n() {
      return this.func_189714_c(true);
   }

   public boolean func_189714_c(boolean p_189714_1_) {
      if (this.field_184427_l == TileEntityStructure.Mode.LOAD && !this.field_145850_b.field_72995_K && !StringUtils.func_151246_b(this.field_184420_a)) {
         BlockPos blockpos = this.func_174877_v();
         BlockPos blockpos1 = blockpos.func_177971_a(this.field_184423_h);
         WorldServer worldserver = (WorldServer)this.field_145850_b;
         MinecraftServer minecraftserver = this.field_145850_b.func_73046_m();
         TemplateManager templatemanager = worldserver.func_184163_y();
         Template template = templatemanager.func_189942_b(minecraftserver, new ResourceLocation(this.field_184420_a));
         if (template == null) {
            return false;
         } else {
            if (!StringUtils.func_151246_b(template.func_186261_b())) {
               this.field_184421_f = template.func_186261_b();
            }

            BlockPos blockpos2 = template.func_186259_a();
            boolean flag = this.field_184424_i.equals(blockpos2);
            if (!flag) {
               this.field_184424_i = blockpos2;
               this.func_70296_d();
               IBlockState iblockstate = this.field_145850_b.func_180495_p(blockpos);
               this.field_145850_b.func_184138_a(blockpos, iblockstate, iblockstate, 3);
            }

            if (p_189714_1_ && !flag) {
               return false;
            } else {
               PlacementSettings placementsettings = (new PlacementSettings()).func_186214_a(this.field_184425_j).func_186220_a(this.field_184426_k).func_186222_a(this.field_184428_m).func_186218_a((ChunkPos)null).func_186225_a((Block)null).func_186226_b(false);
               if (this.field_189730_q < 1.0F) {
                  placementsettings.func_189946_a(MathHelper.func_76131_a(this.field_189730_q, 0.0F, 1.0F)).func_189949_a(Long.valueOf(this.field_189731_r));
               }

               template.func_186260_a(this.field_145850_b, blockpos1, placementsettings);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void func_189706_E() {
      WorldServer worldserver = (WorldServer)this.field_145850_b;
      TemplateManager templatemanager = worldserver.func_184163_y();
      templatemanager.func_189941_a(new ResourceLocation(this.field_184420_a));
   }

   public boolean func_189709_F() {
      if (this.field_184427_l == TileEntityStructure.Mode.LOAD && !this.field_145850_b.field_72995_K) {
         WorldServer worldserver = (WorldServer)this.field_145850_b;
         MinecraftServer minecraftserver = this.field_145850_b.func_73046_m();
         TemplateManager templatemanager = worldserver.func_184163_y();
         return templatemanager.func_189942_b(minecraftserver, new ResourceLocation(this.field_184420_a)) != null;
      } else {
         return false;
      }
   }

   public boolean func_189722_G() {
      return this.field_189727_n;
   }

   public void func_189723_d(boolean p_189723_1_) {
      this.field_189727_n = p_189723_1_;
   }

   public boolean func_189707_H() {
      return this.field_189728_o;
   }

   public void func_189703_e(boolean p_189703_1_) {
      this.field_189728_o = p_189703_1_;
   }

   public boolean func_189721_I() {
      return this.field_189729_p;
   }

   public void func_189710_f(boolean p_189710_1_) {
      this.field_189729_p = p_189710_1_;
   }

   @Nullable
   public ITextComponent func_145748_c_() {
      return new TextComponentTranslation("structure_block.hover." + this.field_184427_l.field_185116_f, new Object[]{this.field_184427_l == TileEntityStructure.Mode.DATA ? this.field_184422_g : this.field_184420_a});
   }

   public static enum Mode implements IStringSerializable {
      SAVE("save", 0),
      LOAD("load", 1),
      CORNER("corner", 2),
      DATA("data", 3);

      private static final TileEntityStructure.Mode[] field_185115_e = new TileEntityStructure.Mode[values().length];
      private final String field_185116_f;
      private final int field_185117_g;

      private Mode(String p_i47027_3_, int p_i47027_4_) {
         this.field_185116_f = p_i47027_3_;
         this.field_185117_g = p_i47027_4_;
      }

      public String func_176610_l() {
         return this.field_185116_f;
      }

      public int func_185110_a() {
         return this.field_185117_g;
      }

      public static TileEntityStructure.Mode func_185108_a(int p_185108_0_) {
         return p_185108_0_ >= 0 && p_185108_0_ < field_185115_e.length ? field_185115_e[p_185108_0_] : field_185115_e[0];
      }

      static {
         for(TileEntityStructure.Mode tileentitystructure$mode : values()) {
            field_185115_e[tileentitystructure$mode.func_185110_a()] = tileentitystructure$mode;
         }

      }
   }
}
