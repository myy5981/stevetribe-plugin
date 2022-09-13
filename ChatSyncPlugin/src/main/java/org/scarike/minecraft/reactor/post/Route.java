package org.scarike.minecraft.reactor.post;

import com.squareup.okhttp.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.MinecraftMessage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * 该类负责路由转发
 */
@Getter
@Setter
@Accessors(chain = true)
public class Route{

    private static final Logger log=Logger.getLogger("minecraft");

    private String serverAddress;
    private Rule rule;
    private List<Target> targets;

    private static final OkHttpClient cli=new OkHttpClient();
    private static final MediaType JSON=MediaType.parse("application/json");
    private void httpRequest(byte[] body){
        Request request = new Request.Builder()
                .url("http://" + serverAddress + "/send_msg")
                .post(RequestBody.create(JSON, body))
                .build();
        cli.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                log.log(Level.SEVERE,"消息转发异常",e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                response.body().close();
            }
        });
    }

    public void route(MinecraftMessage message){
        if(rule.match(message)){
            for (Target target : targets) {
                httpRequest(target.request(message));
            }
        }
    }
}