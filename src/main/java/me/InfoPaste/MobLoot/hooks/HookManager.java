package me.InfoPaste.MobLoot.hooks;

import org.bukkit.Bukkit;

public class HookManager {

    private static VaultHook vault;

    public static void loadDependencies() {
        if (doesPluginExist("Vault", "[MobLoot] Hooked: Vault")) {
            vault = new VaultHook();
        }
    }

    public static boolean doesPluginExist(String plugin, String message) {
        boolean hooked = (Bukkit.getPluginManager().getPlugin(plugin) != null);
        if (hooked) {
            System.out.println(message);
        }
        return hooked;
    }

    public static boolean doesPluginExist(String plugin) {
        return (Bukkit.getPluginManager().getPlugin(plugin) != null);
    }


    public static boolean isVaultLoaded() {
        return vault != null;
    }

    public static void getPlayerMoney(String player, double amount) {
        if (isVaultLoaded()) {
            vault.takePlayerMoney(player, amount);
        }
    }

    public static void givePlayerMoney(String player, double amount) {
        if (isVaultLoaded()) {
            vault.depositPlayer(player, amount);
        }
    }


}
