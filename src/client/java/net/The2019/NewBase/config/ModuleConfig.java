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
import static net.The2019.NewBase.config.ModuleStates.*;

public final class ModuleConfig {

    private static final File configDir = Paths.get("", "config", MOD_ID).toFile();
    private static final File configFile = new File(configDir, "config.json");

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
    }

    public static boolean readModule(String module){
        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            if (json != null && json.has(module)) {
                return json.get(module).getAsBoolean();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void saveModuleState(String module, boolean state) {
        JsonObject json = new JsonObject();

        try (FileReader reader = new FileReader(configFile)) {
            json = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        json.addProperty(module, state);

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDefaultValues() {
        JsonObject json = new JsonObject();

        // Add default values here
        json.addProperty(coordinateDisplay, true);
        json.addProperty(biomeDisplay, true);
        json.addProperty(fpsDisplay, true);
        json.addProperty(beehiveRender, false);
        json.addProperty(fullBrightRender, true);
        json.addProperty(placer, false);
        json.addProperty(tridentHelper, true);
        json.addProperty(deathcoords, true);
        json.addProperty(noFog, true);
        json.addProperty(toggleCamera, true);
        json.addProperty(armorHud, true);


        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
