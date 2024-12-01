package net.The2019.NewBase.utils;

import net.The2019.NewBase.features.generic.YawSet;
import net.The2019.NewBase.screens.ChatCoordinatesScreen;
import net.The2019.NewBase.screens.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import static net.The2019.NewBase.config.KeyBindsConfig.readKeyCode;
import static net.The2019.NewBase.config.KeyBindsStates.*;
import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleConfig.saveModuleState;
import static net.The2019.NewBase.config.ModuleStates.toggleCamera;

public class InitKeyBindings {

    private static final MinecraftClient mc = MinecraftClient.getInstance();


    public static void initKeyBinds(){
        KeyBinding configScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.configscreen", GLFW.GLFW_KEY_O, "newbase.name"));

        KeyBinding chatCoordinates = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.sendcoordinates", GLFW.GLFW_KEY_P, "newbase.name"));

        KeyBinding toggleYawSet = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.toggletest", GLFW.GLFW_KEY_J, "newbase.name"));

        KeyBinding toggleCameraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("newbase.keybinds.togglecamera", GLFW.GLFW_KEY_K, "newbase.name"));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(configScreen.wasPressed()){
                mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));
            }
            if(chatCoordinates.wasPressed()){
                mc.setScreen(new ChatCoordinatesScreen(mc.currentScreen, mc.options));
            }
            if(toggleYawSet.wasPressed()){
                YawSet.setYaw();
            }
            if(toggleCameraKey.wasPressed()){
                saveModuleState(toggleCamera, !readModule(toggleCamera));
            }
        });
    }
}
