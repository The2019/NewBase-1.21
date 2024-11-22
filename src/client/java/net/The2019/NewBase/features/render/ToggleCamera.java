package net.The2019.NewBase.features.render;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ToggleCamera {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void changeCameraPosition(){
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (mc.cameraEntity != null) {
            }
        });
    }
}
