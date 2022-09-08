package org.scarike.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.scarike.minecraft.entity.Configuration;
import org.scarike.minecraft.entity.post.PostChatReactor;
import org.scarike.minecraft.exception.ChatSyncException;
import org.scarike.minecraft.listener.PlayerChatEventHandler;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class ChatSyncPlugin extends JavaPlugin {

    private final Logger log=Logger.getLogger("minecraft");

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            this.saveDefaultConfig();
            Configuration conf = Configuration.readConf(this.getConfig());
            PostChatReactor post = conf.getPost();
            getServer().getPluginManager().registerEvents(new PlayerChatEventHandler(this,post),this);
        } catch (Exception exception) {
            log.warning("聊天同步插件启动失败！"+exception.getMessage());
            throw new ChatSyncException("聊天同步插件启动失败！",exception);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
