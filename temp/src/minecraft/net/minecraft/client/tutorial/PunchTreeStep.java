package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class PunchTreeStep implements ITutorialStep {
   private static final Set<Block> field_193274_a = Sets.newHashSet(Blocks.field_150364_r, Blocks.field_150363_s);
   private static final ITextComponent field_193275_b = new TextComponentTranslation("tutorial.punch_tree.title", new Object[0]);
   private static final ITextComponent field_193276_c = new TextComponentTranslation("tutorial.punch_tree.description", new Object[]{Tutorial.func_193291_a("attack")});
   private final Tutorial field_193277_d;
   private TutorialToast field_193278_e;
   private int field_193279_f;
   private int field_193280_g;

   public PunchTreeStep(Tutorial p_i47579_1_) {
      this.field_193277_d = p_i47579_1_;
   }

   public void func_193245_a() {
      ++this.field_193279_f;
      if (this.field_193277_d.func_194072_f() != GameType.SURVIVAL) {
         this.field_193277_d.func_193292_a(TutorialSteps.NONE);
      } else {
         if (this.field_193279_f == 1) {
            EntityPlayerSP entityplayersp = this.field_193277_d.func_193295_e().field_71439_g;
            if (entityplayersp != null) {
               for(Block block : field_193274_a) {
                  if (entityplayersp.field_71071_by.func_70431_c(new ItemStack(block))) {
                     this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
                     return;
                  }
               }

               if (FindTreeStep.func_194070_a(entityplayersp)) {
                  this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if ((this.field_193279_f >= 600 || this.field_193280_g > 3) && this.field_193278_e == null) {
            this.field_193278_e = new TutorialToast(TutorialToast.Icons.TREE, field_193275_b, field_193276_c, true);
            this.field_193277_d.func_193295_e().func_193033_an().func_192988_a(this.field_193278_e);
         }

      }
   }

   public void func_193248_b() {
      if (this.field_193278_e != null) {
         this.field_193278_e.func_193670_a();
         this.field_193278_e = null;
      }

   }

   public void func_193250_a(WorldClient p_193250_1_, BlockPos p_193250_2_, IBlockState p_193250_3_, float p_193250_4_) {
      boolean flag = field_193274_a.contains(p_193250_3_.func_177230_c());
      if (flag && p_193250_4_ > 0.0F) {
         if (this.field_193278_e != null) {
            this.field_193278_e.func_193669_a(p_193250_4_);
         }

         if (p_193250_4_ >= 1.0F) {
            this.field_193277_d.func_193292_a(TutorialSteps.OPEN_INVENTORY);
         }
      } else if (this.field_193278_e != null) {
         this.field_193278_e.func_193669_a(0.0F);
      } else if (flag) {
         ++this.field_193280_g;
      }

   }

   public void func_193252_a(ItemStack p_193252_1_) {
      for(Block block : field_193274_a) {
         if (p_193252_1_.func_77973_b() == Item.func_150898_a(block)) {
            this.field_193277_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
            return;
         }
      }

   }
}
