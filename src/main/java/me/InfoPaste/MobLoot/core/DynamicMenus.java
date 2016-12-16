package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.objects.IconMenu;
import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import me.InfoPaste.MobLoot.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DynamicMenus {

    private static DynamicMenus instance;

    static {
        instance = new DynamicMenus();
    }

    public DynamicMenus() {
    }

    public static DynamicMenus getInstance() {
        return instance;
    }

    public IconMenu loadEntityMenu(final String entity) {

        String path = "Mobs." + entity;


        if (Config.data.contains(path)) {

        } else {

        }

        return null;
    }

    public IconMenu loadWorldsMenu() {

        IconMenu iconMenu = new IconMenu("Worlds", 6 * 9, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent event) {

                event.setWillClose(false);

                Player player = event.getPlayer();
                String name = event.getName();

                if (name.equalsIgnoreCase("Back")) {

                    player.closeInventory();
                    StaticMenus.getInstance().getMainMenu().open(player);

                } else {
                    List<String> disabledWorlds = Config.data.getStringList("DisabledWorlds");

                    if (disabledWorlds.contains(name)) {
                        disabledWorlds.remove(name);

                        event.getInventory().setItem(event.getPosition(), enabledWorldIcon(Bukkit.getWorld(event.getName())));
                    } else {
                        disabledWorlds.add(name);
                        event.getInventory().setItem(event.getPosition(), disableWorldIcon(Bukkit.getWorld(event.getName())));
                    }

                    player.updateInventory();

                    Config.data.set("DisabledWorlds", disabledWorlds);
                    Config.saveData();
                    Config.reloadDataFile();

                }
            }
        }, new IconMenu.OptionCloseEventHandler() {
            public void onOptionClose(IconMenu.OptionCloseEvent event) {
                event.getPlayer().sendMessage("..");

            }
        });

        int i = 0;
        for (World world : Bukkit.getServer().getWorlds()) {

            if (WorldUtil.isEnabledWorld(world)) {
                iconMenu.setOption(i, enabledWorldIcon(world), world.getName());
            } else {
                iconMenu.setOption(i, disableWorldIcon(world), world.getName());
            }

            i++;
        }

        iconMenu.setOption(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build(), "Back");

        return iconMenu;
    }

    private ItemStack enabledWorldIcon(World world) {

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

    private ItemStack disableWorldIcon(World world) {

        List<String> lore = new ArrayList<String>();

        lore.add("&7MobLoot is &cdisabled&7 in this world,");
        lore.add("&7click the icon to &aenable&7.");
        lore.add(" ");
        lore.add("&7Difficulty: " + world.getDifficulty().name());
        lore.add("&7World Type: " + world.getWorldType().name());
        lore.add("&7Environment: " + world.getEnvironment().name());

        return new ItemStackBuilder(Material.WOOL, 14).withName("&c" + world.getName()).withLore(lore).build();
    }
}
