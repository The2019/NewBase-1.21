package net.the2019.newbase.features.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class GenWorld {

    private static ClientWorld backgroundWorld;

    /**
     * Creates a background ClientWorld with a specific seed
     * @param seed The world seed to use
     * @return A new ClientWorld instance
     */
    public static ClientWorld createWorld(long seed) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null || client.getNetworkHandler() == null) {
            throw new IllegalStateException("Cannot create background world - client world not loaded");
        }

        // Create world properties for the background world
        ClientWorld.Properties properties = new ClientWorld.Properties(
                Difficulty.NORMAL,  // difficulty
                false,              // hardcore
                false               // flatWorld
        );

        // Set initial spawn point
        properties.setSpawnPoint(new net.minecraft.world.WorldProperties.SpawnPoint(
                new GlobalPos(World.OVERWORLD,new BlockPos(0, 64, 0)),
                0.0F,
                0.0F
        ));

        // Get dimension type from current world
        var dimensionType = client.world.getDimensionEntry();

        // Create the background world
        ClientWorld world = new ClientWorld(
                client.getNetworkHandler(),
                properties,
                World.OVERWORLD,
                dimensionType,
                2,                      // loadDistance
                2,                      // simulationDistance
                client.worldRenderer,
                false,                  // debugWorld
                seed,
                64                      // seaLevel
        );

        return world;
    }

    /**
     * Creates a background world with a random seed
     * @return A new ClientWorld instance with random seed
     */
    public static ClientWorld createWorldRandom() {
        long randomSeed = System.currentTimeMillis();
        return createWorld(randomSeed);
    }

    /**
     * Initialize and store a background world
     * @param seed The seed to use
     */
    public static void initBackgroundWorld(long seed) {
        backgroundWorld = createWorld(seed);
        System.out.println("Background world created with seed: " + seed);
    }

    /**
     * Get the stored background world
     * @return The background world, or null if not initialized
     */
    public static ClientWorld getBackgroundWorld() {
        return backgroundWorld;
    }

    /**
     * Compare biomes between the real world and background world
     */
    public static void compareBiomes(BlockPos pos) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null) {
            System.err.println("No client world loaded");
            return;
        }

        if (backgroundWorld == null) {
            System.err.println("Background world not initialized");
            return;
        }

        var realBiome = client.world.getBiome(pos).value();
        var backgroundBiome = backgroundWorld.getBiome(pos).value();

        System.out.println("Position: " + pos);
        System.out.println("Real World Biome: " + realBiome);
        System.out.println("Background World Biome: " + backgroundBiome);
        System.out.println("Same biome: " + realBiome.equals(backgroundBiome));
    }

    /**
     * Compare block states between worlds
     */
    public static void compareBlocks(BlockPos pos) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null || backgroundWorld == null) {
            System.err.println("Worlds not ready");
            return;
        }

        var realBlock = client.world.getBlockState(pos);
        var backgroundBlock = backgroundWorld.getBlockState(pos);

        System.out.println("Position: " + pos);
        System.out.println("Real World Block: " + realBlock.getBlock());
        System.out.println("Background World Block: " + backgroundBlock.getBlock());
        System.out.println("Same block: " + realBlock.equals(backgroundBlock));
    }

    /**
     * Test method to demonstrate usage
     */
    public static void test() {
        try {
            // Create background world with specific seed
            long seed = 123456789L;
            initBackgroundWorld(seed);

            // Compare at a specific position
            BlockPos testPos = new BlockPos(0, 64, 0);
            compareBiomes(testPos);

            // Compare at player position
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                BlockPos playerPos = client.player.getBlockPos();
                compareBiomes(playerPos);
                compareBlocks(playerPos);
            }

        } catch (Exception e) {
            System.err.println("Error creating background world: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clean up the background world
     */
    public static void cleanup() {
        if (backgroundWorld != null) {
            backgroundWorld = null;
            System.out.println("Background world cleaned up");
        }
    }
}