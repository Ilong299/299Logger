package dev.ilong299.logger.commands;

import dev.ilong299.logger.L299User;
import dev.ilong299.logger.SearchQueryBuilder;
import dev.ilong299.logger.search.Search;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class SearchCommand extends L299Command{

    public static boolean s1 = false;
    public static String last;
    private static int a;
    private static SearchQueryBuilder b = new SearchQueryBuilder();
    public SearchCommand() {
        super("search", "299logger.onlyconsole", "Search query from files");
    }

    @Override
    public void execute(L299User user, String label, String[] args) {
        if(!user.isConsole) {
            user.sendMessage("&cOnly CONSOLE can use 299Logger search");
            return;
        }
        if(last==null) {
            user.sendMessage("Enter names(CONSOLE, RCON, playername): ");
            s1 = true;
            a = 0;
            return;
        }

    }

    // List<String> senders, List<String> types, List<String> queries, List<String> logFiles
    public static void r(String obj) {
        if (obj.equalsIgnoreCase("break")) {
            a = -199;
            s1 = false;
        }
        if(a==0) {Bukkit.getConsoleSender().sendMessage("Enter types(ASYNC_CHAT, COMMAND, COMMAND_PREPROCESS");

            b.senders = build(obj);
            a = 1;
        }
        else if(a==1) {Bukkit.getConsoleSender().sendMessage("Enter search queries");

            b.types = build(obj);
            a = 2;
        } else if (a==2) {Bukkit.getConsoleSender().sendMessage("Enter log files");

            b.queries = build(obj);
            a = 3;
        } else if(a==3) {
            b.logFiles = build(obj);
            b.call();
            a = -199;
            s1 = false;
        }
    }

    private static List<String> build(String input) {
        List<String> l = new ArrayList<>();
        String a = input.replaceAll(",", " ");
        for(String s1: a.split(" ")) {
            l.add(s1);
        }
        return l;
    }

}
