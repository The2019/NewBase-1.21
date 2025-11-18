package net.the2019.newbase.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.the2019.newbase.features.world.WorldDiffSystem;
import net.the2019.newbase.render.BoxRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public abstract class DebugRendererMixin {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "render", at = @At("TAIL"))
    private void renderCustomBox(MatrixStack matrices, net.minecraft.client.render.Frustum frustum, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, boolean renderShapes, CallbackInfo ci) {

        WorldDiffSystem.renderChanges();

        BoxRender.renderAll(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }
}
