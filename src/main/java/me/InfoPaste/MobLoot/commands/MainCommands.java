package me.InfoPaste.MobLoot.commands;

import me.InfoPaste.MobLoot.Main;
import me.InfoPaste.MobLoot.core.Config;
import me.InfoPaste.MobLoot.core.StaticMenus;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommands implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("mobloot")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Commands can only be executed by players.");
                return true;
            }


            if (args.length == 0) {
                /* /mobloot */
                if (sender.hasPermission("mobloot.gui")) {
                    // Opens Main menu for player
                    StaticMenus.getInstance().getMainMenu().open((Player) sender);
                } else {
                    permissionDenied(sender);
                }
            } else {
                /* /mobloot help || /mobloot ? */
                if (args[1].equalsIgnoreCase("help")
                        || args[1].equalsIgnoreCase("?")) {

                    if (sender.hasPermission("mobloot.help")) {
                        showHelpMenu(sender);
                    } else {
                        permissionDenied(sender);
                    }
                } else if (args[1].equalsIgnoreCase("info")) {

                    if (sender.hasPermission("mobloot.help")) {

                        //TODO: Show information menu

                    } else {
                        permissionDenied(sender);
                    }

                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("mobloot.reload")) {
                        Config.reloadAll();
                        sender.sendMessage(TextUtil.colorize("&aSuccessfully reloaded configuration files"));
                    } else {
                        permissionDenied(sender);
                    }
                } else {

                    for (EntityType entityType : EntityType.values()) {
                        if (entityType.name().equalsIgnoreCase(args[1])
                                || entityType.name().replaceAll("_", "").equalsIgnoreCase(args[1])
                                || entityType.name().replaceAll("_", " ").equalsIgnoreCase(args[1] + " " + args[2])) {

                            TextUtil.sendMessage(sender, Main.getPrefix() + "Opening " + entityType.name() + "...");
                            //TODO: Open menu of entity type


                        }
                    }
                }
            }

            return true;
        }

        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

    private void permissionDenied(CommandSender sender) {
        TextUtil.sendMessage(sender, Config.config.getString("PermissionDenied"));
        return;
    }

    private void showHelpMenu(CommandSender sender) {

        List<String> commands = new ArrayList<String>();

        if (sender.hasPermission("mobloot.gui")) {
            commands.add("&8/&aml &8- &7Opens MobLoots GUI");
        }

        if (sender.hasPermission("mobloot.item")) {
            commands.add("&8/&bitem &8- &7Opens MobLoots GUI");
        }

        if (sender.hasPermission("mobloot.help")) {
            commands.add("&8/&aml help &8- &7Displays help menu");
        }

        if (sender.hasPermission("mobloot.help")) {
            commands.add("&8/&bml help &8- &7Displays help menu");
        }

        Collections.sort(commands);

        for (String command : commands) {
            TextUtil.sendMessage(sender, command);
        }

        return;
    }
}
