package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   public EntityPainting.EnumArt field_70522_e;

   public EntityPainting(World p_i1599_1_) {
      super(p_i1599_1_);
   }

   public EntityPainting(World p_i45849_1_, BlockPos p_i45849_2_, EnumFacing p_i45849_3_) {
      super(p_i45849_1_, p_i45849_2_);
      List<EntityPainting.EnumArt> list = Lists.<EntityPainting.EnumArt>newArrayList();
      int i = 0;

      for(EntityPainting.EnumArt entitypainting$enumart : EntityPainting.EnumArt.values()) {
         this.field_70522_e = entitypainting$enumart;
         this.func_174859_a(p_i45849_3_);
         if (this.func_70518_d()) {
            list.add(entitypainting$enumart);
            int j = entitypainting$enumart.field_75703_B * entitypainting$enumart.field_75704_C;
            if (j > i) {
               i = j;
            }
         }
      }

      if (!list.isEmpty()) {
         Iterator<EntityPainting.EnumArt> iterator = list.iterator();

         while(iterator.hasNext()) {
            EntityPainting.EnumArt entitypainting$enumart1 = iterator.next();
            if (entitypainting$enumart1.field_75703_B * entitypainting$enumart1.field_75704_C < i) {
               iterator.remove();
            }
         }

         this.field_70522_e = list.get(this.field_70146_Z.nextInt(list.size()));
      }

      this.func_174859_a(p_i45849_3_);
   }

   public EntityPainting(World p_i45850_1_, BlockPos p_i45850_2_, EnumFacing p_i45850_3_, String p_i45850_4_) {
      this(p_i45850_1_, p_i45850_2_, p_i45850_3_);

      for(EntityPainting.EnumArt entitypainting$enumart : EntityPainting.EnumArt.values()) {
         if (entitypainting$enumart.field_75702_A.equals(p_i45850_4_)) {
            this.field_70522_e = entitypainting$enumart;
            break;
         }
      }

      this.func_174859_a(p_i45850_3_);
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74778_a("Motive", this.field_70522_e.field_75702_A);
      super.func_70014_b(p_70014_1_);
   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      String s = p_70037_1_.func_74779_i("Motive");

      for(EntityPainting.EnumArt entitypainting$enumart : EntityPainting.EnumArt.values()) {
         if (entitypainting$enumart.field_75702_A.equals(s)) {
            this.field_70522_e = entitypainting$enumart;
         }
      }

      if (this.field_70522_e == null) {
         this.field_70522_e = EntityPainting.EnumArt.KEBAB;
      }

      super.func_70037_a(p_70037_1_);
   }

   public int func_82329_d() {
      return this.field_70522_e.field_75703_B;
   }

   public int func_82330_g() {
      return this.field_70522_e.field_75704_C;
   }

   public void func_110128_b(@Nullable Entity p_110128_1_) {
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         this.func_184185_a(SoundEvents.field_187691_dJ, 1.0F, 1.0F);
         if (p_110128_1_ instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)p_110128_1_;
            if (entityplayer.field_71075_bZ.field_75098_d) {
               return;
            }
         }

         this.func_70099_a(new ItemStack(Items.field_151159_an), 0.0F);
      }
   }

   public void func_184523_o() {
      this.func_184185_a(SoundEvents.field_187694_dK, 1.0F, 1.0F);
   }

   public void func_70012_b(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_) {
      this.func_70107_b(p_70012_1_, p_70012_3_, p_70012_5_);
   }

   public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
      BlockPos blockpos = this.field_174861_a.func_177963_a(p_180426_1_ - this.field_70165_t, p_180426_3_ - this.field_70163_u, p_180426_5_ - this.field_70161_v);
      this.func_70107_b((double)blockpos.func_177958_n(), (double)blockpos.func_177956_o(), (double)blockpos.func_177952_p());
   }

   public static enum EnumArt {
      KEBAB("Kebab", 16, 16, 0, 0),
      AZTEC("Aztec", 16, 16, 16, 0),
      ALBAN("Alban", 16, 16, 32, 0),
      AZTEC_2("Aztec2", 16, 16, 48, 0),
      BOMB("Bomb", 16, 16, 64, 0),
      PLANT("Plant", 16, 16, 80, 0),
      WASTELAND("Wasteland", 16, 16, 96, 0),
      POOL("Pool", 32, 16, 0, 32),
      COURBET("Courbet", 32, 16, 32, 32),
      SEA("Sea", 32, 16, 64, 32),
      SUNSET("Sunset", 32, 16, 96, 32),
      CREEBET("Creebet", 32, 16, 128, 32),
      WANDERER("Wanderer", 16, 32, 0, 64),
      GRAHAM("Graham", 16, 32, 16, 64),
      MATCH("Match", 32, 32, 0, 128),
      BUST("Bust", 32, 32, 32, 128),
      STAGE("Stage", 32, 32, 64, 128),
      VOID("Void", 32, 32, 96, 128),
      SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
      WITHER("Wither", 32, 32, 160, 128),
      FIGHTERS("Fighters", 64, 32, 0, 96),
      POINTER("Pointer", 64, 64, 0, 192),
      PIGSCENE("Pigscene", 64, 64, 64, 192),
      BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
      SKELETON("Skeleton", 64, 48, 192, 64),
      DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);

      public static final int field_180001_A = "SkullAndRoses".length();
      public final String field_75702_A;
      public final int field_75703_B;
      public final int field_75704_C;
      public final int field_75699_D;
      public final int field_75700_E;

      private EnumArt(String p_i1598_3_, int p_i1598_4_, int p_i1598_5_, int p_i1598_6_, int p_i1598_7_) {
         this.field_75702_A = p_i1598_3_;
         this.field_75703_B = p_i1598_4_;
         this.field_75704_C = p_i1598_5_;
         this.field_75699_D = p_i1598_6_;
         this.field_75700_E = p_i1598_7_;
      }
   }
}
