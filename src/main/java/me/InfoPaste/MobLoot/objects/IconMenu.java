package me.InfoPaste.MobLoot.objects;

import me.InfoPaste.MobLoot.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.InfoPaste.MobLoot.Main.plugin;

public class IconMenu implements Listener {

    private String name;
    private int size;
    private OptionClickEventHandler clickHandler;
    private OptionCloseEventHandler closeHandler;

    private String[] optionNames;
    private ItemStack[] optionIcons;

    public IconMenu(String name, int size, OptionClickEventHandler handler1, OptionCloseEventHandler handler2) {
        this.name = name;
        this.size = size;
        this.clickHandler = handler1;
        this.closeHandler = handler2;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public IconMenu(String name, int size, OptionCloseEventHandler handler) {
        this.name = name;
        this.size = size;
        this.closeHandler = handler;
        this.clickHandler = new OptionClickEventHandler() {
            public void onOptionClick(OptionClickEvent event) {
                return;
            }
        };
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public IconMenu(String name, int size, OptionClickEventHandler handler) {
        this.name = name;
        this.size = size;
        this.clickHandler = handler;
        this.closeHandler = new OptionCloseEventHandler() {
            public void onOptionClose(OptionCloseEvent event) {
                return;
            }
        };
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }

    public IconMenu setOption(int position, ItemStack icon, String name) {
        optionNames[position] = name;
        optionIcons[position] = icon;
        return this;
    }

    public IconMenu setOption(int position, ItemStackBuilder icon, String name) {
        optionNames[position] = name;
        optionIcons[position] = icon.build();
        return this;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        return inventory;
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        clickHandler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot], event.getClick(), event.getCurrentItem(), event.getClickedInventory());
                clickHandler.onOptionClick(e);

                if (e.willClose()) {
                    final Player p = (Player) event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            OptionCloseEvent e = new OptionCloseEvent((Player) event.getPlayer(), event.getInventory());
            closeHandler.onOptionClose(e);
            if (e.willDestroy()) {
                destroy();
            }
        }
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public interface OptionClickEventHandler {
        void onOptionClick(OptionClickEvent event);
    }

    public interface OptionCloseEventHandler {
        void onOptionClose(OptionCloseEvent event);
    }

    public class OptionCloseEvent {
        private Inventory inventory;
        private Player player;
        private boolean destroy;

        public OptionCloseEvent(Player player, Inventory inventory) {
            this.inventory = inventory;
            this.player = player;
            this.destroy = false;
        }

        public Player getPlayer() {
            return player;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public boolean willDestroy() {
            return destroy;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }

    }

    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
        private ClickType clickType;
        private ItemStack item;
        private Inventory inventory;

        public OptionClickEvent(Player player, int position, String name, ClickType clickType, ItemStack item, Inventory inventory) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
            this.clickType = clickType;
            this.item = item;
            this.inventory = inventory;
        }

        public Player getPlayer() {
            return player;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public boolean willClose() {
            return close;
        }

        public boolean willDestroy() {
            return destroy;
        }

        public void setWillClose(boolean close) {
            this.close = close;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }

        public ClickType getClick() {
            return clickType;
        }

        public ItemStack getClickedItem() {
            return item;
        }

        public Inventory getInventory() {
            return inventory;
        }
    }

}
