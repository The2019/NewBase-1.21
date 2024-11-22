package net.The2019.NewBase.utils;

import net.minecraft.text.Text;

import java.util.function.Supplier;

public class DisplayElements {
    private final String name;
    private final int color;
    private final Supplier<Text> textSupplier;
    private boolean active;

    public DisplayElements(String name, int color, Supplier<Text> textSupplier) {
        this.name = name;
        this.color = color;
        this.textSupplier = textSupplier;
        this.active = true; // Elements are active by default
    }

    public Text getText() {
        return textSupplier.get();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}

