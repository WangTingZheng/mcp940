package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketCooldown;

public class CooldownTrackerServer extends CooldownTracker {
   private final EntityPlayerMP field_185149_a;

   public CooldownTrackerServer(EntityPlayerMP p_i46741_1_) {
      this.field_185149_a = p_i46741_1_;
   }

   protected void func_185140_b(Item p_185140_1_, int p_185140_2_) {
      super.func_185140_b(p_185140_1_, p_185140_2_);
      this.field_185149_a.field_71135_a.func_147359_a(new SPacketCooldown(p_185140_1_, p_185140_2_));
   }

   protected void func_185146_c(Item p_185146_1_) {
      super.func_185146_c(p_185146_1_);
      this.field_185149_a.field_71135_a.func_147359_a(new SPacketCooldown(p_185146_1_, 0));
   }
}
