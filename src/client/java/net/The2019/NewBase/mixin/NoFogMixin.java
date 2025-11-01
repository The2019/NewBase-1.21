package net.The2019.NewBase.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.noFog;

@Mixin(FogRenderer.class)
public class NoFogMixin {

    @Shadow
    private static boolean fogEnabled;

    @Inject(method = "getFogBuffer", at = @At("HEAD"))
    private void onGetFogBuffer(FogRenderer.FogType fogType, CallbackInfoReturnable<?> cir) {
        // Set fogEnabled based on your config
        fogEnabled = !readModule(noFog);
    }
}