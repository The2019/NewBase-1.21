package net.The2019.NewBase.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    public void getPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        String username = MinecraftClient.getInstance().getSession().getUsername();

        if (entry.getProfile().name().equals(username)) {
            if (username.equals("The2019")){
                cir.setReturnValue(Text.literal("§c" + username + " §f[§a⌬§f]"));
            }else {
                cir.setReturnValue(Text.literal(username + " §f[§a⌬§f]"));
            }
            if(username.equals("TheChrisgamer18")){
                cir.setReturnValue(Text.literal("Noggi" + " §f[§a⌬§f]"));
            }
        }
    }
}
