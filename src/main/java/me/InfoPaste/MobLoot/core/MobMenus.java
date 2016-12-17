package me.InfoPaste.MobLoot.core;

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

import static me.InfoPaste.MobLoot.Main.getPrefix;

public class MobMenus implements Listener {

    public static void openMobMenu(final Player player, final String entity) {

        Timer timer = new Timer(true);

        MobCategories mobCategory = MobCategories.getCategory(EntityType.valueOf(entity));
        String path = "Mobs." + entity + ".Inventory";

        TextUtil.sendMessage(player, getPrefix() + "Loading &e" + entity + "&7 inventory, this may take a few seconds...");

        Inventory inventory = Bukkit.createInventory(null, 6 * 9, mobCategory.getName() + ": " + entity);
        for (int i = 0; i < 45; i++) {
            ItemStack item = Config.data.getItemStack(path + ".Items." + i);
            inventory.setItem(i, item);
        }

        //Default Items
        inventory.setItem(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build());

        timer.stop();
        TextUtil.sendMessage(player, getPrefix() + "Completed: &e" + timer.time() + "&7ms");

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        String name = event.getInventory().getName();
        Player player = (Player) event.getWhoClicked();

        for (MobCategories mobCategory : MobCategories.values()) {
            if (name.toUpperCase().startsWith(mobCategory.name().toUpperCase())) {
                if (event.getSlot() > 44) {

                    ItemStack item = event.getCurrentItem();

                    if (item.getItemMeta().getDisplayName().contains("Back")) {
                        StaticMenus.getInstance().getMainMenu().open(player);
                    }

                    event.setCancelled(true);
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
