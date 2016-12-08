package me.InfoPaste.MobLoot.core;


import me.InfoPaste.MobLoot.objects.IconMenu;
import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import me.InfoPaste.MobLoot.objects.Timer;
import me.InfoPaste.MobLoot.utils.InventoryUtil;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static me.InfoPaste.MobLoot.Main.getPrefix;
import static me.InfoPaste.MobLoot.Main.plugin;

/*
 * Any menu that can me Initialized onEnable will be configured here
 */
public class StaticMenus {

    private static StaticMenus instance;

    private IconMenu mainMenu;
    private HashMap<MobCategories, IconMenu> subMenus;

    public StaticMenus() {
    }

    static {
        instance = new StaticMenus();
    }

    public static StaticMenus getInstance() {
        return instance;
    }

    public void initializeMenus() {

        plugin.getLogger().info("Initializing Static Menus");

        int i = 0;
        int inventorySize = InventoryUtil.convertToRows(MobCategories.values().length) + 9;
        this.subMenus = new HashMap<MobCategories, IconMenu>();

        /* MAIN MENU */
        this.mainMenu = new IconMenu("MobLoot V2: Beta 1", inventorySize, new IconMenu.OptionClickEventHandler() {

            // Event handler for Main Menu
            public void onOptionClick(IconMenu.OptionClickEvent event) {

                event.setWillClose(false);

                String name = event.getName();
                Player player = event.getPlayer();
                MobCategories mobCategory = MobCategories.getCategory(name);

                player.closeInventory();

                if (mobCategory != null) {
                    if (subMenus.containsKey(mobCategory)) {
                        subMenus.get(mobCategory).open(player);
                    } else {
                        // This message should never be sent
                        player.sendMessage("MobLoot >> Error: StaticMenus[46] Category not found. Please contact plugin developer.");
                    }
                } else if (name.equalsIgnoreCase("Worlds")) {
                    // Open Load Menu
                    DynamicMenus.getInstance().loadWorldsMenu().open(player);
                } else {
                    event.setWillClose(true);
                    // Not a mob Category Menu
                    //TODO: Open other menu
                }
            }
        });

        for (MobCategories mobCategory : MobCategories.values()) {

            /* MAIN MENU */
            String name = mobCategory.getName();
            // Sets category icons to each slots in main inventory
            mainMenu.setOption(i, mobCategory.getIcon(), name);
            i++;

            /* MOB MENUS */
            // Creates category menu
            IconMenu mobMenu = new IconMenu(mobCategory.getFormattedName(), 6 * 9, new IconMenu.OptionClickEventHandler() {

                // Category menu event handler
                public void onOptionClick(IconMenu.OptionClickEvent event) {

                    event.setWillClose(false);

                    String name = event.getName();
                    Player player = event.getPlayer();

                    if (name.equalsIgnoreCase("Back")) {
                        mainMenu.open(player);
                    } else {

                        Timer timer = new Timer(true);
                        TextUtil.sendMessage(player, getPrefix() + "Loading &e" + TextUtil.formatEntityName(name) + "&7, this may take a few seconds...");

                        // Load menu of entity they clicked
                        DynamicMenus dynamicMenus = DynamicMenus.getInstance();
                        IconMenu entityMenu = dynamicMenus.loadEntityMenu(name);

                        if (entityMenu != null) {
                            // Open menu of entity they clicked
                            entityMenu.open(player);
                        } else {
                            // Something when wrong
                        }

                        timer.stop();
                        TextUtil.sendMessage(player, getPrefix() + "Completed: &e" + timer.time() + "&7ms");
                    }
                    //TODO: Open menu they of mob the clicked
                }
            });

            int j = 0;

            // Assigns entity type icons in menu
            for (EntityType entityType : mobCategory.getMobs()) {

                String entityName = entityType.name();

                // Assign entity icons with heads
                ItemStack icon = new ItemStackBuilder(Material.SKULL_ITEM, 3)
                        .setPlayerHead(Head.getHeadOf(entityType))
                        .withName(mobCategory.getColor() + TextUtil.formatEntityName(entityName))
                        .build();

                mobMenu.setOption(j, icon, entityName);
                j++;
            }

            // Static Menu Icons, (Back Option, etc.)
            mobMenu.setOption(45, new ItemStackBuilder(Material.REDSTONE).withName("&cBack").build(), "Back");

            subMenus.put(mobCategory, mobMenu);
        }

        /* MAIN MENU - Static items */

        mainMenu.setOption(9, new ItemStackBuilder(Material.GRASS).withName("&aWorlds").build(), "World");

        return;
    }

    public IconMenu getMainMenu() {
        return mainMenu;
    }
}