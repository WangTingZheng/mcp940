package net.minecraft.client.audio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IResourceManagerReloadListener, ITickable {
   public static final Sound field_147700_a = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.Type.FILE, false);
   private static final Logger field_147698_b = LogManager.getLogger();
   private static final Gson field_147699_c = (new GsonBuilder()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
   private static final ParameterizedType field_147696_d = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class, SoundList.class};
      }

      public Type getRawType() {
         return Map.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   private final SoundRegistry field_147697_e = new SoundRegistry();
   private final SoundManager field_147694_f;
   private final IResourceManager field_147695_g;

   public SoundHandler(IResourceManager p_i45122_1_, GameSettings p_i45122_2_) {
      this.field_147695_g = p_i45122_1_;
      this.field_147694_f = new SoundManager(this, p_i45122_2_);
   }

   public void func_110549_a(IResourceManager p_110549_1_) {
      this.field_147697_e.func_148763_c();

      for(String s : p_110549_1_.func_135055_a()) {
         try {
            for(IResource iresource : p_110549_1_.func_135056_b(new ResourceLocation(s, "sounds.json"))) {
               try {
                  Map<String, SoundList> map = this.func_175085_a(iresource.func_110527_b());

                  for(Entry<String, SoundList> entry : map.entrySet()) {
                     this.func_147693_a(new ResourceLocation(s, entry.getKey()), entry.getValue());
                  }
               } catch (RuntimeException runtimeexception) {
                  field_147698_b.warn("Invalid sounds.json", (Throwable)runtimeexception);
               }
            }
         } catch (IOException var11) {
            ;
         }
      }

      for(ResourceLocation resourcelocation : this.field_147697_e.func_148742_b()) {
         SoundEventAccessor soundeventaccessor = (SoundEventAccessor)this.field_147697_e.func_82594_a(resourcelocation);
         if (soundeventaccessor.func_188712_c() instanceof TextComponentTranslation) {
            String s1 = ((TextComponentTranslation)soundeventaccessor.func_188712_c()).func_150268_i();
            if (!I18n.func_188566_a(s1)) {
               field_147698_b.debug("Missing subtitle {} for event: {}", s1, resourcelocation);
            }
         }
      }

      for(ResourceLocation resourcelocation1 : this.field_147697_e.func_148742_b()) {
         if (SoundEvent.field_187505_a.func_82594_a(resourcelocation1) == null) {
            field_147698_b.debug("Not having sound event for: {}", (Object)resourcelocation1);
         }
      }

      this.field_147694_f.func_148596_a();
   }

   @Nullable
   protected Map<String, SoundList> func_175085_a(InputStream p_175085_1_) {
      Map map;
      try {
         map = (Map)JsonUtils.func_193841_a(field_147699_c, new InputStreamReader(p_175085_1_, StandardCharsets.UTF_8), field_147696_d);
      } finally {
         IOUtils.closeQuietly(p_175085_1_);
      }

      return map;
   }

   private void func_147693_a(ResourceLocation p_147693_1_, SoundList p_147693_2_) {
      SoundEventAccessor soundeventaccessor = (SoundEventAccessor)this.field_147697_e.func_82594_a(p_147693_1_);
      boolean flag = soundeventaccessor == null;
      if (flag || p_147693_2_.func_148574_b()) {
         if (!flag) {
            field_147698_b.debug("Replaced sound event location {}", (Object)p_147693_1_);
         }

         soundeventaccessor = new SoundEventAccessor(p_147693_1_, p_147693_2_.func_188701_c());
         this.field_147697_e.func_186803_a(soundeventaccessor);
      }

      for(final Sound sound : p_147693_2_.func_188700_a()) {
         final ResourceLocation resourcelocation = sound.func_188719_a();
         ISoundEventAccessor<Sound> isoundeventaccessor;
         switch(sound.func_188722_g()) {
         case FILE:
            if (!this.func_184401_a(sound, p_147693_1_)) {
               continue;
            }

            isoundeventaccessor = sound;
            break;
         case SOUND_EVENT:
            isoundeventaccessor = new ISoundEventAccessor<Sound>() {
               public int func_148721_a() {
                  SoundEventAccessor soundeventaccessor1 = (SoundEventAccessor)SoundHandler.this.field_147697_e.func_82594_a(resourcelocation);
                  return soundeventaccessor1 == null ? 0 : soundeventaccessor1.func_148721_a();
               }

               public Sound func_148720_g() {
                  SoundEventAccessor soundeventaccessor1 = (SoundEventAccessor)SoundHandler.this.field_147697_e.func_82594_a(resourcelocation);
                  if (soundeventaccessor1 == null) {
                     return SoundHandler.field_147700_a;
                  } else {
                     Sound sound1 = soundeventaccessor1.func_148720_g();
                     return new Sound(sound1.func_188719_a().toString(), sound1.func_188724_c() * sound.func_188724_c(), sound1.func_188725_d() * sound.func_188725_d(), sound.func_148721_a(), Sound.Type.FILE, sound1.func_188723_h() || sound.func_188723_h());
                  }
               }
            };
            break;
         default:
            throw new IllegalStateException("Unknown SoundEventRegistration type: " + sound.func_188722_g());
         }

         soundeventaccessor.func_188715_a(isoundeventaccessor);
      }

   }

   private boolean func_184401_a(Sound p_184401_1_, ResourceLocation p_184401_2_) {
      ResourceLocation resourcelocation = p_184401_1_.func_188721_b();
      IResource iresource = null;

      boolean flag;
      try {
         iresource = this.field_147695_g.func_110536_a(resourcelocation);
         iresource.func_110527_b();
         return true;
      } catch (FileNotFoundException var11) {
         field_147698_b.warn("File {} does not exist, cannot add it to event {}", resourcelocation, p_184401_2_);
         flag = false;
      } catch (IOException ioexception) {
         field_147698_b.warn("Could not load sound file {}, cannot add it to event {}", resourcelocation, p_184401_2_, ioexception);
         flag = false;
         return flag;
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

      return flag;
   }

   @Nullable
   public SoundEventAccessor func_184398_a(ResourceLocation p_184398_1_) {
      return (SoundEventAccessor)this.field_147697_e.func_82594_a(p_184398_1_);
   }

   public void func_147682_a(ISound p_147682_1_) {
      this.field_147694_f.func_148611_c(p_147682_1_);
   }

   public void func_147681_a(ISound p_147681_1_, int p_147681_2_) {
      this.field_147694_f.func_148599_a(p_147681_1_, p_147681_2_);
   }

   public void func_147691_a(EntityPlayer p_147691_1_, float p_147691_2_) {
      this.field_147694_f.func_148615_a(p_147691_1_, p_147691_2_);
   }

   public void func_147689_b() {
      this.field_147694_f.func_148610_e();
   }

   public void func_147690_c() {
      this.field_147694_f.func_148614_c();
   }

   public void func_147685_d() {
      this.field_147694_f.func_148613_b();
   }

   public void func_73660_a() {
      this.field_147694_f.func_148605_d();
   }

   public void func_147687_e() {
      this.field_147694_f.func_148604_f();
   }

   public void func_184399_a(SoundCategory p_184399_1_, float p_184399_2_) {
      if (p_184399_1_ == SoundCategory.MASTER && p_184399_2_ <= 0.0F) {
         this.func_147690_c();
      }

      this.field_147694_f.func_188771_a(p_184399_1_, p_184399_2_);
   }

   public void func_147683_b(ISound p_147683_1_) {
      this.field_147694_f.func_148602_b(p_147683_1_);
   }

   public boolean func_147692_c(ISound p_147692_1_) {
      return this.field_147694_f.func_148597_a(p_147692_1_);
   }

   public void func_184402_a(ISoundEventListener p_184402_1_) {
      this.field_147694_f.func_188774_a(p_184402_1_);
   }

   public void func_184400_b(ISoundEventListener p_184400_1_) {
      this.field_147694_f.func_188773_b(p_184400_1_);
   }

   public void func_189520_a(String p_189520_1_, SoundCategory p_189520_2_) {
      this.field_147694_f.func_189567_a(p_189520_1_, p_189520_2_);
   }
}
