package net.the2019.newbase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class BiomeDisplay {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Text getBiomeText() {
        if (mc.player != null && mc.world != null) {
            return Text.literal("Biome: ").append(Text.translatable(mc.world.getBiome(mc.player.getBlockPos()).getKey().get().getValue().toTranslationKey()));
        }
        return null;
    }
}
