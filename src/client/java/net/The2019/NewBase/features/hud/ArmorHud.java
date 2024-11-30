package net.The2019.NewBase.features.hud;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.armorHud;
import static net.The2019.NewBase.render.HudRender.renderArmorPiece;


public class ArmorHud {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static ItemStack helmet = ItemStack.EMPTY;
    private static ItemStack chestpalte = ItemStack.EMPTY;
    private static ItemStack leggings = ItemStack.EMPTY;
    private static ItemStack boots = ItemStack.EMPTY;
    private static int screenhight = 0;
    private static final float scale = 1.5f;


    public static void renderArmorHud(){

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (mc.player != null && readModule(armorHud)){

                helmet = mc.player.getEquippedStack(EquipmentSlot.HEAD);
                chestpalte = mc.player.getEquippedStack(EquipmentSlot.CHEST);
                leggings = mc.player.getEquippedStack(EquipmentSlot.LEGS);
                boots = mc.player.getEquippedStack(EquipmentSlot.FEET);

                screenhight = drawContext.getScaledWindowHeight();

                int x = 20;
                int y = screenhight-40;

                renderArmorPiece(drawContext, helmet, x, y, scale);
                renderArmorPiece(drawContext, chestpalte, (int) (x + 21*scale), y, scale);
                renderArmorPiece(drawContext, leggings, (int) (x + 42*scale), y, scale);
                renderArmorPiece(drawContext, boots, (int) (x + 63*scale), y, scale);
            }
        });
    }
}
