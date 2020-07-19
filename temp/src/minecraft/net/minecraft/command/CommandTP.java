package net.minecraft.command;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CommandTP extends CommandBase {
   public String func_71517_b() {
      return "tp";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.tp.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 1) {
         throw new WrongUsageException("commands.tp.usage", new Object[0]);
      } else {
         int i = 0;
         Entity entity;
         if (p_184881_3_.length != 2 && p_184881_3_.length != 4 && p_184881_3_.length != 6) {
            entity = func_71521_c(p_184881_2_);
         } else {
            entity = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[0]);
            i = 1;
         }

         if (p_184881_3_.length != 1 && p_184881_3_.length != 2) {
            if (p_184881_3_.length < i + 3) {
               throw new WrongUsageException("commands.tp.usage", new Object[0]);
            } else if (entity.field_70170_p != null) {
               int j = 4096;
               int k = i + 1;
               CommandBase.CoordinateArg commandbase$coordinatearg = func_175770_a(entity.field_70165_t, p_184881_3_[i], true);
               CommandBase.CoordinateArg commandbase$coordinatearg1 = func_175767_a(entity.field_70163_u, p_184881_3_[k++], -4096, 4096, false);
               CommandBase.CoordinateArg commandbase$coordinatearg2 = func_175770_a(entity.field_70161_v, p_184881_3_[k++], true);
               CommandBase.CoordinateArg commandbase$coordinatearg3 = func_175770_a((double)entity.field_70177_z, p_184881_3_.length > k ? p_184881_3_[k++] : "~", false);
               CommandBase.CoordinateArg commandbase$coordinatearg4 = func_175770_a((double)entity.field_70125_A, p_184881_3_.length > k ? p_184881_3_[k] : "~", false);
               func_189863_a(entity, commandbase$coordinatearg, commandbase$coordinatearg1, commandbase$coordinatearg2, commandbase$coordinatearg3, commandbase$coordinatearg4);
               func_152373_a(p_184881_2_, this, "commands.tp.success.coordinates", new Object[]{entity.func_70005_c_(), commandbase$coordinatearg.func_179628_a(), commandbase$coordinatearg1.func_179628_a(), commandbase$coordinatearg2.func_179628_a()});
            }
         } else {
            Entity entity1 = func_184885_b(p_184881_1_, p_184881_2_, p_184881_3_[p_184881_3_.length - 1]);
            if (entity1.field_70170_p != entity.field_70170_p) {
               throw new CommandException("commands.tp.notSameDimension", new Object[0]);
            } else {
               entity.func_184210_p();
               if (entity instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)entity).field_71135_a.func_147364_a(entity1.field_70165_t, entity1.field_70163_u, entity1.field_70161_v, entity1.field_70177_z, entity1.field_70125_A);
               } else {
                  entity.func_70012_b(entity1.field_70165_t, entity1.field_70163_u, entity1.field_70161_v, entity1.field_70177_z, entity1.field_70125_A);
               }

               func_152373_a(p_184881_2_, this, "commands.tp.success", new Object[]{entity.func_70005_c_(), entity1.func_70005_c_()});
            }
         }
      }
   }

   private static void func_189863_a(Entity p_189863_0_, CommandBase.CoordinateArg p_189863_1_, CommandBase.CoordinateArg p_189863_2_, CommandBase.CoordinateArg p_189863_3_, CommandBase.CoordinateArg p_189863_4_, CommandBase.CoordinateArg p_189863_5_) {
      if (p_189863_0_ instanceof EntityPlayerMP) {
         Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);
         if (p_189863_1_.func_179630_c()) {
            set.add(SPacketPlayerPosLook.EnumFlags.X);
         }

         if (p_189863_2_.func_179630_c()) {
            set.add(SPacketPlayerPosLook.EnumFlags.Y);
         }

         if (p_189863_3_.func_179630_c()) {
            set.add(SPacketPlayerPosLook.EnumFlags.Z);
         }

         if (p_189863_5_.func_179630_c()) {
            set.add(SPacketPlayerPosLook.EnumFlags.X_ROT);
         }

         if (p_189863_4_.func_179630_c()) {
            set.add(SPacketPlayerPosLook.EnumFlags.Y_ROT);
         }

         float f = (float)p_189863_4_.func_179629_b();
         if (!p_189863_4_.func_179630_c()) {
            f = MathHelper.func_76142_g(f);
         }

         float f1 = (float)p_189863_5_.func_179629_b();
         if (!p_189863_5_.func_179630_c()) {
            f1 = MathHelper.func_76142_g(f1);
         }

         p_189863_0_.func_184210_p();
         ((EntityPlayerMP)p_189863_0_).field_71135_a.func_175089_a(p_189863_1_.func_179629_b(), p_189863_2_.func_179629_b(), p_189863_3_.func_179629_b(), f, f1, set);
         p_189863_0_.func_70034_d(f);
      } else {
         float f2 = (float)MathHelper.func_76138_g(p_189863_4_.func_179628_a());
         float f3 = (float)MathHelper.func_76138_g(p_189863_5_.func_179628_a());
         f3 = MathHelper.func_76131_a(f3, -90.0F, 90.0F);
         p_189863_0_.func_70012_b(p_189863_1_.func_179628_a(), p_189863_2_.func_179628_a(), p_189863_3_.func_179628_a(), f2, f3);
         p_189863_0_.func_70034_d(f2);
      }

      if (!(p_189863_0_ instanceof EntityLivingBase) || !((EntityLivingBase)p_189863_0_).func_184613_cA()) {
         p_189863_0_.field_70181_x = 0.0D;
         p_189863_0_.field_70122_E = true;
      }

   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return p_184883_3_.length != 1 && p_184883_3_.length != 2 ? Collections.emptyList() : func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
