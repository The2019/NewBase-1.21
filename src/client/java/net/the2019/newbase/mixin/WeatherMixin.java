package net.the2019.newbase.mixin;

import net.minecraft.client.render.WorldRenderer;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.noRain;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WeatherMixin {

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(CallbackInfo ci) {
        if (readModule(noRain)) {
            ci.cancel();
        }
    }
}