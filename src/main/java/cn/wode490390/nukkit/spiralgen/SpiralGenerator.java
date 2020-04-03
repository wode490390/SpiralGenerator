package cn.wode490390.nukkit.spiralgen;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.Collections;
import java.util.Map;

public class SpiralGenerator extends Generator {

    protected ChunkManager level;
    protected SpiralGeneratorSettings settings;

    public SpiralGenerator() {
        this(null);
    }

    public SpiralGenerator(Map<String, Object> options) {
        this.settings = SpiralGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public int getId() {
        return TYPE_INFINITE;
    }

    @Override
    public String getName() {
        return "normal";
    }

    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }

    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);
        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;

        int d;
        if (chunkX >= 0) {
            d = 0;
        } else if (chunkZ >= 0) {
            d = -1;
        } else {
            d = 1;
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (Math.max(Math.abs(baseX + x - d), Math.abs(baseZ + z)) % 2 == 0) {
                    for (int y = 0; y < 8; y++) {
                        chunk.setBlock(x, y, z, this.settings.getWallId(), this.settings.getWallMeta());
                    }
                } else {
                    chunk.setBlock(x, 0, z, this.settings.getGroundId(), this.settings.getGroundMeta());
                }
                chunk.setBiome(x, z, EnumBiome.PLAINS.biome);
            }
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {

    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 1.5, -1.5);
    }
}
