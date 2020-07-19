package net.minecraft.command;

import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Doubles;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.exception.ExceptionUtils;

public abstract class CommandBase implements ICommand {
   private static ICommandListener field_71533_a;
   private static final Splitter field_190796_b = Splitter.on(',');
   private static final Splitter field_190797_c = Splitter.on('=').limit(2);

   protected static SyntaxErrorException func_184889_a(JsonParseException p_184889_0_) {
      Throwable throwable = ExceptionUtils.getRootCause(p_184889_0_);
      String s = "";
      if (throwable != null) {
         s = throwable.getMessage();
         if (s.contains("setLenient")) {
            s = s.substring(s.indexOf("to accept ") + 10);
         }
      }

      return new SyntaxErrorException("commands.tellraw.jsonException", new Object[]{s});
   }

   public static NBTTagCompound func_184887_a(Entity p_184887_0_) {
      NBTTagCompound nbttagcompound = p_184887_0_.func_189511_e(new NBTTagCompound());
      if (p_184887_0_ instanceof EntityPlayer) {
         ItemStack itemstack = ((EntityPlayer)p_184887_0_).field_71071_by.func_70448_g();
         if (!itemstack.func_190926_b()) {
            nbttagcompound.func_74782_a("SelectedItem", itemstack.func_77955_b(new NBTTagCompound()));
         }
      }

      return nbttagcompound;
   }

   public int func_82362_a() {
      return 4;
   }

   public List<String> func_71514_a() {
      return Collections.<String>emptyList();
   }

   public boolean func_184882_a(MinecraftServer p_184882_1_, ICommandSender p_184882_2_) {
      return p_184882_2_.func_70003_b(this.func_82362_a(), this.func_71517_b());
   }

   public List<String> func_184883_a(MinecraftServer p_184883_1_, ICommandSender p_184883_2_, String[] p_184883_3_, @Nullable BlockPos p_184883_4_) {
      return Collections.<String>emptyList();
   }

