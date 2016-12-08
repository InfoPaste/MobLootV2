package me.InfoPaste.MobLoot.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ItemCommands implements CommandExecutor, TabCompleter {


    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
