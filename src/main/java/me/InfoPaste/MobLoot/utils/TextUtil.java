package me.InfoPaste.MobLoot.utils;

import me.InfoPaste.MobLoot.core.Config;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;

public class TextUtil {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String stringFormatDecimalPlaces(double value) {

        String pattern = Config.config.getString("MoneySettings.DisplayFormat");
        DecimalFormat df = new DecimalFormat(pattern);

        return df.format(value);
    }

    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(colorize(message));
        return;
    }

    public static String formatEntityName(String entityName) {
        return WordUtils.capitalize(entityName.replaceAll("_", " ").toLowerCase());
    }
}
