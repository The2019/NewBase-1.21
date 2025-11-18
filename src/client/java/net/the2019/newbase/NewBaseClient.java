package net.the2019.newbase;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import net.the2019.newbase.config.ColorConfig;
import net.the2019.newbase.config.IntegerConfig;
import net.the2019.newbase.config.ModuleConfig;
import net.the2019.newbase.features.generic.TridentHelper;
import net.the2019.newbase.features.render.BeeHiveHelper;
import net.the2019.newbase.features.world.GenWorld;
import net.the2019.newbase.features.world.WorldDiffSystem;
import net.the2019.newbase.render.HudRender;
import net.the2019.newbase.utils.CommandRegister;
import net.the2019.newbase.utils.InitKeyBindings;
import net.the2019.newbase.utils.PermissionLevel;

public class NewBaseClient implements ClientModInitializer {

	public static final String MOD_ID = "newbase";

	@Override
	public void onInitializeClient() {
		//Utils
		PermissionLevel.initAllowedPlayers();
		ModuleConfig.init();
		ColorConfig.init();
		IntegerConfig.init();
		InitKeyBindings.initKeyBinds();

		//Hud
        HudElementRegistry.attachElementBefore(VanillaHudElements.MISC_OVERLAYS, Identifier.of(MOD_ID, "hudrender"), HudRender::render);
       // HudElementRegistry.attachElementBefore(VanillaHudElements.MISC_OVERLAYS, Identifier.of(MOD_ID, "maprender"), MapRenderer::onRenderHud);
		HudRender.initRender();

		//Render
		BeeHiveHelper.highlightBeeHives();

		//generic
		TridentHelper.tridentHelper();
        ClientCommandRegistrationCallback.EVENT.register(CommandRegister::registerCommands);
        registerWorldEvents();

        //World
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            WorldDiffSystem.tick();
        });
	}

    private void registerWorldEvents() {
        // Called when client starts
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            System.out.println("Client started");
        });

        // Called when client is stopping (before shutdown)
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            System.out.println("Client stopping - cleaning up resources");
            GenWorld.cleanup();
        });
    }
}

