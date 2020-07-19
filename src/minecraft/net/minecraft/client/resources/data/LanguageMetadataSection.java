package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.Language;

public class LanguageMetadataSection implements IMetadataSection
{
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn)
    {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages()
    {
        return this.languages;
    }
}
