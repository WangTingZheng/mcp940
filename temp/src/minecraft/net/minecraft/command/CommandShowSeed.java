package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {
   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return p_184882_1_.func_71264_H() || super.func_184882_a(p_184882_1_, p_184882_2_);
   }

   public String func_71517_b() {
      return "seed";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.seed.usage";
   }

   public void func_184881_a(MinecraftServer p_184881_1_, ICommandSender p_184881_2_, String[] p_184881_3_) throws CommandException {
      World world = (World)(p_184881_2_ instanceof EntityPlayer ? ((EntityPlayer)p_184881_2_).field_70170_p : p_184881_1_.func_71218_a(0));
      p_184881_2_.func_145747_a(new TextComponentTranslation("commands.seed.success", new Object[]{world.func_72905_C()}));
   }
}
