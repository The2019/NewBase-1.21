package net.The2019.NewBase.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;

public class SendPacket {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void sendPacket(Packet<?> packet) {
        if (mc.player != null) {
            mc.player.networkHandler.sendPacket(packet);
        }
    }
}
