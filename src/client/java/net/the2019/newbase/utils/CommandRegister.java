package net.the2019.newbase.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.the2019.newbase.features.world.WorldDiffSystem;
import net.the2019.newbase.screens.ConfigScreen;

public class CommandRegister {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register( ClientCommandManager.literal("add")
                .then(ClientCommandManager.argument("a", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int a = IntegerArgumentType.getInteger(context, "a");
                                    int b = IntegerArgumentType.getInteger(context, "b");
                                    int sum = a + b;

                                    context.getSource().sendFeedback(Text.literal("Result: " + sum));
                                    return 1;
                                })
                        )
                )
        );
        dispatcher.register( ClientCommandManager.literal("subtract")
                .then(ClientCommandManager.argument("a", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int a = IntegerArgumentType.getInteger(context, "a");
                                    int b = IntegerArgumentType.getInteger(context, "b");
                                    int sum = a - b;

                                    context.getSource().sendFeedback(Text.literal("Result: " + sum));
                                    return 1;
                                })
                        )
                )
        );
        dispatcher.register( ClientCommandManager.literal("multiply")
                .then(ClientCommandManager.argument("a", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int a = IntegerArgumentType.getInteger(context, "a");
                                    int b = IntegerArgumentType.getInteger(context, "b");
                                    int sum = a * b;

                                    context.getSource().sendFeedback(Text.literal("Result: " + sum));
                                    return 1;
                                })
                        )
                )
        );
        dispatcher.register( ClientCommandManager.literal("divide")
                .then(ClientCommandManager.argument("a", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int a = IntegerArgumentType.getInteger(context, "a");
                                    int b = IntegerArgumentType.getInteger(context, "b");
                                    int sum = a / b;

                                    context.getSource().sendFeedback(Text.literal("Result: " + sum));
                                    return 1;
                                })
                        )
                )
        );
        dispatcher.register(
                ClientCommandManager.literal("newbase")
                        .then(ClientCommandManager.literal("settings")
                                .executes(context -> {
                                    mc.setScreen(new ConfigScreen(null, mc.options));
                                    return 1;
                                })
                        )/*
                        .then(ClientCommandManager.literal("map")
                                .executes(context -> {
                                    context.getSource().sendFeedback(Text.literal("Opened map"));
                                    return 1;
                                })
                        )

                        .then(ClientCommandManager.literal("fullbright")
                                .executes(context -> {
                                    context.getSource().sendFeedback(Text.literal("Fullbright toggled"));
                                    return 1;
                                })
                        )*/
        );
        dispatcher.register(ClientCommandManager.literal("worlddiff")
                .then(ClientCommandManager.literal("start")
                        .then(ClientCommandManager.argument("seed", LongArgumentType.longArg())
                                .then(ClientCommandManager.argument("radius", IntegerArgumentType.integer(1, 10))
                                        .executes(context -> {
                                            long seed = LongArgumentType.getLong(context, "seed");
                                            int radius = IntegerArgumentType.getInteger(context, "radius");
                                            WorldDiffSystem.start(seed, radius);
                                            sendMessage("Started WorldDiff with seed " + seed + " and radius " + radius);
                                            return 1;
                                        })
                                )
                                .executes(context -> {
                                    long seed = LongArgumentType.getLong(context, "seed");
                                    WorldDiffSystem.start(seed, 3);
                                    sendMessage("Started WorldDiff with seed " + seed + " and radius 3");
                                    return 1;
                                })
                        )
                        .executes(context -> {
                            long seed = System.currentTimeMillis();
                            WorldDiffSystem.start(seed, 3);
                            sendMessage("Started WorldDiff with random seed " + seed + " and radius 3");
                            return 1;
                        })
                )
                .then(ClientCommandManager.literal("stop")
                        .executes(context -> {
                            WorldDiffSystem.stop();
                            sendMessage("Stopped WorldDiff");
                            return 1;
                        })
                )
                .then(ClientCommandManager.literal("radius")
                        .then(ClientCommandManager.argument("radius", IntegerArgumentType.integer(1, 10))
                                .executes(context -> {
                                    int radius = IntegerArgumentType.getInteger(context, "radius");
                                    WorldDiffSystem.setRadius(radius);
                                    sendMessage("Set chunk radius to " + radius);
                                    return 1;
                                })
                        )
                )
                .then(ClientCommandManager.literal("stats")
                        .executes(context -> {
                            sendMessage(WorldDiffSystem.getStats());
                            return 1;
                        })
                )
        );
    }
    private static void sendMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§6[WorldDiff] §f" + message), false);
        }
    }
}
