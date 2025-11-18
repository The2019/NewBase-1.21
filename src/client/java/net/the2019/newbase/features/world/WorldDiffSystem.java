package net.the2019.newbase.features.world;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.the2019.newbase.render.BoxRender;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages chunk loading around player and highlights changed blocks
 */
public class WorldDiffSystem {

    private static boolean enabled = false;
    private static int chunkRadius = 3; // chunks around player to load
    private static ChunkPos lastPlayerChunk = null;
    private static final Set<ChunkPos> loadedChunks = new HashSet<>();
    private static final Map<BlockPos, BlockChange> changedBlocks = new HashMap<>();

    public static class BlockChange {
        public final BlockState originalState;
        public final BlockState currentState;

        public BlockChange(BlockState originalState, BlockState currentState) {
            this.originalState = originalState;
            this.currentState = currentState;
        }

        public boolean isAddition() {
            return originalState.isAir() && !currentState.isAir();
        }

        public boolean isRemoval() {
            return !originalState.isAir() && currentState.isAir();
        }

        public boolean isModification() {
            return !originalState.isAir() && !currentState.isAir();
        }
    }

    /**
     * Start the system with a seed and chunk radius
     */
    public static void start(long seed, int radius) {
        GenWorld.initBackgroundWorld(seed);
        chunkRadius = radius;
        enabled = true;
        lastPlayerChunk = null;
        loadedChunks.clear();
        changedBlocks.clear();
        System.out.println("WorldDiff started - Seed: " + seed + ", Radius: " + radius + " chunks");
    }

    /**
     * Stop the system
     */
    public static void stop() {
        enabled = false;
        loadedChunks.clear();
        changedBlocks.clear();
        GenWorld.cleanup();
        System.out.println("WorldDiff stopped");
    }

    /**
     * Set chunk radius
     */
    public static void setRadius(int radius) {
        chunkRadius = Math.max(1, Math.min(radius, 10));
    }

