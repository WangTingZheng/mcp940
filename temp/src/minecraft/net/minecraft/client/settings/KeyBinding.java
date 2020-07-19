package net.minecraft.client.settings;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;
import org.lwjgl.input.Keyboard;

public class KeyBinding implements Comparable<KeyBinding> {
   private static final Map<String, KeyBinding> field_74516_a = Maps.<String, KeyBinding>newHashMap();
   private static final IntHashMap<KeyBinding> field_74514_b = new IntHashMap<KeyBinding>();
   private static final Set<String> field_151473_c = Sets.<String>newHashSet();
   private static final Map<String, Integer> field_193627_d = Maps.<String, Integer>newHashMap();
   private final String field_74515_c;
   private final int field_151472_e;
   private final String field_151471_f;
   private int field_74512_d;
   private boolean field_74513_e;
   private int field_151474_i;

   public static void func_74507_a(int p_74507_0_) {
      if (p_74507_0_ != 0) {
         KeyBinding keybinding = field_74514_b.func_76041_a(p_74507_0_);
         if (keybinding != null) {
            ++keybinding.field_151474_i;
         }

      }
   }

   public static void func_74510_a(int p_74510_0_, boolean p_74510_1_) {
      if (p_74510_0_ != 0) {
         KeyBinding keybinding = field_74514_b.func_76041_a(p_74510_0_);
         if (keybinding != null) {
            keybinding.field_74513_e = p_74510_1_;
         }

      }
   }

   public static void func_186704_a() {
      for(KeyBinding keybinding : field_74516_a.values()) {
         try {
            func_74510_a(keybinding.field_74512_d, keybinding.field_74512_d < 256 && Keyboard.isKeyDown(keybinding.field_74512_d));
         } catch (IndexOutOfBoundsException var3) {
            ;
         }
      }

   }

   public static void func_74506_a() {
      for(KeyBinding keybinding : field_74516_a.values()) {
         keybinding.func_74505_d();
      }

   }

   public static void func_74508_b() {
      field_74514_b.func_76046_c();

      for(KeyBinding keybinding : field_74516_a.values()) {
         field_74514_b.func_76038_a(keybinding.field_74512_d, keybinding);
      }

   }

   public static Set<String> func_151467_c() {
      return field_151473_c;
   }

   public KeyBinding(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_) {
      this.field_74515_c = p_i45001_1_;
      this.field_74512_d = p_i45001_2_;
      this.field_151472_e = p_i45001_2_;
      this.field_151471_f = p_i45001_3_;
      field_74516_a.put(p_i45001_1_, this);
      field_74514_b.func_76038_a(p_i45001_2_, this);
      field_151473_c.add(p_i45001_3_);
   }

   public boolean func_151470_d() {
      return this.field_74513_e;
   }

   public String func_151466_e() {
      return this.field_151471_f;
   }

   public boolean func_151468_f() {
      if (this.field_151474_i == 0) {
         return false;
      } else {
         --this.field_151474_i;
         return true;
      }
   }

   private void func_74505_d() {
      this.field_151474_i = 0;
      this.field_74513_e = false;
   }

   public String func_151464_g() {
      return this.field_74515_c;
   }

   public int func_151469_h() {
      return this.field_151472_e;
   }

   public int func_151463_i() {
      return this.field_74512_d;
   }

   public void func_151462_b(int p_151462_1_) {
      this.field_74512_d = p_151462_1_;
   }

   public int compareTo(KeyBinding p_compareTo_1_) {
      return this.field_151471_f.equals(p_compareTo_1_.field_151471_f) ? I18n.func_135052_a(this.field_74515_c).compareTo(I18n.func_135052_a(p_compareTo_1_.field_74515_c)) : ((Integer)field_193627_d.get(this.field_151471_f)).compareTo(field_193627_d.get(p_compareTo_1_.field_151471_f));
   }

   public static Supplier<String> func_193626_b(String p_193626_0_) {
      KeyBinding keybinding = field_74516_a.get(p_193626_0_);
      return keybinding == null ? () -> {
         return p_193624_0_;
      } : () -> {
         return GameSettings.func_74298_c(p_193625_0_.func_151463_i());
      };
   }

   static {
      field_193627_d.put("key.categories.movement", Integer.valueOf(1));
      field_193627_d.put("key.categories.gameplay", Integer.valueOf(2));
      field_193627_d.put("key.categories.inventory", Integer.valueOf(3));
      field_193627_d.put("key.categories.creative", Integer.valueOf(4));
      field_193627_d.put("key.categories.multiplayer", Integer.valueOf(5));
      field_193627_d.put("key.categories.ui", Integer.valueOf(6));
      field_193627_d.put("key.categories.misc", Integer.valueOf(7));
   }
}
