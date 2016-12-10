package me.InfoPaste.MobLoot;

import me.InfoPaste.MobLoot.commands.ItemCommands;
import me.InfoPaste.MobLoot.commands.MainCommands;
import me.InfoPaste.MobLoot.core.Config;
import me.InfoPaste.MobLoot.core.StaticMenus;
import me.InfoPaste.MobLoot.hooks.HookManager;
import me.InfoPaste.MobLoot.listeners.DeathEvents;
import me.InfoPaste.MobLoot.objects.Timer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static me.InfoPaste.MobLoot.objects.Logger.loadLogger;

public class Main extends JavaPlugin {

    public static Plugin plugin;

    private static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    private static String checkWebsiteForString() {

        try {

            int resource = 21653;

            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
                            .getBytes("UTF-8"));

            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            if (version.length() <= 7) {
                return version.replaceAll("[B]", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String getPrefix() {
        return "&8[&aMobLoot&8]&7 ";
    }

    @Override
    public void onEnable() {

        Timer startUp = new Timer(true);

        plugin = this;

        HookManager.loadDependencies();
        Config.setup();
        loadCommands();

        // Check logger into a Singleton?
        loadLogger();

        registerEvents(this, new DeathEvents());

        StaticMenus st = StaticMenus.getInstance();
        st.initializeMenus();

        startUp.stop();
        getLogger().info("Start up time: " + startUp.time() + "ms");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                System.out.println("[MobLoot] Checking for update...");
                String website = Main.checkWebsiteForString();
                if (website.equalsIgnoreCase(plugin.getDescription().getVersion())) {
                    System.out.print("[MobLoot] You are using the most current version");
                } else if (website.equalsIgnoreCase("Error")) {
                    System.out.print("[MobLoot] Error checking for update... couldn't connect to spigotmc.org");
                } else {
                    System.out.print("[MobLoot] A new update is available!");
                }
            }
        });
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    private void loadCommands() {
        getCommand("item").setExecutor(new ItemCommands());
        getCommand("mobloot").setExecutor(new MainCommands());
    }
}
