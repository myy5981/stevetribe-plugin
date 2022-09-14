package org.scarike.minecraft.reactor.get;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import org.scarike.minecraft.entity.QQMessage;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {

    private static final Logger log = Logger.getLogger("minecraft");

    private final int port;
    private final BlockingQueue<String> queue;
    private final QQMessageConverter converter;
    private final Gson om = new Gson();
    private Channel channel;

    public HttpServer(int port, BlockingQueue<String> queue, QQMessageConverter converter) {
        this.port = port;
        this.queue = queue;
        this.converter = converter;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        try {
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            log.log(Level.SEVERE,"unexpected error!",cause);
                            ctx.close();
                        }

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(1024))
                                    .addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
                                            // debug_request(req);
                                            try {
                                                QQMessage msg = om.fromJson(req.content().toString(StandardCharsets.UTF_8), QQMessage.class);
                                                String message;
                                                if ((message = converter.convert(msg)) != null) {
                                                    queue.add(message);
                                                }
                                            } catch (Exception e) {
                                                log.log(Level.WARNING,"failure when parse message event",e);
                                            } finally {
                                                //响应无内容
                                                FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
                                                ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
                                                //短连接
                                                ctx.close();
                                            }
                                        }
                                    });
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("server started successfully on port " + port);
            channel = future.channel();
            channel.closeFuture().addListener(future1 -> {
                group.shutdownGracefully();
                log.info("netty http server stop");
            });
        } catch (Exception ex) {
            log.log(Level.SEVERE, "unexpected error!", ex);
        }
    }

    public void close() {
        channel.close();
    }

    private static void debug_request(FullHttpRequest req) {
        System.out.println("-----------------------------------");
        System.out.println(req.method().name() + " " + req.uri() + " " + req.protocolVersion().toString());
        Iterator<Map.Entry<String, String>> it = req.headers().iteratorAsString();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            System.out.println(next.getKey() + ":" + next.getValue());
        }
        System.out.println();
        System.out.println(req.content().toString(StandardCharsets.UTF_8));

    }
}
