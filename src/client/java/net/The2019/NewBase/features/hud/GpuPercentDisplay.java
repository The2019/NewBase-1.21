package net.The2019.NewBase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class GpuPercentDisplay {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Text gpuPercent(){
        return Text.of("GPU: " + mc.getGpuUtilizationPercentage() + "%");
    }
}
