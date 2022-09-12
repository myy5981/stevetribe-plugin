package org.scarike.minecraft.entity.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.MinecraftMessage;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

/**
 * 该类负责构造请求数据
 */
@Getter
@Setter
@Accessors(chain = true)
public class Target {
    private MessageType type;
    private String id;
    private String format;

    public String format(MinecraftMessage message, Matcher matcher) {
        String result=format;
        for (int i = 0; i < matcher.groupCount(); i++) {
            result=result.replaceAll("_"+i+"_",matcher.group(i));
        }
        return result
                .replaceAll("_player_", message.getPlayer().replaceAll("\"", "\\\\\""))
                .replaceAll("_world_", message.getWorld().replaceAll("\"", "\\\\\""))
                .replaceAll("_time_", message.getTime())
                .replaceAll("_message_", message.getMessage().replaceAll("\"", "\\\\\""));
    }

    public byte[] request(MinecraftMessage message, Matcher matcher) {
        StringBuilder sb = new StringBuilder(256)
                .append("{\"message_type\":\"").append(type.raw)
                .append("\",\"message\":\"").append(format(message, matcher));
        if (type == MessageType.PRIVATE) {
            sb.append("\",\"user_id\":\"").append(id).append("\"}");
        } else if (type == MessageType.GROUP) {
            sb.append("\",\"group_id\":\"").append(id).append("\"}");
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public enum MessageType {
        PRIVATE("private"), GROUP("group");
        private final String raw;

        MessageType(String raw) {
            this.raw = raw;
        }
    }
}