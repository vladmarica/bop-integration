package com.vladmarica.bop;

import biomesoplenty.common.world.generation.WorldGenFieldAssociation;
import com.vladmarica.bop.ic2.IC2ModCompat;
import com.vladmarica.bop.legacy.BOPLegacyWorldGenerator;
import com.vladmarica.bop.legacy.WorldGenNothing;
import com.vladmarica.bop.thaumcraft.ThaumcraftModCompat;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@Mod(modid = BOPIntegrationMod.MODID, version = "1.0", dependencies = "required-after:BiomesOPlenty")
public class BOPIntegrationMod {

    static final String MODID = "BOPIntegration";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new BOPLegacyWorldGenerator(), 0);
        WorldGenFieldAssociation.associateFeature("gravesPerChunk", new WorldGenNothing());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("Thaumcraft")) {
            ThaumcraftModCompat.apply();
        }
        else {
            LOGGER.info("Thaumcraft not found - skipping integration patch");
        }

        if (Loader.isModLoaded("IC2")) {
            IC2ModCompat.apply();
        }
        else {
            LOGGER.info("IC2 not found - skipping integration patch");
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean unregisterWorldGenerator(IWorldGenerator worldGenerator) {
        try {
            Field worldGeneratorsField = GameRegistry.class.getDeclaredField("worldGenerators");
            worldGeneratorsField.setAccessible(true);
            Field worldGeneratorIndexField = GameRegistry.class.getDeclaredField("worldGeneratorIndex");
            worldGeneratorIndexField.setAccessible(true);

            Set<IWorldGenerator> generators = (Set<IWorldGenerator>) worldGeneratorsField.get(worldGenerator);
            Map<IWorldGenerator, Integer> generatorIndexMap = (Map<IWorldGenerator, Integer>) worldGeneratorIndexField.get(worldGenerator);
            if (!generators.contains(worldGenerator)) {
                return false;
            }

            generators.remove(worldGenerator);
            generatorIndexMap.remove(worldGenerator);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
