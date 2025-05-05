package dev.ilong299.logger;

import dev.ilong299.logger.datetime.DateTimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

public class LogManager {

    ChatHandler chatListener = new ChatHandler();
    CommandHandler commandListener = new CommandHandler();
    ConsoleRconCommandHandler consoleRconCommandListener = new ConsoleRconCommandHandler();
    private final Plugin plugin = Plugin299Logger.getInstance();
    private List<String> records;
    String id;

    public LogManager(String id, boolean listenConsole, boolean listenRCON) {
        this.records = new ArrayList<>();
        this.id = id;
    }

    public void addRecord(String erecord) {
        this.records.add(erecord);
        erecord = null;
        return;
    }



    public void startRecording() {
        Bukkit.getPluginManager().registerEvents(chatListener, plugin);
        Bukkit.getPluginManager().registerEvents(commandListener, plugin);
        Bukkit.getPluginManager().registerEvents(consoleRconCommandListener, plugin);
    }
    private String randStr(int len) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = len;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public void stopRecording() {

        // Save output file

        try {
            File f1 = Plugin299Logger.getInstance().getDataFolder();
            File f2 = new File(f1, "latest.log");
            if(f2.exists()) {
                f2.delete();
            }
            Bukkit.getLogger().info( "[" +id+"] Saving...");
            String outformat = Plugin299Logger.getInstance().getConfig().getString("outputFileFormat", "");
            outformat = DateTimeFormatter.formatDateAndTime(outformat).replace(
                    Matcher.quoteReplacement("{RANDOM_8}"), randStr(8)
            ).replace(Matcher.quoteReplacement("{RANDOM_16"), randStr(16));
            File outfile = new File( f1, outformat);
            if(!outfile.exists()) {
                boolean ignore = outfile.createNewFile();
            }
            PrintWriter writer = new PrintWriter(outfile, "UTF-8");
            for(String  s1: records) {
                writer.println(s1);
                Bukkit.getLogger().info(s1);
            }
            writer.close();
            try {
                copyFileUsingStream(outfile, f2 );
            } catch (Exception e) {
                try {
                    f2.createNewFile();
                    copyFileUsingStream(outfile, f2 );
                } catch (Exception e1) {
                    return;
                }
            }
            records.clear();
            Bukkit.getLogger().info( "[" +id+"] Saved. ");

        } catch (Exception ignore) {
            Bukkit.getLogger().severe(id + " raised error while saving output file, contact ilon@ilongeniy.me if it happens a lot");
        }

        AsyncPlayerChatEvent.getHandlerList().unregister(chatListener);
        PlayerCommandPreprocessEvent.getHandlerList().unregister(commandListener);

        ServerCommandEvent.getHandlerList().unregister(consoleRconCommandListener);
        RemoteServerCommandEvent.getHandlerList().unregister(consoleRconCommandListener);

    }

}
