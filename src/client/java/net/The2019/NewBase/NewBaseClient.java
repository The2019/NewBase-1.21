package net.The2019.NewBase;

import net.The2019.NewBase.config.ColorConfig;
import net.The2019.NewBase.config.IntegerConfig;
import net.The2019.NewBase.config.ModuleConfig;
import net.The2019.NewBase.features.generic.TridentHelper;
import net.The2019.NewBase.features.hud.ArmorHud;
//import net.The2019.NewBase.features.render.BeeHiveHelper;
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
		ColorConfig.init();
		IntegerConfig.init();
		InitKeyBindings.initKeyBinds();


		//Hud
		HudRender.registerHudRendering();
		ArmorHud.renderArmorHud();

		//Render
		//BeeHiveHelper.highlightBeeHives();

		//generic
		TridentHelper.tridentHelper();




	}
}
