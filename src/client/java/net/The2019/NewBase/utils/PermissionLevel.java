package net.The2019.NewBase.utils;

import java.util.ArrayList;

public class PermissionLevel {
    private static final ArrayList<String> allowedPlayers = new ArrayList<>();

    public static void initAllowedPlayers() {
        addPlayer("The2019");
    }

    private static void addPlayer(String playerName) {
        allowedPlayers.add(playerName);
    }
}