package net.The2019.NewBase;

import net.The2019.NewBase.config.ModuleConfig;
import net.The2019.NewBase.features.generic.Placer;
import net.The2019.NewBase.features.generic.TridentHelper;
import net.The2019.NewBase.features.hud.ArmorHud;
import net.The2019.NewBase.features.render.BeeHiveHelper;
import net.The2019.NewBase.features.render.NoFog;
import net.The2019.NewBase.features.render.ToggleCamera;
import net.The2019.NewBase.features.waypoints.Waypoints;
import net.The2019.NewBase.render.HudRender;
import net.The2019.NewBase.utils.InitKeyBindings;
import net.The2019.NewBase.utils.PermissionLevel;
import net.fabricmc.api.ClientModInitializer;

public class NewBaseClient implements ClientModInitializer {

	public static final String MOD_ID = "newbase";

	@Override
	public void onInitializeClient() {
		//Utils
		PermissionLevel.initAllowedPlayers();
		ModuleConfig.init();
		InitKeyBindings.initKeys();

		//Hud
		HudRender.registerHudRendering();
		ArmorHud.renderArmorInHud();
		ToggleCamera.changeCameraPosition();

		//Render
		BeeHiveHelper.register();
		NoFog.noFog();

		//generic
		Placer.place();
		//TridentHelper.tridentHelper();

		//waypoints
		//Waypoints.init();
		//Waypoints.renderWaypoints();

	}
}
