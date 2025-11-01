package net.The2019.NewBase.screens.editScreens;

import net.The2019.NewBase.screens.configScreen.RenderScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import static net.The2019.NewBase.config.IntegerConfig.readValue;
import static net.The2019.NewBase.config.IntegerConfig.saveValue;
import static net.The2019.NewBase.config.IntegerStates.normalFOV;
import static net.The2019.NewBase.config.IntegerStates.zoomFOV;

public class ZoomEditScreen extends Screen {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static int x = 20;
    private static int y = 30;
    private static final int spacing = 30;
    private TextFieldWidget zoomFovField;
    private TextFieldWidget normalFovField;

    public ZoomEditScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("newbase.zoomscreen.name"));
    }

    @Override
    protected void init() {
        y = 30;

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.renderscreen.back"), button -> mc.setScreen(new RenderScreen(mc.currentScreen, mc.options)))
                .dimensions(x, y, 100, 20).build());

        this.addDrawableChild(new TextWidget(x, y += spacing, 500, 20, Text.translatable("newbase.zoomscreen.zoomfov"), mc.textRenderer));

        zoomFovField = new TextFieldWidget(textRenderer, this.width-120, y, 100, 20, Text.translatable("newbase.zoomscreen.zoomfov"));
        zoomFovField.setText(String.valueOf(readValue(zoomFOV))); // Default value
        this.addDrawableChild(zoomFovField);

        this.addDrawableChild(new TextWidget(x, y += spacing , 500, 20, Text.translatable("newbase.zoomscreen.normalfov"), mc.textRenderer));

        normalFovField = new TextFieldWidget(textRenderer, this.width-120, y, 100, 20, Text.translatable("newbase.zoomscreen.normalfov"));
        normalFovField.setText(String.valueOf(readValue(normalFOV))); // Default value
        this.addDrawableChild(normalFovField);

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("newbase.zoomscreen.save"), button -> {
            saveValues();
            mc.setScreen(new ZoomEditScreen(mc.currentScreen, mc.options));
        })
                .dimensions(this.width-120, y += spacing + 10, 100, 20).build());
    }
    private void saveValues() {
        try {
            int zoomFov = Integer.parseInt(zoomFovField.getText());
            int normalFov = Integer.parseInt(normalFovField.getText());

            saveValue(zoomFOV, zoomFov);
            saveValue(normalFOV, normalFov);


        } catch (NumberFormatException ignored) {
        }
    }
    @Override
    public boolean keyPressed(KeyInput input) {
        if(input.key() == GLFW.GLFW_KEY_ENTER){
            saveValues();
            mc.setScreen(new ZoomEditScreen(mc.currentScreen, mc.options));
        }
        if(input.key() == GLFW.GLFW_KEY_ESCAPE){
            mc.setScreen(new RenderScreen(mc.currentScreen, mc.options));
        }
        return super.keyPressed(input);
    }
}
