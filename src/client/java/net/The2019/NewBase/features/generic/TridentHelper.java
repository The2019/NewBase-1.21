package net.The2019.NewBase.features.generic;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.tridentHelper;

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
