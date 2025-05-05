package dev.ilong299.logger;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class L299User {

    private CommandSender parent;
    public boolean isPlayer = false, isConsole = false, isRemoteCon = false;

    public L299User(CommandSender parent) {
        this.parent = parent;
        this.isPlayer = parent instanceof Player;
        this.isConsole = parent instanceof ConsoleCommandSender;
        this.isRemoteCon = parent instanceof RemoteConsoleCommandSender;
    }

    private static FileConfiguration defaultConfig;

    public static void setDefaultConfig(FileConfiguration config) {
        defaultConfig = config;
    }

    public void sendMessage(String message) {
        parent.sendMessage(StringUtils.format(message));
    }

    public void sendConfigMessage(FileConfiguration config, String key) {
        sendMessage(config.getString(key, ""));
    }

    public void sendDefaultConfigMessage(String key) {
        sendConfigMessage(defaultConfig, key);
    }

}
