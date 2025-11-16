package net.the2019.newbase.features.generic;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.tridentHelper;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

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
