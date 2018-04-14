package com.vladmarica.bop.tweaks;

import biomesoplenty.common.world.features.nether.WorldGenWaspHive;
import com.vladmarica.bop.BOPIntegrationMod;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenWaspHiveFixed extends WorldGenWaspHive {

    @Override
    public boolean func_76484_a(World world, Random rand, int x, int y, int z) {
        if (rand.nextInt(100) + 1 <= BOPIntegrationMod.config.waspHiveRarityModifier * 100) {
            return super.func_76484_a(world, rand, x, y, z);
        }
        return false;
    }
}
