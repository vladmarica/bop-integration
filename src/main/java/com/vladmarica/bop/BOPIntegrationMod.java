package com.vladmarica.bop;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.world.generation.WorldGenFieldAssociation;
import com.vladmarica.bop.ic2.IC2ModCompat;
import com.vladmarica.bop.tweaks.BOPLegacyWorldGenerator;
import com.vladmarica.bop.tweaks.WorldGenNothing;
import com.vladmarica.bop.thaumcraft.ThaumcraftModCompat;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mod(modid = BOPIntegrationMod.MODID, version = "1.0", dependencies = "required-after:BiomesOPlenty")
public class BOPIntegrationMod {

    static final String MODID = "BOPIntegration";
    public static final Logger logger = LogManager.getLogger(MODID);
    public static Config config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new BOPLegacyWorldGenerator(), 0);

        if (config.removeNetherGravestones) {
            WorldGenFieldAssociation.associateFeature("gravesPerChunk", new WorldGenNothing());
        }

        if (config.craftableRottenFlesh) {
            GameRegistry.addShapedRecipe(new ItemStack(Items.rotten_flesh, 4), "###", "#X#", "###", '#', new ItemStack(BOPCItems.misc, 1, 3), 'X', new ItemStack(BOPCBlocks.flowers, 1, 13));
        }

        if (config.removeEnderporterRecipe) {
            if (removeRecipe(new ItemStack(BOPCItems.enderporter, 1))) {
                logger.info("Removed Enderporter recipe");
            }
            else {
                logger.error("Failed to remove Enderporter recipe!");
            }
        }

        if (config.harderBiomeFinderRecipe) {
            if (removeRecipe(new ItemStack(BOPCItems.biomeFinder, 1))) {
                GameRegistry.addShapedRecipe(new ItemStack(BOPCItems.biomeFinder, 1), "#X#", "XYX", "#X#", '#', new ItemStack(Items.emerald, 1), 'X', new ItemStack(BOPCBlocks.crystal, 1), 'Y', new ItemStack(BOPCItems.misc, 1, 10));
            }
            else {
                logger.error("Failed to remove Biome Finder recipe!");
            }
        }

        if (Loader.isModLoaded("Thaumcraft")) {
            ThaumcraftModCompat.apply();
        }
        else {
            logger.info("Thaumcraft not found - skipping integration patch");
        }

        if (Loader.isModLoaded("IC2")) {
            IC2ModCompat.apply();
        }
        else {
            logger.info("IC2 not found - skipping integration patch");
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

    @SuppressWarnings("unchecked")
    public static boolean removeRecipe(ItemStack output) {
        if (output == null) {
            return false;
        }

        try {
            CraftingManager craftingManager = CraftingManager.getInstance();
            List recipesToRemove = new ArrayList();
            for (Object obj : craftingManager.getRecipeList()) {
                if (obj instanceof IRecipe) {
                    IRecipe recipe = (IRecipe) obj;
                    ItemStack thisOutput = recipe.getRecipeOutput();
                    if (thisOutput == null) {
                        continue;
                    }

                    if (thisOutput.getItem() == output.getItem() && thisOutput.getItemDamage() == output.getItemDamage()) {
                        recipesToRemove.add(recipe);
                    }
                }
            }

            return craftingManager.getRecipeList().removeAll(recipesToRemove);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
