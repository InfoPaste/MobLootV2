package me.InfoPaste.MobLoot.commands;

import me.InfoPaste.MobLoot.core.Config;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemCommands implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("item")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                String permissionMessage = TextUtil.colorize(Config.config.getString("PermissionDenied"));

                if (!sender.hasPermission("mobloot.item")) {
                    p.sendMessage(permissionMessage);
                    return true;
                }

                if (p.getItemInHand() == null || p.getItemInHand().getTypeId() == 0) {
                    sender.sendMessage(TextUtil.colorize("&cError: You are not holding an item in your hand"));
                    return true;
                } else {
                    ItemStack item = p.getItemInHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    if (args.length == 0) {
                        if (p.hasPermission("mobloot.item")) {
                            helpMessage(p);
                            return true;
                        } else {
                            p.sendMessage(permissionMessage);
                            return true;
                        }
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("hideattributes")) {
                            if (p.hasPermission("mobloot.item.hideattributes")) {
                                if (itemMeta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
                                    itemMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    p.sendMessage(TextUtil.colorize("&7Default Lores: &aUnhidden"));
                                } else {
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    p.sendMessage(TextUtil.colorize("&7Default Lores: &cHidden"));
                                }
                                item.setItemMeta(itemMeta);
                                p.getInventory().setItemInMainHand(item);
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("removelores")) {
                            if (p.hasPermission("mobloot.item.removelores")) {
                                itemMeta.setLore(null);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Lores: &cAll lores remove"));
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else {
                            helpMessage(p);
                        }
                    } else if (args.length >= 2) {
                        if (args[0].equalsIgnoreCase("name")) {
                            if (p.hasPermission("mobloot.item.name")) {

                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                itemMeta.setDisplayName(TextUtil.colorize("&r" + str.toString()));
                                item.setItemMeta(itemMeta);
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("console")) {
                            if (p.hasPermission("mobloot.item.command.console")) {
                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                }

                                itemLores.add(TextUtil.colorize("&7|| Console: " + str.toString()));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Console Command Set: " + str.toString()));
                                return true;

                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("player")) {
                            if (p.hasPermission("mobloot.item.command.player")) {
                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                }

                                itemLores.add(TextUtil.colorize("&7|| Player: " + str.toString()));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Player Command Set: " + str.toString()));
                                return true;

                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("broadcast")) {
                            if (p.hasPermission("mobloot.item.broadcast")) {
                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                }

                                itemLores.add(TextUtil.colorize("&7|| Broadcast: " + str.toString()));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Broadcast Set: " + str.toString()));
                                return true;

                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("message")) {
                            if (p.hasPermission("mobloot.item.message")) {
                                StringBuilder str = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                }

                                itemLores.add(TextUtil.colorize("&7|| Message: " + str.toString()));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Message Set: " + str.toString()));
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("lore")) {
                            if (p.hasPermission("mobloot.item.lore")) {
                                StringBuilder str = new StringBuilder();

                                for (int i = 1; i < args.length; i++) {
                                    str.append(args[i]).append(" ");
                                }

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                }
                                itemLores.add(TextUtil.colorize("&r" + str.toString()));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }

                        } else if (args[0].equalsIgnoreCase("percent") && args.length <= 2) {
                            if (p.hasPermission("mobloot.item.percent")) {
                                double percent = Double.valueOf(args[1]);

                                List<String> itemLores = itemMeta.getLore();
                                if (itemLores == null) {
                                    itemLores = new LinkedList();
                                } else {
                                    for (int i = 0; i < itemLores.size(); i++) {
                                        String string = TextUtil.colorize(itemLores.get(i));
                                        if (string.contains("|| Percent: ")) {
                                            itemLores.remove(i);
                                        }
                                    }
                                }
                                itemLores.add(TextUtil.colorize("&7|| Percent: " + percent + "%"));
                                itemMeta.setLore(itemLores);
                                item.setItemMeta(itemMeta);
                                p.sendMessage(TextUtil.colorize("&7Percentage Set: &a" + percent + "%"));
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("enchant") && args.length > 1 && args.length <= 3) {
                            if (p.hasPermission("mobloot.item.enchant")) {
                                int level = 1;
                                if (args.length == 3) {
                                    try {
                                        level = Integer.valueOf(args[2]);
                                    } catch (Exception e) {
                                        p.sendMessage(TextUtil.colorize("&cError: Invalid number"));
                                    }
                                } else {
                                    p.sendMessage(TextUtil.colorize("&7Enchanment level was not set. Using default of 1."));
                                }
                                p.sendMessage(TextUtil.colorize(enchantItem(item, args[1], level)));
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        } else {
                            if (p.hasPermission("ehub.item")) {
                                helpMessage(p);
                                return true;
                            } else {
                                p.sendMessage(permissionMessage);
                                return true;
                            }
                        }

                    }
                    return true;
                }

            } else {
                sender.sendMessage("Sorry only players can use this command.");
                return true;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("item") && sender.hasPermission("mobloot.item")) {
            if (args.length == 1) {
                ArrayList<String> options = new ArrayList<String>();

                if (sender.hasPermission("mobloot.item.hideattributes"))
                    options.add("hideattributes");

                if (sender.hasPermission("mobloot.item.removelores"))
                    options.add("removelores");

                if (sender.hasPermission("mobloot.item.name"))
                    options.add("name");

                if (sender.hasPermission("mobloot.item.lore"))
                    options.add("lore");

                if (sender.hasPermission("mobloot.item.percent"))
                    options.add("percent");

                if (sender.hasPermission("mobloot.item.enchant"))
                    options.add("enchant");

                if (sender.hasPermission("mobloot.item.command.console"))
                    options.add("console");

                if (sender.hasPermission("mobloot.item.command.player"))
                    options.add("player");

                if (sender.hasPermission("mobloot.item.broadcast"))
                    options.add("broadcast");

                if (sender.hasPermission("mobloot.item.message"))
                    options.add("message");

                if (!args[0].equals("")) {
                    for (Iterator<String> iterator = options.iterator(); iterator.hasNext(); ) {
                        if (!iterator.next().toLowerCase().startsWith(args[0].toLowerCase())) {
                            iterator.remove();
                        }
                    }
                }

                Collections.sort(options);
                return options;

            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("enchant")) {
                    ArrayList<String> enchantmentOptions = new ArrayList<String>();

                    if (!args[1].equals("")) {
                        for (Enchantment e : Enchantment.values()) {
                            if (e.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                                enchantmentOptions.add(e.getName().toLowerCase());
                            }
                        }
                    } else {
                        for (Enchantment e : Enchantment.values()) {
                            enchantmentOptions.add(e.getName().toLowerCase());
                        }
                    }

                    Collections.sort(enchantmentOptions);
                    return enchantmentOptions;
                }
            }
        }

        return null;
    }

    public void helpMessage(Player p) {
        p.sendMessage(TextUtil.colorize("&a/item &7- Shows help menu"));

        if (p.hasPermission("mobloot.item.hideattributes"))
            p.sendMessage(TextUtil.colorize("&a/item hideattributes &7- Removes default lores from item-in-hand"));

        if (p.hasPermission("mobloot.item.removelores"))
            p.sendMessage(TextUtil.colorize("&a/item removelores &7- Removes all lores from item-in-hand"));

        if (p.hasPermission("mobloot.item.name"))
            p.sendMessage(TextUtil.colorize("&a/item name <Name> &7- Sets name to item-in-hand"));

        if (p.hasPermission("mobloot.item.lore"))
            p.sendMessage(TextUtil.colorize("&a/item lore <Lore> &7- Adds lore to item-in-hand"));

        if (p.hasPermission("mobloot.item.broadcast"))
            p.sendMessage(TextUtil.colorize("&a/item broadcast <Message> &7- Broadcasts a message when this item is picked up"));

        if (p.hasPermission("mobloot.item.message"))
            p.sendMessage(TextUtil.colorize("&a/item message <Message> &7- Send the player a message when this item is picked up"));

        if (p.hasPermission("mobloot.item.percent"))
            p.sendMessage(TextUtil.colorize("&a/item percent <Percent> &7- Sets item-in-hands drop rate (Between 0 - 100)"));

        if (p.hasPermission("mobloot.item.enchant"))
            p.sendMessage(TextUtil.colorize("&a/item enchant <Enchantment> <Level> &7- Adds enchantment to item-in-hand"));

        if (p.hasPermission("mobloot.item.command.console"))
            p.sendMessage(TextUtil.colorize("&a/item console <Command> &7- Executes a command by console when this item is picked up"));

        if (p.hasPermission("mobloot.item.command.player"))
            p.sendMessage(TextUtil.colorize("&a/item player <Command> &7- Executes a command by player when this item is picked up"));
    }

    public String enchantItem(ItemStack item, String enchant, int level) {
        try {
            Enchantment ech = Enchantment.getByName(enchant.toUpperCase());
            return enchantItem(item, ech, level);
        } catch (Exception e) {
            return "&cError: Could not find that enchantment";
        }
    }

    public String enchantItem(ItemStack item, Enchantment enchant, int level) {
        if (level < 1) {
            item.removeEnchantment(enchant);
            return "&aEnchantment removed (" + enchant.getName().toUpperCase() + ")";
        }
        if (level > 32000) {
            return "&cError: Level is too high for an enchantment";
        }
        item.addUnsafeEnchantment(enchant, level);
        return "&aItem enchanted added (" + enchant.getName().toUpperCase() + ", " + level + ")";
    }

}
