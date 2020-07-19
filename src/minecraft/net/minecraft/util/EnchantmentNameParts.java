package net.minecraft.util;

import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.FontRenderer;

public class EnchantmentNameParts
{
    private static final EnchantmentNameParts INSTANCE = new EnchantmentNameParts();
    private final Random rand = new Random();
    private final String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale phnglui mglwnafh cthulhu rlyeh wgahnagl fhtagnbaguette".split(" ");

    public static EnchantmentNameParts getInstance()
    {
        return INSTANCE;
    }

    /**
     * Randomly generates a new name built up of 3 or 4 randomly selected words.
     */
    public String generateNewRandomName(FontRenderer fontRendererIn, int length)
    {
        int i = this.rand.nextInt(2) + 3;
        String s = "";

        for (int j = 0; j < i; ++j)
        {
            if (j > 0)
            {
                s = s + " ";
            }

            s = s + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
        }

        List<String> list = fontRendererIn.listFormattedStringToWidth(s, length);
        return org.apache.commons.lang3.StringUtils.join((Iterable)(list.size() >= 2 ? list.subList(0, 2) : list), " ");
    }

    /**
     * Resets the underlying random number generator using a given seed.
     */
    public void reseedRandomGenerator(long seed)
    {
        this.rand.setSeed(seed);
    }
}
