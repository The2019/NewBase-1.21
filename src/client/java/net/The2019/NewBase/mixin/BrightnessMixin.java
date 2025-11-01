package net.The2019.NewBase.mixin;

import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.fullBrightRender;

@Mixin(DimensionType.class)
public abstract class BrightnessMixin {

    @Inject(method = "ambientLight", at = @At("HEAD"), cancellable = true)
    private void onAmbientLight(CallbackInfoReturnable<Float> cir) {
        if (readModule(fullBrightRender)) {
            cir.setReturnValue(1.0f);
        }
    }
}