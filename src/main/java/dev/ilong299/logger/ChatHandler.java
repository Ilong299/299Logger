package dev.ilong299.logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    @EventHandler
    public void handle(AsyncPlayerChatEvent event) {
        LogUtil.record(event.getPlayer().getName(), "ASYNC_CHAT", event.getMessage());
    }
}
