package net.the2019.newbase.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import static net.the2019.newbase.NewBaseClient.MOD_ID;
import static net.the2019.newbase.config.IntegerStates.normalFOV;
import static net.the2019.newbase.config.IntegerStates.zoomFOV;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public final class IntegerConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "integer_values.json");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static JsonObject cachedConfig;

    /**
     * Initialize the configuration system by loading the file or creating it if it doesn't exist.
     */
    public static void init() {
        try {
            if (!configDir.exists()) {
                boolean dirCreated = configDir.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create configuration directory.");
                }
            }

            if (!configFile.exists()) {
                boolean fileCreated = configFile.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Failed to create configuration file.");
                }
                writeDefaultValues(); // Write defaults on the first run
            }

            reloadConfig(); // Load the configuration
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize configuration: " + e.getMessage());
        }
    }

    /**
     * Read an integer value from the configuration file by key.
     * @param key The key to read.
     * @return The integer value associated with the key, or 0 if not found.
     */
    public static int readValue(String key) {
        if (cachedConfig != null && cachedConfig.has(key)) {
            return cachedConfig.get(key).getAsInt();
        }
        return 0; // Default to 0 if the key is missing
    }

    /**
     * Save an integer value to the configuration file under the given key.
     * @param key The key to write.
     * @param value The value to associate with the key.
     */
    public static void saveValue(String key, int value) {
        if (cachedConfig == null) {
            cachedConfig = new JsonObject();
        }
        cachedConfig.addProperty(key, value);
        saveConfigToFile();
    }

    /**
     * Reload the configuration file into memory.
     */
    public static void reloadConfig() {
        try (FileReader reader = new FileReader(configFile)) {
            cachedConfig = gson.fromJson(reader, JsonObject.class);
            if (cachedConfig == null) {
                cachedConfig = new JsonObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load configuration: " + e.getMessage());
            cachedConfig = new JsonObject();
        }
    }

    /**
     * Save the cached configuration to the file.
     */
    private static void saveConfigToFile() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(cachedConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Write default values to the configuration file.
     */
    private static void writeDefaultValues() {
        cachedConfig = new JsonObject();

        cachedConfig.addProperty(zoomFOV, 30); // Default zoom FOV
        cachedConfig.addProperty(normalFOV, 110); // Default normal FOV

        saveConfigToFile();
    }
}