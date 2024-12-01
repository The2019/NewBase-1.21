package net.The2019.NewBase.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.awt.Color;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;
import static net.The2019.NewBase.config.ColorStates.hudColor;

public final class ColorConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "colors.json");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static JsonObject cachedConfig;

    public static void init() {
        if (!configDir.exists()) configDir.mkdirs();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                if (configFile.length() == 0) {
                    writeDefaultValues();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        reloadConfig();
    }

    public static Color readColor(String feature) {
        if (cachedConfig != null && cachedConfig.has(feature)) {
            return new Color(cachedConfig.get(feature).getAsInt());
        }
        return Color.WHITE;
    }

    public static void saveColor(String feature, Color color) {
        if (cachedConfig == null) {
            cachedConfig = new JsonObject();
        }
        cachedConfig.addProperty(feature, color.getRGB());
        saveConfigToFile();
    }

    public static void reloadConfig() {
        try (FileReader reader = new FileReader(configFile)) {
            cachedConfig = gson.fromJson(reader, JsonObject.class);
            if (cachedConfig == null) {
                cachedConfig = new JsonObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
            cachedConfig = new JsonObject();
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
        cachedConfig.addProperty(hudColor, Color.GREEN.getRGB());
        saveConfigToFile();
    }
}