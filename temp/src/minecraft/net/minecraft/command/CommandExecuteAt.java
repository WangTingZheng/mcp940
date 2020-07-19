package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CommandExecuteAt extends CommandBase {
   public String func_71517_b() {
      return "execute";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.execute.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      if (p_184881_3_.length < 5) {
         throw new WrongUsageException("commands.execute.usage", new Object[0]);
      } else {
         Entity entity = func_184884_a(p_184881_1_, p_184881_2_, p_184881_3_[0], Entity.class);
         double d0 = func_175761_b(entity.field_70165_t, p_184881_3_[1], false);
         double d1 = func_175761_b(entity.field_70163_u, p_184881_3_[2], false);
         double d2 = func_175761_b(entity.field_70161_v, p_184881_3_[3], false);
         new BlockPos(d0, d1, d2);
         int i = 4;
         if ("detect".equals(p_184881_3_[4]) && p_184881_3_.length > 10) {
            World world = entity.func_130014_f_();
            double d3 = func_175761_b(d0, p_184881_3_[5], false);
            double d4 = func_175761_b(d1, p_184881_3_[6], false);
            double d5 = func_175761_b(d2, p_184881_3_[7], false);
            Block block = func_147180_g(p_184881_2_, p_184881_3_[8]);
            BlockPos blockpos = new BlockPos(d3, d4, d5);
            if (!world.func_175667_e(blockpos)) {
               throw new CommandException("commands.execute.failed", new Object[]{"detect", entity.func_70005_c_()});
            }

            IBlockState iblockstate = world.func_180495_p(blockpos);
            if (iblockstate.func_177230_c() != block) {
               throw new CommandException("commands.execute.failed", new Object[]{"detect", entity.func_70005_c_()});
            }

            if (!CommandBase.func_190791_b(block, p_184881_3_[9]).apply(iblockstate)) {
               throw new CommandException("commands.execute.failed", new Object[]{"detect", entity.func_70005_c_()});
            }

            i = 10;
         }

         String s = func_180529_a(p_184881_3_, i);
         ICommandSender icommandsender = CommandSenderWrapper.func_193998_a(p_184881_2_).func_193997_a(entity, new Vec3d(d0, d1, d2)).func_194001_a(p_184881_1_.field_71305_c[0].func_82736_K().func_82766_b("commandBlockOutput"));
         ICommandManager icommandmanager = p_184881_1_.func_71187_D();

         try {
            int j = icommandmanager.func_71556_a(icommandsender, s);
            if (j < 1) {
               throw new CommandException("commands.execute.allInvocationsFailed", new Object[]{s});
            }
         } catch (Throwable var23) {
            throw new CommandException("commands.execute.failed", new Object[]{s, entity.func_70005_c_()});
         }
      }
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      if (p_184883_3_.length == 1) {
         return func_71530_a(p_184883_3_, p_184883_1_.func_71213_z());
      } else if (p_184883_3_.length > 1 && p_184883_3_.length <= 4) {
         return func_175771_a(p_184883_3_, 1, p_184883_4_);
      } else if (p_184883_3_.length > 5 && p_184883_3_.length <= 8 && "detect".equals(p_184883_3_[4])) {
         return func_175771_a(p_184883_3_, 5, p_184883_4_);
      } else {
         return p_184883_3_.length == 9 && "detect".equals(p_184883_3_[4]) ? func_175762_a(p_184883_3_, Block.field_149771_c.func_148742_b()) : Collections.emptyList();
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
