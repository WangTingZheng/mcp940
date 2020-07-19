package net.minecraft.realms;

import java.lang.reflect.Constructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBridge extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private GuiScreen previousScreen;

   public void switchToRealms(GuiScreen p_switchToRealms_1_) {
      this.previousScreen = p_switchToRealms_1_;

      try {
         Class<?> oclass = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
         Constructor<?> constructor = oclass.getDeclaredConstructor(RealmsScreen.class);
         constructor.setAccessible(true);
         Object object = constructor.newInstance(this);
         Minecraft.func_71410_x().func_147108_a(((RealmsScreen)object).getProxy());
      } catch (ClassNotFoundException var5) {
         LOGGER.error("Realms module missing");
      } catch (Exception exception) {
         LOGGER.error("Failed to load Realms module", (Throwable)exception);
      }

   }

   public GuiScreenRealmsProxy getNotificationScreen(GuiScreen p_getNotificationScreen_1_) {
      try {
         this.previousScreen = p_getNotificationScreen_1_;
         Class<?> oclass = Class.forName("com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen");
         Constructor<?> constructor = oclass.getDeclaredConstructor(RealmsScreen.class);
         constructor.setAccessible(true);
         Object object = constructor.newInstance(this);
         return ((RealmsScreen)object).getProxy();
      } catch (ClassNotFoundException var5) {
         LOGGER.error("Realms module missing");
      } catch (Exception exception) {
         LOGGER.error("Failed to load Realms module", (Throwable)exception);
      }

      return null;
   }

   public void init() {
      Minecraft.func_71410_x().func_147108_a(this.previousScreen);
   }
}
