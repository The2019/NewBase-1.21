package net.the2019.newbase.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.the2019.newbase.config.ColorStates;
import net.the2019.newbase.features.hud.*;
import net.the2019.newbase.utils.DisplayElements;

import java.util.ArrayList;
import java.util.List;

import static net.the2019.newbase.NewBaseClient.MOD_ID;
import static net.the2019.newbase.config.ColorConfig.readColor;
import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleStates.*;
import static net.the2019.newbase.features.hud.ArmorHud.renderArmorPiece;

public class HudRender {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final List<DisplayElements> displayElements = new ArrayList<>();
    public static final Identifier slot = Identifier.of(MOD_ID, "textures/gui/slot_22x30.png");
    private static final float scale = 1f;

    public static void initRender(){
        displayElements.add(new DisplayElements("Coordinates", CoordinatesDisplay::getPositionText));
        displayElements.add(new DisplayElements("Biome", BiomeDisplay::getBiomeText));
        displayElements.add(new DisplayElements("Fps", FpsDisplay::getFpsText));
        displayElements.add(new DisplayElements("Pitch Yaw", PitchYawDisplay::getPitchYaw));
        displayElements.add(new DisplayElements("Day Count", DayCount::getDays));
        displayElements.add(new DisplayElements("Real Live Time", RealTImeDisplay::getRealWorldTime));

    }

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        drawInfoHud(drawContext);
        renderArmorHud(drawContext);
    }

    private static void drawInfoHud(DrawContext drawContext){
        int yOffset = 10;

        displayElements.get(0).setActive(readModule(coordinateDisplay));
        displayElements.get(1).setActive(readModule(biomeDisplay));
        displayElements.get(2).setActive(readModule(fpsDisplay));
        displayElements.get(3).setActive(readModule(pitchYaw));
        displayElements.get(4).setActive(readModule(dayCount));
        displayElements.get(5).setActive(readModule(realLiveTime));

        for (DisplayElements element : displayElements) {
            if (element.isActive()) {
                drawContext.drawText(mc.textRenderer, element.getText(), 10, yOffset, readColor(ColorStates.hudColor).getRGB(), false);
                yOffset += 10;
            }
        }
    }

    private static void renderArmorHud(DrawContext drawContext){
        if (mc.player != null && readModule(armorHud)){

            ItemStack helmet = mc.player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplate = mc.player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggings = mc.player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack boots = mc.player.getEquippedStack(EquipmentSlot.FEET);

            int screenHeight = drawContext.getScaledWindowHeight();

            int x = 20;
            int y = screenHeight -40;

            renderArmorPiece(drawContext, helmet, x, y);
            renderArmorPiece(drawContext, chestplate, (x + 21), y);
            renderArmorPiece(drawContext, leggings, (x + 42), y);
            renderArmorPiece(drawContext, boots, (x + 63), y);
        }
    }
}