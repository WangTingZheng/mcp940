package shadersmod.client;

import net.minecraft.src.MatchBlock;

public class BlockAlias
{
    private int blockId;
    private MatchBlock[] matchBlocks;

    public BlockAlias(int blockId, MatchBlock[] matchBlocks)
    {
        this.blockId = blockId;
        this.matchBlocks = matchBlocks;
    }

    public int getBlockId()
    {
        return this.blockId;
    }

    public boolean matches(int id, int metadata)
    {
        for (int i = 0; i < this.matchBlocks.length; ++i)
        {
            MatchBlock matchblock = this.matchBlocks[i];

            if (matchblock.matches(id, metadata))
            {
                return true;
            }
        }

        return false;
    }

    public int[] getMatchBlockIds()
    {
        int[] aint = new int[this.matchBlocks.length];

        for (int i = 0; i < aint.length; ++i)
        {
            aint[i] = this.matchBlocks[i].getBlockId();
        }

        return aint;
    }
}
