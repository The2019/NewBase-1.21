package net.The2019.NewBase.features.waypoints;

import net.The2019.NewBase.render.WorldRender;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashMap;

public class Waypoints {
    public static HashMap<BlockPos, String> waypoints = new HashMap<>();

    public static void init() {
        addWaypoint(new BlockPos(0, 70, 0), "Test");
    }

    public static void addWaypoint(BlockPos pos, String name) {
        waypoints.put(pos, name);
    }

    public static void renderWaypoints() {
        WorldRenderEvents.END.register(context -> {
            //for (BlockPos pos : waypoints.keySet()) {
                WorldRender.drawString(context.matrixStack(), context.consumers(), String.valueOf(Text.literal("Test")), 0, 100, 0, Color.BLACK.getRGB(), 10f, true, 0f, true);
            //}
        });
    }
}