package net.The2019.NewBase.screens.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.function.Consumer;

public class ColorSelectWidget extends ClickableWidget {

    private final Color color;
    private final Consumer<ColorSelectWidget> onClickCallback;

    public ColorSelectWidget(int x, int y, int width, int height, Text message, Color color, Consumer<ColorSelectWidget> onClickCallback) {
        super(x, y, width, height, message);
        this.color = color;
        this.onClickCallback = onClickCallback;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered()) {
            context.drawBorder(this.getX(), this.getY(), this.width, this.height, Color.WHITE.getRGB());
            context.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1, color.getRGB());
        } else {
            context.drawBorder(this.getX(), this.getY(), this.width, this.height, Color.BLACK.getRGB());
            context.fill(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1, color.getRGB());
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