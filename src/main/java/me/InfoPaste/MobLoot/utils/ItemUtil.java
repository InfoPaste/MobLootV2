package me.InfoPaste.MobLoot.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static double getPercent(ItemStack item) {

        // Default drop percent 50%
        double dropPercent = 50;

        if (item.hasItemMeta()) {

            ItemMeta itemMeta = item.getItemMeta();
            List<String> lores = itemMeta.getLore();

            if (lores != null) {

                for (int d = 0; d < lores.size(); d++) {
                    String lore = lores.get(d);
                    if (lore.contains("|| Percent: ")) {
                        dropPercent = Double.valueOf(lore.substring(2).replaceAll("[^\\d.]", ""));
                        break;
                    }
                }

                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
            }
        }
        return dropPercent;

    }

    public static ItemStack removeDoubleLine(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {

            ItemMeta itemMeta = item.getItemMeta();
            List<String> lores = itemMeta.getLore();

            ArrayList toRemove = new ArrayList();

            for (String lore : lores) {
                if (lore.contains("|| ")) {
                    toRemove.add(lore);
                }
            }
            lores.removeAll(toRemove);

            itemMeta.setLore(lores);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

}