    /**
     * Call this every tick from your client tick event
     */
    public static void tick() {
        if (!enabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        ClientWorld backgroundWorld = GenWorld.getBackgroundWorld();
        if (backgroundWorld == null) return;

        ChunkPos playerChunk = client.player.getChunkPos();

        // Check if player moved to a new chunk
        if (lastPlayerChunk == null || !lastPlayerChunk.equals(playerChunk)) {
            lastPlayerChunk = playerChunk;
            updateLoadedChunks(client.world, backgroundWorld, playerChunk);
        }
    }

    /**
     * Update which chunks are loaded based on player position
     */
    private static void updateLoadedChunks(ClientWorld realWorld, ClientWorld backgroundWorld, ChunkPos playerChunk) {
        Set<ChunkPos> newChunks = new HashSet<>();

        // Calculate which chunks should be loaded
        for (int x = -chunkRadius; x <= chunkRadius; x++) {
            for (int z = -chunkRadius; z <= chunkRadius; z++) {
                ChunkPos chunkPos = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
                newChunks.add(chunkPos);
            }
        }

        // Remove changes from chunks that are no longer loaded
        Set<ChunkPos> chunksToUnload = new HashSet<>(loadedChunks);
        chunksToUnload.removeAll(newChunks);

        for (ChunkPos chunkToUnload : chunksToUnload) {
            // Remove all block changes from this chunk
            changedBlocks.entrySet().removeIf(entry -> {
                BlockPos pos = entry.getKey();
                return new ChunkPos(pos).equals(chunkToUnload);
            });

            // Unload chunk from background world
            WorldChunk chunk = backgroundWorld.getChunk(chunkToUnload.x, chunkToUnload.z);
            if (chunk != null) {
                backgroundWorld.unloadBlockEntities(chunk);
            }
        }

        // Scan new chunks
        Set<ChunkPos> chunksToLoad = new HashSet<>(newChunks);
        chunksToLoad.removeAll(loadedChunks);

        for (ChunkPos chunkToLoad : chunksToLoad) {
            scanChunk(realWorld, backgroundWorld, chunkToLoad);
        }

        loadedChunks.clear();
        loadedChunks.addAll(newChunks);

        System.out.println("Loaded chunks: " + loadedChunks.size() + ", Changes: " + changedBlocks.size());
    }

    /**
     * Scan a chunk and detect all changed blocks
     */
    /**
     * * <h4>Backup if above dosen't work</h1>
     * *
     * * int minY = realWorld.getBottomY();
     * * int maxY = realWorld.getTopY(Heightmap.Type.WORLD_SURFACE, chunkPos.x , chunkPos.z);
     *
     */
    private static void scanChunk(ClientWorld realWorld, ClientWorld backgroundWorld, ChunkPos chunkPos) {
        WorldChunk realChunk = realWorld.getChunk(chunkPos.x, chunkPos.z);

        // Force generate the background chunk properly
        backgroundWorld.getChunk(chunkPos.x, chunkPos.z);
        WorldChunk backgroundChunk = backgroundWorld.getChunkManager().getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, true);

        if (realChunk == null || backgroundChunk == null) {
            System.out.println("Chunk is null at " + chunkPos);
            return;
        }

        int minY = realWorld.getBottomY();
        int maxY = realWorld.getHeight();

        BlockPos.Mutable pos = new BlockPos.Mutable();

        int debugCount = 0;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = minY; y < maxY; y++) {
                    pos.set(chunkPos.getStartX() + x, y, chunkPos.getStartZ() + z);

                    BlockState realState = realChunk.getBlockState(pos);
                    BlockState backgroundState = backgroundChunk.getBlockState(pos);

                    if (realState.isAir() && backgroundState.isAir()) continue;
                    if (shouldIgnore(realState) && shouldIgnore(backgroundState)) continue;

                    if (!realState.getBlock().equals(backgroundState.getBlock())) {
                        if (debugCount < 10) {
                            System.out.println("Difference at " + pos + ":");
                            System.out.println("  Real: " + realState.getBlock().getTranslationKey());
                            System.out.println("  Fake: " + backgroundState.getBlock().getTranslationKey());
                            debugCount++;
                        }

                        changedBlocks.put(pos.toImmutable(), new BlockChange(backgroundState, realState));
                    }
                }
            }
        }
    }

    private static boolean shouldIgnore(BlockState state) {
        String blockId = state.getBlock().getTranslationKey();
        return blockId.contains("water") ||
            blockId.contains("grass") ||
            blockId.contains("flower") ||
            blockId.contains("leaves") ||
            blockId.contains("tall_grass") ||
            blockId.contains("seagrass") ||
            blockId.contains("kelp") ||
            blockId.contains("fern");
    }

    /**
     * Render all changed blocks - call this from your render mixin
     */
    public static void renderChanges() {
        if (!enabled || changedBlocks.isEmpty()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double playerX = client.player.getX();
        double playerY = client.player.getY();
        double playerZ = client.player.getZ();
        double maxDistSq = 64 * 64; // Only render within 64 blocks

        for (Map.Entry<BlockPos, BlockChange> entry : changedBlocks.entrySet()) {
            BlockPos pos = entry.getKey();
            BlockChange change = entry.getValue();

            // Distance check
            double distSq = pos.getSquaredDistance(playerX, playerY, playerZ);
            if (distSq > maxDistSq) continue;

            // Get color based on change type
            float red, green, blue;
            if (change.isAddition()) {
                // Green for added blocks
                red = 0.0f;
                green = 1.0f;
                blue = 0.0f;
            } else if (change.isRemoval()) {
                // Red for removed blocks
                red = 1.0f;
                green = 0.0f;
                blue = 0.0f;
            } else {
                // Yellow for modified blocks
                red = 1.0f;
                green = 1.0f;
                blue = 0.0f;
            }

            // Calculate alpha based on distance
            float alpha = 1.0f - (float)(Math.sqrt(distSq) / 64.0);
            alpha = Math.max(0.3f, Math.min(0.8f, alpha));

            // Create box around block
            Box box = new Box(
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
            );

            BoxRender.addBox(box, red, green, blue, alpha);
        }
    }

    /**
     * Get statistics
     */
    public static String getStats() {
        int additions = 0;
        int removals = 0;
        int modifications = 0;

        for (BlockChange change : changedBlocks.values()) {
            if (change.isAddition()) additions++;
            else if (change.isRemoval()) removals++;
            else if (change.isModification()) modifications++;
        }

        return String.format("Chunks: %d | Changes: %d (Added: %d, Removed: %d, Modified: %d)",
                loadedChunks.size(), changedBlocks.size(), additions, removals, modifications);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static int getChangedBlockCount() {
        return changedBlocks.size();
    }
}