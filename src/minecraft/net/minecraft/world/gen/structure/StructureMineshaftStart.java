package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.world.World;

public class StructureMineshaftStart extends StructureStart
{
    private MapGenMineshaft.Type mineShaftType;

    public StructureMineshaftStart()
    {
    }

    public StructureMineshaftStart(World p_i47149_1_, Random p_i47149_2_, int p_i47149_3_, int p_i47149_4_, MapGenMineshaft.Type p_i47149_5_)
    {
        super(p_i47149_3_, p_i47149_4_);
        this.mineShaftType = p_i47149_5_;
        StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, p_i47149_2_, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2, this.mineShaftType);
        this.components.add(structuremineshaftpieces$room);
        structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, p_i47149_2_);
        this.updateBoundingBox();

        if (p_i47149_5_ == MapGenMineshaft.Type.MESA)
        {
            int i = -5;
            int j = p_i47149_1_.getSeaLevel() - this.boundingBox.maxY + this.boundingBox.getYSize() / 2 - -5;
            this.boundingBox.offset(0, j, 0);

            for (StructureComponent structurecomponent : this.components)
            {
                structurecomponent.offset(0, j, 0);
            }
        }
        else
        {
            this.markAvailableHeight(p_i47149_1_, p_i47149_2_, 10);
        }
    }
}
