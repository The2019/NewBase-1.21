package net.the2019.newbase.features.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class DayCount {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static Text getDays(){
        return Text.literal("Days: " + getDayCount());
    }
    private static int getDayCount(){
        if(mc.player != null && mc.world != null){
            long time = mc.world.getTimeOfDay();
            return (int) (time /24000L);
        }else{
            MinecraftServer server = mc.getServer();
            if(server != null && (server.getOverworld() != null)){
                long time = server.getOverworld().getTimeOfDay();
                return (int) (time/24000L);
            }
        }
        return 0;
    }
}
