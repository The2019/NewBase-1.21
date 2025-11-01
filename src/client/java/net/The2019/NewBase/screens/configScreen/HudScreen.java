package net.The2019.NewBase.screens.configScreen;

import net.The2019.NewBase.screens.ConfigScreen;
import net.The2019.NewBase.screens.widget.ColorSelectWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static net.The2019.NewBase.config.ColorConfig.saveColor;
import static net.The2019.NewBase.config.ColorStates.hudColor;
import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleConfig.saveModuleState;
import static net.The2019.NewBase.config.ModuleStates.*;


public class HudScreen extends Screen {
    private static final int x = 20;
    private static final int yWidget = 50;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int buttonWidth = 200;
    private static final int buttonHeight = 20;
    private static final int spacing = 30;

    public HudScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.hudscreen.name"));
    }

    @Override
    protected void init() {

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.hudscreen.back"), button -> {mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));}).dimensions(17, 20, 100,20).build());

        int y = 60;
        int xWidget = this.width - 40;

        this.addDrawable(new TextWidget(x, yWidget, 500, 20, Text.translatable("newbase.hudscreen.changecolor"), mc.textRenderer));

        this.addDrawableChild(new ColorSelectWidget(xWidget, yWidget, 20, 20, Text.literal(""), Color.GREEN, colorSelectWidget -> {
            saveColor(hudColor, Color.GREEN);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.BLACK, colorSelectWidget -> {
            saveColor(hudColor, Color.BLACK);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.WHITE, colorSelectWidget -> {
            saveColor(hudColor, Color.WHITE);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.RED, colorSelectWidget -> {
            saveColor(hudColor, Color.RED);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.ORANGE, colorSelectWidget -> {
            saveColor(hudColor, Color.ORANGE);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.BLUE, colorSelectWidget -> {
            saveColor(hudColor, Color.BLUE);
        }));
        this.addDrawableChild(new ColorSelectWidget(xWidget -= spacing , yWidget, 20, 20, Text.literal(""), Color.MAGENTA, colorSelectWidget -> {
            saveColor(hudColor, Color.MAGENTA);
        }));

        addTextButton("togglecoordinatesdisplay", coordinateDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("togglebiomedisplay", biomeDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("togglefpsdisplay", fpsDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("toggelpitchyaw", pitchYaw, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("toggeldaycount", dayCount, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("toggelreallivetime", realLiveTime, x, y += spacing, buttonWidth, buttonHeight);


    }

    private void addTextButton(String key, String module, int x, int y, int buttonWidth, int buttonHeight) {
        this.addDrawable(new TextWidget(x, y, 500, 20, Text.translatable("newbase.hudscreen." + key), mc.textRenderer));
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(module), button -> {
            saveModuleState(module, !readModule(module));
            mc.setScreen(new HudScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip")))
        .dimensions(this.width - 240, y, buttonWidth, buttonHeight).build());
    }

    private static Text toggleModule(String module){
        if(readModule(module)){
            return Text.translatable("newbase.hudscreen.enabled");
        }else {
            return Text.translatable("newbase.hudscreen.disabled");
        }
    }
    @Override
    public boolean keyPressed(KeyInput input) {
        if(input.key() == GLFW.GLFW_KEY_ESCAPE){
            mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));
        }
        return false;
    }
}
