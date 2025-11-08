package net.the2019.newbase.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static net.the2019.newbase.NewBaseClient.MOD_ID;
import static net.the2019.newbase.config.ColorStates.hudColor;

import java.awt.Color;

public final class ColorConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "colors.json");

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
            System.err.println("Failed to initialize color configuration: " + e.getMessage());
        }
    }

    /**
     * Read a color from the configuration file by feature name.
     *
     * @param feature The feature key.
     * @return The color associated with the feature, or white if not found.
     */
    public static Color readColor(String feature) {
        if (cachedConfig != null && cachedConfig.has(feature)) {
            return new Color(cachedConfig.get(feature).getAsInt(), true); // Use ARGB for proper color handling
        }
        return Color.WHITE; // Default to white if key is missing
    }

    /**
     * Save a color to the configuration file under the given feature name.
     *
     * @param feature The feature key.
     * @param color   The color to save.
     */
    public static void saveColor(String feature, Color color) {
        if (cachedConfig == null) {
            cachedConfig = new JsonObject();
        }
        cachedConfig.addProperty(feature, color.getRGB());
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
            System.err.println("Failed to load color configuration: " + e.getMessage());
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
            System.err.println("Failed to save color configuration: " + e.getMessage());
        }
    }

    /**
     * Write default values to the configuration file.
     */
    private static void writeDefaultValues() {
        cachedConfig = new JsonObject();

        cachedConfig.addProperty(hudColor, Color.GREEN.getRGB()); // Default HUD color
        saveConfigToFile();
    }
}