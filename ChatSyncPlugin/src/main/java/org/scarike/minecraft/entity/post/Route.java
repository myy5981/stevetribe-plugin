package org.scarike.minecraft.entity.post;

import com.squareup.okhttp.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.Message;

import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * 该类负责路由转发
 */
@Getter
@Setter
@Accessors(chain = true)
public class Route{
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

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    public void route(Message message){
        if(rule.match(message.getPlayer(), message.getMessage())){
            for (Target target : targets) {
                httpRequest(target.request(message));
            }
        }
    }
}