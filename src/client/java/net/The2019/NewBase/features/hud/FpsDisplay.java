package net.The2019.NewBase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class FpsDisplay {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Text getFpsText() {
        return Text.literal("FPS: " + mc.getCurrentFps());
    }
}
