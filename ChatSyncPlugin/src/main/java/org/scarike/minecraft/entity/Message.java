package org.scarike.minecraft.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class Message {
    private String player;
    private String message;
    private String world;
    private String time;
}
