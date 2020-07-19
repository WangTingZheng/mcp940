package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class WoodlandMansionPieces
{
    public static void registerWoodlandMansionPieces()
    {
        MapGenStructureIO.registerStructureComponent(WoodlandMansionPieces.MansionTemplate.class, "WMP");
    }

    public static void generateMansion(TemplateManager p_191152_0_, BlockPos p_191152_1_, Rotation p_191152_2_, List<WoodlandMansionPieces.MansionTemplate> p_191152_3_, Random p_191152_4_)
    {
        WoodlandMansionPieces.Grid woodlandmansionpieces$grid = new WoodlandMansionPieces.Grid(p_191152_4_);
        WoodlandMansionPieces.Placer woodlandmansionpieces$placer = new WoodlandMansionPieces.Placer(p_191152_0_, p_191152_4_);
        woodlandmansionpieces$placer.createMansion(p_191152_1_, p_191152_2_, p_191152_3_, woodlandmansionpieces$grid);
    }

    static class FirstFloor extends WoodlandMansionPieces.RoomCollection
    {
        private FirstFloor()
        {
        }

        public String get1x1(Random p_191104_1_)
        {
            return "1x1_a" + (p_191104_1_.nextInt(5) + 1);
        }

        public String get1x1Secret(Random p_191099_1_)
        {
            return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
        }

        public String get1x2SideEntrance(Random p_191100_1_, boolean p_191100_2_)
        {
            return "1x2_a" + (p_191100_1_.nextInt(9) + 1);
        }

        public String get1x2FrontEntrance(Random p_191098_1_, boolean p_191098_2_)
        {
            return "1x2_b" + (p_191098_1_.nextInt(5) + 1);
        }

        public String get1x2Secret(Random p_191102_1_)
        {
            return "1x2_s" + (p_191102_1_.nextInt(2) + 1);
        }

        public String get2x2(Random p_191101_1_)
        {
            return "2x2_a" + (p_191101_1_.nextInt(4) + 1);
        }

        public String get2x2Secret(Random p_191103_1_)
        {
            return "2x2_s1";
        }
    }

    static class Grid
    {
        private final Random random;
        private final WoodlandMansionPieces.SimpleGrid baseGrid;
        private final WoodlandMansionPieces.SimpleGrid thirdFloorGrid;
        private final WoodlandMansionPieces.SimpleGrid[] floorRooms;
        private final int entranceX;
        private final int entranceY;

        public Grid(Random randomIn)
        {
            this.random = randomIn;
            int i = 11;
            this.entranceX = 7;
            this.entranceY = 4;
            this.baseGrid = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
            this.baseGrid.set(this.entranceX, this.entranceY, this.entranceX + 1, this.entranceY + 1, 3);
            this.baseGrid.set(this.entranceX - 1, this.entranceY, this.entranceX - 1, this.entranceY + 1, 2);
            this.baseGrid.set(this.entranceX + 2, this.entranceY - 2, this.entranceX + 3, this.entranceY + 3, 5);
            this.baseGrid.set(this.entranceX + 1, this.entranceY - 2, this.entranceX + 1, this.entranceY - 1, 1);
            this.baseGrid.set(this.entranceX + 1, this.entranceY + 2, this.entranceX + 1, this.entranceY + 3, 1);
            this.baseGrid.set(this.entranceX - 1, this.entranceY - 1, 1);
            this.baseGrid.set(this.entranceX - 1, this.entranceY + 2, 1);
            this.baseGrid.set(0, 0, 11, 1, 5);
            this.baseGrid.set(0, 9, 11, 11, 5);
            this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY - 2, EnumFacing.WEST, 6);
            this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY + 3, EnumFacing.WEST, 6);
            this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY - 1, EnumFacing.WEST, 3);
            this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY + 2, EnumFacing.WEST, 3);

            while (this.cleanEdges(this.baseGrid))
            {
                ;
            }

            this.floorRooms = new WoodlandMansionPieces.SimpleGrid[3];
            this.floorRooms[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
            this.floorRooms[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
            this.floorRooms[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
            this.identifyRooms(this.baseGrid, this.floorRooms[0]);
            this.identifyRooms(this.baseGrid, this.floorRooms[1]);
            this.floorRooms[0].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
            this.floorRooms[1].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
            this.thirdFloorGrid = new WoodlandMansionPieces.SimpleGrid(this.baseGrid.width, this.baseGrid.height, 5);
            this.setupThirdFloor();
            this.identifyRooms(this.thirdFloorGrid, this.floorRooms[2]);
        }

        public static boolean isHouse(WoodlandMansionPieces.SimpleGrid p_191109_0_, int p_191109_1_, int p_191109_2_)
        {
            int i = p_191109_0_.get(p_191109_1_, p_191109_2_);
            return i == 1 || i == 2 || i == 3 || i == 4;
        }

        public boolean isRoomId(WoodlandMansionPieces.SimpleGrid p_191114_1_, int p_191114_2_, int p_191114_3_, int p_191114_4_, int p_191114_5_)
        {
            return (this.floorRooms[p_191114_4_].get(p_191114_2_, p_191114_3_) & 65535) == p_191114_5_;
        }

        @Nullable
        public EnumFacing get1x2RoomDirection(WoodlandMansionPieces.SimpleGrid p_191113_1_, int p_191113_2_, int p_191113_3_, int p_191113_4_, int p_191113_5_)
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
            {
                if (this.isRoomId(p_191113_1_, p_191113_2_ + enumfacing.getFrontOffsetX(), p_191113_3_ + enumfacing.getFrontOffsetZ(), p_191113_4_, p_191113_5_))
                {
                    return enumfacing;
                }
            }

            return null;
        }

        private void recursiveCorridor(WoodlandMansionPieces.SimpleGrid p_191110_1_, int p_191110_2_, int p_191110_3_, EnumFacing p_191110_4_, int p_191110_5_)
        {
            if (p_191110_5_ > 0)
            {
                p_191110_1_.set(p_191110_2_, p_191110_3_, 1);
                p_191110_1_.setIf(p_191110_2_ + p_191110_4_.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ(), 0, 1);

                for (int i = 0; i < 8; ++i)
                {
                    EnumFacing enumfacing = EnumFacing.getHorizontal(this.random.nextInt(4));

                    if (enumfacing != p_191110_4_.getOpposite() && (enumfacing != EnumFacing.EAST || !this.random.nextBoolean()))
                    {
                        int j = p_191110_2_ + p_191110_4_.getFrontOffsetX();
                        int k = p_191110_3_ + p_191110_4_.getFrontOffsetZ();

                        if (p_191110_1_.get(j + enumfacing.getFrontOffsetX(), k + enumfacing.getFrontOffsetZ()) == 0 && p_191110_1_.get(j + enumfacing.getFrontOffsetX() * 2, k + enumfacing.getFrontOffsetZ() * 2) == 0)
                        {
                            this.recursiveCorridor(p_191110_1_, p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing.getFrontOffsetZ(), enumfacing, p_191110_5_ - 1);
                            break;
                        }
                    }
                }

                EnumFacing enumfacing1 = p_191110_4_.rotateY();
                EnumFacing enumfacing2 = p_191110_4_.rotateYCCW();
                p_191110_1_.setIf(p_191110_2_ + enumfacing1.getFrontOffsetX(), p_191110_3_ + enumfacing1.getFrontOffsetZ(), 0, 2);
                p_191110_1_.setIf(p_191110_2_ + enumfacing2.getFrontOffsetX(), p_191110_3_ + enumfacing2.getFrontOffsetZ(), 0, 2);
                p_191110_1_.setIf(p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing1.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing1.getFrontOffsetZ(), 0, 2);
                p_191110_1_.setIf(p_191110_2_ + p_191110_4_.getFrontOffsetX() + enumfacing2.getFrontOffsetX(), p_191110_3_ + p_191110_4_.getFrontOffsetZ() + enumfacing2.getFrontOffsetZ(), 0, 2);
                p_191110_1_.setIf(p_191110_2_ + p_191110_4_.getFrontOffsetX() * 2, p_191110_3_ + p_191110_4_.getFrontOffsetZ() * 2, 0, 2);
                p_191110_1_.setIf(p_191110_2_ + enumfacing1.getFrontOffsetX() * 2, p_191110_3_ + enumfacing1.getFrontOffsetZ() * 2, 0, 2);
                p_191110_1_.setIf(p_191110_2_ + enumfacing2.getFrontOffsetX() * 2, p_191110_3_ + enumfacing2.getFrontOffsetZ() * 2, 0, 2);
            }
        }

        private boolean cleanEdges(WoodlandMansionPieces.SimpleGrid p_191111_1_)
        {
            boolean flag = false;

            for (int i = 0; i < p_191111_1_.height; ++i)
            {
                for (int j = 0; j < p_191111_1_.width; ++j)
                {
                    if (p_191111_1_.get(j, i) == 0)
                    {
                        int k = 0;
                        k = k + (isHouse(p_191111_1_, j + 1, i) ? 1 : 0);
                        k = k + (isHouse(p_191111_1_, j - 1, i) ? 1 : 0);
                        k = k + (isHouse(p_191111_1_, j, i + 1) ? 1 : 0);
                        k = k + (isHouse(p_191111_1_, j, i - 1) ? 1 : 0);

                        if (k >= 3)
                        {
                            p_191111_1_.set(j, i, 2);
                            flag = true;
                        }
                        else if (k == 2)
                        {
                            int l = 0;
                            l = l + (isHouse(p_191111_1_, j + 1, i + 1) ? 1 : 0);
                            l = l + (isHouse(p_191111_1_, j - 1, i + 1) ? 1 : 0);
                            l = l + (isHouse(p_191111_1_, j + 1, i - 1) ? 1 : 0);
                            l = l + (isHouse(p_191111_1_, j - 1, i - 1) ? 1 : 0);

                            if (l <= 1)
                            {
                                p_191111_1_.set(j, i, 2);
                                flag = true;
                            }
                        }
                    }
                }
            }

            return flag;
        }

        private void setupThirdFloor()
        {
            List<Tuple<Integer, Integer>> list = Lists.<Tuple<Integer, Integer>>newArrayList();
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = this.floorRooms[1];

            for (int i = 0; i < this.thirdFloorGrid.height; ++i)
            {
                for (int j = 0; j < this.thirdFloorGrid.width; ++j)
                {
                    int k = woodlandmansionpieces$simplegrid.get(j, i);
                    int l = k & 983040;

                    if (l == 131072 && (k & 2097152) == 2097152)
                    {
                        list.add(new Tuple(j, i));
                    }
                }
            }

            if (list.isEmpty())
            {
                this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
            }
            else
            {
                Tuple<Integer, Integer> tuple = (Tuple)list.get(this.random.nextInt(list.size()));
                int l1 = woodlandmansionpieces$simplegrid.get(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue());
                woodlandmansionpieces$simplegrid.set(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), l1 | 4194304);
                EnumFacing enumfacing1 = this.get1x2RoomDirection(this.baseGrid, ((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), 1, l1 & 65535);
                int i2 = ((Integer)tuple.getFirst()).intValue() + enumfacing1.getFrontOffsetX();
                int i1 = ((Integer)tuple.getSecond()).intValue() + enumfacing1.getFrontOffsetZ();

                for (int j1 = 0; j1 < this.thirdFloorGrid.height; ++j1)
                {
                    for (int k1 = 0; k1 < this.thirdFloorGrid.width; ++k1)
                    {
                        if (!isHouse(this.baseGrid, k1, j1))
                        {
                            this.thirdFloorGrid.set(k1, j1, 5);
                        }
                        else if (k1 == ((Integer)tuple.getFirst()).intValue() && j1 == ((Integer)tuple.getSecond()).intValue())
                        {
                            this.thirdFloorGrid.set(k1, j1, 3);
                        }
                        else if (k1 == i2 && j1 == i1)
                        {
                            this.thirdFloorGrid.set(k1, j1, 3);
                            this.floorRooms[2].set(k1, j1, 8388608);
                        }
                    }
                }

                List<EnumFacing> list1 = Lists.<EnumFacing>newArrayList();

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
                {
                    if (this.thirdFloorGrid.get(i2 + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()) == 0)
                    {
                        list1.add(enumfacing);
                    }
                }

                if (list1.isEmpty())
                {
                    this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
                    woodlandmansionpieces$simplegrid.set(((Integer)tuple.getFirst()).intValue(), ((Integer)tuple.getSecond()).intValue(), l1);
                }
                else
                {
                    EnumFacing enumfacing2 = list1.get(this.random.nextInt(list1.size()));
                    this.recursiveCorridor(this.thirdFloorGrid, i2 + enumfacing2.getFrontOffsetX(), i1 + enumfacing2.getFrontOffsetZ(), enumfacing2, 4);

                    while (this.cleanEdges(this.thirdFloorGrid))
                    {
                        ;
                    }
                }
            }
        }

        private void identifyRooms(WoodlandMansionPieces.SimpleGrid p_191116_1_, WoodlandMansionPieces.SimpleGrid p_191116_2_)
        {
            List<Tuple<Integer, Integer>> list = Lists.<Tuple<Integer, Integer>>newArrayList();

            for (int i = 0; i < p_191116_1_.height; ++i)
            {
                for (int j = 0; j < p_191116_1_.width; ++j)
                {
                    if (p_191116_1_.get(j, i) == 2)
                    {
                        list.add(new Tuple(j, i));
                    }
                }
            }

            Collections.shuffle(list, this.random);
            int k3 = 10;

            for (Tuple<Integer, Integer> tuple : list)
            {
                int k = ((Integer)tuple.getFirst()).intValue();
                int l = ((Integer)tuple.getSecond()).intValue();

                if (p_191116_2_.get(k, l) == 0)
                {
                    int i1 = k;
                    int j1 = k;
                    int k1 = l;
                    int l1 = l;
                    int i2 = 65536;

                    if (p_191116_2_.get(k + 1, l) == 0 && p_191116_2_.get(k, l + 1) == 0 && p_191116_2_.get(k + 1, l + 1) == 0 && p_191116_1_.get(k + 1, l) == 2 && p_191116_1_.get(k, l + 1) == 2 && p_191116_1_.get(k + 1, l + 1) == 2)
                    {
                        j1 = k + 1;
                        l1 = l + 1;
                        i2 = 262144;
                    }
                    else if (p_191116_2_.get(k - 1, l) == 0 && p_191116_2_.get(k, l + 1) == 0 && p_191116_2_.get(k - 1, l + 1) == 0 && p_191116_1_.get(k - 1, l) == 2 && p_191116_1_.get(k, l + 1) == 2 && p_191116_1_.get(k - 1, l + 1) == 2)
                    {
                        i1 = k - 1;
                        l1 = l + 1;
                        i2 = 262144;
                    }
                    else if (p_191116_2_.get(k - 1, l) == 0 && p_191116_2_.get(k, l - 1) == 0 && p_191116_2_.get(k - 1, l - 1) == 0 && p_191116_1_.get(k - 1, l) == 2 && p_191116_1_.get(k, l - 1) == 2 && p_191116_1_.get(k - 1, l - 1) == 2)
                    {
                        i1 = k - 1;
                        k1 = l - 1;
                        i2 = 262144;
                    }
                    else if (p_191116_2_.get(k + 1, l) == 0 && p_191116_1_.get(k + 1, l) == 2)
                    {
                        j1 = k + 1;
                        i2 = 131072;
                    }
                    else if (p_191116_2_.get(k, l + 1) == 0 && p_191116_1_.get(k, l + 1) == 2)
                    {
                        l1 = l + 1;
                        i2 = 131072;
                    }
                    else if (p_191116_2_.get(k - 1, l) == 0 && p_191116_1_.get(k - 1, l) == 2)
                    {
                        i1 = k - 1;
                        i2 = 131072;
                    }
                    else if (p_191116_2_.get(k, l - 1) == 0 && p_191116_1_.get(k, l - 1) == 2)
                    {
                        k1 = l - 1;
                        i2 = 131072;
                    }

                    int j2 = this.random.nextBoolean() ? i1 : j1;
                    int k2 = this.random.nextBoolean() ? k1 : l1;
                    int l2 = 2097152;

                    if (!p_191116_1_.edgesTo(j2, k2, 1))
                    {
                        j2 = j2 == i1 ? j1 : i1;
                        k2 = k2 == k1 ? l1 : k1;

                        if (!p_191116_1_.edgesTo(j2, k2, 1))
                        {
                            k2 = k2 == k1 ? l1 : k1;

                            if (!p_191116_1_.edgesTo(j2, k2, 1))
                            {
                                j2 = j2 == i1 ? j1 : i1;
                                k2 = k2 == k1 ? l1 : k1;

                                if (!p_191116_1_.edgesTo(j2, k2, 1))
                                {
                                    l2 = 0;
                                    j2 = i1;
                                    k2 = k1;
                                }
                            }
                        }
                    }

                    for (int i3 = k1; i3 <= l1; ++i3)
                    {
                        for (int j3 = i1; j3 <= j1; ++j3)
                        {
                            if (j3 == j2 && i3 == k2)
                            {
                                p_191116_2_.set(j3, i3, 1048576 | l2 | i2 | k3);
                            }
                            else
                            {
                                p_191116_2_.set(j3, i3, i2 | k3);
                            }
                        }
                    }

                    ++k3;
                }
            }
        }
    }

    public static class MansionTemplate extends StructureComponentTemplate
    {
        private String templateName;
        private Rotation rotation;
        private Mirror mirror;

        public MansionTemplate()
        {
        }

        public MansionTemplate(TemplateManager p_i47355_1_, String p_i47355_2_, BlockPos p_i47355_3_, Rotation p_i47355_4_)
        {
            this(p_i47355_1_, p_i47355_2_, p_i47355_3_, p_i47355_4_, Mirror.NONE);
        }

        public MansionTemplate(TemplateManager p_i47356_1_, String p_i47356_2_, BlockPos p_i47356_3_, Rotation p_i47356_4_, Mirror p_i47356_5_)
        {
            super(0);
            this.templateName = p_i47356_2_;
            this.templatePosition = p_i47356_3_;
            this.rotation = p_i47356_4_;
            this.mirror = p_i47356_5_;
            this.loadTemplate(p_i47356_1_);
        }

        private void loadTemplate(TemplateManager p_191081_1_)
        {
            Template template = p_191081_1_.getTemplate((MinecraftServer)null, new ResourceLocation("mansion/" + this.templateName));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).setRotation(this.rotation).setMirror(this.mirror);
            this.setup(template, this.templatePosition, placementsettings);
        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Template", this.templateName);
            tagCompound.setString("Rot", this.placeSettings.getRotation().name());
            tagCompound.setString("Mi", this.placeSettings.getMirror().name());
        }

        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
        {
            super.readStructureFromNBT(tagCompound, p_143011_2_);
            this.templateName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.mirror = Mirror.valueOf(tagCompound.getString("Mi"));
            this.loadTemplate(p_143011_2_);
        }

        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
        {
            if (function.startsWith("Chest"))
            {
                Rotation rotation = this.placeSettings.getRotation();
                IBlockState iblockstate = Blocks.CHEST.getDefaultState();

                if ("ChestWest".equals(function))
                {
                    iblockstate = iblockstate.withProperty(BlockChest.FACING, rotation.rotate(EnumFacing.WEST));
                }
                else if ("ChestEast".equals(function))
                {
                    iblockstate = iblockstate.withProperty(BlockChest.FACING, rotation.rotate(EnumFacing.EAST));
                }
                else if ("ChestSouth".equals(function))
                {
                    iblockstate = iblockstate.withProperty(BlockChest.FACING, rotation.rotate(EnumFacing.SOUTH));
                }
                else if ("ChestNorth".equals(function))
                {
                    iblockstate = iblockstate.withProperty(BlockChest.FACING, rotation.rotate(EnumFacing.NORTH));
                }

                this.generateChest(worldIn, sbb, rand, pos, LootTableList.CHESTS_WOODLAND_MANSION, iblockstate);
            }
            else if ("Mage".equals(function))
            {
                EntityEvoker entityevoker = new EntityEvoker(worldIn);
                entityevoker.enablePersistence();
                entityevoker.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                worldIn.spawnEntity(entityevoker);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
            else if ("Warrior".equals(function))
            {
                EntityVindicator entityvindicator = new EntityVindicator(worldIn);
                entityvindicator.enablePersistence();
                entityvindicator.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                entityvindicator.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityvindicator)), (IEntityLivingData)null);
                worldIn.spawnEntity(entityvindicator);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }

    static class PlacementData
    {
        public Rotation rotation;
        public BlockPos position;
        public String wallType;

        private PlacementData()
        {
        }
    }

    static class Placer
    {
        private final TemplateManager templateManager;
        private final Random random;
        private int startX;
        private int startY;

        public Placer(TemplateManager p_i47361_1_, Random p_i47361_2_)
        {
            this.templateManager = p_i47361_1_;
            this.random = p_i47361_2_;
        }

        public void createMansion(BlockPos p_191125_1_, Rotation p_191125_2_, List<WoodlandMansionPieces.MansionTemplate> p_191125_3_, WoodlandMansionPieces.Grid p_191125_4_)
        {
            WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata = new WoodlandMansionPieces.PlacementData();
            woodlandmansionpieces$placementdata.position = p_191125_1_;
            woodlandmansionpieces$placementdata.rotation = p_191125_2_;
            woodlandmansionpieces$placementdata.wallType = "wall_flat";
            WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata1 = new WoodlandMansionPieces.PlacementData();
            this.entrance(p_191125_3_, woodlandmansionpieces$placementdata);
            woodlandmansionpieces$placementdata1.position = woodlandmansionpieces$placementdata.position.up(8);
            woodlandmansionpieces$placementdata1.rotation = woodlandmansionpieces$placementdata.rotation;
            woodlandmansionpieces$placementdata1.wallType = "wall_window";

            if (!p_191125_3_.isEmpty())
            {
                ;
            }

            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid = p_191125_4_.baseGrid;
            WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid1 = p_191125_4_.thirdFloorGrid;
            this.startX = p_191125_4_.entranceX + 1;
            this.startY = p_191125_4_.entranceY + 1;
            int i = p_191125_4_.entranceX + 1;
            int j = p_191125_4_.entranceY;
            this.traverseOuterWalls(p_191125_3_, woodlandmansionpieces$placementdata, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.startX, this.startY, i, j);
            this.traverseOuterWalls(p_191125_3_, woodlandmansionpieces$placementdata1, woodlandmansionpieces$simplegrid, EnumFacing.SOUTH, this.startX, this.startY, i, j);
            WoodlandMansionPieces.PlacementData woodlandmansionpieces$placementdata2 = new WoodlandMansionPieces.PlacementData();
            woodlandmansionpieces$placementdata2.position = woodlandmansionpieces$placementdata.position.up(19);
            woodlandmansionpieces$placementdata2.rotation = woodlandmansionpieces$placementdata.rotation;
            woodlandmansionpieces$placementdata2.wallType = "wall_window";
            boolean flag = false;

            for (int k = 0; k < woodlandmansionpieces$simplegrid1.height && !flag; ++k)
            {
                for (int l = woodlandmansionpieces$simplegrid1.width - 1; l >= 0 && !flag; --l)
                {
                    if (WoodlandMansionPieces.Grid.isHouse(woodlandmansionpieces$simplegrid1, l, k))
                    {
                        woodlandmansionpieces$placementdata2.position = woodlandmansionpieces$placementdata2.position.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (k - this.startY) * 8);
                        woodlandmansionpieces$placementdata2.position = woodlandmansionpieces$placementdata2.position.offset(p_191125_2_.rotate(EnumFacing.EAST), (l - this.startX) * 8);
                        this.traverseWallPiece(p_191125_3_, woodlandmansionpieces$placementdata2);
                        this.traverseOuterWalls(p_191125_3_, woodlandmansionpieces$placementdata2, woodlandmansionpieces$simplegrid1, EnumFacing.SOUTH, l, k, l, k);
                        flag = true;
                    }
                }
            }

            this.createRoof(p_191125_3_, p_191125_1_.up(16), p_191125_2_, woodlandmansionpieces$simplegrid, woodlandmansionpieces$simplegrid1);
            this.createRoof(p_191125_3_, p_191125_1_.up(27), p_191125_2_, woodlandmansionpieces$simplegrid1, (WoodlandMansionPieces.SimpleGrid)null);

            if (!p_191125_3_.isEmpty())
            {
                ;
            }

            WoodlandMansionPieces.RoomCollection[] awoodlandmansionpieces$roomcollection = new WoodlandMansionPieces.RoomCollection[3];
            awoodlandmansionpieces$roomcollection[0] = new WoodlandMansionPieces.FirstFloor();
            awoodlandmansionpieces$roomcollection[1] = new WoodlandMansionPieces.SecondFloor();
            awoodlandmansionpieces$roomcollection[2] = new WoodlandMansionPieces.ThirdFloor();

            for (int l2 = 0; l2 < 3; ++l2)
            {
                BlockPos blockpos = p_191125_1_.up(8 * l2 + (l2 == 2 ? 3 : 0));
                WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid2 = p_191125_4_.floorRooms[l2];
                WoodlandMansionPieces.SimpleGrid woodlandmansionpieces$simplegrid3 = l2 == 2 ? woodlandmansionpieces$simplegrid1 : woodlandmansionpieces$simplegrid;
                String s = l2 == 0 ? "carpet_south" : "carpet_south_2";
                String s1 = l2 == 0 ? "carpet_west" : "carpet_west_2";

                for (int i1 = 0; i1 < woodlandmansionpieces$simplegrid3.height; ++i1)
                {
                    for (int j1 = 0; j1 < woodlandmansionpieces$simplegrid3.width; ++j1)
                    {
                        if (woodlandmansionpieces$simplegrid3.get(j1, i1) == 1)
                        {
                            BlockPos blockpos1 = blockpos.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (i1 - this.startY) * 8);
                            blockpos1 = blockpos1.offset(p_191125_2_.rotate(EnumFacing.EAST), (j1 - this.startX) * 8);
                            p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "corridor_floor", blockpos1, p_191125_2_));

                            if (woodlandmansionpieces$simplegrid3.get(j1, i1 - 1) == 1 || (woodlandmansionpieces$simplegrid2.get(j1, i1 - 1) & 8388608) == 8388608)
                            {
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "carpet_north", blockpos1.offset(p_191125_2_.rotate(EnumFacing.EAST), 1).up(), p_191125_2_));
                            }

                            if (woodlandmansionpieces$simplegrid3.get(j1 + 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.get(j1 + 1, i1) & 8388608) == 8388608)
                            {
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "carpet_east", blockpos1.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 1).offset(p_191125_2_.rotate(EnumFacing.EAST), 5).up(), p_191125_2_));
                            }

                            if (woodlandmansionpieces$simplegrid3.get(j1, i1 + 1) == 1 || (woodlandmansionpieces$simplegrid2.get(j1, i1 + 1) & 8388608) == 8388608)
                            {
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, s, blockpos1.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 5).offset(p_191125_2_.rotate(EnumFacing.WEST), 1), p_191125_2_));
                            }

                            if (woodlandmansionpieces$simplegrid3.get(j1 - 1, i1) == 1 || (woodlandmansionpieces$simplegrid2.get(j1 - 1, i1) & 8388608) == 8388608)
                            {
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, s1, blockpos1.offset(p_191125_2_.rotate(EnumFacing.WEST), 1).offset(p_191125_2_.rotate(EnumFacing.NORTH), 1), p_191125_2_));
                            }
                        }
                    }
                }

                String s2 = l2 == 0 ? "indoors_wall" : "indoors_wall_2";
                String s3 = l2 == 0 ? "indoors_door" : "indoors_door_2";
                List<EnumFacing> list = Lists.<EnumFacing>newArrayList();

                for (int k1 = 0; k1 < woodlandmansionpieces$simplegrid3.height; ++k1)
                {
                    for (int l1 = 0; l1 < woodlandmansionpieces$simplegrid3.width; ++l1)
                    {
                        boolean flag1 = l2 == 2 && woodlandmansionpieces$simplegrid3.get(l1, k1) == 3;

                        if (woodlandmansionpieces$simplegrid3.get(l1, k1) == 2 || flag1)
                        {
                            int i2 = woodlandmansionpieces$simplegrid2.get(l1, k1);
                            int j2 = i2 & 983040;
                            int k2 = i2 & 65535;
                            flag1 = flag1 && (i2 & 8388608) == 8388608;
                            list.clear();

                            if ((i2 & 2097152) == 2097152)
                            {
                                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings())
                                {
                                    if (woodlandmansionpieces$simplegrid3.get(l1 + enumfacing.getFrontOffsetX(), k1 + enumfacing.getFrontOffsetZ()) == 1)
                                    {
                                        list.add(enumfacing);
                                    }
                                }
                            }

                            EnumFacing enumfacing1 = null;

                            if (!list.isEmpty())
                            {
                                enumfacing1 = list.get(this.random.nextInt(list.size()));
                            }
                            else if ((i2 & 1048576) == 1048576)
                            {
                                enumfacing1 = EnumFacing.UP;
                            }

                            BlockPos blockpos2 = blockpos.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 8 + (k1 - this.startY) * 8);
                            blockpos2 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.EAST), -1 + (l1 - this.startX) * 8);

                            if (WoodlandMansionPieces.Grid.isHouse(woodlandmansionpieces$simplegrid3, l1 - 1, k1) && !p_191125_4_.isRoomId(woodlandmansionpieces$simplegrid3, l1 - 1, k1, l2, k2))
                            {
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, enumfacing1 == EnumFacing.WEST ? s3 : s2, blockpos2, p_191125_2_));
                            }

                            if (woodlandmansionpieces$simplegrid3.get(l1 + 1, k1) == 1 && !flag1)
                            {
                                BlockPos blockpos3 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.EAST), 8);
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, enumfacing1 == EnumFacing.EAST ? s3 : s2, blockpos3, p_191125_2_));
                            }

                            if (WoodlandMansionPieces.Grid.isHouse(woodlandmansionpieces$simplegrid3, l1, k1 + 1) && !p_191125_4_.isRoomId(woodlandmansionpieces$simplegrid3, l1, k1 + 1, l2, k2))
                            {
                                BlockPos blockpos4 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.SOUTH), 7);
                                blockpos4 = blockpos4.offset(p_191125_2_.rotate(EnumFacing.EAST), 7);
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, enumfacing1 == EnumFacing.SOUTH ? s3 : s2, blockpos4, p_191125_2_.add(Rotation.CLOCKWISE_90)));
                            }

                            if (woodlandmansionpieces$simplegrid3.get(l1, k1 - 1) == 1 && !flag1)
                            {
                                BlockPos blockpos5 = blockpos2.offset(p_191125_2_.rotate(EnumFacing.NORTH), 1);
                                blockpos5 = blockpos5.offset(p_191125_2_.rotate(EnumFacing.EAST), 7);
                                p_191125_3_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, enumfacing1 == EnumFacing.NORTH ? s3 : s2, blockpos5, p_191125_2_.add(Rotation.CLOCKWISE_90)));
                            }

                            if (j2 == 65536)
                            {
                                this.addRoom1x1(p_191125_3_, blockpos2, p_191125_2_, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
                            }
                            else if (j2 == 131072 && enumfacing1 != null)
                            {
                                EnumFacing enumfacing3 = p_191125_4_.get1x2RoomDirection(woodlandmansionpieces$simplegrid3, l1, k1, l2, k2);
                                boolean flag2 = (i2 & 4194304) == 4194304;
                                this.addRoom1x2(p_191125_3_, blockpos2, p_191125_2_, enumfacing3, enumfacing1, awoodlandmansionpieces$roomcollection[l2], flag2);
                            }
                            else if (j2 == 262144 && enumfacing1 != null && enumfacing1 != EnumFacing.UP)
                            {
                                EnumFacing enumfacing2 = enumfacing1.rotateY();

                                if (!p_191125_4_.isRoomId(woodlandmansionpieces$simplegrid3, l1 + enumfacing2.getFrontOffsetX(), k1 + enumfacing2.getFrontOffsetZ(), l2, k2))
                                {
                                    enumfacing2 = enumfacing2.getOpposite();
                                }

                                this.addRoom2x2(p_191125_3_, blockpos2, p_191125_2_, enumfacing2, enumfacing1, awoodlandmansionpieces$roomcollection[l2]);
                            }
                            else if (j2 == 262144 && enumfacing1 == EnumFacing.UP)
                            {
                                this.addRoom2x2Secret(p_191125_3_, blockpos2, p_191125_2_, awoodlandmansionpieces$roomcollection[l2]);
                            }
                        }
                    }
                }
            }
        }

        private void traverseOuterWalls(List<WoodlandMansionPieces.MansionTemplate> p_191130_1_, WoodlandMansionPieces.PlacementData p_191130_2_, WoodlandMansionPieces.SimpleGrid p_191130_3_, EnumFacing p_191130_4_, int p_191130_5_, int p_191130_6_, int p_191130_7_, int p_191130_8_)
        {
            int i = p_191130_5_;
            int j = p_191130_6_;
            EnumFacing enumfacing = p_191130_4_;

            while (true)
            {
                if (!WoodlandMansionPieces.Grid.isHouse(p_191130_3_, i + p_191130_4_.getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ()))
                {
                    this.traverseTurn(p_191130_1_, p_191130_2_);
                    p_191130_4_ = p_191130_4_.rotateY();

                    if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_)
                    {
                        this.traverseWallPiece(p_191130_1_, p_191130_2_);
                    }
                }
                else if (WoodlandMansionPieces.Grid.isHouse(p_191130_3_, i + p_191130_4_.getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ()) && WoodlandMansionPieces.Grid.isHouse(p_191130_3_, i + p_191130_4_.getFrontOffsetX() + p_191130_4_.rotateYCCW().getFrontOffsetX(), j + p_191130_4_.getFrontOffsetZ() + p_191130_4_.rotateYCCW().getFrontOffsetZ()))
                {
                    this.traverseInnerTurn(p_191130_1_, p_191130_2_);
                    i += p_191130_4_.getFrontOffsetX();
                    j += p_191130_4_.getFrontOffsetZ();
                    p_191130_4_ = p_191130_4_.rotateYCCW();
                }
                else
                {
                    i += p_191130_4_.getFrontOffsetX();
                    j += p_191130_4_.getFrontOffsetZ();

                    if (i != p_191130_7_ || j != p_191130_8_ || enumfacing != p_191130_4_)
                    {
                        this.traverseWallPiece(p_191130_1_, p_191130_2_);
                    }
                }

                if (i == p_191130_7_ && j == p_191130_8_ && enumfacing == p_191130_4_)
                {
                    break;
                }
            }
        }

        private void createRoof(List<WoodlandMansionPieces.MansionTemplate> p_191123_1_, BlockPos p_191123_2_, Rotation p_191123_3_, WoodlandMansionPieces.SimpleGrid p_191123_4_, @Nullable WoodlandMansionPieces.SimpleGrid p_191123_5_)
        {
            for (int i = 0; i < p_191123_4_.height; ++i)
            {
                for (int j = 0; j < p_191123_4_.width; ++j)
                {
                    BlockPos lvt_8_3_ = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (i - this.startY) * 8);
                    lvt_8_3_ = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), (j - this.startX) * 8);
                    boolean flag = p_191123_5_ != null && WoodlandMansionPieces.Grid.isHouse(p_191123_5_, j, i);

                    if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j, i) && !flag)
                    {
                        p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof", lvt_8_3_.up(3), p_191123_3_));

                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j + 1, i))
                        {
                            BlockPos blockpos1 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
                            p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_front", blockpos1, p_191123_3_));
                        }

                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j - 1, i))
                        {
                            BlockPos blockpos5 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 0);
                            blockpos5 = blockpos5.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
                            p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_front", blockpos5, p_191123_3_.add(Rotation.CLOCKWISE_180)));
                        }

                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j, i - 1))
                        {
                            BlockPos blockpos6 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
                            p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_front", blockpos6, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
                        }

                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j, i + 1))
                        {
                            BlockPos blockpos7 = lvt_8_3_.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
                            blockpos7 = blockpos7.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
                            p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_front", blockpos7, p_191123_3_.add(Rotation.CLOCKWISE_90)));
                        }
                    }
                }
            }

            if (p_191123_5_ != null)
            {
                for (int k = 0; k < p_191123_4_.height; ++k)
                {
                    for (int i1 = 0; i1 < p_191123_4_.width; ++i1)
                    {
                        BlockPos blockpos3 = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (k - this.startY) * 8);
                        blockpos3 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), (i1 - this.startX) * 8);
                        boolean flag1 = WoodlandMansionPieces.Grid.isHouse(p_191123_5_, i1, k);

                        if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k) && flag1)
                        {
                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1 + 1, k))
                            {
                                BlockPos blockpos8 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 7);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall", blockpos8, p_191123_3_));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1 - 1, k))
                            {
                                BlockPos blockpos9 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
                                blockpos9 = blockpos9.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall", blockpos9, p_191123_3_.add(Rotation.CLOCKWISE_180)));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k - 1))
                            {
                                BlockPos blockpos10 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 0);
                                blockpos10 = blockpos10.offset(p_191123_3_.rotate(EnumFacing.NORTH), 1);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall", blockpos10, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k + 1))
                            {
                                BlockPos blockpos11 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);
                                blockpos11 = blockpos11.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall", blockpos11, p_191123_3_.add(Rotation.CLOCKWISE_90)));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1 + 1, k))
                            {
                                if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k - 1))
                                {
                                    BlockPos blockpos12 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 7);
                                    blockpos12 = blockpos12.offset(p_191123_3_.rotate(EnumFacing.NORTH), 2);
                                    p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall_corner", blockpos12, p_191123_3_));
                                }

                                if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k + 1))
                                {
                                    BlockPos blockpos13 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.EAST), 8);
                                    blockpos13 = blockpos13.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 7);
                                    p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall_corner", blockpos13, p_191123_3_.add(Rotation.CLOCKWISE_90)));
                                }
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1 - 1, k))
                            {
                                if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k - 1))
                                {
                                    BlockPos blockpos14 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 2);
                                    blockpos14 = blockpos14.offset(p_191123_3_.rotate(EnumFacing.NORTH), 1);
                                    p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall_corner", blockpos14, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
                                }

                                if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, i1, k + 1))
                                {
                                    BlockPos blockpos15 = blockpos3.offset(p_191123_3_.rotate(EnumFacing.WEST), 1);
                                    blockpos15 = blockpos15.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8);
                                    p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "small_wall_corner", blockpos15, p_191123_3_.add(Rotation.CLOCKWISE_180)));
                                }
                            }
                        }
                    }
                }
            }

            for (int l = 0; l < p_191123_4_.height; ++l)
            {
                for (int j1 = 0; j1 < p_191123_4_.width; ++j1)
                {
                    BlockPos blockpos4 = p_191123_2_.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8 + (l - this.startY) * 8);
                    blockpos4 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), (j1 - this.startX) * 8);
                    boolean flag2 = p_191123_5_ != null && WoodlandMansionPieces.Grid.isHouse(p_191123_5_, j1, l);

                    if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1, l) && !flag2)
                    {
                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 + 1, l))
                        {
                            BlockPos blockpos16 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 6);

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1, l + 1))
                            {
                                BlockPos blockpos2 = blockpos16.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_corner", blockpos2, p_191123_3_));
                            }
                            else if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 + 1, l + 1))
                            {
                                BlockPos blockpos18 = blockpos16.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 5);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_inner_corner", blockpos18, p_191123_3_));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1, l - 1))
                            {
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_corner", blockpos16, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
                            }
                            else if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 + 1, l - 1))
                            {
                                BlockPos blockpos19 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 9);
                                blockpos19 = blockpos19.offset(p_191123_3_.rotate(EnumFacing.NORTH), 2);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_inner_corner", blockpos19, p_191123_3_.add(Rotation.CLOCKWISE_90)));
                            }
                        }

                        if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 - 1, l))
                        {
                            BlockPos blockpos17 = blockpos4.offset(p_191123_3_.rotate(EnumFacing.EAST), 0);
                            blockpos17 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 0);

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1, l + 1))
                            {
                                BlockPos blockpos20 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 6);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_corner", blockpos20, p_191123_3_.add(Rotation.CLOCKWISE_90)));
                            }
                            else if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 - 1, l + 1))
                            {
                                BlockPos blockpos21 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 8);
                                blockpos21 = blockpos21.offset(p_191123_3_.rotate(EnumFacing.WEST), 3);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_inner_corner", blockpos21, p_191123_3_.add(Rotation.COUNTERCLOCKWISE_90)));
                            }

                            if (!WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1, l - 1))
                            {
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_corner", blockpos17, p_191123_3_.add(Rotation.CLOCKWISE_180)));
                            }
                            else if (WoodlandMansionPieces.Grid.isHouse(p_191123_4_, j1 - 1, l - 1))
                            {
                                BlockPos blockpos22 = blockpos17.offset(p_191123_3_.rotate(EnumFacing.SOUTH), 1);
                                p_191123_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "roof_inner_corner", blockpos22, p_191123_3_.add(Rotation.CLOCKWISE_180)));
                            }
                        }
                    }
                }
            }
        }

        private void entrance(List<WoodlandMansionPieces.MansionTemplate> p_191133_1_, WoodlandMansionPieces.PlacementData p_191133_2_)
        {
            EnumFacing enumfacing = p_191133_2_.rotation.rotate(EnumFacing.WEST);
            p_191133_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "entrance", p_191133_2_.position.offset(enumfacing, 9), p_191133_2_.rotation));
            p_191133_2_.position = p_191133_2_.position.offset(p_191133_2_.rotation.rotate(EnumFacing.SOUTH), 16);
        }

        private void traverseWallPiece(List<WoodlandMansionPieces.MansionTemplate> p_191131_1_, WoodlandMansionPieces.PlacementData p_191131_2_)
        {
            p_191131_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191131_2_.wallType, p_191131_2_.position.offset(p_191131_2_.rotation.rotate(EnumFacing.EAST), 7), p_191131_2_.rotation));
            p_191131_2_.position = p_191131_2_.position.offset(p_191131_2_.rotation.rotate(EnumFacing.SOUTH), 8);
        }

        private void traverseTurn(List<WoodlandMansionPieces.MansionTemplate> p_191124_1_, WoodlandMansionPieces.PlacementData p_191124_2_)
        {
            p_191124_2_.position = p_191124_2_.position.offset(p_191124_2_.rotation.rotate(EnumFacing.SOUTH), -1);
            p_191124_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, "wall_corner", p_191124_2_.position, p_191124_2_.rotation));
            p_191124_2_.position = p_191124_2_.position.offset(p_191124_2_.rotation.rotate(EnumFacing.SOUTH), -7);
            p_191124_2_.position = p_191124_2_.position.offset(p_191124_2_.rotation.rotate(EnumFacing.WEST), -6);
            p_191124_2_.rotation = p_191124_2_.rotation.add(Rotation.CLOCKWISE_90);
        }

        private void traverseInnerTurn(List<WoodlandMansionPieces.MansionTemplate> p_191126_1_, WoodlandMansionPieces.PlacementData p_191126_2_)
        {
            p_191126_2_.position = p_191126_2_.position.offset(p_191126_2_.rotation.rotate(EnumFacing.SOUTH), 6);
            p_191126_2_.position = p_191126_2_.position.offset(p_191126_2_.rotation.rotate(EnumFacing.EAST), 8);
            p_191126_2_.rotation = p_191126_2_.rotation.add(Rotation.COUNTERCLOCKWISE_90);
        }

        private void addRoom1x1(List<WoodlandMansionPieces.MansionTemplate> p_191129_1_, BlockPos p_191129_2_, Rotation p_191129_3_, EnumFacing p_191129_4_, WoodlandMansionPieces.RoomCollection p_191129_5_)
        {
            Rotation rotation = Rotation.NONE;
            String s = p_191129_5_.get1x1(this.random);

            if (p_191129_4_ != EnumFacing.EAST)
            {
                if (p_191129_4_ == EnumFacing.NORTH)
                {
                    rotation = rotation.add(Rotation.COUNTERCLOCKWISE_90);
                }
                else if (p_191129_4_ == EnumFacing.WEST)
                {
                    rotation = rotation.add(Rotation.CLOCKWISE_180);
                }
                else if (p_191129_4_ == EnumFacing.SOUTH)
                {
                    rotation = rotation.add(Rotation.CLOCKWISE_90);
                }
                else
                {
                    s = p_191129_5_.get1x1Secret(this.random);
                }
            }

            BlockPos blockpos = Template.getZeroPositionWithTransform(new BlockPos(1, 0, 0), Mirror.NONE, rotation, 7, 7);
            rotation = rotation.add(p_191129_3_);
            blockpos = blockpos.rotate(p_191129_3_);
            BlockPos blockpos1 = p_191129_2_.add(blockpos.getX(), 0, blockpos.getZ());
            p_191129_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, s, blockpos1, rotation));
        }

        private void addRoom1x2(List<WoodlandMansionPieces.MansionTemplate> p_191132_1_, BlockPos p_191132_2_, Rotation p_191132_3_, EnumFacing p_191132_4_, EnumFacing p_191132_5_, WoodlandMansionPieces.RoomCollection p_191132_6_, boolean p_191132_7_)
        {
            if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.SOUTH)
            {
                BlockPos blockpos13 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos13, p_191132_3_));
            }
            else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.NORTH)
            {
                BlockPos blockpos12 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                blockpos12 = blockpos12.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos12, p_191132_3_, Mirror.LEFT_RIGHT));
            }
            else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.NORTH)
            {
                BlockPos blockpos11 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
                blockpos11 = blockpos11.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos11, p_191132_3_.add(Rotation.CLOCKWISE_180)));
            }
            else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.SOUTH)
            {
                BlockPos blockpos10 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos10, p_191132_3_, Mirror.FRONT_BACK));
            }
            else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.EAST)
            {
                BlockPos blockpos9 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos9, p_191132_3_.add(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
            }
            else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.WEST)
            {
                BlockPos blockpos8 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos8, p_191132_3_.add(Rotation.CLOCKWISE_90)));
            }
            else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.WEST)
            {
                BlockPos blockpos7 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
                blockpos7 = blockpos7.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos7, p_191132_3_.add(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
            }
            else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.EAST)
            {
                BlockPos blockpos6 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                blockpos6 = blockpos6.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2SideEntrance(this.random, p_191132_7_), blockpos6, p_191132_3_.add(Rotation.COUNTERCLOCKWISE_90)));
            }
            else if (p_191132_5_ == EnumFacing.SOUTH && p_191132_4_ == EnumFacing.NORTH)
            {
                BlockPos blockpos5 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                blockpos5 = blockpos5.offset(p_191132_3_.rotate(EnumFacing.NORTH), 8);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2FrontEntrance(this.random, p_191132_7_), blockpos5, p_191132_3_));
            }
            else if (p_191132_5_ == EnumFacing.NORTH && p_191132_4_ == EnumFacing.SOUTH)
            {
                BlockPos blockpos4 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 7);
                blockpos4 = blockpos4.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 14);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2FrontEntrance(this.random, p_191132_7_), blockpos4, p_191132_3_.add(Rotation.CLOCKWISE_180)));
            }
            else if (p_191132_5_ == EnumFacing.WEST && p_191132_4_ == EnumFacing.EAST)
            {
                BlockPos blockpos3 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 15);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2FrontEntrance(this.random, p_191132_7_), blockpos3, p_191132_3_.add(Rotation.CLOCKWISE_90)));
            }
            else if (p_191132_5_ == EnumFacing.EAST && p_191132_4_ == EnumFacing.WEST)
            {
                BlockPos blockpos2 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.WEST), 7);
                blockpos2 = blockpos2.offset(p_191132_3_.rotate(EnumFacing.SOUTH), 6);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2FrontEntrance(this.random, p_191132_7_), blockpos2, p_191132_3_.add(Rotation.COUNTERCLOCKWISE_90)));
            }
            else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.EAST)
            {
                BlockPos blockpos1 = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 15);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2Secret(this.random), blockpos1, p_191132_3_.add(Rotation.CLOCKWISE_90)));
            }
            else if (p_191132_5_ == EnumFacing.UP && p_191132_4_ == EnumFacing.SOUTH)
            {
                BlockPos blockpos = p_191132_2_.offset(p_191132_3_.rotate(EnumFacing.EAST), 1);
                blockpos = blockpos.offset(p_191132_3_.rotate(EnumFacing.NORTH), 0);
                p_191132_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191132_6_.get1x2Secret(this.random), blockpos, p_191132_3_));
            }
        }

        private void addRoom2x2(List<WoodlandMansionPieces.MansionTemplate> p_191127_1_, BlockPos p_191127_2_, Rotation p_191127_3_, EnumFacing p_191127_4_, EnumFacing p_191127_5_, WoodlandMansionPieces.RoomCollection p_191127_6_)
        {
            int i = 0;
            int j = 0;
            Rotation rotation = p_191127_3_;
            Mirror mirror = Mirror.NONE;

            if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.SOUTH)
            {
                i = -7;
            }
            else if (p_191127_5_ == EnumFacing.EAST && p_191127_4_ == EnumFacing.NORTH)
            {
                i = -7;
                j = 6;
                mirror = Mirror.LEFT_RIGHT;
            }
            else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.EAST)
            {
                i = 1;
                j = 14;
                rotation = p_191127_3_.add(Rotation.COUNTERCLOCKWISE_90);
            }
            else if (p_191127_5_ == EnumFacing.NORTH && p_191127_4_ == EnumFacing.WEST)
            {
                i = 7;
                j = 14;
                rotation = p_191127_3_.add(Rotation.COUNTERCLOCKWISE_90);
                mirror = Mirror.LEFT_RIGHT;
            }
            else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.WEST)
            {
                i = 7;
                j = -8;
                rotation = p_191127_3_.add(Rotation.CLOCKWISE_90);
            }
            else if (p_191127_5_ == EnumFacing.SOUTH && p_191127_4_ == EnumFacing.EAST)
            {
                i = 1;
                j = -8;
                rotation = p_191127_3_.add(Rotation.CLOCKWISE_90);
                mirror = Mirror.LEFT_RIGHT;
            }
            else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.NORTH)
            {
                i = 15;
                j = 6;
                rotation = p_191127_3_.add(Rotation.CLOCKWISE_180);
            }
            else if (p_191127_5_ == EnumFacing.WEST && p_191127_4_ == EnumFacing.SOUTH)
            {
                i = 15;
                mirror = Mirror.FRONT_BACK;
            }

            BlockPos blockpos = p_191127_2_.offset(p_191127_3_.rotate(EnumFacing.EAST), i);
            blockpos = blockpos.offset(p_191127_3_.rotate(EnumFacing.SOUTH), j);
            p_191127_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191127_6_.get2x2(this.random), blockpos, rotation, mirror));
        }

        private void addRoom2x2Secret(List<WoodlandMansionPieces.MansionTemplate> p_191128_1_, BlockPos p_191128_2_, Rotation p_191128_3_, WoodlandMansionPieces.RoomCollection p_191128_4_)
        {
            BlockPos blockpos = p_191128_2_.offset(p_191128_3_.rotate(EnumFacing.EAST), 1);
            p_191128_1_.add(new WoodlandMansionPieces.MansionTemplate(this.templateManager, p_191128_4_.get2x2Secret(this.random), blockpos, p_191128_3_, Mirror.NONE));
        }
    }

    abstract static class RoomCollection
    {
        private RoomCollection()
        {
        }

        public abstract String get1x1(Random p_191104_1_);

        public abstract String get1x1Secret(Random p_191099_1_);

        public abstract String get1x2SideEntrance(Random p_191100_1_, boolean p_191100_2_);

        public abstract String get1x2FrontEntrance(Random p_191098_1_, boolean p_191098_2_);

        public abstract String get1x2Secret(Random p_191102_1_);

        public abstract String get2x2(Random p_191101_1_);

        public abstract String get2x2Secret(Random p_191103_1_);
    }

    static class SecondFloor extends WoodlandMansionPieces.RoomCollection
    {
        private SecondFloor()
        {
        }

        public String get1x1(Random p_191104_1_)
        {
            return "1x1_b" + (p_191104_1_.nextInt(4) + 1);
        }

        public String get1x1Secret(Random p_191099_1_)
        {
            return "1x1_as" + (p_191099_1_.nextInt(4) + 1);
        }

        public String get1x2SideEntrance(Random p_191100_1_, boolean p_191100_2_)
        {
            return p_191100_2_ ? "1x2_c_stairs" : "1x2_c" + (p_191100_1_.nextInt(4) + 1);
        }

        public String get1x2FrontEntrance(Random p_191098_1_, boolean p_191098_2_)
        {
            return p_191098_2_ ? "1x2_d_stairs" : "1x2_d" + (p_191098_1_.nextInt(5) + 1);
        }

        public String get1x2Secret(Random p_191102_1_)
        {
            return "1x2_se" + (p_191102_1_.nextInt(1) + 1);
        }

        public String get2x2(Random p_191101_1_)
        {
            return "2x2_b" + (p_191101_1_.nextInt(5) + 1);
        }

        public String get2x2Secret(Random p_191103_1_)
        {
            return "2x2_s1";
        }
    }

    static class SimpleGrid
    {
        private final int[][] grid;
        private final int width;
        private final int height;
        private final int valueIfOutside;

        public SimpleGrid(int p_i47358_1_, int p_i47358_2_, int p_i47358_3_)
        {
            this.width = p_i47358_1_;
            this.height = p_i47358_2_;
            this.valueIfOutside = p_i47358_3_;
            this.grid = new int[p_i47358_1_][p_i47358_2_];
        }

        public void set(int p_191144_1_, int p_191144_2_, int p_191144_3_)
        {
            if (p_191144_1_ >= 0 && p_191144_1_ < this.width && p_191144_2_ >= 0 && p_191144_2_ < this.height)
            {
                this.grid[p_191144_1_][p_191144_2_] = p_191144_3_;
            }
        }

        public void set(int p_191142_1_, int p_191142_2_, int p_191142_3_, int p_191142_4_, int p_191142_5_)
        {
            for (int i = p_191142_2_; i <= p_191142_4_; ++i)
            {
                for (int j = p_191142_1_; j <= p_191142_3_; ++j)
                {
                    this.set(j, i, p_191142_5_);
                }
            }
        }

        public int get(int p_191145_1_, int p_191145_2_)
        {
            return p_191145_1_ >= 0 && p_191145_1_ < this.width && p_191145_2_ >= 0 && p_191145_2_ < this.height ? this.grid[p_191145_1_][p_191145_2_] : this.valueIfOutside;
        }

        public void setIf(int p_191141_1_, int p_191141_2_, int p_191141_3_, int p_191141_4_)
        {
            if (this.get(p_191141_1_, p_191141_2_) == p_191141_3_)
            {
                this.set(p_191141_1_, p_191141_2_, p_191141_4_);
            }
        }

        public boolean edgesTo(int p_191147_1_, int p_191147_2_, int p_191147_3_)
        {
            return this.get(p_191147_1_ - 1, p_191147_2_) == p_191147_3_ || this.get(p_191147_1_ + 1, p_191147_2_) == p_191147_3_ || this.get(p_191147_1_, p_191147_2_ + 1) == p_191147_3_ || this.get(p_191147_1_, p_191147_2_ - 1) == p_191147_3_;
        }
    }

    static class ThirdFloor extends WoodlandMansionPieces.SecondFloor
    {
        private ThirdFloor()
        {
        }
    }
}
