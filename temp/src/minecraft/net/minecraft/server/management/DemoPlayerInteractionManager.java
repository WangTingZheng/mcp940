package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DemoPlayerInteractionManager extends PlayerInteractionManager {
   private boolean field_73105_c;
   private boolean field_73103_d;
   private int field_73104_e;
   private int field_73102_f;

   public DemoPlayerInteractionManager(World p_i1513_1_) {
      super(p_i1513_1_);
   }

   public void func_73075_a() {
      super.func_73075_a();
      ++this.field_73102_f;
      long i = this.field_73092_a.func_82737_E();
      long j = i / 24000L + 1L;
      if (!this.field_73105_c && this.field_73102_f > 20) {
         this.field_73105_c = true;
         this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 0.0F));
      }

      this.field_73103_d = i > 120500L;
      if (this.field_73103_d) {
         ++this.field_73104_e;
      }

      if (i % 24000L == 500L) {
         if (j <= 6L) {
            this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.day." + j, new Object[0]));
         }
      } else if (j == 1L) {
         if (i == 100L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 101.0F));
         } else if (i == 175L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 102.0F));
         } else if (i == 250L) {
            this.field_73090_b.field_71135_a.func_147359_a(new SPacketChangeGameState(5, 103.0F));
         }
      } else if (j == 5L && i % 24000L == 22000L) {
         this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.day.warning", new Object[0]));
      }

   }

   private void func_73101_e() {
      if (this.field_73104_e > 100) {
         this.field_73090_b.func_145747_a(new TextComponentTranslation("demo.reminder", new Object[0]));
         this.field_73104_e = 0;
      }

   }

   public void func_180784_a(BlockPos p_180784_1_, EnumFacing p_180784_2_) {
      if (this.field_73103_d) {
         this.func_73101_e();
      } else {
         super.func_180784_a(p_180784_1_, p_180784_2_);
      }
   }

   public void func_180785_a(BlockPos p_180785_1_) {
      if (!this.field_73103_d) {
         super.func_180785_a(p_180785_1_);
      }
   }

   public boolean func_180237_b(BlockPos p_180237_1_) {
      return this.field_73103_d ? false : super.func_180237_b(p_180237_1_);
   }

   public EnumActionResult func_187250_a(EntityPlayer p_187250_1_, World p_187250_2_, ItemStack p_187250_3_, EnumHand p_187250_4_) {
      if (this.field_73103_d) {
         this.func_73101_e();
         return EnumActionResult.PASS;
      } else {
         return super.func_187250_a(p_187250_1_, p_187250_2_, p_187250_3_, p_187250_4_);
      }
   }

   public EnumActionResult func_187251_a(EntityPlayer p_187251_1_, World p_187251_2_, ItemStack p_187251_3_, EnumHand p_187251_4_, BlockPos p_187251_5_, EnumFacing p_187251_6_, float p_187251_7_, float p_187251_8_, float p_187251_9_) {
      if (this.field_73103_d) {
         this.func_73101_e();
         return EnumActionResult.PASS;
      } else {
         return super.func_187251_a(p_187251_1_, p_187251_2_, p_187251_3_, p_187251_4_, p_187251_5_, p_187251_6_, p_187251_7_, p_187251_8_, p_187251_9_);
      }
   }
}
