package dev.ilong299.logger.search;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import dev.ilong299.logger.Plugin299Logger;
import dev.ilong299.logger.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Search {

    private static String f = Plugin299Logger.getInstance().getConfig().getString("logFormat", "");

    private static void send(String s1) {
        Bukkit.getConsoleSender().sendMessage(StringUtils.format(s1));
    }

    public static void searchQuery(List<String> senders, List<String> types, List<String> queries, List<String> logFiles) throws IOException {
        send("CAUTION: When searching in a log file saved using a configuration other than the current configuration, the search will not work properly");
        if(logFiles.contains("*")) {
            File[] f11 =  Plugin299Logger.getInstance().getDataFolder().listFiles();
            logFiles.clear();
            for(File bf : f11) {
                if(bf.getName().equals("config.yml")) continue;
                logFiles.add(bf.getName());
            }
        }
        for(String logFilePath: logFiles) {
            send("\nSearching in: " + logFilePath + "\n");
            List<String> content = Files.asCharSource(new File(Plugin299Logger.getInstance().getDataFolder(), logFilePath), Charsets.UTF_8).readLines();
            for(String c2: content) {
                boolean s11 = false;
                if(c2.equals("*")) {
                    s11=true;
                }
                String c1 = c2.toLowerCase();
                if( types.stream().anyMatch(c1::contains) || !s11 ) {
                    send(c1);
                    continue;
                } else {
                    c1 = c1.replace("COMMAND", "").replace("COMMAND_PREPROCESS", "").replaceAll("ASYNC_CHAT", "");
                }
                if( senders.stream().anyMatch(c1::contains) || !s11) {
                    send(c1);
                    continue;
                }
                if(queries.stream().anyMatch(s-> c2.contains(s) || s.contains(c2)) || !s11) {
                    send(c1);
                    continue;
                }
            }
        }
        send("Search process ended");
    }

}
