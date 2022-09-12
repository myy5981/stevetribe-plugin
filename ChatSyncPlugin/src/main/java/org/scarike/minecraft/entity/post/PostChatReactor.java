package org.scarike.minecraft.entity.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.MinecraftMessage;

import java.util.List;

/**
 * 该类负责分发消息
 */
@Getter
@Setter
@Accessors(chain = true)
public class PostChatReactor {
    private String format;
    private List<Route> routes;

    public void forward(MinecraftMessage message){
        routes.forEach(route -> route.route(message));
    }
}
