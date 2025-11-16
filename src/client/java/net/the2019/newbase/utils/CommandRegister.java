package net.the2019.newbase.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
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
    }
}