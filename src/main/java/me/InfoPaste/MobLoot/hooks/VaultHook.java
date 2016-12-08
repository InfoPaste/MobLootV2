package me.InfoPaste.MobLoot.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

class VaultHook {

    private Economy economy;

    VaultHook() {
        ServicesManager services = Bukkit.getServicesManager();
        RegisteredServiceProvider<Economy> economyProvider = services.getRegistration(Economy.class);

        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }
    }

    void takePlayerMoney(String player, double amount) {
        if (this.economy != null) {
            this.economy.withdrawPlayer(player, amount);
        }
    }

    void depositPlayer(String player, double amount) {
        if (this.economy != null) {
            this.economy.depositPlayer(player, amount);
        }
    }
}
