package me.InfoPaste.MobLoot.utils;

import me.InfoPaste.MobLoot.objects.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldUtil {

    public static boolean isWorldEnabled(World world) {


        return true;
    }

    public static ItemStackBuilder createWorldItem(World world, boolean enabled) {

        String name = world.getName();

        ItemStackBuilder item;


        if (!enabled) {
            // Color of red wool: 4
            item = new ItemStackBuilder(Material.WOOL, 1, (byte) 4);
        } else {

            Material material;

            switch (world.getEnvironment()) {
                case NORMAL:
                    material = Material.GRASS;
                    break;
                case NETHER:
                    material = Material.NETHERRACK;
                    break;
                case THE_END:
                    material = Material.ENDER_STONE;
                    break;

                default:
                    material = Material.BEDROCK;
                    break;
            }

            item = new ItemStackBuilder(material);
        }

        return item;
    }

}
