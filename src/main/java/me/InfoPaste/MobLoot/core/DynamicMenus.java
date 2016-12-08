package me.InfoPaste.MobLoot.core;

import me.InfoPaste.MobLoot.objects.IconMenu;
import org.bukkit.Material;
import org.bukkit.World;

import static me.InfoPaste.MobLoot.Main.plugin;

public class DynamicMenus {

    private static DynamicMenus instance;

    public DynamicMenus() {
    }

    static {
        instance = new DynamicMenus();
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

        IconMenu iconMenu = new IconMenu("Worlds", 6 * 9, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent event) {

            }
        });

        for (World world : plugin.getServer().getWorlds()) {

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
            }
        }

        return null;
    }
}
