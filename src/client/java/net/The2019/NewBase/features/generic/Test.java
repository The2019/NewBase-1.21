package net.The2019.NewBase.features.generic;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class Test {

    private static MinecraftClient mc = MinecraftClient.getInstance();
    private static Entity entity = null;

    public static void test(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.world != null) {
                entity = mc.world.getEntities().iterator().next();
            }
            if (mc.interactionManager != null) {
                if(entity != mc.player){
                    mc.interactionManager.attackEntity(mc.player,entity);
                    mc.player.sendMessage(entity.getName());
                }
            }
        });
    }
}
