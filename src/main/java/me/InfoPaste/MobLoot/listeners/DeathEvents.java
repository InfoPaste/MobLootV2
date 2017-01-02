package me.InfoPaste.MobLoot.listeners;

import me.InfoPaste.MobLoot.core.Config;
import me.InfoPaste.MobLoot.core.MobMenus;
import me.InfoPaste.MobLoot.hooks.HookManager;
import me.InfoPaste.MobLoot.objects.Logger;
import me.InfoPaste.MobLoot.utils.ItemUtil;
import me.InfoPaste.MobLoot.utils.NumUtil;
import me.InfoPaste.MobLoot.utils.TextUtil;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
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
        if (isEnabledWorld(world)) {

            String path = "Mobs." + entity.getName();
            int dp = Config.config.getInt("MoneySettings.DecimalPlaces");

            // Item Handling
            boolean defaultDrops = Config.data.getBoolean(path + ".DefaultDrops");
            boolean disableExp = Config.data.getBoolean(path + ".DefaultExp");

            List<ItemStack> items = MobMenus.getMobItems(entity.getName());

            if (disableExp) {
                event.setDroppedExp(0);
            }

            if (Config.data.contains(path + ".DefaultDrops")) {
                if (!defaultDrops) {
                    event.getDrops().clear();
                }
            }

            if (items.size() > 0) {

                Collections.shuffle(items);

                int maxAmount = 10;
                int counter = 0;

                while (counter < maxAmount && counter < items.size()) {

                    double random = NumUtil.getRandomValue(0, 100, Config.config.getInt("RandomDecimalPlaces"));

                    ItemStack item = items.get(counter);

                    // Debugging
                    //System.out.println("Item:" + item.getType().name() + " Percent:" + ItemUtil.getPercent(item) + " Random:" + random);

                    if (random <= ItemUtil.getPercent(item)) {
                        event.getDrops().add(item);
                    }

                    counter++;
                }
            }
            double amountOfMoney = 0.0;
            // Money Handling
            if (Config.config.getBoolean("MoneySettings.RewardMoney")
                    && HookManager.doesPluginExist("Vault")
                    && Config.data.contains("Mobs." + entity.getName().toUpperCase() + ".Money")) {

                String money = Config.data.getString("Mobs." + entity.getName().toUpperCase() + ".Money");

                if (!money.replaceAll("[^0-9]", "").equalsIgnoreCase("0")) {

                    double max, min;
                    String[] moneyList = money.split("-", 2);

                    if (moneyList.length == 2) {

                        min = Double.valueOf(moneyList[0].replaceAll("[^\\d.]", ""));
                        max = Double.valueOf(moneyList[1].replaceAll("[^\\d.]", ""));
                        amountOfMoney = NumUtil.getRandomValue(min, max, dp);

                    } else {
                        amountOfMoney = Double.valueOf(moneyList[0].replaceAll("[^\\d.]", ""));
                    }

                    HookManager.givePlayerMoney(killer.getName(), amountOfMoney);

                    if (!Config.config.getBoolean("MoneySettings.DisableMessage") && amountOfMoney != 0) {

                        String message = TextUtil.colorize(Config.config.getString("MoneySettings.Message")
                                .replaceAll("%money%", TextUtil.stringFormatDecimalPlaces(amountOfMoney))
                                .replaceAll("%uentity%", TextUtil.formatEntityName(entity.getName()).toUpperCase())
                                .replaceAll("%lentity%", TextUtil.formatEntityName(entity.getName()).toLowerCase())
                                .replaceAll("%entity%", TextUtil.formatEntityName(entity.getName())));

                        killer.sendMessage(message);
                    }
                }
            }

            Logger.logger.drop(killer.getName(), entity.getName(), event.getDrops(), amountOfMoney);

        }
    }

    private boolean isEnabledWorld(World world) {
        List<String> disabledWorlds = Config.data.getStringList("DisabledWorlds");

        return !disabledWorlds.contains(world.getName());
    }
}
