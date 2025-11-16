package net.the2019.newbase.mixin;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
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

    }
}


/*
player.getMainHandStack()
player.getOffHandStack()
 */