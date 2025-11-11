package net.the2019.newbase.features.generic;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class CommandRegister {

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
    }
}