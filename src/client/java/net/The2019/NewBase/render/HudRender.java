package net.The2019.NewBase.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.The2019.NewBase.config.ColorStates;
import net.The2019.NewBase.features.hud.*;
import net.The2019.NewBase.utils.DisplayElements;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;
import static net.The2019.NewBase.config.ColorConfig.readColor;
import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.*;

public class HudRender {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final List<DisplayElements> displayElements = new ArrayList<>();
    private static final Identifier slot = Identifier.of(MOD_ID, "textures/gui/slot_22x30.png");


    public static void registerHudRendering() {
        displayElements.add(new DisplayElements("Coordinates", CoordinatesDisplay::getPositionText));
        displayElements.add(new DisplayElements("Biome", BiomeDisplay::getBiomeText));
        displayElements.add(new DisplayElements("Fps", FpsDisplay::getFpsText));
        displayElements.add(new DisplayElements("Pitch Yaw", PitchYawDisplay::getPitchYaw));
        displayElements.add(new DisplayElements("Day Count", DayCount::getDays));
        displayElements.add(new DisplayElements("Real Live Time", RealTImeDisplay::getRealWorldTime));
        displayElements.add(new DisplayElements("GPU Percent", GpuPercentDisplay::gpuPercent));

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            int yOffset = 10;

            displayElements.get(0).setActive(readModule(coordinateDisplay));
            displayElements.get(1).setActive(readModule(biomeDisplay));
            displayElements.get(2).setActive(readModule(fpsDisplay));
            displayElements.get(3).setActive(readModule(pitchYaw));
            displayElements.get(4).setActive(readModule(dayCount));
            displayElements.get(5).setActive(readModule(realLiveTime));
            displayElements.get(5).setActive(readModule(gpuPercent));

            for (DisplayElements element : displayElements) {
                    if (element.isActive()) {
                    drawContext.drawText(mc.textRenderer, element.getText(), 10, yOffset, readColor(ColorStates.hudColor).getRGB(), false);
                    yOffset += 10;
                }
            }
        });
    }



    public static void renderArmorPiece(DrawContext drawContext, ItemStack itemStack, int x, int y, float scale) {
        if (!itemStack.isEmpty()) {

            drawContext.getMatrices().push();
            drawContext.getMatrices().scale(scale, scale, scale);

            int scaledX = (int) (x / scale);
            int scaledY = (int) (y / scale);

            drawContext.drawItem(itemStack, scaledX, scaledY);

            if (itemStack.isDamageable()) {
                int durability = itemStack.getMaxDamage() - itemStack.getDamage();
                int maxDurability = itemStack.getMaxDamage();

                int barWidth = (int) (13 * ((double) durability / maxDurability));

                int barColor;

                if (durability <= maxDurability * 0.15) {
                    barColor = Color.red.getRGB();
                } else if (durability <= maxDurability * 0.25) {
                    barColor = Color.yellow.getRGB();
                } else {
                    barColor = Color.green.getRGB();
                }
                int outlineColor = Color.darkGray.getRGB();

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0 ,slot);

                drawContext.drawTexture(slot, scaledX-3, scaledY-3, 0, 0, 22, 30 , 22, 30);
                RenderSystem.disableBlend();

                drawContext.fill(scaledX + 2, scaledY + 18, scaledX + 2 + barWidth, scaledY + 20, barColor);
                drawContext.drawBorder(scaledX + 1, scaledY + 17, 15, 4, outlineColor);
            }

            drawContext.getMatrices().pop();
        }
    }
}