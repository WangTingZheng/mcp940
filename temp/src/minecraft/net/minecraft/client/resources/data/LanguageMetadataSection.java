package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;

public class LanguageMetadataSection implements IMetadataSection {
   private final Collection<Language> field_135019_a;

   public LanguageMetadataSection(Collection<Language> p_i1311_1_) {
      this.field_135019_a = p_i1311_1_;
   }

   public Collection<Language> func_135018_a() {
      return this.field_135019_a;
   }
}
