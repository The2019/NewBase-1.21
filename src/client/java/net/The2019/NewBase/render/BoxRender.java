package net.The2019.NewBase.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

public class BoxRender {

    private static final List<BoxToRender> boxes = new ArrayList<>();

    public static class BoxToRender {
        public Box box;
        public float red;
        public float green;
        public float blue;
        public float alpha;

        public BoxToRender(Box box, float red, float green, float blue, float alpha) {
            this.box = box;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
    }

    public static void addBox(Box box, float red, float green, float blue, float alpha) {
        boxes.add(new BoxToRender(box, red, green, blue, alpha));
    }

    public static void renderAll(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        for (BoxToRender boxToRender : boxes) {
            matrices.push();

            double centerX = (boxToRender.box.minX + boxToRender.box.maxX) / 2.0;
            double centerY = (boxToRender.box.minY + boxToRender.box.maxY) / 2.0;
            double centerZ = (boxToRender.box.minZ + boxToRender.box.maxZ) / 2.0;

            matrices.translate(centerX - cameraX, centerY - cameraY, centerZ - cameraZ);

            Box centeredBox = boxToRender.box.offset(-centerX, -centerY, -centerZ);

            DebugRenderer.drawBox(matrices, vertexConsumers, centeredBox, boxToRender.red, boxToRender.green, boxToRender.blue, boxToRender.alpha);

            matrices.pop();
        }

        boxes.clear();
    }
}