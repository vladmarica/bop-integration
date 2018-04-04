package com.vladmarica.bop;

import cpw.mods.fml.common.registry.GameRegistry;

public class ThaumcraftModCompat {

    public static void apply() {
        GameRegistry.registerWorldGenerator(new ThaumcraftCompatWorldGenerator(), 0);
        BOPIntegrationMod.LOGGER.info("Thaumcraft Biomes O' Plenty integration patch has been applied");
    }
}
