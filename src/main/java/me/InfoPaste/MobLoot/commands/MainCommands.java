package me.InfoPaste.MobLoot.commands;

import me.InfoPaste.MobLoot.Main;
import me.InfoPaste.MobLoot.core.Config;
import me.InfoPaste.MobLoot.core.MobMenus;
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
import java.util.Iterator;
import java.util.List;

public class MainCommands implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("mobloot")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Commands can only be executed by players.");
                return true;
            }

            if (args.length < 1) {
                /* /mobloot */
                if (sender.hasPermission("mobloot.gui")) {
                    // Opens Main menu for player
                    StaticMenus.getInstance().getMainMenu().open((Player) sender);
                } else {
                    permissionDenied(sender);
                }
            } else {
                /* /mobloot help || /mobloot ? */
                if (args[0].equalsIgnoreCase("help")
                        || args[0].equalsIgnoreCase("?")) {

                    if (sender.hasPermission("mobloot.help")) {
                        showHelpMenu(sender);
                    } else {
                        permissionDenied(sender);
                    }
                } else if (args[0].equalsIgnoreCase("info")) {

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
                } else if (args[0].equalsIgnoreCase("setmoney")) {
                    if (sender.hasPermission("mobloot.reload")) {
                        Config.reloadAll();
                        sender.sendMessage(TextUtil.colorize("&aSuccessfully reloaded configuration files"));
                    } else {
                        permissionDenied(sender);
                    }
                } else {

                    for (EntityType entityType : EntityType.values()) {
                        if (entityType.name().equalsIgnoreCase(args[0])
                                || entityType.name().replaceAll("_", "").equalsIgnoreCase(args[0])) {

                            if (args.length < 2) {
                                MobMenus.openMobMenu((Player) sender, entityType.name());
                            } else {
                                if (args[1].equalsIgnoreCase("setmoney")) {

                                    //TODO: Check if its a number

                                    double min;
                                    double max;

                                    switch (args.length) {
                                        case 4:
                                            max = Double.valueOf(args[3]);
                                            min = Double.valueOf(args[2]);

                                            if (max < min) {
                                                max = min;
                                                min = Double.valueOf(args[3]);
                                            }
                                            break;

                                        case 3:
                                            max = Double.valueOf(args[2]);
                                            min = Double.valueOf(args[2]);
                                            break;

                                        default:
                                            TextUtil.sendMessage(sender, "&8/&aml <entity> setmoney <min> (max) &8- &7Opens menu of entity");
                                            return true;
                                    }

                                    String range = min + "-" + max;

                                    Config.data.set("Mobs." + entityType.name() + ".Money", range);
                                    Config.saveData();

                                    TextUtil.sendMessage(sender, Main.getPrefix() + "Setting money range between &f" + min + "&7 and &f" + max + "&7 for &a" + TextUtil.formatEntityName(entityType.name()));

                                } else if (args[1].equalsIgnoreCase("setmax")) {
                                    //TODO: Maybe add this command it or stick to GUI - TBD
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> options = new ArrayList<String>();
        if (args.length == 1) {

            if (sender.hasPermission("mobloot.gui")) {
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.isAlive()) {
                        options.add(entityType.name().toLowerCase());
                    }
                }
            }

            if (!args[0].equals("")) {
                for (Iterator<String> iterator = options.iterator(); iterator.hasNext(); ) {
                    if (!iterator.next().toLowerCase().startsWith(args[0].toLowerCase())) {
                        iterator.remove();
                    }
                }
            }

            Collections.sort(options);
        }

        return options;
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

        if (sender.hasPermission("mobloot.gui")) {
            commands.add("&8/&aml <entity> &8- &7Opens menu of entity &8(&7hit tab to see options&8)");
        }

        if (sender.hasPermission("mobloot.setmoney")) {
            commands.add("&8/&aml <entity> setmoney <min> (max) &8- &7Opens menu of entity");
        }

        Collections.sort(commands);

        for (String command : commands) {
            TextUtil.sendMessage(sender, command);
        }

        return;
    }
}
