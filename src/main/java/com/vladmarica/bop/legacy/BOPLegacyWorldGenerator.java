package com.vladmarica.bop.legacy;
import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;

import java.util.Random;

public class BOPLegacyWorldGenerator implements IWorldGenerator {

    private WorldGenCrystals crystalGenerator = new WorldGenCrystals();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 1) {
            generateEnd(random, chunkX, chunkZ, world);
        }
    }

    private void generateEnd(Random random, int chunkX, int chunkZ, World world) {
        for (int i = 0; i < 30; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int y = 10 + random.nextInt(60);
            int z = chunkZ * 16 + random.nextInt(16);

            if (world.getBlock(x, y, z) == Blocks.end_stone) {
                world.setBlock(x, y, z, BOPCBlocks.biomeBlock);
            }
        }

        for (int i = 0; i < 40; i++)
        {
            int x = chunkX * 16 + random.nextInt(16);
            int y = 10 + random.nextInt(60);
            int z = chunkZ * 16 + random.nextInt(16);
            crystalGenerator.generate(world, random, x, y, z);
        }
    }
}
