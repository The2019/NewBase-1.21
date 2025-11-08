package net.the2019.newbase.features.render;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.beehiveRender;
import static net.the2019.newbase.render.BoxRender.addBox;
import static net.the2019.newbase.utils.ChunkStream.getBlockEntities;

import java.util.ArrayList;
import java.util.stream.Collectors;
public class BeeHiveHelper {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void highlightBeeHives() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if(readModule(beehiveRender) && (mc.player != null)) {
                renderOutlines();
            }
        });
    }

    private static void renderOutlines(){
        for (BlockEntity blockEntity : getBlockEntities().collect(Collectors.toCollection(ArrayList::new))) {
            if (blockEntity instanceof BeehiveBlockEntity) {
                BlockPos blockEntityPos = blockEntity.getPos();
                Box box = new Box(blockEntityPos);
                addBox(box, 1.0f, 0f, 1.0f, 0.5f);
            }
        }
    }
}