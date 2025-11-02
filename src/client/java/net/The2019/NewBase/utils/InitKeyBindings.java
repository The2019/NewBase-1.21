package net.The2019.NewBase.utils;

import net.The2019.NewBase.features.generic.YawSet;
import net.The2019.NewBase.screens.ChatCoordinatesScreen;
import net.The2019.NewBase.screens.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;
import static net.The2019.NewBase.config.IntegerConfig.readValue;
import static net.The2019.NewBase.config.IntegerStates.normalFOV;
import static net.The2019.NewBase.config.IntegerStates.zoomFOV;
import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleConfig.saveModuleState;
import static net.The2019.NewBase.config.ModuleStates.*;

public class InitKeyBindings {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static final KeyBinding.Category NEWBASE_CATEGORY = KeyBinding.Category.create(Identifier.of(MOD_ID, "name"));

    public static void initKeyBinds() {
        KeyBinding configScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.configscreen", GLFW.GLFW_KEY_O, NEWBASE_CATEGORY));

        KeyBinding chatCoordinates = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.sendcoordinates", GLFW.GLFW_KEY_UNKNOWN, NEWBASE_CATEGORY));

        KeyBinding toggleYawSet = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.toggleyaw", GLFW.GLFW_KEY_UNKNOWN, NEWBASE_CATEGORY));

        KeyBinding toggleCameraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglecamera", GLFW.GLFW_KEY_UNKNOWN, NEWBASE_CATEGORY));

        KeyBinding toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglefullbright", GLFW.GLFW_KEY_H, NEWBASE_CATEGORY));

        KeyBinding toggleNoFog = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglenofog", GLFW.GLFW_KEY_UNKNOWN, NEWBASE_CATEGORY));

        KeyBinding toggleZoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglezoom", GLFW.GLFW_KEY_V, NEWBASE_CATEGORY));

        KeyBinding toggleNoRain = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglenorain", GLFW.GLFW_KEY_UNKNOWN, NEWBASE_CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (configScreen.wasPressed()) {
                mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));
            }
            if (chatCoordinates.wasPressed()) {
                mc.setScreen(new ChatCoordinatesScreen(mc.currentScreen, mc.options));
            }
            if (toggleYawSet.wasPressed()) {
                YawSet.setYaw();
            }
            if (toggleCameraKey.wasPressed()) {
                saveModuleState(toggleCamera, !readModule(toggleCamera));
            }
            if (toggleFullBright.wasPressed()) {
                saveModuleState(fullBrightRender, !readModule(fullBrightRender));
            }
            if (toggleNoFog.wasPressed()) {
                saveModuleState(noFog, !readModule(noFog));
            }
            if (toggleNoRain.wasPressed()) {
                saveModuleState(noRain, !readModule(noRain));
            }
            if (toggleZoomKey.isPressed()) {
                if(readModule(toggleZoom)){
                    mc.options.getFov().setValue(readValue(zoomFOV));
                }
            }else {
                if(readModule(toggleZoom)){
                    if(readValue(normalFOV) != mc.options.getFov().getValue()) {
                        mc.options.getFov().setValue(readValue(normalFOV));
                    }
                }
            }
        });
    }
}