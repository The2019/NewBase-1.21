package net.The2019.NewBase.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;

public final class ModuleConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "config.json");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static JsonObject cachedConfig;

    public static void init() {
        if (!configDir.exists()) configDir.mkdirs();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                if (Files.size(configFile.toPath()) == 0) {
                    // The file is empty, write default values
                    writeDefaultValues();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Load the configuration into the cache
        reloadConfig();
    }

    public static boolean readModule(String module) {
        if (cachedConfig != null && cachedConfig.has(module)) {
            return cachedConfig.get(module).getAsBoolean();
        }
        return true;
    }

    public static void saveModuleState(String module, boolean state) {
        if (cachedConfig == null) {
            cachedConfig = new JsonObject();
        }

        // Update the cache
        cachedConfig.addProperty(module, state);

        // Write the updated configuration to the file
        saveConfigToFile();
    }

    public static void reloadConfig() {
        try (FileReader reader = new FileReader(configFile)) {
            cachedConfig = gson.fromJson(reader, JsonObject.class);
            if (cachedConfig == null) {
                cachedConfig = new JsonObject(); // If the file is empty or invalid
            }
        } catch (IOException e) {
            e.printStackTrace();
            cachedConfig = new JsonObject(); // Default to an empty configuration on error
        }
    }

    private static void saveConfigToFile() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(cachedConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDefaultValues() {
        cachedConfig = new JsonObject();

        // Add default values here
        cachedConfig.addProperty(ModuleStates.coordinateDisplay, true);
        cachedConfig.addProperty(ModuleStates.biomeDisplay, true);
        cachedConfig.addProperty(ModuleStates.fpsDisplay, true);
        cachedConfig.addProperty(ModuleStates.beehiveRender, false);
        cachedConfig.addProperty(ModuleStates.fullBrightRender, true);
        cachedConfig.addProperty(ModuleStates.placer, false);
        cachedConfig.addProperty(ModuleStates.tridentHelper, true);
        cachedConfig.addProperty(ModuleStates.deathcoords, true);
        cachedConfig.addProperty(ModuleStates.noFog, true);
        cachedConfig.addProperty(ModuleStates.toggleCamera, true);
        cachedConfig.addProperty(ModuleStates.armorHud, true);

        // Save to file
        saveConfigToFile();
        }
}
