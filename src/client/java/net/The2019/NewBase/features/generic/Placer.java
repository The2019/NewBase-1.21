package net.The2019.NewBase.features.generic;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.placer;
import static net.The2019.NewBase.utils.InteractionUtils.placeBlock;

public class Placer {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void place() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(readModule(placer)){
                if (mc.player != null && mc.interactionManager != null) {
                    BlockPos pos = mc.player.getBlockPos().add(0, -1, 0);

                    placeBlock(Hand.OFF_HAND, pos, Direction.UP, false);
                }
            }
        });
    }
}
