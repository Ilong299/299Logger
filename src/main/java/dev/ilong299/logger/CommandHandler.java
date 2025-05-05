package dev.ilong299.logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandHandler implements Listener {

    @EventHandler
    public void handle(PlayerCommandPreprocessEvent event) {
        LogUtil.record(event.getPlayer().getName(), "COMMAND_PREPROCESS", event.getMessage());
    }
}
