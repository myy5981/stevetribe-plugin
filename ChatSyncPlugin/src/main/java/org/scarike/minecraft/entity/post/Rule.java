package org.scarike.minecraft.entity.post;

import org.scarike.minecraft.entity.MinecraftMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Rule {
    Matcher DEFAULT_MATCHER_ALL =Pattern.compile("d").matcher("d");

    Matcher match(MinecraftMessage message);
}
