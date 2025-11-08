package net.the2019.newbase;

import net.fabricmc.api.ClientModInitializer;
import net.the2019.newbase.config.ColorConfig;
import net.the2019.newbase.config.IntegerConfig;
import net.the2019.newbase.config.ModuleConfig;
import net.the2019.newbase.features.generic.TridentHelper;
import net.the2019.newbase.features.hud.ArmorHud;
import net.the2019.newbase.features.render.BeeHiveHelper;
import net.the2019.newbase.features.render.MapRenderer;
import net.the2019.newbase.render.HudRender;
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
		HudRender.run();
		ArmorHud.renderArmorHud();

		//Render
		BeeHiveHelper.highlightBeeHives();

		//generic
		TridentHelper.tridentHelper();

        MapRenderer.run();


	}
}
