package net.minecraft.client.resources;

public class Language implements Comparable<Language>
{
    private final String languageCode;
    private final String region;
    private final String name;
    private final boolean bidirectional;

    public Language(String languageCodeIn, String regionIn, String nameIn, boolean bidirectionalIn)
    {
        this.languageCode = languageCodeIn;
        this.region = regionIn;
        this.name = nameIn;
        this.bidirectional = bidirectionalIn;
    }

    public String getLanguageCode()
    {
        return this.languageCode;
    }

    public boolean isBidirectional()
    {
        return this.bidirectional;
    }

    public String toString()
    {
        return String.format("%s (%s)", this.name, this.region);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else
        {
            return !(p_equals_1_ instanceof Language) ? false : this.languageCode.equals(((Language)p_equals_1_).languageCode);
        }
    }

    public int hashCode()
    {
        return this.languageCode.hashCode();
    }

    public int compareTo(Language p_compareTo_1_)
    {
        return this.languageCode.compareTo(p_compareTo_1_.languageCode);
    }
}
