package net.minecraft.util.text;

public class TextComponentTranslationFormatException extends IllegalArgumentException {
   public TextComponentTranslationFormatException(TextComponentTranslation p_i45161_1_, String p_i45161_2_) {
      super(String.format("Error parsing: %s: %s", p_i45161_1_, p_i45161_2_));
   }

   public TextComponentTranslationFormatException(TextComponentTranslation p_i45162_1_, int p_i45162_2_) {
      super(String.format("Invalid index %d requested for %s", p_i45162_2_, p_i45162_1_));
   }

   public TextComponentTranslationFormatException(TextComponentTranslation p_i45163_1_, Throwable p_i45163_2_) {
      super(String.format("Error while parsing: %s", p_i45163_1_), p_i45163_2_);
   }
}
