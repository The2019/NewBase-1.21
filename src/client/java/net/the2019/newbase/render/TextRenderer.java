package net.the2019.newbase.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class TextRenderer {

    public static void drawLargeFloatingText(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity pos, int lineNumber, String string, int color, float size) {
        double d = 2.4;
        double e = 0.25;
        double f = (double)pos.getBlockX() + 0.5;
        double g = pos.getY() + 2.4 + (double)lineNumber * 0.25;
        double h = (double)pos.getBlockZ() + 0.5;
        float i = 0.5F;
        drawString(matrices, vertexConsumers, string, f, g, h, color, size, false, 0.5F, true);
    }

    public static void drawString(MatrixStack matrices, VertexConsumerProvider vertexConsumers, String string, double x, double y, double z, int color, float size, boolean center, float offset, boolean visibleThroughObjects) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Camera camera = minecraftClient.gameRenderer.getCamera();
        if (camera.isReady() && minecraftClient.getEntityRenderDispatcher().gameOptions != null) {
            net.minecraft.client.font.TextRenderer textRenderer = minecraftClient.textRenderer;
            double d = camera.getPos().x;
            double e = camera.getPos().y;
            double f = camera.getPos().z;
            matrices.push();
            matrices.translate((float)(x - d), (float)(y - e) + 0.07F, (float)(z - f));
            matrices.multiply(camera.getRotation());
            matrices.scale(size, -size, size);
            float g = center ? (float)(-textRenderer.getWidth(string)) / 2.0F : 0.0F;
            g -= offset / size;
            textRenderer.draw((String)string, g, 0.0F, color, false, matrices.peek().getPositionMatrix(), vertexConsumers, visibleThroughObjects ? net.minecraft.client.font.TextRenderer.TextLayerType.SEE_THROUGH : net.minecraft.client.font.TextRenderer.TextLayerType.NORMAL, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
            matrices.pop();
        }
    }
}
