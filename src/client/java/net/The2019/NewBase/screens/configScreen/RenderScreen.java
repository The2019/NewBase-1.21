package net.The2019.NewBase.screens.configScreen;

import net.The2019.NewBase.screens.ConfigScreen;
import net.The2019.NewBase.screens.widget.ColorSelectWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

import java.awt.*;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleConfig.saveModuleState;
import static net.The2019.NewBase.config.ModuleStates.*;

public class RenderScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;
    private static int x = 20;
    private static int y = 30;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int buttonWidth = 200;
    private static final int buttonHeight = 20;
    private static final int spacing = 30;

    public RenderScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.hudscreen.name"));
        this.parent = parent;
        this.settings = settings;
    }

    @Override
    protected void init() {

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.renderscreen.back"), button -> {mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));}).dimensions(17, 20, 100,20).build());

        y = 30;

        addTextButton("beehiverender", beehiveRender, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("fullbrightrender", fullBrightRender, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("nofog", noFog, x, y += spacing, buttonWidth, buttonHeight);
        addTextButton("armorHud", armorHud, x, y += spacing, buttonWidth, buttonHeight);

    }

    private void addTextButton(String key, String module, int x, int y, int buttonWidth, int buttonHeight) {
        this.addDrawable(new TextWidget(x, y, 500, 20, Text.translatable("newbase.renderscreen." + key), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(module), button -> {
            saveModuleState(module, !readModule(module));
            mc.setScreen(new HudScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip")))
        .dimensions(this.width - 220, y, buttonWidth, buttonHeight).build());
    }


    private static Text toggleModule(String module){
        if(readModule(module)){
            return Text.translatable("newbase.renderscreen.enabled");
        }else {
            return Text.translatable("newbase.renderscreen.disabled");
        }
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
