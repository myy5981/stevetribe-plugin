package org.scarike.minecraft.entity.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.Message;

import java.nio.charset.StandardCharsets;

/**
 * 该类负责构造请求数据
 */
@Getter
@Setter
@Accessors(chain = true)
public class Target{
    private MessageType type;
    private String id;
    private String format;

    public String format(Message message){
        return format.replace("$player$",message.getPlayer().replaceAll("\"","\\\\\""))
            .replace("$world$",message.getWorld().replaceAll("\"","\\\\\""))
            .replace("$time$",message.getTime())
            .replace("$message$",message.getMessage().replaceAll("\"","\\\\\""));
    }

    public byte[] request(Message message){
        StringBuilder sb=new StringBuilder(256)
                .append("{\"message_type\":\"").append(type.raw)
                .append("\",\"message\":\"").append(format(message));
        if(type==MessageType.PRIVATE){
            sb.append("\",\"user_id\":\"").append(id).append("\"}");
        }else if(type==MessageType.GROUP){
            sb.append("\",\"group_id\":\"").append(id).append("\"}");
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public enum MessageType{
        PRIVATE("private"),GROUP("group");
        private final String raw;

        MessageType(String raw) {
            this.raw=raw;
        }
    }
}