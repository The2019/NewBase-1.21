package net.The2019.NewBase.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.fullBrightRender;

@Mixin(LightmapTextureManager.class)
abstract class BrightnessMixin{
    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> cir) {
        if(readModule(fullBrightRender)) {
            cir.setReturnValue(1f);
        }
    }
}
