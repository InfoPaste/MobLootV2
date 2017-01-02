package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import me.InfoPaste.MobLoot.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WorldMenu implements Listener {

    public static void openWorldMenu(Player player) {

        Inventory inventory = Bukkit.createInventory(player, 6 * 9, "MobLoot: Worlds");
        int i = 0;

        for (World world : Bukkit.getServer().getWorlds()) {

            if (WorldUtil.isEnabledWorld(world)) {
                inventory.setItem(i, enabledWorldIcon(world));
            } else {
                inventory.setItem(i, disableWorldIcon(world));
            }

            i++;
        }

        inventory.setItem(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build());

        player.openInventory(inventory);
    }

    private static ItemStack enabledWorldIcon(World world) {

        List<String> lore = new ArrayList<String>();

        lore.add("&7click the icon to &cdisable&7.");
        lore.add("&7MobLoot is &aenabled&7 in this world,");
        lore.add(" ");
        lore.add("&7Difficulty: " + world.getDifficulty().name());
        lore.add("&7World Type: " + world.getWorldType().name());
        lore.add("&7Environment: " + world.getEnvironment().name());

        Material material;

        switch (world.getEnvironment()) {
            case NETHER:
                material = Material.NETHERRACK;
                break;
            case NORMAL:
                material = Material.GRASS;
                break;
            case THE_END:
                material = Material.ENDER_STONE;
                break;
            default:
                material = Material.BEDROCK;
        }

        return new ItemStackBuilder(material).withName("&a" + world.getName()).withLore(lore).build();
    }

    private static ItemStack disableWorldIcon(World world) {

        List<String> lore = new ArrayList<String>();

        lore.add("&7MobLoot is &cdisabled&7 in this world,");
        lore.add("&7click the icon to &aenable&7.");
        lore.add(" ");
        lore.add("&7Difficulty: " + world.getDifficulty().name());
        lore.add("&7World Type: " + world.getWorldType().name());
        lore.add("&7Environment: " + world.getEnvironment().name());

        return new ItemStackBuilder(Material.WOOL, 14).withName("&c" + world.getName()).withLore(lore).build();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase("MobLoot: Worlds")) {

            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            Player player = (Player) event.getWhoClicked();

            if (!item.hasItemMeta()) {
                return;
            }

            String name = item.getItemMeta().getDisplayName();

            if (name.contains("Back")) {
                player.closeInventory();
                StaticMenus.getInstance().getMainMenu().open(player);
            } else {

                name = name.substring(2);

                List<String> disabledWorlds = Config.data.getStringList("DisabledWorlds");

                if (disabledWorlds.contains(name)) {
                    disabledWorlds.remove(name);
                    event.getInventory().setItem(event.getSlot(), enabledWorldIcon(Bukkit.getWorld(name)));
                } else {
                    disabledWorlds.add(name);
                    event.getInventory().setItem(event.getSlot(), disableWorldIcon(Bukkit.getWorld(name)));
                }

                player.updateInventory();

                Config.data.set("DisabledWorlds", disabledWorlds);
                Config.saveData();
            }
        }
    }


}
