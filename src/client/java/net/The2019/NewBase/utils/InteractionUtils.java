package net.The2019.NewBase.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class InteractionUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void placeBlock(Hand hand, BlockPos blockPos, Direction direction, boolean indsideBlock){
        mc.interactionManager.interactBlock(mc.player, hand, new BlockHitResult(mc.player.getPos(), direction, blockPos, indsideBlock));
    }

}
