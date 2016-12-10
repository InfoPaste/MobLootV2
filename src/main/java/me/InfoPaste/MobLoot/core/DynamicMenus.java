package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.objects.IconMenu;
import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import me.InfoPaste.MobLoot.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    public IconMenu loadEntityMenu(String entity) {

        //TODO: Load Menu from data file Async

        return null;
    }

    public IconMenu loadWorldsMenu() {

        //TODO: Create worlds into a menu

        final IconMenu iconMenu = new IconMenu("Worlds", 6 * 9, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent event) {

                event.setWillClose(false);

                Player player = event.getPlayer();
                String name = event.getName();

                if (name.equalsIgnoreCase("Back")) {
                    StaticMenus.getInstance().getMainMenu().open(player);
                } else {
                    List<String> disabledWorlds = Config.data.getStringList("DisabledWorlds");

                    if (disabledWorlds.contains(name)) {
                        disabledWorlds.remove(name);
                    } else {
                        disabledWorlds.add(name);
                    }

                    // TODO: Replace Icon or reload inventory

                    Config.data.set("DisabledWorlds", disabledWorlds);
                    Config.saveData();
                    Config.reloadDataFile();

                }
            }
        });

        int i = 0;
        for (World world : Bukkit.getServer().getWorlds()) {

            List<String> lore = new ArrayList<String>();
            lore.add(" ");
            lore.add("&7Difficulty: " + world.getDifficulty().name());
            lore.add("&7World Type: " + world.getWorldType().name());
            lore.add("&7Environment: " + world.getEnvironment().name());

            if (WorldUtil.isEnabledWorld(world)) {

                lore.add(0, "&7click the icon to &cdisable&7.");
                lore.add(0, "&7MobLoot is &aenabled&7 in this world,");

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

                iconMenu.setOption(i, new ItemStackBuilder(material).withName("&a" + world.getName()).withLore(lore).build(), world.getName());
            } else {
                lore.add(0, "&7click the icon to &aenable&7.");
                lore.add(0, "&7MobLoot is &cdisabled&7 in this world,");
                iconMenu.setOption(i, new ItemStackBuilder(Material.WOOL, 14).withName("&c" + world.getName()).withLore(lore).build(), world.getName());
            }

            i++;
        }

        iconMenu.setOption(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build(), "Back");

        return iconMenu;
    }
}
