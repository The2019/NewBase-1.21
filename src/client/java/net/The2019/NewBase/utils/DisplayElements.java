package net.the2019.newbase.utils;

import net.minecraft.text.Text;

import java.util.function.Supplier;

public class DisplayElements {
    private final String name;
    private final Supplier<Text> textSupplier;
    private boolean active;

    public DisplayElements(String name, Supplier<Text> textSupplier) {
        this.name = name;
        this.textSupplier = textSupplier;
        this.active = true;
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
}

