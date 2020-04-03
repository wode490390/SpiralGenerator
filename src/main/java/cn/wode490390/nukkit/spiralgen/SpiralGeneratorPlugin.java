package cn.wode490390.nukkit.spiralgen;

import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.spiralgen.util.MetricsLite;

import java.util.NoSuchElementException;

public class SpiralGeneratorPlugin extends PluginBase {

    private static SpiralGeneratorPlugin INSTANCE;

    private SpiralGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 6987);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "wall.material";
        int wallMaterial = Block.MOSSY_STONE;
        try {
            wallMaterial = config.getInt(node, wallMaterial);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int wallMeta = 0;
        node = "wall.meta";
        try {
            wallMeta = config.getInt(node, wallMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        node = "ground.material";
        int groundMaterial = Block.GRASS;
        try {
            groundMaterial = config.getInt(node, groundMaterial);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int groundMeta = 0;
        node = "ground.meta";
        try {
            groundMeta = config.getInt(node, groundMeta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(wallMaterial, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(wallMaterial, wallMeta);
            } catch (NoSuchElementException e) {
                wallMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            wallMaterial = Block.MOSSY_STONE;
            wallMeta = 0;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }
        try {
            GlobalBlockPalette.getOrCreateRuntimeId(groundMaterial, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(groundMaterial, groundMeta);
            } catch (NoSuchElementException e) {
                groundMeta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            groundMaterial = Block.GRASS;
            groundMeta = 0;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new SpiralGeneratorSettings(wallMaterial, wallMeta, groundMaterial, groundMeta);

        Generator.addGenerator(SpiralGenerator.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(SpiralGenerator.class, "normal", Generator.TYPE_INFINITE);
    }

    public SpiralGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static SpiralGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
