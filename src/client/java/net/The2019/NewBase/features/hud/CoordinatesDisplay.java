package net.the2019.newbase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CoordinatesDisplay {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static Text getPositionText() {
        if (mc.player != null) {
            BlockPos playerPos = mc.player.getBlockPos();
            return Text.literal(String.format("X: %s Y: %s Z: %s", playerPos.getX(), playerPos.getY(), playerPos.getZ()));
        }
        return null;
    }
}