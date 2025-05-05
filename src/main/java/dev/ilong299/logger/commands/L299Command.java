package dev.ilong299.logger.commands;

import dev.ilong299.logger.L299User;
import dev.ilong299.logger.Plugin299Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class L299Command extends Command {
    public L299Command(String name, String perm, String desc) {
        super(name);
        this.setPermission(perm);
        this.setDescription(desc);
        Plugin299Logger.register299Command(this);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        this.execute(new L299User(commandSender), s, strings);
        return true;
    }

    public abstract void execute(L299User user, String label, String[] args);
}
