package net.the2019.newbase.screens.configScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.the2019.newbase.screens.ConfigScreen;

import static net.the2019.newbase.config.ModuleConfig.readModule;
import static net.the2019.newbase.config.ModuleConfig.saveModuleState;
import static net.the2019.newbase.config.ModuleStates.*;

import org.lwjgl.glfw.GLFW;

public class GenericScreen extends Screen {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int buttonWidth = 200;
    private static final int buttonHeight = 20;
    private static final int spacing = 30;

    public GenericScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.genericscreen.name"));
    }

    @Override
    protected void init() {

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.renderscreen.back"), button -> {mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));}).dimensions(17, 20, 100,20).build());

        int y = 30;
        int x = 20;

        addTextButton("tridenthelper", tridentHelper, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("deathcorrds", deathcoords, x, y += spacing, buttonWidth, buttonHeight);

    }

    private void addTextButton(String key, String module, int x, int y, int buttonWidth, int buttonHeight) {
        this.addDrawable(new TextWidget(x, y, 500, 20, Text.translatable("newbase.genericscreen." + key), mc.textRenderer));
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(module), button -> {
            saveModuleState(module, !readModule(module));
            mc.setScreen(new GenericScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip")))
        .dimensions(this.width - 240, y, buttonWidth, buttonHeight).build());
    }

    private static Text toggleModule(String module){
        if(readModule(module)){
            return Text.translatable("newbase.renderscreen.enabled");
        }else {
            return Text.translatable("newbase.renderscreen.disabled");
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
