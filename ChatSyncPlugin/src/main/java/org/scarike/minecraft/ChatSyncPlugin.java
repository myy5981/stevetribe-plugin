package org.scarike.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.scarike.minecraft.conf.Configuration;
import org.scarike.minecraft.reactor.get.GetReactor;
import org.scarike.minecraft.reactor.post.PostChatReactor;
import org.scarike.minecraft.exception.ChatSyncException;
import org.scarike.minecraft.listener.PlayerChatEventHandler;

import java.util.logging.Logger;

public final class ChatSyncPlugin extends JavaPlugin {

    private final Logger log=Logger.getLogger("minecraft");
    private Configuration configuration;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            this.saveDefaultConfig();
            Configuration conf = Configuration.readConf(this.getConfig());
            configuration=conf;
            // post相关
            PostChatReactor post = conf.getPost();
            getServer().getPluginManager().registerEvents(new PlayerChatEventHandler(this,post),this);
            // get相关
            GetReactor get=conf.getGet();
            get.starterServer();
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    String message;
                    while ((message=get.nextMessage()) != null) {
                        getServer().broadcastMessage(message);
                    }
                }
        },0L,10L);
        } catch (Exception exception) {
            log.warning("聊天同步插件启动失败！"+exception.getMessage());
            throw new ChatSyncException("聊天同步插件启动失败！",exception);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        configuration.getGet().stopServer();
    }
}
