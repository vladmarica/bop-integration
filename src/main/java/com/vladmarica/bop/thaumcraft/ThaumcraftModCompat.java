package com.vladmarica.bop.thaumcraft;

import com.vladmarica.bop.BOPIntegrationMod;
import com.vladmarica.bop.thaumcraft.ThaumcraftCompatWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ThaumcraftModCompat {

    public static void apply() {
        GameRegistry.registerWorldGenerator(new ThaumcraftCompatWorldGenerator(), 0);
        BOPIntegrationMod.LOGGER.info("Thaumcraft Biomes O' Plenty integration patch has been applied");
    }
}
