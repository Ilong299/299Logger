package dev.ilong299.logger;

import dev.ilong299.logger.commands.SearchCommand;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class ConsoleRconCommandHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void listenRCON(ServerCommandEvent event) {
        if(!SearchCommand.s1) {
            LogUtil.record("CONSOLE", "COMMAND", event.getCommand());
            return;
        }else {
            SearchCommand.r(event.getCommand());
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void listenCon(RemoteServerCommandEvent event) {
        LogUtil.record("RCON", "COMMAND", event.getCommand());
    }
}
