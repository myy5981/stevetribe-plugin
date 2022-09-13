package org.scarike.minecraft.reactor.post;

import org.scarike.minecraft.entity.MinecraftMessage;

public interface Rule {
    boolean match(MinecraftMessage message);
}
