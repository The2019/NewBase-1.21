package net.the2019.newbase.features.render;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.block.MapColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MapRenderer {

    private static final int MAP_SIZE = 192;
    private static final int MAP_SCALE = 1;
    private static final int UPDATE_INTERVAL = 10;

    private static NativeImageBackedTexture mapTexture;
    private static Identifier mapTextureId;
    private static int tickCounter = 0;


    public static void onRenderHud(DrawContext context, RenderTickCounter tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.world == null) {
            return;
        }

        if (mapTexture == null) {
            mapTexture = new NativeImageBackedTexture("minimap", MAP_SIZE, MAP_SIZE, false);
            mapTextureId = Identifier.of("newbase", "minimap");
            MinecraftClient.getInstance().getTextureManager().registerTexture(mapTextureId, mapTexture);
        }

        tickCounter++;
        if (tickCounter >= UPDATE_INTERVAL) {
            tickCounter = 0;
            updateMap(client);
        }

        int screenWidth = client.getWindow().getScaledWidth();
        int mapX = screenWidth - MAP_SIZE - 10;
        int mapY = 10;

        context.fill(mapX - 2, mapY - 2, mapX + MAP_SIZE + 2, mapY + MAP_SIZE + 2, 0xFF000000);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, mapTextureId, mapX, mapY, 0, 0, MAP_SIZE, MAP_SIZE, MAP_SIZE,
                MAP_SIZE);

        int centerX = mapX + MAP_SIZE / 2;
        int centerY = mapY + MAP_SIZE / 2;
        context.fill(centerX - 2, centerY - 2, centerX + 2, centerY + 2, 0xFFFF0000);
    }

    private static void updateMap(MinecraftClient client) {
        World world = client.world;
        BlockPos playerPos = client.player.getBlockPos();

        NativeImage image = mapTexture.getImage();

        if (image == null) {
            return;
        }

        int halfSize = (MAP_SIZE * MAP_SCALE) / 2;

        for (int x = 0; x < MAP_SIZE; x++) {
            for (int z = 0; z < MAP_SIZE; z++) {
                int worldX = playerPos.getX() - halfSize + (x * MAP_SCALE);
                int worldZ = playerPos.getZ() - halfSize + (z * MAP_SCALE);

                int color = getColorAtPosition(world, worldX, worldZ);

                image.setColor(x, z, color);
            }
        }

        mapTexture.upload();
    }

    private static int getColorAtPosition(World world, int x, int z) {
        BlockPos.Mutable pos = new BlockPos.Mutable(x,
                world.getTopY(net.minecraft.world.Heightmap.Type.WORLD_SURFACE, x, z), z);

        for (int y = world.getTopY(net.minecraft.world.Heightmap.Type.WORLD_SURFACE, x, z); y >= world
                .getBottomY(); y--) {
            pos.setY(y);
            if (!world.getBlockState(pos).isAir()) {
                break;
            }
        }

        MapColor mapColor = world.getBlockState(pos).getMapColor(world, pos);

        int rgb = mapColor.color;

        int y = pos.getY();
        float shade = 1.0f;
        if (y < 64) {
            shade = 0.7f + (y / 64.0f) * 0.3f;
        }

        int r = (int) (((rgb >> 16) & 0xFF) * shade);
        int g = (int) (((rgb >> 8) & 0xFF) * shade);
        int b = (int) ((rgb & 0xFF) * shade);

        return 0xFF000000 | (b << 16) | (g << 8) | r;
    }
}
