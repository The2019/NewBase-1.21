package net.The2019.NewBase.features.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import static net.The2019.NewBase.config.ModuleConfig.readModule;
import static net.The2019.NewBase.config.ModuleStates.noFog;

public class NoFog {
    public static void noFog(){
        if(readModule(noFog)){
            WorldRenderEvents.START.register(context -> {
                RenderSystem.setShaderFogStart(Float.MAX_VALUE);
                RenderSystem.setShaderFogEnd(Float.MAX_VALUE);
            });
        }else {
            WorldRenderEvents.START.register(context -> {
                RenderSystem.setShaderFogStart(0.0f);
                RenderSystem.setShaderFogEnd(1.0f);
            });
        }
    }
}
