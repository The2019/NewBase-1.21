package net.the2019.newbase.features.generic;

import net.minecraft.client.MinecraftClient;

public class YawSet {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void setYaw() {
        if (mc.player != null) {
            mc.player.setYaw(roundYaw(mc.player.getYaw()));
        }
    }

    private static float roundYaw(float yaw) {
        float angel = yaw % 360.0F;
        float roundYaw = Math.round(angel/ 90.0F) * 90.0F;

        if(roundYaw == 360.0F){
            roundYaw = 0.0F;
        }
        return roundYaw;
    }
}
