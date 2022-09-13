package org.scarike.minecraft.reactor.get;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Setter
@Accessors(chain = true)
public class GetReactor {
    private int port;
    private QQMessageConverter converter;

    private HttpServer server;
    private BlockingQueue<String> queue;

    public String nextMessage(){
        return queue.poll();
    }

    public void starterServer(){
        queue=new LinkedBlockingQueue<>();
        server=new HttpServer(port,queue, converter);
        server.start();
    }

    public void stopServer(){
        server.close();
    }
}
