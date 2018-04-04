package com.vladmarica.bop;
import biomesoplenty.api.biome.BOPBiome;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import thaumcraft.common.lib.world.WorldGenSilverwoodTrees;

import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.FOREST;
import static net.minecraftforge.common.BiomeDictionary.Type.MAGICAL;
import static net.minecraftforge.common.BiomeDictionary.Type.PLAINS;

public class ThaumcraftCompatWorldGenerator implements IWorldGenerator {

    private WorldGenSilverwoodTrees silverwoodTreeGen;

    public ThaumcraftCompatWorldGenerator() {
        silverwoodTreeGen = new WorldGenSilverwoodTrees(false, 7, 4);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 0) {
            BiomeGenBase biome = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
            if (!(biome instanceof BOPBiome)) {
                return;
            }

            if ((!BiomeDictionary.isBiomeOfType(biome, FOREST) && !BiomeDictionary.isBiomeOfType(biome, PLAINS)) || BiomeDictionary.isBiomeOfType(biome, MAGICAL)) {
                return;
            }

            if (random.nextInt(60) <= 3) {
                int x = chunkX * 16 + random.nextInt(16);
                int z = chunkZ * 16 + random.nextInt(16);
                int y = world.getHeightValue(x, z);
                silverwoodTreeGen.generate(world, random, x, y, z);
            }
        }
    }
}
