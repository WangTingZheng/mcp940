package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.IChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ExtendedBlockStorage NULL_BLOCK_STORAGE = null;

    /**
     * Used to store block IDs, block MSBs, Sky-light maps, Block-light maps, and metadata. Each entry corresponds to a
     * logical segment of 16x16x16 blocks, stacked vertically.
     * 通常被用来存储方块id，方块msbs，全局光照maps，局部光照maps，还有一些元数据，每一个入口对应一个16*16*16的方块段，垂直堆叠（也就是（16*16）*16*16）
     * 就是一个section数组
     */
    private final ExtendedBlockStorage[] storageArrays;

    /**
     * Contains a 16x16 mapping on the X/Z plane of the biome ID to which each colum belongs.
     * 包含在每个柱所属的生物群落ID的X/Z平面上的16x16映射。
     * 包含每个区块的生物群系ID，XZ平面上的16x16个ID。
     */
    private final byte[] blockBiomeArray;

    /**
     * A map, similar to heightMap, that tracks how far down precipitation can fall.
     * 一个类似于heightMap的map，记录降水下降的程度
     */
    private final int[] precipitationHeightMap;

    /**
     * Which columns need their skylightMaps updated.
     * 哪些列需要更新全局光照
     * */
    private final boolean[] updateSkylightColumns;

    /**
     * Whether or not this Chunk is currently loaded into the World
     * 记录当前这个chunk是否被加载进这个世界
     * */
    private boolean loaded;

    /**
     * Reference to the World object.
     * 世界对象的引用
     * */
    private final World world;
    /*
    * 一个256长度的数组，因为一个chunk的底边是256个方块的
     */
    private final int[] heightMap;

    /**
     * The x coordinate of the chunk.
     * 这个chunk的x轴
     * */
    public final int x;

    /** The z coordinate of the chunk.
     * 这个chunk的z轴
     * */
    public final int z;
    private boolean isGapLightingUpdated; //间隙照明是否更新
    /*
    * 平铺实体列表，宝箱之类的
     */
    private final Map<BlockPos, TileEntity> tileEntities;
    /*
    * 实体列表，比如一些（mob）怪物
     */
    private final ClassInheritanceMultiMap<Entity>[] entityLists;

    /** Boolean value indicating if the terrain is populated. */
    private boolean isTerrainPopulated;
    private boolean isLightPopulated;
    private boolean ticked;

    /**
     * Set to true if the chunk has been modified and needs to be updated internally.
     * 如果块已被修改并需要内部更新，则设置为true。
     */
    private boolean dirty;

    /**
     * Whether this Chunk has any Entities and thus requires saving on every tick
     * 此区块是否有任何实体，因此每次勾选都需要保存
     */
    private boolean hasEntities;

    /**
     * The time according to World.worldTime when this chunk was last saved
     * 根据World.worldTime的上次保存此块的时间
     * */
    private long lastSaveTime;

    /**
     * Lowest value in the heightmap.
     * 在heightmap列表中的最低值
     * */
    private int heightMapMinimum;

    /**
     * the cumulative number of ticks players have been in this chunk
     * 在这个chunk中，ticks 玩家的累计数值
     * */
    private long inhabitedTime;

    /**
     * Contains the current round-robin relight check index, and is implied as the relight check location as well.
     * 包含当前的循环重新点火检查索引，并暗示为重新点火检查位置。
     */
    private int queuedLightChecks;
    private final ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
    public boolean unloadQueued;

    /**
     * 创建一个空的chunk对象，也就是只是新建，不赋值
     * @param worldIn 世界对象
     * @param x chunk的x坐标
     * @param z chunk的z坐标
     */
    public Chunk(World worldIn, int x, int z)
    {
        this.storageArrays = new ExtendedBlockStorage[16];   //初始化section的list
        this.blockBiomeArray = new byte[256]; //
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.tileEntities = Maps.<BlockPos, TileEntity>newHashMap();
        this.queuedLightChecks = 4096;
        this.tileEntityPosQueue = Queues.<BlockPos>newConcurrentLinkedQueue();
        this.entityLists = (ClassInheritanceMultiMap[])(new ClassInheritanceMultiMap[16]);
        this.world = worldIn;
        this.x = x;
        this.z = z;
        this.heightMap = new int[256];

        for (int i = 0; i < this.entityLists.length; ++i)
        {
            this.entityLists[i] = new ClassInheritanceMultiMap(Entity.class);
        }

        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte) - 1);
    }

    /**
     * 从ChunkPrimer中获取方块状态，把它们填入到所对应的chunk中
     * @param worldIn chunk所在的世界
     * @param primer ChunkPrimer变量
     * @param x chunk的x轴位置
     * @param z chunk的z轴位置，为什么没有y呢？因为以一个chunk来讲，他们是平面的
     */
    public Chunk(World worldIn, ChunkPrimer primer, int x, int z)
    {
        this(worldIn, x, z); //调用上一个构造方法，初始化各个变量
        int i = 256;
        boolean flag = worldIn.provider.hasSkyLight();

        //其本质是初始化chunk里所有区块的状态
        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                for (int l = 0; l < 256; ++l) //遍历一个chunk
                {
                    IBlockState iblockstate = primer.getBlockState(j, l, k);  //获取一个方块状态

                    if (iblockstate.getMaterial() != Material.AIR)  //如果这个方块不是空气
                    {
                        int i1 = l >> 4; //一个chunk是256/16个section，此处是根据block的位置算出它在哪一个section

                        if (this.storageArrays[i1] == NULL_BLOCK_STORAGE) //如果这个section为空
                        {
                            this.storageArrays[i1] = new ExtendedBlockStorage(i1 << 4, flag);  //根据section的id计算出能代表一个section的y的值，先右移再左移相当于取整了
                        }

                        this.storageArrays[i1].set(j, l & 15, k, iblockstate); //设置方块状态，&上15是为了获得这一个区块在它所在section的位置
                    }
                }
            }
        }
    }

    /**
     * Checks whether the chunk is at the X/Z location specified
     * 判断这个chunk是否在所给定的坐标点上
     */
    public boolean isAtLocation(int x, int z)
    {
        return x == this.x && z == this.z;
    }

    /**
     * 从方块坐标获得方块高度，由于方块坐标不一定在0-15之间，所以需要取&来保证
     * @param pos 方块坐标
     * @return 高度
     */
    public int getHeight(BlockPos pos)
    {
        return this.getHeightValue(pos.getX() & 15, pos.getZ() & 15);
    }

    /**
     * Returns the value in the height map at this x, z coordinate in the chunk
     * 获取x，z的地形高度
     */
    public int getHeightValue(int x, int z)
    {
        return this.heightMap[z << 4 | x]; //把x,z转化为0-255的值，也就是说找到一个方块的坐标，转化为它的序号
    }

    /**
     * 获得最上面的非空section
     * @return
     */
    @Nullable
    private ExtendedBlockStorage getLastExtendedBlockStorage()
    {
        for (int i = this.storageArrays.length - 1; i >= 0; --i)  //从上到下遍历chunk里的section
        {
            if (this.storageArrays[i] != NULL_BLOCK_STORAGE) //如果此section非空
            {
                return this.storageArrays[i]; //就返回该section
            }
        }

        return null; //如果没找到，就返回空
    }

    /**
     * Returns the topmost ExtendedBlockStorage instance for this Chunk that actually contains a block.
     * 返回此实际包含块的块的最顶层ExtendedBlockStorage实例。
     * 返回的是能代表它的y坐标
     */
    public int getTopFilledSegment()
    {
        ExtendedBlockStorage extendedblockstorage = this.getLastExtendedBlockStorage();
        return extendedblockstorage == null ? 0 : extendedblockstorage.getYLocation();
    }

    /**
     * Returns the ExtendedBlockStorage array for this Chunk.
     * 从chunk返回section列表
     */
    public ExtendedBlockStorage[] getBlockStorageArray()
    {
        return this.storageArrays;
    }

    /**
     * Generates the height map for a chunk from scratch
     * 从头开始生成chunk的高度map
     * 本质上是赋值降水量高度map和高度map
     */
    protected void generateHeightMap()
    {
        int i = this.getTopFilledSegment();  //获得顶端非空section
        this.heightMapMinimum = Integer.MAX_VALUE; //设定heightMap中的最小值的初始值是int类型的最大值

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)  //遍历底面
            {
                this.precipitationHeightMap[j + (k << 4)] = -999; //赋值降水量？？？-999是没有降水的意思吗？毕竟在地下 TODO：j + (k << 4)是怎么来的，-999又是什么意思

                for (int l = i + 16; l > 0; --l) //从顶端非空section的上一个section开始
                {
                    IBlockState iblockstate = this.getBlockState(j, l - 1, k); //获得方块状态

                    if (iblockstate.getLightOpacity() != 0) //如果方块不透明度不等于0，也就是方块不是透明的，从上往下第一个不透明方块代表最高值
                    {
                        this.heightMap[k << 4 | j] = l; //根据输入的平面坐标，转化为序号，把高度传进去，顶端非空section的第一个透明方块，作为高度

                        if (l < this.heightMapMinimum) //如果高度小于当前最小值，就替换
                        {
                            this.heightMapMinimum = l; //把旧高度最小值替换了
                        }

                        break; //找到了就跳出
                    }
                }
            }
        }

        this.dirty = true;  //设定区块以改变，需要更新
    }

    /**
     * Generates the initial skylight map for the chunk upon generation or load.
     * 生成或加载时生成块的初始全局光照
     */
    public void generateSkylightMap()
    {
        int i = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                this.precipitationHeightMap[j + (k << 4)] = -999;

                for (int l = i + 16; l > 0; --l)  //从第一个空section开始
                {
                    if (this.getBlockLightOpacity(j, l - 1, k) != 0) //和generateHeightMap基本一样，只不过这里获得透明度的方法合一起写了
                    {
                        this.heightMap[k << 4 | j] = l;

                        if (l < this.heightMapMinimum)
                        {
                            this.heightMapMinimum = l;
                        }

                        break;  //找到一个不透明方块，就跳出，从上往下
                    }
                }

                if (this.world.provider.hasSkyLight())  //如果有全局光照
                {
                    int k1 = 15;  //没有其它方块光线影响下，太阳照射到方块上的亮度
                    int i1 = i + 16 - 1;  //获取不透明方块的y轴坐标，未被操作过的最上面的方块

                    while (true)
                    {
                        int j1 = this.getBlockLightOpacity(j, i1, k);  //获取当前方块的不透明度

                        if (j1 == 0 && k1 != 15)  //如果不透明度等于0，也就是全透明，也就是上面有一个空气，且至少已经经过一次循环了，j1=0，k1=15时候啥都没减，这是啥原因？？
                        {
                            j1 = 1;  //设置遮挡的透明度为1，表示每经过一个空气方块就折损1
                        }

                        k1 -= j1; //当前透明度减去遮挡透明度

                        if (k1 > 0) //如果这个方块还能被看见的话
                        {
                            ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];  //根据y坐标获取section对象,i1>>4表示此区块是在哪个section内的（0-15）

                            if (extendedblockstorage != NULL_BLOCK_STORAGE) //如果section非空
                            {
                                extendedblockstorage.setSkyLight(j, i1 & 15, k, k1); //设置这个方块的全局光照为减去阻碍的透明度，i&15是计算该区块在所在section的哪一个位置的（0-15）
                                this.world.notifyLightSet(new BlockPos((this.x << 4) + j, i1, (this.z << 4) + k)); //提醒更新方块关照
                            }
                        }

                        --i1; //进行下一个方块的光照的更新

                        if (i1 <= 0 || k1 <= 0)  //如果方块到头了或者光线看不见的话
                        {
                            break; //就停止
                        }
                    }
                }
            }
        }

        this.dirty = true; //把区块标记为脏，方便下一个tick时候更新
    }

    /**
     * Propagates a given sky-visible block's light value downward and upward to neighboring blocks as necessary.
     * 根据需要，将给定天空可见块的灯光值向下和向上传播到相邻块。
     * 这篇文章提到了这一点：https://www.zhihu.com/question/24459078/answer/133609241，还指出了一个bug
     */
    private void propagateSkylightOcclusion(int x, int z)
    {
        this.updateSkylightColumns[x + z * 16] = true;  //x+z*16是计算(x, z)确定的方块在平面上的位置，这个点代表一列也就是1*255，把它设置为需要更新
        this.isGapLightingUpdated = true;  //间隙照明是否更新设置为真，TODO:不知道啥意思
    }

    private void recheckGaps(boolean onlyOne)
    {
        this.world.profiler.startSection("recheckGaps");  //TODO: 不知道啥意思

        if (this.world.isAreaLoaded(new BlockPos(this.x * 16 + 8, 0, this.z * 16 + 8), 16)) //以前面这个方块为中心，半径为16的区域内，前面这个方块刚好是这个chunk相邻的chunk
        {
            for (int i = 0; i < 16; ++i)
            {
                for (int j = 0; j < 16; ++j)  //遍历底面方块
                {
                    if (this.updateSkylightColumns[i + j * 16]) //如果底面方块所在列需要全局光照更新
                    {
                        this.updateSkylightColumns[i + j * 16] = false; //取消更新，因为接下来要更新了
                        int k = this.getHeightValue(i, j); //获得该列的高度
                        int l = this.x * 16 + i; //获取该列的x坐标
                        int i1 = this.z * 16 + j; //获取该列的z坐标
                        int j1 = Integer.MAX_VALUE; //设置获取阳光直接到达的块的最低高度最大值

                        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                        {
                            //把当前获取阳光直接到达的块的最低高度和已有的获取阳光直接到达的块的最低高度比较，取最小
                            j1 = Math.min(j1, this.world.getChunksLowestHorizon(l + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()));
                        }

                        this.checkSkylightNeighborHeight(l, i1, j1); //检查天空可见块旁边的块的高度，并根据需要安排照明更新。

                        for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
                        {
                            this.checkSkylightNeighborHeight(l + enumfacing1.getFrontOffsetX(), i1 + enumfacing1.getFrontOffsetZ(), k);
                        }

                        if (onlyOne) //如果只操作一列的话，就停止了
                        {
                            this.world.profiler.endSection();
                            return;
                        }
                    }
                }
            }

            this.isGapLightingUpdated = false;
        }

        this.world.profiler.endSection();
    }

    /**
     * Checks the height of a block next to a sky-visible block and schedules a lighting update as necessary.
     * 检查天空可见块旁边的块的高度，并根据需要安排照明更新。
     */
    private void checkSkylightNeighborHeight(int x, int z, int maxValue)
    {
        int i = this.world.getHeight(new BlockPos(x, 0, z)).getY();  //获取该chunk代表点的真正y

        if (i > maxValue)
        {
            this.updateSkylightNeighborHeight(x, z, maxValue, i + 1);
        }
        else if (i < maxValue)
        {
            this.updateSkylightNeighborHeight(x, z, i, maxValue + 1);
        }
    }

    /**
     *
     * @param x
     * @param z
     * @param startY
     * @param endY
     */
    private void updateSkylightNeighborHeight(int x, int z, int startY, int endY)
    {
        if (endY > startY && this.world.isAreaLoaded(new BlockPos(x, 0, z), 16))
        {
            for (int i = startY; i < endY; ++i)
            {
                this.world.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, i, z));
            }

            this.dirty = true;
        }
    }

    /**
     * Initiates the recalculation of both the block-light and sky-light for a given block inside a chunk.
     */
    private void relightBlock(int x, int y, int z)
    {
        int i = this.heightMap[z << 4 | x] & 255; //获得(x,z)坐标的最高点
        int j = i;  //保存当前最高点

        if (y > i) //如果方块的y坐标在最高点上面，说明最高点有误
        {
            j = y; //设置新的最高点
        }

        while (j > 0 && this.getBlockLightOpacity(x, j - 1, z) == 0) // 如果不是全空的列，且下面一格不透明度为0，也就是透明，方块会掉落？
        {
            --j; //最高点减一
        }

        if (j != i) //当区块的最高值发生改变
        {
            this.world.markBlocksDirtyVertical(x + this.x * 16, z + this.z * 16, j, i); //
            this.heightMap[z << 4 | x] = j;
            int k = this.x * 16 + x;
            int l = this.z * 16 + z;

            if (this.world.provider.hasSkyLight()) //
            {
                if (j < i) //如果最高值变小了
                {
                    for (int j1 = j; j1 < i; ++j1) //遍历当前最高值到之前最高值
                    {
                        ExtendedBlockStorage extendedblockstorage2 = this.storageArrays[j1 >> 4]; //获取相应的section

                        if (extendedblockstorage2 != NULL_BLOCK_STORAGE) //如果section非空
                        {
                            extendedblockstorage2.setSkyLight(x, j1 & 15, z, 15); //光照设置为15，因为这些方块之前是没有亮光的
                            this.world.notifyLightSet(new BlockPos((this.x << 4) + x, j1, (this.z << 4) + z)); //提醒这个区块更新
                        }
                    }
                }
                else //如果变大了
                {
                    for (int i1 = i; i1 < j; ++i1) //也是遍历不同的方块
                    {
                        ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4]; //取得section

                        if (extendedblockstorage != NULL_BLOCK_STORAGE)
                        {
                            extendedblockstorage.setSkyLight(x, i1 & 15, z, 0); //把区块光照设置为0，因为这些方块被填充了，之前是空的，现在是有方块的
                            this.world.notifyLightSet(new BlockPos((this.x << 4) + x, i1, (this.z << 4) + z)); //提醒方块更新
                        }
                    }
                }

                int k1 = 15; //设置最高全局光照强度

                while (j > 0 && k1 > 0) //如果方块没到头，且全局光照强度高于0，也就是还有光照
                {
                    --j; //下一个方块
                    int i2 = this.getBlockLightOpacity(x, j, z); //获取不透明度

                    if (i2 == 0) //如果方块透明
                    {
                        i2 = 1; //设置光照阻碍是1，即使是透明方块，光线也有衰减
                    }

                    k1 -= i2; //当前光照减去阻碍光强

                    if (k1 < 0) //如果不小心减成了负数
                    {
                        k1 = 0; //变成全黑
                    }

                    ExtendedBlockStorage extendedblockstorage1 = this.storageArrays[j >> 4]; //取出相应section

                    if (extendedblockstorage1 != NULL_BLOCK_STORAGE)
                    {
                        extendedblockstorage1.setSkyLight(x, j & 15, z, k1); //设置全局光照值
                    }
                }
            }

            int l1 = this.heightMap[z << 4 | x]; //获取现在最高海拔
            int j2 = i;  //获取之前的最高海拔，作为总海拔
            int k2 = l1; // 设置新变量存现在最高海拔

            if (l1 < i) //如果现在海拔低于之前海拔
            {
                j2 = l1; //把最低的现在的海拔设置为总海拔
                k2 = i; //把
            }

            if (l1 < this.heightMapMinimum)
            {
                this.heightMapMinimum = l1;
            }

            if (this.world.provider.hasSkyLight())
            {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    this.updateSkylightNeighborHeight(k + enumfacing.getFrontOffsetX(), l + enumfacing.getFrontOffsetZ(), j2, k2);
                }

                this.updateSkylightNeighborHeight(k, l, j2, k2);
            }

            this.dirty = true;
        }
    }

    /**
     * 返回特定坐标的方块的不透明度
     * @param pos 方块坐标对象
     * @return 方块不透明度
     */
    public int getBlockLightOpacity(BlockPos pos)
    {
        return this.getBlockState(pos).getLightOpacity();
    }

    /**
     * 返回特定坐标的方块的不透明度
     * @param x x坐标值
     * @param y y坐标值
     * @param z z坐标值
     * @return 方块不透明度
     */
    private int getBlockLightOpacity(int x, int y, int z)
    {
        return this.getBlockState(x, y, z).getLightOpacity();
    }

    /**
     * 以BlockPos方式传入方块坐标
     * @param pos 方块坐标对象
     * @return IBlockState方块状态对象
     */
    public IBlockState getBlockState(BlockPos pos)
    {
        return this.getBlockState(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * 返回地图中特定坐标的方块对象
     * @param x x坐标值
     * @param y y坐标值
     * @param z z坐标值
     * @return IBlockState对象
     */
    public IBlockState getBlockState(final int x, final int y, final int z)
    {
        if (this.world.getWorldType() == WorldType.DEBUG_ALL_BLOCK_STATES) //如果世界的类型是调试模式
        {
            IBlockState iblockstate = null;

            if (y == 60) //debug模式中的障碍层
            {
                iblockstate = Blocks.BARRIER.getDefaultState(); //这一层全是障碍方块，直接返回障碍方块
            }

            if (y == 70) //debug的展示层
            {
                iblockstate = ChunkGeneratorDebug.getBlockStateFor(x, z);  //从debug模式获取展示的方块
            }

            return iblockstate == null ? Blocks.AIR.getDefaultState() : iblockstate; //空方块以空气方块填充
        }
        else //如果不是debug模式
        {
            try
            {
                if (y >= 0 && y >> 4 < this.storageArrays.length) //如果在基岩上面而且方块所在section的id小于本区块的section的数目，也就是16
                {
                    ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4]; //获取方块所在section

                    if (extendedblockstorage != NULL_BLOCK_STORAGE)
                    {
                        return extendedblockstorage.get(x & 15, y & 15, z & 15); //获取坐标所指向的方块对象
                    }
                }

                return Blocks.AIR.getDefaultState(); //如果找不到，返回默认值空气
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block state");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being got");
                crashreportcategory.addDetail("Location", new ICrashReportDetail<String>()
                {
                    public String call() throws Exception
                    {
                        return CrashReportCategory.getCoordinateInfo(x, y, z);
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    @Nullable
    public IBlockState setBlockState(BlockPos pos, IBlockState state)
    {
        int i = pos.getX() & 15; //获取x坐标并取到一个chunk以内
        int j = pos.getY(); //获取y坐标
        int k = pos.getZ() & 15; //获取x坐标并取到一个chunk以内
        int l = k << 4 | i; //获取列序号

        if (j >= this.precipitationHeightMap[l] - 1) //如果该区块在有降水的方块上面
        {
            this.precipitationHeightMap[l] = -999; //设置为没降水？？
        }

        int i1 = this.heightMap[l]; //获得列高度
        IBlockState iblockstate = this.getBlockState(pos); //获得方块状态

        if (iblockstate == state) //如果是一样的，就不替换了
        {
            return null; //直接返回null
        }
        else //如果不一样
        {
            Block block = state.getBlock(); //获得要设置的方块类型
            Block block1 = iblockstate.getBlock(); //获得存在的方块类型
            ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4]; //获得方块所在的section
            boolean flag = false; //

            if (extendedblockstorage == NULL_BLOCK_STORAGE) //如果section为空
            {
                if (block == Blocks.AIR) //如果要设置的方块是空气
                {
                    return null; //设置失败，直接返回null
                }

                extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.world.provider.hasSkyLight()); //新建一个新section,j取一个小于等于j的16的倍数
                this.storageArrays[j >> 4] = extendedblockstorage; //设置新section
                flag = j >= i1; //方块是否在最高高度上面
            }

            extendedblockstorage.set(i, j & 15, k, state);//设置方块状态

            if (block1 != block) //如果新旧方块类型不一样
            {
                if (!this.world.isRemote) //是否是服务端世界
                {
                    block1.breakBlock(this.world, pos, iblockstate);//TODO: 不知道啥意思
                }
                else if (block1 instanceof ITileEntityProvider) //是客户端世界且方块是ITileEntity？？？
                {
                    this.world.removeTileEntity(pos); //去除这个实体
                }
            }

            if (extendedblockstorage.get(i, j & 15, k).getBlock() != block)  //如果这个方块不是新方块，代表替换失败了？？？
            {
                return null; //直接返回null
            }
            else //如果替换成功了
            {
                if (flag) //如果方块在最高海拔之上，
                {
                    this.generateSkylightMap(); //重新加载全局光照
                }
                else //如果不是
                {
                    int j1 = state.getLightOpacity(); //获得新方块不透明度
                    int k1 = iblockstate.getLightOpacity(); //获得旧方块不透明度

                    if (j1 > 0) //如果旧的方块不是透明的
                    {
                        if (j >= i1) //如果方块在最高海拔上面
                        {
                            this.relightBlock(i, j + 1, k); //
                        }
                    }
                    else if (j == i1 - 1)
                    {
                        this.relightBlock(i, j, k);
                    }

                    if (j1 != k1 && (j1 < k1 || this.getLightFor(EnumSkyBlock.SKY, pos) > 0 || this.getLightFor(EnumSkyBlock.BLOCK, pos) > 0))
                    {
                        this.propagateSkylightOcclusion(i, k);
                    }
                }

                if (block1 instanceof ITileEntityProvider)
                {
                    TileEntity tileentity = this.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);

                    if (tileentity != null)
                    {
                        tileentity.updateContainingBlockInfo();
                    }
                }

                if (!this.world.isRemote && block1 != block)
                {
                    block.onBlockAdded(this.world, pos, state);
                }

                if (block instanceof ITileEntityProvider)
                {
                    TileEntity tileentity1 = this.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);

                    if (tileentity1 == null)
                    {
                        tileentity1 = ((ITileEntityProvider)block).createNewTileEntity(this.world, block.getMetaFromState(state));
                        this.world.setTileEntity(pos, tileentity1);
                    }

                    if (tileentity1 != null)
                    {
                        tileentity1.updateContainingBlockInfo();
                    }
                }

                this.dirty = true;
                return iblockstate;
            }
        }
    }

    /**
     * 根据方块类型返回亮度
     * 如果是空的，返回默认值
     * 如果是天空，返回全局光照
     * 如果是方块，返回局部光照
     * @param type 方块类型
     * @param pos 坐标对象
     * @return 亮度值
     */
    public int getLightFor(EnumSkyBlock type, BlockPos pos)
    {
        int i = pos.getX() & 15; //取 0-15之间，也就是一个section底面以内
        int j = pos.getY();
        int k = pos.getZ() & 15; //取 0-15之间，也就是一个section底面以内
        ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4]; //得到所在section

        if (extendedblockstorage == NULL_BLOCK_STORAGE)
        {
            return this.canSeeSky(pos) ? type.defaultLightValue : 0; //如果能看见天空，就返回默认的光亮值，如果看不见，就返回0
        }
        else if (type == EnumSkyBlock.SKY)
        {
            return !this.world.provider.hasSkyLight() ? 0 : extendedblockstorage.getSkyLight(i, j & 15, k);//从section获得sky light
        }
        else
        {
            return type == EnumSkyBlock.BLOCK ? extendedblockstorage.getBlockLight(i, j & 15, k) : type.defaultLightValue;//如果是一个block，就从block处返回局部光照，否则返回默认的光照
        }
    }

    public void setLightFor(EnumSkyBlock type, BlockPos pos, int value)
    {
        int i = pos.getX() & 15; //获取x坐标并转化为一个chunk内的
        int j = pos.getY(); //获得y坐标
        int k = pos.getZ() & 15; //获取x坐标并转化为一个chunk内的
        ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4]; //获取方块所在section

        if (extendedblockstorage == NULL_BLOCK_STORAGE) //如果section是空的
        {
            extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.world.provider.hasSkyLight()); //新建一个section
            this.storageArrays[j >> 4] = extendedblockstorage; //赋值section到section数组
            this.generateSkylightMap(); //重新生成全局光照map
        }

        this.dirty = true; //设置chunk为脏

        if (type == EnumSkyBlock.SKY) //如果方块是天空的话
        {
            if (this.world.provider.hasSkyLight()) //有全局光照
            {
                extendedblockstorage.setSkyLight(i, j & 15, k, value); //设置全局光照
            }
        }
        else if (type == EnumSkyBlock.BLOCK) //如果是方块
        {
            extendedblockstorage.setBlockLight(i, j & 15, k, value);//设置局部光照
        }
    }

    public int getLightSubtracted(BlockPos pos, int amount)
    {
        int i = pos.getX() & 15;
        int j = pos.getY();
        int k = pos.getZ() & 15;
        ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];

        if (extendedblockstorage == NULL_BLOCK_STORAGE) //如果section为空，就从worlc里获取skylight
        {
            return this.world.provider.hasSkyLight() && amount < EnumSkyBlock.SKY.defaultLightValue ? EnumSkyBlock.SKY.defaultLightValue - amount : 0; //有sky light且够减的话就返回减去的值，如果不是的话返回0
        }
        else //如果有section
        {
            int l = !this.world.provider.hasSkyLight() ? 0 : extendedblockstorage.getSkyLight(i, j & 15, k); //通过section获得全局光照
            l = l - amount;//减去衰减值
            int i1 = extendedblockstorage.getBlockLight(i, j & 15, k); //通过block获得局部光照

            if (i1 > l) //如果局部光照比全局光照还大
            {
                l = i1; //就取局部光照
            }

            return l; //总之就是选择全局光照折损之后的与局部光照之间的最大值
        }
    }

    /**
     * Adds an entity to the chunk.
     */
    public void addEntity(Entity entityIn)
    {
        this.hasEntities = true;
        int i = MathHelper.floor(entityIn.posX / 16.0D);
        int j = MathHelper.floor(entityIn.posZ / 16.0D);

        if (i != this.x || j != this.z)
        {
            LOGGER.warn("Wrong location! ({}, {}) should be ({}, {}), {}", Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(this.x), Integer.valueOf(this.z), entityIn);
            entityIn.setDead();
        }

        int k = MathHelper.floor(entityIn.posY / 16.0D);

        if (k < 0)
        {
            k = 0;
        }

        if (k >= this.entityLists.length)
        {
            k = this.entityLists.length - 1;
        }

        entityIn.addedToChunk = true;
        entityIn.chunkCoordX = this.x;
        entityIn.chunkCoordY = k;
        entityIn.chunkCoordZ = this.z;
        this.entityLists[k].add(entityIn);
    }

    /**
     * removes entity using its y chunk coordinate as its index
     */
    public void removeEntity(Entity entityIn)
    {
        this.removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
    }

    /**
     * Removes entity at the specified index from the entity array.
     */
    public void removeEntityAtIndex(Entity entityIn, int index)
    {
        if (index < 0)
        {
            index = 0;
        }

        if (index >= this.entityLists.length)
        {
            index = this.entityLists.length - 1;
        }

        this.entityLists[index].remove(entityIn);
    }

    /**
     * 判断一个区块是否能够看见天空
     * @param pos 区块的位置对象
     * @return 能就返回true，否则返回false
     */
    public boolean canSeeSky(BlockPos pos)
    {
        int i = pos.getX() & 15;
        int j = pos.getY();
        int k = pos.getZ() & 15;
        return j >= this.heightMap[k << 4 | i]; //取出该列的最高值，如果比最高值还大，说明能看见天空，否则看不见
    }

    @Nullable
    private TileEntity createNewTileEntity(BlockPos pos)
    {
        IBlockState iblockstate = this.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return !block.hasTileEntity() ? null : ((ITileEntityProvider)block).createNewTileEntity(this.world, iblockstate.getBlock().getMetaFromState(iblockstate));
    }

    @Nullable
    public TileEntity getTileEntity(BlockPos pos, Chunk.EnumCreateEntityType createType)
    {
        TileEntity tileentity = this.tileEntities.get(pos);  //获得tile 实体

        if (tileentity == null) //如果实体是空，要创建
        {
            if (createType == Chunk.EnumCreateEntityType.IMMEDIATE)  //如果创建实体的类型是立即
            {
                tileentity = this.createNewTileEntity(pos); //新建实体
                this.world.setTileEntity(pos, tileentity); //设置实体
            }
            else if (createType == Chunk.EnumCreateEntityType.QUEUED) //如果创建实体的类型是排队
            {
                this.tileEntityPosQueue.add(pos); //把它的位置对象加入到队列中取
            }
        }
        else if (tileentity.isInvalid())  //如果不为空且是失效的
        {
            this.tileEntities.remove(pos); //去除实体
            return null; //返回空
        }

        return tileentity; //返回实体
    }

    public void addTileEntity(TileEntity tileEntityIn)
    {
        this.addTileEntity(tileEntityIn.getPos(), tileEntityIn);

        if (this.loaded)
        {
            this.world.addTileEntity(tileEntityIn);
        }
    }

    public void addTileEntity(BlockPos pos, TileEntity tileEntityIn)
    {
        tileEntityIn.setWorld(this.world);
        tileEntityIn.setPos(pos);

        if (this.getBlockState(pos).getBlock() instanceof ITileEntityProvider)
        {
            if (this.tileEntities.containsKey(pos))
            {
                ((TileEntity)this.tileEntities.get(pos)).invalidate();
            }

            tileEntityIn.validate();
            this.tileEntities.put(pos, tileEntityIn);
        }
    }

    public void removeTileEntity(BlockPos pos)
    {
        if (this.loaded)
        {
            TileEntity tileentity = this.tileEntities.remove(pos);

            if (tileentity != null)
            {
                tileentity.invalidate();
            }
        }
    }

    /**
     * Called when this Chunk is loaded by the ChunkProvider
     */
    public void onLoad()
    {
        this.loaded = true;
        this.world.addTileEntities(this.tileEntities.values());

        for (ClassInheritanceMultiMap<Entity> classinheritancemultimap : this.entityLists)
        {
            this.world.loadEntities(classinheritancemultimap);
        }
    }

    /**
     * Called when this Chunk is unloaded by the ChunkProvider
     * 卸载chunk
     */
    public void onUnload()
    {
        this.loaded = false; //设置已加载标志位为false

        for (TileEntity tileentity : this.tileEntities.values())
        {
            this.world.markTileEntityForRemoval(tileentity); //将指定的TileEntity添加到挂起的删除列表中。
        }

        for (ClassInheritanceMultiMap<Entity> classinheritancemultimap : this.entityLists)
        {
            this.world.unloadEntities(classinheritancemultimap); //将指定的Entity添加到列表中。
        }
    }

    /**
     * Sets the isModified flag for this Chunk
     */
    public void markDirty()
    {
        this.dirty = true;
    }

    /**
     * Fills the given list of all entities that intersect within the given bounding box that aren't the passed entity.
     * 填充给定边界框内相交但不是传递实体的所有实体的给定列表。
     */
    public void getEntitiesWithinAABBForEntity(@Nullable Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate <? super Entity > filter)
    {
        int i = MathHelper.floor((aabb.minY - 2.0D) / 16.0D);
        int j = MathHelper.floor((aabb.maxY + 2.0D) / 16.0D);
        i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
        j = MathHelper.clamp(j, 0, this.entityLists.length - 1);

        for (int k = i; k <= j; ++k)
        {
            if (!this.entityLists[k].isEmpty())
            {
                for (Entity entity : this.entityLists[k])
                {
                    if (entity.getEntityBoundingBox().intersects(aabb) && entity != entityIn)
                    {
                        if (filter == null || filter.apply(entity))
                        {
                            listToFill.add(entity);
                        }

                        Entity[] aentity = entity.getParts();

                        if (aentity != null)
                        {
                            for (Entity entity1 : aentity)
                            {
                                if (entity1 != entityIn && entity1.getEntityBoundingBox().intersects(aabb) && (filter == null || filter.apply(entity1)))
                                {
                                    listToFill.add(entity1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public <T extends Entity> void getEntitiesOfTypeWithinAABB(Class <? extends T > entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate <? super T > filter)
    {
        int i = MathHelper.floor((aabb.minY - 2.0D) / 16.0D);
        int j = MathHelper.floor((aabb.maxY + 2.0D) / 16.0D);
        i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
        j = MathHelper.clamp(j, 0, this.entityLists.length - 1);

        for (int k = i; k <= j; ++k)
        {
            for (T t : this.entityLists[k].getByClass(entityClass))
            {
                if (t.getEntityBoundingBox().intersects(aabb) && (filter == null || filter.apply(t))) //如果选到的区域与aabb重叠且通过了过滤
                {
                    listToFill.add(t); //就把它加入到列表中
                }
            }
        }
    }

    /**
     * Returns true if this Chunk needs to be saved
     */
    public boolean needsSaving(boolean p_76601_1_)
    {
        if (p_76601_1_)
        {
            if (this.hasEntities && this.world.getTotalWorldTime() != this.lastSaveTime || this.dirty)
            {
                return true;
            }
        }
        else if (this.hasEntities && this.world.getTotalWorldTime() >= this.lastSaveTime + 600L)
        {
            return true;
        }

        return this.dirty;
    }

    /**
     * 使用地图种子生成chunk的随机数
     * 随机数与输入的种子，区块的x，z有关
     * @param seed 地图种子
     * @return 随机数变量
     */
    public Random getRandomWithSeed(long seed)
    {
        return new Random(this.world.getSeed() + (long)(this.x * this.x * 4987142) + (long)(this.x * 5947611) + (long)(this.z * this.z) * 4392871L + (long)(this.z * 389711) ^ seed);
    }

    public boolean isEmpty()
    {
        return false;
    }

    public void populate(IChunkProvider chunkProvider, IChunkGenerator chunkGenrator)
    {
        Chunk chunk = chunkProvider.getLoadedChunk(this.x, this.z - 1);
        Chunk chunk1 = chunkProvider.getLoadedChunk(this.x + 1, this.z);
        Chunk chunk2 = chunkProvider.getLoadedChunk(this.x, this.z + 1);
        Chunk chunk3 = chunkProvider.getLoadedChunk(this.x - 1, this.z);

        if (chunk1 != null && chunk2 != null && chunkProvider.getLoadedChunk(this.x + 1, this.z + 1) != null)
        {
            this.populate(chunkGenrator);
        }

        if (chunk3 != null && chunk2 != null && chunkProvider.getLoadedChunk(this.x - 1, this.z + 1) != null)
        {
            chunk3.populate(chunkGenrator);
        }

        if (chunk != null && chunk1 != null && chunkProvider.getLoadedChunk(this.x + 1, this.z - 1) != null)
        {
            chunk.populate(chunkGenrator);
        }

        if (chunk != null && chunk3 != null)
        {
            Chunk chunk4 = chunkProvider.getLoadedChunk(this.x - 1, this.z - 1);

            if (chunk4 != null)
            {
                chunk4.populate(chunkGenrator);
            }
        }
    }

    protected void populate(IChunkGenerator generator)
    {
        if (this.isTerrainPopulated())
        {
            if (generator.generateStructures(this, this.x, this.z))
            {
                this.markDirty();
            }
        }
        else
        {
            this.checkLight();
            generator.populate(this.x, this.z);
            this.markDirty();
        }
    }

    public BlockPos getPrecipitationHeight(BlockPos pos)
    {
        int i = pos.getX() & 15;
        int j = pos.getZ() & 15;
        int k = i | j << 4; //根据x，z轴获得在一个chunk平面的序号
        BlockPos blockpos = new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ()); //根据方块的x，z和降水高度获得接收到降水的区块的位置

        if (blockpos.getY() == -999) //TODO: 这啥意思？？
        {
            int l = this.getTopFilledSegment() + 15; //取最上面非空section的最上面一层方块
            blockpos = new BlockPos(pos.getX(), l, pos.getZ()); //获取该方块的位置对象
            int i1 = -1; //定义初始降水高度

            while (blockpos.getY() > 0 && i1 == -1) //如果方块存在且刚刚开始
            {
                IBlockState iblockstate = this.getBlockState(blockpos); //获得方块
                Material material = iblockstate.getMaterial(); //获得材质

                if (!material.blocksMovement() && !material.isLiquid()) //如果方块不是固体且方块不是液体
                {
                    blockpos = blockpos.down(); //那就是空的呗，位置下降一格，大概是模拟雨滴下降的动作吧
                }
                else //如果是固体或者是液体，雨滴收到了遮挡
                {
                    i1 = blockpos.getY() + 1; //降水高度应该是接触方块的上面一格
                }
            }
            //如果你想获得0层以及0层以下的降水高度，那么降水高度是-1
            this.precipitationHeightMap[k] = i1; //把降水高度赋值给列表
        }

        return new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
    }

    public void onTick(boolean skipRecheckGaps)
    {
        if (this.isGapLightingUpdated && this.world.provider.hasSkyLight() && !skipRecheckGaps)
        {
            this.recheckGaps(this.world.isRemote);
        }

        this.ticked = true;

        if (!this.isLightPopulated && this.isTerrainPopulated)
        {
            this.checkLight();
        }

        while (!this.tileEntityPosQueue.isEmpty())
        {
            BlockPos blockpos = this.tileEntityPosQueue.poll();

            if (this.getTileEntity(blockpos, Chunk.EnumCreateEntityType.CHECK) == null && this.getBlockState(blockpos).getBlock().hasTileEntity())
            {
                TileEntity tileentity = this.createNewTileEntity(blockpos);
                this.world.setTileEntity(blockpos, tileentity);
                this.world.markBlockRangeForRenderUpdate(blockpos, blockpos);
            }
        }
    }

    public boolean isPopulated()
    {
        return this.ticked && this.isTerrainPopulated && this.isLightPopulated;
    }

    public boolean wasTicked()
    {
        return this.ticked;
    }

    /**
     * Gets a {@link ChunkPos} representing the x and z coordinates of this chunk.
     */
    public ChunkPos getPos()
    {
        return new ChunkPos(this.x, this.z);
    }

    /**
     * Returns whether the ExtendedBlockStorages containing levels (in blocks) from arg 1 to arg 2 are fully empty
     * (true) or not (false).
     */
    public boolean isEmptyBetween(int startY, int endY)
    {
        if (startY < 0) //如果给的是基岩以下
        {
            startY = 0; //就算是基岩开始的
        }

        if (endY >= 256) //如果终点超过了最大值
        {
            endY = 255; //设置最大值
        }

        for (int i = startY; i <= endY; i += 16) //遍历之间的方块
        {
            ExtendedBlockStorage extendedblockstorage = this.storageArrays[i >> 4]; //取得方块所在的section

            if (extendedblockstorage != NULL_BLOCK_STORAGE && !extendedblockstorage.isEmpty()) //如果方块section存在且不是空的
            {
                return false; //返回false
            }
        }

        return true;//返回true
    }

    public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays)
    {
        if (this.storageArrays.length != newStorageArrays.length) //如果长度不一样
        {
            LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", Integer.valueOf(newStorageArrays.length), Integer.valueOf(this.storageArrays.length)); //warning一下
        }
        else
        {
            System.arraycopy(newStorageArrays, 0, this.storageArrays, 0, this.storageArrays.length); //拷贝数组
        }
    }

    /**
     * Loads this chunk from the given buffer.
     *  
     * @see net.minecraft.network.play.server.SPacketChunkData#getReadBuffer()
     */
    public void read(PacketBuffer buf, int availableSections, boolean groundUpContinuous)
    {
        boolean flag = this.world.provider.hasSkyLight();

        for (int i = 0; i < this.storageArrays.length; ++i)
        {
            ExtendedBlockStorage extendedblockstorage = this.storageArrays[i];

            if ((availableSections & 1 << i) == 0)
            {
                if (groundUpContinuous && extendedblockstorage != NULL_BLOCK_STORAGE)
                {
                    this.storageArrays[i] = NULL_BLOCK_STORAGE;
                }
            }
            else
            {
                if (extendedblockstorage == NULL_BLOCK_STORAGE)
                {
                    extendedblockstorage = new ExtendedBlockStorage(i << 4, flag);
                    this.storageArrays[i] = extendedblockstorage;
                }

                extendedblockstorage.getData().read(buf);
                buf.readBytes(extendedblockstorage.getBlockLight().getData());

                if (flag)
                {
                    buf.readBytes(extendedblockstorage.getSkyLight().getData());
                }
            }
        }

        if (groundUpContinuous)
        {
            buf.readBytes(this.blockBiomeArray);
        }

        for (int j = 0; j < this.storageArrays.length; ++j)
        {
            if (this.storageArrays[j] != NULL_BLOCK_STORAGE && (availableSections & 1 << j) != 0)
            {
                this.storageArrays[j].recalculateRefCounts();
            }
        }

        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.generateHeightMap();

        for (TileEntity tileentity : this.tileEntities.values())
        {
            tileentity.updateContainingBlockInfo();
        }
    }

    public Biome getBiome(BlockPos pos, BiomeProvider provider)
    {
        int i = pos.getX() & 15; //获取方块的x坐标，与上15，使之在一个chunk内
        int j = pos.getZ() & 15; //获取方块的z坐标，与上15，使之在一个chunk内
        int k = this.blockBiomeArray[j << 4 | i] & 255; //获取一个点的生物群系id

        if (k == 255) //
        {
            Biome biome = provider.getBiome(pos, Biomes.PLAINS);  //从坐标里获得生物群系，默认群系为平原
            k = Biome.getIdForBiome(biome); //获得生物群系id
            this.blockBiomeArray[j << 4 | i] = (byte)(k & 255); //重新赋值生物群系id
        }

        Biome biome1 = Biome.getBiome(k); //获得生物群系
        return biome1 == null ? Biomes.PLAINS : biome1; //返回生物群系，如果是空，返回默认的平原
    }

    /**
     * Returns an array containing a 16x16 mapping on the X/Z of block positions in this Chunk to biome IDs.
     */
    public byte[] getBiomeArray()
    {
        return this.blockBiomeArray;
    }

    /**
     * Accepts a 256-entry array that contains a 16x16 mapping on the X/Z plane of block positions in this Chunk to biome IDs.
     * 接受一个256项数组，该数组包含该块中块位置X/Z平面上的16x16映射到biome IDs。
     * 这里是chunk类中唯一一个可以写入blockBiomeArray的方法，构造方法只是初始化了变量
     */
    public void setBiomeArray(byte[] biomeArray)
    {
        if (this.blockBiomeArray.length != biomeArray.length)  //如果传入的数组长度不符合要求，就报错
        {
            LOGGER.warn("Could not set level chunk biomes, array length is {} instead of {}", Integer.valueOf(biomeArray.length), Integer.valueOf(this.blockBiomeArray.length));
        }
        else
        {
            System.arraycopy(biomeArray, 0, this.blockBiomeArray, 0, this.blockBiomeArray.length); //拷贝数组到blockBiomeArray
        }
    }

    /**
     * Resets the relight check index to 0 for this Chunk.
     */
    public void resetRelightChecks()
    {
        this.queuedLightChecks = 0;
    }

    /**
     * Called once-per-chunk-per-tick, and advances the round-robin relight check index by up to 8 blocks at a time. In
     * a worst-case scenario, can potentially take up to 25.6 seconds, calculated via (4096/8)/20, to re-check all
     * blocks in a chunk, which may explain lagging light updates on initial world generation.
     */
    public void enqueueRelightChecks()
    {
        if (this.queuedLightChecks < 4096)
        {
            BlockPos blockpos = new BlockPos(this.x << 4, 0, this.z << 4);

            for (int i = 0; i < 8; ++i)
            {
                if (this.queuedLightChecks >= 4096)
                {
                    return;
                }

                int j = this.queuedLightChecks % 16;
                int k = this.queuedLightChecks / 16 % 16;
                int l = this.queuedLightChecks / 256;
                ++this.queuedLightChecks;

                for (int i1 = 0; i1 < 16; ++i1)
                {
                    BlockPos blockpos1 = blockpos.add(k, (j << 4) + i1, l);
                    boolean flag = i1 == 0 || i1 == 15 || k == 0 || k == 15 || l == 0 || l == 15;

                    if (this.storageArrays[j] == NULL_BLOCK_STORAGE && flag || this.storageArrays[j] != NULL_BLOCK_STORAGE && this.storageArrays[j].get(k, i1, l).getMaterial() == Material.AIR)
                    {
                        for (EnumFacing enumfacing : EnumFacing.values())
                        {
                            BlockPos blockpos2 = blockpos1.offset(enumfacing);

                            if (this.world.getBlockState(blockpos2).getLightValue() > 0)
                            {
                                this.world.checkLight(blockpos2);
                            }
                        }

                        this.world.checkLight(blockpos1);
                    }
                }
            }
        }
    }

    public void checkLight()
    {
        this.isTerrainPopulated = true;
        this.isLightPopulated = true;
        BlockPos blockpos = new BlockPos(this.x << 4, 0, this.z << 4);

        if (this.world.provider.hasSkyLight())
        {
            if (this.world.isAreaLoaded(blockpos.add(-1, 0, -1), blockpos.add(16, this.world.getSeaLevel(), 16)))
            {
                label44:

                for (int i = 0; i < 16; ++i)
                {
                    for (int j = 0; j < 16; ++j)
                    {
                        if (!this.checkLight(i, j))
                        {
                            this.isLightPopulated = false;
                            break label44;
                        }
                    }
                }

                if (this.isLightPopulated)
                {
                    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                    {
                        int k = enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? 16 : 1;
                        this.world.getChunkFromBlockCoords(blockpos.offset(enumfacing, k)).checkLightSide(enumfacing.getOpposite());
                    }

                    this.setSkylightUpdated();
                }
            }
            else
            {
                this.isLightPopulated = false;
            }
        }
    }

    private void setSkylightUpdated()
    {
        for (int i = 0; i < this.updateSkylightColumns.length; ++i) //遍历整个updateSkylightColumns
        {
            this.updateSkylightColumns[i] = true; //设置为true
        }

        this.recheckGaps(false); //重新计算gaps？？TODO 不知道啥意思
    }

    private void checkLightSide(EnumFacing facing)
    {
        if (this.isTerrainPopulated)
        {
            if (facing == EnumFacing.EAST)
            {
                for (int i = 0; i < 16; ++i)
                {
                    this.checkLight(15, i);
                }
            }
            else if (facing == EnumFacing.WEST)
            {
                for (int j = 0; j < 16; ++j)
                {
                    this.checkLight(0, j);
                }
            }
            else if (facing == EnumFacing.SOUTH)
            {
                for (int k = 0; k < 16; ++k)
                {
                    this.checkLight(k, 15);
                }
            }
            else if (facing == EnumFacing.NORTH)
            {
                for (int l = 0; l < 16; ++l)
                {
                    this.checkLight(l, 0);
                }
            }
        }
    }

    private boolean checkLight(int x, int z)
    {
        int i = this.getTopFilledSegment();
        boolean flag = false;
        boolean flag1 = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.x << 4) + x, 0, (this.z << 4) + z);

        for (int j = i + 16 - 1; j > this.world.getSeaLevel() || j > 0 && !flag1; --j)
        {
            blockpos$mutableblockpos.setPos(blockpos$mutableblockpos.getX(), j, blockpos$mutableblockpos.getZ());
            int k = this.getBlockLightOpacity(blockpos$mutableblockpos);

            if (k == 255 && blockpos$mutableblockpos.getY() < this.world.getSeaLevel())
            {
                flag1 = true;
            }

            if (!flag && k > 0)
            {
                flag = true;
            }
            else if (flag && k == 0 && !this.world.checkLight(blockpos$mutableblockpos))
            {
                return false;
            }
        }

        for (int l = blockpos$mutableblockpos.getY(); l > 0; --l)
        {
            blockpos$mutableblockpos.setPos(blockpos$mutableblockpos.getX(), l, blockpos$mutableblockpos.getZ());

            if (this.getBlockState(blockpos$mutableblockpos).getLightValue() > 0)
            {
                this.world.checkLight(blockpos$mutableblockpos);
            }
        }

        return true;
    }

    public boolean isLoaded()
    {
        return this.loaded;
    }

    public void markLoaded(boolean loaded)
    {
        this.loaded = loaded;
    }

    public World getWorld()
    {
        return this.world;
    }

    public int[] getHeightMap()
    {
        return this.heightMap;
    }

    public void setHeightMap(int[] newHeightMap)
    {
        if (this.heightMap.length != newHeightMap.length) //如果长度不一样
        {
            LOGGER.warn("Could not set level chunk heightmap, array length is {} instead of {}", Integer.valueOf(newHeightMap.length), Integer.valueOf(this.heightMap.length)); //报warning
        }
        else
        {
            System.arraycopy(newHeightMap, 0, this.heightMap, 0, this.heightMap.length); //拷贝数组
        }
    }

    public Map<BlockPos, TileEntity> getTileEntityMap()
    {
        return this.tileEntities;
    }

    public ClassInheritanceMultiMap<Entity>[] getEntityLists()
    {
        return this.entityLists;
    }

    public boolean isTerrainPopulated()
    {
        return this.isTerrainPopulated;
    }

    public void setTerrainPopulated(boolean terrainPopulated)
    {
        this.isTerrainPopulated = terrainPopulated;
    }

    public boolean isLightPopulated()
    {
        return this.isLightPopulated;
    }

    public void setLightPopulated(boolean lightPopulated)
    {
        this.isLightPopulated = lightPopulated;
    }

    public void setModified(boolean modified)
    {
        this.dirty = modified;
    }

    public void setHasEntities(boolean hasEntitiesIn)
    {
        this.hasEntities = hasEntitiesIn;
    }

    public void setLastSaveTime(long saveTime)
    {
        this.lastSaveTime = saveTime;
    }

    public int getLowestHeight()
    {
        return this.heightMapMinimum;
    }

    public long getInhabitedTime()
    {
        return this.inhabitedTime;
    }

    public void setInhabitedTime(long newInhabitedTime)
    {
        this.inhabitedTime = newInhabitedTime;
    }

    public static enum EnumCreateEntityType
    {
        IMMEDIATE,
        QUEUED,
        CHECK;
    }
}
