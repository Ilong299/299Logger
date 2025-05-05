package dev.ilong299.logger.commands;

import dev.ilong299.logger.L299User;
import dev.ilong299.logger.Plugin299Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LoggerSave extends L299Command {
    public LoggerSave() {
        super("savelogs", "299logger.save", "Save last records to file");
    }

    @Override
    public void execute(L299User user, String label, String[] args) {
        user.sendMessage("&aSaving...");
        Plugin299Logger.getActualLogManager().stopRecording();
        Plugin299Logger.getActualLogManager().startRecording();
        user.sendMessage("&aSaved");
    }
}
