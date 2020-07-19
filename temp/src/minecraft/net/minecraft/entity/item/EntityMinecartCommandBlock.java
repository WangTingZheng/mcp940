package net.minecraft.entity.item;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private static final DataParameter<String> field_184273_a = EntityDataManager.<String>func_187226_a(EntityMinecartCommandBlock.class, DataSerializers.field_187194_d);
   private static final DataParameter<ITextComponent> field_184274_b = EntityDataManager.<ITextComponent>func_187226_a(EntityMinecartCommandBlock.class, DataSerializers.field_187195_e);
   private final CommandBlockBaseLogic field_145824_a = new CommandBlockBaseLogic() {
      public void func_145756_e() {
         EntityMinecartCommandBlock.this.func_184212_Q().func_187227_b(EntityMinecartCommandBlock.field_184273_a, this.func_145753_i());
         EntityMinecartCommandBlock.this.func_184212_Q().func_187227_b(EntityMinecartCommandBlock.field_184274_b, this.func_145749_h());
      }

      public int func_145751_f() {
         return 1;
      }

      public void func_145757_a(ByteBuf p_145757_1_) {
         p_145757_1_.writeInt(EntityMinecartCommandBlock.this.func_145782_y());
      }

      public BlockPos func_180425_c() {
         return new BlockPos(EntityMinecartCommandBlock.this.field_70165_t, EntityMinecartCommandBlock.this.field_70163_u + 0.5D, EntityMinecartCommandBlock.this.field_70161_v);
      }

      public Vec3d func_174791_d() {
         return new Vec3d(EntityMinecartCommandBlock.this.field_70165_t, EntityMinecartCommandBlock.this.field_70163_u, EntityMinecartCommandBlock.this.field_70161_v);
      }

      public World func_130014_f_() {
         return EntityMinecartCommandBlock.this.field_70170_p;
      }

      public Entity func_174793_f() {
         return EntityMinecartCommandBlock.this;
      }

      public MinecraftServer func_184102_h() {
         return EntityMinecartCommandBlock.this.field_70170_p.func_73046_m();
      }
   };
   private int field_145823_b;

   public EntityMinecartCommandBlock(World p_i46754_1_) {
      super(p_i46754_1_);
   }

   public EntityMinecartCommandBlock(World p_i46755_1_, double p_i46755_2_, double p_i46755_4_, double p_i46755_6_) {
      super(p_i46755_1_, p_i46755_2_, p_i46755_4_, p_i46755_6_);
   }

   public static void func_189670_a(DataFixer p_189670_0_) {
      EntityMinecart.func_189669_a(p_189670_0_, EntityMinecartCommandBlock.class);
      p_189670_0_.func_188258_a(FixTypes.ENTITY, new IDataWalker() {
         public NBTTagCompound func_188266_a(IDataFixer p_188266_1_, NBTTagCompound p_188266_2_, int p_188266_3_) {
            if (TileEntity.func_190559_a(TileEntityCommandBlock.class).equals(new ResourceLocation(p_188266_2_.func_74779_i("id")))) {
               p_188266_2_.func_74778_a("id", "Control");
               p_188266_1_.func_188251_a(FixTypes.BLOCK_ENTITY, p_188266_2_, p_188266_3_);
               p_188266_2_.func_74778_a("id", "MinecartCommandBlock");
            }

            return p_188266_2_;
         }
      });
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.func_184212_Q().func_187214_a(field_184273_a, "");
      this.func_184212_Q().func_187214_a(field_184274_b, new TextComponentString(""));
   }

   protected void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.field_145824_a.func_145759_b(p_70037_1_);
      this.func_184212_Q().func_187227_b(field_184273_a, this.func_145822_e().func_145753_i());
      this.func_184212_Q().func_187227_b(field_184274_b, this.func_145822_e().func_145749_h());
   }

   protected void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      this.field_145824_a.func_189510_a(p_70014_1_);
   }

   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.COMMAND_BLOCK;
   }

   public IBlockState func_180457_u() {
      return Blocks.field_150483_bI.func_176223_P();
   }

   public CommandBlockBaseLogic func_145822_e() {
      return this.field_145824_a;
   }

   public void func_96095_a(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_) {
      if (p_96095_4_ && this.field_70173_aa - this.field_145823_b >= 4) {
         this.func_145822_e().func_145755_a(this.field_70170_p);
         this.field_145823_b = this.field_70173_aa;
      }

   }

   public boolean func_184230_a(EntityPlayer p_184230_1_, EnumHand p_184230_2_) {
      this.field_145824_a.func_175574_a(p_184230_1_);
      return false;
   }

   public void func_184206_a(DataParameter<?> p_184206_1_) {
      super.func_184206_a(p_184206_1_);
      if (field_184274_b.equals(p_184206_1_)) {
         try {
            this.field_145824_a.func_145750_b((ITextComponent)this.func_184212_Q().func_187225_a(field_184274_b));
         } catch (Throwable var3) {
            ;
         }
      } else if (field_184273_a.equals(p_184206_1_)) {
         this.field_145824_a.func_145752_a((String)this.func_184212_Q().func_187225_a(field_184273_a));
      }

   }

   public boolean func_184213_bq() {
      return true;
   }
}
