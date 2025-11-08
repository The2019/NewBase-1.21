package net.the2019.newbase.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ChatCoordinatesScreen extends Screen {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public ChatCoordinatesScreen(Screen parent, GameOptions gameOptions) {
        super(Text.translatable("newbase.chatcoordinatesscreen.name"));
    }

    @Override
    protected void init() {
        ButtonWidget yesButton = new ButtonWidget.Builder(Text.translatable("newbase.chatcoordinatesscreen.yes"), button -> {
            if (mc.player != null) {
                BlockPos playerPos = mc.player.getBlockPos();
                mc.player.networkHandler.sendChatMessage(String.format("X: %s Y: %s Z: %s", playerPos.getX(), playerPos.getY(), playerPos.getZ()));
                mc.setScreen(null);
            }
        }).dimensions(this.width / 2 - 90, this.height / 2, 80, 20).build();

        ButtonWidget noButton = new ButtonWidget.Builder(Text.translatable("newbase.chatcoordinatesscreen.no"), button -> {
            mc.setScreen(null);
        }).dimensions(this.width / 2, this.height / 2, 80, 20).build();

        this.addDrawable(new TextWidget(this.width / 2 - 250, this.height / 2 - 30, 500, 20, Text.translatable("newbase.chatcoordinatesscreen.message"), textRenderer));
        this.addDrawableChild(yesButton);
        this.addDrawableChild(noButton);
    }
}

