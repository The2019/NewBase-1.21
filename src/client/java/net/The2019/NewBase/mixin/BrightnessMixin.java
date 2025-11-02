package net.the2019.newbase.mixin;

import net.minecraft.world.dimension.DimensionType;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.fullBrightRender;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public abstract class BrightnessMixin {

    @Inject(method = "ambientLight", at = @At("HEAD"), cancellable = true)
    private void onAmbientLight(CallbackInfoReturnable<Float> cir) {
        if (readModule(fullBrightRender)) {
            cir.setReturnValue(1.0f);
        }
    }
}