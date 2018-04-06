package com.vladmarica.bop.ic2;

import biomesoplenty.api.content.BOPCBiomes;
import cpw.mods.fml.common.IWorldGenerator;
import ic2.core.IC2;
import ic2.core.block.WorldGenRubTree;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public class IC2CompatWorldGenerator implements IWorldGenerator {

    private WorldGenRubTree rubberTreeGenerator = new WorldGenRubTree();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        IC2.enableWorldGenTreeRubber = false;
        IC2.getInstance().generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        IC2.enableWorldGenTreeRubber = true;

        BiomeGenBase biome = world.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);
        if (biome == BOPCBiomes.grassland || biome == BOPCBiomes.marsh || biome == BOPCBiomes.landOfLakesMarsh) {
            return;
        }

        int numTrees = 0;
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.CONIFEROUS)) {
            numTrees += random.nextInt(3);
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE)) {
            numTrees += random.nextInt(5) + 2;
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)) {
            numTrees += random.nextInt(10) + 3;
        }

        if (random.nextInt(100) + 1 <= numTrees * 2) {
            rubberTreeGenerator.func_76484_a(world, random, chunkX * 16 + random.nextInt(16), numTrees, chunkZ * 16 + random.nextInt(16));
        }
    }
}
