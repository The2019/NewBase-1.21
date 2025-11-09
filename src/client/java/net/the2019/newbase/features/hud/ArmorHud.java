package net.the2019.newbase.features.hud;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import java.awt.*;

import static net.the2019.newbase.render.HudRender.slot;


public class ArmorHud {

    public static void renderArmorPiece(DrawContext drawContext, ItemStack itemStack, int x, int y, float scale) {
        if (!itemStack.isEmpty()) {
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

                drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, slot, scaledX-3, scaledY-3, 0, 0, 22, 30 , 22, 30);

                drawContext.fill(scaledX + 2, scaledY + 18, scaledX + 2 + barWidth, scaledY + 20, barColor);
                drawContext.drawStrokedRectangle(scaledX + 1, scaledY + 17, 15, 4, outlineColor);
            }
        }
    }
}
