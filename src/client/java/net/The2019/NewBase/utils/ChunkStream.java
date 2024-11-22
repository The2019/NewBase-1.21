package net.The2019.NewBase.utils;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Objects;
import java.util.stream.Stream;

public class ChunkStream {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Stream<BlockEntity> getBlockEntities() {
        return getLoadedChunks().flatMap(chunk -> chunk.getBlockEntities().values().stream());
    }

    public static Stream<WorldChunk> getLoadedChunks() {
        int radius = Math.max(2, mc.options.getClampedViewDistance()) + 3;
        int diameter = radius * 2 + 1;

        ChunkPos centerChunk = mc.player.getChunkPos();
        ChunkPos min = new ChunkPos(centerChunk.x - radius, centerChunk.z - radius);
        ChunkPos max = new ChunkPos(centerChunk.x + radius, centerChunk.z + radius);

        Stream<WorldChunk> stream = Stream.<ChunkPos>iterate(min, pos -> {
            int x = pos.x;
            int z = pos.z;
            x++;

            if (x > max.x) {
                x = min.x;
                z++;
            }

            return new ChunkPos(x, z);

        }).limit(diameter * diameter).filter(c -> mc.world.isChunkLoaded(c.x, c.z)).map(c -> mc.world.getChunk(c.x, c.z)).filter(Objects::nonNull);

        return stream;
    }
}