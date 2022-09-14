package org.scarike.minecraft.util;

import org.scarike.minecraft.entity.MinecraftMessage;

import java.util.regex.Pattern;

public class Format {

    public static final Pattern replace=Pattern.compile("\"");
    public static final String _PLAYER_="_player_";
    public static final String _WORLD_="_world_";
    public static final String _MESSAGE_="_message_";
    public static final String _TIME_="_time_";

    public static final String SPECIAL_CHAR_1="\"";
    public static final String SPECIAL_CHAR_2="&amp;";
    public static final String SPECIAL_CHAR_3="&#91;";
    public static final String SPECIAL_CHAR_4="&#93;";
    public static final String SPECIAL_CHAR_5="&#44;";

    public static String formatMC(String format, MinecraftMessage message) {
        return format.replace("\"","\\\"")
                .replace(_PLAYER_, message.getPlayer())
                .replace(_MESSAGE_, message.getMessage())
                .replace(_TIME_, message.getTime())
                .replace(_WORLD_, message.getWorld());
    }

    public static String formatQQ(String format,String player,String message){
        return format.replace(_PLAYER_,player).replace(_MESSAGE_,message)
                .replace(SPECIAL_CHAR_2,"&")
                .replace(SPECIAL_CHAR_3,"[")
                .replace(SPECIAL_CHAR_4,"]")
                .replace(SPECIAL_CHAR_5,",");

    }


}
