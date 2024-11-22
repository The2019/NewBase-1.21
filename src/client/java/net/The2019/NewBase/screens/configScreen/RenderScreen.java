package net.The2019.NewBase.screens.configScreen;

import net.The2019.NewBase.screens.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleConfig.saveModuleState;
import static net.The2019.NewBase.config.ModuleStates.*;

public class RenderScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;
    private static int x = 20;
    private static int y = 50;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public RenderScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.hudscreen.name"));
        this.parent = parent;
        this.settings = settings;
    }

    @Override
    protected void init() {

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.renderscreen.back"), button -> {mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));}).dimensions(17, 20, 100,20).build());

        //Beehive Helper
        this.addDrawable(new TextWidget(x, y, 500, 20, Text.translatable("newbase.renderscreen.beehiverender"), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(beehiveRender), button -> {
            saveModuleState(beehiveRender, !readModule(beehiveRender));
            mc.setScreen(new RenderScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip"))).dimensions(this.width - 220, y, 200, 20).build());

        //Coordinates
        this.addDrawable(new TextWidget(x, y+30, 500, 20, Text.translatable("newbase.renderscreen.fullbrightrender"), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(fullBrightRender), button -> {
            saveModuleState(fullBrightRender, !readModule(fullBrightRender));
            mc.setScreen(new RenderScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip"))).dimensions(this.width - 220, y+30, 200, 20).build());

        //NoFog
        this.addDrawable(new TextWidget(x, y+60, 500, 20, Text.translatable("newbase.renderscreen.nofog"), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(noFog), button -> {
            saveModuleState(noFog, !readModule(noFog));
            mc.setScreen(new RenderScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip"))).dimensions(this.width - 220, y+60, 200, 20).build());

        //ArmorHud
        this.addDrawable(new TextWidget(x, y+90, 500, 20, Text.translatable("newbase.renderscreen.armorHud"), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(armorHud), button -> {
            saveModuleState(armorHud, !readModule(armorHud));
            mc.setScreen(new RenderScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip"))).dimensions(this.width - 220, y+90, 200, 20).build());
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
