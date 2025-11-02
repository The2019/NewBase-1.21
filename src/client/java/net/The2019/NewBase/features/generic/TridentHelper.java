package net.the2019.newbase.features.generic;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.tridentHelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

public class TridentHelper {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void tridentHelper() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (readModule(tridentHelper)){
                if (mc.player != null && mc.player.getActiveItem().getItem() == Items.TRIDENT && mc.player.isTouchingWater()) {
                    mc.player.setSprinting(true);
                }
            }
        });
    }
}
