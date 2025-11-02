package net.the2019.newbase.features.hud;

import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RealTImeDisplay {

    public static Text getRealWorldTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);

        return Text.literal("Real Time: " + formattedTime);
    }
}
