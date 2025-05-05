package dev.ilong299.logger.commands;

import dev.ilong299.logger.L299User;
import dev.ilong299.logger.Plugin299Logger;

public class LoggerReload extends L299Command{

    public LoggerReload() {
        super("reloadlogger", "299logger.reload", "Reload 299Logger");
    }

    @Override
    public void execute(L299User user, String label, String[] args) {
        Plugin299Logger plugin = Plugin299Logger.getInstance();
        String name = plugin.getName();
        user.sendMessage("&aReloading &c" + name + " &aversion: &c" + plugin.getDescription().getVersion());
        Plugin299Logger.getInstance().reload();
        user.sendMessage("&aSuccessful Reloaded &c" + name);
    }
}