   public static int func_175755_a(String p_175755_0_) throws NumberInvalidException {
      try {
         return Integer.parseInt(p_175755_0_);
      } catch (NumberFormatException var2) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175755_0_});
      }
   }

   public static int func_180528_a(String p_180528_0_, int p_180528_1_) throws NumberInvalidException {
      return func_175764_a(p_180528_0_, p_180528_1_, Integer.MAX_VALUE);
   }

   public static int func_175764_a(String p_175764_0_, int p_175764_1_, int p_175764_2_) throws NumberInvalidException {
      int i = func_175755_a(p_175764_0_);
      if (i < p_175764_1_) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{i, p_175764_1_});
      } else if (i > p_175764_2_) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{i, p_175764_2_});
      } else {
         return i;
      }
   }

   public static long func_175766_b(String p_175766_0_) throws NumberInvalidException {
      try {
         return Long.parseLong(p_175766_0_);
      } catch (NumberFormatException var2) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175766_0_});
      }
   }

   public static long func_175760_a(String p_175760_0_, long p_175760_1_, long p_175760_3_) throws NumberInvalidException {
      long i = func_175766_b(p_175760_0_);
      if (i < p_175760_1_) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{i, p_175760_1_});
      } else if (i > p_175760_3_) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{i, p_175760_3_});
      } else {
         return i;
      }
   }

   public static BlockPos func_175757_a(ICommandSender p_175757_0_, String[] p_175757_1_, int p_175757_2_, boolean p_175757_3_) throws NumberInvalidException {
      BlockPos blockpos = p_175757_0_.func_180425_c();
      return new BlockPos(func_175769_b((double)blockpos.func_177958_n(), p_175757_1_[p_175757_2_], -30000000, 30000000, p_175757_3_), func_175769_b((double)blockpos.func_177956_o(), p_175757_1_[p_175757_2_ + 1], 0, 256, false), func_175769_b((double)blockpos.func_177952_p(), p_175757_1_[p_175757_2_ + 2], -30000000, 30000000, p_175757_3_));
   }

   public static double func_175765_c(String p_175765_0_) throws NumberInvalidException {
      try {
         double d0 = Double.parseDouble(p_175765_0_);
         if (!Doubles.isFinite(d0)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175765_0_});
         } else {
            return d0;
         }
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175765_0_});
      }
   }

   public static double func_180526_a(String p_180526_0_, double p_180526_1_) throws NumberInvalidException {
      return func_175756_a(p_180526_0_, p_180526_1_, Double.MAX_VALUE);
   }

   public static double func_175756_a(String p_175756_0_, double p_175756_1_, double p_175756_3_) throws NumberInvalidException {
      double d0 = func_175765_c(p_175756_0_);
      if (d0 < p_175756_1_) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{String.format("%.2f", d0), String.format("%.2f", p_175756_1_)});
      } else if (d0 > p_175756_3_) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{String.format("%.2f", d0), String.format("%.2f", p_175756_3_)});
      } else {
         return d0;
      }
   }

   public static boolean func_180527_d(String p_180527_0_) throws CommandException {
      if (!"true".equals(p_180527_0_) && !"1".equals(p_180527_0_)) {
         if (!"false".equals(p_180527_0_) && !"0".equals(p_180527_0_)) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[]{p_180527_0_});
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static EntityPlayerMP func_71521_c(ICommandSender p_71521_0_) throws PlayerNotFoundException {
      if (p_71521_0_ instanceof EntityPlayerMP) {
         return (EntityPlayerMP)p_71521_0_;
      } else {
         throw new PlayerNotFoundException("commands.generic.player.unspecified");
      }
   }

   public static List<EntityPlayerMP> func_193513_a(MinecraftServer p_193513_0_, ICommandSender p_193513_1_, String p_193513_2_) throws CommandException {
      List<EntityPlayerMP> list = EntitySelector.func_193531_b(p_193513_1_, p_193513_2_);
      return (List<EntityPlayerMP>)(list.isEmpty() ? Lists.newArrayList(func_193512_a(p_193513_0_, (EntityPlayerMP)null, p_193513_2_)) : list);
   }

   public static EntityPlayerMP func_184888_a(MinecraftServer p_184888_0_, ICommandSender p_184888_1_, String p_184888_2_) throws PlayerNotFoundException, CommandException {
      return func_193512_a(p_184888_0_, EntitySelector.func_82386_a(p_184888_1_, p_184888_2_), p_184888_2_);
   }

   private static EntityPlayerMP func_193512_a(MinecraftServer p_193512_0_, @Nullable EntityPlayerMP p_193512_1_, String p_193512_2_) throws CommandException {
      if (p_193512_1_ == null) {
         try {
            p_193512_1_ = p_193512_0_.func_184103_al().func_177451_a(UUID.fromString(p_193512_2_));
         } catch (IllegalArgumentException var4) {
            ;
         }
      }

      if (p_193512_1_ == null) {
         p_193512_1_ = p_193512_0_.func_184103_al().func_152612_a(p_193512_2_);
      }

      if (p_193512_1_ == null) {
         throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[]{p_193512_2_});
      } else {
         return p_193512_1_;
      }
   }

   public static Entity func_184885_b(MinecraftServer p_184885_0_, ICommandSender p_184885_1_, String p_184885_2_) throws EntityNotFoundException, CommandException {
      return func_184884_a(p_184885_0_, p_184885_1_, p_184885_2_, Entity.class);
   }

   public static <T extends Entity> T func_184884_a(MinecraftServer p_184884_0_, ICommandSender p_184884_1_, String p_184884_2_, Class<? extends T> p_184884_3_) throws EntityNotFoundException, CommandException {
      Entity entity = EntitySelector.func_179652_a(p_184884_1_, p_184884_2_, p_184884_3_);
      if (entity == null) {
         entity = p_184884_0_.func_184103_al().func_152612_a(p_184884_2_);
      }

      if (entity == null) {
         try {
            UUID uuid = UUID.fromString(p_184884_2_);
            entity = p_184884_0_.func_175576_a(uuid);
            if (entity == null) {
               entity = p_184884_0_.func_184103_al().func_177451_a(uuid);
            }
         } catch (IllegalArgumentException var6) {
            if (p_184884_2_.split("-").length == 5) {
               throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[]{p_184884_2_});
            }
         }
      }

      if (entity != null && p_184884_3_.isAssignableFrom(entity.getClass())) {
         return (T)entity;
      } else {
         throw new EntityNotFoundException(p_184884_2_);
      }
   }

   public static List<Entity> func_184890_c(MinecraftServer p_184890_0_, ICommandSender p_184890_1_, String p_184890_2_) throws EntityNotFoundException, CommandException {
      return (List<Entity>)(EntitySelector.func_82378_b(p_184890_2_) ? EntitySelector.func_179656_b(p_184890_1_, p_184890_2_, Entity.class) : Lists.newArrayList(func_184885_b(p_184890_0_, p_184890_1_, p_184890_2_)));
   }

   public static String func_184886_d(MinecraftServer p_184886_0_, ICommandSender p_184886_1_, String p_184886_2_) throws PlayerNotFoundException, CommandException {
      try {
         return func_184888_a(p_184886_0_, p_184886_1_, p_184886_2_).func_70005_c_();
      } catch (CommandException commandexception) {
         if (EntitySelector.func_82378_b(p_184886_2_)) {
            throw commandexception;
         } else {
            return p_184886_2_;
         }
      }
   }

   public static String func_184891_e(MinecraftServer p_184891_0_, ICommandSender p_184891_1_, String p_184891_2_) throws EntityNotFoundException, CommandException {
      try {
         return func_184888_a(p_184891_0_, p_184891_1_, p_184891_2_).func_70005_c_();
      } catch (PlayerNotFoundException var6) {
         try {
            return func_184885_b(p_184891_0_, p_184891_1_, p_184891_2_).func_189512_bd();
         } catch (EntityNotFoundException entitynotfoundexception) {
            if (EntitySelector.func_82378_b(p_184891_2_)) {
               throw entitynotfoundexception;
            } else {
               return p_184891_2_;
            }
         }
      }
   }

   public static ITextComponent func_147178_a(ICommandSender p_147178_0_, String[] p_147178_1_, int p_147178_2_) throws CommandException, PlayerNotFoundException {
      return func_147176_a(p_147178_0_, p_147178_1_, p_147178_2_, false);
   }

   public static ITextComponent func_147176_a(ICommandSender p_147176_0_, String[] p_147176_1_, int p_147176_2_, boolean p_147176_3_) throws PlayerNotFoundException, CommandException {
      ITextComponent itextcomponent = new TextComponentString("");

      for(int i = p_147176_2_; i < p_147176_1_.length; ++i) {
         if (i > p_147176_2_) {
            itextcomponent.func_150258_a(" ");
         }

         ITextComponent itextcomponent1 = new TextComponentString(p_147176_1_[i]);
         if (p_147176_3_) {
            ITextComponent itextcomponent2 = EntitySelector.func_150869_b(p_147176_0_, p_147176_1_[i]);
            if (itextcomponent2 == null) {
               if (EntitySelector.func_82378_b(p_147176_1_[i])) {
                  throw new PlayerNotFoundException("commands.generic.selector.notFound", new Object[]{p_147176_1_[i]});
               }
            } else {
               itextcomponent1 = itextcomponent2;
            }
         }

         itextcomponent.func_150257_a(itextcomponent1);
      }

      return itextcomponent;
   }

   public static String func_180529_a(String[] p_180529_0_, int p_180529_1_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = p_180529_1_; i < p_180529_0_.length; ++i) {
         if (i > p_180529_1_) {
            stringbuilder.append(" ");
         }

         String s = p_180529_0_[i];
         stringbuilder.append(s);
      }

      return stringbuilder.toString();
   }

   public static CommandBase.CoordinateArg func_175770_a(double p_175770_0_, String p_175770_2_, boolean p_175770_3_) throws NumberInvalidException {
      return func_175767_a(p_175770_0_, p_175770_2_, -30000000, 30000000, p_175770_3_);
   }

   public static CommandBase.CoordinateArg func_175767_a(double p_175767_0_, String p_175767_2_, int p_175767_3_, int p_175767_4_, boolean p_175767_5_) throws NumberInvalidException {
      boolean flag = p_175767_2_.startsWith("~");
      if (flag && Double.isNaN(p_175767_0_)) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175767_0_});
      } else {
         double d0 = 0.0D;
         if (!flag || p_175767_2_.length() > 1) {
            boolean flag1 = p_175767_2_.contains(".");
            if (flag) {
               p_175767_2_ = p_175767_2_.substring(1);
            }

            d0 += func_175765_c(p_175767_2_);
            if (!flag1 && !flag && p_175767_5_) {
               d0 += 0.5D;
            }
         }

         double d1 = d0 + (flag ? p_175767_0_ : 0.0D);
         if (p_175767_3_ != 0 || p_175767_4_ != 0) {
            if (d1 < (double)p_175767_3_) {
               throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{String.format("%.2f", d1), p_175767_3_});
            }

            if (d1 > (double)p_175767_4_) {
               throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{String.format("%.2f", d1), p_175767_4_});
            }
         }

         return new CommandBase.CoordinateArg(d1, d0, flag);
      }
   }

   public static double func_175761_b(double p_175761_0_, String p_175761_2_, boolean p_175761_3_) throws NumberInvalidException {
      return func_175769_b(p_175761_0_, p_175761_2_, -30000000, 30000000, p_175761_3_);
   }

   public static double func_175769_b(double p_175769_0_, String p_175769_2_, int p_175769_3_, int p_175769_4_, boolean p_175769_5_) throws NumberInvalidException {
      boolean flag = p_175769_2_.startsWith("~");
      if (flag && Double.isNaN(p_175769_0_)) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_175769_0_});
      } else {
         double d0 = flag ? p_175769_0_ : 0.0D;
         if (!flag || p_175769_2_.length() > 1) {
            boolean flag1 = p_175769_2_.contains(".");
            if (flag) {
               p_175769_2_ = p_175769_2_.substring(1);
            }

            d0 += func_175765_c(p_175769_2_);
            if (!flag1 && !flag && p_175769_5_) {
               d0 += 0.5D;
            }
         }

         if (p_175769_3_ != 0 || p_175769_4_ != 0) {
            if (d0 < (double)p_175769_3_) {
               throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{String.format("%.2f", d0), p_175769_3_});
            }

            if (d0 > (double)p_175769_4_) {
               throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{String.format("%.2f", d0), p_175769_4_});
            }
         }

         return d0;
      }
   }

   public static Item func_147179_f(ICommandSender p_147179_0_, String p_147179_1_) throws NumberInvalidException {
      ResourceLocation resourcelocation = new ResourceLocation(p_147179_1_);
      Item item = Item.field_150901_e.func_82594_a(resourcelocation);
      if (item == null) {
         throw new NumberInvalidException("commands.give.item.notFound", new Object[]{resourcelocation});
      } else {
         return item;
      }
   }

   public static Block func_147180_g(ICommandSender p_147180_0_, String p_147180_1_) throws NumberInvalidException {
      ResourceLocation resourcelocation = new ResourceLocation(p_147180_1_);
      if (!Block.field_149771_c.func_148741_d(resourcelocation)) {
         throw new NumberInvalidException("commands.give.block.notFound", new Object[]{resourcelocation});
      } else {
         return Block.field_149771_c.func_82594_a(resourcelocation);
      }
   }

   public static IBlockState func_190794_a(Block p_190794_0_, String p_190794_1_) throws NumberInvalidException, InvalidBlockStateException {
      try {
         int i = Integer.parseInt(p_190794_1_);
         if (i < 0) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{i, Integer.valueOf(0)});
         } else if (i > 15) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{i, Integer.valueOf(15)});
         } else {
            return p_190794_0_.func_176203_a(Integer.parseInt(p_190794_1_));
         }
      } catch (RuntimeException var7) {
         try {
            Map<IProperty<?>, Comparable<?>> map = func_190795_c(p_190794_0_, p_190794_1_);
            IBlockState iblockstate = p_190794_0_.func_176223_P();

            for(Entry<IProperty<?>, Comparable<?>> entry : map.entrySet()) {
               iblockstate = func_190793_a(iblockstate, entry.getKey(), entry.getValue());
            }

            return iblockstate;
         } catch (RuntimeException var6) {
            throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[]{p_190794_1_, Block.field_149771_c.func_177774_c(p_190794_0_)});
         }
      }
   }

   private static <T extends Comparable<T>> IBlockState func_190793_a(IBlockState p_190793_0_, IProperty<T> p_190793_1_, Comparable<?> p_190793_2_) {
      return p_190793_0_.func_177226_a(p_190793_1_, p_190793_2_);
   }

   public static Predicate<IBlockState> func_190791_b(final Block p_190791_0_, String p_190791_1_) throws InvalidBlockStateException {
      if (!"*".equals(p_190791_1_) && !"-1".equals(p_190791_1_)) {
         try {
            final int i = Integer.parseInt(p_190791_1_);
            return new Predicate<IBlockState>() {
               public boolean apply(@Nullable IBlockState p_apply_1_) {
                  return i == p_apply_1_.func_177230_c().func_176201_c(p_apply_1_);
               }
            };
         } catch (RuntimeException var3) {
            final Map<IProperty<?>, Comparable<?>> map = func_190795_c(p_190791_0_, p_190791_1_);
            return new Predicate<IBlockState>() {
               public boolean apply(@Nullable IBlockState p_apply_1_) {
                  if (p_apply_1_ != null && p_190791_0_ == p_apply_1_.func_177230_c()) {
                     for(Entry<IProperty<?>, Comparable<?>> entry : map.entrySet()) {
                        if (!p_apply_1_.func_177229_b(entry.getKey()).equals(entry.getValue())) {
                           return false;
                        }
                     }

                     return true;
                  } else {
                     return false;
                  }
               }
            };
         }
      } else {
         return Predicates.alwaysTrue();
      }
   }

   private static Map<IProperty<?>, Comparable<?>> func_190795_c(Block p_190795_0_, String p_190795_1_) throws InvalidBlockStateException {
      Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newHashMap();
      if ("default".equals(p_190795_1_)) {
         return p_190795_0_.func_176223_P().func_177228_b();
      } else {
         BlockStateContainer blockstatecontainer = p_190795_0_.func_176194_O();
         Iterator iterator = field_190796_b.split(p_190795_1_).iterator();

         while(true) {
            if (!iterator.hasNext()) {
               return map;
            }

            String s = (String)iterator.next();
            Iterator<String> iterator1 = field_190797_c.split(s).iterator();
            if (!iterator1.hasNext()) {
               break;
            }

            IProperty<?> iproperty = blockstatecontainer.func_185920_a(iterator1.next());
            if (iproperty == null || !iterator1.hasNext()) {
               break;
            }

            Comparable<?> comparable = func_190792_a(iproperty, iterator1.next());
            if (comparable == null) {
               break;
            }

            map.put(iproperty, comparable);
         }

         throw new InvalidBlockStateException("commands.generic.blockstate.invalid", new Object[]{p_190795_1_, Block.field_149771_c.func_177774_c(p_190795_0_)});
      }
   }

   @Nullable
   private static <T extends Comparable<T>> T func_190792_a(IProperty<T> p_190792_0_, String p_190792_1_) {
      return (T)(p_190792_0_.func_185929_b(p_190792_1_).orNull());
   }

   public static String func_71527_a(Object[] p_71527_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(int i = 0; i < p_71527_0_.length; ++i) {
         String s = p_71527_0_[i].toString();
         if (i > 0) {
            if (i == p_71527_0_.length - 1) {
               stringbuilder.append(" and ");
            } else {
               stringbuilder.append(", ");
            }
         }

         stringbuilder.append(s);
      }

      return stringbuilder.toString();
   }

   public static ITextComponent func_180530_a(List<ITextComponent> p_180530_0_) {
      ITextComponent itextcomponent = new TextComponentString("");

      for(int i = 0; i < p_180530_0_.size(); ++i) {
         if (i > 0) {
            if (i == p_180530_0_.size() - 1) {
               itextcomponent.func_150258_a(" and ");
            } else if (i > 0) {
               itextcomponent.func_150258_a(", ");
            }
         }

         itextcomponent.func_150257_a(p_180530_0_.get(i));
      }

      return itextcomponent;
   }

   public static String func_96333_a(Collection<String> p_96333_0_) {
      return func_71527_a(p_96333_0_.toArray(new String[p_96333_0_.size()]));
   }

   public static List<String> func_175771_a(String[] p_175771_0_, int p_175771_1_, @Nullable BlockPos p_175771_2_) {
      if (p_175771_2_ == null) {
         return Lists.newArrayList("~");
      } else {
         int i = p_175771_0_.length - 1;
         String s;
         if (i == p_175771_1_) {
            s = Integer.toString(p_175771_2_.func_177958_n());
         } else if (i == p_175771_1_ + 1) {
            s = Integer.toString(p_175771_2_.func_177956_o());
         } else {
            if (i != p_175771_1_ + 2) {
               return Collections.<String>emptyList();
            }

            s = Integer.toString(p_175771_2_.func_177952_p());
         }

         return Lists.newArrayList(s);
      }
   }

   public static List<String> func_181043_b(String[] p_181043_0_, int p_181043_1_, @Nullable BlockPos p_181043_2_) {
      if (p_181043_2_ == null) {
         return Lists.newArrayList("~");
      } else {
         int i = p_181043_0_.length - 1;
         String s;
         if (i == p_181043_1_) {
            s = Integer.toString(p_181043_2_.func_177958_n());
         } else {
            if (i != p_181043_1_ + 1) {
               return Collections.<String>emptyList();
            }

            s = Integer.toString(p_181043_2_.func_177952_p());
         }

         return Lists.newArrayList(s);
      }
   }

   public static boolean func_71523_a(String p_71523_0_, String p_71523_1_) {
      return p_71523_1_.regionMatches(true, 0, p_71523_0_, 0, p_71523_0_.length());
   }

   public static List<String> func_71530_a(String[] p_71530_0_, String... p_71530_1_) {
      return func_175762_a(p_71530_0_, Arrays.asList(p_71530_1_));
   }

   public static List<String> func_175762_a(String[] p_175762_0_, Collection<?> p_175762_1_) {
      String s = p_175762_0_[p_175762_0_.length - 1];
      List<String> list = Lists.<String>newArrayList();
      if (!p_175762_1_.isEmpty()) {
         for(String s1 : Iterables.transform(p_175762_1_, Functions.toStringFunction())) {
            if (func_71523_a(s, s1)) {
               list.add(s1);
            }
         }

         if (list.isEmpty()) {
            for(Object object : p_175762_1_) {
               if (object instanceof ResourceLocation && func_71523_a(s, ((ResourceLocation)object).func_110623_a())) {
                  list.add(String.valueOf(object));
               }
            }
         }
      }

      return list;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return false;
   }

   public static void func_152373_a(ICommandSender p_152373_0_, ICommand p_152373_1_, String p_152373_2_, Object... p_152373_3_) {
      func_152374_a(p_152373_0_, p_152373_1_, 0, p_152373_2_, p_152373_3_);
   }

   public static void func_152374_a(ICommandSender p_152374_0_, ICommand p_152374_1_, int p_152374_2_, String p_152374_3_, Object... p_152374_4_) {
      if (field_71533_a != null) {
         field_71533_a.func_152372_a(p_152374_0_, p_152374_1_, p_152374_2_, p_152374_3_, p_152374_4_);
      }

   }

   public static void func_71529_a(ICommandListener p_71529_0_) {
      field_71533_a = p_71529_0_;
   }

   public int compareTo(ICommand p_compareTo_1_) {
      return this.func_71517_b().compareTo(p_compareTo_1_.func_71517_b());
   }

   public static class CoordinateArg {
      private final double field_179633_a;
      private final double field_179631_b;
      private final boolean field_179632_c;

      protected CoordinateArg(double p_i46051_1_, double p_i46051_3_, boolean p_i46051_5_) {
         this.field_179633_a = p_i46051_1_;
         this.field_179631_b = p_i46051_3_;
         this.field_179632_c = p_i46051_5_;
      }

      public double func_179628_a() {
         return this.field_179633_a;
      }

      public double func_179629_b() {
         return this.field_179631_b;
      }

      public boolean func_179630_c() {
         return this.field_179632_c;
      }
   }
}
