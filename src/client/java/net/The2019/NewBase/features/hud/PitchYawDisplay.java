package net.The2019.NewBase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class PitchYawDisplay {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static float getPitch(){
        assert mc.player != null;
        return mc.player.getPitch();
    }
    private static float getYaw(){
        assert mc.player != null;
        return mc.player.getYaw();
    }

    public static Text getPitchYaw(){
        return Text.literal("Pitch: " + String.format("%.1f", getPitch()) + " | Yaw: " + String.format("%.1f", getYaw()));
    }
}
