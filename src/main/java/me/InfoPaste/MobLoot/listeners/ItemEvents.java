package me.InfoPaste.MobLoot.listeners;

import me.InfoPaste.MobLoot.utils.ItemUtil;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemEvents implements Listener {

    @EventHandler
    public static void onHopperPickUp(InventoryPickupItemEvent event) {
        event.getItem().setItemStack(ItemUtil.removeDoubleLine(event.getItem().getItemStack()));
    }

    @EventHandler
    public void onItemPickUpEvent(PlayerPickupItemEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {

            ItemMeta itemMeta = item.getItemMeta();
            List<String> lores = itemMeta.getLore();

            ArrayList toRemove = new ArrayList();

            for (String lore : lores) {

                if (lore.contains("|| Percent: ")) {
                    toRemove.add(lore);


                } else if (lore.contains("|| Broadcast: ")) {
                    String[] str = lore.split(" Broadcast: ", 2);
                    String broadcast = TextUtil.colorize(str[1]
                            .replaceAll("%player%", player.getName()));

                    Bukkit.getServer().broadcastMessage(broadcast);
                    toRemove.add(lore);


                } else if (lore.contains("|| Message: ")) {

                    String[] str = lore.split(" Message: ", 2);
                    String message = TextUtil.colorize(str[1]
                            .replaceAll("%player%", player.getName()));

                    player.sendMessage(message);
                    toRemove.add(lore);


                } else if (lore.contains("|| Console: ")) {

                    String[] str = lore.split(" Console: ", 2);
                    String message = TextUtil.colorize(str[1]
                            .replaceAll("%player%", player.getName()));

                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), message);
                    toRemove.add(lore);


                } else if (lore.contains("|| Player: ")) {

                    String[] str = lore.split(" Player: ", 2);
                    String message = TextUtil.colorize(str[1]
                            .replaceAll("%player%", player.getName()));

                    player.performCommand(message);
                    toRemove.add(lore);


                } else if (lore.contains("|| Remove Item: ")) {

                } else if (lore.contains("|| Money: ")) {

                }

            }
            lores.removeAll(toRemove);

            itemMeta.setLore(lores);
            item.setItemMeta(itemMeta);
        }

        event.getItem().setItemStack(item);
    }
}
