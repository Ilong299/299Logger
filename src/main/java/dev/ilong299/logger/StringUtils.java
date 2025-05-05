package dev.ilong299.logger;

import org.bukkit.ChatColor;

public class StringUtils {

    protected StringUtils() {}

    public static String format(
            String input
    ) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
