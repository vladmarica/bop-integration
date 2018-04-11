package com.vladmarica.bop.ic2;

import com.vladmarica.bop.BOPIntegrationMod;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.core.IC2;

public final class IC2ModCompat {

    public static void apply() {
        if (IC2.enableWorldGenTreeRubber && BOPIntegrationMod.config.fixRubberTrees) {
            if (!BOPIntegrationMod.unregisterWorldGenerator(IC2.getInstance())) {
                BOPIntegrationMod.logger.error("Failed to unregister IC2 world generator. This may result in no rubber trees.");
                return;
            }
            GameRegistry.registerWorldGenerator(new IC2CompatWorldGenerator(), 0);
        }
        BOPIntegrationMod.logger.info("IC2 Biomes O' Plenty integration patch has been applied");
    }
}
