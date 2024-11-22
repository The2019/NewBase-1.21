package net.The2019.NewBase.render.waypoints;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class WaypointRender {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void renderWaypoint(WorldRenderContext context, BlockPos pos, String name) {
        Camera camera = context.camera();

        Vec3d targetPosition = new Vec3d(0, 100, 0);
        Vec3d transformedPosition = targetPosition.subtract(camera.getPos());
        MatrixStack matrixStack = context.matrixStack();

        //matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        //matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
        matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();

        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);



        // Render text
        TextRenderer textRenderer = mc.textRenderer;
        float textX = (float) (pos.getX() - camera.getBlockPos().getX());
        float textY = (float) (pos.getY() - camera.getBlockPos().getY());
        textRenderer.draw(Text.literal(name), textX, textY, 1, false, positionMatrix, mc.getBufferBuilders().getOutlineVertexConsumers(), TextRenderer.TextLayerType.SEE_THROUGH, 1, 1);

        RenderSystem.disableBlend();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableCull();
    }

    public static void renderTextOnBlock(MatrixStack matrices, BlockPos pos, Text text) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.player == null || client.world == null) {
            return;
        }

        // Get the camera position
        double cameraX = client.getEntityRenderDispatcher().camera.getPos().x;
        double cameraY = client.getEntityRenderDispatcher().camera.getPos().y;
        double cameraZ = client.getEntityRenderDispatcher().camera.getPos().z;

        // Prepare for rendering
        matrices.push();

        // Translate to the block position relative to the camera
        double x = pos.getX() + 0.5 - cameraX;
        double y = pos.getY() + 1.5 - cameraY;
        double z = pos.getZ() + 0.5 - cameraZ;
        matrices.translate(x, y, z);

        // Rotate to face the player
        matrices.multiply(client.getEntityRenderDispatcher().camera.getRotation());

        // Flip text to prevent mirroring
        matrices.scale(-0.025F, -0.025F, 0.025F); // Adjust the scale for visibility

        // Disable depth test for text visibility
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.disableDepthTest();

        // Render the text
        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();
        client.textRenderer.draw(
                text,
                -client.textRenderer.getWidth(text) / 2.0F, // Center the text
                0,
                0xFFFFFF, // White color
                false,
                matrices.peek().getPositionMatrix(),
                vertexConsumers,
                TextRenderer.TextLayerType.NORMAL,
                0,
                15728880 // Full brightness
        );

        vertexConsumers.draw();

        // Re-enable depth test and restore matrix
        RenderSystem.enableDepthTest();
        matrices.pop();
    }
}