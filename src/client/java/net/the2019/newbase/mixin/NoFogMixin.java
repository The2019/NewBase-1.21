package net.the2019.newbase.mixin;

import net.minecraft.client.render.fog.FogRenderer;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.noFog;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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