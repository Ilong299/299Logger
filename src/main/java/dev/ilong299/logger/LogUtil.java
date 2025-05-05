package dev.ilong299.logger;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import dev.ilong299.logger.datetime.DateTimeFormatter;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;

public class LogUtil {

    private static LogManager logMgr;
    private static String format2;

    public static void initLogUtil(LogManager mgr, String defFormat) {
        logMgr = mgr;
        format2 = defFormat;
    }

    private static String process(String sender, String type, String... strings) {
        String format = DateTimeFormatter.formatDateAndTime(format2);
        format = format.replace(Matcher.quoteReplacement("{WHO}"), sender);
        String what = "";
        for(String s: strings) {
            what = what + s;
        }
        format = format.replace(Matcher.quoteReplacement("{WHAT}"), what);
        format = format.replace(Matcher.quoteReplacement("{TYPE}"), type);
        sender = null;
        type = null;
        strings = null;
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    public static void record(String sender, String type, String... strings) {
//        if (strings.length != 0) {
        logMgr.addRecord(process(sender, type, strings));
        sender = null;
        type = null;
        strings = null;
//        }
    }
}
