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
import static net.The2019.NewBase.config.IntegerStates.normalFOV;
import static net.The2019.NewBase.config.IntegerStates.zoomFOV;

public final class IntegerConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "integer_values.json");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static JsonObject cachedConfig;

    public static void init() {
        if (!configDir.exists()) configDir.mkdirs();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                if (Files.size(configFile.toPath()) == 0) {
                    writeDefaultValues();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        reloadConfig();
    }

    public static int readValue(String key) {
        if (cachedConfig != null && cachedConfig.has(key)) {
            return cachedConfig.get(key).getAsInt();
        }
        return 0;
    }

    public static void saveValue(String key, int value) {
        if (cachedConfig == null) {
            cachedConfig = new JsonObject();
        }

        cachedConfig.addProperty(key, value);

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

        // Example default values (add as needed)
        cachedConfig.addProperty(zoomFOV, 30);
        cachedConfig.addProperty(normalFOV, 110);

        saveConfigToFile();
    }
}