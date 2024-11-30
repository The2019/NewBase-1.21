package net.The2019.NewBase.features.render;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.beehiveRender;
import static net.The2019.NewBase.render.WorldRender.drawBox;
import static net.The2019.NewBase.utils.ChunkStream.getBlockEntities;

public class BeeHiveHelper {

    private static final MinecraftClient mc = MinecraftClient.getInstance();


    public static void highlightBeeHives() {
        WorldRenderEvents.END.register(context -> {
            if(readModule(beehiveRender) && (mc.player != null)) {
                renderOutlines(context);
            }
        });
    }

    private static void renderOutlines(WorldRenderContext context){
        for (BlockEntity blockEntity : getBlockEntities().collect(Collectors.toCollection(ArrayList::new))) {
            if (blockEntity instanceof BeehiveBlockEntity) {
                BlockPos blockEntityPos = blockEntity.getPos();
                drawBox(context.matrixStack(), context.consumers(), blockEntityPos, blockEntityPos, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}