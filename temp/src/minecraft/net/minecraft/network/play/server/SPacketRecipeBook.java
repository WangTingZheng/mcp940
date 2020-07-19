package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketRecipeBook implements Packet<INetHandlerPlayClient> {
   private SPacketRecipeBook.State field_193646_a;
   private List<IRecipe> field_192596_a;
   private List<IRecipe> field_193647_c;
   private boolean field_192598_c;
   private boolean field_192599_d;

   public SPacketRecipeBook() {
   }

   public SPacketRecipeBook(SPacketRecipeBook.State p_i47597_1_, List<IRecipe> p_i47597_2_, List<IRecipe> p_i47597_3_, boolean p_i47597_4_, boolean p_i47597_5_) {
      this.field_193646_a = p_i47597_1_;
      this.field_192596_a = p_i47597_2_;
      this.field_193647_c = p_i47597_3_;
      this.field_192598_c = p_i47597_4_;
      this.field_192599_d = p_i47597_5_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_191980_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_193646_a = (SPacketRecipeBook.State)p_148837_1_.func_179257_a(SPacketRecipeBook.State.class);
      this.field_192598_c = p_148837_1_.readBoolean();
      this.field_192599_d = p_148837_1_.readBoolean();
      int i = p_148837_1_.func_150792_a();
      this.field_192596_a = Lists.<IRecipe>newArrayList();

      for(int j = 0; j < i; ++j) {
         this.field_192596_a.add(CraftingManager.func_193374_a(p_148837_1_.func_150792_a()));
      }

      if (this.field_193646_a == SPacketRecipeBook.State.INIT) {
         i = p_148837_1_.func_150792_a();
         this.field_193647_c = Lists.<IRecipe>newArrayList();

         for(int k = 0; k < i; ++k) {
            this.field_193647_c.add(CraftingManager.func_193374_a(p_148837_1_.func_150792_a()));
         }
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179249_a(this.field_193646_a);
      p_148840_1_.writeBoolean(this.field_192598_c);
      p_148840_1_.writeBoolean(this.field_192599_d);
      p_148840_1_.func_150787_b(this.field_192596_a.size());

      for(IRecipe irecipe : this.field_192596_a) {
         p_148840_1_.func_150787_b(CraftingManager.func_193375_a(irecipe));
      }

      if (this.field_193646_a == SPacketRecipeBook.State.INIT) {
         p_148840_1_.func_150787_b(this.field_193647_c.size());

         for(IRecipe irecipe1 : this.field_193647_c) {
            p_148840_1_.func_150787_b(CraftingManager.func_193375_a(irecipe1));
         }
      }

   }

   public List<IRecipe> func_192595_a() {
      return this.field_192596_a;
   }

   public List<IRecipe> func_193644_b() {
      return this.field_193647_c;
   }

   public boolean func_192593_c() {
      return this.field_192598_c;
   }

   public boolean func_192594_d() {
      return this.field_192599_d;
   }

   public SPacketRecipeBook.State func_194151_e() {
      return this.field_193646_a;
   }

   public static enum State {
      INIT,
      ADD,
      REMOVE;
   }
}
