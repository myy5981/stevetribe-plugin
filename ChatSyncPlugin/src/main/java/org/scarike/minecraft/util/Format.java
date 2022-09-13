package org.scarike.minecraft.util;

import org.scarike.minecraft.entity.MinecraftMessage;

import java.util.regex.Pattern;

public class Format {

    public static final Pattern replace=Pattern.compile("\"");
    public static final Pattern _PLAYER_=Pattern.compile("_player_");
    public static final Pattern _WORLD_=Pattern.compile("_world_");
    public static final Pattern _MESSAGE_=Pattern.compile("_message_");
    public static final Pattern _TIME_=Pattern.compile("_time_");

    public static String formatMC(String format, MinecraftMessage message) {
        format=_PLAYER_.matcher(format).replaceAll(message.getPlayer());
        format=_MESSAGE_.matcher(format).replaceAll(message.getMessage());
        format=_TIME_.matcher(format).replaceAll(message.getTime());
        format=_WORLD_.matcher(format).replaceAll(message.getWorld());
        return format;
    }

    public static String formatQQ(String format,String player,String message){
        format=_PLAYER_.matcher(format).replaceAll(player);
        format=_MESSAGE_.matcher(format).replaceAll(message);
        return format;
    }


}
