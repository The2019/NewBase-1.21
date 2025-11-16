package net.the2019.newbase.features.hud;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import java.awt.*;

import static net.the2019.newbase.render.HudRender.slot;


public class ArmorHud {

    public static void renderArmorPiece(DrawContext drawContext, ItemStack itemStack, int x, int y) {
        if (!itemStack.isEmpty()) {

            drawContext.drawItem(itemStack, x, y);

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
                drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, slot, x-3, y-3, 0, 0, 22, 30 , 22, 30);

                drawContext.fill(x + 1, y + 18, x + 2 + barWidth, y + 20, barColor);
            }
        }
    }
}
