package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureEndCityPieces
{
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
    private static final StructureEndCityPieces.IGenerator HOUSE_TOWER_GENERATOR = new StructureEndCityPieces.IGenerator()
    {
        public void init()
        {
        }
        public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_)
        {
            if (p_191086_2_ > 8)
            {
                return false;
            }
            else
            {
                Rotation rotation = p_191086_3_.placeSettings.getRotation();
                StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, p_191086_3_, p_191086_4_, "base_floor", rotation, true));
                int i = p_191086_6_.nextInt(3);

                if (i == 0)
                {
                    StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
                }
                else if (i == 1)
                {
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
                    StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191086_5_, p_191086_6_);
                }
                else if (i == 2)
                {
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor_c", rotation, false));
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
                    StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191086_5_, p_191086_6_);
                }

                return true;
            }
        }
    };
    private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)));
    private static final StructureEndCityPieces.IGenerator TOWER_GENERATOR = new StructureEndCityPieces.IGenerator()
    {
        public void init()
        {
        }
        public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_)
        {
            Rotation rotation = p_191086_3_.placeSettings.getRotation();
            StructureEndCityPieces.CityTemplate lvt_8_1_ = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, p_191086_3_, new BlockPos(3 + p_191086_6_.nextInt(2), -3, 3 + p_191086_6_.nextInt(2)), "tower_base", rotation, true));
            lvt_8_1_ = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, lvt_8_1_, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
            StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = p_191086_6_.nextInt(3) == 0 ? lvt_8_1_ : null;
            int i = 1 + p_191086_6_.nextInt(3);

            for (int j = 0; j < i; ++j)
            {
                lvt_8_1_ = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, lvt_8_1_, new BlockPos(0, 4, 0), "tower_piece", rotation, true));

                if (j < i - 1 && p_191086_6_.nextBoolean())
                {
                    structureendcitypieces$citytemplate1 = lvt_8_1_;
                }
            }

            if (structureendcitypieces$citytemplate1 != null)
            {
                for (Tuple<Rotation, BlockPos> tuple : StructureEndCityPieces.TOWER_BRIDGES)
                {
                    if (p_191086_6_.nextBoolean())
                    {
                        StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate2 = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate1, tuple.getSecond(), "bridge_end", rotation.add(tuple.getFirst()), true));
                        StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.TOWER_BRIDGE_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate2, (BlockPos)null, p_191086_5_, p_191086_6_);
                    }
                }

                StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
            }
            else
            {
                if (p_191086_2_ != 7)
                {
                    return StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.FAT_TOWER_GENERATOR, p_191086_2_ + 1, lvt_8_1_, (BlockPos)null, p_191086_5_, p_191086_6_);
                }

                StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
            }

            return true;
        }
    };
    private static final StructureEndCityPieces.IGenerator TOWER_BRIDGE_GENERATOR = new StructureEndCityPieces.IGenerator()
    {
        public boolean shipCreated;
        public void init()
        {
            this.shipCreated = false;
        }
        public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_)
        {
            Rotation rotation = p_191086_3_.placeSettings.getRotation();
            int i = p_191086_6_.nextInt(4) + 1;
            StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, p_191086_3_, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
            structureendcitypieces$citytemplate.componentType = -1;
            int j = 0;

            for (int k = 0; k < i; ++k)
            {
                if (p_191086_6_.nextBoolean())
                {
                    structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_piece", rotation, true));
                    j = 0;
                }
                else
                {
                    if (p_191086_6_.nextBoolean())
                    {
                        structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -4), "bridge_steep_stairs", rotation, true));
                    }
                    else
                    {
                        structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, j, -8), "bridge_gentle_stairs", rotation, true));
                    }

                    j = 4;
                }
            }

            if (!this.shipCreated && p_191086_6_.nextInt(10 - p_191086_2_) == 0)
            {
                StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-8 + p_191086_6_.nextInt(8), j, -70 + p_191086_6_.nextInt(10)), "ship", rotation, true));
                this.shipCreated = true;
            }
            else if (!StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.HOUSE_TOWER_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate, new BlockPos(-3, j + 1, -11), p_191086_5_, p_191086_6_))
            {
                return false;
            }

            structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(4, j, 0), "bridge_end", rotation.add(Rotation.CLOCKWISE_180), true));
            structureendcitypieces$citytemplate.componentType = -1;
            return true;
        }
    };
    private static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)));
    private static final StructureEndCityPieces.IGenerator FAT_TOWER_GENERATOR = new StructureEndCityPieces.IGenerator()
    {
        public void init()
        {
        }
        public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_)
        {
            Rotation rotation = p_191086_3_.placeSettings.getRotation();
            StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, p_191086_3_, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
            structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));

            for (int i = 0; i < 2 && p_191086_6_.nextInt(3) != 0; ++i)
            {
                structureendcitypieces$citytemplate = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));

                for (Tuple<Rotation, BlockPos> tuple : StructureEndCityPieces.FAT_TOWER_BRIDGES)
                {
                    if (p_191086_6_.nextBoolean())
                    {
                        StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate1 = StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, tuple.getSecond(), "bridge_end", rotation.add(tuple.getFirst()), true));
                        StructureEndCityPieces.recursiveChildren(p_191086_1_, StructureEndCityPieces.TOWER_BRIDGE_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate1, (BlockPos)null, p_191086_5_, p_191086_6_);
                    }
                }
            }

            StructureEndCityPieces.addHelper(p_191086_5_, StructureEndCityPieces.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
            return true;
        }
    };

    public static void registerPieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureEndCityPieces.CityTemplate.class, "ECP");
    }

    private static StructureEndCityPieces.CityTemplate addPiece(TemplateManager p_191090_0_, StructureEndCityPieces.CityTemplate p_191090_1_, BlockPos p_191090_2_, String p_191090_3_, Rotation p_191090_4_, boolean p_191090_5_)
    {
        StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = new StructureEndCityPieces.CityTemplate(p_191090_0_, p_191090_3_, p_191090_1_.templatePosition, p_191090_4_, p_191090_5_);
        BlockPos blockpos = p_191090_1_.template.calculateConnectedPos(p_191090_1_.placeSettings, p_191090_2_, structureendcitypieces$citytemplate.placeSettings, BlockPos.ORIGIN);
        structureendcitypieces$citytemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
        return structureendcitypieces$citytemplate;
    }

    public static void startHouseTower(TemplateManager p_191087_0_, BlockPos p_191087_1_, Rotation p_191087_2_, List<StructureComponent> p_191087_3_, Random p_191087_4_)
    {
        FAT_TOWER_GENERATOR.init();
        HOUSE_TOWER_GENERATOR.init();
        TOWER_BRIDGE_GENERATOR.init();
        TOWER_GENERATOR.init();
        StructureEndCityPieces.CityTemplate structureendcitypieces$citytemplate = addHelper(p_191087_3_, new StructureEndCityPieces.CityTemplate(p_191087_0_, "base_floor", p_191087_1_, p_191087_2_, true));
        structureendcitypieces$citytemplate = addHelper(p_191087_3_, addPiece(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor", p_191087_2_, false));
        structureendcitypieces$citytemplate = addHelper(p_191087_3_, addPiece(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor", p_191087_2_, false));
        structureendcitypieces$citytemplate = addHelper(p_191087_3_, addPiece(p_191087_0_, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", p_191087_2_, true));
        recursiveChildren(p_191087_0_, TOWER_GENERATOR, 1, structureendcitypieces$citytemplate, (BlockPos)null, p_191087_3_, p_191087_4_);
    }

    private static StructureEndCityPieces.CityTemplate addHelper(List<StructureComponent> p_189935_0_, StructureEndCityPieces.CityTemplate p_189935_1_)
    {
        p_189935_0_.add(p_189935_1_);
        return p_189935_1_;
    }

    private static boolean recursiveChildren(TemplateManager p_191088_0_, StructureEndCityPieces.IGenerator p_191088_1_, int p_191088_2_, StructureEndCityPieces.CityTemplate p_191088_3_, BlockPos p_191088_4_, List<StructureComponent> p_191088_5_, Random p_191088_6_)
    {
        if (p_191088_2_ > 8)
        {
            return false;
        }
        else
        {
            List<StructureComponent> list = Lists.<StructureComponent>newArrayList();

            if (p_191088_1_.generate(p_191088_0_, p_191088_2_, p_191088_3_, p_191088_4_, list, p_191088_6_))
            {
                boolean flag = false;
                int i = p_191088_6_.nextInt();

                for (StructureComponent structurecomponent : list)
                {
                    structurecomponent.componentType = i;
                    StructureComponent structurecomponent1 = StructureComponent.findIntersecting(p_191088_5_, structurecomponent.getBoundingBox());

                    if (structurecomponent1 != null && structurecomponent1.componentType != p_191088_3_.componentType)
                    {
                        flag = true;
                        break;
                    }
                }

                if (!flag)
                {
                    p_191088_5_.addAll(list);
                    return true;
                }
            }

            return false;
        }
    }

    public static class CityTemplate extends StructureComponentTemplate
    {
        private String pieceName;
        private Rotation rotation;
        private boolean overwrite;

        public CityTemplate()
        {
        }

        public CityTemplate(TemplateManager p_i47214_1_, String p_i47214_2_, BlockPos p_i47214_3_, Rotation p_i47214_4_, boolean p_i47214_5_)
        {
            super(0);
            this.pieceName = p_i47214_2_;
            this.templatePosition = p_i47214_3_;
            this.rotation = p_i47214_4_;
            this.overwrite = p_i47214_5_;
            this.loadTemplate(p_i47214_1_);
        }

        private void loadTemplate(TemplateManager p_191085_1_)
        {
            Template template = p_191085_1_.getTemplate((MinecraftServer)null, new ResourceLocation("endcity/" + this.pieceName));
            PlacementSettings placementsettings = (this.overwrite ? StructureEndCityPieces.OVERWRITE : StructureEndCityPieces.INSERT).copy().setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementsettings);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Template", this.pieceName);
            tagCompound.setString("Rot", this.rotation.name());
            tagCompound.setBoolean("OW", this.overwrite);
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.pieceName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.overwrite = tagCompound.getBoolean("OW");
            this.loadTemplate(p_143011_2_);
        }

        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
        {
            if (function.startsWith("Chest"))
            {
                BlockPos blockpos = pos.down();

                if (sbb.isVecInside(blockpos))
                {
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntityChest)
                    {
                        ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, rand.nextLong());
                    }
                }
            }
            else if (function.startsWith("Sentry"))
            {
                EntityShulker entityshulker = new EntityShulker(worldIn);
                entityshulker.setPosition((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
                entityshulker.setAttachmentPos(pos);
                worldIn.spawnEntity(entityshulker);
            }
            else if (function.startsWith("Elytra"))
            {
                EntityItemFrame entityitemframe = new EntityItemFrame(worldIn, pos, this.rotation.rotate(EnumFacing.SOUTH));
                entityitemframe.setDisplayedItem(new ItemStack(Items.ELYTRA));
                worldIn.spawnEntity(entityitemframe);
            }
        }
    }

    interface IGenerator
    {
        void init();

        boolean generate(TemplateManager p_191086_1_, int p_191086_2_, StructureEndCityPieces.CityTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_, Random p_191086_6_);
    }
}
