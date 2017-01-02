package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.hooks.HookManager;
import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import me.InfoPaste.MobLoot.objects.Timer;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.InfoPaste.MobLoot.Main.getPrefix;

public class MobMenus implements Listener {

    public static List<ItemStack> getMobItems(final String entity) {

        String path = "Mobs." + entity.toUpperCase() + ".Inventory.Items.";
        List<ItemStack> items = new ArrayList<ItemStack>();

        if (Config.data.contains(path)) {
            for (int i = 0; i < 45; i++) {
                ItemStack item = Config.data.getItemStack(path + i);

                if (item != null) {
                    items.add(item);
                }

            }
        }

        return items;
    }

    public static void openMobMenu(final Player player, final String entity) {
        openMobMenu(player, entity, true);
    }

    public static void openMobMenu(final Player player, final String entity, boolean showText) {

        Timer timer = new Timer(true);

        MobCategories mobCategory = MobCategories.getCategory(EntityType.valueOf(entity));
        String path = "Mobs." + entity;

        if (showText)
            TextUtil.sendMessage(player, getPrefix() + "Loading &e" + entity + "&7 inventory, this may take a few seconds...");

        Inventory inventory = Bukkit.createInventory(player, 6 * 9, mobCategory.getName() + ": " + entity);
        for (int i = 0; i < 45; i++) {
            ItemStack item = Config.data.getItemStack(path + ".Inventory.Items." + i);
            inventory.setItem(i, item);
        }

        List<String> moneyLore = new ArrayList<String>();
        moneyLore.add("&7Give players money for");
        moneyLore.add("&7killing this mob type");
        moneyLore.add("&7/ml <entity> setmoney <min> <max>");

        //Default Items
        inventory.setItem(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build());
        if (HookManager.isVaultLoaded()) {

            String moneyNum = "0";

            if (Config.data.contains(path + ".Money")) {
                moneyNum = Config.data.getString(path + ".Money");
            }
            moneyLore.add("&7&aVault Found");
            inventory.setItem(47, new ItemStackBuilder(Material.GOLD_NUGGET).withLore(moneyLore).withName("&aMoney: &f" + moneyNum).build());

        } else {
            moneyLore.add("&7&cRequires Vault");
            inventory.setItem(47, new ItemStackBuilder(Material.GOLD_NUGGET).withLore(moneyLore).withName("&aMoney: &fVault Not Found").build());
        }

        String drops = path + ".DefaultDrops";
        String exp = path + ".DefaultExp";
        String max = path + ".MaxDrops";

        if (!Config.data.contains(drops)) {
            Config.data.set(drops, true);
            Config.saveData();
        }
        if (!Config.data.contains(exp)) {
            Config.data.set(exp, true);
            Config.saveData();
        }
        if (!Config.data.contains(max)) {
            Config.data.set(max, 10);
            Config.saveData();
        }

        inventory.setItem(48, new ItemStackBuilder(Material.STRING).withName("&bDefault Dropped Items: &f" + Config.data.getBoolean(drops)).build());
        inventory.setItem(49, new ItemStackBuilder(Material.EXP_BOTTLE).withName("&bKeep Default Exp: &f" + Config.data.getBoolean(exp)).build());
        inventory.setItem(50, new ItemStackBuilder(Material.FIREBALL).withName("&bMax Item Drops: &f" + Config.data.getInt(max)).build());

        timer.stop();
        if (showText) TextUtil.sendMessage(player, getPrefix() + "Completed: &e" + timer.time() + "&7ms");

        player.openInventory(inventory);
    }

    public static Inventory buildCounterInventory(String title, String entity) {

        Inventory inventory = Bukkit.getServer().createInventory(null, 54, title + " - Max Item Drops");

        List<String> current = new ArrayList<String>();
        current.add("&7&oCurrently Set");

        for (int i = 0; i < 54; i++) {
            if ((i + 1) == Config.data.getInt("Mobs." + entity + ".MaxDrops")) {
                inventory.setItem(i, new ItemStackBuilder(Material.FIREBALL, (i + 1), 0).withName("&a" + (i + 1)).withLore(current).build());
            } else {
                inventory.setItem(i, new ItemStackBuilder(Material.SLIME_BALL, (i + 1), 0).withName("&r&f" + (i + 1)).build());
            }
        }

        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        String name = event.getInventory().getName();
        Player player = (Player) event.getWhoClicked();

        for (MobCategories mobCategory : MobCategories.values()) {
            if (name.toUpperCase().startsWith(mobCategory.name().toUpperCase())) {

                String entity = name.substring(mobCategory.name().length() + 2).toUpperCase();

                if (entity.endsWith(" - MAX ITEM DROPS")) {

                    name = entity.substring(0, entity.length() - " - Max Item Drops".length());
                    player.closeInventory();
                    Config.data.set("Mobs." + name + ".MaxDrops", event.getSlot() + 1);
                    Config.saveData();
                    MobMenus.openMobMenu(player, name, false);

                } else {

                    if (event.getSlot() > 44) {

                        event.setCancelled(true);

                        ItemStack item = event.getCurrentItem();

                        if (!item.hasItemMeta()) {
                            return;
                        }

                        String path = "Mobs." + entity;

                        if (item.getItemMeta().getDisplayName().contains("Back")) {
                            StaticMenus.getInstance().getMainMenu().open(player);
                            return;
                        } else if (item.getItemMeta().getDisplayName().contains("Money:")) {
                            // Could add something here to make it nice. Player performs the command? Change lore?
                            return;

                        } else if (item.getItemMeta().getDisplayName().contains("Default Dropped Items:")) {

                            if (Config.data.getBoolean(path + ".DefaultDrops")) {
                                Config.data.set(path + ".DefaultDrops", false);
                            } else {
                                Config.data.set(path + ".DefaultDrops", true);
                            }

                        } else if (item.getItemMeta().getDisplayName().contains("Disable Default Exp:")) {

                            if (Config.data.getBoolean(path + ".DefaultExp")) {
                                Config.data.set(path + ".DefaultExp", false);
                            } else {
                                Config.data.set(path + ".DefaultExp", true);
                            }
                        } else if (item.getItemMeta().getDisplayName().contains("Max Item Drops")) {
                            player.closeInventory();
                            player.openInventory(buildCounterInventory(event.getInventory().getName(), entity));
                            return;
                        }

                        player.closeInventory();
                        Config.saveData();
                        openMobMenu(player, entity, false);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String name = event.getInventory().getName();

        for (MobCategories mobCategory : MobCategories.values()) {
            if (name.toUpperCase().startsWith(mobCategory.name().toUpperCase())) {

                name = name.substring((mobCategory.name() + ": ").length());

                for (int i = 0; i < 45; i++) {
                    Config.data.set("Mobs." + name + ".Inventory.Items." + i, event.getInventory().getItem(i));
                }

                Config.saveData();
                return;
            }
        }
    }

}
