package net.the2019.newbase.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.the2019.newbase.NewBaseClient.MOD_ID;

@Mixin(LivingEntityRenderer.class)
public abstract class PlayerIconMixin {

    @Unique
    private static final Identifier texture = Identifier.of(MOD_ID, "textures/gui/slot_22x30.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void injectRenderIcon(LivingEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState camera, CallbackInfo ci) {
        if (!(state.entityType.getName() instanceof LivingEntity entity)) return;

        if (!(entity instanceof net.minecraft.client.network.AbstractClientPlayerEntity player)) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.getCameraEntity() == null) return;

        double distSq = mc.player.squaredDistanceTo(player);
        if (distSq > 64 * 64) return; // only render nearby players

        matrices.push();

        // move to position above head
        matrices.translate(0, entity.getHeight() + 0.5f, 0);
        matrices.scale(-0.025f, -0.025f, 0.025f);

        DrawContext drawContext = new DrawContext(mc, new GuiRenderState());
        drawContext.drawTexture(RenderPipelines.RENDERTYPE_TEXT, texture, 30, 0, 0, 0, 16, 16, 16, 16);

        matrices.pop();
    }
}


/*
player.getMainHandStack()
player.getOffHandStack()
 */