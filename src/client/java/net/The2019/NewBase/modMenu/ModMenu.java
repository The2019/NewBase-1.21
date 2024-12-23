package net.The2019.NewBase.modMenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.The2019.NewBase.screens.ConfigScreen;
import net.minecraft.client.MinecraftClient;

public class ModMenu implements ModMenuApi {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return (currentScreen) -> {
            return new ConfigScreen(mc.currentScreen, mc.options);
        };
    }
}
