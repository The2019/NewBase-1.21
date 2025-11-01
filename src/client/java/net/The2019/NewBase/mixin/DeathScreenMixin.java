package net.The2019.NewBase.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.deathcoords;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
    private static String deathCoordsMessage = "";

    @Inject(at = @At("TAIL"), method = "<init>")
    private void constructor(Text message, boolean isHardcore, CallbackInfo ci) {
        if(readModule(deathcoords)) {
           MinecraftClient minecraftClient = MinecraftClient.getInstance();
           Vec3d pos = minecraftClient.player.getEntityPos();
           Identifier dimensionType = minecraftClient.world.getRegistryKey().getValue();
           String dimension = "";
           if (dimensionType.equals(DimensionTypes.OVERWORLD_ID)) {
               dimension = "Overworld";
           } else if (dimensionType.equals(DimensionTypes.THE_NETHER_ID)) {
               dimension = "Nether";
           } else if (dimensionType.equals(DimensionTypes.THE_END_ID)) {
               dimension = "End";
           }
           deathCoordsMessage = String.format("You died at X: %.1f, Y: %.1f, Z: %.1f in the %s.", pos.x, pos.y, pos.z, dimension);
           MutableText deathCoordsText = Text.literal(deathCoordsMessage);
           minecraftClient.player.sendMessage(deathCoordsText, false);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(readModule(deathcoords)) {
            DeathScreen screen = (DeathScreen) (Object) this;
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            context.drawTextWithShadow(minecraftClient.textRenderer, deathCoordsMessage,
                    (screen.width / 2) - (minecraftClient.textRenderer.getWidth(deathCoordsMessage) / 2), 120, Color.WHITE.getRGB());
        }
    }
}