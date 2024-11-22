package net.The2019.NewBase.utils;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class PermissionLevel {
    private static final ArrayList<String> allowedPlayers = new ArrayList<>();
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void initAllowedPlayers() {
        addPlayer("The2019");
        addPlayer("TheChrisgamer18");
    }

    private static void addPlayer(String playerName) {
        allowedPlayers.add(playerName);
    }

    public static boolean isPlayerAllowed() {
        return allowedPlayers.contains(mc.getSession().getUsername());
    }
}