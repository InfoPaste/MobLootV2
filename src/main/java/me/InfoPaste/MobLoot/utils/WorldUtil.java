package me.InfoPaste.MobLoot.utils;

import me.InfoPaste.MobLoot.core.Config;
import org.bukkit.World;

public class WorldUtil {

    public static boolean isEnabledWorld(World world) {
        return !Config.data.getStringList("DisabledWorlds").contains(world.getName());
    }

}
