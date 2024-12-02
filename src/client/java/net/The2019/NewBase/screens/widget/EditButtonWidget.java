package net.The2019.NewBase.screens.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.function.Consumer;

import static net.The2019.NewBase.NewBaseClient.MOD_ID;

public class EditButtonWidget extends ClickableWidget {

    private final Consumer<EditButtonWidget> onClickCallback;
    private static final Identifier editButton = Identifier.of(MOD_ID, "textures/gui/edit_button.png");

    public EditButtonWidget(int x, int y, int width, int height, Text message, Consumer<EditButtonWidget> onClickCallback) {
        super(x, y, width, height, message);
        this.onClickCallback = onClickCallback;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered()) {
            RenderSystem.setShaderTexture(0 ,editButton);
            context.drawTexture(editButton, this.getX(), this.getY(), 0 , 0, this.width, this.height, 20, 20);
            context.drawBorder(this.getX(), this.getY(), this.width, this.height, Color.WHITE.getRGB());
        } else {
            RenderSystem.setShaderTexture(0 ,editButton);
            context.drawTexture(editButton, this.getX(), this.getY(), 0 , 0, this.width, this.height, 20, 20);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (onClickCallback != null) {
            onClickCallback.accept(this);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
