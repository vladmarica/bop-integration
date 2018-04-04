package com.vladmarica.bop;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = BOPIntegrationMod.MODID, version = "1.0", dependencies = "required-after:BiomesOPlenty")
public class BOPIntegrationMod {

    static final String MODID = "BOPIntegration";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("Thaumcraft")) {
            ThaumcraftModCompat.apply();
        }
        else {
            LOGGER.info("Thaumcraft not found - skipping compatibility patch");
        }
    }
}
