package dev.ilong299.logger;

import dev.ilong299.logger.commands.LoggerReload;
import dev.ilong299.logger.commands.LoggerSave;
import dev.ilong299.logger.commands.SearchCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Plugin299Logger extends JavaPlugin {

    private static List<String> commands;

    public static final String ID = "299logger";

    // https://github.com/Ilong299/299ChatManager/blob/main/src/main/java/dev/ilong/cm/Plugin299CM.java#L50
    public static void register299Command(Command command) {
        commands.add(command.getName());
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            commandMap.register(ID, command);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    // https://github.com/Ilong299/299ChatManager/blob/main/src/main/java/dev/ilong/cm/Plugin299CM.java#L63
    public static void unregister299Command(Command command) {
        if(command == null || command.getName() == null) return;
        commands.remove(command.getName());

        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> knownCommands = (Map<String, org.bukkit.command.Command>) knownCommandsField.get(commandMap);

            knownCommands.keySet().removeIf(key -> key.equalsIgnoreCase(command.getName()) || key.startsWith(ID + ":"));

        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }


    private static void clearCommands() {
        commands = new ArrayList<>();
    }

    private static Plugin299Logger instance;

    private static LogManager actLogMgr;

    public static Plugin299Logger getInstance() {
        return instance;
    }

    public static LogManager getActualLogManager() {
        return actLogMgr;
    }

    @Override
    public void onLoad() {
        instance = this;
        actLogMgr = new LogManager(this.getName(),
                this.getConfig().getBoolean("listen.console", false), this
                .getConfig().getBoolean("listen.rcon", false));
        saveDefaultConfig();
        L299User.setDefaultConfig(this.getConfig());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        commands = new ArrayList<>();

        LogUtil.initLogUtil(actLogMgr, this.getConfig().getString("logFormat", ""));
        LoggerReload reloadcmd = new LoggerReload();
        LoggerSave savecmd = new LoggerSave();
        SearchCommand search = new SearchCommand();
        register299Command(reloadcmd);
        register299Command(savecmd);
        register299Command(search);
        actLogMgr.startRecording();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        PlayerCommandSendEvent.getHandlerList().unregister(this);

        actLogMgr.stopRecording();

        for(String command: commands) {
            unregister299Command(Bukkit.getPluginCommand(command));
        }
        instance = null;
        clearCommands();

    }

    public void reload() {
        this.onDisable();
        this.onLoad();
        this.onEnable();
    }
}
