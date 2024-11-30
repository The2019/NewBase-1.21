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


public class HudScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;
    private static final int x = 20;
    private static int y = 30;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final int buttonWidth = 200;
    private static final int buttonHeight = 20;
    private static final int spacing = 30;

    public HudScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.hudscreen.name"));
        this.parent = parent;
        this.settings = settings;
    }

    @Override
    protected void init() {

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.hudscreen.back"), button -> {mc.setScreen(new ConfigScreen(mc.currentScreen, mc.options));}).dimensions(17, 20, 100,20).build());

        y = 30;

        addContent("togglecoordinatesdisplay", coordinateDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addContent("togglebiomedisplay", biomeDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addContent("togglefpsdisplay", fpsDisplay, x, y += spacing, buttonWidth, buttonHeight);
        addContent("toggelpitchyaw", pitchYaw, x, y += spacing, buttonWidth, buttonHeight);
        addContent("toggeldaycount", dayCount, x, y += spacing, buttonWidth, buttonHeight);

    }

    private void addContent(String key, String module, int x, int y, int buttonWidth, int buttonHeight) {
        this.addDrawable(new TextWidget(x, y, 500, 20, Text.translatable("newbase.hudscreen." + key), mc.textRenderer).alignLeft());
        this.addDrawableChild(new ButtonWidget.Builder(toggleModule(module), button -> {
            saveModuleState(module, !readModule(module));
            mc.setScreen(new HudScreen(mc.currentScreen, mc.options));
        }).tooltip(Tooltip.of(Text.translatable("newbase.hudscreen.tooltip")))
                .dimensions(this.width - 220, y, buttonWidth, buttonHeight).build());
    }

    private static Text toggleModule(String module){
        if(readModule(module)){
            return Text.translatable("newbase.hudscreen.enabled");
        }else {
            return Text.translatable("newbase.hudscreen.disabled");
        }
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
