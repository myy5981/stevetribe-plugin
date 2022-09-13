package org.scarike.minecraft.reactor.post;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.MinecraftMessage;

import java.nio.charset.StandardCharsets;

import static org.scarike.minecraft.util.Format.formatMC;

/**
 * 该类负责构造请求数据
 */
@Getter
@Setter
@Accessors(chain = true)
public class Target {

    private static final Gson om=new Gson();
    private MessageType type;
    private String id;
    private String format;

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class RequestBody{
        private String message_type;
        private String message;
        private String user_id;
        private String group_id;
    }

    public byte[] request(MinecraftMessage message) {
        RequestBody body=new RequestBody()
                .setMessage_type(type.raw)
                .setMessage(formatMC(format,message));
        if(type==MessageType.PRIVATE){
            body.setUser_id(id);
        }else if(type==MessageType.GROUP){
            body.setGroup_id(id);
        }
        return om.toJson(body).getBytes(StandardCharsets.UTF_8);
    }

    public enum MessageType {
        PRIVATE("private"), GROUP("group");
        private final String raw;

        MessageType(String raw) {
            this.raw = raw;
        }
    }
}