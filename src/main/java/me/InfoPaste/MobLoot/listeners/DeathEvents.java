package me.InfoPaste.MobLoot.listeners;

import me.InfoPaste.MobLoot.core.Config;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class DeathEvents implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        World world = entity.getWorld();

        // TODO: Check how mob was spawn spawner or not and check config
        if (isEnabledWorld(world) && killer != null) {

        }
    }

    private boolean isEnabledWorld(World world) {

        List<String> disabledWorlds = Config.config.getStringList("Disable.Worlds");

        return !disabledWorlds.isEmpty() && disabledWorlds.contains(world.getName());

    }
}
