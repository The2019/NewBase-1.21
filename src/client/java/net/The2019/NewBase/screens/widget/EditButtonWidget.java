package net.the2019.newbase.screens.widget;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.the2019.newbase.NewBaseClient.MOD_ID;

import java.awt.*;
import java.util.function.Consumer;

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
            context.drawTexture(RenderPipelines.GUI_TEXTURED, editButton, this.getX(), this.getY(), 0 , 0, this.width, this.height, 20, 20 );
            context.drawStrokedRectangle(this.getX(), this.getY(), this.width, this.height, Color.WHITE.getRGB());
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, editButton, this.getX(), this.getY(), 0 , 0, this.width, this.height, 20, 20 );
        }
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if (onClickCallback != null) {
            onClickCallback.accept(this);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
