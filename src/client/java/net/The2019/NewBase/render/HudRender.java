package net.The2019.NewBase.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.The2019.NewBase.features.hud.BiomeDisplay;
import net.The2019.NewBase.features.hud.CoordinatesDisplay;
import net.The2019.NewBase.features.hud.FpsDisplay;
import net.The2019.NewBase.utils.DisplayElements;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;
import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.*;

public class HudRender {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final List<DisplayElements> displayElements = new ArrayList<>();
    private static final Identifier slot = Identifier.of(MOD_ID, "textures/gui/slot_22x30.png");


    public static void registerHudRendering() {
        displayElements.add(new DisplayElements("Coordinates", Color.GREEN.getRGB(), CoordinatesDisplay::getPositionText));
        displayElements.add(new DisplayElements("Biome", Color.GREEN.getRGB(), BiomeDisplay::getBiomeText));
        displayElements.add(new DisplayElements("Fps", Color.GREEN.getRGB(), FpsDisplay::getFpsText));

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            int yOffset = 10;

            displayElements.get(0).setActive(readModule(coordinateDisplay));
            displayElements.get(1).setActive(readModule(biomeDisplay));
            displayElements.get(2).setActive(readModule(fpsDisplay));

            for (DisplayElements element : displayElements) {
                if (element.isActive()) {
                    drawContext.drawText(mc.textRenderer, element.getText(), 10, yOffset, element.getColor(), false);
                    yOffset += 10; // Adjust the Y position for the next element
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


    public static void updateCamera() {
        if (readModule(toggleCamera)) {
            MinecraftClient client = MinecraftClient.getInstance();

            // Position the RTS camera at a fixed angle above the saved player position
            double rtsCameraHeight = 20.0;
            double rtsCameraDistance = 20.0;
            double angleRadians = Math.toRadians(45); // 45-degree angle

            double offsetX = Math.sin(angleRadians) * rtsCameraDistance;
            double offsetZ = Math.cos(angleRadians) * rtsCameraDistance;

            // Set the camera position and direction

        }
    }
}