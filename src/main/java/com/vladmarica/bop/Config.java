package com.vladmarica.bop;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    private Configuration configurationFile;

    public boolean genCelestialCrystals;
    public boolean getBiomeOre;
    public boolean removeNetherGravestones;
    public boolean fixSilverwoodTrees;
    public boolean rubberTreeBiomeBlacklist;

    public Config(File file) {
        configurationFile = new Configuration(file);
        configurationFile.addCustomCategoryComment("Legacy", "These features are unavailable in the 1.7.10 version of BOP, but you can reenable them here.");
        configurationFile.addCustomCategoryComment("Thaumcraft", "Options to make BOP work better with Thaumcraft");
        configurationFile.addCustomCategoryComment("Industrial Craft 2", "Options to make BOP work better with IC2");

        configurationFile.load();
        sync();
        save();
    }

    public void sync() {
        genCelestialCrystals = configurationFile.getBoolean("genCelestialCrystals", "Legacy", true, "Generate Celestial Crystals in the End. Used to make Ambrosia.");
        getBiomeOre = configurationFile.getBoolean("getBiomeOre", "Legacy", true, "Generate Biome Ore in the End. Drops Biome Essence.");
        removeNetherGravestones = configurationFile.getBoolean("removeNetherGravestones", "Legacy", true, "Prevent gravestones from spawning in the Nether. They are ugly and useless.");

        fixSilverwoodTrees = configurationFile.getBoolean("fixSilverwoodTrees", "Thaumcraft", true, "Allows Silverwood trees to spawn in all forest and plains biomes.");
    }

    public void save() {
        configurationFile.save();
    }

    public boolean hasChanged() {
        return configurationFile.hasChanged();
    }
}
