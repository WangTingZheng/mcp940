package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
   private static final Logger field_145852_a = LogManager.getLogger();
   private static final RegistryNamespaced<ResourceLocation, Class<? extends TileEntity>> field_190562_f = new RegistryNamespaced<ResourceLocation, Class<? extends TileEntity>>();
   protected World field_145850_b;
   protected BlockPos field_174879_c = BlockPos.field_177992_a;
   protected boolean field_145846_f;
   private int field_145847_g = -1;
   protected Block field_145854_h;

   private static void func_190560_a(String p_190560_0_, Class<? extends TileEntity> p_190560_1_) {
      field_190562_f.func_82595_a(new ResourceLocation(p_190560_0_), p_190560_1_);
   }

   @Nullable
   public static ResourceLocation func_190559_a(Class<? extends TileEntity> p_190559_0_) {
      return field_190562_f.func_177774_c(p_190559_0_);
   }

   public World func_145831_w() {
      return this.field_145850_b;
   }

   public void func_145834_a(World p_145834_1_) {
      this.field_145850_b = p_145834_1_;
   }

   public boolean func_145830_o() {
      return this.field_145850_b != null;
   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      this.field_174879_c = new BlockPos(p_145839_1_.func_74762_e("x"), p_145839_1_.func_74762_e("y"), p_145839_1_.func_74762_e("z"));
   }

   public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_) {
      return this.func_189516_d(p_189515_1_);
   }

   private NBTTagCompound func_189516_d(NBTTagCompound p_189516_1_) {
      ResourceLocation resourcelocation = field_190562_f.func_177774_c(this.getClass());
      if (resourcelocation == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         p_189516_1_.func_74778_a("id", resourcelocation.toString());
         p_189516_1_.func_74768_a("x", this.field_174879_c.func_177958_n());
         p_189516_1_.func_74768_a("y", this.field_174879_c.func_177956_o());
         p_189516_1_.func_74768_a("z", this.field_174879_c.func_177952_p());
         return p_189516_1_;
      }
   }

   @Nullable
   public static TileEntity func_190200_a(World p_190200_0_, NBTTagCompound p_190200_1_) {
      TileEntity tileentity = null;
      String s = p_190200_1_.func_74779_i("id");

      try {
         Class<? extends TileEntity> oclass = (Class)field_190562_f.func_82594_a(new ResourceLocation(s));
         if (oclass != null) {
            tileentity = oclass.newInstance();
         }
      } catch (Throwable throwable1) {
         field_145852_a.error("Failed to create block entity {}", s, throwable1);
      }

      if (tileentity != null) {
         try {
            tileentity.func_190201_b(p_190200_0_);
            tileentity.func_145839_a(p_190200_1_);
         } catch (Throwable throwable) {
            field_145852_a.error("Failed to load data for block entity {}", s, throwable);
            tileentity = null;
         }
      } else {
         field_145852_a.warn("Skipping BlockEntity with id {}", (Object)s);
      }

      return tileentity;
   }

   protected void func_190201_b(World p_190201_1_) {
   }

   public int func_145832_p() {
      if (this.field_145847_g == -1) {
         IBlockState iblockstate = this.field_145850_b.func_180495_p(this.field_174879_c);
         this.field_145847_g = iblockstate.func_177230_c().func_176201_c(iblockstate);
      }

      return this.field_145847_g;
   }

   public void func_70296_d() {
      if (this.field_145850_b != null) {
         IBlockState iblockstate = this.field_145850_b.func_180495_p(this.field_174879_c);
         this.field_145847_g = iblockstate.func_177230_c().func_176201_c(iblockstate);
         this.field_145850_b.func_175646_b(this.field_174879_c, this);
         if (this.func_145838_q() != Blocks.field_150350_a) {
            this.field_145850_b.func_175666_e(this.field_174879_c, this.func_145838_q());
         }
      }

   }

   public double func_145835_a(double p_145835_1_, double p_145835_3_, double p_145835_5_) {
      double d0 = (double)this.field_174879_c.func_177958_n() + 0.5D - p_145835_1_;
      double d1 = (double)this.field_174879_c.func_177956_o() + 0.5D - p_145835_3_;
      double d2 = (double)this.field_174879_c.func_177952_p() + 0.5D - p_145835_5_;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double func_145833_n() {
      return 4096.0D;
   }

   public BlockPos func_174877_v() {
      return this.field_174879_c;
   }

   public Block func_145838_q() {
      if (this.field_145854_h == null && this.field_145850_b != null) {
         this.field_145854_h = this.field_145850_b.func_180495_p(this.field_174879_c).func_177230_c();
      }

      return this.field_145854_h;
   }

   @Nullable
   public SPacketUpdateTileEntity func_189518_D_() {
      return null;
   }

   public NBTTagCompound func_189517_E_() {
      return this.func_189516_d(new NBTTagCompound());
   }

   public boolean func_145837_r() {
      return this.field_145846_f;
   }

   public void func_145843_s() {
      this.field_145846_f = true;
   }

   public void func_145829_t() {
      this.field_145846_f = false;
   }

   public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
      return false;
   }

   public void func_145836_u() {
      this.field_145854_h = null;
      this.field_145847_g = -1;
   }

   public void func_145828_a(CrashReportCategory p_145828_1_) {
      p_145828_1_.func_189529_a("Name", new ICrashReportDetail<String>() {
         public String call() throws Exception {
            return TileEntity.field_190562_f.func_177774_c(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
         }
      });
      if (this.field_145850_b != null) {
         CrashReportCategory.func_180523_a(p_145828_1_, this.field_174879_c, this.func_145838_q(), this.func_145832_p());
         p_145828_1_.func_189529_a("Actual block type", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               int i = Block.func_149682_b(TileEntity.this.field_145850_b.func_180495_p(TileEntity.this.field_174879_c).func_177230_c());

               try {
                  return String.format("ID #%d (%s // %s)", i, Block.func_149729_e(i).func_149739_a(), Block.func_149729_e(i).getClass().getCanonicalName());
               } catch (Throwable var3) {
                  return "ID #" + i;
               }
            }
         });
         p_145828_1_.func_189529_a("Actual block data value", new ICrashReportDetail<String>() {
            public String call() throws Exception {
               IBlockState iblockstate = TileEntity.this.field_145850_b.func_180495_p(TileEntity.this.field_174879_c);
               int i = iblockstate.func_177230_c().func_176201_c(iblockstate);
               if (i < 0) {
                  return "Unknown? (Got " + i + ")";
               } else {
                  String s = String.format("%4s", Integer.toBinaryString(i)).replace(" ", "0");
                  return String.format("%1$d / 0x%1$X / 0b%2$s", i, s);
               }
            }
         });
      }
   }

   public void func_174878_a(BlockPos p_174878_1_) {
      this.field_174879_c = p_174878_1_.func_185334_h();
   }

   public boolean func_183000_F() {
      return false;
   }

   @Nullable
   public ITextComponent func_145748_c_() {
      return null;
   }

   public void func_189667_a(Rotation p_189667_1_) {
   }

   public void func_189668_a(Mirror p_189668_1_) {
   }

   static {
      func_190560_a("furnace", TileEntityFurnace.class);
      func_190560_a("chest", TileEntityChest.class);
      func_190560_a("ender_chest", TileEntityEnderChest.class);
      func_190560_a("jukebox", BlockJukebox.TileEntityJukebox.class);
      func_190560_a("dispenser", TileEntityDispenser.class);
      func_190560_a("dropper", TileEntityDropper.class);
      func_190560_a("sign", TileEntitySign.class);
      func_190560_a("mob_spawner", TileEntityMobSpawner.class);
      func_190560_a("noteblock", TileEntityNote.class);
      func_190560_a("piston", TileEntityPiston.class);
      func_190560_a("brewing_stand", TileEntityBrewingStand.class);
      func_190560_a("enchanting_table", TileEntityEnchantmentTable.class);
      func_190560_a("end_portal", TileEntityEndPortal.class);
      func_190560_a("beacon", TileEntityBeacon.class);
      func_190560_a("skull", TileEntitySkull.class);
      func_190560_a("daylight_detector", TileEntityDaylightDetector.class);
      func_190560_a("hopper", TileEntityHopper.class);
      func_190560_a("comparator", TileEntityComparator.class);
      func_190560_a("flower_pot", TileEntityFlowerPot.class);
      func_190560_a("banner", TileEntityBanner.class);
      func_190560_a("structure_block", TileEntityStructure.class);
      func_190560_a("end_gateway", TileEntityEndGateway.class);
      func_190560_a("command_block", TileEntityCommandBlock.class);
      func_190560_a("shulker_box", TileEntityShulkerBox.class);
      func_190560_a("bed", TileEntityBed.class);
   }
}
