package net.The2019.NewBase.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;


public class WorldRender {
    /*
    public static void renderOutline(WorldRenderContext context, Box box, float[] colorComponents, float lineWidth, boolean throughWalls) {

        MatrixStack matrices = context.matrixStack();
        Vec3d camera = context.camera().getPos();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(throughWalls ? GL11.GL_ALWAYS : GL11.GL_LEQUAL);

        matrices.push();
        matrices.translate(-camera.getX(), -camera.getY(), -camera.getZ());

        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        WorldRenderer.drawBox(matrices, buffer, box, colorComponents[0], colorComponents[1], colorComponents[2], 1f);
        tessellator.draw();

        matrices.pop();
        RenderSystem.lineWidth(1f);
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
    */

    public static void renderBlock(WorldRenderContext context, Box box){

        Vec3d camera = context.camera().getPos();
        MatrixStack matrixStack = context.matrixStack();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.LINES ,VertexFormats.LINES);

        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.lineWidth(1f);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        matrixStack.push();
        matrixStack.translate(-camera.getX(), -camera.getY(), -camera.getZ());

        WorldRenderer.drawBox(matrixStack, buffer, box, 1f, 1f, 1f, 1f);
        tessellator.clear();

        matrixStack.pop();
        RenderSystem.lineWidth(1f);
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }



    public static void renderBox(WorldRenderContext context, Box box){
        MatrixStack matrixStack = context.matrixStack();
        Tessellator tessellator = Tessellator.getInstance();
        Vec3d camera = context.camera().getPos();

        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);

        matrixStack.push();
        matrixStack.translate(camera.getX(), +camera.getY(), -camera.getZ());

        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.lineWidth(1f);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        WorldRenderer.drawBox(matrixStack, bufferBuilder, box , 1.0f, 1.0f, 1.0f, 1.0f);

        matrixStack.pop();

        RenderSystem.lineWidth(1f);
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

    }

    public static void drawString(MatrixStack matrices, VertexConsumerProvider vertexConsumers, String string, double x, double y, double z, int color, float size, boolean center, float offset, boolean visibleThroughObjects) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Camera camera = minecraftClient.gameRenderer.getCamera();
        if (camera.isReady() && minecraftClient.getEntityRenderDispatcher().gameOptions != null) {
            TextRenderer textRenderer = minecraftClient.textRenderer;
            double d = camera.getPos().x;
            double e = camera.getPos().y;
            double f = camera.getPos().z;
            matrices.push();
            matrices.translate((float)(x - d), (float)(y - e) + 0.07F, (float)(z - f));
            matrices.multiply(camera.getRotation());
            matrices.scale(size, -size, size);
            float g = center ? (float)(-textRenderer.getWidth(string)) / 2.0F : 0.0F;
            g -= offset / size;
            textRenderer.draw(string, g, 0.0F, color, false, matrices.peek().getPositionMatrix(), vertexConsumers, visibleThroughObjects ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            matrices.pop();
        }
    }

    public static void drawBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockPos pos1, BlockPos pos2, float red, float green, float blue, float alpha) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        if (camera.isReady()) {
            Vec3d vec3d = camera.getPos().negate();
            Box box = Box.enclosing(pos1, pos2).offset(vec3d);
            drawBox(matrices, vertexConsumers, box, red, green, blue, alpha);
        }
    }

    public static void drawBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Box box, float red, float green, float blue, float alpha) {
        drawBox(matrices, vertexConsumers, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
    }

    public static void drawBox(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getDebugFilledBox());
        WorldRenderer.renderFilledBox(matrices, vertexConsumer, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
    }
}
