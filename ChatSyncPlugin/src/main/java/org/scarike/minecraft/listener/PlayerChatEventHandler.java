package org.scarike.minecraft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.scarike.minecraft.ChatSyncPlugin;
import org.scarike.minecraft.entity.Message;
import org.scarike.minecraft.entity.post.PostChatReactor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerChatEventHandler implements Listener {

    private final ChatSyncPlugin plugin;
    private final PostChatReactor post;
    private final SimpleDateFormat fmt=new SimpleDateFormat("HH:mm:ss");

    public PlayerChatEventHandler(ChatSyncPlugin plugin, PostChatReactor post) {
        this.plugin = plugin;
        this.post = post;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        post.forward(
                new Message()
                .setPlayer(event.getPlayer().getName())
                .setWorld(event.getPlayer().getWorld().getName())
                .setMessage(event.getMessage())
                .setTime(fmt.format(new Date()))
        );
    }
}
